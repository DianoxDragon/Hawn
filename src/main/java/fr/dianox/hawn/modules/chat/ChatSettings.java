package fr.dianox.hawn.modules.chat;

import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
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

		ConfigurationSection groups = cfg().getConfigurationSection("Per-World-Chat.Groups");
		if (groups != null) {
			for (String name : groups.getKeys(false)) {
				for (String w : groups.getStringList(name + ".Worlds")) {
					if (w.equalsIgnoreCase(world)) {
						return name;
					}
				}
			}
		}
		return OTHER_WORLDS;
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
