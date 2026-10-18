package fr.dianox.hawn.utility.config.configs;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        Config.set("Slots.One-Slot-Free", true);
        Config.set("Slots.Fake-Max-Player.Enable", false);
        Config.set("Slots.Fake-Max-Player.Number", 2000);

        Config.set("On-Join.Player-With-Permission-Join-Full-Server", true);
        Config.set("On-Join.Message", java.util.Arrays.asList("&cThe multi line",
		            "&bworks like that %player%"));

        Config.set("Anti-WDL.Enable", true);
        Config.set("Anti-WDL.Kick-Message", Collections.singletonList("&cSorry you use a world downloader"));

        Config.set("Motd.Classic.Enable", true);
        Config.set("Motd.Classic.Random", true);
        Config.set("Motd.Classic.Main.Line-1", "&6&lYour Server &8- &7Lobby");
        Config.set("Motd.Classic.Main.Line-2", "&eplay.yourserver.net &8- &7%gettime%");

        // Random: true, one of them at each ping (as many as you want)
        Config.set("Motd.Classic.Random-List.discord.Line-1", "&6&lYour Server &8- &7Lobby");
        Config.set("Motd.Classic.Random-List.discord.Line-2", "&7Join our Discord: &ediscord.gg/yourserver");
        Config.set("Motd.Classic.Random-List.event.Line-1", "&6&lYour Server &8- &bEvent this weekend");
        Config.set("Motd.Classic.Random-List.event.Line-2", "&eplay.yourserver.net");
        Config.set("Motd.Classic.Random-List.vote.Line-1", "&6&lYour Server &8- &7Lobby");
        Config.set("Motd.Classic.Random-List.vote.Line-2", "&7Vote for us: &eyourserver.net/vote");

        Config.set("Motd.WhiteList.Enable", true);
        Config.set("Motd.WhiteList.Line-1", "&eThe server is on whitelist");
        Config.set("Motd.WhiteList.Line-2", "&bPlease come back later");
        Config.set("Motd.Maintenance.Enable", true);
        Config.set("Motd.Maintenance.Line-1", "&cThe server is in maintenance");
        Config.set("Motd.Maintenance.Line-2", "&bPlease come back later");
        Config.set("Motd.Urgent.Enable", true);
        Config.set("Motd.Urgent.Line-1", "&cThe server is whitelisted for now");
        Config.set("Motd.Urgent.Line-2", "&ePlease come back later");

        Config = ConfigDefaults.apply(file, loaded, Config, "Motd.Classic.Random-List");
    }

}