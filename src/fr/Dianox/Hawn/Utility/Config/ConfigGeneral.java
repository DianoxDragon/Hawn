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
            
            Config.set("Plugin.Use.PlaceholderAPI", Boolean.valueOf(false));
            Config.set("Plugin.Use.Keep-The-Option", Boolean.valueOf(false));
            Config.set("Plugin.Use.MYSQL.Enable", Boolean.valueOf(false));
            Config.set("Plugin.Use.MYSQL.Host", String.valueOf("localhost"));
            Config.set("Plugin.Use.MYSQL.Username", String.valueOf("root"));
            Config.set("Plugin.Use.MYSQL.Password", String.valueOf("123"));
            Config.set("Plugin.Use.MYSQL.Database", String.valueOf("Hawn"));
            Config.set("Plugin.Use.MYSQL.Port", Integer.valueOf(3306));
            Config.set("Plugin.Use.MYSQL.Use-SSL", Boolean.valueOf(false));
            Config.set("Plugin.Update.Check-Update", Boolean.valueOf(true));
            Config.set("Plugin.Date-Format", String.valueOf("dd-MM-yyyy"));
            Config.set("Plugin.12-Hours-Or-24-Hours-Format", Integer.valueOf(24));
            Config.set("Plugin.Tps.Warn-system", Boolean.valueOf(true));
            
            saveConfigFile();

        }
    }

}