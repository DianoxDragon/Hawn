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

            Config.set("Commands.Spawn.Use-Permission", false);
            Config.set("Commands.Spawn.CustomSpawn.Enable", false);
            Config.set("Commands.Spawn.CustomSpawn.Spawn", "CHANGE ME");
            
            Config.set("Commands.Spawn.Delay.Self.Enable", true);
            Config.set("Commands.Spawn.Delay.Self.Delay-Seconds", 5);
            Config.set("Commands.Spawn.Delay.Self.Bypass-Delay", false);
            
            Config.set("Commands.Spawn.Delay.Other.Enable", true);
            Config.set("Commands.Spawn.Delay.Other.Delay-Seconds", 5);
            Config.set("Commands.Spawn.Delay.Other.Bypass-Delay", false);

            Config.set("Commands.Spawn.Delay.Cancel-Tp-On.Any-movements", true);
            Config.set("Commands.Spawn.Delay.Cancel-Tp-On.On-Damages", true);
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("SetSpawn.Enable", true);
            Config.set("SetSpawn.Disable-Message", true);
            Config.set("SetSpawn.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("DelSpawn.Enable", true);
            Config.set("DelSpawn.Disable-Message", true);
            Config.set("DelSpawn.DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }

}
