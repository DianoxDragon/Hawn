package fr.Dianox.Hawn.Event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.Config.ScoreboardMainConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ScoreboardCommandConfig;
import fr.Dianox.Hawn.Utility.Scoreboard.PlayerBoard;
import fr.Dianox.Hawn.Utility.Scoreboard.ScoreboardInfo;

public class OnScoreboard implements Listener {

	@EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player player = e.getPlayer();
        
        Main.playerWorldTimer.put(player, System.currentTimeMillis() + 3000);
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        final Player p = e.getPlayer();
        
        if (Main.nosb.contains(p)) {
        	return;
        }
        
        if (Main.boards.containsKey(e.getPlayer())) {
	        if (!e.getFrom().getWorld().getName().equals(e.getTo().getWorld().getName())) {
	        	Main.playerWorldTimer.put(p, System.currentTimeMillis() + 3000);
	        }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        final Player p = e.getPlayer();

        if (Main.nosb.contains(p)) {
        	return;
        }
        
        Main.playerWorldTimer.put(p, System.currentTimeMillis() + 3000);
    }

    @SuppressWarnings("unlikely-arg-type")
	@EventHandler
    public void onQuit(PlayerQuitEvent e) {
    	Main.playerWorldTimer.remove(e.getPlayer());
    	
    	if (Main.boards.containsKey(e.getPlayer())) {
    		Main.boards.get(e.getPlayer()).stopTasks();
    		Main.boards.remove(e.getPlayer());
    		Main.allboards.remove(Main.getInstance());
    	}
    	
    	if (Main.nosb.contains(e.getPlayer())) {
    		Main.nosb.remove(e.getPlayer());
    	}
    }
	
	@EventHandler
    public void onChangeWorldEvent(PlayerChangedWorldEvent e) {
    	Player p = e.getPlayer();
    	
    	
    	if (Main.nosb.contains(p)) {
    		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    		return;
    	}
    	
    	String bool = Main.getYmlaMysqlsb(e.getPlayer(), "keepsb");
    	
		if (bool.equalsIgnoreCase("TRUE") && ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Option.Keep-Scoreboard-Change")) {
			String sb = Main.getYmlaMysqlsb(e.getPlayer(), "scoreboard");
			if (Main.getInstance().getBoards().containsKey(p)) {
				ScoreboardInfo in = (ScoreboardInfo)Main.getInstance().getInfo().get("hawn.scoreboard."+sb);
				((PlayerBoard)Main.getInstance().getBoards().get(p)).createNew(in.getText(), in.getTitle(), in.getTitleUpdate(), in.getTextUpdate());
			} else {
				new PlayerBoard(Main.getInstance(), p, (ScoreboardInfo)Main.getInstance().getInfo().get("hawn.scoreboard."+sb));
			}
		} else {
	    	Main.playerWorldTimer.put(e.getPlayer(), System.currentTimeMillis() + 3000);
	    	
	    	if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
	    		Main.getInstance().createDefaultScoreboard(p);
	    	}
		}  
    }
    
}
