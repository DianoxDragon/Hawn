package fr.dianox.hawn.command.commands.tasks;

import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

// Planned through Tasks (since 1.5.3): it stops itself with its own handle
public class VanishTaskAB implements Runnable {

	private Player p;
	private BukkitTask task;

	public VanishTaskAB(Player p) {
        this.p = p;
	}

	/**
	 * The action bar of a vanished player, every period ticks from the next second.
	 */
	public static BukkitTask start(Player p, long period) {
		VanishTaskAB runnable = new VanishTaskAB(p);
		runnable.task = Tasks.timer(runnable, 20, period);
		return runnable.task;
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
            p = null;
            Tasks.cancel(task);
            return;
        }

		String str = ConfigMMsg.getConfig().getString("Vanish.Action-Bar");
		str = MessageUtils.colourTheStuff(str);
		str = PlaceHolders.ReplaceMainplaceholderP(str, p);
		if (HooksManager.papi()) {
			str = PlaceholderAPI.setPlaceholders(p, str);
		}
		str = str.substring(1, str.length() - 1);

		ActionBar.sendActionBar(p, str);

	}

}
