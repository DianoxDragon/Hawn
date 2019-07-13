package fr.Dianox.Hawn.Utility.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigGeneral {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigGeneral() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "general.yml");
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
            
            Config.set("Plugin.Use.PlaceholderAPI", false);
            Config.set("Plugin.Use.Keep-The-Option", false);
            Config.set("Plugin.Use.WorldGuard.Enable", false);
            Config.set("Plugin.Use.WorldGuard.Keep-The-Option", false);
	    Config.set("Plugin.Use.MVdWPlaceholderAPI.Enable", false);
            Config.set("Plugin.Use.MVdWPlaceholderAPI.Keep-The-Option", false);
            Config.set("Plugin.Use.MYSQL.Enable", false);
            Config.set("Plugin.Use.MYSQL.Host", "localhost");
            Config.set("Plugin.Use.MYSQL.Username", "root");
            Config.set("Plugin.Use.MYSQL.Password", "123");
            Config.set("Plugin.Use.MYSQL.Database", "Hawn");
            Config.set("Plugin.Use.MYSQL.Port", 3306);
            Config.set("Plugin.Use.MYSQL.Use-SSL", false);
            Config.set("Plugin.Update.Check-Update", true);
            Config.set("Plugin.Date-Format", "dd-MM-yyyy");
            Config.set("Plugin.12-Hours-Or-24-Hours-Format", 24);
            Config.set("Plugin.Tps.Warn-system", true);
            
            saveConfigFile();

        }
    }

}
