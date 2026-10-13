package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.modules.world.DefaultWorld;
import fr.dianox.hawn.command.commands.tab.Tab;

import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.modules.world.WorldDeletion;
import fr.dianox.hawn.modules.world.WorldFolders;
import fr.dianox.hawn.modules.world.generator.Generators;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.configs.commands.WorldCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.configs.messages.WorldManagerPanelConfig;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorldCommand extends BukkitCommand {

	public static List<File> fileList = new ArrayList<>();
    String GeneralPermission = "hawn.command.world.general";

    public WorldCommand(String name) {
        super(name);
        Tab.hideWithoutPermission(this, GeneralPermission);
        this.description = "Manage world system";
        this.usageMessage = "/hw <argument> <argument two> etc.";
    }

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (!sender.hasPermission(GeneralPermission)) {
			return Tab.none();
		}

		if (args.length == 1) {
			return Tab.of(args, "list", "info", "tp", "delete", "create", "import", "unload", "default");
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")
					|| args[0].equalsIgnoreCase("unload") || args[0].equalsIgnoreCase("default")) {
				List<String> worlds = new ArrayList<>();
				for (World world : Bukkit.getWorlds()) {
					worlds.add(world.getName());
				}
				return Tab.of(args, worlds);
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("import")) {
				return Tab.of(args, "normal", "the_end", "nether");
			}
		} else if (args.length == 4) {
			if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("import")) {
				return Tab.of(args, "flat", "amplified", "large_biomes", "g:hvg");
			}
		}

		return Tab.none();
	}

	@Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
            if (args.length >= 1 && args[0].equalsIgnoreCase("default")) {
                setDefault(sender, args);
            }
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!WorldCommandConfig.getConfig().getBoolean("World.Enable")) {
            if (WorldCommandConfig.getConfig().getBoolean("World.Disable-Message")) {
                if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
                }
            }

            return true;
        }

        if (!p.hasPermission(GeneralPermission)) {
            MessageUtils.MessageNoPermission(p, GeneralPermission);
            return true;
        }

        // The command

        if (args.length == 0) {
        	GuiSystem.FirstPage(p);
        } else {
        	if (args[0].equalsIgnoreCase("list")) {
        		if (!p.hasPermission("hawn.command.world.list") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.list");

					return true;
				}

        		List<World> worldList = Bukkit.getServer().getWorlds();
        		for (int i = 0; i < Bukkit.getServer().getWorlds().size(); i++) {
        			String name = worldList.get(i).getName();
        			String type = worldList.get(i).getEnvironment().toString();
        			if (type.equalsIgnoreCase("NORMAL")) {
            			type = type.replace("NORMAL", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replace("&", "§"));
            		} else if (type.equalsIgnoreCase("NETHER")) {
            			type = type.replace("NETHER", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replace("&", "§"));
            		} else if (type.equalsIgnoreCase("THE_END")) {
            			type = type.replace("THE_END", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replace("&", "§"));
            		}
        			p.sendMessage("§8- §f" + name + "§8 || §e" + type);
        		}
        	} else if (args[0].equalsIgnoreCase("info")) {
        		if (!p.hasPermission("hawn.command.world.info") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.info");

					return true;
				}

        		p.sendMessage("§b" + WorldManagerPanelConfig.getConfig().getString("Command.Other.World").replace("&", "§") + " §7" + p.getWorld().getName());
        		String type = Bukkit.getWorld(p.getWorld().getName()).getEnvironment().toString();
        		if (type.equalsIgnoreCase("NORMAL")) {
        			type = type.replace("NORMAL", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replace("&", "§"));
        		} else if (type.equalsIgnoreCase("NETHER")) {
        			type = type.replace("NETHER", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replace("&", "§"));
        		} else if (type.equalsIgnoreCase("THE_END")) {
        			type = type.replace("THE_END", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replace("&", "§"));
        		}
        		p.sendMessage("§7- §e" + WorldManagerPanelConfig.getConfig().getString("Command.Other.Type").replace("&", "§") + " " + type);
        	} else if (args[0].equalsIgnoreCase("tp")) {
        		if (!p.hasPermission("hawn.command.world.tp") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.tp");

					return true;
				}

        		String worldname = args[1];

                List<String> stringList = new ArrayList<>();
                List<World> worldList = Bukkit.getServer().getWorlds();

                for (int i = 0; i < Bukkit.getServer().getWorlds().size(); i++) {
                  String name = worldList.get(i).getName();
                  stringList.add(name);
                }

                if (stringList.contains(worldname)) {
	                if (p.getWorld().getName().equals(worldname)) {
		                for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Error-Tp")) {
			                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
		                }
	                } else {
		                World world = Bukkit.getWorld(worldname);
		                Location location = world.getSpawnLocation();
		                p.teleport(location);

		                for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Success")) {
			                ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
		                }
	                }
                }
        	} else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")) {
        		if (!p.hasPermission("hawn.command.world.delete") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.delete");

					return true;
				}

        		String worldname;

        		if (args.length > 1) {
        			worldname = args[1];
        		} else {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldNameNotTyped")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        			return true;
        		}

        		if (Bukkit.getWorld(worldname) != null) {
        			World world = Bukkit.getServer().getWorld(worldname);

        			if (WorldDeletion.isProtected(world.getName())) {
        				for (String msg: WorldDeletion.message("Error.Protected-World", "%prefix% &cThe world &e%arg1% &ccan't be deleted: the server needs it")) {
        					ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        				}
        				return true;
        			}

        			if (DefaultWorld.is(world.getName())) {
        				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.Default-World")) {
        					ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        				}
        				return true;
        			}

        			// No undo: the command is typed a second time with "confirm"
        			if (args.length < 3 || !args[2].equalsIgnoreCase("confirm")) {
        				for (String msg: WorldDeletion.message("Gui.Delete.Confirm-Command", "%prefix% &cThis deletes the world &e%arg1% &cand its folder, with no undo. Type &e/hw delete %arg1% confirm &cto do it")) {
        					ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        				}
        				return true;
        			}

        			if (!WorldDeletion.delete(world)) {
        				for (String msg: WorldDeletion.message("Error.Unload-Failed", "%prefix% &cThe world &e%arg1% &ccould not be unloaded: nothing was deleted")) {
        					ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        				}
        				return true;
        			}

        			ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", null);
        			ConfigWorldGeneral.getConfig().set("World-List." + worldname, null);
	        		ConfigWorldGeneral.saveConfigFile();

        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.World-Deleted")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
	                }
        		} else {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Not-Exist")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        		}
        	} else if (args[0].equalsIgnoreCase("default")) {
        		if (!p.hasPermission("hawn.command.world.setdefault") && !p.hasPermission("hawn.command.world.*")) {
        			MessageUtils.MessageNoPermission(p, "hawn.command.world.setdefault");
        			return true;
        		}

        		setDefault(p, args);
        	} else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("add")) {
        		if (!p.hasPermission("hawn.command.world.create") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.create");

					return true;
				}

        		if (args.length < 2) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldNameNotTyped")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        			return true;
        		}

        		String worldname = args[1];

        		// Letters, numbers, _ and -: nothing that could leave the folder of the worlds
        		if (!WorldFolders.validName(worldname)) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
					return true;
				}

        		// Loaded, in the folder of the worlds, or a dimension of the main world (Paper 26.x)
        		if (WorldFolders.exists(worldname)) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Already-Exist")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        			}
        			return true;
        		}

        		// The environment is needed to create a world
        		if (args.length < 3) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
                    }
        			return true;
        		}

        		createOrImport(p, worldname, args, false);
        	} else if (args[0].equalsIgnoreCase("import")) {
        		if (!p.hasPermission("hawn.command.world.import") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.import");

					return true;
				}

        		if (args.length < 2) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldNameNotTyped")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        			return true;
        		}

        		String worldname = args[1];

        		if (!WorldFolders.validImportName(worldname)) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
					return true;
				}

		        if (Bukkit.getWorld(worldname) != null) {
			        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Already-Exist")) {
				        ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
			        }
			        return true;
		        }

		        boolean check = checkIfIsWorld(new File(Bukkit.getWorldContainer(), worldname)) || WorldFolders.dimension(worldname) != null;

				if (!check) {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Not-Exist")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
					}

					return true;
				}

		        createOrImport(p, worldname, args, true);
        	} else if (args[0].equalsIgnoreCase("unload")) {
        		if (!p.hasPermission("hawn.command.world.unload") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.unload");

					return true;
				}

        		if (args.length < 2) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldNameNotTyped")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        			return true;
        		}

        		String worldname = args[1];
        		World world = Bukkit.getWorld(worldname);

        		if (world == null) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Command.Unload.Error")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        		} else if (WorldDeletion.isProtected(world.getName())) {
        			// The main world, its nether and its end: the same refusals as /hw delete
        			for (String msg: WorldDeletion.message("Error.Unload-Protected", "%prefix% &cThe world &e%arg1% &ccan't be unloaded: the server needs it")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        			}
        		} else if (DefaultWorld.is(world.getName())) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.Default-World")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        			}
        		} else if (!Bukkit.getServer().unloadWorld(world, true)) {
        			// Bukkit refuses while players are in the world
        			for (String msg: WorldDeletion.message("Command.Unload.Failed", "%prefix% &cThe world &e%arg1% &ccould not be unloaded: no player must be in it")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
        			}
        		} else {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Unload")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
                    }
        		}
        	}
        }

        return true;
    }
    
    
    /**
     * /hw create and /hw import: &lt;world&gt; [normal/nether/end] [flat/amplified/large_biomes/g:&lt;generator&gt;].
     * A wrong argument stops everything: no world, nothing written in Worlds.yml. A world created or imported is
     * loaded again at each start (Load: true).
     */
    private static void createOrImport(Player p, String worldname, String[] args, boolean importing) {
    	Environment environment = Environment.NORMAL;
    	if (args.length >= 3) {
    		environment = environment(args[2]);
    	}

    	WorldCreator creator = environment == null ? null : new WorldCreator(worldname).environment(environment);
    	String type = null;
    	String generator = null;

    	if (creator != null && args.length >= 4) {
    		if (args[3].toLowerCase(Locale.ROOT).startsWith("g:") && args[3].length() > 2) {
    			generator = args[3].substring(2);
    			if (generator.equalsIgnoreCase("hvg")) {
    				creator.generator(Generators.voidGenerator());
    			} else {
    				creator.generator(generator);
    			}
    		} else {
    			type = args[3].toLowerCase(Locale.ROOT).replace("largebiomes", "large_biomes");
    			WorldType worldType = worldType(type);
    			if (worldType == null) {
    				creator = null;
    			} else {
    				creator.type(worldType);
    			}
    		}
    	}

    	if (creator == null) {
    		for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
    			ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
    		}
    		return;
    	}

    	for (String msg: WorldManagerPanelConfig.getConfig().getStringList(importing ? "Gui.Import.Importing-A-World" : "Gui.Create.Creating-The-World")) {
    		ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
    	}

    	World world;
    	try {
    		world = Bukkit.getServer().createWorld(creator);
    	} catch (Exception e) {
    		Bukkit.getLogger().warning("[Hawn] The world " + worldname + " could not be " + (importing ? "imported" : "created") + ": " + e);
    		world = null;
    	}

    	if (world == null) {
    		for (String msg: WorldDeletion.message("Gui.Create.Failed", "%prefix% &cThe world &e%arg1% &ccould not be created, see the console")) {
    			ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
    		}
    		return;
    	}

    	String path = "World-List." + worldname;
    	ConfigWorldGeneral.getConfig().set(path + ".Load", true);
    	ConfigWorldGeneral.getConfig().set(path + ".Environment", world.getEnvironment() == Environment.NETHER ? "nether"
    			: world.getEnvironment() == Environment.THE_END ? "the_end" : "normal");
    	ConfigWorldGeneral.getConfig().set(path + ".Type", generator == null ? type : null);
    	ConfigWorldGeneral.getConfig().set(path + ".Generator", generator);
    	ConfigWorldGeneral.saveConfigFile();

    	for (String msg: WorldManagerPanelConfig.getConfig().getStringList(importing ? "Gui.Import.World-Loaded" : "Gui.Create.World-Created")) {
    		ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)), "", "", false);
    	}
    }

    // normal, nether, end or the_end; null for another word
    private static Environment environment(String name) {
    	switch (name.toLowerCase(Locale.ROOT)) {
    		case "normal": return Environment.NORMAL;
    		case "nether": return Environment.NETHER;
    		case "end":
    		case "the_end": return Environment.THE_END;
    		default: return null;
    	}
    }

    // flat, amplified, large_biomes or normal; null for another word
    private static WorldType worldType(String name) {
    	switch (name) {
    		case "normal": return WorldType.NORMAL;
    		case "flat": return WorldType.FLAT;
    		case "amplified": return WorldType.AMPLIFIED;
    		case "large_biomes": return WorldType.LARGE_BIOMES;
    		default: return null;
    	}
    }

    // /hw default: shows the default world, or makes a loaded world the default one
    private static void setDefault(CommandSender sender, String[] args) {
    	if (args.length < 2) {
    		DefaultWorld.say(sender, "Command.Default-World.Current", DefaultWorld.get());
    		return;
    	}

    	World world = null;
    	for (World w : Bukkit.getWorlds()) {
    		if (w.getName().equalsIgnoreCase(args[1])) world = w;
    	}

    	if (world == null) {
    		DefaultWorld.say(sender, "Error.World-Not-Exist", args[1]);
    	} else if (DefaultWorld.is(world.getName())) {
    		DefaultWorld.say(sender, "Gui.Default-World.Already", world.getName());
    	} else {
    		DefaultWorld.apply(sender, world.getName());
    	}
    }

    public static boolean checkIfIsWorld(File worldFolder) {
		if (worldFolder.isDirectory()) {
			File[] files = worldFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".dat"));
			return files != null && files.length > 0;
		}
		return false;
	}
    
    public static void getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                	if (file.getAbsolutePath().contains("\\.\\cache\\")) continue;
                	if (file.getAbsolutePath().contains("\\.\\dumps\\")) continue;
                	
                	fileList.add(file);
                } else {
                    getFileList(file);
                }
            }
        }
    }
}