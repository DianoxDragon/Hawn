package fr.Dianox.Hawn.Utility.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.AdminPanelConfig;

public class TaskShutdownServer extends BukkitRunnable {
	
	private Player p;

	public TaskShutdownServer(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		Bukkit.getServer().shutdown();
		
		for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Shutdown.Messages")) {
			MessageUtils.ReplaceCharMessagePlayer(msg, p);
		}
	}

}
