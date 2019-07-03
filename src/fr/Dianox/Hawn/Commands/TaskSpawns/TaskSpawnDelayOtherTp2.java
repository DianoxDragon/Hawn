package fr.Dianox.Hawn.Commands.TaskSpawns;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;

public class TaskSpawnDelayOtherTp2 extends BukkitRunnable {
	
	private Player p, other;
	String tp;
	
	public TaskSpawnDelayOtherTp2(Player p, String tp, Player other) {
        this.p = p;
        this.tp = tp;
        this.other = other;
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
            p = null;
            cancel();
            return;
        }
		
		if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
			MessageUtils.MessageNoSpawn(p);
			return;
		}
		if (!p.hasPermission("hawn.command.spawn."+tp)) {
			String Permission = "hawn.command.spawn."+tp;
			MessageUtils.MessageNoPermission(p, Permission);
			return;
		}
		SpawnUtils.teleportToSpawn(other, tp);
		for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
			MessageUtils.ReplaceCharMessagePlayer(msg, p);
		}
		if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
			for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
				MessageUtils.ReplaceCharMessagePlayer(msg, other);
			}
		}
		
		Main.player_spawnwarpdelay.remove(other.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}