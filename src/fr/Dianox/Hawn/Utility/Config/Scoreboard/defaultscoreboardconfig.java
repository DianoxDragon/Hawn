package fr.Dianox.Hawn.Utility.Config.Scoreboard;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

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
            		"&f&l> &c&lHawn&f&l <",
            		"&f&l> &c&lHaw&f&l <",
            		"&f&l> &c&lHa&f&l <",
            		"&f&l> &c&lH&f&l <",
            		"&f&l> &f&l <",
            		"&f&l< &f&l >",
            		"&f&l< &c&lH&f&l >",
            		"&f&l< &c&lHa&f&l >",
            		"&f&l< &c&lHaw&f&l >",
            		"&f&l< &c&lHawn&f&l >",
            		"&c&lHawn",
            		"&6&lHawn",
            		"&e&lHawn",
            		"&a&lHawn",
            		"&7&lHawn",
            		"&d&lHawn",
            		"&b&lHawn",
            		"&c&lHawn",
            		"&c&lHawn",
            		"&6&lHawn",
            		"&e&lHawn",
            		"&a&lHawn",
            		"&7&lHawn",
            		"&d&lHawn",
            		"&b&lHawn",
            		"&c&lHawn"
            }));
            
            Config.set("text", java.util.Arrays.asList(new String[] {
            		" ",
            		"&aWelcome &e{PLAYER}",
            		" ",
            		"{CH_infoloc}",
            		"{CH_infoloc2}",
            		" ",
            		"&aYour latency",
            		"&7%player_ping% ms",
            		" ",
            		"{SC_info}",
            		" ",
            		"&aA kind thanks to use &6&lHawn"
            }));
            
            Config.set("updater.title", Integer.valueOf(5));
            Config.set("updater.text", Integer.valueOf(5));
            
            Config.set("World.All_World", Boolean.valueOf(false));
            Config.set("World.Worlds", java.util.Arrays.asList(new String[] {
                    "world"
            }));
            
            Config.set("changeableText.infoloc.text", java.util.Arrays.asList(new String[] {
            		"&e%player_x% %player_y% %player_z%",
            		"&aJoined &e%player_first_join_date%",
            		"&aCan fly : &e%player_allow_flight%"
            }));
            Config.set("changeableText.infoloc.interval", Integer.valueOf(6));
            
            Config.set("changeableText.infoloc2.text", java.util.Arrays.asList(new String[] {
            		"&aHealth: &c%player_health% &lHP",
            		"&aInstall PlaceHolderAPI - Player",
            		"&aTo see these variables work"
            }));
            Config.set("changeableText.infoloc2.interval", Integer.valueOf(60));
            
            Config.set("scroller.info.text", String.valueOf("&7Welcome on this server! This server is running on &e&lHawn"));
            Config.set("scroller.info.width", Integer.valueOf(26));
            Config.set("scroller.info.spaceBetween", Integer.valueOf(6));
            Config.set("scroller.info.update", Integer.valueOf(3));
            
            saveConfigFile();

        }
    }

}