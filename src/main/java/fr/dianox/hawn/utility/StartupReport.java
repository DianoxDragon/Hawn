package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.world.DefaultWorld;
import fr.dianox.hawn.event.PlatformEvents;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.modules.chat.ChatFormat;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.utility.config.ConfigDefaults;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.server.WarnTPS;
import fr.dianox.hawn.modules.ranks.Ranks;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.CustomCommandConfig;
import fr.dianox.hawn.utility.config.configs.ScoreboardMainConfig;
import fr.dianox.hawn.utility.config.configs.WarpListConfig;
import fr.dianox.hawn.utility.config.configs.commands.HawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import fr.dianox.hawn.utility.config.configs.events.ConfigGProtection;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.events.PlayerEventsConfig;
import fr.dianox.hawn.utility.config.configs.events.ProtectionPlayerConfig;
import fr.dianox.hawn.utility.config.configs.events.VoidTPConfig;
import fr.dianox.hawn.utility.config.configs.events.WorldEventConfig;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The end of the startup banner: on what Hawn runs, what it uses and loaded, what is on,
 * and the options that contradict each other.
 */
public final class StartupReport {

	private static final String LINE = ChatColor.BLUE + "| ";
	private static final String WARN = ChatColor.YELLOW + "| " + ChatColor.GOLD;

	private StartupReport() {}

	private static void say(String text) {
		Bukkit.getConsoleSender().sendMessage(text);
	}

	/**
	 * /hawn reload: the options that contradict each other, with the files read again.
	 */
	public static void printWarnings() {
		List<String> warnings = new ArrayList<>();
		checks(warnings);
		for (String w : warnings) {
			say(WARN + w);
		}
	}

	public static void print(long startNanos) {
		Main hawn = Main.getInstance();

		// Server, hooks, storage
		say(LINE + ChatColor.YELLOW + "Server: " + ChatColor.WHITE + hawn.getVersionClass().getVersionsS() + ChatColor.GRAY + " - "
				+ (PlatformEvents.isPaperChat() || PlatformEvents.isPaperLogin()
					? "Paper events (chat " + yes(PlatformEvents.isPaperChat()) + ", join " + yes(PlatformEvents.isPaperLogin()) + ")"
					: "Bukkit events")
				+ " - MiniMessage " + yes(PlatformEvents.isMiniMessage()));
		say(LINE + ChatColor.YELLOW + "Hooks: " + hook("PlaceholderAPI", HooksManager.papi())
				+ (HooksManager.expansionRegistered() ? ChatColor.GRAY + " (gives %hawn_...%)" : "") + ChatColor.GRAY + " - "
				+ hook("LuckPerms", HooksManager.luckPerms()) + ChatColor.GRAY + " - " + hook("WorldGuard", HooksManager.worldGuard())
				// Nexo: its items in the join items and the menus (since 1.5), only shown when it is installed
				+ (Bukkit.getPluginManager().getPlugin("Nexo") != null ? ChatColor.GRAY + " - " + hook("Nexo", Bukkit.getPluginManager().isPluginEnabled("Nexo")) : ""));
		boolean mysql = ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Enable");
		boolean yaml = hawn.getSql() == null || hawn.getSql().useyamllistplayer;
		say(LINE + ChatColor.YELLOW + "Storage: " + ChatColor.WHITE + (yaml ? "YAML" : "MySQL")
				+ (mysql && yaml ? ChatColor.RED + " (MySQL could not connect, see above)" : "")
				+ ChatColor.GRAY + " - " + ChatColor.YELLOW + "Language: " + ChatColor.WHITE + Main.LanguageType
				+ ChatColor.GRAY + " - " + ChatColor.YELLOW + "Default world: " + ChatColor.WHITE + DefaultWorld.get());
		say(LINE);

		// What is loaded
		say(LINE + ChatColor.YELLOW + "Loaded: " + ChatColor.WHITE + String.join(ChatColor.GRAY + " - " + ChatColor.WHITE, loaded()));
		StringBuilder files = new StringBuilder(ChatColor.WHITE + "" + ConfigDefaults.files() + " files");
		if (ConfigDefaults.optionsAdded() > 0) files.append(ChatColor.GRAY).append(" - ").append(ChatColor.WHITE).append(ConfigDefaults.optionsAdded()).append(" missing option(s) added (listed above)");
		if (ConfigDefaults.commented() > 0) files.append(ChatColor.GRAY).append(" - ").append(ChatColor.WHITE).append("comments written in ").append(ConfigDefaults.commented()).append(" file(s)");
		if (!ConfigDefaults.yamlErrors().isEmpty()) files.append(ChatColor.GRAY).append(" - ").append(ChatColor.RED).append(ConfigDefaults.yamlErrors().size())
				.append(" with a YAML error, left as they are: ").append(String.join(", ", ConfigDefaults.yamlErrors()));
		say(LINE + ChatColor.YELLOW + "Files: " + files);
		say(LINE);

		// What is on, then what contradicts
		List<String> warnings = new ArrayList<>();
		modes(warnings);
		checks(warnings);
		for (String w : warnings) {
			say(WARN + w);
		}
		if (!warnings.isEmpty()) say(ChatColor.YELLOW + "| ");

		say(LINE + ChatColor.DARK_RED + "License:" + ChatColor.RESET + " GNU GPL v3" + ChatColor.GRAY + " - " + ChatColor.YELLOW + "Support: " + ChatColor.WHITE + "https://discord.gg/yeUg5mbJuz");
		say(LINE);
		say(ChatColor.BLUE + "| ------------------------------------");
		say(LINE);
		say(LINE + ChatColor.GREEN + "Hawn ready in " + (System.nanoTime() - startNanos) / 1000000 + " ms !");
		say(LINE);
	}

	private static String yes(boolean b) {
		return b ? "yes" : "no";
	}

	// yes: used. off: installed, but Enable is false. no: not installed
	private static String hook(String plugin, boolean used) {
		if (used) return ChatColor.WHITE + plugin + " " + ChatColor.GREEN + "yes";
		if (Bukkit.getPluginManager().getPlugin(plugin) != null) return ChatColor.WHITE + plugin + " " + ChatColor.GOLD + "off";
		return ChatColor.WHITE + plugin + " " + ChatColor.GRAY + "no";
	}

	private static List<String> loaded() {
		List<String> parts = new ArrayList<>();
		Main hawn = Main.getInstance();

		int scoreboards = ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable") && hawn.getScoreManager() != null ? hawn.getScoreManager().scoreacess.size() : 0;
		parts.add(count(scoreboards, "scoreboard"));
		int tablists = hawn.getTabManager() == null ? 0 : hawn.getTabManager().count();
		parts.add(!TablistConfig.getConfig().getBoolean("Tablist.enable") ? "tab list off" : tablists == 0 ? "default tab list" : count(tablists, "tab list") + " + default");
		parts.add(count(keys(ConfigSpawn.getConfig().getConfigurationSection("Coordinated")), "spawn"));
		parts.add(count(keys(WarpListConfig.getConfig().getConfigurationSection("Coordinated")), "warp"));
		parts.add(count(keys(ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items")), "join item"));
		parts.add(count(keys(CustomCommandConfig.getConfig().getConfigurationSection("commands")), "custom command"));
		parts.add(count(ChatEmojisLoad.emojislist.size(), "emoji"));
		parts.add(count(fr.dianox.hawn.modules.menus.Menus.all().size(), "menu"));
		parts.add(!Ranks.enabled() ? "ranks off" : count(Ranks.count(), "rank") + (Ranks.tablist() ? "" : " (tab list: " + Ranks.tabPlugin() + ")"));
		return parts;
	}

	private static int keys(ConfigurationSection section) {
		return section == null ? 0 : section.getKeys(false).size();
	}

	private static String count(int n, String what) {
		return n + " " + what + (n > 1 ? "s" : "");
	}

	/* ------------------------------------------------------------------ what is on */

	private static void modes(List<String> warnings) {
		if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable")) {
			warnings.add("Maintenance is ON: only its whitelist and hawn.maintenance.bypass can join (/hawn maintenance to turn it off)");
		}
		if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
			warnings.add("Urgent mode is ON: the operators are removed and the commands locked until the console turns it off (/hawn urgent)");
		}
		if (OnChatConfig.getConfig().getBoolean("Per-World-Chat.Enable")) {
			warnings.add("Chat per group of worlds is ON: the players only see the messages of their group (Events/Chat.yml)");
		}

		String format = OnChatConfig.getConfig().getString("Chat-Format.Enable", "AUTO");
		if (ChatFormat.mode() == ChatFormat.Mode.OFF && !"false".equalsIgnoreCase(format)) {
			warnings.add("Chat-Format is AUTO and " + ChatFormat.chatPlugin() + " is installed: the chat format is left to it (true to use the one of Hawn)");
		} else if (ChatFormat.mode() == ChatFormat.Mode.ON && ChatFormat.chatPlugin() != null) {
			warnings.add("Chat-Format is true and " + ChatFormat.chatPlugin() + " is installed: the format of Hawn is applied over it");
		}
	}

	/* ------------------------------------------------------------------ contradictions */

	private static void checks(List<String> warnings) {
		// The default world, written in place of the old one in the files when it changes
		if (!DefaultWorld.exists(DefaultWorld.get())) {
			warnings.add("The default world '" + DefaultWorld.get() + "' doesn't exist (general.yml, Plugin.Default-World): choose another one in the world manager (/hw, right click on a world)");
		}

		// Fly and double jump
		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable") && OnJoinConfig.getConfig().getBoolean("Fly.Enable")) {
			warnings.add("The players can both fly and double jump (Fly on join + DoubleJump): the double jump can take the place of the fly");
		}

		// Lightning on join
		if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Enable") && WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.Disable")) {
			warnings.add("The lightning strike on join is on, but the lightnings are disabled (World.Weather.Disable.LightningStrike): it will not show");
		}

		// Always day and always night in the same world
		if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Day.Enable") && WorldEventConfig.getConfig().getBoolean("World.Time.Always-Night.Enable")) {
			List<String> both = commonWorlds("World.Time.Always-Day.World", "World.Time.Always-Night.World");
			if (!both.isEmpty()) {
				warnings.add("Always-Day and Always-Night are both on in " + String.join(", ", both) + " (Events/WorldEvent.yml): the time will jump between day and night");
			}
		}

		spawns(warnings);
		chat(warnings);
		files(warnings);
		tps(warnings);
		drops(warnings);
		ranks(warnings);

		worldGuard(warnings);

		// A hook kept on without its plugin
		hookKept(warnings, "PlaceholderAPI");
	}

	private static List<String> commonWorlds(String a, String b) {
		YamlConfiguration cfg = WorldEventConfig.getConfig();
		List<String> both = new ArrayList<>();
		if (cfg.getBoolean(a + ".All_World")) {
			both.addAll(cfg.getBoolean(b + ".All_World") ? java.util.Collections.singletonList("every world") : cfg.getStringList(b + ".Worlds"));
		} else if (cfg.getBoolean(b + ".All_World")) {
			both.addAll(cfg.getStringList(a + ".Worlds"));
		} else {
			for (String w : cfg.getStringList(a + ".Worlds")) {
				for (String other : cfg.getStringList(b + ".Worlds")) {
					if (w.equalsIgnoreCase(other)) both.add(w);
				}
			}
		}
		return both;
	}

	// The default value of the files, or nothing
	private static boolean notSet(String spawn) {
		return spawn == null || spawn.trim().isEmpty() || spawn.equalsIgnoreCase("CHANGE ME");
	}

	private static boolean spawnExists(String spawn) {
		return spawn != null && ConfigSpawn.getConfig().isSet("Coordinated." + spawn);
	}

	private static void spawns(List<String> warnings) {
		YamlConfiguration join = OnJoinConfig.getConfig();
		String main = join.getString("Spawn.DefaultSpawn");
		boolean group = join.getBoolean("Spawn.Spawn-Group.Enable");

		if (join.getBoolean("Event.OnJoin.Tp-To-Spawn") && !group && !spawnExists(main)) {
			warnings.add(notSet(main)
					? "No default spawn yet: the players are not teleported on join. Create one with /setspawn <name>, then set Spawn.DefaultSpawn (Events/OnJoin.yml)"
					: "The players are teleported to the spawn on join, but the default spawn '" + main + "' doesn't exist (Events/OnJoin.yml, Spawn.DefaultSpawn): create it with /setspawn " + main);
		}

		if (group) {
			List<String> missing = new ArrayList<>();
			int existing = 0;
			for (String s : join.getStringList("Spawn.Spawn-Group.Spawns")) {
				if (spawnExists(s)) existing++; else missing.add(s);
			}
			if (!missing.isEmpty()) {
				warnings.add("Spawn group: " + String.join(", ", missing) + " don't exist (Events/OnJoin.yml, Spawn.Spawn-Group.Spawns)");
			}
			if (existing < 2) {
				warnings.add("Spawn group on with " + existing + " existing spawn: there is nothing to spread the players between");
			}
		}

		// Spawns whose world was deleted or renamed: nobody can be teleported there
		java.util.Set<String> withoutWorld = SpawnUtils.spawnsWithoutWorld();
		if (!withoutWorld.isEmpty()) {
			warnings.add("The world of " + (withoutWorld.size() == 1 ? "the spawn " : "the spawns ") + String.join(", ", withoutWorld)
					+ " doesn't exist: nobody can be teleported there. Create the world again, or delete the spawn with /delspawn");
		}

		// Void TP: a spawn under the height of the void TP sends the players back into the void, again and again
		YamlConfiguration voidtp = VoidTPConfig.getConfig();
		if (voidtp.getBoolean("VoidTP.Enable")) {
			String spawn = voidtp.getBoolean("VoidTP.Custom-Spawn.Enable") ? voidtp.getString("VoidTP.Custom-Spawn.Spawn") : main;
			if (!spawnExists(spawn)) {
				warnings.add(notSet(spawn)
						? "Void TP is on, but no spawn is set yet: the players falling in the void are not teleported"
						: "Void TP is on, but its spawn '" + spawn + "' doesn't exist: the players falling in the void are not teleported");
			} else if (ConfigSpawn.getConfig().getDouble("Coordinated." + spawn + ".Y") <= voidtp.getDouble("VoidTP.Options.TP-y")) {
				warnings.add("Void TP: the spawn '" + spawn + "' is at or below TP-y (" + voidtp.getInt("VoidTP.Options.TP-y")
						+ "): the players would be teleported into the void again and again");
			}
		}
	}

	private static void chat(List<String> warnings) {
		YamlConfiguration chat = OnChatConfig.getConfig();

		if (ChatFormat.mode() != ChatFormat.Mode.OFF && !chat.getString("Chat-Format.Format", "").contains("%message%")) {
			warnings.add("Chat-Format.Format has no %message%: the chat format of Hawn is not used");
		}

		if (chat.getBoolean("Anti-Swear.Enable") && !chat.getBoolean("Anti-Swear.Replace-Message.Enable") && !chat.getBoolean("Anti-Swear.Notify-Staff")) {
			warnings.add("Anti-Swear is on, but it neither replaces the words nor warns the staff: it does nothing");
		}

		if (chat.getBoolean("Per-World-Chat.Enable")) {
			ConfigurationSection groups = chat.getConfigurationSection("Per-World-Chat.Groups");
			if (groups == null || groups.getKeys(false).isEmpty()) {
				warnings.add("Chat per group of worlds is on, but there is no group: every world talks together");
			} else {
				Map<String, String> seen = new HashMap<>();
				for (String g : groups.getKeys(false)) {
					for (String w : groups.getStringList(g + ".Worlds")) {
						String before = seen.put(w.toLowerCase(), g);
						if (before != null) {
							warnings.add("The world " + w + " is in two chat groups (" + before + " and " + g + "): only " + before + " is used");
						}
					}
				}
			}
		}
	}

	private static void files(List<String> warnings) {
		// Scoreboards on without any file
		if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
			File[] boards = new File(Main.getInstance().getDataFolder(), "Scoreboard").listFiles((d, n) -> n.endsWith(".yml"));
			if (boards == null || boards.length == 0) {
				warnings.add("Scoreboard.Enable is true, but the Scoreboard folder has no file: no scoreboard is shown");
			}
		}

		// Tab list files that can't show
		if (!TablistConfig.getConfig().getBoolean("Tablist.enable")) {
			File[] lists = new File(Main.getInstance().getDataFolder(), "Tablist").listFiles((d, n) -> n.endsWith(".yml") && !n.equals("Tablist.yml"));
			int on = 0;
			if (lists != null) {
				for (File f : lists) {
					if (YamlConfiguration.loadConfiguration(f).getBoolean("enable", true)) on++;
				}
			}
			if (on > 0) {
				warnings.add("Tablist.enable is false: " + (on > 1 ? "the " + on + " tab list files" : "the tab list file") + " turned on in the Tablist folder "
						+ (on > 1 ? "are" : "is") + " not used");
			}
		}
	}

	// The worlds of an option: null for every world (All_World)
	private static List<String> worldsOf(YamlConfiguration cfg, String path) {
		return cfg.getBoolean(path + ".All_World") ? null : cfg.getStringList(path + ".Worlds");
	}

	// The worlds in both lists (null = every world), never null
	private static List<String> common(List<String> a, List<String> b) {
		if (a == null && b == null) return java.util.Collections.singletonList("every world");
		if (a == null) return b;
		if (b == null) return a;
		List<String> both = new ArrayList<>();
		for (String w : a) {
			for (String other : b) {
				if (w.equalsIgnoreCase(other)) both.add(w);
			}
		}
		return both;
	}

	/**
	 * Two options block the drops: the protection Items.Drop.Disable and Block-Drop of the join items. One can undo what the other is set to.
	 */
	private static void drops(List<String> warnings) {
		YamlConfiguration cji = ConfigCJIGeneral.getConfig();
		if (!cji.getBoolean("Custom-Join-Item.Enable")) {
			return;
		}

		YamlConfiguration events = PlayerEventsConfig.getConfig();
		String mode = cji.getString("Custom-Join-Item.General-Option.Block-Drop", "ALL").toUpperCase();
		List<String> both = common(worldsOf(cji, "Custom-Join-Item.General-Option.World"), worldsOf(events, "Items.Drop.World"));

		if (events.getBoolean("Items.Drop.Disable")) {
			if ((mode.equals("JOIN-ITEMS") || mode.equals("NONE")) && !both.isEmpty()) {
				warnings.add("Block-Drop is " + mode + " (CustomJoinItem/General.yml), but Items.Drop.Disable (Events/PlayerEvents.yml) still blocks every drop in "
						+ String.join(", ", both) + ": set Items.Drop.Disable to false, or remove these worlds from its list");
			}
		} else if (mode.equals("ALL")) {
			List<String> cjiWorlds = worldsOf(cji, "Custom-Join-Item.General-Option.World");
			if (cjiWorlds != null && cjiWorlds.isEmpty()) return;
			warnings.add("Items.Drop.Disable is false (Events/PlayerEvents.yml), but the join items still block every drop in "
					+ (cjiWorlds == null ? "every world" : String.join(", ", cjiWorlds))
					+ " (Block-Drop: ALL in CustomJoinItem/General.yml): JOIN-ITEMS only blocks the join items, NONE nothing");
		}
	}

	private static void ranks(List<String> warnings) {
		if (!Ranks.enabled()) {
			return;
		}
		if (!Ranks.tablist() && Ranks.tabPlugin() != null) {
			warnings.add("Ranks: " + Ranks.tabPlugin() + " is installed, the tab list and the heads are left to it (Ranks.yml, Enable: true to show the ranks of Hawn there); %rank_prefix% still works");
		}
		List<String> wrong = Ranks.unknownByHand();
		if (!wrong.isEmpty()) {
			warnings.add("Ranks.Players: " + String.join(", ", wrong) + " (Ranks.yml): no such rank turned on, the permissions decide");
		}
		List<String> colours = Ranks.wrongColours();
		if (!colours.isEmpty()) {
			warnings.add("Ranks.List: " + String.join(", ", colours) + " (Ranks.yml): Name-Colour must be one of the 16 colours (RED, GOLD, YELLOW, GREEN, AQUA, BLUE, GRAY, WHITE...), no colour is used");
		}
		String def = fr.dianox.hawn.utility.config.configs.RanksConfig.getConfig().getString("Ranks.Default-Rank", "");
		if (def != null && !def.isEmpty() && !fr.dianox.hawn.utility.config.configs.RanksConfig.getConfig().isSet("Ranks.List." + def)) {
			warnings.add("Ranks.Default-Rank is " + def + ", but there is no such rank in Ranks.List (Ranks.yml)");
		}
	}

	private static void tps(List<String> warnings) {
		YamlConfiguration general = ConfigGeneral.getConfig();
		if (!general.getBoolean("Plugin.Tps.Warn-system")) {
			if (general.getBoolean("Plugin.Tps.Emergency-Save.Enable")) {
				warnings.add("Plugin.Tps.Emergency-Save is on, but Plugin.Tps.Warn-system is off (general.yml): the emergency save never runs");
			}
			return;
		}
		if (WarnTPS.criticalAt() >= WarnTPS.warningAt()) {
			warnings.add("Plugin.Tps.Critical-At (" + WarnTPS.criticalAt() + ") is not below Plugin.Tps.Warning-At (" + WarnTPS.warningAt()
					+ ") in general.yml: only the critical message is sent");
		}
	}

	/**
	 * 94: a protection limited to WorldGuard regions applies everywhere when the WorldGuard hook is not used (as always):
	 * said here, the behaviour stays the same.
	 */
	private static void worldGuard(List<String> warnings) {
		if (HooksManager.worldGuard()) {
			return;
		}

		List<String> options = new ArrayList<>();
		Map<String, YamlConfiguration> files = new java.util.LinkedHashMap<>();
		files.put("ProtectionWorld.yml", ConfigGProtection.getConfig());
		files.put("ProtectionPlayer.yml", ProtectionPlayerConfig.getConfig());
		files.put("PlayerEvents.yml", PlayerEventsConfig.getConfig());
		files.put("WorldEvent.yml", WorldEventConfig.getConfig());

		for (Map.Entry<String, YamlConfiguration> file : files.entrySet()) {
			for (String key : file.getValue().getKeys(true)) {
				if (!key.endsWith(".WorldGuard.Enable") || !file.getValue().getBoolean(key)) continue;
				// Only the features turned on
				String feature = key.substring(0, key.length() - ".WorldGuard.Enable".length());
				if (file.getValue().isBoolean(feature + ".Enable") && !file.getValue().getBoolean(feature + ".Enable")) continue;
				options.add(feature);
			}
		}

		if (!options.isEmpty()) {
			String shown = String.join(", ", options.subList(0, Math.min(3, options.size()))) + (options.size() > 3 ? " and " + (options.size() - 3) + " more" : "");
			warnings.add(options.size() + " option(s) of Events/ are limited to WorldGuard regions (" + shown + "), but WorldGuard "
					+ (Bukkit.getPluginManager().isPluginEnabled("WorldGuard") ? "is off in general.yml (Plugin.Use.Hook.WorldGuard)" : "is not installed")
					+ ": they apply in the whole worlds");
		}
	}

	private static void hookKept(List<String> warnings, String plugin) {
		String path = "Plugin.Use.Hook." + plugin + ".";
		if (ConfigGeneral.getConfig().getBoolean(path + "Enable") && ConfigGeneral.getConfig().getBoolean(path + "Keep-The-Option")
				&& !Bukkit.getPluginManager().isPluginEnabled(plugin)) {
			warnings.add(plugin + " is kept on in general.yml (Keep-The-Option), but it is not installed: its placeholders are not replaced");
		}
	}
}
