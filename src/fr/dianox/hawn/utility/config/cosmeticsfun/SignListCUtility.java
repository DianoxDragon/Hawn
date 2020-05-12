package fr.dianox.hawn.utility.config.cosmeticsfun;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SignListCUtility {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public SignListCUtility() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Cosmetics-Fun/Utility/Sign-List.yml");
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

            Config.set("Sign-List.FW1.Text", java.util.Arrays.asList(new String[] {
                    "&eThis is a sign",
                    "----------",
                    " ",
                    "MAGIC"
                }));
            
            Config.set("Sign-List.FW1.Event", java.util.Arrays.asList(new String[] {
                    "&bDefault",
                    "Events works like the customcommand module"
                }));
            
            Config.set("Sign-List.FW2.Text", java.util.Arrays.asList(new String[] {
                    "&cThis is a sign",
                    "----------",
                    " ",
                    "MAGIC 2"
                }));
            
            saveConfigFile();

        }
    }
}