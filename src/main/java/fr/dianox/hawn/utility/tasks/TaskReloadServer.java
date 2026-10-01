package fr.dianox.hawn.utility.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskReloadServer extends BukkitRunnable {
	
	private final Player p;

	public TaskReloadServer(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		Bukkit.getServer().reload();
		
		p.sendMessage("§eDone");
	}

}
