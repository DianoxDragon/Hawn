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
            
            Config.set("ClearChat.Enable", Boolean.valueOf(true));
            Config.set("ClearChat.Lines-To-Clear", Integer.valueOf(150));
            Config.set("ClearChat.Disable-Message", Boolean.valueOf(true));
            Config.set("ClearChat.Anonymous.Enable", Boolean.valueOf(true));
            Config.set("ClearChat.Anonymous.Message-Clear", Boolean.valueOf(true));
            Config.set("ClearChat.Anonymous.Use_Permission", Boolean.valueOf(true));
            Config.set("ClearChat.Anonymous.Disable-Message", Boolean.valueOf(true));
            Config.set("ClearChat.Normal.Enable", Boolean.valueOf(true));
            Config.set("ClearChat.Normal.Message-Clear", Boolean.valueOf(true));
            Config.set("ClearChat.Normal.Use_Permission", Boolean.valueOf(true));
            Config.set("ClearChat.Normal.Disable-Message", Boolean.valueOf(true));
            Config.set("ClearChat.Own.Enable", Boolean.valueOf(true));
            Config.set("ClearChat.Own.Message-Clear", Boolean.valueOf(true));
            Config.set("ClearChat.Own.Use_Permission", Boolean.valueOf(false));
            Config.set("ClearChat.Own.Disable-Message", Boolean.valueOf(true));
            Config.set("ClearChat.Other.Enable", Boolean.valueOf(true));
            Config.set("ClearChat.Other.Message-Clear", Boolean.valueOf(true));
            Config.set("ClearChat.Other.Use_Permission", Boolean.valueOf(false));
            Config.set("ClearChat.Other.Disable-Message", Boolean.valueOf(true));
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
            
            saveConfigFile();

        }
    }
}