package fr.Dianox.Hawn.Utility.Config.Messages.fr_FR;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AdminErrorConfigAM {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public AdminErrorConfigAM() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Administration/Errors.yml");
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
            		"&cVous n'êtes pas un joueur"
            		}));
            
            Config.set("Error.Command.Hawn", java.util.Arrays.asList(new String[] {
            		"&cErreur, Essayez de faire /hawn"
            		}));
            
            Config.set("Error.Command.Delspawn", java.util.Arrays.asList(new String[] {
            		"&c/hawn delspawn <spawn>"
            		}));
            
            Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList(new String[] {
            		"&cLe nom existe déjà"
            		}));
            
            Config.set("Error.Argument-Missing", java.util.Arrays.asList(new String[] {
            		"&cJe suis désolé, mais il doit manquer un ou deux arguments"
            		}));
            Config.set("Error.No-Spawn", java.util.Arrays.asList(new String[] {"&cLe spawn n'existe pas"}));
            
            saveConfigFile();

        }
    }
}