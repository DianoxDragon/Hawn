package fr.Dianox.Hawn.Utility.Config.Tab;

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
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {}
            
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
            		"{anim_hawntitle}"
            		}));
            
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
            
            saveConfigFile();

        }
    }

}
