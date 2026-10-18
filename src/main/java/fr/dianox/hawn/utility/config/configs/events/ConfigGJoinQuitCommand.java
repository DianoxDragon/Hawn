package fr.dianox.hawn.utility.config.configs.events;

import fr.dianox.hawn.utility.config.ConfigDefaults;

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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        Config.set("JoinCommand.Enable", true);
        Config.set("JoinCommand.Options.New.Enable", false);
        Config.set("JoinCommand.Options.New.Commands",java.util.Arrays.asList(new String[] {
        		"&7Welcome to the server, &e%player%&7! Type &e/rules &7to read the rules"
        }));
        Config.set("JoinCommand.Options.New.World.All_World", false);
        Config.set("JoinCommand.Options.New.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
        }));

        Config.set("JoinCommand.Options.No-New.Enable", true);
        Config.set("JoinCommand.Options.No-New.Commands",java.util.Arrays.asList(new String[] {
        		"&8&m-----------------------------------------------------",
        		"&7Welcome back, &e%player%&7!",
                "[ping]",
        		"<perm>hawn.admin</perm> &7Admin tools: &e/hawn &7and &e/ap",
                "&8&m-----------------------------------------------------"
        }));
        Config.set("JoinCommand.Options.No-New.World.All_World", false);
        Config.set("JoinCommand.Options.No-New.World.Worlds", java.util.Arrays.asList(new String[] {
        		"world",
                "world_nether"
        }));

        Config.set("QuitCommand.Enable", false);
        Config.set("QuitCommand.Commands",java.util.Arrays.asList(new String[] {
        		"say %player% left the lobby"
        }));
        Config.set("QuitCommand.World.All_World", false);
        Config.set("QuitCommand.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
        }));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}
