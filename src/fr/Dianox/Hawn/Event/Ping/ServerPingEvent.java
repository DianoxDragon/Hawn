package fr.Dianox.Hawn.Event.Ping;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;

public class ServerPingEvent implements Listener {

	Integer MaxPlayers = Bukkit.getMaxPlayers();
	
	@EventHandler
	public void ping(ServerListPingEvent e) {
		if (ServerListConfig.getConfig().getBoolean("Slots.One-Slot-Free")) {
			if (e.getNumPlayers() < MaxPlayers) {
				e.setMaxPlayers(e.getNumPlayers() + 1);
			} else {
				e.setMaxPlayers(MaxPlayers);
			}
		} else {
			if (ServerListConfig.getConfig().getBoolean("Slots.Fake-Max-Player.Enable")) {
				e.setMaxPlayers(ServerListConfig.getConfig().getInt("Slots.Fake-Max-Player.Number"));
			} else {
				e.setMaxPlayers(MaxPlayers);
			}
		}
		
		if (ServerListConfig.getConfig().getBoolean("Motd.Classic.Enable")) {
			String line1 = ServerListConfig.getConfig().getString("Motd.Classic.Line-1");
			String line2 = ServerListConfig.getConfig().getString("Motd.Classic.Line-2");
				
			line1 = line1.replaceAll("&", "§");
			line2 = line2.replaceAll("&", "§");
			
			line1 = MessageUtils.ReplaceMainplaceholderC(line1);
			line2 = MessageUtils.ReplaceMainplaceholderC(line2);
				
			e.setMotd(String.valueOf(line1) + "\n" + line2);
		}
		
		if (Bukkit.hasWhitelist()) {
			if (ServerListConfig.getConfig().getBoolean("Motd.WhiteList.Enable")) {
				String line1 = ServerListConfig.getConfig().getString("Motd.WhiteList.Line-1");
				String line2 = ServerListConfig.getConfig().getString("Motd.WhiteList.Line-2");
				
				line1 = line1.replaceAll("&", "§");
				line2 = line2.replaceAll("&", "§");
				
				line1 = MessageUtils.ReplaceMainplaceholderC(line1);
				line2 = MessageUtils.ReplaceMainplaceholderC(line2);
				
				e.setMotd(String.valueOf(line1) + "\n" + line2);
			}
		}
		
		if (ServerListConfig.getConfig().getBoolean("Maintenance.Enable") && ServerListConfig.getConfig().getBoolean("Motd.Maintenance.Enable")) {
			String line1 = ServerListConfig.getConfig().getString("Motd.Maintenance.Line-1");
			String line2 = ServerListConfig.getConfig().getString("Motd.Maintenance.Line-2");
			
			line1 = line1.replaceAll("&", "§");
			line2 = line2.replaceAll("&", "§");
			
			line1 = MessageUtils.ReplaceMainplaceholderC(line1);
			line2 = MessageUtils.ReplaceMainplaceholderC(line2);
			
			e.setMotd(String.valueOf(line1) + "\n" + line2);
		}
		
		if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable") && ServerListConfig.getConfig().getBoolean("Motd.Urgent.Enable")) {
			String line1 = ServerListConfig.getConfig().getString("Motd.Urgent.Line-1");
			String line2 = ServerListConfig.getConfig().getString("Motd.Urgent.Line-2");
			
			line1 = line1.replaceAll("&", "§");
			line2 = line2.replaceAll("&", "§");
			
			line1 = MessageUtils.ReplaceMainplaceholderC(line1);
			line2 = MessageUtils.ReplaceMainplaceholderC(line2);
			
			e.setMotd(String.valueOf(line1) + "\n" + line2);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void login(PlayerLoginEvent e) {
		
		if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
			List<String> whitelist = ServerListConfig.getConfig().getStringList("Urgent-mode.whitelist");
			if (!whitelist.contains(e.getPlayer().getName())) {
				String message = ServerListConfig.getConfig().getString("Urgent-mode.Kick-Message");
				message = message.replaceAll("&", "§");
				message = MessageUtils.ReplaceMainplaceholderP(message, e.getPlayer());
				
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
			}
		}
		
		if (ServerListConfig.getConfig().getBoolean("Maintenance.Enable")) {
			List<String> whitelist = ServerListConfig.getConfig().getStringList("Maintenance.whitelist");
			if (!whitelist.contains(e.getPlayer().getName())) {
				String message = ServerListConfig.getConfig().getString("Maintenance.Kick-Message");
				message = message.replaceAll("&", "§");
				message = MessageUtils.ReplaceMainplaceholderP(message, e.getPlayer());
				
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
			}
		}
		
		if (ServerListConfig.getConfig().getBoolean("On-Join.Player-With-Permission-Join-Full-Server")) {
			if (e.getResult().equals(PlayerLoginEvent.Result.KICK_FULL)) {
				if (e.getPlayer().hasPermission("hawn.join.full")) {
					e.allow();
				} else {
					String message = ServerListConfig.getConfig().getString("On-Join.Message");
					message = message.replaceAll("&", "§");
					message = MessageUtils.ReplaceMainplaceholderP(message, e.getPlayer());
					
					e.setKickMessage(message);
				}
			}
		}
	}
}
