package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class VersionUtils {
	
	String versionsS;
	
	public VersionUtils() {
		versionsS = Bukkit.getVersion();

		if (!versionsS.contains("1.16") && !versionsS.contains("1.15") && !versionsS.contains("1.14") && !versionsS.contains("1.13")) {
			Bukkit.getConsoleSender().sendMessage("| " + ChatColor.YELLOW + "Not developed for these versions, but it should work.");
			Bukkit.getConsoleSender().sendMessage("| ");
		}
	}
	
	public String get() {
		return this.versionsS;
	} 

}