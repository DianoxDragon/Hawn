package fr.dianox.hawn.utility;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * What {@link Tasks} asks of the server (since 1.6): the scheduler of Bukkit on Spigot and Paper, the schedulers of
 * Folia (global, region, entity, async) on Folia. The delays and periods are in ticks; a delay of 0 runs as soon as
 * possible (the next tick at the latest).
 */
public interface Scheduler {

	/**
	 * For what is not tied to a place: the main thread, or the global region of Folia.
	 */
	Task global(Runnable task, long delay, long period);

	/**
	 * On the thread that owns the entity, and not at all once it is gone (a player who left): then retired runs
	 * instead, once, and a timer stops.
	 */
	Task entity(Entity entity, Runnable task, Runnable retired, long delay, long period);

	/**
	 * On the thread that owns this place of the world.
	 */
	Task region(Location location, Runnable task, long delay, long period);

	Task async(Runnable task, long delay, long period);

	/**
	 * True when the current thread may touch the entity now.
	 */
	boolean owns(Entity entity);

	boolean owns(Location location);

	/**
	 * True when the current thread may do what {@link #global} does: the main thread, or the global region.
	 */
	boolean isGlobalThread();
}
