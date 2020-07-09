package fr.dianox.hawn.event.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.utility.config.configs.events.WorldEventConfig;

public class AlwaysDayTask extends BukkitRunnable {

	@Override
	public void run() {
		if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Day.World.All_World")) {
			for (World w : Bukkit.getServer().getWorlds()) {
				w.setTime(0L);
			}
		} else {
			for (String world : WorldEventConfig.getConfig().getStringList("World.Time.Always-Day.World.Worlds")) {
				if (Bukkit.getWorld(world) != null) {
					World w = Bukkit.getWorld(world);
					w.setTime(0L);
				}
			}
		}
	}

}
