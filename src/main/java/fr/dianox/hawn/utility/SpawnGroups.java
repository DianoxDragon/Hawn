package fr.dianox.hawn.utility;

import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Spawn groups (Events/OnJoin.yml → Spawn.Spawn-Group): the players are spread between several spawns instead of
 * all going to Spawn.DefaultSpawn. The spawn given to a player is kept while they are online, so /spawn takes them
 * back to the same one.
 */
public class SpawnGroups implements Listener {

	private static final String PATH = "Spawn.Spawn-Group.";

	// Player → spawn given, while they are online
	private static final Map<UUID, String> assigned = new HashMap<>();
	private static int next = 0;

	/**
	 * The default spawn of this player: a spawn of the group when it is on, Spawn.DefaultSpawn otherwise
	 */
	public static String defaultSpawn(Player p) {
		String fallback = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");

		if (p == null || !OnJoinConfig.getConfig().getBoolean(PATH + "Enable")) {
			return fallback;
		}

		// The spawns of the group that exist and that the player can go to (hawn.command.spawn.<spawn>, also needed by /spawn)
		List<String> existing = new ArrayList<>();
		List<String> candidates = new ArrayList<>();
		for (String spawn : OnJoinConfig.getConfig().getStringList(PATH + "Spawns")) {
			if (ConfigSpawn.getConfig().isSet("Coordinated." + spawn)) {
				existing.add(spawn);
				if (p.hasPermission("hawn.command.spawn." + spawn)) {
					candidates.add(spawn);
				}
			}
		}

		// No permission for any of them: when the join doesn't need the permission (Event.OnJoin.Spawn-Permission off),
		// the player is still spread between all the spawns of the group
		if (candidates.isEmpty() && !OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Spawn-Permission.Enable")) {
			candidates = existing;
		}

		if (candidates.isEmpty()) {
			return fallback;
		}

		String already = assigned.get(p.getUniqueId());
		if (already != null && candidates.contains(already)) {
			return already;
		}

		String chosen;
		switch (OnJoinConfig.getConfig().getString(PATH + "Mode", "LEAST_PLAYERS").toUpperCase(Locale.ROOT)) {
			case "RANDOM":
				chosen = candidates.get(ThreadLocalRandom.current().nextInt(candidates.size()));
				break;
			case "ROUND_ROBIN":
				chosen = candidates.get(Math.floorMod(next++, candidates.size()));
				break;
			default:
				// LEAST_PLAYERS: the spawn with the fewest online players given to it, the first of the list on a tie
				chosen = candidates.get(0);
				int fewest = Integer.MAX_VALUE;
				for (String spawn : candidates) {
					int count = 0;
					for (String s : assigned.values()) {
						if (s.equals(spawn)) {
							count++;
						}
					}
					if (count < fewest) {
						fewest = count;
						chosen = spawn;
					}
				}
				break;
		}

		assigned.put(p.getUniqueId(), chosen);
		return chosen;
	}

	public static Map<UUID, String> getAssigned() {
		return assigned;
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		assigned.remove(e.getPlayer().getUniqueId());
	}
}
