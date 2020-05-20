package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.IpCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;

public class IpCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.ip";

    public IpCommand(String name) {
        super(name);
        this.description = "Get the ip of a player";
        this.usageMessage = "/ip <player>";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {

        	 if (args.length == 0) {
                 if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
     				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
     					MessageUtils.ConsoleMessages(msg);
     				}
     			}
             } else if (args.length == 1) {
             	Player target = Bukkit.getServer().getPlayer(args[0]);

         		if (target == null) {
         			if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
        	            for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
        	            	MessageUtils.ConsoleMessages(msg);
        	            }
        	        }
         			return true;
         		}
         		
         		String ip = target.getAddress().getHostString();
         		
         		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.IP")) {
         			MessageUtils.ConsoleMessages(msg
         					.replaceAll("%target%", target.getName())
         					.replaceAll("%getplayerip%", ip));
         		}
             } else {
             	sender.sendMessage("§c/ip <player>");
             }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!IpCommandConfig.getConfig().getBoolean("Ip.Enable")) {
            if (IpCommandConfig.getConfig().getBoolean("Ip.Disable-Message")) {
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
            if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
        } else if (args.length == 1) {
        	Player target = Bukkit.getServer().getPlayer(args[0]);

    		if (target == null) {
    			MessageUtils.PlayerDoesntExist(p);
    			return true;
    		}
    		
    		String ip = target.getAddress().getHostString();
    		
    		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.IP")) {
    			ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()).replaceAll("%getplayerip%", ip), "", "", false);
    		}
        } else {
        	p.sendMessage("§c/ip <player>");
        }

        return true;
    }

}