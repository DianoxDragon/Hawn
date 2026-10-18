package fr.dianox.hawn.utility.config.configs.messages.de_de;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The German messages (Messages/de_DE), since Hawn 1.4.6: the texts of ConfigMAdmin translated.
 */
public class DEAdminAMConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public DEAdminAMConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/de_DE/Admin.yml");
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
        Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList("&cDu bist kein Spieler"));
        Config.set("Error.Command.Hawn", java.util.Arrays.asList("&cFehler, versuche /hawn"));
        Config.set("Error.Command.Delspawn", java.util.Arrays.asList("&c/hawn delspawn <spawn>"));
        Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList("&cDieser Name existiert bereits"));
        Config.set("Error.Argument-Missing", java.util.Arrays.asList("&cEs fehlen ein oder mehrere Argumente."));
        Config.set("Error.No-Spawn", java.util.Arrays.asList("&cDieser Spawn existiert nicht"));

	        /* ------------ *
	         * HELP COMMAND *
	         * ------------ */

	        Config.set("Command.Help.Hawn-SpawnManager", "Spawns verwalten");
	        Config.set("Command.Help.Hawn-Reload", "Konfigurationsdateien neu laden");
	        Config.set("Command.Help.Hawn-Version", "Version des Plugins anzeigen");
	        Config.set("Command.Help.Hawn-Tps", "TPS des Servers anzeigen");
	        Config.set("Command.Help.Hawn-Info", "Infos zum Server anzeigen");
	        Config.set("Command.Help.Hawn-Build", "Schutzfunktionen vorübergehend umgehen");
	        Config.set("Command.Help.Hawn-Hooks", "Prüfen, ob die unterstützten Plugins aktiv sind");
	        Config.set("Command.Help.Hawn-Urgent", "Den Notfallmodus verwenden");
	        Config.set("Command.Help.Hawn-Maintenance", "Den Wartungsmodus verwenden");
	        Config.set("Command.Help.Hawn-Donors", "Die Spender des Plugins anzeigen");
	        Config.set("Command.Help.Hawn-About", "Ein paar Informationen anzeigen");
	        Config.set("Command.Help.Hawn-Parse", "Anzeigen, was ein Platzhalter ergibt");
	        Config.set("Command.Help.Hawn-NightVision", "Im Dunkeln sehen");
	        Config.set("Command.Help.Hawn-NoClip", "Im Kreativmodus durch Blöcke gehen");
	        Config.set("Command.Help.Hawn-SlotView", "Die Slot-Nummer beim Anklicken anzeigen");
	        Config.set("Command.Help.Hawn-EditPlayer", "Einen Spieler bearbeiten");
	        Config.set("Command.Help.Hawn-Setup", "Hawn zum ersten Mal einrichten");
	        Config.set("Command.Help.Hawn-Debug", "Die Fehler der Konfigurationsdateien und die versteckten Fehler");
	        Config.set("Command.Help.Hawn-Rank", "Den Rang eines Spielers geben, entfernen oder ansehen (Ranks.yml)");
	        Config.set("Command.Help.Broadcast", "Eine Nachricht an alle senden");
	        Config.set("Command.Help.Warning", "Eine Warnung an alle senden");
	        Config.set("Command.Help.Broadcast-Title", "Einen Titel an alle senden");
	        Config.set("Command.Help.Broadcast-ActionBar", "Eine Actionbar-Nachricht an alle senden");
	        Config.set("Command.Help.AdminPanel", "Das Admin-Panel öffnen");
	        Config.set("Command.Help.CheckAccount", "Einen Spieler prüfen");
	        Config.set("Command.Help.ClearInv", "Das Inventar eines Spielers leeren");
	        Config.set("Command.Help.InvSee", "Das Inventar eines Spielers ansehen");
	        Config.set("Command.Help.Ip", "Die IP eines Spielers anzeigen");
	        Config.set("Command.Help.List", "Die Liste der Spieler in der Lobby");
	        Config.set("Command.Help.Spawn", "Zum Spawn gehen");
	        Config.set("Command.Help.Spawn-Tp", "Einen Spieler zu einem Spawn teleportieren");
	        Config.set("Command.Help.DelSpawn", "Einen Spawn entfernen");
	        Config.set("Command.Help.SpawnList", "Die Liste der Spawns anzeigen");
	        Config.set("Command.Help.SetSpawn", "Einen Spawn setzen");
	        Config.set("Command.Help.SetWarp", "Einen Warp setzen");
	        Config.set("Command.Help.Warp", "Zu einem Warp gehen");
	        Config.set("Command.Help.WarpList", "Die Liste der Warps anzeigen");
	        Config.set("Command.Help.DelWarp", "Einen Warp entfernen");
	        Config.set("Command.Help.EditWarp", "Die Position eines Warps ändern");
	        Config.set("Command.Help.Sun", "Klares Wetter");
	        Config.set("Command.Help.Rain", "Regen in der Welt");
	        Config.set("Command.Help.Thunder", "Gewitter, wenn du schlechtes Wetter magst");
	        Config.set("Command.Help.Day", "Tag machen");
	        Config.set("Command.Help.Night", "Nacht machen");
	        Config.set("Command.Help.Fly", "Den Flugmodus umschalten");
	        Config.set("Command.Help.FlySpeed", "Die Fluggeschwindigkeit eines Spielers ändern");
	        Config.set("Command.Help.Speed", "Die Geschwindigkeit ändern oder ein-/ausschalten");
	        Config.set("Command.Help.Heal", "Einen Spieler heilen");
	        Config.set("Command.Help.Feed", "Einen Spieler sättigen");
	        Config.set("Command.Help.Ping", "Den Ping eines Spielers anzeigen");
	        Config.set("Command.Help.Vanish", "Einen Spieler unsichtbar machen");
	        Config.set("Command.Help.Gamemode", "Den Spielmodus eines Spielers ändern");
	        Config.set("Command.Help.Burn", "Einen Spieler anzünden");
	        Config.set("Command.Help.Cleargrounditems", "Gegenstände auf dem Boden entfernen");
	        Config.set("Command.Help.Clearmobs", "Mobs entfernen");
	        Config.set("Command.Help.EnderChest", "Die Endertruhe öffnen");
	        Config.set("Command.Help.Exp", "Die Erfahrung eines Spielers ändern");
	        Config.set("Command.Help.Getpos", "Die Position eines Spielers anzeigen");
	        Config.set("Command.Help.Hat", "Einen Hut aufsetzen");
	        Config.set("Command.Help.Kickall", "Alle Spieler kicken");
	        Config.set("Command.Help.Repair", "Einen Gegenstand reparieren");
	        Config.set("Command.Help.Skull", "Den Kopf eines Spielers erhalten");
	        Config.set("Command.Help.Suicide", "Dich selbst töten");
	        Config.set("Command.Help.Workbench", "Eine Werkbank öffnen");
	        Config.set("Command.Help.ClearChat", "Die Hilfe von /clearchat anzeigen");
	        Config.set("Command.Help.DelayChat", "Eine Verzögerung im Chat setzen");
	        Config.set("Command.Help.Gmute", "Den Chat stummschalten");
	        Config.set("Command.Help.Help", "Die eigene Hilfe anzeigen, falls aktiviert");
	        Config.set("Command.Help.Gotop", "Auf den höchsten Block deiner Position gehen");
	        Config.set("Command.Help.Emoji", "Das Emoji-Menü öffnen");
	        Config.set("Command.Help.Scoreboard", "Das Scoreboard ein- oder ausschalten");
	        Config.set("Command.Help.Scoreboard-Set", "Das aktuelle Scoreboard ändern");
	        Config.set("Command.Help.Scoreboard-Keep", "Das Scoreboard zwischen Servern behalten");
	        Config.set("Command.Help.Scoreboard-List", "Alle Scoreboards anzeigen");
	        Config.set("Command.Help.Option", "Die Spieleroptionen");
	        Config.set("Command.Help.Hworld", "Die Welten verwalten");

        /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
        Config.set("Command.Server-Info.General", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer-Informationen (Alles)",
		            "        &3erstellt von Dianox",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lJava-Version&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lHawn-Updates&r&8:&r %checkupdatehawn%",
		            "&7&lServer-Version&r&8:&r %serverversion%",
		            "",
		            "  &8→ &6&lArbeitsspeicher",
		            "&7&lMaximaler Speicher &o(Java)&r&8:&r %maxmemory%&8MB",
		            "&a&lFrei&7&l/&c&lGesamt&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "  &8→ &6&lCPU (Prozessor)",
		            "&7&lDurchschnittliche Last&8:&r %averagecpuload%&8%",
		            "&7&lAktuelle Last&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "  &8→ &6&lSpeicherplatz",
		            "&7&lGesamter Speicherplatz&r&8:&r %totalspace%&8MB",
		            "&a&lFrei&7&l/&c&lGesamt&7&l nutzbar&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Memory", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer-Informationen (Arbeitsspeicher)",
		            "",
		            "  &8→ &6&lArbeitsspeicher",
		            "&7&lMaximaler Speicher &o(Java)&r&8:&r %maxmemory%&8MB",
		            "&a&lFrei&7&l/&c&lGesamt&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.CPU", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer-Informationen (CPU)",
		            "",
		            "  &8→ &6&lCPU (Prozessor)",
		            "&7&lDurchschnittliche Last&8:&r %averagecpuload%&8%",
		            "&7&lAktuelle Last&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Disk", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer-Informationen (Speicherplatz)",
		            "",
		            "  &8→ &6&lSpeicherplatz",
		            "&7&lGesamter Speicherplatz&r&8:&r %totalspace%&8MB",
		            "&a&lFrei&7&l/&c&lGesamt&7&l nutzbar&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Server", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer-Informationen (Server)",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lJava-Version&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lHawn-Updates&r&8:&r %checkupdatehawn%",
		            "&7&lServer-Version&r&8:&r %serverversion%",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Tps", java.util.Arrays.asList("  &8→ &6&lTps&8: &r%tps%"));

        Config.set("Command.Version", java.util.Arrays.asList("  &8→ &6&lHawn-Version (erstellt von Dianox)&8: &r%gethawnversion%"));

        // Since 1.4.8: /hawn debug
        Config.set("Debug.Header", java.util.Arrays.asList("&8&m-----&r &6Hawn debug &8&m-----"));
        Config.set("Debug.No-Config-Error", java.util.Arrays.asList("&aKein Fehler in den Konfigurationsdateien."));
        Config.set("Debug.Config-Errors", java.util.Arrays.asList("&e%arg 1% &7Fehler in den Konfigurationsdateien:"));
        Config.set("Debug.Auto", java.util.Arrays.asList("&e%arg 1% &7Option(en) auf AUTO, einem anderen Plugin überlassen:"));
        Config.set("Debug.No-Hidden-Error", java.util.Arrays.asList("&aKein versteckter Fehler seit dem Start."));
        Config.set("Debug.Hidden-Errors", java.util.Arrays.asList("&e%arg 1% &7Fehler, die Hawn seit dem Start übergangen hat:"));
        Config.set("Debug.Tip", java.util.Arrays.asList("&7Plugin.Debug: true in general.yml schreibt sie mit Details in die Konsole."));
        // Since 1.4.8: /hawn rank
        Config.set("Ranks.Usage", java.util.Arrays.asList("%prefix% &7/hawn rank set <Spieler> <Rang> &8| &7remove <Spieler> &8| &7info <Spieler> &8| &7list"));
        Config.set("Ranks.Off", java.util.Arrays.asList("%prefix% &cDie Ränge sind ausgeschaltet (Ranks.yml, Ranks.Enable: false)"));
        Config.set("Ranks.Unknown-Rank", java.util.Arrays.asList("%prefix% &cDer Rang &e%arg 1% &cexistiert nicht oder ist aus. Ränge: &7%arg 2%"));
        Config.set("Ranks.LuckPerms-Source", java.util.Arrays.asList("%prefix% &7Die Ränge kommen von LuckPerms (Ranks.Source): ändere die Gruppe von &e%arg 1% &7in LuckPerms (&e/lp user %arg 1% parent set <Gruppe>&7)"));
        Config.set("Ranks.Set", java.util.Arrays.asList("%prefix% &e%arg 1% &7hat jetzt den Rang &e%arg 2%"));
        Config.set("Ranks.Removed", java.util.Arrays.asList("%prefix% &e%arg 1% &7hat keinen von Hand gegebenen Rang mehr"));
        Config.set("Ranks.No-Rank-By-Hand", java.util.Arrays.asList("%prefix% &e%arg 1% &chat keinen von Hand gegebenen Rang (die Rechte oder LuckPerms geben ihn)"));
        Config.set("Ranks.Info", java.util.Arrays.asList("%prefix% &e%arg 1%&7: Rang &e%arg 2% &7(%arg 3%) &8| &r%arg 4%"));
        Config.set("Ranks.Info-Offline", java.util.Arrays.asList("%prefix% &e%arg 1% &7ist offline. Von Hand gegebener Rang: &e%arg 2%"));
        Config.set("Ranks.List-Header", java.util.Arrays.asList("%prefix% &e%arg 1% &7Rang/Ränge, der erste oben in der Tabliste:"));
        Config.set("Ranks.List-Line", java.util.Arrays.asList("&8- &e%arg 1% &7(Priorität %arg 2%) &r%arg 3% &8| &7online: &f%arg 4%"));
        Config.set("Ranks.Source.HAND", "von Hand gegeben");
        Config.set("Ranks.Source.PERMISSION", "durch ein Recht");
        Config.set("Ranks.Source.LUCKPERMS", "von LuckPerms");
        Config.set("Ranks.Source.DEFAULT", "der Standardrang");
        Config.set("Ranks.Source.NONE", "keiner");
        Config.set("TPS.Check.15", java.util.Arrays.asList("&cDeine TPS liegen unter %arg 1%, tu etwas für die Stabilität deiner Lobby"));
        Config.set("TPS.Check.5", java.util.Arrays.asList("&cDeine TPS liegen unter %arg 1%, dein Server könnte abstürzen: benutze /stop, um Probleme zu vermeiden.", "&cACHTUNG: KRITISCHER ZUSTAND DES SERVERS"));

        /* --------------- *
         * RELOAD COMMANDS *
         * --------------- */
        Config.set("Command.Reload", java.util.Arrays.asList("&aKonfiguration neu geladen"));

        Config.set("Command.Build-Bypass.On", java.util.Arrays.asList("&bDu kannst jetzt alle Bau-Einschränkungen umgehen"));
        Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList("&cDu kannst die Bau-Einschränkungen nicht mehr umgehen"));


        /*
         * Vanish
         */
        Config.set("Vanish.Vanish-On.Enable", true);
        Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList("&7[ %player% ist jetzt unsichtbar ]"));

        Config.set("Vanish.Vanish-Off.Enable", true);
        Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList("&7[ %player% ist nicht mehr unsichtbar ]"));
        Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList("&7[ %target% wurde von %player% unsichtbar gemacht ]"));
        Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList("&7[ %target% wurde von %player% wieder sichtbar gemacht ]"));

        Config.set("Maintenance.On", java.util.Arrays.asList("%prefix% &7Du hast die Wartung &aaktiviert"));
        Config.set("Maintenance.Off", java.util.Arrays.asList("%prefix% &7Du hast die Wartung &cdeaktiviert"));
        Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(" &4* &cDie Wartung ist &eaktiv&4 *"));
        Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(" &4* &cDie Wartung ist &ebeendet&4 *"));

        Config.set("Urgent-mode.On", java.util.Arrays.asList("%prefix% &7Du hast den Notfallmodus &aaktiviert"));
        Config.set("Urgent-mode.Off", java.util.Arrays.asList("%prefix% &7Du hast den Notfallmodus &cdeaktiviert"));
        Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(" &4* &cDer Notfallmodus ist &eaktiv&4 *"));
        Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(" &4* &cDer Notfallmodus ist &ebeendet&4 *"));
        Config.set("Urgent-mode.Zip", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cEine Sicherung von Hawn wurde erstellt"));
        Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cFehler: der Notfallmodus kann nur in der Konsole beendet werden"));
        Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDu kannst diesen Befehl nicht benutzen"));
        Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cEine Änderung von %player% im Admin-Panel wurde erkannt",
		            "%arg1% in der Datei %arg2%"));
        Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cAlle Plugins wurden deaktiviert"));
        Config.set("Urgent-mode.Ops-Removed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c%arg1% Operator(en) bis zum Ende des Notfallmodus entfernt"));
        Config.set("Urgent-mode.Ops-Restored", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7%arg1% Operator(en) zurückgegeben"));
        Config.set("Urgent-mode.Lockdown-Command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDer Server ist im Notfallmodus: Befehle sind deaktiviert"));
        Config.set("Urgent-mode.Lockdown-Chat", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDer Server ist im Notfallmodus: der Chat ist deaktiviert"));
        Config.set("Urgent-mode.Zip-Failed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDie Sicherung ist fehlgeschlagen, siehe Konsole"));
        Config.set("Urgent-mode.Console-Only", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDer Notfallmodus kann nur in der Konsole gestartet werden"));
        Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7Alle Plugins wurden &aaktiviert",
		            "&eBitte starte den Server neu, um Probleme zu vermeiden"));

        Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList("%prefix% &e%player% wollte diesen Befehl ausführen: &b%arg1%"));

        Config.set("Command.SlotView.On", java.util.Arrays.asList("%prefix% &7Du &asiehst&7 jetzt die Nummer jedes Slots, den du in einem Menü oder Inventar anklickst"));
        Config.set("Command.SlotView.Off", java.util.Arrays.asList("%prefix% &7SlotView ist &cdeaktiviert"));

        Config.set("Command.IP", java.util.Arrays.asList("%prefix% &7Die IP des Spielers ist: &e%getplayerip%"));

        Config.set("Command.Kickall", java.util.Arrays.asList("%prefix% &7Alle Spieler wurden gekickt"));

        Config.set("Command.ClearGroundItems", java.util.Arrays.asList("%prefix% &7Alle Gegenstände wurden entfernt"));

        Config.set("Command.ClearMobs", java.util.Arrays.asList("%prefix% &7Alle Mobs wurden entfernt"));

        Config.set("Command.CheckAccount", java.util.Arrays.asList("",
		            "  &8→ &r&lSpieler-Infos zu&8:&r &b%target%",
		            "&7&lLetzter Beitritt&r&8:&e %hawn_player_join_date%",
		            "&7&lErster Beitritt&r&8:&e %hawn_player_first_join_date%",
		            "&7&lIp&r&8:&e %player_ip%",
		            "",
		            "  &8→ &r&lOptionen&8:&r &8(&aGrün &8=&7 an / &cRot&8 = &7aus&8)",
		            "&7&lSpieler sichtbar: %pv_point%",
		            "&7&lGeschwindigkeit: %ps_point% &7(&e%ps_number%&7)",
		            "&7&lFliegen: %pof_point%",
		            "&7&lDoppelsprung: %dj_point%",
		            "&7&lAnkündigungen: %ab_point%",
		            "&7&lUnsichtbar: %v_point%",
		            "&7&lScoreboard behalten: %ksb_point% &8(&e%scorename%&8)",
		            "&7&lSprungkraft: %jb_point%",
		            "",
		            "&7&lSpielmodus: &e%gm_number%",
		            ""));

        // List

        Config.set("Command.List.Part-One", java.util.Arrays.asList("§8//§7§m---------------§r§8\\\\ §3[§bList§3] §8//§7§m---------------§r§8\\\\",
		            " ",
		            "  &8→ &6&lSeite %number%",
		            " "));

        Config.set("Command.List.Part-Two", java.util.Arrays.asList("§8\\\\§7§m---------------§r§8// §3[§bList§3] §8\\\\§7§m---------------§r§8//"));

        Config.set("Command.List.Gui.Other.Page.Next", "&bWeiter");
        Config.set("Command.List.Gui.Other.Page.Back", "&bZurück");
        Config.set("Command.List.Gui.Other.Back.PanelAdmin", "&c&lZurück zum Admin-Panel");
        Config.set("Command.List.Gui.Player.Survival", "&cÜBERLEBEN");
        Config.set("Command.List.Gui.Player.Spectator", "&eZUSCHAUER");
        Config.set("Command.List.Gui.Player.Creative", "&6KREATIV");
        Config.set("Command.List.Gui.Player.Adventure", "&aABENTEUER");
        Config.set("Command.List.Gui.Player.Gamemode", "&7Spielmodus: ");
        Config.set("Command.List.Gui.Player.World", "&7Welt: &e");
        Config.set("Command.List.Gui.Player.LeftClick", "&eLinksklick&7, um den Spieler zu bearbeiten");

        // EDIT PLAYER

        Config.set("Command.EditPlayer.Gui.Gamemode.LeftClick", "&eLinksklick&7, um den Spielmodus zu ändern");
        Config.set("Command.EditPlayer.Gui.Gamemode.Survival", "&cÜBERLEBEN");
        Config.set("Command.EditPlayer.Gui.Gamemode.Spectator", "&eZUSCHAUER");
        Config.set("Command.EditPlayer.Gui.Gamemode.Creative", "&6KREATIV");
        Config.set("Command.EditPlayer.Gui.Gamemode.Adventure", "&aABENTEUER");

        Config.set("Command.EditPlayer.Gui.ClearInv.LeftClick", "&eLinksklick&7, um Inventar und Rüstung zu leeren");
        Config.set("Command.EditPlayer.Gui.ClearInv.Item-Name", "&6Inventar leeren");

        Config.set("Command.EditPlayer.Gui.Teleport.LeftClick", "&eLinksklick&7, um dich zum Spieler zu teleportieren");
        Config.set("Command.EditPlayer.Gui.Teleport.Item-Name", "&6Zu diesem Spieler teleportieren");

        Config.set("Command.EditPlayer.Gui.MoreSoon.Item-Name", "Bald mehr");

        Config.set("Command.EditPlayer.Gui.BackToPlayerList.Item-Name", "&cZurück zur Spielerliste");

        //

        Config.set("Command.No-Clip.Enable", java.util.Arrays.asList("%prefix% &7No-Clip ist &aaktiviert"));

        Config.set("Command.No-Clip.Disable", java.util.Arrays.asList("%prefix% &7No-Clip ist &cdeaktiviert"));

        Config.set("Command.NightVision", java.util.Arrays.asList("%prefix% &7Du kannst jetzt im Dunkeln sehen"));
        Config.set("Command.NightVision-Off", java.util.Arrays.asList("%prefix% &7Du siehst nicht mehr im Dunkeln"));

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList("&cDu hast diesem Spawn keinen Namen gegeben, ein Name wurde automatisch gewählt",
		            "§eSpawn unter dem Namen %spawnName% gesetzt"));

        Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList("§eSpawn unter dem Namen %spawnName% gesetzt"));

        Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList("&bDer Spawn &e%spawn%&b wurde gelöscht"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}