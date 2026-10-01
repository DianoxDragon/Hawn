package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionUtils {

	private static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)(?:\\.(\\d+))?");

	private final String versionsS;
	private final int major;
	private final int minor;
	private final int patch;
	final Integer Spigot_Version;

	public VersionUtils() {
		versionsS = Bukkit.getVersion();

		// getBukkitVersion() looks like "1.21.4-R0.1-SNAPSHOT" or "26.1-R0.1-SNAPSHOT"
		Matcher matcher = VERSION_PATTERN.matcher(Bukkit.getBukkitVersion());
		if (matcher.find()) {
			major = Integer.parseInt(matcher.group(1));
			minor = Integer.parseInt(matcher.group(2));
			patch = matcher.group(3) == null ? 0 : Integer.parseInt(matcher.group(3));
		} else {
			major = 1;
			minor = 16;
			patch = 5;
		}

		// Kept for the existing checks: 1.16 -> 116, 1.21 -> 121, 26.1 -> 2601, ...
		Spigot_Version = major == 1 ? 100 + minor : major * 100 + minor;

		if (!isAtLeast(1, 16, 5)) {
			Bukkit.getConsoleSender().sendMessage("| " + ChatColor.RED + "Hawn requires Minecraft 1.16.5 or newer, some features will not work.");
			Bukkit.getConsoleSender().sendMessage("| ");
		}
	}

	public boolean isAtLeast(int major, int minor, int patch) {
		if (this.major != major) return this.major > major;
		if (this.minor != minor) return this.minor > minor;
		return this.patch >= patch;
	}

	public String getVersionsS() {
		return this.versionsS;
	}

	public Integer getSpigot_Version() {
		return Spigot_Version;
	}
}
