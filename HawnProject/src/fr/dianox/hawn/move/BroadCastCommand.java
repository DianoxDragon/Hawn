package fr.Dianox.Hawn.move;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Utility.MessageUtils;
import fr.dianox.hawn.Utility.XSound;
import fr.dianox.hawn.Utility.Config.Commands.BroadCastCommandConfig;
import fr.dianox.hawn.Utility.Config.Messages.ConfigMCommands;
import fr.dianox.hawn.Utility.Config.Messages.ConfigMOStuff;

public class BroadCastCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.broadcast";
	String[] parts;
	int partlenght = 0;
	Boolean partsenabled = false;
	
	public BroadCastCommand(String name) {
		super(name);
		this.description = "Broadcast a message";
        this.usageMessage = "/broadcast <msg>";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) {
				if (args.length == 0) {
					for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
						MessageUtils.ReplaceMessageForConsole(msg);
	            	}
                    return true;
				}
				
				String msgbc = "";
				
				if (args.length >= 1) {
                    if (!args[0].isEmpty()) {
                    	for (int i = 1; i < args.length; i++) {
                    		if (!Objects.equals(msgbc, "")) {
                    			msgbc = msgbc + " ";
                    		}
                    		msgbc = msgbc + args[i];
                    	}
                    }
                }
				
				msgbc = args[0] + " " + msgbc;
				
				if (msgbc.contains("//n")) {
					parts = msgbc.split("//n");
					partsenabled = true;
					partlenght = parts.length;
				}
				
				if (partsenabled) {
					int check = 0;
					while (check < partlenght) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Broadcast")) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%broadcast%", parts[check])));
							MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%broadcast%", parts[check]));
						}
						
						check++;
					}
				} else {
					for (String msg: ConfigMCommands.getConfig().getStringList("Broadcast")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%broadcast%", msgbc)));
						MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%broadcast%", msgbc));
					}
				}
				
				if (BroadCastCommandConfig.getConfig().getBoolean("Broadcast.Sounds.Enabled")) {
                    for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                        String sound = BroadCastCommandConfig.getConfig().getString("Broadcast.Sounds.Sound");
                        int volume = BroadCastCommandConfig.getConfig().getInt("Broadcast.Sounds.Volume");
                        int pitch = BroadCastCommandConfig.getConfig().getInt("Broadcast.Sounds.Pitch");
                        player.playSound(player.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
                    }
				
			}
		    return true;
		    
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!BroadCastCommandConfig.getConfig().getBoolean("Broadcast.Enable")) {
			if (BroadCastCommandConfig.getConfig().getBoolean("Broadcast.Disable-Message")) {
				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
						if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
							for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
	                    return true;
					}
					
					String msgbc = "";
					
					if (args.length >= 1) {
                        if (!args[0].isEmpty()) {
                        	for (int i = 1; i < args.length; i++) {
                        		if (!Objects.equals(msgbc, "")) {
                        			msgbc = msgbc + " ";
                        		}
                        		msgbc = msgbc + args[i];
                        	}
                        }
                    }
										
					msgbc = args[0] + " " + msgbc;
					
					if (msgbc.contains("//n")) {
						parts = msgbc.split("//n");
						partsenabled = true;
						partlenght = parts.length;
					}
					
					if (partsenabled) {
						int check = 0;
						while (check < partlenght) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Broadcast")) {
								Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%broadcast%", parts[check])));
								MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%broadcast%", parts[check]));
							}
							
							check++;
						}
					} else {
						for (String msg: ConfigMCommands.getConfig().getStringList("Broadcast")) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%broadcast%", msgbc)));
							MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%broadcast%", msgbc));
						}
					}
					
					if (BroadCastCommandConfig.getConfig().getBoolean("Broadcast.Sounds.Enabled")) {
	                    for (Player player: Bukkit.getServer().getOnlinePlayers()) {
	                        String sound = BroadCastCommandConfig.getConfig().getString("Broadcast.Sounds.Sound");
	                        int volume = BroadCastCommandConfig.getConfig().getInt("Broadcast.Sounds.Volume");
	                        int pitch = BroadCastCommandConfig.getConfig().getInt("Broadcast.Sounds.Pitch");
	                        player.playSound(player.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
	                    }
					}
		
		return true;
	}
}