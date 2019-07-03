package fr.Dianox.Hawn.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class VersionUtils {
	
	static String versionsS = "";
	
	public static void onGetServerVersiononLoad() {
		
		versionsS = Bukkit.getVersion();
		
		if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {	
			
		} else {
			Bukkit.getConsoleSender().sendMessage("| "+ChatColor.YELLOW+"Not developped for theses versions, but it should work");
			Bukkit.getConsoleSender().sendMessage("| ");
		}
	}
	
	public static String getVersionS() {
		return versionsS;
	} 

}
