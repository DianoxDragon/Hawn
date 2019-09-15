package fr.dianox.hawn.utility.config.messages.fr_fr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMProtection {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMProtection() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Classic/Protection.yml");
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

            Config.set("Protection.Anti-Place", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, vous ne pouvez pas placer de bloc ici !"}));
            Config.set("Protection.Anti-Break", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, vous ne pouvez casser de bloc ici !"}));
            
            saveConfigFile();

        }
    }

}