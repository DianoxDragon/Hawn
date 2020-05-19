package fr.dianox.hawn.modules.scoreboard.scoreboards;

import fr.dianox.hawn.modules.scoreboard.ScoreManager;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimationTask extends BukkitRunnable {

	private final String anim;
	private final String scorename;
	private final ScoreManager scoreManager;

	public AnimationTask(String anim, String scorename, ScoreManager scoreManager) {
		this.anim = anim;
		this.scorename = scorename;
		this.scoreManager = scoreManager;
	}

	@SuppressWarnings("unused")
	@Override
	public void run() {
		if (!scoreManager.getAnimScore().containsKey(scorename + anim)) {
			scoreManager.getAnimScore().put(scorename + anim, 0);
		}

		try {
			if (!anim.equals("TITLESCORENAME")) {
				Integer numberline = scoreManager.getAnimScore().get(scorename + anim);
				String animation = scoreManager.getFile(scorename).getStringList("changeableText." + anim + ".text").get(numberline);

				try {
					numberline++;
					String animation2 = scoreManager.getFile(scorename).getStringList("changeableText." + anim + ".text").get(numberline);
					scoreManager.getAnimScore().replace(scorename + anim, numberline);
				} catch (Exception e) {
					scoreManager.getAnimScore().replace(scorename + anim, 0);
				}
			} else {
				Integer numberline = scoreManager.getAnimScore().get(scorename + anim);
				String animation = scoreManager.getFile(scorename).getStringList("title").get(numberline);

				try {
					numberline++;
					String animation2 = scoreManager.getFile(scorename).getStringList("title").get(numberline);
					scoreManager.getAnimScore().replace(scorename + anim, numberline);
				} catch (Exception e) {
					scoreManager.getAnimScore().replace(scorename + anim, 0);
				}
			}
		} catch (Exception e) {
			scoreManager.getAnimScore().replace(scorename + anim, 0);
		}
	}
}
