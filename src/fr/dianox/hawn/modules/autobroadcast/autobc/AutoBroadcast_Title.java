package fr.dianox.hawn.modules.autobroadcast.autobc;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.Titles;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class AutoBroadcast_Title extends BukkitRunnable  {
	
	@SuppressWarnings("unused")
	private final JavaPlugin pl;
	  
	public AutoBroadcast_Title(JavaPlugin pl) { this.pl = pl; }

	String msg = "";

	public void run() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total_titles+1);
			
			msg = String.valueOf(Main.autobroadcast_titles.get(value));
			
			SendTitle(msg);
		} else {
			if (Main.curMsg_titles <= Main.autobroadcast_total_titles) {
				msg = String.valueOf(Main.autobroadcast_titles.get(Main.curMsg_titles));
				
				SendTitle(msg);
				
				Main.curMsg_titles++;
			} else {
				Main.curMsg_titles = 0;
			}
		}
	}
	
	private void SendTitle(String msg) {
		
		String title = "";
		String subtitle = "";
		
		if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
			title = AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Title.Message");
		}
		
		if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
			subtitle = AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".SubTitle.Message");
		}
		
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			// Permission
			if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Use-Permission-To-Get-Messages")) {
				if (!p.hasPermission("hawn.get.autobroadcast_titles")) {
					continue;
				}
			}
			
			// Check want
			String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
			        	
			if (!valuepo.equalsIgnoreCase("TRUE")) {
				continue;
			}
			
			// Check world
			if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.World.All_World")) {
				if (!BasicEventsPW.getAutoBroadcast_title().contains(p.getWorld().getName())) {
					continue;
				}
			}
			
			// Event
			// >> Send broadcast			
			Integer FadeIn = 0;
			Integer Stay = 0;
			Integer FadeOut = 0;
			
			if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".FadeIn")) {
				FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".FadeIn");
			} else {
				FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.FadeIn");
			}
				
			if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Stay")) {
				Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Stay");
			} else {
				Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Stay");
			}
				
			if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".FadeOut")) {
				FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".FadeOut");
			} else {
				FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.FadeOut");
			}
			
			if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
				if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
					Titles.sendTitle(p, FadeIn, Stay, FadeOut, title, subtitle);
				} else {
					Titles.sendTitle(p, FadeIn, Stay, FadeOut, title, " ");
				}
			} else {
				if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
					Titles.sendTitle(p, FadeIn, Stay, FadeOut, " ", subtitle);
				}
			}
			
			if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
				p.playSound(p.getLocation(), XSound.getSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound"), "Config.Titles.messages."+msg+".Sound"), 1, 1);
			}
		}
	}
	
}
