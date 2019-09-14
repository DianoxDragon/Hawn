package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class BroadCastCommandConfig {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public BroadCastCommandConfig() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Commands/Broadcast.yml");
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
            
            Config.set("Broadcast.Enable", true);
            Config.set("Broadcast.Disable-Message", true);
            Config.set("Broadcast.Sounds.Enabled", true);
            Config.set("Broadcast.Sounds.Sound", "NOTE_PIANO");
            Config.set("Broadcast.Sounds.Volume", 10);
            Config.set("Broadcast.Sounds.Pitch", 1);
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);
            
            saveConfigFile();

        }
    }
}