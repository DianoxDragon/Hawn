package fr.dianox.hawn.modules.autobroadcast;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast_AB;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast_BossBar;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast_Title;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;

public class AutoBroadcastManager {
	
	public AutoBroadcastManager() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Enable")) {
			StartAB();
		}

		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Enable")) {
			StartActionAB();
		}

		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Enable")) {
			StartTitleAB();
		}

		if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.Enable")) {
			StartBossAB();
		}
	}
	
	/*
	 * Manage stop of autobroadcast
	 */
	public void stopAll() {
		if (! Main.tasklist.isEmpty()) {

			ArrayList<String> list = new ArrayList<String>(Main.tasklist.keySet());
			
			for (String s2: list) {
				Bukkit.getScheduler().cancelTask(Main.tasklist.get(s2));
				Main.tasklist.remove(s2);
			}
		}
	}
	
	/*
	 * Manage start of autobroadcast
	 */
	public void StartAB() {
		int interval = AutoBroadcastConfig.getConfig().getInt("Config.Messages.Interval");

		Iterator<?> iterator2 = AutoBroadcastConfig.getConfig().getConfigurationSection("Config.Messages.messages").getKeys(false).iterator();
		
		Integer abnumberput = 0;
		
		while (iterator2.hasNext()) {
			String string = (String) iterator2.next();
			Main.autobroadcast.put(abnumberput, string);
			abnumberput++;
			Main.autobroadcast_total++;
		}
			
		Main.autobroadcast_total--;

		BukkitTask TaskName = (new AutoBroadcast(Main.getInstance())).runTaskTimer(Main.getInstance(), 0, interval);
		Main.tasklist.put("B_AB", TaskName.getTaskId());
	}
	
	public void StartActionAB() {
		int interval_ab = AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Interval");

	    Iterator<?> iterator4 = AutoBroadcastConfig.getConfig().getConfigurationSection("Config.Action-Bar.messages").getKeys(false).iterator();

	    Integer abnumberput = 0;

	    while (iterator4.hasNext()) {
			String string = (String) iterator4.next();
			Main.autobroadcast_ab.put(abnumberput, string);
			abnumberput++;
			Main.autobroadcast_total_ab++;
	    }

	    Main.autobroadcast_total_ab--;
	    
		BukkitTask TaskName = (new AutoBroadcast_AB(Main.getInstance())).runTaskTimer(Main.getInstance(), 0, interval_ab);
		Main.tasklist.put("AB_AB", TaskName.getTaskId());
	}
	
	public void StartTitleAB() {
		int interval_titles = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Interval");

	    Iterator<?> iterator3 = AutoBroadcastConfig.getConfig().getConfigurationSection("Config.Titles.messages").getKeys(false).iterator();

	    Integer abnumberput = 0;

	    while (iterator3.hasNext()) {
			String string = (String) iterator3.next();
			Main.autobroadcast_titles.put(abnumberput, string);
			abnumberput++;
			Main.autobroadcast_total_titles++;
	    }

	    Main.autobroadcast_total_titles--;
	    
		BukkitTask TaskName = (new AutoBroadcast_Title(Main.getInstance())).runTaskTimer(Main.getInstance(), 0, interval_titles);
		Main.tasklist.put("T_AB", TaskName.getTaskId());
	}
	
	public void StartBossAB() {
		int interval_bb = AutoBroadcastConfig.getConfig().getInt("Config.BossBar.Interval");

	    Iterator<?> iterator5 = AutoBroadcastConfig.getConfig().getConfigurationSection("Config.BossBar.messages").getKeys(false).iterator();

	    Integer bbnumberput = 0;

	    while (iterator5.hasNext()) {
			String string = (String) iterator5.next();
			Main.autobroadcast_bb.put(bbnumberput, string);
			bbnumberput++;
			Main.autobroadcast_total_bb++;
	    }

	    Main.autobroadcast_total_bb--;
	    
		BukkitTask TaskName = (new AutoBroadcast_BossBar(Main.getInstance())).runTaskTimer(Main.getInstance(), 0, interval_bb);
		Main.tasklist.put("BB_AB", TaskName.getTaskId());
	}

}
