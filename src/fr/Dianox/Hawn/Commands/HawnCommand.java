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
import fr.Dianox.Hawn.Utility.Config.PlayerConfig;
import fr.Dianox.Hawn.Utility.Config.ScoreboardMainConfig;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
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
import fr.Dianox.Hawn.Utility.Config.Commands.WarpSetWarpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WeatherTimeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGLP;
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
						onHelp(sender, 1);
					} else if (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?"))) {
						if (args[1].equalsIgnoreCase("1")) {
							onHelp(sender, 1);
						} else if (args[1].equalsIgnoreCase("2")) {
							onHelp(sender, 2);
						} else {
							onHelp(sender, 1);
						}
					} else {
						onHelp(sender, 1);
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
				} else {
					for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Hawn")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				}
			}
			
			return true;
		}

		// ////////////
		// SEPARATOR //
		// ////////////
		
		Player p = (Player) sender;
		
		if (label.equalsIgnoreCase("hawn")) {
			if (p.hasPermission("hawn.admin") || p.hasPermission("hawn.admin.*")) {
				if ((args.length == 0) || (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?")))) {
					if (!(args.length == 2)) {
						onHelp(p, 1);
					} else if (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?"))) {
						if (args[1].equalsIgnoreCase("1")) {
							onHelp(p, 1);
						} else if (args[1].equalsIgnoreCase("2")) {
							onHelp(p, 2);
						} else {
							onHelp(p, 1);
						}
					} else {
						onHelp(p, 1);
					}
				} else if (args[0].equalsIgnoreCase("delspawn")) {
					if (args.length == 1) {
						// If no argument has been put in the command
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					} else if (args.length == 2) {
						// If the warp does not exist
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
				} else if (args[0].equalsIgnoreCase("tps")) {
					if (!p.hasPermission("hawn.admin.command.info") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
						
						return true;
					}
					
					for (String msg: ConfigMOStuff.getConfig().getStringList("TPS.Normal")) {
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
	
	public void onHelp(CommandSender sender, Integer n) {
		if (n == 1) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
				sender.sendMessage("");
				sender.sendMessage("     §l>> §e§o§lGlobal Help (Page 1)");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §c/spawn [SpawnName] - §eSet the spawn");
				sender.sendMessage(" §8>> §7/spawn tp <player> [SpawnName] - §eSet the spawn");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/cc - §eShow the help of the clearchat");
				sender.sendMessage(" §8>> §7/ping [player] - §eShow the help of the clearchat");
				sender.sendMessage(" §8>> §7/delaychat <number> - §ePut a delay on the chat");
				sender.sendMessage(" §8>> §7/gmute - §eMute the chat");
				sender.sendMessage(" §8>> §c/broadcast <message>§7 - §eBroadcast a message");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §c/hawn setspawn [name]§7 - §eSet the spawn");
				sender.sendMessage("");
				sender.sendMessage("   §l>> §e§o§lPage 2 >> /hawn help 2");
				sender.sendMessage("");
				sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
			} else {
				sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
				sender.sendMessage("");
				sender.sendMessage("     §l>> §e§o§lGlobal Help (Page 1)");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/spawn [SpawnName] - §eSet the spawn");
				sender.sendMessage(" §8>> §7/spawn tp <player> [SpawnName] - §eSet the spawn");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/cc - §eShow the help of the clearchat");
				sender.sendMessage(" §8>> §7/ping [player] - §eShow the help of the clearchat");
				sender.sendMessage(" §8>> §7/delaychat <number> - §ePut a delay on the chat");
				sender.sendMessage(" §8>> §7/gmute - §eMute the chat");
				sender.sendMessage(" §8>> §c/broadcast <message>§7 - §eBroadcast a message");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/hawn setspawn [name] - §eSet the spawn");
				sender.sendMessage(" §8>> §7/hawn reload §eor §7rl - §eReload some config files");
				sender.sendMessage("");
				sender.sendMessage("   §l>> §e§o§lPage 2 >> /hawn help 2");
				sender.sendMessage("");
				sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
			}
		} else if (n == 2) {
			if(!(sender instanceof Player)) {
				sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
				sender.sendMessage("");
				sender.sendMessage("     §l>> §e§o§lGlobal Help (Page 2)");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/hawn reload §eor §7rl - §eReload some config files");
				sender.sendMessage(" §8>> §7/hawn version §eor §7v  - §eSee the version of the plugin");
				sender.sendMessage(" §8>> §7/hawn tps - §eSee the TPS of the server");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §c/sun or /clearw - §eClear the weather");
				sender.sendMessage(" §8>> §c/rain - §eTo make the world raining");
				sender.sendMessage(" §8>> §c/thunder - §eIf you like a bad weather");
				sender.sendMessage(" §8>> §c/day - §ePut the day");
				sender.sendMessage(" §8>> §c/night - §ePut the night");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §c/fly <player> - §eSet the fly mode");
				sender.sendMessage("");
				sender.sendMessage("   §l>> §e§o§l/hawn help 1 << Page 1");
				sender.sendMessage("");
				sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
			} else {
				sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
				sender.sendMessage("");
				sender.sendMessage("     §l>> §e§o§lGlobal Help (Page 2)");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/hawn reload §eor §7rl - §eReload some config files");
				sender.sendMessage(" §8>> §7/hawn version §eor §7v  - §eSee the version of the plugin");
				sender.sendMessage(" §8>> §7/hawn tps - §eSee the TPS of the server");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/sun or /clearw - §eClear the weather");
				sender.sendMessage(" §8>> §7/rain - §eTo make the world raining");
				sender.sendMessage(" §8>> §7/thunder - §eIf you like a bad weather");
				sender.sendMessage(" §8>> §7/day - §ePut the day");
				sender.sendMessage(" §8>> §7/night - §ePut the night");
				sender.sendMessage("");
				sender.sendMessage(" §8>> §7/fly [player] - §eSet the fly mode");
				sender.sendMessage("");
				sender.sendMessage("   §l>> §e§o§l/hawn help 1 << Page 1");
				sender.sendMessage("");
				sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
			}
		}
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
		PlayerConfig.reloadConfig();
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
		CommandsPW.worlds_JoinCommands_Console_New.clear();
		CommandsPW.worlds_JoinCommands_Console_No_New.clear();
		CommandsPW.worlds_JoinCommands_Player_New.clear();
		CommandsPW.worlds_JoinCommands_Player_No_New.clear();
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
		Main.GetSetWorld();
		
		EmojiesUtility.setaliaseslist();
		
		Main.UpdateCheckReload();
		
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
			if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				Bukkit.getConsoleSender().sendMessage("| Please note that to remove the PlaceHolderAPI support, you must restart the server");
				Bukkit.getConsoleSender().sendMessage("| The plugin supports fast removal, but does not guarantee a return to normal with a hawn reload");
				ConfigGeneral.getConfig().set("Plugin.Use.PlaceholderAPI", Boolean.valueOf(false));
				ConfigGeneral.saveConfigFile();
			}
		}
		
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