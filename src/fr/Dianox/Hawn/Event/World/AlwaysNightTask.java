package fr.Dianox.Hawn.Event.World;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Utility.Config.Events.WorldEventConfig;

public class AlwaysNightTask extends BukkitRunnable {

	@Override
	public void run() {
		if (!WorldEventConfig.getConfig().getBoolean("World.Time.Always-Night.World.All_World")) {
			for (String world: WorldEventConfig.getConfig().getStringList("World.Time.Always-Night.World.Worlds")) {
				if (Bukkit.getWorld(world) != null) {
					World w = Bukkit.getWorld(world);
					w.setTime(14000L);
				}
			}
		} else {
			for (World w : Bukkit.getServer().getWorlds()) {
				w.setTime(14000L);
			}
		}
	}

}