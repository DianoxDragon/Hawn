package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The texts of the admin in several languages (since 1.6): {lang_&lt;key&gt;} in a menu, a scoreboard, the tab list, an
 * announcement... is replaced by the text of the key in Translations/&lt;language&gt;.yml, in the language of each
 * player, else in the language of the server, else in English, else by the key itself. A text without key stays as
 * it is written.
 */
public final class Translations {

	private Translations() {}

	private static final Pattern KEY = Pattern.compile("\\{lang_([A-Za-z0-9_.-]+)}");

	private static final Map<String, YamlConfiguration> files = new HashMap<>();

	/**
	 * At the start and at each reload: the files of Translations/, with the keys of the default files of Hawn added
	 * where they are missing (the texts changed by the admin are kept).
	 */
	public static synchronized void load() {
		files.clear();
		File folder = new File(Main.getInstance().getDataFolder(), "Translations");
		if (!folder.exists() && !folder.mkdirs()) {
			return;
		}

		for (String code : Lang.CODES) {
			File file = new File(folder, code + ".yml");
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			boolean changed = !file.exists();

			if (!file.exists()) {
				config.options().header("The texts of {lang_<key>} in " + code + " (see Plugin.Language-Per-Player in general.yml).\n"
						+ "Write {lang_<key>} in a menu, a scoreboard, the tab list or a message: each player sees the text of the key\n"
						+ "in their language. Add your own keys in every file of this folder.\n"
						+ "Documentation: https://hawn.gitbook.io/hawn/help/translating");
			}

			for (Map.Entry<String, String> e : DefaultTranslations.of(code).entrySet()) {
				if (!config.isSet(e.getKey())) {
					config.set(e.getKey(), e.getValue());
					changed = true;
				}
			}

			if (changed) {
				try {
					config.save(file);
				} catch (IOException e) {
					Main.getInstance().getLogger().warning("Translations/" + file.getName() + " could not be written: " + e);
				}
			}
			files.put(code, config);
		}

		// The files of other languages the admin added (xx_XX.yml)
		File[] others = folder.listFiles((dir, name) -> name.endsWith(".yml"));
		if (others != null) {
			for (File f : others) {
				String code = f.getName().substring(0, f.getName().length() - 4);
				files.putIfAbsent(code, YamlConfiguration.loadConfiguration(f));
			}
		}
	}

	/**
	 * The text of a key in a language, else in the language of the server, else in English, else null.
	 */
	public static String get(String key, String code) {
		for (String c : new String[] {code, Lang.server(), "en_US"}) {
			YamlConfiguration config = c == null ? null : files.get(c);
			if (config != null && config.isString(key)) {
				return config.getString(key);
			}
		}
		return null;
	}

	/**
	 * The {lang_&lt;key&gt;} of a text, in the language of who receives it (the server one for the console or null).
	 */
	public static String apply(String text, CommandSender to) {
		if (text == null || text.indexOf("{lang_") < 0) {
			return text;
		}

		String code = to == null ? Lang.server() : Lang.of(to);
		Matcher m = KEY.matcher(text);
		StringBuffer out = new StringBuffer();
		while (m.find()) {
			String value = get(m.group(1), code);
			// The colours of the text: some places (the tab list, the scoreboard) colour the line before its placeholders
			m.appendReplacement(out, Matcher.quoteReplacement(value == null ? m.group(1) : MessageUtils.colourTheStuff(value)));
		}
		m.appendTail(out);
		return out.toString();
	}
}
