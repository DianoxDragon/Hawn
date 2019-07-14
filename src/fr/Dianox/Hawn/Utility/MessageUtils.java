package fr.Dianox.Hawn.Utility;

import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Commands.PingCommand;
import fr.Dianox.Hawn.Commands.Features.Chat.DelaychatCommand;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Server.Tps;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

public class MessageUtils {
	
	public static String ReplaceMainplaceholderP(String str, Player p) {
		
		if (str.contains("%prefix%")) {
			str = str.replaceAll("%prefix%", ConfigMGeneral.getConfig().getString("General.Prefix"));
		}
		
		if (str.contains("%player%")) {
			str = str.replaceAll("%player%", p.getName());
		}
		
		if (str.contains("%target%")) {
			str = str.replaceAll("%target%", p.getName());
		}
		
		if (str.contains("%ping%")) {
			str = str.replaceAll("%ping%", String.valueOf(PingCommand.getPing(p)));
		}
		
		if (str.contains("%DELAY%")) {
			str = str.replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay));
		}
		
		if (str.contains("%tps%")) {
			str = str.replaceAll("%tps%", String.valueOf(Tps.getTPS()));
		}
		
		if (str.contains("%timedelaypvcji%")) {
			long secondsLeft = ((Long)Main.hiderCooldowns.get(p)).longValue() / 1000L + SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay") - System.currentTimeMillis() / 1000L;
			str = str.replaceAll("%timedelaypvcji%", String.valueOf(secondsLeft));
		}
		
		if (str.contains("%barmemory%")) {
			str = str.replaceAll("%barmemory%", OtherUtils.MemoryUsageBar);
		}
		
		if (str.contains("%barcpu%")) {
			str = str.replaceAll("%barcpu%", OtherUtils.CpuUsageBar);
		}
		
		if (str.contains("%bardisk%")) {
			str = str.replaceAll("%bardisk%", OtherUtils.DiskUsageBar);
		}
		
		if (str.contains("%maxmemory%")) {
			str = str.replaceAll("%maxmemory%", OtherUtils.maxmemoryv);
		}
		
		if (str.contains("%freememory%")) {
			str = str.replaceAll("%freememory%", OtherUtils.freememoryv);
		}
		
		if (str.contains("%totalmemory%")) {
			str = str.replaceAll("%totalmemory%", OtherUtils.totalmemoryv);
		}
		
		if (str.contains("%averagecpuload%")) {
			str = str.replaceAll("%averagecpuload%", String.valueOf(OtherUtils.LoadAverange()));
		}
		
		if (str.contains("%cpuload%")) {
			str = str.replaceAll("%cpuload%", String.valueOf(OtherUtils.getProcessCpuLoad()));
		}
		
		if (str.contains("%totalspace%")) {
			str = str.replaceAll("%totalspace%",  OtherUtils.totalspaceusablev);
		}
		
		if (str.contains("%freespace%")) {
			str = str.replaceAll("%freespace%",  OtherUtils.freespacev);
		}
		
		if (str.contains("%totalspaceusable%")) {
			str = str.replaceAll("%totalspaceusable%", OtherUtils.totaldiskv);
		}
		
		if (str.contains("%javaversion%")) {
			str = str.replaceAll("%javaversion%", OtherUtils.javaver);
		}
		
		if (str.contains("%osversion%")) {
			str = str.replaceAll("%osversion%", OtherUtils.ossystem);
		}
		
		if (str.contains("%checkupdatehawn%")) {
			str = str.replaceAll("%checkupdatehawn%", Main.getVersionUpdate());
		}
		
		if (str.contains("%gethawnversion%")) {
			str = str.replaceAll("%gethawnversion%", Main.getVersion());
		}
		
		if (str.contains("%serverversion%")) {
			str = str.replaceAll("%serverversion%", String.valueOf(Bukkit.getBukkitVersion() + " ("+VersionUtils.getVersionS()+")"));
		}
		
		if (str.contains("%gettime%")) {
			str = str.replaceAll("%gettime%", OtherUtils.getTime());
		}
		
		return str;
	}
	
	public static String ReplaceMainplaceholderC(String str) {
		
		if (str.contains("%prefix%")) {
			str = str.replaceAll("%prefix%", ConfigMGeneral.getConfig().getString("General.Prefix"));
		}
		
		if (str.contains("%player%")) {
			str = str.replaceAll("%player%", "player name");
		}
		
		if (str.contains("%target%")) {
			str = str.replaceAll("%target%", "player name");
		}
		
		if (str.contains("%ping%")) {
			str = str.replaceAll("%ping%", "(ping unknow)");
		}
		
		if (str.contains("%DELAY%")) {
			str = str.replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay));
		}
		
		if (str.contains("%tps%")) {
			str = str.replaceAll("%tps%", String.valueOf(Tps.getTPS()));
		}
		
		if (str.contains("%timedelaypvcji%")) {
			str = str.replaceAll("%timedelaypvcji%", String.valueOf(SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")));
		}
		
		if (str.contains("%barmemory%")) {
			str = str.replaceAll("%barmemory%", OtherUtils.MemoryUsageBar);
		}
		
		if (str.contains("%barcpu%")) {
			str = str.replaceAll("%barcpu%", OtherUtils.CpuUsageBar);
		}
		
		if (str.contains("%bardisk%")) {
			str = str.replaceAll("%bardisk%", OtherUtils.DiskUsageBar);
		}
		
		if (str.contains("%maxmemory%")) {
			str = str.replaceAll("%maxmemory%", OtherUtils.maxmemoryv);
		}
		
		if (str.contains("%freememory%")) {
			str = str.replaceAll("%freememory%", OtherUtils.freememoryv);
		}
		
		if (str.contains("%totalmemory%")) {
			str = str.replaceAll("%totalmemory%", OtherUtils.totalmemoryv);
		}
		
		if (str.contains("%averagecpuload%")) {
			str = str.replaceAll("%averagecpuload%", String.valueOf(OtherUtils.LoadAverange()));
		}
		
		if (str.contains("%cpuload%")) {
			str = str.replaceAll("%cpuload%", String.valueOf(OtherUtils.getProcessCpuLoad()));
		}
		
		if (str.contains("%totalspace%")) {
			str = str.replaceAll("%totalspace%",  OtherUtils.totalspaceusablev);
		}
		
		if (str.contains("%freespace%")) {
			str = str.replaceAll("%freespace%",  OtherUtils.freespacev);
		}
		
		if (str.contains("%totalspaceusable%")) {
			str = str.replaceAll("%totalspaceusable%", OtherUtils.totaldiskv);
		}
		
		if (str.contains("%javaversion%")) {
			str = str.replaceAll("%javaversion%", OtherUtils.javaver);
		}
		
		if (str.contains("%osversion%")) {
			str = str.replaceAll("%osversion%", OtherUtils.ossystem);
		}
		
		if (str.contains("%checkupdatehawn%")) {
			str = str.replaceAll("%checkupdatehawn%", Main.getVersionUpdate());
		}
		
		if (str.contains("%gethawnversion%")) {
			str = str.replaceAll("%gethawnversion%", Main.getVersion());
		}
		
		if (str.contains("%serverversion%")) {
			str = str.replaceAll("%serverversion%", String.valueOf(Bukkit.getBukkitVersion() + " ("+VersionUtils.getVersionS()+")"));
		}
		
		if (str.contains("%gettime%")) {
			str = str.replaceAll("%gettime%", OtherUtils.getTime());
		}
		
		return str;
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void ReplaceCharMessagePlayer(String str, Player player) {
		Player p = player;
		
		if (str.startsWith("json:")) {
			str = str.replace("json:", "");
			str = ReplaceMainplaceholderP(str, player);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			BaseComponent[] bc = ComponentSerializer.parse(str);
			for (Iterator localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext();) {
				p = (Player)localIterator.next();
				p.spigot().sendMessage(bc);
			}
		} else if (str.startsWith("[send-title]: ")) {
			str = str.replace("[send-title]: ", "");
			str = str.replaceAll("&", "§");
			
			str = ReplaceMainplaceholderP(str, player);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			Boolean activate = false;
			
			String title = "";
			String subtitle = "";
													
			if (str.contains("//n")) {
				String[] parts = str.split("//n");
				title = parts[0];
				subtitle = parts[1];
				
				title = title.replaceAll("//n", "");
				subtitle = subtitle.replaceAll("//n", "");
				
				activate = true;
			}
			
			if (activate == false) {
				TitleUtils.sendTitle(p, 20, 150, 75, str);
			} else {
				TitleUtils.sendTitle(p, 20, 150, 75, title);
				TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
			}
		} else if (str.startsWith("[send-title[")) {
			str = str.replace("[send-title[", "");
			
			str = ReplaceMainplaceholderP(str, player);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			String[] parts = str.split("]]: ");
			
			Boolean activate = false;
			
			String title = "";
			String subtitle = "";
													
			if (str.contains("//n")) {
				String[] part = parts[1].split("//n");
				title = part[0];
				subtitle = part[1];
				
				title = title.replaceAll("//n", "");
				subtitle = subtitle.replaceAll("//n", "");
				
				activate = true;
			}
			
			if (activate == false) {
				TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
			} else {
				TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
				TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
			}
		} else if (str.startsWith("[send-actionbar]: ")) {
			str = str.replace("[send-actionbar]: ", "");
			str = str.replaceAll("&", "§");
			
			str = ReplaceMainplaceholderP(str, player);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			ActionBar.sendActionBar(p, str);
		} else if (str.startsWith("[send-actionbar[")) {
			str = str.replace("[send-actionbar[", "");
			
			str = ReplaceMainplaceholderP(str, player);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			String[] parts = str.split("]]: ");
			
			ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
		} else {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			str = ReplaceMainplaceholderP(str, player);
			str = str.replaceAll("&", "§");
			
			p.sendMessage(str);
		}
	}

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void ReplaceCharBroadcastPlayer(String str, Player player) {
		if (str.startsWith("json:")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("json:", "");
				str = ReplaceMainplaceholderP(str, player);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				BaseComponent[] bc = ComponentSerializer.parse(str);
				for (Iterator localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext();) {
					p = (Player)localIterator.next();
					p.spigot().sendMessage(bc);
				}
			}
		} else if (str.startsWith("[send-title]: ")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-title]: ", "");
				str = str.replaceAll("&", "§");
				
				str = ReplaceMainplaceholderP(str, player);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				Boolean activate = false;
				
				String title = "";
				String subtitle = "";
														
				if (str.contains("//n")) {
					String[] parts = str.split("//n");
					title = parts[0];
					subtitle = parts[1];
					
					title = title.replaceAll("//n", "");
					subtitle = subtitle.replaceAll("//n", "");
					
					activate = true;
				}
				
				if (activate == false) {
					TitleUtils.sendTitle(p, 20, 150, 75, str);
				} else {
					TitleUtils.sendTitle(p, 20, 150, 75, title);
					TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
				}
			}
		} else if (str.startsWith("[send-title[")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-title[", "");
				
				str = ReplaceMainplaceholderP(str, player);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				String[] parts = str.split("]]: ");
				
				Boolean activate = false;
				
				String title = "";
				String subtitle = "";
														
				if (str.contains("//n")) {
					String[] part = parts[1].split("//n");
					title = part[0];
					subtitle = part[1];
					
					title = title.replaceAll("//n", "");
					subtitle = subtitle.replaceAll("//n", "");
					
					activate = true;
				}
				
				if (activate == false) {
					TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
				} else {
					TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
					TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
				}
			}
		} else if (str.startsWith("[send-actionbar]: ")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-actionbar]: ", "");
				str = str.replaceAll("&", "§");
				
				str = ReplaceMainplaceholderP(str, player);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				ActionBar.sendActionBar(p, str);
			}
		} else if (str.startsWith("[send-actionbar[")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-actionbar[", "");
				
				str = ReplaceMainplaceholderP(str, player);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				String[] parts = str.split("]]: ");
				
				ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
			}
		} else {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(player, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(player, str);
			}
			
			str = ReplaceMainplaceholderP(str, player);
			str = str.replaceAll("&", "§");
			
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				p.sendMessage(str);
			}
		}
	}
	
	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static void ReplaceCharBroadcastNoPlayer(String str) {
		if (str.startsWith("json:")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("json:", "");
				str = ReplaceMainplaceholderP(str, p);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				BaseComponent[] bc = ComponentSerializer.parse(str);
				for (Iterator localIterator = Bukkit.getOnlinePlayers().iterator(); localIterator.hasNext();) {
			      p = (Player)localIterator.next();
			      p.spigot().sendMessage(bc);
			    }
			}
		} else if (str.startsWith("[send-title]: ")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-title]: ", "");
				str = str.replaceAll("&", "§");
				
				str = ReplaceMainplaceholderP(str, p);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				Boolean activate = false;
				
				String title = "";
				String subtitle = "";
														
				if (str.contains("//n")) {
					String[] parts = str.split("//n");
					title = parts[0];
					subtitle = parts[1];
					
					title = title.replaceAll("//n", "");
					subtitle = subtitle.replaceAll("//n", "");
					
					activate = true;
				}
				
				if (activate == false) {
					TitleUtils.sendTitle(p, 20, 150, 75, str);
				} else {
					TitleUtils.sendTitle(p, 20, 150, 75, title);
					TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
				}
			}
		} else if (str.startsWith("[send-title[")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-title[", "");
				
				str = ReplaceMainplaceholderP(str, p);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				String[] parts = str.split("]]: ");
				
				Boolean activate = false;
				
				String title = "";
				String subtitle = "";
														
				if (str.contains("//n")) {
					String[] part = parts[1].split("//n");
					title = part[0];
					subtitle = part[1];
					
					title = title.replaceAll("//n", "");
					subtitle = subtitle.replaceAll("//n", "");
					
					activate = true;
				}
				
				if (activate == false) {
					TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
				} else {
					TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
					TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
				}
			}
		} else if (str.startsWith("[send-actionbar]: ")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-actionbar]: ", "");
				str = str.replaceAll("&", "§");
				
				str = ReplaceMainplaceholderP(str, p);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				ActionBar.sendActionBar(p, str);
			}
		} else if (str.startsWith("[send-actionbar[")) {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				str = str.replace("[send-actionbar[", "");
				
				str = ReplaceMainplaceholderP(str, p);
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				String[] parts = str.split("]]: ");
				
				ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
			}
		} else {
			for (Player p: Bukkit.getServer().getOnlinePlayers()) {
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					str = PlaceholderAPI.setPlaceholders(p, str);
				}
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
					str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
				}
				
				str = ReplaceMainplaceholderP(str, p);
				str = str.replaceAll("&", "§");
				
				p.sendMessage(str);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void ReplaceCharBroadcastGeneral(String str, Player p) {
		if (str.startsWith("json:")) {
			str = str.replace("json:", "");
			str = ReplaceMainplaceholderP(str, p);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			BaseComponent[] bc = ComponentSerializer.parse(str);
			Bukkit.spigot().broadcast(bc);
			
			StringBuilder sb = new StringBuilder();
		    for (BaseComponent b : bc) {
		    	sb.append(b.toLegacyText());
		    }
		    
		    Bukkit.getConsoleSender().sendMessage(sb.toString());
		} else if (str.startsWith("[send-title]: ")) {
			str = str.replace("[send-title]: ", "");
			str = str.replaceAll("&", "§");
			
			str = ReplaceMainplaceholderP(str, p);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			Boolean activate = false;
			
			String title = "";
			String subtitle = "";
													
			if (str.contains("//n")) {
				String[] parts = str.split("//n");
				title = parts[0];
				subtitle = parts[1];
				
				title = title.replaceAll("//n", "");
				subtitle = subtitle.replaceAll("//n", "");
				
				activate = true;
			}
			
			if (activate == false) {
				for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
					TitleUtils.sendTitle(p1, 20, 150, 75, str);
				}
			} else {
				for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
					TitleUtils.sendTitle(p1, 20, 150, 75, title);
					TitleUtils.sendSubtitle(p1, 20, 150, 75, subtitle);
				}
			}
		} else if (str.startsWith("[send-title[")) {
			str = str.replace("[send-title[", "");
			
			str = ReplaceMainplaceholderP(str, p);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			String[] parts = str.split("]]: ");
			
			Boolean activate = false;
			
			String title = "";
			String subtitle = "";
													
			if (str.contains("//n")) {
				String[] part = parts[1].split("//n");
				title = part[0];
				subtitle = part[1];
				
				title = title.replaceAll("//n", "");
				subtitle = subtitle.replaceAll("//n", "");
				
				activate = true;
			}
			
			if (activate == false) {
				for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
					TitleUtils.sendTitle(p1, 20, Integer.valueOf(parts[0]), 75, parts[1]);
				}
			} else {
				for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
					TitleUtils.sendTitle(p1, 20, Integer.valueOf(parts[0]), 75, title);
					TitleUtils.sendSubtitle(p1, 20, Integer.valueOf(parts[0]), 75, subtitle);
				}
			}
		} else if (str.startsWith("[send-actionbar]: ")) {
			str = str.replace("[send-actionbar]: ", "");
			str = str.replaceAll("&", "§");
			
			str = ReplaceMainplaceholderP(str, p);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
				ActionBar.sendActionBar(p1, str);
			}
		} else if (str.startsWith("[send-actionbar[")) {
			str = str.replace("[send-actionbar[", "");
			
			str = ReplaceMainplaceholderP(str, p);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			String[] parts = str.split("]]: ");
			
			for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
				ActionBar.sendActionBar(p1, parts[1], Integer.valueOf(parts[0]));
			}
		} else {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(p, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, str);
			}
			
			str = ReplaceMainplaceholderP(str, p);
			str = str.replaceAll("&", "§");
			
			Bukkit.broadcastMessage(str);
		}
	}
	
	public static void ReplaceMessageForConsole(String str) {
		if (str.startsWith("json:")) {
			str = str.replace("json:", "");
			str = ReplaceMainplaceholderC(str);
				
			BaseComponent[] bc = ComponentSerializer.parse(str);
				
			StringBuilder sb = new StringBuilder();
		    for (BaseComponent b : bc) {
		    	sb.append(b.toLegacyText());
		    }
			    
		    Bukkit.getConsoleSender().sendMessage(sb.toString());
		} else {
			str = ReplaceMainplaceholderC(str);
			str = str.replaceAll("&", "§");
			
			Bukkit.getConsoleSender().sendMessage(str);
		}
	}
	
	public static void ReplaceMessageForConsolePingCommand(String str, CommandSender sender, Player target) {
		if (str.startsWith("json:")) {
			str = str.replace("json:", "");
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(target, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, str);
			}
			
			str = ReplaceMainplaceholderP(str, target);
			str = str.replaceAll("&", "§");
				
			BaseComponent[] bc = ComponentSerializer.parse(str);
				
			StringBuilder sb = new StringBuilder();
		    for (BaseComponent b : bc) {
		    	sb.append(b.toLegacyText());
		    }
			    
		    Bukkit.getConsoleSender().sendMessage(sb.toString());
		} else {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				str = PlaceholderAPI.setPlaceholders(target, str);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
				str = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, str);
			}
			
			str = ReplaceMainplaceholderP(str, target);
			str = str.replaceAll("&", "§");
			
			Bukkit.getConsoleSender().sendMessage(str);
		}
	}
	
	// Just for some messages
	
	// >> No permissions
	public static void MessageNoPermission(Player player, String p) {
		if (ConfigMOStuff.getConfig().getBoolean("Error.No-Permissions.Enable")) {
			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Permissions.Messages")) {
				
				msg = msg.replaceAll("%noperm%", p);
				
				ReplaceCharMessagePlayer(msg, player);
			}
		}
	}
	
	// > No Spawn
	public static void MessageNoSpawn(Player player) {
		if (ConfigMOStuff.getConfig().getBoolean("Error.No-Spawn.Enable")) {
			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Spawn.Messages")) {
				ReplaceCharMessagePlayer(msg, player);
			}
		}
	}
	
	// > No Player
	public static void PlayerDoesntExist(Player player) {
		if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
				ReplaceCharMessagePlayer(msg, player);
			}
		}
	}
	
	// > No Page found
	public static void NoPageFound(Player player) {
		if (ConfigMOStuff.getConfig().getBoolean("Error.No-Page-Found.Enable")) {
			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Page-Found.Messages")) {
				ReplaceCharMessagePlayer(msg, player);
			}
		}
	}
	
	// > No category
	public static void NoCategory(Player player) {
		if (ConfigMOStuff.getConfig().getBoolean("Error.No-Category.Enable")) {
			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Category.Messages")) {
				ReplaceCharMessagePlayer(msg, player);
			}
		}
	}
	
	// > Use number
	public static void UseNumber(Player player) {
		if (ConfigMOStuff.getConfig().getBoolean("Error.Use-Number.Enable")) {
			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Use-Number.Messages")) {
				ReplaceCharMessagePlayer(msg, player);
			}
		}
	}

}
