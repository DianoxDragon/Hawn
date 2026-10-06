package fr.dianox.hawn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.bukkit.plugin.java.JavaPlugin;

public class UpdateChecker {

	private final JavaPlugin plugin;
	private final int projectID;

	public static String new_number_version = "";

	public UpdateChecker(JavaPlugin plugin, int projectID) {
		this.plugin = plugin;
		this.projectID = projectID;
	}

	public JavaPlugin getPlugin() {
		return this.plugin;
	}

	/**
	 * Blocking network call: never call it from the main server thread.
	 *
	 * @return true if the version published on Spigot is newer than the installed one
	 */
	public boolean checkForUpdates() throws IOException {
		URLConnection con = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID).openConnection();
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
			String remote = reader.readLine();
			new_number_version = remote == null ? "" : remote.trim();
		}

		return isNewer(new_number_version, plugin.getDescription().getVersion());
	}

	/**
	 * Compares versions number by number ("1.1.5-Beta" < "1.2.0"). With the same numbers, a release is newer
	 * than a beta ("1.3.0" > "1.3.0-Beta"), so the beta testers are told when the release is out.
	 */
	static boolean isNewer(String remote, String current) {
		int[] r = parse(remote);
		int[] c = parse(current);

		for (int i = 0; i < Math.max(r.length, c.length); i++) {
			int a = i < r.length ? r[i] : 0;
			int b = i < c.length ? c[i] : 0;
			if (a != b) return a > b;
		}

		return hasSuffix(current) && !hasSuffix(remote);
	}

	private static boolean hasSuffix(String version) {
		return !version.trim().matches("[0-9.]*");
	}

	private static int[] parse(String version) {
		String numbers = version.split("[^0-9.]", 2)[0];
		if (numbers.isEmpty()) return new int[0];

		String[] parts = numbers.split("\\.");
		int[] result = new int[parts.length];
		for (int i = 0; i < parts.length; i++) {
			try {
				result[i] = Integer.parseInt(parts[i]);
			} catch (NumberFormatException e) {
				result[i] = 0;
			}
		}
		return result;
	}

}
