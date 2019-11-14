package fr.dianox.hawn.utility.world;

import fr.dianox.hawn.utility.config.events.OtherFeaturesConfig;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class OtherFeaturesPW {

    public static List < String > worlds_ColorSign = new ArrayList < String > ();

    public static void setWGetWorldEventColorSign() {
        if (OtherFeaturesConfig.getConfig().getBoolean("ColorSign.Enable") && !OtherFeaturesConfig.getConfig().getBoolean("ColorSign.World.All_World")) {
            for (String world: OtherFeaturesConfig.getConfig().getStringList("ColorSign.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OtherFeatures.yml, ColorSign.World: " + world);
                } else {
                    worlds_ColorSign.add(world);
                }
            }
        }
    }

    public static List < String > getWColorSign() {
        return worlds_ColorSign;
    }

}