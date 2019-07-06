package fr.Dianox.Hawn.Event;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.SQL;
import fr.Dianox.Hawn.Commands.Features.FlyCommand;
import fr.Dianox.Hawn.Commands.Features.VanishCommand;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.PlayerVisibility;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.PlayerConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.VanishCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGJoinQuitCommand;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.World.CommandsPW;
import fr.Dianox.Hawn.Utility.World.OnQuitPW;

public class OnQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		int gm = 0;
		
		if (p.getGameMode() == GameMode.SURVIVAL){
			gm = 0;
		} else if (p.getGameMode() == GameMode.CREATIVE){
			gm = 1;
		} else if (p.getGameMode() == GameMode.ADVENTURE){
			gm = 2;
		} else if (p.getGameMode() == GameMode.SPECTATOR){
			gm = 3;
		}
		
		PlayerVisibility.getPlayerVisibility().remove(p);
		
		// Mysql
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Enable")) {
			if (!Main.useyamllistplayer) {
				if (SQL.tableExists("player_last_position")) {
					if (SQL.exists("player_UUID", ""+p.getUniqueId()+"", "player_last_position")) {
						SQL.set("player_last_position", "player", ""+p.getName()+"", "player_UUID", ""+p.getUniqueId()+"");
						SQL.set("player_last_position", "World", ""+p.getLocation().getWorld().getName()+"", "player_UUID", ""+p.getUniqueId()+"");
						SQL.set("player_last_position", "X", ""+p.getLocation().getX()+"", "player_UUID", ""+p.getUniqueId()+"");
						SQL.set("player_last_position", "Y", ""+p.getLocation().getY()+"", "player_UUID", ""+p.getUniqueId()+"");
						SQL.set("player_last_position", "Z", ""+p.getLocation().getZ()+"", "player_UUID", ""+p.getUniqueId()+"");
						SQL.set("player_last_position", "YAW", ""+p.getLocation().getYaw()+"", "player_UUID", ""+p.getUniqueId()+"");
						SQL.set("player_last_position", "PITCH", ""+p.getLocation().getPitch()+"", "player_UUID", ""+p.getUniqueId()+"");
					} else {
						SQL.insertData("player, player_UUID, World, X, Y, Z, YAW, PITCH", 
								" '"+ p.getName() +"', '"+ p.getUniqueId() +"', '"+ p.getLocation().getWorld().getName() +"',"
										+ " '"+ p.getLocation().getX() +"', '"+ p.getLocation().getY() +"',"
												+ " '"+ p.getLocation().getZ() +"', '"+ p.getLocation().getYaw() +"',"
														+ " '"+ p.getLocation().getPitch() +"' ", "player_last_position");
					}
				} else {
					SQL.createTable("player_last_position", "player TEXT, player_UUID TEXT, World TEXT, X DOUBLE, Y DOUBLE, Z DOUBLE, YAW FLOAT, PITCH FLOAT");
						if (SQL.exists("player_UUID", ""+p.getUniqueId()+"", "player_last_position")) {
							SQL.set("player_last_position", "player", ""+p.getName()+"", "player_UUID", ""+p.getUniqueId()+"");
							SQL.set("player_last_position", "World", ""+p.getLocation().getWorld().getName()+"", "player_UUID", ""+p.getUniqueId()+"");
							SQL.set("player_last_position", "X", ""+p.getLocation().getX()+"", "player_UUID", ""+p.getUniqueId()+"");
							SQL.set("player_last_position", "Y", ""+p.getLocation().getY()+"", "player_UUID", ""+p.getUniqueId()+"");
							SQL.set("player_last_position", "Z", ""+p.getLocation().getZ()+"", "player_UUID", ""+p.getUniqueId()+"");
							SQL.set("player_last_position", "YAW", ""+p.getLocation().getYaw()+"", "player_UUID", ""+p.getUniqueId()+"");
							SQL.set("player_last_position", "PITCH", ""+p.getLocation().getPitch()+"", "player_UUID", ""+p.getUniqueId()+"");
						} else {
							SQL.insertData("player, player_UUID, World, X, Y, Z, YAW, PITCH", 
									" '"+ p.getName() +"', '"+ p.getUniqueId() +"', '"+ p.getLocation().getWorld().getName() +"',"
											+ " '"+ p.getLocation().getX() +"', '"+ p.getLocation().getY() +"',"
													+ " '"+ p.getLocation().getZ() +"', '"+ p.getLocation().getYaw() +"',"
															+ " '"+ p.getLocation().getPitch() +"' ", "player_last_position");
						}
				}
				
				// Player last position
				if (SQL.tableExists("player_gamemode")) {
					if (SQL.exists("player_UUID", ""+p.getUniqueId()+"", "player_gamemode")) {
						SQL.set("player_gamemode", "player", ""+p.getName()+"", "player_UUID", ""+p.getUniqueId()+"");	
						SQL.set("player_gamemode", "gamemode_state", ""+gm+"", "player_UUID", ""+p.getUniqueId()+"");	
					} else {
						SQL.insertData("player, player_UUID, gamemode_state", 
								" '"+ p.getName() +"', '"+ p.getUniqueId() +"', '"+gm+"' ", "player_gamemode");
					}
				} else {
					SQL.createTable("player_gamemode", "player TEXT, player_UUID TEXT, gamemode_state INT");
					if (SQL.exists("player_UUID", ""+p.getUniqueId()+"", "player_gamemode")) {
						SQL.set("player_gamemode", "player", ""+p.getName()+"", "player_UUID", ""+p.getUniqueId()+"");
						SQL.set("player_gamemode", "gamemode_state", ""+gm+"", "player_UUID", ""+p.getUniqueId()+"");
					} else {
						SQL.insertData("player, player_UUID, gamemode_state", 
								" '"+ p.getName() +"', '"+ p.getUniqueId() +"', '"+gm+"' ", "player_gamemode");
					}
				}
				if (!VanishCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				// Player vanish
				if (p.hasPermission("hawn.betweenservers.keepvanish")) {
					if (SQL.tableExists("player_vanish")) {
						if (SQL.exists("player_UUID", ""+p.getUniqueId()+"", "player_vanish")) {
							SQL.set("player_vanish", "player", ""+p.getName()+"", "player_UUID", ""+p.getUniqueId()+"");
							if (VanishCommand.player_list_vanish.contains(p)) {
								SQL.set("player_vanish", "vanished", "true", "player_UUID", ""+p.getUniqueId()+"");
							} else {
								SQL.set("player_vanish", "vanished", "false", "player_UUID", ""+p.getUniqueId()+"");
							}
						} else {
							if (VanishCommand.player_list_vanish.contains(p)) {
								SQL.insertData("player, player_UUID, vanished", 
										" '"+ p.getName() +"', '"+ p.getUniqueId() +"', 'true' ", "player_vanish");
							} else {
								SQL.insertData("player, player_UUID, vanished", 
										" '"+ p.getName() +"', '"+ p.getUniqueId() +"', 'false' ", "player_vanish");
							}
						}
					} else {
						SQL.createTable("player_vanish", "player TEXT, player_UUID TEXT, vanished TEXT");
						if (SQL.exists("player_UUID", ""+p.getUniqueId()+"", "player_vanish")) {
							SQL.set("player_vanish", "player", ""+p.getName()+"", "player_UUID", ""+p.getUniqueId()+"");
							if (VanishCommand.player_list_vanish.contains(p)) {
								SQL.set("player_vanish", "vanished", "true", "player_UUID", ""+p.getUniqueId()+"");
							} else {
								SQL.set("player_vanish", "vanished", "false", "player_UUID", ""+p.getUniqueId()+"");
							}
						} else {
							if (VanishCommand.player_list_vanish.contains(p)) {
								SQL.insertData("player, player_UUID, vanished", 
										" '"+ p.getName() +"', '"+ p.getUniqueId() +"', 'true' ", "player_vanish");
							} else {
								SQL.insertData("player, player_UUID, vanished", 
										" '"+ p.getName() +"', '"+ p.getUniqueId() +"', 'false' ", "player_vanish");
							}
						}
					}
				}
				}
			} else {
				Bukkit.getConsoleSender().sendMessage("§cRemember, the database MYSQL is not working, or the connection is not possible at the moment. Save on MYSQL is not possible");
			}
		}
		
		// YAML
			// Player last position
			PlayerConfig.getConfig().set("player_last_position."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
			PlayerConfig.getConfig().set("player_last_position."+p.getUniqueId()+".World", String.valueOf(p.getLocation().getWorld().getName()));
			PlayerConfig.getConfig().set("player_last_position."+p.getUniqueId()+".X", Double.valueOf(p.getLocation().getX()));
			PlayerConfig.getConfig().set("player_last_position."+p.getUniqueId()+".Y", Double.valueOf(p.getLocation().getY()));
			PlayerConfig.getConfig().set("player_last_position."+p.getUniqueId()+".Z", Double.valueOf(p.getLocation().getZ()));
			PlayerConfig.getConfig().set("player_last_position."+p.getUniqueId()+".YAW", Float.valueOf(p.getLocation().getYaw()));
			PlayerConfig.getConfig().set("player_last_position."+p.getUniqueId()+".PITCH", Float.valueOf(p.getLocation().getPitch()));
			
			// Player gamemode
			PlayerConfig.getConfig().set("player_gamemode."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
			PlayerConfig.getConfig().set("player_gamemode."+p.getUniqueId()+".player_gamemode", Integer.valueOf(gm));
			
			if (!VanishCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				// Player vanish
				if (p.hasPermission("hawn.betweenservers.keepvanish")) {
					PlayerConfig.getConfig().set("player_vanish."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
					if (VanishCommand.player_list_vanish.contains(p)) {
						PlayerConfig.getConfig().set("player_vanish."+p.getUniqueId()+".vanished", Boolean.valueOf(true));
					} else {
						PlayerConfig.getConfig().set("player_vanish."+p.getUniqueId()+".vanished", Boolean.valueOf(false));
					}
				}
			}
			
			PlayerConfig.saveConfigFile();
			
		// Quit message
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Enable")) {
			if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Silent-Staff-Quit")) {
				if (p.hasPermission("hawn.event.silentquit")) {
					e.setQuitMessage("");
				} else {
					if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Disable-Default-Message")) {
						e.setQuitMessage("");
					}
					
					if (!ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.World.All_World")) {
						if (OnQuitPW.getQM().contains(p.getWorld().getName())) {
							
							if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
								if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit")) {
									e.setQuitMessage("");
									
									Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
									
									while (iterator.hasNext()) {
										String string = (String)iterator.next();
										
										if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
											
											if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
												for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
													MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
												}
											} else {
												for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
													MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
												}
											}
											
											break;
										}
									}
									
								} else {
									Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
									
									while (iterator.hasNext()) {
										String string = (String)iterator.next();
										
										if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
											
											if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
												for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
													MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
												}
											} else {
												for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
													MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
												}
											}
											
											break;
										}
									}
									
									if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
										for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
											MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
										}
									} else {
										for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
											MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
										}
									}
								}
							} else {
								if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
									for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
										MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
									}
								} else {
									for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
										MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
									}
								}
							}
						}
					} else {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
							if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit")) {
								e.setQuitMessage("");
								
								Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
								
								while (iterator.hasNext()) {
									String string = (String)iterator.next();
									
									if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
										
										if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
											}
										} else {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
											}
										}
										
										break;
									}
								}
								
							} else {
								Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
								
								while (iterator.hasNext()) {
									String string = (String)iterator.next();
									
									if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
										
										if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
											}
										} else {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
											}
										}
										
										break;
									}
								}
								
								if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
									for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
										MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
									}
								} else {
									for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
										MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
									}
								}
							}
						} else {
							if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
								for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
									MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
								}
							} else {
								for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
									MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
								}
							}
						}
					}
				}
			} else {
				if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Disable-Default-Message")) {
					e.setQuitMessage("");
				}
				
				if (!ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.World.All_World")) {
					if (OnQuitPW.getQM().contains(p.getWorld().getName())) {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
							if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit")) {
								e.setQuitMessage("");
								
								Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
								
								while (iterator.hasNext()) {
									String string = (String)iterator.next();
									
									if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
										
										if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
											}
										} else {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
											}
										}
										
										break;
									}
								}
								
							} else {
								Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
								
								while (iterator.hasNext()) {
									String string = (String)iterator.next();
									
									if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
										
										if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
											}
										} else {
											for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
												MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
											}
										}
										
										break;
									}
								}
								
								if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
									for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
										MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
									}
								} else {
									for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
										MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
									}
								}
							}
						} else {
							if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
								for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
									MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
								}
							} else {
								for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
									MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
								}
							}
						}
					}
				} else {
					if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit")) {
							e.setQuitMessage("");
							
							Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
							
							while (iterator.hasNext()) {
								String string = (String)iterator.next();
								
								if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
									
									if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
										for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
											MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
										}
									} else {
										for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
											MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
										}
									}
									
									break;
								}
							}
							
						} else {
							Iterator<?> iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false).iterator();
							
							while (iterator.hasNext()) {
								String string = (String)iterator.next();
								
								if (p.hasPermission("hawn.on-quit.custommessage."+string)) {
									
									if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
										for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
											MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
										}
									} else {
										for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups."+string)) {
											MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
										}
									}
									
									break;
								}
							}
							
							if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
								for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
									MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
								}
							} else {
								for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
									MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
								}
							}
						}
					} else {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
							for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
								MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
							}
						} else {
							for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
								MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
							}
						}
					}
				}
			}
			
			FlyCommand.player_list_flyc.remove(p);
		}
		
		// QuitCommand
        if (ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.Enable")) {
        	if (!ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.QuitCommand-Console.World.All_World")) {
        		if (CommandsPW.getWConsoleQuitCommand().contains(p.getWorld().getName())) {
		        	for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("QuitCommand.QuitCommand-Console.Commands")) {
						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
					}
        		}
        	} else {
        		for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("QuitCommand.QuitCommand-Console.Commands")) {
    				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
    			}
        	}
        }
		
		OnJoin.player_list.remove(p);
	}

}