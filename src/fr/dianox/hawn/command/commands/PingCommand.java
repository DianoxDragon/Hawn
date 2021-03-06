package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.PingCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
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
            List<String> tab = new ArrayList<>();
            for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                tab.add(p.getName());
            }

            java.util.Collections.sort(tab);

            return tab;
        }

        return null;
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
                    MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()));
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
                            ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%ping%", String.valueOf(PingCommand.getPing(other))), "", "", false);
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
                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", other.getName()).replaceAll("%ping%", String.valueOf(PingCommand.getPing(other))), "", "", false);
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
        String bpName = Bukkit.getServer().getClass().getPackage().getName();
        String version = bpName.substring(bpName.lastIndexOf(".") + 1, bpName.length());
        try {
            Class < ? > CPClass = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
            Object CraftPlayer = CPClass.cast(p);

            Method getHandle = CraftPlayer.getClass().getMethod("getHandle", new Class[0]);
            Object EntityPlayer = getHandle.invoke(CraftPlayer, new Object[0]);

            Field ping = EntityPlayer.getClass().getDeclaredField("ping");
            
            return ping.getInt(EntityPlayer);
        } catch (Exception e) {}
        return 0;
    }

}