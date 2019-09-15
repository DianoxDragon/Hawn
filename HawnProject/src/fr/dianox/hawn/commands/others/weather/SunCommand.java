package fr.dianox.hawn.commands.others.weather;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.WeatherTimeCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class SunCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.weather.sun";
	
	String universal_link = "Weather.Set.Sun.";
	
	public SunCommand(String name) {
		super(name);
		this.description = "Put the sun";
        this.usageMessage = "/sun or /clearw";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!WeatherTimeCommandConfig.getConfig().getBoolean(universal_link+"Enable")) {
			if (WeatherTimeCommandConfig.getConfig().getBoolean(universal_link+"Disable-Message")) {
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
			p.getWorld().setThundering(false);
			p.getWorld().setStorm(false);
			if (ConfigMCommands.getConfig().getBoolean(universal_link+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(universal_link+"Message")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		} else {
			p.sendMessage("§c/sun or /clearw");
		}
		
		return false;
	}
	
}