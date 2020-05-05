package fr.dianox.hawn.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class CancelTPWarpSpawn implements Listener {
	
	@EventHandler
	public void onAnyMovements(PlayerMoveEvent e) {
		
		Player p = e.getPlayer();
		
		if (Main.inwarpd.contains(p)) {
			if (WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Delay.Cancel-Tp-On.Any-movements")) {
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
			}
		} else if (Main.inspawnd.contains(p)) {
			if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Cancel-Tp-On.Any-movements")) {
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
	
	@EventHandler
	public void onAnyDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			
			Player p = (Player) e.getEntity();
			
			if (Main.inwarpd.contains(p)) {
				if (WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.Delay.Cancel-Tp-On.Any-movements")) {
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
				}
			} else if (Main.inspawnd.contains(p)) {
				if (SpawnCommandConfig.getConfig().getBoolean("Commands.Spawn.Delay.Cancel-Tp-On.Any-movements")) {
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
	}

}
