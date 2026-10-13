package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.StringUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The size of the world folders for the world manager. Reading every file of a big world takes time: the sizes are
 * computed outside the main thread and kept one minute.
 */
public final class WorldSizes {

	private static final long KEEP = 60_000L;

	// Folder -> {size in bytes, when it was computed}
	private static final Map<String, long[]> sizes = new ConcurrentHashMap<>();
	private static final Set<String> computing = ConcurrentHashMap.newKeySet();

	private WorldSizes() {}

	/**
	 * The last size known, even older than one minute (a new one is then on its way), or -1 when there is none yet.
	 */
	public static long get(File folder) {
		long[] known = sizes.get(folder.getAbsolutePath());
		return known == null ? -1 : known[0];
	}

	/**
	 * No size, or a size older than one minute.
	 */
	public static boolean outdated(File folder) {
		long[] known = sizes.get(folder.getAbsolutePath());
		return known == null || System.currentTimeMillis() - known[1] > KEEP;
	}

	/**
	 * Computes the sizes of these folders outside the main thread (the ones not already being computed), then runs
	 * done on the main thread.
	 */
	public static void compute(List<File> folders, Runnable done) {
		List<File> todo = new ArrayList<>();
		for (File folder : folders) {
			if (computing.add(folder.getAbsolutePath())) {
				todo.add(folder);
			}
		}
		if (todo.isEmpty()) return;

		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				for (File folder : todo) {
					long size = 0;
					try {
						size = StringUtils.sizeOfDirectory(folder);
					} catch (Exception | NoClassDefFoundError ignored) {}
					sizes.put(folder.getAbsolutePath(), new long[] {size, System.currentTimeMillis()});
				}
			} finally {
				for (File folder : todo) {
					computing.remove(folder.getAbsolutePath());
				}
			}

			if (Main.getInstance().isEnabled()) {
				Bukkit.getScheduler().runTask(Main.getInstance(), done);
			}
		});
	}
}
