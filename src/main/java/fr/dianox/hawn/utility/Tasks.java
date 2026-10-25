package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

/**
 * The one place where Hawn plans its tasks (since 1.5). The new code goes through it; the old code moves here
 * module by module. On Folia (1.6), only this class will have to choose the right scheduler.
 */
public final class Tasks {

	private Tasks() {}

	/**
	 * On the main thread, now if already there.
	 */
	public static void sync(Runnable task) {
		if (Bukkit.isPrimaryThread()) {
			task.run();
		} else {
			Bukkit.getScheduler().runTask(Main.getInstance(), task);
		}
	}

	/**
	 * On the main thread, at the next tick (also from the main thread: after what is running now).
	 */
	public static BukkitTask next(Runnable task) {
		return Bukkit.getScheduler().runTask(Main.getInstance(), task);
	}

	public static BukkitTask later(Runnable task, long ticks) {
		return Bukkit.getScheduler().runTaskLater(Main.getInstance(), task, ticks);
	}

	public static BukkitTask timer(Runnable task, long delay, long period) {
		return Bukkit.getScheduler().runTaskTimer(Main.getInstance(), task, delay, period);
	}

	public static BukkitTask async(Runnable task) {
		return Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), task);
	}

	public static void cancel(BukkitTask task) {
		if (task != null) {
			task.cancel();
		}
	}

	/**
	 * A task kept by its number (the old code keeps the numbers in maps).
	 */
	public static void cancel(int id) {
		Bukkit.getScheduler().cancelTask(id);
	}
}
