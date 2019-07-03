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

            Config.set("JumpPads.Enable", Boolean.valueOf(true));
            Config.set("JumpPads.Options.Block", String.valueOf("REDSTONE_BLOCK"));
            Config.set("JumpPads.Options.Plate", String.valueOf("GOLD_PLATE"));
            Config.set("JumpPads.Options.Height", Integer.valueOf(1));
            Config.set("JumpPads.Options.Length", Integer.valueOf(3));
            Config.set("JumpPads.Sounds.Enable", Boolean.valueOf(true));
            Config.set("JumpPads.Sounds.Sound", "NOTE_PIANO");
            Config.set("JumpPads.Sounds.Volume", Integer.valueOf(10));
            Config.set("JumpPads.Sounds.Pitch", Integer.valueOf(1));
            Config.set("JumpPads.Effect.Enable", Boolean.valueOf(true));
            Config.set("JumpPads.Effect.Effect", "ENDER_SIGNAL");
            Config.set("JumpPads.Effect.Pitch", Integer.valueOf(10));
            Config.set("JumpPads.World.All_World", Boolean.valueOf(false));
            Config.set("JumpPads.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));

            saveConfigFile();

        }
    }
}