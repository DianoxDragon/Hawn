package fr.dianox.hawn.utility.config.scoreboard;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class worldnetherdsc {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public worldnetherdsc() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Scoreboard/scoreboard.worldnetherbecausewelikethat.yml");
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
            
            Config.set("title", java.util.Arrays.asList(new String[] {
            		"&c&lHawn",
            		"&4&lHawn"
            }));
            
            Config.set("text", java.util.Arrays.asList(new String[] {
            		" ",
            		"&cMinimalist scoreboard",
            		" ",
            		"&cA kind thanks to use &4&lHawn"
            }));
            
            Config.set("updater.title", 50);
            Config.set("updater.text", 60);
            
            Config.set("World.All_World", false);
            Config.set("World.Worlds", java.util.Arrays.asList(new String[] {
                    "world_nether"
            }));
            
            saveConfigFile();

        }
    }

}