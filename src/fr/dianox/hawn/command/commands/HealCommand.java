package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.HealCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

public class HealCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.heal";

    public HealCommand(String name) {
        super(name);
        this.description = "Heal a player";
        this.usageMessage = "/heal <player>";
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {

            if (args.length == 1) {
                Player target = Bukkit.getServer().getPlayer(args[0]);

                if (target == null) {
                    if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
                            MessageUtils.ConsoleMessages(msg);
                        }
                    }
                    return true;
                }

                Double health = Double.valueOf(target.getMaxHealth());
                target.setHealth(health);
                target.setFireTicks(0);
                
                if (HealCommandConfig.getConfig().getBoolean("Heal.Option.Feed")) {
                	target.setFoodLevel(20);
                }
                
                if (ConfigMMsg.getConfig().getBoolean("Heal.Other-Sender.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Heal.Other-Sender.Messages")) {
                        MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()));
                    }
                }
                if (ConfigMMsg.getConfig().getBoolean("Heal.Other.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Heal.Other.Messages")) {
                        ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
                    }
                }
            } else {
                Bukkit.getConsoleSender().sendMessage("§c/heal <player>");
            }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!HealCommandConfig.getConfig().getBoolean("Heal.Enable")) {
            if (HealCommandConfig.getConfig().getBoolean("Heal.Disable-Message")) {
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
            Double health = Double.valueOf(p.getMaxHealth());
            p.setHealth(health);
            p.setFireTicks(0);
            
            if (HealCommandConfig.getConfig().getBoolean("Heal.Option.Feed")) {
            	p.setFoodLevel(20);
            }
            
            if (ConfigMMsg.getConfig().getBoolean("Heal.Self.Enable")) {
                for (String msg: ConfigMMsg.getConfig().getStringList("Heal.Self.Messages")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                }
            }

        } else if (args.length == 1) {
            if (HealCommandConfig.getConfig().getBoolean("Heal.Others.Enable")) {
                if (p.hasPermission("hawn.command.heal.other")) {
                    Player target = Bukkit.getServer().getPlayer(args[0]);

                    if (target == null) {
                        MessageUtils.PlayerDoesntExist(p);
                        return true;
                    }

                    Double health = Double.valueOf(target.getMaxHealth());
                    target.setHealth(health);
                    target.setFireTicks(0);
                    
                    if (HealCommandConfig.getConfig().getBoolean("Heal.Option.Feed")) {
                    	target.setFoodLevel(20);
                    }
                    
                    if (ConfigMMsg.getConfig().getBoolean("Heal.Other-Sender.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Heal.Other-Sender.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), "", "", false);
                        }
                    }
                    if (ConfigMMsg.getConfig().getBoolean("Heal.Other.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Heal.Other.Messages")) {
                            ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
                        }
                    }

                } else {
                    String Permission = "hawn.command.heal.other";
                    MessageUtils.MessageNoPermission(p, Permission);
                }
            } else {
                p.sendMessage("§c/heal");
            }
        } else {
            if (p.hasPermission("hawn.command.heal.other")) {
                if (HealCommandConfig.getConfig().getBoolean("Heal.Others.Enable")) {
                    p.sendMessage("§c/heal or §c/heal [player]");
                } else {
                    p.sendMessage("§c/heal");
                }
            } else {
                p.sendMessage("§c/heal");
            }
        }

        return true;
    }

}