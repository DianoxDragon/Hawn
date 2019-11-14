package fr.dianox.hawn.commands.features.warp;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.OtherUtils;
import fr.dianox.hawn.utility.config.WarpListConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class EditWarpCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.warp.editwarp";
	
	String msg_warp_dosent_exist = "Warp.Del.Warp-Doesnt-Exist.";
	String msg_warp_edited = "Warp.Edit.Warp-Edited.";
	
	public EditWarpCommand(String name) {
		super(name);
		this.description = "Edit the specified warp";
        this.usageMessage = "/editwarp <warp>";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("EditWarp.Enable")) {
			if (WarpSetWarpCommandConfig.getConfig().getBoolean("EditWarp.Disable-Message")) {
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
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".World", p.getLocation().getWorld().getName());
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".X", p.getLocation().getX());
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Y", p.getLocation().getY());
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Z", p.getLocation().getZ());
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Yaw", p.getLocation().getYaw());
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Pitch", p.getLocation().getPitch());
			
			String info = WarpListConfig.getConfig().getString("Coordinated."+args[0]+".Info");
			
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Info", info + " || Player "+p.getName()+" edited the warp at: "+OtherUtils.getDate()+", "+OtherUtils.getTime());
                
			WarpListConfig.saveConfigFile();

			if (ConfigMCommands.getConfig().getBoolean(msg_warp_edited+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_warp_edited+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warp%", args[0]), p);
				}
			}
		} else {
			p.sendMessage("§c/editwarp <warp>");
		}
		
		return false;
	}
	
}
