package fr.dianox.hawn.utility.config.configs.messages.de_de;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The German messages (Messages/de_DE), since Hawn 1.4.6: the texts of WorldManagerPanelConfig translated.
 */
public class DEWorldManagerPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public DEWorldManagerPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/de_DE/WorldManager.yml");
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
        Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList("[send-title]: &cImport gestartet //n &7Bitte warten..."));
        Config.set("Gui.Import.Importing-With-A-Command", java.util.Arrays.asList("%prefix% &7Bitte benutze stattdessen einen Befehl: /hw import weltname"));

        Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(
        		"%prefix% &7Die Welt %arg1% wurde geladen",
		            "[send-title]: &3Import fertig //n &7Du kannst diese Welt betreten"));

        Config.set("Gui.Unload", java.util.Arrays.asList("%prefix% &7Die Welt %arg1% ist jetzt entladen"));
        // Since 1.4.7
        Config.set("Gui.Unload-Moved", java.util.Arrays.asList("%prefix% &7Die Welt &e%arg1% &7wurde entladen: du bist jetzt in &e%arg2%"));
        Config.set("Gui.Import.Failed", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &ckonnte nicht geladen werden, siehe Konsole"));

        // tp
        Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList("%prefix% &cDie Teleportation ist fehlgeschlagen",
		            "%prefix% &cDu bist bereits in dieser Welt"));

        Config.set("Gui.Tp.Success", java.util.Arrays.asList("%prefix% &7Du wurdest in die Welt &e%arg1%&7 teleportiert"));

        // create
        Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList("[send-title]: &eWähle einen Weltnamen //n &7Schreibe ihn in den Chat"));
        Config.set("Gui.Create.Choose-A-Generator", java.util.Arrays.asList("[send-title]: &eWähle einen Generator //n &7Schreibe ihn in den Chat"));

        Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList("[send-title]: &cWelt wird erstellt&7..."));

        Config.set("Gui.Create.World-Created", java.util.Arrays.asList("%prefix% &7Die Welt &e%arg1% &7wurde erstellt",
                "[send-title]: &3Welt erstellt //n &7Du kannst diese Welt betreten"));

        Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList("%prefix% &7Die Erstellung der Welt wurde abgebrochen..."));

        // Delete
        Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &cwurde gelöscht"));
        Config.set("Gui.Delete.Confirm-Command", java.util.Arrays.asList("%prefix% &cDas löscht die Welt &e%arg1% &cund ihren Ordner, ohne Rückgängig. Schreibe &e/hw delete %arg1% confirm&c, um es zu tun"));
        Config.set("Error.Protected-World", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &ckann nicht gelöscht werden: der Server braucht sie"));
        Config.set("Error.Unload-Failed", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &ckonnte nicht entladen werden: nichts wurde gelöscht"));
        Config.set("Gui.Default-World.Changed", java.util.Arrays.asList("%prefix% &e%arg1% &7ist jetzt die Standardwelt: sie ersetzt &e%arg2% &7in &e%arg3% &7Option(en) von &e%arg4% &7Datei(en)"));
        Config.set("Gui.Default-World.Already", java.util.Arrays.asList("%prefix% &e%arg1% &7ist bereits die Standardwelt"));
        Config.set("Gui.Default-World.Shift-Click", java.util.Arrays.asList("%prefix% &eShift + Klick&7, um &e%arg1% &7zur Standardwelt zu machen"));
        Config.set("Gui.Default-World.Conflict", java.util.Arrays.asList("%prefix% &6%arg1%: &e%arg2% &6hatte bereits eigene Optionen, die von &e%arg3% &6bleiben daneben erhalten"));
        Config.set("Gui.Default-World.Spawn-Elsewhere", java.util.Arrays.asList("%prefix% &6Der Standard-Spawn &e%arg1% &6ist noch in &e%arg2%&6: erstelle einen in der neuen Welt mit &e/setspawn"));
        Config.set("Gui.Default-World.Yaml-Error", java.util.Arrays.asList("%prefix% &c%arg1% hat einen YAML-Fehler: die Datei wurde nicht geändert"));
        Config.set("Command.Default-World.Current", java.util.Arrays.asList("%prefix% &7Die Standardwelt ist &e%arg1% &7(&e/hw default <welt> &7zum Ändern)"));
        Config.set("Error.Default-World", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &cist die Standardwelt von Hawn: wähle zuerst eine andere Standardwelt"));
        Config.set("Gui.Other.Default-World.Title", "&6Standardwelt");
        Config.set("Gui.Other.Default-World.Current", java.util.Arrays.asList("&7Das ist die Standardwelt von Hawn:", "&7die Welt, die in den Weltlisten", "&7der Konfigurationsdateien steht"));
        Config.set("Gui.Other.Default-World.Change", java.util.Arrays.asList("&7Aktuell: &e%arg1%", "&eShift + Klick&7, um diese Welt", "&7zur Standardwelt zu machen: sie ersetzt &e%arg1%", "&7in den Weltlisten der Dateien"));
        Config.set("Gui.Other.Main.Default-World", "&6Standardwelt");

        Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList("%prefix% &cEtwas Seltsames ist passiert... es geschieht nichts, ein Fehler, ignoriere ihn einfach..."));

        // Modify World
        Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList("%prefix% &7Die Zeit der Welt &e%arg1%&7 wurde geändert"));

        Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList("%prefix% &7Das Wetter der Welt &e%arg1%&7 ist jetzt sonnig"));

        Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList("%prefix% &7Das Wetter der Welt &e%arg1%&7 ist jetzt regnerisch"));

        Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList("%prefix% &7Das Wetter der Welt &e%arg1%&7 ist jetzt ein Gewitter"));

        // M. W. Dif.
        Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList("%prefix% &7Der Schwierigkeitsgrad der Welt &e%arg1%&7 ist jetzt friedlich"));

        Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList("%prefix% &7Der Schwierigkeitsgrad der Welt &e%arg1%&7 ist jetzt einfach"));

        Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList("%prefix% &7Der Schwierigkeitsgrad der Welt &e%arg1%&7 ist jetzt normal"));

        Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList("%prefix% &7Der Schwierigkeitsgrad der Welt &e%arg1%&7 ist jetzt schwer"));

        // Other
        Config.set("Gui.Other.Generator", "&7Generator");
        Config.set("Gui.Other.Generator-Page.Void-Generator", "&7Leere Welt");
        Config.set("Gui.Other.Generator-Page.Custom-Generator", "&7Eigenen Generator eingeben");
        // Since 1.4
        Config.set("Gui.Create.Failed", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &ckonnte nicht erstellt werden, siehe Konsole"));
        Config.set("Gui.Modify-World.Nothing-Selected", java.util.Arrays.asList("%prefix% &7Klicke zuerst auf eine Auswahl, dann auf das Schild"));
        Config.set("Gui.Other.Generator-Default", "&7Standard");
        Config.set("Gui.Other.Generator-Help", java.util.Arrays.asList("&8Linksklick: Standard oder leer", "&8Rechtsklick: Generator in den Chat schreiben"));
        Config.set("Gui.Other.Info.Title", "&eInformationen");
        Config.set("Gui.Other.Info.Players", "&7Spieler:");
        Config.set("Gui.Other.Info.Environment", "&7Umgebung:");
        Config.set("Gui.Other.Info.Generator", "&7Generator:");
        Config.set("Gui.Other.Info.Chunks", "&7Geladene Chunks:");
        Config.set("Gui.Other.Info.Entities", "&7Entitäten:");
        Config.set("Gui.Other.Info.Spawn", "&7Spawn:");
        Config.set("Gui.Other.Info.Time", "&7Zeit:");
        Config.set("Gui.Other.Info.PvP", "&7PvP:");
        Config.set("Gui.Other.Info.Seed", "&7Seed:");
        // Since 1.4.4
        Config.set("Gui.Other.Size-Pending", "&7...");
        Config.set("Gui.Create.Generator-Cancelled", java.util.Arrays.asList("%prefix% &7Kein Generator eingegeben: der Standard bleibt"));
        Config.set("Error.Unload-Protected", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &ckann nicht entladen werden: der Server braucht sie"));
        Config.set("Command.Unload.Failed", java.util.Arrays.asList("%prefix% &cDie Welt &e%arg1% &ckonnte nicht entladen werden: es darf kein Spieler darin sein"));
        Config.set("Gui.Other.WorldType.World-Type", "&7Welttyp:");
        Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
        Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
        Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");

        Config.set("Gui.Other.WorldFace.World-Face", "&7Weltform:");
        Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
        Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
        Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
        Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");

        Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Sonne");
        Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Regen");
        Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Gewitter");
        Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Aktuelles Wetter:");

        Config.set("Gui.Other.Difficulty.Peaceful", "PEACEFUL");
        Config.set("Gui.Other.Difficulty.Easy", "EASY");
        Config.set("Gui.Other.Difficulty.Normal", "NORMAL");
        Config.set("Gui.Other.Difficulty.Hard", "HARD");

        Config.set("Gui.Other.Name", "Name:");
        Config.set("Gui.Other.Players", "Spieler:");
        Config.set("Gui.Other.Size", "Größe:");
        Config.set("Gui.Other.Difficulty-Main", "Schwierigkeit:");
        Config.set("Gui.Other.Difficulty-Two", "Schwierigkeit");
        Config.set("Gui.Other.Environment", "Umgebung:");
        Config.set("Gui.Other.World-Time", "Weltzeit");
        Config.set("Gui.Other.World-Weather", "Weltwetter");
        Config.set("Gui.Other.Done", "&f&lFertig");
        Config.set("Gui.Other.Main.Line-One", "&eLinksklick&7, um die Welt zu betreten");
        Config.set("Gui.Other.Main.Line-Two", "&eRechtsklick&7, um die Einstellungen der Welt zu ändern");
        Config.set("Gui.Other.Main.Line-Three", "&eShift &7+ &eRechtsklick&7, um die Welt zu löschen");
        Config.set("Gui.Other.Main.Line-Four", "&7Die Welt ist gerade nicht geladen");
        Config.set("Gui.Other.Main.Line-One-Second", "&eLinksklick&7, um die Welt zu laden");
        Config.set("Gui.Other.Unload.Name", "&cWelt entladen");
        Config.set("Gui.Other.Unload.Lore", "&7Bis zum nächsten Neustart: die Spieler darin kommen in die Standardwelt");
        Config.set("Gui.Other.Page.Back", "&bZurück");
        Config.set("Gui.Other.Page.Next", "&bWeiter");
        Config.set("Gui.Other.Create-a-new-world", "&f&lNeue Welt erstellen");
        Config.set("Gui.Other.Back.PanelAdmin", "&c&lZurück zum Admin-Panel");
        Config.set("Gui.Other.Back.Menu", "&c&lZurück zum Menü");
        Config.set("Gui.Other.Back.Main-Menu", "&c&lZurück zum Hauptmenü");
        Config.set("Gui.Other.Delete.Confirm", "&aIch weiß, was ich tue");
        Config.set("Gui.Other.Delete.No", "&cDoch lieber nicht");

        /*
         * Command
         */
        Config.set("Command.Unload.Error", java.util.Arrays.asList("%prefix% &cFehler, diese Welt kann nicht entladen werden"));
        Config.set("Command.Other.World", "Welt:");
        Config.set("Command.Other.Type", "Typ:");

        Config.set("Error.NotGoodName", java.util.Arrays.asList("%prefix% &cFehler: ein Weltname in diesem Format ist nicht erlaubt",
		            "%prefix% &cErlaubt sind nur: a-z | A-Z | 0-9 | _ | -"));

        Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList("%prefix% &cDu hast keinen Weltnamen angegeben"));

        Config.set("Error.WorldCreation", java.util.Arrays.asList("%prefix% &cIm Befehl fehlt etwas..."));

        Config.set("Error.World-Already-Exist", java.util.Arrays.asList("%prefix% &cDiese Welt existiert bereits"));

        Config.set("Error.World-Not-Exist", java.util.Arrays.asList("%prefix% &cDiese Welt existiert nicht"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}