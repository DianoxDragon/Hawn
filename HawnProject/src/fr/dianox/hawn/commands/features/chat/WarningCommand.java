package fr.dianox.hawn.commands.features.chat;

import java.util.Objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.commands.WarningCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class WarningCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.warning";
	int partlenght = 0;
	Boolean partsenabled = false;
	
	public WarningCommand(String name) {
		super(name);
		this.description = "Warning a message";
        this.usageMessage = "/warning <msg>";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
            	}
                return true;
			}
			
			String msgbc = "";
			
			if (args.length >= 1) {
                if (!args[0].isEmpty()) {
                	for (int i = 1; i < args.length; i++) {
                		if (!Objects.equals(msgbc, "")) {
                			msgbc = msgbc + " ";
                		}
                		msgbc = msgbc + args[i];
                	}
                }
            }
			
			msgbc = args[0] + " " + msgbc;
			
			for (String msg: ConfigMCommands.getConfig().getStringList("Broadcast")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%broadcast%", msgbc)));
				MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%broadcast%", msgbc));
			}
			
			if (WarningCommandConfig.getConfig().getBoolean("Warning.Sounds.Enabled")) {
	            for (Player player: Bukkit.getServer().getOnlinePlayers()) {
	                String sound = WarningCommandConfig.getConfig().getString("Warning.Sounds.Sound");
	                int volume = WarningCommandConfig.getConfig().getInt("Warning.Sounds.Volume");
	                int pitch = WarningCommandConfig.getConfig().getInt("Warning.Sounds.Pitch");
	                player.playSound(player.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
	            }
			}
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!WarningCommandConfig.getConfig().getBoolean("Warning.Enable")) {
			if (WarningCommandConfig.getConfig().getBoolean("Warning.Disable-Message")) {
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
			if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
            return true;
		}
		
		String msgbc = "";
		
		if (args.length >= 1) {
            if (!args[0].isEmpty()) {
            	for (int i = 1; i < args.length; i++) {
            		if (!Objects.equals(msgbc, "")) {
            			msgbc = msgbc + " ";
            		}
            		msgbc = msgbc + args[i];
            	}
            }
        }
		
		msgbc = args[0] + " " + msgbc;
		
		for (String msg: ConfigMCommands.getConfig().getStringList("Warning")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%broadcast%", msgbc)));
			MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%broadcast%", msgbc));
		}
		
		if (WarningCommandConfig.getConfig().getBoolean("Warning.Sounds.Enabled")) {
            for (Player player: Bukkit.getServer().getOnlinePlayers()) {
                String sound = WarningCommandConfig.getConfig().getString("Warning.Sounds.Sound");
                int volume = WarningCommandConfig.getConfig().getInt("Warning.Sounds.Volume");
                int pitch = WarningCommandConfig.getConfig().getInt("Warning.Sounds.Pitch");
                player.playSound(player.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
            }
		}
		
		return true;
	}

}
