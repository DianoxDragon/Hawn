package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;

import java.io.File;

public class WorldManager {

	public WorldManager() {
		load();
	}

	public void load() {
		GuiSystem.fileList.clear();

		if (!ConfigWorldGeneral.getConfig().isSet("World.CheckConfig")) {
			String pathname = new File(".").getAbsolutePath();
			File directory = new File(pathname);
			GuiSystem.getFileList(directory);

			for (File directorfile : GuiSystem.fileList) {
				if (GuiSystem.checkIfIsWorld(directorfile)) {
					String worldname = directorfile.getName();

					ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", Bukkit.getWorld(worldname) != null);
					ConfigWorldGeneral.saveConfigFile();
				}
			}

			ConfigWorldGeneral.getConfig().set("World.CheckConfig", true);
			ConfigWorldGeneral.saveConfigFile();

			for (File directorfile : GuiSystem.fileList) {
				if (GuiSystem.checkIfIsWorld(directorfile)) {
					String worldname = directorfile.getName();

					if (ConfigWorldGeneral.getConfig().getBoolean("World-List." + worldname + ".Load")) {
						if (Bukkit.getWorld(worldname) == null) {
							Bukkit.getServer().createWorld((new WorldCreator(worldname)));
						}
					}
				}
			}
		}
	}
}
