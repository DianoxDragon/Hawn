package fr.dianox.hawn.utility;

import fr.dianox.hawn.hook.HooksManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BossBarApi {

	// One boss bar per player (a single shared bar made every player see the last created one)
	// Safe for several threads (since 1.5.4)
	private static final Map<UUID, BossBar> bars = new ConcurrentHashMap<>();
	public static Map<Player, Integer> ptaskbb = new ConcurrentHashMap<>();
	public static List<Player> BBBlock = new CopyOnWriteArrayList<>();

	public static void createnewbar(Player p, String color, String title, String style, Float progress) {
		deletebar(p);

		BossBar bar = Bukkit.createBossBar(format(title, p), parseColor(color), parseStyle(style));
		bar.setProgress(clamp(progress));
		bar.addPlayer(p);

		bars.put(p.getUniqueId(), bar);
	}

	public static void updateBar(Player p, String color, String title, String style, Float progress) {
		BossBar bar = bars.get(p.getUniqueId());

		if (bar == null) {
			createnewbar(p, color, title, style, progress);
			return;
		}

		bar.setColor(parseColor(color));
		bar.setTitle(format(title, p));
		bar.setStyle(parseStyle(style));
		bar.setProgress(clamp(progress));
	}

	public static void deletebar(Player p) {
		BossBar bar = bars.remove(p.getUniqueId());

		if (bar != null) {
			bar.removeAll();
		}
	}

	private static String format(String title, Player p) {
		if (title == null) {
			return "";
		}

		if (HooksManager.papi()) {
			if (PlaceholderAPI.containsPlaceholders(title))
				title = PlaceholderAPI.setPlaceholders(p, title);
		}


		title = PlaceHolders.ReplaceMainplaceholderP(title, p);

		return MessageUtils.colourTheStuff(title);
	}

	private static BarColor parseColor(String color) {
		try {
			return BarColor.valueOf(color.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			return BarColor.WHITE;
		}
	}

	private static BarStyle parseStyle(String style) {
		try {
			return BarStyle.valueOf(style.toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			return BarStyle.SOLID;
		}
	}

	private static double clamp(Float progress) {
		if (progress == null) return 1.0D;
		return Math.max(0.0D, Math.min(1.0D, progress));
	}

}
