package fr.dianox.hawn.utility.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskShutdownServer extends BukkitRunnable {
	
	@Override
	public void run() {
		Bukkit.getServer().shutdown();
	}

}
