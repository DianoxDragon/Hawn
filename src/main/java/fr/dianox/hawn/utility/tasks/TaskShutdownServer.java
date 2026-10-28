package fr.dianox.hawn.utility.tasks;

import org.bukkit.Bukkit;

// Planned through Tasks (since 1.5.5)
public class TaskShutdownServer implements Runnable {
	
	@Override
	public void run() {
		Bukkit.getServer().shutdown();
	}

}
