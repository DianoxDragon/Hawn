package fr.dianox.hawn.modules.scoreboard;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.event.OnScoreboard;
import fr.dianox.hawn.modules.scoreboard.scoreboards.AnimationTask;
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.config.configs.ScoreboardMainConfig;
import fr.mrmicky.fastboard.FastBoard;
import fr.dianox.hawn.modules.scoreboard.scoreboards.Scroller;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ScoreManager {

	// Each scoreboard file is read once, not at every refresh of every player
	private final Map<String, YamlConfiguration> files = new ConcurrentHashMap<>();

	public HashMap<String, String> scoreacess = new HashMap<>();
	public HashMap<String, List<String>> worldscore = new HashMap<>();

	// The players and the animations: safe for several threads (since 1.5.6)
	public Map<Player, String> playerscore = new ConcurrentHashMap<>();
	public Map<Player, FastBoard> playerboard = new ConcurrentHashMap<>();
	public Map<Player, Integer> scoretaskplayer = new ConcurrentHashMap<>();

	public Map<String, Integer> animationtab = new ConcurrentHashMap<>();

	public List<Player> noscore = new CopyOnWriteArrayList<>();
	private final Map<String, String> scrollerText = new ConcurrentHashMap<>();

	public List<Integer> alltasks = new CopyOnWriteArrayList<>();

	public ScoreManager(Main plugin) {
		if (!ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) return;

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
							alltasks.add(Tasks.timer(new AnimationTask(string, filename, this), 0, getFile(filename).getInt(
									"changeableText." + string + ".interval")).getTaskId());
						}
					}
				} catch (Exception ignored) {
					fr.dianox.hawn.utility.Debug.hidden("Scoreboard: an animation {CH_...}", ignored);
				}

				try {
					if (getFile(filename).isSet("scroller")) {
						for (String string : getFile(filename).getConfigurationSection("scroller").getKeys(false)) {
							Scroller scroller = new Scroller(getFile(filename).getString("scroller." + string + ".text"),
									getFile(filename).getInt("scroller." + string + ".width", 26),
									getFile(filename).getInt("scroller." + string + ".spaceBetween", 6), '&');

							alltasks.add(Tasks.timer(() -> scrollerText.put(filename + string, scroller.next()),
									0L, getFile(filename).getInt("scroller." + string + ".update")).getTaskId());
						}
					}
				} catch (Exception ignored) {
					fr.dianox.hawn.utility.Debug.hidden("Scoreboard: a scroller {SC_...}", ignored);
				}

				alltasks.add(Tasks.timer(new AnimationTask("TITLESCORENAME", filename, this), 0, getFile(filename).getInt("updater.title")).getTaskId());
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"The file : "+ f.getName() + "is not accepted. Accepted only '.yml' files (YAML)");
			}
		}

		Tasks.later(() -> {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				if (playerscore.containsKey(p)) {
					try {
						Tasks.cancel(scoretaskplayer.get(p));

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
		}, 60L);
	}

	/*
	Get score information
	 */
	public YamlConfiguration getFile(String file) {
		return files.computeIfAbsent(file, name ->
				YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "Scoreboard/" + name + ".yml")));
	}

	/**
	 * /hawn reload: the scoreboard files are read again.
	 */
	public void reloadFiles() {
		files.clear();
		// The lists can be shorter now: the animations start again from their first line
		animationtab.replaceAll((key, line) -> 0);
	}

	public void writeInt(String file, String link, Integer i) {
		File f = new File(Main.getInstance().getDataFolder(), "Scoreboard/" + file + ".yml");
		YamlConfiguration cfg = getFile(file);
		cfg.set(link, i);

		try {
			cfg.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Map<String, Integer> getAnimScore() {
		return animationtab;
	}

	public List<Player> getNoScore() {
		return noscore;
	}

	public Map<String, String> getScrollerText() {
		return scrollerText;
	}
}
