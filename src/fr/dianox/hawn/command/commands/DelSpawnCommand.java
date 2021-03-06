package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class DelSpawnCommand extends BukkitCommand {
	
	public DelSpawnCommand(String name) {
		super(name);
		this.description = "Delete a spawn";
        this.usageMessage = "/delspawn <spawn>";
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

		if (args.length == 1) {
			return new ArrayList<>(ConfigSpawn.getConfig().getConfigurationSection("Coordinated").getKeys(false));
		}

		return null;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			
			if (args.length == 0) {
				// If no argument has been put in the command
				if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
						MessageUtils.ConsoleMessages(msg);
					}
				}
			} else if (args.length == 1) {
				// If the spawn does not exist
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[0]+".World")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("Error.No-Spawn")) {
						MessageUtils.ConsoleMessages(msg);
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
				
				for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Del.Spawn-Delete")) {
					MessageUtils.ConsoleMessages(msg.replaceAll("%spawn%", args[0]));
				}
			} else {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Delspawn")) {
					MessageUtils.ConsoleMessages(msg);
				}
			}
			
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!SpawnCommandConfig.getConfig().getBoolean("DelSpawn.Enable")) {
			if (SpawnCommandConfig.getConfig().getBoolean("DelSpawn.Disable-Message")) {
				if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
			if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
		} else if (args.length == 1) {
			// If the spawn does not exist
			if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[0]+".World")) {
				for (String msg: ConfigMAdmin.getConfig().getStringList("Error.No-Spawn")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
			
			for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Del.Spawn-Delete")) {
				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%spawn%", args[0]), "", "", false);
			}
		} else {
			for (String msg: ConfigMAdmin.getConfig().getStringList("Error.Command.Delspawn")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		}
		
		return false;
	}
	
}