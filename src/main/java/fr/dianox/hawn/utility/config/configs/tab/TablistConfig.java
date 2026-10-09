package fr.dianox.hawn.utility.config.configs.tab;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class TablistConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public TablistConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Tablist/Tablist.yml");
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

        Config.set("Tablist.enable", true);
        Config.set("Tablist.refresh-time-ticks", 2);

        Config.set("Tablist.header.enabled", true);
        Config.set("Tablist.header.message", java.util.Arrays.asList(new String[] {
        		"",
        		"&7Thank you to choose &b&lHawn",
        		"",
        		"&7You are &e%player%",
        		"{anim_website}",
        		"",
        		"{anim_maininformations}",
        		"",
        		"{anim_separator}"
        		}));

        Config.set("Tablist.footer.enabled", true);
        Config.set("Tablist.footer.message", java.util.Arrays.asList(new String[] {
        		"{anim_separator}",
        		"",
        		"{anim_hawntitle}",
        		""
        		}));

        Config.set("Animations.Enable", true);
        Config.set("Animations.separator.refresh-time-ticks", 2);
        Config.set("Animations.separator.text", java.util.Arrays.asList(new String[] {
        		"&e&l>> &8&m-------------------&r &e&l<<",
        		"&7&l>&e&l> &8&m-------------------&r &e&l<&7&l<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&e&l>&7&l> &8&m-------------------&7 &7&l<&e&l<",
        		"&e&l>> &8&m-------------------&7 &e&l<<"
        		}));

        Config.set("Animations.hawntitle.refresh-time-ticks", 2);
        Config.set("Animations.hawntitle.text", java.util.Arrays.asList(new String[] {
        		"&8&l> &7&lHawn&8&l <",
        		"&8&l> &7&lHaw&8&l <",
        		"&8&l> &7&lHa&8&l <",
        		"&8&l> &7&lH&8&l <",
        		"&8&l> &8&l <",
        		"&8&l< &8&l >",
        		"&8&l< &7&lH&8&l >",
        		"&8&l< &7&lHa&8&l >",
        		"&8&l< &7&lHaw&8&l >",
        		"&8&l< &7&lHawn&8&l >",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn"
        		}));

        Config.set("Animations.website.refresh-time-ticks", 60);
        Config.set("Animations.website.text", java.util.Arrays.asList(new String[] {
        		"&7Discord&8: &eLink 1",
        		"&7Shop&8: &eLink 2",
        		"&7Website&8: &eLink 3"
        		}));

        Config.set("Animations.maininformations.refresh-time-ticks", 60);
        Config.set("Animations.maininformations.text", java.util.Arrays.asList(new String[] {
        		"&7Time&8: &e%gettime%",
        		"&e%player_x% %player_y% %player_z%"
        		}));

        Config = ConfigDefaults.apply(file, loaded, Config, "Animations");

        convertCustomTablists();

        // The examples of the Tablist folder, created once (like the scoreboards): deleted, they don't come back
        if (!Config.isSet("Examples-Generated")) {
            example("staff", 10, true, null, java.util.Arrays.asList("", "&c&lSTAFF &8- &e%player%", "", "{anim_maininformations}", "", "{anim_separator}"),
                    java.util.Arrays.asList("{anim_separator}", "", "&7Ping&8: &e%ping% ms", ""));
            example("nether", 0, false, java.util.Arrays.asList("world_nether", "world_the_end"),
                    java.util.Arrays.asList("", "&7You are in &c%player_world%", "", "{anim_flames}"), null);

            Config.set("Examples-Generated", true);
            saveConfigFile();
        }
    }

    /**
     * A tab list file of the Tablist folder, off. A null header, footer or worlds list is not written:
     * the tab list then shows the one of Tablist.yml (every world for the worlds).
     */
    private static void example(String name, int priority, boolean permission, java.util.List<String> worlds,
                                java.util.List<String> header, java.util.List<String> footer) {
        File f = new File(pl.getDataFolder(), "Tablist/" + name + ".yml");
        if (f.exists()) return;

        YamlConfiguration cfg = new YamlConfiguration();
        cfg.set("enable", false);
        cfg.set("priority", priority);
        cfg.set("permission", permission);
        cfg.set("World.All_World", worlds == null);
        cfg.set("World.Worlds", worlds == null ? java.util.Collections.emptyList() : worlds);
        if (header != null) {
            cfg.set("header.enabled", true);
            cfg.set("header.message", header);
        }
        if (footer != null) {
            cfg.set("footer.enabled", true);
            cfg.set("footer.message", footer);
        }
        if (name.equals("nether")) {
            cfg.set("Animations.flames.refresh-time-ticks", 10);
            cfg.set("Animations.flames.text", java.util.Arrays.asList("&c&m-------&6&m-------&e&m-------", "&6&m-------&e&m-------&c&m-------", "&e&m-------&c&m-------&6&m-------"));
        }
        ConfigDefaults.apply(f, new YamlConfiguration(), cfg);
    }

    /**
     * The Custom-Tablists section of the first 1.4 test builds: each tab list becomes a file of the Tablist folder.
     */
    private static void convertCustomTablists() {
        org.bukkit.configuration.ConfigurationSection custom = Config.getConfigurationSection("Custom-Tablists");
        if (custom == null) return;

        for (String name : custom.getKeys(false)) {
            org.bukkit.configuration.ConfigurationSection section = custom.getConfigurationSection(name);
            File f = new File(pl.getDataFolder(), "Tablist/" + name + ".yml");
            if (section == null || f.exists()) continue;

            YamlConfiguration cfg = new YamlConfiguration();
            for (String key : section.getKeys(true)) {
                if (!section.isConfigurationSection(key)) cfg.set(key, section.get(key));
            }
            // It was a permission name before the switch, the permission is now hawn.tablist.<file name>
            if (!cfg.isBoolean("permission")) {
                String permission = cfg.getString("permission", "");
                cfg.set("permission", permission != null && !permission.isEmpty());
            }
            ConfigDefaults.apply(f, new YamlConfiguration(), cfg);
        }

        Config.set("Custom-Tablists", null);
        Config.set("Examples-Generated", true);
        saveConfigFile();
        org.bukkit.Bukkit.getLogger().info("[Hawn] Tablist/Tablist.yml: Custom-Tablists moved to one file per tab list in the Tablist folder");
    }

}