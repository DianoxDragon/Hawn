package fr.dianox.hawn.utility.config.configs;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Ranks.yml (since 1.4.7): the ranks of the players, their prefix and suffix in the tab list, and the order of the tab list.
 */
public class RanksConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public RanksConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Ranks.yml");
		Config = YamlConfiguration.loadConfiguration(file);

		if (!pl.getDataFolder().exists()) {
			pl.getDataFolder().mkdir();
		}

		create();
	}

	public static File getFile() {
		return file;
	}

	public static YamlConfiguration getConfig() {
		return Config;
	}

	public static void reloadConfig() {
		loadConfig(pl);
	}

	public static void saveConfigFile() {
		try {
			Config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void rank(String name, boolean enable, String prefix, String suffix, int priority, String colour) {
		String path = "Ranks.List." + name + ".";
		Config.set(path + "Enable", enable);
		Config.set(path + "Prefix", prefix);
		Config.set(path + "Suffix", suffix);
		Config.set(path + "Priority", priority);
		Config.set(path + "Name-Colour", colour);
	}

	private static void create() {
		YamlConfiguration loaded = Config;
		Config = new YamlConfiguration();

		// AUTO: off when TAB or NametagEdit is installed (they show the ranks themselves), true / false: always on / off
		Config.set("Ranks.Enable", "AUTO");
		// The rank of the players who have no other one (off in the example below)
		Config.set("Ranks.Default-Rank", "default");
		// The ranks given by permission are checked again every few seconds (a rank given in LuckPerms shows without a rejoin)
		Config.set("Ranks.Refresh-Seconds", 5);
		// Since 1.4.8: HAWN (the ranks below) or LUCKPERMS (the group, prefix, suffix and weight of LuckPerms)
		Config.set("Ranks.Source", "HAWN");
		Config.set("Ranks.Tablist.Enable", true);
		Config.set("Ranks.Sorting.Enable", true);
		// Since 1.4.8: the prefix and the suffix above the head, and the colour of the name
		Config.set("Ranks.Above-Head.Enable", true);
		Config.set("Ranks.Name-Colour.Enable", true);

		// The permission hawn.rank.<rank> gives the rank (not given to the operators by default). The lowest Priority is shown first
		rank("owner", true, "&4[Owner] &c", "", 1, "RED");
		rank("admin", true, "&c[Admin] &6", "", 2, "GOLD");
		rank("vip", true, "&6[VIP] &e", "", 10, "YELLOW");
		rank("default", false, "&7", "", 100, "GRAY");

		// <player>: <rank>, a rank given by hand, before the permissions
		Config.set("Ranks.Players", new LinkedHashMap<String, Object>());
		// <world>: Tablist / Sorting false to turn them off for the players of this world
		Config.set("Ranks.Worlds", new LinkedHashMap<String, Object>());

		Config = ConfigDefaults.apply(file, loaded, Config, "Ranks.List", "Ranks.Players", "Ranks.Worlds");
	}
}
