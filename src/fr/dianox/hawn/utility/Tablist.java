package fr.dianox.hawn.utility;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.config.configs.tab.NameTagConfig;

public class Tablist {
	
	public static String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}

	//
	@SuppressWarnings("deprecation")
	public static void setPrefix(Player p) {
		String team = "";
				
		/*if (NameTagConfig.getConfig().isSet("nametag-player."+p.getName())) {
			String nametagplayer = String.valueOf(NameTagConfig.getConfig().getString("nametag-player."+p.getName()));
			
			team = String.valueOf(NameTagConfig.getConfig().getInt("nametag." + nametagplayer + ".priority") + nametagplayer);
			
			Main.getInstance().board.getTeam(team).addPlayer(p);
			
			p.setCustomName(String.valueOf(Main.getInstance().board.getTeam(team).getPrefix()) + p.getName());
			p.setPlayerListName(String.valueOf(Main.getInstance().board.getTeam(team).getPrefix()) + p.getName());
			
			for (Player all : Bukkit.getOnlinePlayers()) {
			      all.setScoreboard(Main.getInstance().board);
			}
			
			return;
		}*/
		
		Iterator<?> iterator = NameTagConfig.getConfig().getConfigurationSection("nametag").getKeys(false).iterator();
		
		while (iterator.hasNext()) {
			String string = (String)iterator.next();
			
			if (p.hasPermission("hawn.nametag." + string)) {
				
				p.getScoreboard().registerNewTeam(NameTagConfig.getConfig().getInt("nametag." + string + ".priority") + string);
				
				team = String.valueOf(NameTagConfig.getConfig().getInt("nametag." + string + ".priority") + string);
				
				p.getScoreboard().getTeam(team).addPlayer(p);
				
				p.setCustomName(String.valueOf(p.getScoreboard().getTeam(team).getPrefix()) + p.getName());
				p.setPlayerListName(String.valueOf(p.getScoreboard().getTeam(team).getPrefix()) + p.getName());
				
				for (Player all : Bukkit.getOnlinePlayers()) {
				      all.getScoreboard().getTeam(team);
				}
				
				break;
			} else {
				p.setCustomName(p.getName());
				p.setPlayerListName(p.getName());
			}
		}
	}
	
	public static void disable() {
		Iterator<?> iterator = NameTagConfig.getConfig().getConfigurationSection("nametag").getKeys(false).iterator();
		
		while (iterator.hasNext()) {
			String string = (String)iterator.next();
			
			//if (//scoremain.board.getTeam(NameTagConfig.getConfig().getInt("nametag." + string + ".priority") + string) != null) {
				//scoremain.board.getTeam(NameTagConfig.getConfig().getInt("nametag." + string + ".priority") + string).unregister();
			//}
		}
		
		//for (Player all : Bukkit.getOnlinePlayers()) {
		      //all.setScoreboard(scoremain.board);
		//}
	}
	
	

}