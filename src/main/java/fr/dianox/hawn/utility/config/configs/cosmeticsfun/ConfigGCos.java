package fr.dianox.hawn.utility.config.configs.cosmeticsfun;

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

            Config.set("Cosmetics.Firework.Enable", true);
            Config.set("Cosmetics.Firework.Bypass", false);
            
            Config.set("Cosmetics.Firework.Options.First-Join-Only", false);
            Config.set("Cosmetics.Firework.Options.Firework-List", java.util.Arrays.asList(new String[] {
                    "[FWLU]: Firework1"
            }));
            
            Config.set("Cosmetics.Firework.World.All_World", false);
            Config.set("Cosmetics.Firework.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Cosmetics.Lightning-Strike.Enable", true);
            Config.set("Cosmetics.Lightning-Strike.Bypass", false);
            Config.set("Cosmetics.Lightning-Strike.Options.First-Join-Only", false);
            Config.set("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes", 3);

            Config.set("Cosmetics.Lightning-Strike.World.All_World", false);
            Config.set("Cosmetics.Lightning-Strike.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            saveConfigFile();

        }
    }
}