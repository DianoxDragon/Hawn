package fr.dianox.hawn.utility.config.configs.messages.es_es;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * The Spanish messages (Messages/es_ES), since Hawn 1.5.6: the texts of SetupLangFile translated.
 */
public class ESSetupLangFile {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public ESSetupLangFile() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/es_ES/SetupLang.yml");
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
		Config.set("SetupLanguage.Close-Inventory", "&cNo necesito el setup");
		Config.set("SetupLanguage.Done", "&aSí, continuar");
		Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Idioma cambiado a %arg 1%"));

		Config.set("SetupWorld.Close-Inventory", "&cPodemos parar el setup aquí");
		Config.set("SetupWorld.Set-Up-World", "&aSí, elijamos un mundo");
		Config.set("SetupWorld.Done", "&aSí, continuemos");
		Config.set("SetupWorld.Info", "&eElige un mundo");
		Config.set("SetupWorld.WARNING", Collections.singletonList("&cNO TE MUEVAS"));
		Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Mundo por defecto cambiado a %arg 1%"));
		Config.set("SetupWorld.Line-1", "&eClic izquierdo&7 para elegir este mundo");

		Config.set("SetupSpawn.Close-Inventory", "&cPodemos parar el setup aquí");
		Config.set("SetupSpawn.Set-Up-Spawn", "&6Sí, creemos un spawn");
		Config.set("SetupSpawn.Done", "&aSí, continuemos");
		Config.set("SetupSpawn.Info", "&eCrear un spawn");
		Config.set("SetupSpawn.WARNING", Collections.singletonList("&cSigues en el setup, no olvides crear un spawn para terminarlo"));
		Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Spawn por defecto cambiado a %arg 1%"));

		// Since 1.4.8: the Previous / Next buttons, the teleport before the spawn
		Config.set("Setup.Previous", "&7« Paso anterior");
		Config.set("Setup.Next", "&7Paso siguiente »");
		Config.set("SetupSpawn.Teleported", Collections.singletonList("%prefix% &7Estás en &e%arg 1%&7, el mundo por defecto: ponte donde debe estar el spawn y haz clic en el bloque de oro"));
		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Reinicia el servidor, por favor"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6Sigues en el setup &7(&e/hawn setup &7para abrirlo otra vez)"));

		// Since 1.5.1: no restart at the end, a confirmation before running it again, the page of the modules
		Config.set("Setup.Finished", Collections.singletonList("%prefix% &aSetup terminado: los cambios se han aplicado, no hace falta reiniciar"));
		Config.set("Setup.Closed", Collections.singletonList("%prefix% &7Setup cerrado, no se ha cambiado nada"));
		Config.set("SetupConfirm.Info", "&e¿Volver a ejecutar el setup de Hawn?");
		Config.set("SetupConfirm.Info-Lore", java.util.Arrays.asList("&7El idioma, los módulos, el mundo por defecto", "&7y el spawn por defecto se pueden volver a cambiar"));
		Config.set("SetupConfirm.Yes", "&aSí, volver a ejecutar el setup");
		Config.set("SetupConfirm.No", "&cNo, cancelar");
		Config.set("SetupModules.Info", "&eActivar o desactivar los módulos");
		Config.set("SetupModules.Info-Lore", java.util.Arrays.asList("&7Se aplica al final del setup, sin reiniciar", "&7Todo lo demás está en los archivos de Hawn"));
		Config.set("SetupModules.Close-Inventory", "&cPodemos parar el setup aquí");
		Config.set("SetupModules.On", "&aactivado");
		Config.set("SetupModules.Off", "&cdesactivado");
		Config.set("SetupModules.Click-To-Turn-Off", "&7Haz clic para desactivarlo");
		Config.set("SetupModules.Click-To-Turn-On", "&7Haz clic para activarlo");
		Config.set("SetupModules.Join-Items", "Objetos al entrar");
		Config.set("SetupModules.Tablist", "Lista de jugadores");
		Config.set("SetupModules.Boss-Bar", "Boss bar al entrar");
		Config.set("SetupModules.Join-Title", "Título al entrar");
		Config.set("SetupModules.Double-Jump", "Doble salto");
		Config.set("SetupModules.Void-TP", "Void TP");
		Config.set("SetupModules.Anti-Swear", "Anti-insultos");
		Config.set("SetupModules.Anti-Advertising", "Anti-publicidad");

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}
