package fr.Dianox.Hawn.Utility.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.AdminPanelConfig;

public class TaskSavePlayerServer extends BukkitRunnable {

	private Player p;

	public TaskSavePlayerServer(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		Bukkit.getServer().savePlayers();
		
		for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Save-Players.Messages")) {
			MessageUtils.ReplaceCharMessagePlayer(msg, p);
		}
	}

}

