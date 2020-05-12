package fr.dianox.hawn.utility.config.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CommandEventConfig {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public CommandEventConfig() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Events/OnCommands.yml");
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
            
            Config.set("Block-Commands.Enable", true);
            Config.set("Block-Commands.Bypass", true);
            Config.set("Block-Commands.Message-Enable", true);
            Config.set("Block-Commands.Message", java.util.Arrays.asList(new String[] {"&cSorry... But ! You're noob"}));
            Config.set("Block-Commands.Options.Face-Guardian-1-13-1-14", true);
            Config.set("Block-Commands.Options.Notify-Staff", true);
            Config.set("Block-Commands.List", java.util.Arrays.asList(new String[] {
                    "/pl",
                    "/plugins",
                    "/bukkit:pl",
                    "/bukkit:plugins",
                    "/bukkit:ver",
                    "/ver",
                    "/version",
                    "/icanhasbukkit",
                    "/info",
                    "/essentials:help",
                    "/ehelp",
                    "/minecraft:help",
                    "/bukkit:help",
                    "/bukkit:?",
                    "/bukkit:version",
                    "/seed",
                    "/minecraft:me",
                    "/me",
                    "/about",
                    "/bukkit:about",
                    "/eabout"
                }));
            
            saveConfigFile();

        }
    }

}
