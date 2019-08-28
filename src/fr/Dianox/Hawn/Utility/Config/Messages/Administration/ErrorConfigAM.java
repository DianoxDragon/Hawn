package fr.Dianox.Hawn.Utility.Config.Messages.Administration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.Dianox.Hawn.Main;

public class ErrorConfigAM {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ErrorConfigAM() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/Administration/Errors.yml");
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
            Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList(new String[] {
            		"&cYou are not a player"
            		}));
            
            Config.set("Error.Command.Hawn", java.util.Arrays.asList(new String[] {
            		"&cError, Try to do /hawn"
            		}));
            
            Config.set("Error.Command.Delspawn", java.util.Arrays.asList(new String[] {
            		"&c/hawn delspawn <spawn>"
            		}));
            
            Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList(new String[] {
            		"&cThe name already exist"
            		}));
            
            Config.set("Error.Argument-Missing", java.util.Arrays.asList(new String[] {
            		"&cI'm sorry, but there must be one or two arguments missing."
            		}));
            Config.set("Error.No-Spawn", java.util.Arrays.asList(new String[] {"&cSpawn doesn't exist"}));
            
            saveConfigFile();

        }
    }
}
