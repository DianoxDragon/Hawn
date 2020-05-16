package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.FeedCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class FeedCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.feed";

    public FeedCommand(String name) {
        super(name);
        this.description = "Feed a player";
        this.usageMessage = "/feed <player>";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
                	if (args.length == 0) {
	                    if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
	                        for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
	                            MessageUtils.ConsoleMessages(msg);
	                        }
	                    }
	                } else if (args.length == 1) {
	                    if (FeedCommandConfig.getConfig().getBoolean("Feed.Others.Enable")) {	
	                    	Player target = Bukkit.getServer().getPlayer(args[0]);
	                    	if (target == null) {
	                    		 if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
	        						 for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
	        							 MessageUtils.ConsoleMessages(msg);
	        						 }
	        					 }
	                    		return true;
	                    	}
	
	                    	target.setFoodLevel(20);
	
	                    	if (ConfigMMsg.getConfig().getBoolean("Feed.Other-Sender.Enable")) {
	                    		for (String msg: ConfigMMsg.getConfig().getStringList("Feed.Other-Sender.Messages")) {
	                    			MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()));
	                    		}
	                    	}
	                    	if (ConfigMMsg.getConfig().getBoolean("Feed.Other.Enable")) {
	                    		for (String msg: ConfigMMsg.getConfig().getStringList("Feed.Other.Messages")) {
	                    			ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
	                    		}
	                    	}
	                    } else {
	                        sender.sendMessage("§c/feed");
	                    }
	                } else {
	                    if (sender.hasPermission("hawn.command.feed.other")) {
	                        if (FeedCommandConfig.getConfig().getBoolean("Feed.Others.Enable")) {
	                        	sender.sendMessage("§c/feed or §c/feed [player]");
	                        } else {
	                        	sender.sendMessage("§c/feed");
	                        }
	                    } else {
	                    	sender.sendMessage("§c/feed");
	                    }
	                }
                    return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!FeedCommandConfig.getConfig().getBoolean("Feed.Enable")) {
            if (FeedCommandConfig.getConfig().getBoolean("Feed.Disable-Message")) {
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
            p.setFoodLevel(20);

            if (ConfigMMsg.getConfig().getBoolean("Feed.Self.Enable")) {
                for (String msg: ConfigMMsg.getConfig().getStringList("Feed.Self.Messages")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                }
            }

        } else if (args.length == 1) {
            if (FeedCommandConfig.getConfig().getBoolean("Feed.Others.Enable")) {
                if (p.hasPermission("hawn.command.feed.other")) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);

                    if (target == null) {
                        MessageUtils.PlayerDoesntExist(p);
                        return true;
                    }

                    target.setFoodLevel(20);

                    if (ConfigMMsg.getConfig().getBoolean("Feed.Other-Sender.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Feed.Other-Sender.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
                        }
                    }
                    if (ConfigMMsg.getConfig().getBoolean("Feed.Other.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Feed.Other.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
                        }
                    }

                } else {
                    String Permission = "hawn.command.feed.other";
                    MessageUtils.MessageNoPermission(p, Permission);
                }
            } else {
                p.sendMessage("§c/feed");
            }
        } else {
            if (p.hasPermission("hawn.command.feed.other")) {
                if (FeedCommandConfig.getConfig().getBoolean("Feed.Others.Enable")) {
                    p.sendMessage("§c/feed or §c/feed [player]");
                } else {
                    p.sendMessage("§c/feed");
                }
            } else {
                p.sendMessage("§c/feed");
            }
        }

        return true;
    }

}