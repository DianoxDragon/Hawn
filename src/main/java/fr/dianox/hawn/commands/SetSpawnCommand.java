package fr.dianox.hawn.commands;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.SpawnUtils;
import fr.dianox.hawn.utility.config.ConfigSpawn;
import fr.dianox.hawn.utility.config.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.administration.SpawnMConfig;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends BukkitCommand {
	
	public SetSpawnCommand(String name) {
		super(name);
		this.description = "Creates a new spawn";
        this.usageMessage = "/setspawn [spawn] [d:true] [w:world1,world2 etc.]";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!SpawnCommandConfig.getConfig().getBoolean("SetSpawn.Enable")) {
			if (SpawnCommandConfig.getConfig().getBoolean("SetSpawn.Disable-Message")) {
				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                	}
    			}
			}
			
			return true;
		}
		
		if (!p.hasPermission("hawn.admin") && !p.hasPermission("hawn.admin.*")) {
			MessageUtils.MessageNoPermission(p, "hawn.admin");
			return true;
		}
		
		// The command
		
		//  SI LA COMMANDE EST NUL
		if (args.length == 0) {
			createcomplexspawn(p, true, false, "", false, "");
			
			return true;
		// SI ARGUMENT SUPERIEUR OU EGALE A 1
		} else if (args.length == 1) {
			if (args[0].startsWith("d:")) {
				createcomplexspawn(p, true, false, "", false, "");
			} else if (args[0].startsWith("w:")) {		
				createcomplexspawn(p, false, true, args[0], false, "");				
			} else {
				createcomplexspawn(p, false, false, "", true, args[0]);		
			}
		} else if (args.length == 2) {
			if (args[0].startsWith("d:")) {
				if (args[1].startsWith("w:")) {
					createcomplexspawn(p, true, true, args[1], false, "");		
				} else {
					createcomplexspawn(p, true, false, "", true, args[1]);		
				}
			} else if (args[0].startsWith("w:")) {			
				if (args[1].startsWith("d:")) {
					createcomplexspawn(p, true, true, args[0], false, "");
				} else {
					createcomplexspawn(p, false, true, args[0], true, args[1]);
				}
			} else {
				if (args[1].startsWith("d:")) {
					createcomplexspawn(p, true, false, "", true, args[0]);
				} else if (args[1].startsWith("w:")) {
					createcomplexspawn(p, false, true, args[1], true, args[0]);
				}
			}
		} else if (args.length == 3) {
			if (args[0].startsWith("d:")) {
				if (args[1].startsWith("w:")) {
					createcomplexspawn(p, true, true, args[1], true, args[2]);		
				} else {
					createcomplexspawn(p, true, true, args[2], true, args[1]);		
				}
			} else if (args[0].startsWith("w:")) {			
				if (args[1].startsWith("d:")) {
					createcomplexspawn(p, true, true, args[0], true, args[2]);
				} else {
					createcomplexspawn(p, true, true, args[0], true, args[1]);
				}
			} else {
				if (args[1].startsWith("d:")) {
					createcomplexspawn(p, true, true, args[2], true, args[0]);
				} else if (args[1].startsWith("w:")) {
					createcomplexspawn(p, true, true, args[1], true, args[0]);
				}
			}
		} else {
			p.sendMessage("§c/setspawn [spawn] [d:true] [w:world1,world2 etc.]");
		}
		
		return false;
	}

	public static void createcomplexspawn(Player p, Boolean defaulbool, Boolean multiworld, String worldlist, Boolean spawname, String worldstring) {
		if (multiworld) {
			String worldlistformat = worldlist;
			
			worldlistformat = worldlistformat.replace("w:", "");
			
			String[] arr = worldlistformat.split(",");
			
			for (String ss : arr) {
				
				Integer repeat = 0;
				
				if (spawname) {
					if (!ConfigSpawn.getConfig().isSet("Coordinated."+worldstring + repeat)) {
						String spawnName = worldstring + repeat;
						Location l = p.getLocation();
						
						SpawnUtils.createSpawn(p, spawnName, ss, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			            
			            ConfigSpawn.saveConfigFile();
			            
			            p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
			            
			            for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
						}
			            
			            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
			            }
			            
			            if (defaulbool) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
		                }
			            
			            repeat++;
					} else {
			            Integer number = 1;
			            while (ConfigSpawn.getConfig().isSet("Coordinated."+worldstring+number)) {
			            	number++;
			            }
			            String spawnName = worldstring+number;
			            Location l = p.getLocation();
						
			            SpawnUtils.createSpawn(p, spawnName, ss, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			            
			            p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
			            
			            for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
						}
			            
			            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
			            }
			            
			            if (defaulbool) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
		                }
					}
				} else {
					if (!ConfigSpawn.getConfig().isSet("Coordinated.Spawn1")) {
						String spawnName = "Spawn1";
						Location l = p.getLocation();
						
						SpawnUtils.createSpawn(p, spawnName, ss, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			            
			            ConfigSpawn.saveConfigFile();
			            
			            p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
			            
			            for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
						}
			            
			            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
			            }
			            
			            if (defaulbool) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
		                }
					} else {
			            Integer number = 1;
			            while (ConfigSpawn.getConfig().isSet("Coordinated.Spawn"+number)) {
			            	number++;
			            }
			            String spawnName = "Spawn"+number;
			            Location l = p.getLocation();
						
			            SpawnUtils.createSpawn(p, spawnName, ss, l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			            
			            p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
			            
			            for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
						}
			            
			            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
			            }
			            
			            if (defaulbool) {
			            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			            	OnJoinConfig.saveConfigFile();
		                }
					}
				}
			}
		} else {
			if (spawname) {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+worldstring)) {
					String spawnName = worldstring;
					Location l = p.getLocation();
					
					SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
	                
	                ConfigSpawn.saveConfigFile();
	                
	                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
	                
	                for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Other")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
					}
	                
	                if (defaulbool) {
		            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
		            	OnJoinConfig.saveConfigFile();
	                }
				} else {
					for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Name-already-exist")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated.Spawn1")) {
					String spawnName = "Spawn1";
					Location l = p.getLocation();
					
					SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		            
		            ConfigSpawn.saveConfigFile();
		            
		            p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
		            
		            for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
					}
		            
		            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
		            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
		            	OnJoinConfig.saveConfigFile();
		            }
		            
		            if (defaulbool) {
		            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
		            	OnJoinConfig.saveConfigFile();
	                }
				} else {
		            Integer number = 1;
		            while (ConfigSpawn.getConfig().isSet("Coordinated.Spawn"+number)) {
		            	number++;
		            }
		            String spawnName = "Spawn"+number;
		            Location l = p.getLocation();
					
		            SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
		            
		            p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
		            
		            for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
					}
		            
		            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
		            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
		            	OnJoinConfig.saveConfigFile();
		            }
		            
		            if (defaulbool) {
		            	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
		            	OnJoinConfig.saveConfigFile();
	                }
				}
			}
		}
	}
}