package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.Events.ConfigGProtection;

public class ProtectionPW {
	
	public static List<String> worlds_c_place = new ArrayList<String>();
	public static List<String> worlds_c_break = new ArrayList<String>();
	
	public static void setWGetWorldProtectionPlace() {
		if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Enable")) {
	        if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.World.All_World")) {
	            for (String world : ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Place.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.Construct.Anti-Place.World: "+world);
	            	} else {
	            		worlds_c_place.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWPCP() {
		return worlds_c_place;
	}
	
	public static void setWGetWorldProtectionBreak() {
		if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Enable")) {
	        if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.World.All_World")) {
	            for (String world : ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Break.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.Construct.Anti-Break.World: "+world);
	            	} else {
	            		worlds_c_break.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWPCB() {
		return worlds_c_break;
	}
	

	public static List<String> worlds_HagingBreakByEntity = new ArrayList<String>();
	public static List<String> worlds_PlayerInteractEntity_ItemFrame = new ArrayList<String>();
	
	public static void setWGetWorldProtectionHagingBreakByEntity() {
		if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Enable")) {
	        if (!ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.World.All_World")) {
	            for (String world : ConfigGProtection.getConfig().getStringList("Protection.HagingBreakByEntity.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.HagingBreakByEntity.World: "+world);
	            	} else {
	            		worlds_HagingBreakByEntity.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldProtectionPlayerInteractEntityItemFrame() {
		if (!ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.World.All_World")) {
            for (String world : ConfigGProtection.getConfig().getStringList("Protection.PlayerInteractEntity-ItemFrame.World.Worlds")) {
            	if (Bukkit.getWorld(world) == null) {
            		System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.PlayerInteractEntity-ItemFrame.World: "+world);
            	} else {
            		worlds_PlayerInteractEntity_ItemFrame.add(world);
            	}
            }
        }
	}
	
	public static List<String> getWHBBE() {
		return worlds_HagingBreakByEntity;
	}
	
	public static List<String> getWPIEIF() {
		return worlds_PlayerInteractEntity_ItemFrame;
	}

}