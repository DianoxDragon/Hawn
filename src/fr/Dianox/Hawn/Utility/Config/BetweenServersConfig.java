package fr.Dianox.Hawn.Utility.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class BetweenServersConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public BetweenServersConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "between-servers.yml");
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
            
            // Keep states
            Config.set("Keep.Gamemode-On-Join.Enable", Boolean.valueOf(false));
            Config.set("Keep.Vanish-On-Join.Enable", Boolean.valueOf(false));
            Config.set("Keep.PlayerVisibility-OnJoin.Enable", Boolean.valueOf(false));
            Config.set("Keep.Speed-OnJoin.Enable", Boolean.valueOf(false));
            /*Config.set("Keep.JumpBoost-OnJoin.Enable", Boolean.valueOf(false));
            Config.set("Keep.Fly-Double-Jump-OnJoin.Enable", Boolean.valueOf(false));*/
            Config.set("TP.Last-Position-On-Join.Enable", Boolean.valueOf(false));
            
            saveConfigFile();

        }
    }

}