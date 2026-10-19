package fr.dianox.hawn.modules.ranks;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.PlayerVisibility;
import fr.dianox.hawn.hook.HooksManager;
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
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The ranks of Ranks.yml (since 1.4.7): a rank by hand (Players) or by permission (hawn.rank.&lt;rank&gt;), else the
 * default rank; or the group of the player in LuckPerms (Source: LUCKPERMS, 1.4.8). Shown in the tab list (prefix + name
 * + suffix, sorted by priority), above the head and in the colour of the name (1.4.8), and with %rank%, %rank_prefix%
 * and %rank_suffix% everywhere in Hawn.
 */
public final class Ranks implements Listener {

	public static final class Rank {
		public final String name;
		public final String prefix;
		public final String suffix;
		public final int priority;
		// A colour of the name above the head (RED, GOLD...), null for none
		public final String nameColour;

		Rank(String name, String prefix, String suffix, int priority, String nameColour) {
			this.name = name;
			this.prefix = prefix;
			this.suffix = suffix;
			this.priority = priority;
			this.nameColour = nameColour;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Rank)) return false;
			Rank r = (Rank) o;
			return name.equals(r.name) && prefix.equals(r.prefix) && suffix.equals(r.suffix) && priority == r.priority && Objects.equals(nameColour, r.nameColour);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, prefix, suffix, priority, nameColour);
		}
	}

	/**
	 * Where the rank of a player comes from (/hawn rank info).
	 */
	public enum Source { HAND, PERMISSION, LUCKPERMS, DEFAULT, NONE }

	// The tab list plugins that show the ranks themselves: Enable: AUTO leaves the tab list and the heads to them
	private static final String[] TAB_PLUGINS = {"TAB", "NametagEdit"};

	// The 16 colours a name can have above the head
	public static final List<String> COLOURS = java.util.Arrays.asList("BLACK", "DARK_BLUE", "DARK_GREEN", "DARK_AQUA", "DARK_RED",
			"DARK_PURPLE", "GOLD", "GRAY", "DARK_GRAY", "BLUE", "GREEN", "AQUA", "RED", "LIGHT_PURPLE", "YELLOW", "WHITE");

	// The rank-less players, after the ranks (an empty team name would come first)
	private static final String NO_RANK_TEAM = "hwnzzzzzz";

	// Sorted by priority (the lowest first)
	private static volatile Map<String, Rank> ranks = Collections.emptyMap();
	private static volatile Map<String, String> byHand = Collections.emptyMap();
	private static Rank defaultRank;

	private static boolean enabled;
	private static boolean tablist;
	private static boolean luckPerms;
	private static String tabPlugin;

	// The rank of each online player, and what is shown of it
	private static final Map<UUID, Rank> current = new ConcurrentHashMap<>();
	private static final Map<UUID, Source> sources = new ConcurrentHashMap<>();
	private static final Map<UUID, String> shownName = new HashMap<>();
	private static final Map<UUID, String> orderOf = new HashMap<>();
	private static final Map<UUID, String> teamOf = new HashMap<>();
	// The teams sent to the players: team -> the names in it, and what it shows
	private static final Map<String, Set<String>> teams = new HashMap<>();
	private static final Map<String, RankTeams.Look> looks = new HashMap<>();

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

	static String colourName(String value) {
		if (value == null || value.trim().isEmpty()) return null;
		String c = value.trim().toUpperCase(Locale.ROOT).replace(' ', '_');
		return COLOURS.contains(c) ? c : null;
	}

	// The 16 colours as RGB, in the order of COLOURS (the codes 0 to f)
	private static final int[] RGB = {0x000000, 0x0000AA, 0x00AA00, 0x00AAAA, 0xAA0000, 0xAA00AA, 0xFFAA00, 0xAAAAAA,
			0x555555, 0x5555FF, 0x55FF55, 0x55FFFF, 0xFF5555, 0xFF55FF, 0xFFFF55, 0xFFFFFF};

	/**
	 * The colour of the name when Name-Colour is not given: the last colour of the prefix (a § code, or a hex colour as
	 * the nearest of the 16 colours). Null when the prefix has no colour.
	 */
	static String colourOfPrefix(String prefix) {
		if (prefix == null) return null;
		String found = null;
		for (int i = 0; i < prefix.length() - 1; i++) {
			if (prefix.charAt(i) != '\u00a7') continue;
			char c = Character.toLowerCase(prefix.charAt(i + 1));
			// §x§R§R§G§G§B§B: a hex colour
			if (c == 'x' && i + 13 < prefix.length()) {
				StringBuilder hex = new StringBuilder();
				for (int k = 0; k < 6; k++) hex.append(prefix.charAt(i + 3 + 2 * k));
				try {
					found = nearest(Integer.parseInt(hex.toString(), 16));
				} catch (NumberFormatException ignored) {
					// not a hex colour: the colour before stays
				}
				i += 13;
			} else if ("0123456789abcdef".indexOf(c) >= 0) {
				found = COLOURS.get("0123456789abcdef".indexOf(c));
			} else if (c == 'r') {
				found = null;
			}
		}
		return found;
	}

	private static String nearest(int rgb) {
		int best = 0;
		long bestDistance = Long.MAX_VALUE;
		for (int i = 0; i < RGB.length; i++) {
			long dr = ((rgb >> 16) & 0xFF) - ((RGB[i] >> 16) & 0xFF);
			long dg = ((rgb >> 8) & 0xFF) - ((RGB[i] >> 8) & 0xFF);
			long db = (rgb & 0xFF) - (RGB[i] & 0xFF);
			long d = dr * dr + dg * dg + db * db;
			if (d < bestDistance) {
				bestDistance = d;
				best = i;
			}
		}
		return COLOURS.get(best);
	}

	/**
	 * Name-Colour of a rank: one of the 16 colours, NONE for none, empty or missing for the last colour of the prefix.
	 */
	static String nameColour(String value, String prefix) {
		if (value != null && value.trim().equalsIgnoreCase("NONE")) return null;
		String c = colourName(value);
		return c != null ? c : colourOfPrefix(prefix);
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
				String prefix = MessageUtils.colourTheStuff(section.getString(name + ".Prefix", ""));
				sorted.add(new Rank(name, prefix, MessageUtils.colourTheStuff(section.getString(name + ".Suffix", "")),
						section.getInt(name + ".Priority", 100), nameColour(section.getString(name + ".Name-Colour", ""), prefix)));
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
		// AUTO: the ranks and their placeholders stay, the tab list and the heads are left to the other plugin
		tablist = enabled && (enable.equalsIgnoreCase("true") || tabPlugin == null);
		luckPerms = cfg().getString("Ranks.Source", "HAWN").equalsIgnoreCase("LUCKPERMS");
		if (luckPerms && !HooksManager.luckPerms()) {
			Main.getInstance().getLogger().warning("Ranks: Source is LUCKPERMS, but LuckPerms is not installed: the ranks of Ranks.yml are used");
			luckPerms = false;
		}
		if (luckPerms && enabled) {
			// A group or a prefix changed in LuckPerms: shown at once (its event comes outside the main thread)
			RanksLuckPerms.listen();
		}

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
		if (!Objects.equals(before, now)) {
			load();
			if (enabled && !tablist) {
				Main.getInstance().getLogger().info("Ranks: " + now + " is installed, the tab list and the heads are left to it (Ranks.yml, Enable: true to use the ranks of Hawn there)");
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
	 * Back to the tab list and the names of the server: the names, the order, the teams removed (reload and stop).
	 */
	public static void clearAll() {
		if (task != null) {
			task.cancel();
			task = null;
		}
		if (HooksManager.luckPerms()) {
			RanksLuckPerms.stopListening();
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
			if (shownName.containsKey(p.getUniqueId())) {
				p.setPlayerListName(null);
			}
			if (LIST_ORDER != null && orderOf.containsKey(p.getUniqueId())) {
				setOrder(p, 0);
			}
			for (String team : teams.keySet()) {
				RankTeams.send(p, team, RankTeams.REMOVE, Collections.<String>emptyList(), null);
			}
		}

		current.clear();
		sources.clear();
		shownName.clear();
		orderOf.clear();
		teamOf.clear();
		teams.clear();
		looks.clear();
	}

	/* ------------------------------------------------------------------ the rank of a player */

	/**
	 * The rank of the player: LuckPerms (Source: LUCKPERMS), else by hand, by permission (the lowest priority), the default
	 * rank. Null for none.
	 */
	public static Rank rankOf(Player p) {
		return resolve(p).rank;
	}

	private static final class Found {
		final Rank rank;
		final Source source;

		Found(Rank rank, Source source) {
			this.rank = rank;
			this.source = source;
		}
	}

	private static Found resolve(Player p) {
		if (!enabled) {
			return new Found(null, Source.NONE);
		}

		if (luckPerms) {
			Rank r = RanksLuckPerms.rankOf(p, ranks);
			if (r != null) return new Found(r, Source.LUCKPERMS);
		}

		String hand = byHand.get(p.getName().toLowerCase(Locale.ROOT));
		if (hand != null && ranks.containsKey(hand)) {
			return new Found(ranks.get(hand), Source.HAND);
		}

		for (Rank r : ranks.values()) {
			if (p.hasPermission("hawn.rank." + r.name.toLowerCase(Locale.ROOT))) {
				return new Found(r, Source.PERMISSION);
			}
		}
		return defaultRank == null ? new Found(null, Source.NONE) : new Found(defaultRank, Source.DEFAULT);
	}

	public static Source sourceOf(Player p) {
		Source s = sources.get(p.getUniqueId());
		return s != null ? s : resolve(p).source;
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

	/* ------------------------------------------------------------------ the ranks given by hand (/hawn rank) */

	public static Rank byName(String name) {
		return name == null ? null : ranks.get(name.toLowerCase(Locale.ROOT));
	}

	public static Map<String, Rank> all() {
		return ranks;
	}

	public static boolean luckPermsSource() {
		return luckPerms;
	}

	/**
	 * Ranks.Players.&lt;player&gt;: &lt;rank&gt;, or removed with null. Saved, then shown at once.
	 */
	public static void setByHand(String player, String rank) {
		ConfigurationSection players = cfg().getConfigurationSection("Ranks.Players");
		String key = player;
		if (players != null) {
			for (String existing : players.getKeys(false)) {
				if (existing.equalsIgnoreCase(player)) key = existing;
			}
		}
		cfg().set("Ranks.Players." + key, rank == null ? null : rank.toLowerCase(Locale.ROOT));
		if (rank == null && players != null && players.getKeys(false).isEmpty()) {
			cfg().createSection("Ranks.Players");
		}
		RanksConfig.saveConfigFile();

		Map<String, String> hand = new HashMap<>(byHand);
		if (rank == null) {
			hand.remove(player.toLowerCase(Locale.ROOT));
		} else {
			hand.put(player.toLowerCase(Locale.ROOT), rank.toLowerCase(Locale.ROOT));
		}
		byHand = hand;

		Player online = Bukkit.getPlayerExact(player);
		if (online != null) {
			refresh(online, false);
		}
	}

	public static String byHandOf(String player) {
		return byHand.get(player.toLowerCase(Locale.ROOT));
	}

	/* ------------------------------------------------------------------ the tab list and the heads */

	private static boolean on(Player p, String option, String world) {
		return cfg().getBoolean("Ranks." + option + ".Enable", true) && cfg().getBoolean("Ranks.Worlds." + p.getWorld().getName() + "." + world, true);
	}

	/**
	 * The rank checked again, and shown again when it or the world changed.
	 */
	public static void refresh(Player p, boolean force) {
		if (!enabled || !p.isOnline()) {
			return;
		}

		Found found = resolve(p);
		Rank r = found.rank;
		UUID id = p.getUniqueId();
		Rank previous = r == null ? current.remove(id) : current.put(id, r);
		sources.put(id, found.source);

		// The players who see only the staff and the VIP see this one again, or no longer (1.4.9)
		if (!Objects.equals(previous, r)) {
			PlayerVisibility.updateTarget(p);
		}

		if (!tablist) {
			return;
		}

		// prefix + name + suffix in the tab list
		String name = null;
		if (r != null && on(p, "Tablist", "Tablist")) {
			name = r.prefix + p.getName() + r.suffix;
		}
		String before = shownName.get(id);
		if (force || !Objects.equals(before, name)) {
			if (name == null) {
				if (before != null || force) p.setPlayerListName(null);
				shownName.remove(id);
			} else {
				p.setPlayerListName(name);
				shownName.put(id, name);
			}
		}

		boolean sorting = on(p, "Sorting", "Sorting");
		boolean above = r != null && on(p, "Above-Head", "Above-Head");
		boolean colour = r != null && r.nameColour != null && on(p, "Name-Colour", "Name-Colour");

		// The order: the tab list order since 1.21.2
		if (LIST_ORDER != null) {
			int order = sorting && r != null ? Math.max(1, 10000 - Math.max(0, r.priority)) : 0;
			String key = String.valueOf(order);
			if (force || !key.equals(orderOf.get(id))) {
				setOrder(p, order);
				orderOf.put(id, key);
			}
		}

		// The team: above the head, the colour of the name, and the order before 1.21.2
		boolean sortByTeam = LIST_ORDER == null && sorting;
		String team = null;
		RankTeams.Look look = RankTeams.Look.NONE;
		if (r != null && (above || colour || sortByTeam)) {
			team = teamName(r, sortByTeam, above, colour, p);
			look = new RankTeams.Look(above ? r.prefix : "", above ? r.suffix : "", colour ? r.nameColour : null);
		} else if (r == null && sortByTeam) {
			team = NO_RANK_TEAM;
		}
		setTeam(p, team, look);
	}

	private static void setOrder(Player p, int order) {
		try {
			LIST_ORDER.invoke(p, order);
		} catch (ReflectiveOperationException ignored) {
			// 1.21.2+ only, found at startup: never fails there
		}
	}

	// hwn + the priority on 4 digits + the rank + what it shows: sorted by the client, 16 characters at most (1.16.5).
	// LuckPerms: the prefix can be the one of the player, not of the group: a team for each player (the name in place of the rank)
	private static String teamName(Rank r, boolean sorted, boolean above, boolean colour, Player p) {
		String rank = (luckPerms ? p.getName() : r.name).toLowerCase(Locale.ROOT);
		String priority = sorted ? String.format("%04d", Math.min(9999, Math.max(0, r.priority))) : "9999";
		char variant = above ? (colour ? 'a' : 'b') : (colour ? 'c' : 'd');
		return "hwn" + priority + (rank.length() > 8 ? rank.substring(0, 8) : rank) + variant;
	}

	// The player moved to their team, for every player online
	private static void setTeam(Player p, String team, RankTeams.Look look) {
		UUID id = p.getUniqueId();
		String before = teamOf.get(id);

		// The same team, maybe with a new prefix (Ranks.yml changed, a LuckPerms prefix changed)
		if (Objects.equals(before, team)) {
			if (team != null && !look.equals(looks.get(team)) && RankTeams.init()) {
				looks.put(team, look);
				for (Player viewer : Bukkit.getOnlinePlayers()) {
					RankTeams.send(viewer, team, RankTeams.UPDATE, Collections.<String>emptyList(), look);
				}
			}
			return;
		}
		if (!RankTeams.init()) {
			return;
		}

		List<String> name = Collections.singletonList(p.getName());
		if (before != null) {
			Set<String> members = teams.get(before);
			if (members != null) {
				members.remove(p.getName());
				for (Player viewer : Bukkit.getOnlinePlayers()) {
					RankTeams.send(viewer, before, members.isEmpty() ? RankTeams.REMOVE : RankTeams.REMOVE_PLAYERS, name, null);
				}
				if (members.isEmpty()) {
					teams.remove(before);
					looks.remove(before);
				}
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
			looks.put(team, look);
		}
		members.add(p.getName());
		teamOf.put(id, team);

		for (Player viewer : Bukkit.getOnlinePlayers()) {
			RankTeams.send(viewer, team, created ? RankTeams.CREATE : RankTeams.ADD_PLAYERS, created ? members : name, looks.get(team));
		}
	}

	// A player who joins gets the teams of the others
	private static void sendTeamsTo(Player viewer) {
		if (teams.isEmpty() || !RankTeams.init()) {
			return;
		}
		for (Map.Entry<String, Set<String>> team : teams.entrySet()) {
			RankTeams.send(viewer, team.getKey(), RankTeams.CREATE, team.getValue(), looks.get(team.getKey()));
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

	/**
	 * The ranks of Ranks.List with a Name-Colour that is not one of the 16 colours.
	 */
	public static List<String> wrongColours() {
		List<String> wrong = new ArrayList<>();
		ConfigurationSection section = cfg().getConfigurationSection("Ranks.List");
		if (section != null) {
			for (String name : section.getKeys(false)) {
				String value = section.getString(name + ".Name-Colour", "");
				if (value != null && !value.trim().isEmpty() && !value.trim().equalsIgnoreCase("NONE") && colourName(value) == null) {
					wrong.add(name + ": " + value);
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
		sources.remove(id);
		shownName.remove(id);
		orderOf.remove(id);
		if (teamOf.containsKey(id)) {
			setTeam(p, null, RankTeams.Look.NONE);
		}
		teamOf.remove(id);
	}
}
