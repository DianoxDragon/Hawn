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

            Config.set("Protection.Construct.Anti-Place.Enable", true);
            Config.set("Protection.Construct.Anti-Place.Bypass", true);
            Config.set("Protection.Construct.Anti-Place.Message", true);
            Config.set("Protection.Construct.Anti-Place.WorldGuard.Enable", false);
            Config.set("Protection.Construct.Anti-Place.WorldGuard.Method", "BLACKLIST");
            Config.set("Protection.Construct.Anti-Place.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("Protection.Construct.Anti-Place.World.All_World", false);
            Config.set("Protection.Construct.Anti-Place.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Protection.Construct.Anti-Break.Enable", true);
            Config.set("Protection.Construct.Anti-Break.Bypass", true);
            Config.set("Protection.Construct.Anti-Break.Message", true);
            Config.set("Protection.Construct.Anti-Break.WorldGuard.Enable", false);
            Config.set("Protection.Construct.Anti-Break.WorldGuard.Method", "WHITELIST");
            Config.set("Protection.Construct.Anti-Break.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("Protection.Construct.Anti-Break.World.All_World", false);
            Config.set("Protection.Construct.Anti-Break.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Protection.HagingBreakByEntity.Enable", true);
            Config.set("Protection.HagingBreakByEntity.Bypass", true);
            Config.set("Protection.HagingBreakByEntity.WorldGuard.Enable", false);
            Config.set("Protection.HagingBreakByEntity.WorldGuard.Method", "WHITELIST");
            Config.set("Protection.HagingBreakByEntity.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("Protection.HagingBreakByEntity.World.All_World", false);
            Config.set("Protection.HagingBreakByEntity.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            Config.set("Protection.PlayerInteractEntity-ItemFrame.Enable", true);
            Config.set("Protection.PlayerInteractEntity-ItemFrame.Bypass", true);
            Config.set("Protection.PlayerInteractEntity-ItemFrame.WorldGuard.Enable", false);
            Config.set("Protection.PlayerInteractEntity-ItemFrame.WorldGuard.Method", "WHITELIST");
            Config.set("Protection.PlayerInteractEntity-ItemFrame.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("Protection.PlayerInteractEntity-ItemFrame.World.All_World", false);
            Config.set("Protection.PlayerInteractEntity-ItemFrame.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
	    }));
            
	    Config.set("PlayerInteract-Items-Blocks.Enable", true);
            Config.set("PlayerInteract-Items-Blocks.Bypass", true);
            Config.set("PlayerInteract-Items-Blocks.WorldGuard.Enable", false);
            Config.set("PlayerInteract-Items-Blocks.WorldGuard.Method", "WHITELIST");
            Config.set("PlayerInteract-Items-Blocks.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("PlayerInteract-Items-Blocks.World.All_World", false);
            Config.set("PlayerInteract-Items-Blocks.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
		
            saveConfigFile();

        }
    }

}
