package fr.dianox.hawn.commands;

import java.util.Iterator;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.WarpListConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

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
										
					if (ConfigMMsg.getConfig().getBoolean(msg_listwarp_list+"Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList(msg_listwarp_list+"Messages")) {
							MessageUtils.ConsoleMessages(msg.replaceAll("%warplist%", warplist));
						}
					}
					
				}
			} catch (NullPointerException e) {
				if (ConfigMMsg.getConfig().getBoolean(msg_nowarp+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_nowarp+"Messages")) {
						MessageUtils.ConsoleMessages(msg);
					}
				}
			}
			 
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("WarpList.Enable")) {
			if (WarpSetWarpCommandConfig.getConfig().getBoolean("WarpList.Disable-Message")) {
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
									
				if (ConfigMMsg.getConfig().getBoolean(msg_listwarp_list+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_listwarp_list+"Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%warplist%", warplist), "", "", false);
					}
				}
			}
		} catch (NullPointerException e) {
			if (ConfigMMsg.getConfig().getBoolean(msg_nowarp+"Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList(msg_nowarp+"Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
		}
		
		return false;
	}

}
