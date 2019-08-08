package fr.Dianox.Hawn.Utility.Config.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigGJoinQuitCommand {
	
    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public ConfigGJoinQuitCommand() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Events/JoinQuitCommand.yml");
        Config = YamlConfiguration.loadConfiguration(file);

        if (!pl.getDataFolder().exists()) {
            pl.getDataFolder().mkdir();
        }

        create();

    }

    public static File getFile() {
        return file;
    }

    public static YamlConfiguration getConfig() {
        return Config;
    }

    public static void reloadConfig() {
        loadConfig(pl);
    }

    public static void saveConfigFile() {
        try {
            Config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void create() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {}

            Config.set("JoinCommand.Enable", true);
            Config.set("JoinCommand.Options.New.Enable", true);
            Config.set("JoinCommand.Options.New.Commands",java.util.Arrays.asList(new String[] {
            		"commands for new player!"
            }));
            Config.set("JoinCommand.Options.New.World.All_World", false);
            Config.set("JoinCommand.Options.New.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("JoinCommand.Options.No-New.Enable", true);
            Config.set("JoinCommand.Options.No-New.Commands",java.util.Arrays.asList(new String[] {
            		"<world>world</world> <perm>serer</perm> [command-player]: spawn",
                    "[ping]",
                    "send a message",
                    "[command-player]: or just execute a command"
            }));
            Config.set("JoinCommand.Options.No-New.World.All_World", false);
            Config.set("JoinCommand.Options.No-New.World.Worlds", java.util.Arrays.asList(new String[] {
            		"world",
                    "world_nether"
            }));
            
            Config.set("QuitCommand.Enable", false);
            Config.set("QuitCommand.Commands",java.util.Arrays.asList(new String[] {
            		"you can execute one or a lot of commands here with %player%"
            }));
            Config.set("QuitCommand.World.All_World", false);
            Config.set("QuitCommand.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));

            saveConfigFile();

        }
    }

}
