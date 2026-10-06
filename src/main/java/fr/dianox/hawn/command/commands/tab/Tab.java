package fr.dianox.hawn.command.commands.tab;

import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tab completion helpers: only the suggestions starting with what is typed are sent, sorted.
 */
public final class Tab {

	private Tab() {}

	/** The suggestions that start with the last argument */
	public static List<String> of(String[] args, Collection<String> options) {
		String typed = args.length == 0 ? "" : args[args.length - 1];
		List<String> result = StringUtil.copyPartialMatches(typed, options, new ArrayList<>());
		result.sort(String.CASE_INSENSITIVE_ORDER);
		return result;
	}

	public static List<String> of(String[] args, String... options) {
		return of(args, Arrays.asList(options));
	}

	/** The online players the sender can see (vanished players are not suggested) */
	public static List<String> players(CommandSender sender, String[] args) {
		List<String> names = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (!(sender instanceof Player) || ((Player) sender).canSee(p)) {
				names.add(p.getName());
			}
		}
		return of(args, names);
	}

	/** The keys of a section, nothing when it doesn't exist */
	public static List<String> keys(YamlConfiguration config, String path, String[] args) {
		return keys(config, path, args, key -> true);
	}

	public static List<String> keys(YamlConfiguration config, String path, String[] args, Predicate<String> filter) {
		ConfigurationSection section = config == null ? null : config.getConfigurationSection(path);
		if (section == null) {
			return none();
		}
		List<String> keys = new ArrayList<>();
		for (String key : section.getKeys(false)) {
			if (filter.test(key)) {
				keys.add(key);
			}
		}
		return of(args, keys);
	}

	/** Numbers from min to max */
	public static List<String> range(String[] args, int min, int max) {
		List<String> numbers = new ArrayList<>();
		for (int i = min; i <= max; i++) {
			numbers.add(String.valueOf(i));
		}
		List<String> result = StringUtil.copyPartialMatches(args.length == 0 ? "" : args[args.length - 1], numbers, new ArrayList<>());
		return result;
	}

	/**
	 * general.yml → Plugin.Commands.Hide-Without-Permission: the command is hidden from the players without its
	 * permission (list of the commands, tab completion). Several permissions are separated by ";".
	 * Only for the commands that always need this permission. Read when the commands are registered (restart).
	 */
	public static void hideWithoutPermission(Command command, String permission) {
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Commands.Hide-Without-Permission", true)) {
			command.setPermission(permission);
		}
	}

	/** No suggestion (an empty list: null would make the server suggest the player names) */
	public static List<String> none() {
		return new ArrayList<>();
	}
}
