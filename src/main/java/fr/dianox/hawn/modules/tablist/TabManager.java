package fr.dianox.hawn.modules.tablist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.tablist.tab.AnimationTabTask;
import fr.dianox.hawn.modules.tablist.tab.MainTablist;
import fr.dianox.hawn.modules.tablist.tab.TablistLayout;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The tab lists, like the scoreboards: Tablist/Tablist.yml holds the general options and the default tab list,
 * every other .yml file of the Tablist folder is a tab list with its own worlds, permission, header, footer and animations.
 */
public class TabManager {

	private final List<BukkitTask> tasks = new ArrayList<>();
	// "name" for the animations of Tablist.yml, "file/name" for those of a tab list file
	private final Map<String, AnimationTabTask> animations = new HashMap<>();
	public Integer tablistnumber = 0;

	// The tab lists of the files, highest priority first, then the default one
	private final List<TablistLayout> layouts = new ArrayList<>();

	public TabManager(Main plugin) {
		start(plugin);
	}

	public void start(Main plugin) {
		if (!TablistConfig.getConfig().getBoolean("Tablist.enable")) {
			return;
		}

		animations.clear();
		if (TablistConfig.getConfig().getBoolean("Animations.Enable")) {
			startAnimations(plugin, "", TablistConfig.getConfig().getConfigurationSection("Animations"));
		}
		loadLayouts(plugin);

		BukkitTask tablistmain = new MainTablist().runTaskTimer(plugin, 20L, Math.max(1L, TablistConfig.getConfig().getLong("Tablist.refresh-time-ticks")));
		tablistnumber = tablistmain.getTaskId();
	}

	private void startAnimations(Main plugin, String prefix, ConfigurationSection section) {
		if (section == null) return;

		for (String anim : section.getKeys(false)) {
			if (!section.isConfigurationSection(anim)) continue; // Enable

			AnimationTabTask task = new AnimationTabTask(section.getStringList(anim + ".text"));
			tasks.add(task.runTaskTimer(plugin, 20, Math.max(1, section.getInt(anim + ".refresh-time-ticks", 20))));
			animations.put(prefix + anim, task);
		}
	}

	private void loadLayouts(Main plugin) {
		layouts.clear();

		TablistLayout main = TablistLayout.main(TablistConfig.getConfig().getConfigurationSection("Tablist"));

		File[] files = new File(plugin.getDataFolder(), "Tablist").listFiles((dir, name) -> name.endsWith(".yml") && !name.equals("Tablist.yml"));
		if (files != null) {
			Arrays.sort(files);
			for (File f : files) {
				String name = f.getName().substring(0, f.getName().length() - 4);
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
				if (!cfg.getBoolean("enable", true)) continue;

				TablistLayout layout = TablistLayout.custom(name, cfg, main);
				layouts.add(layout);
				startAnimations(plugin, name + "/", cfg.getConfigurationSection("Animations"));
			}
		}

		// Stable sort: with the same priority, the files in alphabetical order
		layouts.sort((a, b) -> Integer.compare(b.getPriority(), a.getPriority()));
		layouts.add(main);
	}

	/**
	 * The tab list the player sees: the first one of the files for their world and permissions, or the default one.
	 */
	public TablistLayout getLayout(Player p) {
		for (TablistLayout layout : layouts) {
			if (layout.matches(p)) {
				return layout;
			}
		}
		return layouts.get(layouts.size() - 1);
	}

	/**
	 * The current frame of {anim_name} for a tab list: its own animation, else the one of Tablist.yml.
	 * @return null when there is no such animation
	 */
	public String frame(TablistLayout layout, String anim) {
		AnimationTabTask task = animations.get(layout.getName() + "/" + anim);
		if (task == null) {
			task = animations.get(anim);
		}
		return task == null ? null : task.current();
	}

	/**
	 * The tab lists of the files in use (the default one not counted).
	 */
	public int count() {
		return Math.max(0, layouts.size() - 1);
	}

	public void stop() {
		Bukkit.getScheduler().cancelTask(tablistnumber);

		for (BukkitTask task : tasks) {
			task.cancel();
		}

		tasks.clear();
		animations.clear();
	}
}
