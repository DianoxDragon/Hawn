package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.event.OnScoreboard;
import fr.dianox.hawn.modules.scoreboard.scoreboards.FastBoard;
import fr.dianox.hawn.modules.scoreboard.scoreboards.ScoreTask;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.configs.commands.ScoreboardCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.scheduler.BukkitTask;

public class ScoreboardCommand extends BukkitCommand {

	String GeneralPermission = "hawn.command.scoreboard.toggle";
	
	public ScoreboardCommand(String name) {
		super(name);
		this.description = "Toggle on or off the scoreboard";
        this.usageMessage = "/scoreboard";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ConsoleMessages(msg);
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
				
		if (!ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Enable")) {
			if (ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Disable-Message")) {
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
		
		boolean value = false;

		if (args.length == 0) {
			for (String s : Main.getInstance().getScoreManager().worldscore.keySet()) {
				if (p.hasPermission("hawn.scoreboard." + s)) {
					value = true;
					break;
				}
			}

			if (!value) {
				if (ConfigMMsg.getConfig().getBoolean("Scoreboard.Error-No-Perm-For-Any-Score.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Scoreboard.Error-No-Perm-For-Any-Score.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
				return true;
			}

			if (Main.getInstance().getScoreManager().playerscore.containsKey(p)) {
				Bukkit.getScheduler().cancelTask(Main.getInstance().getScoreManager().scoretaskplayer.get(p));

				FastBoard board = Main.getInstance().getScoreManager().playerboard.get(p);
				if (board != null) {
					board.delete();
				}

				Main.getInstance().getScoreManager().playerboard.remove(p);
				Main.getInstance().getScoreManager().playerscore.remove(p);
				Main.getInstance().getScoreManager().scoretaskplayer.remove(p);

				if (!Main.getInstance().getScoreManager().getNoScore().contains(p)) {
					Main.getInstance().getScoreManager().getNoScore().add(p);
				}

				if (ConfigMMsg.getConfig().getBoolean("Scoreboard.Toggle.Off.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Scoreboard.Toggle.Off.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
			} else {
				Main.getInstance().getScoreManager().getNoScore().remove(p);

				String world = p.getWorld().getName();
				OnScoreboard.createNewScore(p, world);

				if (ConfigMMsg.getConfig().getBoolean("Scoreboard.Toggle.On.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Scoreboard.Toggle.On.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
			}
		} else {
			if (args[0].equalsIgnoreCase("set")) {
				if (p.hasPermission("hawn.scoreboard.command.set")) {

					if (args.length == 1) {

						for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
		            	}

						return true;
					}

					if (Main.getInstance().getScoreManager().getNoScore().contains(p)) {
						return false;
					}

					String perm = args[1];
					if (!Main.getInstance().getScoreManager().scoreacess.containsKey(perm)) {
						if (ConfigMMsg.getConfig().getBoolean("Scoreboard.Error-Changes.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Scoreboard.Error-Changes.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", args[1]), "", "", false);
							}
						}
						return true;
					}

					if (p.hasPermission("hawn.scoreboard-keep-scoreboard-change")) {
						PlayerOptionSQLClass.saveSBmysqlyaml(p, args[1], "TRUE");
					} else {
						PlayerOptionSQLClass.saveSBmysqlyaml(p, args[1], "FALSE");
					}

					if (p.hasPermission("hawn.scoreboard." + perm)) {
						if (Main.getInstance().getScoreManager().scoretaskplayer.containsKey(p)) {
							Bukkit.getScheduler().cancelTask(Main.getInstance().getScoreManager().scoretaskplayer.get(p));
						}

						if (Main.getInstance().getScoreManager().playerboard.containsKey(p)) {
							FastBoard board = Main.getInstance().getScoreManager().playerboard.get(p);
							if (board != null) {
								board.delete();
							}
						}

						Main.getInstance().getScoreManager().playerboard.remove(p);
						Main.getInstance().getScoreManager().playerscore.remove(p);
						Main.getInstance().getScoreManager().scoretaskplayer.remove(p);

						FastBoard boardnew = new FastBoard(p);

						Main.getInstance().getScoreManager().playerboard.put(p, boardnew);
						Main.getInstance().getScoreManager().playerscore.put(p, perm);

						if (Main.getInstance().getScoreManager().getFile(perm).isSet("updater.text")) {
							int number = Main.getInstance().getScoreManager().getFile(perm).getInt("updater.text");
							Main.getInstance().getScoreManager().writeInt(perm, "updater.scoreboard", number);
							Main.getInstance().getScoreManager().writeInt(perm, "updater.text", null);
						}

						BukkitTask TaskName = new ScoreTask(Main.getInstance().getScoreManager(), boardnew, p).runTaskTimer(Main.getInstance(), 0,
								Main.getInstance().getScoreManager().getFile(perm).getInt("updater.scoreboard"));

						Main.getInstance().getScoreManager().scoretaskplayer.put(p, TaskName.getTaskId());
					} else {
						MessageUtils.MessageNoPermission(p, "hawn.scoreboard." + perm);
						return true;
					}

					if (ConfigMMsg.getConfig().getBoolean("Scoreboard.Changes.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Scoreboard.Changes.Messages")) {
							ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", args[1]), "", "", false);
						}
					}
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.scoreboard.command.set");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("keep")) {
				if (p.hasPermission("hawn.scoreboard-keep-scoreboard-change")) {

					String bool = PlayerOptionSQLClass.getYmlaMysqlsb(p, "keepsb");

					if (bool.equalsIgnoreCase("TRUE") && ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Option.Keep-Scoreboard-Change")) {
						String sb = PlayerOptionSQLClass.getYmlaMysqlsb(p, "scoreboard");

						PlayerOptionSQLClass.saveSBmysqlyaml(p, sb, "FALSE");

						if (ConfigMMsg.getConfig().getBoolean("Scoreboard.Keep-Off.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Scoreboard.Keep-Off.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						}
					} else {
						String sb = PlayerOptionSQLClass.getYmlaMysqlsb(p, "scoreboard");

						PlayerOptionSQLClass.saveSBmysqlyaml(p, sb, "TRUE");

						if (ConfigMMsg.getConfig().getBoolean("Scoreboard.Keep-On.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Scoreboard.Keep-On.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						}
					}
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.scoreboard-keep-scoreboard-change");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("list")) {
				if (p.hasPermission("hawn.scoreboard-list")) {
					p.sendMessage("§8//§7§m--------------§r§8\\\\ §3[§bScoreboard List§3] §8//§7§m--------------§r§8\\\\");
					p.sendMessage("");

					for (String s: Main.getInstance().getScoreManager().scoreacess.keySet()) {
						p.sendMessage("§e" + s + " §7 => §b hawn.scoreboard." + s);
					}

					p.sendMessage("");
					p.sendMessage("§8\\\\§7§m--------------§r§8// §3[§bScoreboard List§3] §8\\\\§7§m--------------§r§8//");
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.scoreboard-list");
					return true;
				}
			}
		}
		
		return false;
	}

}
