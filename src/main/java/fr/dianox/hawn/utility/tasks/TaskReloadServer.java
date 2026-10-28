package fr.dianox.hawn.utility.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

// Planned through Tasks (since 1.5.5)
public class TaskReloadServer implements Runnable {
	
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
