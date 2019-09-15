package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.dianox.hawn.Utility.Config.Events.PlayerWorldChangeConfigE;

public class ChangeWorldPW {

    public static List < String > world_change_keepfly = new ArrayList < String > ();
    public static List < String > worlds_GM_OnChangeWorld = new ArrayList < String > ();
    public static List < String > worlds_po = new ArrayList < String > ();
    public static List < String > worlds_commands = new ArrayList < String > ();

    // Fly
    public static void setWKEEPFLY() {
        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.Enable") && !PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.World.All_World")) {
            for (String world: PlayerWorldChangeConfigE.getConfig().getStringList("Fly.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/PlayerWorldChange.yml, Fly.World.Worlds: " + world);
                } else {
                    world_change_keepfly.add(world);
                }
            }
        }
    }

    // Gamemode
    public static void setWGetWorldGamemodeChangeWorld() {
        if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.Enable") && !PlayerWorldChangeConfigE.getConfig().getBoolean("GM.World.All_World")) {
            for (String world: PlayerWorldChangeConfigE.getConfig().getStringList("GM.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/PlayerWorldChange.yml, GM.World: " + world);
                } else {
                    worlds_GM_OnChangeWorld.add(world);
                }
            }
        }
    }

    // PO
    public static void setWPO() {
        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.Enable") && !PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.World.All_World")) {
            for (String world: PlayerWorldChangeConfigE.getConfig().getStringList("Player-Options.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/PlayerWorldChange.yml, Player-Options.World: " + world);
                } else {
                    worlds_po.add(world);
                }
            }
        }
    }
    
    // Commands
    public static void setCommands() {
        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Execute-Command.Enable") && !PlayerWorldChangeConfigE.getConfig().getBoolean("Execute-Command.World.All_World")) {
            for (String world: PlayerWorldChangeConfigE.getConfig().getStringList("Execute-Command.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/PlayerWorldChange.yml, Execute-Command.World: " + world);
                } else {
                	worlds_commands.add(world);
                }
            }
        }
    }

    public static List < String > getCommands() {
        return worlds_commands;
    }
    
    public static List < String > getWPO() {
        return worlds_po;
    }

    public static List < String > getWGamemodeOnChangeWorld() {
        return worlds_GM_OnChangeWorld;
    }

    public static List < String > getWFlyKeepOnChangeWorld() {
        return world_change_keepfly;
    }

}