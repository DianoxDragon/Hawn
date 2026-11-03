package fr.dianox.hawn.utility.config.configs.customjoinitem;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

// The pearl mount (since 1.5.6)
public class SpecialCjiPearlMount {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public SpecialCjiPearlMount() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "CustomJoinItem/Special-PearlMount.yml");
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

        Config.set("PearlMount.Enable", true);
        Config.set("PearlMount.Option.Item-Delay.Enable", true);
        Config.set("PearlMount.Option.Item-Delay.Delay", 5);
        Config.set("PearlMount.Option.Ride", true);
        Config.set("PearlMount.Option.Speed", 1.5);
        Config.set("PearlMount.Option.Steer.Enable", true);
        Config.set("PearlMount.Option.Steer.Strength", 0.2);
        Config.set("PearlMount.Option.Max-Distance", 60);
        Config.set("PearlMount.Option.Max-Seconds", 8);
        Config.set("PearlMount.Option.No-Fall-Damage", true);
        Config.set("PearlMount.Option.Height.Enable", false);
        Config.set("PearlMount.Option.Height.Min-Y", 0);
        Config.set("PearlMount.Option.Height.Max-Y", 256);
        Config.set("PearlMount.Sound.Enable", true);
        Config.set("PearlMount.Sound.Sound", "ENTITY_ENDER_PEARL_THROW");
        Config.set("PearlMount.Sound.Volume", 1);
        Config.set("PearlMount.Sound.Pitch", 1);
        Config.set("PearlMount.Trail.Enable", true);
        Config.set("PearlMount.Trail.Particle", "DUST");
        Config.set("PearlMount.Trail.Amount", 10);
        Config.set("PearlMount.Trail.Offset", 0.15);
        Config.set("PearlMount.Trail.Speed", 0.05);
        Config.set("PearlMount.Trail.Color", "170,0,255");

        Config.set("PearlMount.Item.Title", "&5Pearl mount");
        Config.set("PearlMount.Item.Lore", java.util.Arrays.asList(new String[] {
                " ",
                "&7Right click to ride the pearl"
            }));
        Config.set("PearlMount.Item.Material.Amount", 1);

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}
