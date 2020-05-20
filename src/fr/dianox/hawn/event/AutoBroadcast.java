package fr.dianox.hawn.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import fr.dianox.hawn.utility.world.BasicEventsPW;

public class AutoBroadcast extends BukkitRunnable {

    @SuppressWarnings("unused")
    private final JavaPlugin pl;

    public AutoBroadcast(JavaPlugin pl) {
        this.pl = pl;
    }

    String msg = "";
    public static List < String > newmessage = new ArrayList < String > ();

    public void run() {
    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total+1);
			
			msg = String.valueOf(Main.autobroadcast.get(value));
			
			SendBroadcast(msg);
		} else {
			if (Main.curMsg <= Main.autobroadcast_total) {
				msg = String.valueOf(Main.autobroadcast.get(Main.curMsg));
				
				SendBroadcast(msg);
				
				Main.curMsg++;
			} else {
				Main.curMsg = 0;
			}
		}
    }

    private void SendBroadcast(String msg) {
    	newmessage.clear();
		
    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Header.Enable")) {
    		for (String s: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Header.messages")) {
    			newmessage.add(s);
    		}
    	}
    	
    	for (String msg2: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".message")) {
    		newmessage.add(msg2);
    	}

    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Footer.Enable")) {
    		for (String s: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Footer.messages")) {
    			newmessage.add(s);
    		}
    	}
    	
    	for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			// Permission
    		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Use-Permission-To-Get-Messages")) {
    			if (!p.hasPermission("hawn.get.autobroadcast")) {
					continue;
				}
			}
			
			// Check want
			String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
			        	
			if (!valuepo.equalsIgnoreCase("TRUE")) {
				continue;
			}
			
			// Check world
			if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
				if (!BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
					continue;
				}
			}
			
			// Event
			// >> Send broadcast
			Boolean check = false;
			
			for (String s: newmessage) {
				if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                		if (p.getWorld().getName().contains(str)) {
                			check = true;
                		}
                	}
            	} else {
            		check = true;
            	}
            	
            	if (!check) {
            		continue;
            	}
            	
            	ConfigEventUtils.ExecuteEvent(p, s, "", "", false);
			}
		}
    	
    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Broadcast-To-Console")) {
        	for (String s: newmessage) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', s
                	.replaceAll("%player%", "Player name")
                    .replaceAll("%target%", "Player name")
                ));
        	}
        }
	}

}