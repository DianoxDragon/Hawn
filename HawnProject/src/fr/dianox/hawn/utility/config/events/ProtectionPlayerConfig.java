package fr.dianox.hawn.utility.config.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

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
            } catch (IOException e) {}
            
            Config.set("Anti-Damage.Enable", true);
            Config.set("Anti-Damage.Custom.Enable", false);
            Config.set("Anti-Damage.Bypass-With-Permission", false);
            
            Config.set("Anti-Damage.WorldGuard.Enable", false);
            Config.set("Anti-Damage.WorldGuard.Method", "WHITELIST");
            Config.set("Anti-Damage.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            
            Config.set("Anti-Damage.World.All_World", false);
            Config.set("Anti-Damage.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("AntiDamage-Custom.Entity.Options.Entity.EntityDamageByEntity", true);
            Config.set("AntiDamage-Custom.Entity.Options.BLOCK_EXPLOSION", true);
            Config.set("AntiDamage-Custom.Entity.Options.CONTACT", true);
            Config.set("AntiDamage-Custom.Entity.Options.CRAMMING", true);
            Config.set("AntiDamage-Custom.Entity.Options.CUSTOM", true);
            Config.set("AntiDamage-Custom.Entity.Options.DROWNING", true);
            Config.set("AntiDamage-Custom.Entity.Options.DRAGON_BREATH", true);
            Config.set("AntiDamage-Custom.Entity.Options.DRYOUT", true);
            Config.set("AntiDamage-Custom.Entity.Options.ENTITY_ATTACK", true);
            Config.set("AntiDamage-Custom.Entity.Options.ENTITY_EXPLOSION", true);
            Config.set("AntiDamage-Custom.Entity.Options.ENTITY_SWEEP_ATTACK", true);
            Config.set("AntiDamage-Custom.Entity.Options.FALL", true);
            Config.set("AntiDamage-Custom.Entity.Options.FALLING_BLOCK", true);
            Config.set("AntiDamage-Custom.Entity.Options.FIRE", true);
            Config.set("AntiDamage-Custom.Entity.Options.FIRE_TICK", true);
            Config.set("AntiDamage-Custom.Entity.Options.FLY_INTO_WALL", true);
            Config.set("AntiDamage-Custom.Entity.Options.HOT_FLOOR", true);
            Config.set("AntiDamage-Custom.Entity.Options.LAVA", true);
            Config.set("AntiDamage-Custom.Entity.Options.LIGHTNING", true);
            Config.set("AntiDamage-Custom.Entity.Options.MAGIC", true);
            Config.set("AntiDamage-Custom.Entity.Options.MELTING", true);
            Config.set("AntiDamage-Custom.Entity.Options.POISON", true);
            Config.set("AntiDamage-Custom.Entity.Options.PROJECTILE", true);
            Config.set("AntiDamage-Custom.Entity.Options.STARVATION", true);
            Config.set("AntiDamage-Custom.Entity.Options.SUFFOCATION", true);
            Config.set("AntiDamage-Custom.Entity.Options.SUICIDE", true);
            Config.set("AntiDamage-Custom.Entity.Options.THORNS", true);
            Config.set("AntiDamage-Custom.Entity.Options.VOID", true);
            Config.set("AntiDamage-Custom.Entity.Options.WITHER", true);
            
            saveConfigFile();

        }
    }

}