package fr.dianox.hawn.utility.config.configs.events;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ProtectionPlayerConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ProtectionPlayerConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Events/ProtectionPlayer.yml");
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
            } catch (IOException ignored) {}
            
            Config.set("Anti-Damage.Enable", true);
            Config.set("Anti-Damage.Custom.Enable", false);
            Config.set("Anti-Damage.Bypass-With-Permission", false);
            
            Config.set("Anti-Damage.WorldGuard.Enable", false);
            Config.set("Anti-Damage.WorldGuard.Method", "WHITELIST");
            Config.set("Anti-Damage.WorldGuard.Regions", java.util.Arrays.asList("region1",
                    "whatyouwant"));
            
            Config.set("Anti-Damage.World.All_World", false);
            Config.set("Anti-Damage.World.Worlds", java.util.Arrays.asList("world",
                    "world_nether"));

            Config.set("AntiDamage-Custom.Entity.Options.Entity.EntityDamageByEntity", true);

            Config.set("AntiDamage-Custom.Entity.Options.Damage-Type-List", java.util.Arrays.asList(
                    "BLOCK_EXPLOSION",
                    "CONTACT",
                    "CRAMMING",
                    "CUSTOM",
                    "DROWNING",
                    "DRAGON_BREATH",
                    "DRYOUT",
                    "ENTITY_ATTACK",
                    "ENTITY_EXPLOSION",
                    "ENTITY_SWEEP_ATTACK",
                    "FALL",
                    "FALLING_BLOCK",
                    "FIRE",
                    "FIRE_TICK",
                    "FLY_INTO_WALL",
                    "HOT_FLOOR",
                    "LAVA",
                    "LIGHTNING",
                    "MAGIC",
                    "MELTING",
                    "POISON",
                    "PROJECTILE",
                    "STARVATION",
                    "SUFFOCATION",
                    "SUICIDE",
                    "THORNS",
                    "VOID",
                    "WITHER"));

            saveConfigFile();

        }
    }

}