package fr.dianox.hawn.utility.config.configs;

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
            Config.set("On-Join.Message", java.util.Arrays.asList(new String[] {
            		"&cThe multi line",
            		"&bworks like that %player%"
            }));
            
            Config.set("Anti-WDL.Kick-Message", java.util.Arrays.asList(new String[] {
            		"&cSorry you used A world downloader"
            }));
            
            Config.set("Motd.Classic.Enable", true);
            Config.set("Motd.Classic.Random", true);
            Config.set("Motd.Classic.Main.Line-1", "&cThis is a test of motd of course &7- &e%gettime%");
            Config.set("Motd.Classic.Main.Line-2", "&eThanks to choose &lhawn");
            
            Config.set("Motd.Classic.Random-List.first.Line-1", "&aThis is a test of motd of course &7- &e%gettime%");
            Config.set("Motd.Classic.Random-List.first.Line-2", "&eThanks to choose &lhawn");
            Config.set("Motd.Classic.Random-List.second.Line-1", "&eThis is a test of motd of course &7- &e%gettime%");
            Config.set("Motd.Classic.Random-List.second.Line-2", "&eThanks to choose &lhawn");
            Config.set("Motd.Classic.Random-List.itsunlimited.Line-1", "&bThis is a test of motd of course &7- &e%gettime%");
            Config.set("Motd.Classic.Random-List.itsunlimited.Line-2", "&eThanks to choose &lhawn");
            
            Config.set("Motd.WhiteList.Enable", true);
            Config.set("Motd.WhiteList.Line-1", "&eThe server is on whitelist");
            Config.set("Motd.WhiteList.Line-2", "&bPlease come back later");
            Config.set("Motd.Maintenance.Enable", true);
            Config.set("Motd.Maintenance.Line-1", "&cThe server is in maintenance");
            Config.set("Motd.Maintenance.Line-2", "&bPlease come back later");
            Config.set("Motd.Urgent.Enable", true);
            Config.set("Motd.Urgent.Line-1", "&cThe server is whitelisted for now");
            Config.set("Motd.Urgent.Line-2", "&ePlease come back later");
            
            saveConfigFile();

        }
    }

}