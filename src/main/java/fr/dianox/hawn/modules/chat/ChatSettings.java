package fr.dianox.hawn.modules.chat;

import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * The chat options of a player: those of their group of worlds (Per-World-Chat.Groups.<group>) when the group
 * sets them, the ones of Events/Chat.yml otherwise.
 */
public final class ChatSettings {

	// The worlds in no group talk together
	public static final String OTHER_WORLDS = "";

	private final String group;
	private final ConfigurationSection section;

	private ChatSettings(String group, ConfigurationSection section) {
		this.group = group;
		this.section = section;
	}

	private static YamlConfiguration cfg() {
		return OnChatConfig.getConfig();
	}

	public static boolean perWorld() {
		return cfg().getBoolean("Per-World-Chat.Enable");
	}

	/**
	 * @return the group of the world, OTHER_WORLDS when it is in none, null when the chat is not split by world
	 */
	public static String groupOf(String world) {
		if (!perWorld()) {
			return null;
		}

		String group = groups().get(world.toLowerCase(Locale.ROOT));
		return group == null ? OTHER_WORLDS : group;
	}

	// 72: world -> group, made once for each loaded Events/Chat.yml (a /hawn reload loads a new one), not at each message
	private static YamlConfiguration groupsFor;
	private static volatile Map<String, String> groups = Collections.emptyMap();

	private static synchronized Map<String, String> groups() {
		YamlConfiguration cfg = cfg();
		if (cfg != groupsFor) {
			Map<String, String> map = new HashMap<>();
			ConfigurationSection section = cfg.getConfigurationSection("Per-World-Chat.Groups");
			if (section != null) {
				for (String name : section.getKeys(false)) {
					for (String w : section.getStringList(name + ".Worlds")) {
						// A world in two groups: the first one, as before (the startup report warns)
						map.putIfAbsent(w.toLowerCase(Locale.ROOT), name);
					}
				}
			}
			groups = map;
			groupsFor = cfg;
		}
		return groups;
	}

	public static ChatSettings of(Player p) {
		String group = groupOf(p.getWorld().getName());
		ConfigurationSection section = group == null || group.isEmpty() ? null : cfg().getConfigurationSection("Per-World-Chat.Groups." + group);
		return new ChatSettings(group, section);
	}

	/**
	 * Both players see each other's messages.
	 */
	public boolean sameChat(Player other) {
		return group == null || Objects.equals(group, groupOf(other.getWorld().getName()));
	}

	public String group() {
		return group;
	}

	private ConfigurationSection source(String path) {
		return section != null && section.contains(path) ? section : cfg();
	}

	public boolean contains(String path) {
		return section != null && section.contains(path) || cfg().contains(path);
	}

	/**
	 * true when the group of the player sets this option itself.
	 */
	public boolean inGroup(String path) {
		return section != null && section.contains(path);
	}

	public boolean bool(String path) {
		return source(path).getBoolean(path);
	}

	public int integer(String path, int def) {
		return source(path).getInt(path, def);
	}

	public double decimal(String path, double def) {
		return source(path).getDouble(path, def);
	}

	public String string(String path, String def) {
		return source(path).getString(path, def);
	}

	public List<String> list(String path) {
		return source(path).getStringList(path);
	}
}
