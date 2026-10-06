package fr.dianox.hawn.modules.autobroadcast;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast_AB;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast_BossBar;
import fr.dianox.hawn.modules.autobroadcast.autobc.AutoBroadcast_Title;
import fr.dianox.hawn.utility.BossBarApi;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Iterator;

public class AutoBroadcastManager {
	
	public AutoBroadcastManager() {
		// A list of messages removed from the file: that type is not started (it was an error)
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Enable") && hasMessages("Config.Messages")) {
			StartAB();
		}

		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Enable") && hasMessages("Config.Action-Bar")) {
			StartActionAB();
		}

		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Enable") && hasMessages("Config.Titles")) {
			StartTitleAB();
		}

		if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.Enable") && hasMessages("Config.BossBar")) {
			StartBossAB();
		}
	}

	private static boolean hasMessages(String path) {
		return AutoBroadcastConfig.getConfig().getConfigurationSection(path + ".messages") != null;
	}

	private static final String[] TASKS = {"B_AB", "AB_AB", "T_AB", "BB_AB"};

	/**
	 * /hawn reload: the auto broadcast is started again with the new AutoBroadcast.yml
	 * (turning a type off or changing its messages and interval no longer needs a restart)
	 */
	public static void restart() {
		boolean bossBarWasOn = Main.tasklist.containsKey("BB_AB");

		for (String key : TASKS) {
			Integer id = Main.tasklist.remove(key);
			if (id != null) {
				Bukkit.getScheduler().cancelTask(id);
			}
		}

		Main.autobroadcast.clear();
		Main.autobroadcast_ab.clear();
		Main.autobroadcast_titles.clear();
		Main.autobroadcast_bb.clear();
		Main.autobroadcast_total = 0;
		Main.autobroadcast_total_ab = 0;
		Main.autobroadcast_total_titles = 0;
		Main.autobroadcast_total_bb = 0;
		Main.curMsg = 0;
		Main.curMsg_ab = 0;
		Main.curMsg_titles = 0;
		Main.curMsg_bb = 0;

		// The boss bar of the auto broadcast stays on screen: removed when that type is turned off
		// (not the boss bar of the join, players in BBBlock)
		if (bossBarWasOn && !AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.Enable")) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (!BossBarApi.BBBlock.contains(p)) {
					BossBarApi.deletebar(p);
				}
			}
		}

		new AutoBroadcastManager();
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
