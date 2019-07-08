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
        this.p = p; //I mean, this is pretty much self-explanatory
        this.tp = tp; //I mean, this is pretty much self-explanatory
        this.other = other; //I mean, this is pretty much self-explanatory
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) {  //If the player isn't online or doesn't exist, cancel everything
            p = null;
            cancel(); //cancels the function
            return; //goes back to the start of the function
        }
		
		if (!ConfigSpawn.getConfig().isSet("Coordinated."+tp)) { //if it doesn't match with the spawn
			MessageUtils.MessageNoSpawn(p); //tells the player there is no spawn
			return; //goes back to the start of the function
		}
		if (!p.hasPermission("hawn.command.spawn."+tp)) { //if the player doesn't have the required permission
			String Permission = "hawn.command.spawn."+tp; //this is the message it outputs on the next line
			MessageUtils.MessageNoPermission(p, Permission); //tells the player it doesn't have the required permission
			return; //goes back to the start of the function
		}
		SpawnUtils.teleportToSpawn(other, tp);
		for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) { //outputs the message declared in the config file
			MessageUtils.ReplaceCharMessagePlayer(msg, p);
		}
		if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
			for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) { //outputs the message declared in the config file
				MessageUtils.ReplaceCharMessagePlayer(msg, other);
			}
		}
		
		Main.player_spawnwarpdelay.remove(other.getUniqueId());
		Main.inspawnd.remove(p);
		Main.inwarpd.remove(p);
	}

}