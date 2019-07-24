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
            
            Config.set("Config.Messages.Enable", true);
            Config.set("Config.Messages.Random", false);
            Config.set("Config.Messages.Interval", 60);
            Config.set("Config.Messages.Broadcast-To-Console", false);
            Config.set("Config.Messages.Use-Permission-To-Get-Messages", false);
            Config.set("Config.Messages.World.All_World", false);
            Config.set("Config.Messages.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            Config.set("Config.Messages.messages.firstmessage.message", java.util.Arrays.asList(new String[] {
            		"",
                    "<--center--> &4/&c!&4\\ &6Warning &e- &6Autobroadcast &4/&c!&4\\",
                    "<--center--> The best way to support me is to put 5 stars on spigot",
                    ""
            }));
            Config.set("Config.Messages.messages.mymessage.message", java.util.Arrays.asList(new String[] {
                    "",
                    "<--center--> &4/&c!&4\\ &6Warning &e- &6Autobroadcast &4/&c!&4\\",
                    "<--center--> You are free to configure the messages as you want %player%",
                    ""
            }));
            
            saveConfigFile();

        }
    }
}
