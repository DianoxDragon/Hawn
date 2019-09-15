package fr.dianox.hawn.utility.config.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class GoTopCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public GoTopCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/Gotop.yml");
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
            
            Config.set("Gotop.Enable", true);
            Config.set("Gotop.Disable-Message", true);
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);
            
            saveConfigFile();

        }
    }

}