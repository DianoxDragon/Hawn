package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.modules.world.generator.VoidGenerator;
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

public class WorldCommand extends BukkitCommand {

	public static List<File> fileList = new ArrayList<>();
    String GeneralPermission = "hawn.command.world.general";

    public WorldCommand(String name) {
        super(name);
        this.description = "Manage world system";
        this.usageMessage = "/hw <argument> <argument two> etc.";
    }

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

    	if (args.length == 1) {
    		List<String> tab =  new ArrayList<>();
    		tab.add("list");
		    tab.add("info");
		    tab.add("tp");
		    tab.add("delete");
		    tab.add("create");
		    tab.add("import");
		    tab.add("unload");
		    return tab;
	    } if (args.length == 2) {
    		if (args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")
				    || args[0].equalsIgnoreCase("unload")) {
			    List<String> tab =  new ArrayList<>();
			    List<World> worldList = Bukkit.getServer().getWorlds();
			    for (int i = 0; i < Bukkit.getServer().getWorlds().size(); i++) {
				    String name = worldList.get(i).getName();
				    tab.add(name);
			    }
			    return tab;
		    }
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("import")) {
				List<String> tab =  new ArrayList<>();
				tab.add("normal");
				tab.add("the_end");
				tab.add("nether");
				return tab;
			}
		} else if (args.length == 4) {
    		if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("import")) {
    			List<String> tab =  new ArrayList<>();
    			tab.add("flat");
    			tab.add("amplified");
    			tab.add("large_biomes");
			    tab.add("g:hvg");
    			return tab;
    		}
		}

		return null;
	}

	@Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
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
            			type = type.replace("NORMAL", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replaceAll("&", "§"));
            		} else if (type.equalsIgnoreCase("NETHER")) {
            			type = type.replace("NETHER", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replaceAll("&", "§"));
            		} else if (type.equalsIgnoreCase("THE_END")) {
            			type = type.replace("THE_END", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replaceAll("&", "§"));
            		}
        			p.sendMessage("§8- §f" + name + "§8 || §e" + type);
        		}
        	} else if (args[0].equalsIgnoreCase("info")) {
        		if (!p.hasPermission("hawn.command.world.info") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.info");

					return true;
				}

        		p.sendMessage("§b" + WorldManagerPanelConfig.getConfig().getString("Command.Other.World").replaceAll("&", "§") + " §7" + p.getWorld().getName());
        		String type = Bukkit.getWorld(p.getWorld().getName()).getEnvironment().toString();
        		if (type.equalsIgnoreCase("NORMAL")) {
        			type = type.replace("NORMAL", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replaceAll("&", "§"));
        		} else if (type.equalsIgnoreCase("NETHER")) {
        			type = type.replace("NETHER", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replaceAll("&", "§"));
        		} else if (type.equalsIgnoreCase("THE_END")) {
        			type = type.replace("THE_END", WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replaceAll("&", "§"));
        		}
        		p.sendMessage("§7- §e" + WorldManagerPanelConfig.getConfig().getString("Command.Other.Type").replaceAll("&", "§") + " " + type);
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
			                ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
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
        			File folder = new File(Bukkit.getServer().getWorld(worldname).getWorldFolder().getPath());
        			World world = Bukkit.getServer().getWorld(worldname);
        			if (!world.getPlayers().isEmpty()) {
        				List<Player> list = world.getPlayers();
				        for (Player plist : list) {
					        List<World> tpList = Bukkit.getServer().getWorlds();
					        World spawn = tpList.get(0);
					        plist.teleport(spawn.getSpawnLocation());
				        }
        			}

        			ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", null);
        			ConfigWorldGeneral.getConfig().set("World-List." + worldname, null);
	        		ConfigWorldGeneral.saveConfigFile();

        			Bukkit.getServer().unloadWorld(worldname, true);
        			deleteDirectory(folder);

        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.World-Deleted")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
	                }
        		} else {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Not-Exist")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        		}
        	} else if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("add")) {
        		if (!p.hasPermission("hawn.command.world.create") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.create");

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

        		if (worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
					return true;
				}

        		fileList.clear();

        		String pathname = new File(".").getAbsolutePath();
        		File directory = new File(pathname);
        		getFileList(directory);

				for (File directorfile : fileList) {
					if (checkIfIsWorld(directorfile)) {
						String worldnamecheck = directorfile.getName();

						if (worldnamecheck.equals(worldname)) {

							for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Already-Exist")) {
		                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
		                    }

							return false;
						}
					}
        		}

        		if (args.length >= 3) {
	        		if (args[2].equalsIgnoreCase("normal")) {
	        			if (args.length >= 4) {
					        if (args[3].startsWith("g:")) {
					        	String worldgenerator = args[3];
					        	worldgenerator = worldgenerator.replace("g:", "");

					        	if (worldgenerator.equalsIgnoreCase("hvg")) {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).generator(new VoidGenerator()));
						        } else {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).generator(worldgenerator));
						        }

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Generator", worldgenerator);
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("flat")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.FLAT));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "flat");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else if (args[3].equalsIgnoreCase("amplified")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.AMPLIFIED));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "amplified");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.LARGE_BIOMES));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "large_biomes");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else {
		        				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
			                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
			                    }
		        			}
	        			} else {
	        				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
		                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
		                    }

	        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL));
	        			}

				        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "normal");
				        ConfigWorldGeneral.saveConfigFile();

	        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
	                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
	                    }
	        		} else if (args[2].equalsIgnoreCase("end") || args[2].equalsIgnoreCase("the_end")) {
	        			if (args.length >= 4) {
					        if (args[3].startsWith("g:")) {
						        String worldgenerator = args[3];
						        worldgenerator = worldgenerator.replace("g:", "");

						        if (worldgenerator.equalsIgnoreCase("hvg")) {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).generator(new VoidGenerator()));
						        } else {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).generator(worldgenerator));
						        }

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Generator", worldgenerator);
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("flat")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.FLAT));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "flat");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else if (args[3].equalsIgnoreCase("amplified")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.AMPLIFIED));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "amplified");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.LARGE_BIOMES));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "large_biomes");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else {
		        				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
			                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
			                    }
		        			}
	        			} else {
	        				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
		                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
		                    }

	        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END));
	        			}

				        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "the_end");
				        ConfigWorldGeneral.saveConfigFile();

	        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
	                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
	                    }
	        		} else if (args[2].equalsIgnoreCase("nether")) {
	        			if (args.length >= 4) {
					        if (args[3].startsWith("g:")) {
						        String worldgenerator = args[3];
						        worldgenerator = worldgenerator.replace("g:", "");

						        if (worldgenerator.equalsIgnoreCase("hvg")) {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).generator(new VoidGenerator()));
						        } else {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).generator(worldgenerator));
						        }

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Generator", worldgenerator);
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("flat")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.FLAT));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "flat");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else if (args[3].equalsIgnoreCase("amplified")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.AMPLIFIED));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "amplified");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.LARGE_BIOMES));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "large_biomes");
						        ConfigWorldGeneral.saveConfigFile();
		        			} else {
		        				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
			                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
			                    }
		        			}
	        			} else {
	        				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
	        					ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
		                    }

	        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER));
	        			}

				        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "nether");
				        ConfigWorldGeneral.saveConfigFile();

	        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
	        				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
	                    }
	        		}

	        		ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", true);
	        		ConfigWorldGeneral.saveConfigFile();
        		} else {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
                    }
        		}
        	} else if (args[0].equalsIgnoreCase("import")) {
        		if (!p.hasPermission("hawn.command.world.import") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.import");

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

        		if (worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
					return true;
				}

        		boolean check = false;

		        fileList.clear();

		        String pathname = new File(".").getAbsolutePath();
		        File directory = new File(pathname);
		        getFileList(directory);

		        for (File directorfile : fileList) {
			        if (checkIfIsWorld(directorfile)) {
				        String worldnamecheck = directorfile.getName();

				        if (worldnamecheck.equals(worldname) && Bukkit.getWorld(worldname) == null) {
				        	check = true;
				        } else if (worldnamecheck.equals(worldname)) {

					        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Already-Exist")) {
						        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
					        }

					        return false;
				        }
			        }
		        }

				if (!check) {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.World-Not-Exist")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
					}

					return false;
				}

		        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.Importing-A-World")) {
			        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
		        }

		        if (args.length >= 3) {
			        if (args[2].equalsIgnoreCase("normal")) {
				        if (args.length >= 4) {
					        if (args[3].startsWith("g:")) {
						        String worldgenerator = args[3];
						        worldgenerator = worldgenerator.replace("g:", "");

						        if (worldgenerator.equalsIgnoreCase("hvg")) {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).generator(new VoidGenerator()));
						        } else {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).generator(worldgenerator));
						        }

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Generator", worldgenerator);
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("flat")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.FLAT));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "flat");
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("amplified")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.AMPLIFIED));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "amplified");
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.LARGE_BIOMES));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "large_biomes");
						        ConfigWorldGeneral.saveConfigFile();
					        } else {
						        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
							        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
						        }
					        }
				        } else {
					        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
						        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
					        }

					        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL));
				        }

				        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "normal");
				        ConfigWorldGeneral.saveConfigFile();

				        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded")) {
					        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
				        }
			        } else if (args[2].equalsIgnoreCase("end") || args[2].equalsIgnoreCase("the_end")) {
				        if (args.length >= 4) {
					        if (args[3].startsWith("g:")) {
						        String worldgenerator = args[3];
						        worldgenerator = worldgenerator.replace("g:", "");

						        if (worldgenerator.equalsIgnoreCase("hvg")) {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).generator(new VoidGenerator()));
						        } else {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).generator(worldgenerator));
						        }

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Generator", worldgenerator);
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("flat")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.FLAT));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "flat");
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("amplified")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.AMPLIFIED));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "amplified");
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.LARGE_BIOMES));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "large_biomes");
						        ConfigWorldGeneral.saveConfigFile();
					        } else {
						        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
							        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
						        }
					        }
				        } else {
					        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
						        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
					        }

					        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END));
				        }

				        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "the_end");
				        ConfigWorldGeneral.saveConfigFile();

				        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded")) {
					        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
				        }
			        } else if (args[2].equalsIgnoreCase("nether")) {
				        if (args.length >= 4) {
					        if (args[3].startsWith("g:")) {
						        String worldgenerator = args[3];
						        worldgenerator = worldgenerator.replace("g:", "");

						        if (worldgenerator.equalsIgnoreCase("hvg")) {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).generator(new VoidGenerator()));
						        } else {
							        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).generator(worldgenerator));
						        }

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Generator", worldgenerator);
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("flat")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.FLAT));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "flat");
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("amplified")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.AMPLIFIED));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "amplified");
						        ConfigWorldGeneral.saveConfigFile();
					        } else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
						        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.LARGE_BIOMES));

						        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "large_biomes");
						        ConfigWorldGeneral.saveConfigFile();
					        } else {
						        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.WorldCreation")) {
							        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
						        }
					        }
				        } else {
					        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
						        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
					        }

					        Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER));
				        }

				        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "nether");
				        ConfigWorldGeneral.saveConfigFile();

				        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded")) {
					        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
				        }
			        }

			        ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", true);
			        ConfigWorldGeneral.saveConfigFile();
		        } else {
			        Bukkit.getServer().createWorld((new WorldCreator(worldname)));

			        for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded")) {
				        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
			        }
		        }
        	} else if (args[0].equalsIgnoreCase("unload")) {
        		if (!p.hasPermission("hawn.command.world.unload") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.unload");

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

        		if (worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
					return true;
				}

        		if (Bukkit.getWorld(worldname) != null) {
        			Bukkit.getServer().unloadWorld(worldname, true);

        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Unload")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
                    }

        		} else {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Command.Unload.Error")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        		}
        	}
        }

        return true;
    }
    
    private static boolean deleteDirectory(File path) {
    	if (path.exists()) {
    		File[] files = path.listFiles();
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