package fr.dianox.hawn.modules.tablist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.tablist.tab.AnimationTabTask;
import fr.dianox.hawn.modules.tablist.tab.MainTablist;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class TabManager {

	public HashMap<String, Integer> animationtab = new HashMap<>();
	public HashMap<String, Integer> animationtabtask = new HashMap<>();
	public Integer tablistnumber = 0;

	public String hea = "";
	public String foo = "";

	public TabManager(Main plugin) {
		start(plugin);
	}

	public void start(Main plugin) {
		if (!TablistConfig.getConfig().getBoolean("Tablist.enable")) {
			return;
		}

		animationtab.clear();

		ConfigurationSection animations = TablistConfig.getConfig().getConfigurationSection("Animations");
		if (animations != null && TablistConfig.getConfig().getBoolean("Animations.Enable")) {
			for (String anim : animations.getKeys(false)) {
				if (anim.contentEquals("Enable")) continue;

				BukkitTask task = new AnimationTabTask(anim).runTaskTimer(plugin, 20, TablistConfig.getConfig().getInt("Animations." + anim + ".refresh-time-ticks"));
				animationtabtask.put(anim, task.getTaskId());
			}
		}

		BukkitTask tablistmain = new MainTablist().runTaskTimer(plugin, 20L, Math.max(1L, TablistConfig.getConfig().getLong("Tablist.refresh-time-ticks")));
		tablistnumber = tablistmain.getTaskId();
	}

	public void stop() {
		Bukkit.getScheduler().cancelTask(tablistnumber);

		for (Integer task : animationtabtask.values()) {
			Bukkit.getScheduler().cancelTask(task);
		}

		animationtabtask.clear();
		animationtab.clear();
	}
}
