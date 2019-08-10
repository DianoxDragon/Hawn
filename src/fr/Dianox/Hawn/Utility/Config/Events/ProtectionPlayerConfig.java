package fr.Dianox.Hawn.Utility.Config.Events;

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
            
            Config.set("Anti-Damage.Enable", Boolean.valueOf(true));
            Config.set("Anti-Damage.Custom.Enable", Boolean.valueOf(false));
            Config.set("Anti-Damage.Bypass-With-Permission", Boolean.valueOf(false));
            
            Config.set("Anti-Damage.WorldGuard.Enable", false);
            Config.set("Anti-Damage.WorldGuard.Method", "WHITELIST");
            Config.set("Anti-Damage.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            
            Config.set("Anti-Damage.World.All_World", Boolean.valueOf(false));
            Config.set("Anti-Damage.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("AntiDamage-Custom.Entity.Options.Entity.EntityDamageByEntity", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.BLOCK_EXPLOSION", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.CONTACT", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.CRAMMING", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.CUSTOM", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.DROWNING", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.DRAGON_BREATH", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.DRYOUT", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.ENTITY_ATTACK", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.ENTITY_EXPLOSION", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.ENTITY_SWEEP_ATTACK", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.FALL", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.FALLING_BLOCK", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.FIRE", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.FIRE_TICK", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.FLY_INTO_WALL", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.HOT_FLOOR", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.LAVA", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.LIGHTNING", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.MAGIC", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.MELTING", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.POISON", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.PROJECTILE", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.STARVATION", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.SUFFOCATION", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.SUICIDE", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.THORNS", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.VOID", Boolean.valueOf(true));
            Config.set("AntiDamage-Custom.Entity.Options.WITHER", Boolean.valueOf(true));
            
            saveConfigFile();

        }
    }

}
