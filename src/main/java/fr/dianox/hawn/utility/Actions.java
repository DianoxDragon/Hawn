package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.hook.HooksManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

/**
 * The actions added in 1.4, read by ConfigEventUtils:
 * [delay[ticks]]: line, [chance[percent]]: line, [broadcast]: text, [sound]: SOUND volume pitch, [title[in,stay,out]]: title //n subtitle.
 */
public final class Actions {

	private Actions() {}

	/**
	 * [delay[40]]: the rest of the line runs 40 ticks later. [chance[25]]: it runs one time out of four.
	 * They can be chained: [chance[50]]: [delay[20]]: [sound]: ENTITY_PLAYER_LEVELUP
	 * @param run what runs the rest of the line
	 * @return true when the line was such a prefix (and is handled)
	 */
	public static boolean prefix(Player p, String line, Consumer<String> run) {
		String[] delay = split(line, "[delay[");
		if (delay != null) {
			long ticks = Math.max(0, (long) number(delay[0], 0));
			Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
				if (p.isOnline()) {
					run.accept(delay[1]);
				}
			}, ticks);
			return true;
		}

		String[] chance = split(line, "[chance[");
		if (chance != null) {
			if (ThreadLocalRandom.current().nextDouble(100) < number(chance[0], 100)) {
				run.accept(chance[1]);
			}
			return true;
		}

		return false;
	}

	/**
	 * @return true when the line was one of these actions
	 */
	public static boolean run(Player p, String line, String error) {
		if (line.startsWith("[broadcast]: ")) {
			broadcast(p, line.substring("[broadcast]: ".length()));
			return true;
		}

		// [sounds]: the action of 1.3, now with the volume and the pitch too
		if (line.startsWith("[sound]: ") || line.startsWith("[sounds]: ")) {
			sound(p, line.substring(line.indexOf("]: ") + 3).trim(), error);
			return true;
		}

		if (line.startsWith("[title]: ")) {
			title(p, "10,70,20", line.substring("[title]: ".length()));
			return true;
		}

		String[] title = split(line, "[title[");
		if (title != null) {
			title(p, title[0], title[1]);
			return true;
		}

		return false;
	}

	// [broadcast]: the text, with the placeholders of the player who runs it, to everybody
	private static void broadcast(Player p, String text) {
		text = placeholders(p, text);
		for (Player all : Bukkit.getOnlinePlayers()) {
			MessageUtils.send(all, text);
		}
		MessageUtils.send(Bukkit.getConsoleSender(), text);
	}

	// [sound]: ENTITY_PLAYER_LEVELUP 0.8 1.5 (volume and pitch are optional, 1 by default)
	private static void sound(Player p, String text, String error) {
		String[] parts = text.split("\\s+");
		float volume = parts.length > 1 ? (float) number(parts[1], 1) : 1f;
		float pitch = parts.length > 2 ? (float) number(parts[2], 1) : 1f;
		p.playSound(p.getLocation(), XParse.sound(parts[0], error), volume, pitch);
	}

	// [title[10,70,20]]: Title //n Subtitle (fade in, stay, fade out in ticks)
	private static void title(Player p, String times, String text) {
		String[] t = times.split("[,;]");
		int fadeIn = t.length > 0 ? (int) number(t[0], 10) : 10;
		int stay = t.length > 1 ? (int) number(t[1], 70) : 70;
		int fadeOut = t.length > 2 ? (int) number(t[2], 20) : 20;

		text = placeholders(p, text);
		String title = text;
		String subtitle = " ";
		int i = text.indexOf("//n");
		if (i >= 0) {
			title = text.substring(0, i).trim();
			subtitle = text.substring(i + 3).trim();
		}

		Titles.sendTitle(p, fadeIn, stay, fadeOut, title, subtitle);
	}

	private static String placeholders(Player p, String text) {
		text = PlaceHolders.ReplaceMainplaceholderP(text, p);
		if (HooksManager.papi()) {
			text = PlaceholderAPI.setPlaceholders(p, text);
		}
		if (HooksManager.mvdw()) {
			text = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, text);
		}
		return MessageUtils.colourTheStuff(text);
	}

	// "[delay[40]]: rest" with "[delay[" -> {"40", "rest"}
	private static String[] split(String line, String start) {
		if (!line.startsWith(start)) {
			return null;
		}
		int end = line.indexOf("]]: ", start.length());
		if (end < 0) {
			return null;
		}
		return new String[] {line.substring(start.length(), end).trim(), line.substring(end + 4)};
	}

	private static double number(String text, double def) {
		try {
			return Double.parseDouble(text.trim().replace(',', '.'));
		} catch (NumberFormatException e) {
			return def;
		}
	}
}
