package fr.dianox.hawn.utility.config.configs.messages.it_it;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Italian messages (since 1.6), made from the English ones.
 */
public class ITAdminAMConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ITAdminAMConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/it_IT/Admin.yml");
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

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList("&cNon sei un giocatore"));
        Config.set("Error.Command.Hawn", java.util.Arrays.asList("&cErrore, prova /hawn"));
        Config.set("Error.Command.Delspawn", java.util.Arrays.asList("&c/hawn delspawn <spawn>"));
        Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList("&cQuesto nome esiste già"));
        Config.set("Error.Argument-Missing", java.util.Arrays.asList("&cMi dispiace, mancano uno o due argomenti."));
        Config.set("Error.No-Spawn", java.util.Arrays.asList("&cLo spawn non esiste"));

	        /* ------------ *
	         * HELP COMMAND *
	         * ------------ */

	        Config.set("Command.Help.Hawn-SpawnManager", "Gestisci gli spawn");
	        Config.set("Command.Help.Hawn-Reload", "Ricarica alcuni file di configurazione");
	        Config.set("Command.Help.Hawn-Version", "Mostra la versione del plugin");
	        Config.set("Command.Help.Hawn-Tps", "Mostra il TPS del server");
	        Config.set("Command.Help.Hawn-Info", "Mostra le informazioni del server");
	        Config.set("Command.Help.Hawn-Build", "Per aggirare le protezioni per un po'");
	        Config.set("Command.Help.Hawn-Hooks", "Per controllare se le dipendenze del plugin sono attive");
	        Config.set("Command.Help.Hawn-Urgent", "Per usare la modalità d'urgenza");
	        Config.set("Command.Help.Hawn-Maintenance", "Per usare la modalità manutenzione");
	        Config.set("Command.Help.Hawn-Donors", "Mostra chi ha fatto una donazione per il plugin");
	        Config.set("Command.Help.Hawn-About", "Mostra solo qualche informazione");
	        Config.set("Command.Help.Hawn-Parse", "Mostra cosa restituisce un placeholder");
	        Config.set("Command.Help.Hawn-NightVision", "Vedi al buio");
	        Config.set("Command.Help.Hawn-NoClip", "Attraversa i blocchi restando in creativa");
	        Config.set("Command.Help.Hawn-SlotView", "Mostra il numero dello slot cliccato");
	        Config.set("Command.Help.Hawn-EditPlayer", "Modifica un giocatore");
	        Config.set("Command.Help.Hawn-Setup", "Configura Hawn per la prima volta");
	        Config.set("Command.Help.Hawn-Debug", "Gli errori dei file di configurazione e gli errori nascosti");
	        Config.set("Command.Help.Hawn-Rank", "Dai, togli o controlla il rango di un giocatore (Ranks.yml)");
	        Config.set("Command.Help.Broadcast", "Invia un annuncio");
	        Config.set("Command.Help.Warning", "Invia un avvertimento");
	        Config.set("Command.Help.Broadcast-Title", "Invia un annuncio come titolo");
	        Config.set("Command.Help.Broadcast-ActionBar", "Invia un annuncio nella barra delle azioni");
	        Config.set("Command.Help.AdminPanel", "Apri il pannello admin");
	        Config.set("Command.Help.CheckAccount", "Controlla un giocatore");
	        Config.set("Command.Help.ClearInv", "Svuota l'inventario di un giocatore");
	        Config.set("Command.Help.InvSee", "Mostra l'inventario di un giocatore");
	        Config.set("Command.Help.Ip", "Mostra l'IP di un giocatore");
	        Config.set("Command.Help.List", "Mostra la lista dei giocatori della lobby");
	        Config.set("Command.Help.Spawn", "Vai allo spawn");
	        Config.set("Command.Help.Spawn-Tp", "Teletrasporta un giocatore a uno spawn");
	        Config.set("Command.Help.DelSpawn", "Elimina uno spawn");
	        Config.set("Command.Help.SpawnList", "Mostra la lista degli spawn");
	        Config.set("Command.Help.SetSpawn", "Imposta uno spawn");
	        Config.set("Command.Help.SetWarp", "Imposta un warp");
	        Config.set("Command.Help.Warp", "Vai al warp");
	        Config.set("Command.Help.WarpList", "Mostra la lista dei warp");
	        Config.set("Command.Help.DelWarp", "Elimina il warp");
	        Config.set("Command.Help.EditWarp", "Modifica la posizione di un warp");
	        Config.set("Command.Help.Sun", "Fa tornare il sole");
	        Config.set("Command.Help.Rain", "Fa piovere sul mondo");
	        Config.set("Command.Help.Thunder", "Se ti piace il brutto tempo");
	        Config.set("Command.Help.Day", "Mette il giorno");
	        Config.set("Command.Help.Night", "Mette la notte");
	        Config.set("Command.Help.Fly", "Attiva il volo");
	        Config.set("Command.Help.FlySpeed", "Cambia la velocità di volo di un giocatore");
	        Config.set("Command.Help.Speed", "Cambia, attiva o disattiva la velocità");
	        Config.set("Command.Help.Heal", "Cura un giocatore");
	        Config.set("Command.Help.Feed", "Sfama un giocatore");
	        Config.set("Command.Help.Ping", "Mostra il ping di un giocatore");
	        Config.set("Command.Help.Vanish", "Rende invisibile un giocatore");
	        Config.set("Command.Help.Gamemode", "Cambia la modalità di gioco di un giocatore");
	        Config.set("Command.Help.Burn", "Fa bruciare un giocatore");
	        Config.set("Command.Help.Cleargrounditems", "Toglie gli oggetti a terra");
	        Config.set("Command.Help.Clearmobs", "Toglie i mob");
	        Config.set("Command.Help.EnderChest", "Apre la cassa di Ender");
	        Config.set("Command.Help.Exp", "Modifica l'esperienza di un giocatore");
	        Config.set("Command.Help.Getpos", "Mostra la posizione di un giocatore");
	        Config.set("Command.Help.Hat", "Mettiti un cappello");
	        Config.set("Command.Help.Kickall", "Espelle tutti");
	        Config.set("Command.Help.Repair", "Ripara un oggetto");
	        Config.set("Command.Help.Skull", "Ottieni la testa di un giocatore");
	        Config.set("Command.Help.Suicide", "Suicidati");
	        Config.set("Command.Help.Workbench", "Apre il banco da lavoro di un giocatore");
	        Config.set("Command.Help.ClearChat", "Mostra l'aiuto di clearchat");
	        Config.set("Command.Help.DelayChat", "Mette un ritardo sulla chat");
	        Config.set("Command.Help.Gmute", "Blocca la chat");
	        Config.set("Command.Help.Help", "Mostra l'aiuto personalizzato, se attivo");
	        Config.set("Command.Help.Gotop", "Vai sul blocco più alto della tua posizione");
	        Config.set("Command.Help.Emoji", "Mostra il menu delle emoji");
	        Config.set("Command.Help.Scoreboard", "Attiva o disattiva lo scoreboard");
	        Config.set("Command.Help.Scoreboard-Set", "Per cambiare lo scoreboard attuale");
	        Config.set("Command.Help.Scoreboard-Keep", "Tieni lo scoreboard tra i server");
	        Config.set("Command.Help.Scoreboard-List", "Mostra tutti gli scoreboard registrati");
	        Config.set("Command.Help.Option", "Per le opzioni principali del giocatore");
	        Config.set("Command.Help.Hworld", "Gestisci i mondi");

        /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
        Config.set("Command.Server-Info.General", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformazioni del server (Tutto)",
		            "        &3creato da Dianox",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersione di Java&r&8:&r %javaversion%",
		            "&7&lSO&r&8:&r %osversion%",
		            "&7&lAggiornamento di Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersione del server&r&8:&r %serverversion%",
		            "",
		            "  &8→ &6&lMemoria",
		            "&7&lMemoria massima &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLibera&7&l/&c&lTotale&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Barra-[%barmemory%&8]",
		            "",
		            "  &8→ &6&lCPU (Processore)",
		            "&7&lCarico medio&8:&r %averagecpuload%&8%",
		            "&7&lCarico attuale&8:&r %cpuload%&8%",
		            "&8Barra-[%barcpu%&8]",
		            "",
		            "  &8→ &6&lSpazio su disco",
		            "&7&lDisco totale&r&8:&r %totalspace%&8MB",
		            "&a&lDisco libero&7&l/&c&lDisco totale&7&l utilizzabile&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Barra-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Memory", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformazioni del server (Memoria)",
		            "",
		            "  &8→ &6&lMemoria",
		            "&7&lMemoria massima &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLibera&7&l/&c&lTotale&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Barra-[%barmemory%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.CPU", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformazioni del server (CPU)",
		            "",
		            "  &8→ &6&lCPU (Processore)",
		            "&7&lCarico medio&8:&r %averagecpuload%&8%",
		            "&7&lCarico attuale&8:&r %cpuload%&8%",
		            "&8Barra-[%barcpu%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Disk", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformazioni del server (Disco)",
		            "",
		            "  &8→ &6&lSpazio su disco",
		            "&7&lDisco totale&r&8:&r %totalspace%&8MB",
		            "&a&lDisco libero&7&l/&c&lDisco totale&7&l utilizzabile&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Barra-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Server", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformazioni del server (Server)",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersione di Java&r&8:&r %javaversion%",
		            "&7&lSO&r&8:&r %osversion%",
		            "&7&lAggiornamento di Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersione del server&r&8:&r %serverversion%",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Tps", java.util.Arrays.asList("  &8→ &6&lTps&8: &r%tps%"));

        Config.set("Command.Version", java.util.Arrays.asList("  &8→ &6&lVersione di Hawn (creato da Dianox)&8: &r%gethawnversion%"));

        // Since 1.4.8: /hawn debug
        Config.set("Debug.Header", java.util.Arrays.asList("&8&m-----&r &6Debug di Hawn &8&m-----"));
        Config.set("Debug.No-Config-Error", java.util.Arrays.asList("&aNessun errore trovato nei file di configurazione."));
        Config.set("Debug.Config-Errors", java.util.Arrays.asList("&e%arg 1% &7errore/i nei file di configurazione:"));
        Config.set("Debug.Auto", java.util.Arrays.asList("&e%arg 1% &7opzione/i in AUTO lasciate a un altro plugin:"));
        Config.set("Debug.No-Hidden-Error", java.util.Arrays.asList("&aNessun errore nascosto dall'avvio."));
        Config.set("Debug.Hidden-Errors", java.util.Arrays.asList("&e%arg 1% &7errore/i superati da Hawn dall'avvio:"));
        Config.set("Debug.Tip", java.util.Arrays.asList("&7Plugin.Debug: true in general.yml li scrive nella console, con i dettagli."));
        // Since 1.4.8: /hawn rank
        Config.set("Ranks.Usage", java.util.Arrays.asList("%prefix% &7/hawn rank set <giocatore> <rango> &8| &7remove <giocatore> &8| &7info <giocatore> &8| &7list"));
        Config.set("Ranks.Off", java.util.Arrays.asList("%prefix% &cI ranghi sono spenti (Ranks.yml, Ranks.Enable: false)"));
        Config.set("Ranks.Unknown-Rank", java.util.Arrays.asList("%prefix% &cIl rango &e%arg 1% &cnon esiste o è spento. Ranghi: &7%arg 2%"));
        Config.set("Ranks.LuckPerms-Source", java.util.Arrays.asList("%prefix% &7I ranghi vengono da LuckPerms (Ranks.Source): cambia il gruppo di &e%arg 1% &7in LuckPerms (&e/lp user %arg 1% parent set <gruppo>&7)"));
        Config.set("Ranks.Set", java.util.Arrays.asList("%prefix% &e%arg 1% &7ha ora il rango &e%arg 2%"));
        Config.set("Ranks.Removed", java.util.Arrays.asList("%prefix% &e%arg 1% &7non ha più un rango dato a mano"));
        Config.set("Ranks.No-Rank-By-Hand", java.util.Arrays.asList("%prefix% &e%arg 1% &cnon ha un rango dato a mano (lo danno i permessi o LuckPerms)"));
        // 123: a Bedrock name with a dot (since 1.5.6)
        Config.set("Ranks.Name-With-Dot", java.util.Arrays.asList("%prefix% &e%arg 1% &cha un punto nel nome (Bedrock): deve essere online per ricevere un rango, salvato sotto il suo UUID"));
        Config.set("Ranks.Info", java.util.Arrays.asList("%prefix% &e%arg 1%&7: rango &e%arg 2% &7(%arg 3%) &8| &r%arg 4%"));
        Config.set("Ranks.Info-Offline", java.util.Arrays.asList("%prefix% &e%arg 1% &7è offline. Rango dato a mano: &e%arg 2%"));
        Config.set("Ranks.List-Header", java.util.Arrays.asList("%prefix% &e%arg 1% &7rango/i, il primo in cima alla tab list:"));
        Config.set("Ranks.List-Line", java.util.Arrays.asList("&8- &e%arg 1% &7(priorità %arg 2%) &r%arg 3% &8| &7online: &f%arg 4%"));
        Config.set("Ranks.Source.HAND", "dato a mano");
        Config.set("Ranks.Source.PERMISSION", "tramite permesso");
        Config.set("Ranks.Source.LUCKPERMS", "da LuckPerms");
        Config.set("Ranks.Source.DEFAULT", "il rango predefinito");
        Config.set("Ranks.Source.NONE", "none");
        // Since 1.5: /hawn menu
        Config.set("Command.Help.Hawn-Menu", "Apri un menu della cartella Menus per te o per un giocatore, o elencali");
        // The holograms (since 1.6)
        Config.set("Holograms.Usage", java.util.Arrays.asList("%prefix% &7/hawn hologram create <nome> [testo] &8| &7delete <nome> &8| &7movehere <nome> &8| &7addline <nome> <testo> &8| &7setline <nome> <riga> <testo> &8| &7removeline <nome> <riga> &8| &7list &8| &7enable <nome> &8| &7disable <nome>"));
        Config.set("Holograms.Created", java.util.Arrays.asList("%prefix% &7L'ologramma &e%arg 1% &7è stato creato dove sei"));
        Config.set("Holograms.Exists", java.util.Arrays.asList("%prefix% &cL'ologramma &e%arg 1% &cesiste già"));
        Config.set("Holograms.Not-Found", java.util.Arrays.asList("%prefix% &cL'ologramma &e%arg 1% &cnon esiste"));
        Config.set("Holograms.Deleted", java.util.Arrays.asList("%prefix% &7L'ologramma &e%arg 1% &7è stato eliminato"));
        Config.set("Holograms.Moved", java.util.Arrays.asList("%prefix% &7L'ologramma &e%arg 1% &7ora è dove sei"));
        Config.set("Holograms.Changed", java.util.Arrays.asList("%prefix% &7L'ologramma &e%arg 1% &7ora ha &e%arg 2% &7riga/e"));
        Config.set("Holograms.Bad-Line", java.util.Arrays.asList("%prefix% &cL'ologramma &e%arg 1% &cnon ha la riga &e%arg 2%"));
        Config.set("Holograms.List", java.util.Arrays.asList("%prefix% &7Ologrammi: &e%arg 1%"));
        Config.set("Holograms.None", java.util.Arrays.asList("%prefix% &7Nessun ologramma nella cartella Holograms"));
        Config.set("Holograms.Enabled", java.util.Arrays.asList("%prefix% &7L'ologramma &e%arg 1% &7è attivo: viene mostrato"));
        Config.set("Holograms.Disabled", java.util.Arrays.asList("%prefix% &7L'ologramma &e%arg 1% &7è disattivato: viene nascosto"));
        Config.set("Holograms.Off-Tag", java.util.Arrays.asList("&8(disattivato)"));
        Config.set("Holograms.No-PacketEvents", java.util.Arrays.asList("%prefix% &cGli ologrammi hanno bisogno del plugin &ePacketEvents&c: modrinth.com/plugin/packetevents"));
        Config.set("Holograms.Bad-Name", java.util.Arrays.asList("%prefix% &cIl nome di un ologramma: lettere, numeri, _ e -"));
        Config.set("Command.Help.Hawn-Hologram", "Mostra un testo in un luogo, nella lingua di ogni giocatore");
        Config.set("Menus.Usage", java.util.Arrays.asList("%prefix% &7/hawn menu open <menu> [giocatore] &8| &7list"));
        Config.set("Menus.Opened", java.util.Arrays.asList("%prefix% &7Il menu &e%arg 1% &7è aperto per &e%arg 2%"));
        Config.set("Menus.Not-Opened", java.util.Arrays.asList("%prefix% &cIl menu &e%arg 1% &cnon è stato aperto per &e%arg 2% &c(permesso o mondo)"));
        Config.set("Menus.List", java.util.Arrays.asList("%prefix% &7Menu: &e%arg 1%"));
        Config.set("Menus.None", java.util.Arrays.asList("%prefix% &7Nessun menu nella cartella Menus"));
        Config.set("TPS.Check.15", java.util.Arrays.asList("&cIl tuo TPS è sotto %arg 1%, fai qualcosa per migliorare la stabilità della tua lobby"));
        Config.set("TPS.Check.5", java.util.Arrays.asList("&cIl tuo TPS è sotto %arg 1%, il server potrebbe spegnersi: fai /stop per evitare problemi.", "&cATTENZIONE: SERVER IN STATO CRITICO"));

        /* --------------- *
         * RELOAD COMMANDS *
         * --------------- */
        Config.set("Command.Reload", java.util.Arrays.asList("&aConfigurazione ricaricata"));

        Config.set("Command.Build-Bypass.On", java.util.Arrays.asList("&bOra puoi aggirare tutte le restrizioni di costruzione"));
        Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList("&cNon puoi più aggirare le restrizioni di costruzione"));


        /*
         * Vanish
         */
        Config.set("Vanish.Vanish-On.Enable", true);
        Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList("&7[ %player% è ora invisibile ]"));

        Config.set("Vanish.Vanish-Off.Enable", true);
        Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList("&7[ %player% non è più invisibile ]"));
        Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList("&7[ %target% è ora invisibile, da %player% ]"));
        Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList("&7[ %target% non è più invisibile, da %player% ]"));

        Config.set("Maintenance.On", java.util.Arrays.asList("%prefix% &7Hai &aattivato&7 la manutenzione"));
        Config.set("Maintenance.Off", java.util.Arrays.asList("%prefix% &7Hai &cdisattivato&7 la manutenzione"));
        Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(" &4* &cLa manutenzione è &eattiva&4 *"));
        Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(" &4* &cLa manutenzione è &edisattivata&4 *"));

        Config.set("Urgent-mode.On", java.util.Arrays.asList("%prefix% &7Hai &aattivato&7 la modalità d'urgenza"));
        Config.set("Urgent-mode.Off", java.util.Arrays.asList("%prefix% &7Hai &cdisattivato&7 la modalità d'urgenza"));
        Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(" &4* &cLa modalità d'urgenza è &eattiva&4 *"));
        Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(" &4* &cLa modalità d'urgenza è &edisattivata&4 *"));
        Config.set("Urgent-mode.Zip", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cÈ stato fatto un backup di Hawn"));
        Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cErrore, devi essere nella console per disattivare la modalità d'urgenza"));
        Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cMi dispiace, non puoi usare questo comando"));
        Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cUna modifica è stata fatta da %player% nel pannello admin",
		            "%arg1% nel file %arg2%"));
        Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cTutti i plugin sono stati disattivati"));
        Config.set("Urgent-mode.Ops-Removed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c%arg1% operatore/i tolti fino alla fine della modalità d'urgenza"));
        Config.set("Urgent-mode.Ops-Restored", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7%arg1% operatore/i restituiti"));
        Config.set("Urgent-mode.Lockdown-Command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cIl server è in modalità d'urgenza: i comandi sono disattivati"));
        Config.set("Urgent-mode.Lockdown-Chat", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cIl server è in modalità d'urgenza: la chat è disattivata"));
        Config.set("Urgent-mode.Zip-Failed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cIl backup non è riuscito, guarda la console"));
        Config.set("Urgent-mode.Console-Only", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cLa modalità d'urgenza si avvia solo dalla console"));
        Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7Tutti i plugin sono stati &aattivati",
		            "&eServe un riavvio per evitare problemi"));

        Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList("%prefix% &e%player% ha provato questo comando: &b%arg1%"));

        Config.set("Command.SlotView.On", java.util.Arrays.asList("%prefix% &7Ora &avedi&7 il numero di ogni slot di un menu o di un inventario quando ci clicchi"));
        Config.set("Command.SlotView.Off", java.util.Arrays.asList("%prefix% &7Lo SlotView è &cdisattivato"));

        Config.set("Command.IP", java.util.Arrays.asList("%prefix% &7L'IP del giocatore è: &e%getplayerip%"));

        Config.set("Command.Kickall", java.util.Arrays.asList("%prefix% &7Tutti i giocatori sono stati espulsi"));

        Config.set("Command.ClearGroundItems", java.util.Arrays.asList("%prefix% &7Tutti gli oggetti sono stati tolti"));

        Config.set("Command.ClearMobs", java.util.Arrays.asList("%prefix% &7Tutti i mob sono stati tolti"));

        Config.set("Command.CheckAccount", java.util.Arrays.asList("",
		            "  &8→ &r&lInformazioni del giocatore&8:&r &b%target%",
		            "&7&lData di accesso&r&8:&e %hawn_player_join_date%",
		            "&7&lData del primo accesso&r&8:&e %hawn_player_first_join_date%",
		            "&7&lIP&r&8:&e %player_ip%",
		            "",
		            "  &8→ &r&lOpzioni&8:&r &8(&aVerde &8=&7 sì / &cRosso&8 = &7no&8)",
		            "&7&lVisibilità dei giocatori: %pv_point%",
		            "&7&lVelocità: %ps_point% &7(&e%ps_number%&7)",
		            "&7&lVolo: %pof_point%",
		            "&7&lDoppio salto: %dj_point%",
		            "&7&lAnnunci automatici: %ab_point%",
		            "&7&lInvisibile: %v_point%",
		            "&7&lTieni lo scoreboard: %ksb_point% &8(&e%scorename%&8)",
		            "&7&lSalto potenziato: %jb_point%",
		            "",
		            "&7&lModalità di gioco: &e%gm_number%",
		            ""));

        // List

        Config.set("Command.List.Part-One", java.util.Arrays.asList("§8//§7§m---------------§r§8\\\\ §3[§bLista§3] §8//§7§m---------------§r§8\\\\",
		            " ",
		            "  &8→ &6&lPagina %number%",
		            " "));

        Config.set("Command.List.Part-Two", java.util.Arrays.asList("§8\\\\§7§m---------------§r§8// §3[§bLista§3] §8\\\\§7§m---------------§r§8//"));

        Config.set("Command.List.Gui.Other.Page.Next", "&bAvanti");
        Config.set("Command.List.Gui.Other.Page.Back", "&bIndietro");
        Config.set("Command.List.Gui.Other.Back.PanelAdmin", "&c&lTorna al pannello admin");
        Config.set("Command.List.Gui.Player.Survival", "&cSOPRAVVIVENZA");
        Config.set("Command.List.Gui.Player.Spectator", "&eSPETTATORE");
        Config.set("Command.List.Gui.Player.Creative", "&6CREATIVA");
        Config.set("Command.List.Gui.Player.Adventure", "&aAVVENTURA");
        Config.set("Command.List.Gui.Player.Gamemode", "&7Modalità di gioco:");
        Config.set("Command.List.Gui.Player.World", "&7Mondo: &e");
        Config.set("Command.List.Gui.Player.LeftClick", "&eClic sinistro&7 per modificare il giocatore");

        // EDIT PLAYER

        Config.set("Command.EditPlayer.Gui.Gamemode.LeftClick", "&eClic sinistro&7 per cambiare la modalità di gioco del giocatore");
        Config.set("Command.EditPlayer.Gui.Gamemode.Survival", "&cSOPRAVVIVENZA");
        Config.set("Command.EditPlayer.Gui.Gamemode.Spectator", "&eSPETTATORE");
        Config.set("Command.EditPlayer.Gui.Gamemode.Creative", "&6CREATIVA");
        Config.set("Command.EditPlayer.Gui.Gamemode.Adventure", "&aAVVENTURA");

        Config.set("Command.EditPlayer.Gui.ClearInv.LeftClick", "&eClic sinistro&7 per svuotare l'inventario e l'armatura del giocatore");
        Config.set("Command.EditPlayer.Gui.ClearInv.Item-Name", "&6Svuota l'inventario");

        Config.set("Command.EditPlayer.Gui.Teleport.LeftClick", "&eClic sinistro&7 per teletrasportarti dal giocatore");
        Config.set("Command.EditPlayer.Gui.Teleport.Item-Name", "&6Teletrasportati da questo giocatore");

        Config.set("Command.EditPlayer.Gui.MoreSoon.Item-Name", "Altro in arrivo");

        Config.set("Command.EditPlayer.Gui.BackToPlayerList.Item-Name", "&cTorna alla lista dei giocatori");

        // Since 1.5.3: the new items of Edit Player
        Config.set("Command.EditPlayer.Gui.Gamemode.Lore", java.util.Arrays.asList("", "&7Clic sinistro: la modalità successiva", "&7Clic destro: quella precedente"));
        Config.set("Command.EditPlayer.Gui.Head.Lore", java.util.Arrays.asList("&7Mondo: &f%world%", "&7Modalità di gioco: &f%gamemode%", "&7Rango: &f%rank%"));
        Config.set("Command.EditPlayer.Gui.On", "&aattivo");
        Config.set("Command.EditPlayer.Gui.Off", "&cspento");
        Config.set("Command.EditPlayer.Gui.Toggle.Lore", java.util.Arrays.asList("", "&7Clicca per cambiarlo"));
        Config.set("Command.EditPlayer.Gui.Fly.Item-Name", "&eVolo: %value%");
        Config.set("Command.EditPlayer.Gui.Vanish.Item-Name", "&eVanish: %value%");
        Config.set("Command.EditPlayer.Gui.Walk-Speed.Item-Name", "&eVelocità a piedi: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Fly-Speed.Item-Name", "&eVelocità di volo: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Speed.Lore", java.util.Arrays.asList("", "&7Clic sinistro: &a+1&7, clic destro: &c-1", "&7Shift + clic: valore normale"));
        Config.set("Command.EditPlayer.Gui.Heal.Item-Name", "&eCura e sfama");
        Config.set("Command.EditPlayer.Gui.Heal.Lore", java.util.Arrays.asList("", "&7Clic sinistro: cura", "&7Clic destro: sfama"));
        Config.set("Command.EditPlayer.Gui.Teleport-Here.Item-Name", "&6Porta qui questo giocatore");
        Config.set("Command.EditPlayer.Gui.Teleport-Here.LeftClick", "&eClic sinistro&7 per teletrasportare il giocatore da te");
        Config.set("Command.EditPlayer.Gui.Rank.Item-Name", "&eRango dato a mano: &f%value%");
        Config.set("Command.EditPlayer.Gui.Rank.Lore", java.util.Arrays.asList("", "&7Clic sinistro: il rango successivo", "&7Clic destro: quella precedente", "&7(- : nessun rango a mano)"));
        Config.set("Command.EditPlayer.Gui.Rank.LuckPerms", java.util.Arrays.asList("", "&7I ranghi vengono da LuckPerms:", "&7cambia lì il gruppo del giocatore"));
        Config.set("Command.EditPlayer.Gui.Hider.Item-Name", "&eChi vede il giocatore: %value%");
        Config.set("Command.EditPlayer.Gui.Hider.Everyone", "&atutti");
        Config.set("Command.EditPlayer.Gui.Hider.Staff", "&estaff e VIP");
        Config.set("Command.EditPlayer.Gui.Hider.Nobody", "&cnessuno");
        Config.set("Command.EditPlayer.Gui.Hider.Lore", java.util.Arrays.asList("", "&7Clic: lo stato successivo"));
        Config.set("Command.EditPlayer.Changed", java.util.Arrays.asList("%prefix% &e%arg2% &8» %arg1%"));

        //

        Config.set("Command.No-Clip.Enable", java.util.Arrays.asList("%prefix% &7Il No clip è &aattivato"));

        Config.set("Command.No-Clip.Disable", java.util.Arrays.asList("%prefix% &7Il No clip è &cdisattivato"));

        Config.set("Command.NightVision", java.util.Arrays.asList("%prefix% &7Ora vedi di notte"));
        Config.set("Command.NightVision-Off", java.util.Arrays.asList("%prefix% &7Non vedi più di notte"));

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList("&cNon hai dato un nome a questo spawn: è stato scelto un nome automatico",
		            "§eSpawn impostato con il nome %spawnName%"));

        Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList("§eSpawn impostato con il nome %spawnName%"));

        Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList("&bLo spawn &e%spawn%&b è stato eliminato"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}