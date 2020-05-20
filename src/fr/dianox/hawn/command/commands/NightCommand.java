package fr.dianox.hawn.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.WeatherTimeCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

public class NightCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.time.night";
	
	String universal_link = "Time.Set.Night.";
	
	public NightCommand(String name) {
		super(name);
		this.description = "Put the night";
        this.usageMessage = "/night";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ConsoleMessages(msg);
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!WeatherTimeCommandConfig.getConfig().getBoolean(universal_link+"Enable")) {
			if (WeatherTimeCommandConfig.getConfig().getBoolean(universal_link+"Disable-Message")) {
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
			p.getWorld().setTime(WeatherTimeCommandConfig.getConfig().getLong(universal_link+"Value"));
			if (ConfigMMsg.getConfig().getBoolean(universal_link+"Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList(universal_link+"Message")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
		} else {
			p.sendMessage("§c/night");
		}
		
		return false;
	}
	
}