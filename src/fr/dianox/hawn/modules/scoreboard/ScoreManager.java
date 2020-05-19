package fr.dianox.hawn.modules.scoreboard;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.event.OnScoreboard;
import fr.dianox.hawn.modules.scoreboard.scoreboards.AnimationTask;
import fr.dianox.hawn.modules.scoreboard.scoreboards.FastBoard;
import fr.dianox.hawn.modules.scoreboard.scoreboards.Scroller;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ScoreManager {

	private YamlConfiguration cfg;

	public HashMap<String, String> scoreacess = new HashMap<>();
	public HashMap<String, List<String>> worldscore = new HashMap<>();

	public HashMap<Player, String> playerscore = new HashMap<>();
	public HashMap<Player, FastBoard> playerboard = new HashMap<>();
	public HashMap<Player, Integer> scoretaskplayer = new HashMap<>();

	public HashMap<String, Integer> animationtab = new HashMap<>();

	public List<Player> noscore = new ArrayList<>();
	private final HashMap<String, String> scrollerText = new HashMap<>();

	public List<Integer> alltasks = new ArrayList<>();

	public ScoreManager(Main plugin) {
		File fo = new File(plugin.getDataFolder().getAbsolutePath() + "/Scoreboard/");

		if (fo.listFiles() == null) return;
		if (Objects.requireNonNull(fo.listFiles()).length <= 0) return;

		for (File f : Objects.requireNonNull(fo.listFiles())) {
			if (f.getName().endsWith(".yml")) {
				String filename = f.getName().replace(".yml", "");
				scoreacess.put(filename, f.getAbsolutePath());
				if (getFile(filename).getBoolean("World.All_World")) {
					List<String> list = new ArrayList<>();
					list.add("ALLWORLDSOFCOURSE");
					worldscore.put(filename, list);
				} else {
					worldscore.put(filename, getFile(filename).getStringList("World.Worlds"));
				}

				try {
					if (getFile(filename).isSet("changeableText")) {
						for (String string : getFile(filename).getConfigurationSection("changeableText").getKeys(false)) {
							BukkitTask TaskScoreName = new AnimationTask(string, filename, this).runTaskTimer(plugin, 0, getFile(filename).getInt(
									"changeableText." + string + ".interval"));

							alltasks.add(TaskScoreName.getTaskId());
						}
					}
				} catch (Exception ignored) {}

				try {
					if (getFile(filename).isSet("scroller")) {
						for (String string : getFile(filename).getConfigurationSection("scroller").getKeys(false)) {
							new BukkitRunnable() {

								final Scroller scroller = new Scroller(getFile(filename).getString("scroller." + string + ".text"),
										getFile(filename).getInt("scroller." + string + ".width", 26),
										getFile(filename).getInt("scroller." + string + ".spaceBetween", 6), '&');

								boolean check = false;

								@Override
								public void run() {
									if (!scrollerText.containsKey(filename + string)) {
										scrollerText.put(filename + string, scroller.next());
									} else {
										scrollerText.replace(filename + string, scroller.next());
									}

									if (!check) {
										if (!alltasks.contains(getTaskId())) {
											alltasks.add(getTaskId());
											check = true;
										}
									}
								}
							}.runTaskTimer(plugin, 0L, getFile(filename).getInt("scroller." + string + ".update"));
						}
					}
				} catch (Exception ignored) {}

				BukkitTask TaskScoreName = new AnimationTask("TITLESCORENAME", filename, this).runTaskTimer(plugin, 0, getFile(filename).getInt("updater.title"));

				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.GRAY+"Loaded the scoreboard : " + ChatColor.GREEN + f.getName() + ChatColor.GRAY
						+ " with the permission : " + ChatColor.GREEN + "hawn.scoreboard." + filename);
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"The file : "+ f.getName() + "is not accepted. Accepted only '.yml' files (YAML)");
			}
		}

		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

		scheduler.scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			@Override
			public void run() {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (playerscore.containsKey(p)) {
						try {
							Bukkit.getScheduler().cancelTask(scoretaskplayer.get(p));

							FastBoard board = playerboard.get(p);
							if (board != null) {
								board.delete();
							}
						} catch (Exception ignored) {}

						playerboard.remove(p);
						playerscore.remove(p);
						scoretaskplayer.remove(p);
					}

					String world = p.getWorld().getName();
					OnScoreboard.createNewScore(p, world);
				}
			}
		}, 60L);
	}

	/*
	Get score information
	 */
	public YamlConfiguration getFile(String file) {
		File f = new File(Main.getInstance().getDataFolder(), "Scoreboard/" + file + ".yml");
		cfg = YamlConfiguration.loadConfiguration(f);
		return cfg;
	}

	public void writeInt(String file, String link, Integer i) {
		File f = new File(Main.getInstance().getDataFolder(), "Scoreboard/" + file + ".yml");
		cfg = YamlConfiguration.loadConfiguration(f);
		cfg.set(link, i);

		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> getAnimScore() {
		return animationtab;
	}

	public List<Player> getNoScore() {
		return noscore;
	}

	public HashMap<String, String> getScrollerText() {
		return scrollerText;
	}
}
