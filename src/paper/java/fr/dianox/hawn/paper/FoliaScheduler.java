package fr.dianox.hawn.paper;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.Scheduler;
import fr.dianox.hawn.utility.Task;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * The schedulers of Folia (since 1.6): the global region for what has no place, the region of a place, the thread of
 * an entity, and async. Loaded by Tasks only on Folia.
 */
public class FoliaScheduler implements Scheduler {

	private static final Task DEAD = new Task() {
		@Override
		public int getTaskId() {
			return -1;
		}

		@Override
		public void cancel() {}

		@Override
		public boolean isCancelled() {
			return true;
		}
	};

	private static final class Wrapped implements Task {

		private final ScheduledTask task;

		Wrapped(ScheduledTask task) {
			this.task = task;
		}

		@Override
		public int getTaskId() {
			return -1;
		}

		@Override
		public void cancel() {
			task.cancel();
		}

		@Override
		public boolean isCancelled() {
			return task.isCancelled() || task.getExecutionState() == ScheduledTask.ExecutionState.FINISHED;
		}
	}

	private static Task wrap(ScheduledTask task) {
		return task == null ? DEAD : new Wrapped(task);
	}

	@Override
	public Task global(Runnable task, long delay, long period) {
		Consumer<ScheduledTask> c = t -> task.run();
		if (period > 0) {
			return wrap(Bukkit.getGlobalRegionScheduler().runAtFixedRate(Main.getInstance(), c, Math.max(1, delay), period));
		}
		if (delay > 0) {
			return wrap(Bukkit.getGlobalRegionScheduler().runDelayed(Main.getInstance(), c, delay));
		}
		return wrap(Bukkit.getGlobalRegionScheduler().run(Main.getInstance(), c));
	}

	@Override
	public Task entity(Entity entity, Runnable task, Runnable retired, long delay, long period) {
		Consumer<ScheduledTask> c = t -> task.run();
		ScheduledTask t;
		if (period > 0) {
			t = entity.getScheduler().runAtFixedRate(Main.getInstance(), c, retired, Math.max(1, delay), period);
		} else if (delay > 0) {
			t = entity.getScheduler().runDelayed(Main.getInstance(), c, retired, delay);
		} else {
			t = entity.getScheduler().run(Main.getInstance(), c, retired);
		}
		if (t == null && retired != null) {
			// The entity was already gone
			retired.run();
		}
		return wrap(t);
	}

	@Override
	public Task region(Location location, Runnable task, long delay, long period) {
		Consumer<ScheduledTask> c = t -> task.run();
		if (period > 0) {
			return wrap(Bukkit.getRegionScheduler().runAtFixedRate(Main.getInstance(), location, c, Math.max(1, delay), period));
		}
		if (delay > 0) {
			return wrap(Bukkit.getRegionScheduler().runDelayed(Main.getInstance(), location, c, delay));
		}
		return wrap(Bukkit.getRegionScheduler().run(Main.getInstance(), location, c));
	}

	@Override
	public Task async(Runnable task, long delay, long period) {
		Consumer<ScheduledTask> c = t -> task.run();
		if (period > 0) {
			return wrap(Bukkit.getAsyncScheduler().runAtFixedRate(Main.getInstance(), c, Math.max(1, delay * 50), period * 50, TimeUnit.MILLISECONDS));
		}
		if (delay > 0) {
			return wrap(Bukkit.getAsyncScheduler().runDelayed(Main.getInstance(), c, delay * 50, TimeUnit.MILLISECONDS));
		}
		return wrap(Bukkit.getAsyncScheduler().runNow(Main.getInstance(), c));
	}

	@Override
	public boolean owns(Entity entity) {
		return Bukkit.isOwnedByCurrentRegion(entity);
	}

	@Override
	public boolean owns(Location location) {
		return Bukkit.isOwnedByCurrentRegion(location);
	}

	@Override
	public boolean isGlobalThread() {
		return Bukkit.isGlobalTickThread();
	}
}
