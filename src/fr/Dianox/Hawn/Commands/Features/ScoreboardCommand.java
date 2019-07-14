package fr.Dianox.Hawn.Commands.Features;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.PlayerOptionSQLClass;
import fr.Dianox.Hawn.Utility.Config.Commands.ScoreboardCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Scoreboard.PlayerBoard;

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
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
				
		if (!ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Enable")) {
			if (ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Disable-Message")) {
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
			if (Main.boards.containsKey(p)) {
				((PlayerBoard)Main.getInstance().getBoards().get(p)).remove();
				Main.playerWorldTimer.remove(p);
				Main.nosb.add(p);
				
				if (ConfigMCommands.getConfig().getBoolean("Scoreboard.Toggle.Off.Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList("Scoreboard.Toggle.Off.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else {
				Main.getInstance().createDefaultScoreboard(p);
				Main.playerWorldTimer.put(p, System.currentTimeMillis() + 3000);
				Main.nosb.remove(p);
				
				if (ConfigMCommands.getConfig().getBoolean("Scoreboard.Toggle.On.Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList("Scoreboard.Toggle.On.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
		} else if (args.length >= 1) {
			if (args[0].equalsIgnoreCase("set")) {
				if (p.hasPermission("hawn.scoreboard.command.set")) {
					if (Main.nosb.contains(p)) {
			    		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			    		return true;
			    	}
					
					String perm = args[1];
					if (!Main.getInstance().getInfo().containsKey("hawn.scoreboard."+perm)) {
						if (ConfigMCommands.getConfig().getBoolean("Scoreboard.Error-Changes.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Scoreboard.Error-Changes.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", args[1]), p);
							}
						}
						return true;
					}
					
					if (p.hasPermission("hawn.scoreboard-keep-scoreboard-change")) {
						PlayerOptionSQLClass.saveSBmysqlyaml(p, args[1], "TRUE");
					} else {
						PlayerOptionSQLClass.saveSBmysqlyaml(p, args[1], "FALSE");
					}
					
					Main.playerWorldTimer.remove(p);
					((PlayerBoard)Main.getInstance().getBoards().get(p)).remove();
					Main.nosb.add(p);
						
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						public void run() {
							Main.nosb.remove(p);
							Main.playerWorldTimer.put(p, System.currentTimeMillis() + 3000);
							Main.getInstance().createDefaultScoreboard(p);
							
							if (ConfigMCommands.getConfig().getBoolean("Scoreboard.Changes.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Scoreboard.Changes.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", args[1]), p);
								}
							}
						}
					}, 20);
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
						
						if (ConfigMCommands.getConfig().getBoolean("Scoreboard.Keep-Off.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Scoreboard.Keep-Off.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					} else {
						String sb = PlayerOptionSQLClass.getYmlaMysqlsb(p, "scoreboard");
						
						PlayerOptionSQLClass.saveSBmysqlyaml(p, sb, "TRUE");
						
						if (ConfigMCommands.getConfig().getBoolean("Scoreboard.Keep-On.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Scoreboard.Keep-On.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					}
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.scoreboard-keep-scoreboard-change");
					return true;
				}
			}
		} else {
			p.sendMessage("§c/scoreboard");
		}
		
		return false;
	}

}
