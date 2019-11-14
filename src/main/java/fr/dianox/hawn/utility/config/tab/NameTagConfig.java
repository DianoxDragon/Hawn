package fr.dianox.hawn.utility.config.tab;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class NameTagConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public NameTagConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Tablist/Nametag.yml");
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
            
            Config.set("nametag-general.enable", true);
            
            Config.set("nametag.owner.prefix.enabled", true);
            Config.set("nametag.owner.prefix.message", "&4[Owner]&c ");
            
            Config.set("nametag.owner.suffix.enabled", false);
            Config.set("nametag.owner.suffix.message", " &aoui");
            
            Config.set("nametag.owner.priority", 01);
            
            Config.set("nametag.admin.prefix.enabled", true);
            Config.set("nametag.admin.prefix.message", "&c[Admin]&6 ");
            
            Config.set("nametag.admin.suffix.enabled", false);
            Config.set("nametag.admin.suffix.message", " &aoui");
            
            Config.set("nametag.admin.priority", 02);
            
            Config.set("nametag-player.dianox", "owner");
            Config.set("nametag-player.randomplayeromg", "admin");
            
            saveConfigFile();

        }
    }

}