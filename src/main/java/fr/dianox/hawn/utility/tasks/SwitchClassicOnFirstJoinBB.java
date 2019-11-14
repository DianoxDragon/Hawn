package fr.dianox.hawn.utility.tasks;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.BossBarApi;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.world.OnJoinPW;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SwitchClassicOnFirstJoinBB extends BukkitRunnable {
	
	private Player p;

	public SwitchClassicOnFirstJoinBB(Player p) {
        this.p = p;
	}
	
	private void updateClassicBB(Player p) {
    	if (!OnJoinConfig.getConfig().isSet("Boss-Bar.Join.Message")) {
			return;
		}
    	
		Double progress = 1D;
		
		if (OnJoinConfig.getConfig().isSet("Boss-Bar.Join.Progress")) {
			progress = OnJoinConfig.getConfig().getDouble("Boss-Bar.Join.Progress");
		}
		
    	BossBarApi.updateBar(p, OnJoinConfig.getConfig().getString("Boss-Bar.Join.Color"), 
    			OnJoinConfig.getConfig().getString("Boss-Bar.Join.Message"), OnJoinConfig.getConfig().getString("Boss-Bar.Join.Style"), progress);
    }
	
	@Override
	public void run() {
		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Enable")) {
        	if (!OnJoinConfig.getConfig().getBoolean("Boss-Bar.World.All_World")) {
                if (OnJoinPW.getBB().contains(p.getWorld().getName())) {
                	if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Time.If-not.Swith-To-OnJoin-BossBar.Enable")) {
                		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Time.If-not.Swith-To-OnJoin-BossBar.Keep-The-BossBar")) {
                			if (!BossBarApi.BBBlock.contains(p)) {
                				BossBarApi.BBBlock.add(p);
                			}
    						
                			updateClassicBB(p);
                		} else {
                			if (!BossBarApi.BBBlock.contains(p)) {
                				BossBarApi.BBBlock.add(p);
                			}
    						
                			updateClassicBB(p);
                			
                			BukkitTask task = new SwitchClassicOnJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.Join.Time.If-not.Time-Stay"));
    						
                			if (BossBarApi.ptaskbb.containsKey(p)) {
                				BossBarApi.ptaskbb.remove(p);
                				BossBarApi.ptaskbb.put(p, task.getTaskId());
                			} else {
                				BossBarApi.ptaskbb.put(p, task.getTaskId());
                			}
                		}
                	}
                }
        	} else {
        		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Time.If-not.Swith-To-OnJoin-BossBar.Enable")) {
            		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Time.If-not.Swith-To-OnJoin-BossBar.Keep-The-BossBar")) {
            			if (!BossBarApi.BBBlock.contains(p)) {
            				BossBarApi.BBBlock.add(p);
            			}
						
            			updateClassicBB(p);
            		} else {
            			if (!BossBarApi.BBBlock.contains(p)) {
            				BossBarApi.BBBlock.add(p);
            			}
						
            			updateClassicBB(p);
            			
            			BukkitTask task = new SwitchClassicOnJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.Join.Time.If-not.Time-Stay"));
						
            			if (BossBarApi.ptaskbb.containsKey(p)) {
            				BossBarApi.ptaskbb.remove(p);
            				BossBarApi.ptaskbb.put(p, task.getTaskId());
            			} else {
            				BossBarApi.ptaskbb.put(p, task.getTaskId());
            			}
            		}
            	}
        	}
        }
	}

}