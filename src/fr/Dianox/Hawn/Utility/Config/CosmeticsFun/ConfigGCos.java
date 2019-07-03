package fr.Dianox.Hawn.Utility.Config.CosmeticsFun;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigGCos {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public ConfigGCos() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Cosmetics-Fun/OnJoin.yml");
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

            Config.set("Cosmetics.Firework.Enable", Boolean.valueOf(true));
            Config.set("Cosmetics.Firework.Bypass", Boolean.valueOf(false));
            Config.set("Cosmetics.Firework.Options.Amount", Integer.valueOf(2));
            Config.set("Cosmetics.Firework.Options.Height", Integer.valueOf(3));
            Config.set("Cosmetics.Firework.Options.Flicker", Boolean.valueOf(false));
            Config.set("Cosmetics.Firework.Options.Trail", Boolean.valueOf(false));
            Config.set("Cosmetics.Firework.Options.Type", "BALL");
            Config.set("Cosmetics.Firework.Options.Instant-explode", Boolean.valueOf(false));
            Config.set("Cosmetics.Firework.Options.Power", Integer.valueOf(3));
            Config.set("Cosmetics.Firework.Options.Colors", java.util.Arrays.asList(new String[] {
                    "YELLOW",
                    "RED"
                }));
            Config.set("Cosmetics.Firework.Options.Fade", java.util.Arrays.asList(new String[] {
                    "BLUE",
                    "WHITE"
                }));
            Config.set("Cosmetics.Firework.World.All_World", Boolean.valueOf(false));
            Config.set("Cosmetics.Firework.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            saveConfigFile();

        }
    }
}