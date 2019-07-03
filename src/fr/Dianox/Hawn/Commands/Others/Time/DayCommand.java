package fr.Dianox.Hawn.Commands.Others.Time;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.WeatherTimeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class DayCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.time.day";
	
	String universal_link = "Time.Set.Day.";
	
	public DayCommand(String name) {
		super(name);
		this.description = "Put the day";
        this.usageMessage = "/day";
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
			p.getWorld().setTime(WeatherTimeCommandConfig.getConfig().getLong(universal_link+"Value"));
			if (ConfigMCommands.getConfig().getBoolean(universal_link+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(universal_link+"Message")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		} else {
			p.sendMessage("§c/day");
		}
		
		return false;
	}
	
}