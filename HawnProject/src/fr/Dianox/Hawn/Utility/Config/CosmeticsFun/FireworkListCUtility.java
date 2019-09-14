package fr.Dianox.Hawn.Utility.Config.CosmeticsFun;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FireworkListCUtility {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public FireworkListCUtility() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Cosmetics-Fun/Utility/Firework-List.yml");
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

            Config.set("Firework-List.Firework1.Options.Amount", 2);
            Config.set("Firework-List.Firework1.Options.Height", 3);
            Config.set("Firework-List.Firework1.Options.Flicker", false);
            Config.set("Firework-List.Firework1.Options.Trail", false);
            Config.set("Firework-List.Firework1.Options.Type", "BALL");
            Config.set("Firework-List.Firework1.Options.Instant-explode", false);
            Config.set("Firework-List.Firework1.Options.Power", 3);
            Config.set("Firework-List.Firework1.Options.Colors", java.util.Arrays.asList(new String[] {
                    "YELLOW",
                    "RED"
                }));
            Config.set("Firework-List.Firework1.Options.Fade", java.util.Arrays.asList(new String[] {
                    "BLUE",
                    "WHITE"
                }));
            
            Config.set("Firework-List.Firework2.Options.Amount", 2);
            Config.set("Firework-List.Firework2.Options.Height", 3);
            Config.set("Firework-List.Firework2.Options.Flicker", false);
            Config.set("Firework-List.Firework2.Options.Trail", false);
            Config.set("Firework-List.Firework2.Options.Type", "BALL");
            Config.set("Firework-List.Firework2.Options.Instant-explode", true);
            Config.set("Firework-List.Firework2.Options.Power", 1);
            Config.set("Firework-List.Firework2.Options.Colors", java.util.Arrays.asList(new String[] {
                    "YELLOW",
                    "BLUE"
                }));
            Config.set("Firework-List.Firework1.Options.Fade", java.util.Arrays.asList(new String[] {
                    "GREEN",
                    "RED"
                }));
            
            saveConfigFile();

        }
    }
}