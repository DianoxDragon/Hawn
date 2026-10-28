package fr.dianox.hawn.utility.config.configs.customjoinitem;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

// The grappling hook (since 1.5.5)
public class SpecialCjiGrappling {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public SpecialCjiGrappling() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "CustomJoinItem/Special-Grappling.yml");
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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        Config.set("Grappling.Enable", true);
        Config.set("Grappling.Option.Item-Delay.Enable", true);
        Config.set("Grappling.Option.Item-Delay.Delay", 3);
        Config.set("Grappling.Option.Force", 1.0);
        Config.set("Grappling.Option.Max-Distance", 30);
        Config.set("Grappling.Option.No-Fall-Damage", true);
        Config.set("Grappling.Sound.Enable", true);
        Config.set("Grappling.Sound.Sound", "ENTITY_BAT_TAKEOFF");
        Config.set("Grappling.Sound.Volume", 1);
        Config.set("Grappling.Sound.Pitch", 1);

        Config.set("Grappling.Item.Title", "&6Grappling hook");
        Config.set("Grappling.Item.Lore", java.util.Arrays.asList(new String[] {
                " ",
                "&7Throw it on a block, then pull"
            }));
        Config.set("Grappling.Item.Material.Amount", 1);

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}
