package fr.dianox.hawn.command.commands.tab;

import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HawnTabCompletion implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

		if (args.length == 1) {
			List<String> tab = new ArrayList<>();
			tab.add("parse");
			tab.add("nightvision");
			tab.add("noclip");
			tab.add("spawnmanager");
			tab.add("urgent");
			tab.add("reload");
			tab.add("slotview");
			tab.add("editplayer");
			tab.add("hooks");
			tab.add("info");
			tab.add("version");
			tab.add("about");
			tab.add("donors");
			tab.add("tps");
			tab.add("build");
			tab.add("maintenance");
			tab.add("help");

			java.util.Collections.sort(tab);

			return tab;
		} else if (args.length == 2) {
			if (args[0].equalsIgnoreCase("pholders") || args[0].equalsIgnoreCase("editplayer") || args[0].equalsIgnoreCase("pholder") || args[0].equalsIgnoreCase("parse")) {
				List<String> tab = new ArrayList<>();
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					tab.add(p.getName());
				}

				java.util.Collections.sort(tab);

				return tab;
			} else if (args[0].equalsIgnoreCase("spawnmanager")) {
				List<String> tab = new ArrayList<>();
				tab.add("remove");
				tab.add("setspawn");

				java.util.Collections.sort(tab);

				return tab;
			} else if (args[0].equalsIgnoreCase("info")) {
				List<String> tab = new ArrayList<>();
				tab.add("all");
				tab.add("memory");
				tab.add("cpu");
				tab.add("disk");
				tab.add("tps");
				tab.add("server");
				tab.add("version");

				java.util.Collections.sort(tab);

				return tab;
			}
		} else if (args.length == 3) {
			if (args[0].equalsIgnoreCase("spawnmanager")) {
				if (args[1].equalsIgnoreCase("remove")) {
					List<String> tab = new ArrayList<>(ConfigSpawn.getConfig().getConfigurationSection("Coordinated").getKeys(false));
					return tab;
				}
			}
		}

		return new ArrayList<>();
	}
}
