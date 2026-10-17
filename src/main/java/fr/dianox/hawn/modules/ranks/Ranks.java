package fr.dianox.hawn.modules.ranks;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.RanksConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The ranks of Ranks.yml (since 1.4.7): a rank by hand (Players) or by permission (hawn.rank.&lt;rank&gt;), else the
 * default rank. Shown in the tab list (prefix + name + suffix), which is sorted by priority, and with %rank%,
 * %rank_prefix% and %rank_suffix% everywhere in Hawn.
 */
public final class Ranks implements Listener {

	public static final class Rank {
		public final String name;
		public final String prefix;
		public final String suffix;
		public final int priority;

		Rank(String name, String prefix, String suffix, int priority) {
			this.name = name;
			this.prefix = prefix;
			this.suffix = suffix;
			this.priority = priority;
		}
	}

	// The tab list plugins that show the ranks themselves: Enable: AUTO leaves the tab list to them
	private static final String[] TAB_PLUGINS = {"TAB", "NametagEdit"};

	// The rank-less players, after the ranks (an empty team name would come first)
	private static final String NO_RANK_TEAM = "hwnzzzzzz";

	// Sorted by priority (the lowest first)
	private static volatile Map<String, Rank> ranks = Collections.emptyMap();
	private static volatile Map<String, String> byHand = Collections.emptyMap();
	private static Rank defaultRank;

	private static boolean enabled;
	private static boolean tablist;
	private static String tabPlugin;

	// The rank of each online player, and what is shown of it
	private static final Map<UUID, Rank> current = new ConcurrentHashMap<>();
	private static final Map<UUID, String> shownName = new HashMap<>();
	private static final Map<UUID, String> teamOf = new HashMap<>();
	// The teams sent to the players (before 1.21.2): team -> the names in it
	private static final Map<String, Set<String>> teams = new HashMap<>();

	private static final Set<String> permissions = new HashSet<>();
	private static BukkitTask task;

	// Player#setPlayerListOrder (1.21.2+), null before
	private static final Method LIST_ORDER = listOrder();

	private static Method listOrder() {
		try {
			return Player.class.getMethod("setPlayerListOrder", int.class);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	/* ------------------------------------------------------------------ loading */

	private static YamlConfiguration cfg() {
		return RanksConfig.getConfig();
	}

	/**
	 * At startup and at /hawn reload: the ranks read again, everything shown again.
	 */
	public static void load() {
		clearAll();

		Map<String, Rank> list = new LinkedHashMap<>();
		ConfigurationSection section = cfg().getConfigurationSection("Ranks.List");
		if (section != null) {
			List<Rank> sorted = new ArrayList<>();
			for (String name : section.getKeys(false)) {
				if (!section.getBoolean(name + ".Enable", true)) continue;
				sorted.add(new Rank(name, MessageUtils.colourTheStuff(section.getString(name + ".Prefix", "")),
						MessageUtils.colourTheStuff(section.getString(name + ".Suffix", "")), section.getInt(name + ".Priority", 100)));
			}
			sorted.sort((a, b) -> a.priority != b.priority ? Integer.compare(a.priority, b.priority) : a.name.compareToIgnoreCase(b.name));
			for (Rank r : sorted) {
				list.put(r.name.toLowerCase(Locale.ROOT), r);
			}
		}
		ranks = list;

		Map<String, String> hand = new HashMap<>();
		ConfigurationSection players = cfg().getConfigurationSection("Ranks.Players");
		if (players != null) {
			for (String player : players.getKeys(false)) {
				hand.put(player.toLowerCase(Locale.ROOT), String.valueOf(players.getString(player)).toLowerCase(Locale.ROOT));
			}
		}
		byHand = hand;

		String def = cfg().getString("Ranks.Default-Rank", "");
		defaultRank = def == null ? null : list.get(def.toLowerCase(Locale.ROOT));

		registerPermissions(section);

		String enable = String.valueOf(cfg().get("Ranks.Enable", "AUTO"));
		tabPlugin = null;
		for (String plugin : TAB_PLUGINS) {
			if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
				tabPlugin = plugin;
				break;
			}
		}
		enabled = !enable.equalsIgnoreCase("false");
		// AUTO: the ranks and their placeholders stay, the tab list is left to the other plugin
		tablist = enabled && (enable.equalsIgnoreCase("true") || tabPlugin == null);

		if (!enabled) {
			return;
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			refresh(p, true);
		}

		long every = Math.max(1, cfg().getInt("Ranks.Refresh-Seconds", 5)) * 20L;
		task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
			for (Player p : Bukkit.getOnlinePlayers()) {
				refresh(p, false);
			}
		}, every, every);
	}

	/**
	 * After the startup: a tab list plugin enabled after Hawn takes the tab list (Enable: AUTO).
	 */
	public static void recheckPlugins() {
		String before = tabPlugin;
		String now = null;
		for (String plugin : TAB_PLUGINS) {
			if (Bukkit.getPluginManager().isPluginEnabled(plugin)) {
				now = plugin;
				break;
			}
		}
		if (!java.util.Objects.equals(before, now)) {
			load();
			if (enabled && !tablist) {
				Main.getInstance().getLogger().info("Ranks: " + now + " is installed, the tab list is left to it (Ranks.yml, Enable: true to use the ranks of Hawn there)");
			}
		}
	}

	/**
	 * hawn.rank.&lt;rank&gt;, not given to the operators: an op doesn't get every rank at once.
	 */
	private static void registerPermissions(ConfigurationSection section) {
		PluginManager pm = Bukkit.getPluginManager();
		Set<String> now = new HashSet<>();
		if (section != null) {
			for (String name : section.getKeys(false)) {
				now.add("hawn.rank." + name.toLowerCase(Locale.ROOT));
			}
		}

		for (String old : permissions) {
			if (!now.contains(old)) pm.removePermission(old);
		}
		for (String perm : now) {
			Permission existing = pm.getPermission(perm);
			if (existing == null) {
				pm.addPermission(new Permission(perm, "The rank " + perm.substring(10) + " of Ranks.yml", PermissionDefault.FALSE));
			} else if (existing.getDefault() != PermissionDefault.FALSE) {
				existing.setDefault(PermissionDefault.FALSE);
			}
		}
		permissions.clear();
		permissions.addAll(now);
	}

	/**
	 * Back to the tab list of the server: the names, the order, the teams removed (reload and stop).
	 */
	public static void clearAll() {
		if (task != null) {
			task.cancel();
			task = null;
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (shownName.containsKey(p.getUniqueId())) {
				p.setPlayerListName(null);
			}
			if (LIST_ORDER != null && teamOf.containsKey(p.getUniqueId())) {
				setOrder(p, 0);
			}
			for (String team : teams.keySet()) {
				RankTeams.send(p, team, RankTeams.REMOVE, Collections.<String>emptyList());
			}
		}

		current.clear();
		shownName.clear();
		teamOf.clear();
		teams.clear();
	}

	/* ------------------------------------------------------------------ the rank of a player */

	/**
	 * The rank of the player: by hand, else by permission (the lowest priority), else the default rank. Null for none.
	 */
	public static Rank rankOf(Player p) {
		if (!enabled) {
			return null;
		}

		String hand = byHand.get(p.getName().toLowerCase(Locale.ROOT));
		if (hand != null && ranks.containsKey(hand)) {
			return ranks.get(hand);
		}

		for (Rank r : ranks.values()) {
			if (p.hasPermission("hawn.rank." + r.name.toLowerCase(Locale.ROOT))) {
				return r;
			}
		}
		return defaultRank;
	}

	// The rank kept for the placeholders (the chat is async: no permission check there)
	private static Rank known(Player p) {
		if (!enabled) return null;
		Rank r = current.get(p.getUniqueId());
		if (r == null && Bukkit.isPrimaryThread()) {
			r = rankOf(p);
		}
		return r;
	}

	public static String rank(Player p) {
		Rank r = known(p);
		return r == null ? "" : r.name;
	}

	public static String prefix(Player p) {
		Rank r = known(p);
		return r == null ? "" : r.prefix;
	}

	public static String suffix(Player p) {
		Rank r = known(p);
		return r == null ? "" : r.suffix;
	}

	/* ------------------------------------------------------------------ the tab list */

	private static boolean worldAllows(Player p, String function) {
		return cfg().getBoolean("Ranks.Worlds." + p.getWorld().getName() + "." + function, true);
	}

	/**
	 * The rank checked again, and shown again when it or the world changed.
	 */
	public static void refresh(Player p, boolean force) {
		if (!enabled || !p.isOnline()) {
			return;
		}

		Rank r = rankOf(p);
		UUID id = p.getUniqueId();
		if (r == null) {
			current.remove(id);
		} else {
			current.put(id, r);
		}

		if (!tablist) {
			return;
		}

		// prefix + name + suffix
		String name = null;
		if (r != null && cfg().getBoolean("Ranks.Tablist.Enable", true) && worldAllows(p, "Tablist")) {
			name = r.prefix + p.getName() + r.suffix;
		}
		String before = shownName.get(id);
		if (force || !java.util.Objects.equals(before, name)) {
			if (name == null) {
				if (before != null || force) p.setPlayerListName(null);
				shownName.remove(id);
			} else {
				p.setPlayerListName(name);
				shownName.put(id, name);
			}
		}

		// The order
		boolean sorting = cfg().getBoolean("Ranks.Sorting.Enable", true) && worldAllows(p, "Sorting");
		if (LIST_ORDER != null) {
			int order = sorting && r != null ? Math.max(1, 10000 - Math.max(0, r.priority)) : 0;
			String key = String.valueOf(order);
			if (force || !key.equals(teamOf.get(id))) {
				setOrder(p, order);
				teamOf.put(id, key);
			}
		} else {
			setTeam(p, sorting ? teamName(r) : null);
		}
	}

	private static void setOrder(Player p, int order) {
		try {
			LIST_ORDER.invoke(p, order);
		} catch (ReflectiveOperationException ignored) {}
	}

	// hwn + the priority on 4 digits + the rank: sorted by the client, 16 characters at most (1.16.5)
	private static String teamName(Rank r) {
		if (r == null) {
			return NO_RANK_TEAM;
		}
		String rank = r.name.toLowerCase(Locale.ROOT);
		return "hwn" + String.format("%04d", Math.min(9999, Math.max(0, r.priority))) + (rank.length() > 9 ? rank.substring(0, 9) : rank);
	}

	// Before 1.21.2: the player moved to the team of their rank, for every player online
	private static void setTeam(Player p, String team) {
		UUID id = p.getUniqueId();
		String before = teamOf.get(id);
		if (java.util.Objects.equals(before, team) || !RankTeams.init()) {
			return;
		}

		List<String> name = Collections.singletonList(p.getName());
		if (before != null) {
			Set<String> members = teams.get(before);
			if (members != null) {
				members.remove(p.getName());
				for (Player viewer : Bukkit.getOnlinePlayers()) {
					RankTeams.send(viewer, before, members.isEmpty() ? RankTeams.REMOVE : RankTeams.REMOVE_PLAYERS, name);
				}
				if (members.isEmpty()) teams.remove(before);
			}
			teamOf.remove(id);
		}

		if (team == null) {
			return;
		}

		Set<String> members = teams.get(team);
		boolean created = members == null;
		if (created) {
			members = new HashSet<>();
			teams.put(team, members);
		}
		members.add(p.getName());
		teamOf.put(id, team);

		for (Player viewer : Bukkit.getOnlinePlayers()) {
			if (viewer.equals(p) && created) {
				// Sent whole below with the others
				continue;
			}
			RankTeams.send(viewer, team, created ? RankTeams.CREATE : RankTeams.ADD_PLAYERS, created ? members : name);
		}
		if (created) {
			RankTeams.send(p, team, RankTeams.CREATE, members);
		}
	}

	// A player who joins gets the teams of the others
	private static void sendTeamsTo(Player viewer) {
		if (LIST_ORDER != null || teams.isEmpty() || !RankTeams.init()) {
			return;
		}
		for (Map.Entry<String, Set<String>> team : teams.entrySet()) {
			RankTeams.send(viewer, team.getKey(), RankTeams.CREATE, team.getValue());
		}
	}

	/* ------------------------------------------------------------------ the state, for the startup report */

	public static boolean enabled() {
		return enabled;
	}

	public static boolean tablist() {
		return tablist;
	}

	public static String tabPlugin() {
		return tabPlugin;
	}

	public static int count() {
		return ranks.size();
	}

	/**
	 * The players of Ranks.Players given a rank that doesn't exist or is off.
	 */
	public static List<String> unknownByHand() {
		List<String> wrong = new ArrayList<>();
		ConfigurationSection players = cfg().getConfigurationSection("Ranks.Players");
		if (players != null) {
			for (String player : players.getKeys(false)) {
				if (!ranks.containsKey(String.valueOf(players.getString(player)).toLowerCase(Locale.ROOT))) {
					wrong.add(player + ": " + players.getString(player));
				}
			}
		}
		return wrong;
	}

	/* ------------------------------------------------------------------ events */

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent e) {
		if (!enabled) return;
		Player p = e.getPlayer();
		if (tablist) sendTeamsTo(p);
		refresh(p, true);
	}

	@EventHandler
	public void onWorld(PlayerChangedWorldEvent e) {
		refresh(e.getPlayer(), false);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID id = p.getUniqueId();
		current.remove(id);
		shownName.remove(id);
		if (LIST_ORDER == null && teamOf.containsKey(id)) {
			setTeam(p, null);
		}
		teamOf.remove(id);
	}
}
