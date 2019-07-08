package fr.Dianox.Hawn.Commands.TaskSpawns; //package name

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
        this.p = p; //I mean, this is pretty much self-explanatory
        this.tp = tp; //I mean, this is pretty much self-explanatory
        this.other = other; //I mean, this is pretty much self-explanatory
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) { //If the player isn't online or doesn't exist, cancel everything
            p = null;
            cancel(); //cancels the function
            return; //goes back to the start of the function
        }
		
		if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.CustomSpawn.Enable")) { //if the custom spawn is enabled in the config
			if (SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn").contentEquals("CHANGE ME")) { //checks if it contains the default value
				p.sendMessage("You have to change the spawn on Commands.CustomSpawn.Spawn on Events/OnJoin.yml"); //tells you to change the value in the config
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) { //if it doesn't match with the spawn
					MessageUtils.MessageNoSpawn(p); //tells the player there is no spawn
					return; //goes back to the start of the function
				}
				if (!p.hasPermission("hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"))) { //if the player doesn't have the required permission
					String Permission = "hawn.command.spawn."+SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"); //this is the message it outputs on the next line
					MessageUtils.MessageNoPermission(p, Permission); //tells the player it doesn't have the required permission
					return; //goes back to the start of the function
				}
				SpawnUtils.teleportToSpawn(other, SpawnCommandConfig.getConfig().getString("Commands.Spawn.CustomSpawn.Spawn"));
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) { //if it's enabled in the config
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, other);
					}
				}
			}
		} else {
			if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) { //checks if it contains the default value
				p.sendMessage("You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml"); //tells you to change the value in the config
			} else {
				if (!ConfigSpawn.getConfig().isSet("Coordinated."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
					MessageUtils.MessageNoSpawn(p); //tells the player there is no spawn
					return; //goes back to the start of the function
				}
				if (!p.hasPermission("hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) { //if the player doesn't have the required permission
					String Permission = "hawn.command.spawn."+OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"); //this is the message it outputs on the next line
					MessageUtils.MessageNoPermission(p, Permission);  //tells the player it doesn't have the required permission
					return; //goes back to the start of the function
				}
				SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
				for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Sender")) { //outputs the message declared in the config file
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
				if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) { //if it's enabled in the config
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport-By-Player.Messages")) { //outputs the message declared in the config file
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