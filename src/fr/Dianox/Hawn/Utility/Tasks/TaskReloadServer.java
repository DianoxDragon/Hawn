package fr.Dianox.Hawn.Utility.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskReloadServer extends BukkitRunnable {
	
	private Player p;

	public TaskReloadServer(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		Bukkit.getServer().reload();
		
		p.sendMessage("§eDone");
	}

}
