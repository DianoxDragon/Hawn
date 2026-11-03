package fr.dianox.hawn.event.world;

import org.bukkit.Bukkit;
import org.bukkit.World;

import fr.dianox.hawn.utility.config.configs.events.WorldEventConfig;

// Planned through Tasks (since 1.5.6)
public class AlwaysNightTask implements Runnable {

	@Override
	public void run() {
		if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Night.World.All_World")) {
			for (World w : Bukkit.getServer().getWorlds()) {
				w.setTime(14000L);
			}
		} else {
			for (String world : WorldEventConfig.getConfig().getStringList("World.Time.Always-Night.World.Worlds")) {
				if (Bukkit.getWorld(world) != null) {
					World w = Bukkit.getWorld(world);
					w.setTime(14000L);
				}
			}
		}
	}

}
