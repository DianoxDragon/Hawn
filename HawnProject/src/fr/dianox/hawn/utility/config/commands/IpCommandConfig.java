package fr.dianox.hawn.utility.config.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class IpCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public IpCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/Ip.yml");
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

            Config.set("Ip.Enable", true);
            Config.set("Ip.Disable-Message", true);
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }

}