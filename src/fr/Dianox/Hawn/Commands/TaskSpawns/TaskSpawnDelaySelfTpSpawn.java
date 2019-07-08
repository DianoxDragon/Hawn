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
		if (p == null || !p.isOnline()) { //If the player isn't online or doesn't exist, cancel everything
            p = null;
            cancel(); //cancels the function
            return; //goes back to the start of the function
        }
		
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Use-Permission")) { //checks if the user (server config) has enabled permissions
			if (p.hasPermission("hawn.command.spawn")) { //if the player does have the required permission
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
					MessageUtils.MessageNoSpawn(p); //tells the player there is no spawn
					return; //goes back to the start of the function
				}
				if (!p.hasPermission("hawn.command.spawn."+tp)) { //if the player doesn't have the required permission
					String Permission = "hawn.command.spawn."+tp;
					MessageUtils.MessageNoPermission(p, Permission); //tells the player it doesn't have the required permission
					return; //goes back to the start of the function
				}
				SpawnUtils.teleportToSpawn(p, tp);
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else {
				String Permission = "hawn.command.spawn";
				MessageUtils.MessageNoPermission(p, Permission); //tells the player it doesn't have the required permission
			}
		} else {
			if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) {
				MessageUtils.MessageNoSpawn(p); //tells the player there is no spawn
				return; //goes back to the start of the function
			}
			if (!p.hasPermission("hawn.command.spawn."+tp)) { //if the player doesn't have the required permission
				String Permission = "hawn.command.spawn."+tp;
				MessageUtils.MessageNoPermission(p, Permission); //tells the player it doesn't have the required permission
				return; //goes back to the start of the function
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