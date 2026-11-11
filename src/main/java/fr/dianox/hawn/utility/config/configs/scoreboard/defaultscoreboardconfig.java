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
            		"{lang_text.sb-welcome}",
            		" ",
            		"{CH_infoloc}",
            		"{CH_infoloc2}",
            		" ",
            		"{lang_text.sb-latency}",
            		"&e%ping% ms",
            		" ",
            		"{lang_text.sb-online}",
            		" ",
            		"{SC_info}",
            		" ",
            		"&eplay.yourserver.net"
            }));
            
            Config.set("updater.title", 5);
            Config.set("updater.scoreboard", 5);
            
            Config.set("World.All_World", false);
            Config.set("World.Worlds", java.util.Arrays.asList(new String[] {
                    "world"
            }));
            
            Config.set("changeableText.infoloc.text", java.util.Arrays.asList(new String[] {
            		"&e%player_x% %player_y% %player_z%",
            		"{lang_text.sb-exp}",
            		"{lang_text.sb-time}"
            }));
            Config.set("changeableText.infoloc.interval", 120);
            
            Config.set("changeableText.infoloc2.text", java.util.Arrays.asList(new String[] {
            		"{lang_text.sb-health}",
            		"{lang_text.sb-food}",
            		"{lang_text.sb-world}"
            }));
            Config.set("changeableText.infoloc2.interval", 120);
            
            Config.set("scroller.info.text", String.valueOf("&7Welcome on &6Your Server&7! Have fun  -"));
            Config.set("scroller.info.width", 27);
            Config.set("scroller.info.spaceBetween", 2);
            Config.set("scroller.info.update", 3);
            
            // Since 1.5.1: the worlds of this server, not "world"
            fr.dianox.hawn.modules.world.DefaultWorld.adaptDefaults(file, Config);
            saveConfigFile();

        }
    }

}