package fr.Dianox.Hawn.Commands.Features;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.VanishCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class VanishCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.vanish";
	public static List<Player> player_list_vanish = new ArrayList<Player>();
	
	public VanishCommand(String name) {
		super(name);
		this.description = "Vanish a player";
        this.usageMessage = "/vanish [msg]";
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (args.length == 1 && !args[0].equalsIgnoreCase("list")) {
				Player target = Bukkit.getServer().getPlayer(args[0]);
				
				if (target == null) {
					if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
						for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
	        		return true;
				}
				
				if (player_list_vanish.contains(target)) {
					for (Player all : Bukkit.getServer().getOnlinePlayers()) {
						if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
							all.showPlayer(Main.getInstance(), target);
						} else {
							all.showPlayer(target);
						}
					}
					player_list_vanish.remove(target);
					
					if (Main.TaskVanishAB.containsKey(target)) {
						Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
						Main.TaskVanishAB.remove(target);
					}
					
					if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Sender-Disabled.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Sender-Disabled.Messages")) {
							msg = msg.replaceAll("%player%", "console").replaceAll("%target%", target.getName());
		            		MessageUtils.ReplaceMessageForConsole(msg);
		            	}
					}
					
					if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Target-Disabled.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Target-Disabled.Messages")) {
		            		MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
		            	}
					}
				} else {
					for (Player all : Bukkit.getServer().getOnlinePlayers()) {
						if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
							all.hidePlayer(Main.getInstance(), target);
						} else {
							all.hidePlayer(target);
						}
					}
					player_list_vanish.add(target);
					
					if (Main.TaskVanishAB.containsKey(target)) {
						Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
						Main.TaskVanishAB.remove(target);
					}
					
					if (target.hasPermission("hawn.command.vanish.actionbar")) {
						BukkitTask task = new VanishTaskAB(target).runTaskTimer(Main.getInstance(), 100, 0);
						Main.TaskVanishAB.put(target, task.getTaskId());
					}
					
					if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Sender.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Sender.Messages")) {
							msg = msg.replaceAll("%player%", "console").replaceAll("%target%", target.getName());
		            		MessageUtils.ReplaceMessageForConsole(msg);
		            	}
					}
					
					if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Target.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Target.Messages")) {
		            		MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
		            	}
					}
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				Bukkit.getConsoleSender().sendMessage(player_list_vanish.toString());
			} else {
				Bukkit.getConsoleSender().sendMessage("§c/v <player> or /v list");
			}
			
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!VanishCommandConfig.getConfig().getBoolean("Vanish.Enable")) {
			if (VanishCommandConfig.getConfig().getBoolean("Vanish.Disable-Message")) {
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
						if (player_list_vanish.contains(p)) {
							for (Player all : Bukkit.getServer().getOnlinePlayers()) {
								if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
									all.showPlayer(Main.getInstance(), p);
								} else {
									all.showPlayer(p);
								}
							}
							player_list_vanish.remove(p);
							
							if (Main.TaskVanishAB.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
								Main.TaskVanishAB.remove(p);
							}
							
							if (ConfigMCommands.getConfig().getBoolean("Vanish.Self-Disabled.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Self-Disabled.Messages")) {
				            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
				            	}
							}
						} else {
							for (Player all : Bukkit.getServer().getOnlinePlayers()) {
								if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
									all.hidePlayer(Main.getInstance(), p);
								} else {
									all.hidePlayer(p);
								}
							}
							player_list_vanish.add(p);
							
							if (Main.TaskVanishAB.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
								Main.TaskVanishAB.remove(p);
							}
							
							if (p.hasPermission("hawn.command.vanish.actionbar")) {
								BukkitTask task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 100, 0);
								Main.TaskVanishAB.put(p, task.getTaskId());
							}
							
							if (ConfigMCommands.getConfig().getBoolean("Vanish.Self.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Self.Messages")) {
				            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
				            	}
							}
						}
					} else if (args.length == 1 && !args[0].equalsIgnoreCase("list")) {
						if (p.hasPermission("Hawn.command.vanish.others")) {
							Player target = Bukkit.getServer().getPlayer(args[0]);
							
							if (target == null) {
								MessageUtils.PlayerDoesntExist(p);
			            		return true;
							}
							
							if (player_list_vanish.contains(target)) {
								for (Player all : Bukkit.getServer().getOnlinePlayers()) {
									if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
										all.showPlayer(Main.getInstance(), target);
									} else {
										all.showPlayer(target);
									}
								}
								player_list_vanish.remove(target);
								
								if (Main.TaskVanishAB.containsKey(target)) {
									Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
									Main.TaskVanishAB.remove(target);
								}
								
								if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Sender-Disabled.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Sender-Disabled.Messages")) {
										msg = msg.replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName());
					            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
					            	}
								}
								
								if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Target-Disabled.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Target-Disabled.Messages")) {
										msg = msg.replaceAll("%player%", p.getName());
					            		MessageUtils.ReplaceCharMessagePlayer(msg, target);
					            	}
								}
							} else {
								for (Player all : Bukkit.getServer().getOnlinePlayers()) {
									if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
										all.hidePlayer(Main.getInstance(), target);
									} else {
										all.hidePlayer(target);
									}
								}
								player_list_vanish.add(target);
								
								if (Main.TaskVanishAB.containsKey(target)) {
									Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
									Main.TaskVanishAB.remove(target);
								}
								
								if (target.hasPermission("hawn.command.vanish.actionbar")) {
									BukkitTask task = new VanishTaskAB(target).runTaskTimer(Main.getInstance(), 100, 0);
									Main.TaskVanishAB.put(target, task.getTaskId());
								}
								
								if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Sender.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Sender.Messages")) {
										msg = msg.replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName());
					            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
					            	}
								}
								
								if (ConfigMCommands.getConfig().getBoolean("Vanish.Other-Target.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Other-Target.Messages")) {
										msg = msg.replaceAll("%player%", p.getName());
					            		MessageUtils.ReplaceCharMessagePlayer(msg, target);
					            	}
								}
							}
						
						} else {
							String Permission = "hawn.command.vanish.others";
							MessageUtils.MessageNoPermission(p, Permission);
						}
					} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
						p.sendMessage(player_list_vanish.toString());
					}
		
		return false;
	}

}
