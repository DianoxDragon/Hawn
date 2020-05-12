package fr.dianox.hawn.utility.config.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class WeatherTimeCommandConfig {
	
	private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public WeatherTimeCommandConfig() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Commands/Weather-Time.yml");
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

            Config.set("Weather.Set.Sun.Enable", true);
            Config.set("Weather.Set.Sun.Disable-Message", true);
            Config.set("Weather.Set.Sun.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("Weather.Set.Rain.Enable", true);
            Config.set("Weather.Set.Rain.Disable-Message", true);
            Config.set("Weather.Set.Rain.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("Weather.Set.Thunder.Enable", true);
            Config.set("Weather.Set.Thunder.Disable-Message", true);
            Config.set("Weather.Set.Thunder.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("Time.Set.Day.Enable", true);
            Config.set("Time.Set.Day.Value", 0);
            Config.set("Time.Set.Day.Disable-Message", true);
            Config.set("Time.Set.Day.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("Time.Set.Night.Enable", true);
            Config.set("Time.Set.Night.Value", 16000);
            Config.set("Time.Set.Night.Disable-Message", true);
            Config.set("Time.Set.Night.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            saveConfigFile();

        }
    }

}
