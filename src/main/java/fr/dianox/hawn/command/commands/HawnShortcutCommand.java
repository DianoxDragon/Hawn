package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.command.commands.tab.HawnTabCompletion;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * A short command for a sub-command of /hawn (since 1.5.3): /editplayer Steve is /hawn editplayer Steve. The permissions
 * and the messages are the ones of /hawn, the completion too.
 */
public class HawnShortcutCommand extends BukkitCommand {

	private final String sub;

	public HawnShortcutCommand(String name, String sub) {
		super(name);
		this.sub = sub;
		this.description = "/hawn " + sub;
		this.usageMessage = "/" + name + " ...";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		String command = "hawn " + sub + (args.length == 0 ? "" : " " + String.join(" ", args));
		if (sender instanceof Player) {
			((Player) sender).performCommand(command);
		} else {
			Bukkit.dispatchCommand(sender, command);
		}
		return true;
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		String[] all = new String[args.length + 1];
		all[0] = sub;
		System.arraycopy(args, 0, all, 1, args.length);
		List<String> out = new HawnTabCompletion().onTabComplete(sender, this, "hawn", all);
		return out == null ? new java.util.ArrayList<>() : out;
	}
}
