package fr.Dianox.Hawn.Utility.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskShutdownServer extends BukkitRunnable {
	
	@Override
	public void run() {
		Bukkit.getServer().shutdown();
	}

}
