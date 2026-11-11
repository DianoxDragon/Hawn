package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.SQL;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLocaleChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The language of each player (since 1.6): the one they chose in the language menu, else the one of their client,
 * else the one of the server (Plugin.Language-Type). With Plugin.Language-Per-Player off, everyone gets the language
 * of the server, as before 1.6. The console always gets the language of the server.
 */
public final class Lang {

	private Lang() {}

	/**
	 * The languages of Hawn, as their folders in Messages/.
	 */
	public static final List<String> CODES = Collections.unmodifiableList(Arrays.asList(
			"en_US", "fr_FR", "de_DE", "nl_NL", "es_ES", "it_IT", "pt_BR"));

	// The choice of the online players: a code of CODES, or "" for the language of their client
	private static final Map<UUID, String> chosen = new ConcurrentHashMap<>();

	// Before 1.20.2, the game sends its language just after the join: until then getLocale() says en_us for everyone,
	// so these players get the language of the server (as before 1.6) rather than English
	private static final Set<UUID> waiting = ConcurrentHashMap.newKeySet();
	private static final long WAIT_TICKS = 40;

	private static final String PATH = "player_language.Value";
	private static final String TABLE = "player_language";

	public static boolean perPlayer() {
		return ConfigGeneral.getConfig() == null || ConfigGeneral.getConfig().getBoolean("Plugin.Language-Per-Player", true);
	}

	public static String server() {
		String code = normalize(Main.LanguageType);
		return code == null ? "en_US" : code;
	}

	/**
	 * The language of what is sent to this sender.
	 */
	public static String of(CommandSender sender) {
		if (!(sender instanceof Player) || !perPlayer()) {
			return server();
		}

		Player p = (Player) sender;
		String choice = chosen.get(p.getUniqueId());
		if (choice == null && p.isOnline()) {
			// Online before Hawn started (a reload)
			load(p);
			choice = chosen.get(p.getUniqueId());
		}
		if (choice != null && !choice.isEmpty()) {
			return choice;
		}
		if (waiting.contains(p.getUniqueId())) {
			return server();
		}

		String client = fromLocale(locale(p));
		return client == null ? server() : client;
	}

	@SuppressWarnings("deprecation")
	private static String locale(Player p) {
		try {
			return p.getLocale();
		} catch (Throwable e) {
			// Before 1.12
			return null;
		}
	}

	/**
	 * The code of CODES written like this one (en_us, EN_US...), or null.
	 */
	public static String normalize(String code) {
		if (code == null) {
			return null;
		}
		for (String c : CODES) {
			if (c.equalsIgnoreCase(code.trim())) {
				return c;
			}
		}
		return null;
	}

	/**
	 * The language of Hawn for the language of a client (fr_ca gives fr_FR, pt_pt gives pt_BR), or null.
	 */
	public static String fromLocale(String locale) {
		if (locale == null || locale.length() < 2) {
			return null;
		}
		String exact = normalize(locale);
		if (exact != null) {
			return exact;
		}
		String prefix = locale.substring(0, 2).toLowerCase(Locale.ROOT);
		for (String c : CODES) {
			if (c.startsWith(prefix)) {
				return c;
			}
		}
		return null;
	}

	/* ------------------------------------------------------------------ the choice of the player */

	/**
	 * At the join (after the options of MySQL are copied into the file of the player).
	 */
	public static void load(Player p) {
		String value = ConfigPlayerGet.getFile(p.getUniqueId().toString()).getString(PATH, "");
		String code = normalize(value);
		chosen.put(p.getUniqueId(), code == null ? "" : code);
	}

	public static void forget(Player p) {
		chosen.remove(p.getUniqueId());
		waiting.remove(p.getUniqueId());
	}

	/**
	 * The language chosen by the player, or "" for the one of their client.
	 */
	public static String choice(Player p) {
		String c = chosen.get(p.getUniqueId());
		return c == null ? "" : c;
	}

	/**
	 * The player chooses a language (a code of CODES), or the language of their client (null or "").
	 */
	public static void choose(Player p, String code) {
		String value = code == null || code.trim().isEmpty() ? "" : normalize(code);
		if (value == null) {
			return;
		}
		chosen.put(p.getUniqueId(), value);

		String uuid = p.getUniqueId().toString();
		ConfigPlayerGet.writeString(uuid, PATH, value);

		if (Main.getInstance().getSql() != null && !Main.getInstance().getSql().useyamllistplayer) {
			// Like the other options (PlayerOptionSQLClass): SQL sends the writes outside the main thread itself
			if (!SQL.tableExists(TABLE)) {
				SQL.createTable(TABLE, "player TEXT, player_UUID TEXT, Value TEXT");
			}
			if (SQL.exists("player_UUID", uuid, TABLE)) {
				SQL.set(TABLE, "Value", value, "player_UUID", uuid);
				SQL.set(TABLE, "player", p.getName(), "player_UUID", uuid);
			} else {
				SQL.insertData("player, player_UUID, Value", " '" + SQL.q(p.getName()) + "', '" + SQL.q(uuid) + "', '" + SQL.q(value) + "' ", TABLE);
			}
		}
	}

	/**
	 * With MySQL: the choice made on another server of the network, copied into the file of the player.
	 */
	static void syncFromMySQL(String uuid) {
		String value = SQL.cachedValue(TABLE, "Value", uuid);
		if (value != null) {
			ConfigPlayerGet.writeString(uuid, PATH, value);
		}
	}

	/**
	 * The choice is read at the join, after MySQL (LOWEST) and before the messages of the join, and forgotten at the quit.
	 */
	public static final class Events implements Listener {

		@EventHandler(priority = EventPriority.LOW)
		public void onJoin(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			load(p);

			// 1.20.2+ knows the language of the game before the join; before, it comes just after (PlayerLocaleChangeEvent),
			// and a game in English sends no change at all: wait 2 seconds for it
			if (!Main.getInstance().getVersionUtils().isAtLeast(1, 20, 2) && "en_us".equalsIgnoreCase(locale(p))) {
				UUID id = p.getUniqueId();
				waiting.add(id);
				Tasks.later(p, () -> waiting.remove(id), WAIT_TICKS);
			}
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void onLocale(PlayerLocaleChangeEvent e) {
			waiting.remove(e.getPlayer().getUniqueId());
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void onQuit(PlayerQuitEvent e) {
			forget(e.getPlayer());
		}
	}
}
