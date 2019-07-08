package fr.Dianox.Hawn.Commands; //package name

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.PingCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class PingCommand extends BukkitCommand {
		
	public PingCommand(String name) {
		super(name);
		this.description = "To know the ping"; //description displayed in the /help section
        this.usageMessage = "/ping"; //the actual command used to trigger the code
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) { //if the sender is NOT a player (referring to the console)

				if ((args.length == 1)) { //if there's only word, in this instance that means that it's just "/ping"
					
					Player target = Bukkit.getServer().getPlayer(args[0]);
					
					if (target == null) { //if there's no player targeted
						if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) { //checks if it's allowed to output an error
							for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) { //outputs an error
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						}
	            		return true;
					}
					
					for (String msg: ConfigMCommands.getConfig().getStringList("Ping.Other")) {
        				MessageUtils.ReplaceMessageForConsolePingCommand(msg, sender, target);
        			}
					
				} else {
					sender.sendMessage("§c/ping <player>");
				}

            return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		// Command
            if ((args.length == 0)) {
            	if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Use_Permission")) { //checks if the user (server config) has enabled permissions
            		if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Enable")) { //checks if the user (server config) has enabled this permission
            			if (p.hasPermission("hawn.command.ping.self")) { //checks if the player has the permissions to use the command
            				for (String msg: ConfigMCommands.getConfig().getStringList("Ping.Self")) { //outputs the message put in the config
            					MessageUtils.ReplaceCharMessagePlayer(msg, p);
            				}
            			} else {
            				String Permission = "hawn.command.ping.self";
                			MessageUtils.MessageNoPermission(p, Permission); //if the player doesn't have the permission
            			}
            		} else {
            			if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Disable-Message")) { //checks if the message is disabled in the config
            				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) { //checks if it's allowed to output an error
	                			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) { //outputs the error message put in the config
	                        		MessageUtils.ReplaceCharMessagePlayer(msg, p);
	                        	}
                			}
            			}
            		}
            	} else {
            		if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Enable")) {
            			for (String msg: ConfigMCommands.getConfig().getStringList("Ping.Self")) {
            				MessageUtils.ReplaceCharMessagePlayer(msg, p);
        				}
	                } else {
	                    if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Disable-Message")) {
	                    	if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
	                			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
	                        		MessageUtils.ReplaceCharMessagePlayer(msg, p);
	                        	}
                			}
	                    }
	                }
            	}
            } else if ((args.length == 1)) {
                Player other = Bukkit.getPlayer(args[0]);
                if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Use_Permission")) {
                	if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Enable")) {
                		if (p.hasPermission("hawn.command.ping.other")) {
                			if (other == null) {
                				MessageUtils.PlayerDoesntExist(p);
	                            return true;
                			}
                			for (String msg: ConfigMCommands.getConfig().getStringList("Ping.Other")) {
                				MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%ping%", String.valueOf(PingCommand.getPing(other))), p);
                			}
                		} else {
                			String Permission = "hawn.command.ping.other";
                			MessageUtils.MessageNoPermission(p, Permission);
                		}
                	} else {
                		if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Disable-Message")) {
                			if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
	                			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
	                        		MessageUtils.ReplaceCharMessagePlayer(msg, p);
	                        	}
                			}
            			}
                	}
                } else {
                	if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Enable")) {
	                    if (other == null) {
	                    	MessageUtils.PlayerDoesntExist(p);
                            return true;
	                    }
	                    for (String msg: ConfigMCommands.getConfig().getStringList("Ping.Other")) {
	                    	MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%ping%", String.valueOf(PingCommand.getPing(other))), p);
            			}
	                } else {
	                    if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Disable-Message")) {
	                    	if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
	                			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
	                        		MessageUtils.ReplaceCharMessagePlayer(msg, p);
	                        	}
                			}
	                    }
	                }
                }
            }
		
		return true;
	}
	
	public static int getPing(Player p) { //public static integer "getPing"
        String bpName = Bukkit.getServer().getClass().getPackage().getName();
        String version = bpName.substring(bpName.lastIndexOf(".") + 1, bpName.length());
        try {
          Class<?> CPClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
          Object CraftPlayer = CPClass.cast(p);
          
          Method getHandle = CraftPlayer.getClass().getMethod("getHandle", new Class[0]);
          Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);
          
          Field ping = EntityPlayer.getClass().getDeclaredField("ping");
          
          return ping.getInt(EntityPlayer); //makes sure the getPing integer value is equal to the ping of the player whom casted the command
        } catch (Exception e) {
          e.printStackTrace(); //prints a helpful error which helps Dianox find where the actual problem occurred
        }
        return 0;
	}

}
