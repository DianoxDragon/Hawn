package fr.dianox.hawn.utility.config.cosmeticsfun;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class EmojisListCUtility {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public EmojisListCUtility() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Cosmetics-Fun/Utility/Emojis-List.yml");
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
            
            Config.set("Emojis-list.Smiley.Enable", true);
            Config.set("Emojis-list.Smiley.Use_Permission", true);
            Config.set("Emojis-list.Smiley.Emoji.Shape", "☺");
            Config.set("Emojis-list.Smiley.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":smiley:"
            }));
            Config.set("Emojis-list.Smiley.Gui.Title", String.valueOf("&cSmiley"));
            Config.set("Emojis-list.Smiley.Gui.Material", String.valueOf("SKULL_ITEM"));
            Config.set("Emojis-list.Smiley.Gui.Skull-Name", String.valueOf("%player%"));
            Config.set("Emojis-list.Smiley.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☺",
                    "&bUse :smiley: in the chat",
                    " "
                }));

            Config.set("Emojis-list.Sad.Enable", true);
            Config.set("Emojis-list.Sad.Use_Permission", true);
            Config.set("Emojis-list.Sad.Emoji.Shape", "☹");
            Config.set("Emojis-list.Sad.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":sad:"
            }));
            Config.set("Emojis-list.Sad.Gui.Title", String.valueOf("&cSad"));
            Config.set("Emojis-list.Sad.Gui.Material", String.valueOf("PAPER"));
            Config.set("Emojis-list.Sad.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☹",
                    "&bUse :sad: in the chat",
                    " "
                }));
            
            Config.set("Emojis-list.Swords.Enable", true);
            Config.set("Emojis-list.Swords.Use_Permission", true);
            Config.set("Emojis-list.Swords.Emoji.Shape", "⚔");
            Config.set("Emojis-list.Swords.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":sword:",
            		":swords:"
            }));
            Config.set("Emojis-list.Swords.Gui.Title", String.valueOf("&cSwords"));
            Config.set("Emojis-list.Swords.Gui.Material", String.valueOf("DIAMOND_SWORD"));
            Config.set("Emojis-list.Swords.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚔",
                    "&bUse :swords: in the chat",
                    " "
                }));
            
            Config.set("Emojis-list.Heart.Enable", true);
            Config.set("Emojis-list.Heart.Use_Permission", true);
            Config.set("Emojis-list.Heart.Emoji.Shape", "❤");
            Config.set("Emojis-list.Heart.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":heart:"
            }));
            Config.set("Emojis-list.Heart.Gui.Title", String.valueOf("&cHeart"));
            Config.set("Emojis-list.Heart.Gui.Material", String.valueOf("REDSTONE"));
            Config.set("Emojis-list.Heart.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ❤",
                    "&bUse :heart: in the chat",
                    " "
                }));

            Config.set("Emojis-list.Star.Enable", true);
            Config.set("Emojis-list.Star.Use_Permission", true);
            Config.set("Emojis-list.Star.Emoji.Shape", "✰");
            Config.set("Emojis-list.Star.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":star:"
            }));
            Config.set("Emojis-list.Star.Gui.Title", String.valueOf("&cStar"));
            Config.set("Emojis-list.Star.Gui.Material", String.valueOf("NETHER_STAR"));
            Config.set("Emojis-list.Star.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✰",
                    "&bUse :star: in the chat",
                    " "
            }));

            Config.set("Emojis-list.Copyright.Enable", true);
            Config.set("Emojis-list.Copyright.Use_Permission", true);
            Config.set("Emojis-list.Copyright.Emoji.Shape", "©");
            Config.set("Emojis-list.Copyright.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":copyright:"
            }));
            Config.set("Emojis-list.Copyright.Gui.Title", String.valueOf("&cCopyright"));
            Config.set("Emojis-list.Copyright.Gui.Material", String.valueOf("PAINTING"));
            Config.set("Emojis-list.Copyright.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ©",
                    "&bUse :chess: in the chat",
                    " "
                }));

            Config.set("Emojis-list.Skull.Enable", true);
            Config.set("Emojis-list.Skull.Use_Permission", true);
            Config.set("Emojis-list.Skull.Emoji.Shape", "☠");
            Config.set("Emojis-list.Skull.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":skull:"
            }));
            Config.set("Emojis-list.Skull.Gui.Title", String.valueOf("&cSkull"));
            Config.set("Emojis-list.Skull.Gui.Material", String.valueOf("SKULL_ITEM"));
            Config.set("Emojis-list.Skull.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☠",
                    "&bUse :skull: in the chat",
                    " "
                }));

            Config.set("Emojis-list.Checkmark.Enable", true);
            Config.set("Emojis-list.Checkmark.Use_Permission", true);
            Config.set("Emojis-list.Checkmark.Emoji.Shape", "✔");
            Config.set("Emojis-list.Checkmark.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":checkmark:",
            		":ok:",
            		":yes:"
            }));
            Config.set("Emojis-list.Checkmark.Gui.Title", String.valueOf("&cCheckmark"));
            Config.set("Emojis-list.Checkmark.Gui.Material", String.valueOf("EMERALD_BLOCK"));
            Config.set("Emojis-list.Checkmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✔",
                    "&bUse :checkmark: in the chat",
                    " "
                }));

            Config.set("Emojis-list.Crossmark.Enable", true);
            Config.set("Emojis-list.Crossmark.Use_Permission", true);
            Config.set("Emojis-list.Crossmark.Emoji.Shape", "✖");
            Config.set("Emojis-list.Crossmark.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":no:",
            		":crossmark:"
            }));
            Config.set("Emojis-list.Crossmark.Gui.Title", String.valueOf("&cCrossmark"));
            Config.set("Emojis-list.Crossmark.Gui.Material", String.valueOf("REDSTONE_BLOCK"));
            Config.set("Emojis-list.Crossmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✖",
                    "&bUse :crossmark: in the chat",
                    " "
                }));

            Config.set("Emojis-list.Arrow.Enable", true);
            Config.set("Emojis-list.Arrow.Use_Permission", true);
            Config.set("Emojis-list.Arrow.Emoji.Shape", "Σ>―(´･ω･`)→");
            Config.set("Emojis-list.Arrow.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":arrow:"
            }));
            Config.set("Emojis-list.Arrow.Gui.Title", String.valueOf("&cArrow"));
            Config.set("Emojis-list.Arrow.Gui.Material", String.valueOf("ARROW"));
            Config.set("Emojis-list.Arrow.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : Σ>―(´･ω･`)→",
                    "&bUse :arrow: in the chat",
                    " "
                }));

            Config.set("Emojis-list.Pushups.Enable", true);
            Config.set("Emojis-list.Pushups.Use_Permission", true);
            Config.set("Emojis-list.Pushups.Emoji.Shape", "┌(◣﹏◢)┐");
            Config.set("Emojis-list.Pushups.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":pushups:"
            }));
            Config.set("Emojis-list.Pushups.Gui.Title", String.valueOf("&cPushups"));
            Config.set("Emojis-list.Pushups.Gui.Material", String.valueOf("SPONGE"));
            Config.set("Emojis-list.Pushups.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ┌(◣﹏◢)┐",
                    "&bUse :pushups: in the chat",
                    " "
                }));

            Config.set("Emojis-list.shrug.Enable", true);
            Config.set("Emojis-list.shrug.Use_Permission", true);
            Config.set("Emojis-list.shrug.Emoji.Shape", "¯\\_(ツ)_/¯");
            Config.set("Emojis-list.shrug.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":shrug:"
            }));
            Config.set("Emojis-list.shrug.Gui.Title", String.valueOf("&cShrug"));
            Config.set("Emojis-list.shrug.Gui.Material", String.valueOf("STRING"));
            Config.set("Emojis-list.shrug.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ¯\\_(ツ)_/¯",
                    "&bUse :shrug: in the chat",
                    " "
                }));
            
            Config.set("Emojis-list.fliptable.Enable", true);
            Config.set("Emojis-list.fliptable.Use_Permission", true);
            Config.set("Emojis-list.fliptable.Emoji.Shape", "(╯°□°）╯︵ ┻━┻");
            Config.set("Emojis-list.fliptable.Emoji.Replace_With", java.util.Arrays.asList(new String[] {
            		":fliptable:"
            }));
            Config.set("Emojis-list.fliptable.Gui.Title", String.valueOf("&cFliptable"));
            Config.set("Emojis-list.fliptable.Gui.Material", String.valueOf("OAK_WOOD"));
            Config.set("Emojis-list.fliptable.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : (╯°□°）╯︵ ┻━┻",
                    "&bUse :fliptable: in the chat",
                    " "
                }));
                       
            
            saveConfigFile();

        }
    }
}