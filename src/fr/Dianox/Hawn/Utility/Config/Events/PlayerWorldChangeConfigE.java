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
            
            Config.set("GM.Enable", true);
            Config.set("GM.CustomMode.Enable", true);
            Config.set("GM.CustomMode.GameMode", 1);
            Config.set("GM.CustomMode.If-player-have.Survival", true);
            Config.set("GM.CustomMode.If-player-have.Creative", true);
            Config.set("GM.CustomMode.If-player-have.Adventure", true);
            Config.set("GM.CustomMode.If-player-have.Spectator", true);
            Config.set("GM.World.All_World", false);
            Config.set("GM.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Fly.Enable.Enable", true);
            Config.set("Fly.Enable.SetFlyOnChangeWorld", true);
            Config.set("Fly.Cancel-Event-If-Player-Is-In.Gamemode-Creative-Spectator", true);
            Config.set("Fly.Enable.DisableFlyIfAWorldIsNotListed", true);
            Config.set("Fly.World.All_World", false);
            Config.set("Fly.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            
            Config.set("Player-Options.Enable", true);
            Config.set("Player-Options.Keep-Options", true);
            
            Config.set("Player-Options.If-Not-Keeping.Reset-settings-on-world-change", false);
            Config.set("Player-Options.If-Not-Keeping.Reset-When-Enter-Or-Leave-A-World.False-Is-Leave", false);
            
            Config.set("Player-Options.If-Not-Keeping.Options-Default.GameMode.Enable", true);
            Config.set("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value", 1);
            
            Config.set("Player-Options.If-Not-Keeping.Options-Default.Fly", true);
            Config.set("Player-Options.If-Not-Keeping.Options-Default.DoubleJump", false);
            Config.set("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility", false);
            
            Config.set("Player-Options.World.All_World", false);
            Config.set("Player-Options.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            
            saveConfigFile();

        }
    }

}
