package fr.Dianox.Hawn.Utility.Config.Messages;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMEvents {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMEvents() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/Classic/Events.yml");
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

            Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"&eNooooooooooooooooooooo........."}));
            Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&e[Anti-Swear] &c%player% &esaid &6%message%"}));
            
            Config.set("Cancel-Tp.Warp.Enable", Boolean.valueOf(true));
            Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"&cTp cancelled"}));
            Config.set("Cancel-Tp.Spawn.Enable", Boolean.valueOf(true));
            Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"&cTp cancelled"}));

            saveConfigFile();

        }
    }

}