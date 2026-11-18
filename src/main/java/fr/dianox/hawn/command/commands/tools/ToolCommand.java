package fr.dianox.hawn.command.commands.tools;

import fr.dianox.hawn.command.commands.tab.Tab;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.ServerToolsCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

/**
 * The commands of Commands/Server-Tools.yml (since 1.6.2): Enable, Disable-Message and the permission are checked here,
 * the messages come from Messages.yml in the language of the player.
 */
abstract class ToolCommand extends BukkitCommand {

	private final String section;
	private final String permission;
	private final boolean playerOnly;

	ToolCommand(String name, String section, String permission, String usage, String description, boolean playerOnly) {
		super(name);
		this.section = section;
		this.permission = permission;
		this.playerOnly = playerOnly;
		this.usageMessage = usage;
		this.description = description;
		Tab.hideWithoutPermission(this, permission);
	}

	@Override
	public final boolean execute(CommandSender sender, String label, String[] args) {
		if (playerOnly && !(sender instanceof Player)) {
			say(sender, "Error.Not-A-Player");
			return true;
		}

		if (!ServerToolsCommandConfig.getConfig().getBoolean(section + ".Enable", true)) {
			if (ServerToolsCommandConfig.getConfig().getBoolean(section + ".Disable-Message", true)) {
				say(sender, "Error.Command-Disable");
			}
			return true;
		}

		if (sender instanceof Player && !sender.hasPermission(permission)) {
			MessageUtils.MessageNoPermission((Player) sender, permission);
			return true;
		}

		run(sender, args);
		return true;
	}

	abstract void run(CommandSender sender, String[] args);

	/**
	 * A command typed wrongly (no argument, a word in place of a number...): its usage, nothing in the console.
	 */
	final void usage(CommandSender sender) {
		say(sender, "Error.Command-Wrong-Arguments", "%usage%", getUsage());
	}

	/**
	 * The whole number typed, or null when it is not one.
	 */
	static Integer number(String text) {
		try {
			return Integer.valueOf(text.trim());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * A message of Messages.yml (its Enable and its Messages), with "%name%", "value" pairs. The values typed by a player
	 * can't run actions.
	 */
	static void say(CommandSender to, String key, String... replacements) {
		if (!ConfigMMsg.getConfig().getBoolean(key + ".Enable", true)) {
			return;
		}

		java.util.List<String> lines = to instanceof Player ? ConfigMMsg.getConfig((Player) to).getStringList(key + ".Messages")
				: ConfigMMsg.getConfig().getStringList(key + ".Messages");
		for (String line : lines) {
			for (int i = 0; i + 1 < replacements.length; i += 2) {
				line = line.replace(replacements[i], ConfigEventUtils.noAction(replacements[i + 1]));
			}
			if (to instanceof Player) {
				ConfigEventUtils.ExecuteEvent((Player) to, line, "", "", false);
			} else {
				MessageUtils.ConsoleMessages(line);
			}
		}
	}
}
