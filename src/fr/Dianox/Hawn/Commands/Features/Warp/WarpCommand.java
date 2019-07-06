package fr.Dianox.Hawn.Commands.Features.Warp;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Commands.Features.Warp.Task.TaskOnTPWarp;
import fr.Dianox.Hawn.Commands.Features.Warp.Task.TaskOnTpOthersWarp;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WarpSetWarpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class WarpCommand extends BukkitCommand {

	String GeneralPermission = "hawn.command.warp";
	String perm_bypassdelya_self = "hawn.command.warp.bypassdelay.self";
	
	String perm_tp_other = "hawn.command.warp.others";
	String perm_bypassdelya_other = "hawn.command.warp.bypassdelay.other";
	
	public WarpCommand(String name) {
		super(name);
		this.description = "Warp to the specified location";
		this.usageMessage = "/warp <warp> [player]";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {

		// >>> Executed by the console
		if (! (sender instanceof Player)) {
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
			return true;
		}

		// >>> Executed by the player
		Player p = (Player) sender;

		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Enable")) {
			if (WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Disable-Message")) {
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
			// If no argument has been put in the command
			if (p.hasPermission(WarpListCommand.GeneralPermission)) {
				p.performCommand("warplist");
			} else {
				if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
					for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
		} else if (args.length == 1) {
			if (p.hasPermission("hawn.warp." + args[0])) {
				if (WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Delay.Self.Enable")) {
					if (WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Delay.Self.Bypass-Delay")) {
						if (p.hasPermission(perm_bypassdelya_self)) {
							onTp(p, args[0]);
						} else {
							if (!WarpListConfig.getConfig().isSet("Coordinated." + args[0] + ".World")) {
								if (ConfigMCommands.getConfig().getBoolean("Warp.No-Warp.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Warp.No-Warp.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}

								return true;
							}

							if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Self-Delay.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Self-Delay.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", args[0]).replaceAll("%second%", String.valueOf(WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Self.Delay-Seconds"))), p);
								}
							}
							
							if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
								Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
								Main.player_spawnwarpdelay.remove(p.getUniqueId());
							}
							
							Main.inspawnd.remove(p);
							Main.inwarpd.remove(p);
							
							BukkitTask task = new TaskOnTPWarp(p, args[0]).runTaskLater(Main.getInstance(), 
									WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Self.Delay-Seconds") * 20);
							
							Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
							Main.inwarpd.add(p);
							
						}
					} else {
						if (!WarpListConfig.getConfig().isSet("Coordinated." + args[0] + ".World")) {
							if (ConfigMCommands.getConfig().getBoolean("Warp.No-Warp.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Warp.No-Warp.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}

							return true;
						}

						if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Self-Delay.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Self-Delay.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", args[0]).replaceAll("%second%", String.valueOf(WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Self.Delay-Seconds"))), p);
							}
						}
						
						if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
							Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
							Main.player_spawnwarpdelay.remove(p.getUniqueId());
						}
						
						Main.inspawnd.remove(p);
						Main.inwarpd.remove(p);
						
						BukkitTask task = new TaskOnTPWarp(p, args[0]).runTaskLater(Main.getInstance(), 
								WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Self.Delay-Seconds") * 20);
						
						Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
						Main.inwarpd.add(p);
					}
				} else {
					onTp(p, args[0]);
				}
			} else {
				String Permission = String.valueOf("hawn.warp." + args[0]);
				MessageUtils.MessageNoPermission(p, Permission);
			}
		} else if (args.length == 2) {

			Player target = Bukkit.getServer().getPlayer(args[1]);

			if (target == null) {
				MessageUtils.PlayerDoesntExist(p);
				return true;
			}

			if (p.hasPermission(perm_tp_other)) {
				if (p.hasPermission("hawn.warp." + args[0])) {

					if (WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Delay.Other.Enable")) {
						if (WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Delay.Other.Bypass-Delay")) {
							if (p.hasPermission(perm_bypassdelya_other)) {
								onTpOthers(p, target, args[0]);
							} else {
								if (!WarpListConfig.getConfig().isSet("Coordinated." + args[0] + ".World")) {
									if (ConfigMCommands.getConfig().getBoolean("Warp.No-Warp.Enable")) {
										for (String msg: ConfigMCommands.getConfig().getStringList("Warp.No-Warp.Messages")) {
											MessageUtils.ReplaceCharMessagePlayer(msg, p);
										}
									}

									return true;
								}

								if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Other-Sender-Delay.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Other-Sender-Delay.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", args[0]).replaceAll("%target%", target.getName()).replaceAll("%second%", String.valueOf(WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Other.Delay-Seconds"))), p);
									}
								}

								if (Main.player_spawnwarpdelay.containsKey(target.getUniqueId())) {
									Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(target.getUniqueId()));
									Main.player_spawnwarpdelay.remove(target.getUniqueId());
								}
								
								Main.inspawnd.remove(target);
								Main.inwarpd.remove(target);
								
								BukkitTask task = new TaskOnTpOthersWarp(p, target, args[0]).runTaskLater(Main.getInstance(), 
										WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Other.Delay-Seconds") * 20);
								
								Main.player_spawnwarpdelay.put(target.getUniqueId(), task.getTaskId());
								Main.inwarpd.add(target);
							}
						} else {
							if (!WarpListConfig.getConfig().isSet("Coordinated." + args[0] + ".World")) {
								if (ConfigMCommands.getConfig().getBoolean("Warp.No-Warp.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Warp.No-Warp.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}

								return true;
							}

							if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Other-Sender-Delay.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Other-Sender-Delay.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", args[0]).replaceAll("%target%", target.getName()).replaceAll("%second%", String.valueOf(WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Other.Delay-Seconds"))), p);
								}
							}

							if (Main.player_spawnwarpdelay.containsKey(target.getUniqueId())) {
								Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(target.getUniqueId()));
								Main.player_spawnwarpdelay.remove(target.getUniqueId());
							}
							
							Main.inspawnd.remove(target);
							Main.inwarpd.remove(target);
							
							BukkitTask task = new TaskOnTpOthersWarp(p, target, args[0]).runTaskLater(Main.getInstance(), 
									WarpSetWarpCommandConfig.getConfig().getInt("Warp.Delay.Other.Delay-Seconds") * 20);
							
							Main.player_spawnwarpdelay.put(target.getUniqueId(), task.getTaskId());
							Main.inwarpd.add(target);
						}
					} else {
						onTpOthers(p, target, args[0]);
					}

				} else {
					String Permission = String.valueOf("hawn.warp." + args[0]);
					MessageUtils.MessageNoPermission(p, Permission);
				}
			} else {
				MessageUtils.MessageNoPermission(p, perm_tp_other);
			}

		} else {
			p.sendMessage("§c/warp <warp>");
		}

		return false;
	}

	public static void onTp(Player sender, String tp) {
		try {
			org.bukkit.World w = org.bukkit.Bukkit.getServer().getWorld(WarpListConfig.getConfig().getString("Coordinated." + tp + ".World"));
			double x = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".X");
			double y = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Y");
			double z = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Z");
			float yaw = WarpListConfig.getConfig().getInt("Coordinated." + tp + ".Yaw");
			float pitch = WarpListConfig.getConfig().getInt("Coordinated." + tp + ".Pitch");

			sender.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));

			if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Self.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Self.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", tp), sender);
				}
			}

		} catch(Exception e) {
			if (ConfigMCommands.getConfig().getBoolean("Warp.No-Warp.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.No-Warp.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, sender);
				}
			}
		}
	}

	private void onTpOthers(Player sender, Player other, String tp) {
		try {
			org.bukkit.World w = org.bukkit.Bukkit.getServer().getWorld(WarpListConfig.getConfig().getString("Coordinated." + tp + ".World"));
			double x = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".X");
			double y = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Y");
			double z = WarpListConfig.getConfig().getDouble("Coordinated." + tp + ".Z");
			float yaw = WarpListConfig.getConfig().getInt("Coordinated." + tp + ".Yaw");
			float pitch = WarpListConfig.getConfig().getInt("Coordinated." + tp + ".Pitch");

			other.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));

			if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Other-Sender.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Other-Sender.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", tp).replaceAll("%target%", other.getName()), sender);
				}
			}

			if (ConfigMCommands.getConfig().getBoolean("Warp.Tp.Other.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.Tp.Other.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", tp).replaceAll("%player%", sender.getName()), other);
				}
			}

		} catch(Exception e) {
			if (ConfigMCommands.getConfig().getBoolean("Warp.No-Warp.Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList("Warp.No-Warp.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, sender);
				}
			}
		}
	}

}
