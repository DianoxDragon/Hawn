package fr.Dianox.Hawn.Utility.Config.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class PlayerWorldChangeConfigE {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PlayerWorldChangeConfigE() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Events/PlayerWorldChange.yml");
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
            
            Config.set("GM.Enable", Boolean.valueOf(true));
            Config.set("GM.CustomMode.Enable", Boolean.valueOf(true));
            Config.set("GM.CustomMode.GameMode", Integer.valueOf(1));
            Config.set("GM.CustomMode.If-player-have.Survival", Boolean.valueOf(true));
            Config.set("GM.CustomMode.If-player-have.Creative", Boolean.valueOf(true));
            Config.set("GM.CustomMode.If-player-have.Adventure", Boolean.valueOf(true));
            Config.set("GM.CustomMode.If-player-have.Spectator", Boolean.valueOf(true));
            Config.set("GM.World.All_World", Boolean.valueOf(false));
            Config.set("GM.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Fly.Enable.Enable", Boolean.valueOf(true));
            Config.set("Fly.Enable.SetFlyOnChangeWorld", Boolean.valueOf(true));
            Config.set("Fly.Cancel-Event-If-Player-Is-In.Gamemode-Creative-Spectator", Boolean.valueOf(true));
            Config.set("Fly.Enable.DisableFlyIfAWorldIsNotListed", Boolean.valueOf(true));
            Config.set("Fly.World.All_World", Boolean.valueOf(false));
            Config.set("Fly.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            saveConfigFile();

        }
    }

}