package fr.dianox.hawn.utility.config.configs.messages.it_it;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Italian messages (since 1.6), made from the English ones.
 */
public class ITWorldManagerPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ITWorldManagerPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/it_IT/WorldManager.yml");
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
        Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList("[send-title]: &cImportazione avviata //n &7Attendi..."));
        Config.set("Gui.Import.Importing-With-A-Command", java.util.Arrays.asList("%prefix% &7Usa invece un comando: /hw import nomedelmondo"));

        Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(
        		"%prefix% &7Il mondo %arg1% è stato caricato",
		            "[send-title]: &3Importazione finita //n &7Puoi entrare in questo mondo"));

        Config.set("Gui.Unload", java.util.Arrays.asList("%prefix% &7Il mondo %arg1% è ora scaricato"));
        // Since 1.4.7
        Config.set("Gui.Unload-Moved", java.util.Arrays.asList("%prefix% &7Il mondo &e%arg1% &7è stato scaricato: ora sei in &e%arg2%"));
        Config.set("Gui.Import.Failed", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cnon è stato caricato, guarda la console"));
        // Since 1.6
        Config.set("Folia-Disabled", java.util.Arrays.asList("%prefix% &cIl World Manager è spento su Folia: Folia non può né creare né scaricare un mondo mentre il server è acceso"));

        // tp
        Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList("%prefix% &cIl teletrasporto non è riuscito",
		            "%prefix% &cSei già in quel mondo"));

        Config.set("Gui.Tp.Success", java.util.Arrays.asList("%prefix% &7Sei stato teletrasportato nel mondo &e%arg1%"));

        // create
        Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList("[send-title]: &eScegli il nome del mondo //n &7Scrivilo in chat"));
        Config.set("Gui.Create.Choose-A-Generator", java.util.Arrays.asList("[send-title]: &eScegli un generatore //n &7Scrivilo in chat"));

        Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList("[send-title]: &cMondo in creazione&7..."));

        Config.set("Gui.Create.World-Created", java.util.Arrays.asList("%prefix% &7Il mondo &e%arg1% &7è stato creato",
                "[send-title]: &3Mondo creato //n &7Puoi entrare in questo mondo"));

        Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList("%prefix% &7Creazione del mondo annullata..."));

        // Delete
        Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cè stato eliminato"));
        Config.set("Gui.Delete.Confirm-Command", java.util.Arrays.asList("%prefix% &cQuesto elimina il mondo &e%arg1% &ce la sua cartella, senza ritorno. Scrivi &e/hw delete %arg1% confirm &cper farlo"));
        Config.set("Error.Protected-World", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cnon può essere eliminato: il server ne ha bisogno"));
        Config.set("Error.Unload-Failed", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cnon è stato scaricato: non è stato eliminato niente"));
        Config.set("Gui.Default-World.Changed", java.util.Arrays.asList("%prefix% &e%arg1% &7è ora il mondo predefinito: sostituisce &e%arg2% &7in &e%arg3% &7opzione/i di &e%arg4% &7file"));
        Config.set("Gui.Default-World.Already", java.util.Arrays.asList("%prefix% &e%arg1% &7è già il mondo predefinito"));
        Config.set("Gui.Default-World.Shift-Click", java.util.Arrays.asList("%prefix% &eShift + clic &7per fare di &e%arg1% &7il mondo predefinito"));
        Config.set("Gui.Default-World.Conflict", java.util.Arrays.asList("%prefix% &6%arg1%: &e%arg2% &6aveva già le sue opzioni, quelle di &e%arg3% &6sono tenute accanto"));
        Config.set("Gui.Default-World.Spawn-Elsewhere", java.util.Arrays.asList("%prefix% &6Lo spawn predefinito &e%arg1% &6è ancora in &e%arg2%&6: creane uno nel nuovo mondo con &e/setspawn"));
        Config.set("Gui.Default-World.Yaml-Error", java.util.Arrays.asList("%prefix% &c%arg1% ha un errore YAML: non è stato modificato"));
        Config.set("Command.Default-World.Current", java.util.Arrays.asList("%prefix% &7Il mondo predefinito è &e%arg1% &7(&e/hw default <mondo> &7per cambiarlo)"));
        Config.set("Error.Default-World", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cè il mondo predefinito di Hawn: scegli prima un altro mondo predefinito"));
        Config.set("Gui.Other.Default-World.Title", "&6Mondo predefinito");
        Config.set("Gui.Other.Default-World.Current", java.util.Arrays.asList("&7È il mondo predefinito di Hawn:", "&7quello scritto nelle liste dei mondi", "&7dei file di configurazione"));
        Config.set("Gui.Other.Default-World.Change", java.util.Arrays.asList("&7Ora: &e%arg1%", "&eShift + clic&7 per fare di questo mondo", "&7quello predefinito: sostituisce &e%arg1%", "&7nelle liste dei mondi dei file"));
        Config.set("Gui.Other.Main.Default-World", "&6Mondo predefinito");

        Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList("%prefix% &cÈ successo qualcosa di strano... non succede niente, è un errore, ignoralo..."));

        // Modify World
        Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList("%prefix% &7L'ora del mondo &e%arg1%&7 è stata cambiata"));

        Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList("%prefix% &7Il meteo del mondo &e%arg1%&7 è ora sole"));

        Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList("%prefix% &7Il meteo del mondo &e%arg1%&7 è ora pioggia"));

        Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList("%prefix% &7Il meteo del mondo &e%arg1%&7 è ora temporale"));

        // M. W. Dif.
        Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList("%prefix% &7La difficoltà del mondo &e%arg1%&7 è ora pacifica"));

        Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList("%prefix% &7La difficoltà del mondo &e%arg1%&7 è ora facile"));

        Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList("%prefix% &7La difficoltà del mondo &e%arg1%&7 è ora normale"));

        Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList("%prefix% &7La difficoltà del mondo &e%arg1%&7 è ora difficile"));

        // Other
        Config.set("Gui.Other.Generator", "&7Generatore");
        Config.set("Gui.Other.Generator-Page.Void-Generator", "&7Generatore vuoto");
        Config.set("Gui.Other.Generator-Page.Custom-Generator", "&7Scrivi il tuo generatore");
        // Since 1.4
        Config.set("Gui.Create.Failed", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cnon è stato creato, guarda la console"));
        Config.set("Gui.Modify-World.Nothing-Selected", java.util.Arrays.asList("%prefix% &7Clicca prima una scelta, poi il cartello"));
        Config.set("Gui.Other.Generator-Default", "&7Predefinito");
        Config.set("Gui.Other.Generator-Help", java.util.Arrays.asList("&8Clic sinistro: predefinito o vuoto", "&8Clic destro: scrivi un generatore in chat"));
        Config.set("Gui.Other.Info.Title", "&eInformazioni");
        Config.set("Gui.Other.Info.Players", "&7Giocatori:");
        Config.set("Gui.Other.Info.Environment", "&7Ambiente:");
        Config.set("Gui.Other.Info.Generator", "&7Generatore:");
        Config.set("Gui.Other.Info.Chunks", "&7Chunk caricati:");
        Config.set("Gui.Other.Info.Entities", "&7Entità:");
        Config.set("Gui.Other.Info.Spawn", "&7Spawn:");
        Config.set("Gui.Other.Info.Time", "&7Ora:");
        Config.set("Gui.Other.Info.PvP", "&7PvP:");
        Config.set("Gui.Other.Info.Seed", "&7Seme:");
        // Since 1.4.4
        Config.set("Gui.Other.Size-Pending", "&7...");
        Config.set("Gui.Create.Generator-Cancelled", java.util.Arrays.asList("%prefix% &7Nessun generatore scritto: resta quello predefinito"));
        Config.set("Error.Unload-Protected", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cnon può essere scaricato: il server ne ha bisogno"));
        Config.set("Command.Unload.Failed", java.util.Arrays.asList("%prefix% &cIl mondo &e%arg1% &cnon è stato scaricato: dentro non deve esserci nessun giocatore"));
        Config.set("Gui.Other.WorldType.World-Type", "&7Tipo di mondo:");
        Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
        Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
        Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");

        Config.set("Gui.Other.WorldFace.World-Face", "&7Forma del mondo:");
        Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
        Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
        Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
        Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");

        Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Sole");
        Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Pioggia");
        Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Temporale");
        Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Meteo attuale:");

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
        Config.set("Gui.Other.World-Time", "Ora del mondo");
        Config.set("Gui.Other.World-Weather", "Meteo del mondo");
        Config.set("Gui.Other.Done", "&f&lFatto");
        Config.set("Gui.Other.Main.Line-One", "&eClic sinistro&7 per entrare nel mondo");
        Config.set("Gui.Other.Main.Line-Two", "&eClic destro&7 per cambiare le impostazioni del mondo");
        Config.set("Gui.Other.Main.Line-Three", "&eShift &7e &eclic destro&7 per eliminare il mondo");
        Config.set("Gui.Other.Main.Line-Four", "&7Il mondo non è caricato al momento");
        Config.set("Gui.Other.Main.Line-One-Second", "&eClic sinistro&7 per caricare il mondo");
        Config.set("Gui.Other.Unload.Name", "&cScarica il mondo");
        Config.set("Gui.Other.Unload.Lore", "&7Fino al prossimo riavvio: i giocatori dentro vanno nel mondo predefinito");
        Config.set("Gui.Other.Page.Back", "&bIndietro");
        Config.set("Gui.Other.Page.Next", "&bAvanti");
        Config.set("Gui.Other.Create-a-new-world", "&f&lCrea un nuovo mondo");
        Config.set("Gui.Other.Back.PanelAdmin", "&c&lTorna al pannello admin");
        Config.set("Gui.Other.Back.Menu", "&c&lTorna al menu");
        Config.set("Gui.Other.Back.Main-Menu", "&c&lTorna al menu principale");
        Config.set("Gui.Other.Delete.Confirm", "&aSo cosa sto facendo");
        Config.set("Gui.Other.Delete.No", "&cAlla fine... no");

        /*
         * Command
         */
        Config.set("Command.Unload.Error", java.util.Arrays.asList("%prefix% &cErrore, impossibile scaricare questo mondo"));
        Config.set("Command.Other.World", "World:");
        Config.set("Command.Other.Type", "Type:");

        Config.set("Error.NotGoodName", java.util.Arrays.asList("%prefix% &cErrore di Hawn: un nome di mondo con questo formato non è permesso",
		            "%prefix% &cGli unici formati di nome permessi sono: a-z | A-Z | 0-9 | _ | -"));

        Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList("%prefix% &cNon hai scritto il nome del mondo"));

        Config.set("Error.WorldCreation", java.util.Arrays.asList("%prefix% &cHai dimenticato qualcosa scrivendo il comando..."));

        Config.set("Error.World-Already-Exist", java.util.Arrays.asList("%prefix% &cQuesto mondo esiste"));

        Config.set("Error.World-Not-Exist", java.util.Arrays.asList("%prefix% &cQuesto mondo non esiste"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}