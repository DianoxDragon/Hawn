package fr.dianox.hawn.utility;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.XSound;
import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Since 1.4.8. The errors Hawn hides to keep running (89): counted, written in the console with Plugin.Debug: true,
 * and listed by /hawn debug with the mistakes of the configuration files (sounds, materials, effects, actions).
 */
public final class Debug {

	private Debug() {}

	// Where -> how many times; the last error of each place
	private static final Map<String, Integer> hidden = new LinkedHashMap<>();
	private static final Map<String, String> lastHidden = new LinkedHashMap<>();
	// The values that were not recognized while Hawn used them (XParse)
	private static final Set<String> seen = new LinkedHashSet<>();

	// The errors written in the console for each place with Plugin.Debug: true, then only counted
	private static final int SHOWN = 3;

	// An action at the start of a message line: [word]: ... or [word[... ("[VIP] text" is a text)
	private static final Pattern ACTION = Pattern.compile("^\\[([A-Za-z-]+)(\\]: |\\[)");

	private static boolean debug() {
		return ConfigGeneral.getConfig() != null && ConfigGeneral.getConfig().getBoolean("Plugin.Debug");
	}

	/**
	 * An error Hawn goes on without (the feature of this place is skipped).
	 */
	public static synchronized void hidden(String where, Throwable t) {
		int n = hidden.merge(where, 1, Integer::sum);
		lastHidden.put(where, String.valueOf(t));
		if (debug() && n <= SHOWN) {
			Main.getInstance().getLogger().warning("[Debug] " + where + ": " + t + (n == SHOWN ? " (the next ones of this place are only counted)" : ""));
			if (n == 1) {
				t.printStackTrace();
			}
		}
	}

	/**
	 * @return false when this value was already reported (XParse warns once per value, not at each use)
	 */
	public static synchronized boolean firstTime(String what, String name) {
		return seen.add(what + ":" + name);
	}

	/**
	 * /hawn reload: a value fixed then broken again is warned again (the hidden errors stay counted since the start).
	 */
	public static synchronized void forgetWarnings() {
		seen.clear();
	}

	public static synchronized Map<String, Integer> hiddenErrors() {
		return new LinkedHashMap<>(hidden);
	}

	public static synchronized String lastHidden(String where) {
		return lastHidden.get(where);
	}

	/* ------------------------------------------------------------------ the configuration files */

	/**
	 * The mistakes of the configuration files: "file: path = value (what is wrong)".
	 */
	public static List<String> scan() {
		List<String> errors = new ArrayList<>();
		File folder = Main.getInstance().getDataFolder();
		List<File> files = new ArrayList<>();
		collect(folder, files);

		for (File f : files) {
			String name = folder.toPath().relativize(f.toPath()).toString().replace(File.separatorChar, '/');
			// The data of the players and the lists written by Hawn
			if (name.startsWith("StockageInfo/") || name.startsWith("Logs/")) continue;

			YamlConfiguration c = new YamlConfiguration();
			try {
				c.load(f);
			} catch (Exception e) {
				errors.add(name + ": YAML error, the file is not read (" + firstLine(e) + ")");
				continue;
			}

			// The menus: slots, conditions (since 1.5)
			if (name.startsWith("Menus/")) {
				errors.addAll(fr.dianox.hawn.modules.menus.Menus.check(name, c));
			}

			for (String key : c.getKeys(true)) {
				if (c.isConfigurationSection(key)) continue;
				String last = key.substring(key.lastIndexOf('.') + 1);

				if (c.isString(key)) {
					String value = c.getString(key);
					if (value == null || value.trim().isEmpty()) continue;
					if (last.equals("Material") && fr.dianox.hawn.hook.CustomItems.isCustom(value)) {
						String problem = fr.dianox.hawn.hook.CustomItems.problem(value);
						if (problem != null) errors.add(name + ": " + key + " = " + value + " (" + problem + ")");
					} else if ((last.equals("Material") || last.equals("Fill")) && !value.toLowerCase(java.util.Locale.ROOT).startsWith("head:")
							&& !value.contains("%") && !value.contains("{anim_") && !material(value)) {
						errors.add(name + ": " + key + " = " + value + " (unknown material)");
					} else if (last.equals("Sound") && !sound(value)) {
						errors.add(name + ": " + key + " = " + value + " (unknown sound)");
					}
				} else if (c.isList(key) && last.equals("Materials")) {
					for (String value : c.getStringList(key)) {
						if (!material(value)) errors.add(name + ": " + key + " = " + value + " (unknown material)");
					}
				} else if (c.isList(key) && last.equals("Damage-Type-List")) {
					for (String value : c.getStringList(key)) {
						if (!value.equals("Entity") && !damageCause(value)) errors.add(name + ": " + key + " = " + value + " (unknown damage type)");
					}
				} else if (c.isList(key)) {
					for (String line : c.getStringList(key)) {
						String problem = action(line);
						if (problem != null) {
							errors.add(name + ": " + key + " = " + line + " (" + problem + ")");
						}
					}
				}
			}
		}
		return errors;
	}

	private static void collect(File dir, List<File> out) {
		File[] list = dir.listFiles();
		if (list == null) return;
		for (File f : list) {
			if (f.isDirectory()) {
				collect(f, out);
			} else if (f.getName().endsWith(".yml")) {
				out.add(f);
			}
		}
	}

	private static String firstLine(Exception e) {
		String m = String.valueOf(e.getMessage());
		int i = m.indexOf('\n');
		return i < 0 ? m : m.substring(0, i);
	}

	private static boolean material(String name) {
		return XMaterial.matchXMaterial(name).map(XMaterial::parseMaterial).orElse(null) != null;
	}

	private static boolean sound(String name) {
		return XSound.matchXSound(name.trim().split("\\s+")[0]).map(XSound::parseSound).orElse(null) != null;
	}

	private static boolean damageCause(String name) {
		try {
			org.bukkit.event.entity.EntityDamageEvent.DamageCause.valueOf(name);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	private static boolean potion(String name) {
		return XPotion.matchXPotion(name.trim()).map(XPotion::get).orElse(null) != null;
	}

	/**
	 * What is wrong with a message line, null when nothing is (or when it is a text, not an action).
	 */
	static String action(String line) {
		if (line == null) return null;
		String l = line;

		// <world>...</world> and <perm>...</perm> in front of the action
		while (l.startsWith("<world>") || l.startsWith("<perm>")) {
			String end = l.startsWith("<world>") ? "</world>" : "</perm>";
			int i = l.indexOf(end);
			if (i < 0) return null;
			l = l.substring(i + end.length()).trim();
		}

		// [delay[..]]: and [chance[..]]: in front of the action
		while (l.startsWith("[delay[") || l.startsWith("[chance[")) {
			int i = l.indexOf("]]: ");
			if (i < 0) return "\"]]: \" missing after " + l.substring(0, l.indexOf('[', 1) + 1);
			l = l.substring(i + 4);
		}

		Matcher m = ACTION.matcher(l);
		if (!m.find()) return null;

		boolean known = false;
		for (String action : ConfigEventUtils.ACTIONS) {
			if (l.startsWith(action) || (action.endsWith("]") && l.equals(action))) {
				known = true;
				break;
			}
		}
		if (!known) {
			// Written like an action, but not one of Hawn: the line is shown as a text
			return "unknown action [" + m.group(1) + "], shown as a text";
		}

		if (l.startsWith("[sound]: ") || l.startsWith("[sounds]: ")) {
			String sound = l.substring(l.indexOf("]: ") + 3).trim();
			return sound(sound) ? null : "unknown sound " + sound;
		}
		if (l.startsWith("[menu]: ")) {
			String menu = l.substring("[menu]: ".length()).trim();
			return fr.dianox.hawn.modules.menus.Menus.exists(menu) ? null : "no menu " + menu + " in Menus/";
		}
		if (l.startsWith("[effectclear]: ")) {
			String effect = l.substring("[effectclear]: ".length()).trim();
			return potion(effect) ? null : "unknown effect " + effect;
		}
		if (l.startsWith("[effect[")) {
			int i = l.indexOf("]]: ");
			if (i < 0) return "\"]]: \" missing after [effect[";
			String effect = l.substring(i + 4).trim();
			return potion(effect) ? null : "unknown effect " + effect;
		}
		return null;
	}

	/* ------------------------------------------------------------------ /hawn debug */

	// The lines shown for each list, then "... and N more"
	private static final int LINES = 25;

	/**
	 * /hawn debug: the mistakes of the files (read outside the main thread) and the hidden errors.
	 */
	public static void show(org.bukkit.command.CommandSender sender) {
		org.bukkit.Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			List<String> errors = scan();
			org.bukkit.Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
				say(sender, "Debug.Header", "");
				if (errors.isEmpty()) {
					say(sender, "Debug.No-Config-Error", "");
				} else {
					say(sender, "Debug.Config-Errors", String.valueOf(errors.size()));
					list(sender, errors);
				}

				// Enable: AUTO, left to another plugin
				List<String> auto = new ArrayList<>();
				if (fr.dianox.hawn.modules.ranks.Ranks.enabled() && !fr.dianox.hawn.modules.ranks.Ranks.tablist() && fr.dianox.hawn.modules.ranks.Ranks.tabPlugin() != null) {
					auto.add("Ranks (Ranks.yml, Enable: AUTO): the tab list and the heads are left to " + withVersion(fr.dianox.hawn.modules.ranks.Ranks.tabPlugin()));
				}
				if (fr.dianox.hawn.modules.chat.ChatFormat.mode() == fr.dianox.hawn.modules.chat.ChatFormat.Mode.AUTO && fr.dianox.hawn.modules.chat.ChatFormat.chatPlugin() != null) {
					auto.add("Chat-Format (Events/Chat.yml, AUTO): the format of the chat is left to " + withVersion(fr.dianox.hawn.modules.chat.ChatFormat.chatPlugin()));
				}
				if (!auto.isEmpty()) {
					say(sender, "Debug.Auto", String.valueOf(auto.size()));
					list(sender, auto);
				}

				Map<String, Integer> h = hiddenErrors();
				if (h.isEmpty()) {
					say(sender, "Debug.No-Hidden-Error", "");
				} else {
					int total = 0;
					List<String> lines = new ArrayList<>();
					for (Map.Entry<String, Integer> e : h.entrySet()) {
						total += e.getValue();
						lines.add(e.getKey() + " (" + e.getValue() + "): " + lastHidden(e.getKey()));
					}
					say(sender, "Debug.Hidden-Errors", String.valueOf(total));
					list(sender, lines);
					if (!debug()) say(sender, "Debug.Tip", "");
				}
			});
		});
	}

	private static String withVersion(String plugin) {
		org.bukkit.plugin.Plugin p = org.bukkit.Bukkit.getPluginManager().getPlugin(plugin);
		return p == null ? plugin : plugin + " " + p.getDescription().getVersion();
	}

	private static void list(org.bukkit.command.CommandSender sender, List<String> lines) {
		for (int i = 0; i < Math.min(LINES, lines.size()); i++) {
			sender.sendMessage("§8- §7" + lines.get(i));
		}
		if (lines.size() > LINES) {
			sender.sendMessage("§8... §7+" + (lines.size() - LINES));
		}
	}

	private static void say(org.bukkit.command.CommandSender sender, String key, String arg) {
		for (String msg : fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin.getConfig().getStringList(key)) {
			msg = msg.replace("%arg 1%", arg);
			if (sender instanceof org.bukkit.entity.Player) {
				ConfigEventUtils.ExecuteEvent((org.bukkit.entity.Player) sender, msg, "", "", false);
			} else {
				MessageUtils.ConsoleMessages(msg);
			}
		}
	}
}
