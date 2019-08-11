package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ClearChatCommandConfig {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public ClearChatCommandConfig() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Commands/ClearChat.yml");
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
            
            Config.set("ClearChat.Enable", true);
            Config.set("ClearChat.Lines-To-Clear", 150);
            Config.set("ClearChat.Disable-Message", true);
            Config.set("ClearChat.Anonymous.Enable", true);
            Config.set("ClearChat.Anonymous.Message-Clear", true);
            Config.set("ClearChat.Anonymous.Use_Permission", true);
            Config.set("ClearChat.Anonymous.Disable-Message", true);
            Config.set("ClearChat.Normal.Enable", true);
            Config.set("ClearChat.Normal.Message-Clear", true);
            Config.set("ClearChat.Normal.Use_Permission", true);
            Config.set("ClearChat.Normal.Disable-Message", true);
            Config.set("ClearChat.Own.Enable", true);
            Config.set("ClearChat.Own.Message-Clear", true);
            Config.set("ClearChat.Own.Use_Permission", false);
            Config.set("ClearChat.Own.Disable-Message", true);
            Config.set("ClearChat.Other.Enable", true);
            Config.set("ClearChat.Other.Message-Clear", true);
            Config.set("ClearChat.Other.Use_Permission", false);
            Config.set("ClearChat.Other.Disable-Message", true);
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);
            
            saveConfigFile();

        }
    }
}
