package fr.dianox.hawn.utility.config.cosmeticsfun;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class BookListConfiguration {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public BookListConfiguration() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Cosmetics-Fun/Utility/Book-List.yml");
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
            
            Config.set("Book-List.Book1.Title", "The best book!");
            Config.set("Book-List.Book1.Author", "Dianox");
            Config.set("Book-List.Book1.page1.page", java.util.Arrays.asList(new String[] {
                    "One: Learn how to configure Hawn",
                    "Two: it's just a simple page"
                }));
            Config.set("Book-List.Book1.page2owo.page", java.util.Arrays.asList(new String[] {
                    "Another page wow"
                }));

            Config.set("Book-List.Book2.Title", "&bThe best book!");
            Config.set("Book-List.Book2.Author", "Dianox");
            Config.set("Book-List.Book2.page1.page", java.util.Arrays.asList(new String[] {
                    "The same book with another page",
                    "mmh..",
                    "a single page"
                }));

            
            saveConfigFile();

        }
    }
}