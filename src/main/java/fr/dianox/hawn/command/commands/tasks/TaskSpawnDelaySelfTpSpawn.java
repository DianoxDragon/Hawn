package fr.dianox.hawn.command.commands.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.SpawnUtils;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;

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
		// 50: done, refused or failed, the player teleported is no longer waiting
		try {
			teleport();
		} finally {
			Main.endTeleportDelay(p);
		}
	}

	private void teleport() {
		if (!p.isOnline()) {
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
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
		}
		
	}

}