package fr.Dianox.Hawn.move;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Utility.MessageUtils;
import fr.dianox.hawn.Utility.Config.Commands.MuteChatCommandConfig;
import fr.dianox.hawn.Utility.Config.Messages.ConfigMCommands;
import fr.dianox.hawn.Utility.Config.Messages.ConfigMOStuff;

public class MuteChatCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.mutechat";
	
	public MuteChatCommand(String name) {
		super(name);
		this.description = "Mute the chat";
        this.usageMessage = "/gmute";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) {
				if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.Off")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player%", "console")));
						MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%player%", "console"));
					}
					MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
				} else {
					for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.On")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player%", "console")));
						MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%player%", "console"));
					}
					MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
				}
		    return true;
		    
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Enable")) {
			if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Disable-Message")) {
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
			
		// >> The command
					if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.Off")) {
							MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
						}
						MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
					} else {
						for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.On")) {
							MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
						}
						MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
					}
		
		
		return true;
	}

}
