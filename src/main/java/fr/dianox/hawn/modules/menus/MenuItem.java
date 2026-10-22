package fr.dianox.hawn.modules.menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * An item of a menu, as written in its file (Menus/&lt;menu&gt;.yml, Items.&lt;key&gt;).
 */
public final class MenuItem {

	final String key;
	final String path;
	final List<Integer> slots;
	final String material;
	final int amount;
	final String name;
	final List<String> lore;
	final boolean glow;
	final String itemModel;
	final Integer customModelData;
	final boolean update;
	final int priority;
	final List<String> view;
	final List<String> click;
	final List<String> deny;
	final List<String> actions;
	final Map<ClickType, List<String>> byClick = new EnumMap<>(ClickType.class);
	// Set by its menu: the animations of the menu, and whether this item uses one
	Menu menu;
	boolean animated;

	// The options of the click types: Left-Actions, Shift-Right-Actions...
	static final String[][] CLICKS = {
			{"Left-Actions", "LEFT"}, {"Right-Actions", "RIGHT"}, {"Shift-Left-Actions", "SHIFT_LEFT"},
			{"Shift-Right-Actions", "SHIFT_RIGHT"}, {"Middle-Actions", "MIDDLE"}, {"Drop-Actions", "DROP"}};

	MenuItem(String key, String path, ConfigurationSection c, int size, int order) {
		this.key = key;
		this.path = path;
		this.slots = slots(c, size);
		this.material = c.getString("Material", "STONE");
		this.amount = Math.max(1, c.getInt("Amount", 1));
		this.name = c.getString("Name", null);
		this.lore = c.getStringList("Lore");
		this.glow = c.getBoolean("Glow");
		this.itemModel = c.getString("Item-Model", null);
		this.customModelData = c.isInt("Custom-Model-Data") ? c.getInt("Custom-Model-Data") : null;
		this.update = c.getBoolean("Update");
		// Without a priority, the order of the file
		this.priority = c.isInt("Priority") ? c.getInt("Priority") : 100000 + order;
		this.view = c.getStringList("View-Requirements");
		if (c.isConfigurationSection("Click-Requirements")) {
			this.click = c.getStringList("Click-Requirements.Conditions");
			this.deny = c.getStringList("Click-Requirements.Deny");
		} else {
			this.click = c.getStringList("Click-Requirements");
			this.deny = Collections.emptyList();
		}
		this.actions = c.getStringList("Actions");
		for (String[] click : CLICKS) {
			if (c.isList(click[0])) {
				byClick.put(ClickType.valueOf(click[1]), c.getStringList(click[0]));
			}
		}
	}

	/**
	 * The actions of this click: the ones of its type when written, else Actions.
	 */
	List<String> actionsFor(ClickType type) {
		List<String> list = byClick.get(type);
		if (list == null && type == ClickType.CONTROL_DROP) {
			list = byClick.get(ClickType.DROP);
		}
		return list != null ? list : actions;
	}

	/**
	 * Slot: 11, or Slots: [0-8, 18, '20-22'] (also "0-8, 18" in one text). The slots outside the menu are left out
	 * (see /hawn debug).
	 */
	static List<Integer> slots(ConfigurationSection c, int size) {
		List<String> parts = new ArrayList<>();
		if (c.isList("Slots")) {
			for (Object o : c.getList("Slots")) {
				parts.add(String.valueOf(o));
			}
		} else if (c.isSet("Slots")) {
			Collections.addAll(parts, String.valueOf(c.get("Slots")).split(","));
		} else if (c.isSet("Slot")) {
			parts.add(String.valueOf(c.get("Slot")));
		}

		List<Integer> slots = new ArrayList<>();
		for (String part : parts) {
			int[] range = range(part);
			if (range == null) continue;
			for (int i = range[0]; i <= range[1]; i++) {
				if (i >= 0 && i < size && !slots.contains(i)) {
					slots.add(i);
				}
			}
		}
		return slots;
	}

	/**
	 * "5" or "0-8", null when it is not a number.
	 */
	static int[] range(String part) {
		String p = part.trim();
		try {
			int dash = p.indexOf('-', 1);
			if (dash > 0) {
				int a = Integer.parseInt(p.substring(0, dash).trim());
				int b = Integer.parseInt(p.substring(dash + 1).trim());
				return new int[] {Math.min(a, b), Math.max(a, b)};
			}
			int n = Integer.parseInt(p);
			return new int[] {n, n};
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
