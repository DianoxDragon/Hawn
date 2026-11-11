package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * The one place where Hawn plans its tasks and teleports (since 1.5). No other class calls a scheduler: since 1.6,
 * this class picks the scheduler of Bukkit on Spigot and Paper, and the schedulers of Folia on Folia.
 *
 * Without a place, a task runs on the main thread (the global region of Folia). What touches a player or another
 * entity goes through the methods that take it, so it runs on the thread that owns it.
 */
public final class Tasks {

	private Tasks() {}

	private static final boolean FOLIA = findFolia();

	private static final Scheduler SCHEDULER = FOLIA ? loadFolia() : new BukkitScheduler();

	// Entity.teleportAsync of Paper, null on Spigot (since 1.5.6)
	private static final Method TELEPORT_ASYNC = findTeleportAsync();

	// The tasks by their number, for Tasks.cancel(int)
	private static final Map<Integer, Handle> TASKS = new ConcurrentHashMap<>();
	private static final AtomicInteger NEXT_ID = new AtomicInteger();

	private static boolean findFolia() {
		try {
			Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	private static Scheduler loadFolia() {
		try {
			return (Scheduler) Class.forName("fr.dianox.hawn.paper.FoliaScheduler").getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException | LinkageError e) {
			throw new IllegalStateException("The schedulers of Folia could not be loaded", e);
		}
	}

	private static Method findTeleportAsync() {
		try {
			return Entity.class.getMethod("teleportAsync", Location.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

	/**
	 * True on a Folia server (since 1.6).
	 */
	public static boolean isFolia() {
		return FOLIA;
	}

	// The task given to the callers: its number, and its cancel that works before the server has it.
	private static final class Handle implements Task {

		private final int id = NEXT_ID.incrementAndGet();
		private volatile Task inner;
		private volatile boolean cancelled;

		@Override
		public int getTaskId() {
			return id;
		}

		@Override
		public void cancel() {
			cancelled = true;
			TASKS.remove(id);
			Task t = inner;
			if (t != null) {
				t.cancel();
			}
		}

		@Override
		public boolean isCancelled() {
			Task t = inner;
			return cancelled || (t != null && t.isCancelled());
		}
	}

	private interface Planner {
		Task plan(Runnable body);
	}

	private static Task plan(Runnable task, boolean repeat, Planner planner) {
		Handle h = new Handle();
		TASKS.put(h.id, h);
		Runnable body = () -> {
			if (h.cancelled) {
				return;
			}
			if (!repeat) {
				TASKS.remove(h.id);
			}
			task.run();
		};
		h.inner = planner.plan(body);
		if (h.cancelled) {
			// Cancelled from another thread before the server had it
			h.inner.cancel();
		} else if (h.inner.isCancelled()) {
			// Never planned: the entity was already gone
			TASKS.remove(h.id);
		}
		return h;
	}

	/*
	 * Without a place: the main thread, or the global region of Folia
	 */

	/**
	 * On the main thread (the global region of Folia), now if already there.
	 */
	public static void sync(Runnable task) {
		if (SCHEDULER.isGlobalThread()) {
			task.run();
		} else {
			next(task);
		}
	}

	/**
	 * On the main thread (the global region of Folia), at the next tick (also from there: after what is running now).
	 */
	public static Task next(Runnable task) {
		return plan(task, false, body -> SCHEDULER.global(body, 0, 0));
	}

	public static Task later(Runnable task, long ticks) {
		return plan(task, false, body -> SCHEDULER.global(body, ticks, 0));
	}

	public static Task timer(Runnable task, long delay, long period) {
		return plan(task, true, body -> SCHEDULER.global(body, delay, period));
	}

	/*
	 * For an entity: on the thread that owns it, never once it is gone (since 1.6)
	 */

	/**
	 * On the thread of the entity, now if already there.
	 */
	public static void sync(Entity entity, Runnable task) {
		if (SCHEDULER.owns(entity)) {
			task.run();
		} else {
			next(entity, task);
		}
	}

	public static Task next(Entity entity, Runnable task) {
		return plan(task, false, body -> SCHEDULER.entity(entity, body, null, 0, 0));
	}

	public static Task later(Entity entity, Runnable task, long ticks) {
		return plan(task, false, body -> SCHEDULER.entity(entity, body, null, ticks, 0));
	}

	public static Task timer(Entity entity, Runnable task, long delay, long period) {
		Handle[] self = new Handle[1];
		Task t = plan(task, true, body -> SCHEDULER.entity(entity, body, () -> {
			if (self[0] != null) {
				TASKS.remove(self[0].id);
			}
		}, delay, period));
		self[0] = (Handle) t;
		return t;
	}

	/**
	 * For each online player, on the thread of the player (at once for those owned by the current thread).
	 * On Spigot and Paper, all at once on the main thread.
	 */
	public static void forEachPlayer(Consumer<Player> action) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (SCHEDULER.owns(p)) {
				action.accept(p);
			} else {
				next(p, () -> action.accept(p));
			}
		}
	}

	/*
	 * For a place of the world: on the thread that owns it (since 1.6)
	 */

	public static void sync(Location location, Runnable task) {
		if (SCHEDULER.owns(location)) {
			task.run();
		} else {
			later(location, task, 0);
		}
	}

	public static Task later(Location location, Runnable task, long ticks) {
		return plan(task, false, body -> SCHEDULER.region(location, body, ticks, 0));
	}

	/**
	 * A command of the console: on the main thread (the global region of Folia), now if already there.
	 */
	public static void console(String command) {
		sync(() -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
	}

	/*
	 * Outside the server threads
	 */

	public static Task async(Runnable task) {
		return plan(task, false, body -> SCHEDULER.async(body, 0, 0));
	}

	/**
	 * Outside the main thread, every period ticks (since 1.5.4).
	 */
	public static Task asyncTimer(Runnable task, long delay, long period) {
		return plan(task, true, body -> SCHEDULER.async(body, delay, period));
	}

	/**
	 * True when the current thread may touch the entity now (always the main thread on Spigot and Paper).
	 */
	public static boolean owns(Entity entity) {
		return SCHEDULER.owns(entity);
	}

	/*
	 * Teleports
	 */

	/**
	 * Teleports at once, on the main thread (since 1.5.4). The code after it can read the new world of the player.
	 * Folia only has teleportAsync: there, the teleport is started and true is returned.
	 */
	public static boolean teleport(Entity entity, Location to) {
		if (FOLIA) {
			teleportAsync(entity, to);
			return true;
		}
		return entity.teleport(to);
	}

	/**
	 * Teleports once the chunk of the destination is loaded (since 1.5.6): teleportAsync on Paper and Folia, at once
	 * on Spigot. For the callers that don't read the new place of the entity right after: what depends on it goes in
	 * thenAccept (on Folia, it runs on the thread of the entity). A location without world is refused like teleport
	 * does.
	 */
	public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location to) {
		if (to == null || to.getWorld() == null) {
			throw new IllegalArgumentException("The location has no world");
		}

		if (FOLIA && !SCHEDULER.owns(entity)) {
			CompletableFuture<Boolean> result = new CompletableFuture<>();
			next(entity, () -> callTeleportAsync(entity, to).whenComplete((ok, e) -> {
				if (e != null) {
					result.completeExceptionally(e);
				} else {
					result.complete(ok);
				}
			}));
			return result;
		}

		return callTeleportAsync(entity, to);
	}

	@SuppressWarnings("unchecked")
	private static CompletableFuture<Boolean> callTeleportAsync(Entity entity, Location to) {
		if (TELEPORT_ASYNC != null) {
			try {
				return (CompletableFuture<Boolean>) TELEPORT_ASYNC.invoke(entity, to);
			} catch (ReflectiveOperationException | ClassCastException e) {
				// Teleported at once below
			}
		}

		return CompletableFuture.completedFuture(entity.teleport(to));
	}

	/*
	 * Cancel
	 */

	public static void cancel(Task task) {
		if (task != null) {
			task.cancel();
		}
	}

	/**
	 * A task kept by its number (the old code keeps the numbers in maps).
	 */
	public static void cancel(Integer id) {
		if (id == null) {
			return;
		}
		Handle h = TASKS.get(id);
		if (h != null) {
			h.cancel();
		}
	}

	/**
	 * At the stop of the plugin: the server cancels the tasks of Bukkit itself, but not all those of Folia.
	 */
	public static void cancelAll() {
		for (Handle h : TASKS.values()) {
			h.cancel();
		}
		TASKS.clear();
	}
}
