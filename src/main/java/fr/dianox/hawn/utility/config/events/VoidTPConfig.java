package fr.dianox.hawn.utility.config.events;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class VoidTPConfig {
    
    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;
    
    public VoidTPConfig() {}
    
    public static void loadConfig(Plugin plugin) {
        pl = plugin;
        
        file = new File(pl.getDataFolder(), "Events/VoidTP.yml");
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
            
            Config.set("VoidTP.Enable", true);
            Config.set("VoidTP.Options.TP-y", 0);
            
            Config.set("VoidTP.Options.Bypass-With-Permission", true);
            
            Config.set("VoidTP.Options.Message.Custom", true);
            Config.set("VoidTP.Options.Message.Disable", false);
            
            Config.set("VoidTP.Custom-Spawn.Enable", false);
            Config.set("VoidTP.Custom-Spawn.Spawn", "CHANGE ME");
            
            Config.set("VoidTP.Options.Message.Disable", false);
            
            Config.set("VoidTP.Options.Sounds.Enable", true);
            Config.set("VoidTP.Options.Sounds.Sound", "BLOCK_NOTE_HARP");
            Config.set("VoidTP.Options.Sounds.Volume", 1);
            Config.set("VoidTP.Options.Sounds.Pitch", 1);
            
            Config.set("VoidTP.Options.Fireworks.Enable", true);
            Config.set("VoidTP.Options.Fireworks.Firework-List", java.util.Arrays.asList(new String[] {
                    "[FWLU]: Firework1"
            }));
            
            Config.set("VoidTP.Options.Execute-Commands.Enable", true);
            Config.set("VoidTP.Options.Execute-Commands.Commands", java.util.Arrays.asList(new String[] {
                    "[command-player]: you can execute your custom commands too",
                    "[command-player]: to send titles for exemple",
                    "[command-console]: And More commands"
                }));
            
            Config.set("VoidTP.Options.VoidTP-Per-World.Enable", true);
            
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.Enable", true);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.VoidTP", true);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.Custom-Spawn.Enable", true);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.Custom-Spawn.Spawn", "CHANGE ME");
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.TP-y", 0);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.Execute-Commands.Enable", true);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.Execute-Commands.Override-Default-Commands", true);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world.Execute-Commands.Commands", java.util.Arrays.asList(new String[] {
                    "[command-player]: a command",
                    "You can put if you want, custom commands",
                    "For one world only"
                }));
            
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.Enable", false);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.VoidTP", true);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.Custom-Spawn.Enable", true);
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.Custom-Spawn.Spawn", "CHANGE ME");
            Config.set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.TP-y", 0);
            
            Config.set("VoidTP.World.All_World", false);
            Config.set("VoidTP.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            saveConfigFile();

        }
    }

}