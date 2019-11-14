package fr.dianox.hawn.commands.others;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.ListCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ListCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.list";
    public static HashMap<Integer, String> plist = new HashMap<>();
    
    public ListCommand(String name) {
        super(name);
        this.description = "Get the total number of players on the server";
        this.usageMessage = "/list [page number]";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
        	if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
        		for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
        			MessageUtils.ReplaceMessageForConsole(msg);
        		}
        	}
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!ListCommandConfig.getConfig().getBoolean("List.Enable")) {
            if (ListCommandConfig.getConfig().getBoolean("List.Disable-Message")) {
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

        Integer checklist = 1;
        
        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
        	plist.put(checklist, all.getName());
        	checklist++;
        }
                
        // The command
        if (args.length == 0) {
        	for (String msg: OtherAMConfig.getConfig().getStringList("Command.List.Part-One")) {
    			MessageUtils.ReplaceCharMessagePlayer(msg
    					.replaceAll("%number%", "1")
    					, p);
    		}
        	
        	Integer playerl = 1;
        	
			while (playerl <= 10) {
				if (plist.containsKey(playerl)) {
					p.sendMessage(plist.get(playerl));
				}
        		playerl++;
        	}
        	
        	for (String msg: OtherAMConfig.getConfig().getStringList("Command.List.Part-Two")) {
    			MessageUtils.ReplaceCharMessagePlayer(msg, p);
    		}
        } else if (args.length == 1) {
        	if (args[0].equals("0") || args[0].equals("1")) {
        		p.performCommand("list");
        	} else {
        		for (String msg: OtherAMConfig.getConfig().getStringList("Command.List.Part-One")) {
        			MessageUtils.ReplaceCharMessagePlayer(msg
        					.replaceAll("%number%", args[0])
        					, p);
        		}
        		
        		Integer pagedemande = (Integer.valueOf(args[0]) * 10) + 1;
        		Integer pagedemandefinale = pagedemande + 10 - 1;
        		
        		while (pagedemande <= pagedemandefinale) {
        			try {
        				if (plist.containsKey(pagedemande)) {
        					p.sendMessage(plist.get(pagedemande));
        				}
	    				pagedemande++;
        			} catch (Exception e) {
        				pagedemande++;
        			}
            	}
        		
        		for (String msg: OtherAMConfig.getConfig().getStringList("Command.List.Part-Two")) {
        			MessageUtils.ReplaceCharMessagePlayer(msg, p);
        		}
        	}
        } else {
        	p.sendMessage("§c/list [page number]");
        }

        return true;
    }

}