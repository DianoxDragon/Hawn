package fr.Dianox.Hawn.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.ErrorConfigAM;

public class DelSpawnCommand extends BukkitCommand {
	
	public DelSpawnCommand(String name) {
		super(name);
		this.description = "Delete a spawn";
        this.usageMessage = "/delspawn <spawn>";
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
		
		if (!SpawnCommandConfig.getConfig().getBoolean("DelSpawn.Enable")) {
			if (SpawnCommandConfig.getConfig().getBoolean("DelSpawn.Disable-Message")) {
				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                	}
    			}
			}
			
			return true;
		}
		
		if (!p.hasPermission("hawn.admin") && !p.hasPermission("hawn.admin.*")) {
			MessageUtils.MessageNoPermission(p, "hawn.admin");
			return true;
		}
		
		// The command
		if (args.length == 0) {
			// If no argument has been put in the command
			if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		} else if (args.length == 1) {
			// If the spawn does not exist
			if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[0]+".World")) {
				for (String msg: ErrorConfigAM.getConfig().getStringList("Error.No-Spawn")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
				
				return true;
			}
			
			// Execute the command
			ConfigSpawn.getConfig().set("Coordinated."+args[0]+".World", null);
			ConfigSpawn.getConfig().set("Coordinated."+args[0]+".X", null);
			ConfigSpawn.getConfig().set("Coordinated."+args[0]+".Y", null);
			ConfigSpawn.getConfig().set("Coordinated."+args[0]+".Z", null);
			ConfigSpawn.getConfig().set("Coordinated."+args[0]+".Yaw", null);
			ConfigSpawn.getConfig().set("Coordinated."+args[0]+".Pitch", null);
			ConfigSpawn.getConfig().set("Coordinated."+args[0]+".Info", null);
			ConfigSpawn.getConfig().set("Coordinated."+args[0], null);
                
			ConfigSpawn.saveConfigFile();
			
			for (String msg: ErrorConfigAM.getConfig().getStringList("Command.Del.Spawn-Delete")) {
				MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawn%", args[0]), p);
			}
		} else {
			for (String msg: ErrorConfigAM.getConfig().getStringList("Error.Command.Delspawn")) {
				MessageUtils.ReplaceCharMessagePlayer(msg, p);
			}
		}
		
		return false;
	}
	
}
