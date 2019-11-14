package fr.dianox.hawn.commands.others;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.FeedCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

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

            if (args.length == 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);

                if (target == null) {
                    if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
                        for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
                            MessageUtils.ReplaceMessageForConsole(msg);
                        }
                    }
                    return true;
                }

                target.setFoodLevel(20);

                if (ConfigMCommands.getConfig().getBoolean("Feed.Other-Sender.Enable")) {
                    for (String msg: ConfigMCommands.getConfig().getStringList("Feed.Other-Sender.Messages")) {
                        MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
                    }
                }
                if (ConfigMCommands.getConfig().getBoolean("Feed.Other.Enable")) {
                    for (String msg: ConfigMCommands.getConfig().getStringList("Feed.Other.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
                    }
                }
            } else {
                Bukkit.getConsoleSender().sendMessage("§c/feed <player>");
            }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!FeedCommandConfig.getConfig().getBoolean("Feed.Enable")) {
            if (FeedCommandConfig.getConfig().getBoolean("Feed.Disable-Message")) {
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
            p.setFoodLevel(20);

            if (ConfigMCommands.getConfig().getBoolean("Feed.Self.Enable")) {
                for (String msg: ConfigMCommands.getConfig().getStringList("Feed.Self.Messages")) {
                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
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

                    if (ConfigMCommands.getConfig().getBoolean("Feed.Other-Sender.Enable")) {
                        for (String msg: ConfigMCommands.getConfig().getStringList("Feed.Other-Sender.Messages")) {
                        	MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
                        }
                    }
                    if (ConfigMCommands.getConfig().getBoolean("Feed.Other.Enable")) {
                        for (String msg: ConfigMCommands.getConfig().getStringList("Feed.Other.Messages")) {
                        	MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), target);
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