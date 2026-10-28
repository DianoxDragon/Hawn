package fr.dianox.hawn.utility.config.configs.messages.nl_nl;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Dutch messages (Messages/nl_NL), since Hawn 1.5.4: the texts of WorldManagerPanelConfig translated.
 */
public class NLWorldManagerPanelConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public NLWorldManagerPanelConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/nl_NL/WorldManager.yml");
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
        Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList("[send-title]: &cImport gestart //n &7Even geduld..."));
        Config.set("Gui.Import.Importing-With-A-Command", java.util.Arrays.asList("%prefix% &7Gebruik hiervoor een commando: /hw import wereldnaam"));

        Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(
        		"%prefix% &7De wereld %arg1% is geladen",
		            "[send-title]: &3Import klaar //n &7Je kunt naar deze wereld gaan"));

        Config.set("Gui.Unload", java.util.Arrays.asList("%prefix% &7De wereld %arg1% is nu ontladen"));
        // Since 1.4.7
        Config.set("Gui.Unload-Moved", java.util.Arrays.asList("%prefix% &7De wereld &e%arg1% &7is ontladen: je bent nu in &e%arg2%"));
        Config.set("Gui.Import.Failed", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &ckon niet worden geladen, zie de console"));

        // tp
        Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList("%prefix% &cDe teleportatie is mislukt",
		            "%prefix% &cJe bent al in deze wereld"));

        Config.set("Gui.Tp.Success", java.util.Arrays.asList("%prefix% &7Je bent naar de wereld &e%arg1%&7 geteleporteerd"));

        // create
        Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList("[send-title]: &eKies een naam voor de wereld //n &7Typ hem in de chat"));
        Config.set("Gui.Create.Choose-A-Generator", java.util.Arrays.asList("[send-title]: &eKies een generator //n &7Typ hem in de chat"));

        Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList("[send-title]: &cWereld wordt gemaakt&7..."));

        Config.set("Gui.Create.World-Created", java.util.Arrays.asList("%prefix% &7De wereld &e%arg1% &7is gemaakt",
                "[send-title]: &3Wereld gemaakt //n &7Je kunt naar deze wereld gaan"));

        Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList("%prefix% &7Het maken van de wereld is geannuleerd..."));

        // Delete
        Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &cis verwijderd"));
        Config.set("Gui.Delete.Confirm-Command", java.util.Arrays.asList("%prefix% &cDit verwijdert de wereld &e%arg1% &cen haar map, zonder weg terug. Typ &e/hw delete %arg1% confirm&c om het te doen"));
        Config.set("Error.Protected-World", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &ckan niet worden verwijderd: de server heeft haar nodig"));
        Config.set("Error.Unload-Failed", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &ckon niet worden ontladen: er is niets verwijderd"));
        Config.set("Gui.Default-World.Changed", java.util.Arrays.asList("%prefix% &e%arg1% &7is nu de standaardwereld: ze vervangt &e%arg2% &7in &e%arg3% &7optie(s) van &e%arg4% &7bestand(en)"));
        Config.set("Gui.Default-World.Already", java.util.Arrays.asList("%prefix% &e%arg1% &7is al de standaardwereld"));
        Config.set("Gui.Default-World.Shift-Click", java.util.Arrays.asList("%prefix% &eShift + klik&7 om &e%arg1% &7de standaardwereld te maken"));
        Config.set("Gui.Default-World.Conflict", java.util.Arrays.asList("%prefix% &6%arg1%: &e%arg2% &6had al eigen opties, die van &e%arg3% &6blijven ernaast staan"));
        Config.set("Gui.Default-World.Spawn-Elsewhere", java.util.Arrays.asList("%prefix% &6De standaardspawn &e%arg1% &6is nog in &e%arg2%&6: maak er een in de nieuwe wereld met &e/setspawn"));
        Config.set("Gui.Default-World.Yaml-Error", java.util.Arrays.asList("%prefix% &c%arg1% heeft een YAML-fout: het bestand is niet gewijzigd"));
        Config.set("Command.Default-World.Current", java.util.Arrays.asList("%prefix% &7De standaardwereld is &e%arg1% &7(&e/hw default <wereld> &7om te wijzigen)"));
        Config.set("Error.Default-World", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &cis de standaardwereld van Hawn: kies eerst een andere standaardwereld"));
        Config.set("Gui.Other.Default-World.Title", "&6Standaardwereld");
        Config.set("Gui.Other.Default-World.Current", java.util.Arrays.asList("&7Dit is de standaardwereld van Hawn:", "&7de wereld die in de wereldlijsten", "&7van de configuratiebestanden staat"));
        Config.set("Gui.Other.Default-World.Change", java.util.Arrays.asList("&7Nu: &e%arg1%", "&eShift + klik&7 om deze wereld", "&7de standaardwereld te maken: ze vervangt &e%arg1%", "&7in de wereldlijsten van de bestanden"));
        Config.set("Gui.Other.Main.Default-World", "&6Standaardwereld");

        Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList("%prefix% &cEr is iets vreemds gebeurd... er gebeurt niets, een fout, negeer haar gewoon..."));

        // Modify World
        Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList("%prefix% &7De tijd van de wereld &e%arg1%&7 is gewijzigd"));

        Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList("%prefix% &7Het weer van de wereld &e%arg1%&7 is nu zonnig"));

        Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList("%prefix% &7Het weer van de wereld &e%arg1%&7 is nu regenachtig"));

        Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList("%prefix% &7Het weer van de wereld &e%arg1%&7 is nu onweer"));

        // M. W. Dif.
        Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList("%prefix% &7De moeilijkheid van de wereld &e%arg1%&7 is nu vredig"));

        Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList("%prefix% &7De moeilijkheid van de wereld &e%arg1%&7 is nu makkelijk"));

        Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList("%prefix% &7De moeilijkheid van de wereld &e%arg1%&7 is nu normaal"));

        Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList("%prefix% &7De moeilijkheid van de wereld &e%arg1%&7 is nu moeilijk"));

        // Other
        Config.set("Gui.Other.Generator", "&7Generator");
        Config.set("Gui.Other.Generator-Page.Void-Generator", "&7Lege wereld");
        Config.set("Gui.Other.Generator-Page.Custom-Generator", "&7Eigen generator typen");
        // Since 1.4
        Config.set("Gui.Create.Failed", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &ckon niet worden gemaakt, zie de console"));
        Config.set("Gui.Modify-World.Nothing-Selected", java.util.Arrays.asList("%prefix% &7Klik eerst op een keuze, dan op het bord"));
        Config.set("Gui.Other.Generator-Default", "&7Standaard");
        Config.set("Gui.Other.Generator-Help", java.util.Arrays.asList("&8Linkerklik: standaard of leeg", "&8Rechterklik: een generator in de chat typen"));
        Config.set("Gui.Other.Info.Title", "&eInformatie");
        Config.set("Gui.Other.Info.Players", "&7Spelers:");
        Config.set("Gui.Other.Info.Environment", "&7Omgeving:");
        Config.set("Gui.Other.Info.Generator", "&7Generator:");
        Config.set("Gui.Other.Info.Chunks", "&7Geladen chunks:");
        Config.set("Gui.Other.Info.Entities", "&7Entiteiten:");
        Config.set("Gui.Other.Info.Spawn", "&7Spawn:");
        Config.set("Gui.Other.Info.Time", "&7Tijd:");
        Config.set("Gui.Other.Info.PvP", "&7PvP:");
        Config.set("Gui.Other.Info.Seed", "&7Seed:");
        // Since 1.4.4
        Config.set("Gui.Other.Size-Pending", "&7...");
        Config.set("Gui.Create.Generator-Cancelled", java.util.Arrays.asList("%prefix% &7Geen generator getypt: de standaard blijft"));
        Config.set("Error.Unload-Protected", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &ckan niet worden ontladen: de server heeft haar nodig"));
        Config.set("Command.Unload.Failed", java.util.Arrays.asList("%prefix% &cDe wereld &e%arg1% &ckon niet worden ontladen: er mag geen speler in zijn"));
        Config.set("Gui.Other.WorldType.World-Type", "&7Soort wereld:");
        Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
        Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
        Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");

        Config.set("Gui.Other.WorldFace.World-Face", "&7Vorm van de wereld:");
        Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
        Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
        Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
        Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");

        Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Zon");
        Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Regen");
        Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Onweer");
        Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Huidig weer:");

        Config.set("Gui.Other.Difficulty.Peaceful", "PEACEFUL");
        Config.set("Gui.Other.Difficulty.Easy", "EASY");
        Config.set("Gui.Other.Difficulty.Normal", "NORMAL");
        Config.set("Gui.Other.Difficulty.Hard", "HARD");

        Config.set("Gui.Other.Name", "Naam:");
        Config.set("Gui.Other.Players", "Spelers:");
        Config.set("Gui.Other.Size", "Grootte:");
        Config.set("Gui.Other.Difficulty-Main", "Moeilijkheid:");
        Config.set("Gui.Other.Difficulty-Two", "Moeilijkheid");
        Config.set("Gui.Other.Environment", "Omgeving:");
        Config.set("Gui.Other.World-Time", "Tijd van de wereld");
        Config.set("Gui.Other.World-Weather", "Weer van de wereld");
        Config.set("Gui.Other.Done", "&f&lKlaar");
        Config.set("Gui.Other.Main.Line-One", "&eLinkerklik&7 om naar de wereld te gaan");
        Config.set("Gui.Other.Main.Line-Two", "&eRechterklik&7 om de instellingen van de wereld te wijzigen");
        Config.set("Gui.Other.Main.Line-Three", "&eShift &7+ &erechterklik&7 om de wereld te verwijderen");
        Config.set("Gui.Other.Main.Line-Four", "&7De wereld is nu niet geladen");
        Config.set("Gui.Other.Main.Line-One-Second", "&eLinkerklik&7 om de wereld te laden");
        Config.set("Gui.Other.Unload.Name", "&cWereld ontladen");
        Config.set("Gui.Other.Unload.Lore", "&7Tot de volgende herstart: de spelers erin gaan naar de standaardwereld");
        Config.set("Gui.Other.Page.Back", "&bVorige");
        Config.set("Gui.Other.Page.Next", "&bVolgende");
        Config.set("Gui.Other.Create-a-new-world", "&f&lNieuwe wereld maken");
        Config.set("Gui.Other.Back.PanelAdmin", "&c&lTerug naar het adminpaneel");
        Config.set("Gui.Other.Back.Menu", "&c&lTerug naar het menu");
        Config.set("Gui.Other.Back.Main-Menu", "&c&lTerug naar het hoofdmenu");
        Config.set("Gui.Other.Delete.Confirm", "&aIk weet wat ik doe");
        Config.set("Gui.Other.Delete.No", "&cToch maar niet");

        /*
         * Command
         */
        Config.set("Command.Unload.Error", java.util.Arrays.asList("%prefix% &cFout, deze wereld kan niet worden ontladen"));
        Config.set("Command.Other.World", "Wereld:");
        Config.set("Command.Other.Type", "Soort:");

        Config.set("Error.NotGoodName", java.util.Arrays.asList("%prefix% &cFout: een wereldnaam in deze vorm mag niet",
		            "%prefix% &cAlleen deze tekens mogen: a-z | A-Z | 0-9 | _ | -"));

        Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList("%prefix% &cJe hebt geen wereldnaam gegeven"));

        Config.set("Error.WorldCreation", java.util.Arrays.asList("%prefix% &cEr ontbreekt iets in het commando..."));

        Config.set("Error.World-Already-Exist", java.util.Arrays.asList("%prefix% &cDeze wereld bestaat al"));

        Config.set("Error.World-Not-Exist", java.util.Arrays.asList("%prefix% &cDeze wereld bestaat niet"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}
