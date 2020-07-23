package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.GamemodeCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

import java.util.ArrayList;
import java.util.List;

public class gmsCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.gamemode.self";
	String GeneralOtherPermission = "hawn.command.gamemode.other";
	
	String Command_section = "gms.";
	
	String msg_self = "Gamemode.Self.Survival.";
	
	String msg_other = "Gamemode.Other.Survival.";
	
	String msg_other_sender = "Gamemode.Other-Sender.Survival.";
	
	public gmsCommand(String name) {
		super(name);
		this.description = "Easily change the gamemode";
        this.usageMessage = "/gms";
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

		if (args.length == 1) {
			List<String> tab = new ArrayList<>();
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				tab.add(p.getName());
			}

			java.util.Collections.sort(tab);

			return tab;
		}

		return null;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (args.length == 1) {
				Player target = Bukkit.getServer().getPlayer(args[0]);

				if (target == null) {
					if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
							MessageUtils.ConsoleMessages(msg);
						}
					}
					return true;
				}
				
				if (target.getGameMode() == GameMode.SURVIVAL) {
					if (ConfigMMsg.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
							MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()));
						}
					}
					return true;
				}
				
				target.setGameMode(GameMode.SURVIVAL);
					
				if (ConfigMMsg.getConfig().getBoolean(msg_other+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_other+"Messages")) {
						ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
					}
				}
					
				if (ConfigMMsg.getConfig().getBoolean(msg_other_sender+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_sender+"Messages")) {
						MessageUtils.ConsoleMessages(msg.replaceAll("%player%", target.getName()).replaceAll("%target%", target.getName()));
					}
				}

			} else {
				sender.sendMessage("§c/gms <player>");
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!GamemodeCommandConfig.getConfig().getBoolean(Command_section+"Enable")) {
			if (GamemodeCommandConfig.getConfig().getBoolean(Command_section+"Disable-Message")) {
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
		if (args.length == 0) {
			// Self gamemode
			if (p.getGameMode() == GameMode.SURVIVAL) {
				if (ConfigMMsg.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
				return true;
			}
			
			p.setGameMode(GameMode.SURVIVAL);
			if (ConfigMMsg.getConfig().getBoolean(msg_self+"Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList(msg_self+"Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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

			if (target.getGameMode() == GameMode.SURVIVAL) {
				if (ConfigMMsg.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
					}
				}
				return true;
			}
			
			target.setGameMode(GameMode.SURVIVAL);
				
			if (ConfigMMsg.getConfig().getBoolean(msg_other+"Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList(msg_other+"Messages")) {
					ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
				}
			}
				
			if (ConfigMMsg.getConfig().getBoolean(msg_other_sender+"Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_sender+"Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
				}
			}

		} else {
			p.sendMessage("§c/gms");
		}
		
		return false;
	}
	
}