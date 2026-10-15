package fr.dianox.hawn.modules.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

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
	 * A world of this name: loaded, a world folder of the world container, or (Paper 26.x) a dimension of the main world.
	 */
	public static boolean exists(String name) {
		for (World w : Bukkit.getWorlds()) {
			if (w.getName().equalsIgnoreCase(name)) return true;
		}
		return GuiSystem.checkIfIsWorld(new File(Bukkit.getWorldContainer(), name)) || dimension(name) != null;
	}

	/**
	 * A name for a new world: letters, numbers, _ and -, 64 characters at most. Nothing that could leave the folder
	 * of the worlds (/, .., \) nor break the menus (§, spaces).
	 */
	public static boolean validName(String name) {
		return name != null && name.matches("[A-Za-z0-9_-]{1,64}");
	}

	/**
	 * A world folder to import: the same, with dots too (world.old), but never at the start.
	 */
	public static boolean validImportName(String name) {
		return name != null && name.matches("[A-Za-z0-9_-][A-Za-z0-9_.-]{0,63}") && !name.contains("..");
	}

	/**
	 * Paper 26.x keeps the created worlds in the folder of the main world: world/dimensions/minecraft/<name>. Null when there is none.
	 */
	public static File dimension(String name) {
		if (Bukkit.getWorlds().isEmpty()) return null;

		File dimensions = new File(root(Bukkit.getWorlds().get(0)), "dimensions" + File.separator + "minecraft");
		for (String folder : new String[] {name, name.toLowerCase(java.util.Locale.ROOT)}) {
			File f = new File(dimensions, folder);
			if (f.isDirectory()) return f;
		}
		return null;
	}

	/**
	 * A world folder copied from a loaded world keeps its uid.dat: Spigot then refuses to load it ("duplicate of
	 * another world") and keeps its folder locked until the next restart. Removes that uid.dat (the server writes a
	 * new one) and gives the name of the loaded world it came from, null when the folder has no such uid.dat.
	 */
	public static String freeDuplicateUid(String name) {
		File uid = new File(new File(Bukkit.getWorldContainer(), name), "uid.dat");
		if (!uid.isFile() || uid.length() < 16) return null;

		UUID id;
		try (DataInputStream in = new DataInputStream(new FileInputStream(uid))) {
			id = new UUID(in.readLong(), in.readLong());
		} catch (IOException e) {
			return null;
		}

		for (World w : Bukkit.getWorlds()) {
			if (w.getUID().equals(id) && !w.getName().equalsIgnoreCase(name)) {
				if (!uid.delete()) return null;
				Bukkit.getLogger().info("[Hawn] The world " + name + " is a copy of the world " + w.getName() + ": its uid.dat is removed so that it can be loaded");
				return w.getName();
			}
		}
		return null;
	}

	/**
	 * Spigot 26.x keeps the dimension of the spawn in level.dat, and a world converted from an older version gets
	 * minecraft:overworld there: its getSpawnLocation() is then in the main world, and /hw tp sends the players to
	 * the main world. Puts the spawn back in the world itself, at the same place. Never for the main world, its
	 * nether and its end.
	 */
	public static void fixSpawn(World world) {
		if (world == null || WorldDeletion.isProtected(world.getName())) return;

		try {
			Location spawn = world.getSpawnLocation();
			if (spawn.getWorld() != null && !spawn.getWorld().equals(world)) {
				world.setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
				Bukkit.getLogger().info("[Hawn] The spawn of the world " + world.getName() + " was in the world " + spawn.getWorld().getName() + ": it is now in " + world.getName());
			}
		} catch (RuntimeException e) {
			// The spawn points to a dimension that is not loaded
			Bukkit.getLogger().warning("[Hawn] The spawn of the world " + world.getName() + " could not be read: " + e);
		}
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
