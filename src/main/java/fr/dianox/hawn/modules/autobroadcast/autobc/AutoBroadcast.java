package fr.dianox.hawn.modules.autobroadcast.autobc;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.XParse;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

// Planned through Tasks (since 1.5.2)
public class AutoBroadcast implements Runnable {

    @SuppressWarnings("unused")
    private final JavaPlugin pl;

    public AutoBroadcast(JavaPlugin pl) {
        this.pl = pl;
    }

    String msg = "";
    public static List <String> newmessage = new ArrayList <> ();

    public void run() {
    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total+1);
			
			msg = String.valueOf(Main.autobroadcast.get(value));
			
			SendBroadcast(msg);
		} else {
			// After the last message, the first one again: no turn without a message
			if (Main.curMsg > Main.autobroadcast_total) {
				Main.curMsg = 0;
			}

			msg = String.valueOf(Main.autobroadcast.get(Main.curMsg));
			
			SendBroadcast(msg);

			Main.curMsg++;
		}
    }

    private void SendBroadcast(String msg) {
    	newmessage.clear();
		
    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Header.Enable")) {
		    newmessage.addAll(AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Header.messages"));
    	}

	    newmessage.addAll(AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".message"));

    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Footer.Enable")) {
		    newmessage.addAll(AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Footer.messages"));
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
			if (!AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")
					&& !BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
				continue;
			}
			
			// The worlds of this message
			if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")
					&& !inWorlds(p.getWorld().getName(), AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list"))) {
				continue;
			}

			// Event
			// >> Send broadcast
			for (String s: newmessage) {
            	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
		            ConfigEventUtils.ExecuteEvent(p, s, "AUTOCENTER", "", false);
	            } else {
		            ConfigEventUtils.ExecuteEvent(p, s, "", "", false);
	            }
			}

			// Since 1.5.2: a sound, like the titles, the action bar and the boss bar
			if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".Sound")) {
				p.playSound(p.getLocation(), XParse.sound(AutoBroadcastConfig.getConfig().getString("Config.Messages.messages." + msg + ".Sound"), "Config.Messages.messages." + msg + ".Sound"), 1, 1);
			}
		}
    	
    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Broadcast-To-Console")) {
        	for (String s: newmessage) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', s
                	.replace("%player%", "Player name")
                    .replace("%target%", "Player name")
                ));
        	}
        }
	}

	/**
	 * The whole name: "world" is not "world_nether". A * stands for any text: "world*" is world, world_nether and world_the_end.
	 */
	static boolean inWorlds(String world, List<String> names) {
		for (String name : names) {
			if (name.contains("*")) {
				List<String> parts = new ArrayList<>();
				for (String part : name.split("\\*", -1)) {
					parts.add(Pattern.quote(part));
				}
				if (world.matches(String.join(".*", parts))) return true;
			} else if (world.equals(name)) {
				return true;
			}
		}
		return false;
	}
}
