package fr.dianox.hawn.utility.config.configs.messages.nl_nl;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Dutch messages (Messages/nl_NL), since Hawn 1.5.4: the texts of ConfigMAdmin translated.
 */
public class NLAdminAMConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public NLAdminAMConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/nl_NL/Admin.yml");
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
        Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList("&cJe bent geen speler"));
        Config.set("Error.Command.Hawn", java.util.Arrays.asList("&cFout, probeer /hawn"));
        Config.set("Error.Command.Delspawn", java.util.Arrays.asList("&c/hawn delspawn <spawn>"));
        Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList("&cDeze naam bestaat al"));
        Config.set("Error.Argument-Missing", java.util.Arrays.asList("&cEr ontbreken een of meer argumenten."));
        Config.set("Error.No-Spawn", java.util.Arrays.asList("&cDeze spawn bestaat niet"));

	        /* ------------ *
	         * HELP COMMAND *
	         * ------------ */

	        Config.set("Command.Help.Hawn-SpawnManager", "De spawns beheren");
	        Config.set("Command.Help.Hawn-Reload", "De configuratiebestanden herladen");
	        Config.set("Command.Help.Hawn-Version", "De versie van de plugin tonen");
	        Config.set("Command.Help.Hawn-Tps", "De TPS van de server tonen");
	        Config.set("Command.Help.Hawn-Info", "Informatie over de server tonen");
	        Config.set("Command.Help.Hawn-Build", "De beveiligingen even omzeilen");
	        Config.set("Command.Help.Hawn-Hooks", "Nagaan of de ondersteunde plugins aan staan");
	        Config.set("Command.Help.Hawn-Urgent", "De noodmodus gebruiken");
	        Config.set("Command.Help.Hawn-Maintenance", "De onderhoudsmodus gebruiken");
	        Config.set("Command.Help.Hawn-Donors", "De donateurs van de plugin tonen");
	        Config.set("Command.Help.Hawn-About", "Wat informatie tonen");
	        Config.set("Command.Help.Hawn-Parse", "Tonen wat een placeholder geeft");
	        Config.set("Command.Help.Hawn-NightVision", "In het donker zien");
	        Config.set("Command.Help.Hawn-NoClip", "In creatief door blokken lopen");
	        Config.set("Command.Help.Hawn-SlotView", "Het nummer van een slot tonen bij een klik");
	        Config.set("Command.Help.Hawn-EditPlayer", "Een speler bewerken");
	        Config.set("Command.Help.Hawn-Setup", "Hawn voor de eerste keer instellen");
	        Config.set("Command.Help.Hawn-Debug", "De fouten van de configuratiebestanden en de verborgen fouten");
	        Config.set("Command.Help.Hawn-Rank", "De rang van een speler geven, weghalen of bekijken (Ranks.yml)");
	        Config.set("Command.Help.Broadcast", "Een bericht naar iedereen sturen");
	        Config.set("Command.Help.Warning", "Een waarschuwing naar iedereen sturen");
	        Config.set("Command.Help.Broadcast-Title", "Een titel naar iedereen sturen");
	        Config.set("Command.Help.Broadcast-ActionBar", "Een actiebalkbericht naar iedereen sturen");
	        Config.set("Command.Help.AdminPanel", "Het adminpaneel openen");
	        Config.set("Command.Help.CheckAccount", "Een speler controleren");
	        Config.set("Command.Help.ClearInv", "De inventaris van een speler leegmaken");
	        Config.set("Command.Help.InvSee", "De inventaris van een speler bekijken");
	        Config.set("Command.Help.Ip", "Het IP van een speler tonen");
	        Config.set("Command.Help.List", "De lijst van de spelers in de lobby");
	        Config.set("Command.Help.Spawn", "Naar de spawn gaan");
	        Config.set("Command.Help.Spawn-Tp", "Een speler naar een spawn teleporteren");
	        Config.set("Command.Help.DelSpawn", "Een spawn weghalen");
	        Config.set("Command.Help.SpawnList", "De lijst van de spawns tonen");
	        Config.set("Command.Help.SetSpawn", "Een spawn zetten");
	        Config.set("Command.Help.SetWarp", "Een warp zetten");
	        Config.set("Command.Help.Warp", "Naar een warp gaan");
	        Config.set("Command.Help.WarpList", "De lijst van de warps tonen");
	        Config.set("Command.Help.DelWarp", "Een warp weghalen");
	        Config.set("Command.Help.EditWarp", "De plek van een warp wijzigen");
	        Config.set("Command.Help.Sun", "Helder weer");
	        Config.set("Command.Help.Rain", "Regen in de wereld");
	        Config.set("Command.Help.Thunder", "Onweer, als je van slecht weer houdt");
	        Config.set("Command.Help.Day", "Dag maken");
	        Config.set("Command.Help.Night", "Nacht maken");
	        Config.set("Command.Help.Fly", "Vliegen aan- of uitzetten");
	        Config.set("Command.Help.FlySpeed", "De vliegsnelheid van een speler wijzigen");
	        Config.set("Command.Help.Speed", "De snelheid wijzigen of aan-/uitzetten");
	        Config.set("Command.Help.Heal", "Een speler genezen");
	        Config.set("Command.Help.Feed", "Een speler voeden");
	        Config.set("Command.Help.Ping", "De ping van een speler tonen");
	        Config.set("Command.Help.Vanish", "Een speler onzichtbaar maken");
	        Config.set("Command.Help.Gamemode", "De spelmodus van een speler wijzigen");
	        Config.set("Command.Help.Burn", "Een speler in brand steken");
	        Config.set("Command.Help.Cleargrounditems", "De voorwerpen op de grond weghalen");
	        Config.set("Command.Help.Clearmobs", "De mobs weghalen");
	        Config.set("Command.Help.EnderChest", "De enderkist openen");
	        Config.set("Command.Help.Exp", "De ervaring van een speler wijzigen");
	        Config.set("Command.Help.Getpos", "De positie van een speler tonen");
	        Config.set("Command.Help.Hat", "Een hoed opzetten");
	        Config.set("Command.Help.Kickall", "Alle spelers kicken");
	        Config.set("Command.Help.Repair", "Een voorwerp repareren");
	        Config.set("Command.Help.Skull", "Het hoofd van een speler krijgen");
	        Config.set("Command.Help.Suicide", "Jezelf doden");
	        Config.set("Command.Help.Workbench", "Een werkbank openen");
	        Config.set("Command.Help.ClearChat", "De hulp van /clearchat tonen");
	        Config.set("Command.Help.DelayChat", "Een vertraging in de chat zetten");
	        Config.set("Command.Help.Gmute", "De chat dempen");
	        Config.set("Command.Help.Help", "De eigen hulp tonen, als die aan staat");
	        Config.set("Command.Help.Gotop", "Naar het hoogste blok op je plek gaan");
	        Config.set("Command.Help.Emoji", "Het emoji-menu openen");
	        Config.set("Command.Help.Scoreboard", "Het scoreboard aan- of uitzetten");
	        Config.set("Command.Help.Scoreboard-Set", "Het huidige scoreboard wijzigen");
	        Config.set("Command.Help.Scoreboard-Keep", "Het scoreboard houden tussen de servers");
	        Config.set("Command.Help.Scoreboard-List", "Alle scoreboards tonen");
	        Config.set("Command.Help.Option", "De opties van de speler");
	        Config.set("Command.Help.Hworld", "De werelden beheren");

        /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
        Config.set("Command.Server-Info.General", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformatie over de server (alles)",
		            "        &3gemaakt door Dianox",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lJava-versie&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lUpdates van Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lServerversie&r&8:&r %serverversion%",
		            "",
		            "  &8→ &6&lGeheugen",
		            "&7&lMaximaal geheugen &o(Java)&r&8:&r %maxmemory%&8MB",
		            "&a&lVrij&7&l/&c&lTotaal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "  &8→ &6&lCPU (processor)",
		            "&7&lGemiddelde belasting&8:&r %averagecpuload%&8%",
		            "&7&lHuidige belasting&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "  &8→ &6&lSchijfruimte",
		            "&7&lTotale schijfruimte&r&8:&r %totalspace%&8MB",
		            "&a&lVrij&7&l/&c&lTotaal&7&l bruikbaar&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Memory", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformatie over de server (geheugen)",
		            "",
		            "  &8→ &6&lGeheugen",
		            "&7&lMaximaal geheugen &o(Java)&r&8:&r %maxmemory%&8MB",
		            "&a&lVrij&7&l/&c&lTotaal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.CPU", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformatie over de server (CPU)",
		            "",
		            "  &8→ &6&lCPU (processor)",
		            "&7&lGemiddelde belasting&8:&r %averagecpuload%&8%",
		            "&7&lHuidige belasting&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Disk", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformatie over de server (schijfruimte)",
		            "",
		            "  &8→ &6&lSchijfruimte",
		            "&7&lTotale schijfruimte&r&8:&r %totalspace%&8MB",
		            "&a&lVrij&7&l/&c&lTotaal&7&l bruikbaar&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Server", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformatie over de server (server)",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lJava-versie&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lUpdates van Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lServerversie&r&8:&r %serverversion%",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Tps", java.util.Arrays.asList("  &8→ &6&lTps&8: &r%tps%"));

        Config.set("Command.Version", java.util.Arrays.asList("  &8→ &6&lVersie van Hawn (gemaakt door Dianox)&8: &r%gethawnversion%"));

        // Since 1.4.8: /hawn debug
        Config.set("Debug.Header", java.util.Arrays.asList("&8&m-----&r &6Hawn debug &8&m-----"));
        Config.set("Debug.No-Config-Error", java.util.Arrays.asList("&aGeen fout in de configuratiebestanden."));
        Config.set("Debug.Config-Errors", java.util.Arrays.asList("&e%arg 1% &7fout(en) in de configuratiebestanden:"));
        Config.set("Debug.Auto", java.util.Arrays.asList("&e%arg 1% &7optie(s) op AUTO, overgelaten aan een andere plugin:"));
        Config.set("Debug.No-Hidden-Error", java.util.Arrays.asList("&aGeen verborgen fout sinds de start."));
        Config.set("Debug.Hidden-Errors", java.util.Arrays.asList("&e%arg 1% &7fout(en) die Hawn sinds de start heeft overgeslagen:"));
        Config.set("Debug.Tip", java.util.Arrays.asList("&7Plugin.Debug: true in general.yml schrijft ze met details in de console."));
        // Since 1.4.8: /hawn rank
        Config.set("Ranks.Usage", java.util.Arrays.asList("%prefix% &7/hawn rank set <speler> <rang> &8| &7remove <speler> &8| &7info <speler> &8| &7list"));
        Config.set("Ranks.Off", java.util.Arrays.asList("%prefix% &cDe rangen staan uit (Ranks.yml, Ranks.Enable: false)"));
        Config.set("Ranks.Unknown-Rank", java.util.Arrays.asList("%prefix% &cDe rang &e%arg 1% &cbestaat niet of staat uit. Rangen: &7%arg 2%"));
        Config.set("Ranks.LuckPerms-Source", java.util.Arrays.asList("%prefix% &7De rangen komen van LuckPerms (Ranks.Source): wijzig de groep van &e%arg 1% &7in LuckPerms (&e/lp user %arg 1% parent set <groep>&7)"));
        Config.set("Ranks.Set", java.util.Arrays.asList("%prefix% &e%arg 1% &7heeft nu de rang &e%arg 2%"));
        Config.set("Ranks.Removed", java.util.Arrays.asList("%prefix% &e%arg 1% &7heeft geen met de hand gegeven rang meer"));
        Config.set("Ranks.No-Rank-By-Hand", java.util.Arrays.asList("%prefix% &e%arg 1% &cheeft geen met de hand gegeven rang (de permissies of LuckPerms geven hem)"));
        Config.set("Ranks.Info", java.util.Arrays.asList("%prefix% &e%arg 1%&7: rang &e%arg 2% &7(%arg 3%) &8| &r%arg 4%"));
        Config.set("Ranks.Info-Offline", java.util.Arrays.asList("%prefix% &e%arg 1% &7is offline. Met de hand gegeven rang: &e%arg 2%"));
        Config.set("Ranks.List-Header", java.util.Arrays.asList("%prefix% &e%arg 1% &7rang(en), de eerste bovenaan in de tablijst:"));
        Config.set("Ranks.List-Line", java.util.Arrays.asList("&8- &e%arg 1% &7(prioriteit %arg 2%) &r%arg 3% &8| &7online: &f%arg 4%"));
        Config.set("Ranks.Source.HAND", "met de hand gegeven");
        Config.set("Ranks.Source.PERMISSION", "door een permissie");
        Config.set("Ranks.Source.LUCKPERMS", "van LuckPerms");
        Config.set("Ranks.Source.DEFAULT", "de standaardrang");
        Config.set("Ranks.Source.NONE", "geen");
        // Since 1.5: /hawn menu
        Config.set("Command.Help.Hawn-Menu", "Een menu uit de map Menus openen voor jezelf of een speler, of ze opsommen");
        Config.set("Menus.Usage", java.util.Arrays.asList("%prefix% &7/hawn menu open <menu> [speler] &8| &7list"));
        Config.set("Menus.Opened", java.util.Arrays.asList("%prefix% &7Het menu &e%arg 1% &7is geopend voor &e%arg 2%"));
        Config.set("Menus.Not-Opened", java.util.Arrays.asList("%prefix% &cHet menu &e%arg 1% &cis niet geopend voor &e%arg 2% &c(permissie of wereld)"));
        Config.set("Menus.List", java.util.Arrays.asList("%prefix% &7Menu's: &e%arg 1%"));
        Config.set("Menus.None", java.util.Arrays.asList("%prefix% &7Geen menu in de map Menus"));
        Config.set("TPS.Check.15", java.util.Arrays.asList("&cJe TPS is lager dan %arg 1%, doe iets voor de stabiliteit van je lobby"));
        Config.set("TPS.Check.5", java.util.Arrays.asList("&cJe TPS is lager dan %arg 1%, je server kan crashen: gebruik /stop om problemen te voorkomen.", "&cLET OP: KRITIEKE TOESTAND VAN DE SERVER"));

        /* --------------- *
         * RELOAD COMMANDS *
         * --------------- */
        Config.set("Command.Reload", java.util.Arrays.asList("&aConfiguratie herladen"));

        Config.set("Command.Build-Bypass.On", java.util.Arrays.asList("&bJe kunt nu alle bouwbeperkingen omzeilen"));
        Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList("&cJe kunt de bouwbeperkingen niet meer omzeilen"));


        /*
         * Vanish
         */
        Config.set("Vanish.Vanish-On.Enable", true);
        Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList("&7[ %player% is nu onzichtbaar ]"));

        Config.set("Vanish.Vanish-Off.Enable", true);
        Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList("&7[ %player% is niet meer onzichtbaar ]"));
        Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList("&7[ %target% is door %player% onzichtbaar gemaakt ]"));
        Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList("&7[ %target% is door %player% weer zichtbaar gemaakt ]"));

        Config.set("Maintenance.On", java.util.Arrays.asList("%prefix% &7Je hebt het onderhoud &aaangezet"));
        Config.set("Maintenance.Off", java.util.Arrays.asList("%prefix% &7Je hebt het onderhoud &cuitgezet"));
        Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(" &4* &cHet onderhoud is &ebegonnen&4 *"));
        Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(" &4* &cHet onderhoud is &evoorbij&4 *"));

        Config.set("Urgent-mode.On", java.util.Arrays.asList("%prefix% &7Je hebt de noodmodus &aaangezet"));
        Config.set("Urgent-mode.Off", java.util.Arrays.asList("%prefix% &7Je hebt de noodmodus &cuitgezet"));
        Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(" &4* &cDe noodmodus is &ebegonnen&4 *"));
        Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(" &4* &cDe noodmodus is &evoorbij&4 *"));
        Config.set("Urgent-mode.Zip", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cEr is een back-up van Hawn gemaakt"));
        Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cFout: de noodmodus kan alleen in de console worden gestopt"));
        Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cJe kunt dit commando niet gebruiken"));
        Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cEen wijziging van %player% in het adminpaneel is gezien",
		            "%arg1% in het bestand %arg2%"));
        Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cAlle plugins zijn uitgezet"));
        Config.set("Urgent-mode.Ops-Removed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c%arg1% operator(s) weggehaald tot het einde van de noodmodus"));
        Config.set("Urgent-mode.Ops-Restored", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7%arg1% operator(s) teruggegeven"));
        Config.set("Urgent-mode.Lockdown-Command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDe server is in de noodmodus: de commando's staan uit"));
        Config.set("Urgent-mode.Lockdown-Chat", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDe server is in de noodmodus: de chat staat uit"));
        Config.set("Urgent-mode.Zip-Failed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDe back-up is mislukt, zie de console"));
        Config.set("Urgent-mode.Console-Only", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDe noodmodus kan alleen in de console worden gestart"));
        Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7Alle plugins zijn &aaangezet",
		            "&eStart de server opnieuw op om problemen te voorkomen"));

        Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList("%prefix% &e%player% wilde dit commando uitvoeren: &b%arg1%"));

        Config.set("Command.SlotView.On", java.util.Arrays.asList("%prefix% &7Je &aziet&7 nu het nummer van elk slot waarop je klikt in een menu of inventaris"));
        Config.set("Command.SlotView.Off", java.util.Arrays.asList("%prefix% &7SlotView staat &cuit"));

        Config.set("Command.IP", java.util.Arrays.asList("%prefix% &7Het IP van de speler is: &e%getplayerip%"));

        Config.set("Command.Kickall", java.util.Arrays.asList("%prefix% &7Alle spelers zijn gekickt"));

        Config.set("Command.ClearGroundItems", java.util.Arrays.asList("%prefix% &7Alle voorwerpen zijn weggehaald"));

        Config.set("Command.ClearMobs", java.util.Arrays.asList("%prefix% &7Alle mobs zijn weggehaald"));

        Config.set("Command.CheckAccount", java.util.Arrays.asList("",
		            "  &8→ &r&lInformatie over de speler&8:&r &b%target%",
		            "&7&lLaatste keer gejoind&r&8:&e %hawn_player_join_date%",
		            "&7&lEerste keer gejoind&r&8:&e %hawn_player_first_join_date%",
		            "&7&lIp&r&8:&e %player_ip%",
		            "",
		            "  &8→ &r&lOpties&8:&r &8(&aGroen &8=&7 aan / &cRood&8 = &7uit&8)",
		            "&7&lSpelers zichtbaar: %pv_point%",
		            "&7&lSnelheid: %ps_point% &7(&e%ps_number%&7)",
		            "&7&lVliegen: %pof_point%",
		            "&7&lDubbele sprong: %dj_point%",
		            "&7&lAankondigingen: %ab_point%",
		            "&7&lOnzichtbaar: %v_point%",
		            "&7&lScoreboard houden: %ksb_point% &8(&e%scorename%&8)",
		            "&7&lSprongkracht: %jb_point%",
		            "",
		            "&7&lSpelmodus: &e%gm_number%",
		            ""));

        // List

        Config.set("Command.List.Part-One", java.util.Arrays.asList("§8//§7§m---------------§r§8\\\\ §3[§bList§3] §8//§7§m---------------§r§8\\\\",
		            " ",
		            "  &8→ &6&lPagina %number%",
		            " "));

        Config.set("Command.List.Part-Two", java.util.Arrays.asList("§8\\\\§7§m---------------§r§8// §3[§bList§3] §8\\\\§7§m---------------§r§8//"));

        Config.set("Command.List.Gui.Other.Page.Next", "&bVolgende");
        Config.set("Command.List.Gui.Other.Page.Back", "&bVorige");
        Config.set("Command.List.Gui.Other.Back.PanelAdmin", "&c&lTerug naar het adminpaneel");
        Config.set("Command.List.Gui.Player.Survival", "&cSURVIVAL");
        Config.set("Command.List.Gui.Player.Spectator", "&eTOESCHOUWER");
        Config.set("Command.List.Gui.Player.Creative", "&6CREATIEF");
        Config.set("Command.List.Gui.Player.Adventure", "&aAVONTUUR");
        Config.set("Command.List.Gui.Player.Gamemode", "&7Spelmodus: ");
        Config.set("Command.List.Gui.Player.World", "&7Wereld: &e");
        Config.set("Command.List.Gui.Player.LeftClick", "&eLinkerklik&7 om de speler te bewerken");

        // EDIT PLAYER

        Config.set("Command.EditPlayer.Gui.Gamemode.LeftClick", "&eLinkerklik&7 om de spelmodus te wijzigen");
        Config.set("Command.EditPlayer.Gui.Gamemode.Survival", "&cSURVIVAL");
        Config.set("Command.EditPlayer.Gui.Gamemode.Spectator", "&eTOESCHOUWER");
        Config.set("Command.EditPlayer.Gui.Gamemode.Creative", "&6CREATIEF");
        Config.set("Command.EditPlayer.Gui.Gamemode.Adventure", "&aAVONTUUR");

        Config.set("Command.EditPlayer.Gui.ClearInv.LeftClick", "&eLinkerklik&7 om de inventaris en het harnas leeg te maken");
        Config.set("Command.EditPlayer.Gui.ClearInv.Item-Name", "&6Inventaris leegmaken");

        Config.set("Command.EditPlayer.Gui.Teleport.LeftClick", "&eLinkerklik&7 om je naar de speler te teleporteren");
        Config.set("Command.EditPlayer.Gui.Teleport.Item-Name", "&6Naar deze speler teleporteren");

        Config.set("Command.EditPlayer.Gui.MoreSoon.Item-Name", "Binnenkort meer");

        Config.set("Command.EditPlayer.Gui.BackToPlayerList.Item-Name", "&cTerug naar de spelerslijst");

        // Since 1.5.3: the new items of Edit Player
        Config.set("Command.EditPlayer.Gui.Gamemode.Lore", java.util.Arrays.asList("", "&7Linkerklik: de volgende spelmodus", "&7Rechterklik: de vorige"));
        Config.set("Command.EditPlayer.Gui.Head.Lore", java.util.Arrays.asList("&7Wereld: &f%world%", "&7Spelmodus: &f%gamemode%", "&7Rang: &f%rank%"));
        Config.set("Command.EditPlayer.Gui.On", "&aaan");
        Config.set("Command.EditPlayer.Gui.Off", "&cuit");
        Config.set("Command.EditPlayer.Gui.Toggle.Lore", java.util.Arrays.asList("", "&7Klik om om te schakelen"));
        Config.set("Command.EditPlayer.Gui.Fly.Item-Name", "&eVliegen: %value%");
        Config.set("Command.EditPlayer.Gui.Vanish.Item-Name", "&eVanish: %value%");
        Config.set("Command.EditPlayer.Gui.Walk-Speed.Item-Name", "&eLoopsnelheid: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Fly-Speed.Item-Name", "&eVliegsnelheid: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Speed.Lore", java.util.Arrays.asList("", "&7Linkerklik: &a+1&7, rechterklik: &c-1", "&7Shift + klik: normale snelheid"));
        Config.set("Command.EditPlayer.Gui.Heal.Item-Name", "&eGenezen en voeden");
        Config.set("Command.EditPlayer.Gui.Heal.Lore", java.util.Arrays.asList("", "&7Linkerklik: genezen", "&7Rechterklik: voeden"));
        Config.set("Command.EditPlayer.Gui.Teleport-Here.Item-Name", "&6Deze speler naar je toe halen");
        Config.set("Command.EditPlayer.Gui.Teleport-Here.LeftClick", "&eLinkerklik&7 om de speler naar je toe te teleporteren");
        Config.set("Command.EditPlayer.Gui.Rank.Item-Name", "&eRang met de hand: &f%value%");
        Config.set("Command.EditPlayer.Gui.Rank.Lore", java.util.Arrays.asList("", "&7Linkerklik: de volgende rang", "&7Rechterklik: de vorige", "&7(- : geen rang met de hand)"));
        Config.set("Command.EditPlayer.Gui.Rank.LuckPerms", java.util.Arrays.asList("", "&7De rangen komen van LuckPerms:", "&7wijzig daar de groep van de speler"));
        Config.set("Command.EditPlayer.Gui.Hider.Item-Name", "&eWie de speler ziet: %value%");
        Config.set("Command.EditPlayer.Gui.Hider.Everyone", "&aiedereen");
        Config.set("Command.EditPlayer.Gui.Hider.Staff", "&estaff en VIP");
        Config.set("Command.EditPlayer.Gui.Hider.Nobody", "&cniemand");
        Config.set("Command.EditPlayer.Gui.Hider.Lore", java.util.Arrays.asList("", "&7Klik: de volgende stand"));
        Config.set("Command.EditPlayer.Changed", java.util.Arrays.asList("%prefix% &e%arg2% &8» %arg1%"));

        //

        Config.set("Command.No-Clip.Enable", java.util.Arrays.asList("%prefix% &7No-Clip staat &aaan"));

        Config.set("Command.No-Clip.Disable", java.util.Arrays.asList("%prefix% &7No-Clip staat &cuit"));

        Config.set("Command.NightVision", java.util.Arrays.asList("%prefix% &7Je kunt nu in het donker zien"));
        Config.set("Command.NightVision-Off", java.util.Arrays.asList("%prefix% &7Je ziet niet meer in het donker"));

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList("&cJe hebt deze spawn geen naam gegeven, er is automatisch een naam gekozen",
		            "§eSpawn gezet onder de naam %spawnName%"));

        Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList("§eSpawn gezet onder de naam %spawnName%"));

        Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList("&bDe spawn &e%spawn%&b is verwijderd"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}
