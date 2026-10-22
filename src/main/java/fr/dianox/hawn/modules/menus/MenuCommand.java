package fr.dianox.hawn.modules.menus;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * /hawn menu (since 1.5): open &lt;menu&gt; [player], list.
 */
public final class MenuCommand {

	private MenuCommand() {}

	public static void run(CommandSender sender, String[] args) {
		String sub = args.length > 1 ? args[1].toLowerCase(Locale.ROOT) : "";

		if (sub.equals("list")) {
			if (Menus.all().isEmpty()) {
				say(sender, "Menus.None");
			} else {
				say(sender, "Menus.List", String.join(", ", Menus.all().keySet()));
			}
			return;
		}

		if (sub.equals("open") && args.length >= 3) {
			Player target;
			if (args.length >= 4) {
				target = Bukkit.getPlayerExact(args[3]);
				if (target == null) {
					if (sender instanceof Player) {
						MessageUtils.PlayerDoesntExist((Player) sender);
					} else {
						for (String msg : fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
							MessageUtils.ConsoleMessages(msg);
						}
					}
					return;
				}
			} else if (sender instanceof Player) {
				target = (Player) sender;
			} else {
				say(sender, "Menus.Usage");
				return;
			}

			boolean opened = Menus.open(target, args[2]);
			if (target != sender) {
				say(sender, opened ? "Menus.Opened" : "Menus.Not-Opened", args[2], target.getName());
			}
			return;
		}

		say(sender, "Menus.Usage");
	}

	public static List<String> tab(String[] args) {
		List<String> out = new ArrayList<>();
		if (args.length == 2) {
			for (String s : new String[] {"open", "list"}) {
				if (s.startsWith(args[1].toLowerCase(Locale.ROOT))) out.add(s);
			}
		} else if (args.length == 3 && args[1].equalsIgnoreCase("open")) {
			for (String menu : Menus.all().keySet()) {
				if (menu.startsWith(args[2].toLowerCase(Locale.ROOT))) out.add(menu);
			}
		} else if (args.length == 4 && args[1].equalsIgnoreCase("open")) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().toLowerCase(Locale.ROOT).startsWith(args[3].toLowerCase(Locale.ROOT))) out.add(p.getName());
			}
		}
		return out;
	}

	private static void say(CommandSender sender, String key, String... args) {
		for (String msg : ConfigMAdmin.getConfig().getStringList(key)) {
			for (int i = 0; i < args.length; i++) {
				msg = msg.replace("%arg " + (i + 1) + "%", ConfigEventUtils.noAction(args[i]));
			}
			if (sender instanceof Player) {
				ConfigEventUtils.ExecuteEvent((Player) sender, msg, "", "", false);
			} else {
				MessageUtils.ConsoleMessages(msg);
			}
		}
	}
}
