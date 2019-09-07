package fr.Dianox.Hawn.Commands.Others;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.HealCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

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
                    if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
                        for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
                            MessageUtils.ReplaceMessageForConsole(msg);
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
                
                if (ConfigMCommands.getConfig().getBoolean("Heal.Other-Sender.Enable")) {
                    for (String msg: ConfigMCommands.getConfig().getStringList("Heal.Other-Sender.Messages")) {
                        MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
                    }
                }
                if (ConfigMCommands.getConfig().getBoolean("Heal.Other.Enable")) {
                    for (String msg: ConfigMCommands.getConfig().getStringList("Heal.Other.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
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
            Double health = Double.valueOf(p.getMaxHealth());
            p.setHealth(health);
            p.setFireTicks(0);
            
            if (HealCommandConfig.getConfig().getBoolean("Heal.Option.Feed")) {
            	p.setFoodLevel(20);
            }
            
            if (ConfigMCommands.getConfig().getBoolean("Heal.Self.Enable")) {
                for (String msg: ConfigMCommands.getConfig().getStringList("Heal.Self.Messages")) {
                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
                    
                    if (ConfigMCommands.getConfig().getBoolean("Heal.Other-Sender.Enable")) {
                        for (String msg: ConfigMCommands.getConfig().getStringList("Heal.Other-Sender.Messages")) {
                            MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
                        }
                    }
                    if (ConfigMCommands.getConfig().getBoolean("Heal.Other.Enable")) {
                        for (String msg: ConfigMCommands.getConfig().getStringList("Heal.Other.Messages")) {
                            MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), target);
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
