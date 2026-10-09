package fr.dianox.hawn.utility.config.configs.messages.fr_fr;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class FRSetupLangFile {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public FRSetupLangFile() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/fr_FR/SetupLang.yml");
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
		Config.set("SetupLanguage.Close-Inventory", "&cJe n'ai pas besoin de la configuration");
		Config.set("SetupLanguage.Done", "&aOui, continuer");
		Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Langue changée en %arg 1%"));

		Config.set("SetupWorld.Close-Inventory", "&cArrêter la configuration ici");
		Config.set("SetupWorld.Set-Up-World", "&aOui, choisissons un monde");
		Config.set("SetupWorld.Done", "&aOui, continuons");
		Config.set("SetupWorld.Info", "&eChoisissez un monde");
		Config.set("SetupWorld.WARNING", Collections.singletonList("&cNE BOUGEZ PAS"));
		Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Le monde par défaut est maintenant %arg 1%"));
		Config.set("SetupWorld.Line-1", "&eClic gauche&7 pour choisir ce monde");

		Config.set("SetupSpawn.Close-Inventory", "&cArrêter la configuration ici");
		Config.set("SetupSpawn.Set-Up-Spawn", "&6Oui, créons un spawn");
		Config.set("SetupSpawn.Done", "&aOui, continuons");
		Config.set("SetupSpawn.Info", "&eCréer un spawn");
		Config.set("SetupSpawn.WARNING", Collections.singletonList("&cLa configuration n'est pas finie : créez un spawn pour la terminer"));
		Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Le spawn par défaut est maintenant %arg 1%"));

		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Veuillez redémarrer le serveur"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6Vous êtes toujours dans le setup &7(&e/hawn setup &7pour le rouvrir)"));

		YamlConfiguration defaults = Config;
		Config = ConfigDefaults.apply(file, loaded, Config);
		// The French corrected in 1.4, in the files that still have the old texts
		ConfigDefaults.replaceOldDefaults(file, Config, defaults, FrenchFixes.SETUPLANGFILE);
	}
}