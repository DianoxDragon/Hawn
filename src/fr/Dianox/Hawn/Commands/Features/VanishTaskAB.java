package fr.Dianox.Hawn.Commands.Features;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Utility.ActionBar;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class VanishTaskAB extends BukkitRunnable {

	private Player p;

	public VanishTaskAB(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
            p = null;
            cancel();
            return;
        }
		
		ActionBar.sendActionBar(p, ConfigMCommands.getConfig().getString("Vanish.Action-Bar"));
		
	}

}
