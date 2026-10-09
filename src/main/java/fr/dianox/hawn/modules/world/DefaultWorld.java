package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.messages.WorldManagerPanelConfig;
import fr.dianox.hawn.utility.load.Reload;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The default world of Hawn (Plugin.Default-World in general.yml): the world written in the "Worlds" lists of the files.
 * Changing it writes the new world in place of the old one in these lists, and renames the sections named after it.
 */
public class DefaultWorld {

	public static final String PATH = "Plugin.Default-World";

	// The sections whose keys are world names (messages per world, void TP per world, commands when entering a world)
	private static final List<String> PER_WORLD = Arrays.asList("Worlds", "World-List", "When-Enter-in-The-World");

	// Not options: the loaded worlds, the spawns, the warps and the player data
	private static final List<String> SKIPPED = Arrays.asList("World-List.yml", "spawn.yml", "warplist.yml");

	public static String get() {
		String name = ConfigGeneral.getConfig().getString(PATH);
		return name == null || name.trim().isEmpty() ? mainWorld() : name;
	}

	public static boolean is(String world) {
		return world != null && get().equalsIgnoreCase(world);
	}

	public static String mainWorld() {
		return Bukkit.getWorlds().isEmpty() ? "world" : Bukkit.getWorlds().get(0).getName();
	}

	/**
	 * First start with the option: the world the most written in the "Worlds" lists (the one chosen in the setup), else the main world.
	 */
	public static String guess(File dataFolder) {
		Map<String, Integer> count = new HashMap<>();
		for (File f : files(dataFolder)) {
			YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
			for (String key : c.getKeys(true)) {
				if (isWorldList(c, key)) {
					for (String w : c.getStringList(key)) {
						count.merge(w, 1, Integer::sum);
					}
				}
			}
		}

		// The worlds of Hawn are loaded after its files (and Paper 26.x keeps them in world/dimensions)
		YamlConfiguration hawnWorlds = YamlConfiguration.loadConfiguration(new File(dataFolder, "World-List.yml"));

		// The main world wins a tie: the default files list it with its nether
		String best = mainWorld();
		int max = count.getOrDefault(best, 0);
		for (Map.Entry<String, Integer> e : count.entrySet()) {
			if (e.getValue() > max && (exists(e.getKey()) || hawnWorlds.isConfigurationSection("World-List." + e.getKey()))) {
				best = e.getKey();
				max = e.getValue();
			}
		}
		return best;
	}

	public static boolean exists(String world) {
		return WorldFolders.exists(world);
	}

	/**
	 * Makes "to" the default world and writes it in place of the old one (and of the "others") in every file.
	 * The files must be read again afterwards (see Reload).
	 */
	public static Result change(String to, String... others) {
		Result r = new Result(get(), to);

		List<String> replaced = new ArrayList<>();
		for (String name : concat(r.from, others)) {
			if (!name.equalsIgnoreCase(to) && replaced.stream().noneMatch(name::equalsIgnoreCase)) replaced.add(name);
		}

		ConfigGeneral.getConfig().set(PATH, to);
		ConfigGeneral.saveConfigFile();
		if (replaced.isEmpty()) return r;

		for (File f : files(Main.getInstance().getDataFolder())) {
			String name = Main.getInstance().getDataFolder().toPath().relativize(f.toPath()).toString().replace(File.separatorChar, '/');

			YamlConfiguration c = new YamlConfiguration();
			try {
				c.load(f);
			} catch (Exception e) {
				r.yamlErrors.add(name); // left alone, like the missing options
				continue;
			}

			int n = 0;
			for (String from : replaced) {
				n += replace(c, from, to, name, r.conflicts);
			}
			if (n > 0) {
				try {
					c.save(f);
					r.files++;
					r.values += n;
				} catch (Exception e) {
					r.yamlErrors.add(name);
				}
			}
		}
		return r;
	}

	private static List<String> concat(String first, String... others) {
		List<String> list = new ArrayList<>();
		list.add(first);
		list.addAll(Arrays.asList(others));
		return list;
	}

	/**
	 * The world manager and /hw default: changes the default world, reads the files again and says what changed.
	 */
	public static void apply(CommandSender sender, String world) {
		Result r = change(world);

		for (String error : r.yamlErrors) {
			say(sender, "Gui.Default-World.Yaml-Error", error);
		}
		for (String conflict : r.conflicts) {
			say(sender, "Gui.Default-World.Conflict", conflict, r.to, r.from);
		}

		Reload.reloadconfig();

		say(sender, "Gui.Default-World.Changed", r.to, r.from, String.valueOf(r.values), String.valueOf(r.files));

		// The spawn of the players on join stays where it is
		String spawn = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
		String spawnWorld = spawn == null ? null : ConfigSpawn.getConfig().getString("Coordinated." + spawn + ".World");
		if (spawnWorld != null && !spawnWorld.equalsIgnoreCase(r.to)) {
			say(sender, "Gui.Default-World.Spawn-Elsewhere", spawn, spawnWorld);
		}
	}

	/**
	 * A message of WorldManager.yml, %arg1%, %arg2%... replaced, to a player or to the console.
	 */
	public static void say(CommandSender sender, String key, String... args) {
		for (String msg : WorldManagerPanelConfig.getConfig().getStringList(key)) {
			for (int i = 0; i < args.length; i++) {
				msg = msg.replace("%arg" + (i + 1) + "%", ConfigEventUtils.noAction(args[i]));
			}
			if (sender instanceof Player) {
				ConfigEventUtils.ExecuteEvent((Player) sender, msg, key, "DefaultWorld", false);
			} else {
				MessageUtils.ConsoleMessages(msg);
			}
		}
	}

	private static int replace(YamlConfiguration c, String from, String to, String file, List<String> conflicts) {
		int n = 0;

		for (String key : new ArrayList<>(c.getKeys(true))) {
			if (!isWorldList(c, key)) continue;

			List<String> list = c.getStringList(key);
			if (list.stream().noneMatch(from::equalsIgnoreCase)) continue;

			List<String> changed = new ArrayList<>();
			for (String w : list) {
				String now = w.equalsIgnoreCase(from) ? to : w;
				if (changed.stream().noneMatch(now::equalsIgnoreCase)) changed.add(now);
			}
			c.set(key, changed);
			n++;
		}

		for (String key : new ArrayList<>(c.getKeys(true))) {
			int dot = key.lastIndexOf('.');
			if (dot < 0 || !key.substring(dot + 1).equalsIgnoreCase(from)) continue;

			String parent = key.substring(0, dot);
			if (!PER_WORLD.contains(parent.substring(parent.lastIndexOf('.') + 1))) continue;

			String target = parent + "." + to;
			if (c.contains(target)) {
				conflicts.add(file + ": " + parent);
				continue;
			}

			Object value = c.get(key);
			if (value instanceof ConfigurationSection) {
				copy((ConfigurationSection) value, c, target);
			} else {
				c.set(target, value);
			}
			c.set(key, null);
			n++;
		}
		return n;
	}

	private static void copy(ConfigurationSection from, YamlConfiguration c, String to) {
		c.createSection(to);
		for (String key : from.getKeys(false)) {
			Object value = from.get(key);
			if (value instanceof ConfigurationSection) {
				copy((ConfigurationSection) value, c, to + "." + key);
			} else {
				c.set(to + "." + key, value);
			}
		}
	}

	private static boolean isWorldList(YamlConfiguration c, String key) {
		return (key.equals("Worlds") || key.endsWith(".Worlds")) && c.isList(key);
	}

	private static List<File> files(File folder) {
		List<File> list = new ArrayList<>();
		File[] files = folder.listFiles();
		if (files == null) return list;

		for (File f : files) {
			if (f.isDirectory()) {
				if (!f.getName().equals("StockageInfo")) list.addAll(files(f));
			} else if (f.getName().endsWith(".yml") && !SKIPPED.contains(f.getName())) {
				list.add(f);
			}
		}
		return list;
	}

	public static class Result {
		public final String from;
		public final String to;
		public int files = 0;
		public int values = 0;
		public final List<String> conflicts = new ArrayList<>();
		public final List<String> yamlErrors = new ArrayList<>();

		Result(String from, String to) {
			this.from = from;
			this.to = to;
		}
	}
}
