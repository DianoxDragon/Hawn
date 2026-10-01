package fr.dianox.hawn.modules.tablist.tab;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.StringUtils;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class MainTablist extends BukkitRunnable {

	@Override
	public void run() {
		String header = "";
		String footer = "";

		if (TablistConfig.getConfig().getBoolean("Tablist.header.enabled")) {
			header = build(TablistConfig.getConfig().getStringList("Tablist.header.message"));
		}

		if (TablistConfig.getConfig().getBoolean("Tablist.footer.enabled")) {
			footer = build(TablistConfig.getConfig().getStringList("Tablist.footer.message"));
		}

		Main.getInstance().getTabManager().hea = header;
		Main.getInstance().getTabManager().foo = footer;

		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			p.setPlayerListHeaderFooter(applyPlaceholders(header, p), applyPlaceholders(footer, p));
		}
	}

	private static String build(List<String> lines) {
		StringBuilder sb = new StringBuilder();

		for (String s : lines) {
			if (s.contains("{anim_")) {
				String anim = StringUtils.substringBetween(s, "{anim_", "}");
				if (anim != null && TablistConfig.getConfig().isSet("Animations." + anim + ".text")) {
					Integer frame = Main.getInstance().getTabManager().animationtab.get(anim);
					List<String> frames = TablistConfig.getConfig().getStringList("Animations." + anim + ".text");
					if (frame != null && frame < frames.size()) {
						s = s.replace("{anim_" + anim + "}", frames.get(frame));
					}
				}
			}

			if (sb.length() > 0) sb.append('\n');
			sb.append(MessageUtils.colourTheStuff(s));
		}

		return sb.toString();
	}

	private static String applyPlaceholders(String text, Player p) {
		text = PlaceHolders.ReplaceMainplaceholderP(text, p);

		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
			text = PlaceholderAPI.setPlaceholders(p, text);
		}

		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
			text = PlaceHolders.BattleLevelPO(text, p);
		}

		return text;
	}
}
