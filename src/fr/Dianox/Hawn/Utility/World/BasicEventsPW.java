package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerEventsConfig;
import fr.Dianox.Hawn.Utility.Config.Events.ProtectionPlayerConfig;
import fr.Dianox.Hawn.Utility.Config.Events.VoidTPConfig;

public class BasicEventsPW {
	
	// Void TP
	public static List<String> voidtp_world = new ArrayList<String>();
	
	public static void setWGetWorldforVOIDTP() {
		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Enable")) {
	        if (!VoidTPConfig.getConfig().getBoolean("VoidTP.World.All_World")) {
	            for (String world : VoidTPConfig.getConfig().getStringList("VoidTP.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/VoidTP.yml, VoidTP.World: "+world);
	            	} else {
	            		voidtp_world.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getVTP() {
		return voidtp_world;
	}
	
	// GameMode
	public static List<String> gm_world = new ArrayList<String>();
		
	public static void setWGetWorldforGM() {
		if (OnJoinConfig.getConfig().getBoolean("Change-Gamemode.Enable")) {
			if (!OnJoinConfig.getConfig().getBoolean("Change-Gamemode.World.All_World")) {
				for (String world : OnJoinConfig.getConfig().getStringList("Change-Gamemode.World.Worlds")) {
					if (Bukkit.getWorld(world) == null) {
						System.out.println("| Invalid world in Events/OnJoin.yml, Change-Gamemode.World: "+world);
					} else {
						gm_world.add(world);
					}
				}
			}
		}
	}
		
	public static List<String> getGM() {
		return gm_world;
	}
	
	public static List<String> kgm_world = new ArrayList<String>();
	
	public static void setWGetWorldforKGM() {
		if (PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.Enable")) {
			if (!PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.World.All_World")) {
				for (String world : PlayerEventsConfig.getConfig().getStringList("Keep-Gamemode.World.Worlds")) {
					if (Bukkit.getWorld(world) == null) {
						System.out.println("| Invalid world in Events/BasicEvents.yml, Event.Keep-Gamemode.World: "+world);
					} else {
						kgm_world.add(world);
					}
				}
			}
		}
	}
		
	public static List<String> getKGM() {
		return kgm_world;
	}
	
	// keep health and food
		public static List<String> worlds_kFood = new ArrayList<String>();
		public static List<String> worlds_kANTIDAMAGE = new ArrayList<String>();
		
		public static void setWGetWorldkFood() {
			if (PlayerEventsConfig.getConfig().getBoolean("Keep.Food.Enable")) {
		        if (!PlayerEventsConfig.getConfig().getBoolean("Keep.Food.World.All_World")) {
		            for (String world : PlayerEventsConfig.getConfig().getStringList("Keep.Food.World.Worlds")) {
		            	if (Bukkit.getWorld(world) == null) {
		            		System.out.println("| Invalid world in BasicEvents.yml, Event.Keep.Food.World: "+world);
		            	} else {
		            		worlds_kFood.add(world);
		            	}
		            }
		        }
	        }
		}
		
		public static void setWGetWorldANTIDAMAGE() {
			if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Enable")) {
		        if (!ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.World.All_World")) {
		            for (String world : ProtectionPlayerConfig.getConfig().getStringList("Anti-Damage.World.Worlds")) {
		            	if (Bukkit.getWorld(world) == null) {
		            		System.out.println("| Invalid world in ProtectionPlayer.yml, Anti-Damage.World: "+world);
		            	} else {
		            		worlds_kANTIDAMAGE.add(world);
		            	}
		            }
		        }
	        }
		}
		
		public static List<String> getWkFood() {
			return worlds_kFood;
		}
		
		public static List<String> getWkHealth() {
			return worlds_kANTIDAMAGE;
		}
		
		// AB
		
		public static List<String> worlds_autobroadcast = new ArrayList<String>();
		
		public static void setWGetWorldautobroadcast() {
			if (AutoBroadcastConfig.getConfig().getBoolean("Config.Enable")) {
		        if (!AutoBroadcastConfig.getConfig().getBoolean("Config.World.All_World")) {
		            for (String world : AutoBroadcastConfig.getConfig().getStringList("Config.World.Worlds")) {
		            	if (Bukkit.getWorld(world) == null) {
		            		System.out.println("| Invalid world in AutoBroadcast.yml, Config.World: "+world);
		            	} else {
		            		worlds_autobroadcast.add(world);
		            	}
		            }
		        }
	        }
		}
		
		public static List<String> getAutoBroadcast() {
			return worlds_autobroadcast;
		}

}
