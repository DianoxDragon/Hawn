package fr.dianox.hawn.utility.server;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;

public class WarnTPS {

		private static long lastWarning = 0L;
		private static long lastCritical = 0L;

		public static void runWarnSystemTask(Main plugin) {
			
			new BukkitRunnable() {
	
				public void run() {
					double ticks = Tps.getTPS();
					long now = System.currentTimeMillis();

					// At most one warning (and one emergency save) per minute, not every 3 seconds
					if (ticks <= 5.0D) {
						if (now - lastCritical >= 60000L) {
							lastCritical = now;
							onCritique();
							Bukkit.getServer().savePlayers();
							for (World world : Bukkit.getWorlds()) {
					            world.save();
							}
						}
					} else if (ticks <= 15.0D) {
						if (now - lastWarning >= 60000L) {
							lastWarning = now;
							onPrevient();
						}
					}
					
				}
				
			// On the main thread: the saves and the actions of the messages need it
			}.runTaskTimer(plugin, 40L, 60L);
			
		}
		
		public static void onPrevient() {
			for (Player player: Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("hawn.event.warn.tps")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("TPS.Check.15")) {
						ConfigEventUtils.ExecuteEvent(player, msg, "TPS.Check.15", "WarnTPS", false);
					}
				}
			}
		}
		
		public static void onCritique() {
			for (Player player: Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("hawn.event.warn.tps")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("TPS.Check.5")) {
						ConfigEventUtils.ExecuteEvent(player, msg, "TPS.Check.5", "WarnTPS", false);
					}
				}
			}
	}

}
