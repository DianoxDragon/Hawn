package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The player files (StockageInfo/YamlPlayer/<uuid>.yml) are kept in memory: a read never touches the disk,
 * and a write is saved later, outside the main thread (when the player leaves and every 5 minutes).
 */
public class ConfigPlayerGet {

	private static final Map<String, YamlConfiguration> cache = new ConcurrentHashMap<>();
	private static final Set<String> dirty = ConcurrentHashMap.newKeySet();
	// Content waiting to be written: a file read again before the end of its write gets its last values
	private static final Map<String, String> pending = new ConcurrentHashMap<>();
	private static ExecutorService writer = newWriter();

	private static ExecutorService newWriter() {
		return Executors.newSingleThreadExecutor(r -> {
			Thread t = new Thread(r, "Hawn - player files");
			t.setDaemon(true);
			return t;
		});
	}

	public static void start(Plugin plugin) {
		if (writer.isShutdown()) {
			writer = newWriter();
		}

		Bukkit.getPluginManager().registerEvents(new Listener() {
			// After every other quit listener, which can still write in the file
			@EventHandler(priority = EventPriority.MONITOR)
			public void onQuit(PlayerQuitEvent e) {
				String file = e.getPlayer().getUniqueId().toString();
				save(file);
				cache.remove(file);
			}
		}, plugin);

		Bukkit.getScheduler().runTaskTimer(plugin, () -> {
			for (String file : new ArrayList<>(cache.keySet())) {
				save(file);

				// The files of offline players (/checkaccount...) do not stay in memory
				if (!isOnline(file)) {
					cache.remove(file);
				}
			}
		}, 6000L, 6000L);
	}

	/**
	 * Server stop: writes every changed file and waits for the end of the writes.
	 */
	public static void shutdown() {
		for (String file : new ArrayList<>(cache.keySet())) {
			save(file);
		}
		cache.clear();

		writer.shutdown();
		try {
			writer.awaitTermination(10, TimeUnit.SECONDS);
		} catch (InterruptedException ignored) {
			Thread.currentThread().interrupt();
		}
	}

	public static YamlConfiguration getFile(String file) {
		return cache.computeIfAbsent(file, ConfigPlayerGet::load);
	}

	public static YamlConfiguration writeString(String file, String link, String string) {
		return write(file, link, string);
	}

	public static YamlConfiguration writeInt(String file, String link, Integer i) {
		return write(file, link, i);
	}

	public static YamlConfiguration writeDouble(String file, String link, Double i) {
		return write(file, link, i);
	}

	public static YamlConfiguration writeLong(String file, String link, Long i) {
		return write(file, link, i);
	}

	public static YamlConfiguration writeFloat(String file, String link, Float i) {
		return write(file, link, i);
	}

	public static YamlConfiguration writeBoolean(String file, String link, Boolean b) {
		return write(file, link, b);
	}

	public static YamlConfiguration writeList(String file, String link, List<String> asList) {
		return write(file, link, asList);
	}

	private static YamlConfiguration write(String file, String link, Object value) {
		YamlConfiguration cfg = getFile(file);
		cfg.set(link, value);
		dirty.add(file);
		return cfg;
	}

	private static YamlConfiguration load(String file) {
		String waiting = pending.get(file);
		if (waiting != null) {
			YamlConfiguration cfg = new YamlConfiguration();
			try {
				cfg.loadFromString(waiting);
				return cfg;
			} catch (InvalidConfigurationException ignored) {}
		}

		File f = fileOf(file);
		if (!f.exists()) {
			createNewFile(file);
		}

		return YamlConfiguration.loadConfiguration(f);
	}

	private static void save(String file) {
		YamlConfiguration cfg = cache.get(file);
		if (cfg == null || !dirty.remove(file)) {
			return;
		}

		// Turned into text on this thread, before any other change
		String content = cfg.saveToString();
		pending.put(file, content);

		writer.execute(() -> {
			File f = fileOf(file);
			try {
				f.getParentFile().mkdirs();
				Files.write(f.toPath(), content.getBytes(StandardCharsets.UTF_8));
			} catch (IOException e) {
				Main.getInstance().getLogger().warning("Could not save " + f.getPath() + ": " + e.getMessage());
			}
			pending.remove(file, content);
		});
	}

	private static boolean isOnline(String file) {
		try {
			return Bukkit.getPlayer(UUID.fromString(file)) != null;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private static File fileOf(String file) {
		return new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer/" + file + ".yml");
	}

	private static void createNewFile(String file) {
		File f = fileOf(file);
		File folder = f.getParentFile();

		try {
			if (!folder.exists()) {
				folder.mkdirs();
			}
			f.createNewFile();
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(f.getAbsolutePath());
			e.printStackTrace();
		}
	}
}
