package fr.dianox.hawn.utility.config;

import fr.dianox.hawn.utility.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The files of messages in every language at once (since 1.6), for the language of each player. A text missing from
 * a language is taken from the language of the server.
 */
public final class LangFiles {

	private LangFiles() {}

	/**
	 * Messages/&lt;code&gt;/name for every language of Lang.CODES that has the file; server for the language of the server.
	 */
	public static Map<String, YamlConfiguration> load(Plugin pl, String name, YamlConfiguration server) {
		Map<String, YamlConfiguration> byLanguage = new HashMap<>();
		String serverCode = Lang.server();

		for (String code : Lang.CODES) {
			if (code.equals(serverCode)) {
				byLanguage.put(code, server);
				continue;
			}

			File file = new File(pl.getDataFolder(), "Messages/" + code + "/" + name);
			if (!file.exists()) {
				continue;
			}

			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			config.setDefaults(server);
			byLanguage.put(code, config);
		}
		return Collections.unmodifiableMap(byLanguage);
	}

	/**
	 * The file in the language of the sender (the server one for the console, or when the language is missing).
	 */
	public static YamlConfiguration of(Map<String, YamlConfiguration> byLanguage, YamlConfiguration server, CommandSender to) {
		if (to == null || byLanguage == null) {
			return server;
		}
		YamlConfiguration config = byLanguage.get(Lang.of(to));
		return config == null ? server : config;
	}
}
