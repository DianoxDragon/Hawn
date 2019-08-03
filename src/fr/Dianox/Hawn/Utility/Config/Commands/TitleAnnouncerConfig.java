package fr.Dianox.Hawn.Utility.Config.Commands;

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
            
            Config.set("Title-Announcer.Options.Write-In-The-Chat-The-Announce", true);
            
            Config.set("Title-Announcer.Options.Firework.Enable", true);
            Config.set("Title-Announcer.Options.Firework.Amount", 2);
            Config.set("Title-Announcer.Options.Firework.Height", 3);
            Config.set("Title-Announcer.Options.Firework.Flicker", false);
            Config.set("Title-Announcer.Options.Firework.Trail", false);
            Config.set("Title-Announcer.Options.Firework.Type", "BALL");
            Config.set("Title-Announcer.Options.Firework.Instant-explode", false);
            Config.set("Title-Announcer.Options.Firework.Power", 3);
            Config.set("Title-Announcer.Options.Firework.Colors", java.util.Arrays.asList(new String[] {
                    "YELLOW",
                    "RED"
                }));
            Config.set("Title-Announcer.Options.Firework.Fade", java.util.Arrays.asList(new String[] {
                    "BLUE",
                    "WHITE"
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
