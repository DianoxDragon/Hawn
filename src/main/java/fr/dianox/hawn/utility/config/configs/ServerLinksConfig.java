package fr.dianox.hawn.utility.config.configs;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * ServerLinks.yml (since 1.6.2): the links of the pause menu of the clients 1.21+.
 */
public class ServerLinksConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public ServerLinksConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "ServerLinks.yml");
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

	private static void create() {
		YamlConfiguration loaded = Config;
		Config = new YamlConfiguration();

		Config.set("Server-Links.Enable", false);
		// The examples are only written in a new file: a link removed by the admin stays removed
		if (!file.exists()) {
			Config.set("Server-Links.Links.website.Type", "WEBSITE");
			Config.set("Server-Links.Links.website.Url", "https://yourserver.net");
			Config.set("Server-Links.Links.discord.Name", "&9Discord");
			Config.set("Server-Links.Links.discord.Url", "https://discord.gg/yourserver");
			Config.set("Server-Links.Links.rules.Type", "COMMUNITY_GUIDELINES");
			Config.set("Server-Links.Links.rules.Url", "https://yourserver.net/rules");
			Config.set("Server-Links.Links.store.Name", "&6Store");
			Config.set("Server-Links.Links.store.Url", "https://store.yourserver.net");
		}

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}
