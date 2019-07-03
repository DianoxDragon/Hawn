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

public class TaskSpawnDelaySelfTp extends BukkitRunnable {
	
	private Player p;
	
	public TaskSpawnDelaySelfTp(Player p) {
        this.p = p;
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
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				if (!p.hasPermission("hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) {
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn");
					MessageUtils.MessageNoPermission(p, Permission);
					return;
				}
				SpawnUtils.teleportToSpawn(p, SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"));
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
		}
		
		if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
			Main.player_spawnwarpdelay.remove(p.getUniqueId());
		}
		
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}