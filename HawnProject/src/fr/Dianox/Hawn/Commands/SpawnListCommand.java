package fr.Dianox.Hawn.Commands;

import java.util.Iterator;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class SpawnListCommand extends BukkitCommand{
	
	public static String GeneralPermission = "hawn.command.spawn.spawnlist";
	
	String msg_listspawn_list = "Spawn.List.";
	String msg_nospawn = "Spawn.No-Spawn.";
	
	public SpawnListCommand(String name) {
		super(name);
		this.description = "List all spawns";
        this.usageMessage = "/spawnlist";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			try {
				if (ConfigSpawn.getConfig().getConfigurationSection("Coordinated").getKeys(false) != null) {
					
					Iterator<?> iterator = ConfigSpawn.getConfig().getConfigurationSection("Coordinated").getKeys(false).iterator();
					
					String spawnlist = "";
					Boolean firstword = true;
					
					while (iterator.hasNext()) {
						String string = (String)iterator.next();
						
						if (firstword == true) {
							spawnlist = String.valueOf(string);
							firstword = false;
						} else {
							spawnlist = String.valueOf(spawnlist + ", " +string);
						}
					}
										
					if (ConfigMCommands.getConfig().getBoolean(msg_listspawn_list+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_listspawn_list+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%spawnlist%", spawnlist));
						}
					}
					
				}
			} catch (NullPointerException e) {
				if (ConfigMCommands.getConfig().getBoolean(msg_nospawn+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_nospawn+"Messages")) {
						MessageUtils.ReplaceMessageForConsole(msg);
					}
				}
			}
			 
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!SpawnCommandConfig.getConfig().getBoolean("SpawnList.Enable")) {
			if (SpawnCommandConfig.getConfig().getBoolean("SpawnList.Disable-Message")) {
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
		try {
			if (ConfigSpawn.getConfig().getConfigurationSection("Coordinated").getKeys(false) != null) {
				
				Iterator<?> iterator = ConfigSpawn.getConfig().getConfigurationSection("Coordinated").getKeys(false).iterator();
				
				String spawnlist = "";
				Boolean firstword = true;
				
				while (iterator.hasNext()) {
					String string = (String)iterator.next();
					
					if (p.hasPermission("hawn.spawn."+string)) {
						if (firstword == true) {
							spawnlist = String.valueOf(string);
							firstword = false;
						} else {
							spawnlist = String.valueOf(spawnlist + ", " +string);
						}
					}
				}
								
				if (ConfigMCommands.getConfig().getBoolean(msg_listspawn_list+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_listspawn_list+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnlist%", spawnlist), p);
					}
				}
			}
		} catch (NullPointerException e) {
			if (ConfigMCommands.getConfig().getBoolean(msg_nospawn+"Enable")) {
				for (String msg: ConfigMCommands.getConfig().getStringList(msg_nospawn+"Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		}
		
		return false;
	}

}
