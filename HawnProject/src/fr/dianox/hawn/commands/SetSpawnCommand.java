package fr.dianox.hawn.commands;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.SpawnUtils;
import fr.dianox.hawn.utility.config.ConfigSpawn;
import fr.dianox.hawn.utility.config.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.administration.SpawnMConfig;

public class SetSpawnCommand extends BukkitCommand {
	
	public SetSpawnCommand(String name) {
		super(name);
		this.description = "Creates a new spawn";
        this.usageMessage = "/setspawn <spawn>";
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
		
		if (!SpawnCommandConfig.getConfig().getBoolean("SetSpawn.Enable")) {
			if (SpawnCommandConfig.getConfig().getBoolean("SetSpawn.Disable-Message")) {
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
			if (!ConfigSpawn.getConfig().isSet("Coordinated.Spawn1")) {
				String spawnName = "Spawn1";
				Location l = p.getLocation();
				
				SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
                
                ConfigSpawn.saveConfigFile();
                
                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
                
                for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
				}
                
                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
                	OnJoinConfig.saveConfigFile();
                }
			} else {
                Integer number = 1;
                while (ConfigSpawn.getConfig().isSet("Coordinated.Spawn"+number)) {
                	number++;
                }
                String spawnName = "Spawn"+number;
                Location l = p.getLocation();
				
                SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
                
                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
                
                for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Default")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
				}
                
                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
                	OnJoinConfig.saveConfigFile();
                }
			}
		} else if (args.length == 1) {
			if (!ConfigSpawn.getConfig().isSet("Coordinated."+args[0])) {
				String spawnName = args[0];
				Location l = p.getLocation();
				
				SpawnUtils.createSpawn(p, spawnName, l.getWorld().getName(), l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
                
                ConfigSpawn.saveConfigFile();
                
                p.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(), (int) l.getZ());
                
                for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Spawn-Set.Other")) {
					MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%spawnName%", spawnName), p);
				}
                
                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                	OnJoinConfig.getConfig().set("Spawn.DefaultSpawn", String.valueOf(spawnName));
                	OnJoinConfig.saveConfigFile();
                }
			} else {
				for (String msg: SpawnMConfig.getConfig().getStringList("Command.Spawn.Name-already-exist")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		} else {
			p.sendMessage("§c/setspawn <spawn>");
		}
		
		return false;
	}
	
}