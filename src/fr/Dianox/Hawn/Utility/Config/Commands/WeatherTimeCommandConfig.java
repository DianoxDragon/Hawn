package fr.Dianox.Hawn.Utility.Config.Commands;

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

            Config.set("Weather.Set.Sun.Enable", Boolean.valueOf(true));
            Config.set("Weather.Set.Sun.Disable-Message", Boolean.valueOf(true));
            Config.set("Weather.Set.Sun.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
            
            Config.set("Weather.Set.Rain.Enable", Boolean.valueOf(true));
            Config.set("Weather.Set.Rain.Disable-Message", Boolean.valueOf(true));
            Config.set("Weather.Set.Rain.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
            
            Config.set("Weather.Set.Thunder.Enable", Boolean.valueOf(true));
            Config.set("Weather.Set.Thunder.Disable-Message", Boolean.valueOf(true));
            Config.set("Weather.Set.Thunder.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
            
            Config.set("Time.Set.Day.Enable", Boolean.valueOf(true));
            Config.set("Time.Set.Day.Value", Long.valueOf(0));
            Config.set("Time.Set.Day.Disable-Message", Boolean.valueOf(true));
            Config.set("Time.Set.Day.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
            
            Config.set("Time.Set.Night.Enable", Boolean.valueOf(true));
            Config.set("Time.Set.Night.Value", Long.valueOf(16000));
            Config.set("Time.Set.Night.Disable-Message", Boolean.valueOf(true));
            Config.set("Time.Set.Night.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
            
            saveConfigFile();

        }
    }

}
