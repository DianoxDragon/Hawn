package fr.dianox.hawn.utility.world;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class OnQuitPW {

    // Quit message
    public static List < String > qm_world = new ArrayList <> ();

    public static void setGetWorldforQuitMessage() {
        if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Enable") && !ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.World.All_World")) {
            for (String world: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Messages/General.yml, General.On-Quit.Quit-Message: " + world);
                } else {
                    qm_world.add(world);
                }
            }
        }
    }

    public static List < String > getQM() {
        return qm_world;
    }
}