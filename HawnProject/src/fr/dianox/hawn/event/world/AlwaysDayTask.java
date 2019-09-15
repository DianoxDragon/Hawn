package fr.Dianox.Hawn.Event.World;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Utility.Config.Events.WorldEventConfig;

public class AlwaysDayTask extends BukkitRunnable {

	@Override
	public void run() {
		if (!WorldEventConfig.getConfig().getBoolean("World.Time.Always-Day.World.All_World")) {
			for (String world: WorldEventConfig.getConfig().getStringList("World.Time.Always-Day.World.Worlds")) {
				if (Bukkit.getWorld(world) != null) {
					World w = Bukkit.getWorld(world);
					w.setTime(0L);
				}
			}
		} else {
			for (World w : Bukkit.getServer().getWorlds()) {
				w.setTime(0L);
			}
		}
	}

}
