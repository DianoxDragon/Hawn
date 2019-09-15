package fr.dianox.hawn.utility.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskSavePlayerServer extends BukkitRunnable {

	private Player p;

	public TaskSavePlayerServer(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		Bukkit.getServer().savePlayers();
		
		p.sendMessage("§eDone");
	}

}
