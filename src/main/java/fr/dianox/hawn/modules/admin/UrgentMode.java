package fr.dianox.hawn.modules.admin;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.serverlist.ServerPingEvent;
import fr.dianox.hawn.modules.world.WorldFolders;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.HawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * The urgent mode (/hawn urgent): made for a hacked staff account or a griefing in progress.
 * It kicks the players who are not in its whitelist, takes the operator status away, locks the commands and the
 * chat of the players, makes a backup outside the main thread and can disable the other plugins.
 * Only the console can turn it off, which gives everything back.
 */
public class UrgentMode implements Listener {

	private static final String PATH = "Urgent-mode.";
	private static final String SAVE_PREFIX = "Hawn-save-";

	private static YamlConfiguration cfg() {
		return HawnCommandConfig.getConfig();
	}

	public static boolean isOn() {
		return cfg().getBoolean(PATH + "Enable");
	}

	/**
	 * Can-Use-Urgent-Mode: names without case, or UUIDs
	 */
	public static boolean canUse(Player p) {
		for (String s : cfg().getStringList(PATH + "Can-Use-Urgent-Mode")) {
			if (s.equalsIgnoreCase(p.getName()) || s.equalsIgnoreCase(p.getUniqueId().toString())) {
				return true;
			}
		}
		return false;
	}

	// Use-It-Only-On-The-Console: told to the player only (it was sent to every player)
	public static void consoleOnly(Player p) {
		tell(p, messages("Urgent-mode.Console-Only", "&8[&eHawn-Urgent&8] &cThe urgent mode can only be started from the console"), "");
	}

	/* ------------------------------------------------------------------ start and stop */

	public static void start(CommandSender by) {
		cfg().set(PATH + "Enable", true);
		HawnCommandConfig.saveConfigFile();

		record("ON", by);
		tell(by, ConfigMAdmin.getConfig().getStringList("Urgent-mode.On"), "");

		for (Player ps : new ArrayList<>(Bukkit.getServer().getOnlinePlayers())) {
			if (!ServerPingEvent.canJoin(ps, "Urgent-mode")) {
				ps.kickPlayer(ServerPingEvent.kickMessage(ps, "Urgent-mode"));
			}
		}

		if (cfg().getBoolean(PATH + "Remove-Op", true)) {
			removeOps(by);
		}

		if (cfg().getBoolean(PATH + "Backup.Enable", true)) {
			backup(by);
		}

		if (cfg().getBoolean(PATH + "Plugin-desactivation.Disable-All-Plugins-When-Enabled", false)) {
			disablePlugins(by);
		}

		broadcast(by, ConfigMAdmin.getConfig().getStringList("Urgent-mode.Broadcast.On"));
	}

	/**
	 * Console only: gives back the operators and the plugins disabled by the urgent mode
	 */
	public static void stop(CommandSender by) {
		cfg().set(PATH + "Enable", false);
		HawnCommandConfig.saveConfigFile();

		record("OFF", by);

		YamlConfiguration state = state();
		restoreOps(by, state);
		enablePlugins(by, state);
		state.set("Removed-Ops", null);
		state.set("Disabled-Plugins", null);
		saveState(state);

		tell(by, ConfigMAdmin.getConfig().getStringList("Urgent-mode.Off"), "");
		broadcast(by, ConfigMAdmin.getConfig().getStringList("Urgent-mode.Broadcast.Off"));
	}

	/* ------------------------------------------------------------------ operators */

	private static void removeOps(CommandSender by) {
		YamlConfiguration state = state();
		List<String> removed = state.getStringList("Removed-Ops");

		for (OfflinePlayer op : new ArrayList<>(Bukkit.getOperators())) {
			String id = op.getUniqueId().toString();
			if (!removed.contains(id)) {
				removed.add(id);
			}
			op.setOp(false);
		}

		state.set("Removed-Ops", removed);
		saveState(state);

		tellAndConsole(by, messages("Urgent-mode.Ops-Removed", "&8[&eHawn-Urgent&8] &c%arg1% operator(s) removed until the end of the urgent mode"), String.valueOf(removed.size()));
	}

	private static void restoreOps(CommandSender by, YamlConfiguration state) {
		List<String> removed = state.getStringList("Removed-Ops");

		for (String id : removed) {
			try {
				Bukkit.getOfflinePlayer(UUID.fromString(id)).setOp(true);
			} catch (IllegalArgumentException ignored) {}
		}

		if (!removed.isEmpty()) {
			tell(by, messages("Urgent-mode.Ops-Restored", "&8[&eHawn-Urgent&8] &7%arg1% operator(s) given back"), String.valueOf(removed.size()));
		}
	}

	/* ------------------------------------------------------------------ plugins */

	private static void disablePlugins(CommandSender by) {
		List<String> ignored = cfg().getStringList(PATH + "Plugin-desactivation.Plugin-Ignored");
		YamlConfiguration state = state();
		List<String> disabled = state.getStringList("Disabled-Plugins");

		for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
			if (plugin == Main.getInstance() || ignored.contains(plugin.getName()) || !plugin.isEnabled()) {
				continue;
			}

			Bukkit.getPluginManager().disablePlugin(plugin);
			if (!disabled.contains(plugin.getName())) {
				disabled.add(plugin.getName());
			}
		}

		state.set("Disabled-Plugins", disabled);
		saveState(state);

		tellAndConsole(by, ConfigMAdmin.getConfig().getStringList("Urgent-mode.Disabled-Plugin-function"), "");
	}

	// Only the plugins the urgent mode disabled, not the ones that were already off
	private static void enablePlugins(CommandSender by, YamlConfiguration state) {
		if (!state.isSet("Disabled-Plugins")) {
			return;
		}

		for (String name : state.getStringList("Disabled-Plugins")) {
			Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
			if (plugin != null && !plugin.isEnabled()) {
				Bukkit.getPluginManager().enablePlugin(plugin);
			}
		}

		tell(by, ConfigMAdmin.getConfig().getStringList("Urgent-mode.Back-To-Normal-For-All-Plugins"), "");
	}

	/* ------------------------------------------------------------------ backup */

	/**
	 * The worlds (saved first, auto save paused while the zip is written) and the plugins folder without the jars
	 * and the previous backups, in plugins/Hawn-save-<date>.zip, outside the main thread.
	 */
	private static void backup(CommandSender by) {
		File pluginsDir = Main.getInstance().getDataFolder().getAbsoluteFile().getParentFile();
		String date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.ROOT).format(new Date());
		File target = new File(pluginsDir, SAVE_PREFIX + date + ".zip");

		Map<World, Boolean> autoSave = new LinkedHashMap<>();
		Map<String, File> roots = new LinkedHashMap<>();
		for (World w : Bukkit.getWorlds()) {
			w.save();
			autoSave.put(w, w.isAutoSave());
			w.setAutoSave(false);

			// The whole world folder with its level.dat (recent Paper: the dimensions share one folder)
			File root = WorldFolders.root(w);
			if (!roots.containsValue(root)) {
				roots.put("worlds/" + root.getName(), root);
			}
		}
		roots.put("plugins", pluginsDir);

		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			int[] skipped = {0};
			boolean ok = true;

			try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(target))) {
				for (Map.Entry<String, File> root : roots.entrySet()) {
					zipFolder(zos, root.getValue(), root.getKey(), target, skipped);
				}
			} catch (IOException e) {
				ok = false;
				Main.getInstance().getLogger().log(Level.WARNING, "Urgent mode: the backup failed", e);
			}

			boolean done = ok;
			Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
				for (Map.Entry<World, Boolean> w : autoSave.entrySet()) {
					w.getKey().setAutoSave(w.getValue());
				}

				if (done) {
					deleteOldBackups(pluginsDir);
					if (skipped[0] > 0) {
						Main.getInstance().getLogger().warning("Urgent mode: " + skipped[0] + " file(s) could not be read and are not in " + target.getName());
					}
					tellAndConsole(by, ConfigMAdmin.getConfig().getStringList("Urgent-mode.Zip"), target.getName());
				} else {
					tellAndConsole(by, messages("Urgent-mode.Zip-Failed", "&8[&eHawn-Urgent&8] &cThe backup failed, see the console"), "");
				}
			});
		});
	}

	private static void zipFolder(ZipOutputStream zos, File folder, String entry, File target, int[] skipped) throws IOException {
		File[] files = folder.listFiles();
		if (files == null) {
			return;
		}

		for (File file : files) {
			String name = file.getName();
			if (file.isDirectory()) {
				if (name.equals("cache") || name.equals("dumps")) {
					continue;
				}
				zipFolder(zos, file, entry + "/" + name, target, skipped);
				continue;
			}

			// The plugin jars (they can be downloaded again, and a backdoor would be kept), the previous backups
			if (name.endsWith(".jar") || name.equals("session.lock") || file.equals(target)
					|| (name.startsWith(SAVE_PREFIX) && name.endsWith(".zip"))) {
				continue;
			}

			InputStream in;
			try {
				in = new FileInputStream(file);
			} catch (IOException e) {
				// A file locked by another program: the rest of the backup goes on
				skipped[0]++;
				continue;
			}

			try (InputStream input = in) {
				zos.putNextEntry(new ZipEntry(entry + "/" + name));
				byte[] buffer = new byte[8192];
				int n;
				while ((n = input.read(buffer)) > 0) {
					zos.write(buffer, 0, n);
				}
				zos.closeEntry();
			}
		}
	}

	// Keep: the newest backups kept (0 or less: all), only the backups named by this version
	private static void deleteOldBackups(File pluginsDir) {
		int keep = cfg().getInt(PATH + "Backup.Keep", 3);
		if (keep <= 0) {
			return;
		}

		File[] saves = pluginsDir.listFiles((dir, name) -> name.matches("Hawn-save-\\d{4}-\\d{2}-\\d{2}_\\d{2}-\\d{2}-\\d{2}\\.zip"));
		if (saves == null || saves.length <= keep) {
			return;
		}

		// The names sort by date
		Arrays.sort(saves, (a, b) -> b.getName().compareTo(a.getName()));
		for (int i = keep; i < saves.length; i++) {
			if (!saves[i].delete()) {
				Main.getInstance().getLogger().warning("Urgent mode: could not delete the old backup " + saves[i].getName());
			}
		}
	}

	/* ------------------------------------------------------------------ lockdown */

	@EventHandler(priority = EventPriority.LOWEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (!isOn() || !cfg().getBoolean(PATH + "Lockdown.Block-Commands", true)) {
			return;
		}

		String label = e.getMessage().split(" ", 2)[0];
		label = label.startsWith("/") ? label.substring(1) : label;
		label = label.substring(label.indexOf(':') + 1).toLowerCase(Locale.ROOT);

		for (String allowed : cfg().getStringList(PATH + "Lockdown.Allowed-Commands")) {
			String a = allowed.startsWith("/") ? allowed.substring(1) : allowed;
			if (a.equalsIgnoreCase(label)) {
				return;
			}
		}

		e.setCancelled(true);
		tell(e.getPlayer(), messages("Urgent-mode.Lockdown-Command", "&8[&eHawn-Urgent&8] &cThe server is in urgent mode: the commands are disabled"), "");
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChat(AsyncPlayerChatEvent e) {
		if (!isOn() || !cfg().getBoolean(PATH + "Lockdown.Block-Chat", true)) {
			return;
		}

		e.setCancelled(true);
		Player p = e.getPlayer();
		List<String> lines = messages("Urgent-mode.Lockdown-Chat", "&8[&eHawn-Urgent&8] &cThe server is in urgent mode: the chat is disabled");
		Bukkit.getScheduler().runTask(Main.getInstance(), () -> tell(p, lines, ""));
	}

	/* ------------------------------------------------------------------ log and alert */

	// plugins/Hawn/urgent-mode.log and the Discord webhook
	private static void record(String what, CommandSender by) {
		String who = by instanceof Player ? by.getName() + " (" + ((Player) by).getUniqueId() + ")" : "the console";
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT).format(new Date());
		String line = date + " Urgent mode " + what + " by " + who;

		try (PrintWriter w = new PrintWriter(new FileWriter(new File(Main.getInstance().getDataFolder(), "urgent-mode.log"), true))) {
			w.println(line);
		} catch (IOException e) {
			Main.getInstance().getLogger().log(Level.WARNING, "Urgent mode: could not write urgent-mode.log", e);
		}

		Main.getInstance().getLogger().warning(line);
		webhook("Hawn: urgent mode **" + what + "** by " + who + " (" + date + ")");
	}

	private static void webhook(String text) {
		String url = cfg().getString(PATH + "Discord-Webhook", "");
		if (url == null || !(url.startsWith("https://") || url.startsWith("http://"))) {
			return;
		}

		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
			try {
				HttpURLConnection c = (HttpURLConnection) new URL(url).openConnection();
				c.setRequestMethod("POST");
				c.setDoOutput(true);
				c.setConnectTimeout(5000);
				c.setReadTimeout(5000);
				c.setRequestProperty("Content-Type", "application/json");
				c.setRequestProperty("User-Agent", "Hawn");

				byte[] body = ("{\"content\":\"" + json(text) + "\"}").getBytes(StandardCharsets.UTF_8);
				try (OutputStream o = c.getOutputStream()) {
					o.write(body);
				}

				int code = c.getResponseCode();
				if (code >= 300) {
					Main.getInstance().getLogger().warning("Urgent mode: the Discord webhook answered " + code);
				}
				c.disconnect();
			} catch (IOException e) {
				Main.getInstance().getLogger().warning("Urgent mode: the Discord webhook could not be reached (" + e.getMessage() + ")");
			}
		});
	}

	private static String json(String s) {
		StringBuilder sb = new StringBuilder();
		for (char ch : s.toCharArray()) {
			switch (ch) {
				case '"': sb.append("\\\""); break;
				case '\\': sb.append("\\\\"); break;
				case '\n': sb.append("\\n"); break;
				case '\r': sb.append("\\r"); break;
				case '\t': sb.append("\\t"); break;
				default:
					if (ch < 0x20) {
						sb.append(String.format("\\u%04x", (int) ch));
					} else {
						sb.append(ch);
					}
			}
		}
		return sb.toString();
	}

	/* ------------------------------------------------------------------ state file and messages */

	// What the urgent mode took away, kept across a restart: StockageInfo/urgent-mode.yml
	private static File stateFile() {
		return new File(Main.getInstance().getDataFolder(), "StockageInfo/urgent-mode.yml");
	}

	private static YamlConfiguration state() {
		return YamlConfiguration.loadConfiguration(stateFile());
	}

	private static void saveState(YamlConfiguration state) {
		try {
			stateFile().getParentFile().mkdirs();
			state.save(stateFile());
		} catch (IOException e) {
			Main.getInstance().getLogger().log(Level.WARNING, "Urgent mode: could not save " + stateFile(), e);
		}
	}

	// A message added in 1.3: the default text when the message file is older
	private static List<String> messages(String key, String... byDefault) {
		if (ConfigMAdmin.getConfig().isSet(key)) {
			return ConfigMAdmin.getConfig().getStringList(key);
		}
		return Arrays.asList(byDefault);
	}

	private static void tell(CommandSender by, List<String> lines, String arg) {
		for (String msg : lines) {
			msg = msg.replace("%arg1%", arg);
			if (by instanceof Player) {
				if (((Player) by).isOnline()) {
					ConfigEventUtils.ExecuteEvent((Player) by, msg, "", "", false);
				}
			} else {
				MessageUtils.ConsoleMessages(msg);
			}
		}
	}

	private static void tellAndConsole(CommandSender by, List<String> lines, String arg) {
		tell(by, lines, arg);
		if (by instanceof Player) {
			tell(Bukkit.getConsoleSender(), lines, arg);
		}
	}

	private static void broadcast(CommandSender by, List<String> lines) {
		for (String msg : lines) {
			if (by instanceof Player) {
				ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", (Player) by, true);
			} else {
				ConfigEventUtils.ExecuteEventAllPlayersConsole(msg, "", "");
			}
			MessageUtils.ConsoleMessages(msg);
		}
	}
}
