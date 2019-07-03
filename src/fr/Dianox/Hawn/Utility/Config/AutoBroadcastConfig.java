package fr.Dianox.Hawn.Utility.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AutoBroadcastConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public AutoBroadcastConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "AutoBroadcast.yml");
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
            
            // Configuration
            
            Config.set("Config.Enable", Boolean.valueOf(true));
            Config.set("Config.Random", Boolean.valueOf(false));
            Config.set("Config.Interval", Integer.valueOf(10));
            Config.set("Config.Broadcast-To-Console", Boolean.valueOf(true));
            Config.set("Config.Use-Permission-To-Get-Messages", Boolean.valueOf(false));
            Config.set("Config.World.All_World", Boolean.valueOf(false));
            Config.set("Config.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            Config.set("messages.ohwowitsmyfirstmessage.message", java.util.Arrays.asList(new String[] {
                    "Yes, it's the first message",
                    "&cIncredible no %player% ?"
            }));
            Config.set("messages.itsunlimited.message", java.util.Arrays.asList(new String[] {
                    "Yes, this one works too",
                    "No limits ! ...",
                    "Or maybe just some variables in the consoles"
            }));
            
            saveConfigFile();

        }
    }

}