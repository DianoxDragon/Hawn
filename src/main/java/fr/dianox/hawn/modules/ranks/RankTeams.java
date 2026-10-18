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
 * The teams of the ranks, sent to the players in packets: nothing is created on the server (no team left after a
 * crash), the teams of the other plugins are never touched, and a plugin that gives its own scoreboard to a player
 * doesn't remove them. A team gives the prefix and the suffix above the head and the colour of the name (1.4.8), and
 * the order of the tab list before 1.21.2.
 * <p>
 * On the model of the team packets of FastBoard, with its FastReflection to find the classes of Minecraft (1.16.5,
 * 1.17+, the Mojang names of Paper): the fields are found by their type, never by their name.
 */
final class RankTeams {

	// The modes of the team packet
	static final int CREATE = 0;
	static final int REMOVE = 1;
	static final int UPDATE = 2;
	static final int ADD_PLAYERS = 3;
	static final int REMOVE_PLAYERS = 4;

	/**
	 * What a team shows: § texts and a colour name (RED, GOLD...; null for none).
	 */
	static final class Look {
		final String prefix;
		final String suffix;
		final String colour;

		Look(String prefix, String suffix, String colour) {
			this.prefix = prefix == null ? "" : prefix;
			this.suffix = suffix == null ? "" : suffix;
			this.colour = colour;
		}

		static final Look NONE = new Look("", "", null);

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Look)) return false;
			Look l = (Look) o;
			return prefix.equals(l.prefix) && suffix.equals(l.suffix) && java.util.Objects.equals(colour, l.colour);
		}

		@Override
		public int hashCode() {
			return java.util.Objects.hash(prefix, suffix, colour);
		}
	}

	private static boolean tried;
	private static boolean ready;

	private static Method getHandle;
	private static Field connection;
	private static Method sendPacket;
	// CraftChatMessage.fromString: a § text as a component of Minecraft
	private static Method fromString;

	// 1.17+: a team of Minecraft (no scoreboard), its parameters, then the packet
	private static Constructor<?> playerTeam;
	private static Field[] teamFields;
	private static Constructor<?> parameters;
	private static Constructor<?> modernPacket;

	// 1.16.5: an empty packet, its fields set by type
	private static Constructor<?> legacyPacket;
	private static Field[] legacyFields;

	// The component class and the colour enum of Minecraft
	private static Class<?> componentClass;
	private static Class<?> colourClass;
	// 26.x: the colour of a team is an Optional<TeamColor>, not a ChatFormatting
	private static boolean colourOptional;

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
			componentClass = FastReflection.nmsClass("network.chat", "IChatBaseComponent", "Component");

			getHandle = craftPlayer.getMethod("getHandle");
			connection = Arrays.stream(serverPlayer.getFields()).filter(f -> f.getType() == listener).findFirst()
					.orElseThrow(() -> new NoSuchFieldException("the connection of the player"));
			Stream<Method> methods = listener.getSuperclass() == null ? Arrays.stream(listener.getMethods())
					: Stream.concat(Arrays.stream(listener.getSuperclass().getMethods()), Arrays.stream(listener.getMethods()));
			sendPacket = methods.filter(m -> m.getParameterCount() == 1 && m.getParameterTypes()[0] == packet).findFirst()
					.orElseThrow(() -> new NoSuchMethodException("sending a packet"));

			Class<?> chatMessage = FastReflection.obcClass("util.CraftChatMessage");
			fromString = chatMessage.getMethod("fromString", String.class);

			Field[] fields;
			if (FastReflection.isRepackaged()) {
				Class<?> scoreboard = FastReflection.nmsClass("world.scores", "Scoreboard");
				Class<?> team = FastReflection.nmsClass("world.scores", "ScoreboardTeam", "PlayerTeam");
				playerTeam = team.getConstructor(scoreboard, String.class);
				teamFields = instanceFields(team);
				fields = teamFields;

				Class<?> params = Arrays.stream(teamPacket.getDeclaredClasses()).filter(c -> !c.isEnum()).findFirst()
						.orElseThrow(() -> new ClassNotFoundException("the parameters of the team packet"));
				parameters = params.getDeclaredConstructor(team);
				parameters.setAccessible(true);

				modernPacket = teamPacket.getDeclaredConstructor(String.class, int.class, Optional.class, Collection.class);
				modernPacket.setAccessible(true);
			} else {
				legacyPacket = teamPacket.getDeclaredConstructor();
				legacyPacket.setAccessible(true);
				legacyFields = instanceFields(teamPacket);
				fields = legacyFields;
			}

			// The colour of the name: the enum field that has RED (not the visibility nor the collisions)
			for (Field f : fields) {
				if (f.getType().isEnum() && Arrays.stream(f.getType().getEnumConstants()).anyMatch(c -> ((Enum<?>) c).name().equals("RED"))) {
					colourClass = f.getType();
					break;
				}
			}
			// 26.x: Optional<TeamColor> in the team
			if (colourClass == null && teamFields != null && Arrays.stream(teamFields).anyMatch(f -> f.getType() == Optional.class)) {
				Optional<Class<?>> teamColour = FastReflection.nmsOptionalClass("world.scores", "TeamColor");
				if (teamColour.isPresent() && teamColour.get().isEnum()) {
					colourClass = teamColour.get();
					colourOptional = true;
				}
			}
			if (colourClass == null) {
				Bukkit.getLogger().warning("[Hawn] Ranks: the colour of the names can't be set on this server, the names above the heads keep their colour");
			}

			ready = true;
		} catch (Throwable e) {
			Bukkit.getLogger().warning("[Hawn] Ranks: the teams can't be sent on this server (" + e + "): no rank above the heads, and no order of the tab list before 1.21.2");
		}
		return ready;
	}

	private static Field[] instanceFields(Class<?> c) {
		List<Field> list = new ArrayList<>();
		for (Field f : c.getDeclaredFields()) {
			if (Modifier.isStatic(f.getModifiers())) continue;
			f.setAccessible(true);
			list.add(f);
		}
		return list.toArray(new Field[0]);
	}

	/**
	 * A team packet to one player. players: the names in the team (CREATE, ADD_PLAYERS, REMOVE_PLAYERS); look: for
	 * CREATE and UPDATE.
	 */
	static void send(Player viewer, String name, int mode, Collection<String> players, Look look) {
		if (!ready || !viewer.isOnline()) {
			return;
		}
		if (look == null) look = Look.NONE;

		try {
			Object packet;
			boolean withLook = mode == CREATE || mode == UPDATE;
			boolean withPlayers = mode == CREATE || mode == ADD_PLAYERS || mode == REMOVE_PLAYERS;

			if (modernPacket != null) {
				Optional<Object> params = Optional.empty();
				if (withLook) {
					// The default values of a team (names always visible, no collision change), then the look
					Object team = playerTeam.newInstance(null, name);
					setNth(teamFields, team, componentClass, 1, component(look.prefix));
					setNth(teamFields, team, componentClass, 2, component(look.suffix));
					if (look.colour != null && colourClass != null) {
						Object c = colour(look.colour);
						if (colourOptional) {
							setNth(teamFields, team, Optional.class, 0, c == null ? null : Optional.of(c));
						} else {
							setNth(teamFields, team, colourClass, 0, c);
						}
					}
					params = Optional.of(parameters.newInstance(team));
				}
				packet = modernPacket.newInstance(name, mode, params, withPlayers ? new ArrayList<>(players) : new ArrayList<String>());
			} else {
				packet = legacyPacket.newInstance();
				setNth(legacyFields, packet, String.class, 0, name);
				setNth(legacyFields, packet, int.class, 0, mode);
				if (withLook) {
					setNth(legacyFields, packet, componentClass, 1, component(look.prefix));
					setNth(legacyFields, packet, componentClass, 2, component(look.suffix));
					if (look.colour != null && colourClass != null) {
						setNth(legacyFields, packet, colourClass, 0, colour(look.colour));
					}
				}
				if (withPlayers) {
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
			// Never a crash for the ranks: off until the next restart
			ready = false;
			Bukkit.getLogger().warning("[Hawn] Ranks: a team packet could not be sent (" + e + "), the teams of the ranks are off until the next restart");
		}
	}

	private static Object component(String text) throws ReflectiveOperationException {
		Object[] parts = (Object[]) fromString.invoke(null, text == null ? "" : text);
		return parts.length == 0 ? fromString.invoke(null, "") : parts[0];
	}

	private static Object colour(String name) {
		for (Object c : colourClass.getEnumConstants()) {
			if (((Enum<?>) c).name().equalsIgnoreCase(name)) return c;
		}
		return null;
	}

	// The n-th field of this type (the fields keep the order of the source of Minecraft)
	private static void setNth(Field[] fields, Object target, Class<?> type, int n, Object value) throws IllegalAccessException {
		if (value == null) return;
		int i = 0;
		for (Field f : fields) {
			if (f.getType() == type && i++ == n) {
				f.set(target, value);
				return;
			}
		}
	}
}
