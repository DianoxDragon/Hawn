package fr.dianox.hawn.utility.world;

import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGLP;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsPW {

    public static List < String > worlds_firework = new ArrayList < String > ();
    public static List < String > worlds_ls = new ArrayList < String > ();
    public static List < String > worlds_jumppads = new ArrayList < String > ();

    public static void setWGetWorldFirework() {
        if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Enable") && !ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.World.All_World")) {
            for (String world: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Cosmetics/OnJoin.yml, Cosmetics.Firework.World: " + world);
                } else {
                    worlds_firework.add(world);
                }
            }
        }
    }

    public static void setWGetWorldJumpPads() {
        if (ConfigGLP.getConfig().getBoolean("JumpPads.Enable") && !ConfigGLP.getConfig().getBoolean("JumpPads.World.All_World")) {
            for (String world: ConfigGLP.getConfig().getStringList("JumpPads.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Cosmetics-Fun/JumpPads.yml, JumpPads.World: " + world);
                } else {
                    worlds_jumppads.add(world);
                }
            }
        }
    }

    public static void setWGetWorldls() {
        if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Enable") && !ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.World.All_World")) {
            for (String world: ConfigGCos.getConfig().getStringList("Cosmetics.Lightning-Strike.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Cosmetics-Fun/OnJoin.yml, Cosmetics.Lightning-Strike.World: " + world);
                } else {
                    worlds_ls.add(world);
                }
            }
        }
    }

    public static List < String > getWLightningStrike() {
        return worlds_ls;
    }

    public static List < String > getWFirework() {
        return worlds_firework;
    }

    public static List < String > getWJumpPads() {
        return worlds_jumppads;
    }

}