package fr.dianox.hawn.utility.config.configs;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        /*
         * Auto broadcast
         * Messages
         */
        // Configuration

        Config.set("Config.Messages.Enable", true);
        Config.set("Config.Messages.Random", false);
        Config.set("Config.Messages.Interval", 600);
        Config.set("Config.Messages.Broadcast-To-Console", false);
        Config.set("Config.Messages.Use-Permission-To-Get-Messages", false);

        Config.set("Config.Messages.Custom-Header-Footer.Header.Enable", true);
        Config.set("Config.Messages.Custom-Header-Footer.Header.messages", java.util.Arrays.asList(
                "",
                "°·..·°¯°·._.·  &e&l{lang_text.announcement}&r ·._.·°¯°·..·°",
                ""));
        Config.set("Config.Messages.Custom-Header-Footer.Footer.Enable", true);
        Config.set("Config.Messages.Custom-Header-Footer.Footer.messages", Collections.singletonList(""));

        Config.set("Config.Messages.Options.Auto-Center", true);

        Config.set("Config.Messages.World.All_World", false);
        Config.set("Config.Messages.World.Worlds", java.util.Arrays.asList(
                "world",
                "world_nether"));

        // Messages

        // Since 1.4.8: a demo of a lobby (the files already there keep their messages)
        Config.set("Config.Messages.messages.welcome.message", Collections.singletonList("{lang_text.welcome-server}"));
        Config.set("Config.Messages.messages.discord.message", Collections.singletonList("{lang_text.discord}"));
        Config.set("Config.Messages.messages.vote.message", Collections.singletonList("{lang_text.vote}"));
        // Since 1.5.2: a sound with the message (new installations)
        Config.set("Config.Messages.messages.vote.Sound", "BLOCK_NOTE_BLOCK_PLING");

        // Only in the nether (world_list)
        Config.set("Config.Messages.messages.nether.message", Collections.singletonList("{lang_text.nether}"));
        Config.set("Config.Messages.messages.nether.world_list", Collections.singletonList("world_nether"));
        /*
         * Auto broadcast
         * Titles
         */
        Config.set("Config.Titles.Enable", false);
        Config.set("Config.Titles.Random", false);
        Config.set("Config.Titles.Interval", 1200);
        Config.set("Config.Titles.Use-Permission-To-Get-Messages", false);
        Config.set("Config.Titles.Options-Default.FadeIn", 20);
        Config.set("Config.Titles.Options-Default.Stay", 30);
        Config.set("Config.Titles.Options-Default.FadeOut", 20);
        Config.set("Config.Titles.World.All_World", false);
        Config.set("Config.Titles.World.Worlds", java.util.Arrays.asList("world",
                "world_nether"));

        // Messages

        Config.set("Config.Titles.messages.default.Title.Message", "&6&lYour Server");
        Config.set("Config.Titles.messages.default.SubTitle.Message", "{lang_text.have-fun}");

        // Its own times
        Config.set("Config.Titles.messages.event.FadeIn", 20);
        Config.set("Config.Titles.messages.event.Stay", 150);
        Config.set("Config.Titles.messages.event.FadeOut", 20);
        Config.set("Config.Titles.messages.event.Title.Message", "{lang_text.event}");
        Config.set("Config.Titles.messages.event.SubTitle.Message", "{lang_text.event-soon}");

        // A subtitle only
        Config.set("Config.Titles.messages.spawn.SubTitle.Message", "{lang_text.lost}");

        /*
         * Auto broadcast
         * Action bar
         */
        Config.set("Config.Action-Bar.Enable", false);
        Config.set("Config.Action-Bar.Random", false);
        Config.set("Config.Action-Bar.Interval", 600);
        Config.set("Config.Action-Bar.Use-Permission-To-Get-Messages", false);
        Config.set("Config.Action-Bar.Options-Default.Time-Stay", 120);
        Config.set("Config.Action-Bar.World.All_World", false);
        Config.set("Config.Action-Bar.World.Worlds", java.util.Arrays.asList("world",
                "world_nether"));

        // Messages

        Config.set("Config.Action-Bar.messages.online.Message", "{lang_text.players-online}");

        // Its own time
        Config.set("Config.Action-Bar.messages.help.Time-Stay", 60);
        Config.set("Config.Action-Bar.messages.help.Message", "{lang_text.need-help}");

        /*
         * Auto broadcast
         * BossBar
         */
        Config.set("Config.BossBar.Enable", false);
        Config.set("Config.BossBar.Random", false);
        Config.set("Config.BossBar.Interval", 600);
        Config.set("Config.BossBar.Use-Permission-To-Get-Messages", false);
        Config.set("Config.BossBar.Options-Default.Color", "PURPLE");
        Config.set("Config.BossBar.Options-Default.Style", "SOLID");
        Config.set("Config.BossBar.Options-Default.Progress", 0.7D);
        Config.set("Config.BossBar.World.All_World", false);
        Config.set("Config.BossBar.World.Worlds", java.util.Arrays.asList("world",
                "world_nether"));

        // Messages

        Config.set("Config.BossBar.messages.welcome.Message", "{lang_text.bossbar-welcome}");

        // Every option of its own
        Config.set("Config.BossBar.messages.event.Message", "{lang_text.bossbar-event}");
        Config.set("Config.BossBar.messages.event.Color", "BLUE");
        Config.set("Config.BossBar.messages.event.Style", "SEGMENTED_20");
        Config.set("Config.BossBar.messages.event.Progress", 1.0D);

        // Only some options
        Config.set("Config.BossBar.messages.vote.Message", "{lang_text.bossbar-vote}");
        Config.set("Config.BossBar.messages.vote.Color", "YELLOW");
        Config.set("Config.BossBar.messages.vote.Progress", 0.1D);

        Config = ConfigDefaults.apply(file, loaded, Config, "Config.Messages.messages", "Config.Titles.messages", "Config.Action-Bar.messages", "Config.BossBar.messages");
    }
}