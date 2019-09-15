package fr.Dianox.Hawn.Commands.Features.Chat;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.DelayChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class DelaychatCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.delaychat";
	public static int delay = DelayChatCommandConfig.getConfig().getInt("DelayChat.Delay.Delay_By_Default");
	
	public DelaychatCommand(String name) {
		super(name);
		this.description = "Set a number to delay the chat";
        this.usageMessage = "/delaychat <delay>";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) {
			
			if (label.equalsIgnoreCase("delaychat") || label.equalsIgnoreCase("dchat")) {
				if (args.length == 1) {
					delay = Integer.parseInt(args[0]);
					for (String msg: ConfigMCommands.getConfig().getStringList("ChatDelay.Admin.Set")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player%", "CONSOLE").replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay))));
					}
				} else {
					if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
						for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Enable")) {
			if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Disable-Message")) {
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
		
					if (args.length == 1) {
						delay = Integer.parseInt(args[0]);
						for (String msg: ConfigMCommands.getConfig().getStringList("ChatDelay.Admin.Set")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					} else {
						if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
							for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					}
		
		return true;
	}
}