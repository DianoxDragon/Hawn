package fr.dianox.hawn.utility.config.configs.messages.de_de;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The German messages (Messages/de_DE), since Hawn 1.4.6: the texts of ConfigMMsg translated.
 */
public class DEConfigMMsg {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public DEConfigMMsg() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/de_DE/Messages.yml");
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
        Config.set("ClearChat.No-Reason", "kein Grund");
        Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {" &4&l* &cDer Chat wurde geleert &7(Grund: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {" &4&l* &cDer Chat wurde von &6%player% &cgeleert &7(Grund: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7, dein Chat wurde geleert"}));
        Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7, dein Chat wurde geleert"}));
        Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"%prefix% &7Der Chat von &e%player%&7 wurde geleert"}));

        Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"%prefix% &cDu kannst nicht schreiben: der Chat ist gesperrt"}));
        Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cDer Chat wurde von &6%player% &cgesperrt &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cDer Chat wurde von &6%player%&c für &6%minutes% Minuten &cgesperrt &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bDer Chat wurde von &e%player% &aentsperrt &3&m&l->-=-<-"}));

        Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Ping beträgt &e%ping%&7 ms"}));
        Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7Der Ping von &b%target%&7 beträgt &e%ping%&7 ms"}));

        Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cDu kannst noch nicht schreiben: warte &6%DELAY%&c Sekunde(n) zwischen zwei Nachrichten"}));
        Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7Die Chat-Verzögerung wurde auf &e%DELAY%&7 Sekunde(n) gesetzt", 
        		"          &7&oVergiss nicht, diesen Wert in der Konfiguration zu ändern", 
        		"          &7&oSonst geht er beim nächsten Neustart verloren"}));
        Config.set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7Die Chat-Verzögerung wurde &cdeaktiviert"}));

        Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eBroadcast&8]&r %broadcast%"}));

        /*
         * Weather and Time
         */
        Config.set("Weather.Set.Sun.Enable", true);
        Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Regen und Gewitter sind vorbei, &ees lebe die Sonne&7!"}));
        Config.set("Weather.Set.Rain.Enable", true);
        Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"%prefix% &7In dieser Welt &bregnet&7 es jetzt, &bpass auf, dass du nicht nass wirst&7!"}));
        Config.set("Weather.Set.Thunder.Enable", true);
        Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"%prefix% &6&k!!!!&7 &eEin Gewitter zieht auf&7... &ePass auf, dass dich kein Blitz trifft &6&k!!!!"}));

        Config.set("Time.Set.Day.Enable", true);
        Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Es ist &eMorgen&7, Zeit für die Schule"}));
        Config.set("Time.Set.Night.Enable", true);
        Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"%prefix% &f&lEs wird dunkel. Vorsicht, die Nacht ist dunkel und voller Schrecken!"}));

        /*
         * FLY COMMANDS
         */
        Config.set("Fly.Enable.Enable", true);
        Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Flugmodus wurde &aaktiviert"}));
        Config.set("Fly.Enable-Other.Enable", true);
        Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Flugmodus wurde von &e%player% &aaktiviert"}));
        Config.set("Fly.Enable-Other-Executor.Enable", true);
        Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Flugmodus von &e%target%&7 wurde &aaktiviert"}));

        Config.set("Fly.Disable.Enable", true);
        Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Flugmodus wurde &cdeaktiviert"}));
        Config.set("Fly.Disable-Other.Enable", true);
        Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Flugmodus wurde von &e%player% &cdeaktiviert"}));
        Config.set("Fly.Disable-Other-Executor.Enable", true);
        Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Flugmodus von &e%target%&7 wurde &cdeaktiviert"}));

        /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
        Config.set("Gamemode.Self.Survival.Enable", true);
        Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde auf &eÜberleben&7 gesetzt"}));
        Config.set("Gamemode.Self.Creative.Enable", true);
        Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde auf &eKreativ&7 gesetzt"}));
        Config.set("Gamemode.Self.Adventure.Enable", true);
        Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde auf &eAbenteuer&7 gesetzt"}));
        Config.set("Gamemode.Self.Spectator.Enable", true);
        Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde auf &eZuschauer&7 gesetzt"}));

        Config.set("Gamemode.Other.Survival.Enable", true);
        Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde von &b%player%&7 auf &eÜberleben&7 gesetzt"}));
        Config.set("Gamemode.Other.Creative.Enable", true);
        Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde von &b%player%&7 auf &eKreativ&7 gesetzt"}));
        Config.set("Gamemode.Other.Adventure.Enable", true);
        Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde von &b%player%&7 auf &eAbenteuer&7 gesetzt"}));
        Config.set("Gamemode.Other.Spectator.Enable", true);
        Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Spielmodus wurde von &b%player%&7 auf &eZuschauer&7 gesetzt"}));

        Config.set("Gamemode.Other-Sender.Survival.Enable", true);
        Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Spielmodus von &b%player%&7 wurde auf &eÜberleben&7 gesetzt"}));
        Config.set("Gamemode.Other-Sender.Creative.Enable", true);
        Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Spielmodus von &b%player%&7 wurde auf &eKreativ&7 gesetzt"}));
        Config.set("Gamemode.Other-Sender.Adventure.Enable", true);
        Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Spielmodus von &b%player%&7 wurde auf &eAbenteuer&7 gesetzt"}));
        Config.set("Gamemode.Other-Sender.Spectator.Enable", true);
        Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Spielmodus von &b%player%&7 wurde auf &eZuschauer&7 gesetzt"}));

        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDu bist bereits in diesem Spielmodus"}));
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDer Spieler %target% ist bereits in diesem Spielmodus"}));

        /* -------------- *
			 * GOTOP COMMANDS *
			 * -------------- */
        Config.set("Gotop.Enable", true);
        Config.set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du wurdest auf den höchsten Block deiner Position teleportiert"}));

        /* ------------- *
			 * FEED COMMANDS *
			 * ------------- */
        Config.set("Feed.Self.Enable", true);
        Config.set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du wurdest gesättigt"}));

        Config.set("Feed.Other.Enable", true);
        Config.set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du wurdest von &e%player%&7 gesättigt"}));
        Config.set("Feed.Other-Sender.Enable", true);
        Config.set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 wurde gesättigt"}));

        /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
        Config.set("Heal.Self.Enable", true);
        Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du wurdest geheilt"}));

        Config.set("Heal.Other.Enable", true);
        Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du wurdest von &e%player%&7 geheilt"}));
        Config.set("Heal.Other-Sender.Enable", true);
        Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 wurde geheilt"}));

        /* ------------------- *
			 * ENDERCHEST COMMANDS *
			 * ------------------- */

        Config.set("EnderChest.Self.Enable", true);
        Config.set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast deine Endertruhe geöffnet"}));

        Config.set("EnderChest.Other-Sender.Enable", true);
        Config.set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast die Endertruhe von &e%target%&7 geöffnet"}));

        /* --------------- *
			 * INVSEE COMMANDS *
			 * --------------- */

        Config.set("InvSee.Other-Sender.Enable", true);
        Config.set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast das Inventar von &e%target%&7 geöffnet"}));

        /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
        Config.set("Scoreboard.Toggle.On.Enable", true);
        Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Das Scoreboard wurde &aaktiviert"}));
        Config.set("Scoreboard.Toggle.Off.Enable", true);
        Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Das Scoreboard wurde &cdeaktiviert"}));

        Config.set("Scoreboard.Changes.Enable", true);
        Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Scoreboard ist jetzt &e%arg1%"}));
        Config.set("Scoreboard.Error-Changes.Enable", true);
        Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDas Scoreboard &6%arg1%&c existiert nicht"}));

        Config.set("Scoreboard.Keep-On.Enable", true);
        Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du behältst jetzt dieses Scoreboard"}));
        Config.set("Scoreboard.Keep-Off.Enable", true);
        Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du behältst dieses Scoreboard &cnicht mehr"}));

        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDu hast für kein Scoreboard die Berechtigung"}));

        /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
        Config.set("Warp.Tp.Self.Enable", true);
        Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du wurdest zum Warp &e%warp%&7 teleportiert"}));
        Config.set("Warp.Tp.Self-Delay.Enable", true);
        Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Bitte warte &4&l%second%&c Sekunde(n)&7, bevor du teleportiert wirst"}));

        Config.set("Warp.Tp.Other.Enable", true);
        Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du wurdest von &b%player%&7 zum Warp &e%warp%&7 teleportiert"}));
        Config.set("Warp.Tp.Other-Sender.Enable", true);
        Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast &b%target%&7 zum Warp &e%warp%&7 teleportiert"}));
        Config.set("Warp.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Bitte warte &4&l%second%&c Sekunde(n)&7, bis dieser Spieler teleportiert wird"}));

        Config.set("Warp.List.Enable", true);
        Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bWarps:&e %warplist%"}));

        Config.set("Warp.No-Warp.Enable", true);
        Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEs gibt keinen Warp"}));

        Config.set("Warp.Set.Warp-Set.Enable", true);
        Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Warp &e%arg%&7 wurde erstellt"}));
        Config.set("Warp.Set.Warp-Already-Exist.Enable", true);
        Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDieser Name existiert bereits"}));

        Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
        Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDieser Warp existiert nicht"}));
        Config.set("Warp.Del.Warp-Delete.Enable", true);
        Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Warp &e%warp%&7 wurde &cgelöscht"}));

        Config.set("Warp.Edit.Warp-Edited.Enable", true);
        Config.set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Die Position des Warps &e%warp%&7 wurde aktualisiert"}));

        /* ---------------- *
			 * SUICIDE COMMANDS *
			 * ---------------- */

        Config.set("Suicide.Self.Enable", true);
        Config.set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast dich selbst getötet"}));

        Config.set("Suicide.Broadcast.Enable", true);
        Config.set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% hat sich von der Welt verabschiedet"}));

        /* --------------- *
			 * REPAIR COMMANDS *
			 * --------------- */

        Config.set("Repair.Repaired.Enable", true);
        Config.set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Der Gegenstand &e%item% &7wurde repariert"}));

        Config.set("Repair.Can-t-Repair.Enable", true);
        Config.set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDieser Gegenstand kann nicht repariert werden"}));

        /* ------------ *
			 * EXP COMMANDS *
			 * ------------ */

        Config.set("Exp.Add.Sender.Enable", true);
        Config.set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7Erfahrungspunkte wurden &b%target%&7 hinzugefügt"}));
        Config.set("Exp.Add.Target.Enable", true);
        Config.set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7Erfahrungspunkte wurden dir hinzugefügt"}));

        Config.set("Exp.Set.Sender.Enable", true);
        Config.set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Die Erfahrungspunkte von &b%target%&7 wurden auf &e%number_exp%&7 gesetzt"}));
        Config.set("Exp.Set.Target.Enable", true);
        Config.set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Erfahrungspunkte wurden auf &e%number_exp%&7 gesetzt"}));

        Config.set("Exp.Take.Sender.Enable", true);
        Config.set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 hat &e%number_exp%&7 Erfahrungspunkte verloren"}));
        Config.set("Exp.Take.Target.Enable", true);
        Config.set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast &e%number_exp%&7 Erfahrungspunkte verloren"}));
        Config.set("Exp.Take.Sender-Error.Enable", true);
        Config.set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDu kannst &e%target%&c nicht mehr als &6%target_exp_points%&c Erfahrungspunkte abziehen"}));

        Config.set("Exp.Clear.Sender.Enable", true);
        Config.set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 hat jetzt 0 Erfahrungspunkte"}));
        Config.set("Exp.Clear.Target.Enable", true);
        Config.set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Erfahrungspunkte wurden zurückgesetzt"}));

        /* ------------------ *
			 * WORKBENCH COMMANDS *
			 * ------------------ */
        Config.set("WorkBench.Self.Enable", true);
        Config.set("WorkBench.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast deine Werkbank geöffnet"}));

        Config.set("WorkBench.Target.Enable", true);
        Config.set("WorkBench.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Werkbank wurde von &e%player%&7 geöffnet"}));
        Config.set("WorkBench.Sender.Enable", true);
        Config.set("WorkBench.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast die Werkbank für &e%target%&7 geöffnet"}));

        /* ------------- *
			 * BURN COMMANDS *
			 * ------------- */
        Config.set("Burn.Target.Enable", true);
        Config.set("Burn.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du brennst wegen &e%player%&7 für &b%duration%&7 Sekunden"}));
        Config.set("Burn.Sender.Enable", true);
        Config.set("Burn.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du lässt &e%target%&7 für &b%duration%&7 Sekunden brennen"}));

        /* -------------- *
			 * SKULL COMMANDS *
			 * -------------- */
        Config.set("Skull.Self.Enable", true);
        Config.set("Skull.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast deinen eigenen Kopf erhalten"}));

        Config.set("Skull.Sender.Enable", true);
        Config.set("Skull.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du hast den Kopf von &e%target%&7 erhalten"}));
        Config.set("Skull.Target.Enable", false);
        Config.set("Skull.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7 hat deinen Kopf genommen"}));

        /*
         * Hat
         */
        Config.set("Hat.Self.Set.Enable", true);
        Config.set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Dein neuer Hut wurde aufgesetzt"}));
        Config.set("Hat.Self.Removed.Enable", true);
        Config.set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Dein Hut wurde abgenommen"}));

        Config.set("Hat.Other-Target.Set.Enable", true);
        Config.set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Du hast von &e%player%&7 einen neuen Hut bekommen"}));
        Config.set("Hat.Other-Target.Removed.Enable", true);
        Config.set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Dein Hut wurde von &e%player%&7 abgenommen"}));

        Config.set("Hat.Other-Sender.Set.Enable", true);
        Config.set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Du hast &e%target%&7 einen neuen Hut aufgesetzt"}));
        Config.set("Hat.Other-Sender.Removed.Enable", true);
        Config.set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Du hast den Hut von &e%target%&7 abgenommen"}));

        Config.set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
        Config.set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cDieser Gegenstand kann nicht als Hut aufgesetzt werden"}));
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cDu trägst keinen Hut, den man abnehmen könnte"}));

        /*
         * GetPos
         */
        Config.set("GetPos.Enable", true);
        Config.set("GetPos.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Die Position des Spielers ist: &e%X% %Y% %Z%&7 in der Welt &b%world%"}));

        /*
         * Vanish stuff
         */
        Config.set("Vanish.Self.Enable", true);
        Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish wurde &aaktiviert"}));
        Config.set("Vanish.Self-Disabled.Enable", true);
        Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish wurde &cdeaktiviert"}));
        /*Config.set("Vanish.Self-Still.Enable", true);
        Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bVanish ist noch aktiv"}));*/
        Config.set("Vanish.Other-Target.Enable", true);
        Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish wurde von &e%player% &aaktiviert"}));
        Config.set("Vanish.Other-Target-Disabled.Enable", true);
        Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish wurde von &e%player% &cdeaktiviert"}));
        Config.set("Vanish.Other-Sender.Enable", true);
        Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish von &b%target%&7 wurde von &e%player% &aaktiviert"}));
        Config.set("Vanish.Other-Sender-Disabled.Enable", true);
        Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish von &b%target%&7 wurde von &e%player% &cdeaktiviert"}));
        Config.set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aDu bist unsichtbar (Vanish)"}));

        /*
         * Clear inv stuff
         */
        Config.set("ClearInv.Self.Enable", true);
        Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Inventar wurde geleert"}));
        Config.set("ClearInv.Other-Target.Enable", true);
        Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Inventar wurde von &e%player%&7 geleert"}));
        Config.set("ClearInv.Other-Sender.Enable", true);
        Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Das Inventar von &e%target%&7 wurde geleert"}));

        /*
         * Spawn stuff
         */
        Config.set("Spawn.Tp.Self-Delay.Enable", true);
        Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Bitte warte &4&l%second%&c Sekunde(n)&7, bevor du teleportiert wirst"}));
        Config.set("Spawn.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Bitte warte &4&l%second%&c Sekunde(n)&7, bis dieser Spieler teleportiert wird"}));

        Config.set("Spawn.List.Enable", true);
        Config.set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bSpawns:&e %spawnlist%"}));

        Config.set("Spawn.No-Spawn.Enable", true);
        Config.set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEs gibt keinen Spawn"}));

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

        Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"%prefix% &7Oh nein, du bist ins Leere gefallen"}));
        Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eFluchen&8] &b%player% &7schrieb &e%message%"}));
        Config.set("Anti-Spam.Repeat", java.util.Arrays.asList(new String[] {"%prefix% &cSende nicht zweimal dieselbe Nachricht"}));
        Config.set("Anti-Spam.Caps", java.util.Arrays.asList(new String[] {"%prefix% &cZu viele Großbuchstaben in deiner Nachricht"}));
        Config.set("Anti-Advertising.Blocked", java.util.Arrays.asList(new String[] {"%prefix% &cKeine Werbung im Chat: Adressen anderer Server sind nicht erlaubt"}));
        Config.set("Anti-Advertising.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eWerbung&8] &b%player% &7wollte senden: &e%message%"}));

        Config.set("LaunchPad.Cant-Use-Cooldown.Enable", true);
        Config.set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Du kannst die Launchpads noch nicht wieder benutzen"}));

        Config.set("Cancel-Tp.Warp.Enable", true);
        Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teleport &cabgebrochen"}));
        Config.set("Cancel-Tp.Spawn.Enable", true);
        Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teleport &cabgebrochen"}));

        Config.set("Custom-Join-Item.Error.FunGun.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.FunGun.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Bitte warte &4&l%timedelayfunguncji% &c&lSekunde(n)&7!"}));
        // The grappling hook (since 1.5.5)
        Config.set("Custom-Join-Item.Error.Grappling.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Bitte warte &4&l%timedelaygrapplingcji% &c&lSekunde(n)&7!"}));
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDer Haken ist zu weit weg!"}));

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
        		"     &l>> &e&o&lHilfe zu den Spieleroptionen",
        		"",
        		" &8>> &7/option fly - &eFliegen ein- oder ausschalten",
        		" &8>> &7/option doublejump - &eDoppelsprung ein- oder ausschalten",
        		" &8>> &7/option speed - &eGeschwindigkeit ein- oder ausschalten",
        		" &8>> &7/option flyspeed - &eFluggeschwindigkeit ein- oder ausschalten",
        		" &8>> &7/option jumpboost - &eSprungkraft ein- oder ausschalten",
        		" &8>> &7/option autobc - &eAutomatische Ankündigungen ein- oder ausblenden",
        		" &8>> &7/option pv - &eAndere Spieler ein- oder ausblenden",
        		"",
        		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));

        Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Doppelsprung wurde &aaktiviert"}));
        Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Dein Doppelsprung wurde &cdeaktiviert"}));

        Config.set("PlayerOption.Speed.Enable.Enable", true);
        Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Geschwindigkeit wurde &aaktiviert"}));
        Config.set("PlayerOption.Speed.Disable.Enable", true);
        Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Geschwindigkeit wurde &cdeaktiviert"}));
        Config.set("PlayerOption.Speed.Set.Enable", true);
        Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Geschwindigkeit wurde auf &e%arg1%&7 gesetzt"}));

        Config.set("PlayerOption.FlySpeed.Enable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Fluggeschwindigkeit wurde &aaktiviert"}));
        Config.set("PlayerOption.FlySpeed.Disable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Fluggeschwindigkeit wurde &cdeaktiviert"}));
        Config.set("PlayerOption.FlySpeed.Set.Enable", true);
        Config.set("PlayerOption.FlySpeed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Fluggeschwindigkeit wurde auf &e%arg1%&7 gesetzt"}));

        Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Sprungkraft wurde &aaktiviert"}));
        Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Deine Sprungkraft wurde &cdeaktiviert"}));

        Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Die automatischen Ankündigungen wurden &aeingeblendet"}));
        Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Die automatischen Ankündigungen wurden &causgeblendet"}));

        Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Puff! Die Spieler sind verschwunden"}));
        Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Die Spieler sind jetzt sichtbar"}));
        // Since 1.4.9: the third state of the player hider
        Config.set("PlayerOption.PlayerVisibility.STAFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.STAFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Nur das Team und die VIPs sind jetzt sichtbar"}));
        // Since 1.5: the menus
        Config.set("Menus.Not-Found", java.util.Arrays.asList("%prefix% &cDas Menü &e%arg% &cexistiert nicht"));
        Config.set("Menus.Not-In-This-World", java.util.Arrays.asList("%prefix% &cDieses Menü kann in dieser Welt nicht geöffnet werden"));

        Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDer Doppelsprung ist in &6Cosmetics-Fun/DoubleJump.yml&c deaktiviert"}));
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDer Doppelsprung ist in dieser Welt nicht erlaubt"}));

        Config.set("PlayerOption.Error.Option-Disabled.Enable", true);
        Config.set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDiese Option ist deaktiviert, du kannst sie nicht ändern"}));
        //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
        //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cDiese Option ist bereits so eingestellt"}));
        Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
        Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Bitte warte &4&l%timedelaypvcji% &c&lSekunde(n)&7!"}));

        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDie Spieleroptionen sind in dieser Welt nicht aktiviert"}));
        /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lBitte warte &4%timedelaypvcommands% &c&lSekunden!"}));*/            

        /*
         * 
         * OTHER STUFF
         * 
         */

        Config.set("Error.No-Permissions.Enable", true);
        Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDir fehlt die Berechtigung: %noperm%"}));
        Config.set("Error.No-Spawn.Enable", true);
        Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDieser Spawn existiert nicht"}));
        Config.set("Error.Change-Me.Enable", true);
        Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDu musst den Spawn/Warp/usw. bei &6%arg1%&c in &e%arg2%&c ändern"}));
        Config.set("Error.No-Players.Enable", true);
        Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDer Spieler ist nicht online oder existiert nicht"}));
        Config.set("Error.No-Page-Found.Enable", true);
        Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDie Seite wurde nicht gefunden"}));
        Config.set("Error.No-Category.Enable", true);
        Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDiese Kategorie existiert nicht"}));
        Config.set("Error.Use-Number.Enable", true);
        Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cBitte gib eine Zahl an"}));
        Config.set("Error.Command-Disable.Enable", true);
        Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDieser Befehl ist deaktiviert"}));
        // Since 1.4.5: an error in a command (written in the console), instead of "An internal error occurred"
        Config.set("Error.Command-Failed.Enable", true);
        Config.set("Error.Command-Failed.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDieser Befehl ist fehlgeschlagen: der Fehler steht in der Konsole, sag einem Admin Bescheid"}));
        Config.set("Error.Command-Wrong-Arguments.Enable", true);
        Config.set("Error.Command-Wrong-Arguments.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cFalsche Argumente. Verwendung: &e%usage%"}));
        Config.set("Error.Argument-Missing.Enable", true);
        Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEs fehlen ein oder mehrere Argumente"}));
        Config.set("Error.Not-A-Player.Enable", true);
        Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDu bist kein Spieler"}));
        Config.set("Protection.Anti-Place", java.util.Arrays.asList(new String[] {"%prefix% &cDu kannst hier keine Blöcke platzieren!"}));
        Config.set("Protection.Anti-Break", java.util.Arrays.asList(new String[] {"%prefix% &cDu kannst hier keine Blöcke abbauen!"}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}