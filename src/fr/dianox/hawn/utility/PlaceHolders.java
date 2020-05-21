package fr.dianox.hawn.utility;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.DelaychatCommand;
import fr.dianox.hawn.command.commands.PingCommand;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.server.Tps;
import me.robin.battlelevels.api.BattleLevelsAPI;

public class PlaceHolders {

	@SuppressWarnings("deprecation")
	public static String ReplaceMainplaceholderP(String str, Player p) {

	    if (str.contains("%bungee_total%")) {
	        if (Main.getInstance().getBungApi().PlayerCountVar.containsKey("ALL")) {
                str = str.replaceAll("%bungee_total%", String.valueOf(Main.getInstance().getBungApi().PlayerCountVar.get("ALL")));
            } else {
                str = str.replaceAll("%bungee_total%", "0");
            }
        }

        if (str.contains("%bungee_") && str.contains("%")) {
            String server;
            server = StringUtils.substringBetween(str, "%bungee_", "%");

            if (Main.getInstance().getBungApi().PlayerCountVar.containsKey(server)) {
                str = str.replaceAll("%bungee_" + server + "%", String.valueOf(Main.getInstance().getBungApi().PlayerCountVar.get(server)));
            } else {
                str = str.replaceAll("%bungee_" + server + "%", "0");
            }
        }

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
        	long secondsLeft = 0;
        	try {
        		secondsLeft = Main.hiderCooldowns.get(p) / 1000L + SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay") - System.currentTimeMillis() / 1000L;
        	} catch (Exception e) {
        		Bukkit.getConsoleSender().sendMessage("§cThe compass, well something does not work actually, please reload the server");
        	}
            str = str.replaceAll("%timedelaypvcji%", String.valueOf(secondsLeft));
        }
        
        if (str.contains("%timedelayfunguncji%")) {
        	long secondsLeft = 0;
        	try {
        		secondsLeft = Main.fungunCooldowns.get(p) / 1000L + SpecialCjiFunGun.getConfig().getInt("FunGun.Option.Item-Delay.Delay") - System.currentTimeMillis() / 1000L;
        	} catch (Exception e) {
        		Bukkit.getConsoleSender().sendMessage("§cThe fungun, well something does not work actually, please reload the server");
        	}
            str = str.replaceAll("%timedelayfunguncji%", String.valueOf(secondsLeft));
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
            str = str.replaceAll("%totalspace%", OtherUtils.totaldiskv);
        }

        if (str.contains("%freespace%")) {
            str = str.replaceAll("%freespace%", OtherUtils.freespacev);
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
            str = str.replaceAll("%serverversion%", Bukkit.getBukkitVersion() + " (" + Main.getInstance().getVersionClass().getVersionsS() + ")");
        }

        if (str.contains("%gettime%")) {
            str = str.replaceAll("%gettime%", OtherUtils.getTime());
        }
        
        if (str.contains("%getdate%")) {
            str = str.replaceAll("%getdate%", Main.date);
        }

        // Total player
        if (str.contains("%player_x%")) {
            str = str.replaceAll("%player_x%", String.valueOf((int) p.getLocation().getX()));
        }
        
        if (str.contains("%player_y%")) {
            str = str.replaceAll("%player_y%", String.valueOf((int) p.getLocation().getY()));
        }
        
        if (str.contains("%player_z%")) {
            str = str.replaceAll("%player_z%", String.valueOf((int) p.getLocation().getZ()));
        }
        
        if (str.contains("%player_world%")) {
            str = str.replaceAll("%player_world%", p.getWorld().getName());
        }
        
        if (str.contains("%player_uuid%")) {
            str = str.replaceAll("%player_uuid%", p.getUniqueId().toString());
        }
        
        if (str.contains("%player_level%")) {
            str = str.replaceAll("%player_level%", String.valueOf(p.getLevel()));
        }
        
        if (str.contains("%player_exp%")) {
            str = str.replaceAll("%player_exp%", String.valueOf(p.getExp()));
        }
        
        if (str.contains("%player_exp_to_level%")) {
            str = str.replaceAll("%player_exp_to_level%", String.valueOf(p.getExpToLevel()));
        }
        
        if (str.contains("%player_food_level%")) {
            str = str.replaceAll("%player_food_level%", String.valueOf(p.getFoodLevel()));
        }
        
        if (str.contains("%player_health%")) {
            str = str.replaceAll("%player_health%", String.valueOf(p.getHealth()));
        }
        
        if (str.contains("%player_health_scale%")) {
            str = str.replaceAll("%player_health_scale%", String.valueOf(p.getHealthScale()));
        }
        
        if (str.contains("%player_bed_x%")) {
            str = str.replaceAll("%player_bed_x%", String.valueOf(p.getBedLocation().getBlockX()));
        }
        
        if (str.contains("%player_bed_y%")) {
            str = str.replaceAll("%player_bed_y%", String.valueOf(p.getBedLocation().getBlockY()));
        }
        
        if (str.contains("%player_bed_z%")) {
            str = str.replaceAll("%player_bed_z%", String.valueOf(p.getBedLocation().getBlockZ()));
        }
        
        if (str.contains("%player_bed_world%")) {
            str = str.replaceAll("%player_bed_world%", String.valueOf(p.getBedLocation().getWorld()));
        }
        
        if (str.contains("%player_biome%")) {
            str = str.replaceAll("%player_biome%", String.valueOf(p.getLocation().getBlock().getBiome()));
        }
        
        if (str.contains("%player_ip%")) {
            str = str.replaceAll("%player_ip%", String.valueOf(p.getAddress().getHostString()));
        }
        
        if (str.contains("%player_max_health%")) {
            str = str.replaceAll("%player_max_health%", String.valueOf(p.getMaxHealth()));
        }
        
        if (str.contains("%player_max_health_rounded%")) {
            str = str.replaceAll("%player_max_health_rounded%", String.valueOf(Integer.valueOf((int) p.getMaxHealth())));
        }
        
        if (str.contains("%player_name%")) {
            str = str.replaceAll("%player_name%", p.getName());
        }
        
        if (str.contains("%player_displayname%")) {
            str = str.replaceAll("%player_displayname%", p.getDisplayName());
        }
        
        if (str.contains("%player_saturation%")) {
            str = str.replaceAll("%player_saturation%", String.valueOf(p.getSaturation()));
        }
        
        if (str.contains("%worldtime%")) {
            str = str.replaceAll("%worldtime%", String.valueOf(p.getWorld().getTime()));
        }
        
        if (str.contains("%hawn_player_first_join_date%")) {
            str = str.replaceAll("%hawn_player_first_join_date%", ConfigPlayerGet.getFile(p.getUniqueId().toString()).getString("player_info.first_join"));
        }
        
        if (str.contains("%hawn_player_join_date%")) {
        	str = str.replaceAll("%hawn_player_join_date%", ConfigPlayerGet.getFile(p.getUniqueId().toString()).getString("player_info.join_date"));
        }
        
        return str;
    }

    public static String ReplaceMainplaceholderC(String str) {

        if (str.contains("%bungee_total%")) {
            if (Main.getInstance().getBungApi().PlayerCountVar.containsKey("ALL")) {
                str = str.replaceAll("%bungee_total%", String.valueOf(Main.getInstance().getBungApi().PlayerCountVar.get("ALL")));
            } else {
                str = str.replaceAll("%bungee_total%", "0");
            }
        }

        if (str.contains("%bungee_") && str.contains("%")) {
            String server;
            server = StringUtils.substringBetween(str, "%bungee_", "%");

            if (Main.getInstance().getBungApi().PlayerCountVar.containsKey(server)) {
                str = str.replaceAll("%bungee_" + server + "%", String.valueOf(Main.getInstance().getBungApi().PlayerCountVar.get(server)));
            } else {
                str = str.replaceAll("%bungee_" + server + "%", "0");
            }
        }

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
            str = str.replaceAll("%totalspace%", OtherUtils.totaldiskv);
        }

        if (str.contains("%freespace%")) {
            str = str.replaceAll("%freespace%", OtherUtils.freespacev);
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
        	str = str.replaceAll("%serverversion%", Bukkit.getBukkitVersion() + " (" + Main.getInstance().getVersionClass().getVersionsS() + ")");
        }

        if (str.contains("%gettime%")) {
            str = str.replaceAll("%gettime%", OtherUtils.getTime());
        }
        
        if (str.contains("%getdate%")) {
            str = str.replaceAll("%getdate%", Main.date);
        }

        return str;
    }
    
    public static String BattleLevelPO(String str, Player p) {
        if (str.contains("%h_battlelevels_level%")) {
            str = str.replaceAll("%h_battlelevels_level%", String.valueOf(BattleLevelsAPI.getLevel(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_score%")) {
            str = str.replaceAll("%h_battlelevels_score%", String.valueOf(BattleLevelsAPI.getScore(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_bar%")) {
            str = str.replaceAll("%h_battlelevels_bar%", BattleLevelsAPI.getProgressBar(p.getUniqueId()));
        }

        if (str.contains("%h_battlelevels_topstreak%")) {
            str = str.replaceAll("%h_battlelevels_topstreak%", String.valueOf(BattleLevelsAPI.getTopKillstreak(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_killstreak%")) {
            str = str.replaceAll("%h_battlelevels_killstreak%", String.valueOf(BattleLevelsAPI.getKillstreak(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_kills%")) {
            str = str.replaceAll("%h_battlelevels_kills%", String.valueOf(BattleLevelsAPI.getKills(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_deaths%")) {
            str = str.replaceAll("%h_battlelevels_deaths%", String.valueOf(BattleLevelsAPI.getDeaths(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_kdr%")) {
            str = str.replaceAll("%h_battlelevels_kdr%", String.valueOf(BattleLevelsAPI.getKdr(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_booster%")) {
            str = str.replaceAll("%h_battlelevels_booster%", String.valueOf(BattleLevelsAPI.getBoosterInMinutes(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_boosterenabled%")) {
            str = str.replaceAll("%h_battlelevels_boosterenabled%", String.valueOf(BattleLevelsAPI.hasBooster(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_globalbooster%")) {
            str = str.replaceAll("%h_battlelevels_globalbooster%", String.valueOf(BattleLevelsAPI.getGlobalBoosterInMinutes()));
        }

        if (str.contains("%h_battlelevels_globalboosterenabled%")) {
            str = str.replaceAll("%h_battlelevels_globalboosterenabled%", String.valueOf(BattleLevelsAPI.isGlobalBoosterEnabled()));
        }

        if (str.contains("%h_battlelevels_neededfornext%")) {
            str = str.replaceAll("%h_battlelevels_neededfornext%", String.valueOf(BattleLevelsAPI.getNeededForNext(p.getUniqueId())));
        }

        if (str.contains("%h_battlelevels_neededfornextremaining%")) {
            str = str.replaceAll("h_battlelevels_neededfornextremaining", String.valueOf(BattleLevelsAPI.getNeededForNextRemaining(p.getUniqueId())));
        }


        return str;
    }

	
}
