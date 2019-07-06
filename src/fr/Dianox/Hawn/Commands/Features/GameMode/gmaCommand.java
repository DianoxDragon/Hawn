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

public class gmaCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.gamemode.self";
	String GeneralOtherPermission = "hawn.command.gamemode.other";
	
	String Command_section = "gma.";
	
	String msg_self = "Gamemode.Self.Adventure.";
	
	String msg_other = "Gamemode.Other.Adventure.";
	
	String msg_other_sender = "Gamemode.Other-Sender.Adventure.";
	
	public gmaCommand(String name) {
		super(name);
		this.description = "Easily change the gamemode";
        this.usageMessage = "/gma";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg, sender);
				}
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
			p.setGameMode(GameMode.ADVENTURE);
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
			
			target.setGameMode(GameMode.ADVENTURE);
				
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
			p.sendMessage("§c/gma");
		}
		
		return false;
	}
	
}