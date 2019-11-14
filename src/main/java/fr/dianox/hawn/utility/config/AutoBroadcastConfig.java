package fr.dianox.hawn.utility.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class AutoBroadcastConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public AutoBroadcastConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "AutoBroadcast.yml");
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
            
            /*
             * Auto broadcast
             * Messages
             */
            // Configuration
            
            Config.set("Config.Messages.Enable", true);
            Config.set("Config.Messages.Random", false);
            Config.set("Config.Messages.Interval", 60);
            Config.set("Config.Messages.Broadcast-To-Console", false);
            Config.set("Config.Messages.Use-Permission-To-Get-Messages", false);
            
            Config.set("Config.Messages.Custom-Header-Footer.Header.Enable", false);
            Config.set("Config.Messages.Custom-Header-Footer.Header.messages", java.util.Arrays.asList(new String[] {
                    "<--center--> &8<===========================>",
                    ""
            }));
            Config.set("Config.Messages.Custom-Header-Footer.Footer.Enable", false);
            Config.set("Config.Messages.Custom-Header-Footer.Footer.messages", java.util.Arrays.asList(new String[] {
            		"",
                    "<--center--> &8<===========================>"
            }));
            
            Config.set("Config.Messages.Options.Auto-Center", false);
            
            Config.set("Config.Messages.World.All_World", false);
            Config.set("Config.Messages.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            Config.set("Config.Messages.messages.firstmessage.message", java.util.Arrays.asList(new String[] {
            		"",
                    "<--center--> &4/&c!&4\\ &6Warning &e- &6Autobroadcast &4/&c!&4\\",
                    "<--center--> The best way to support me is to put 5 stars on spigot",
                    ""
            }));
            Config.set("Config.Messages.messages.firstmessage.world_list", java.util.Arrays.asList(new String[] {
                    "world"
            }));
            Config.set("Config.Messages.messages.mymessage.message", java.util.Arrays.asList(new String[] {
                    "",
                    "<--center--> &4/&c!&4\\ &6Warning &e- &6Autobroadcast &4/&c!&4\\",
                    "<--center--> You are free to configure the messages as you want %player%",
                    ""
            }));
            
            /*
             * Auto broadcast
             * Titles
             */
            Config.set("Config.Titles.Enable", true);
            Config.set("Config.Titles.Random", false);
            Config.set("Config.Titles.Interval", 60);
            Config.set("Config.Titles.Use-Permission-To-Get-Messages", false);
            Config.set("Config.Titles.Options-Default.Title.FadeIn", 20);
            Config.set("Config.Titles.Options-Default.Title.Stay", 30);
            Config.set("Config.Titles.Options-Default.Title.FadeOut", 20);
            Config.set("Config.Titles.Options-Default.SubTitle.FadeIn", 30);
            Config.set("Config.Titles.Options-Default.SubTitle.Stay", 20);
            Config.set("Config.Titles.Options-Default.SubTitle.FadeOut", 20);
            Config.set("Config.Titles.World.All_World", false);
            Config.set("Config.Titles.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            Config.set("Config.Titles.messages.default.Title.Message", "Default message one");
            Config.set("Config.Titles.messages.default.SubTitle.Message", "With default values - Config your autobroadcast");
            
            Config.set("Config.Titles.messages.custom.Title.FadeIn", 20);
            Config.set("Config.Titles.messages.custom.Title.Stay", 150);
            Config.set("Config.Titles.messages.custom.Title.FadeOut", 20);
            Config.set("Config.Titles.messages.custom.Title.Message", "This is a full custom title");
            Config.set("Config.Titles.messages.custom.SubTitle.FadeIn", 20);
            Config.set("Config.Titles.messages.custom.SubTitle.Stay", 150);
            Config.set("Config.Titles.messages.custom.SubTitle.FadeOut", 20);
            Config.set("Config.Titles.messages.custom.SubTitle.Message", "Without default values - Config your autobroadcast");
            
            Config.set("Config.Titles.messages.No-title-omg.SubTitle.Message", "Only subtitles - Config your autobroadcast");
            
            Config.set("Config.Titles.messages.No-subtitle-omgx2.SubTitle.Message", "Only titles (autobroadcast)");
            
            /*
             * Auto broadcast
             * Action bar
             */
            Config.set("Config.Action-Bar.Enable", true);
            Config.set("Config.Action-Bar.Random", false);
            Config.set("Config.Action-Bar.Interval", 60);
            Config.set("Config.Action-Bar.Use-Permission-To-Get-Messages", false);
            Config.set("Config.Action-Bar.Options-Default.Time-Stay", 120);
            Config.set("Config.Action-Bar.World.All_World", false);
            Config.set("Config.Action-Bar.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            Config.set("Config.Action-Bar.messages.default.Message", "&eDefault Action-Bar &7(autobroadcast)");
            
            Config.set("Config.Action-Bar.messages.custom.Time-Stay", 60);
            Config.set("Config.Action-Bar.messages.custom.Message", "&6custom Action-Bar &7(autobroadcast)");
            
            /*
             * Auto broadcast
             * BossBar
             */
            Config.set("Config.BossBar.Enable", true);
            Config.set("Config.BossBar.Random", false);
            Config.set("Config.BossBar.Interval", 60);
            Config.set("Config.BossBar.Use-Permission-To-Get-Messages", false);
            Config.set("Config.BossBar.Options-Default.Color", "PURPLE");
            Config.set("Config.BossBar.Options-Default.Style", "SOLID");
            Config.set("Config.BossBar.Options-Default.Progress", 0.7D);
            Config.set("Config.BossBar.World.All_World", false);
            Config.set("Config.BossBar.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            Config.set("Config.BossBar.messages.default.Message", "&eDefault message bossbar without settings");
            
            Config.set("Config.BossBar.messages.totalcustom.Message", "&cAll settings has been changed here %player%");
            Config.set("Config.BossBar.messages.totalcustom.Color", "BLUE");
            Config.set("Config.BossBar.messages.totalcustom.Style", "SEGMENTED_20");
            Config.set("Config.BossBar.messages.totalcustom.Progress", 1.0D);
            
            Config.set("Config.BossBar.messages.slightchange.Message", "owo");
            Config.set("Config.BossBar.messages.slightchange.Color", "YELLOW");
            Config.set("Config.BossBar.messages.slightchange.Progress", 0.1D);
            
            saveConfigFile();

        }
    }
}