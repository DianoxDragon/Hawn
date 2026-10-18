package fr.dianox.hawn.utility.config.configs;

import fr.dianox.hawn.modules.world.DefaultWorld;
import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigGeneral {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigGeneral() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "general.yml");
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

        Config.set("Plugin.Update.Check-Update", true);
        Config.set("Plugin.Update.Notify-Admins", true);
        Config.set("Plugin.Date-Format", "dd-MM-yyyy");
        Config.set("Plugin.12-Hours-Or-24-Hours-Format", 24);
        Config.set("Plugin.Tps.Warn-system", true);
        Config.set("Plugin.Tps.Warning-At", 15);
        Config.set("Plugin.Tps.Critical-At", 5);
        // Off by default: saving everything while the server already lags makes it worse
        Config.set("Plugin.Tps.Emergency-Save.Enable", false);
        Config.set("Plugin.Tps.Emergency-Save.Worlds", true);
        Config.set("Plugin.Tps.Emergency-Save.Players", true);
        Config.set("Plugin.Tps.Emergency-Save.Spread", true);

        Config.set("Plugin.Language-Type", "en_US");

        // The world written in the "Worlds" lists of the files: the world manager replaces it there when another world becomes the default one
        Config.set(DefaultWorld.PATH, loaded.isSet(DefaultWorld.PATH) ? loaded.getString(DefaultWorld.PATH) : DefaultWorld.guess(pl.getDataFolder()));

        // false: the IP of the players is not written in their data (YAML or MySQL), and the one already written is erased at their next join
        Config.set("Plugin.Players.Save-IP", false);

        // true: the commands are hidden from the players who don't have their permission (restart needed)
        Config.set("Plugin.Commands.Hide-Without-Permission", true);

        // Since 1.4.8: true writes in the console the errors Hawn goes on without (see /hawn debug)
        Config.set("Plugin.Debug", false);

        // Hooks
	        Config.set("Plugin.Use.Hook.PlaceholderAPI.Enable", false);
	        Config.set("Plugin.Use.Hook.PlaceholderAPI.Keep-The-Option", false);
	        Config.set("Plugin.Use.Hook.PlaceholderAPI.Expansion.Enable", true);
	        Config.set("Plugin.Use.Hook.PlaceholderAPI.Expansion.Hidden", java.util.Collections.singletonList("player_ip"));
	        Config.set("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable", false);
	        Config.set("Plugin.Use.Hook.MVdWPlaceholderAPI.Keep-The-Option", false);
	        Config.set("Plugin.Use.Hook.WorldGuard.Enable", false);
	        Config.set("Plugin.Use.Hook.WorldGuard.Keep-The-Option", false);

	        // MySQL
	        Config.set("Plugin.Use.MYSQL.Enable", false);
	        Config.set("Plugin.Use.MYSQL.Host", "localhost");
	        Config.set("Plugin.Use.MYSQL.Username", "root");
	        Config.set("Plugin.Use.MYSQL.Password", "123");
	        Config.set("Plugin.Use.MYSQL.Database", "Hawn");
	        Config.set("Plugin.Use.MYSQL.Port", 3306);
	        Config.set("Plugin.Use.MYSQL.Use-SSL", false);
	        // Since 1.4.4: several servers on the same database count their players together
	        Config.set("Plugin.Use.MYSQL.First-Join-On-The-Network", true);

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}