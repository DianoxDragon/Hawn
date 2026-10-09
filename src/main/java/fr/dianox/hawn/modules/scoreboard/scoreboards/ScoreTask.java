package fr.dianox.hawn.modules.scoreboard.scoreboards;

import fr.dianox.hawn.hook.HooksManager;
import fr.mrmicky.fastboard.FastBoard;

import fr.dianox.hawn.modules.scoreboard.ScoreManager;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import me.clip.placeholderapi.PlaceholderAPI;
import fr.dianox.hawn.utility.StringUtils;
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
		if (board.isDeleted() || !p.isOnline()) {
			cancel();
			return;
		}

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
			if (HooksManager.papi()) {
				title = PlaceholderAPI.setPlaceholders(p, title);
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
			if (HooksManager.papi()) {
				s = PlaceholderAPI.setPlaceholders(p, s);
			}

			lines.add(s);
		}

		board.updateLines(lines);
	}
}
