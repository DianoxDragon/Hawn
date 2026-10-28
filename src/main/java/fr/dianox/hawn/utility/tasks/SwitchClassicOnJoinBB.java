package fr.dianox.hawn.utility.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.dianox.hawn.utility.BossBarApi;
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.world.OnJoinPW;

// Planned through Tasks (since 1.5.4)
public class SwitchClassicOnJoinBB implements Runnable {

	private Player p;

	public SwitchClassicOnJoinBB(Player p) {
        this.p = p;
	}

	/**
	 * Removes the boss bar of the join after ticks.
	 */
	public static BukkitTask start(Player p, long ticks) {
		return Tasks.later(new SwitchClassicOnJoinBB(p), ticks);
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
