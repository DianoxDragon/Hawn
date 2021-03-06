package fr.dianox.hawn.utility.world;

import fr.dianox.hawn.utility.config.configs.events.OtherFeaturesConfig;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class OtherFeaturesPW {

    public static List < String > worlds_ColorSign = new ArrayList <> ();
    public static List < String > worlds_EmojiSign = new ArrayList <> ();
    
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
    
    public static void setWGetWorldEventEmojiSign() {
        if (OtherFeaturesConfig.getConfig().getBoolean("EmojiSign.Enable") && !OtherFeaturesConfig.getConfig().getBoolean("EmojiSign.World.All_World")) {
            for (String world: OtherFeaturesConfig.getConfig().getStringList("EmojiSign.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OtherFeatures.yml, EmojiSign.World: " + world);
                } else {
                	worlds_EmojiSign.add(world);
                }
            }
        }
    }

    public static List < String > getWColorSign() {
        return worlds_ColorSign;
    }
    
    public static List < String > getWEmojiSign() {
        return worlds_EmojiSign;
    }

}