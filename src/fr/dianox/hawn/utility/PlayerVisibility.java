package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerVisibility {
	
	public static List<Player> PVPlayer = new ArrayList<>();
	private static final List<Player> Cooling = new ArrayList<>();
	
	public static List<Player> getPlayerVisibility() {
		return PVPlayer;
	}
	
	@SuppressWarnings("deprecation")
	public static void showPlayer(Player p) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 113) {
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
			if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 113) {
				p.hidePlayer(Main.getInstance(), player);
			} else {
				p.hidePlayer(player);
			}
		}
		
		PVPlayer.add(p);
	}

	public static List<Player> Cooling() {
		return Cooling;
	}
}