package fr.dianox.hawn.commands;

import java.util.Iterator;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.ConfigSpawn;
import fr.dianox.hawn.utility.config.commands.SpawnCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

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
										
					if (ConfigMMsg.getConfig().getBoolean(msg_listspawn_list+"Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList(msg_listspawn_list+"Messages")) {
							MessageUtils.ConsoleMessages(msg.replaceAll("%spawnlist%", spawnlist));
						}
					}
					
				}
			} catch (NullPointerException e) {
				if (ConfigMMsg.getConfig().getBoolean(msg_nospawn+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_nospawn+"Messages")) {
						MessageUtils.ConsoleMessages(msg);
					}
				}
			}
			 
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!SpawnCommandConfig.getConfig().getBoolean("SpawnList.Enable")) {
			if (SpawnCommandConfig.getConfig().getBoolean("SpawnList.Disable-Message")) {
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
								
				if (ConfigMMsg.getConfig().getBoolean(msg_listspawn_list+"Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList(msg_listspawn_list+"Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%spawnlist%", spawnlist), "", "", false);
					}
				}
			}
		} catch (NullPointerException e) {
			if (ConfigMMsg.getConfig().getBoolean(msg_nospawn+"Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList(msg_nospawn+"Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
		}
		
		return false;
	}

}
