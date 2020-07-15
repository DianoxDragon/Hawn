package fr.dianox.hawn.modules.scoreboard.scoreboards;

import fr.dianox.hawn.modules.scoreboard.ScoreManager;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class ScoreTask extends BukkitRunnable {

	private final ScoreManager scoreManager;
	private final FastBoard board;
	private final Player p;

	public ScoreTask(ScoreManager scoreManager, FastBoard board, Player p) {
		this.scoreManager = scoreManager;
		this.board = board;
		this.p = p;
	}

	@Override
	public void run() {
		if (!scoreManager.playerboard.containsKey(p)) return;

		String scoreboardfilename = "EMPTY";

		if (scoreManager.playerscore.containsKey(p)) {
			scoreboardfilename = scoreManager.playerscore.get(p);
		}

		if (scoreboardfilename.equals("EMPTY")) return;

			// Set the title
		String title = scoreManager.getFile(scoreboardfilename).getStringList("title").get(scoreManager.animationtab.get(scoreboardfilename + "TITLESCORENAME"));
		try {
			title = MessageUtils.colourTheStuff(title);
			title = PlaceHolders.ReplaceMainplaceholderP(title, p);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				title = PlaceholderAPI.setPlaceholders(p, title);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				title = PlaceHolders.BattleLevelPO(title, p);
			}
		} catch (Exception ignored) {}
		board.updateTitle(title);

		List<String> lines = new ArrayList<>();

		for (String s : scoreManager.getFile(scoreboardfilename).getStringList("text")) {

			String anim;

			if (s.contains("{CH_")) {
				anim = StringUtils.substringBetween(s, "{CH_", "}");
				if (scoreManager.getFile(scoreboardfilename).isSet("changeableText." + anim + ".text")) {
					try {
						s = scoreManager.getFile(scoreboardfilename).getStringList("changeableText." + anim + ".text").get(scoreManager.getAnimScore().get(scoreboardfilename + anim));
					} catch (Exception ignored) {}
				}
			}

			if (s.contains("{SC_")) {
				anim = StringUtils.substringBetween(s, "{SC_", "}");
				if (scoreManager.getFile(scoreboardfilename).isSet("scroller." + anim + ".text")) {
					try {
						s = scoreManager.getScrollerText().get(scoreboardfilename + anim);
					} catch (Exception ignored) {}
				}
			}

			s = MessageUtils.colourTheStuff(s);

			s = PlaceHolders.ReplaceMainplaceholderP(s, p);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				s = PlaceholderAPI.setPlaceholders(p, s);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				s = PlaceHolders.BattleLevelPO(s, p);
			}

			if (!FastBoard.VersionType.V1_13.isHigherOrEqual()) {
				if (s.length() > 32) {
					Bukkit.getLogger().severe("§cHAWN ERROR REPORT: The line of the scoreboard \"§7" + s + "\" §cis longer than 32 chars");
					Bukkit.getLogger().severe("§cIf the error is related to placeholders, please check your placeholders first");
					s = "§cERROR";
				}
			}

			lines.add(s);
		}

		board.updateLines(lines);
	}
}
