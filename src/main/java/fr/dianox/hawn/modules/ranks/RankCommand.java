package fr.dianox.hawn.modules.ranks;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * /hawn rank (since 1.4.8): set, remove, info, list. The ranks given by hand are written in Ranks.Players and shown at once.
 */
public final class RankCommand {

	private RankCommand() {}

	public static void run(CommandSender sender, String[] args) {
		String sub = args.length > 1 ? args[1].toLowerCase(java.util.Locale.ROOT) : "";

		if (!Ranks.enabled()) {
			say(sender, "Ranks.Off");
			return;
		}

		switch (sub) {
			case "set":
				if (args.length < 4) {
					say(sender, "Ranks.Usage");
					return;
				}
				// The ranks come from LuckPerms: nothing written, the group changes in LuckPerms
				if (Ranks.luckPermsSource()) {
					say(sender, "Ranks.LuckPerms-Source", args[2]);
					return;
				}
				Ranks.Rank rank = Ranks.byName(args[3]);
				if (rank == null) {
					say(sender, "Ranks.Unknown-Rank", args[3], String.join(", ", Ranks.all().keySet()));
					return;
				}
				Ranks.setByHand(args[2], rank.name);
				say(sender, "Ranks.Set", args[2], rank.name);
				return;
			case "remove":
				if (args.length < 3) {
					say(sender, "Ranks.Usage");
					return;
				}
				if (Ranks.byHandOf(args[2]) == null) {
					say(sender, "Ranks.No-Rank-By-Hand", args[2]);
					return;
				}
				Ranks.setByHand(args[2], null);
				say(sender, "Ranks.Removed", args[2]);
				return;
			case "info":
				if (args.length < 3) {
					say(sender, "Ranks.Usage");
					return;
				}
				Player target = Bukkit.getPlayerExact(args[2]);
				if (target == null) {
					String hand = Ranks.byHandOf(args[2]);
					say(sender, "Ranks.Info-Offline", args[2], hand == null ? "-" : hand);
					return;
				}
				Ranks.Rank r = Ranks.rankOf(target);
				say(sender, "Ranks.Info", target.getName(), r == null ? "-" : r.name, source(Ranks.sourceOf(target)),
						r == null ? "" : r.prefix + target.getName() + r.suffix);
				return;
			case "list":
				say(sender, "Ranks.List-Header", String.valueOf(Ranks.all().size()));
				for (Ranks.Rank each : Ranks.all().values()) {
					List<String> names = new ArrayList<>();
					for (Player p : Bukkit.getOnlinePlayers()) {
						Ranks.Rank of = Ranks.rankOf(p);
						if (of != null && of.name.equalsIgnoreCase(each.name)) names.add(p.getName());
					}
					say(sender, "Ranks.List-Line", each.name, String.valueOf(each.priority), each.prefix + "Steve" + each.suffix,
							names.isEmpty() ? "-" : String.join(", ", names));
				}
				return;
			default:
				say(sender, "Ranks.Usage");
		}
	}

	private static String source(Ranks.Source s) {
		return ConfigMAdmin.getConfig().getString("Ranks.Source." + s.name(), s.name());
	}

	public static List<String> tab(String[] args) {
		List<String> out = new ArrayList<>();
		if (args.length == 2) {
			for (String s : new String[] {"set", "remove", "info", "list"}) {
				if (s.startsWith(args[1].toLowerCase(java.util.Locale.ROOT))) out.add(s);
			}
		} else if (args.length == 3 && !args[1].equalsIgnoreCase("list")) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				if (p.getName().toLowerCase(java.util.Locale.ROOT).startsWith(args[2].toLowerCase(java.util.Locale.ROOT))) out.add(p.getName());
			}
		} else if (args.length == 4 && args[1].equalsIgnoreCase("set")) {
			for (String r : Ranks.all().keySet()) {
				if (r.startsWith(args[3].toLowerCase(java.util.Locale.ROOT))) out.add(r);
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
