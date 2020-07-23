package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.ClearChatCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClearChatCommand extends BukkitCommand {
	
	public ClearChatCommand(String name) {
		super(name);
		this.description = "Clear a chat";
        this.usageMessage = "/cc <a/o/c/other> <player>/[reason] [reason]";
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

		if (args.length == 1) {
			List<String> tab = new ArrayList<>();
			tab.add("a");
			tab.add("o");
			tab.add("c");
			tab.add("other");

			return tab;
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("other")) {
				List<String> tab = new ArrayList<>();
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					tab.add(p.getName());
				}

				java.util.Collections.sort(tab);

				return tab;
			}
		}

		return null;
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
                    	ConfigEventUtils.ExecuteEventAllPlayersConsole(" ", "", "");
					}
                    	
					String msg = ConfigMMsg.getConfig().getString("ClearChat.No-Reason");
                        
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
						for (String ms1: ConfigMMsg.getConfig().getStringList("ClearChat.Anonymously")) {
							Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
							ConfigEventUtils.ExecuteEventAllPlayersConsole(ms1.replaceAll("%reason%", msg), "", "");
							MessageUtils.ConsoleMessages(ms1.replaceAll("%reason%", msg));
						}
					}
                        
				} else if (args[0].equalsIgnoreCase("o")) {
					sender.sendMessage("§cYou are the console");
				} else if (args[0].equalsIgnoreCase("c")) {
					for (int i = 0; i < lines; i++) {
						ConfigEventUtils.ExecuteEventAllPlayersConsole(" ", "", "");
                    }
                    
                    String msg = ConfigMMsg.getConfig().getString("ClearChat.No-Reason");
                    
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
                    	for (String ms1: ConfigMMsg.getConfig().getStringList("ClearChat.Normal")) {
                    		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg).replaceAll("%player%", "console")));
                    		ConfigEventUtils.ExecuteEventAllPlayersConsole(ms1.replaceAll("%reason%", msg), "", "");
							MessageUtils.ConsoleMessages(ms1.replaceAll("%reason%", msg));
                    	}
                    }
				} else if (args[0].equalsIgnoreCase("other")) {
					if (args.length < 2) {
                        sender.sendMessage(ChatColor.RED + "/cc other [player]");
                        return true;
                    }
					
                    Player target = Bukkit.getServer().getPlayer(args[1]);
                    
                    if (target == null) {
						if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
								MessageUtils.ConsoleMessages(msg);
							}
						}
	            		return true;
					}
                    
                    for (int i = 0; i < lines; i++) {
                        target.sendMessage(" ");
                    }
                    
                    String msg = ConfigMMsg.getConfig().getString("ClearChat.No-Reason");

					if (!args[1].isEmpty()) {
						msg = "";
						for (int i = 1; i < args.length; i++) {
							if (!Objects.equals(msg, "")) {
								msg = msg + " ";
							}
							msg = msg + args[i];
						}
					}

					for (String ms1: ConfigMMsg.getConfig().getStringList("ClearChat.Other.Sender")) {
                    	ConfigEventUtils.ExecuteEvent(target, ms1.replaceAll("%reason%", msg), "", "", false);
                    	MessageUtils.ConsoleMessages(ms1.replaceAll("%reason%", msg));
                    }
                    for (String ms2: ConfigMMsg.getConfig().getStringList("ClearChat.Other.Target")) {
                    	ConfigEventUtils.ExecuteEvent(target, ms2.replaceAll("%reason%", msg), "", "", false);
                    	MessageUtils.ConsoleMessages(ms2.replaceAll("%reason%", msg));
                    }
				}
			
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Enable")) {
			if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Disable-Message")) {
				if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
                    		if (!p.hasPermission("hawn.command.clearchat.anonymous")) {
                    			String Permission = "hawn.command.clearchat.anonymous";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    			return false;
                    		}
                    	}

                    		for (int i = 0; i < lines; i++) {
                    			ConfigEventUtils.ExecuteEventAllPlayers(" ", "", "", p, true);
	                        }
	                        
	                        String msg = ConfigMMsg.getConfig().getString("ClearChat.No-Reason");
	                        
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
	                        	for (String ms1: ConfigMMsg.getConfig().getStringList("ClearChat.Anonymously")) {
	                        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
	                        		ConfigEventUtils.ExecuteEventAllPlayers(ms1.replaceAll("%reason%", msg), "", "", p, true);
	    							MessageUtils.ConsoleMessages(ms1.replaceAll("%reason%", msg));
	                        	}
	                        }
                    	
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Anonymous.Disable-Message")) {
                    		if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            	}
                			}
                        }
                    }
                } else if (args[0].equalsIgnoreCase("c")) {
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Enable")) {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Use_Permission")) {
                    		if (!p.hasPermission("hawn.command.clearchat.normal")) {
                    			String Permission = "hawn.command.clearchat.normal";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    			return false;
                    		}
                    	}
                    		for (int i = 0; i < lines; i++) {
                    			ConfigEventUtils.ExecuteEventAllPlayers(" ", "", "", p, true);
	                        }
	                        
	                        String msg = ConfigMMsg.getConfig().getString("ClearChat.No-Reason");
	                        
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
	                        	for (String ms1: ConfigMMsg.getConfig().getStringList("ClearChat.Normal")) {
	                        		Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', ms1.replaceAll("%reason%", msg)));
	                        		ConfigEventUtils.ExecuteEventAllPlayers(ms1.replaceAll("%reason%", msg), "", "", p, true);
	    							MessageUtils.ConsoleMessages(ms1.replaceAll("%reason%", msg));
	                        	}
	                        }
                    	
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Normal.Disable-Message")) {
                    		if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            	}
                			}
                        }
                    }
                } else if (args[0].equalsIgnoreCase("o") || args[0].equalsIgnoreCase("own")) {
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Enable")) {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Use_Permission")) {
                    		if (!p.hasPermission("hawn.command.clearchat.own")) {
                    			String Permission = "hawn.command.clearchat.own";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    			return false;
                    		}
                    	}

                    	for (int i = 0; i < lines; i++) {
                    		p.sendMessage(" ");
                    	}
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Message-Clear")) {
                    		for (String ms1: ConfigMMsg.getConfig().getStringList("ClearChat.Own")) {
                    			ConfigEventUtils.ExecuteEvent(p, ms1, "", "", false);
                            	MessageUtils.ConsoleMessages(ms1);
                    		}
                    	}
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Own.Disable-Message")) {
                    		if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            	}
                			}
                        }
                    }
                } else if (args[0].equalsIgnoreCase("other")) {
                    if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Other.Enable")) {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Other.Use_Permission")) {
                    		if (!p.hasPermission("hawn.command.clearchat.other")) {
                    			String Permission = "hawn.command.clearchat.other";
                    			MessageUtils.MessageNoPermission(p, Permission);
                    			return false;
                    		}
                    	}

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
	                        
                    	String msg = ConfigMMsg.getConfig().getString("ClearChat.No-Reason");

	                    if (!args[1].isEmpty()) {
		                    msg = "";
		                    for (int i = 1; i < args.length; i++) {
			                    if (!Objects.equals(msg, "")) {
				                    msg = msg + " ";
			                    }
			                    msg = msg + args[i];
		                    }
	                    }

	                    for (String ms1: ConfigMMsg.getConfig().getStringList("ClearChat.Other.Sender")) {
                    		ConfigEventUtils.ExecuteEvent(target, ms1.replaceAll("%reason%", msg), "", "", false);
                        	MessageUtils.ConsoleMessages(ms1.replaceAll("%reason%", msg));
                    	}
                    	for (String ms2: ConfigMMsg.getConfig().getStringList("ClearChat.Other.Target")) {
                    		ConfigEventUtils.ExecuteEvent(target, ms2.replaceAll("%reason%", msg), "", "", false);
                        	MessageUtils.ConsoleMessages(ms2.replaceAll("%reason%", msg));
                    	}
                    	
                    } else {
                    	if (ClearChatCommandConfig.getConfig().getBoolean("ClearChat.Other.Disable-Message")) {
                    		if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            	}
                			}
                        }
                    }
                }
            
		
		return true;
	}
}
