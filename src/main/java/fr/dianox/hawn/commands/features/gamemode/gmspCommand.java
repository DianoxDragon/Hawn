package fr.dianox.hawn.commands.features.gamemode;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.GamemodeCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class gmspCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.gamemode.self";
	String GeneralOtherPermission = "hawn.command.gamemode.other";
	
	String Command_section = "gmsp.";
	
	String msg_self = "Gamemode.Self.Spectator.";
	
	String msg_other = "Gamemode.Other.Spectator.";
	
	String msg_other_sender = "Gamemode.Other-Sender.Spectator.";
	
	public gmspCommand(String name) {
		super(name);
		this.description = "Easily change the gamemode";
        this.usageMessage = "/gmsp";
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
				
				if (target.getGameMode() == GameMode.SPECTATOR) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
						}
					}
					return true;
				}
				
				target.setGameMode(GameMode.SPECTATOR);
					
				if (ConfigMCommands.getConfig().getBoolean(msg_other+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
					}
				}
					
				if (ConfigMCommands.getConfig().getBoolean(msg_other_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_sender+"Messages")) {
						MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", target.getName()).replaceAll("%target%", target.getName()));
					}
				}

			} else {
				sender.sendMessage("§c/gmsp <player>");
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
			if (p.getGameMode() == GameMode.SPECTATOR) {
				if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
				return true;
			}
			
			// Self gamemode
			p.setGameMode(GameMode.SPECTATOR);
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
			
			if (target.getGameMode() == GameMode.SPECTATOR) {
				if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
					}
				}
				return true;
			}
			
			target.setGameMode(GameMode.SPECTATOR);
				
			if (ConfigMCommands.getConfig().getBoolean(msg_other+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_other+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), target);
				}
			}
				
			if (ConfigMCommands.getConfig().getBoolean(msg_other_sender+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_sender+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
				}
			}

		} else {
			p.sendMessage("§c/gmsp");
		}
		
		return false;
	}
	
}