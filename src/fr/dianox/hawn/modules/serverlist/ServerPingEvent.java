package fr.dianox.hawn.modules.serverlist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import fr.dianox.hawn.utility.config.configs.commands.HawnCommandConfig;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.List;
import java.util.Random;

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
			if (ServerListConfig.getConfig().getBoolean("Motd.Classic.Random")) {
				String msg = "";
				
				Random rand = new Random();
				
				int value = rand.nextInt(Main.getInstance().getServerListManager().getMotd_total_sl()+1);
				msg = String.valueOf(Main.getInstance().getServerListManager().getMotd_sl().get(value));
				
				String line1 = ServerListConfig.getConfig().getString("Motd.Classic.Random-List."+msg+".Line-1");
				String line2 = ServerListConfig.getConfig().getString("Motd.Classic.Random-List."+msg+".Line-2");
				
				line1 = MessageUtils.colourTheStuff(line1);
				line2 = MessageUtils.colourTheStuff(line2);
				
				line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
				line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
				
				e.setMotd(String.valueOf(line1) + "\n" + line2);
			} else {
				String line1 = ServerListConfig.getConfig().getString("Motd.Classic.Main.Line-1");
				String line2 = ServerListConfig.getConfig().getString("Motd.Classic.Main.Line-2");
					
				line1 = MessageUtils.colourTheStuff(line1);
				line2 = MessageUtils.colourTheStuff(line2);
				
				line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
				line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
					
				e.setMotd(String.valueOf(line1) + "\n" + line2);
			}
		}
		
		if (Bukkit.hasWhitelist()) {
			if (ServerListConfig.getConfig().getBoolean("Motd.WhiteList.Enable")) {
				String line1 = ServerListConfig.getConfig().getString("Motd.WhiteList.Line-1");
				String line2 = ServerListConfig.getConfig().getString("Motd.WhiteList.Line-2");
				
				line1 = MessageUtils.colourTheStuff(line1);
				line2 = MessageUtils.colourTheStuff(line2);
				
				line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
				line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
				
				e.setMotd(String.valueOf(line1) + "\n" + line2);
			}
		}
		
		if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable") && ServerListConfig.getConfig().getBoolean("Motd.Maintenance.Enable")) {
			String line1 = ServerListConfig.getConfig().getString("Motd.Maintenance.Line-1");
			String line2 = ServerListConfig.getConfig().getString("Motd.Maintenance.Line-2");
			
			line1 = MessageUtils.colourTheStuff(line1);
			line2 = MessageUtils.colourTheStuff(line2);
			
			line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
			line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
			
			e.setMotd(String.valueOf(line1) + "\n" + line2);
		}
		
		if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable") && ServerListConfig.getConfig().getBoolean("Motd.Urgent.Enable")) {
			String line1 = ServerListConfig.getConfig().getString("Motd.Urgent.Line-1");
			String line2 = ServerListConfig.getConfig().getString("Motd.Urgent.Line-2");
			
			line1 = MessageUtils.colourTheStuff(line1);
			line2 = MessageUtils.colourTheStuff(line2);
			
			line1 = PlaceHolders.ReplaceMainplaceholderC(line1);
			line2 = PlaceHolders.ReplaceMainplaceholderC(line2);
			
			e.setMotd(String.valueOf(line1) + "\n" + line2);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void login(PlayerLoginEvent e) {
		
		if (HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
			List<String> whitelist = HawnCommandConfig.getConfig().getStringList("Urgent-mode.whitelist");
			if (!whitelist.contains(e.getPlayer().getName())) {
				String message = "";
				Boolean bool = false;
				for (String str: HawnCommandConfig.getConfig().getStringList("Urgent-mode.Kick-Message")) {
					if (bool) {
						message = message + "\n" + str;
					} else {
						message = str;
						bool = true;
					}
				}
				message = MessageUtils.colourTheStuff(message);
				message = PlaceHolders.ReplaceMainplaceholderP(message, e.getPlayer());
				
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
			}
		}
		
		if (HawnCommandConfig.getConfig().getBoolean("Maintenance.Enable")) {
			List<String> whitelist = HawnCommandConfig.getConfig().getStringList("Maintenance.whitelist");
			if (!whitelist.contains(e.getPlayer().getName())) {
				String message = "";
				Boolean bool = false;
				for (String str: HawnCommandConfig.getConfig().getStringList("Maintenance.Kick-Message")) {
					if (bool) {
						message = message + "\n" + str;
					} else {
						message = str;
						bool = true;
					}
				}
				message = MessageUtils.colourTheStuff(message);
				message = PlaceHolders.ReplaceMainplaceholderP(message, e.getPlayer());
				
				e.disallow(PlayerLoginEvent.Result.KICK_OTHER, message);
			}
		}
		
		if (ServerListConfig.getConfig().getBoolean("On-Join.Player-With-Permission-Join-Full-Server")) {
			if (e.getResult().equals(PlayerLoginEvent.Result.KICK_FULL)) {
				if (e.getPlayer().hasPermission("hawn.join.full")) {
					e.allow();
				} else {
					String message = "";
					Boolean bool = false;
					for (String str: ServerListConfig.getConfig().getStringList("On-Join.Message")) {
						if (bool) {
							message = message + "\n" + str;
						} else {
							message = str;
							bool = true;
						}
					}
					message = MessageUtils.colourTheStuff(message);
					message = PlaceHolders.ReplaceMainplaceholderP(message, e.getPlayer());
					
					e.setKickMessage(message);
				}
			}
		}
	}
}