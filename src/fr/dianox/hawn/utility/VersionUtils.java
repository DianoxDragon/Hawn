package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class VersionUtils {
	
	String versionsS;
	Integer Spigot_Version = 0;

	public VersionUtils() {
		versionsS = Bukkit.getVersion();

		if (!versionsS.contains("1.16") && !versionsS.contains("1.15") && !versionsS.contains("1.14") && !versionsS.contains("1.13")) {
			Bukkit.getConsoleSender().sendMessage("| " + ChatColor.YELLOW + "Not developed for these versions, but it should work.");
			Bukkit.getConsoleSender().sendMessage("| ");
		}

		if (Bukkit.getVersion().contains("1.16")) {
			Spigot_Version = 116;
		} else if (Bukkit.getVersion().contains("1.15")) {
			Spigot_Version = 115;
		} else if (Bukkit.getVersion().contains("1.14")) {
			Spigot_Version = 114;
		} else if (Bukkit.getVersion().contains("1.13")) {
			Spigot_Version = 113;
		} else if (Bukkit.getVersion().contains("1.12")) {
			Spigot_Version = 112;
		} else if (Bukkit.getVersion().contains("1.11")) {
			Spigot_Version = 111;
		} else if (Bukkit.getVersion().contains("1.10")) {
			Spigot_Version = 110;
		} else if (Bukkit.getVersion().contains("1.9")) {
			Spigot_Version = 19;
		} else if (Bukkit.getVersion().contains("1.8")) {
			Spigot_Version = 18;
		}
	}
	
	public String getVersionsS() {
		return this.versionsS;
	}

	public Integer getSpigot_Version() {
		return Spigot_Version;
	}
}