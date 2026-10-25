package fr.dianox.hawn.modules.menus;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Locale;

/**
 * The conditions of the menus (View-Requirements, Click-Requirements):
 * <ul>
 * <li>permission: &lt;permission&gt; and !permission: &lt;permission&gt;</li>
 * <li>world: &lt;world&gt; and !world: &lt;world&gt; (several worlds separated by commas)</li>
 * <li>a comparison of two texts once the placeholders are replaced: ==, !=, &gt;, &lt;, &gt;=, &lt;=, contains, !contains.
 * Two numbers are compared as numbers.</li>
 * </ul>
 */
public final class Requirements {

	// The longest first: ">=" before ">"
	private static final String[] OPERATORS = {" !contains ", " contains ", ">=", "<=", "!=", "==", ">", "<"};

	private Requirements() {}

	public static boolean all(Player p, List<String> conditions) {
		for (String condition : conditions) {
			if (!test(p, condition)) {
				return false;
			}
		}
		return true;
	}

	public static boolean test(Player p, String condition) {
		String c = condition.trim();
		boolean not = c.startsWith("!");
		String body = not ? c.substring(1).trim() : c;
		String lower = body.toLowerCase(Locale.ROOT);

		if (lower.startsWith("permission:")) {
			return not != p.hasPermission(body.substring("permission:".length()).trim());
		}

		if (lower.startsWith("world:")) {
			boolean in = false;
			for (String world : body.substring("world:".length()).split(",")) {
				if (world.trim().equalsIgnoreCase(p.getWorld().getName())) {
					in = true;
				}
			}
			return not != in;
		}

		// 118: the condition as written is cut first, then each side gets its placeholders: a value with ">" or "=="
		// (a prefix "&8>> ", a name) no longer cuts it at the wrong place
		for (String operator : OPERATORS) {
			int i = c.indexOf(operator);
			if (i > 0) {
				return compare(value(p, c.substring(0, i)), operator.trim(), value(p, c.substring(i + operator.length())));
			}
		}
		// Not a condition (see /hawn debug): never true
		return false;
	}

	// One side of a comparison: its placeholders, without their colours
	private static String value(Player p, String side) {
		return org.bukkit.ChatColor.stripColor(MenuItems.placeholders(p, side).replaceAll("(?i)&[0-9a-fk-or]", "")).trim();
	}

	/**
	 * What is wrong with a condition, null when nothing is (for /hawn debug).
	 */
	public static String problem(String condition) {
		String c = condition.trim();
		String body = c.startsWith("!") ? c.substring(1).trim() : c;
		String lower = body.toLowerCase(Locale.ROOT);
		if (lower.startsWith("permission:") || lower.startsWith("world:")) {
			return body.substring(body.indexOf(':') + 1).trim().isEmpty() ? "nothing after the colon" : null;
		}
		for (String operator : OPERATORS) {
			if (c.indexOf(operator) > 0) {
				return null;
			}
		}
		return "not a condition: permission:, world: or a comparison (==, !=, >, <, >=, <=, contains)";
	}

	private static boolean compare(String left, String operator, String right) {
		Double a = number(left);
		Double b = number(right);

		if (a != null && b != null) {
			switch (operator) {
				case ">=": return a >= b;
				case "<=": return a <= b;
				case ">": return a > b;
				case "<": return a < b;
				case "==": return a.doubleValue() == b.doubleValue();
				case "!=": return a.doubleValue() != b.doubleValue();
				default: break;
			}
		}

		switch (operator) {
			case "==": return left.equalsIgnoreCase(right);
			case "!=": return !left.equalsIgnoreCase(right);
			case "contains": return left.toLowerCase(Locale.ROOT).contains(right.toLowerCase(Locale.ROOT));
			case "!contains": return !left.toLowerCase(Locale.ROOT).contains(right.toLowerCase(Locale.ROOT));
			// > and < on texts that are not numbers
			default: return false;
		}
	}

	private static Double number(String s) {
		try {
			return Double.valueOf(s.replace(',', '.'));
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
