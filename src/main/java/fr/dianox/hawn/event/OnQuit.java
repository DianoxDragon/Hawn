package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.SQL;
import fr.dianox.hawn.commands.features.FlyCommand;
import fr.dianox.hawn.commands.features.VanishCommand;
import fr.dianox.hawn.event.onquite.OQMessages;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.PlayerVisibility;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.commands.VanishCommandConfig;
import fr.dianox.hawn.utility.config.events.ConfigGJoinQuitCommand;
import fr.dianox.hawn.utility.world.CommandsPW;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuit implements Listener {
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		
		Player p = e.getPlayer();
		String uuid = p.getUniqueId().toString();
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
			} else {
				Bukkit.getConsoleSender().sendMessage("§cRemember, the database MYSQL is not working, or the connection is not possible at the moment. Save on MYSQL is not possible");
			}
		}
		
		// YAML
			// Player last position
			ConfigPlayerGet.writeString(uuid, "player_last_position.World", p.getLocation().getWorld().getName());
			ConfigPlayerGet.writeDouble(uuid, "player_last_position.X", p.getLocation().getX());
			ConfigPlayerGet.writeDouble(uuid, "player_last_position.Y", p.getLocation().getY());
			ConfigPlayerGet.writeDouble(uuid, "player_last_position.Z", p.getLocation().getZ());
			ConfigPlayerGet.writeFloat(uuid, "player_last_position.YAW", p.getLocation().getYaw());
			ConfigPlayerGet.writeFloat(uuid, "player_last_position.PITCH", p.getLocation().getPitch());
			
			if (!VanishCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				// Player vanish
				if (p.hasPermission("hawn.betweenservers.keepvanish")) {
					if (VanishCommand.player_list_vanish.contains(p)) {
						PlayerOptionSQLClass.SaveSQLPOVanish(p, "TRUE");
					} else {
						PlayerOptionSQLClass.SaveSQLPOVanish(p, "FALSE");
					}
				}
			}
			
			PlayerOptionSQLClass.SaveSQLPOGamemode(p, gm);
						
		// Quit message
		OQMessages.OnMessage(p, e);
		
		FlyCommand.player_list_flyc.remove(p);
		
		if (Main.indj.contains(p)) {
			Main.indj.remove(p);
		}
		
		if (PlayerOptionMainConfig.getConfig().getBoolean("Options.Flying.Put-boots") && p.hasPermission("hawn.fun.boots.flying")) {
			try {
				if (p.getInventory().getBoots().getType() == XMaterial.DIAMOND_BOOTS.parseMaterial()) {
					if (p.getInventory().getBoots().getItemMeta().getDisplayName().contains("§eI'm flyyyyinggggggg")) {
						p.getInventory().setBoots(null);
						p.getInventory().setBoots(FunFeatures.boots.get(p));
					}
				}
			} catch (Exception e2) {}
		}
		
		// QuitCommand
        if (ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.Enable")) {
        	if (!ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.World.All_World")) {
        		if (CommandsPW.getWConsoleQuitCommand().contains(p.getWorld().getName())) {
		        	for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("QuitCommand.Commands")) {
		        		String perm = "";
                        String world = "";
                        
                        if (commands.startsWith("<world>") && commands.contains("</world>")) {
                        	world = StringUtils.substringBetween(commands, "<world>", "</world>");
                            commands = commands.replace("<world>" + world + "</world> ", "");

                            if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                                continue;
                            }
                        }
                        
                        if (commands.contains("<perm>") && commands.contains("</perm>")) {
                            perm = StringUtils.substringBetween(commands, "<perm>", "</perm>");
                            commands = commands.replace("<perm>" + perm + "</perm> ", "");

                            if (!p.hasPermission(perm)) {
                                continue;
                            }
                        }

                        if (commands.startsWith("[command-console]: ")) {
                            commands = commands.replace("[command-console]: ", "");
                            commands = commands.replaceAll("%player%", p.getName());

                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands);
                        } else {
                        	commands = commands.replaceAll("%player%", p.getName());

                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands);
                        }
					}
        		}
        	} else {
        		for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("QuitCommand.Commands")) {
        			String perm = "";
                    String world = "";
                    
                    if (commands.startsWith("<world>") && commands.contains("</world>")) {
                    	world = StringUtils.substringBetween(commands, "<world>", "</world>");
                        commands = commands.replace("<world>" + world + "</world> ", "");

                        if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                            continue;
                        }
                    }
                    
                    if (commands.contains("<perm>") && commands.contains("</perm>")) {
                        perm = StringUtils.substringBetween(commands, "<perm>", "</perm>");
                        commands = commands.replace("<perm>" + perm + "</perm> ", "");

                        if (!p.hasPermission(perm)) {
                            continue;
                        }
                    }

                    if (commands.startsWith("[command-console]: ")) {
                        commands = commands.replace("[command-console]: ", "");
                        commands = commands.replaceAll("%player%", p.getName());

                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands);
                    } else {
                    	commands = commands.replaceAll("%player%", p.getName());

                        Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands);
                    }
    			}
        	}
        }
		
		OnJoin.player_list.remove(p);
	}

}
