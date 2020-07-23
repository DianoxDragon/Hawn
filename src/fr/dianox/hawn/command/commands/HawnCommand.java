package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.EditPlayerGui;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.commands.HawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class HawnCommand implements CommandExecutor {

	private final List<String> fileList = new ArrayList<>();
	public static List<Player> slotview = new ArrayList<>();
	public static List<Player> noclip = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			// If not a player
			if ((args.length == 0) || (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?")))) {
				if (args.length != 2) {
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "hawn help 1");
				} else if (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?"))) {
					try {
						int i = Integer.parseInt(args[1]);

						sender.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
						sender.sendMessage("");
						sender.sendMessage("     §l>> §e§o§lGlobal Help (Page " + i + ")");
						sender.sendMessage("");

						for (String msg: helpPage(i)) {
							MessageUtils.ConsoleMessages(msg);
						}

						if (i == 1 || i == 0) {
							sender.sendMessage("");
							sender.sendMessage("   §l>> §e§o§lPage 2 >> /hawn help 2");
							sender.sendMessage("");
							sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
						} else if (i == 9) {
							sender.sendMessage("");
							sender.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
							sender.sendMessage("");
							sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
						} else if (i > 9) {
							sender.sendMessage("");
							sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
						} else {
							sender.sendMessage("");
							sender.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1)+" §7// §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
							sender.sendMessage("");
							sender.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
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
								if (!plugin.isEnabled()) {
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
						ConfigEventUtils.ExecuteEventAllPlayersConsole(msg, "", "");
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
							message = MessageUtils.colourTheStuff(message);
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
								if (plugin.isEnabled()) {
									Bukkit.getPluginManager().disablePlugin(plugin);
								}
							}
						}

						for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Disabled-Plugin-function")) {
							MessageUtils.ConsoleMessages(msg);
						}
					}

					for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Broadcast.On")) {
						ConfigEventUtils.ExecuteEventAllPlayersConsole(msg, "", "");
						MessageUtils.ConsoleMessages(msg);
					}
				}
			} else if (args[0].equalsIgnoreCase("pholders") || args[0].equalsIgnoreCase("pholder") || args[0].equalsIgnoreCase("parse")) {
				if (args.length == 1) {
					for (String msg : ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
						MessageUtils.ConsoleMessages(msg);
					}
				} else if (args.length == 2) {
					String msg = PlaceHolders.ReplaceMainplaceholderC(args[1]);

					MessageUtils.ConsoleMessages(msg);
				}
			} else if (args[0].equalsIgnoreCase("spawnmanager")) {
				if (args[1].equalsIgnoreCase("remove")) {
					if (args.length == 2) {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
							MessageUtils.ConsoleMessages(msg);
						}

						return false;
					}

					// If the spawn does not exist
					if (! ConfigSpawn.getConfig().isSet("Coordinated." + args[2] + ".World")) {
						for (String msg: ConfigMAdmin.getConfig().getStringList("Error.No-Spawn")) {
							MessageUtils.ConsoleMessages(msg);
						}

						return true;
					}

					// Execute the command
					ConfigSpawn.getConfig().set("Coordinated." + args[2] + ".World", null);
					ConfigSpawn.getConfig().set("Coordinated." + args[2] + ".X", null);
					ConfigSpawn.getConfig().set("Coordinated." + args[2] + ".Y", null);
					ConfigSpawn.getConfig().set("Coordinated." + args[2] + ".Z", null);
					ConfigSpawn.getConfig().set("Coordinated." + args[2] + ".Yaw", null);
					ConfigSpawn.getConfig().set("Coordinated." + args[2] + ".Pitch", null);
					ConfigSpawn.getConfig().set("Coordinated." + args[2] + ".Info", null);
					ConfigSpawn.getConfig().set("Coordinated." + args[2], null);

					ConfigSpawn.saveConfigFile();

					for (String msg : ConfigMAdmin.getConfig().getStringList("Command.Del.Spawn-Delete")) {
						MessageUtils.ConsoleMessages(msg.replaceAll("%spawn%", args[1]));
					}
				} else if (args[1].equalsIgnoreCase("setspawn")) {
					for (String msg : ConfigMAdmin.getConfig().getStringList("Error.Console.Not-A-Player")) {
						MessageUtils.ConsoleMessages(msg);
					}
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
			} else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("ver")) {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Version")) {
					MessageUtils.ConsoleMessages(msg);
				}
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
						ConfigEventUtils.ExecuteEventAllPlayersConsole(msg, "", "");
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
							message = MessageUtils.colourTheStuff(message);
							message = PlaceHolders.ReplaceMainplaceholderP(message, ps);

							ps.kickPlayer(message);
						}
					}

					for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Broadcast.On")) {
						ConfigEventUtils.ExecuteEventAllPlayersConsole(msg, "", "");
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
				for (String msg : ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Tps")) {
					MessageUtils.ConsoleMessages(msg);
				}
			} else if (args[0].equalsIgnoreCase("hooks") || args[0].equalsIgnoreCase("hook")) {
				sender.sendMessage("");
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
					sender.sendMessage("  §8→ §6§lPlaceholderAPI§8: §a§l✔");
				} else {
					sender.sendMessage("  §8→ §6§lPlaceholderAPI§8: §c§l✗");
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
					sender.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §a§l✔");
				} else {
					sender.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §c§l✗");
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
					sender.sendMessage("  §8→ §6§lWorldGuard§8: §a§l✔");
				} else {
					sender.sendMessage("  §8→ §6§lWorldGuard§8: §c§l✗");
				}
				sender.sendMessage("");
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
					sender.sendMessage("  §8→ §6§lBattleLevels§8: §a§l✔");
				} else {
					sender.sendMessage("  §8→ §6§lBattleLevels§8: §c§l✗");
				}
				sender.sendMessage("");
			} else if (args[0].equalsIgnoreCase("build") || args[0].equalsIgnoreCase("editplayer")
					|| args[0].equalsIgnoreCase("nightvision") || args[0].equalsIgnoreCase("noclip")
					|| args[0].equalsIgnoreCase("slotview")) {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Console.Not-A-Player")) {
					MessageUtils.ConsoleMessages(msg);
				}
			} else {
				try {
					int i = Integer.parseInt(args[0]);
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "hawn help " + i);
				} catch (NumberFormatException e) {
					Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "hawn help 1");
				}
			}
			return true;
		}

		/*
		If it's a player
		 */
		Player p = (Player) sender;

		// Check permission
		boolean permissionunlocked = false;

		if (p.hasPermission("hawn.admin.*")) {
			permissionunlocked = true;
		}

		// Command
		if (!p.hasPermission("hawn.admin") && !permissionunlocked) {
			MessageUtils.MessageNoPermission(p, "hawn.admin");
			return false;
		}

		if ((args.length == 0) || (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?")))) {
			if (args.length != 2) {
				p.performCommand("hawn help 1");
			} else if (args[0].equalsIgnoreCase("help") || (args[0].equalsIgnoreCase("?"))) {
				try {
					int i = Integer.parseInt(args[1]);

					p.sendMessage("§8//§7§m---------------§r§8\\\\ §3[§bHawn§3] §8//§7§m---------------§r§8\\\\");
					p.sendMessage("");
					p.sendMessage("     §l>> §e§o§lGlobal Help (Page " + i + ")");
					p.sendMessage("");

					for (String msg: helpPage(i)) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}

					if (i == 1 || i == 0) {
						p.sendMessage("");
						p.sendMessage("   §l>> §e§o§lPage 2 >> /hawn help 2");
						p.sendMessage("");
						p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
					} else if (i == 9) {
						p.sendMessage("");
						p.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1));
						p.sendMessage("");
						p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
					} else if (i > 9) {
						p.sendMessage("");
						p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
					} else {
						p.sendMessage("");
						p.sendMessage("   §l>> §e§o§l/hawn help "+(i - 1)+" << Page "+(i - 1)+" §7// §e§o§lPage "+(i + 1)+" >> /hawn help " + (i + 1));
						p.sendMessage("");
						p.sendMessage("§8\\\\§7§m---------------§r§8// §3[§bHawn§3] §8\\\\§7§m---------------§r§8//");
					}
				} catch (NumberFormatException e) {
					MessageUtils.UseNumber(p);
				}
			} else {
				p.performCommand("hawn help 1");
			}
		// Parse Place holder
		} else if (args[0].equalsIgnoreCase("pholders") || args[0].equalsIgnoreCase("pholder") || args[0].equalsIgnoreCase("parse")) {
			if (!p.hasPermission("hawn.admin.command.parseholders") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.parseholders");
				return false;
			}

			if (args.length == 1 || args.length == 2) {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			} else if (args.length == 3) {
				String targetname;
				if (args[1].equalsIgnoreCase("me")) {
					targetname = p.getName();
				} else {
					targetname = args[1];
				}

				Player target = Bukkit.getPlayer(targetname);

				if (target == null) {
					MessageUtils.PlayerDoesntExist(p);
					return false;
				}

				String msg = PlaceHolders.ReplaceMainplaceholderP(args[2], target);
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		// Give night vision
		} else if (args[0].equalsIgnoreCase("nv") || args[0].equalsIgnoreCase("nightvision")) {
			if (!p.hasPermission("hawn.admin.command.nightvision") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.nightvision");
				return false;
			}

			p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1999999999, 1));

			for (String msg: ConfigMAdmin.getConfig().getStringList("Command.NightVision")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		// Give noclip
		} else if (args[0].equalsIgnoreCase("noclip")) {
			if (!p.hasPermission("hawn.admin.command.noclip") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.noclip");
				return false;
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
		// Spawn
		} else if (args[0].equalsIgnoreCase("spawnmanager")) {
			if (!p.hasPermission("hawn.admin.command.spawnmanager") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.spawnmanager");
				return false;
			}

			if (args[1].equalsIgnoreCase("remove")) {
				if (args.length == 2) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}

					return false;
				}

				// If the spawn does not exist
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[2]+".World")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("Error.No-Spawn")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}

					return true;
				}

				// Execute the command
				ConfigSpawn.getConfig().set("Coordinated."+args[2]+".World", null);
				ConfigSpawn.getConfig().set("Coordinated."+args[2]+".X", null);
				ConfigSpawn.getConfig().set("Coordinated."+args[2]+".Y", null);
				ConfigSpawn.getConfig().set("Coordinated."+args[2]+".Z", null);
				ConfigSpawn.getConfig().set("Coordinated."+args[2]+".Yaw", null);
				ConfigSpawn.getConfig().set("Coordinated."+args[2]+".Pitch", null);
				ConfigSpawn.getConfig().set("Coordinated."+args[2]+".Info", null);
				ConfigSpawn.getConfig().set("Coordinated."+args[2], null);

				ConfigSpawn.saveConfigFile();

				for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Del.Spawn-Delete")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%spawn%", args[2]), "", "", false);
				}
			} else if (args[1].equalsIgnoreCase("setspawn")) {
				if (args.length == 2) {
					SetSpawnCommand.createcomplexspawn(p, true, false, "", false, "");
					return true;
				} else if (args.length == 3) {
					if (args[2].startsWith("d:")) {
						SetSpawnCommand.createcomplexspawn(p, true, false, "", false, "");
					} else if (args[2].startsWith("w:")) {
						SetSpawnCommand.createcomplexspawn(p, false, true, args[2], false, "");
					} else {
						SetSpawnCommand.createcomplexspawn(p, false, false, "", true, args[2]);
					}
				} else if (args.length == 4) {
					if (args[2].startsWith("d:")) {
						if (args[3].startsWith("w:")) {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[3], false, "");
						} else {
							SetSpawnCommand.createcomplexspawn(p, true, false, "", true, args[3]);
						}
					} else if (args[2].startsWith("w:")) {
						if (args[3].startsWith("d:")) {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[2], false, "");
						} else {
							SetSpawnCommand.createcomplexspawn(p, false, true, args[2], true, args[3]);
						}
					} else {
						if (args[3].startsWith("d:")) {
							SetSpawnCommand.createcomplexspawn(p, true, false, "", true, args[2]);
						} else if (args[3].startsWith("w:")) {
							SetSpawnCommand.createcomplexspawn(p, false, true, args[3], true, args[2]);
						}
					}
				} else if (args.length == 5) {
					if (args[2].startsWith("d:")) {
						if (args[3].startsWith("w:")) {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[3], true, args[4]);
						} else {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[4], true, args[3]);
						}
					} else if (args[2].startsWith("w:")) {
						if (args[3].startsWith("d:")) {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[2], true, args[3]);
						} else {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[2], true, args[3]);
						}
					} else {
						if (args[3].startsWith("d:")) {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[4], true, args[2]);
						} else if (args[3].startsWith("w:")) {
							SetSpawnCommand.createcomplexspawn(p, true, true, args[3], true, args[2]);
						}
					}
				}
			}
		// Urgent
		} else if (args[0].equalsIgnoreCase("urgent")) {
			if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Use-It-Only-On-The-Console")) {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Hawn")) {
					ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", p, true);
					MessageUtils.ConsoleMessages(msg);
				}

				return true;
			}

			if (!p.hasPermission("hawn.admin.command.urgent") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.urgent");
				return false;
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
						message = MessageUtils.colourTheStuff(message);
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
							if (plugin.isEnabled()) {
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
					ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", p, true);
					MessageUtils.ConsoleMessages(msg);
				}
			}
		// Reload
		} else if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			if (!p.hasPermission("hawn.admin.command.reload") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.reload");
				return false;
			}

			Reload.reloadconfig();

			for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Reload")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		// Slot view
		} else if (args[0].equalsIgnoreCase("slotview") || args[0].equalsIgnoreCase("sv")) {
			if (!p.hasPermission("hawn.admin.command.slotview") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.slotview");
				return false;
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
		// Edit players
		} else if (args[0].equalsIgnoreCase("editplayer")) {
			if (!p.hasPermission("hawn.editplayer") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.editplayer");
				return true;
			}

			Player target = Bukkit.getServer().getPlayer(args[1]);

			if (target == null) {
				MessageUtils.PlayerDoesntExist(p);
				return true;
			}

			EditPlayerGui.OpenGui(target);
		// Check hooks
		} else if (args[0].equalsIgnoreCase("hooks") || args[0].equalsIgnoreCase("hook")) {
			if (!p.hasPermission("hawn.editplayer") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.editplayer");
				return true;
			}

			p.sendMessage("");
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				p.sendMessage("  §8→ §6§lPlaceholderAPI§8: §a§l✔");
			} else {
				p.sendMessage("  §8→ §6§lPlaceholderAPI§8: §c§l✗");
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
				p.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §a§l✔");
			} else {
				p.sendMessage("  §8→ §6§lMVdWPlaceholderAPI§8: §c§l✗");
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
				p.sendMessage("  §8→ §6§lWorldGuard§8: §a§l✔");
			} else {
				p.sendMessage("  §8→ §6§lWorldGuard§8: §c§l✗");
			}
			p.sendMessage("");
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				p.sendMessage("  §8→ §6§lBattleLevels§8: §a§l✔");
			} else {
				p.sendMessage("  §8→ §6§lBattleLevels§8: §c§l✗");
			}
			p.sendMessage("");
		// info
		} else if (args[0].equalsIgnoreCase("info")) {
			if (!p.hasPermission("hawn.admin.command.info") && !permissionunlocked) {
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
		// version
		} else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v") || args[0].equalsIgnoreCase("ver")) {
			if (!p.hasPermission("hawn.admin.command.info") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
				return true;
			}

			for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Version")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		// about
		} else if (args[0].equalsIgnoreCase("about")) {
			p.sendMessage("§8§l§m-----------------------------");
			p.sendMessage("§7Plugin name:§c Hawn");
			p.sendMessage("§7Author:§c Dianox");
			p.sendMessage("§7Plugin version:§c " + Main.getVersion());
			p.sendMessage("§7Website: §chttps://www.spigotmc.org/resources/hawn-hub-lobby-management.66907/");
			p.sendMessage("§8§l§m-----------------------------");
		// donors
		} else if (args[0].equalsIgnoreCase("donor") || args[0].equalsIgnoreCase("donors")) {
			p.sendMessage("§8§l§m-----------------------------");
			p.sendMessage("§7Adfrorg: §e25 € §cThank you a lot §4<3§c The first donor");
			p.sendMessage("§7IgnaZ117: §e25 € §cThank you a lot §4<3§c The second donor OMG");
			p.sendMessage("§8§l§m-----------------------------");
		// tps
		} else if (args[0].equalsIgnoreCase("tps")) {
			if (!p.hasPermission("hawn.admin.command.info") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.info");
				return true;
			}

			for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Server-Info.Tps")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		// build
		} else if (args[0].equalsIgnoreCase("build")) {
			if (!p.hasPermission("hawn.admin.command.bypassbuild") && !permissionunlocked) {
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
		// Maintenance
		} else if (args[0].equalsIgnoreCase("m") || args[0].equalsIgnoreCase("maintenance")) {
			if (!p.hasPermission("hawn.admin.command.maintenance") && !permissionunlocked) {
				MessageUtils.MessageNoPermission(p, "hawn.admin.command.maintenance");
				return true;
			}

			if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable")) {
				HawnCommandConfig.getConfig().set("Maintenance.Enable", false);

				HawnCommandConfig.saveConfigFile();

				for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Off")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}

				for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Broadcast.Off")) {
					ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", p, true);
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
						message = MessageUtils.colourTheStuff(message);
						message = PlaceHolders.ReplaceMainplaceholderP(message, ps);

						ps.kickPlayer(message);
					}
				}

				for (String msg: ConfigMAdmin.getConfig().getStringList("Maintenance.Broadcast.On")) {
					ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", p, true);
					MessageUtils.ConsoleMessages(msg);
				}
			}
		} else {
			try {
				int i = Integer.parseInt(args[0]);
				p.performCommand("hawn help " + i);
			} catch (NumberFormatException e) {
				p.performCommand("hawn help 1");
			}
		}

		return true;
	}

	private List<String> helpPage(int i) {
		List<String> page = new ArrayList<>();
		if (i == 1) {
			page.add("§8>> §7/hawn spawnmanager <remove/setspawn> <name> etc. - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-SpawnManager"));
			page.add("§8>> §7/hawn reload §eor §7rl - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Reload"));
			page.add("§8>> §7/hawn version §eor §7v  - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Version"));
			page.add("§8>> §7/hawn tps - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Tps"));
			page.add("§8>> §7/hawn info [all/memory/tps/disk/cpu/server/version] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Info"));
			page.add("§8>> §7/hawn build - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Build"));
			page.add("§8>> §7/hawn hooks - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Hooks"));
			page.add("§8>> §7/hawn urgent - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Urgent"));
			page.add("§8>> §7/hawn [maintenance/m] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Maintenance"));
			page.add("§8>> §7/hawn donors - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Donors"));
		} else if (i == 2) {
			page.add("§8>> §7/hawn about - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-About"));
			page.add("§8>> §7/hawn parse <player> <placeholder> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Parse"));
			page.add("§8>> §7/hawn nightvision §eor §7nv - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-NightVision"));
			page.add("§8>> §7/hawn noclip - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-NoClip"));
			page.add("§8>> §7/hawn slotview - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-SlotView"));
			page.add("§8>> §7/hawn editplayer <player> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-EditPlayer"));
			page.add("");
			page.add("§8>> §7/broadcast <message>§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Broadcast"));
			page.add("§8>> §7/warning <message>§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Warning"));
			page.add("§8>> §7/btcast §eor §7/ta <message>§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Broadcast-Title"));
		} else if (i == 3) {
			page.add("§8>> §7/bacast §eor §7/aba <message>§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Broadcast-ActionBar"));
			page.add("");
			page.add("§8>> §7/ap - §e" + ConfigMAdmin.getConfig().getString("Command.Help.AdminPanel"));
			page.add("§8>> §7/checkaccount - §e" + ConfigMAdmin.getConfig().getString("Command.Help.CheckAccount"));
			page.add("§8>> §7/clearinv [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.ClearInv"));
			page.add("§8>> §7/invsee <player> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.InvSee"));
			page.add("§8>> §7/ip <player> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Ip"));
			page.add("§8>> §7/list [page number] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.List"));
			page.add("");
			page.add("§8>> §7/spawn [SpawnName] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Spawn"));
		} else if (i == 4) {
			page.add("§8>> §7/spawn tp <player> [SpawnName] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Spawn-Tp"));
			page.add("§8>> §7/delspawn <name> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.DelSpawn"));
			page.add("§8>> §7/spawnlist - §e" + ConfigMAdmin.getConfig().getString("Command.Help.SpawnList"));
			page.add("§8>> §7/setspawn [spawn] [d:true] [w:world1,world2 etc.] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.SetSpawn"));
			page.add("");
			page.add("§8>> §7/warp <WarpName> [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Warp"));
			page.add("§8>> §7/warplist - §e" + ConfigMAdmin.getConfig().getString("Command.Help.WarpList"));
			page.add("§8>> §7/setwarp <warpname> [w:world1,world2 etc.] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.SetWarp"));
			page.add("§8>> §7/delwarp <name> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.DelWarp"));
			page.add("§8>> §7/editwarp <name> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.EditWarp"));
		} else if (i == 5) {
			page.add("");
			page.add("§8>> §7/sun or /clearw - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Sun"));
			page.add("§8>> §7/rain - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Rain"));
			page.add("§8>> §7/thunder - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Thunder"));
			page.add("§8>> §7/day - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Day"));
			page.add("§8>> §7/night - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Night"));
			page.add("");
			page.add("§8>> §7/fly [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Fly"));
			page.add("§8>> §7/flyspeed [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.FlySpeed"));
			page.add("§8>> §7/speed [number] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Speed"));
		} else if (i == 6) {
			page.add("§8>> §7/heal [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Heal"));
			page.add("§8>> §7/feed [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Feed"));
			page.add("§8>> §7/ping [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Ping"));
			page.add("§8>> §7/v [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Vanish"));
			page.add("§8>> §7/gamemode or gm... or gma etc. - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Gamemode"));
			page.add("§8>> §7/burn [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Burn"));
			page.add("§8>> §7/cleargrounditems - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Cleargrounditems"));
			page.add("§8>> §7/clearmobs - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Clearmobs"));
			page.add("§8>> §7/ec [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.EnderChest"));
			page.add("§8>> §7/exp <playerName> <add/set/take/clear> <amount> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Exp"));
		} else if (i == 7) {
			page.add("§8>> §7/getpos <playerName> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Getpos"));
			page.add("§8>> §7/hat [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hat"));
			page.add("§8>> §7/kickall - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Kickall"));
			page.add("§8>> §7/repair - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Repair"));
			page.add("§8>> §7/skull [player] - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Skull"));
			page.add("§8>> §7/suicide - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Suicide"));
			page.add("§8>> §7/workbench <player> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Workbench"));
			page.add("");
			page.add("§8>> §7/cc - §e" + ConfigMAdmin.getConfig().getString("Command.Help.ClearChat"));
			page.add("§8>> §7/delaychat <number> - §e" + ConfigMAdmin.getConfig().getString("Command.Help.DelayChat"));
		} else if (i == 8) {
			page.add("§8>> §7/gmute - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Gmute"));
			page.add("");
			page.add("§8>> §7/help <.../...>§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Help"));
			page.add("§8>> §7/gotop§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Gotop"));
			page.add("");
			page.add("§8>> §7/emoji§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Emoji"));
			page.add("");
			page.add("§8>> §7/scoreboard§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Scoreboard"));
			page.add("§8>> §7/scoreboard set <scoreboard's file name>§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Scoreboard-Set"));
			page.add("§8>> §7/scoreboard keep§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Scoreboard-Keep"));
		} else if (i == 9) {
			page.add("§8>> §7/scoreboard list§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Scoreboard-List"));
			page.add("§8>> §7/option§7 - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Option"));
			page.add("");
			page.add("§8>> §7/hw <argument> <argument two> etc. - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hworld"));
			page.add("");
			page.add("§8>> §7/hawn setup - §e" + ConfigMAdmin.getConfig().getString("Command.Help.Hawn-Setup"));
		}

		return page;
	}

	private void Zip(boolean b, Player p) {

		String pathname = new File(".").getAbsolutePath();
		String zipFile = Main.getInstance().getDataFolder().getAbsolutePath() + "-save-1.zip";

		File directory = new File(pathname);
		getFileList(directory);

		File checkname = new File(zipFile);

		int number = 1;

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
				ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", p, true);
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