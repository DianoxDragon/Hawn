package fr.dianox.hawn.modules.menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.inventory.InventoryType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * A menu of the Menus/ folder: Menus/&lt;name&gt;.yml.
 */
public final class Menu {

	final String name;
	final String title;
	final InventoryType type;
	final int size;
	final List<String> commands;
	final String permission;
	final List<String> worlds;
	final List<String> openActions;
	final List<String> closeActions;
	final int refresh;
	final MenuItem fill;
	final List<MenuItem> items = new ArrayList<>();
	// The items of each slot, in the order of their priority
	final Map<Integer, List<MenuItem>> bySlot = new HashMap<>();

	/**
	 * An animation of the menu (Animations.&lt;name&gt;): {anim_&lt;name&gt;} in the title, a name, a lore, a material.
	 */
	static final class Animation {
		final int ticks;
		final List<String> frames;

		Animation(int ticks, List<String> frames) {
			this.ticks = ticks;
			this.frames = frames;
		}
	}

	final Map<String, Animation> animations = new HashMap<>();
	final boolean titleAnimated;
	// Ticks between two checks of the animations (the greatest common divisor of their speeds), 0 without animation
	final int animationTicks;

	Menu(String name, YamlConfiguration c) {
		this.name = name;
		this.title = c.getString("Title", name);
		this.type = type(c.getString("Type", null));
		this.size = type == null ? 9 * Math.max(1, Math.min(6, c.getInt("Rows", 3))) : type.getDefaultSize();
		this.commands = new ArrayList<>();
		if (c.isList("Open.Commands")) {
			for (String s : c.getStringList("Open.Commands")) commands.add(command(s));
		} else if (c.isString("Open.Commands")) {
			commands.add(command(c.getString("Open.Commands")));
		}
		this.permission = c.getString("Open.Permission", null);
		this.worlds = c.getStringList("Open.Worlds");
		this.openActions = c.getStringList("Open.Actions");
		this.closeActions = c.getStringList("Close-Actions");
		this.refresh = Math.max(0, c.getInt("Refresh", 0));

		ConfigurationSection anims = c.getConfigurationSection("Animations");
		for (String key : anims == null ? Collections.<String>emptySet() : anims.getKeys(false)) {
			List<String> frames = anims.getStringList(key + ".text");
			if (!frames.isEmpty()) {
				animations.put(key.toLowerCase(java.util.Locale.ROOT), new Animation(Math.max(1, anims.getInt(key + ".refresh-time-ticks", 20)), frames));
			}
		}
		this.titleAnimated = animated(title);

		if (c.isConfigurationSection("Fill")) {
			this.fill = new MenuItem("Fill", "Fill", c.getConfigurationSection("Fill"), size, 0);
		} else if (c.isString("Fill")) {
			YamlConfiguration f = new YamlConfiguration();
			f.set("Material", c.getString("Fill"));
			f.set("Name", " ");
			this.fill = new MenuItem("Fill", "Fill", f, size, 0);
		} else {
			this.fill = null;
		}

		ConfigurationSection section = c.getConfigurationSection("Items");
		int order = 0;
		for (String key : section == null ? Collections.<String>emptySet() : section.getKeys(false)) {
			if (!section.isConfigurationSection(key)) continue;
			MenuItem item = new MenuItem(key, "Items." + key, section.getConfigurationSection(key), size, order++);
			items.add(item);
			for (int slot : item.slots) {
				bySlot.computeIfAbsent(slot, s -> new ArrayList<>()).add(item);
			}
		}
		for (List<MenuItem> list : bySlot.values()) {
			list.sort((a, b) -> Integer.compare(a.priority, b.priority));
		}

		boolean any = titleAnimated;
		for (MenuItem item : items) {
			item.menu = this;
			item.animated = animated(item);
			any |= item.animated;
		}
		if (fill != null) {
			fill.menu = this;
			fill.animated = animated(fill);
			any |= fill.animated;
		}
		int gcd = 0;
		for (Animation a : animations.values()) {
			gcd = gcd == 0 ? a.ticks : gcd(gcd, a.ticks);
		}
		this.animationTicks = any ? Math.max(1, gcd) : 0;
	}

	private static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}

	private static boolean animated(String s) {
		return s != null && s.contains("{anim_");
	}

	private static boolean animated(MenuItem item) {
		if (animated(item.name) || animated(item.material)) return true;
		for (String line : item.lore) {
			if (animated(line)) return true;
		}
		return false;
	}

	/**
	 * The frames of the animations at this moment: every player of the menu sees the same frame.
	 * An unknown animation stays as it is written (see /hawn debug).
	 */
	String animate(String s) {
		if (s == null || animations.isEmpty() || !s.contains("{anim_")) {
			return s;
		}
		long tick = System.currentTimeMillis() / 50L;
		StringBuilder out = new StringBuilder();
		int from = 0;
		int start;
		while ((start = s.indexOf("{anim_", from)) >= 0) {
			int end = s.indexOf('}', start);
			if (end < 0) break;
			Animation a = animations.get(s.substring(start + 6, end).toLowerCase(java.util.Locale.ROOT));
			out.append(s, from, start);
			out.append(a == null ? s.substring(start, end + 1) : a.frames.get((int) ((tick / a.ticks) % a.frames.size())));
			from = end + 1;
		}
		return out.append(s.substring(from)).toString();
	}

	boolean hasUpdates() {
		for (MenuItem item : items) {
			if (item.update) return true;
		}
		return false;
	}

	private static String command(String s) {
		String c = s.trim().toLowerCase(Locale.ROOT);
		return c.startsWith("/") ? c.substring(1) : c;
	}

	/**
	 * HOPPER, DISPENSER, DROPPER; null for a chest (Rows).
	 */
	static InventoryType type(String value) {
		if (value == null) return null;
		switch (value.trim().toUpperCase(Locale.ROOT)) {
			case "HOPPER": return InventoryType.HOPPER;
			case "DISPENSER": return InventoryType.DISPENSER;
			case "DROPPER": return InventoryType.DROPPER;
			default: return null;
		}
	}
}
