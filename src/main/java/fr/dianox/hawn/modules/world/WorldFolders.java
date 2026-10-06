package fr.dianox.hawn.modules.world;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

/**
 * Folders of the worlds. On recent Paper versions, World#getWorldFolder() is the folder of the dimension
 * (world/dimensions/minecraft/overworld), not the folder of the world with its level.dat.
 */
public final class WorldFolders {

	private WorldFolders() {}

	/**
	 * The folder of the world that holds its level.dat: getWorldFolder() or one of its parents, never above the
	 * world container. getWorldFolder() when none has a level.dat.
	 */
	public static File root(World world) {
		File folder = canonical(world.getWorldFolder());
		File container = canonical(Bukkit.getWorldContainer());

		for (File f = folder; f != null; f = f.getParentFile()) {
			if (new File(f, "level.dat").isFile()) {
				return f;
			}
			if (f.equals(container)) {
				break;
			}
		}

		return folder;
	}

	/**
	 * What deleting this world removes: its own world folder, or only its dimension folder when it is stored
	 * inside the folder of the main world (deleting the root would delete the main world).
	 */
	public static File toDelete(World world) {
		File root = root(world);
		World main = Bukkit.getWorlds().get(0);

		if (!world.equals(main) && root.equals(root(main))) {
			return canonical(world.getWorldFolder());
		}

		return root;
	}

	private static File canonical(File f) {
		try {
			return f.getCanonicalFile();
		} catch (IOException e) {
			return f.getAbsoluteFile();
		}
	}
}
