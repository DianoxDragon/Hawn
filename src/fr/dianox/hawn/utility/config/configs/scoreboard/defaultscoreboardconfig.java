package fr.dianox.hawn.utility.config.configs.scoreboard;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class defaultscoreboardconfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public defaultscoreboardconfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Scoreboard/scoreboard.default.yml");
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
            
            Config.set("title", java.util.Arrays.asList(new String[] {
            		"&f&l> &3&lHawn&f&l <",
            		"&f&l> &3&lHaw&f&l <",
            		"&f&l> &3&lHa&f&l <",
            		"&f&l> &3&lH&f&l <",
            		"&f&l> &f&l <",
            		"&f&l< &f&l >",
            		"&f&l< &3&lH&f&l >",
            		"&f&l< &3&lHa&f&l >",
            		"&f&l< &3&lHaw&f&l >",
            		"&f&l< &3&lHawn&f&l >",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&b&lHawn",
            		"&b&lHawn",
            		"&b&lHawn",
            		"&b&lHawn",
            		"&b&lHawn",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&3&lHawn",
            		"&3&lHawn"
            }));
            
            Config.set("text", java.util.Arrays.asList(new String[] {
            		" ",
            		"&7Welcome &e%player%",
            		" ",
            		"{CH_infoloc}",
            		"{CH_infoloc2}",
            		" ",
            		"&7Your latency",
            		"&e%ping% ms",
            		" ",
            		"{SC_info}",
            		" ",
            		"&7Thanks to use &e&lHawn"
            }));
            
            Config.set("updater.title", 5);
            Config.set("updater.scoreboard", 5);
            
            Config.set("World.All_World", false);
            Config.set("World.Worlds", java.util.Arrays.asList(new String[] {
                    "world"
            }));
            
            Config.set("changeableText.infoloc.text", java.util.Arrays.asList(new String[] {
            		"&e%player_x% %player_y% %player_z%",
            		"&7Exp: &e%player_level% &7(&e%player_exp%&7/&6%player_exp_to_level%&7)",
            		"&7Time: &e%gettime%"
            }));
            Config.set("changeableText.infoloc.interval", 120);
            
            Config.set("changeableText.infoloc2.text", java.util.Arrays.asList(new String[] {
            		"&7Health: &e%player_health% &6&lHP",
            		"&7Food: &e%player_food_level%",
            		"&7World: &e%player_world%"
            }));
            Config.set("changeableText.infoloc2.interval", 120);
            
            Config.set("scroller.info.text", String.valueOf("&7Welcome on this server! This server is running on &eHawn&r  -"));
            Config.set("scroller.info.width", 27);
            Config.set("scroller.info.spaceBetween", 2);
            Config.set("scroller.info.update", 3);
            
            saveConfigFile();

        }
    }

}