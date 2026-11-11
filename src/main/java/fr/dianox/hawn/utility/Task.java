package fr.dianox.hawn.utility;

/**
 * A task planned by {@link Tasks} (since 1.6): the same on Spigot, Paper and Folia, where the tasks of Bukkit don't exist.
 * Its number is given by Hawn, so the old code that keeps the numbers in maps works everywhere.
 */
public interface Task {

	int getTaskId();

	void cancel();

	boolean isCancelled();
}
