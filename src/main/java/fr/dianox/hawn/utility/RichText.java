package fr.dianox.hawn.utility;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MiniMessage (gradients, hover, click...) on Paper. The Paper module gives the renderer; on Spigot there is none
 * and every text stays a legacy text (& codes and hex colours).
 */
public final class RichText {

	/**
	 * Stands for "<" in a text typed by a player (see ConfigEventUtils#noAction): it never opens a MiniMessage tag.
	 */
	public static final char LT = '';

	public interface Renderer {
		void send(CommandSender to, String miniMessage);
		void title(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut);
		void actionBar(Player p, String miniMessage);
	}

	private static Renderer renderer;

	// A MiniMessage tag: <red>, <#ff0000>, <gradient:...>, <hover:...>, </bold>, <!italic>...
	private static final Pattern TAG = Pattern.compile("</?!?(#[0-9a-fA-F]{6}|"
			+ "black|dark_blue|dark_green|dark_aqua|dark_red|dark_purple|gold|gray|grey|dark_gray|dark_grey|blue|green|aqua|red|light_purple|yellow|white|"
			+ "color|colour|c|bold|b|italic|i|em|underlined|u|strikethrough|st|obfuscated|obf|reset|"
			+ "gradient|rainbow|transition|hover|click|insert|insertion|font|key|lang|tr|translate|newline|br|selector|sel|score|nbt|pride|shadow|head|sprite)"
			+ "[:>]", Pattern.CASE_INSENSITIVE);

	private static final Pattern HEX_LEGACY = Pattern.compile("[&§][xX]([&§][0-9a-fA-F]){6}");
	private static final Pattern HEX_AMPERSAND = Pattern.compile("&#([0-9a-fA-F]{6})");
	private static final Pattern HEX_BRACKETS = Pattern.compile("#<([0-9a-fA-F]{6})>");
	private static final Pattern CODE = Pattern.compile("[&§]([0-9a-fA-Fk-oK-OrR])");

	private static final String[] COLOURS = {"black", "dark_blue", "dark_green", "dark_aqua", "dark_red", "dark_purple", "gold", "gray",
			"dark_gray", "blue", "green", "aqua", "red", "light_purple", "yellow", "white"};
	// A legacy colour also ends the bold, italic...
	private static final String RESET_DECORATIONS = "<!b><!i><!u><!st><!obf>";

	private RichText() {}

	public static void setRenderer(Renderer r) {
		renderer = r;
	}

	public static boolean available() {
		return renderer != null;
	}

	/**
	 * @return true when the text has MiniMessage tags and the server can show them
	 */
	public static boolean isRich(String text) {
		return renderer != null && text != null && TAG.matcher(text).find();
	}

	public static void send(CommandSender to, String text) {
		renderer.send(to, toMiniMessage(text));
	}

	public static void title(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		renderer.title(p, title == null ? "" : toMiniMessage(title), subtitle == null ? "" : toMiniMessage(subtitle), fadeIn, stay, fadeOut);
	}

	public static void actionBar(Player p, String text) {
		renderer.actionBar(p, toMiniMessage(text));
	}

	/**
	 * The legacy codes (& and §, hex colours) as MiniMessage tags, so both can be mixed in a text.
	 */
	public static String toMiniMessage(String text) {
		text = text.replace(String.valueOf(LT), "\\<");

		text = replace(text, HEX_LEGACY, m -> RESET_DECORATIONS + "<#" + m.group().replaceAll("[&§xX]", "") + ">");
		text = replace(text, HEX_AMPERSAND, m -> RESET_DECORATIONS + "<#" + m.group(1) + ">");
		text = replace(text, HEX_BRACKETS, m -> RESET_DECORATIONS + "<#" + m.group(1) + ">");

		return replace(text, CODE, m -> {
			char code = Character.toLowerCase(m.group(1).charAt(0));
			int colour = "0123456789abcdef".indexOf(code);
			if (colour >= 0) {
				return RESET_DECORATIONS + "<" + COLOURS[colour] + ">";
			}
			switch (code) {
				case 'k': return "<obf>";
				case 'l': return "<b>";
				case 'm': return "<st>";
				case 'n': return "<u>";
				case 'o': return "<i>";
				default: return "<reset>";
			}
		});
	}

	private static String replace(String text, Pattern pattern, Function<Matcher, String> by) {
		Matcher m = pattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, Matcher.quoteReplacement(by.apply(m)));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
