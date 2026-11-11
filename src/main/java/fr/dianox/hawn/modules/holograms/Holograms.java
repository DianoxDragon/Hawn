package fr.dianox.hawn.modules.holograms;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.utility.Debug;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.Task;
import fr.dianox.hawn.utility.Tasks;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The holograms of Holograms/ (since 1.6). Every 5 ticks, on the thread of each player: the holograms near them are
 * sent (packets, no entity on the server), the others removed, and the texts updated every Refresh ticks with the
 * placeholders and the language of the player. Needs the plugin PacketEvents.
 */
public final class Holograms {

	private Holograms() {}

	private static final long CHECK_TICKS = 5L;

	private static volatile Map<String, Hologram> holograms = Collections.emptyMap();
	// The first entity id of each hologram (one per line for the armor stands), far from the ids of the server
	private static final Map<String, Integer> ids = new ConcurrentHashMap<>();
	private static int nextId = 2_000_000_000;

	// What each player sees: hologram -> its texts and the tick of its next update
	private static final Map<UUID, Map<String, View>> views = new ConcurrentHashMap<>();

	private static Task task;
	private static boolean warned;

	private static final class View {
		List<String> texts;
		long next;
	}

	public static boolean available() {
		return Bukkit.getPluginManager().isPluginEnabled("packetevents");
	}

	public static Map<String, Hologram> all() {
		return holograms;
	}

	// The files of Holograms/ with Enable: false (the example the first time): listed and editable, never shown
	private static volatile Map<String, Hologram> off = Collections.emptyMap();

	public static Map<String, Hologram> off() {
		return off;
	}

	/**
	 * /hawn hologram enable|disable: Enable of the file, then shown or hidden at once.
	 */
	static void setEnabled(Hologram h, boolean on) throws java.io.IOException {
		org.bukkit.configuration.file.YamlConfiguration c = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(h.file);
		c.set("Enable", on);
		c.save(h.file);
		Map<String, Hologram> copy = new LinkedHashMap<>(off);
		if (on) {
			copy.remove(h.name);
			off = copy;
			put(h);
		} else {
			remove(h.name);
			copy.put(h.name, h);
			off = copy;
		}
	}

	static void forget(String name) {
		Map<String, Hologram> copy = new LinkedHashMap<>(off);
		copy.remove(name);
		off = copy;
	}

	/**
	 * At startup and /hawn reload: every file of Holograms/ (the example the first time).
	 */
	public static synchronized void load() {
		hideAll();

		File folder = Hologram.folder();
		if (!folder.exists()) {
			writeExample(folder);
		}

		Map<String, Hologram> loaded = new LinkedHashMap<>();
		Map<String, Hologram> disabled = new LinkedHashMap<>();
		File[] files = folder.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".yml"));
		if (files != null) {
			Arrays.sort(files);
			for (File f : files) {
				try {
					Hologram h = Hologram.load(f);
					if (org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(f).getBoolean("Enable", true)) {
						loaded.put(h.name, h);
					} else {
						disabled.put(h.name, h);
					}
				} catch (Exception e) {
					// Listed by /hawn debug
					Debug.hidden("Holograms/" + f.getName(), e);
				}
			}
		}
		holograms = loaded;
		off = disabled;

		for (Hologram h : loaded.values()) {
			ids.computeIfAbsent(h.name, n -> {
				int id = nextId;
				nextId -= 128;
				return id;
			});
		}

		if (!loaded.isEmpty() && !available() && !warned) {
			warned = true;
			Main.getInstance().getLogger().warning("Holograms: " + loaded.size() + " hologram(s) in Holograms/, but they need the plugin PacketEvents (https://modrinth.com/plugin/packetevents)");
		}

		if (task == null && available()) {
			task = Tasks.timer(() -> Tasks.forEachPlayer(Holograms::update), 20L, CHECK_TICKS);
		}
	}

	public static void stop() {
		Tasks.cancel(task);
		task = null;
		hideAll();
	}

	private static void writeExample(File folder) {
		if (!folder.mkdirs()) {
			return;
		}
		Hologram h = new Hologram("example", new File(folder, "example.yml"));
		org.bukkit.World w = Bukkit.getWorlds().isEmpty() ? null : Bukkit.getWorlds().get(0);
		Location spawn = w == null ? null : w.getSpawnLocation();
		h.world = w == null ? "world" : w.getName();
		h.x = spawn == null ? 0.5 : spawn.getBlockX() + 0.5;
		h.y = spawn == null ? 100 : spawn.getBlockY() + 3;
		h.z = spawn == null ? 0.5 : spawn.getBlockZ() + 0.5;
		h.lines = new ArrayList<>(Arrays.asList("&6&l{lang_text.hologram-title}", "&7{lang_text.hologram-online}", "{anim_arrows}"));
		try {
			h.save();
			org.bukkit.configuration.file.YamlConfiguration c = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(h.file);
			c.set("Enable", false);
			c.set("Animations.arrows.refresh-time-ticks", 10);
			c.set("Animations.arrows.text", Arrays.asList("&e> &6/hawn hologram &e<", "&6> &e/hawn hologram &6<"));
			c.save(h.file);
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("Holograms/example.yml could not be written: " + e);
		}
	}

	/* ------------------------------------------------------------------ what a player sees */

	private static void update(Player p) {
		if (!p.isOnline()) {
			return;
		}
		Map<String, View> seen = views.computeIfAbsent(p.getUniqueId(), u -> new ConcurrentHashMap<>());
		long now = System.currentTimeMillis() / 50L;
		Location at = p.getLocation();

		for (Hologram h : holograms.values()) {
			View v = seen.get(h.name);
			boolean near = at.getWorld() != null && at.getWorld().getName().equals(h.world)
					&& square(at.getX() - h.x) + square(at.getY() - h.y) + square(at.getZ() - h.z) <= (double) h.viewDistance * h.viewDistance
					&& (h.permission == null || h.permission.isEmpty() || p.hasPermission(h.permission));

			try {
				if (near && v == null) {
					v = new View();
					v.texts = texts(p, h);
					show(p, h, v.texts);
					v.next = now + h.refresh;
					seen.put(h.name, v);
				} else if (!near && v != null) {
					hide(p, h, v);
					seen.remove(h.name);
				} else if (near && (h.animated() || h.refresh > 0 && now >= v.next)) {
					List<String> texts = texts(p, h);
					if (!texts.equals(v.texts)) {
						change(p, h, v, texts);
						v.texts = texts;
					}
					v.next = now + h.refresh;
				}
			} catch (Throwable e) {
				Debug.hidden("Hologram " + h.name, e);
			}
		}
	}

	private static double square(double d) {
		return d * d;
	}

	// The lines for this player: the frames of the animations, their placeholders and their language, the colours
	private static List<String> texts(Player p, Hologram h) {
		List<String> out = new ArrayList<>();
		for (String line : h.animatedLines()) {
			String s = PlaceHolders.ReplaceMainplaceholderP(line, p);
			if (HooksManager.papi()) {
				s = PlaceholderAPI.setPlaceholders(p, s);
			}
			out.add(MessageUtils.colourTheStuff(s));
		}
		return out;
	}

	private static void show(Player p, Hologram h, List<String> texts) {
		int id = ids.get(h.name);
		if (HologramPackets.textDisplay()) {
			HologramPackets.spawnDisplay(p, id, h, String.join("\n", texts));
		} else {
			for (int i = 0; i < texts.size() && i < 128; i++) {
				HologramPackets.spawnStand(p, id - i, h.x, h.y + (texts.size() - 1 - i) * h.lineHeight, h.z, texts.get(i));
			}
		}
	}

	private static void change(Player p, Hologram h, View v, List<String> texts) {
		int id = ids.get(h.name);
		if (HologramPackets.textDisplay()) {
			HologramPackets.updateDisplay(p, id, h, String.join("\n", texts));
		} else if (texts.size() != v.texts.size()) {
			// Another number of lines: the stands are placed again
			hide(p, h, v);
			show(p, h, texts);
		} else {
			for (int i = 0; i < texts.size(); i++) {
				if (!texts.get(i).equals(v.texts.get(i))) {
					HologramPackets.updateStand(p, id - i, texts.get(i));
				}
			}
		}
	}

	private static void hide(Player p, Hologram h, View v) {
		Integer id = ids.get(h.name);
		if (id == null) {
			return;
		}
		int count = HologramPackets.textDisplay() ? 1 : Math.max(1, v.texts.size());
		int[] all = new int[count];
		for (int i = 0; i < count; i++) {
			all[i] = id - i;
		}
		HologramPackets.destroy(p, all);
	}

	private static void hideAll() {
		if (!available()) {
			views.clear();
			return;
		}
		Map<String, Hologram> old = holograms;
		for (Map.Entry<UUID, Map<String, View>> e : views.entrySet()) {
			Player p = Bukkit.getPlayer(e.getKey());
			if (p == null) continue;
			for (Map.Entry<String, View> seen : e.getValue().entrySet()) {
				Hologram h = old.get(seen.getKey());
				if (h != null) {
					try {
						hide(p, h, seen.getValue());
					} catch (Throwable ignored) {}
				}
			}
		}
		views.clear();
	}

	/**
	 * After a change of a hologram (command): sent again to the players who see it.
	 */
	static void refresh(String name) {
		Hologram h = holograms.get(name);
		for (Map.Entry<UUID, Map<String, View>> e : views.entrySet()) {
			View v = e.getValue().remove(name);
			Player p = Bukkit.getPlayer(e.getKey());
			if (v != null && p != null && h != null) {
				try {
					hide(p, h, v);
				} catch (Throwable ignored) {}
			}
		}
	}

	static void put(Hologram h) {
		Map<String, Hologram> copy = new LinkedHashMap<>(holograms);
		refresh(h.name);
		copy.put(h.name, h);
		ids.computeIfAbsent(h.name, n -> {
			int id = nextId;
			nextId -= 128;
			return id;
		});
		holograms = copy;
		if (task == null && available()) {
			task = Tasks.timer(() -> Tasks.forEachPlayer(Holograms::update), 20L, CHECK_TICKS);
		}
	}

	static void remove(String name) {
		refresh(name);
		Map<String, Hologram> copy = new LinkedHashMap<>(holograms);
		copy.remove(name);
		holograms = copy;
	}

	/**
	 * The client forgets the entities at a respawn or a change of world, and at the join: they are sent again.
	 */
	public static final class Events implements Listener {

		@EventHandler(priority = EventPriority.MONITOR)
		public void onJoin(PlayerJoinEvent e) {
			views.remove(e.getPlayer().getUniqueId());
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void onQuit(PlayerQuitEvent e) {
			views.remove(e.getPlayer().getUniqueId());
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void onWorld(PlayerChangedWorldEvent e) {
			views.remove(e.getPlayer().getUniqueId());
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void onRespawn(PlayerRespawnEvent e) {
			views.remove(e.getPlayer().getUniqueId());
		}
	}
}
