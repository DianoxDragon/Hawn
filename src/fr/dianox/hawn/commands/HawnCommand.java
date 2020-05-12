package fr.dianox.hawn.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.EditPlayerGui;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.ConfigSpawn;
import fr.dianox.hawn.utility.config.commands.HawnCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.load.Reload;
import fr.dianox.hawn.utility.tasks.TaskNoClipCommand;

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
							
							if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + i)) {
								sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
								sender.sendMessage("");
								sender.sendMessage("     §l>> §e§o§lGlobal Help (Page "+i+")");
								sender.sendMessage("");
								
								for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Hawn-Main-help." + i)) {
									MessageUtils.ConsoleMessages(msg);
								}
								
								if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									sender.sendMessage("");
									sender.sendMessage("   §l>> §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
									sender.sendMessage("");
									sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									sender.sendMessage("");
									sender.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
									sender.sendMessage("");
									sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
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
							if (ConfigMMsg.getConfig().getBoolean("Error.Use-Number.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("Error.Use-Number.Messages")) {
									MessageUtils.ConsoleMessages(msg);
								}
							}
						}
					} else {
						Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "hawn help 1");
					}
				} else if (args[0].equalsIgnoreCase("urgent")) {
					if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
						
						HawnCommandConfig.getConfig().set("Urgent-mode.Enable", false);
						
						HawnCommandConfig.saveConfigFile();
						
						if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled")) {
							List<String> plugincheck = HawnCommandConfig.getConfig().getStringList("Urgent-mode.Plugin-desactivation.Plugin-Ignored");
							
							for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
								
								String check = plugin.getName();
								
								if (!plugincheck.contains(check)) {
									if (plugin != null && !plugin.isEnabled()) {
										Bukkit.getPluginManager().enablePlugin(plugin);
									}
								}
							}
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Back-To-Normal-For-All-Plugins")) {
								MessageUtils.ConsoleMessages(msg);
							}
						}
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Off")) {
							MessageUtils.ConsoleMessages(msg);
						}
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Broadcast.Off")) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
							MessageUtils.ConsoleMessages(msg);
						}
						
					} else {
						HawnCommandConfig.getConfig().set("Urgent-mode.Enable", true);
						
						HawnCommandConfig.saveConfigFile();
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.On")) {
							MessageUtils.ConsoleMessages(msg);
						}
						
						List<String> whitelist = HawnCommandConfig.getConfig().getStringList("Urgent-mode.whitelist");
						
						for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
							if (!whitelist.contains(ps.getName())) {
								String message = HawnCommandConfig.getConfig().getString("Urgent-mode.Kick-Message");
								message = message.replaceAll("&", "§");
								message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
								
								ps.kickPlayer(message);
							}
						}
						
						Zip(false, null);
						
						if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled")) {
							List<String> plugincheck = HawnCommandConfig.getConfig().getStringList("Urgent-mode.Plugin-desactivation.Plugin-Ignored");
							
							for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
								
								String check = plugin.getName();
								
								if (!plugincheck.contains(check)) {
									if (plugin != null && plugin.isEnabled()) {
										Bukkit.getPluginManager().disablePlugin(plugin);
									}
								}
							}
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Disabled-Plugin-function")) {
								MessageUtils.ConsoleMessages(msg);
							}
						}
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Broadcast.On")) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
							MessageUtils.ConsoleMessages(msg);
						}
					}
				} else if (args[0].equalsIgnoreCase("pholders") || args[0].equalsIgnoreCase("pholder")) {
					if (args.length == 1) {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ConsoleMessages(msg);
						}
					} else if (args.length == 2) {
						String msg = PlaceHolders.ReplaceMainplaceholderC(args[1]);
						
						MessageUtils.ConsoleMessages(msg);
					}
				} else if (args[0].equalsIgnoreCase("delspawn")) {
					if (args.length == 1) {
						// If no argument has been put in the command
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ConsoleMessages(msg);
						}
					} else if (args.length == 2) {
						// If the warp does not exist
						if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[1]+".World")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Error.No-Spawn")) {
								MessageUtils.ConsoleMessages(msg);
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
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Del.Spawn-Delete")) {
							MessageUtils.ConsoleMessages(msg.replaceAll("%spawn%", args[1]));
						}
						
					} else {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Delspawn")) {
							MessageUtils.ConsoleMessages(msg);
						}
					}
				} else if (args[0].equalsIgnoreCase("setspawn")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Console.Not-A-Player")) {
						MessageUtils.ConsoleMessages(msg);
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
						
					for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Reload")) {
						MessageUtils.ConsoleMessages(msg);
					}
					// Versions
				} else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("ver")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Version")) {
						MessageUtils.ConsoleMessages(msg);
					}
					// Server info
				} else if (args[0].equalsIgnoreCase("donor") || args[0].equalsIgnoreCase("donors")) {
					sender.sendMessage("§8§l§m-----------------------------");
					sender.sendMessage("§7Adfrorg: §e25 € §cThank you a lot §4<3§c The first donor");
					sender.sendMessage("§7IgnaZ117: §e25 € §cThank you a lot §4<3§c The second donor OMG");
					sender.sendMessage("§8§l§m-----------------------------");
				} else if (args[0].equalsIgnoreCase("m") || args[0].equalsIgnoreCase("maintenance")) {
					if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable")) {
						HawnCommandConfig.getConfig().set("Maintenance.Enable", false);
						
						HawnCommandConfig.saveConfigFile();
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Off")) {
							MessageUtils.ConsoleMessages(msg);
						}
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Broadcast.Off")) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
							MessageUtils.ConsoleMessages(msg);
						}
					} else {
						HawnCommandConfig.getConfig().set("Maintenance.Enable", true);
						
						HawnCommandConfig.saveConfigFile();
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.On")) {
							MessageUtils.ConsoleMessages(msg);
						}
						
						List<String> whitelist = HawnCommandConfig.getConfig().getStringList("Maintenance.whitelist");
						
						for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
							if (!whitelist.contains(ps.getName())) {
								String message = HawnCommandConfig.getConfig().getString("Maintenance.Kick-Message");
								message = message.replaceAll("&", "§");
								message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
								
								ps.kickPlayer(message);
							}
						}
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Broadcast.On")) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
							MessageUtils.ConsoleMessages(msg);
						}
					}
				} else if (args[0].equalsIgnoreCase("info")) {
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("complete") || args[1].equalsIgnoreCase("all")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.General")) {
								MessageUtils.ConsoleMessages(msg);
							}
						} else if (args[1].equalsIgnoreCase("memory")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Memory")) {
								MessageUtils.ConsoleMessages(msg);
							}
						} else if (args[1].equalsIgnoreCase("cpu") || args[1].equalsIgnoreCase("processor")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.CPU")) {
								MessageUtils.ConsoleMessages(msg);
							}
						} else if (args[1].equalsIgnoreCase("disk") || args[1].equalsIgnoreCase("HDD") || args[1].equalsIgnoreCase("SDD")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Disk")) {
								MessageUtils.ConsoleMessages(msg);
							}
						} else if (args[1].equalsIgnoreCase("tps")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Tps")) {
								MessageUtils.ConsoleMessages(msg);
							}
						} else if (args[1].equalsIgnoreCase("server")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Server")) {
								MessageUtils.ConsoleMessages(msg);
							}
						} else if (args[1].equalsIgnoreCase("version") || args[1].equalsIgnoreCase("v") || args[1].equalsIgnoreCase("ver")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Version")) {
								MessageUtils.ConsoleMessages(msg);
							}
						} 
					} else {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.General")) {
							MessageUtils.ConsoleMessages(msg);
						}
					}
				} else if (args[0].equalsIgnoreCase("tps")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Tps")) {
						MessageUtils.ConsoleMessages(msg);
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
						
						if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + i)) {
							sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
							sender.sendMessage("");
							sender.sendMessage("     §l>> §e§o§lGlobal Help (Page "+i+")");
							sender.sendMessage("");
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Hawn-Main-help." + i)) {
								MessageUtils.ConsoleMessages(msg);
							}
							
							if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
								sender.sendMessage("");
								sender.sendMessage("   §l>> §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
								sender.sendMessage("");
								sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
							} else if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
								sender.sendMessage("");
								sender.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
								sender.sendMessage("");
								sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
							} else if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
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
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Hawn")) {
							MessageUtils.ConsoleMessages(msg);
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
							
							if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + i)) {
								p.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
								p.sendMessage("");
								p.sendMessage("     §l>> §e§o§lGlobal Help (Page "+i+")");
								p.sendMessage("");
								
								for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Hawn-Main-help." + i)) {
									ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
								}
								
								if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									p.sendMessage("");
									p.sendMessage("   §l>> §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
									p.sendMessage("");
									p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
									p.sendMessage("");
									p.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
									p.sendMessage("");
									p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
								} else if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
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
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					} else if (args.length == 2) {
						String msg = PlaceHolders.ReplaceMainplaceholderP(args[1], p);
						
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				} else if (args[0].equalsIgnoreCase("nv") || args[0].equalsIgnoreCase("nightvision")) {
					
					if (!p.hasPermission("hawn.admin.command.nightvision") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.nightvision");
						
						return true;
					}
					
					p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1999999999, 1));
					
					for (String msg: ConfigMAdmin.getConfig().getStringList("Command.NightVision")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				} else if (args[0].equalsIgnoreCase("noclip")) {
					
					if (!p.hasPermission("hawn.admin.command.noclip") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.noclip");
						
						return true;
					}
					
					if (noclip.contains(p)) {
						noclip.remove(p);
					} else {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.No-Clip.Enable")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
						
						noclip.add(p);
						
						@SuppressWarnings("unused")
						BukkitTask task = new TaskNoClipCommand(p).runTaskTimer(Main.getInstance(), 5, 2);
					}
				} else if (args[0].equalsIgnoreCase("delspawn")) {
					if (args.length == 1) {
						// If no argument has been put in the command
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					} else if (args.length == 2) {
						// If the spawn does not exist
						if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[1]+".World")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Error.No-Spawn")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Del.Spawn-Delete")) {
							ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%spawn%", args[1]), "", "", false);
						}
						
					} else {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Delspawn")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
					
					if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Use-It-Only-On-The-Console")) {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Hawn")) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
							MessageUtils.ConsoleMessages(msg);
						}
						
						return true;
					}
					
					if (!p.hasPermission("hawn.admin.command.urgent") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.reload");
						
						return true;
					}
					
					List<String> whitelistuse = HawnCommandConfig.getConfig().getStringList("Urgent-mode.Can-Use-Urgent-Mode");
					
					if (!whitelistuse.contains(p.getName())) {
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Error-cant-use-the-command")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
						
						return true;
					}
					
					if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Error-Disable")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					} else {
						HawnCommandConfig.getConfig().set("Urgent-mode.Enable", true);
						
						HawnCommandConfig.saveConfigFile();
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.On")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
						
						List<String> whitelist = HawnCommandConfig.getConfig().getStringList("Urgent-mode.whitelist");
						
						for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
							if (!whitelist.contains(ps.getName())) {
								String message = HawnCommandConfig.getConfig().getString("Urgent-mode.Kick-Message");
								message = message.replaceAll("&", "§");
								message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
								
								ps.kickPlayer(message);
							}
						}
						
						Zip(true, p);
						
						if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled")) {
							List<String> plugincheck = HawnCommandConfig.getConfig().getStringList("Urgent-mode.Plugin-desactivation.Plugin-Ignored");
							
							for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
								
								String check = plugin.getName();
								
								if (!plugincheck.contains(check)) {
									if (plugin != null && plugin.isEnabled()) {
										Bukkit.getPluginManager().disablePlugin(plugin);
									}
								}
							}
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Disabled-Plugin-function")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
								MessageUtils.ConsoleMessages(msg);
							}
						}
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Broadcast.On")) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
							MessageUtils.ConsoleMessages(msg);
						}
					}
					
				} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
					if (!p.hasPermission("hawn.admin.command.reload") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.reload");
						
						return true;
					}
					
					Reload.reloadconfig();
					
					for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Reload")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
					// Server info
				} else if (args[0].equalsIgnoreCase("slotview") || args[0].equalsIgnoreCase("sv")) {
					if (!p.hasPermission("hawn.admin.command.slotview") || !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.slotview");
						
						return true;
					}
					
					if (slotview.contains(p)) {
						slotview.remove(p);
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.SlotView.Off")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					} else {
						slotview.add(p);
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.SlotView.On")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					}
					
				} else if (args[0].equalsIgnoreCase("editplayer")) {
					
					if (!p.hasPermission("hawn.editplayer") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.editplayer");
						
						return true;
					}
					
					Player target = Bukkit.getServer().getPlayer(args[1]);

					if (target == null) {
						MessageUtils.PlayerDoesntExist(p);
						return true;
					}
					
					EditPlayerGui.OpenGui(target);
					
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
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.General")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						} else if (args[1].equalsIgnoreCase("memory")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Memory")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						} else if (args[1].equalsIgnoreCase("cpu") || args[1].equalsIgnoreCase("processor")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.CPU")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						} else if (args[1].equalsIgnoreCase("disk") || args[1].equalsIgnoreCase("HDD") || args[1].equalsIgnoreCase("SDD")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Disk")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						} else if (args[1].equalsIgnoreCase("tps")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Tps")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						} else if (args[1].equalsIgnoreCase("server")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Server")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						} else if (args[1].equalsIgnoreCase("version") || args[1].equalsIgnoreCase("v") || args[1].equalsIgnoreCase("ver")) {
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Version")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						} 
					} else {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.General")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					}
					// Version
				} else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("ver")) {
					if (!p.hasPermission("hawn.admin.command.info") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
						
						return true;
					}
					
					for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Version")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
					
					for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Tps")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				} else if (args[0].equalsIgnoreCase("glow")) {
					// TODO
				} else if (args[0].equalsIgnoreCase("build")) {
					if (!p.hasPermission("hawn.admin.command.bypassbuild") && !p.hasPermission("hawn.admin.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.bypassbuild");
						
						return true;
					}
					
					if (Main.buildbypasscommand.contains(p)) {
						Main.buildbypasscommand.remove(p);
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Build-Bypass.Off")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					} else {
						Main.buildbypasscommand.add(p);
						
						for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Build-Bypass.On")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
						}
					}
				} else if (args[0].equalsIgnoreCase("m") || args[0].equalsIgnoreCase("maintenance")) {
					if (p.hasPermission("hawn.admin.command.maintenance") || p.hasPermission("hawn.admin.*")) {
						if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable")) {
							HawnCommandConfig.getConfig().set("Maintenance.Enable", false);
							
							HawnCommandConfig.saveConfigFile();
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Off")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Broadcast.Off")) {
								ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
								MessageUtils.ConsoleMessages(msg);
							}
						} else {
							HawnCommandConfig.getConfig().set("Maintenance.Enable", true);
							
							HawnCommandConfig.saveConfigFile();
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.On")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
							
							List<String> whitelist = HawnCommandConfig.getConfig().getStringList("Maintenance.whitelist");
							
							for (Player ps: Bukkit.getServer().getOnlinePlayers()) {
								if (!whitelist.contains(ps.getName())) {
									String message = HawnCommandConfig.getConfig().getString("Maintenance.Kick-Message");
									message = message.replaceAll("&", "§");
									message = PlaceHolders.ReplaceMainplaceholderP(message, ps);
									
									ps.kickPlayer(message);
								}
							}
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Broadcast.On")) {
								ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
								MessageUtils.ConsoleMessages(msg);
							}
						}
					} else {
						MessageUtils.MessageNoPermission(p, "hawn.admin.command.maintenance");
					}
				/*} else if (args[0].equalsIgnoreCase("debug")) {
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("emoji") || args[1].equalsIgnoreCase("emojis")) {
							Iterator < ? > iterator = OnChatConfig.getConfig().getConfigurationSection("Chat-Emoji-Player.Emojis-list").getKeys(false).iterator();
							
							while (iterator.hasNext()) {
			                    String string = (String) iterator.next();
			                    
			                    if (!string.equalsIgnoreCase("Option")) {
				                    try {
				                    	String newmat = String.valueOf(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material")));
				                    	p.sendMessage(String.valueOf("§b"+string +"§7: §e" + newmat));
				                    } catch (Exception e) {
										p.sendMessage("§b"+string +"§7: §cnull");
									}
			                    }
							}
						}
					}*/
				} else {
					try {
						int i = Integer.parseInt(args[0]);
						
						if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + i)) {
							p.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
							p.sendMessage("");
							p.sendMessage("     §l>> §e§o§lGlobal Help (Page "+i+")");
							p.sendMessage("");
							
							for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Hawn-Main-help." + i)) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
							
							if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
								p.sendMessage("");
								p.sendMessage("   §l>> §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
								p.sendMessage("");
								p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
							} else if (ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
								p.sendMessage("");
								p.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
								p.sendMessage("");
								p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
							} else if (!ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i - 1)) && !ConfigMAdmin.getConfig().isSet("Command.Hawn-Main-help." + (i + 1))) {
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
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Hawn")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
		
		for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Zip")) {
			if (b) {
				ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
				MessageUtils.ConsoleMessages(msg);
			}
			MessageUtils.ConsoleMessages(msg);
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