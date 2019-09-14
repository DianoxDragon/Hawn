package fr.Dianox.Hawn.Commands.Features.Warp;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WarpSetWarpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

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
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (args.length == 0) {
				// If no argument has been put in the command
				if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
					for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				}
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warplist");
			} else if (args.length == 1) {
				// If the warp does not exist
				if (!WarpListConfig.getConfig().isSet("Coordinated."+args[0]+".World")) {
					if (ConfigMCommands.getConfig().getBoolean(msg_warp_dosent_exist+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_warp_dosent_exist+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg);
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
	                
				if (ConfigMCommands.getConfig().getBoolean(msg_warp_delete+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_warp_delete+"Messages")) {
						MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%warp%", args[0]));
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
			// If no argument has been put in the command
			if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
			if (p.hasPermission(WarpListCommand.GeneralPermission)) {
				p.performCommand("warplist");
			}
		} else if (args.length == 1) {
			// If the warp does not exist
			if (!WarpListConfig.getConfig().isSet("Coordinated."+args[0]+".World")) {
				if (ConfigMCommands.getConfig().getBoolean(msg_warp_dosent_exist+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_warp_dosent_exist+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
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

			if (ConfigMCommands.getConfig().getBoolean(msg_warp_delete+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_warp_delete+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", args[0]), p);
				}
			}
		} else {
			p.sendMessage("§c/delwarp <warp>");
		}
		
		return false;
	}
	
}
