package fr.dianox.hawn.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.DelayChatCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

import java.util.List;

public class DelaychatCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.delaychat";
	public static int delay = DelayChatCommandConfig.getConfig().getInt("DelayChat.Delay.Delay_By_Default");
	
	public DelaychatCommand(String name) {
		super(name);
		this.description = "Set a number to delay the chat";
        this.usageMessage = "/delaychat <delay>";
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) {
			
			if (label.equalsIgnoreCase("delaychat") || label.equalsIgnoreCase("dchat")) {
				if (args.length == 1) {
					delay = Integer.parseInt(args[0]);
					for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Admin.Set")) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player%", "CONSOLE").replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay))));
					}
				} else {
					if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Enable") && DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Enable")) {
						DelayChatCommandConfig.getConfig().set("DelayChat.Delay.Enable", false);
						DelayChatCommandConfig.saveConfigFile();
						
						for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Admin.Removed")) {
							MessageUtils.ConsoleMessages(msg);
						}
					} else {
						if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
								MessageUtils.ConsoleMessages(msg);
							}
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
		
		if (args.length == 1) {
			delay = Integer.parseInt(args[0]);
			for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Admin.Set")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		} else {
			if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Enable") && DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Enable")) {
				DelayChatCommandConfig.getConfig().set("DelayChat.Delay.Enable", false);
				DelayChatCommandConfig.saveConfigFile();
				
				for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Admin.Removed")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			} else {
				if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
			}
		}
		
		return true;
	}
}