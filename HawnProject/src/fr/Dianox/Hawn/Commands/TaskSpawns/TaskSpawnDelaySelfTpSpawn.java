package fr.Dianox.Hawn.Commands.TaskSpawns;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;

public class TaskSpawnDelaySelfTpSpawn extends BukkitRunnable {
	
	private Player p;
	String tp;
	
	public TaskSpawnDelaySelfTpSpawn(Player p, String tp) {
		/*
		 * Main class to catch the variables
		 */
        this.p = p;
        this.tp = tp;
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
		// Check if hawn.command.spawn is enabled
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Use-Permission")) {
			if (p.hasPermission("hawn.command.spawn")) {
				// If the spawn is not set
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
					MessageUtils.MessageNoSpawn(p);
					return;
				}
				
				// If player don't have the permission
				if (!p.hasPermission("hawn.command.spawn."+tp)) {
					String Permission = "hawn.command.spawn."+tp;
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				
				// Teleport
				SpawnUtils.teleportToSpawn(p, tp);
				
				// Messages
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else {
				// If player don't have the permission
				MessageUtils.MessageNoPermission(p, "hawn.command.spawn");
			}
		} else {
			// If the spawn is not set
			if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
				MessageUtils.MessageNoSpawn(p);
				return;
			}
			
			// If player don't have the permission
			if (!p.hasPermission("hawn.command.spawn."+tp)) {
				String Permission = "hawn.command.spawn."+tp;
				MessageUtils.MessageNoPermission(p, Permission);
				return;
			}
			
			// Teleport
			SpawnUtils.teleportToSpawn(p, tp);
			
			// Messages
			if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		}
		
		Main.player_spawnwarpdelay.remove(p.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}