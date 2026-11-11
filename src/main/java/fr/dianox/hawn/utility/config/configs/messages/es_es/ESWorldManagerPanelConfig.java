package fr.dianox.hawn.utility.config.configs.messages.es_es;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * The Spanish messages (Messages/es_ES), since Hawn 1.5.6: the texts of WorldManagerPanelConfig translated.
 */
public class ESWorldManagerPanelConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public ESWorldManagerPanelConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/es_ES/WorldManager.yml");
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
        Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList("[send-title]: &cImportación iniciada //n &7Espera, por favor..."));
        Config.set("Gui.Import.Importing-With-A-Command", java.util.Arrays.asList("%prefix% &7Usa un comando en su lugar: /hw import nombredelmundo"));

        Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(
        		"%prefix% &7El mundo %arg1% se ha cargado",
		            "[send-title]: &3Importación terminada //n &7Puedes entrar en este mundo"));

        Config.set("Gui.Unload", java.util.Arrays.asList("%prefix% &7El mundo %arg1% se ha descargado"));
        // Since 1.4.7
        Config.set("Gui.Unload-Moved", java.util.Arrays.asList("%prefix% &7El mundo &e%arg1% &7se ha descargado: ahora estás en &e%arg2%"));
        Config.set("Gui.Import.Failed", java.util.Arrays.asList("%prefix% &cNo se ha podido cargar el mundo &e%arg1%&c, mira la consola"));
        // Since 1.6
        Config.set("Folia-Disabled", java.util.Arrays.asList("%prefix% &cEl World Manager está desactivado en Folia: Folia no puede crear ni descargar un mundo mientras el servidor funciona"));

        // tp
        Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList("%prefix% &cEl teletransporte ha fallado",
		            "%prefix% &cYa estás en ese mundo"));

        Config.set("Gui.Tp.Success", java.util.Arrays.asList("%prefix% &7Has sido teletransportado al mundo &e%arg1%"));

        // create
        Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList("[send-title]: &eElige un nombre de mundo //n &7Escríbelo en el chat"));
        Config.set("Gui.Create.Choose-A-Generator", java.util.Arrays.asList("[send-title]: &eElige un generador //n &7Escríbelo en el chat"));

        Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList("[send-title]: &cCreando el mundo&7..."));

        Config.set("Gui.Create.World-Created", java.util.Arrays.asList("%prefix% &7Se ha creado el mundo &e%arg1%",
                "[send-title]: &3Mundo creado //n &7Puedes entrar en este mundo"));

        Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList("%prefix% &7Ya no estás creando un mundo..."));

        // Delete
        Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList("%prefix% &cSe ha eliminado el mundo &e%arg1%"));
        Config.set("Gui.Delete.Confirm-Command", java.util.Arrays.asList("%prefix% &cEsto elimina el mundo &e%arg1% &cy su carpeta, sin vuelta atrás. Escribe &e/hw delete %arg1% confirm &cpara hacerlo"));
        Config.set("Error.Protected-World", java.util.Arrays.asList("%prefix% &cNo se puede eliminar el mundo &e%arg1%&c: el servidor lo necesita"));
        Config.set("Error.Unload-Failed", java.util.Arrays.asList("%prefix% &cNo se ha podido descargar el mundo &e%arg1%&c: no se ha eliminado nada"));
        Config.set("Gui.Default-World.Changed", java.util.Arrays.asList("%prefix% &e%arg1% &7es ahora el mundo por defecto: sustituye a &e%arg2% &7en &e%arg3% &7opción(es) de &e%arg4% &7archivo(s)"));
        Config.set("Gui.Default-World.Already", java.util.Arrays.asList("%prefix% &e%arg1% &7ya es el mundo por defecto"));
        Config.set("Gui.Default-World.Shift-Click", java.util.Arrays.asList("%prefix% &eShift + clic &7para hacer de &e%arg1% &7el mundo por defecto"));
        Config.set("Gui.Default-World.Conflict", java.util.Arrays.asList("%prefix% &6%arg1%: &e%arg2% &6ya tenía sus propias opciones, las de &e%arg3% &6se conservan junto a ellas"));
        Config.set("Gui.Default-World.Spawn-Elsewhere", java.util.Arrays.asList("%prefix% &6El spawn por defecto &e%arg1% &6sigue en &e%arg2%&6: crea uno en el nuevo mundo con &e/setspawn"));
        Config.set("Gui.Default-World.Yaml-Error", java.util.Arrays.asList("%prefix% &c%arg1% tiene un error de YAML: no se ha cambiado"));
        Config.set("Command.Default-World.Current", java.util.Arrays.asList("%prefix% &7El mundo por defecto es &e%arg1% &7(&e/hw default <mundo> &7para cambiarlo)"));
        Config.set("Error.Default-World", java.util.Arrays.asList("%prefix% &cEl mundo &e%arg1% &ces el mundo por defecto de Hawn: elige antes otro mundo por defecto"));
        Config.set("Gui.Other.Default-World.Title", "&6Mundo por defecto");
        Config.set("Gui.Other.Default-World.Current", java.util.Arrays.asList("&7Este es el mundo por defecto de Hawn:", "&7el que aparece en las listas de mundos", "&7de los archivos de configuración"));
        Config.set("Gui.Other.Default-World.Change", java.util.Arrays.asList("&7Ahora: &e%arg1%", "&eShift + clic&7 para hacer de este mundo", "&7el mundo por defecto: sustituye a &e%arg1%", "&7en las listas de mundos de los archivos"));
        Config.set("Gui.Other.Main.Default-World", "&6Mundo por defecto");

        Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList("%prefix% &cHa pasado algo raro... no pasa nada, es un error, ignóralo..."));

        // Modify World
        Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList("%prefix% &7Se ha cambiado la hora del mundo &e%arg1%"));

        Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList("%prefix% &7El tiempo del mundo &e%arg1%&7 se ha cambiado a sol"));

        Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList("%prefix% &7El tiempo del mundo &e%arg1%&7 se ha cambiado a lluvia"));

        Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList("%prefix% &7El tiempo del mundo &e%arg1%&7 se ha cambiado a tormenta"));

        // M. W. Dif.
        Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList("%prefix% &7La dificultad del mundo &e%arg1%&7 se ha cambiado a pacífica"));

        Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList("%prefix% &7La dificultad del mundo &e%arg1%&7 se ha cambiado a fácil"));

        Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList("%prefix% &7La dificultad del mundo &e%arg1%&7 se ha cambiado a normal"));

        Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList("%prefix% &7La dificultad del mundo &e%arg1%&7 se ha cambiado a difícil"));

        // Other
        Config.set("Gui.Other.Generator", "&7Generador");
        Config.set("Gui.Other.Generator-Page.Void-Generator", "&7Generador vacío");
        Config.set("Gui.Other.Generator-Page.Custom-Generator", "&7Escribe tu propio generador");
        // Since 1.4
        Config.set("Gui.Create.Failed", java.util.Arrays.asList("%prefix% &cNo se ha podido crear el mundo &e%arg1%&c, mira la consola"));
        Config.set("Gui.Modify-World.Nothing-Selected", java.util.Arrays.asList("%prefix% &7Haz clic primero en una opción y luego en el cartel"));
        Config.set("Gui.Other.Generator-Default", "&7Por defecto");
        Config.set("Gui.Other.Generator-Help", java.util.Arrays.asList("&8Clic izquierdo: por defecto o vacío", "&8Clic derecho: escribe un generador en el chat"));
        Config.set("Gui.Other.Info.Title", "&eInformación");
        Config.set("Gui.Other.Info.Players", "&7Jugadores:");
        Config.set("Gui.Other.Info.Environment", "&7Entorno:");
        Config.set("Gui.Other.Info.Generator", "&7Generador:");
        Config.set("Gui.Other.Info.Chunks", "&7Chunks cargados:");
        Config.set("Gui.Other.Info.Entities", "&7Entidades:");
        Config.set("Gui.Other.Info.Spawn", "&7Spawn:");
        Config.set("Gui.Other.Info.Time", "&7Hora:");
        Config.set("Gui.Other.Info.PvP", "&7PvP:");
        Config.set("Gui.Other.Info.Seed", "&7Semilla:");
        // Since 1.4.4
        Config.set("Gui.Other.Size-Pending", "&7...");
        Config.set("Gui.Create.Generator-Cancelled", java.util.Arrays.asList("%prefix% &7No has escrito ningún generador: se mantiene el de por defecto"));
        Config.set("Error.Unload-Protected", java.util.Arrays.asList("%prefix% &cNo se puede descargar el mundo &e%arg1%&c: el servidor lo necesita"));
        Config.set("Command.Unload.Failed", java.util.Arrays.asList("%prefix% &cNo se ha podido descargar el mundo &e%arg1%&c: no debe haber ningún jugador en él"));
        Config.set("Gui.Other.WorldType.World-Type", "&7Tipo de mundo:");
        Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
        Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
        Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");

        Config.set("Gui.Other.WorldFace.World-Face", "&7Aspecto del mundo:");
        Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
        Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
        Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
        Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");

        Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Sol");
        Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Lluvia");
        Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Tormenta");
        Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Tiempo actual:");

        Config.set("Gui.Other.Difficulty.Peaceful", "PEACEFUL");
        Config.set("Gui.Other.Difficulty.Easy", "EASY");
        Config.set("Gui.Other.Difficulty.Normal", "NORMAL");
        Config.set("Gui.Other.Difficulty.Hard", "HARD");

        Config.set("Gui.Other.Name", "Nombre:");
        Config.set("Gui.Other.Players", "Jugadores:");
        Config.set("Gui.Other.Size", "Tamaño:");
        Config.set("Gui.Other.Difficulty-Main", "Dificultad:");
        Config.set("Gui.Other.Difficulty-Two", "Dificultad");
        Config.set("Gui.Other.Environment", "Entorno:");
        Config.set("Gui.Other.World-Time", "Hora del mundo");
        Config.set("Gui.Other.World-Weather", "Tiempo del mundo");
        Config.set("Gui.Other.Done", "&f&lHecho");
        Config.set("Gui.Other.Main.Line-One", "&eClic izquierdo&7 para entrar en el mundo");
        Config.set("Gui.Other.Main.Line-Two", "&eClic derecho&7 para cambiar los ajustes del mundo");
        Config.set("Gui.Other.Main.Line-Three", "&eShift &7y &eclic derecho&7 para eliminar el mundo");
        Config.set("Gui.Other.Main.Line-Four", "&7El mundo no está cargado ahora");
        Config.set("Gui.Other.Main.Line-One-Second", "&eClic izquierdo&7 para cargar el mundo");
        Config.set("Gui.Other.Unload.Name", "&cDescargar el mundo");
        Config.set("Gui.Other.Unload.Lore", "&7Hasta el próximo reinicio: los jugadores que estén en él van al mundo por defecto");
        Config.set("Gui.Other.Page.Back", "&bAnterior");
        Config.set("Gui.Other.Page.Next", "&bSiguiente");
        Config.set("Gui.Other.Create-a-new-world", "&f&lCrear un mundo nuevo");
        Config.set("Gui.Other.Back.PanelAdmin", "&c&lVolver al panel de administración");
        Config.set("Gui.Other.Back.Menu", "&c&lVolver al menú");
        Config.set("Gui.Other.Back.Main-Menu", "&c&lVolver al menú principal");
        Config.set("Gui.Other.Delete.Confirm", "&aSé lo que hago");
        Config.set("Gui.Other.Delete.No", "&cAl final... no");

        /*
         * Command
         */
        Config.set("Command.Unload.Error", java.util.Arrays.asList("%prefix% &cError, no se puede descargar este mundo"));
        Config.set("Command.Other.World", "Mundo:");
        Config.set("Command.Other.Type", "Tipo:");

        Config.set("Error.NotGoodName", java.util.Arrays.asList("%prefix% &cError de Hawn, no se permite un nombre de mundo con ese formato",
		            "%prefix% &cLos únicos formatos de nombre permitidos son: a-z | A-Z | 0-9 | _ | -"));

        Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList("%prefix% &cNo has escrito un nombre de mundo"));

        Config.set("Error.WorldCreation", java.util.Arrays.asList("%prefix% &cTe has olvidado algo al escribir el comando..."));

        Config.set("Error.World-Already-Exist", java.util.Arrays.asList("%prefix% &cEste mundo ya existe"));

        Config.set("Error.World-Not-Exist", java.util.Arrays.asList("%prefix% &cEste mundo no existe"));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}
