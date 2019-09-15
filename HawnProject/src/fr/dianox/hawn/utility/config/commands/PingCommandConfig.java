package fr.dianox.hawn.utility.config.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class PingCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PingCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/Ping.yml");
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

            Config.set("Ping.Self.Enable", true);
            Config.set("Ping.Self.Use_Permission", false);
            Config.set("Ping.Self.Disable-Message", true);
            Config.set("Ping.Other.Enable", true);
            Config.set("Ping.Other.Use_Permission", false);
            Config.set("Ping.Other.Disable-Message", true);
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }

}