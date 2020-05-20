package fr.dianox.hawn.utility.config.configs.events;

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
            Config.set("Protection.Construct.Anti-Place.Block-Exception.Enable", false);
            Config.set("Protection.Construct.Anti-Place.Block-Exception.Method", "WHITELIST");
            Config.set("Protection.Construct.Anti-Place.Block-Exception.Armor_Stand", false);
            Config.set("Protection.Construct.Anti-Place.Block-Exception.Materials", java.util.Arrays.asList(new String[] {
                    "DIRT"
                }));
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
            Config.set("Protection.Construct.Anti-Break.Block-Exception.Enable", false);
            Config.set("Protection.Construct.Anti-Break.Block-Exception.Method", "WHITELIST");
            Config.set("Protection.Construct.Anti-Break.Block-Exception.Materials", java.util.Arrays.asList(new String[] {
                    "DIRT"
                }));
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
            
            Config.set("Protection.PlayerInteract-Items-Blocks.Enable", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Bypass", true);
            
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_DOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_FENCE_GATE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.ANVIL", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.BEACON", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.RED_BED", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_DOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_FENCE_GATE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.OAK_BOAT", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.BREWING_STAND", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.COMMAND_BLOCK", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.CHEST", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_DOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_FENCE_GATE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.DAYLIGHT_DETECTOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.DISPENSER", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.DROPPER", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.ENCHANTING_TABLE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.ENDER_CHEST", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.OAK_FENCE_GATE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.FURNACE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.HOPPER", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.HOPPER_MINECART", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_DOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_FENCE_GATE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.LEVER", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.MINECART", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.NOTE_BLOCK", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.MINECART", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.COMPARATOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.OAK_SIGN", false);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.CHEST_MINECART", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.OAK_TRAPDOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.TRAPPED_CHEST", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.OAK_BUTTON", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_DOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_FENCE_GATE", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_TRAPDOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_TRAPDOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_TRAPDOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_TRAPDOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_TRAPDOOR", true);
            Config.set("Protection.PlayerInteract-Items-Blocks.Options.SWEET_BERRY_BUSH", true);
            
            Config.set("Protection.PlayerInteract-Items-Blocks.WorldGuard.Enable", false);
            Config.set("Protection.PlayerInteract-Items-Blocks.WorldGuard.Method", "WHITELIST");
            Config.set("Protection.PlayerInteract-Items-Blocks.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("Protection.PlayerInteract-Items-Blocks.World.All_World", false);
            Config.set("Protection.PlayerInteract-Items-Blocks.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Protection.Anti-Bucket-Use.Enable", true);
            Config.set("Protection.Anti-Bucket-Use.Bypass", true);
            Config.set("Protection.Anti-Bucket-Use.WorldGuard.Enable", false);
            Config.set("Protection.Anti-Bucket-Use.WorldGuard.Method", "WHITELIST");
            Config.set("Protection.Anti-Bucket-Use.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            Config.set("Protection.Anti-Bucket-Use.World.All_World", false);
            Config.set("Protection.Anti-Bucket-Use.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            saveConfigFile();

        }
    }

}