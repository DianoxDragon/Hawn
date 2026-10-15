package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.mrmicky.fastboard.FastBoard;
import fr.dianox.hawn.modules.scoreboard.scoreboards.ScoreTask;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.configs.ScoreboardMainConfig;
import fr.dianox.hawn.utility.config.configs.commands.ScoreboardCommandConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class OnScoreboard implements Listener {

	@EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();

		if (!Main.getInstance().getScoreManager().playerscore.containsKey(player)) {
			String world = player.getWorld().getName();
			createNewScore(player, world);
		}
    }

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		if (Main.getInstance().getScoreManager().playerboard.containsKey(p)) {
			Bukkit.getScheduler().cancelTask(Main.getInstance().getScoreManager().scoretaskplayer.get(p));

			FastBoard board = Main.getInstance().getScoreManager().playerboard.get(p);
			if (board != null) {
				board.delete();
			}

			Main.getInstance().getScoreManager().playerboard.remove(p);
			Main.getInstance().getScoreManager().playerscore.remove(p);
			Main.getInstance().getScoreManager().scoretaskplayer.remove(p);
		}

		Main.getInstance().getScoreManager().getNoScore().remove(p);
	}

	@EventHandler
	public void onChangeWorldEvent(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();

		if (Main.getInstance().getScoreManager().playerscore.containsKey(p)) {
			Bukkit.getScheduler().cancelTask(Main.getInstance().getScoreManager().scoretaskplayer.get(p));

			FastBoard board = Main.getInstance().getScoreManager().playerboard.get(p);
			if (board != null) {
				board.delete();
			}

			Main.getInstance().getScoreManager().playerboard.remove(p);
			Main.getInstance().getScoreManager().playerscore.remove(p);
			Main.getInstance().getScoreManager().scoretaskplayer.remove(p);
		}

		String world = p.getWorld().getName();
		createNewScore(p, world);
	}

	public static void createNewScore(Player p, String world) {
		if (!ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
			return;
		}

		if (Main.getInstance().getScoreManager().getNoScore().contains(p)) {
			return;
		}

		String bool = PlayerOptionSQLClass.getYmlaMysqlsb(p, "keepsb");

		if (bool.equalsIgnoreCase("TRUE") && ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Option.Keep-Scoreboard-Change")) {
			String sb = PlayerOptionSQLClass.getYmlaMysqlsb(p, "scoreboard");

			// The kept scoreboard can have been deleted or renamed since: the scoreboard of the world instead
			String kept = Main.getInstance().getScoreManager().scoreacess.get(sb);
			if (kept != null && new java.io.File(kept).exists()) {
				if (p.hasPermission("hawn.scoreboard." + sb)) {
					start(p, sb);
				}
				return;
			}
		}

		for (String s : Main.getInstance().getScoreManager().worldscore.keySet()) {
			List<String> worlds = Main.getInstance().getScoreManager().worldscore.get(s);
			if ((worlds.contains("ALLWORLDSOFCOURSE") || worlds.contains(world)) && p.hasPermission("hawn.scoreboard." + s)) {
				start(p, s);
				PlayerOptionSQLClass.saveSBmysqlyaml(p, s, "FALSE");
				break;
			}
		}
	}

	private static void start(Player p, String sb) {
		FastBoard board = new FastBoard(p);

		Main.getInstance().getScoreManager().playerboard.put(p, board);
		Main.getInstance().getScoreManager().playerscore.put(p, sb);

		if (Main.getInstance().getScoreManager().getFile(sb).isSet("updater.text")) {
			int number = Main.getInstance().getScoreManager().getFile(sb).getInt("updater.text");
			Main.getInstance().getScoreManager().writeInt(sb, "updater.scoreboard", number);
			Main.getInstance().getScoreManager().writeInt(sb, "updater.text", null);
		}

		BukkitTask TaskName = new ScoreTask(Main.getInstance().getScoreManager(), board, p).runTaskTimer(Main.getInstance(), 0,
				Main.getInstance().getScoreManager().getFile(sb).getInt("updater.scoreboard"));

		Main.getInstance().getScoreManager().scoretaskplayer.put(p, TaskName.getTaskId());
	}
}
