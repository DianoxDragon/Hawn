package fr.dianox.hawn.utility.config.configs.messages;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.dianox.hawn.Main;

public class WorldManagerPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public WorldManagerPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/WorldManager.yml");
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
             * Gui
             */
            // import
            Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList(new String[] {
            		"[send-title]: &cImport started //n &7Wait please..."
            		}));
            
            Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world %arg1% has been loaded"
            		}));
            
            // tp
            Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList(new String[] {
            		"%prefix% &cThe teleportation failed",
            		"%prefix% &cYou are already in that world"
            		}));
            
            Config.set("Gui.Tp.Success", java.util.Arrays.asList(new String[] {
            		"%prefix% &7You have been teleported in the world &e%arg1%"
            		}));
            
            // create
            Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList(new String[] {
            		"[send-title]: &eChoose a world name //n &7Type in the chat"
            		}));
            
            Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList(new String[] {
            		"[send-title]: &cWorld in creation&7..."
            		}));
            
            Config.set("Gui.Create.World-Created", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world &e%arg1% &7has been created"
            		}));
            
            Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList(new String[] {
            		"%prefix% &7Not creating a world anymore..."
            		}));
            
            // Delete
            Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList(new String[] {
            		"%prefix% &cThe world &e%arg1% &chas been deleted"
            		}));
            
            Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList(new String[] {
            		"%prefix% &cSomething strange has happened... nothing's happening, it's an error, just ignore it..."
            		}));
            
            // Modify World
            Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world time for the world &e%arg1%&7 has been changed"
            		}));
            
            Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world weather for the world &e%arg1%&7 has been changed to sun"
            		}));
            
            Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world weather for the world &e%arg1%&7 has been changed to rain"
            		}));
            
            Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world weather for the world &e%arg1%&7 has been changed to a storm"
            		}));
            
            // M. W. Dif.
            Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to peaceful"
            		}));
            
            Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to easy"
            		}));
            
            Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to normal"
            		}));
            
            Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to hard"
            		}));
            
            // Other
            Config.set("Gui.Other.WorldType.World-Type", "&7World type:");
            Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
            Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
            Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");
            
            Config.set("Gui.Other.WorldFace.World-Face", "&7World face:");
            Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
            Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
            Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
            Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");
            
            Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Sun");
            Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Rain");
            Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Storm");
            Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Actual weather:");
            
            Config.set("Gui.Other.Difficulty.Peaceful", "PEACEFUL");
            Config.set("Gui.Other.Difficulty.Easy", "EASY");
            Config.set("Gui.Other.Difficulty.Normal", "NORMAL");
            Config.set("Gui.Other.Difficulty.Hard", "HARD");
            
            Config.set("Gui.Other.Name", "Name:");
            Config.set("Gui.Other.Players", "Players:");
            Config.set("Gui.Other.Size", "Size:");
            Config.set("Gui.Other.Difficulty-Main", "Difficulty:");
            Config.set("Gui.Other.Difficulty-Two", "Difficulty");
            Config.set("Gui.Other.Environment", "Environment:");
            Config.set("Gui.Other.World-Time", "World Time");
            Config.set("Gui.Other.World-Weather", "World Weather");
            Config.set("Gui.Other.Done", "&f&lDone");
            Config.set("Gui.Other.Main.Line-One", "&eLeft-Click&7 to join the world");
            Config.set("Gui.Other.Main.Line-One-Second", "&eLeft-Click&7 to load the world");
            Config.set("Gui.Other.Main.Line-Two", "&eRight-Click&7 to change settings of the world");
            Config.set("Gui.Other.Main.Line-Three", "&eShift &7and &eRight-Click&7 to delete the world");
            Config.set("Gui.Other.Main.Line-Four", "&7The world is currently not loaded");
            Config.set("Gui.Other.Page.Back", "&bPrevious");
            Config.set("Gui.Other.Page.Next", "&bNext");
            Config.set("Gui.Other.Create-a-new-world", "&f&lCreate a new world");
            Config.set("Gui.Other.Back.PanelAdmin", "&c&lBack to the panel admin");
            Config.set("Gui.Other.Back.Menu", "&c&lBack to the menu");
            Config.set("Gui.Other.Back.Main-Menu", "&c&lBack to the main menu");
            Config.set("Gui.Other.Delete.Confirm", "&aI know what I'm doing");
            Config.set("Gui.Other.Delete.No", "&cFinally... no");
            
            /*
             * Command
             */
            Config.set("Command.Unload.Error", java.util.Arrays.asList(new String[] {
            		"%prefix% &cError, can't unload this world"
            		}));
            Config.set("Command.Other.World", "World:");
            Config.set("Command.Other.Type", "Type:");
            
            Config.set("Error.NotGoodName", java.util.Arrays.asList(new String[] {
            		"%prefix% &cError Hawn, world name with such a name format is not allowed",
                    "%prefix% &cThe only name formats allowed are : a-z | A-Z | 0-9 | _"
            		}));
            
            Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList(new String[] {
            		"%prefix% &cYou didn't typed a world name"
            		}));
            
            Config.set("Error.WorldCreation", java.util.Arrays.asList(new String[] {
            		"%prefix% &cYou missed something in the writing of the command..."
            		}));
            
            Config.set("Error.World-Already-Exist", java.util.Arrays.asList(new String[] {
            		"%prefix% &cThis world exist"
            		}));
            
            Config.set("Error.World-Not-Exist", java.util.Arrays.asList(new String[] {
            		"%prefix% &cThis world doesn't exist"
            		}));
            
            saveConfigFile();

        }
    }
}