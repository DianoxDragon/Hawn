package fr.dianox.hawn.utility.world;

import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class CjiPW {

    public static List < String > worlds_general = new ArrayList <> ();

    public static void setItemPlayerGeneral() {
        if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable") && !ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
            for (String world: ConfigCJIGeneral.getConfig().getStringList("Custom-Join-Item.General-Option.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in General.yml, Custom-Join-Item.General-Option.World: " + world);
                } else {
                	worlds_general.add(world);
                }
            }
        }
    }
    
    public static List < String > getWItemPG() {
        return worlds_general;
    }

}