package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.modules.world.generator.Generators;
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
							Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(environment).generator(Generators.voidGenerator()));
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
