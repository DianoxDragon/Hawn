package fr.dianox.hawn.modules.tablist.tab;

import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTablist extends BukkitRunnable {

	@Override
	public void run() {
		// Each header and footer is built once per refresh, then the placeholders are set per player
		Map<TablistLayout, String[]> built = new HashMap<>();

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			String[] text = built.computeIfAbsent(Main.getInstance().getTabManager().getLayout(p),
					layout -> new String[] {build(layout, layout.getHeader()), build(layout, layout.getFooter())});

			p.setPlayerListHeaderFooter(applyPlaceholders(text[0], p), applyPlaceholders(text[1], p));
		}
	}

	// The lines, with the current frame of each {anim_name} (the animations of the tab list, else those of Tablist.yml)
	private static String build(TablistLayout layout, List<String> lines) {
		StringBuilder sb = new StringBuilder();

		for (String s : lines) {
			int start = s.indexOf("{anim_");
			while (start >= 0) {
				int end = s.indexOf('}', start);
				if (end < 0) break;

				String anim = s.substring(start + 6, end);
				String frame = Main.getInstance().getTabManager().frame(layout, anim);
				if (frame != null) {
					s = s.substring(0, start) + frame + s.substring(end + 1);
					start = s.indexOf("{anim_", start + frame.length());
				} else {
					start = s.indexOf("{anim_", end);
				}
			}

			if (sb.length() > 0) sb.append('\n');
			sb.append(MessageUtils.colourTheStuff(s));
		}

		return sb.toString();
	}

	private static String applyPlaceholders(String text, Player p) {
		text = PlaceHolders.ReplaceMainplaceholderP(text, p);

		if (HooksManager.papi()) {
			text = PlaceholderAPI.setPlaceholders(p, text);
		}


		return text;
	}
}
