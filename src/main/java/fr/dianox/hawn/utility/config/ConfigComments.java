package fr.dianox.hawn.utility.config;

import fr.dianox.hawn.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A comment above each option of the configuration files, on 1.18.1+ (Bukkit writes the comments since then).
 * The comment of an option comes from config-comments.txt (the file, then all the files), or from its name
 * (Enable, World.Worlds, WorldGuard...). An option that already has a comment keeps it.
 */
public final class ConfigComments {

	private static Method setComments;
	private static Method getComments;
	private static final Map<String, String> ALL_FILES = new HashMap<>();
	private static final Map<String, Map<String, String>> PER_FILE = new HashMap<>();
	private static boolean loaded = false;

	private ConfigComments() {}

	private static synchronized boolean ready() {
		if (!loaded) {
			loaded = true;
			try {
				setComments = ConfigurationSection.class.getMethod("setComments", String.class, List.class);
				getComments = ConfigurationSection.class.getMethod("getComments", String.class);
			} catch (NoSuchMethodException e) {
				return false; // 1.16, 1.17: no comments
			}
			read();
		}
		return setComments != null;
	}

	// config-comments.txt: "path = comment" lines, under [file] for one file only
	private static void read() {
		InputStream in = Main.getInstance().getResource("config-comments.txt");
		if (in == null) return;

		try (BufferedReader r = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
			Map<String, String> target = ALL_FILES;
			String line;
			while ((line = r.readLine()) != null) {
				line = line.trim();
				if (line.isEmpty() || line.startsWith("#")) continue;
				if (line.startsWith("[") && line.endsWith("]")) {
					target = PER_FILE.computeIfAbsent(line.substring(1, line.length() - 1), k -> new HashMap<>());
					continue;
				}
				int eq = line.indexOf(" = ");
				if (eq > 0) {
					target.put(line.substring(0, eq).trim(), line.substring(eq + 3).trim());
				}
			}
		} catch (Exception e) {
			Main.getInstance().getLogger().warning("config-comments.txt could not be read: " + e);
		}
	}

	/**
	 * Adds the missing comments of a file.
	 * @param name     the file, relative to the Hawn folder
	 * @param config   the configuration that is saved
	 * @param defaults its default values: the options that get a comment
	 * @return true when a comment was added
	 */
	public static boolean comment(String name, YamlConfiguration config, YamlConfiguration defaults) {
		if (name.startsWith("Messages/") || !ready()) {
			return false;
		}

		boolean added = false;
		try {
			for (String path : defaults.getKeys(true)) {
				if (defaults.isConfigurationSection(path) || !config.contains(path)) continue;

				List<?> existing = (List<?>) getComments.invoke(config, path);
				if (existing != null && !existing.isEmpty()) continue;

				String comment = commentOf(name, path);
				if (comment == null) continue;

				setComments.invoke(config, path, Collections.singletonList(comment));
				added = true;
			}
		} catch (ReflectiveOperationException e) {
			return added;
		}
		return added;
	}

	static String commentOf(String file, String path) {
		Map<String, String> own = PER_FILE.get(file);
		if (own != null && own.containsKey(path)) return own.get(path);
		if (ALL_FILES.containsKey(path)) return ALL_FILES.get(path);
		return generic(path);
	}

	// From the name of the option
	private static String generic(String path) {
		String[] parts = path.split("\\.");
		String last = parts[parts.length - 1];
		String parent = parts.length > 1 ? parts[parts.length - 2] : "";

		if (path.endsWith("World.All_World")) return "true: in every world. false: only in the worlds of Worlds";
		if (path.endsWith("World.Worlds") || last.equals("Worlds")) return "The worlds where it applies";
		if (parent.equals("WorldGuard")) {
			if (last.equals("Enable")) return "true: also depends on the WorldGuard regions below (WorldGuard 7+)";
			if (last.equals("Method")) return "WHITELIST: only in these regions. BLACKLIST: everywhere but in these regions";
			if (last.equals("Regions")) return "The WorldGuard regions (the case doesn't matter)";
		}
		if (parent.equals("Block-Exception")) {
			if (last.equals("Method")) return "WHITELIST: only these blocks are concerned. BLACKLIST: all the blocks but these ones";
			if (last.equals("Materials")) return "The blocks (names of the Material list, see the wiki page Values)";
			if (last.equals("Armor_Stand")) return "true: the armor stands are concerned too";
		}
		if (path.contains(".PlayerInteract-Items-Blocks.Options.")) return "true: the players can't use it. false: they can";
		if (last.equals("Enable") || last.equals("enable") || last.equals("enabled") || last.equals("Enabled")) return "true: on. false: off";
		if (last.equals("Bypass")) return "true: the players with the bypass permission are not concerned";
		if (last.equals("DISABLE_THE_COMMAND_COMPLETELY")) return "true: the command is removed from the server (restart needed)";
		if (last.equals("Disable-Message")) return "true: a player who uses the disabled command is told so";
		if (last.equals("Keep-The-Option")) return "true: Hawn doesn't change Enable at startup, even if the plugin is missing or installed";
		if (last.endsWith("Main-Command-Is")) return "The main command (only to read)";
		if (last.equals("Aliases")) return "The other names of the command (restart needed)";
		if (last.equals("Messages") || last.equals("Message") || last.equals("messages") || last.equals("message")) return "One line per message. Colours, placeholders and actions work (see the wiki pages Messages and Actions)";
		if (last.equals("Lore") || last.equals("lore")) return "The lines under the name of the item";
		if (last.equals("Sound")) return "The sound (see the wiki page Values)";
		if (last.equals("Volume")) return "Volume of the sound (1 = normal, decimals work)";
		if (last.equals("Pitch")) return "Pitch of the sound (0.5 to 2, decimals work)";
		if (last.equals("Use_Permission") || last.equals("Use-Permission") || last.equals("Use-Permission-To-Get-Messages")) return "true: a permission is needed";
		if (last.equals("Permission") || last.equals("permission")) return "The permission";
		if (last.equals("FadeIn") || last.equals("Stay") || last.equals("FadeOut") || last.equals("Time-Stay")) return "In ticks (20 ticks = 1 second)";
		if (last.equals("Interval") || last.equals("interval") || last.endsWith("-ticks") || last.endsWith("-Ticks") || last.equals("Ticks")) return "In ticks (20 ticks = 1 second)";
		if (last.endsWith("Delay-Seconds") || last.equals("Duration-Second")) return "In seconds";
		if (last.equals("Material") || last.equals("material")) return "The item (names of the Material list, see the wiki page Values)";
		if (last.equals("Data-value")) return "Only for the old versions: not used since Minecraft 1.13";
		if (last.equals("Title") || last.equals("title")) return "The title";
		if (last.equals("SubTitle")) return "The subtitle";
		if (last.equals("Name") || last.equals("name")) return "The name";
		if (last.equals("Slot") || last.equals("slot")) return "The slot of the inventory (0 to 8: the hotbar)";
		if (last.equals("Amount") || last.equals("amount")) return "The number of items";
		if (last.equals("Skull-Name")) return "The player whose head is shown";
		if (last.equals("Command-List") || last.equals("Commands")) return "One action per line (see the wiki page Actions)";
		if (last.equals("Firework-List")) return "Fireworks of Cosmetics-Fun/Utility/Firework-List.yml";
		if (last.equals("Spawn") || last.equals("DefaultSpawn")) return "The name of a spawn (/setspawn)";
		if (last.equals("whitelist")) return "Names or UUIDs that can still join";
		if (last.equals("Kick-Message")) return "The message of the kick (one line per entry)";
		if (last.equals("Line-1")) return "First line of the MOTD";
		if (last.equals("Line-2")) return "Second line of the MOTD";
		if (last.equals("Random")) return "true: in a random order. false: in the order of the file";
		if (last.equals("Color")) return "Colour of the boss bar: BLUE, GREEN, PINK, PURPLE, RED, WHITE or YELLOW";
		if (last.equals("Style")) return "Style of the boss bar: SOLID, SEGMENTED_6, SEGMENTED_10, SEGMENTED_12 or SEGMENTED_20";
		if (last.equals("Progress")) return "Filling of the boss bar, from 0 to 1";
		if (last.equals("Amplifier")) return "Level of the effect: 0 = level I, 1 = level II...";
		if (last.equals("priority")) return "The highest priority is checked first";
		if (last.equals("command")) return "The command the players type";
		if (last.equals("no-permission-message-enable")) return "true: the players without the permission are told so";
		if (last.equals("text")) return "The lines, or the frames shown one after the other";
		if (last.equals("Shape")) return "What replaces the words";
		if (last.equals("Replace_With")) return "The words the players type";
		if (last.equals("Author")) return "The author of the book";
		if (last.equals("page")) return "The text of the page";
		if (last.equals("Lines-To-Clear")) return "The number of empty lines sent";
		if (parts.length > 2 && parts[parts.length - 3].startsWith("Firework")) return "An option of the firework (see the wiki page Lobby fun)";
		return null;
	}
}
