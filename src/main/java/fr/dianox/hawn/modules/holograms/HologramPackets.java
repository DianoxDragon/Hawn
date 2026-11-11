package fr.dianox.hawn.modules.holograms;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.util.Vector3f;
import com.github.retrooper.packetevents.util.adventure.AdventureSerializer;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The packets of the holograms, through PacketEvents (since 1.6): a text display (1.19.4+) or invisible armor stands,
 * one per line (before 1.19.4). The entities only exist for the players they are sent to. Loaded only when the plugin
 * PacketEvents is there.
 */
final class HologramPackets {

	private HologramPackets() {}

	private static ServerVersion version() {
		return PacketEvents.getAPI().getServerManager().getVersion();
	}

	/**
	 * Text displays since 1.19.4, armor stands before.
	 */
	static boolean textDisplay() {
		return version().isNewerThanOrEquals(ServerVersion.V_1_19_4);
	}

	private static void send(Player p, PacketWrapper<?> packet) {
		PacketEvents.getAPI().getPlayerManager().sendPacket(p, packet);
	}

	/* ------------------------------------------------------------------ text display (1.19.4+) */

	static void spawnDisplay(Player p, int id, Hologram h, String text) {
		send(p, new WrapperPlayServerSpawnEntity(id, Optional.of(UUID.randomUUID()), EntityTypes.TEXT_DISPLAY,
				new Vector3d(h.x, h.y, h.z), 0f, 0f, 0f, 0, Optional.empty()));
		send(p, new WrapperPlayServerEntityMetadata(id, displayData(h, text, true)));
	}

	static void updateDisplay(Player p, int id, Hologram h, String text) {
		send(p, new WrapperPlayServerEntityMetadata(id, displayData(h, text, false)));
	}

	// The indexes of the display entities: 1.20.2 added one (the interpolation of the position) before the billboard
	private static List<EntityData<?>> displayData(Hologram h, String text, boolean all) {
		int o = version().isNewerThanOrEquals(ServerVersion.V_1_20_2) ? 0 : -1;
		List<EntityData<?>> data = new ArrayList<>();
		if (all) {
			if (h.scale != 1.0f) {
				data.add(new EntityData<>(12 + o, EntityDataTypes.VECTOR3F, new Vector3f(h.scale, h.scale, h.scale)));
			}
			data.add(new EntityData<>(15 + o, EntityDataTypes.BYTE, (byte) h.billboard.ordinal()));
			data.add(new EntityData<>(24 + o, EntityDataTypes.INT, h.lineWidth));
			if (h.background != null) {
				data.add(new EntityData<>(25 + o, EntityDataTypes.INT, h.background));
			}
			byte flags = 0;
			if (h.shadow) flags |= 0x01;
			if (h.background == null) flags |= 0x04;
			data.add(new EntityData<>(27 + o, EntityDataTypes.BYTE, flags));
		}
		data.add(new EntityData<>(23 + o, EntityDataTypes.ADV_COMPONENT, AdventureSerializer.fromLegacyFormat(text)));
		return data;
	}

	/* ------------------------------------------------------------------ armor stands (before 1.19.4) */

	static void spawnStand(Player p, int id, double x, double y, double z, String line) {
		Vector3d at = new Vector3d(x, y, z);
		if (version().isOlderThan(ServerVersion.V_1_19)) {
			send(p, new WrapperPlayServerSpawnLivingEntity(id, UUID.randomUUID(), EntityTypes.ARMOR_STAND, at, 0f, 0f, 0f,
					new Vector3d(0, 0, 0), new ArrayList<>()));
		} else {
			send(p, new WrapperPlayServerSpawnEntity(id, Optional.of(UUID.randomUUID()), EntityTypes.ARMOR_STAND, at, 0f, 0f, 0f, 0, Optional.empty()));
		}
		send(p, new WrapperPlayServerEntityMetadata(id, standData(line, true)));
	}

	static void updateStand(Player p, int id, String line) {
		send(p, new WrapperPlayServerEntityMetadata(id, standData(line, false)));
	}

	// Invisible, a custom name always shown, no gravity, a marker (no hitbox). 1.17 added one index to every entity
	private static List<EntityData<?>> standData(String line, boolean all) {
		List<EntityData<?>> data = new ArrayList<>();
		boolean empty = line.isEmpty();
		if (all) {
			data.add(new EntityData<>(0, EntityDataTypes.BYTE, (byte) 0x20));
			data.add(new EntityData<>(5, EntityDataTypes.BOOLEAN, true));
			int flags = version().isNewerThanOrEquals(ServerVersion.V_1_17) ? 15 : 14;
			data.add(new EntityData<>(flags, EntityDataTypes.BYTE, (byte) 0x10));
		}
		data.add(new EntityData<>(2, EntityDataTypes.OPTIONAL_ADV_COMPONENT,
				empty ? Optional.empty() : Optional.of(AdventureSerializer.fromLegacyFormat(line))));
		data.add(new EntityData<>(3, EntityDataTypes.BOOLEAN, !empty));
		return data;
	}

	static void destroy(Player p, int... ids) {
		if (ids.length > 0) {
			send(p, new WrapperPlayServerDestroyEntities(ids));
		}
	}
}
