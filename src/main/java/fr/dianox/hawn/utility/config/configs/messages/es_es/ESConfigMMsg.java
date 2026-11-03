package fr.dianox.hawn.utility.config.configs.messages.es_es;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Spanish messages (Messages/es_ES), since Hawn 1.5.6: the texts of ConfigMMsg translated.
 */
public class ESConfigMMsg {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public ESConfigMMsg() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/es_ES/Messages.yml");
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
        Config.set("ClearChat.No-Reason", "no hay motivo");
        Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {" &4&l* &cEl chat se ha borrado porque %reason% &4&l*"}));
        Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {" &4&l* &cEl chat ha sido borrado por &6%player%&c porque %reason% &4&l*"}));
        Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"%prefix% &7Tu chat ha sido borrado &e%player%"}));
        Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"%prefix% &7Tu chat ha sido borrado &e%target%"}));
        Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"%prefix% &7El chat de &e%player%&7 ha sido borrado"}));

        Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"%prefix% &cNo puedes hablar, el chat está bloqueado"}));
        Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cEl chat ha sido bloqueado por &6%player% &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cEl chat ha sido bloqueado por &6%player%&c durante &6%minutes% minutos &4&m&l-<-=->-"}));
        Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bEl chat ha sido &adesbloqueado&b por &e%player% &3&m&l->-=-<-"}));

        Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Tu ping es de &e%ping%&7 ms"}));
        Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7El ping de &b%target%&7 es de &e%ping%&7 ms"}));

        Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cNo puedes hablar, el chat tiene un retraso de &6%DELAY%&c segundo(s)"}));
        Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7El retraso del chat se ha puesto en &e%DELAY%&7 segundo(s)",
        		"          &7&oNo olvides cambiar este valor en la configuración",
        		"          &7&oEl valor solo es válido mientras el servidor no se apague"}));
        Config.set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7El retraso del chat se ha &cdesactivado"}));

        Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eBroadcast&8]&r %broadcast%"}));

        /*
         * Weather and Time
         */
        Config.set("Weather.Set.Sun.Enable", true);
        Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"%prefix% &7La lluvia y la tormenta se han ido, &e¡viva el sol&7!"}));
        Config.set("Weather.Set.Rain.Enable", true);
        Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Has puesto la &blluvia en este mundo&7, &b¡cuidado con mojarte&7!"}));
        Config.set("Weather.Set.Thunder.Enable", true);
        Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"%prefix% &6&k!!!!&7 &eLa &etormenta anuncia desastres&7... &e¡Cuidado con los rayos &6&k!!!!"}));

        Config.set("Time.Set.Day.Enable", true);
        Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Es por la &emañana&7, hora de ir al colegio"}));
        Config.set("Time.Set.Night.Enable", true);
        Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"%prefix% &f&lFuera está oscuro. ¡Cuidado, la noche es oscura y alberga horrores!"}));

        /*
         * FLY COMMANDS
         */
        Config.set("Fly.Enable.Enable", true);
        Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo vuelo se ha &aactivado"}));
        Config.set("Fly.Enable-Other.Enable", true);
        Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo vuelo ha sido &aactivado&7 por &e%player%"}));
        Config.set("Fly.Enable-Other-Executor.Enable", true);
        Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El modo vuelo de &e%target%&7 se ha &aactivado"}));

        Config.set("Fly.Disable.Enable", true);
        Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo vuelo se ha &cdesactivado"}));
        Config.set("Fly.Disable-Other.Enable", true);
        Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo vuelo ha sido &cdesactivado&7 por &e%player%"}));
        Config.set("Fly.Disable-Other-Executor.Enable", true);
        Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El modo vuelo de &e%target%&7 se ha &cdesactivado"}));

        /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
        Config.set("Gamemode.Self.Survival.Enable", true);
        Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego se ha cambiado a &esupervivencia"}));
        Config.set("Gamemode.Self.Creative.Enable", true);
        Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego se ha cambiado a &ecreativo"}));
        Config.set("Gamemode.Self.Adventure.Enable", true);
        Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego se ha cambiado a &eaventura"}));
        Config.set("Gamemode.Self.Spectator.Enable", true);
        Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego se ha cambiado a &eespectador"}));

        Config.set("Gamemode.Other.Survival.Enable", true);
        Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego ha sido cambiado a &esupervivencia&7 por &b%player%"}));
        Config.set("Gamemode.Other.Creative.Enable", true);
        Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego ha sido cambiado a &ecreativo&7 por &b%player%"}));
        Config.set("Gamemode.Other.Adventure.Enable", true);
        Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego ha sido cambiado a &eaventura&7 por &b%player%"}));
        Config.set("Gamemode.Other.Spectator.Enable", true);
        Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu modo de juego ha sido cambiado a &eespectador&7 por &b%player%"}));

        Config.set("Gamemode.Other-Sender.Survival.Enable", true);
        Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El modo de juego de &b%player%&7 se ha cambiado a &esupervivencia"}));
        Config.set("Gamemode.Other-Sender.Creative.Enable", true);
        Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El modo de juego de &b%player%&7 se ha cambiado a &ecreativo"}));
        Config.set("Gamemode.Other-Sender.Adventure.Enable", true);
        Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El modo de juego de &b%player%&7 se ha cambiado a &eaventura"}));
        Config.set("Gamemode.Other-Sender.Spectator.Enable", true);
        Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El modo de juego de &b%player%&7 se ha cambiado a &eespectador"}));

        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYa estás en ese modo de juego"}));
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
        Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEl jugador %target% ya está en ese modo de juego"}));

        /* -------------- *
			 * GOTOP COMMANDS *
			 * -------------- */
        Config.set("Gotop.Enable", true);
        Config.set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has sido teletransportado al bloque más alto de tu posición"}));

        /* ------------- *
			 * FEED COMMANDS *
			 * ------------- */
        Config.set("Feed.Self.Enable", true);
        Config.set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has sido alimentado"}));

        Config.set("Feed.Other.Enable", true);
        Config.set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has sido alimentado por &e%player%"}));
        Config.set("Feed.Other-Sender.Enable", true);
        Config.set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 ha sido alimentado"}));

        /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
        Config.set("Heal.Self.Enable", true);
        Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has sido curado"}));

        Config.set("Heal.Other.Enable", true);
        Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has sido curado por &e%player%"}));
        Config.set("Heal.Other-Sender.Enable", true);
        Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 ha sido curado"}));

        /* ------------------- *
			 * ENDERCHEST COMMANDS *
			 * ------------------- */

        Config.set("EnderChest.Self.Enable", true);
        Config.set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has abierto tu cofre de ender"}));

        Config.set("EnderChest.Other-Sender.Enable", true);
        Config.set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has abierto el cofre de ender de &e%target%"}));

        /* --------------- *
			 * INVSEE COMMANDS *
			 * --------------- */

        Config.set("InvSee.Other-Sender.Enable", true);
        Config.set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has abierto el inventario de &e%target%"}));

        /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
        Config.set("Scoreboard.Toggle.On.Enable", true);
        Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El scoreboard se ha &aactivado"}));
        Config.set("Scoreboard.Toggle.Off.Enable", true);
        Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El scoreboard se ha &cdesactivado"}));

        Config.set("Scoreboard.Changes.Enable", true);
        Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu scoreboard actual es ahora &e%arg1%"}));
        Config.set("Scoreboard.Error-Changes.Enable", true);
        Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEl scoreboard &6%arg1%&c no existe"}));

        Config.set("Scoreboard.Keep-On.Enable", true);
        Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Ahora conservarás este scoreboard"}));
        Config.set("Scoreboard.Keep-Off.Enable", true);
        Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7&cYa no&7 conservarás este scoreboard"}));

        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
        Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cNo tienes permiso para ningún scoreboard"}));

        /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
        Config.set("Warp.Tp.Self.Enable", true);
        Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has sido teletransportado al warp &e%warp%"}));
        Config.set("Warp.Tp.Self-Delay.Enable", true);
        Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espera &4&l%second%&c segundo(s)&7 antes de ser teletransportado"}));

        Config.set("Warp.Tp.Other.Enable", true);
        Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has sido teletransportado al warp &e%warp%&7 por &b%player%"}));
        Config.set("Warp.Tp.Other-Sender.Enable", true);
        Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has teletransportado a &b%target%&7 al warp&e %warp%"}));
        Config.set("Warp.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espera &4&l%second%&c segundo(s)&7 antes de que este jugador sea teletransportado"}));

        Config.set("Warp.List.Enable", true);
        Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bLista de warps :&e %warplist%"}));

        Config.set("Warp.No-Warp.Enable", true);
        Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo siento, pero no hay ningún warp"}));

        Config.set("Warp.Set.Warp-Set.Enable", true);
        Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El warp se ha creado con el nombre &e%arg%"}));
        Config.set("Warp.Set.Warp-Already-Exist.Enable", true);
        Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEse nombre ya existe"}));

        Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
        Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEl warp no existe"}));
        Config.set("Warp.Del.Warp-Delete.Enable", true);
        Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El warp &e%warp%&7 se ha &celiminado"}));

        Config.set("Warp.Edit.Warp-Edited.Enable", true);
        Config.set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Se ha guardado la nueva ubicación del warp &e%warp%"}));

        /* ---------------- *
			 * SUICIDE COMMANDS *
			 * ---------------- */

        Config.set("Suicide.Self.Enable", true);
        Config.set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Te has suicidado"}));

        Config.set("Suicide.Broadcast.Enable", true);
        Config.set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% se ha despedido del mundo"}));

        /* --------------- *
			 * REPAIR COMMANDS *
			 * --------------- */

        Config.set("Repair.Repaired.Enable", true);
        Config.set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El objeto &e%item% &7se ha reparado"}));

        Config.set("Repair.Can-t-Repair.Enable", true);
        Config.set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo siento, pero no puedes reparar este objeto"}));

        /* ------------ *
			 * EXP COMMANDS *
			 * ------------ */

        Config.set("Exp.Add.Sender.Enable", true);
        Config.set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Se han añadido &e%number_exp% &7puntos a la experiencia total de &b%target%"}));
        Config.set("Exp.Add.Target.Enable", true);
        Config.set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Se han añadido &e%number_exp% &7puntos a tu experiencia total"}));

        Config.set("Exp.Set.Sender.Enable", true);
        Config.set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La experiencia total de &b%target%&7 es ahora &e%number_exp%"}));
        Config.set("Exp.Set.Target.Enable", true);
        Config.set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu experiencia total es ahora &e%number_exp%"}));

        Config.set("Exp.Take.Sender.Enable", true);
        Config.set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 ha perdido &e%number_exp%&7 puntos de experiencia"}));
        Config.set("Exp.Take.Target.Enable", true);
        Config.set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has perdido &e%number_exp%&7 puntos de experiencia"}));
        Config.set("Exp.Take.Sender-Error.Enable", true);
        Config.set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cNo puedes quitar más de &6%target_exp_points%&c al jugador &e%target%"}));

        Config.set("Exp.Clear.Sender.Enable", true);
        Config.set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 tiene ahora 0 puntos de experiencia"}));
        Config.set("Exp.Clear.Target.Enable", true);
        Config.set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tus puntos de experiencia se han borrado"}));

        /* ------------------ *
			 * WORKBENCH COMMANDS *
			 * ------------------ */
        Config.set("WorkBench.Self.Enable", true);
        Config.set("WorkBench.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has abierto tu mesa de trabajo"}));

        Config.set("WorkBench.Target.Enable", true);
        Config.set("WorkBench.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu mesa de trabajo ha sido abierta por &e%player%"}));
        Config.set("WorkBench.Sender.Enable", true);
        Config.set("WorkBench.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has abierto la mesa de trabajo de &e%target%&7"}));

        /* ------------- *
			 * BURN COMMANDS *
			 * ------------- */
        Config.set("Burn.Target.Enable", true);
        Config.set("Burn.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Estás ardiendo por culpa de &e%player%&7 durante &b%duration%&7 segundos"}));
        Config.set("Burn.Sender.Enable", true);
        Config.set("Burn.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Estás quemando a &e%target%&7 durante &b%duration%&7 segundos"}));

        /* -------------- *
			 * SKULL COMMANDS *
			 * -------------- */
        Config.set("Skull.Self.Enable", true);
        Config.set("Skull.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has obtenido tu propia cabeza"}));

        Config.set("Skull.Sender.Enable", true);
        Config.set("Skull.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Has obtenido la cabeza de &e%target%"}));
        Config.set("Skull.Target.Enable", false);
        Config.set("Skull.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%player%&7 ha cogido tu cabeza"}));

        /*
         * Hat
         */
        Config.set("Hat.Self.Set.Enable", true);
        Config.set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Te has puesto el nuevo sombrero"}));
        Config.set("Hat.Self.Removed.Enable", true);
        Config.set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Te has quitado el sombrero"}));

        Config.set("Hat.Other-Target.Set.Enable", true);
        Config.set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Has recibido un sombrero nuevo de &e%player%"}));
        Config.set("Hat.Other-Target.Removed.Enable", true);
        Config.set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Tu sombrero ha sido quitado por &e%player%"}));

        Config.set("Hat.Other-Sender.Set.Enable", true);
        Config.set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Has puesto un sombrero nuevo a &e%target%"}));
        Config.set("Hat.Other-Sender.Removed.Enable", true);
        Config.set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Has quitado el sombrero de &e%target%"}));

        Config.set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
        Config.set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cLo siento, pero no puedes ponerte un sombrero nuevo"}));
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
        Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cLo siento, pero no puedes quitarte el sombrero, no llevas ninguno"}));

        /*
         * GetPos
         */
        Config.set("GetPos.Enable", true);
        Config.set("GetPos.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% La ubicación del jugador es: &e%X% %Y% %Z%&7 en el mundo&b %world%"}));

        /*
         * Vanish stuff
         */
        Config.set("Vanish.Self.Enable", true);
        Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La invisibilidad se ha &aactivado"}));
        Config.set("Vanish.Self-Disabled.Enable", true);
        Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La invisibilidad se ha &cdesactivado"}));
        /*Config.set("Vanish.Self-Still.Enable", true);
        Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bLa invisibilidad sigue activada"}));*/
        Config.set("Vanish.Other-Target.Enable", true);
        Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La invisibilidad ha sido &aactivada&7 por&e %player%"}));
        Config.set("Vanish.Other-Target-Disabled.Enable", true);
        Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La invisibilidad ha sido &cdesactivada&7 por&e %player%"}));
        Config.set("Vanish.Other-Sender.Enable", true);
        Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La invisibilidad de &b%target%&7 ha sido &aactivada&7 por&e %player%"}));
        Config.set("Vanish.Other-Sender-Disabled.Enable", true);
        Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La invisibilidad de &b%target%&7 ha sido &cdesactivada&7 por&e %player%"}));
        Config.set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aEres invisible"}));

        /*
         * Clear inv stuff
         */
        Config.set("ClearInv.Self.Enable", true);
        Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu inventario se ha vaciado"}));
        Config.set("ClearInv.Other-Target.Enable", true);
        Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu inventario ha sido vaciado por &e%player%"}));
        Config.set("ClearInv.Other-Sender.Enable", true);
        Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7El inventario de &e%target%&7 se ha vaciado"}));

        /*
         * Spawn stuff
         */
        Config.set("Spawn.Tp.Self-Delay.Enable", true);
        Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espera &4&l%second%&c segundo(s)&7 antes de ser teletransportado"}));
        Config.set("Spawn.Tp.Other-Sender-Delay.Enable", true);
        Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Espera &4&l%second%&c segundo(s)&7 antes de que este jugador sea teletransportado"}));

        Config.set("Spawn.List.Enable", true);
        Config.set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bLista de spawns :&e %spawnlist%"}));

        Config.set("Spawn.No-Spawn.Enable", true);
        Config.set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo siento, pero no hay ningún spawn"}));

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

        Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"%prefix% &7Oh no, has intentado alcanzar el vacío"}));
        Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eSwear&8] &b%player% &7ha dicho &e%message%"}));
        Config.set("Anti-Spam.Repeat", java.util.Arrays.asList(new String[] {"%prefix% &cNo envíes el mismo mensaje otra vez"}));
        Config.set("Anti-Spam.Caps", java.util.Arrays.asList(new String[] {"%prefix% &cDemasiadas mayúsculas en tu mensaje"}));
        Config.set("Anti-Advertising.Blocked", java.util.Arrays.asList(new String[] {"%prefix% &cNada de publicidad en el chat: no se permiten direcciones de otros servidores"}));
        Config.set("Anti-Advertising.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eAd&8] &b%player% &7ha intentado enviar &e%message%"}));

        Config.set("LaunchPad.Cant-Use-Cooldown.Enable", true);
        Config.set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Lo siento, pero no puedes usar la plataforma de salto por ahora"}));

        Config.set("Cancel-Tp.Warp.Enable", true);
        Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teletransporte &ccancelado"}));
        Config.set("Cancel-Tp.Spawn.Enable", true);
        Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Teletransporte &ccancelado"}));

        Config.set("Custom-Join-Item.Error.FunGun.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.FunGun.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7¡Espera &4&l%timedelayfunguncji% &c&lsegundo(s)&7!"}));
        // The grappling hook (since 1.5.5)
        Config.set("Custom-Join-Item.Error.Grappling.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7¡Espera &4&l%timedelaygrapplingcji% &c&lsegundo(s)&7!"}));
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Enable", true);
        Config.set("Custom-Join-Item.Error.Grappling.Too-Far.Messages", java.util.Arrays.asList(new String[] {"%prefix% &c¡El gancho está demasiado lejos!"}));
        // The pearl mount (since 1.5.6)
        Config.set("Custom-Join-Item.Error.PearlMount.Time.Enable", true);
        Config.set("Custom-Join-Item.Error.PearlMount.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7¡Espera &4&l%timedelaypearlmountcji% &c&lsegundo(s)&7!"}));
        Config.set("Custom-Join-Item.Error.PearlMount.Height.Enable", true);
        Config.set("Custom-Join-Item.Error.PearlMount.Height.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa montura de perla solo funciona entre las alturas &e%arg1% &cy &e%arg2%"}));

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
        		"     &l>> &e&o&lAyuda de las opciones del jugador",
        		"",
        		" &8>> &7/option fly - &eActivar el vuelo",
        		" &8>> &7/option doublejump - &eActivar o desactivar el doble salto",
        		" &8>> &7/option speed - &eActivar o desactivar la velocidad",
        		" &8>> &7/option flyspeed - &eActivar o desactivar la velocidad de vuelo",
        		" &8>> &7/option jumpboost - &eActivar o desactivar el supersalto",
        		" &8>> &7/option autobc - &eMostrar u ocultar los anuncios automáticos",
        		" &8>> &7/option pv - &eActivar o desactivar la visibilidad de los jugadores",
        		"",
        		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));

        Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu doble salto se ha &aactivado"}));
        Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
        Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu doble salto se ha &cdesactivado"}));

        Config.set("PlayerOption.Speed.Enable.Enable", true);
        Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu velocidad se ha &aactivado"}));
        Config.set("PlayerOption.Speed.Disable.Enable", true);
        Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu velocidad se ha &cdesactivado"}));
        Config.set("PlayerOption.Speed.Set.Enable", true);
        Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu velocidad se ha puesto en &e%arg1%"}));

        Config.set("PlayerOption.FlySpeed.Enable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu velocidad de vuelo se ha &aactivado"}));
        Config.set("PlayerOption.FlySpeed.Disable.Enable", true);
        Config.set("PlayerOption.FlySpeed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu velocidad de vuelo se ha &cdesactivado"}));
        Config.set("PlayerOption.FlySpeed.Set.Enable", true);
        Config.set("PlayerOption.FlySpeed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu velocidad de vuelo se ha puesto en &e%arg1%"}));

        Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu supersalto se ha &aactivado"}));
        Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
        Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu supersalto se ha &cdesactivado"}));

        Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Los anuncios automáticos ahora son &avisibles"}));
        Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
        Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Los anuncios automáticos ahora están &cocultos"}));

        Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7¡Puf! Los jugadores han desaparecido"}));
        Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Los jugadores ahora son visibles"}));
        // Since 1.4.9: the third state of the player hider
        Config.set("PlayerOption.PlayerVisibility.STAFF.Enable", true);
        Config.set("PlayerOption.PlayerVisibility.STAFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Ahora solo son visibles el staff y los VIP"}));
        // Since 1.5: the menus
        Config.set("Menus.Not-Found", java.util.Arrays.asList("%prefix% &cEl menú &e%arg% &cno existe"));
        Config.set("Menus.Not-In-This-World", java.util.Arrays.asList("%prefix% &cEste menú no se puede abrir en este mundo"));

        Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEl doble salto está desactivado en &6Cosmetics-Fun/DoubleJump.yml"}));
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
        Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEl doble salto no está activado en este mundo"}));

        Config.set("PlayerOption.Error.Option-Disabled.Enable", true);
        Config.set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cHas desactivado esta opción, así que no puedes gestionar todas las opciones"}));
        //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
        //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cEsta opción ya está puesta o es la misma"}));
        Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
        Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7¡Espera &4&l%timedelaypvcji% &c&lsegundo(s)&7!"}));

        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
        Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLas opciones del jugador no están activadas en este mundo"}));
        /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&l¡Espera &4%timedelaypvcommands% &c&lsegundos!"}));*/

        /*
         *
         * OTHER STUFF
         *
         */

        Config.set("Error.No-Permissions.Enable", true);
        Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo siento, pero no tienes el permiso: %noperm%"}));
        Config.set("Error.No-Spawn.Enable", true);
        Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEl spawn no existe"}));
        Config.set("Error.Change-Me.Enable", true);
        Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cTienes que cambiar el spawn/warp/etc. en &6%arg1%&c en &e%arg2%"}));
        Config.set("Error.No-Players.Enable", true);
        Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEl jugador no está conectado o no existe"}));
        Config.set("Error.No-Page-Found.Enable", true);
        Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cNo se encuentra la página"}));
        Config.set("Error.No-Category.Enable", true);
        Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa categoría no existe"}));
        Config.set("Error.Use-Number.Enable", true);
        Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cIndica un número, por favor"}));
        Config.set("Error.Command-Disable.Enable", true);
        Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo siento, este comando está desactivado"}));
        // Since 1.4.5: an error in a command (written in the console), instead of "An internal error occurred"
        Config.set("Error.Command-Failed.Enable", true);
        Config.set("Error.Command-Failed.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cEste comando ha fallado: el error está escrito en la consola, avisa a un administrador"}));
        Config.set("Error.Command-Wrong-Arguments.Enable", true);
        Config.set("Error.Command-Wrong-Arguments.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cArgumentos incorrectos. Uso: &e%usage%"}));
        Config.set("Error.Argument-Missing.Enable", true);
        Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLo siento, pero faltan uno o dos argumentos"}));
        Config.set("Error.Not-A-Player.Enable", true);
        Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cNo eres un jugador"}));
        Config.set("Protection.Anti-Place", java.util.Arrays.asList(new String[] {"%prefix% &c¡Lo siento, no puedes poner bloques aquí!"}));
        Config.set("Protection.Anti-Break", java.util.Arrays.asList(new String[] {"%prefix% &c¡Lo siento, no puedes romper bloques aquí!"}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}
