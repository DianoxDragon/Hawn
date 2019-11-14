package fr.dianox.hawn.commands.features.warp;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.OtherUtils;
import fr.dianox.hawn.utility.config.WarpListConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class SetWarpCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.warp.setwarp";
	
	String msg_setwarp_set = "Warp.Set.Warp-Set.";
	String msg_setwarp_alreadyexist = "Warp.Set.Warp-Already-Exist.";
	
	public SetWarpCommand(String name) {
		super(name);
		this.description = "Creates a new warp";
        this.usageMessage = "/setwarp <warp> [w:world1,world2 etc.]";
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
			createcomplexwarp(p, false, "", args[0]);
		} else if ((args.length == 2) && (args[1].startsWith("w:"))) {
			createcomplexwarp(p, true, args[1], args[0]);
		} else {
			p.sendMessage("§c/setwarp <warp> [w:world1,world2 etc.]");
		}
		
		return false;
	}
	
	private void createcomplexwarp(Player p, Boolean multiworld, String worldlist, String warpstring) {
		if (multiworld) {
			String worldlistformat = worldlist;
			
			worldlistformat = worldlistformat.replace("w:", "");
			
			String[] arr = worldlistformat.split(",");
						
			for (String ss : arr) {
				if (!WarpListConfig.getConfig().isSet("Coordinated."+warpstring)) {
					Location l = p.getLocation();
					
					WarpListConfig.getConfig().set("Coordinated."+warpstring+".World", ss);
					WarpListConfig.getConfig().set("Coordinated."+warpstring+".X", Double.valueOf(l.getX()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+".Y", Double.valueOf(l.getY()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+".Z", Double.valueOf(l.getZ()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+".Yaw", Float.valueOf(l.getYaw()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+".Pitch", Float.valueOf(l.getPitch()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+".Info", String.valueOf("Player "+p.getName()+" created the warp at: "+OtherUtils.getDate()+", "+OtherUtils.getTime()));
					
					WarpListConfig.saveConfigFile();
					
					if (ConfigMCommands.getConfig().getBoolean(msg_setwarp_set+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_setwarp_set+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg%", warpstring), p);
						}
					}
				} else {
					Integer number = 1;
					while (WarpListConfig.getConfig().isSet("Coordinated."+warpstring+number)) {
						number++;
					}
					Location l = p.getLocation();
					
					WarpListConfig.getConfig().set("Coordinated."+warpstring+number+".World", ss);
					WarpListConfig.getConfig().set("Coordinated."+warpstring+number+".X", Double.valueOf(l.getX()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+number+".Y", Double.valueOf(l.getY()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+number+".Z", Double.valueOf(l.getZ()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+number+".Yaw", Float.valueOf(l.getYaw()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+number+".Pitch", Float.valueOf(l.getPitch()));
					WarpListConfig.getConfig().set("Coordinated."+warpstring+number+".Info", String.valueOf("Player "+p.getName()+" created the warp at: "+OtherUtils.getDate()+", "+OtherUtils.getTime()));
						
					WarpListConfig.saveConfigFile();
			            
					if (ConfigMCommands.getConfig().getBoolean(msg_setwarp_set+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_setwarp_set+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg%", warpstring+number), p);
						}
					}
				}
			}
		} else {
			// If the warp already exist
			if (WarpListConfig.getConfig().isSet("Coordinated."+warpstring)) {
				if (ConfigMCommands.getConfig().getBoolean(msg_setwarp_alreadyexist+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_setwarp_alreadyexist+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
						
			// Set the warp
			Location l = p.getLocation();
						
			WarpListConfig.getConfig().set("Coordinated."+warpstring+".World", l.getWorld().getName());
			WarpListConfig.getConfig().set("Coordinated."+warpstring+".X", Double.valueOf(l.getX()));
			WarpListConfig.getConfig().set("Coordinated."+warpstring+".Y", Double.valueOf(l.getY()));
			WarpListConfig.getConfig().set("Coordinated."+warpstring+".Z", Double.valueOf(l.getZ()));
			WarpListConfig.getConfig().set("Coordinated."+warpstring+".Yaw", Float.valueOf(l.getYaw()));
			WarpListConfig.getConfig().set("Coordinated."+warpstring+".Pitch", Float.valueOf(l.getPitch()));
			WarpListConfig.getConfig().set("Coordinated."+warpstring+".Info", String.valueOf("Player "+p.getName()+" created the warp at: "+OtherUtils.getDate()+", "+OtherUtils.getTime()));
			
			WarpListConfig.saveConfigFile();
			
			if (ConfigMCommands.getConfig().getBoolean(msg_setwarp_set+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_setwarp_set+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg%", warpstring), p);
				}
			}
		}
	}
	
}