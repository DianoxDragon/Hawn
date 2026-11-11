package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

/**
 * The scheduler of Bukkit, on Spigot and Paper (since 1.6): one main thread, so every place is owned by it.
 */
final class BukkitScheduler implements Scheduler {

	private static final class Wrapped implements Task {

		private final BukkitTask task;

		Wrapped(BukkitTask task) {
			this.task = task;
		}

		@Override
		public int getTaskId() {
			return task.getTaskId();
		}

		@Override
		public void cancel() {
			task.cancel();
		}

		@Override
		public boolean isCancelled() {
			return task.isCancelled();
		}
	}

	private static Task plan(Runnable task, long delay, long period) {
		org.bukkit.scheduler.BukkitScheduler s = Bukkit.getScheduler();
		if (period > 0) {
			return new Wrapped(s.runTaskTimer(Main.getInstance(), task, Math.max(0, delay), period));
		}
		if (delay > 0) {
			return new Wrapped(s.runTaskLater(Main.getInstance(), task, delay));
		}
		return new Wrapped(s.runTask(Main.getInstance(), task));
	}

	@Override
	public Task global(Runnable task, long delay, long period) {
		return plan(task, delay, period);
	}

	// Like on Folia: nothing runs for an entity that is gone, and a timer stops.
	@Override
	public Task entity(Entity entity, Runnable task, Runnable retired, long delay, long period) {
		Task[] self = new Task[1];
		boolean[] done = new boolean[1];
		Runnable body = () -> {
			if (done[0]) {
				return;
			}
			if (gone(entity)) {
				done[0] = true;
				if (self[0] != null) {
					self[0].cancel();
				}
				if (retired != null) {
					retired.run();
				}
				return;
			}
			task.run();
		};
		self[0] = plan(body, delay, period);
		return self[0];
	}

	private static boolean gone(Entity entity) {
		if (entity instanceof Player) {
			return !((Player) entity).isOnline();
		}
		return !entity.isValid();
	}

	@Override
	public Task region(Location location, Runnable task, long delay, long period) {
		return plan(task, delay, period);
	}

	@Override
	public Task async(Runnable task, long delay, long period) {
		org.bukkit.scheduler.BukkitScheduler s = Bukkit.getScheduler();
		if (period > 0) {
			return new Wrapped(s.runTaskTimerAsynchronously(Main.getInstance(), task, Math.max(0, delay), period));
		}
		if (delay > 0) {
			return new Wrapped(s.runTaskLaterAsynchronously(Main.getInstance(), task, delay));
		}
		return new Wrapped(s.runTaskAsynchronously(Main.getInstance(), task));
	}

	@Override
	public boolean owns(Entity entity) {
		return Bukkit.isPrimaryThread();
	}

	@Override
	public boolean owns(Location location) {
		return Bukkit.isPrimaryThread();
	}

	@Override
	public boolean isGlobalThread() {
		return Bukkit.isPrimaryThread();
	}
}
