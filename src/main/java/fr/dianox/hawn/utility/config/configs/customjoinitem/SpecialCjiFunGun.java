package fr.dianox.hawn.utility.config.configs.customjoinitem;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SpecialCjiFunGun {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public SpecialCjiFunGun() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "CustomJoinItem/Special-FunGun.yml");
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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        Config.set("FunGun.Enable", true);
        Config.set("FunGun.Option.Item-Delay.Enable", true);
        Config.set("FunGun.Option.Item-Delay.Delay", 5);

        Config.set("FunGun.Item.Title", "&6FunGun");
        Config.set("FunGun.Item.Lore", java.util.Arrays.asList(new String[] {
                " ",
                "&c&lAaaaaahhhh"
            }));
        Config.set("FunGun.Item.Material.Amount", 1);

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}