package fr.dianox.hawn.utility.config.configs.messages.es_es;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Spanish messages (Messages/es_ES), since Hawn 1.5.6: the texts of ConfigMAdmin translated.
 */
public class ESAdminAMConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public ESAdminAMConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/es_ES/Admin.yml");
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
        Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList("&cNo eres un jugador"));
        Config.set("Error.Command.Hawn", java.util.Arrays.asList("&cError, prueba con /hawn"));
        Config.set("Error.Command.Delspawn", java.util.Arrays.asList("&c/hawn delspawn <spawn>"));
        Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList("&cEse nombre ya existe"));
        Config.set("Error.Argument-Missing", java.util.Arrays.asList("&cLo siento, pero faltan uno o dos argumentos."));
        Config.set("Error.No-Spawn", java.util.Arrays.asList("&cEl spawn no existe"));

	        /* ------------ *
	         * HELP COMMAND *
	         * ------------ */

	        Config.set("Command.Help.Hawn-SpawnManager", "Gestionar el spawn");
	        Config.set("Command.Help.Hawn-Reload", "Recargar algunos archivos de configuración");
	        Config.set("Command.Help.Hawn-Version", "Ver la versión del plugin");
	        Config.set("Command.Help.Hawn-Tps", "Ver los TPS del servidor");
	        Config.set("Command.Help.Hawn-Info", "Ver la información del servidor");
	        Config.set("Command.Help.Hawn-Build", "Saltarse la protección temporalmente");
	        Config.set("Command.Help.Hawn-Hooks", "Comprobar las dependencias del plugin y si están activadas");
	        Config.set("Command.Help.Hawn-Urgent", "Usar el modo urgente");
	        Config.set("Command.Help.Hawn-Maintenance", "Usar el modo mantenimiento");
	        Config.set("Command.Help.Hawn-Donors", "Ver quién ha donado al plugin");
	        Config.set("Command.Help.Hawn-About", "Mostrar algo de información");
	        Config.set("Command.Help.Hawn-Parse", "Ver lo que devuelve un placeholder");
	        Config.set("Command.Help.Hawn-NightVision", "Ver en la oscuridad");
	        Config.set("Command.Help.Hawn-NoClip", "Atravesar los bloques en modo creativo");
	        Config.set("Command.Help.Hawn-SlotView", "Ver el número de una casilla al hacer clic en ella");
	        Config.set("Command.Help.Hawn-EditPlayer", "Editar a un jugador");
	        Config.set("Command.Help.Hawn-Setup", "Configurar Hawn por primera vez");
	        Config.set("Command.Help.Hawn-Debug", "Los fallos de los archivos de configuración y los errores ocultos");
	        Config.set("Command.Help.Hawn-Rank", "Dar, quitar o consultar el rango de un jugador (Ranks.yml)");
	        Config.set("Command.Help.Broadcast", "Anunciar un mensaje");
	        Config.set("Command.Help.Warning", "Anunciar un aviso");
	        Config.set("Command.Help.Broadcast-Title", "Anunciar un título");
	        Config.set("Command.Help.Broadcast-ActionBar", "Anunciar un mensaje en la barra de acción");
	        Config.set("Command.Help.AdminPanel", "Acceder al panel de administración");
	        Config.set("Command.Help.CheckAccount", "Consultar a un jugador");
	        Config.set("Command.Help.ClearInv", "Vaciar el inventario de un jugador");
	        Config.set("Command.Help.InvSee", "Ver el inventario de un jugador");
	        Config.set("Command.Help.Ip", "Ver la IP de un jugador");
	        Config.set("Command.Help.List", "Ver la lista de jugadores del lobby");
	        Config.set("Command.Help.Spawn", "Ir al spawn");
	        Config.set("Command.Help.Spawn-Tp", "Teletransportar a un jugador a un spawn");
	        Config.set("Command.Help.DelSpawn", "Eliminar un spawn");
	        Config.set("Command.Help.SpawnList", "Ver la lista de spawns");
	        Config.set("Command.Help.SetSpawn", "Crear un spawn");
	        Config.set("Command.Help.SetWarp", "Crear un warp");
	        Config.set("Command.Help.Warp", "Ir al warp");
	        Config.set("Command.Help.WarpList", "Ver la lista de warps");
	        Config.set("Command.Help.DelWarp", "Eliminar el warp");
	        Config.set("Command.Help.EditWarp", "Cambiar la ubicación de un warp");
	        Config.set("Command.Help.Sun", "Despejar el tiempo");
	        Config.set("Command.Help.Rain", "Hacer que llueva en el mundo");
	        Config.set("Command.Help.Thunder", "Si te gusta el mal tiempo");
	        Config.set("Command.Help.Day", "Poner el día");
	        Config.set("Command.Help.Night", "Poner la noche");
	        Config.set("Command.Help.Fly", "Activar el modo vuelo");
	        Config.set("Command.Help.FlySpeed", "Cambiar la velocidad de vuelo de un jugador");
	        Config.set("Command.Help.Speed", "Cambiar o activar/desactivar la velocidad");
	        Config.set("Command.Help.Heal", "Curar a un jugador");
	        Config.set("Command.Help.Feed", "Alimentar a un jugador");
	        Config.set("Command.Help.Ping", "Ver el ping de un jugador");
	        Config.set("Command.Help.Vanish", "Hacer invisible a un jugador");
	        Config.set("Command.Help.Gamemode", "Cambiar el modo de juego de un jugador");
	        Config.set("Command.Help.Burn", "Quemar a un jugador");
	        Config.set("Command.Help.Cleargrounditems", "Borrar los objetos del suelo");
	        Config.set("Command.Help.Clearmobs", "Eliminar los mobs");
	        Config.set("Command.Help.EnderChest", "Abrir el cofre de ender");
	        Config.set("Command.Help.Exp", "Editar la experiencia de un jugador");
	        Config.set("Command.Help.Getpos", "Ver la ubicación de un jugador");
	        Config.set("Command.Help.Hat", "Ponerse un sombrero");
	        Config.set("Command.Help.Kickall", "Expulsar a todo el mundo");
	        Config.set("Command.Help.Repair", "Reparar un objeto");
	        Config.set("Command.Help.Skull", "Obtener la cabeza de un jugador");
	        Config.set("Command.Help.Suicide", "Suicidarse");
	        Config.set("Command.Help.Workbench", "Abrir la mesa de trabajo de un jugador");
	        Config.set("Command.Help.ClearChat", "Ver la ayuda de clearchat");
	        Config.set("Command.Help.DelayChat", "Poner un retraso en el chat");
	        Config.set("Command.Help.Gmute", "Silenciar el chat");
	        Config.set("Command.Help.Help", "Ver la ayuda personalizada, si está activada");
	        Config.set("Command.Help.Gotop", "Ir al bloque más alto de tu posición");
	        Config.set("Command.Help.Emoji", "Ver el menú de emojis");
	        Config.set("Command.Help.Scoreboard", "Activar o desactivar el scoreboard");
	        Config.set("Command.Help.Scoreboard-Set", "Cambiar el scoreboard actual");
	        Config.set("Command.Help.Scoreboard-Keep", "Conservar el scoreboard entre servidores");
	        Config.set("Command.Help.Scoreboard-List", "Ver todos los scoreboards registrados");
	        Config.set("Command.Help.Option", "Para las opciones principales del jugador");
	        Config.set("Command.Help.Hworld", "Gestionar los mundos");

        /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
        Config.set("Command.Server-Info.General", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformación del servidor (Todo)",
		            "        &3creado por Dianox",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersión de Java&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lActualización de Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersión del servidor&r&8:&r %serverversion%",
		            "",
		            "  &8→ &6&lMemoria",
		            "&7&lMemoria máx. &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLibre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "  &8→ &6&lCPU (Procesador)",
		            "&7&lCarga media&8:&r %averagecpuload%&8%",
		            "&7&lCarga actual&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "  &8→ &6&lEspacio en disco",
		            "&7&lDisco total&r&8:&r %totalspace%&8MB",
		            "&a&lDisco libre&7&l/&c&lDisco total&7&l utilizable&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Memory", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformación del servidor (Memoria)",
		            "",
		            "  &8→ &6&lMemoria",
		            "&7&lMemoria máx. &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLibre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.CPU", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformación del servidor (CPU)",
		            "",
		            "  &8→ &6&lCPU (Procesador)",
		            "&7&lCarga media&8:&r %averagecpuload%&8%",
		            "&7&lCarga actual&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Disk", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformación del servidor (Disco)",
		            "",
		            "  &8→ &6&lEspacio en disco",
		            "&7&lDisco total&r&8:&r %totalspace%&8MB",
		            "&a&lDisco libre&7&l/&c&lDisco total&7&l utilizable&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Server", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformación del servidor (Servidor)",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersión de Java&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lActualización de Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersión del servidor&r&8:&r %serverversion%",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));

        Config.set("Command.Server-Info.Tps", java.util.Arrays.asList("  &8→ &6&lTps&8: &r%tps%"));

        Config.set("Command.Version", java.util.Arrays.asList("  &8→ &6&lVersión de Hawn (creado por Dianox)&8: &r%gethawnversion%"));

        // Since 1.4.8: /hawn debug
        Config.set("Debug.Header", java.util.Arrays.asList("&8&m-----&r &6Hawn debug &8&m-----"));
        Config.set("Debug.No-Config-Error", java.util.Arrays.asList("&aNo hay ningún fallo en los archivos de configuración."));
        Config.set("Debug.Config-Errors", java.util.Arrays.asList("&e%arg 1% &7fallo(s) en los archivos de configuración:"));
        Config.set("Debug.Auto", java.util.Arrays.asList("&e%arg 1% &7opción(es) en AUTO dejadas a otro plugin:"));
        Config.set("Debug.No-Hidden-Error", java.util.Arrays.asList("&aNingún error oculto desde el inicio."));
        Config.set("Debug.Hidden-Errors", java.util.Arrays.asList("&e%arg 1% &7error(es) que Hawn ha superado desde el inicio:"));
        Config.set("Debug.Tip", java.util.Arrays.asList("&7Plugin.Debug: true en general.yml los escribe en la consola, con sus detalles."));
        // Since 1.4.8: /hawn rank
        Config.set("Ranks.Usage", java.util.Arrays.asList("%prefix% &7/hawn rank set <jugador> <rango> &8| &7remove <jugador> &8| &7info <jugador> &8| &7list"));
        Config.set("Ranks.Off", java.util.Arrays.asList("%prefix% &cLos rangos están desactivados (Ranks.yml, Ranks.Enable: false)"));
        Config.set("Ranks.Unknown-Rank", java.util.Arrays.asList("%prefix% &cEl rango &e%arg 1% &cno existe o está desactivado. Rangos: &7%arg 2%"));
        Config.set("Ranks.LuckPerms-Source", java.util.Arrays.asList("%prefix% &7Los rangos vienen de LuckPerms (Ranks.Source): cambia el grupo de &e%arg 1% &7en LuckPerms (&e/lp user %arg 1% parent set <grupo>&7)"));
        Config.set("Ranks.Set", java.util.Arrays.asList("%prefix% &e%arg 1% &7tiene ahora el rango &e%arg 2%"));
        Config.set("Ranks.Removed", java.util.Arrays.asList("%prefix% &e%arg 1% &7ya no tiene un rango dado a mano"));
        Config.set("Ranks.No-Rank-By-Hand", java.util.Arrays.asList("%prefix% &e%arg 1% &cno tiene ningún rango dado a mano (lo dan los permisos o LuckPerms)"));
        // 123: a Bedrock name with a dot (since 1.5.6)
        Config.set("Ranks.Name-With-Dot", java.util.Arrays.asList("%prefix% &e%arg 1% &ctiene un punto en el nombre (Bedrock): tiene que estar conectado para recibir un rango, guardado con su UUID"));
        Config.set("Ranks.Info", java.util.Arrays.asList("%prefix% &e%arg 1%&7: rango &e%arg 2% &7(%arg 3%) &8| &r%arg 4%"));
        Config.set("Ranks.Info-Offline", java.util.Arrays.asList("%prefix% &e%arg 1% &7está desconectado. Rango dado a mano: &e%arg 2%"));
        Config.set("Ranks.List-Header", java.util.Arrays.asList("%prefix% &e%arg 1% &7rango(s), el primero arriba de la lista de jugadores:"));
        Config.set("Ranks.List-Line", java.util.Arrays.asList("&8- &e%arg 1% &7(prioridad %arg 2%) &r%arg 3% &8| &7conectados: &f%arg 4%"));
        Config.set("Ranks.Source.HAND", "dado a mano");
        Config.set("Ranks.Source.PERMISSION", "por permiso");
        Config.set("Ranks.Source.LUCKPERMS", "de LuckPerms");
        Config.set("Ranks.Source.DEFAULT", "el rango por defecto");
        Config.set("Ranks.Source.NONE", "ninguno");
        // Since 1.5: /hawn menu
        Config.set("Command.Help.Hawn-Menu", "Abrir un menú de la carpeta Menus para ti o para un jugador, o listarlos");
        // The holograms (since 1.6)
        Config.set("Holograms.Usage", java.util.Arrays.asList("%prefix% &7/hawn hologram create <nombre> [texto] &8| &7delete <nombre> &8| &7movehere <nombre> &8| &7addline <nombre> <texto> &8| &7setline <nombre> <línea> <texto> &8| &7removeline <nombre> <línea> &8| &7list &8| &7enable <nombre> &8| &7disable <nombre>"));
        Config.set("Holograms.Created", java.util.Arrays.asList("%prefix% &7El holograma &e%arg 1% &7se ha creado donde estás"));
        Config.set("Holograms.Exists", java.util.Arrays.asList("%prefix% &cEl holograma &e%arg 1% &cya existe"));
        Config.set("Holograms.Not-Found", java.util.Arrays.asList("%prefix% &cEl holograma &e%arg 1% &cno existe"));
        Config.set("Holograms.Deleted", java.util.Arrays.asList("%prefix% &7El holograma &e%arg 1% &7se ha borrado"));
        Config.set("Holograms.Moved", java.util.Arrays.asList("%prefix% &7El holograma &e%arg 1% &7ahora está donde estás"));
        Config.set("Holograms.Changed", java.util.Arrays.asList("%prefix% &7El holograma &e%arg 1% &7ahora tiene &e%arg 2% &7línea(s)"));
        Config.set("Holograms.Bad-Line", java.util.Arrays.asList("%prefix% &cEl holograma &e%arg 1% &cno tiene la línea &e%arg 2%"));
        Config.set("Holograms.List", java.util.Arrays.asList("%prefix% &7Hologramas: &e%arg 1%"));
        Config.set("Holograms.None", java.util.Arrays.asList("%prefix% &7Ningún holograma en la carpeta Holograms"));
        Config.set("Holograms.Enabled", java.util.Arrays.asList("%prefix% &7El holograma &e%arg 1% &7está activado: se muestra"));
        Config.set("Holograms.Disabled", java.util.Arrays.asList("%prefix% &7El holograma &e%arg 1% &7está desactivado: se oculta"));
        Config.set("Holograms.Off-Tag", java.util.Arrays.asList("&8(desactivado)"));
        Config.set("Holograms.No-PacketEvents", java.util.Arrays.asList("%prefix% &cLos hologramas necesitan el plugin &ePacketEvents&c: modrinth.com/plugin/packetevents"));
        Config.set("Holograms.Bad-Name", java.util.Arrays.asList("%prefix% &cEl nombre de un holograma: letras, números, _ y -"));
        Config.set("Command.Help.Hawn-Hologram", "Mostrar un texto en un lugar, en el idioma de cada jugador");
        Config.set("Menus.Usage", java.util.Arrays.asList("%prefix% &7/hawn menu open <menú> [jugador] &8| &7list"));
        Config.set("Menus.Opened", java.util.Arrays.asList("%prefix% &7El menú &e%arg 1% &7está abierto para &e%arg 2%"));
        Config.set("Menus.Not-Opened", java.util.Arrays.asList("%prefix% &cEl menú &e%arg 1% &cno se ha abierto para &e%arg 2% &c(permiso o mundo)"));
        Config.set("Menus.List", java.util.Arrays.asList("%prefix% &7Menús: &e%arg 1%"));
        Config.set("Menus.None", java.util.Arrays.asList("%prefix% &7No hay ningún menú en la carpeta Menus"));
        Config.set("TPS.Check.15", java.util.Arrays.asList("&cTus TPS están por debajo de %arg 1%, haz algo para mejorar la estabilidad de tu lobby"));
        Config.set("TPS.Check.5", java.util.Arrays.asList("&cTus TPS están por debajo de %arg 1%, tu servidor podría apagarse, usa /stop para evitar problemas.", "&cATENCIÓN: ESTADO CRÍTICO DEL SERVIDOR"));

        /* --------------- *
         * RELOAD COMMANDS *
         * --------------- */
        Config.set("Command.Reload", java.util.Arrays.asList("&aConfiguración recargada"));

        Config.set("Command.Build-Bypass.On", java.util.Arrays.asList("&bAhora puedes saltarte todas las restricciones de construcción"));
        Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList("&cYa no puedes saltarte las restricciones de construcción"));


        /*
         * Vanish
         */
        Config.set("Vanish.Vanish-On.Enable", true);
        Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList("&7[ %player% ahora es invisible ]"));

        Config.set("Vanish.Vanish-Off.Enable", true);
        Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList("&7[ %player% ya no es invisible ]"));
        Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList("&7[ %player% ha hecho invisible a %target% ]"));
        Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList("&7[ %player% ha hecho visible a %target% ]"));

        Config.set("Maintenance.On", java.util.Arrays.asList("%prefix% &7Has &aactivado&7 el mantenimiento"));
        Config.set("Maintenance.Off", java.util.Arrays.asList("%prefix% &7Has &cdesactivado&7 el mantenimiento"));
        Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(" &4* &cEl mantenimiento está &eactivado&4 *"));
        Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(" &4* &cEl mantenimiento está &edesactivado&4 *"));

        Config.set("Urgent-mode.On", java.util.Arrays.asList("%prefix% &7Has &aactivado&7 el modo urgente"));
        Config.set("Urgent-mode.Off", java.util.Arrays.asList("%prefix% &7Has &cdesactivado&7 el modo urgente"));
        Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(" &4* &cEl modo urgente está &eactivado&4 *"));
        Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(" &4* &cEl modo urgente está &edesactivado&4 *"));
        Config.set("Urgent-mode.Zip", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cSe ha hecho una copia de seguridad de Hawn"));
        Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cError, tienes que estar en la consola para desactivar el modo urgente"));
        Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cLo siento, pero no puedes usar este comando"));
        Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c%player% ha hecho un cambio en el panel de administración",
		            "%arg1% en el archivo %arg2%"));
        Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cTodos los plugins se han desactivado"));
        Config.set("Urgent-mode.Ops-Removed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c%arg1% operador(es) retirado(s) hasta el final del modo urgente"));
        Config.set("Urgent-mode.Ops-Restored", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7%arg1% operador(es) devuelto(s)"));
        Config.set("Urgent-mode.Lockdown-Command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cEl servidor está en modo urgente: los comandos están desactivados"));
        Config.set("Urgent-mode.Lockdown-Chat", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cEl servidor está en modo urgente: el chat está desactivado"));
        Config.set("Urgent-mode.Zip-Failed", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cLa copia de seguridad ha fallado, mira la consola"));
        Config.set("Urgent-mode.Console-Only", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cEl modo urgente solo se puede iniciar desde la consola"));
        Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7Todos los plugins se han &aactivado",
		            "&ePor favor, hace falta reiniciar para evitar problemas"));

        Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList("%prefix% &e%player% ha intentado usar este comando: &b%arg1%"));

        Config.set("Command.SlotView.On", java.util.Arrays.asList("%prefix% &7Ahora puedes &aver&7 el número de cada casilla de un menú o inventario al hacer clic en ella"));
        Config.set("Command.SlotView.Off", java.util.Arrays.asList("%prefix% &7El SlotView está &cdesactivado"));

        Config.set("Command.IP", java.util.Arrays.asList("%prefix% &7La IP del jugador es: &e%getplayerip%"));

        Config.set("Command.Kickall", java.util.Arrays.asList("%prefix% &7Todos los jugadores han sido expulsados"));

        Config.set("Command.ClearGroundItems", java.util.Arrays.asList("%prefix% &7Se han borrado todos los objetos"));

        Config.set("Command.ClearMobs", java.util.Arrays.asList("%prefix% &7Se han eliminado todos los mobs"));

        Config.set("Command.CheckAccount", java.util.Arrays.asList("",
		            "  &8→ &r&lInformación del jugador&8:&r &b%target%",
		            "&7&lFecha de conexión&r&8:&e %hawn_player_join_date%",
		            "&7&lPrimera conexión&r&8:&e %hawn_player_first_join_date%",
		            "&7&lIp&r&8:&e %player_ip%",
		            "",
		            "  &8→ &r&lOpciones&8:&r &8(&aVerde &8=&7 true / &cRojo&8 = &7false&8)",
		            "&7&lVisibilidad de jugadores: %pv_point%",
		            "&7&lVelocidad: %ps_point% &7(&e%ps_number%&7)",
		            "&7&lVuelo: %pof_point%",
		            "&7&lDoble salto: %dj_point%",
		            "&7&lAnuncios automáticos: %ab_point%",
		            "&7&lInvisible: %v_point%",
		            "&7&lConservar scoreboard: %ksb_point% &8(&e%scorename%&8)",
		            "&7&lSupersalto: %jb_point%",
		            "",
		            "&7&lModo de juego: &e%gm_number%",
		            ""));

        // List

        Config.set("Command.List.Part-One", java.util.Arrays.asList("§8//§7§m---------------§r§8\\\\ §3[§bList§3] §8//§7§m---------------§r§8\\\\",
		            " ",
		            "  &8→ &6&lPágina %number%",
		            " "));

        Config.set("Command.List.Part-Two", java.util.Arrays.asList("§8\\\\§7§m---------------§r§8// §3[§bList§3] §8\\\\§7§m---------------§r§8//"));

        Config.set("Command.List.Gui.Other.Page.Next", "&bSiguiente");
        Config.set("Command.List.Gui.Other.Page.Back", "&bAnterior");
        Config.set("Command.List.Gui.Other.Back.PanelAdmin", "&c&lVolver al panel de administración");
        Config.set("Command.List.Gui.Player.Survival", "&cSURVIVAL");
        Config.set("Command.List.Gui.Player.Spectator", "&eESPECTADOR");
        Config.set("Command.List.Gui.Player.Creative", "&6CREATIVO");
        Config.set("Command.List.Gui.Player.Adventure", "&aAVENTURA");
        Config.set("Command.List.Gui.Player.Gamemode", "&7Modo de juego: ");
        Config.set("Command.List.Gui.Player.World", "&7Mundo: &e");
        Config.set("Command.List.Gui.Player.LeftClick", "&eClic izquierdo&7 para editar al jugador");

        // EDIT PLAYER

        Config.set("Command.EditPlayer.Gui.Gamemode.LeftClick", "&eClic izquierdo&7 para cambiar el modo de juego del jugador");
        Config.set("Command.EditPlayer.Gui.Gamemode.Survival", "&cSURVIVAL");
        Config.set("Command.EditPlayer.Gui.Gamemode.Spectator", "&eESPECTADOR");
        Config.set("Command.EditPlayer.Gui.Gamemode.Creative", "&6CREATIVO");
        Config.set("Command.EditPlayer.Gui.Gamemode.Adventure", "&aAVENTURA");

        Config.set("Command.EditPlayer.Gui.ClearInv.LeftClick", "&eClic izquierdo&7 para vaciar el inventario y la armadura del jugador");
        Config.set("Command.EditPlayer.Gui.ClearInv.Item-Name", "&6Vaciar el inventario");

        Config.set("Command.EditPlayer.Gui.Teleport.LeftClick", "&eClic izquierdo&7 para teletransportarte al jugador");
        Config.set("Command.EditPlayer.Gui.Teleport.Item-Name", "&6Teletransportarse a este jugador");

        Config.set("Command.EditPlayer.Gui.MoreSoon.Item-Name", "Más pronto");

        Config.set("Command.EditPlayer.Gui.BackToPlayerList.Item-Name", "&cVolver a la lista de jugadores");

        // Since 1.5.3: the new items of Edit Player
        Config.set("Command.EditPlayer.Gui.Gamemode.Lore", java.util.Arrays.asList("", "&7Clic izquierdo: el modo siguiente", "&7Clic derecho: el anterior"));
        Config.set("Command.EditPlayer.Gui.Head.Lore", java.util.Arrays.asList("&7Mundo: &f%world%", "&7Modo de juego: &f%gamemode%", "&7Rango: &f%rank%"));
        Config.set("Command.EditPlayer.Gui.On", "&aactivado");
        Config.set("Command.EditPlayer.Gui.Off", "&cdesactivado");
        Config.set("Command.EditPlayer.Gui.Toggle.Lore", java.util.Arrays.asList("", "&7Haz clic para cambiarlo"));
        Config.set("Command.EditPlayer.Gui.Fly.Item-Name", "&eVuelo: %value%");
        Config.set("Command.EditPlayer.Gui.Vanish.Item-Name", "&eInvisible: %value%");
        Config.set("Command.EditPlayer.Gui.Walk-Speed.Item-Name", "&eVelocidad al andar: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Fly-Speed.Item-Name", "&eVelocidad de vuelo: &f%value%/10");
        Config.set("Command.EditPlayer.Gui.Speed.Lore", java.util.Arrays.asList("", "&7Clic izquierdo: &a+1&7, clic derecho: &c-1", "&7Shift + clic: volver a lo normal"));
        Config.set("Command.EditPlayer.Gui.Heal.Item-Name", "&eCurar y alimentar");
        Config.set("Command.EditPlayer.Gui.Heal.Lore", java.util.Arrays.asList("", "&7Clic izquierdo: curar", "&7Clic derecho: alimentar"));
        Config.set("Command.EditPlayer.Gui.Teleport-Here.Item-Name", "&6Traer a este jugador hacia ti");
        Config.set("Command.EditPlayer.Gui.Teleport-Here.LeftClick", "&eClic izquierdo&7 para teletransportar al jugador hacia ti");
        Config.set("Command.EditPlayer.Gui.Rank.Item-Name", "&eRango dado a mano: &f%value%");
        Config.set("Command.EditPlayer.Gui.Rank.Lore", java.util.Arrays.asList("", "&7Clic izquierdo: el rango siguiente", "&7Clic derecho: el anterior", "&7(- : sin rango a mano)"));
        Config.set("Command.EditPlayer.Gui.Rank.LuckPerms", java.util.Arrays.asList("", "&7Los rangos vienen de LuckPerms:", "&7cambia allí el grupo del jugador"));
        Config.set("Command.EditPlayer.Gui.Hider.Item-Name", "&eA quién ve el jugador: %value%");
        Config.set("Command.EditPlayer.Gui.Hider.Everyone", "&atodos");
        Config.set("Command.EditPlayer.Gui.Hider.Staff", "&estaff y VIP");
        Config.set("Command.EditPlayer.Gui.Hider.Nobody", "&cnadie");
        Config.set("Command.EditPlayer.Gui.Hider.Lore", java.util.Arrays.asList("", "&7Clic: el estado siguiente"));
        Config.set("Command.EditPlayer.Changed", java.util.Arrays.asList("%prefix% &e%arg2% &8» %arg1%"));

        //

        Config.set("Command.No-Clip.Enable", java.util.Arrays.asList("%prefix% &7El No clip está &aactivado"));

        Config.set("Command.No-Clip.Disable", java.util.Arrays.asList("%prefix% &7El No clip está &cdesactivado"));

        Config.set("Command.NightVision", java.util.Arrays.asList("%prefix% &7Ahora puedes ver en la oscuridad"));
        Config.set("Command.NightVision-Off", java.util.Arrays.asList("%prefix% &7Ya no ves en la oscuridad"));

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList("&cNo has puesto nombre a este spawn, se ha elegido un nombre automático",
		            "§eSpawn creado con el nombre %spawnName%"));

        Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList("§eSpawn creado con el nombre %spawnName%"));

        Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList("&bEl spawn &e%spawn%&b ha sido eliminado"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}
