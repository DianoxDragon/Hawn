package fr.dianox.hawn.utility.config.messages.fr_fr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AdminSpawnMConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public AdminSpawnMConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Administration/Spawn.yml");
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
                        
            /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
            Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList(new String[] {
            		"&cVous n'avez pas mis de nom pour ce spawn, un nom automatique a été choisi.",
            		"§eLe spawn a été placé sous le nom de %spawnName%"
            		}));
            
            Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList(new String[] {
            		"§eLe spawn a été placé sous le nom de %spawnName%"
            		}));
            
            Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList(new String[] {"&bLe spawn &e%spawn%&b a été supprimée"}));
            
            saveConfigFile();

        }
    }
}