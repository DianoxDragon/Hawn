package fr.dianox.hawn.utility.config.configs.messages.nl_nl;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Dutch messages (Messages/nl_NL), since Hawn 1.5.4: the texts of ConfigMMsg translated.
 */
public class NLConfigMMsg {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public NLConfigMMsg() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/nl_NL/Messages.yml");
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
        Config.set("ClearChat.No-Reason", "geen reden");
        Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {" &4&l* &cDe chat is leeggemaakt &7(reden: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {" &4&l* &cDe chat is leeggemaakt door &6%player% &7(reden: %reason%&7) &4&l*"}));
        Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7, je chat is leeggemaakt"}));
        Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7, je chat is leeggemaakt"}));
        Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"%prefix% &7De chat van &e%player%&7 is leeggemaakt"}));

        Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"%prefix% &cJe kunt niet praten: de chat is gedempt"}));
        Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cDe chat is gedempt door &6%player% &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cDe chat is gedempt door &6%player%&c voor &6%minutes% minuten &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bDe chat is &aweer open &bgezet door &e%player% &3&m&l->-=-<-"}));

        Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Je ping is &e%ping%&7 ms"}));
        Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7De ping van &b%target%&7 is &e%ping%&7 ms"}));

        Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cJe kunt nog niet praten: wacht &6%DELAY%&c seconde(n) tussen twee berichten"}));
        Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7De vertraging van de chat is op &e%DELAY%&7 seconde(n) gezet",
        		"          &7&oVergeet niet deze waarde in de configuratie te wijzigen",
        		"          &7&oAnders is ze weg bij de volgende herstart"}));
        Config.set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7De vertraging van de chat is &cuitgezet"}));

        Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eBroadcast&8]&r %broadcast%"}));

        /*
         * Weather and Time
         */
        Config.set("Weather.Set.Sun.Enable", true);
        Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Geen regen en onweer meer, &eleve de zon&7!"}));
        Config.set("Weather.Set.Rain.Enable", true);
        Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Het &bregent&7 nu in deze wereld, &bpas op dat je niet nat wordt&7!"}));
        Config.set("Weather.Set.Thunder.Enable", true);
        Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"%prefix% &6&k!!!!&7 &eEr komt onweer aan&7... &ePas op voor de bliksem &6&k!!!!"}));

        Config.set("Time.Set.Day.Enable", true);
        Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Het is &eochtend&7, tijd voor school"}));
        Config.set("Time.Set.Night.Enable", true);
        Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"%prefix% &f&lHet wordt donker. Pas op, de nacht is donker en vol verschrikkingen!"}));

        /*
         * FLY COMMANDS
         */
        Config.set("Fly.Enable.Enable", true);
        Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vliegen is &aaangezet"}));
        Config.set("Fly.Enable-Other.Enable", true);
        Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vliegen is &aaangezet &7door &e%player%"}));
        Config.set("Fly.Enable-Other-Executor.Enable", true);
        Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vliegen van &e%target%&7 is &aaangezet"}));

        Config.set("Fly.Disable.Enable", true);
        Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vliegen is &cuitgezet"}));
        Config.set("Fly.Disable-Other.Enable", true);
        Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vliegen is &cuitgezet &7door &e%player%"}));
        Config.set("Fly.Disable-Other-Executor.Enable", true);
        Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vliegen van &e%target%&7 is &cuitgezet"}));

        /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
        Config.set("Gamemode.Self.Survival.Enable", true);
        Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is nu &eSurvival"}));
        Config.set("Gamemode.Self.Creative.Enable", true);
        Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is nu &eCreatief"}));
        Config.set("Gamemode.Self.Adventure.Enable", true);
        Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is nu &eAvontuur"}));
        Config.set("Gamemode.Self.Spectator.Enable", true);
        Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is nu &eToeschouwer"}));

        Config.set("Gamemode.Other.Survival.Enable", true);
        Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is door &b%player%&7 op &eSurvival&7 gezet"}));
        Config.set("Gamemode.Other.Creative.Enable", true);
        Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is door &b%player%&7 op &eCreatief&7 gezet"}));
        Config.set("Gamemode.Other.Adventure.Enable", true);
        Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is door &b%player%&7 op &eAvontuur&7 gezet"}));
        Config.set("Gamemode.Other.Spectator.Enable", true);
        Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je spelmodus is door &b%player%&7 op &eToeschouwer&7 gezet"}));

        Config.set("Gamemode.Other-Sender.Survival.Enable", true);
        Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De spelmodus van &b%player%&7 is nu &eSurvival"}));
        Config.set("Gamemode.Other-Sender.Creative.Enable", true);
        Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De spelmodus van &b%player%&7 is nu &eCreatief"}));
        Config.set("Gamemode.Other-Sender.Adventure.Enable", true);
        Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De spelmodus van &b%player%&7 is nu &eAvontuur"}));
        Config.set("Gamemode.Other-Sender.Spectator.Enable", true);
        Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De spelmodus van &b%player%&7 is nu &eToeschouwer"}));

        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe bent al in deze spelmodus"}));
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDe speler %target% is al in deze spelmodus"}));

        /* -------------- *
			 * GOTOP COMMANDS *
			 * -------------- */
        Config.set("Gotop.Enable", true);
        Config.set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent naar het hoogste blok op je plek geteleporteerd"}));

        /* ------------- *
			 * FEED COMMANDS *
			 * ------------- */
        Config.set("Feed.Self.Enable", true);
        Config.set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent gevoed"}));

        Config.set("Feed.Other.Enable", true);
        Config.set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent gevoed door &e%player%"}));
        Config.set("Feed.Other-Sender.Enable", true);
        Config.set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 is gevoed"}));

        /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
        Config.set("Heal.Self.Enable", true);
        Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent genezen"}));

        Config.set("Heal.Other.Enable", true);
        Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent genezen door &e%player%"}));
        Config.set("Heal.Other-Sender.Enable", true);
        Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 is genezen"}));

        /* ------------------- *
			 * ENDERCHEST COMMANDS *
			 * ------------------- */

        Config.set("EnderChest.Self.Enable", true);
        Config.set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt je enderkist geopend"}));

        Config.set("EnderChest.Other-Sender.Enable", true);
        Config.set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt de enderkist van &e%target%&7 geopend"}));

        /* --------------- *
			 * INVSEE COMMANDS *
			 * --------------- */

        Config.set("InvSee.Other-Sender.Enable", true);
        Config.set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt de inventaris van &e%target%&7 geopend"}));

        /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
        Config.set("Scoreboard.Toggle.On.Enable", true);
        Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Het scoreboard is &aaangezet"}));
        Config.set("Scoreboard.Toggle.Off.Enable", true);
        Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Het scoreboard is &cuitgezet"}));

        Config.set("Scoreboard.Changes.Enable", true);
        Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je scoreboard is nu &e%arg1%"}));
        Config.set("Scoreboard.Error-Changes.Enable", true);
        Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cHet scoreboard &6%arg1%&c bestaat niet"}));

        Config.set("Scoreboard.Keep-On.Enable", true);
        Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je houdt nu dit scoreboard"}));
        Config.set("Scoreboard.Keep-Off.Enable", true);
        Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je houdt dit scoreboard &cniet meer"}));

        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe hebt voor geen enkel scoreboard de permissie"}));

        /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
        Config.set("Warp.Tp.Self.Enable", true);
        Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent naar de warp &e%warp%&7 geteleporteerd"}));
        Config.set("Warp.Tp.Self-Delay.Enable", true);
        Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Wacht &4&l%second%&c seconde(n)&7 voor je wordt geteleporteerd"}));

        Config.set("Warp.Tp.Other.Enable", true);
        Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent door &b%player%&7 naar de warp &e%warp%&7 geteleporteerd"}));
        Config.set("Warp.Tp.Other-Sender.Enable", true);
        Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt &b%target%&7 naar de warp &e%warp%&7 geteleporteerd"}));
        Config.set("Warp.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Wacht &4&l%second%&c seconde(n)&7 tot deze speler wordt geteleporteerd"}));

        Config.set("Warp.List.Enable", true);
        Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bWarps:&e %warplist%"}));

        Config.set("Warp.No-Warp.Enable", true);
        Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEr is geen warp"}));

        Config.set("Warp.Set.Warp-Set.Enable", true);
        Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De warp &e%arg%&7 is gemaakt"}));
        Config.set("Warp.Set.Warp-Already-Exist.Enable", true);
        Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDeze naam bestaat al"}));

        Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
        Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDeze warp bestaat niet"}));
        Config.set("Warp.Del.Warp-Delete.Enable", true);
        Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De warp &e%warp%&7 is &cverwijderd"}));

        Config.set("Warp.Edit.Warp-Edited.Enable", true);
        Config.set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De plek van de warp &e%warp%&7 is bijgewerkt"}));

        /* ---------------- *
			 * SUICIDE COMMANDS *
			 * ---------------- */

        Config.set("Suicide.Self.Enable", true);
        Config.set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt jezelf gedood"}));

        Config.set("Suicide.Broadcast.Enable", true);
        Config.set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% heeft afscheid genomen van de wereld"}));

        /* --------------- *
			 * REPAIR COMMANDS *
			 * --------------- */

        Config.set("Repair.Repaired.Enable", true);
        Config.set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Het voorwerp &e%item% &7is gerepareerd"}));

        Config.set("Repair.Can-t-Repair.Enable", true);
        Config.set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDit voorwerp kan niet worden gerepareerd"}));

        /* ------------ *
			 * EXP COMMANDS *
			 * ------------ */

        Config.set("Exp.Add.Sender.Enable", true);
        Config.set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7ervaringspunten gegeven aan &b%target%"}));
        Config.set("Exp.Add.Target.Enable", true);
        Config.set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt &e%number_exp% &7ervaringspunten gekregen"}));

        Config.set("Exp.Set.Sender.Enable", true);
        Config.set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De ervaringspunten van &b%target%&7 zijn op &e%number_exp%&7 gezet"}));
        Config.set("Exp.Set.Target.Enable", true);
        Config.set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je ervaringspunten zijn op &e%number_exp%&7 gezet"}));

        Config.set("Exp.Take.Sender.Enable", true);
        Config.set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 is &e%number_exp%&7 ervaringspunten kwijt"}));
        Config.set("Exp.Take.Target.Enable", true);
        Config.set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je bent &e%number_exp%&7 ervaringspunten kwijt"}));
        Config.set("Exp.Take.Sender-Error.Enable", true);
        Config.set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe kunt &e%target%&c niet meer dan &6%target_exp_points%&c ervaringspunten afnemen"}));

        Config.set("Exp.Clear.Sender.Enable", true);
        Config.set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 heeft nu 0 ervaringspunten"}));
        Config.set("Exp.Clear.Target.Enable", true);
        Config.set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je ervaringspunten zijn op nul gezet"}));

        /* ------------------ *
			 * WORKBENCH COMMANDS *
			 * ------------------ */
        Config.set("WorkBench.Self.Enable", true);
        Config.set("WorkBench.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt je werkbank geopend"}));

        Config.set("WorkBench.Target.Enable", true);
        Config.set("WorkBench.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je werkbank is geopend door &e%player%"}));
        Config.set("WorkBench.Sender.Enable", true);
        Config.set("WorkBench.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt de werkbank voor &e%target%&7 geopend"}));

        /* ------------- *
			 * BURN COMMANDS *
			 * ------------- */
        Config.set("Burn.Target.Enable", true);
        Config.set("Burn.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je brandt &b%duration%&7 seconden door &e%player%"}));
        Config.set("Burn.Sender.Enable", true);
        Config.set("Burn.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je laat &e%target%&7 &b%duration%&7 seconden branden"}));

        /* -------------- *
			 * SKULL COMMANDS *
			 * -------------- */
        Config.set("Skull.Self.Enable", true);
        Config.set("Skull.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt je eigen hoofd gekregen"}));

        Config.set("Skull.Sender.Enable", true);
        Config.set("Skull.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je hebt het hoofd van &e%target%&7 gekregen"}));
        Config.set("Skull.Target.Enable", false);
        Config.set("Skull.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7 heeft je hoofd genomen"}));

        /*
         * Hat
         */
        Config.set("Hat.Self.Set.Enable", true);
        Config.set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Je nieuwe hoed staat op"}));
        Config.set("Hat.Self.Removed.Enable", true);
        Config.set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Je hoed is afgezet"}));

        Config.set("Hat.Other-Target.Set.Enable", true);
        Config.set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Je hebt een nieuwe hoed gekregen van &e%player%"}));
        Config.set("Hat.Other-Target.Removed.Enable", true);
        Config.set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Je hoed is afgezet door &e%player%"}));

        Config.set("Hat.Other-Sender.Set.Enable", true);
        Config.set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Je hebt &e%target%&7 een nieuwe hoed opgezet"}));
        Config.set("Hat.Other-Sender.Removed.Enable", true);
        Config.set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Je hebt de hoed van &e%target%&7 afgezet"}));

        Config.set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
        Config.set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cDit voorwerp kan niet als hoed worden opgezet"}));
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cJe draagt geen hoed die kan worden afgezet"}));

        /*
         * GetPos
         */
        Config.set("GetPos.Enable", true);
        Config.set("GetPos.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De positie van de speler is: &e%X% %Y% %Z%&7 in de wereld &b%world%"}));

        /*
         * Vanish stuff
         */
        Config.set("Vanish.Self.Enable", true);
        Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish is &aaangezet"}));
        Config.set("Vanish.Self-Disabled.Enable", true);
        Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish is &cuitgezet"}));
        /*Config.set("Vanish.Self-Still.Enable", true);
        Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bVanish staat nog aan"}));*/
        Config.set("Vanish.Other-Target.Enable", true);
        Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish is &aaangezet &7door &e%player%"}));
        Config.set("Vanish.Other-Target-Disabled.Enable", true);
        Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish is &cuitgezet &7door &e%player%"}));
        Config.set("Vanish.Other-Sender.Enable", true);
        Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish van &b%target%&7 is &aaangezet &7door &e%player%"}));
        Config.set("Vanish.Other-Sender-Disabled.Enable", true);
        Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vanish van &b%target%&7 is &cuitgezet &7door &e%player%"}));
        Config.set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aJe bent onzichtbaar (vanish)"}));

        /*
         * Clear inv stuff
         */
        Config.set("ClearInv.Self.Enable", true);
        Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je inventaris is leeggemaakt"}));
        Config.set("ClearInv.Other-Target.Enable", true);
        Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je inventaris is leeggemaakt door &e%player%"}));
        Config.set("ClearInv.Other-Sender.Enable", true);
        Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De inventaris van &e%target%&7 is leeggemaakt"}));

        /*
         * Spawn stuff
         */
        Config.set("Spawn.Tp.Self-Delay.Enable", true);
        Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Wacht &4&l%second%&c seconde(n)&7 voor je wordt geteleporteerd"}));
        Config.set("Spawn.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Wacht &4&l%second%&c seconde(n)&7 tot deze speler wordt geteleporteerd"}));

        Config.set("Spawn.List.Enable", true);
        Config.set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bSpawns:&e %spawnlist%"}));

        Config.set("Spawn.No-Spawn.Enable", true);
        Config.set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEr is geen spawn"}));

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

        Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"%prefix% &7O nee, je bent in de leegte gevallen"}));
        Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eScheldwoorden&8] &b%player% &7schreef &e%message%"}));
        Config.set("Anti-Spam.Repeat", java.util.Arrays.asList(new String[] {"%prefix% &cStuur niet twee keer hetzelfde bericht"}));
        Config.set("Anti-Spam.Caps", java.util.Arrays.asList(new String[] {"%prefix% &cTe veel hoofdletters in je bericht"}));
        Config.set("Anti-Advertising.Blocked", java.util.Arrays.asList(new String[] {"%prefix% &cGeen reclame in de chat: adressen van andere servers mogen niet"}));
        Config.set("Anti-Advertising.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eReclame&8] &b%player% &7wilde sturen: &e%message%"}));

        Config.set("LaunchPad.Cant-Use-Cooldown.Enable", true);
        Config.set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je kunt de launchpads nog niet opnieuw gebruiken"}));

        Config.set("Cancel-Tp.Warp.Enable", true);
        Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teleportatie &cgeannuleerd"}));
        Config.set("Cancel-Tp.Spawn.Enable", true);
        Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teleportatie &cgeannuleerd"}));

        Config.set("Custom-Join-Item.Error.FunGun.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.FunGun.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Wacht &4&l%timedelayfunguncji% &c&lseconde(n)&7!"}));

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
        		"     &l>> &e&o&lHulp bij de opties van de speler",
        		"",
        		" &8>> &7/option fly - &eVliegen aan- of uitzetten",
        		" &8>> &7/option doublejump - &eDubbele sprong aan- of uitzetten",
        		" &8>> &7/option speed - &eSnelheid aan- of uitzetten",
        		" &8>> &7/option flyspeed - &eVliegsnelheid aan- of uitzetten",
        		" &8>> &7/option jumpboost - &eSprongkracht aan- of uitzetten",
        		" &8>> &7/option autobc - &eAutomatische aankondigingen tonen of verbergen",
        		" &8>> &7/option pv - &eDe andere spelers tonen of verbergen",
        		"",
        		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));

        Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je dubbele sprong is &aaangezet"}));
        Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je dubbele sprong is &cuitgezet"}));

        Config.set("PlayerOption.Speed.Enable.Enable", true);
        Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je snelheid is &aaangezet"}));
        Config.set("PlayerOption.Speed.Disable.Enable", true);
        Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je snelheid is &cuitgezet"}));
        Config.set("PlayerOption.Speed.Set.Enable", true);
        Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je snelheid is op &e%arg1%&7 gezet"}));

        Config.set("PlayerOption.FlySpeed.Enable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je vliegsnelheid is &aaangezet"}));
        Config.set("PlayerOption.FlySpeed.Disable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je vliegsnelheid is &cuitgezet"}));
        Config.set("PlayerOption.FlySpeed.Set.Enable", true);
        Config.set("PlayerOption.FlySpeed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je vliegsnelheid is op &e%arg1%&7 gezet"}));

        Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je sprongkracht is &aaangezet"}));
        Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Je sprongkracht is &cuitgezet"}));

        Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De automatische aankondigingen worden &agetoond"}));
        Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De automatische aankondigingen worden &cverborgen"}));

        Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Poef! De spelers zijn verdwenen"}));
        Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7De spelers zijn nu zichtbaar"}));
        // Since 1.4.9: the third state of the player hider
        Config.set("PlayerOption.PlayerVisibility.STAFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.STAFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Alleen de staff en de VIP's zijn nu zichtbaar"}));
        // Since 1.5: the menus
        Config.set("Menus.Not-Found", java.util.Arrays.asList("%prefix% &cHet menu &e%arg% &cbestaat niet"));
        Config.set("Menus.Not-In-This-World", java.util.Arrays.asList("%prefix% &cDit menu kan in deze wereld niet worden geopend"));

        Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDe dubbele sprong staat uit in &6Cosmetics-Fun/DoubleJump.yml"}));
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDe dubbele sprong mag niet in deze wereld"}));

        Config.set("PlayerOption.Error.Option-Disabled.Enable", true);
        Config.set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDeze optie staat uit, je kunt haar niet wijzigen"}));
        //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
        //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cDeze optie staat al zo"}));
        Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
        Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Wacht &4&l%timedelaypvcji% &c&lseconde(n)&7!"}));

        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDe opties van de speler staan in deze wereld niet aan"}));
        /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lWacht &4%timedelaypvcommands% &c&lseconden!"}));*/

        /*
         *
         * OTHER STUFF
         *
         */

        Config.set("Error.No-Permissions.Enable", true);
        Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe mist de permissie: %noperm%"}));
        Config.set("Error.No-Spawn.Enable", true);
        Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDeze spawn bestaat niet"}));
        Config.set("Error.Change-Me.Enable", true);
        Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe moet de spawn/warp/enz. wijzigen bij &6%arg1%&c in &e%arg2%"}));
        Config.set("Error.No-Players.Enable", true);
        Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDe speler is niet online of bestaat niet"}));
        Config.set("Error.No-Page-Found.Enable", true);
        Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDe pagina is niet gevonden"}));
        Config.set("Error.No-Category.Enable", true);
        Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDeze categorie bestaat niet"}));
        Config.set("Error.Use-Number.Enable", true);
        Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cGeef een getal"}));
        Config.set("Error.Command-Disable.Enable", true);
        Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDit commando staat uit"}));
        // Since 1.4.5: an error in a command (written in the console), instead of "An internal error occurred"
        Config.set("Error.Command-Failed.Enable", true);
        Config.set("Error.Command-Failed.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDit commando is mislukt: de fout staat in de console, zeg het tegen een admin"}));
        Config.set("Error.Command-Wrong-Arguments.Enable", true);
        Config.set("Error.Command-Wrong-Arguments.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVerkeerde argumenten. Gebruik: &e%usage%"}));
        Config.set("Error.Argument-Missing.Enable", true);
        Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEr ontbreken een of meer argumenten"}));
        Config.set("Error.Not-A-Player.Enable", true);
        Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe bent geen speler"}));
        Config.set("Protection.Anti-Place", java.util.Arrays.asList(new String[] {"%prefix% &cJe kunt hier geen blokken plaatsen!"}));
        Config.set("Protection.Anti-Break", java.util.Arrays.asList(new String[] {"%prefix% &cJe kunt hier geen blokken breken!"}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}
