package fr.dianox.hawn.commands.features.warp;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.OtherUtils;
import fr.dianox.hawn.utility.config.WarpListConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class SetWarpCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.warp.setwarp";
	
	String msg_setwarp_set = "Warp.Set.Warp-Set.";
	String msg_setwarp_alreadyexist = "Warp.Set.Warp-Already-Exist.";
	
	public SetWarpCommand(String name) {
		super(name);
		this.description = "Creates a new warp";
        this.usageMessage = "/setwarp <warp>";
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
		
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("SetWarp.Enable")) {
			if (WarpSetWarpCommandConfig.getConfig().getBoolean("SetWarp.Disable-Message")) {
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
		} else if (args.length == 1) {
			// If the warp already exist
			if (WarpListConfig.getConfig().isSet("Coordinated."+args[0])) {
				if (ConfigMCommands.getConfig().getBoolean(msg_setwarp_alreadyexist+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_setwarp_alreadyexist+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
			
			// Set the warp
			Location l = p.getLocation();
					
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".World", l.getWorld().getName());
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".X", Double.valueOf(l.getX()));
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Y", Double.valueOf(l.getY()));
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Z", Double.valueOf(l.getZ()));
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Yaw", Float.valueOf(l.getYaw()));
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Pitch", Float.valueOf(l.getPitch()));
			WarpListConfig.getConfig().set("Coordinated."+args[0]+".Info", String.valueOf("Player "+p.getName()+" created the warp at: "+OtherUtils.getDate()+", "+OtherUtils.getTime()));
			                
			WarpListConfig.saveConfigFile();
			                
			p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
					
			if (ConfigMCommands.getConfig().getBoolean(msg_setwarp_set+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_setwarp_set+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg%", args[0]), p);
				}
			}
		} else {
			p.sendMessage("§c/setwarp <warp>");
		}
		
		return false;
	}
	
}