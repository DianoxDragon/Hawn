package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.WarpListConfig;
import fr.dianox.hawn.utility.config.configs.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelWarpCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.warp.delwarp";
	
	String msg_warp_dosent_exist = "Warp.Del.Warp-Doesnt-Exist.";
	String msg_warp_delete = "Warp.Del.Warp-Delete.";
	
	public DelWarpCommand(String name) {
		super(name);
		this.description = "Deletes the specified warp";
        this.usageMessage = "/delwarp <warp>";
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

		if (args.length == 1) {
			return new ArrayList<>(WarpListConfig.getConfig().getConfigurationSection("Coordinated").getKeys(false));
		}

		return null;
	}


	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (args.length == 0) {
				// If no argument has been put in the command
				if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
						MessageUtils.ConsoleMessages(msg);
					}
				}
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warplist");
			} else if (args.length == 1) {
				// If the warp does not exist
				if (!WarpListConfig.getConfig().isSet("Coordinated."+args[0]+".World")) {
					if (ConfigMMsg.getConfig().getBoolean(msg_warp_dosent_exist+"Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList(msg_warp_dosent_exist+"Messages")) {
							MessageUtils.ConsoleMessages(msg);
						}
					}
						
					return true;
				}
					
				// Execute the command
				WarpListConfig.getConfig().set("Coordinated."+args[0]+".World", null);
				WarpListConfig.getConfig().set("Coordinated."+args[0]+".X", null);
				WarpListConfig.getConfig().set("Coordinated."+args[0]+".Y", null);
				WarpListConfig.getConfig().set("Coordinated."+args[0]+".Z", null);
				WarpListConfig.getConfig().set("Coordinated."+args[0]+".Yaw", null);
				WarpListConfig.getConfig().set("Coordinated."+args[0]+".Pitch", null);
				WarpListConfig.getConfig().set("Coordinated."+args[0]+".Info", null);
				WarpListConfig.getConfig().set("Coordinated."+args[0], null);
	                
				WarpListConfig.saveConfigFile();
	                
				if (ConfigMMsg.getConfig().getBoolean(msg_warp_delete+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_warp_delete+"Messages")) {
						MessageUtils.ConsoleMessages(msg.replaceAll("%warp%", args[0]));
					}
				}
			} else {
				sender.sendMessage("§c/delwarp <warp>");
			}
			
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("DelWarp.Enable")) {
			if (WarpSetWarpCommandConfig.getConfig().getBoolean("DelWarp.Disable-Message")) {
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
			// If no argument has been put in the command
			if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
			if (p.hasPermission(WarpListCommand.GeneralPermission)) {
				p.performCommand("warplist");
			}
		} else if (args.length == 1) {
			// If the warp does not exist
			if (!WarpListConfig.getConfig().isSet("Coordinated."+args[0]+".World")) {
				if (ConfigMMsg.getConfig().getBoolean(msg_warp_dosent_exist+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_warp_dosent_exist+"Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
					
				return true;
			}
				
			// Execute the command
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".World", null);
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".X", null);
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Y", null);
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Z", null);
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Yaw", null);
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Pitch", null);
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Info", null);
			WarpListConfig.getConfig().set("Coordinated."+args[0], null);
                
			WarpListConfig.saveConfigFile();

			if (ConfigMMsg.getConfig().getBoolean(msg_warp_delete+"Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList(msg_warp_delete+"Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%warp%", args[0]), "", "", false);
				}
			}
		} else {
			p.sendMessage("§c/delwarp <warp>");
		}
		
		return false;
	}
	
}
