package fr.dianox.hawn.commands.others;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.IpCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;

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
                 if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
     				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
     					MessageUtils.ReplaceMessageForConsole(msg);
     				}
     			}
             } else if (args.length == 1) {
             	Player target = Bukkit.getServer().getPlayer(args[0]);

         		if (target == null) {
         			if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
        	            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
        	            	MessageUtils.ReplaceMessageForConsole(msg);
        	            }
        	        }
         			return true;
         		}
         		
         		String ip = target.getAddress().getHostString();
         		
         		for (String msg: OtherAMConfig.getConfig().getStringList("Command.IP")) {
         			MessageUtils.ReplaceMessageForConsole(msg
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
            if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
        } else if (args.length == 1) {
        	Player target = Bukkit.getServer().getPlayer(args[0]);

    		if (target == null) {
    			MessageUtils.PlayerDoesntExist(p);
    			return true;
    		}
    		
    		String ip = target.getAddress().getHostString();
    		
    		for (String msg: OtherAMConfig.getConfig().getStringList("Command.IP")) {
    			MessageUtils.ReplaceCharMessagePlayer(msg
    					.replaceAll("%target%", target.getName())
    					.replaceAll("%getplayerip%", ip)
    					, p);
    		}
        } else {
        	p.sendMessage("§c/ip <player>");
        }

        return true;
    }

}