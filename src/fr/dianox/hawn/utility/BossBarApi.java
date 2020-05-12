package fr.dianox.hawn.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import me.clip.placeholderapi.PlaceholderAPI;

public class BossBarApi {
	
	static BossBar newBar;
	public static HashMap<Player, Integer> ptaskbb = new HashMap<Player, Integer>();
	public static List<Player> BBBlock = new ArrayList<Player>();
	
	public static void createnewbar(Player p, String color, String title, String style, Double progress) {
		
		if (Main.Spigot_Version < 19) {
			return;
		}
		
		// Title
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
        	if (PlaceholderAPI.containsPlaceholders(title))
        		title = PlaceholderAPI.setPlaceholders(p, title);
        }
        
        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
        	title = PlaceHolders.BattleLevelPO(title, p);
		}
        
        title = PlaceHolders.ReplaceMainplaceholderP(title, p);
		
		title = title.replaceAll("&", "§");
		
		// Color
		BarColor colorbar = null;
		
		if (color.equals("RED")) {
			colorbar = BarColor.RED;
		} else if (color.equals("BLUE")) {
			colorbar = BarColor.BLUE;
		} else if (color.equals("GREEN")) {
			colorbar = BarColor.GREEN;
		} else if (color.equals("PINK")) {
			colorbar = BarColor.PINK;
		} else if (color.equals("PURPLE")) {
			colorbar = BarColor.PURPLE;
		} else if (color.equals("WHITE")) {
			colorbar = BarColor.WHITE;
		} else if (color.equals("YELLOW")) {
			colorbar = BarColor.YELLOW;
		} else {
			colorbar = BarColor.WHITE;
		}
		
		// Style
		BarStyle stylebar = null;
		
		if (style.equals("SEGMENTED_6")) {
			stylebar = BarStyle.SEGMENTED_6;
		} else if (style.equals("SEGMENTED_10")) {
			stylebar = BarStyle.SEGMENTED_10;
		} else if (style.equals("SEGMENTED_12")) {
			stylebar = BarStyle.SEGMENTED_12;
		} else if (style.equals("SEGMENTED_20")) {
			stylebar = BarStyle.SEGMENTED_20;
		} else if (style.equals("SOLID")) {
			stylebar = BarStyle.SOLID;
		} else {
			stylebar = BarStyle.SOLID;
		}
		
		newBar = Bukkit.createBossBar(title, colorbar, stylebar, new BarFlag[0]);
		
		newBar.setProgress(progress);
		
		newBar.addPlayer(p);
	}
	
	public static void updateBar(Player p, String color, String title, String style, Double progress) {
		
		if (Main.Spigot_Version < 19) {
			return;
		}
		
		// Title
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
        	if (PlaceholderAPI.containsPlaceholders(title))
        		title = PlaceholderAPI.setPlaceholders(p, title);
        }
        
        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
        	title = PlaceHolders.BattleLevelPO(title, p);
		}
        
        title = PlaceHolders.ReplaceMainplaceholderP(title, p);
		
		title = title.replaceAll("&", "§");
		
		// Color
		BarColor colorbar = null;
		
		if (color.equals("RED")) {
			colorbar = BarColor.RED;
		} else if (color.equals("BLUE")) {
			colorbar = BarColor.BLUE;
		} else if (color.equals("GREEN")) {
			colorbar = BarColor.GREEN;
		} else if (color.equals("PINK")) {
			colorbar = BarColor.PINK;
		} else if (color.equals("PURPLE")) {
			colorbar = BarColor.PURPLE;
		} else if (color.equals("WHITE")) {
			colorbar = BarColor.WHITE;
		} else if (color.equals("YELLOW")) {
			colorbar = BarColor.YELLOW;
		} else {
			colorbar = BarColor.WHITE;
		}
		
		// Style
		BarStyle stylebar = null;
		
		if (style.equals("SEGMENTED_6")) {
			stylebar = BarStyle.SEGMENTED_6;
		} else if (style.equals("SEGMENTED_10")) {
			stylebar = BarStyle.SEGMENTED_10;
		} else if (style.equals("SEGMENTED_12")) {
			stylebar = BarStyle.SEGMENTED_12;
		} else if (style.equals("SEGMENTED_20")) {
			stylebar = BarStyle.SEGMENTED_20;
		} else if (style.equals("SOLID")) {
			stylebar = BarStyle.SOLID;
		} else {
			stylebar = BarStyle.SOLID;
		}
		
		newBar.setColor(colorbar);
		newBar.setTitle(title);
		newBar.setStyle(stylebar);
		newBar.setProgress(progress);
		
		try {
			newBar.removePlayer(p);
			newBar.addPlayer(p);
		} catch (Exception e) {}
	}

	public static void deletebar(Player p) {
		
		if (Main.Spigot_Version < 19) {
			return;
		}
		
		try {
			if (newBar.getPlayers().contains(p)) {
				newBar.removePlayer(p);
			}
		} catch (Exception e) {}
	}
	
}