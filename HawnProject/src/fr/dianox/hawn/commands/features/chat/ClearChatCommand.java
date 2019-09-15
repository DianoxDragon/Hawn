package fr.dianox.hawn.commands.features.chat;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.ClearChatCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class ClearChatCommand extends BukkitCommand {
	
	public ClearChatCommand(String name) {
		super(name);
		this.description = "Clear a chat";
        this.usageMessage = "/cc <a/o/c/other> <player>/[reason] [reason]";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		int lines = ClearChatCommandConfig.getConfig().getInt("ClearChat.Lines-To-Clear");
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
				if ((args.length == 0)) {
					 sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
					 sender.sendMessage("");
					 sender.sendMessage("     §l>> §e§o§lClearChat Help");
					 sender.sendMessage("");
					 sender.sendMessage(" §8>> §7/cc a [reason] - §eClear the chat anonymously");
					 sender.sendMessage(" §8>> §c/cc o§7 - §eClear your own chat");
					 sender.sendMessage(" §8>> §7/cc c [reason] - §eClear the chat");
					 sender.sendMessage(" §8>> §7/cc other <player> [reason] - §eClear someone elses chat");
					 sender.sendMessage("");
					 sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
				} else if (args[0].equalsIgnoreCase("a")) {
					for (int i = 0; i < lines; i++) {
                    	MessageUtils.ReplaceCharBroadcastNoPlayer(" ");
					}
                    	
					String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
                        
					if (args.length >= 2) {
						if (!args[1].isEmpty()) {
							msg = "";
							for (int i = 1; i < args.length; i++) {
								if (!Objects.equals(msg, "")) {
									msg = msg + " ";
								}
								msg = msg + args[i];
							}
						}
					}
                        
					if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Anonymous.Message-Clear")) {
						for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Anonymously")) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
							MessageUtils.ReplaceCharBroadcastNoPlayer(ms1.replaceAll("%reason%", msg));
						}
					}
                        
				} else if (args[0].equalsIgnoreCase("o")) {
					sender.sendMessage("§cYou are the console");
				} else if (args[0].equalsIgnoreCase("c")) {
					for (int i = 0; i < lines; i++) {
        				MessageUtils.ReplaceCharBroadcastNoPlayer(" ");
                    }
                    
                    String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
                    
                    if (args.length >= 2) {
                        if (!args[1].isEmpty()) {
                        	msg = "";
                        	for (int i = 1; i < args.length; i++) {
                        		if (!Objects.equals(msg, "")) {
	    	                    	msg = msg + " ";
                        		}
                        		msg = msg + args[i];
                        	}
                        }
                    }
                    
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Message-Clear")) {
                    	for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Normal")) {
                    		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg).replaceAll("%player%", "console")));
                    		MessageUtils.ReplaceCharBroadcastNoPlayer(ms1.replaceAll("%reason%", msg));
                    	}
                    }
				} else if (args[0].equalsIgnoreCase("other")) {
					if (args.length != 2) {
                        sender.sendMessage(ChatColor.RED + "/cc other [player]");
                        return true;
                    }
					
                    Player target = Bukkit.getServer().getPlayer(args[1]);
                    
                    if (target == null) {
						if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
							for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						}
	            		return true;
					}
                    
                    for (int i = 0; i < lines; i++) {
                        target.sendMessage(" ");
                    }
                    
                    String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
                    
                    if (args.length >= 2) {
                        if (!args[1].isEmpty()) {
                        	msg = "";
                        	for (int i = 1; i < args.length; i++) {
                        		if (!Objects.equals(msg, "")) {
	    	                    	msg = msg + " ";
                        		}
                        		msg = msg + args[i];
                        	}
                        }
                    }
                    
                    for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Other.Sender")) {
                    	MessageUtils.ReplaceMessageForConsolePingCommand(ms1.replaceAll("%reason%", msg), sender, target);
                    }
                    for (String ms2: ConfigMCommands.getConfig().getStringList("ClearChat.Other.Target")) {
                    	MessageUtils.ReplaceCharMessagePlayer(ms2.replaceAll("%reason%", msg), target);
                    }
				}
			
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Enable")) {
			if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Disable-Message")) {
				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                	}
    			}
			}
			
			return true;
		}
		
		// The command
                if ((args.length == 0)) {
                	if (p.hasPermission("hawn.command.clearchat.help")) {
	                    p.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
	                    p.sendMessage("");
	                    p.sendMessage("     §l>> §e§o§lClearChat Help");
	                    p.sendMessage("");
	                    p.sendMessage(" §8>> §7/cc a [reason] - §eClear the chat anonymously");
	                    p.sendMessage(" §8>> §7/cc o - §eClear your own chat");
	                    p.sendMessage(" §8>> §7/cc c [reason] - §eClear the chat");
	                    p.sendMessage(" §8>> §7/cc other <player> [reason] - §eClear someone elses chat");
	                    p.sendMessage("");
	                    p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
                	}
                } else if (args[0].equalsIgnoreCase("a")) {
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Anonymous.Enable")) {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Anonymous.Use_Permission")) {
                    		if (p.hasPermission("hawn.command.clearchat.anonymous")) {
		                        for (int i = 0; i < lines; i++) {
		                        	MessageUtils.ReplaceCharBroadcastNoPlayer(" ");
		                        }
		                        
		                        String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
		                        
		                        if (args.length >= 2) {
		                            if (!args[1].isEmpty()) {
		                            	msg = "";
		                            	for (int i = 1; i < args.length; i++) {
		                            		if (!Objects.equals(msg, "")) {
		    	    	                    	msg = msg + " ";
		                            		}
		                            		msg = msg + args[i];
		                            	}
		                            }
		                        }
		                        
		                        if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Anonymous.Message-Clear")) {
		                        	for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Anonymously")) {
		                        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
		                        		MessageUtils.ReplaceCharBroadcastPlayer(ms1.replaceAll("%reason%", msg), p);
		                        	}
		                        }
                    		} else {
                    			String Permission = "hawn.command.clearchat.anonymous";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    		}
                    	} else {
                    		for (int i = 0; i < lines; i++) {
                    			MessageUtils.ReplaceCharBroadcastNoPlayer(" ");
	                        }
	                        
	                        String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
	                        
	                        if (args.length >= 2) {
	                            if (!args[1].isEmpty()) {
	                            	msg = "";
	                            	for (int i = 1; i < args.length; i++) {
	                            		if (!Objects.equals(msg, "")) {
	    	    	                    	msg = msg + " ";
	                            		}
	                            		msg = msg + args[i];
	                            	}
	                            }
	                        }
	                        
	                        if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Anonymous.Message-Clear")) {
	                        	for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Anonymously")) {
	                        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
	                        		MessageUtils.ReplaceCharBroadcastPlayer(ms1.replaceAll("%reason%", msg), p);
	                        	}
	                        }
                    	}
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Anonymous.Disable-Message")) {
                    		if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            	}
                			}
                        }
                    }
                } else if (args[0].equalsIgnoreCase("c")) {
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Enable")) {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Use_Permission")) {
                    		if (p.hasPermission("hawn.command.clearchat.normal")) {
                    			for (int i = 0; i < lines; i++) {
                    				MessageUtils.ReplaceCharBroadcastNoPlayer(" ");
    	                        }
    	                        
    	                        String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
    	                        
    	                        if (args.length >= 2) {
    	                            if (!args[1].isEmpty()) {
    	                            	msg = "";
    	                            	for (int i = 1; i < args.length; i++) {
    	                            		if (!Objects.equals(msg, "")) {
    	    	    	                    	msg = msg + " ";
    	                            		}
    	                            		msg = msg + args[i];
    	                            	}
    	                            }
    	                        }
		                        
		                        if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Message-Clear")) {
		                        	for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Normal")) {
		                        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
		                        		MessageUtils.ReplaceCharBroadcastPlayer(ms1.replaceAll("%reason%", msg), p);
		                        	}
		                        }
                    		} else {
                    			String Permission = "hawn.command.clearchat.normal";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    		}
                    	} else {
                    		for (int i = 0; i < lines; i++) {
                    			MessageUtils.ReplaceCharBroadcastNoPlayer(" ");
	                        }
	                        
	                        String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
	                        
	                        if (args.length >= 2) {
	                            if (!args[1].isEmpty()) {
	                            	msg = "";
	                            	for (int i = 1; i < args.length; i++) {
	                            		if (!Objects.equals(msg, "")) {
	    	    	                    	msg = msg + " ";
	                            		}
	                            		msg = msg + args[i];
	                            	}
	                            }
	                        }
	                        
	                        if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Message-Clear")) {
	                        	for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Normal")) {
	                        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
	                        		MessageUtils.ReplaceCharBroadcastPlayer(ms1.replaceAll("%reason%", msg), p);
	                        	}
	                        }
                    	}
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Disable-Message")) {
                    		if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            	}
                			}
                        }
                    }
                } else if (args[0].equalsIgnoreCase("o") || args[0].equalsIgnoreCase("own")) {
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Enable")) {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Use_Permission")) {
                    		if (p.hasPermission("hawn.command.clearchat.own")) {
		                        for (int i = 0; i < lines; i++) {
		                            p.sendMessage(" ");
		                        }
		                        if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Message-Clear")) {
			                        for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Own")) {
			                        	MessageUtils.ReplaceCharMessagePlayer(ms1, p);
		                        	}
		                        }
                    		} else {
                    			String Permission = "hawn.command.clearchat.own";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    		}
                    	} else {
                    		for (int i = 0; i < lines; i++) {
	                            p.sendMessage(" ");
	                        }
	                        if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Message-Clear")) {
		                        for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Own")) {
		                        	MessageUtils.ReplaceCharMessagePlayer(ms1, p);
	                        	}
	                        }
                    	}
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Disable-Message")) {
                    		if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            	}
                			}
                        }
                    }
                } else if (args[0].equalsIgnoreCase("other")) {
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Other.Enable")) {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Other.Use_Permission")) {
                    		if (p.hasPermission("hawn.command.clearchat.other")) {
                    			if (args.length < 2) {
    	                            p.sendMessage(ChatColor.RED + "/cc other [player]");
    	                            return true;
    	                        }
    	                        Player target = Bukkit.getServer().getPlayer(args[1]);
    	                        if (target == null) {
    	                        	MessageUtils.PlayerDoesntExist(p);
    	                            return true;
    	                        }
    	                        for (int i = 0; i < lines; i++) {
    	                            target.sendMessage(" ");
    	                        }
    	                        
    	                        String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
    	                        
    	                        if (args.length >= 2) {
    	                            if (!args[1].isEmpty()) {
    	                            	msg = "";
    	                            	for (int i = 1; i < args.length; i++) {
    	                            		if (!Objects.equals(msg, "")) {
    	    	    	                    	msg = msg + " ";
    	                            		}
    	                            		msg = msg + args[i];
    	                            	}
    	                            }
    	                        }
    	                        
    	                        for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Other.Sender")) {
    	                        	MessageUtils.ReplaceCharMessagePlayer(ms1.replaceAll("%reason%", msg), p);
    	                        }
    	                        for (String ms2: ConfigMCommands.getConfig().getStringList("ClearChat.Other.Target")) {
    	                        	MessageUtils.ReplaceCharMessagePlayer(ms2.replaceAll("%reason%", msg), target);
    	                        }
                    		} else {
                    			String Permission = "hawn.command.clearchat.other";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    		}
                    	} else {
                    		if (args.length < 2) {
	                            sender.sendMessage(ChatColor.RED + "/cc other [player]");
	                            return true;
	                        }
	                        Player target = Bukkit.getServer().getPlayer(args[1]);
	                        if (target == null) {
	                        	MessageUtils.PlayerDoesntExist(p);
	                            return true;
	                        }
	                        for (int i = 0; i < lines; i++) {
	                            target.sendMessage(" ");
	                        }
	                        
	                        String msg = ConfigMCommands.getConfig().getString("ClearChat.No-Reason");
	                        
	                        if (args.length >= 2) {
	                            if (!args[1].isEmpty()) {
	                            	msg = "";
	                            	for (int i = 1; i < args.length; i++) {
	                            		if (!Objects.equals(msg, "")) {
	    	    	                    	msg = msg + " ";
	                            		}
	                            		msg = msg + args[i];
	                            	}
	                            }
	                        }
	                        
	                        for (String ms1: ConfigMCommands.getConfig().getStringList("ClearChat.Other.Sender")) {
	                        	MessageUtils.ReplaceCharMessagePlayer(ms1.replaceAll("%reason%", msg), p);
	                        }
	                        for (String ms2: ConfigMCommands.getConfig().getStringList("ClearChat.Other.Target")) {
	                        	MessageUtils.ReplaceCharMessagePlayer(ms2.replaceAll("%reason%", msg), target);
	                        }
                    	}
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Other.Disable-Message")) {
                    		if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            	}
                			}
                        }
                    }
                }
            
		
		return true;
	}
	
	

}
