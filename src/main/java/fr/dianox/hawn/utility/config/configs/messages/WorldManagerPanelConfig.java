package fr.dianox.hawn.utility.config.configs.messages;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import fr.dianox.hawn.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        /*
         * Gui
         */
        // import
        Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList("[send-title]: &cImport started //n &7Wait please..."));
        Config.set("Gui.Import.Importing-With-A-Command", java.util.Arrays.asList("%prefix% &7Please use a command instead : /hw import worldname"));

        Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(
        		"%prefix% &7The world %arg1% has been loaded",
		            "[send-title]: &3Import done //n &7You can join this world"));

        Config.set("Gui.Unload", java.util.Arrays.asList("%prefix% &7The world %arg1% is now unloaded"));

        // tp
        Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList("%prefix% &cThe teleportation failed",
		            "%prefix% &cYou are already in that world"));

        Config.set("Gui.Tp.Success", java.util.Arrays.asList("%prefix% &7You have been teleported in the world &e%arg1%"));

        // create
        Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList("[send-title]: &eChoose a world name //n &7Type in the chat"));
        Config.set("Gui.Create.Choose-A-Generator", java.util.Arrays.asList("[send-title]: &eChoose a generator //n &7Type in the chat"));

        Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList("[send-title]: &cWorld in creation&7..."));

        Config.set("Gui.Create.World-Created", java.util.Arrays.asList("%prefix% &7The world &e%arg1% &7has been created",
                "[send-title]: &3World created //n &7You can join this world"));

        Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList("%prefix% &7Not creating a world anymore..."));

        // Delete
        Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList("%prefix% &cThe world &e%arg1% &chas been deleted"));
        Config.set("Gui.Delete.Confirm-Command", java.util.Arrays.asList("%prefix% &cThis deletes the world &e%arg1% &cand its folder, with no undo. Type &e/hw delete %arg1% confirm &cto do it"));
        Config.set("Error.Protected-World", java.util.Arrays.asList("%prefix% &cThe world &e%arg1% &ccan't be deleted: the server needs it"));
        Config.set("Error.Unload-Failed", java.util.Arrays.asList("%prefix% &cThe world &e%arg1% &ccould not be unloaded: nothing was deleted"));
        Config.set("Gui.Default-World.Changed", java.util.Arrays.asList("%prefix% &e%arg1% &7is now the default world: it replaces &e%arg2% &7in &e%arg3% &7option(s) of &e%arg4% &7file(s)"));
        Config.set("Gui.Default-World.Already", java.util.Arrays.asList("%prefix% &e%arg1% &7is already the default world"));
        Config.set("Gui.Default-World.Shift-Click", java.util.Arrays.asList("%prefix% &eShift + click &7to make &e%arg1% &7the default world"));
        Config.set("Gui.Default-World.Conflict", java.util.Arrays.asList("%prefix% &6%arg1%: &e%arg2% &6already had its own options, the ones of &e%arg3% &6are kept beside them"));
        Config.set("Gui.Default-World.Spawn-Elsewhere", java.util.Arrays.asList("%prefix% &6The default spawn &e%arg1% &6is still in &e%arg2%&6: create one in the new world with &e/setspawn"));
        Config.set("Gui.Default-World.Yaml-Error", java.util.Arrays.asList("%prefix% &c%arg1% has a YAML error: it was not changed"));
        Config.set("Command.Default-World.Current", java.util.Arrays.asList("%prefix% &7The default world is &e%arg1% &7(&e/hw default <world> &7to change it)"));
        Config.set("Error.Default-World", java.util.Arrays.asList("%prefix% &cThe world &e%arg1% &cis the default world of Hawn: choose another default world first"));
        Config.set("Gui.Other.Default-World.Title", "&6Default world");
        Config.set("Gui.Other.Default-World.Current", java.util.Arrays.asList("&7This is the default world of Hawn:", "&7the one written in the world lists", "&7of the configuration files"));
        Config.set("Gui.Other.Default-World.Change", java.util.Arrays.asList("&7Now: &e%arg1%", "&eShift + click&7 to make this world", "&7the default one: it replaces &e%arg1%", "&7in the world lists of the files"));
        Config.set("Gui.Other.Main.Default-World", "&6Default world");

        Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList("%prefix% &cSomething strange has happened... nothing's happening, it's an error, just ignore it..."));

        // Modify World
        Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList("%prefix% &7The world time for the world &e%arg1%&7 has been changed"));

        Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList("%prefix% &7The world weather for the world &e%arg1%&7 has been changed to sun"));

        Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList("%prefix% &7The world weather for the world &e%arg1%&7 has been changed to rain"));

        Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList("%prefix% &7The world weather for the world &e%arg1%&7 has been changed to a storm"));

        // M. W. Dif.
        Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList("%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to peaceful"));

        Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList("%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to easy"));

        Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList("%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to normal"));

        Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList("%prefix% &7The world difficulty for the world &e%arg1%&7 has been changed to hard"));

        // Other
        Config.set("Gui.Other.Generator", "&7Generator");
        Config.set("Gui.Other.Generator-Page.Void-Generator", "&7Void-Generator");
        Config.set("Gui.Other.Generator-Page.Custom-Generator", "&7Type your own generator");
        // Since 1.4
        Config.set("Gui.Create.Failed", java.util.Arrays.asList("%prefix% &cThe world &e%arg1% &ccould not be created, see the console"));
        Config.set("Gui.Modify-World.Nothing-Selected", java.util.Arrays.asList("%prefix% &7Click a choice first, then the sign"));
        Config.set("Gui.Other.Generator-Default", "&7Default");
        Config.set("Gui.Other.Generator-Help", java.util.Arrays.asList("&8Left click: default or void", "&8Right click: type a generator in the chat"));
        Config.set("Gui.Other.Info.Title", "&eInformation");
        Config.set("Gui.Other.Info.Players", "&7Players:");
        Config.set("Gui.Other.Info.Environment", "&7Environment:");
        Config.set("Gui.Other.Info.Generator", "&7Generator:");
        Config.set("Gui.Other.Info.Chunks", "&7Loaded chunks:");
        Config.set("Gui.Other.Info.Entities", "&7Entities:");
        Config.set("Gui.Other.Info.Spawn", "&7Spawn:");
        Config.set("Gui.Other.Info.Time", "&7Time:");
        Config.set("Gui.Other.Info.PvP", "&7PvP:");
        Config.set("Gui.Other.Info.Seed", "&7Seed:");
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
        Config.set("Command.Unload.Error", java.util.Arrays.asList("%prefix% &cError, can't unload this world"));
        Config.set("Command.Other.World", "World:");
        Config.set("Command.Other.Type", "Type:");

        Config.set("Error.NotGoodName", java.util.Arrays.asList("%prefix% &cError Hawn, world name with such a name format is not allowed",
		            "%prefix% &cThe only name formats allowed are : a-z | A-Z | 0-9 | _"));

        Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList("%prefix% &cYou didn't typed a world name"));

        Config.set("Error.WorldCreation", java.util.Arrays.asList("%prefix% &cYou missed something in the writing of the command..."));

        Config.set("Error.World-Already-Exist", java.util.Arrays.asList("%prefix% &cThis world exist"));

        Config.set("Error.World-Not-Exist", java.util.Arrays.asList("%prefix% &cThis world doesn't exist"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}