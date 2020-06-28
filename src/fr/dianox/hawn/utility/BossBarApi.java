package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BossBarApi {
	
	static BossBar newBar;
	public static HashMap<Player, Integer> ptaskbb = new HashMap<>();
	public static List<Player> BBBlock = new ArrayList<>();
	
	public static void createnewbar(Player p, String color, String title, String style, Float progress) {
		
		if (Main.getInstance().getVersionUtils().getSpigot_Version() < 19) {
			return;
		}
		
		// Title
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
        	if (PlaceholderAPI.containsPlaceholders(title))
        		title = PlaceholderAPI.setPlaceholders(p, title);
        }
        
        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
        	title = PlaceHolders.BattleLevelPO(title, p);
		}
        
        title = PlaceHolders.ReplaceMainplaceholderP(title, p);
		
		title = title.replaceAll("&", "§");
		
		// Color
		BarColor colorbar;

		switch (color) {
			case "RED":
				colorbar = BarColor.RED;
				break;
			case "BLUE":
				colorbar = BarColor.BLUE;
				break;
			case "GREEN":
				colorbar = BarColor.GREEN;
				break;
			case "PINK":
				colorbar = BarColor.PINK;
				break;
			case "PURPLE":
				colorbar = BarColor.PURPLE;
				break;
			case "YELLOW":
				colorbar = BarColor.YELLOW;
				break;
			default:
				colorbar = BarColor.WHITE;
				break;
		}
		
		// Style
		BarStyle stylebar;

		switch (style) {
			case "SEGMENTED_6":
				stylebar = BarStyle.SEGMENTED_6;
				break;
			case "SEGMENTED_10":
				stylebar = BarStyle.SEGMENTED_10;
				break;
			case "SEGMENTED_12":
				stylebar = BarStyle.SEGMENTED_12;
				break;
			case "SEGMENTED_20":
				stylebar = BarStyle.SEGMENTED_20;
				break;
			default:
				stylebar = BarStyle.SOLID;
				break;
		}
		
		newBar = Bukkit.createBossBar(title, colorbar, stylebar);
		
		newBar.setProgress(progress);
		
		newBar.addPlayer(p);
	}
	
	public static void updateBar(Player p, String color, String title, String style, Float progress) {
		
		if (Main.getInstance().getVersionUtils().getSpigot_Version() < 19) {
			return;
		}
		
		// Title
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
        	if (PlaceholderAPI.containsPlaceholders(title))
        		title = PlaceholderAPI.setPlaceholders(p, title);
        }
        
        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
        	title = PlaceHolders.BattleLevelPO(title, p);
		}
        
        title = PlaceHolders.ReplaceMainplaceholderP(title, p);
		
		title = title.replaceAll("&", "§");
		
		// Color
		BarColor colorbar;

		switch (color) {
			case "RED":
				colorbar = BarColor.RED;
				break;
			case "BLUE":
				colorbar = BarColor.BLUE;
				break;
			case "GREEN":
				colorbar = BarColor.GREEN;
				break;
			case "PINK":
				colorbar = BarColor.PINK;
				break;
			case "PURPLE":
				colorbar = BarColor.PURPLE;
				break;
			case "YELLOW":
				colorbar = BarColor.YELLOW;
				break;
			default:
				colorbar = BarColor.WHITE;
				break;
		}
		
		// Style
		BarStyle stylebar;

		switch (style) {
			case "SEGMENTED_6":
				stylebar = BarStyle.SEGMENTED_6;
				break;
			case "SEGMENTED_10":
				stylebar = BarStyle.SEGMENTED_10;
				break;
			case "SEGMENTED_12":
				stylebar = BarStyle.SEGMENTED_12;
				break;
			case "SEGMENTED_20":
				stylebar = BarStyle.SEGMENTED_20;
				break;
			default:
				stylebar = BarStyle.SOLID;
				break;
		}
		
		newBar.setColor(colorbar);
		newBar.setTitle(title);
		newBar.setStyle(stylebar);
		newBar.setProgress(progress);
		
		try {
			newBar.removePlayer(p);
			newBar.addPlayer(p);
		} catch (Exception ignored) {}
	}

	public static void deletebar(Player p) {
		
		if (Main.getInstance().getVersionUtils().getSpigot_Version() < 19) {
			return;
		}
		
		try {
			if (newBar.getPlayers().contains(p)) {
				newBar.removePlayer(p);
			}
		} catch (Exception ignored) {}
	}
	
}