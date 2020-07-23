package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.modules.world.generator.VoidGenerator;
import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.io.File;

public class WorldManager {

	public WorldManager() {
		load();
	}

	public void load() {
		GuiSystem.fileList.clear();

		String pathname = new File(".").getAbsolutePath();
		File directory = new File(pathname);
		GuiSystem.getFileList(directory);

		for (File directorfile : GuiSystem.fileList) {
			if (GuiSystem.checkIfIsWorld(directorfile)) {
				String worldname = directorfile.getName();

				if (!ConfigWorldGeneral.getConfig().isSet("World-List." + worldname + ".Load")) {
					ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", Bukkit.getWorld(worldname) != null);
					ConfigWorldGeneral.saveConfigFile();
				}
			}
		}

		for (File directorfile : GuiSystem.fileList) {
			if (GuiSystem.checkIfIsWorld(directorfile)) {
				String worldname = directorfile.getName();

				if (ConfigWorldGeneral.getConfig().getBoolean("World-List." + worldname + ".Load")) {
					if (Bukkit.getWorld(worldname) == null) {
						if (ConfigWorldGeneral.getConfig().isSet("World-List." + worldname + ".Generator")) {
							String worldgenerator = ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Generator");
							World.Environment environment = World.Environment.NORMAL;

							if (ConfigWorldGeneral.getConfig().isSet("World-List." + worldname + ".Environment")) {
								if (ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Environment").equalsIgnoreCase("the_end")) {
									environment = World.Environment.THE_END;
								} else if (ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Environment").equalsIgnoreCase("nether")) {
									environment = World.Environment.NETHER;
								}
							}

							if (worldgenerator.equalsIgnoreCase("hvg")) {
								Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(environment).generator(new VoidGenerator()));
							} else {
								Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(environment).generator(worldgenerator));
							}
						} else {
							World.Environment environment = World.Environment.NORMAL;
							if (ConfigWorldGeneral.getConfig().isSet("World-List." + worldname + ".Environment")) {
								if (ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Environment").equalsIgnoreCase("the_end")) {
									environment = World.Environment.THE_END;
								} else if (ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Environment").equalsIgnoreCase("nether")) {
									environment = World.Environment.NETHER;
								}
							}

							WorldType type = WorldType.NORMAL;
							if (ConfigWorldGeneral.getConfig().isSet("World-List." + worldname + ".Type")) {
								if (ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Type").equalsIgnoreCase("flat")) {
									type = WorldType.FLAT;
								} else if (ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Type").equalsIgnoreCase("amplified")) {
									type = WorldType.AMPLIFIED;
								} else if (ConfigWorldGeneral.getConfig().getString("World-List." + worldname + ".Type").equalsIgnoreCase("large_biomes")) {
									type = WorldType.LARGE_BIOMES;
								}
							}

							Bukkit.getServer().createWorld((new WorldCreator(worldname).environment(environment).type(type)));
						}
					}
				}
			}
		}
	}
}
