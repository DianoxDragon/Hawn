package fr.Dianox.Hawn.Event;

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
import fr.Dianox.Hawn.Event.OnQuitE.OQMessages;
import fr.Dianox.Hawn.Utility.ConfigPlayerGet;
import fr.Dianox.Hawn.Utility.PlayerOptionSQLClass;
import fr.Dianox.Hawn.Utility.PlayerVisibility;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.Commands.VanishCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGJoinQuitCommand;
import fr.Dianox.Hawn.Utility.World.CommandsPW;

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
			
			// Player gamemode
			ConfigPlayerGet.writeInt(uuid, "player_gamemode.player_gamemode", gm);
			
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
						
		// Quit message
		OQMessages.OnMessage(p, e);
		
		FlyCommand.player_list_flyc.remove(p);
		
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
