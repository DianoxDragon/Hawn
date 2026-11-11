package fr.dianox.hawn.command.commands.tasks;

import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.SpawnUtils;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;

// Planned through Tasks (since 1.5.6)
public class TaskSpawnDelayOtherTp2 implements Runnable {
	
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
		// 50: done, refused or failed, the player teleported is no longer waiting
		try {
			teleport();
		} finally {
			Main.endTeleportDelay(other);
		}
	}

	private void teleport() {
		// The sender or the player teleported has left
		if (!p.isOnline() || !other.isOnline()) {
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
		SpawnUtils.teleportToSpawnAsync(other, tp);
		
		// Messages
		for (String msg: ConfigMGeneral.getConfig(p).getStringList("Spawn.Teleport-By-Player.Sender")) {
			ConfigEventUtils.ExecuteEvent(p, msg.replace("%target%", other.getName()), "", "", false);
		}
		if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
			for (String msg: ConfigMGeneral.getConfig(other).getStringList("Spawn.Teleport-By-Player.Messages")) {
				ConfigEventUtils.ExecuteEvent(other, msg, "", "", false);
			}
		}
		
	}

}