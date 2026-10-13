package fr.dianox.hawn.modules.onjoin;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.SQL;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * %player_join_number% and %unique_players%: the players in their order of arrival.
 * <p>
 * Without MySQL, each server counts its own players, in StockageInfo/join-order.txt (one UUID per line).
 * <p>
 * With MySQL, the servers that share the database count together, in the table hawn_join_order: MySQL gives the
 * numbers (AUTO_INCREMENT), so two servers never give the same one, and a player keeps their number on every server.
 * The number shown is the rank of the player in the table, so a value skipped by MySQL leaves no hole. When the
 * database can't be reached, the number is not known ("?"): the local counts are never mixed with the network ones.
 * <p>
 * Everything is read before the player joins (AsyncPlayerPreLoginEvent), outside the main thread.
 */
public final class JoinNumbers implements Listener {

	private static final String TABLE = "hawn_join_order";

	// The numbers of the online players (MySQL), or of every player (local file)
	private static final Map<UUID, Integer> numbers = new ConcurrentHashMap<>();
	// MySQL: true when this server added the player to the table (new on the network), false when they were in it
	private static final Map<UUID, Boolean> newOnNetwork = new ConcurrentHashMap<>();

	private static volatile int unique = -1;
	private static volatile boolean network;
	private static CountDownLatch ready = new CountDownLatch(1);
	private static volatile int task = -1;

	private JoinNumbers() {}

	public static void start(Plugin plugin) {
		numbers.clear();
		newOnNetwork.clear();
		unique = -1;
		ready = new CountDownLatch(1);
		network = ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Enable");

		Bukkit.getPluginManager().registerEvents(new JoinNumbers(), plugin);

		// Outside the main thread; the queries wait for the connection (the MySQL thread runs its tasks in order)
		Runnable load = () -> {
			try {
				if (network) {
					loadNetwork();
				} else {
					loadLocal();
				}
			} catch (Exception e) {
				Main.getInstance().getLogger().warning("Could not load the join numbers: " + e);
			} finally {
				ready.countDown();
			}

			// The players already online (/hawn reload)
			for (Player p : Bukkit.getOnlinePlayers()) {
				assign(p.getUniqueId(), false);
			}
		};

		Bukkit.getScheduler().runTaskAsynchronously(plugin, load);

		if (network) {
			// The other servers add players too
			task = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
				if (Main.getInstance().getSql().useyamllistplayer) {
					return;
				}
				Integer count = SQL.query(c -> {
					try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM " + TABLE + ";")) {
						return rs.next() ? rs.getInt(1) : null;
					}
				}, null, 10);
				if (count != null) {
					unique = count;
				}
			}, 600L, 600L).getTaskId();
		}
	}

	public static void stop() {
		if (task != -1) {
			Bukkit.getScheduler().cancelTask(task);
			task = -1;
		}
	}

	/**
	 * The number of the player (1 = the first player), null when it is not known.
	 */
	public static Integer number(Player p) {
		return numbers.get(p.getUniqueId());
	}

	/**
	 * The number of different players, -1 when it is not known.
	 */
	public static int unique() {
		return unique;
	}

	/**
	 * A first join: never played on this server, and with MySQL (First-Join-On-The-Network), not on another server of
	 * the network either. When the database could not be reached, only this server decides.
	 */
	public static boolean isFirstJoin(Player p) {
		if (p.hasPlayedBefore()) {
			return false;
		}

		if (network && ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.First-Join-On-The-Network", true)) {
			Boolean added = newOnNetwork.get(p.getUniqueId());
			return added == null || added;
		}

		return true;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPreLogin(AsyncPlayerPreLoginEvent e) {
		if (e.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
			assign(e.getUniqueId(), true);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent e) {
		UUID uuid = e.getPlayer().getUniqueId();
		newOnNetwork.remove(uuid);
		if (network) {
			numbers.remove(uuid);
		}
	}

	// The number of a player who joins (or is online): read, or given when the player is new
	private static void assign(UUID uuid, boolean joining) {
		try {
			// The first load (a big old network) must be over: a returning player would be taken for a new one
			if (!ready.await(10, TimeUnit.SECONDS)) {
				return;
			}
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			return;
		}

		if (network) {
			assignNetwork(uuid, joining);
		} else {
			assignLocal(uuid);
		}
	}

	// ------------------------------------------------------------------ MySQL

	// No connection: the numbers stay unknown
	private static void loadNetwork() {
		// The connection failed at startup: Hawn uses YAML until the next start, the numbers stay unknown
		if (!SQL.connected(30)) {
			return;
		}

		SQL.query(c -> {
			try (Statement s = c.createStatement()) {
				s.executeUpdate("CREATE TABLE IF NOT EXISTS " + TABLE + " (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, uuid VARCHAR(36) NOT NULL UNIQUE);");
			}

			// The first time, the players already known, in their order of arrival. One server at a time: the
			// others wait for the lock, then find the table filled
			try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT GET_LOCK('" + TABLE + "', 30);")) {
				rs.next();
			}
			try {
				if (count(c) == 0) {
					Map<String, Long> known = localPlayers();
					try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT player_UUID, first_join FROM player_info;")) {
						while (rs.next()) {
							String id = rs.getString(1);
							if (id == null) continue;
							long when = time(rs.getString(2));
							known.merge(id, when, Math::min);
						}
					} catch (java.sql.SQLException noTable) {
						// No player_info yet: a new database
					}

					try (PreparedStatement s = c.prepareStatement("INSERT IGNORE INTO " + TABLE + " (uuid) VALUES (?);")) {
						for (String id : sorted(known)) {
							s.setString(1, id);
							s.addBatch();
						}
						s.executeBatch();
					}

					if (!known.isEmpty()) {
						Main.getInstance().getLogger().info(known.size() + " players numbered in their order of arrival (" + TABLE + ")");
					}
				}
			} finally {
				try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT RELEASE_LOCK('" + TABLE + "');")) {
					rs.next();
				}
			}

			unique = count(c);
			return true;
		}, false, 60);
	}

	private static void assignNetwork(UUID uuid, boolean joining) {
		if (Main.getInstance().getSql().useyamllistplayer) {
			return;
		}

		String id = uuid.toString();

		int[] result = SQL.query(c -> {
			boolean added = false;
			int row = rowOf(c, id);

			if (row < 0) {
				try (PreparedStatement s = c.prepareStatement("INSERT IGNORE INTO " + TABLE + " (uuid) VALUES (?);")) {
					s.setString(1, id);
					added = s.executeUpdate() == 1;
				}
				row = rowOf(c, id);
				if (row < 0) {
					return null;
				}
			}

			int rank;
			try (PreparedStatement s = c.prepareStatement("SELECT COUNT(*) FROM " + TABLE + " WHERE id <= ?;")) {
				s.setInt(1, row);
				try (ResultSet rs = s.executeQuery()) {
					rank = rs.next() ? rs.getInt(1) : -1;
				}
			}

			return new int[] {rank, added ? 1 : 0, added ? count(c) : -1};
		}, null, 10);

		if (result == null || result[0] <= 0) {
			return;
		}

		numbers.put(uuid, result[0]);
		if (joining) {
			newOnNetwork.put(uuid, result[1] == 1);
		}
		if (result[2] > 0) {
			unique = result[2];
		}
	}

	private static int rowOf(java.sql.Connection c, String id) throws java.sql.SQLException {
		try (PreparedStatement s = c.prepareStatement("SELECT id FROM " + TABLE + " WHERE uuid = ?;")) {
			s.setString(1, id);
			try (ResultSet rs = s.executeQuery()) {
				return rs.next() ? rs.getInt(1) : -1;
			}
		}
	}

	private static int count(java.sql.Connection c) throws java.sql.SQLException {
		try (Statement s = c.createStatement(); ResultSet rs = s.executeQuery("SELECT COUNT(*) FROM " + TABLE + ";")) {
			return rs.next() ? rs.getInt(1) : 0;
		}
	}

	// ------------------------------------------------------------------ local file

	private static File orderFile() {
		return new File(Main.getInstance().getDataFolder(), "StockageInfo/join-order.txt");
	}

	private static void loadLocal() throws IOException {
		File file = orderFile();
		List<String> order;

		if (file.exists()) {
			order = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
		} else {
			// The first time: the players who already have a file, in their order of arrival
			order = sorted(localPlayers());
			file.getParentFile().mkdirs();
			Files.write(file.toPath(), order, StandardCharsets.UTF_8);
			if (!order.isEmpty()) {
				Main.getInstance().getLogger().info(order.size() + " players numbered in their order of arrival (join-order.txt)");
			}
		}

		int n = 0;
		synchronized (numbers) {
			for (String line : order) {
				try {
					UUID uuid = UUID.fromString(line.trim());
					if (!numbers.containsKey(uuid)) {
						numbers.put(uuid, ++n);
					}
				} catch (IllegalArgumentException ignored) {}
			}
			unique = n;
		}
	}

	private static void assignLocal(UUID uuid) {
		synchronized (numbers) {
			if (numbers.containsKey(uuid) || unique < 0) {
				return;
			}

			numbers.put(uuid, ++unique);
			try {
				Files.write(orderFile().toPath(), Collections.singletonList(uuid.toString()), StandardCharsets.UTF_8,
						StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			} catch (IOException e) {
				Main.getInstance().getLogger().warning("Could not save the join number of " + uuid + ": " + e.getMessage());
			}
		}
	}

	// ------------------------------------------------------------------ order of arrival

	// The players with a file on this server: uuid -> time of their first join
	private static Map<String, Long> localPlayers() {
		Map<String, Long> players = new HashMap<>();
		File[] files = new File(Main.getInstance().getDataFolder(), "StockageInfo/YamlPlayer").listFiles((dir, name) -> name.endsWith(".yml"));
		if (files == null) {
			return players;
		}

		for (File f : files) {
			String id = f.getName().substring(0, f.getName().length() - 4);
			try {
				UUID.fromString(id);
			} catch (IllegalArgumentException e) {
				continue;
			}

			long when = time(YamlConfiguration.loadConfiguration(f).getString("player_info.first_join"));
			if (when == Long.MAX_VALUE) {
				// Date written with another format: the creation of the file
				try {
					when = Files.readAttributes(f.toPath(), BasicFileAttributes.class).creationTime().toMillis();
				} catch (IOException ignored) {}
			}
			players.put(id, when);
		}

		return players;
	}

	private static List<String> sorted(Map<String, Long> players) {
		List<Map.Entry<String, Long>> entries = new ArrayList<>(players.entrySet());
		entries.sort(Map.Entry.<String, Long>comparingByValue().thenComparing(Map.Entry.comparingByKey()));
		List<String> ids = new ArrayList<>();
		for (Map.Entry<String, Long> entry : entries) {
			ids.add(entry.getKey());
		}
		return ids;
	}

	// The first_join date ("<Plugin.Date-Format>, <time>"), Long.MAX_VALUE when it can't be read
	private static long time(String text) {
		if (text == null || text.isEmpty()) {
			return Long.MAX_VALUE;
		}

		String date = ConfigGeneral.getConfig().getString("Plugin.Date-Format", "dd/MM/yyyy");
		for (String hours : new String[] {"HH:mm:ss", "h:mm:ss a"}) {
			try {
				SimpleDateFormat format = new SimpleDateFormat(date + ", " + hours);
				format.setLenient(false);
				ParsePosition at = new ParsePosition(0);
				java.util.Date parsed = format.parse(text, at);
				if (parsed != null && at.getIndex() == text.length()) {
					return parsed.getTime();
				}
			} catch (IllegalArgumentException ignored) {}
		}

		return Long.MAX_VALUE;
	}
}
