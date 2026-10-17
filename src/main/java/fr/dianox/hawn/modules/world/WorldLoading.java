package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.modules.world.generator.Generators;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.configs.messages.WorldManagerPanelConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

/**
 * Loads and unloads the worlds of the world list (Worlds.yml, World-List.&lt;world&gt;): at startup, from the menu (left
 * click on a world not loaded, Unload in its settings) and with /hw unload.
 */
public final class WorldLoading {

	private WorldLoading() {}

	private static YamlConfiguration cfg() {
		return ConfigWorldGeneral.getConfig();
	}

	/**
	 * Loads the world with what Worlds.yml says of it (environment, type, generator). Without an environment written,
	 * the one of its folder (DIM-1: nether, DIM1: end). Null when the server could not load it.
	 */
	public static World load(String worldname) {
		World loaded = Bukkit.getWorld(worldname);
		if (loaded != null) {
			return loaded;
		}

		String path = "World-List." + worldname;
		WorldFolders.freeDuplicateUid(worldname);

		Environment environment = cfg().isSet(path + ".Environment") ? environment(cfg().getString(path + ".Environment")) : guessEnvironment(worldname);
		WorldCreator creator = new WorldCreator(worldname).environment(environment);

		if (cfg().isSet(path + ".Generator")) {
			String generator = cfg().getString(path + ".Generator");
			if (generator.equalsIgnoreCase("hvg")) {
				creator.generator(Generators.voidGenerator());
			} else {
				creator.generator(generator);
			}
		} else if (cfg().isSet(path + ".Type")) {
			String type = cfg().getString(path + ".Type");
			if (type.equalsIgnoreCase("flat")) {
				creator.type(WorldType.FLAT);
			} else if (type.equalsIgnoreCase("amplified")) {
				creator.type(WorldType.AMPLIFIED);
			} else if (type.equalsIgnoreCase("large_biomes")) {
				creator.type(WorldType.LARGE_BIOMES);
			}
		}

		World world;
		try {
			world = Bukkit.getServer().createWorld(creator);
		} catch (Exception e) {
			Bukkit.getLogger().warning("[Hawn] The world " + worldname + " could not be loaded: " + e);
			return null;
		}

		if (world != null) {
			WorldFolders.fixSpawn(world);
		}
		return world;
	}

	private static Environment environment(String name) {
		if (name == null) return Environment.NORMAL;
		if (name.equalsIgnoreCase("the_end")) return Environment.THE_END;
		if (name.equalsIgnoreCase("nether")) return Environment.NETHER;
		return Environment.NORMAL;
	}

	// A nether keeps its blocks in DIM-1, an end in DIM1 (Bukkit layout)
	public static Environment guessEnvironment(String worldname) {
		File folder = new File(Bukkit.getWorldContainer(), worldname);
		if (new File(folder, "DIM-1").isDirectory()) return Environment.NETHER;
		if (new File(folder, "DIM1").isDirectory()) return Environment.THE_END;
		return Environment.NORMAL;
	}

	private static String environmentName(Environment environment) {
		return environment == Environment.NETHER ? "nether" : environment == Environment.THE_END ? "the_end" : "normal";
	}

	/**
	 * The menu: a world folder not loaded, loaded like /hw import, and loaded again at each start (Load: true).
	 */
	public static void loadFromMenu(Player p, String worldname) {
		if (!WorldFolders.validImportName(worldname)) {
			say(p, WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName"), worldname);
			return;
		}

		say(p, WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.Importing-A-World"), worldname);

		String path = "World-List." + worldname;
		World world = load(worldname);

		if (world == null) {
			say(p, WorldDeletion.message("Gui.Import.Failed", "%prefix% &cThe world &e%arg1% &ccould not be loaded, see the console"), worldname);
			GuiSystem.refreshWorldPages();
			return;
		}

		cfg().set(path + ".Load", true);
		cfg().set(path + ".Environment", environmentName(world.getEnvironment()));
		ConfigWorldGeneral.saveConfigFile();

		say(p, WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded"), worldname);
		GuiSystem.refreshWorldPages();
	}

	/**
	 * /hw unload and the menu: the players in the world go to the default world first. Until the next restart, as
	 * before (Load in Worlds.yml decides at startup).
	 *
	 * @return true when the world is unloaded
	 */
	public static boolean unload(CommandSender sender, String worldname) {
		World world = Bukkit.getWorld(worldname);

		if (world == null) {
			say(sender, WorldManagerPanelConfig.getConfig().getStringList("Command.Unload.Error"), worldname);
			return false;
		}
		if (WorldDeletion.isProtected(world.getName())) {
			// The main world, its nether and its end: the same refusals as /hw delete
			say(sender, WorldDeletion.message("Error.Unload-Protected", "%prefix% &cThe world &e%arg1% &ccan't be unloaded: the server needs it"), worldname);
			return false;
		}
		if (DefaultWorld.is(world.getName())) {
			say(sender, WorldManagerPanelConfig.getConfig().getStringList("Error.Default-World"), worldname);
			return false;
		}

		World target = Bukkit.getWorld(DefaultWorld.get());
		if (target == null || target.equals(world)) {
			target = Bukkit.getWorlds().get(0);
		}
		for (Player player : world.getPlayers()) {
			player.teleport(target.getSpawnLocation());
			for (String msg : WorldDeletion.message("Gui.Unload-Moved", "%prefix% &7The world &e%arg1% &7was unloaded: you are now in &e%arg2%")) {
				ConfigEventUtils.ExecuteEvent(player, msg.replace("%arg1%", ConfigEventUtils.noAction(worldname)).replace("%arg2%", target.getName()), "", "", false);
			}
		}

		if (!Bukkit.getServer().unloadWorld(world, true)) {
			say(sender, WorldDeletion.message("Command.Unload.Failed", "%prefix% &cThe world &e%arg1% &ccould not be unloaded: no player must be in it"), worldname);
			return false;
		}

		say(sender, WorldManagerPanelConfig.getConfig().getStringList("Gui.Unload"), worldname);
		GuiSystem.refreshWorldPages();
		return true;
	}

	private static void say(CommandSender sender, java.util.List<String> messages, String worldname) {
		for (String msg : messages) {
			msg = msg.replace("%arg1%", ConfigEventUtils.noAction(worldname));
			if (sender instanceof Player) {
				ConfigEventUtils.ExecuteEvent((Player) sender, msg, "", "", false);
			} else {
				fr.dianox.hawn.utility.MessageUtils.ConsoleMessages(msg);
			}
		}
	}
}
