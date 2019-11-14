package fr.dianox.hawn.commands.features.warp;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.WarpListConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class WarpListCommand extends BukkitCommand{
	
	public static String GeneralPermission = "hawn.command.warp.warplist";
	
	String msg_listwarp_list = "Warp.List.";
	String msg_nowarp = "Warp.No-Warp.";
	
	public WarpListCommand(String name) {
		super(name);
		this.description = "List all warps";
        this.usageMessage = "/warplist";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			try {
				if (WarpListConfig.getConfig().getConfigurationSection("Coordinated").getKeys(false) != null) {
					
					Iterator<?> iterator = WarpListConfig.getConfig().getConfigurationSection("Coordinated").getKeys(false).iterator();
					
					String warplist = "";
					Boolean firstword = true;
					
					while (iterator.hasNext()) {
						String string = (String)iterator.next();
						
						if (firstword == true) {
							warplist = String.valueOf(string);
							firstword = false;
						} else {
							warplist = String.valueOf(warplist + ", " +string);
						}
					}
										
					if (ConfigMCommands.getConfig().getBoolean(msg_listwarp_list+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_listwarp_list+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%warplist%", warplist));
						}
					}
					
				}
			} catch (NullPointerException e) {
				if (ConfigMCommands.getConfig().getBoolean(msg_nowarp+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_nowarp+"Messages")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				}
			}
			 
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("WarpList.Enable")) {
			if (WarpSetWarpCommandConfig.getConfig().getBoolean("WarpList.Disable-Message")) {
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
		try {
			if (WarpListConfig.getConfig().getConfigurationSection("Coordinated").getKeys(false) != null) {
				
				Iterator<?> iterator = WarpListConfig.getConfig().getConfigurationSection("Coordinated").getKeys(false).iterator();
				
				String warplist = "";
				Boolean firstword = true;
				
				while (iterator.hasNext()) {
					String string = (String)iterator.next();
					
					if (p.hasPermission("hawn.warp."+string)) {
						if (firstword == true) {
							warplist = String.valueOf(string);
							firstword = false;
						} else {
							warplist = String.valueOf(warplist + ", " +string);
						}
					}
				}
									
				if (ConfigMCommands.getConfig().getBoolean(msg_listwarp_list+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_listwarp_list+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%warplist%", warplist), p);
					}
				}
			}
		} catch (NullPointerException e) {
			if (ConfigMCommands.getConfig().getBoolean(msg_nowarp+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_nowarp+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		}
		
		return false;
	}

}
