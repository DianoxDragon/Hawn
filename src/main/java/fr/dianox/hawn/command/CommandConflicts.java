package fr.dianox.hawn.command;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Since 1.6.1: a command of Hawn that another plugin also declares (/spawn of EssentialsSpawn, /fly of EssentialsX...)
 * is left to that plugin, whatever the order in which the plugins start. The command of Hawn stays at /hawn:&lt;command&gt;.
 * The commands of Plugin.Commands.Conflicts.Keep-Hawn-Commands (general.yml) stay to Hawn.
 *
 * The other commands are read in the plugin.yml of every plugin, so a plugin enabled after Hawn is seen too.
 */
public final class CommandConflicts {

	// The commands and aliases of the other plugins -> the plugin
	private static Map<String, String> declared;
	// The commands of Hawn left to another plugin, to take them back when that plugin did not start
	private static final Map<String, Command> ours = new HashMap<>();
	private static CommandMap commandMap;
	// The commands left to another plugin -> that plugin, and the ones kept by Hawn
	private static final Map<String, String> yielded = new TreeMap<>();
	private static final Map<String, String> kept = new TreeMap<>();
	// Since 1.6.2: the commands that a command of the server (or one registered in the code of a plugin) had first
	private static final Map<String, String> taken = new TreeMap<>();

	private CommandConflicts() {}

	/**
	 * After a command of Hawn was registered: left to the other plugin that declares it, or taken back when it is kept.
	 */
	static synchronized void registered(CommandMap map, Command command) {
		String label = command.getName().toLowerCase(Locale.ROOT);
		String other = owner(label);
		Map<String, Command> known = known(map);
		if (other == null) {
			// Since 1.6.2: a command of the server (/tps of Spigot and Paper) or of a plugin that registers its commands
			// in its code had it first: the one of Hawn is only at /hawn:<command>
			Command holder = known == null ? null : known.get(label);
			if (holder != null && holder != command) {
				taken.put(label, holder instanceof org.bukkit.command.PluginIdentifiableCommand
						? ((org.bukkit.command.PluginIdentifiableCommand) holder).getPlugin().getName() : "the server");
			}
			return;
		}

		if (keep().contains(label)) {
			kept.put(label, other);
			// The plugin enabled before Hawn had it: Hawn takes it back. One enabled after keeps its own prefix
			if (known != null) {
				known.put(label, command);
			}
		} else if (giveWay()) {
			yielded.put(label, other);
			ours.put(label, command);
			commandMap = map;
			if (known != null && known.get(label) == command) {
				known.remove(label);
			}
		}
	}

	/**
	 * Once every plugin has started: a command left to a plugin that failed to start (or was disabled) goes back to Hawn,
	 * instead of answering "plugin is disabled".
	 */
	public static synchronized void afterStartup() {
		if (yielded.isEmpty() || commandMap == null) {
			return;
		}

		Map<String, Command> known = known(commandMap);
		if (known == null) {
			return;
		}

		Map<String, String> back = new TreeMap<>();
		for (Map.Entry<String, String> e : new TreeMap<>(yielded).entrySet()) {
			if (!Bukkit.getPluginManager().isPluginEnabled(e.getValue())) {
				known.put(e.getKey(), ours.get(e.getKey()));
				yielded.remove(e.getKey());
				back.put(e.getKey(), e.getValue());
			}
		}

		if (!back.isEmpty()) {
			java.util.Set<String> plugins = new java.util.TreeSet<>(back.values());
			Main.getInstance().getLogger().info(back.size() + " command(s) given back to Hawn, because " + String.join(", ", plugins)
					+ " did not start: /" + String.join(", /", back.keySet()));
		}
	}

	/**
	 * The plugin (other than Hawn) whose plugin.yml declares this command or alias, null when there is none.
	 */
	static synchronized String owner(String label) {
		if (declared == null) {
			declared = new HashMap<>();
			for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
				if (plugin == Main.getInstance()) continue;
				Map<String, Map<String, Object>> commands;
				try {
					commands = plugin.getDescription().getCommands();
				} catch (RuntimeException e) {
					continue;
				}
				if (commands == null) continue;

				for (Map.Entry<String, Map<String, Object>> c : commands.entrySet()) {
					add(c.getKey(), plugin);
					Object aliases = c.getValue() == null ? null : c.getValue().get("aliases");
					if (aliases instanceof Collection) {
						for (Object alias : (Collection<?>) aliases) add(String.valueOf(alias), plugin);
					} else if (aliases != null) {
						add(String.valueOf(aliases), plugin);
					}
				}
			}
		}
		return declared.get(label.toLowerCase(Locale.ROOT));
	}

	private static void add(String label, Plugin plugin) {
		declared.putIfAbsent(label.toLowerCase(Locale.ROOT), plugin.getName());
	}

	private static boolean giveWay() {
		return ConfigGeneral.getConfig().getBoolean("Plugin.Commands.Conflicts.Give-Way-To-Other-Plugins", true);
	}

	private static Set<String> keep() {
		Set<String> labels = new HashSet<>();
		for (String label : ConfigGeneral.getConfig().getStringList("Plugin.Commands.Conflicts.Keep-Hawn-Commands")) {
			labels.add(label.replace("/", "").trim().toLowerCase(Locale.ROOT));
		}
		return labels;
	}

	// The commands of the server by label: public on Paper, a protected field of SimpleCommandMap on Spigot
	@SuppressWarnings("unchecked")
	private static Map<String, Command> known(CommandMap map) {
		try {
			Method method = map.getClass().getMethod("getKnownCommands");
			return (Map<String, Command>) method.invoke(map);
		} catch (ReflectiveOperationException | RuntimeException ignored) {
			// Spigot: the field
		}
		for (Class<?> c = map.getClass(); c != null; c = c.getSuperclass()) {
			try {
				Field field = c.getDeclaredField("knownCommands");
				field.setAccessible(true);
				return (Map<String, Command>) field.get(map);
			} catch (NoSuchFieldException e) {
				// the class above
			} catch (ReflectiveOperationException | RuntimeException e) {
				break;
			}
		}
		Main.getInstance().getLogger().warning("The commands of the server could not be read: the commands of Hawn that another plugin has too are not left to it");
		return null;
	}

	/**
	 * The label that reaches the command of Hawn: "setspawn", or "hawn:setspawn" when it is left to another plugin.
	 */
	public static synchronized String label(String command) {
		String lower = command.toLowerCase(Locale.ROOT);
		return yielded.containsKey(lower) || taken.containsKey(lower) ? "hawn:" + command : command;
	}

	/**
	 * The commands left to another plugin or to the server: "spawn" -> "Essentials", "tps" -> "the server".
	 */
	public static synchronized Map<String, String> yielded() {
		Map<String, String> all = new TreeMap<>(yielded);
		all.putAll(taken);
		return Collections.unmodifiableMap(all);
	}

	/**
	 * The lines of the startup report and of /hawn debug.
	 */
	public static synchronized List<String> lines() {
		List<String> lines = new java.util.ArrayList<>();
		for (Map.Entry<String, String> e : yielded.entrySet()) {
			lines.add("/" + e.getKey() + " is left to " + e.getValue() + ": the command of Hawn is /hawn:" + e.getKey()
					+ " (general.yml, Plugin.Commands.Conflicts.Keep-Hawn-Commands to keep it)");
		}
		for (Map.Entry<String, String> e : taken.entrySet()) {
			lines.add("/" + e.getKey() + " was taken first by " + e.getValue() + ": the command of Hawn is /hawn:" + e.getKey()
					+ " (or remove it with DISABLE_THE_COMMAND_COMPLETELY)");
		}
		for (Map.Entry<String, String> e : kept.entrySet()) {
			lines.add("/" + e.getKey() + " is kept by Hawn (Keep-Hawn-Commands): the command of " + e.getValue() + " is /"
					+ e.getValue().toLowerCase(Locale.ROOT) + ":" + e.getKey());
		}
		return lines;
	}
}
