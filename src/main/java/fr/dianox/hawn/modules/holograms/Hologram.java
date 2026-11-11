package fr.dianox.hawn.modules.holograms;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A hologram of Holograms/&lt;name&gt;.yml (since 1.6): lines of text at a place, sent in packets to each player near
 * it (no entity on the server), with the placeholders and the language of that player.
 */
final class Hologram {

	enum Billboard { FIXED, VERTICAL, HORIZONTAL, CENTER }

	final String name;
	final File file;

	String world;
	double x, y, z;
	List<String> lines = new ArrayList<>();

	int viewDistance = 32;
	int refresh = 20;
	Billboard billboard = Billboard.CENTER;
	// null: the background of Minecraft
	Integer background;
	boolean shadow = true;
	float scale = 1.0f;
	int lineWidth = 200;
	double lineHeight = 0.28;
	String permission = "";

	// {anim_<name>}: refresh-time-ticks, frames
	final Map<String, Integer> animationTicks = new LinkedHashMap<>();
	final Map<String, List<String>> animationFrames = new LinkedHashMap<>();

	Hologram(String name, File file) {
		this.name = name;
		this.file = file;
	}

	static Hologram load(File file) throws Exception {
		YamlConfiguration c = new YamlConfiguration();
		c.load(file);

		String name = file.getName().substring(0, file.getName().length() - 4).toLowerCase(Locale.ROOT);
		Hologram h = new Hologram(name, file);
		h.world = c.getString("Location.World", "world");
		h.x = c.getDouble("Location.X");
		h.y = c.getDouble("Location.Y");
		h.z = c.getDouble("Location.Z");
		h.lines = new ArrayList<>(c.getStringList("Lines"));
		h.viewDistance = Math.max(1, c.getInt("View-Distance", 32));
		h.refresh = Math.max(0, c.getInt("Refresh", 20));
		try {
			h.billboard = Billboard.valueOf(c.getString("Billboard", "CENTER").toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException e) {
			h.billboard = Billboard.CENTER;
		}
		h.background = colour(c.getString("Background", "DEFAULT"));
		h.shadow = c.getBoolean("Shadow", true);
		h.scale = (float) c.getDouble("Scale", 1.0);
		h.lineWidth = c.getInt("Line-Width", 200);
		h.lineHeight = c.getDouble("Line-Height", 0.28);
		h.permission = c.getString("Permission", "");

		ConfigurationSection anims = c.getConfigurationSection("Animations");
		if (anims != null) {
			for (String anim : anims.getKeys(false)) {
				List<String> frames = anims.getStringList(anim + ".text");
				if (!frames.isEmpty()) {
					h.animationTicks.put(anim, Math.max(1, anims.getInt(anim + ".refresh-time-ticks", 20)));
					h.animationFrames.put(anim, frames);
				}
			}
		}
		return h;
	}

	// DEFAULT: the background of Minecraft, NONE: none, #AARRGGBB or #RRGGBB
	private static Integer colour(String s) {
		if (s == null || s.equalsIgnoreCase("DEFAULT")) {
			return null;
		}
		if (s.equalsIgnoreCase("NONE") || s.equalsIgnoreCase("TRANSPARENT")) {
			return 0;
		}
		String hex = s.startsWith("#") ? s.substring(1) : s;
		try {
			long v = Long.parseLong(hex, 16);
			return (int) (hex.length() <= 6 ? 0xFF000000L | v : v);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	World bukkitWorld() {
		return Bukkit.getWorld(world);
	}

	Location location() {
		World w = bukkitWorld();
		return w == null ? null : new Location(w, x, y, z);
	}

	/**
	 * The lines with the current frame of each {anim_&lt;name&gt;} (the same for everyone, from the time).
	 */
	List<String> animatedLines() {
		if (animationFrames.isEmpty()) {
			return lines;
		}
		long tick = System.currentTimeMillis() / 50L;
		List<String> out = new ArrayList<>(lines.size());
		for (String line : lines) {
			int start = line.indexOf("{anim_");
			while (start >= 0) {
				int end = line.indexOf('}', start);
				if (end < 0) break;
				String anim = line.substring(start + 6, end);
				List<String> frames = animationFrames.get(anim);
				if (frames == null) {
					start = line.indexOf("{anim_", end);
					continue;
				}
				String frame = frames.get((int) ((tick / animationTicks.get(anim)) % frames.size()));
				line = line.substring(0, start) + frame + line.substring(end + 1);
				start = line.indexOf("{anim_", start + frame.length());
			}
			out.add(line);
		}
		return out;
	}

	boolean animated() {
		for (String line : lines) {
			if (line.contains("{anim_")) return true;
		}
		return false;
	}

	/**
	 * Writes the place and the lines (the other options of the file are kept).
	 */
	void save() throws IOException {
		YamlConfiguration c = file.exists() ? YamlConfiguration.loadConfiguration(file) : new YamlConfiguration();
		if (!file.exists()) {
			c.options().header("A hologram of Hawn (since 1.6): /hawn hologram, or edit this file then /hawn reload.\n"
					+ "Lines: placeholders, {lang_<key>} (Translations/) and {anim_<name>} (Animations below).\n"
					+ "Billboard: CENTER, VERTICAL, HORIZONTAL or FIXED. Background: DEFAULT, NONE or #AARRGGBB (1.19.4+).\n"
					+ "Refresh: ticks between two updates of the placeholders (0: never). Permission: only the players with it see it.\n"
					+ "Documentation: https://hawn.gitbook.io/hawn/features/holograms");
			c.set("View-Distance", viewDistance);
			c.set("Refresh", refresh);
			c.set("Billboard", billboard.name());
			c.set("Background", "DEFAULT");
			c.set("Shadow", shadow);
			c.set("Scale", scale);
			c.set("Line-Width", lineWidth);
			c.set("Line-Height", lineHeight);
			c.set("Permission", permission);
		}
		c.set("Location.World", world);
		c.set("Location.X", x);
		c.set("Location.Y", y);
		c.set("Location.Z", z);
		c.set("Lines", lines);
		c.save(file);
	}

	static List<String> names(Map<String, Hologram> all) {
		List<String> out = new ArrayList<>(all.keySet());
		Collections.sort(out);
		return out;
	}

	static File folder() {
		return new File(Main.getInstance().getDataFolder(), "Holograms");
	}
}
