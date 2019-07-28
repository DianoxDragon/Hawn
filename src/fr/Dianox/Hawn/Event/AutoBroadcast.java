package fr.Dianox.Hawn.Event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.World.BasicEventsPW;

public class AutoBroadcast extends BukkitRunnable {
	
	@SuppressWarnings("unused")
	private final JavaPlugin pl;
	  
	public AutoBroadcast(JavaPlugin pl) { this.pl = pl; }

	String msg = "";
	
	public void run() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total+1);
			
			msg = String.valueOf(Main.autobroadcast.get(value));
			
			for (String msg: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages."+msg+".message")) {
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Use-Permission-To-Get-Messages")) {
						if (p.hasPermission("hawn.get.autobroadcast")) {
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							} else {
								if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					} else {
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						} else {
							if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					}
				}
				
				if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Broadcast-To-Console")) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg
							.replaceAll("%player%", "Player name")
							.replaceAll("%target%", "Player name")
							));
				}
			}
		} else {
			if (Main.curMsg <= Main.autobroadcast_total) {
				msg = String.valueOf(Main.autobroadcast.get(Main.curMsg));
				
				for (String msg: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages."+msg+".message")) {
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Use-Permission-To-Get-Messages")) {
							if (p.hasPermission("hawn.get.autobroadcast")) {
								if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								} else {
									if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
							}
						} else {
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							} else {
								if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					}
					
					if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Broadcast-To-Console")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg
								.replaceAll("%player%", "Player name")
								.replaceAll("%target%", "Player name")
								));
					}
				}
				
				Main.curMsg++;
			} else {
				Main.curMsg = 0;
			}
		}
	}
	
}
