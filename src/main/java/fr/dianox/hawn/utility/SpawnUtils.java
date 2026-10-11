package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;

import java.util.HashSet;
import java.util.Set;

public class SpawnUtils {

	// The spawns already reported in the console: once each, not at every teleport (void TP, join...)
	private static final Set<String> reported = new HashSet<>();

	/**
	 * Teleports the player to the spawn. False when the spawn doesn't exist or its world is not loaded:
	 * nothing happens, the console says it once per spawn and the admins are told.
	 */
	public static boolean teleportToSpawn(Player player, String str) {
		return teleportToSpawn(player, str, true);
	}

	/**
	 * tellAdmin false: for the callers with their own message (the void TP, many times per second).
	 */
	public static boolean teleportToSpawn(Player player, String str, boolean tellAdmin) {
		String worldName = ConfigSpawn.getConfig().getString("Coordinated." + str + ".World");
		World w = worldName == null ? null : Bukkit.getWorld(worldName);

		if (w == null) {
			if (reported.add(str)) {
				Main.getInstance().getLogger().warning(worldName == null
						? "The spawn " + str + " is not set"
						: "The world " + worldName + " of the spawn " + str + " is not loaded: nobody can be teleported there");
			}

			if (tellAdmin && player.hasPermission("hawn.admin")) {
				player.sendMessage("Spawn is not set");
			}
			return false;
		}

		double x = ConfigSpawn.getConfig().getDouble("Coordinated."+str+".X");
		double y = ConfigSpawn.getConfig().getDouble("Coordinated."+str+".Y");
		double z = ConfigSpawn.getConfig().getDouble("Coordinated."+str+".Z");
		float yaw = (float) ConfigSpawn.getConfig().getDouble("Coordinated."+str+".Yaw");
		float pitch = (float) ConfigSpawn.getConfig().getDouble("Coordinated."+str+".Pitch");

		reported.remove(str);
		return player.teleport(new Location(w, x, y, z, yaw, pitch));
	}

	/**
	 * The spawns whose world doesn't exist at all (not loaded and no folder): for the warnings of the startup and of /hawn reload.
	 */
	public static Set<String> spawnsWithoutWorld() {
		Set<String> missing = new java.util.TreeSet<>();
		org.bukkit.configuration.ConfigurationSection spawns = ConfigSpawn.getConfig().getConfigurationSection("Coordinated");
		if (spawns == null) {
			return missing;
		}

		for (String spawn : spawns.getKeys(false)) {
			String world = spawns.getString(spawn + ".World");
			if (world != null && !fr.dianox.hawn.modules.world.WorldFolders.exists(world)) {
				missing.add(spawn + " (" + world + ")");
			}
		}
		return missing;
	}

	public static void createSpawn(Player p, String spawnName, String WorldName, Double X, Double Y, Double Z, Float Yaw, Float Pitch) {
		ConfigSpawn.getConfig().set("Coordinated."+spawnName+".World", WorldName);
		ConfigSpawn.getConfig().set("Coordinated."+spawnName+".X", X);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Y", Y);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Z", Z);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Yaw", Yaw);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Pitch", Pitch);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Info", "Player " + p.getName() + " created the spawn at: " + OtherUtils.getDate() + ", " + OtherUtils.getTime());

        ConfigSpawn.saveConfigFile();
	}

}
