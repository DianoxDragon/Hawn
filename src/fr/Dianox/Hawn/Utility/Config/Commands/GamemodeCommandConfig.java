package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class GamemodeCommandConfig {
	
	private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public GamemodeCommandConfig() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Commands/Gamemode.yml");
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

            Config.set("Gamemode.Enable", true);
            Config.set("Gamemode.Disable-Message", true);
            Config.set("Gamemode.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("gms.Enable", true);
            Config.set("gms.Disable-Message", true);
            Config.set("gms.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("gmc.Enable", true);
            Config.set("gmc.Disable-Message", true);
            Config.set("gmc.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("gma.Enable", true);
            Config.set("gma.Disable-Message", true);
            Config.set("gma.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("gmsp.Enable", true);
            Config.set("gmsp.Disable-Message", true);
            Config.set("gmsp.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            saveConfigFile();

        }
    }

}
