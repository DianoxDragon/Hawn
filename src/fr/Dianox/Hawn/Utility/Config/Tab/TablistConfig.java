package fr.Dianox.Hawn.Utility.Config.Tab;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class TablistConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public TablistConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Tablist/Tablist.yml");
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
            
            Config.set("Tablist.enable", Boolean.valueOf(true));
            Config.set("Tablist.refresh-time-ticks", Integer.valueOf(20));
            
            Config.set("Tablist.header.enabled", Boolean.valueOf(true));
            Config.set("Tablist.header.message", java.util.Arrays.asList(new String[] {
            		"",
            		"&7Thank you to choose &6&lHawn",
            		"",
            		"&7You are &c%player%",
            		"",
            		"&6&l>> &3&m-------------------&r &6&l<<"
            		}));
            
            Config.set("Tablist.footer.enabled", Boolean.valueOf(true));
            Config.set("Tablist.footer.message", java.util.Arrays.asList(new String[] {
            		"&6&l>> &3&m-------------------&r &6&l<<",
            		"",
            		"&6&lHawn~~"
            		}));
            
            saveConfigFile();

        }
    }

}