package fr.Dianox.Hawn.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Commands.TaskSpawns.TaskSpawnDelayOtherTp;
import fr.Dianox.Hawn.Commands.TaskSpawns.TaskSpawnDelayOtherTp2;
import fr.Dianox.Hawn.Commands.TaskSpawns.TaskSpawnDelaySelfTp;
import fr.Dianox.Hawn.Commands.TaskSpawns.TaskSpawnDelaySelfTpSpawn;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;

public class SpawnCommand extends BukkitCommand {
	
	public static Integer emojis_total_enabled = 0;
	
	public SpawnCommand(String name) {
		super(name);
		this.description = "Teleports player to the spawn";
        this.usageMessage = "/spawn or /spawn tp <player> [spawnName]";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			
				if (args.length >= 1 && args.length <= 3 && args[0].equalsIgnoreCase("tp")) {
					if (args.length >= 2 && args.length <= 3) {
						Player target = Bukkit.getServer().getPlayer(args[1]);
						
						if (target == null) {
							if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
								for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
									MessageUtils.ReplaceMessageForConsole(msg);
								}
							}
		            		return true;
						}
						
						if (args.length == 2) {
							if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.CustomSpawn.Enable")) {
								if (SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
									String Lineerror = "Commands.CustomSpawn.Spawn";
									String Fileerror = "Events/OnJoin.yml";
									if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
										for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
											MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror));
										}
									}
								} else {
									if (!ConfigSpawn.getConfig().isSet("Coordinated."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
										if (ConfigMOStuff.getConfig().getBoolean("Error.No-Spawn.Enable")) {
											for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Spawn.Messages")) {
												MessageUtils.ReplaceMessageForConsole(msg);
											}
										}
										return true;
									}
									SpawnUtils.teleportToSpawn(target, SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"));
									for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
										MessageUtils.ReplaceMessageForConsole(msg);
									}
									if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
										for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
											MessageUtils.ReplaceCharMessagePlayer(msg, target);
										}
									}
								}
							} else {
								if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
									String Lineerror = "Spawn.DefaultSpawn";
									String Fileerror = "Events/OnJoin.yml";
									if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
										for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
											MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror));
										}
									}
								} else {
									if (!ConfigSpawn.getConfig().isSet("Coordinated."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
										if (ConfigMOStuff.getConfig().getBoolean("Error.No-Spawn.Enable")) {
											for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Spawn.Messages")) {
												MessageUtils.ReplaceMessageForConsole(msg);
											}
										}
										return true;
									}
									SpawnUtils.teleportToSpawn(target, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
									for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
										MessageUtils.ReplaceMessageForConsole(msg);
									}
									if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
										for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
											MessageUtils.ReplaceCharMessagePlayer(msg, target);
										}
									}
								}
							}
						} else if (args.length == 3) {
							if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[2])) {
								if (ConfigMOStuff.getConfig().getBoolean("Error.No-Spawn.Enable")) {
									for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Spawn.Messages")) {
										MessageUtils.ReplaceMessageForConsole(msg);
									}
								}
								return true;
							}
							SpawnUtils.teleportToSpawn(target, args[2]);
							for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
							if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
								for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						}
						
					} else {
						sender.sendMessage("§c/spawn tp <player> [spawnname]");
					}
				} else {
					sender.sendMessage("§cYou are not a player");
				}
			
			return true;
		}
		
		Player p = (Player) sender;
		
			if (args.length == 0) {
				if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Use-Permission")) {
					if (p.hasPermission("hawn.command.spawn")) {
						if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Self.Enable")) {
							if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Self.Bypass-Delay")) {
								if (p.hasPermission("hawn.command.spawn.other.bypassdelay")) {
									tpsapwnselfonlytpself(p);
								} else {
									
									if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Self-Delay.Enable")) {
										for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Self-Delay.Messages")) {
											MessageUtils.ReplaceCharMessagePlayer(msg
													.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds"))), p);
										}
									}
									
									if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
										Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
										Main.player_spawnwarpdelay.remove(p.getUniqueId());
									}
									
									Main.inspawnd.remove(p);
									Main.inwarpd.remove(p);
									
									BukkitTask task = new TaskSpawnDelaySelfTp(p).runTaskLater(Main.getInstance(), 
											SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds") * 20);
									
									Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
									Main.inspawnd.add(p);
								}
							} else {
								if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Self-Delay.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Self-Delay.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg
												.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds"))), p);
									}
								}
								
								if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
									Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
									Main.player_spawnwarpdelay.remove(p.getUniqueId());
								}
								
								Main.inspawnd.remove(p);
								Main.inwarpd.remove(p);
								
								BukkitTask task = new TaskSpawnDelaySelfTp(p).runTaskLater(Main.getInstance(), 
										SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds") * 20);
								
								Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
								Main.inspawnd.add(p);
							}
						} else {
							tpsapwnselfonlytpself(p);
						}
					} else {
						String Permission = "hawn.command.spawn";
						MessageUtils.MessageNoPermission(p, Permission);
					}
				} else {
					if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Self.Enable")) {
						if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Self.Bypass-Delay")) {
							if (p.hasPermission("hawn.command.spawn.other.bypassdelay")) {
								tpsapwnselfonlytpself(p);
							} else {
								if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Self-Delay.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Self-Delay.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg
												.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds"))), p);
									}
								}
								
								if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
									Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
									Main.player_spawnwarpdelay.remove(p.getUniqueId());
								}
								
								Main.inspawnd.remove(p);
								Main.inwarpd.remove(p);
								
								BukkitTask task = new TaskSpawnDelaySelfTp(p).runTaskLater(Main.getInstance(), 
										SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds") * 20);
								
								Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
								Main.inspawnd.add(p);
							}
						} else {
							if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Self-Delay.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Self-Delay.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg
											.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds"))), p);
								}
							}
							
							if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
								Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
								Main.player_spawnwarpdelay.remove(p.getUniqueId());
							}
							
							Main.inspawnd.remove(p);
							Main.inwarpd.remove(p);
							
							BukkitTask task = new TaskSpawnDelaySelfTp(p).runTaskLater(Main.getInstance(), 
									SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds") * 20);
							
							Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
							Main.inspawnd.add(p);
						}
					} else {
						tpsapwnselfonlytpself(p);
					}
				}
			} else if (!args[0].contentEquals("tp") && args.length == 1) {
				if (args.length == 1) {
					if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Self.Enable")) {
						if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Self.Bypass-Delay")) {
							if (p.hasPermission("hawn.command.spawn.other.bypassdelay")) {
								tpsapwnselfonlytp(p, args[0]);
							} else {
								if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Self-Delay.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Self-Delay.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg
												.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds"))), p);
									}
								}
								
								if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
									Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
									Main.player_spawnwarpdelay.remove(p.getUniqueId());
								}
								
								Main.inspawnd.remove(p);
								Main.inwarpd.remove(p);
								
								BukkitTask task = new TaskSpawnDelaySelfTpSpawn(p, args[0]).runTaskLater(Main.getInstance(), 
										SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds") * 20);
								
								Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
								Main.inspawnd.add(p);
							}
						} else {
							if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Self-Delay.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Self-Delay.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg
											.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds"))), p);
								}
							}
							
							if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
								Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
								Main.player_spawnwarpdelay.remove(p.getUniqueId());
							}
							
							Main.inspawnd.remove(p);
							Main.inwarpd.remove(p);
							
							BukkitTask task = new TaskSpawnDelaySelfTpSpawn(p, args[0]).runTaskLater(Main.getInstance(), 
									SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Self.Delay-Seconds") * 20);
							
							Main.player_spawnwarpdelay.put(p.getUniqueId(), task.getTaskId());
							Main.inspawnd.add(p);
						}
					} else {
						tpsapwnselfonlytp(p, args[0]);
					}
				}
			} else if (args.length >= 1 && args.length <= 3 && args[0].equalsIgnoreCase("tp") && p.hasPermission("hawn.command.spawn.teleportothers")) {
				if (args.length >= 2 && args.length <= 3) {
					Player target = Bukkit.getServer().getPlayer(args[1]);
					
					if (target == null) {
						MessageUtils.PlayerDoesntExist(p);
	            		return true;
					}
					
					if (args.length == 2) {
						if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Other.Enable")) {
							if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Other.Bypass-Delay")) {
								if (p.hasPermission("hawn.command.spawn.other.bypassdelay")) {
									tpother(target, p);
								} else {
									if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Other-Sender-Delay.Enable")) {
										for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Other-Sender-Delay.Messages")) {
											MessageUtils.ReplaceCharMessagePlayer(msg
													.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds"))), p);
										}
									}
									
									if (Main.player_spawnwarpdelay.containsKey(target.getUniqueId())) {
										Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(target.getUniqueId()));
										Main.player_spawnwarpdelay.remove(target.getUniqueId());
									}
									
									Main.inspawnd.remove(target);
									Main.inwarpd.remove(target);
									
									BukkitTask task = new TaskSpawnDelayOtherTp(p, args[0], target).runTaskLater(Main.getInstance(), 
											SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds") * 20);
									
									Main.player_spawnwarpdelay.put(target.getUniqueId(), task.getTaskId());
									Main.inspawnd.add(target);
								}
							} else {
								if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Other-Sender-Delay.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Other-Sender-Delay.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg
												.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds"))), p);
									}
								}
								
								if (Main.player_spawnwarpdelay.containsKey(target.getUniqueId())) {
									Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(target.getUniqueId()));
									Main.player_spawnwarpdelay.remove(target.getUniqueId());
								}
								
								Main.inspawnd.remove(target);
								Main.inwarpd.remove(target);
								
								BukkitTask task = new TaskSpawnDelayOtherTp(p, args[0], target).runTaskLater(Main.getInstance(), 
										SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds") * 20);
								
								Main.player_spawnwarpdelay.put(target.getUniqueId(), task.getTaskId());
								Main.inspawnd.add(target);
							}
						} else {
							tpother(target, p);
						}
					} else if (args.length == 3) {
						if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Other.Enable")) {
							if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Other.Bypass-Delay")) {
								if (p.hasPermission("hawn.command.spawn.other.bypassdelay")) {
									tpother2(target, p, args[2]);
								} else {
									if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Other-Sender-Delay.Enable")) {
										for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Other-Sender-Delay.Messages")) {
											MessageUtils.ReplaceCharMessagePlayer(msg
													.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds"))), p);
										}
									}
									
									if (Main.player_spawnwarpdelay.containsKey(target.getUniqueId())) {
										Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(target.getUniqueId()));
										Main.player_spawnwarpdelay.remove(target.getUniqueId());
									}
									
									Main.inspawnd.remove(target);
									Main.inwarpd.remove(target);
									
									BukkitTask task = new TaskSpawnDelayOtherTp2(p, args[2], target).runTaskLater(Main.getInstance(), 
											SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds") * 20);
									
									Main.player_spawnwarpdelay.put(target.getUniqueId(), task.getTaskId());
									Main.inspawnd.add(target);
								}
							} else {
								if (ConfigMCommands.getConfig().getBoolean("Spawn.Tp.Other-Sender-Delay.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Spawn.Tp.Other-Sender-Delay.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg
												.replaceAll("%second%", String.valueOf(SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds"))), p);
									}
								}
								
								if (Main.player_spawnwarpdelay.containsKey(target.getUniqueId())) {
									Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(target.getUniqueId()));
									Main.player_spawnwarpdelay.remove(target.getUniqueId());
								}
								
								Main.inspawnd.remove(target);
								Main.inwarpd.remove(target);
								
								BukkitTask task = new TaskSpawnDelayOtherTp2(p, args[2], target).runTaskLater(Main.getInstance(), 
										SpawnCommandConfig.getConfig().getInt("Commands.Spawn.Delay.Other.Delay-Seconds") * 20);
								
								Main.player_spawnwarpdelay.put(target.getUniqueId(), task.getTaskId());
								Main.inspawnd.add(target);
							}
						} else {
							tpother2(target, p, args[2]);
						}
					}
					
				} else {
					p.sendMessage("§c/spawn tp <player> [spawnname]");
				}
			} else {
				p.sendMessage("§c/spawn <spawnname>");
				if (p.hasPermission("hawn.command.spawn.teleportothers")) {
					p.sendMessage("§c/spawn tp <player> [spawnname]");
				}
			}
		
		return true;
	}
	
	private void tpsapwnselfonlytpself(Player p) {
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.CustomSpawn.Enable")) {
			if (SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
				String Lineerror = "Commands.CustomSpawn.Spawn";
				String Fileerror = "Events/OnJoin.yml";
				if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
					for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
					}
				}
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				if (!p.hasPermission("hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(p, SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"));
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
		} else {
			if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
				String Lineerror = "Spawn.DefaultSpawn";
				String Fileerror = "Events/OnJoin.yml";
				if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
					for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
					}
				}
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					MessageUtils.MessageNoSpawn(p);
					return;
				}
				if (!p.hasPermission("hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					String Permission = "hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
		}
	}
	
	private void tpsapwnselfonlytp(Player sender, String tp) {
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Use-Permission")) {
			if (sender.hasPermission("hawn.command.spawn")) {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
					MessageUtils.MessageNoSpawn(sender);
					return;
				}
				if (!sender.hasPermission("hawn.command.spawn."+tp)) {
					String Permission = "hawn.command.spawn."+tp;
					MessageUtils.MessageNoPermission(sender, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(sender, tp);
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, sender);
					}
				}
			} else {
				String Permission = "hawn.command.spawn";
				MessageUtils.MessageNoPermission(sender, Permission);
			}
		} else {
			if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
				MessageUtils.MessageNoSpawn(sender);
				return;
			}
			if (!sender.hasPermission("hawn.command.spawn."+tp)) {
				String Permission = "hawn.command.spawn."+tp;
				MessageUtils.MessageNoPermission(sender, Permission);
				return;
			}
			SpawnUtils.teleportToSpawn(sender, tp);
			if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, sender);
				}
			}
		}
	}
	
	private void tpother2(Player other, Player sender, String tp) {
		if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
			MessageUtils.MessageNoSpawn(sender);
			return;
		}
		if (!sender.hasPermission("hawn.command.spawn."+tp)) {
			String Permission = "hawn.command.spawn."+tp;
			MessageUtils.MessageNoPermission(sender, Permission);
			return;
		}
		SpawnUtils.teleportToSpawn(other, tp);
		for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
			MessageUtils.ReplaceCharMessagePlayer(msg, sender);
		}
		if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
			for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
				MessageUtils.ReplaceCharMessagePlayer(msg, other);
			}
		}
	}

	private void tpother(Player other, Player sender) {
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.CustomSpawn.Enable")) {
			if (SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
				String Lineerror = "Commands.Spawn.CustomSpawn.Spawn";
				String Fileerror = "Events/OnJoin.yml";
				if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
					for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), sender);
					}
				}
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					MessageUtils.MessageNoSpawn(sender);
					return;
				}
				if (!sender.hasPermission("hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn");
					MessageUtils.MessageNoPermission(sender, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(other, SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"));
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, sender);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, other);
					}
				}
			}
		} else {
			if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
				String Lineerror = "Spawn.DefaultSpawn";
				String Fileerror = "Events/OnJoin.yml";
				if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
					for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), sender);
					}
				}
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					MessageUtils.MessageNoSpawn(sender);
					return;
				}
				if (!sender.hasPermission("hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					String Permission = "hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
					MessageUtils.MessageNoPermission(sender, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(sender, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, sender);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, other);
					}
				}
			}
		}
	}

}
