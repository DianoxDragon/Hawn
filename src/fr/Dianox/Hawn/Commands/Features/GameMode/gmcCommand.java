package fr.Dianox.Hawn.Commands.Features.GameMode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.GamemodeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class gmcCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.gamemode.self";
	String GeneralOtherPermission = "hawn.command.gamemode.other";
	
	String Command_section = "gmc.";
	
	String msg_self = "Gamemode.Self.Creative.";
	
	String msg_other = "Gamemode.Other.Creative.";
	
	String msg_other_sender = "Gamemode.Other-Sender.Creative.";
	
	public gmcCommand(String name) {
		super(name);
		this.description = "Easily change the gamemode";
        this.usageMessage = "/gmc";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);

				if (target == null) {
					if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
						for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
					return true;
				}
				
				target.setGameMode(GameMode.CREATIVE);
					
				if (ConfigMCommands.getConfig().getBoolean(msg_other+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
					}
				}
					
				if (ConfigMCommands.getConfig().getBoolean(msg_other_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_sender+"Messages")) {
						MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
					}
				}

			} else {
				sender.sendMessage("§c/gmc <player>");
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!GamemodeCommandConfig.getConfig().getBoolean(Command_section+"Enable")) {
			if (GamemodeCommandConfig.getConfig().getBoolean(Command_section+"Disable-Message")) {
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
			// Self gamemode
			p.setGameMode(GameMode.CREATIVE);
			if (ConfigMCommands.getConfig().getBoolean(msg_self+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_self+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		} else if (args.length == 1) {
			// Gamemode for others players
			if (!p.hasPermission(GeneralOtherPermission)) {
				MessageUtils.MessageNoPermission(p, GeneralPermission);
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);

			if (target == null) {
				MessageUtils.PlayerDoesntExist(p);
				return true;
			}
			
			target.setGameMode(GameMode.CREATIVE);
				
			if (ConfigMCommands.getConfig().getBoolean(msg_other+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_other+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), p);
				}
			}
				
			if (ConfigMCommands.getConfig().getBoolean(msg_other_sender+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_sender+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
				}
			}

		} else {
			p.sendMessage("§c/gmc");
		}
		
		return false;
	}
	
}
