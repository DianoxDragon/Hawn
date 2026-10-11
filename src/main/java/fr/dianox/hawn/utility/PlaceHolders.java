package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.DelaychatCommand;
import fr.dianox.hawn.command.commands.PingCommand;
import fr.dianox.hawn.command.commands.VanishCommand;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.server.Tps;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolders {

	// Every %bungee_<server>% of the line, not only the first one
	private static String replaceBungeeServers(String str) {
		int start = str.indexOf("%bungee_");

		while (start >= 0) {
			int end = str.indexOf('%', start + 8);
			if (end < 0) {
				break;
			}

			String server = str.substring(start + 8, end);
			Integer count = Main.getInstance().getBungApi().PlayerCountVar.get(server);
			String value = String.valueOf(count == null ? 0 : count);

			str = str.substring(0, start) + value + str.substring(end + 1);
			start = str.indexOf("%bungee_", start + value.length());
		}

		return str;
	}

	// The vanished players are not counted: the counter would give them away
	private static long visible(java.util.Collection<? extends Player> players) {
		long count = 0;
		for (Player player : players) {
			if (!VanishCommand.player_list_vanish.contains(player)) {
				count++;
			}
		}
		return count;
	}

	// %online%, %max_players%, %staff_online%, %online_<world>% and, with a player, %player_world_online%
	private static String replaceOnline(String str, Player p) {
		if (!str.contains("%online") && !str.contains("%max_players%") && !str.contains("%staff_online%") && !str.contains("%player_world_online%")) {
			return str;
		}

		if (str.contains("%online%")) {
			str = str.replace("%online%", String.valueOf(visible(Bukkit.getOnlinePlayers())));
		}

		if (str.contains("%max_players%")) {
			str = str.replace("%max_players%", String.valueOf(Bukkit.getMaxPlayers()));
		}

		if (str.contains("%staff_online%")) {
			List<Player> staff = new ArrayList<>();
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("hawn.staff")) {
					staff.add(player);
				}
			}
			str = str.replace("%staff_online%", String.valueOf(visible(staff)));
		}

		if (p != null && str.contains("%player_world_online%")) {
			str = str.replace("%player_world_online%", String.valueOf(visible(p.getWorld().getPlayers())));
		}

		// Every %online_<world>% of the line; an unknown world stays as it is written
		int start = str.indexOf("%online_");
		while (start >= 0) {
			int end = str.indexOf('%', start + 8);
			if (end < 0) {
				break;
			}

			World world = Bukkit.getWorld(str.substring(start + 8, end));
			if (world == null) {
				start = str.indexOf("%online_", end + 1);
				continue;
			}

			String value = String.valueOf(visible(world.getPlayers()));
			str = str.substring(0, start) + value + str.substring(end + 1);
			start = str.indexOf("%online_", start + value.length());
		}

		return str;
	}

	// No cooldown (the player has not used the item yet): 0, not an error
	private static long secondsLeft(Long usedAt, int delay) {
		if (usedAt == null) {
			return 0;
		}

		return Math.max(0, usedAt / 1000L + delay - System.currentTimeMillis() / 1000L);
	}

	@SuppressWarnings("deprecation")
	public static String ReplaceMainplaceholderP(String str, Player p) {

	    if (str.contains("%bungee_total%")) {
	        if (Main.getInstance().getBungApi().PlayerCountVar.containsKey("ALL")) {
                str = str.replace("%bungee_total%", String.valueOf(Main.getInstance().getBungApi().PlayerCountVar.get("ALL")));
            } else {
                str = str.replace("%bungee_total%", "0");
            }
        }

        str = replaceBungeeServers(str);
        str = replaceOnline(str, p);

        if (str.contains("%prefix%")) {
            str = str.replace("%prefix%", ConfigMGeneral.getConfig().getString("General.Prefix"));
        }

        if (str.contains("%player%")) {
            str = str.replace("%player%", p.getName());
        }

        if (str.contains("%target%")) {
            str = str.replace("%target%", p.getName());
        }

        if (str.contains("%ping%")) {
            str = str.replace("%ping%", String.valueOf(PingCommand.getPing(p)));
        }

        if (str.contains("%DELAY%")) {
            str = str.replace("%DELAY%", String.valueOf(DelaychatCommand.delay));
        }

        if (str.contains("%tps%")) {
            str = str.replace("%tps%", String.valueOf(Tps.getTPS()));
        }

        if (str.contains("%timedelaypvcji%")) {
            str = str.replace("%timedelaypvcji%", String.valueOf(secondsLeft(Main.hiderCooldowns.get(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay"))));
        }
        
        if (str.contains("%timedelayfunguncji%")) {
            str = str.replace("%timedelayfunguncji%", String.valueOf(secondsLeft(Main.fungunCooldowns.get(p), SpecialCjiFunGun.getConfig().getInt("FunGun.Option.Item-Delay.Delay"))));
        }

        if (str.contains("%barmemory%")) {
            str = str.replace("%barmemory%", OtherUtils.MemoryUsageBar);
        }

        if (str.contains("%barcpu%")) {
            str = str.replace("%barcpu%", OtherUtils.CpuUsageBar);
        }

        if (str.contains("%bardisk%")) {
            str = str.replace("%bardisk%", OtherUtils.DiskUsageBar);
        }

        if (str.contains("%maxmemory%")) {
            str = str.replace("%maxmemory%", OtherUtils.maxmemoryv);
        }

        if (str.contains("%freememory%")) {
            str = str.replace("%freememory%", OtherUtils.freememoryv);
        }

        if (str.contains("%totalmemory%")) {
            str = str.replace("%totalmemory%", OtherUtils.totalmemoryv);
        }

        if (str.contains("%averagecpuload%")) {
            str = str.replace("%averagecpuload%", String.valueOf(OtherUtils.LoadAverange()));
        }

        if (str.contains("%cpuload%")) {
            str = str.replace("%cpuload%", String.valueOf(OtherUtils.getProcessCpuLoad()));
        }

        if (str.contains("%totalspace%")) {
            str = str.replace("%totalspace%", OtherUtils.totaldiskv);
        }

        if (str.contains("%freespace%")) {
            str = str.replace("%freespace%", OtherUtils.freespacev);
        }

        if (str.contains("%javaversion%")) {
            str = str.replace("%javaversion%", OtherUtils.javaver);
        }

        if (str.contains("%osversion%")) {
            str = str.replace("%osversion%", OtherUtils.ossystem);
        }

        if (str.contains("%checkupdatehawn%")) {
            str = str.replace("%checkupdatehawn%", Main.getVersionUpdate());
        }

        if (str.contains("%gethawnversion%")) {
            str = str.replace("%gethawnversion%", Main.getVersion());
        }

        if (str.contains("%serverversion%")) {
            str = str.replace("%serverversion%", Bukkit.getBukkitVersion() + " (" + Main.getInstance().getVersionClass().getVersionsS() + ")");
        }

        if (str.contains("%gettime%")) {
            str = str.replace("%gettime%", OtherUtils.getTime());
        }
        
        if (str.contains("%getdate%")) {
            str = str.replace("%getdate%", Main.date);
        }

        // Total player
        if (str.contains("%player_x%")) {
            str = str.replace("%player_x%", String.valueOf((int) p.getLocation().getX()));
        }
        
        if (str.contains("%player_y%")) {
            str = str.replace("%player_y%", String.valueOf((int) p.getLocation().getY()));
        }
        
        if (str.contains("%player_z%")) {
            str = str.replace("%player_z%", String.valueOf((int) p.getLocation().getZ()));
        }
        
        if (str.contains("%player_world%")) {
            str = str.replace("%player_world%", p.getWorld().getName());
        }
        
        if (str.contains("%player_uuid%")) {
            str = str.replace("%player_uuid%", p.getUniqueId().toString());
        }
        
        if (str.contains("%player_level%")) {
            str = str.replace("%player_level%", String.valueOf(p.getLevel()));
        }
        
        if (str.contains("%player_exp%")) {
            str = str.replace("%player_exp%", String.valueOf(p.getExp()));
        }
        
        if (str.contains("%player_exp_to_level%")) {
            str = str.replace("%player_exp_to_level%", String.valueOf(p.getExpToLevel()));
        }
        
        if (str.contains("%player_food_level%")) {
            str = str.replace("%player_food_level%", String.valueOf(p.getFoodLevel()));
        }
        
        if (str.contains("%player_health%")) {
            str = str.replace("%player_health%", String.valueOf(p.getHealth()));
        }
        
        if (str.contains("%player_health_scale%")) {
            str = str.replace("%player_health_scale%", String.valueOf(p.getHealthScale()));
        }
        
        if (str.contains("%player_bed_x%")) {
            str = str.replace("%player_bed_x%", p.getBedSpawnLocation() == null ? "" : String.valueOf(p.getBedSpawnLocation().getBlockX()));
        }
        
        if (str.contains("%player_bed_y%")) {
            str = str.replace("%player_bed_y%", p.getBedSpawnLocation() == null ? "" : String.valueOf(p.getBedSpawnLocation().getBlockY()));
        }
        
        if (str.contains("%player_bed_z%")) {
            str = str.replace("%player_bed_z%", p.getBedSpawnLocation() == null ? "" : String.valueOf(p.getBedSpawnLocation().getBlockZ()));
        }
        
        if (str.contains("%player_bed_world%")) {
            str = str.replace("%player_bed_world%", p.getBedSpawnLocation() == null || p.getBedSpawnLocation().getWorld() == null ? "" : p.getBedSpawnLocation().getWorld().getName());
        }
        
        if (str.contains("%player_biome%")) {
            str = str.replace("%player_biome%", String.valueOf(p.getLocation().getBlock().getBiome()));
        }
        
        if (str.contains("%player_ip%")) {
            str = str.replace("%player_ip%", String.valueOf(p.getAddress().getHostString()));
        }
        
        if (str.contains("%player_max_health%")) {
            str = str.replace("%player_max_health%", String.valueOf(p.getMaxHealth()));
        }
        
        if (str.contains("%player_max_health_rounded%")) {
            str = str.replace("%player_max_health_rounded%", String.valueOf((int) p.getMaxHealth()));
        }
        
        if (str.contains("%player_name%")) {
            str = str.replace("%player_name%", p.getName());
        }
        
        if (str.contains("%player_displayname%")) {
            str = str.replace("%player_displayname%", p.getDisplayName());
        }
        
        if (str.contains("%player_saturation%")) {
            str = str.replace("%player_saturation%", String.valueOf(p.getSaturation()));
        }
        
        if (str.contains("%worldtime%")) {
            str = str.replace("%worldtime%", String.valueOf(p.getWorld().getTime()));
        }
        
        if (str.contains("%hawn_player_first_join_date%")) {
            str = str.replace("%hawn_player_first_join_date%", ConfigPlayerGet.getFile(p.getUniqueId().toString()).getString("player_info.first_join"));
        }
        
        if (str.contains("%hawn_player_join_date%")) {
        	str = str.replace("%hawn_player_join_date%", ConfigPlayerGet.getFile(p.getUniqueId().toString()).getString("player_info.join_date"));
        }
        
        return str;
    }

    public static String ReplaceMainplaceholderC(String str) {

        if (str.contains("%bungee_total%")) {
            if (Main.getInstance().getBungApi().PlayerCountVar.containsKey("ALL")) {
                str = str.replace("%bungee_total%", String.valueOf(Main.getInstance().getBungApi().PlayerCountVar.get("ALL")));
            } else {
                str = str.replace("%bungee_total%", "0");
            }
        }

        str = replaceBungeeServers(str);
        str = replaceOnline(str, null);

        if (str.contains("%prefix%")) {
            str = str.replace("%prefix%", ConfigMGeneral.getConfig().getString("General.Prefix"));
        }

        if (str.contains("%player%")) {
            str = str.replace("%player%", "player name");
        }

        if (str.contains("%target%")) {
            str = str.replace("%target%", "player name");
        }

        if (str.contains("%ping%")) {
            str = str.replace("%ping%", "(ping unknow)");
        }

        if (str.contains("%DELAY%")) {
            str = str.replace("%DELAY%", String.valueOf(DelaychatCommand.delay));
        }

        if (str.contains("%tps%")) {
            str = str.replace("%tps%", String.valueOf(Tps.getTPS()));
        }

        if (str.contains("%timedelaypvcji%")) {
            str = str.replace("%timedelaypvcji%", String.valueOf(SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")));
        }

        if (str.contains("%barmemory%")) {
            str = str.replace("%barmemory%", OtherUtils.MemoryUsageBar);
        }

        if (str.contains("%barcpu%")) {
            str = str.replace("%barcpu%", OtherUtils.CpuUsageBar);
        }

        if (str.contains("%bardisk%")) {
            str = str.replace("%bardisk%", OtherUtils.DiskUsageBar);
        }

        if (str.contains("%maxmemory%")) {
            str = str.replace("%maxmemory%", OtherUtils.maxmemoryv);
        }

        if (str.contains("%freememory%")) {
            str = str.replace("%freememory%", OtherUtils.freememoryv);
        }

        if (str.contains("%totalmemory%")) {
            str = str.replace("%totalmemory%", OtherUtils.totalmemoryv);
        }

        if (str.contains("%averagecpuload%")) {
            str = str.replace("%averagecpuload%", String.valueOf(OtherUtils.LoadAverange()));
        }

        if (str.contains("%cpuload%")) {
            str = str.replace("%cpuload%", String.valueOf(OtherUtils.getProcessCpuLoad()));
        }

        if (str.contains("%totalspace%")) {
            str = str.replace("%totalspace%", OtherUtils.totaldiskv);
        }

        if (str.contains("%freespace%")) {
            str = str.replace("%freespace%", OtherUtils.freespacev);
        }

        if (str.contains("%javaversion%")) {
            str = str.replace("%javaversion%", OtherUtils.javaver);
        }

        if (str.contains("%osversion%")) {
            str = str.replace("%osversion%", OtherUtils.ossystem);
        }

        if (str.contains("%checkupdatehawn%")) {
            str = str.replace("%checkupdatehawn%", Main.getVersionUpdate());
        }

        if (str.contains("%gethawnversion%")) {
            str = str.replace("%gethawnversion%", Main.getVersion());
        }

        if (str.contains("%serverversion%")) {
        	str = str.replace("%serverversion%", Bukkit.getBukkitVersion() + " (" + Main.getInstance().getVersionClass().getVersionsS() + ")");
        }

        if (str.contains("%gettime%")) {
            str = str.replace("%gettime%", OtherUtils.getTime());
        }
        
        if (str.contains("%getdate%")) {
            str = str.replace("%getdate%", Main.date);
        }

        return str;
    }

}
