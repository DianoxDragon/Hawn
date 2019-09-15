package fr.dianox.hawn.utility.world;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.dianox.hawn.utility.config.events.ConfigGJoinQuitCommand;

public class CommandsPW {

    public static List < String > worlds_JoinCommands_New = new ArrayList < String > ();
    public static List < String > worlds_JoinCommands_No_New = new ArrayList < String > ();
    public static List < String > worlds_QuitCommands_Console = new ArrayList < String > ();

    public static void setWGetWorldJoinCommandNew() {
        if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.Enable") && !ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.World.All_World")) {
            for (String worldHunger: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.World.Worlds")) {
                if (Bukkit.getWorld(worldHunger) == null) {
                    System.out.println("| Invalid world in Events/JoinQuitCommand.yml, JoinCommand.Options.New.World: " + worldHunger);
                } else {
                    worlds_JoinCommands_New.add(worldHunger);
                }
            }
        }
    }

    public static void setWGetWorldJoinCommandNoNew() {
        if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.Enable") && !ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.World.All_World")) {
            for (String worldHunger: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.World.Worlds")) {
                if (Bukkit.getWorld(worldHunger) == null) {
                    System.out.println("| Invalid world in Events/JoinQuitCommand.yml, JoinCommand.Options.No-New.World: " + worldHunger);
                } else {
                    worlds_JoinCommands_No_New.add(worldHunger);
                }
            }
        }
    }

    public static void setWGetWorldQuitCommandConsole() {
        if (ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.Enable") && !ConfigGJoinQuitCommand.getConfig().getBoolean("QuitCommand.World.All_World")) {
            for (String worldHunger: ConfigGJoinQuitCommand.getConfig().getStringList("QuitCommand.World.Worlds")) {
                if (Bukkit.getWorld(worldHunger) == null) {
                    System.out.println("| Invalid world in Events/JoinQuitCommand.yml, QuitCommand.World: " + worldHunger);
                } else {
                    worlds_QuitCommands_Console.add(worldHunger);
                }
            }
        }
    }

    public static List < String > getWJoinCommandNew() {
        return worlds_JoinCommands_New;
    }

    public static List < String > getWJoinCommandNoNew() {
        return worlds_JoinCommands_No_New;
    }

    public static List < String > getWConsoleQuitCommand() {
        return worlds_QuitCommands_Console;
    }

}