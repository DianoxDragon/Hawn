package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;

public class CjiPW {
	
	public static List<String> worlds_po_playervisibility_item = new ArrayList<String>();
	
	public static void setItemPlayerVisibility() {
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
	        if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.World.All_World")) {
	            for (String world : SpecialCjiHidePlayers.getConfig().getStringList("PV.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Player-Visibility-Items-OnJoin.yml, PV.World: "+world);
	            	} else {
	            		worlds_po_playervisibility_item.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWItemPVOnJoin() {
		return worlds_po_playervisibility_item;
	}

}
