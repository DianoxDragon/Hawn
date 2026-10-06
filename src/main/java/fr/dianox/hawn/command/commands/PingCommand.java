package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.command.commands.tab.Tab;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.PingCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PingCommand extends BukkitCommand {

    public PingCommand(String name) {
        /*
         * Main class to register the essential information of the command
         */
        super(name);
        this.description = "To know the ping";
        this.usageMessage = "/ping";
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (args.length == 1) {
            return Tab.players(sender, args);
        }

        return Tab.none();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console, in other words, not a player
        if (!(sender instanceof Player)) {

            // If there are an argument with /ping - /ping <arg 1>
            if ((args.length == 1)) {

                Player target = Bukkit.getServer().getPlayer(args[0]);

                // If player doesn't exist
                if (target == null) {
                    if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
                            MessageUtils.ConsoleMessages(msg);
                        }
                    }
                    return true;
                }

                for (String msg: ConfigMMsg.getConfig().getStringList("Ping.Other")) {
                    MessageUtils.ConsoleMessages(msg.replace("%target%", target.getName()));
                }

            } else {
                sender.sendMessage("§c/ping <player>");
            }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        // >> Command
        // If it's only /ping
        if ((args.length == 0)) {
            if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Use_Permission")) {
                if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Enable")) {
                    if (p.hasPermission("hawn.command.ping.self")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Ping.Self")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    } else {
                        String Permission = "hawn.command.ping.self";
                        MessageUtils.MessageNoPermission(p, Permission);
                    }
                } else {
                    if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Disable-Message")) {
                        if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
                    }
                }
            } else {
                if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Ping.Self")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
                } else {
                    if (PingCommandConfig.getConfig().getBoolean("Ping.Self.Disable-Message")) {
                        if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
                    }
                }
            }
            // If there are an argument with /ping - /ping <arg 1>
        } else if ((args.length == 1)) {
            Player other = Bukkit.getPlayer(args[0]);
            if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Use_Permission")) {
                if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Enable")) {
                    if (p.hasPermission("hawn.command.ping.other")) {
                        if (other == null) {
                            MessageUtils.PlayerDoesntExist(p);
                            return true;
                        }
                        for (String msg: ConfigMMsg.getConfig().getStringList("Ping.Other")) {
                            ConfigEventUtils.ExecuteEvent(p, msg.replace("%ping%", String.valueOf(PingCommand.getPing(other))), "", "", false);
                        }
                    } else {
                        String Permission = "hawn.command.ping.other";
                        MessageUtils.MessageNoPermission(p, Permission);
                    }
                } else {
                    if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Disable-Message")) {
                        if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
                    }
                }
            } else {
                if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Enable")) {
                    if (other == null) {
                        MessageUtils.PlayerDoesntExist(p);
                        return true;
                    }
                    for (String msg: ConfigMMsg.getConfig().getStringList("Ping.Other")) {
                        ConfigEventUtils.ExecuteEvent(p, msg.replace("%target%", other.getName()).replace("%ping%", String.valueOf(PingCommand.getPing(other))), "", "", false);
                    }
                } else {
                    if (PingCommandConfig.getConfig().getBoolean("Ping.Other.Disable-Message")) {
                        if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    /*
     * Method to get the ping of a player
     */
    public static int getPing(Player p) {
        try {
            // Player#getPing() exists since 1.17 (and on Paper 1.16.5)
            if (GET_PING != null) {
                return (int) GET_PING.invoke(p);
            }

            // 1.16.5 Spigot: public field EntityPlayer#ping
            Object handle = p.getClass().getMethod("getHandle").invoke(p);
            return handle.getClass().getField("ping").getInt(handle);
        } catch (ReflectiveOperationException | RuntimeException e) {
            return 0;
        }
    }

    private static final Method GET_PING = findGetPing();

    private static Method findGetPing() {
        try {
            return Player.class.getMethod("getPing");
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

}