package fr.dianox.hawn.utility.config.configs.commands;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * Commands/Server-Tools.yml (since 1.6.2): /lag, /maxplayers, /setmotd, /colors, /itemframe and /editsign.
 */
public class ServerToolsCommandConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public static final String[] SECTIONS = {"Lag", "MaxPlayers", "SetMotd", "Colors", "ItemFrame", "EditSign"};

	public ServerToolsCommandConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Commands/Server-Tools.yml");
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

		for (String section : SECTIONS) {
			Config.set(section + ".Enable", true);
			Config.set(section + ".Disable-Message", true);
			Config.set(section + ".DISABLE_THE_COMMAND_COMPLETELY", false);
		}

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}
