package fr.dianox.hawn.command.commands.tools;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.tab.Tab;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

/**
 * /setmotd &lt;1|2&gt; [text] (since 1.6.2): a line of the MOTD of the server list (Motd.Classic.Main of ServerList.yml),
 * without a reload. The random MOTDs are turned off, so that this one is shown.
 */
public class SetMotdCommand extends ToolCommand {

	public SetMotdCommand(String name) {
		super(name, "SetMotd", "hawn.command.setmotd", "/setmotd <1|2> [text]", "Changes the MOTD of the server list", false);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		if (args.length == 1) return Tab.of(args, "1", "2");
		if (args.length == 2 && (args[0].equals("1") || args[0].equals("2"))) {
			String current = ServerListConfig.getConfig().getString("Motd.Classic.Main.Line-" + args[0], "");
			return Tab.of(args, current.split(" ")[0]);
		}
		return Tab.none();
	}

	@Override
	void run(CommandSender sender, String[] args) {
		Integer line = args.length == 0 ? null : number(args[0]);
		if (line == null || (line != 1 && line != 2)) {
			usage(sender);
			return;
		}

		String text = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
		ServerListConfig.getConfig().set("Motd.Classic.Main.Line-" + line, text);

		boolean wasRandom = ServerListConfig.getConfig().getBoolean("Motd.Classic.Random");
		ServerListConfig.getConfig().set("Motd.Classic.Random", false);
		ServerListConfig.getConfig().set("Motd.Classic.Enable", true);
		ServerListConfig.saveConfigFile();
		Main.getInstance().getServerListManager().load();

		say(sender, "SetMotd.Set", "%line%", String.valueOf(line), "%motd%", MessageUtils.colourTheStuff(text));
		if (wasRandom) {
			say(sender, "SetMotd.Random-Off");
		}
	}
}
