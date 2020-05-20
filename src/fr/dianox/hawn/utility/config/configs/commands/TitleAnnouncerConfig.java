package fr.dianox.hawn.utility.config.configs.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class TitleAnnouncerConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public TitleAnnouncerConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/TitleAnnouncer.yml");
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

            Config.set("Title-Announcer.Enable", true);
            Config.set("Title-Announcer.Title.FadeIn", 20);
            Config.set("Title-Announcer.Title.Stay", 75);
            Config.set("Title-Announcer.Title.FadeOut", 20);
            Config.set("Title-Announcer.Disable-Message", true);
            
            Config.set("Title-Announcer.Options.Write-In-The-Chat-The-Announce", false);
            
            Config.set("Title-Announcer.Options.Firework.Enable", true);
            Config.set("Title-Announcer.Options.Firework.Firework-List", java.util.Arrays.asList(new String[] {
                    "[FWLU]: Firework2"
            }));
            
            Config.set("Title-Announcer.Options.Sound-For-All-Players.Enable", true);
            Config.set("Title-Announcer.Options.Sound-For-All-Players.Sound", "BLOCK_NOTE_HARP");
            Config.set("Title-Announcer.Options.Sound-For-All-Players.Volume", 1);
            Config.set("Title-Announcer.Options.Sound-For-All-Players.Pitch", 1);
            
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }
}