package fr.Dianox.Hawn.Utility;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Main;

public class PlayerVisibility {
	
	public static List<Player> PVPlayer = new ArrayList<Player>();
	private static List<String> Cooling = new ArrayList<String>();
	
	public static List<Player> getPlayerVisibility() {
		return PVPlayer;
	}
	
	@SuppressWarnings("deprecation")
	public static void showPlayer(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
				p.showPlayer(Main.getInstance(), player);
			} else {
				p.showPlayer(player);
			}
		}
		
		PVPlayer.remove(p);
	}
	
	@SuppressWarnings("deprecation")
	public static void hidePlayer(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
				p.hidePlayer(Main.getInstance(), player);
			} else {
				p.hidePlayer(player);
			}
		}
		
		PVPlayer.add(p);
	}

	public static List<String> Cooling() {
		return Cooling;
	}
}