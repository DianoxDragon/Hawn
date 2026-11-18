package fr.dianox.hawn.command.commands.tools;

import fr.dianox.hawn.command.commands.tab.Tab;
import fr.dianox.hawn.modules.serverlist.ServerListManager;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * /maxplayers [number] (since 1.6.2): the slots of the server, changed without a restart and kept in ServerList.yml
 * (Slots.Max-Players).
 */
public class MaxPlayersCommand extends ToolCommand {

	public MaxPlayersCommand(String name) {
		super(name, "MaxPlayers", "hawn.command.maxplayers", "/maxplayers [number]", "Changes the slots of the server", false);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		return args.length == 1 ? Tab.of(args, String.valueOf(Bukkit.getMaxPlayers())) : Tab.none();
	}

	@Override
	void run(CommandSender sender, String[] args) {
		if (args.length == 0) {
			say(sender, "MaxPlayers.Show", "%number%", String.valueOf(Bukkit.getMaxPlayers()));
			return;
		}

		Integer number = number(args[0]);
		if (number == null || number < 1 || args.length > 1) {
			usage(sender);
			return;
		}

		if (!ServerListManager.setMaxPlayers(number)) {
			say(sender, "MaxPlayers.Not-Supported");
			return;
		}

		ServerListConfig.getConfig().set("Slots.Max-Players", number);
		ServerListConfig.saveConfigFile();
		say(sender, "MaxPlayers.Set", "%number%", String.valueOf(number));
	}
}
