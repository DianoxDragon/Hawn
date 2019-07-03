package fr.Dianox.Hawn.Utility.Config.Tab;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

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
            
            Config.set("nametag-general.enable", Boolean.valueOf(true));
            
            Config.set("nametag.owner.prefix.enabled", Boolean.valueOf(true));
            Config.set("nametag.owner.prefix.message", String.valueOf("&4[Owner]&c "));
            
            Config.set("nametag.owner.suffix.enabled", Boolean.valueOf(false));
            Config.set("nametag.owner.suffix.message", String.valueOf(" &aoui"));
            
            Config.set("nametag.owner.priority", Integer.valueOf(01));
            
            Config.set("nametag.admin.prefix.enabled", Boolean.valueOf(true));
            Config.set("nametag.admin.prefix.message", String.valueOf("&c[Admin]&6 "));
            
            Config.set("nametag.admin.suffix.enabled", Boolean.valueOf(false));
            Config.set("nametag.admin.suffix.message", String.valueOf(" &aoui"));
            
            Config.set("nametag.admin.priority", Integer.valueOf(02));
            
            Config.set("nametag-player.dianox", String.valueOf("owner"));
            Config.set("nametag-player.randomplayeromg", String.valueOf("admin"));
            
            saveConfigFile();

        }
    }

}