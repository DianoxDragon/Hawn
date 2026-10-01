package fr.dianox.hawn.utility.config.configs.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class WorldEventConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public WorldEventConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Events/WorldEvent.yml");
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

            Config.set("World.Weather.Disable.Weather.Enable", true);
            Config.set("World.Weather.Disable.Weather.World.All_World", false);
            Config.set("World.Weather.Disable.Weather.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("World.Weather.Disable.ThunderChange.Enable", true);
            Config.set("World.Weather.Disable.ThunderChange.World.All_World", false);
            Config.set("World.Weather.Disable.ThunderChange.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("World.Weather.Disable.LightningStrike.Disable", true);
            Config.set("World.Weather.Disable.LightningStrike.World.All_World", false);
            Config.set("World.Weather.Disable.LightningStrike.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("World.Time.Always-Day.Enable", true);
            Config.set("World.Time.Always-Day.World.All_World", false);
            Config.set("World.Time.Always-Day.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world"
            }));
            Config.set("World.Time.Always-Night.Enable", true);
            Config.set("World.Time.Always-Night.World.All_World", false);
            Config.set("World.Time.Always-Night.World.Worlds", java.util.Arrays.asList(new String[] {
                    "worldtest"
            }));
            
            Config.set("World.Burn.Disable.Burn-Block.Disable", true);
            Config.set("World.Burn.Disable.Burn-Block.WorldGuard.Enable", false);
            Config.set("World.Burn.Disable.Burn-Block.WorldGuard.Method", "WHITELIST");
            Config.set("World.Burn.Disable.Burn-Block.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("World.Burn.Disable.Burn-Block.World.All_World", false);
            Config.set("World.Burn.Disable.Burn-Block.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("World.Burn.Disable.BlockIgnite-FireSpread.Disable", true);
            Config.set("World.Burn.Disable.BlockIgnite-FireSpread.WorldGuard.Enable", false);
            Config.set("World.Burn.Disable.BlockIgnite-FireSpread.WorldGuard.Method", "WHITELIST");
            Config.set("World.Burn.Disable.BlockIgnite-FireSpread.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("World.Burn.Disable.BlockIgnite-FireSpread.World.All_World", false);
            Config.set("World.Burn.Disable.BlockIgnite-FireSpread.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("World.Explosion.Disable.Explosion.Disable", true);
            Config.set("World.Explosion.Disable.Explosion.WorldGuard.Enable", false);
            Config.set("World.Explosion.Disable.Explosion.WorldGuard.Method", "WHITELIST");
            Config.set("World.Explosion.Disable.Explosion.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("World.Explosion.Disable.Explosion.World.All_World", false);
            Config.set("World.Explosion.Disable.Explosion.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("World.Blocks.Disable.Leave-Decay.Disable", true);
            Config.set("World.Blocks.Disable.Leave-Decay.World.All_World", false);
            Config.set("World.Blocks.Disable.Leave-Decay.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("World.Blocks.Disable.Block-Fade.Disable", true);
            Config.set("World.Blocks.Disable.Block-Fade.World.All_World", false);
            Config.set("World.Blocks.Disable.Block-Fade.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("World.Disable.Spawning-Monster-Animals.Disable", true);
            Config.set("World.Disable.Spawning-Monster-Animals.World.All_World", false);
            Config.set("World.Disable.Spawning-Monster-Animals.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("No-Shears.Enable", true);
            Config.set("No-Shears.Bypass", true);
            Config.set("No-Shears.WorldGuard.Enable", false);
            Config.set("No-Shears.WorldGuard.Method", "WHITELIST");
            Config.set("No-Shears.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("No-Shears.World.All_World", false);
            Config.set("No-Shears.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("DenyEntityTravelPortal.Enable", true);
            Config.set("DenyEntityTravelPortal.World.All_World", false);
            Config.set("DenyEntityTravelPortal.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            saveConfigFile();

        }
    }

}