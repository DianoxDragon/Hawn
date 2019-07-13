package fr.Dianox.Hawn.Utility.Config.Events;

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
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {}
            
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
            
            Config.set("Chat-Color-Player.Enable", true);
            
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
            
            // Option per emoji
            Config.set("Chat-Emoji-Player.Emojis-list.Smiley.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Smiley.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Title", String.valueOf("&cSmiley"));
            Config.set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Material", String.valueOf("SKULL_ITEM"));
            Config.set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Skull-Name", String.valueOf("%player%"));
            Config.set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☺",
                    "&bUse :smiley: in the chat",
                    " "
                }));


                Config.set("Chat-Emoji-Player.Emojis-list.Swords.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Swords.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Title", String.valueOf("&cSwords"));
            Config.set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Material", String.valueOf("DIAMOND_SWORD"));
            Config.set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚔",
                    "&bUse :swords: in the chat",
                    " "
                }));
            
            Config.set("Chat-Emoji-Player.Emojis-list.coffee.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.coffee.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Title", String.valueOf("&cCoffee"));
            Config.set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Material", String.valueOf("MILK_BUCKET"));
            Config.set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☕",
                    "&bUse :swords: in the chat",
                    " "
                }));
            
            Config.set("Chat-Emoji-Player.Emojis-list.airplane.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.airplane.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Title", String.valueOf("&cAirplane"));
            Config.set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Material", String.valueOf("FEATHER"));
            Config.set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✈",
                    "&bUse :swords: in the chat",
                    " "
                }));
            
                Config.set("Chat-Emoji-Player.Emojis-list.Heart.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Heart.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Title", String.valueOf("&cHeart"));
            Config.set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Material", String.valueOf("REDSTONE"));
            Config.set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ❤",
                    "&bUse :heart: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Notes.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Notes.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Title", String.valueOf("&cNotes"));
            Config.set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Material", String.valueOf("NOTE_BLOCK"));
            Config.set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♪",
                    "&bUse :notes: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Star.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Star.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Star.Gui.Title", String.valueOf("&cStar"));
            Config.set("Chat-Emoji-Player.Emojis-list.Star.Gui.Material", String.valueOf("NETHER_STAR"));
            Config.set("Chat-Emoji-Player.Emojis-list.Star.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✰",
                    "&bUse :star: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Peace.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Peace.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Title", String.valueOf("&cPeace"));
            Config.set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Material", String.valueOf("ORANGE_TULIP"));
            Config.set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☮",
                    "&bUse :peace: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Chess.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Chess.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Title", String.valueOf("&cChess"));
            Config.set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Material", String.valueOf("TORCH"));
            Config.set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♖",
                    "&bUse :chess: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Copyright.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Copyright.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Title", String.valueOf("&cCopyright"));
            Config.set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Material", String.valueOf("PAINTING"));
            Config.set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ©",
                    "&bUse :chess: in the chat",
                    " "
                }));

            Config.set("Chat-Emoji-Player.Emojis-list.Anchor.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Anchor.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Title", String.valueOf("&cAnchor"));
            Config.set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Material", String.valueOf("IRON_ORE"));
            Config.set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚓",
                    "&bUse :anchor: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Skull.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Skull.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Title", String.valueOf("&cSkull"));
            Config.set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Material", String.valueOf("SKULL_ITEM"));
            Config.set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☠",
                    "&bUse :skull: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Umbrella.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Umbrella.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Title", String.valueOf("&cUmbrella"));
            Config.set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Material", String.valueOf("GREEN_CARPET"));
            Config.set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☂",
                    "&bUse :umbrella: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Diamonds.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Diamonds.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Title", String.valueOf("&cDiamonds"));
            Config.set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Material", String.valueOf("DIAMOND"));
            Config.set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♦",
                    "&bUse :diamonds: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Snowflake.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Snowflake.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Title", String.valueOf("&cSnowflake"));
            Config.set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Material", String.valueOf("SNOWBALL"));
            Config.set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ❄",
                    "&bUse :snowflake: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Snowman.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Snowman.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Title", String.valueOf("&cSnowman"));
            Config.set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Material", String.valueOf("SNOW_BLOCK"));
            Config.set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☃",
                    "&bUse :snowman: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Checkmark.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Checkmark.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Title", String.valueOf("&cCheckmark"));
            Config.set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Material", String.valueOf("EMERALD_BLOCK"));
            Config.set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✔",
                    "&bUse :checkmark: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Crossmark.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Crossmark.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Title", String.valueOf("&cCrossmark"));
            Config.set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Material", String.valueOf("REDSTONE_BLOCK"));
            Config.set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✖",
                    "&bUse :crossmark: in the chat",
                    " "
                }));

                Config.set("Chat-Emoji-Player.Emojis-list.Arrow.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Arrow.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Title", String.valueOf("&cArrow"));
            Config.set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Material", String.valueOf("ARROW"));
            Config.set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : Σ>―(´･ω･`)→",
                    "&bUse :arrow: in the chat",
                    " "
                }));

            Config.set("Chat-Emoji-Player.Emojis-list.Strong.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Strong.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Title", String.valueOf("&cStrong"));
            Config.set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Material", String.valueOf("SPLASH_POTION"));
            Config.set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : (9｀･ω･)9",
                    "&bUse :strong: in the chat",
                    " "
                }));

            Config.set("Chat-Emoji-Player.Emojis-list.Pushups.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Pushups.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Title", String.valueOf("&cPushups"));
            Config.set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Material", String.valueOf("SPONGE"));
            Config.set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ┌(◣﹏◢)┐",
                    "&bUse :pushups: in the chat",
                    " "
                }));

            Config.set("Chat-Emoji-Player.Emojis-list.shrug.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.shrug.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Title", String.valueOf("&cShrug"));
            Config.set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Material", String.valueOf("STRING"));
            Config.set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ¯\\_(ツ)_/¯",
                    "&bUse :shrug: in the chat",
                    " "
                }));
            
            Config.set("Chat-Emoji-Player.Emojis-list.fliptable.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.fliptable.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Title", String.valueOf("&cFliptable"));
            Config.set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Material", String.valueOf("OAK_WOOD"));
            Config.set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : (╯°□°）╯︵ ┻━┻",
                    "&bUse :fliptable: in the chat",
                    " "
                }));
            
            //
            
            Config.set("Chat-Emoji-Player.Emojis-list.Sad.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Sad.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Title", String.valueOf("&cSad"));
            Config.set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Material", String.valueOf("PAPER"));
            Config.set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☹",
                    "&bUse :fliptable: in the chat",
                    " "
                }));
            
            Config.set("Chat-Emoji-Player.Emojis-list.Scales.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Scales.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Title", String.valueOf("&cScales"));
            Config.set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Material", String.valueOf("OAK_PRESSURE_PLATE"));
            Config.set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚖",
                    "&bUse :fliptable: in the chat",
                    " "
                }));
            
            Config.set("Chat-Emoji-Player.Emojis-list.Radioactive.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Radioactive.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Title", String.valueOf("&cRadioactive"));
            Config.set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Material", String.valueOf("REDSTONE_TORCH"));
            Config.set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☢",
                    "&bUse :fliptable: in the chat",
                    " "
                }));
            
            Config.set("Chat-Emoji-Player.Emojis-list.King.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.King.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.King.Gui.Title", String.valueOf("&cKing"));
            Config.set("Chat-Emoji-Player.Emojis-list.King.Gui.Material", String.valueOf("GOLD_INGOT"));
            Config.set("Chat-Emoji-Player.Emojis-list.King.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♕",
                    "&bUse :fliptable: in the chat",
                    " "
                }));
            
            Config.set("Chat-Emoji-Player.Emojis-list.Email.Enable", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Email.Use_Permission", true);
            Config.set("Chat-Emoji-Player.Emojis-list.Email.Gui.Title", String.valueOf("&cEmail"));
            Config.set("Chat-Emoji-Player.Emojis-list.Email.Gui.Material", String.valueOf("PAPER"));
            Config.set("Chat-Emoji-Player.Emojis-list.Email.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✉",
                    "&bUse :fliptable: in the chat",
                    " "
                }));
            
            saveConfigFile();

        }
    }

}
