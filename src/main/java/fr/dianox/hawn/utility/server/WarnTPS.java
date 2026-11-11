package fr.dianox.hawn.utility.server;

import fr.dianox.hawn.utility.Tasks;
import java.util.ArrayDeque;
import java.util.Deque;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import fr.dianox.hawn.utility.Task;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;

public class WarnTPS {

		private static long lastWarning = 0L;
		private static long lastCritical = 0L;
		// The emergency save spread over the ticks: still running, not started again
		private static boolean saving = false;

		// The players saved in one tick when the save is spread
		private static final int PLAYERS_PER_TICK = 10;

		private static YamlConfiguration cfg() {
			return ConfigGeneral.getConfig();
		}

		public static double warningAt() {
			return cfg().getDouble("Plugin.Tps.Warning-At", 15.0D);
		}

		public static double criticalAt() {
			return cfg().getDouble("Plugin.Tps.Critical-At", 5.0D);
		}

		public static void runWarnSystemTask(Main plugin) {

			Tasks.timer(() -> {
				double ticks = Tps.getTPS();
				long now = System.currentTimeMillis();

				// At most one warning (and one emergency save) per minute, not every 3 seconds
				if (ticks <= criticalAt()) {
					if (now - lastCritical >= 60000L) {
						lastCritical = now;
						onCritique();
						// 71: off by default, saving everything while the server lags makes it worse
						if (cfg().getBoolean("Plugin.Tps.Emergency-Save.Enable")) {
							emergencySave(plugin);
						}
					}
				} else if (ticks <= warningAt()) {
					if (now - lastWarning >= 60000L) {
						lastWarning = now;
						onPrevient();
					}
				}

			// On the main thread: the saves and the actions of the messages need it
			}, 40L, 60L);

		}

		/**
		 * The worlds and the players, as chosen in general.yml. Bukkit only saves on the main thread (never async, a world could be
		 * corrupted): spread, one world then a few players at each tick, instead of everything in the same tick.
		 */
		public static void emergencySave(Main plugin) {
			if (saving) {
				return;
			}

			// Folia saves the worlds by region itself (since 1.6)
			boolean worlds = !Tasks.isFolia() && cfg().getBoolean("Plugin.Tps.Emergency-Save.Worlds", true);
			boolean players = cfg().getBoolean("Plugin.Tps.Emergency-Save.Players", true);

			if (!cfg().getBoolean("Plugin.Tps.Emergency-Save.Spread", true)) {
				if (players) Tasks.forEachPlayer(Player::saveData);
				if (worlds) {
					for (World world : Bukkit.getWorlds()) {
						world.save();
					}
				}
				return;
			}

			Deque<World> worldQueue = new ArrayDeque<>(worlds ? Bukkit.getWorlds() : java.util.Collections.<World>emptyList());
			Deque<Player> playerQueue = new ArrayDeque<>(players ? Bukkit.getOnlinePlayers() : java.util.Collections.<Player>emptyList());
			saving = true;

			Task[] task = new Task[1];
			task[0] = Tasks.timer(() -> {
				if (!worldQueue.isEmpty()) {
					World world = worldQueue.poll();
					// Unloaded since
					if (Bukkit.getWorld(world.getUID()) != null) {
						world.save();
					}
					return;
				}

				for (int i = 0; i < PLAYERS_PER_TICK && !playerQueue.isEmpty(); i++) {
					Player p = playerQueue.poll();
					if (p.isOnline()) {
						Tasks.sync(p, p::saveData);
					}
				}

				if (playerQueue.isEmpty()) {
					saving = false;
					Tasks.cancel(task[0]);
				}
			}, 1L, 1L);
		}

		private static String shown(double threshold) {
			return threshold == Math.floor(threshold) ? String.valueOf((long) threshold) : String.valueOf(threshold);
		}

		public static void onPrevient() {
			for (Player player: Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("hawn.event.warn.tps")) {
					for (String msg: ConfigMAdmin.getConfig(player).getStringList("TPS.Check.15")) {
						ConfigEventUtils.ExecuteEvent(player, msg.replace("%arg 1%", shown(warningAt())), "TPS.Check.15", "WarnTPS", false);
					}
				}
			}
		}

		public static void onCritique() {
			for (Player player: Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("hawn.event.warn.tps")) {
					for (String msg: ConfigMAdmin.getConfig(player).getStringList("TPS.Check.5")) {
						ConfigEventUtils.ExecuteEvent(player, msg.replace("%arg 1%", shown(criticalAt())), "TPS.Check.5", "WarnTPS", false);
					}
				}
			}
	}

}
