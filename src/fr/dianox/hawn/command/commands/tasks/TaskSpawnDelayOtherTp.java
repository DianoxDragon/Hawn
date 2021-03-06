package fr.dianox.hawn.command.commands.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.SpawnUtils;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;

public class TaskSpawnDelayOtherTp extends BukkitRunnable {
	
	private Player p, 
			other;
	String tp;
	
	public TaskSpawnDelayOtherTp(Player p, String tp, Player other) {
		/*
		 * Main class to catch the variables
		 */
        this.p = p;
        this.tp = tp;
        this.other = other;
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
			/*
			 * If the player is null or not online
			 * Cancel the task and stop the execution of the class
			 */
            p = null;
            cancel();
            return;
        }
		
		/*
		 * Then execute the code
		 */
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.CustomSpawn.Enable")) {
			// If the value of the config is CHANGE ME by default
			if (SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
				p.sendMessage("You have to change the spawn on Commands.CustomSpawn.Spawn on Events/OnJoin.yml");
			} else {
				// If the spawn is not set
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					MessageUtils.MessageNoSpawn(p);
					return;
				}
				
				// If player don't have the permission
				if (!p.hasPermission("hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				
				// Teleport
				SpawnUtils.teleportToSpawn(other, SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"));
				
				// Messages
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
						ConfigEventUtils.ExecuteEvent(other, msg, "", "", false);
					}
				}
			}
		} else {
			// If the value of the config is CHANGE ME by default
			if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
				p.sendMessage("You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml");
			} else {
				// If the spawn is not set
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					MessageUtils.MessageNoSpawn(p);
					return;
				}
				// If player don't have the permission
				if (!p.hasPermission("hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					String Permission = "hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				
				// Teleport
				SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
				
				// Messages
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
						ConfigEventUtils.ExecuteEvent(other, msg, "", "", false);
					}
				}
			}
		}
		
		Main.player_spawnwarpdelay.remove(other.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}