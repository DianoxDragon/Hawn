package fr.dianox.hawn.utility.config.configs.messages.it_it;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Italian messages (since 1.6), made from the English ones.
 */
public class ITConfigMMsg {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ITConfigMMsg() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/it_IT/Messages.yml");
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
         * Chat stuff
         */
        Config.set("ClearChat.No-Reason", "nessun motivo");
        Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {" &4&l* &cLa chat è stata pulita &7(motivo: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {" &4&l* &cLa chat è stata pulita da &6%player% &7(motivo: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7, la tua chat è stata pulita"}));
        Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7, la tua chat è stata pulita"}));
        Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"%prefix% &7La chat di &e%player%&7 è stata pulita"}));

        Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"%prefix% &cNon puoi scrivere: la chat è bloccata"}));
        Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cLa chat è stata bloccata da &6%player% &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cLa chat è stata bloccata da &6%player%&c per &6%minutes% minuti &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bLa chat è stata &asbloccata&b da &e%player% &3&m&l->-=-<-"}));

        Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo ping è &e%ping%&7 ms"}));
        Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7Il ping di &b%target%&7 è &e%ping%&7 ms"}));

        Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cNon puoi ancora scrivere: aspetta &6%DELAY%&c secondo/i tra due messaggi"}));
        Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7Il ritardo della chat è ora di &e%DELAY%&7 secondo/i", 
        		"          &7&oNon dimenticare di modificare questo valore nella configurazione", 
        		"          &7&oAltrimenti va perso al prossimo riavvio"}));
        Config.set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7Il ritardo della chat è stato &cdisattivato"}));

        Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eAnnuncio&8]&r %broadcast%"}));

        /*
         * Weather and Time
         */
        Config.set("Weather.Set.Sun.Enable", true);
        Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Pioggia e temporale sono finiti, &eviva il sole&7!"}));
        Config.set("Weather.Set.Rain.Enable", true);
        Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"%prefix% &7La &bpioggia&7 cade su questo mondo, &battento a non bagnarti&7!"}));
        Config.set("Weather.Set.Thunder.Enable", true);
        Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"%prefix% &6&k!!!!&7 &eIl temporale è un segno di disastri&7... &eAttento a non essere colpito da un fulmine &6&k!!!!"}));

        Config.set("Time.Set.Day.Enable", true);
        Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"%prefix% &7È &emattina&7, ora di andare a scuola"}));
        Config.set("Time.Set.Night.Enable", true);
        Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"%prefix% &f&lFuori è buio. Attenzione, la notte è buia e piena di terrori!"}));

        /*
         * FLY COMMANDS
         */
        Config.set("Fly.Enable.Enable", true);
        Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo volo è stato &aattivato"}));
        Config.set("Fly.Enable-Other.Enable", true);
        Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo volo è stato &aattivato&7 da &e%player%"}));
        Config.set("Fly.Enable-Other-Executor.Enable", true);
        Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il volo di &e%target%&7 è stato &aattivato"}));

        Config.set("Fly.Disable.Enable", true);
        Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo volo è stato &cdisattivato"}));
        Config.set("Fly.Disable-Other.Enable", true);
        Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo volo è stato &cdisattivato&7 da &e%player%"}));
        Config.set("Fly.Disable-Other-Executor.Enable", true);
        Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il volo di &e%target%&7 è stato &cdisattivato"}));

        /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
        Config.set("Gamemode.Self.Survival.Enable", true);
        Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &esopravvivenza"}));
        Config.set("Gamemode.Self.Creative.Enable", true);
        Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &ecreativa"}));
        Config.set("Gamemode.Self.Adventure.Enable", true);
        Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &eavventura"}));
        Config.set("Gamemode.Self.Spectator.Enable", true);
        Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &espettatore"}));

        Config.set("Gamemode.Other.Survival.Enable", true);
        Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &esopravvivenza&7, da &b%player%"}));
        Config.set("Gamemode.Other.Creative.Enable", true);
        Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &ecreativa&7, da &b%player%"}));
        Config.set("Gamemode.Other.Adventure.Enable", true);
        Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &eavventura&7, da &b%player%"}));
        Config.set("Gamemode.Other.Spectator.Enable", true);
        Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua modalità di gioco è ora &espettatore&7, da &b%player%"}));

        Config.set("Gamemode.Other-Sender.Survival.Enable", true);
        Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La modalità di gioco di &b%player%&7 è ora &esopravvivenza"}));
        Config.set("Gamemode.Other-Sender.Creative.Enable", true);
        Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La modalità di gioco di &b%player%&7 è ora &ecreativa"}));
        Config.set("Gamemode.Other-Sender.Adventure.Enable", true);
        Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La modalità di gioco di &b%player%&7 è ora &eavventura"}));
        Config.set("Gamemode.Other-Sender.Spectator.Enable", true);
        Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La modalità di gioco di &b%player%&7 è ora &espettatore"}));

        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cSei già in questa modalità di gioco"}));
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cIl giocatore %target% è già in questa modalità di gioco"}));

        /* -------------- *
			 * GOTOP COMMANDS *
			 * -------------- */
        Config.set("Gotop.Enable", true);
        Config.set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sei stato teletrasportato sul blocco più alto della tua posizione"}));

        /* ------------- *
			 * FEED COMMANDS *
			 * ------------- */
        Config.set("Feed.Self.Enable", true);
        Config.set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sei stato sfamato"}));

        Config.set("Feed.Other.Enable", true);
        Config.set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sei stato sfamato da &e%player%"}));
        Config.set("Feed.Other-Sender.Enable", true);
        Config.set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 è stato sfamato"}));

        /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
        Config.set("Heal.Self.Enable", true);
        Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sei stato curato"}));

        Config.set("Heal.Other.Enable", true);
        Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sei stato curato da &e%player%"}));
        Config.set("Heal.Other-Sender.Enable", true);
        Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 è stato curato"}));

        /* ------------------- *
			 * ENDERCHEST COMMANDS *
			 * ------------------- */

        Config.set("EnderChest.Self.Enable", true);
        Config.set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai aperto la tua cassa di Ender"}));

        Config.set("EnderChest.Other-Sender.Enable", true);
        Config.set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai aperto la cassa di Ender di &e%target%"}));

        /* --------------- *
			 * INVSEE COMMANDS *
			 * --------------- */

        Config.set("InvSee.Other-Sender.Enable", true);
        Config.set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai aperto l'inventario di &e%target%"}));

        /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
        Config.set("Scoreboard.Toggle.On.Enable", true);
        Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Lo scoreboard è stato &aattivato"}));
        Config.set("Scoreboard.Toggle.Off.Enable", true);
        Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Lo scoreboard è stato &cdisattivato"}));

        Config.set("Scoreboard.Changes.Enable", true);
        Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo scoreboard è ora &e%arg1%"}));
        Config.set("Scoreboard.Error-Changes.Enable", true);
        Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo scoreboard &6%arg1%&c non esiste"}));

        Config.set("Scoreboard.Keep-On.Enable", true);
        Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Ora terrai questo scoreboard"}));
        Config.set("Scoreboard.Keep-Off.Enable", true);
        Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Non terrai &cpiù&7 questo scoreboard"}));

        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cNon hai il permesso per nessuno scoreboard"}));

        /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
        Config.set("Warp.Tp.Self.Enable", true);
        Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sei stato teletrasportato al warp &e%warp%"}));
        Config.set("Warp.Tp.Self-Delay.Enable", true);
        Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%second%&c secondo/i&7 prima del teletrasporto"}));

        Config.set("Warp.Tp.Other.Enable", true);
        Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sei stato teletrasportato al warp &e%warp%&7 da &b%player%"}));
        Config.set("Warp.Tp.Other-Sender.Enable", true);
        Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai teletrasportato &b%target%&7 al warp&e %warp%"}));
        Config.set("Warp.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%second%&c secondo/i&7 prima che questo giocatore sia teletrasportato"}));

        Config.set("Warp.List.Enable", true);
        Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bLista dei warp:&e %warplist%"}));

        Config.set("Warp.No-Warp.Enable", true);
        Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, non c'è nessun warp"}));

        Config.set("Warp.Set.Warp-Set.Enable", true);
        Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il warp è stato creato con il nome &e%arg%"}));
        Config.set("Warp.Set.Warp-Already-Exist.Enable", true);
        Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cQuesto nome esiste già"}));

        Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
        Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cQuesto warp non esiste"}));
        Config.set("Warp.Del.Warp-Delete.Enable", true);
        Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il warp &e%warp%&7 è stato &celiminato"}));

        Config.set("Warp.Edit.Warp-Edited.Enable", true);
        Config.set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La nuova posizione del warp &e%warp% è stata impostata"}));

        /* ---------------- *
			 * SUICIDE COMMANDS *
			 * ---------------- */

        Config.set("Suicide.Self.Enable", true);
        Config.set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Ti sei suicidato"}));

        Config.set("Suicide.Broadcast.Enable", true);
        Config.set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% ha detto addio al mondo"}));

        /* --------------- *
			 * REPAIR COMMANDS *
			 * --------------- */

        Config.set("Repair.Repaired.Enable", true);
        Config.set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7L'oggetto &e%item% &7è stato riparato"}));

        Config.set("Repair.Can-t-Repair.Enable", true);
        Config.set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, non puoi riparare questo oggetto"}));

        /* ------------ *
			 * EXP COMMANDS *
			 * ------------ */

        Config.set("Exp.Add.Sender.Enable", true);
        Config.set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7sono stati aggiunti ai punti esperienza di &b%target%"}));
        Config.set("Exp.Add.Target.Enable", true);
        Config.set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7sono stati aggiunti ai tuoi punti esperienza"}));

        Config.set("Exp.Set.Sender.Enable", true);
        Config.set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7I punti esperienza di &b%target%&7 sono ora &e%number_exp%"}));
        Config.set("Exp.Set.Target.Enable", true);
        Config.set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7I tuoi punti esperienza sono ora &e%number_exp%"}));

        Config.set("Exp.Take.Sender.Enable", true);
        Config.set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 ha perso &e%number_exp%&7 punti esperienza"}));
        Config.set("Exp.Take.Target.Enable", true);
        Config.set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai perso &e%number_exp%&7 punti esperienza"}));
        Config.set("Exp.Take.Sender-Error.Enable", true);
        Config.set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cNon puoi togliere più di &6%target_exp_points%&c al giocatore &e%target%"}));

        Config.set("Exp.Clear.Sender.Enable", true);
        Config.set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 ha ora 0 punti esperienza"}));
        Config.set("Exp.Clear.Target.Enable", true);
        Config.set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7I tuoi punti esperienza sono stati azzerati"}));

        /* ------------------ *
			 * WORKBENCH COMMANDS *
			 * ------------------ */
        Config.set("WorkBench.Self.Enable", true);
        Config.set("WorkBench.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai aperto il tuo banco da lavoro"}));

        Config.set("WorkBench.Target.Enable", true);
        Config.set("WorkBench.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo banco da lavoro è stato aperto da &e%player%"}));
        Config.set("WorkBench.Sender.Enable", true);
        Config.set("WorkBench.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai aperto il banco da lavoro per &e%target%&7"}));

        /* ------------- *
			 * BURN COMMANDS *
			 * ------------- */
        Config.set("Burn.Target.Enable", true);
        Config.set("Burn.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Stai bruciando a causa di &e%player%&7 per &b%duration%&7 secondi"}));
        Config.set("Burn.Sender.Enable", true);
        Config.set("Burn.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Fai bruciare &e%target%&7 per &b%duration%&7 secondi"}));

        /* -------------- *
			 * SKULL COMMANDS *
			 * -------------- */
        Config.set("Skull.Self.Enable", true);
        Config.set("Skull.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai ottenuto la tua testa"}));

        Config.set("Skull.Sender.Enable", true);
        Config.set("Skull.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Hai ottenuto la testa di &e%target%"}));
        Config.set("Skull.Target.Enable", false);
        Config.set("Skull.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7 ha preso la tua testa"}));

        /*
         * Hat
         */
        Config.set("Hat.Self.Set.Enable", true);
        Config.set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Il nuovo cappello è stato messo"}));
        Config.set("Hat.Self.Removed.Enable", true);
        Config.set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Il cappello è stato tolto"}));

        Config.set("Hat.Other-Target.Set.Enable", true);
        Config.set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Hai ricevuto un nuovo cappello da &e%player%"}));
        Config.set("Hat.Other-Target.Removed.Enable", true);
        Config.set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Il tuo cappello è stato tolto da &e%player%"}));

        Config.set("Hat.Other-Sender.Set.Enable", true);
        Config.set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Hai messo un nuovo cappello a &e%target%"}));
        Config.set("Hat.Other-Sender.Removed.Enable", true);
        Config.set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Hai tolto il cappello a &e%target%"}));

        Config.set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
        Config.set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cMi dispiace, non puoi mettere un nuovo cappello"}));
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cMi dispiace, non puoi togliere il cappello: è vuoto"}));

        /*
         * GetPos
         */
        Config.set("GetPos.Enable", true);
        Config.set("GetPos.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% La posizione del giocatore è: &e%X% %Y% %Z%&7 nel mondo&b %world%"}));

        /*
         * Vanish stuff
         */
        Config.set("Vanish.Self.Enable", true);
        Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il vanish è stato &aattivato"}));
        Config.set("Vanish.Self-Disabled.Enable", true);
        Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il vanish è stato &cdisattivato"}));
        /*Config.set("Vanish.Self-Still.Enable", true);
        Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bIl vanish è ancora attivo"}));*/
        Config.set("Vanish.Other-Target.Enable", true);
        Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il vanish è stato &aattivato&7 da&e %player%"}));
        Config.set("Vanish.Other-Target-Disabled.Enable", true);
        Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il vanish è stato &cdisattivato&7 da&e %player%"}));
        Config.set("Vanish.Other-Sender.Enable", true);
        Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il vanish di &b%target%&7 è stato &aattivato&7 da&e %player%"}));
        Config.set("Vanish.Other-Sender-Disabled.Enable", true);
        Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il vanish di &b%target%&7 è stato &cdisattivato&7 da&e %player%"}));
        Config.set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aSei invisibile"}));

        /*
         * Clear inv stuff
         */
        Config.set("ClearInv.Self.Enable", true);
        Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo inventario è stato svuotato"}));
        Config.set("ClearInv.Other-Target.Enable", true);
        Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo inventario è stato svuotato da &e%player%"}));
        Config.set("ClearInv.Other-Sender.Enable", true);
        Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7L'inventario di &e%target%&7 è stato svuotato"}));

        /*
         * Spawn stuff
         */
        Config.set("Spawn.Tp.Self-Delay.Enable", true);
        Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%second%&c secondo/i&7 prima del teletrasporto"}));
        Config.set("Spawn.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%second%&c secondo/i&7 prima che questo giocatore sia teletrasportato"}));

        Config.set("Spawn.List.Enable", true);
        Config.set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bLista degli spawn:&e %spawnlist%"}));

        Config.set("Spawn.No-Spawn.Enable", true);
        Config.set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, non c'è nessuno spawn"}));

        /*
         * Warning stuff
         */
        Config.set("Warning", java.util.Arrays.asList(new String[] {
        		"<--center--> &4&m>-----------------------<", 
        		"", 
        		"%broadcast%",
        		"",
        		"<--center--> &4&m>-----------------------<"}));

        /*
         * 
         * 
         * EVENTS
         * 
         * 
         */

        Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"%prefix% &7Oh no, hai provato a raggiungere il vuoto"}));
        Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eInsulti&8] &b%player% &7ha detto &e%message%"}));
        Config.set("Anti-Spam.Repeat", java.util.Arrays.asList(new String[] {"%prefix% &cNon inviare di nuovo lo stesso messaggio"}));
        Config.set("Anti-Spam.Caps", java.util.Arrays.asList(new String[] {"%prefix% &cTroppe lettere maiuscole nel tuo messaggio"}));
        Config.set("Anti-Advertising.Blocked", java.util.Arrays.asList(new String[] {"%prefix% &cNiente pubblicità in chat: gli indirizzi di altri server non sono permessi"}));
        Config.set("Anti-Advertising.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&ePub&8] &b%player% &7ha provato a inviare &e%message%"}));

        Config.set("LaunchPad.Cant-Use-Cooldown.Enable", true);
        Config.set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Mi dispiace, per ora non puoi usare il trampolino"}));

        Config.set("Cancel-Tp.Warp.Enable", true);
        Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teletrasporto &cannullato"}));
        Config.set("Cancel-Tp.Spawn.Enable", true);
        Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teletrasporto &cannullato"}));

        Config.set("Custom-Join-Item.Error.FunGun.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.FunGun.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%timedelayfunguncji% &c&lsecondo/i&7!"}));
        // The grappling hook (since 1.5.5)
        Config.set("Custom-Join-Item.Error.Grappling.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%timedelaygrapplingcji% &c&lsecondo/i&7!"}));
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cIl rampino è troppo lontano!"}));
        // The pearl mount (since 1.5.6)
        Config.set("Custom-Join-Item.Error.PearlMount.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.PearlMount.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%timedelaypearlmountcji% &c&lsecondo/i&7!"}));
        Config.set("Custom-Join-Item.Error.PearlMount.Height.Enable", true);
        Config.set("Custom-Join-Item.Error.PearlMount.Height.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa perla cavalcabile funziona solo tra le altezze &e%arg1% &ce &e%arg2%"}));

        /*
         * 
         * 
         * PLAYER OPTION
         * 
         * 
         */

        Config.set("PlayerOption.Help.Enable", true);
        Config.set("PlayerOption.Help.Messages", java.util.Arrays.asList(new String[] {
        		"&8//&7&m---------------&r&8\\\\ &3[&bPlayerOption&3] &8//&7&m---------------&r&8\\\\",
        		"",
        		"     &l>> &e&o&lAiuto delle opzioni del giocatore",
        		"",
        		" &8>> &7/option fly - &eAttiva il volo",
        		" &8>> &7/option doublejump - &eAttiva o disattiva il doppio salto",
        		" &8>> &7/option speed - &eAttiva o disattiva la velocità",
        		" &8>> &7/option flyspeed - &eAttiva o disattiva la velocità di volo",
        		" &8>> &7/option jumpboost - &eAttiva o disattiva il salto potenziato",
        		" &8>> &7/option autobc - &eMostra o nasconde gli annunci automatici",
        		" &8>> &7/option pv - &eMostra o nasconde i giocatori",
        		"",
        		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));

        Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo doppio salto è stato &aattivato"}));
        Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo doppio salto è stato &cdisattivato"}));

        Config.set("PlayerOption.Speed.Enable.Enable", true);
        Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua velocità è stata &aattivata"}));
        Config.set("PlayerOption.Speed.Disable.Enable", true);
        Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua velocità è stata &cdisattivata"}));
        Config.set("PlayerOption.Speed.Set.Enable", true);
        Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua velocità è ora &e%arg1%"}));

        Config.set("PlayerOption.FlySpeed.Enable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua velocità di volo è stata &aattivata"}));
        Config.set("PlayerOption.FlySpeed.Disable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua velocità di volo è stata &cdisattivata"}));
        Config.set("PlayerOption.FlySpeed.Set.Enable", true);
        Config.set("PlayerOption.FlySpeed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La tua velocità di volo è ora &e%arg1%"}));

        Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo salto potenziato è stato &aattivato"}));
        Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Il tuo salto potenziato è stato &cdisattivato"}));

        Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Gli annunci automatici sono ora &avisibili"}));
        Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Gli annunci automatici sono ora &cnascosti"}));

        Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Puf! I giocatori sono spariti"}));
        Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7I giocatori sono di nuovo visibili"}));
        // Since 1.4.9: the third state of the player hider
        Config.set("PlayerOption.PlayerVisibility.STAFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.STAFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Ora sono visibili solo lo staff e i VIP"}));
        // Since 1.5: the menus
        Config.set("Menus.Not-Found", java.util.Arrays.asList("%prefix% &cIl menu &e%arg% &cnon esiste"));
        Config.set("Menus.Not-In-This-World", java.util.Arrays.asList("%prefix% &cQuesto menu non può essere aperto in questo mondo"));

        Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cIl doppio salto è disattivato in &6Cosmetics-Fun/DoubleJump.yml"}));
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cIl doppio salto non è attivo in questo mondo"}));

        // Since 1.5.7: /speed <n> and /flyspeed <n> while the option is off, with the command that turns it on
        Config.set("PlayerOption.Error.Turn-On-First.Enable", true);
        Config.set("PlayerOption.Error.Turn-On-First.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cQuesta opzione è spenta: attivala prima con &e%arg1%"}));
        //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
        //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cQuesta opzione è già impostata o è la stessa"}));
        Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
        Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Aspetta &4&l%timedelaypvcji% &c&lsecondo/i&7!"}));

        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe opzioni del giocatore non sono attive in questo mondo"}));
        /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lAspetta &4%timedelaypvcommands% &c&lsecondi!"}));*/            

        /*
         * 
         * OTHER STUFF
         * 
         */

        Config.set("Error.No-Permissions.Enable", true);
        Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, non hai il permesso: %noperm%"}));
        Config.set("Error.No-Spawn.Enable", true);
        Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo spawn non esiste"}));
        Config.set("Error.Change-Me.Enable", true);
        Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDevi cambiare lo spawn/warp/ecc. in &6%arg1%&c nel file &e%arg2%"}));
        Config.set("Error.No-Players.Enable", true);
        Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cIl giocatore non è online o non esiste"}));
        Config.set("Error.No-Page-Found.Enable", true);
        Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa pagina non è stata trovata"}));
        Config.set("Error.No-Category.Enable", true);
        Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa categoria non esiste"}));
        Config.set("Error.Use-Number.Enable", true);
        Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cIndica un numero"}));
        Config.set("Error.Command-Disable.Enable", true);
        Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, questo comando è disattivato"}));
        // Since 1.4.5: an error in a command (written in the console), instead of "Si è verificato un errore interno"
        Config.set("Error.Command-Failed.Enable", true);
        Config.set("Error.Command-Failed.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cQuesto comando non è riuscito: l'errore è scritto nella console, avvisa un admin"}));
        Config.set("Error.Command-Wrong-Arguments.Enable", true);
        Config.set("Error.Command-Wrong-Arguments.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cArgomenti sbagliati. Uso: &e%usage%"}));
        Config.set("Error.Argument-Missing.Enable", true);
        Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, mancano uno o due argomenti"}));
        Config.set("Error.Not-A-Player.Enable", true);
        Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cNon sei un giocatore"}));
        Config.set("Protection.Anti-Place", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, non puoi piazzare blocchi qui!"}));
        // The language menu (since 1.6)
        Config.set("Language.Changed.Enable", true);
        Config.set("Language.Changed.Messages", java.util.Arrays.asList("%prefix% &7La tua lingua ora è &e%arg1%"));
        Config.set("Language.Client.Enable", true);
        Config.set("Language.Client.Messages", java.util.Arrays.asList("%prefix% &7Ora ricevi i messaggi nella lingua del tuo gioco &8(&e%arg1%&8)"));
        Config.set("Protection.Anti-Break", java.util.Arrays.asList(new String[] {"%prefix% &cMi dispiace, non puoi rompere blocchi qui!"}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}