package fr.dianox.hawn.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.commands.tasks.VanishTaskAB;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.VanishCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.messages.ConfigMAdmin;

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
					if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
							MessageUtils.ConsoleMessages(msg);
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
					
					for (Player all : Bukkit.getServer().getOnlinePlayers()) {
						if (all.hasPermission("hawn.staff.seevanished")) {
							for (String s: ConfigMAdmin.getConfig().getStringList("Vanish.Vanish-Off-Others")) {
								ConfigEventUtils.ExecuteEvent(all, s.replaceAll("%target%", target.getName()).replaceAll("%player%", "console"), "", "", false);
							}
						}
					}
					
					player_list_vanish.remove(target);
					
					if (Main.TaskVanishAB.containsKey(target)) {
						Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
						Main.TaskVanishAB.remove(target);
					}
					
					if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Sender-Disabled.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Sender-Disabled.Messages")) {
							msg = msg.replaceAll("%player%", "console").replaceAll("%target%", target.getName());
		            		MessageUtils.ConsoleMessages(msg);
		            	}
					}
					
					if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Target-Disabled.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Target-Disabled.Messages")) {
		            		ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
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
					
					for (Player all : Bukkit.getServer().getOnlinePlayers()) {
						if (all.hasPermission("hawn.staff.seevanished")) {			
							for (String s: ConfigMAdmin.getConfig().getStringList("Vanish.Vanish-On.Messages")) {
								ConfigEventUtils.ExecuteEvent(all, s.replaceAll("%target%", target.getName()).replaceAll("%player%", "console"), "", "", false);
							}
						}
					}
					
					player_list_vanish.add(target);
					
					if (Main.TaskVanishAB.containsKey(target)) {
						Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
						Main.TaskVanishAB.remove(target);
					}
					
					if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar-If-Vanished")) {
						if (target.hasPermission("hawn.command.vanish.actionbar")) {
							BukkitTask task = null;
							if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar.Message-blinking")) {
								task = new VanishTaskAB(target).runTaskTimer(Main.getInstance(), 20, 100);
							} else {
								task = new VanishTaskAB(target).runTaskTimer(Main.getInstance(), 20, 20);
							}
							Main.TaskVanishAB.put(target, task.getTaskId());
						}
					}
					
					if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Sender.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Sender.Messages")) {
							msg = msg.replaceAll("%player%", "console").replaceAll("%target%", target.getName());
		            		MessageUtils.ConsoleMessages(msg);
		            	}
					}
					
					if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Target.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Target.Messages")) {
		            		ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
		            	}
					}
				}
			} else if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
				Bukkit.getConsoleSender().sendMessage(player_list_vanish.toString());
			} else {
				Bukkit.getConsoleSender().sendMessage("Â§c/v <player> or /v list");
			}
			
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!VanishCommandConfig.getConfig().getBoolean("Vanish.Enable")) {
			if (VanishCommandConfig.getConfig().getBoolean("Vanish.Disable-Message")) {
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
							
							for (Player all : Bukkit.getServer().getOnlinePlayers()) {
								if (all.hasPermission("hawn.staff.seevanished")) {
									if (ConfigMAdmin.getConfig().getBoolean("Vanish.Vanish-Off.Enable")) {
										for (String s: ConfigMAdmin.getConfig().getStringList("Vanish.Vanish-Off.Messages")) {
											ConfigEventUtils.ExecuteEvent(all, s.replaceAll("%player%", p.getName()), "", "", false);
										}
		                        	}
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean("Vanish.Self-Disabled.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Self-Disabled.Messages")) {
				            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
							
							for (Player all : Bukkit.getServer().getOnlinePlayers()) {
								if (all.hasPermission("hawn.staff.seevanished")) {
									if (ConfigMAdmin.getConfig().getBoolean("Vanish.Vanish-On.Enable")) {
										for (String s: ConfigMAdmin.getConfig().getStringList("Vanish.Vanish-On.Messages")) {
											ConfigEventUtils.ExecuteEvent(all, s.replaceAll("%player%", p.getName()), "", "", false);
										}
									}
								}
							}
							
							if (Main.TaskVanishAB.containsKey(p)) {
								Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
								Main.TaskVanishAB.remove(p);
							}
							
							if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar-If-Vanished")) {
								if (p.hasPermission("hawn.command.vanish.actionbar")) {
									BukkitTask task = null;
									if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar.Message-blinking")) {
										task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 100);
									} else {
										task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 20);
									}
									Main.TaskVanishAB.put(p, task.getTaskId());
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean("Vanish.Self.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Self.Messages")) {
				            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
								
								for (Player all : Bukkit.getServer().getOnlinePlayers()) {
									if (all.hasPermission("hawn.staff.seevanished")) {
										for (String s: ConfigMAdmin.getConfig().getStringList("Vanish.Vanish-Off-Others")) {
											ConfigEventUtils.ExecuteEvent(all, s.replaceAll("%target%", target.getName()).replaceAll("%player%", p.getName()), "", "", false);
										}
									}
								}
								
								if (Main.TaskVanishAB.containsKey(target)) {
									Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
									Main.TaskVanishAB.remove(target);
								}
								
								if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Sender-Disabled.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Sender-Disabled.Messages")) {
										msg = msg.replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName());
					            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					            	}
								}
								
								if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Target-Disabled.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Target-Disabled.Messages")) {
										msg = msg.replaceAll("%player%", p.getName());
					            		ConfigEventUtils.ExecuteEvent(target, msg, "", "", false);
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
								
								for (Player all : Bukkit.getServer().getOnlinePlayers()) {
									if (all.hasPermission("hawn.staff.seevanished")) {
										for (String s: ConfigMAdmin.getConfig().getStringList("Vanish.Vanish-On-Others")) {
											ConfigEventUtils.ExecuteEvent(all, s.replaceAll("%target%", target.getName()).replaceAll("%player%", p.getName()), "", "", false);
										}
									}
								}
								
								if (Main.TaskVanishAB.containsKey(target)) {
									Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(target));
									Main.TaskVanishAB.remove(target);
								}
								
								if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar-If-Vanished")) {
									if (target.hasPermission("hawn.command.vanish.actionbar")) {
										BukkitTask task = null;
										if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar.Message-blinking")) {
											task = new VanishTaskAB(target).runTaskTimer(Main.getInstance(), 20, 100);
										} else {
											task = new VanishTaskAB(target).runTaskTimer(Main.getInstance(), 20, 20);
										}
										Main.TaskVanishAB.put(target, task.getTaskId());
									}
								}
								
								if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Sender.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Sender.Messages")) {
										msg = msg.replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName());
					            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					            	}
								}
								
								if (ConfigMMsg.getConfig().getBoolean("Vanish.Other-Target.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Vanish.Other-Target.Messages")) {
										msg = msg.replaceAll("%player%", p.getName());
					            		ConfigEventUtils.ExecuteEvent(target, msg, "", "", false);
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
