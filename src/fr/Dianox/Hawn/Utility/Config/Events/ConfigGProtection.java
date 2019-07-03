package fr.Dianox.Hawn.Utility.Config.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigGProtection {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigGProtection() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Events/ProtectionWorld.yml");
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

            Config.set("Protection.Construct.Anti-Place.Enable", Boolean.valueOf(true));
            Config.set("Protection.Construct.Anti-Place.Bypass", Boolean.valueOf(true));
            Config.set("Protection.Construct.Anti-Place.Message", Boolean.valueOf(true));
            Config.set("Protection.Construct.Anti-Place.World.All_World", Boolean.valueOf(false));
            Config.set("Protection.Construct.Anti-Place.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Protection.Construct.Anti-Break.Enable", Boolean.valueOf(true));
            Config.set("Protection.Construct.Anti-Break.Bypass", Boolean.valueOf(true));
            Config.set("Protection.Construct.Anti-Break.Message", Boolean.valueOf(true));
            Config.set("Protection.Construct.Anti-Break.World.All_World", Boolean.valueOf(false));
            Config.set("Protection.Construct.Anti-Break.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Protection.HagingBreakByEntity.Enable", Boolean.valueOf(true));
            Config.set("Protection.HagingBreakByEntity.Bypass", Boolean.valueOf(true));
            Config.set("Protection.HagingBreakByEntity.World.All_World", Boolean.valueOf(false));
            Config.set("Protection.HagingBreakByEntity.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            Config.set("Protection.PlayerInteractEntity-ItemFrame.Enable", Boolean.valueOf(true));
            Config.set("Protection.PlayerInteractEntity-ItemFrame.Bypass", Boolean.valueOf(true));
            Config.set("Protection.PlayerInteractEntity-ItemFrame.World.All_World", Boolean.valueOf(false));
            Config.set("Protection.PlayerInteractEntity-ItemFrame.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
}));
            
            saveConfigFile();

        }
    }

}