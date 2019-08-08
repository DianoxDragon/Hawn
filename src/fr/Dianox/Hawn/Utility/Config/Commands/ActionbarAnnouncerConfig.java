package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ActionbarAnnouncerConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ActionbarAnnouncerConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/ActionBarAnnouncer.yml");
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

            Config.set("ActionBar-Announcer.Enable", true);
            Config.set("ActionBar-Announcer.Action-Bar.Stay", 75);
            Config.set("ActionBar-Announcer.Disable-Message", true);
            
            Config.set("ActionBar-Announcer.Options.Write-In-The-Chat-The-Announce", false);
            
            Config.set("ActionBar-Announcer.Options.Sound-For-All-Players.Enable", true);
            Config.set("ActionBar-Announcer.Options.Sound-For-All-Players.Sound", "BLOCK_NOTE_HARP");
            Config.set("ActionBar-Announcer.Options.Sound-For-All-Players.Volume", 1);
            Config.set("ActionBar-Announcer.Options.Sound-For-All-Players.Pitch", 1);
            
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }
}
