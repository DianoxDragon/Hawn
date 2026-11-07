package fr.dianox.hawn.modules.chat.emojis;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import fr.dianox.hawn.utility.Debug;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.EmojisListCUtility;

public class ChatEmojisLoad {

	// Read by the chat outside the main thread: /hawn reload builds new maps and puts them in place at once (since 1.5.7),
	// never a map being emptied and filled again
	public static volatile Map<String, String> emojislist = Collections.emptyMap();
	public static volatile Map<String, String> emojislistperm = Collections.emptyMap();
	public static volatile Map<String, String> emojislistname = Collections.emptyMap();

	public static void onLoad() {
		Map<String, String> list = new LinkedHashMap<>();
		Map<String, String> perm = new LinkedHashMap<>();
		Map<String, String> name = new LinkedHashMap<>();

		ConfigurationSection section = EmojisListCUtility.getConfig().getConfigurationSection("Emojis-list");
		if (section != null) {
			for (String string : section.getKeys(false)) {
				String path = "Emojis-list." + string;
				if (!EmojisListCUtility.getConfig().getBoolean(path + ".Enable")) {
					continue;
				}

				// An emoji without Shape replaced the words by nothing (and failed in the chat)
				String shape = EmojisListCUtility.getConfig().getString(path + ".Emoji.Shape");
				if (shape == null || shape.isEmpty()) {
					warn(string, path + ".Emoji.Shape is missing");
					continue;
				}

				for (String replacewith : EmojisListCUtility.getConfig().getStringList(path + ".Emoji.Replace_With")) {
					// An empty word is found everywhere: the emoji between every letter
					if (replacewith == null || replacewith.trim().isEmpty()) {
						warn(string, path + ".Emoji.Replace_With has an empty word");
						continue;
					}

					list.put(replacewith, shape);

					if (EmojisListCUtility.getConfig().getBoolean(path + ".Use_Permission")) {
						perm.put(replacewith, "hawn.emoji." + string);
					}

					name.put(replacewith, string);
				}
			}
		}

		emojislistperm = Collections.unmodifiableMap(perm);
		emojislistname = Collections.unmodifiableMap(name);
		emojislist = Collections.unmodifiableMap(list);
	}

	private static void warn(String emoji, String why) {
		if (Debug.firstTime("emoji", emoji + " " + why)) {
			Bukkit.getLogger().log(Level.WARNING, "Hawn | (Configuration error): The emoji " + emoji + " is skipped: " + why + " (Cosmetics-Fun/Utility/Emojis-List.yml)");
		}
	}

}
