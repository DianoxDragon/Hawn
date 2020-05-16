package fr.dianox.hawn.command.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.modules.admin.ListGui;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.ListCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.messages.ConfigMAdmin;

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
        	
        	Integer checklist = 1;
            
        	plist.clear();
        	
            for (Player all: Bukkit.getServer().getOnlinePlayers()) {
            	plist.put(checklist, all.getName());
            	checklist++;
            }
        	
        	if (args.length == 0) {
            	for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-One")) {
        			MessageUtils.ConsoleMessages(msg
        					.replaceAll("%number%", "1"));
        		}
            	
            	Integer playerl = 1;
            	
    			while (playerl <= 10) {
    				if (plist.containsKey(playerl)) {
    					sender.sendMessage(plist.get(playerl));
    				}
            		playerl++;
            	}
            	
            	for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-Two")) {
        			MessageUtils.ConsoleMessages(msg);
        		}
            } else if (args.length == 1) {
            	if (args[0].equals("0") || args[0].equals("1")) {
            		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-One")) {
            			MessageUtils.ConsoleMessages(msg
            					.replaceAll("%number%", "1"));
            		}
                	
                	Integer playerl = 1;
                	
        			while (playerl <= 10) {
        				if (plist.containsKey(playerl)) {
        					sender.sendMessage(plist.get(playerl));
        				}
                		playerl++;
                	}
                	
                	for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-Two")) {
            			MessageUtils.ConsoleMessages(msg);
            		}
            	} else {
            		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-One")) {
            			MessageUtils.ConsoleMessages(msg
            					.replaceAll("%number%", args[0]));
            		}
            		
            		Integer pagedemande = (Integer.valueOf(args[0]) * 10) + 1;
            		Integer pagedemandefinale = pagedemande + 10 - 1;
            		
            		while (pagedemande <= pagedemandefinale) {
            			try {
            				if (plist.containsKey(pagedemande)) {
            					sender.sendMessage(plist.get(pagedemande));
            				}
    	    				pagedemande++;
            			} catch (Exception e) {
            				pagedemande++;
            			}
                	}
            		
            		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-Two")) {
            			MessageUtils.ConsoleMessages(msg);
            		}
            	}
            } else {
            	sender.sendMessage("§c/list [page number]");
            }
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!ListCommandConfig.getConfig().getBoolean("List.Enable")) {
            if (ListCommandConfig.getConfig().getBoolean("List.Disable-Message")) {
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
        
        Integer checklist = 1;
        
    	plist.clear();
    	
        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
        	plist.put(checklist, all.getName());
        	checklist++;
        }
        
        // The command
        if (args.length == 0) {
        	
        	if (ListCommandConfig.getConfig().getBoolean("List.Gui-Version")) {
        		ListGui.OpenGui(p, 1);
        		return true;
        	}
        	
        	for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-One")) {
    			ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%number%", "1"), "", "", false);
    		}
        	
        	Integer playerl = 1;
        	
			while (playerl <= 10) {
				if (plist.containsKey(playerl)) {
					p.sendMessage(plist.get(playerl));
				}
        		playerl++;
        	}
        	
        	for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-Two")) {
    			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
    		}
        } else if (args.length == 1) {
        	            
        	if (ListCommandConfig.getConfig().getBoolean("List.Gui-Version")) {
        		ListGui.OpenGui(p, Integer.valueOf(args[0]));
        		return true;
        	}
        	        	
        	if (args[0].equals("0") || args[0].equals("1")) {
        		p.performCommand("list");
        	} else {
        		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-One")) {
        			ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%number%", args[0]), "", "", false);
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
        		
        		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.List.Part-Two")) {
        			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
        		}
        	}
        } else {
        	p.sendMessage("§c/list [page number]");
        }
        

        return true;
    }

}