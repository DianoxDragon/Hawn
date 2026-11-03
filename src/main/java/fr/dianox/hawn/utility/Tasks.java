package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

/**
 * The one place where Hawn plans its tasks and teleports (since 1.5). Since 1.5.6, no other class calls the scheduler
 * of Bukkit: on Folia (1.6), only this class will have to choose the right scheduler.
 */
public final class Tasks {

	private Tasks() {}

	// Entity.teleportAsync of Paper, null on Spigot (since 1.5.6)
	private static final Method TELEPORT_ASYNC = findTeleportAsync();

	private static Method findTeleportAsync() {
		try {
			return Entity.class.getMethod("teleportAsync", Location.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

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

	/**
	 * Outside the main thread, every period ticks (since 1.5.4).
	 */
	public static BukkitTask asyncTimer(Runnable task, long delay, long period) {
		return Bukkit.getScheduler().runTaskTimerAsynchronously(Main.getInstance(), task, delay, period);
	}

	/**
	 * Teleports at once, on the main thread (since 1.5.4). The code after it can read the new world of the player,
	 * so it is not teleportAsync yet: on Folia (1.6), only this method will change.
	 */
	public static boolean teleport(Entity entity, Location to) {
		return entity.teleport(to);
	}

	/**
	 * Teleports once the chunk of the destination is loaded (since 1.5.6): teleportAsync on Paper, at once on Spigot.
	 * For the callers that don't read the new place of the entity right after: what depends on it goes in thenAccept,
	 * which runs on the main thread. A location without world is refused like teleport does.
	 */
	@SuppressWarnings("unchecked")
	public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location to) {
		if (to == null || to.getWorld() == null) {
			throw new IllegalArgumentException("The location has no world");
		}

		if (TELEPORT_ASYNC != null) {
			try {
				return (CompletableFuture<Boolean>) TELEPORT_ASYNC.invoke(entity, to);
			} catch (ReflectiveOperationException | ClassCastException e) {
				// Teleported at once below
			}
		}

		return CompletableFuture.completedFuture(entity.teleport(to));
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
