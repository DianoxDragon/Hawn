package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SpawnCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public SpawnCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/Spawn.yml");
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

            Config.set("Commands.Spawn.Use-Permission", Boolean.valueOf(false));
            Config.set("Commands.Spawn.CustomSpawn.Enable", Boolean.valueOf(false));
            Config.set("Commands.Spawn.CustomSpawn.Spawn", String.valueOf("CHANGE ME"));
            
            Config.set("Commands.Spawn.Delay.Self.Enable", Boolean.valueOf(true));
            Config.set("Commands.Spawn.Delay.Self.Delay-Seconds", Integer.valueOf(5));
            Config.set("Commands.Spawn.Delay.Self.Bypass-Delay", Boolean.valueOf(false));
            
            Config.set("Commands.Spawn.Delay.Other.Enable", Boolean.valueOf(true));
            Config.set("Commands.Spawn.Delay.Other.Delay-Seconds", Integer.valueOf(5));
            Config.set("Commands.Spawn.Delay.Other.Bypass-Delay", Boolean.valueOf(false));
            
            Config.set("Commands.Spawn.Delay.Cancel-Tp-On.Any-movements", Boolean.valueOf(true));
            Config.set("Commands.Spawn.Delay.Cancel-Tp-On.On-Damages", Boolean.valueOf(true));
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));

            saveConfigFile();

        }
    }

}