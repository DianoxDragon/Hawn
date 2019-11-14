package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.ScoreboardMainConfig;
import fr.dianox.hawn.utility.config.commands.ScoreboardCommandConfig;
import fr.dianox.hawn.utility.scoreboard.PlayerBoard;
import fr.dianox.hawn.utility.scoreboard.ScoreboardInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

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
        
        if (Main.boards.containsKey(e.getPlayer().getUniqueId())) {
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
    	
    	String bool = PlayerOptionSQLClass.getYmlaMysqlsb(e.getPlayer(), "keepsb");
    	
		if (bool.equalsIgnoreCase("TRUE") && ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Option.Keep-Scoreboard-Change")) {
			String sb = PlayerOptionSQLClass.getYmlaMysqlsb(e.getPlayer(), "scoreboard");
			if (Main.getInstance().getBoards().containsKey(p.getUniqueId())) {
				ScoreboardInfo in = (ScoreboardInfo)Main.getInstance().getInfo().get("hawn.scoreboard."+sb);
				((PlayerBoard)Main.getInstance().getBoards().get(p.getUniqueId())).createNew(in);
			} else {
				new PlayerBoard(Main.getInstance(), p.getUniqueId(), (ScoreboardInfo)Main.getInstance().getInfo().get("hawn.scoreboard."+sb));
			}
		} else {
	    	Main.playerWorldTimer.put(e.getPlayer(), System.currentTimeMillis() + 3000);
	    	
	    	if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
	    		Main.getInstance().createDefaultScoreboard(p);
	    	}
		}  
    }
    
}