package fr.dianox.hawn.utility.config.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class PlayerOptionMainConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PlayerOptionMainConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Player-Option-General.yml");
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
            
            Config.set("General.Enable", true);
            
            // Keep states
            Config.set("Keep.Gamemode-On-Join.Enable", false);
            Config.set("Keep.Vanish-On-Join.Enable", false);
            Config.set("Keep.PlayerVisibility-OnJoin.Enable", false);
            Config.set("Keep.Speed-OnJoin.Enable", false);
            Config.set("Keep.FlySpeed-OnJoin.Enable", false);
            Config.set("Keep.DoubleJump-Fly-OnJoin.Enable", false);
            Config.set("Keep.JumpBoost-OnJoin.Enable", false);
            Config.set("TP.Last-Position-On-Join.Enable", false);
            
            Config.set("Options.Flying.Put-boots", true);
            
            saveConfigFile();

        }
    }

}