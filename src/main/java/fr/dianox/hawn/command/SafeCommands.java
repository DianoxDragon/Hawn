package fr.dianox.hawn.command;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * An error in a Hawn command no longer ends with "An internal error occurred while attempting to perform this
 * command": the sender gets Error.Command-Wrong-Arguments (with the usage) when the arguments caused it, else
 * Error.Command-Failed of Messages.yml; the console gets the whole error with the command typed.
 */
public final class SafeCommands {

	private SafeCommands() {}

	/**
	 * The command map of the server: every command registered through it is guarded, with the prefix hawn: (/hawn:spawn
	 * always reaches the command of Hawn, since 1.6.1), and gives way to the same command of another plugin
	 * ({@link CommandConflicts}).
	 */
	public static CommandMap guard(CommandMap map) {
		return (CommandMap) Proxy.newProxyInstance(CommandMap.class.getClassLoader(), new Class<?>[] {CommandMap.class}, (proxy, method, args) -> {
			Command guarded = null;
			if (method.getName().equals("register") && args != null && args.length == 2 && args[0] instanceof String && args[1] instanceof Command) {
				guarded = new Guarded((Command) args[1]);
				args[0] = "hawn";
				args[1] = guarded;
			}

			try {
				Object result = method.invoke(map, args);
				if (guarded != null) {
					CommandConflicts.registered(map, guarded);
				}
				return result;
			} catch (InvocationTargetException e) {
				throw e.getCause();
			}
		});
	}

	/**
	 * The same for a command of plugin.yml (/hawn).
	 */
	public static CommandExecutor guard(CommandExecutor executor) {
		return (sender, command, label, args) -> {
			try {
				return executor.onCommand(sender, command, label, args);
			} catch (Throwable t) {
				failed(sender, label, args, "/hawn help", t);
				return true;
			}
		};
	}

	private static void failed(CommandSender sender, String label, String[] args, String usage, Throwable error) {
		// A missing argument or a word where a number is expected: the player typed the command wrongly, the usage helps
		// more than "this command failed". Since 1.6.2, not written in the console as a bug: /hawn debug counts it
		// (and Plugin.Debug: true shows it)
		boolean arguments = wrongArguments(error);
		String typed = "/" + label + (args.length == 0 ? "" : " " + String.join(" ", args));
		if (arguments) {
			// One line per command in /hawn debug, whatever was typed
			fr.dianox.hawn.utility.Debug.hidden("Command /" + label.toLowerCase(java.util.Locale.ROOT) + " typed wrongly", error);
		} else {
			Main.getInstance().getLogger().log(Level.SEVERE, "The command " + typed + " of " + sender.getName()
					+ " failed. Please report it on GitHub with this error:", error);
		}
		tell(sender, arguments, usage == null || usage.isEmpty() ? "/" + label : usage.replace("<command>", label));
	}

	/**
	 * Since 1.6.2: Error.Command-Wrong-Arguments with the usage, for a command that checks its arguments itself.
	 */
	public static void usage(CommandSender sender, String usage) {
		tell(sender, true, usage);
	}

	private static void tell(CommandSender sender, boolean arguments, String shown) {
		String path = arguments ? "Error.Command-Wrong-Arguments" : "Error.Command-Failed";
		String byDefault = arguments ? "%prefix% &cWrong arguments. Usage: &e%usage%"
				: "%prefix% &cThis command failed: the error is written in the console, tell an admin";

		if (!ConfigMMsg.getConfig().getBoolean(path + ".Enable", true)) {
			return;
		}

		List<String> messages = ConfigMMsg.getConfig().isSet(path + ".Messages")
				? ConfigMMsg.getConfig(sender).getStringList(path + ".Messages")
				: java.util.Collections.singletonList(byDefault);

		for (String msg : messages) {
			msg = msg.replace("%usage%", ConfigEventUtils.noAction(shown));
			if (sender instanceof Player) {
				ConfigEventUtils.ExecuteEvent((Player) sender, msg, path, "SafeCommands", false);
			} else {
				MessageUtils.ConsoleMessages(msg);
			}
		}
	}

	// An error thrown while reading the arguments
	private static boolean wrongArguments(Throwable error) {
		for (Throwable t = error; t != null; t = t.getCause()) {
			if (t instanceof IndexOutOfBoundsException || t instanceof NumberFormatException) {
				return true;
			}
		}
		return false;
	}

	// A command of Hawn, with its name, aliases, description, usage and permission (hidden from the players without it)
	private static final class Guarded extends Command {

		private final Command command;

		Guarded(Command command) {
			super(command.getName(), command.getDescription(), command.getUsage(), command.getAliases());
			this.command = command;
			setPermission(command.getPermission());
		}

		@Override
		public boolean execute(CommandSender sender, String label, String[] args) {
			try {
				return command.execute(sender, label, args);
			} catch (Throwable t) {
				failed(sender, label, args, command.getUsage(), t);
				return true;
			}
		}

		@Override
		public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
			try {
				return command.tabComplete(sender, alias, args);
			} catch (Throwable t) {
				Main.getInstance().getLogger().log(Level.WARNING, "The completion of /" + alias + " failed", t);
				return new ArrayList<>();
			}
		}
	}
}
