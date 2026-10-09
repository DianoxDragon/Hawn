package fr.dianox.hawn.utility.config.configs.events;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class OnChatConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public OnChatConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Events/Chat.yml");
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
         * ANTI SWEAR
         */

        Config.set("Anti-Swear.Enable", true);
        Config.set("Anti-Swear.Bypass", true);
        Config.set("Anti-Swear.Replace-Message.Enable", true);
        Config.set("Anti-Swear.Replace-Message.Message", java.util.Arrays.asList(new String[] {"*****"}));
        Config.set("Anti-Swear.Notify-Staff", true);
        Config.set("Anti-Swear.List", java.util.Arrays.asList(new String[] {
                "fuck",
                "fuck you",
                "shut up",
                "shit"
            }));

        /*
         * CHAT COLOR
         */

        Config.set("Chat-Color-Player.Enable", true);
        Config.set("Chat-Color-Player.Per-Color-Permission", false);

        /*
         * MENTIONS
         */

        Config.set("Chat-Mention.Enable", true);

        Config.set("Chat-Mention.Mentionned.Self-Mention.Enable", true);

        Config.set("Chat-Mention.Mentionned.Chat-Highlight.Enable", true);
        Config.set("Chat-Mention.Mentionned.Chat-Highlight.Highlighting", "&6&l");

        Config.set("Chat-Mention.Mentionned.Sound.Enable", true);
        Config.set("Chat-Mention.Mentionned.Sound.Sound", "BLOCK_NOTE_HARP");
        Config.set("Chat-Mention.Mentionned.Sound.Volume", 1);
        Config.set("Chat-Mention.Mentionned.Sound.Pitch", 1);

        Config.set("Chat-Mention.Mentionned.Send-Message.Enable", true);
        Config.set("Chat-Mention.Mentionned.Send-Message.Messages", java.util.Arrays.asList(new String[] {
                "%prefix% You have been mentionned by %sender%"
            }));

        Config.set("Chat-Mention.Mentionned.Send-ActionBar.Enable", true);
        Config.set("Chat-Mention.Mentionned.Send-ActionBar.Options.Message", "&bYou have been mentionned by &e&l%sender%");
        Config.set("Chat-Mention.Mentionned.Send-ActionBar.Options.Time-Stay", 150);

        Config.set("Chat-Mention.Mentionned.Send-Title.Enable", true);

        Config.set("Chat-Mention.Mentionned.Send-Title.Options.Enable", true);
        Config.set("Chat-Mention.Mentionned.Send-Title.Options.FadeIn", 20);
        Config.set("Chat-Mention.Mentionned.Send-Title.Options.Stay", 150);
        Config.set("Chat-Mention.Mentionned.Send-Title.Options.FadeOut", 20);
        Config.set("Chat-Mention.Mentionned.Send-Title.Options.Title", "&6✉ &bYou have been &ementionned&6 ✉");
        Config.set("Chat-Mention.Mentionned.Send-Title.Options.SubTitle", "&bAnswer to &e%sender%");

        /*
         * CHAT EMOJIS GENERAL CONFIGURATION
         */

        Config.set("Chat-Emoji-Player.Enable", true);

        // Option Gui
        Config.set("Chat-Emoji-Player.Emojis-list.Option.Gui.Enable", true);
        Config.set("Chat-Emoji-Player.Emojis-list.Option.Gui.Use_Permission", false);
        Config.set("Chat-Emoji-Player.Emojis-list.Option.Gui.Title", String.valueOf("&6Emojis list"));

        Config.set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Enable", true);
        Config.set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title", String.valueOf("&cClose the Gui"));
        Config.set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material", String.valueOf("BARRIER"));
        Config.set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore", java.util.Arrays.asList(new String[] {
                " ",
                "You can simply delete too",
                "If you don't want lore",
                " "
            }));

        /*
         * CHAT FORMAT
         */

        // AUTO: used when no known chat plugin is installed, and a chat plugin that changes the format after Hawn wins.
        // true: always used, over the other plugins. false: never used.
        Config.set("Chat-Format.Enable", "AUTO");
        Config.set("Chat-Format.Format", "&7%player% &8» &f%message%");

        /*
         * ANTI SPAM
         */

        Config.set("Anti-Spam.Enable", true);
        Config.set("Anti-Spam.Bypass", true);
        Config.set("Anti-Spam.Repeat.Enable", true);
        Config.set("Anti-Spam.Repeat.Seconds", 30);
        Config.set("Anti-Spam.Caps.Enable", true);
        Config.set("Anti-Spam.Caps.Min-Letters", 6);
        Config.set("Anti-Spam.Caps.Max-Percent", 70);
        Config.set("Anti-Spam.Caps.Block", false);

        /*
         * CHAT PER GROUP OF WORLDS
         */

        // The players only see the messages of their group of worlds. A group can set any option of this file
        // for itself (Chat-Format.Format, Anti-Swear.List...), plus Mute and Chat-Delay.
        Config.set("Per-World-Chat.Enable", false);
        Config.set("Per-World-Chat.Groups.main.Worlds", java.util.Arrays.asList(new String[] {"world"}));
        Config.set("Per-World-Chat.Groups.nether_end.Worlds", java.util.Arrays.asList(new String[] {"world_nether", "world_the_end"}));
        Config.set("Per-World-Chat.Groups.nether_end.Chat-Format.Format", "&c[Nether] &7%player% &8» &f%message%");

        Config = ConfigDefaults.apply(file, loaded, Config, "Per-World-Chat.Groups");
    }

}