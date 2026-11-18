package fr.dianox.hawn.command.commands.tools;

import fr.dianox.hawn.command.commands.tab.Tab;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.event.PlatformEvents;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * /colors (since 1.6.2): the colour and format codes, each written in its colour, then the hex colours (and MiniMessage
 * on Paper).
 */
public class ColorsCommand extends ToolCommand {

	private static final String[][] COLOURS = {
		{"0", "black"}, {"1", "dark_blue"}, {"2", "dark_green"}, {"3", "dark_aqua"}, {"4", "dark_red"}, {"5", "dark_purple"},
		{"6", "gold"}, {"7", "gray"}, {"8", "dark_gray"}, {"9", "blue"}, {"a", "green"}, {"b", "aqua"}, {"c", "red"},
		{"d", "light_purple"}, {"e", "yellow"}, {"f", "white"}
	};
	private static final String[][] FORMATS = {{"l", "bold"}, {"o", "italic"}, {"n", "underline"}, {"m", "strikethrough"}, {"k", "obfuscated"}, {"r", "reset"}};

	public ColorsCommand(String name) {
		super(name, "Colors", "hawn.command.colors", "/colors", "Shows the colour codes", false);
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
		return Tab.none();
	}

	@Override
	void run(CommandSender sender, String[] args) {
		say(sender, "Colors.Header");

		StringBuilder colours = new StringBuilder();
		for (int i = 0; i < COLOURS.length; i++) {
			colours.append("&").append(COLOURS[i][0]).append("&&").append(COLOURS[i][0]).append(" ");
			if (i == 7 || i == COLOURS.length - 1) {
				send(sender, colours.toString().trim());
				colours.setLength(0);
			}
		}

		StringBuilder formats = new StringBuilder();
		for (String[] f : FORMATS) {
			formats.append("&7&").append(f[0]).append("&&").append(f[0]).append(" ").append(f[1]).append("&r ");
		}
		send(sender, formats.toString().trim());

		send(sender, "&#ff5555&&#ff5555 &#55ff55&&#55ff55 &#5555ff&&#5555ff &7(&e&&#RRGGBB&7)");
		if (PlatformEvents.isMiniMessage()) {
			send(sender, "&7MiniMessage: &e<red>&7, &e<#ff8800>&7, &e<gradient:gold:red>text</gradient>");
		}
	}

	// && shows a & ; the rest is coloured
	private static void send(CommandSender to, String line) {
		String marker = "\u0000";
		String coloured = MessageUtils.colourTheStuff(line.replace("&&", marker)).replace(marker, "&");
		// sendMessage(String) reads no MiniMessage: the tags are shown as they are written
		to.sendMessage(coloured);
	}
}
