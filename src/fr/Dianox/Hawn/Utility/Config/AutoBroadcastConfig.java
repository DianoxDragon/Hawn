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
            
            Config.set("Config.Enable", true);
            Config.set("Config.Random", false);
            Config.set("Config.Interval", 60);
            Config.set("Config.Broadcast-To-Console", false);
            Config.set("Config.Use-Permission-To-Get-Messages", false);
            Config.set("Config.World.All_World", false);
            Config.set("Config.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            Config.set("messages.firstmessage.message", java.util.Arrays.asList(new String[] {
            		"",
                    "<--center--> &4/&c!&4\\ &6Warning &e- &6Autobroadcast &4/&c!&4\\",
                    "<--center--> The best way to support me is to put 5 stars on spigot",
                    ""
            }));
            Config.set("messages.mymessage.message", java.util.Arrays.asList(new String[] {
                    "",
                    "<--center--> &4/&c!&4\\ &6Warning &e- &6Autobroadcast &4/&c!&4\\",
                    "<--center--> You are free to configure the messages as you want %player%",
                    ""
            }));
            
            saveConfigFile();

        }
    }
}
