package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

public class WorldManager {

	public WorldManager() {
		load();
	}

	/**
	 * Folia can neither create nor unload a world while the server runs (since 1.6): the World Manager is off there.
	 * True, with the message, when the sender can't use it.
	 */
	public static boolean offOnFolia(org.bukkit.command.CommandSender sender) {
		if (!fr.dianox.hawn.utility.Tasks.isFolia()) {
			return false;
		}
		if (sender instanceof org.bukkit.entity.Player) {
			for (String msg : WorldDeletion.message("Folia-Disabled", "%prefix% &cThe World Manager is off on Folia: Folia can neither create nor unload a world while the server runs")) {
				fr.dianox.hawn.utility.ConfigEventUtils.ExecuteEvent((org.bukkit.entity.Player) sender, msg, "", "", false);
			}
		} else if (sender != null) {
			sender.sendMessage("[Hawn] The World Manager is off on Folia: Folia can neither create nor unload a world while the server runs");
		}
		return true;
	}

	public void load() {
		if (fr.dianox.hawn.utility.Tasks.isFolia()) {
			// The worlds of Worlds.yml are not loaded: see offOnFolia
			return;
		}

		java.util.Set<String> names = new java.util.LinkedHashSet<>();

		// The world folders of the world container (--world-container)
		GuiSystem.fileList.clear();
		GuiSystem.getFileList(Bukkit.getWorldContainer());
		for (File directorfile : GuiSystem.fileList) {
			if (GuiSystem.checkIfIsWorld(directorfile)) {
				names.add(directorfile.getName());
			}
		}

		// Paper 26.x: the created worlds are in the folder of the main world (world/dimensions/minecraft/<name>)
		org.bukkit.configuration.ConfigurationSection list = ConfigWorldGeneral.getConfig().getConfigurationSection("World-List");
		if (list != null) {
			for (String worldname : list.getKeys(false)) {
				if (WorldFolders.dimension(worldname) != null) {
					names.add(worldname);
				}
			}
		}

		for (String worldname : names) {
			if (!ConfigWorldGeneral.getConfig().isSet("World-List." + worldname + ".Load")) {
				ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", Bukkit.getWorld(worldname) != null);
				ConfigWorldGeneral.saveConfigFile();
			}
		}

		for (String worldname : names) {
			if (ConfigWorldGeneral.getConfig().getBoolean("World-List." + worldname + ".Load")) {
				// The settings of Worlds.yml (environment, type, generator), the same code as the menu
				World world = WorldLoading.load(worldname);
				if (world != null) {
					WorldFolders.fixSpawn(world);
				}
			}
		}
	}
}
