package fr.dianox.hawn.utility.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

// Planned through Tasks (since 1.5.5)
public class TaskSavePlayerServer implements Runnable {

	private final Player p;

	public TaskSavePlayerServer(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		Bukkit.getServer().savePlayers();
		
		p.sendMessage("§eDone");
	}

}
