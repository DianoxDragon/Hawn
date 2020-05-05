package fr.dianox.hawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.GetPosCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class GetPosCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.getpos";

    public GetPosCommand(String name) {
        super(name);
        this.description = "Get the position of a player";
        this.usageMessage = "/getpos <player>";
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
        		
        		Integer X = target.getLocation().getBlockX();
        		Integer Y = target.getLocation().getBlockY();
        		Integer Z = target.getLocation().getBlockZ();
        		String World = target.getLocation().getWorld().getName();
        		
        		if (ConfigMMsg.getConfig().getBoolean("GetPos.Enable")) {
        			for (String msg: ConfigMMsg.getConfig().getStringList("GetPos.Messages")) {
        				MessageUtils.ConsoleMessages(msg
        						.replaceAll("%target%", target.getName())
        						.replaceAll("%X%", String.valueOf(X))
        						.replaceAll("%Y%", String.valueOf(Y))
        						.replaceAll("%Z%", String.valueOf(Z))
        						.replaceAll("%world%", World));
        			}
        		}
            } else {
            	sender.sendMessage("§c/getpos <player>");
            }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!GetPosCommandConfig.getConfig().getBoolean("GetPos.Enable")) {
            if (GetPosCommandConfig.getConfig().getBoolean("GetPos.Disable-Message")) {
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
    		
    		Integer X = target.getLocation().getBlockX();
    		Integer Y = target.getLocation().getBlockY();
    		Integer Z = target.getLocation().getBlockZ();
    		String World = target.getLocation().getWorld().getName();
    		
    		if (ConfigMMsg.getConfig().getBoolean("GetPos.Enable")) {
    			for (String msg: ConfigMMsg.getConfig().getStringList("GetPos.Messages")) {
    				ConfigEventUtils.ExecuteEvent(p, msg
    						.replaceAll("%target%", target.getName())
    						.replaceAll("%X%", String.valueOf(X))
    						.replaceAll("%Y%", String.valueOf(Y))
    						.replaceAll("%Z%", String.valueOf(Z))
    						.replaceAll("%world%", World)
    						, "", "", false);
    			}
    		}
        } else {
        	p.sendMessage("§c/getpos <player>");
        }

        return true;
    }

}