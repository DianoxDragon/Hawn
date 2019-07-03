package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class OptionPlayerConfigCommand {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public OptionPlayerConfigCommand() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/PlayerOption.yml");
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
            
            Config.set("PlayerOption.Enable", Boolean.valueOf(true));
            Config.set("PlayerOption.Disable-Message", Boolean.valueOf(true));
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));

            saveConfigFile();

        }
    }

}