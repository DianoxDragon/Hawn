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
        this.p = p;
        this.tp = tp;
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
            p = null;
            cancel();
            return;
        }
		
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Use-Permission")) {
			if (p.hasPermission("hawn.command.spawn")) {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
					MessageUtils.MessageNoSpawn(p);
					return;
				}
				if (!p.hasPermission("hawn.command.spawn."+tp)) {
					String Permission = "hawn.command.spawn."+tp;
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(p, tp);
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else {
				String Permission = "hawn.command.spawn";
				MessageUtils.MessageNoPermission(p, Permission);
			}
		} else {
			if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
				MessageUtils.MessageNoSpawn(p);
				return;
			}
			if (!p.hasPermission("hawn.command.spawn."+tp)) {
				String Permission = "hawn.command.spawn."+tp;
				MessageUtils.MessageNoPermission(p, Permission);
				return;
			}
			SpawnUtils.teleportToSpawn(p, tp);
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