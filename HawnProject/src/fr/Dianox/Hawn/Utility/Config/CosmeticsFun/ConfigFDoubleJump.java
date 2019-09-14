package fr.Dianox.Hawn.Utility.Config.CosmeticsFun;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigFDoubleJump {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public ConfigFDoubleJump() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Cosmetics-Fun/DoubleJump.yml");
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

            Config.set("DoubleJump.Enable", true);
            Config.set("DoubleJump.Double.Enable", true);
            Config.set("DoubleJump.Double.Use_Permission", true);
            Config.set("DoubleJump.Double.Sounds.Enable", true);
            Config.set("DoubleJump.Double.Sounds.Sound", "NOTE_PIANO");
            Config.set("DoubleJump.Double.Sounds.Volume", 10);
            Config.set("DoubleJump.Double.Sounds.Pitch", 1);
            Config.set("DoubleJump.Double.World.All_World", false);
            Config.set("DoubleJump.Double.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            saveConfigFile();

        }
    }
}