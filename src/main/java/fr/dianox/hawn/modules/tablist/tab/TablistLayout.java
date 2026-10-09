package fr.dianox.hawn.modules.tablist.tab;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * A header and a footer, and who sees them: the default tab list (Tablist) or one of Custom-Tablists.
 */
public class TablistLayout {

	private final String name;
	private final int priority;
	private final String permission;
	// null = every world
	private final Set<String> worlds;
	// Empty list = nothing shown
	private final List<String> header;
	private final List<String> footer;

	private TablistLayout(String name, int priority, String permission, Set<String> worlds, List<String> header, List<String> footer) {
		this.name = name;
		this.priority = priority;
		this.permission = permission;
		this.worlds = worlds;
		this.header = header;
		this.footer = footer;
	}

	/**
	 * The default tab list, seen when no custom tab list matches.
	 */
	public static TablistLayout main(ConfigurationSection section) {
		return new TablistLayout("Tablist", Integer.MIN_VALUE, null, null, lines(section, "header"), lines(section, "footer"));
	}

	/**
	 * A custom tab list. A missing header or footer is the one of the default tab list.
	 */
	public static TablistLayout custom(String name, ConfigurationSection section, TablistLayout main) {
		// Like the scoreboards: the permission is always hawn.tablist.<name>, the file only says if it is needed
		String permission = section.getBoolean("permission") ? "hawn.tablist." + name : null;

		Set<String> worlds = null;
		if (section.isConfigurationSection("World") && !section.getBoolean("World.All_World")) {
			worlds = new HashSet<>();
			for (String world : section.getStringList("World.Worlds")) {
				worlds.add(world.toLowerCase(Locale.ROOT));
			}
		}

		List<String> header = section.isSet("header") ? lines(section, "header") : main.header;
		List<String> footer = section.isSet("footer") ? lines(section, "footer") : main.footer;

		return new TablistLayout(name, section.getInt("priority"), permission, worlds, header, footer);
	}

	private static List<String> lines(ConfigurationSection section, String part) {
		if (!section.getBoolean(part + ".enabled", true)) {
			return Collections.emptyList();
		}
		return section.getStringList(part + ".message");
	}

	public boolean matches(Player p) {
		if (worlds != null && !worlds.contains(p.getWorld().getName().toLowerCase(Locale.ROOT))) {
			return false;
		}
		return permission == null || p.hasPermission(permission);
	}

	public String getName() {
		return name;
	}

	public int getPriority() {
		return priority;
	}

	public String getPermission() {
		return permission;
	}

	public List<String> getHeader() {
		return header;
	}

	public List<String> getFooter() {
		return footer;
	}
}
