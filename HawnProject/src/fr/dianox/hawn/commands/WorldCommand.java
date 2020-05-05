package fr.dianox.hawn.commands;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.modules.worldsystem.GuiSystem;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.commands.WorldCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.messages.WorldManagerPanelConfig;

public class WorldCommand extends BukkitCommand {

	public static List<File> fileList = new ArrayList<>();
    String GeneralPermission = "hawn.command.world.general";

    public WorldCommand(String name) {
        super(name);
        this.description = "Manage world system";
        this.usageMessage = "/hw <argument> <argument two> etc.";
    }

	@Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
        	// Check world system
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
        } else if (args.length >= 1) {
        	if (args[0].equalsIgnoreCase("list")) {
        		if (!p.hasPermission("hawn.command.world.list") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.list");
					
					return true;
				}
        		
        		List<World> worldList = Bukkit.getServer().getWorlds();
        		for (int i = 0; i < Bukkit.getServer().getWorlds().size(); i++) {
        			String name = ((World)worldList.get(i)).getName();
        			String type = ((World)worldList.get(i)).getEnvironment().toString();
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
        		
                List<String> stringList = new ArrayList<String>();
                List<World> worldList = Bukkit.getServer().getWorlds();
                
                for (int i = 0; i < Bukkit.getServer().getWorlds().size(); i++) {
                  String name = ((World)worldList.get(i)).getName();
                  stringList.add(name);
                }
                
                if (stringList.contains(worldname)) {
                	if (!p.getWorld().getName().equals(worldname)) {
	                	World world = Bukkit.getWorld(worldname);
	                    Location location = world.getSpawnLocation();
	                    p.teleport(location);
	                    
	                    for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Success")) {
	                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
	                    }
                	} else {
                		for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Error-Tp")) {
	                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
	                    }
                	}
                } 
        	} else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")) {
        		if (!p.hasPermission("hawn.command.world.delete") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.delete");
					
					return true;
				}
        		
        		String worldname = "";
        		
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
        				for (int i = 0; i < list.size(); i++) {
        					Player plist = (Player)list.get(i);
        					List<World> tpList = Bukkit.getServer().getWorlds();
        					World spawn = (World)tpList.get(0);
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
        		
        		String worldname = "";
        		
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
		        			if (args[3].equalsIgnoreCase("flat")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.FLAT));
		        			} else if (args[3].equalsIgnoreCase("amplified")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.AMPLIFIED));
		        			} else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NORMAL).type(WorldType.LARGE_BIOMES));
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
	        			
	        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
	                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
	                    }
	        		} else if (args[2].equalsIgnoreCase("end") || args[2].equalsIgnoreCase("the_end")) {
	        			if (args.length >= 4) {
		        			if (args[3].equalsIgnoreCase("flat")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.FLAT));
		        			} else if (args[3].equalsIgnoreCase("amplified")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.AMPLIFIED));
		        			} else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.THE_END).type(WorldType.LARGE_BIOMES));
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
	        			
	        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
	                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
	                    }
	        		} else if (args[2].equalsIgnoreCase("nether")) {
	        			if (args.length >= 4) {
		        			if (args[3].equalsIgnoreCase("flat")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.FLAT));
		        			} else if (args[3].equalsIgnoreCase("amplified")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.AMPLIFIED));
		        			} else if (args[3].equalsIgnoreCase("large_biomes") || args[3].equalsIgnoreCase("largebiomes")) {
		        				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(Environment.NETHER).type(WorldType.LARGE_BIOMES));
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
        		
        		String worldname = "";
        		
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
        		
        		for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.Importing-A-World")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                }
        		
        		Bukkit.getServer().createWorld((new WorldCreator(worldname)));
        		
        		for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded")) {
        			ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
                }
        		
        	} else if (args[0].equalsIgnoreCase("unload")) {
        		if (!p.hasPermission("hawn.command.world.unload") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.unload");
					
					return true;
				}
        		
        		String worldname = "";
        		
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
        			
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded")) {
        				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "", "", false);
                    }
        			
        		} else {
        			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Command.Unload.Error")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
        		}
        	}
        } else {
        	p.sendMessage("§c/hw <argument> <argument two> etc.");
        }

        return true;
    }
    
    private static boolean deleteDirectory(File path) {
    	if (path.exists()) {
    		File[] files = path.listFiles();
    		for (int i = 0; i < files.length; i++) {
    			if (files[i].isDirectory()) {
    				deleteDirectory(files[i]);
    			} else {
    				files[i].delete();
    			} 
    		} 
    	} 
    	return path.delete();
    }
    
    public static boolean checkIfIsWorld(File worldFolder) {
		if (worldFolder.isDirectory()) {
			File[] files = worldFolder.listFiles(new FilenameFilter() {
				public boolean accept(File file, String name) {
					return name.toLowerCase().endsWith(".dat");
				}
			});
			if (files != null && files.length > 0) return true;
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