package fr.Dianox.Hawn.Utility.Config.Messages.Administration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.Dianox.Hawn.Main;

public class SpawnMConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public SpawnMConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/Administration/Spawn.yml");
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
            		"&cYou have not put a name for this spawn, an automatic name has been chosen",
            		"§eSpawn set on behalf of %spawnName%"
            		}));
            
            Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList(new String[] {
            		"§eSpawn set on behalf of %spawnName%"
            		}));
            
            Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList(new String[] {"&bThe spawn &e%spawn%&b has been deleted"}));
            
            saveConfigFile();

        }
    }
}