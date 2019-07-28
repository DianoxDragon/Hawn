package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.Events.PlayerWorldChangeConfigE;

public class ChangeWorldPW {
	
	// Fly
		public static List<String> world_change_keepfly = new ArrayList<String>();
		
		public static void setWKEEPFLY() {
			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.Enable")) {
		        if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.World.All_World")) {
		            for (String world : PlayerWorldChangeConfigE.getConfig().getStringList("Fly.World.Worlds")) {
		            	if (Bukkit.getWorld(world) == null) {
		            		System.out.println("| Invalid world in Events/PlayerWorldChange.yml, Fly.World.Worlds: "+world);
		            	} else {
		            		world_change_keepfly.add(world);
		            	}
		            }
		        }
	        }
		}
		
		public static List<String> getWFlyKeepOnChangeWorld() {
			return world_change_keepfly;
		}
		
		// Gamemode
		public static List<String> worlds_GM_OnChangeWorld = new ArrayList<String>();
		
		public static void setWGetWorldGamemodeChangeWorld() {
			if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.Enable")) {
		        if (!PlayerWorldChangeConfigE.getConfig().getBoolean("GM.World.All_World")) {
		            for (String world : PlayerWorldChangeConfigE.getConfig().getStringList("GM.World.Worlds")) {
		            	if (Bukkit.getWorld(world) == null) {
		            		System.out.println("| Invalid world in Events/PlayerWorldChange.yml, GM.World: "+world);
		            	} else {
		            		worlds_GM_OnChangeWorld.add(world);
		            	}
		            }
		        }
	        }
		}
		
		public static List<String> getWGamemodeOnChangeWorld() {
			return worlds_GM_OnChangeWorld;
		}
		
		// PO
				public static List<String> worlds_po = new ArrayList<String>();
				
				public static void setWPO() {
					if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.Enable")) {
				        if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.World.All_World")) {
				            for (String world : PlayerWorldChangeConfigE.getConfig().getStringList("Player-Options.World.Worlds")) {
				            	if (Bukkit.getWorld(world) == null) {
				            		System.out.println("| Invalid world in Events/PlayerWorldChange.yml, Player-Options.World: "+world);
				            	} else {
				            		worlds_po.add(world);
				            	}
				            }
				        }
			        }
				}
				
				public static List<String> getWPO() {
					return worlds_po;
				}

}
