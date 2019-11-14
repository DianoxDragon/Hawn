package fr.dianox.hawn.commands;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.ConfigSpawn;
import fr.dianox.hawn.utility.config.ServerListConfig;
import fr.dianox.hawn.utility.config.events.OnChatConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.administration.ErrorConfigAM;
import fr.dianox.hawn.utility.config.messages.administration.InfoServerOverviewC;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;
import fr.dianox.hawn.utility.load.Reload;
import fr.dianox.hawn.utility.tasks.TaskNoClipCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class HawnCommand implements CommandExecutor {

	private List<String> fileList = new ArrayList<>();
	public static List<Player> slotview = new ArrayList<Player>();
	public static List<Player> noclip = new ArrayList<Player>();
	
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
				} else if (args[0].equalsIgnoreCase("urgent")) {
					if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
						
						ServerListConfig.getConfig().set("Urgent-mode.Enable", false);
						
						ServerListConfig.saveConfigFile();
						
						if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled")) {
							List<String> plugincheck = ServerListConfig.getConfig().getStringList("Urgent-mode.Plugin-desactivation.Plugin-Ignored");
							
							for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
								
								String check = plugin.getName();
								
								if (!plugincheck.contains(check)) {
									if (plugin != null && !plugin.isEnabled()) {
										Bukkit.getPluginManager().enablePlugin(plugin);
									}
								}
							}
							
							for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Back-To-Normal-For-All-Plugins")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						}
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Off")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Broadcast.Off")) {
							MessageUtils.ReplaceCharBroadcastNoPlayer(msg);
							MessageUtils.ReplaceMessageForConsole(msg);
						}
						
					} else {
						ServerListConfig.getConfig().set("Urgent-mode.Enable", true);
						
						ServerListConfig.saveConfigFile();
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.On")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
						
						List<String> whitelist = ServerListConfig.getConfig().getStringList("Urgent-mode.whitelist");
						
						for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
							if (!whitelist.contains(ps.getName())) {
								String message = ServerListConfig.getConfig().getString("Urgent-mode.Kick-Message");
								message = message.replaceAll("&", "§");
								message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
								
								ps.kickPlayer(message);
							}
						}
						
						Zip(false, null);
						
						if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled")) {
							List<String> plugincheck = ServerListConfig.getConfig().getStringList("Urgent-mode.Plugin-desactivation.Plugin-Ignored");
							
							for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
								
								String check = plugin.getName();
								
								if (!plugincheck.contains(check)) {
									if (plugin != null && plugin.isEnabled()) {
										Bukkit.getPluginManager().disablePlugin(plugin);
									}
								}
							}
							
							for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Disabled-Plugin-function")) {
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						}
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Broadcast.On")) {
							MessageUtils.ReplaceCharBroadcastNoPlayer(msg);
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
				} else if (args[0].equalsIgnoreCase("pholders") || args[0].equalsIgnoreCase("pholder")) {
					if (args.length == 1) {
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					} else if (args.length == 2) {
						String msg = PlaceHolders.ReplaceMainplaceholderC(args[1]);
						
						MessageUtils.ReplaceMessageForConsole(msg);
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
					Reload.reloadconfig();
						
					for (String msg: OtherAMConfig.getConfig().getStringList("Command.Reload")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
					// Versions
				} else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("ver")) {
					for (String msg: InfoServerOverviewC.getConfig().getStringList("Command.Version")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
					// Server info
				} else if (args[0].equalsIgnoreCase("donor") || args[0].equalsIgnoreCase("donors")) {
					sender.sendMessage("§8§l§m-----------------------------");
					sender.sendMessage("§7Adfrorg: §e25 € §cThank you a lot §4<3§c The first donor");
					sender.sendMessage("§7IgnaZ117: §e25 € §cThank you a lot §4<3§c The second donor OMG");
					sender.sendMessage("§8§l§m-----------------------------");
				} else if (args[0].equalsIgnoreCase("m") || args[0].equalsIgnoreCase("maintenance")) {
					if (ServerListConfig.getConfig().getBoolean("Maintenance.Enable")) {
						ServerListConfig.getConfig().set("Maintenance.Enable", false);
						
						ServerListConfig.saveConfigFile();
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.Off")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.Broadcast.Off")) {
							MessageUtils.ReplaceCharBroadcastNoPlayer(msg);
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					} else {
						ServerListConfig.getConfig().set("Maintenance.Enable", true);
						
						ServerListConfig.saveConfigFile();
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.On")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
						
						List<String> whitelist = ServerListConfig.getConfig().getStringList("Maintenance.whitelist");
						
						for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
							if (!whitelist.contains(ps.getName())) {
								String message = ServerListConfig.getConfig().getString("Maintenance.Kick-Message");
								message = message.replaceAll("&", "§");
								message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
								
								ps.kickPlayer(message);
							}
						}
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.Broadcast.On")) {
							MessageUtils.ReplaceCharBroadcastNoPlayer(msg);
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
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
					try {
						int i = Integer.parseInt(args[0]);
						
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
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Hawn")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
				}
			}
			
			return true;
		}

		/*
		 * ==============================
		 * 
		 * COMMAND EXECUTED ON THE SERVER
		 * 
		 * ==============================
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
				} else if (args[0].equalsIgnoreCase("pholders") || args[0].equalsIgnoreCase("pholder")) {
					if (args.length == 1) {
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					} else if (args.length == 2) {
						String msg = PlaceHolders.ReplaceMainplaceholderP(args[1], p);
						
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				} else if (args[0].equalsIgnoreCase("nv") || args[0].equalsIgnoreCase("nightvision")) {
					
					if (!p.hasPermission("hawn.admin.command.nightvision") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.nightvision");
						
						return true;
					}
					
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1999999999, 1));
					
					for (String msg: OtherAMConfig.getConfig().getStringList("Command.NightVision")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
					
				} else if (args[0].equalsIgnoreCase("noclip")) {
					
					if (!p.hasPermission("hawn.admin.command.noclip") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.noclip");
						
						return true;
					}
					
					if (noclip.contains(p)) {
						noclip.remove(p);
					} else {
						for (String msg: OtherAMConfig.getConfig().getStringList("Command.No-Clip.Enable")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
						
						noclip.add(p);
						
						@SuppressWarnings("unused")
						BukkitTask task = new TaskNoClipCommand(p).runTaskTimer(Main.getInstance(), 5, 2);
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
					
					//  SI LA COMMANDE EST NUL
					if (args.length == 1) {
						SetSpawnCommand.createcomplexspawn(p, true, false, "", false, "");
						
						return true;
					// SI ARGUMENT SUPERIEUR OU EGALE A 1
					} else if (args.length == 2) {
						if (args[1].startsWith("d:")) {
							SetSpawnCommand.createcomplexspawn(p, true, false, "", false, "");
						} else if (args[1].startsWith("w:")) {		
							SetSpawnCommand.createcomplexspawn(p, false, true, args[1], false, "");				
						} else {
							SetSpawnCommand.createcomplexspawn(p, false, false, "", true, args[1]);		
						}
					} else if (args.length == 3) {
						if (args[1].startsWith("d:")) {
							if (args[2].startsWith("w:")) {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[2], false, "");		
							} else {
								SetSpawnCommand.createcomplexspawn(p, true, false, "", true, args[2]);		
							}
						} else if (args[1].startsWith("w:")) {			
							if (args[2].startsWith("d:")) {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[1], false, "");
							} else {
								SetSpawnCommand.createcomplexspawn(p, false, true, args[1], true, args[2]);
							}
						} else {
							if (args[2].startsWith("d:")) {
								SetSpawnCommand.createcomplexspawn(p, true, false, "", true, args[1]);
							} else if (args[2].startsWith("w:")) {
								SetSpawnCommand.createcomplexspawn(p, false, true, args[2], true, args[1]);
							}
						}
					} else if (args.length == 4) {
						if (args[1].startsWith("d:")) {
							if (args[2].startsWith("w:")) {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[2], true, args[3]);		
							} else {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[3], true, args[2]);		
							}
						} else if (args[1].startsWith("w:")) {			
							if (args[2].startsWith("d:")) {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[1], true, args[3]);
							} else {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[1], true, args[2]);
							}
						} else {
							if (args[2].startsWith("d:")) {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[3], true, args[1]);
							} else if (args[2].startsWith("w:")) {
								SetSpawnCommand.createcomplexspawn(p, true, true, args[2], true, args[1]);
							}
						}
					} else {
						p.sendMessage("§c/hawn setspawn [spawn] [d:true] [w:world1,world2 etc.]");
					}
					
					return true;
				} else if (args[0].equalsIgnoreCase("urgent")) {
					
					if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Use-It-Only-On-The-Console")) {
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Hawn")) {
							MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
						}
						
						return true;
					}
					
					if (!p.hasPermission("hawn.admin.command.urgent") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.reload");
						
						return true;
					}
					
					List<String> whitelistuse = ServerListConfig.getConfig().getStringList("Urgent-mode.Can-Use-Urgent-Mode");
					
					if (!whitelistuse.contains(p.getName())) {
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Error-cant-use-the-command")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
						
						return true;
					}
					
					if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Error-Disable")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					} else {
						ServerListConfig.getConfig().set("Urgent-mode.Enable", true);
						
						ServerListConfig.saveConfigFile();
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.On")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
						
						List<String> whitelist = ServerListConfig.getConfig().getStringList("Urgent-mode.whitelist");
						
						for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
							if (!whitelist.contains(ps.getName())) {
								String message = ServerListConfig.getConfig().getString("Urgent-mode.Kick-Message");
								message = message.replaceAll("&", "§");
								message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
								
								ps.kickPlayer(message);
							}
						}
						
						Zip(true, p);
						
						if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled")) {
							List<String> plugincheck = ServerListConfig.getConfig().getStringList("Urgent-mode.Plugin-desactivation.Plugin-Ignored");
							
							for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
								
								String check = plugin.getName();
								
								if (!plugincheck.contains(check)) {
									if (plugin != null && plugin.isEnabled()) {
										Bukkit.getPluginManager().disablePlugin(plugin);
									}
								}
							}
							
							for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Disabled-Plugin-function")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						}
						
						for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Broadcast.On")) {
							MessageUtils.ReplaceCharBroadcastNoPlayer(msg);
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
					
				} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
					if (!p.hasPermission("hawn.admin.command.reload") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.reload");
						
						return true;
					}
					
					Reload.reloadconfig();
					
					for (String msg: OtherAMConfig.getConfig().getStringList("Command.Reload")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
					// Server info
				} else if (args[0].equalsIgnoreCase("slotview") || args[0].equalsIgnoreCase("sv")) {
					if (!p.hasPermission("hawn.admin.command.slotview") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.slotview");
						
						return true;
					}
					
					if (slotview.contains(p)) {
						slotview.remove(p);
						for (String msg: OtherAMConfig.getConfig().getStringList("Command.SlotView.Off")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					} else {
						slotview.add(p);
						for (String msg: OtherAMConfig.getConfig().getStringList("Command.SlotView.On")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
					
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
					if (!p.hasPermission("hawn.admin.command.info") && !p.hasPermission("hawn.admin.*")) {
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
					if (!p.hasPermission("hawn.admin.command.info") && !p.hasPermission("hawn.admin.*")) {
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
				} else if (args[0].equalsIgnoreCase("donor") || args[0].equalsIgnoreCase("donors")) {
					p.sendMessage("§8§l§m-----------------------------");
					p.sendMessage("§7Adfrorg: §e25 € §cThank you a lot §4<3§c The first donor");
					p.sendMessage("§7IgnaZ117: §e25 € §cThank you a lot §4<3§c The second donor OMG");
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
					if (!p.hasPermission("hawn.admin.command.bypassbuild") && !p.hasPermission("hawn.admin.*")) {
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
				} else if (args[0].equalsIgnoreCase("m") || args[0].equalsIgnoreCase("maintenance")) {
					if (p.hasPermission("hawn.admin.command.maintenance") || p.hasPermission("hawn.admin.*")) {
						if (ServerListConfig.getConfig().getBoolean("Maintenance.Enable")) {
							ServerListConfig.getConfig().set("Maintenance.Enable", false);
							
							ServerListConfig.saveConfigFile();
							
							for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.Off")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
							
							for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.Broadcast.Off")) {
								MessageUtils.ReplaceCharBroadcastNoPlayer(msg);
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						} else {
							ServerListConfig.getConfig().set("Maintenance.Enable", true);
							
							ServerListConfig.saveConfigFile();
							
							for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.On")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
							
							List<String> whitelist = ServerListConfig.getConfig().getStringList("Maintenance.whitelist");
							
							for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
								if (!whitelist.contains(ps.getName())) {
									String message = ServerListConfig.getConfig().getString("Maintenance.Kick-Message");
									message = message.replaceAll("&", "§");
									message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
									
									ps.kickPlayer(message);
								}
							}
							
							for (String msg: OtherAMConfig.getConfig().getStringList("Maintenance.Broadcast.On")) {
								MessageUtils.ReplaceCharBroadcastNoPlayer(msg);
								MessageUtils.ReplaceMessageForConsole(msg);
							}
						}
					} else {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.maintenance");
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
						}
					}
				} else {
					try {
						int i = Integer.parseInt(args[0]);
						
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
						for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Hawn")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
				}
			} else {
				MessageUtils.MessageNoPermission(p, "hawn.admin");
			}
		}
		
		return true;
	}
	
	private void Zip(boolean b, Player p) {
		
		String pathname = new File(".").getAbsolutePath();
		String zipFile = Main.getInstance().getDataFolder().getAbsolutePath() + "-save-1.zip";
		
		File directory = new File(pathname);
		getFileList(directory);
		
		File checkname = new File(zipFile);
		
		Integer number = 1;
		
        while (checkname.exists()) {
        	number++;
        	zipFile = Main.getInstance().getDataFolder().getAbsolutePath() + "-save-"+ number +".zip";
        	checkname = new File(zipFile);
        }
		
		try (FileOutputStream fos = new FileOutputStream(zipFile); ZipOutputStream zos = new ZipOutputStream(fos)) {
			
			for (String filePath : fileList) {
				System.out.println("Compressing: " + filePath);
				
				// Creates a zip entry.
                String name = filePath.substring(
                    directory.getAbsolutePath().length() + 1,
                    filePath.length());
                
                ZipEntry zipEntry = new ZipEntry(name);
                zos.putNextEntry(zipEntry);
                
                // Read file content and write to zip output stream.
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    // Close the zip entry.
                    zos.closeEntry();
                } catch (IOException e) {
                	e.printStackTrace();
				}
			}
			
			System.out.println("Compression done ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fileList.clear();
		
		for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Zip")) {
			if (b) {
				MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
			}
			MessageUtils.ReplaceMessageForConsole(msg);
		}
	}
	
	/**
     * Get files list from the directory recursive to the sub directory.
     */
    private void getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                	if (file.getAbsolutePath().contains("\\.\\cache\\")) continue;
                	if (file.getAbsolutePath().contains("\\.\\dumps\\")) continue;
                	
                	fileList.add(file.getAbsolutePath());
                } else {
                    getFileList(file);
                }
            }
        }
    }

}