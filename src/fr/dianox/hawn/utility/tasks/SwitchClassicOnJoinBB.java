package fr.dianox.hawn.utility.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.utility.BossBarApi;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.world.OnJoinPW;

public class SwitchClassicOnJoinBB extends BukkitRunnable {
	
	private Player p;

	public SwitchClassicOnJoinBB(Player p) {
        this.p = p;
	}
	
	@Override
	public void run() {
		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Enable")) {
        	if (!OnJoinConfig.getConfig().getBoolean("Boss-Bar.World.All_World")) {
                if (OnJoinPW.getBB().contains(p.getWorld().getName())) {
                	BossBarApi.deletebar(p);
            		BossBarApi.BBBlock.remove(p);
            		BossBarApi.ptaskbb.remove(p);
                }
        	} else {
        		BossBarApi.deletebar(p);
        		BossBarApi.BBBlock.remove(p);
        		BossBarApi.ptaskbb.remove(p);
        	}
        }
	}

}
