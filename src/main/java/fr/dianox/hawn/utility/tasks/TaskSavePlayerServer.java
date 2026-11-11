package fr.dianox.hawn.utility.tasks;

import fr.dianox.hawn.utility.Tasks;
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
		Tasks.forEachPlayer(Player::saveData);
		
		p.sendMessage("§eDone");
	}

}
