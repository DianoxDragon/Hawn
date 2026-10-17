package fr.dianox.hawn.modules.ranks;

import fr.mrmicky.fastboard.FastReflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The teams of the tab list order, sent to the players in packets: nothing is created on the server (no team left
 * after a crash), the teams of the other plugins are never touched, and a plugin that gives its own scoreboard to a
 * player doesn't remove them. Only before 1.21.2: since then the tab list has its own order (Player#setPlayerListOrder).
 * <p>
 * On the model of the team packets of FastBoard, with its FastReflection to find the classes of Minecraft (1.16.5,
 * 1.17+, the Mojang names of Paper).
 */
final class RankTeams {

	// The modes of the team packet
	static final int CREATE = 0;
	static final int REMOVE = 1;
	static final int ADD_PLAYERS = 3;
	static final int REMOVE_PLAYERS = 4;

	private static boolean tried;
	private static boolean ready;

	private static Method getHandle;
	private static Field connection;
	private static Method sendPacket;

	// 1.17+: a team of Minecraft (no scoreboard), its parameters, then the packet
	private static Constructor<?> playerTeam;
	private static Constructor<?> parameters;
	private static Constructor<?> modernPacket;

	// 1.16.5: an empty packet, its fields set by type
	private static Constructor<?> legacyPacket;
	private static Field[] legacyFields;

	private RankTeams() {}

	/**
	 * @return false when the packets can't be made on this server (the reason is in the console, once)
	 */
	static synchronized boolean init() {
		if (tried) {
			return ready;
		}
		tried = true;

		try {
			Class<?> craftPlayer = FastReflection.obcClass("entity.CraftPlayer");
			Class<?> serverPlayer = FastReflection.nmsClass("server.level", "EntityPlayer", "ServerPlayer");
			Class<?> listener = FastReflection.nmsClass("server.network", "PlayerConnection", "ServerGamePacketListenerImpl");
			Class<?> packet = FastReflection.nmsClass("network.protocol", "Packet");
			Class<?> teamPacket = FastReflection.nmsClass("network.protocol.game", "PacketPlayOutScoreboardTeam", "ClientboundSetPlayerTeamPacket");

			getHandle = craftPlayer.getMethod("getHandle");
			connection = Arrays.stream(serverPlayer.getFields()).filter(f -> f.getType() == listener).findFirst()
					.orElseThrow(() -> new NoSuchFieldException("the connection of the player"));
			Stream<Method> methods = listener.getSuperclass() == null ? Arrays.stream(listener.getMethods())
					: Stream.concat(Arrays.stream(listener.getSuperclass().getMethods()), Arrays.stream(listener.getMethods()));
			sendPacket = methods.filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == packet).findFirst()
					.orElseThrow(() -> new NoSuchMethodException("sending a packet"));

			if (FastReflection.isRepackaged()) {
				Class<?> scoreboard = FastReflection.nmsClass("world.scores", "Scoreboard");
				Class<?> team = FastReflection.nmsClass("world.scores", "ScoreboardTeam", "PlayerTeam");
				playerTeam = team.getConstructor(scoreboard, String.class);

				Class<?> params = Arrays.stream(teamPacket.getDeclaredClasses()).filter(c -> !c.isEnum()).findFirst()
						.orElseThrow(() -> new ClassNotFoundException("the parameters of the team packet"));
				parameters = params.getDeclaredConstructor(team);
				parameters.setAccessible(true);

				modernPacket = teamPacket.getDeclaredConstructor(String.class, int.class, Optional.class, Collection.class);
				modernPacket.setAccessible(true);
			} else {
				legacyPacket = teamPacket.getDeclaredConstructor();
				legacyPacket.setAccessible(true);
				List<Field> fields = new ArrayList<>();
				for (Field f : teamPacket.getDeclaredFields()) {
					if (Modifier.isStatic(f.getModifiers())) continue;
					f.setAccessible(true);
					fields.add(f);
				}
				legacyFields = fields.toArray(new Field[0]);
			}

			ready = true;
		} catch (Throwable e) {
			Bukkit.getLogger().warning("[Hawn] Ranks: the order of the tab list can't be sent on this server (" + e + "), the ranks are not sorted");
		}
		return ready;
	}

	/**
	 * A team packet to one player. players: the names in the team (CREATE, ADD_PLAYERS, REMOVE_PLAYERS).
	 */
	static void send(Player viewer, String name, int mode, Collection<String> players) {
		if (!ready || !viewer.isOnline()) {
			return;
		}

		try {
			Object packet;
			if (modernPacket != null) {
				Optional<Object> params = Optional.empty();
				if (mode == CREATE) {
					// The default values of a team: no prefix, no colour, names always visible
					params = Optional.of(parameters.newInstance(playerTeam.newInstance(null, name)));
				}
				packet = modernPacket.newInstance(name, mode, params, mode == REMOVE ? new ArrayList<String>() : new ArrayList<>(players));
			} else {
				packet = legacyPacket.newInstance();
				setNth(packet, String.class, 0, name);
				setNth(packet, int.class, 0, mode);
				if (mode != REMOVE) {
					for (Field f : legacyFields) {
						if (Collection.class.isAssignableFrom(f.getType())) {
							@SuppressWarnings("unchecked")
							Collection<String> list = (Collection<String>) f.get(packet);
							list.addAll(players);
							break;
						}
					}
				}
			}

			sendPacket.invoke(connection.get(getHandle.invoke(viewer)), packet);
		} catch (Throwable e) {
			// Never a crash for the order of the tab list: off until the next restart
			ready = false;
			Bukkit.getLogger().warning("[Hawn] Ranks: a team packet could not be sent (" + e + "), the ranks are not sorted until the next restart");
		}
	}

	// The n-th field of this type (the fields of the packet keep the order of the source of Minecraft)
	private static void setNth(Object packet, Class<?> type, int n, Object value) throws IllegalAccessException {
		int i = 0;
		for (Field f : legacyFields) {
			if (f.getType() == type && i++ == n) {
				f.set(packet, value);
				return;
			}
		}
	}
}
