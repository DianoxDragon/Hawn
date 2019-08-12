package fr.Dianox.Hawn.Utility.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ServerListConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ServerListConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "ServerList.yml");
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
            
            Config.set("Slots.One-Slot-Free", true);
            Config.set("Slots.Fake-Max-Player.Enable", false);
            Config.set("Slots.Fake-Max-Player.Number", 2000);
            Config.set("On-Join.Player-With-Permission-Join-Full-Server", true);
            Config.set("On-Join.Message", "&cThe multi line \n&bworks like that %player%");
            Config.set("Motd.Classic.Enable", true);
            Config.set("Motd.Classic.Line-1", "&cThis is a test of motd of course");
            Config.set("Motd.Classic.Line-2", "&eThanks to choose &lhawn");
            Config.set("Motd.WhiteList.Enable", true);
            Config.set("Motd.WhiteList.Line-1", "&eServer is on whitelist");
            Config.set("Motd.WhiteList.Line-2", "&bPlease come back later");
            
            saveConfigFile();

        }
    }

}
