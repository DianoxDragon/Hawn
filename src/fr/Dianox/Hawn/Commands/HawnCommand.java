package fr.Dianox.Hawn.Commands;

import java.io.File;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Commands.Features.FlyCommand;
import fr.Dianox.Hawn.Event.BasicFeatures;
import fr.Dianox.Hawn.Event.OnJoin;
import fr.Dianox.Hawn.Utility.CheckConfig;
import fr.Dianox.Hawn.Utility.EmojiesUtility;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.CommandAliasesConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.CustomCommandConfig;
import fr.Dianox.Hawn.Utility.Config.ScoreboardMainConfig;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ActionbarAnnouncerConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.BroadCastCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ClearChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ClearInvCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.DelayChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.EmojiCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.FlyCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.GamemodeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.HealCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.HelpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.MuteChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.OptionPlayerConfigCommand;
import fr.Dianox.Hawn.Utility.Config.Commands.PingCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ScoreboardCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.TitleAnnouncerConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.VanishCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WarningCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WarpSetWarpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WeatherTimeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGLP;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.FireworkListCUtility;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Events.ProtectionPlayerConfig;
import fr.Dianox.Hawn.Utility.Config.Events.CommandEventConfig;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGJoinQuitCommand;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGProtection;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OtherFeaturesConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerEventsConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerWorldChangeConfigE;
import fr.Dianox.Hawn.Utility.Config.Events.VoidTPConfig;
import fr.Dianox.Hawn.Utility.Config.Events.WorldEventConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMEvents;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMPlayerOption;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMProtection;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.AdminPanelConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.ErrorConfigAM;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.InfoServerOverviewC;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.OtherAMConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.SpawnMConfig;
import fr.Dianox.Hawn.Utility.Config.Tab.TablistConfig;
import fr.Dianox.Hawn.Utility.World.BasicEventsPW;
import fr.Dianox.Hawn.Utility.World.ChangeWorldPW;
import fr.Dianox.Hawn.Utility.World.CjiPW;
import fr.Dianox.Hawn.Utility.World.CommandsPW;
import fr.Dianox.Hawn.Utility.World.CosmeticsPW;
import fr.Dianox.Hawn.Utility.World.OnJoinPW;
import fr.Dianox.Hawn.Utility.World.OnQuitPW;
import fr.Dianox.Hawn.Utility.World.OtherFeaturesPW;
import fr.Dianox.Hawn.Utility.World.PlayerEventsPW;
import fr.Dianox.Hawn.Utility.World.ProtectionPW;
import fr.Dianox.Hawn.Utility.World.WorldPW;

public class HawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			if (label.equalsIgnoreCase("hawn")) {
				if ((args.length == 0) || (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?")))) {
					if (!(args.length == 2)) {
						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "hawn help 1");
					} else if (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?"))) {
						try {
							int i = Integer.parseInt(args[1]);
							
							if (OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + i)) {
								sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
								sender.sendMessage("");
								sender.sendMessage("     §l>> §e§o§lGlobal Help (Page "+i+")");
								sender.sendMessage("");
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Hawn-Main-help." + i)) {
									MessageUtils.ReplaceMessageForConsole(msg);
								}
								
								if (!OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									sender.sendMessage("");
									sender.sendMessage("   §l>> §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
									sender.sendMessage("");
									sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									sender.sendMessage("");
									sender.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
									sender.sendMessage("");
									sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (!OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									sender.sendMessage("");
									sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else {
									sender.sendMessage("");
									sender.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1)+" §7// §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
									sender.sendMessage("");
									sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								}
							} else {
								sender.sendMessage("§cerror");
							}
							
						} catch (NumberFormatException e) {
							if (ConfigMOStuff.getConfig().getBoolean("Error.Use-Number.Enable")) {
								for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Use-Number.Messages")) {
									MessageUtils.ReplaceMessageForConsole(msg);
								}
							}
						}
					} else {
						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "hawn help 1");
					}
				} else if (args[0].equalsIgnoreCase("delspawn")) {
					if (args.length == 1) {
						// If no argument has been put in the command
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					} else if (args.length == 2) {
						// If the warp does not exist
						if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[1]+".World")) {
							for (String msg: ErrorConfigAM.getConfig().getStringList("Error.No-Spawn")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
								
							return true;
						}
						
						// Execute the command
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".World", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".X", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Y", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Z", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Yaw", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Pitch", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Info", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1], null);
			                
						ConfigSpawn.saveConfigFile();
						
						for (String msg: ErrorConfigAM.getConfig().getStringList("Command.Del.Spawn-Delete")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%spawn%", args[1]));
						}
						
					} else {
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Delspawn")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
				} else if (args[0].equalsIgnoreCase("setspawn")) {
					for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Console.Not-A-Player")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				} else if (args[0].equalsIgnoreCase("about")) {
					sender.sendMessage("§8§l§m-----------------------------");
					sender.sendMessage("§7Plugin name:§c Hawn");
					sender.sendMessage("§7Author:§c Dianox");
					sender.sendMessage("§7Plugin version:§c " + Main.getVersion());
					sender.sendMessage("§7Website: §chttps://www.spigotmc.org/resources/hawn-hub-lobby-management.66907/");
					sender.sendMessage("§8§l§m-----------------------------");
				} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
					reloadconfig();
						
					for (String msg: OtherAMConfig.getConfig().getStringList("Command.Reload")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
					// Versions
				} else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("ver")) {
					for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Version")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
					// Server info
				} else if (args[0].equalsIgnoreCase("info")) {
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("complete") || args[1].equalsIgnoreCase("all")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.General")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} else if (args[1].equalsIgnoreCase("memory")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Memory")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} else if (args[1].equalsIgnoreCase("cpu") || args[1].equalsIgnoreCase("processor")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.CPU")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} else if (args[1].equalsIgnoreCase("disk") || args[1].equalsIgnoreCase("HDD") || args[1].equalsIgnoreCase("SDD")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Disk")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} else if (args[1].equalsIgnoreCase("tps")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Tps")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} else if (args[1].equalsIgnoreCase("server")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Server")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} else if (args[1].equalsIgnoreCase("version") || args[1].equalsIgnoreCase("v") || args[1].equalsIgnoreCase("ver")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Version")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} 
					} else {
						for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.General")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
				} else if (args[0].equalsIgnoreCase("tps")) {
					for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Tps")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				} else if (args[0].equalsIgnoreCase("hooks") || args[0].equalsIgnoreCase("hook")) {
					sender.sendMessage("");
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
			        	  sender.sendMessage("  §8→ §6§lPlaceholderAPI§8: §a§l✔");
			          } else {
			        	  sender.sendMessage("  §8→ §6§lPlaceholderAPI§8: §c§l✗");
			          }
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
			        	  sender.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §a§l✔");
			          } else {
			        	  sender.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §c§l✗");
			          }
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
			        	  sender.sendMessage("  §8→ §6§lWorldGuard§8: §a§l✔");
			          } else {
			        	  sender.sendMessage("  §8→ §6§lWorldGuard§8: §c§l✗");
			          }
			          sender.sendMessage("");
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
			        	  sender.sendMessage("  §8→ §6§lBattleLevels§8: §a§l✔");
			          } else {
			        	  sender.sendMessage("  §8→ §6§lBattleLevels§8: §c§l✗");
			          }
			          sender.sendMessage("");
				} else {
					for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Hawn")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				}
			}
			
			return true;
		}

		/*
		 * Executed by the player
		 */
		
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("hawn")) {
			if (p.hasPermission("hawn.admin") || p.hasPermission("hawn.admin.*")) {
				if ((args.length == 0) || (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?")))) {
					if (!(args.length == 2)) {
						p.performCommand("hawn help 1");
					} else if (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?"))) {
						try {
							int i = Integer.parseInt(args[1]);
							
							if (OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + i)) {
								p.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
								p.sendMessage("");
								p.sendMessage("     §l>> §e§o§lGlobal Help (Page "+i+")");
								p.sendMessage("");
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Hawn-Main-help." + i)) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
								
								if (!OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									p.sendMessage("");
									p.sendMessage("   §l>> §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
									p.sendMessage("");
									p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									p.sendMessage("");
									p.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
									p.sendMessage("");
									p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (!OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									p.sendMessage("");
									p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else {
									p.sendMessage("");
									p.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1)+" §7// §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
									p.sendMessage("");
									p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								}
							} else {
								p.sendMessage("§cerror");
							}
							
						} catch (NumberFormatException e) {
							MessageUtils.UseNumber(p);
						}
					} else {
						p.performCommand("hawn help 1");
					}
				} else if (args[0].equalsIgnoreCase("delspawn")) {
					if (args.length == 1) {
						// If no argument has been put in the command
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					} else if (args.length == 2) {
						// If the spawn does not exist
						if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[1]+".World")) {
							for (String msg: ErrorConfigAM.getConfig().getStringList("Error.No-Spawn")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
							
							return true;
						}
						
						// Execute the command
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".World", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".X", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Y", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Z", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Yaw", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Pitch", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1]+".Info", null);
						ConfigSpawn.getConfig().set("Coordinated."+args[1], null);
			                
						ConfigSpawn.saveConfigFile();
						
						for (String msg: ErrorConfigAM.getConfig().getStringList("Command.Del.Spawn-Delete")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawn%", args[1]), p);
						}
						
					} else {
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Delspawn")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
				} else if (args[0].equalsIgnoreCase("setspawn")) {
					if (args.length == 2) {
						if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[1])) {
							String spawnName = args[1];
							Location l = p.getLocation();
							
							SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			                
			                ConfigSpawn.saveConfigFile();
			                
			                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
			                
			                for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Other")) {
								MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
							}
			                
			                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
			                	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			                	OnJoinConfig.saveConfigFile();
			                }
						} else {
							for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Name-already-exist")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					} else {
						if (!ConfigSpawn.getConfig().isSet("Coordinated.Spawn1")) {
							String spawnName = "Spawn1";
							Location l = p.getLocation();
							
							SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			                
			                ConfigSpawn.saveConfigFile();
			                
			                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
			                
			                for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
								MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
							}
			                
			                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
			                	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			                	OnJoinConfig.saveConfigFile();
			                }
						} else {
			                Integer number = 1;
			                while (ConfigSpawn.getConfig().isSet("Coordinated.Spawn"+number)) {
			                	number++;
			                }
			                String spawnName = "Spawn"+number;
			                Location l = p.getLocation();
							
			                SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
			                
			                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
			                
			                for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
								MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
							}
			                
			                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
			                	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
			                	OnJoinConfig.saveConfigFile();
			                }
						}
					}
					return true;
				} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
					if (!p.hasPermission("hawn.admin.command.reload") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.reload");
						
						return true;
					}
					
					reloadconfig();
					
					for (String msg: OtherAMConfig.getConfig().getStringList("Command.Reload")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
					// Server info
				} else if (args[0].equalsIgnoreCase("hooks") || args[0].equalsIgnoreCase("hook")) {
					if (!p.hasPermission("hawn.admin.command.hooks") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
						
						return true;
					}
					
					p.sendMessage("");
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
			            p.sendMessage("  §8→ §6§lPlaceholderAPI§8: §a§l✔");
			          } else {
			            p.sendMessage("  §8→ §6§lPlaceholderAPI§8: §c§l✗");
			          }
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
			            p.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §a§l✔");
			          } else {
			            p.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §c§l✗");
			          }
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
			            p.sendMessage("  §8→ §6§lWorldGuard§8: §a§l✔");
			          } else {
			            p.sendMessage("  §8→ §6§lWorldGuard§8: §c§l✗");
			          }
			          p.sendMessage("");
			          if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
			        	  p.sendMessage("  §8→ §6§lBattleLevels§8: §a§l✔");
			          } else {
			        	  p.sendMessage("  §8→ §6§lBattleLevels§8: §c§l✗");
			          }
			          p.sendMessage("");
			          
				} else if (args[0].equalsIgnoreCase("info")) {
					if (!p.hasPermission("hawn.admin.command.info") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
						
						return true;
					}
					
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("complete") || args[1].equalsIgnoreCase("all")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.General")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						} else if (args[1].equalsIgnoreCase("memory")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Memory")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						} else if (args[1].equalsIgnoreCase("cpu") || args[1].equalsIgnoreCase("processor")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.CPU")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						} else if (args[1].equalsIgnoreCase("disk") || args[1].equalsIgnoreCase("HDD") || args[1].equalsIgnoreCase("SDD")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Disk")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						} else if (args[1].equalsIgnoreCase("tps")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Tps")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						} else if (args[1].equalsIgnoreCase("server")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Server")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						} else if (args[1].equalsIgnoreCase("version") || args[1].equalsIgnoreCase("v") || args[1].equalsIgnoreCase("ver")) {
							for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Version")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						} 
					} else {
						for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.General")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
					// Version
				} else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("ver")) {
					if (!p.hasPermission("hawn.admin.command.info") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
						
						return true;
					}
					
					for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Version")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				} else if (args[0].equalsIgnoreCase("about")) {
					p.sendMessage("§8§l§m-----------------------------");
					p.sendMessage("§7Plugin name:§c Hawn");
					p.sendMessage("§7Author:§c Dianox");
					p.sendMessage("§7Plugin version:§c " + Main.getVersion());
					p.sendMessage("§7Website: §chttps://www.spigotmc.org/resources/hawn-hub-lobby-management.66907/");
					p.sendMessage("§8§l§m-----------------------------");
				} else if (args[0].equalsIgnoreCase("tps")) {
					if (!p.hasPermission("hawn.admin.command.info") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
						
						return true;
					}
					
					for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Server-Info.Tps")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				} else if (args[0].equalsIgnoreCase("build")) {
					if (!p.hasPermission("hawn.admin.command.bypassbuild") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.bypassbuild");
						
						return true;
					}
					
					if (Main.buildbypasscommand.contains(p)) {
						Main.buildbypasscommand.remove(p);
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					} else {
						Main.buildbypasscommand.add(p);
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
				} else if (args[0].equalsIgnoreCase("debug")) {
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("emoji") || args[1].equalsIgnoreCase("emojis")) {
							Iterator < ? > iterator = OnChatConfig.getConfig().getConfigurationSection("Chat-Emoji-Player.Emojis-list").getKeys(false).iterator();
							
							while (iterator.hasNext()) {
			                    String string = (String) iterator.next();
			                    
			                    if (!string.equalsIgnoreCase("Option")) {
				                    try {
				                    	p.sendMessage(String.valueOf("§b"+string +"§7: §e"+XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material")).parseMaterial()));
				                    } catch (Exception e) {
										p.sendMessage("§b"+string +"§7: §cnull");
									}
			                    }
							}
						} else if (args[1].equalsIgnoreCase("t")) {
							p.sendMessage(String.valueOf(p.getLocation().getWorld().getPlayerCount()));
						}
					}
				} else {
					for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Hawn")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				}
			} else {
				MessageUtils.MessageNoPermission(p, "hawn.admin");
			}
		}
		
		return true;
	}
	
	public static void configlist() {
		ConfigSpawn.reloadConfig();
		ConfigGeneral.reloadConfig();
		ConfigMGeneral.reloadConfig();
		ConfigMEvents.reloadConfig();
		VoidTPConfig.reloadConfig();
		ConfigGProtection.reloadConfig();
		ConfigMProtection.reloadConfig();
		ProtectionPlayerConfig.reloadConfig();
		OtherFeaturesConfig.reloadConfig();
		WorldEventConfig.reloadConfig();
		ConfigMOStuff.reloadConfig();
		HelpCommandConfig.reloadConfig();
		PlayerEventsConfig.reloadConfig();
		ConfigGCos.reloadConfig();
		OnJoinConfig.reloadConfig();
		CommandEventConfig.reloadConfig();
		ConfigGLP.reloadConfig();
		ClearChatCommandConfig.reloadConfig();
		ConfigMCommands.reloadConfig();
		SpawnCommandConfig.reloadConfig();
		MuteChatCommandConfig.reloadConfig();
		PingCommandConfig.reloadConfig();
		DelayChatCommandConfig.reloadConfig();
		ServerListConfig.reloadConfig();
		ConfigGJoinQuitCommand.reloadConfig();
		BroadCastCommandConfig.reloadConfig();
		WeatherTimeCommandConfig.reloadConfig();
		FlyCommandConfig.reloadConfig();
		ConfigFDoubleJump.reloadConfig();
		OnChatConfig.reloadConfig();
		HealCommandConfig.reloadConfig();
		//NameTagConfig.reloadConfig();
		WarpSetWarpCommandConfig.reloadConfig();
		WarpListConfig.reloadConfig();
		CustomCommandConfig.reloadConfig();
		VanishCommandConfig.reloadConfig();
		TitleAnnouncerConfig.reloadConfig();
		ClearInvCommandConfig.reloadConfig();
		AutoBroadcastConfig.reloadConfig();
		EmojiCommandConfig.reloadConfig();
		ScoreboardMainConfig.reloadConfig();
		BetweenServersConfig.reloadConfig();
		PlayerWorldChangeConfigE.reloadConfig();
		ScoreboardCommandConfig.reloadConfig();
		GamemodeCommandConfig.reloadConfig();
		SpecialCjiHidePlayers.reloadConfig();
		ConfigMPlayerOption.reloadConfig();
		OptionPlayerConfigCommand.reloadConfig();
		ErrorConfigAM.reloadConfig();
		OtherAMConfig.reloadConfig();
		SpawnMConfig.reloadConfig();
		TablistConfig.reloadConfig();
		CommandAliasesConfig.reloadConfig();
		WarningCommandConfig.reloadConfig();
		AdminPanelConfig.reloadConfig();
		ActionbarAnnouncerConfig.reloadConfig();
		FireworkListCUtility.reloadConfig();
	}
	
	public void reloadconfig() {
		configlist();
		
		OnJoinPW.motd_world.clear();
		OnJoinPW.jm_world.clear();
		OnQuitPW.qm_world.clear();
		BasicEventsPW.voidtp_world.clear();
		BasicEventsPW.gm_world.clear();
		ProtectionPW.worlds_c_break.clear();
		ProtectionPW.worlds_c_place.clear();
		BasicEventsPW.kgm_world.clear();
		OnJoinPW.worlds_Food.clear();
		OnJoinPW.worlds_Health.clear();
		BasicEventsPW.worlds_kFood.clear();
		BasicEventsPW.worlds_kANTIDAMAGE.clear();
		ProtectionPW.worlds_HagingBreakByEntity.clear();
		ProtectionPW.worlds_PlayerInteractEntity_ItemFrame.clear();
		OtherFeaturesPW.worlds_ColorSign.clear();
		WorldPW.worlds_LightningStrike.clear();
		WorldPW.worlds_ThunderChange.clear();
		WorldPW.worlds_weather.clear();
		WorldPW.worlds_burn_block.clear();
		PlayerEventsPW.worlds_item_drop.clear();
		PlayerEventsPW.worlds_item_move.clear();
		PlayerEventsPW.worlds_item_pickup.clear();
		PlayerEventsPW.worlds_item_damageitem.clear();
		WorldPW.worlds_explosions.clear();
		WorldPW.worlds_LeaveDecay.clear();
		CosmeticsPW.worlds_firework.clear();
		WorldPW.worlds_spawning_mob_animals.clear();
		PlayerEventsPW.worlds_death_message.clear();
		PlayerEventsPW.worlds_respawn.clear();
		PlayerEventsPW.worlds_force_selected_slot.clear();
		WorldPW.worlds_BlockFade.clear();
		PlayerEventsPW.worlds_item_clearondrop.clear();
		OnJoinPW.worlds_inventory.clear();
		OnJoinPW.worlds_clear_chat.clear();
		OnJoinPW.worlds_XP_Exp.clear();
		OnJoinPW.worlds_XP_Lvl.clear();
		OnJoinPW.worlds_sounds_join.clear();
		CosmeticsPW.worlds_jumppads.clear();
		CommandsPW.worlds_JoinCommands_New.clear();
		CommandsPW.worlds_JoinCommands_No_New.clear();
		CommandsPW.worlds_QuitCommands_Console.clear();
		OnJoinPW.worlds_fly.clear();
		PlayerEventsPW.worlds_fun_doublejump.clear();
		OnJoinPW.worlds_join_title.clear();
		OnJoinPW.worlds_first_join_title.clear();
		OnJoinPW.world_speed_on_join.clear();
		OnJoinPW.worlds_first_join_ab.clear();
		OnJoinPW.worlds_join_ab.clear();
		BasicEventsPW.worlds_autobroadcast.clear();
		OnJoinPW.world_pe_blindness.clear();
		OnJoinPW.world_pe_jump.clear();
		ChangeWorldPW.world_change_keepfly.clear();
		ChangeWorldPW.worlds_GM_OnChangeWorld.clear();
		CjiPW.worlds_po_playervisibility_item.clear();
		PlayerEventsPW.worlds_playeroption_join.clear();
		BasicEventsPW.worlds_autobroadcast_ab.clear();
		BasicEventsPW.worlds_autobroadcast_title.clear();
		ChangeWorldPW.worlds_po.clear();
		CosmeticsPW.worlds_ls.clear();
		PlayerEventsPW.worlds_respawncji.clear();
		ProtectionPW.worlds_PlayerInteract_Items_Blocks.clear();
		Main.GetSetWorld();
		
		EmojiesUtility.setaliaseslist();
		
		Main.UpdateCheckReload();
		
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
			if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				Bukkit.getConsoleSender().sendMessage("| Please note that to remove the PlaceHolderAPI support, you must restart the server");
				Bukkit.getConsoleSender().sendMessage("| The plugin supports fast removal, but does not guarantee a return to normal with a hawn reload");
				ConfigGeneral.getConfig().set("Plugin.Use.PlaceholderAPI", false);
				ConfigGeneral.saveConfigFile();
			}
		}
				
		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Enable") && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.Enable")) {
	    	
	    	BasicFeatures.world_voidtp.clear();
	    	
	    	Iterator<?> iterator5 = VoidTPConfig.getConfig().getConfigurationSection("VoidTP.Options.VoidTP-Per-World.World-List").getKeys(false).iterator();
	    	
	    	while (iterator5.hasNext()) {
	    		String string = (String) iterator5.next();
	    		
	    		BasicFeatures.world_voidtp.add(string);
	    	}
	    }
		
		Main.injumpwithjumppad.clear();
		OnJoin.player_list.clear();
		
		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			OnJoin.player_list.add(p);
			
			if (FlyCommand.player_list_flyc.contains(p)) {
				if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable")) {
					FlyCommand.player_list_flyc.remove(p);
					p.setAllowFlight(false);
					p.setFlying(false);
				}
			}
			
			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
	        	if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
	        		if (PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
	        			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
	        				if (p.hasPermission("hawn.fun.doublejump.double")) {
	        					p.setAllowFlight(true);
	        				}
	        			} else {
	        				p.setAllowFlight(true);
	        			}
	        		}
	        	} else {
	        		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
	    				if (p.hasPermission("hawn.fun.doublejump.double")) {
	    					p.setAllowFlight(true);
	    				}
	    			} else {
	    				p.setAllowFlight(true);
	    			}
	        	} 	
	        }
		}
		
		if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Enable")) {
	    	
	    	Main.interactables.clear();
	    	
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_DOOR")) {
	    		Main.interactables.add(XMaterial.ACACIA_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.ACACIA_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ANVIL")) {
	    		Main.interactables.add(XMaterial.ANVIL.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BEACON")) {
	    		Main.interactables.add(XMaterial.BEACON.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.RED_BED")) {
	    		Main.interactables.add(XMaterial.RED_BED.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_DOOR")) {
	    		Main.interactables.add(XMaterial.BIRCH_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.BIRCH_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BOAT")) {
	    		Main.interactables.add(XMaterial.OAK_BOAT.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BREWING_STAND")) {
	    		Main.interactables.add(XMaterial.BREWING_STAND.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMMAND_BLOCK")) {
	    		Main.interactables.add(XMaterial.COMMAND_BLOCK.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST")) {
	    		Main.interactables.add(XMaterial.CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_DOOR")) {
	    		Main.interactables.add(XMaterial.DARK_OAK_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.DARK_OAK_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DAYLIGHT_DETECTOR")) {
	    		Main.interactables.add(XMaterial.DAYLIGHT_DETECTOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DISPENSER")) {
	    		Main.interactables.add(XMaterial.DISPENSER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DROPPER")) {
	    		Main.interactables.add(XMaterial.DROPPER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENCHANTING_TABLE")) {
	    		Main.interactables.add(XMaterial.ENCHANTING_TABLE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENDER_CHEST")) {
	    		Main.interactables.add(XMaterial.ENDER_CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.OAK_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.FURNACE")) {
	    		Main.interactables.add(XMaterial.FURNACE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER")) {
	    		Main.interactables.add(XMaterial.HOPPER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER_MINECART")) {
	    		Main.interactables.add(XMaterial.HOPPER_MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_DOOR")) {
	    		Main.interactables.add(XMaterial.JUNGLE_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.JUNGLE_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.LEVER")) {
	    		Main.interactables.add(XMaterial.LEVER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
	    		Main.interactables.add(XMaterial.MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.NOTE_BLOCK")) {
	    		Main.interactables.add(XMaterial.NOTE_BLOCK.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
	    		Main.interactables.add(XMaterial.MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMPARATOR")) {
	    		Main.interactables.add(XMaterial.COMPARATOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_SIGN")) {
	    		Main.interactables.add(XMaterial.OAK_SIGN.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST_MINECART")) {
	    		Main.interactables.add(XMaterial.CHEST_MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
	    		Main.interactables.add(XMaterial.OAK_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_TRAPDOOR")) {
	    		Main.interactables.add(XMaterial.OAK_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.TRAPPED_CHEST")) {
	    		Main.interactables.add(XMaterial.TRAPPED_CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BUTTON")) {
	    		Main.interactables.add(XMaterial.OAK_BUTTON.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
	    		Main.interactables.add(XMaterial.OAK_DOOR.parseMaterial());
	    	}
	    }
		
		if (TablistConfig.getConfig().getBoolean("Tablist.header.enabled")) {
	    	Main.getInstance().hea = String.valueOf(TablistConfig.getConfig().getStringList("Tablist.header.message"));
	    	
	    	Main.getInstance().hea = Main.getInstance().hea.substring(1, Main.getInstance().hea.length() - 1).replaceAll(", ", "\n");
	    	Main.getInstance().hea = Main.getInstance().hea.replaceAll("&", "§");
	    }
	    
	    if (TablistConfig.getConfig().getBoolean("Tablist.footer.enabled")) {
	    	Main.getInstance().foo = String.valueOf(TablistConfig.getConfig().getStringList("Tablist.footer.message"));
	    	
	    	Main.getInstance().foo = Main.getInstance().foo.substring(1, Main.getInstance().foo.length() - 1).replaceAll(", ", "\n");
	    	Main.getInstance().foo = Main.getInstance().foo.replaceAll("&", "§");
	    }
		
		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable") && OnJoinConfig.getConfig().getBoolean("Fly.Enable")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"Please note that if a player can both fly, or make a double jump");
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"It can cause problems");
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| ");
		}
		
		if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
			File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Scoreboard/");
			Main.getInstance().loadScoreboards(folder);
			
			for (Player p : Bukkit.getOnlinePlayers()) {
	            if (Main.getInstance().getBoards().containsKey(p)) {
	            	Main.getInstance().getBoards().get(p).remove();
	            	Main.getInstance().getBoards().remove(p);
	            }
	            Main.getInstance().createDefaultScoreboard(p);
			}
		}
		

		CheckConfig.warnhawnreload();
		
	}

}
