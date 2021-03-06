package fr.dianox.hawn.command.commands.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.SpawnUtils;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;

public class TaskSpawnDelayOtherTp2 extends BukkitRunnable {
	
	private Player p,
			other;
	String tp;
	
	public TaskSpawnDelayOtherTp2(Player p, String tp, Player other) {
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
		SpawnUtils.teleportToSpawn(other, tp);
		
		// Messages
		for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
		}
		if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
			for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
				ConfigEventUtils.ExecuteEvent(other, msg, "", "", false);
			}
		}
		
		Main.player_spawnwarpdelay.remove(other.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}