package fr.Dianox.Hawn.Commands.TaskSpawns;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;

public class TaskSpawnDelayOtherTp extends BukkitRunnable {
	
	private Player p, other;
	String tp;
	
	public TaskSpawnDelayOtherTp(Player p, String tp, Player other) {
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
		
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.CustomSpawn.Enable")) {
			if (SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
				p.sendMessage("You have to change the spawn on Commands.CustomSpawn.Spawn on Events/OnJoin.yml");
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					MessageUtils.MessageNoSpawn(p);
					return;
				}
				if (!p.hasPermission("hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(other, SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"));
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, other);
					}
				}
			}
		} else {
			if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
				p.sendMessage("You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml");
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					MessageUtils.MessageNoSpawn(p);
					return;
				}
				if (!p.hasPermission("hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					String Permission = "hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, other);
					}
				}
			}
		}
		
		Main.player_spawnwarpdelay.remove(other.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}