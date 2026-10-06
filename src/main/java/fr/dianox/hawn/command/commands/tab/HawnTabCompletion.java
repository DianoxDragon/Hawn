package fr.dianox.hawn.command.commands.tab;

import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class HawnTabCompletion implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

		// Same permission as the command: the players who can't use /hawn don't see its sub commands
		if (commandSender instanceof Player && !commandSender.hasPermission("hawn.admin") && !commandSender.hasPermission("hawn.admin.*")) {
			return Tab.none();
		}

		if (args.length == 1) {
			return Tab.of(args, "parse", "nightvision", "noclip", "spawnmanager", "urgent", "reload", "slotview", "editplayer",
				"hooks", "setup", "info", "version", "about", "donors", "tps", "build", "maintenance", "help");
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("editplayer")) {
				return Tab.players(commandSender, args);
			} else if (args[0].equalsIgnoreCase("pholders") || args[0].equalsIgnoreCase("pholder") || args[0].equalsIgnoreCase("parse")) {
				List<String> tab = Tab.players(commandSender, args);
				tab.addAll(Tab.of(args, "me"));
				return tab;
			} else if (args[0].equalsIgnoreCase("spawnmanager")) {
				return Tab.of(args, "remove", "setspawn");
			} else if (args[0].equalsIgnoreCase("info")) {
				return Tab.of(args, "all", "memory", "cpu", "disk", "tps", "server", "version");
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("spawnmanager") && args[1].equalsIgnoreCase("remove")) {
				return Tab.keys(ConfigSpawn.getConfig(), "Coordinated", args);
			}
		}

		return Tab.none();
	}
}
