package fr.dianox.hawn.modules.autobroadcast.autobc;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import me.clip.placeholderapi.PlaceholderAPI;

public class AutoBroadcast_AB extends BukkitRunnable {
	
	@SuppressWarnings("unused")
	private final JavaPlugin pl;
	  
	public AutoBroadcast_AB(JavaPlugin pl) { this.pl = pl; }

	String msg = "";
	
	public void run() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total_ab+1);
			
			msg = String.valueOf(Main.autobroadcast_ab.get(value));
			
			SendActionBar(msg);
		} else {
			if (Main.curMsg_ab <= Main.autobroadcast_total_ab) {
				msg = String.valueOf(Main.autobroadcast_ab.get(Main.curMsg_ab));
				
				SendActionBar(msg);
				
				Main.curMsg_ab++;
			} else {
				Main.curMsg_ab = 0;
			}
		}
	}
	
	private void SendActionBar(String msg) {
		String message = AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Message");
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			// Permission
			if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Use-Permission-To-Get-Messages")) {
				if (!p.hasPermission("hawn.get.autobroadcast_ab")) {
					continue;
				}
			}
			
			// Check want
			String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
			        	
			if (!valuepo.equalsIgnoreCase("TRUE")) {
				continue;
			}
			
			// Check world
			if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.World.All_World")) {
				if (!BasicEventsPW.getAutoBroadcast_ab().contains(p.getWorld().getName())) {
					continue;
				}
			}
			
			// Event
			// >> Format
			message = PlaceHolders.ReplaceMainplaceholderP(message, p);
			
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				message = PlaceholderAPI.setPlaceholders(p, message);
			}
			
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				message = PlaceHolders.BattleLevelPO(message, p);
			}
			
			message = message.replaceAll("&", "§");
			
			// >> Send broadcast			
			if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
				ActionBar.sendActionBar(p, message, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
			} else {
				ActionBar.sendActionBar(p, message, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
			}
			
			if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
				p.playSound(p.getLocation(), XSound.getSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound"), "Config.Action-Bar.messages."+msg+".Sound"), 1, 1);
			}
		}
	}

}
