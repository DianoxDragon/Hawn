package fr.Dianox.Hawn.Utility.Config.CosmeticsFun;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigGLP {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public ConfigGLP() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Cosmetics-Fun/JumpPads.yml");
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

            Config.set("JumpPads.Enable", true);
            
            Config.set("JumpPads.Options.Block", "REDSTONE_BLOCK");
            Config.set("JumpPads.Options.Plate", "GOLD_PLATE");
            Config.set("JumpPads.Options.Height", 1);
            Config.set("JumpPads.Options.Length", 3);
            
            Config.set("JumpPads.Sounds.Enable", true);
            Config.set("JumpPads.Sounds.Play-for-all-players", true);
            Config.set("JumpPads.Sounds.Sound", "NOTE_PIANO");
            Config.set("JumpPads.Sounds.Volume", 10);
            Config.set("JumpPads.Sounds.Pitch", 1);
            
            Config.set("JumpPads.Effect.Enable", true);
            Config.set("JumpPads.Effect.Effect", "ENDER_SIGNAL");
            Config.set("JumpPads.Effect.Pitch", 10);
            
            Config.set("JumpPads.Send-Message.Enable", true);
            Config.set("JumpPads.Send-Message.Messages", java.util.Arrays.asList(new String[] {
                    "%prefix% &eWhoosh!"
            }));
            
            Config.set("JumpPads.Cooldown.Enable", true);
            Config.set("JumpPads.Cooldown.Ticks", 60);
            
            Config.set("JumpPads.World.All_World", false);
            Config.set("JumpPads.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));

            saveConfigFile();

        }
    }
}
