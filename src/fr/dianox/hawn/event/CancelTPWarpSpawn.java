package fr.dianox.hawn.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

public class CancelTPWarpSpawn implements Listener {
	
	@EventHandler
	public void onAnyMovements(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		cancelTPWarpSpawn(p);
	}
	
	@EventHandler
	public void onAnyDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			
			cancelTPWarpSpawn(p);
		}
	}

	private void cancelTPWarpSpawn(Player p) {
		if (Main.inwarpd.contains(p) && WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Delay.Cancel-Tp-On.Any-movements")) {
			if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
				Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
				Main.player_spawnwarpdelay.remove(p.getUniqueId());
			}

			if (ConfigMMsg.getConfig().getBoolean("Cancel-Tp.Warp.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Cancel-Tp.Warp.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}

			Main.inspawnd.remove(p);
			Main.inwarpd.remove(p);
		} else if (Main.inspawnd.contains(p) && SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Cancel-Tp-On.Any-movements")) {
			if (Main.player_spawnwarpdelay.containsKey(p.getUniqueId())) {
				Bukkit.getScheduler().cancelTask(Main.player_spawnwarpdelay.get(p.getUniqueId()));
				Main.player_spawnwarpdelay.remove(p.getUniqueId());
			}

			if (ConfigMMsg.getConfig().getBoolean("Cancel-Tp.Spawn.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Cancel-Tp.Spawn.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}

			Main.inspawnd.remove(p);
			Main.inwarpd.remove(p);
		}
	}

}