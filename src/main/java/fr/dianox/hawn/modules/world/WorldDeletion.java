package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.messages.WorldManagerPanelConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * World deletion of the world manager (/hw delete and the menu).
 */
public class WorldDeletion {

	/**
	 * The main world, its nether and its end can't be deleted: the server needs them.
	 */
	public static boolean isProtected(String worldname) {
		String main = Bukkit.getWorlds().get(0).getName();
		return worldname.equalsIgnoreCase(main) || worldname.equalsIgnoreCase(main + "_nether") || worldname.equalsIgnoreCase(main + "_the_end");
	}

	/**
	 * Sends the players of the world to the main world, unloads it, then deletes its folder outside the main thread.
	 *
	 * @return false when the world could not be unloaded: nothing is deleted then
	 */
	public static boolean delete(World world) {
		World main = Bukkit.getWorlds().get(0);
		for (Player player : world.getPlayers()) {
			player.teleport(main.getSpawnLocation());
		}

		// Recent Paper versions: getWorldFolder() is a dimension folder, see WorldFolders
		File folder = WorldFolders.toDelete(world);
		File mainRoot = WorldFolders.root(main);

		// Never the folder of the main world, nor a folder that contains it
		if (mainRoot.getPath().equals(folder.getPath()) || mainRoot.getPath().startsWith(folder.getPath() + File.separator)) {
			return false;
		}

		if (!Bukkit.unloadWorld(world, true)) {
			return false;
		}

		deleteFolder(folder);
		return true;
	}

	/**
	 * Deletes a folder (an unloaded world) outside the main thread.
	 */
	public static void deleteFolder(File folder) {
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			if (!deleteDirectory(folder)) {
				Main.getInstance().getLogger().warning("Could not delete the whole folder " + folder.getPath());
			}
		});
	}

	private static boolean deleteDirectory(File path) {
		File[] files = path.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					deleteDirectory(file);
				} else {
					file.delete();
				}
			}
		}
		return path.delete();
	}

	/**
	 * A message of WorldManager.yml, with a default text for the files generated before it existed.
	 */
	public static List<String> message(String key, String... byDefault) {
		if (WorldManagerPanelConfig.getConfig().isSet(key)) {
			return WorldManagerPanelConfig.getConfig().getStringList(key);
		}
		return Arrays.asList(byDefault);
	}
}
