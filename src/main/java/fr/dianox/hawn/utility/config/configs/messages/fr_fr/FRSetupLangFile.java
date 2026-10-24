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

		// Since 1.4.8: the Previous / Next buttons, the teleport before the spawn
		Config.set("Setup.Previous", "&7« Étape précédente");
		Config.set("Setup.Next", "&7Étape suivante »");
		Config.set("SetupSpawn.Teleported", Collections.singletonList("%prefix% &7Vous êtes dans &e%arg 1%&7, le monde par défaut : placez-vous à l'endroit du spawn, puis cliquez sur le bloc d'or"));
		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Veuillez redémarrer le serveur"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6Vous êtes toujours dans le setup &7(&e/hawn setup &7pour le rouvrir)"));

		// Since 1.5.1: no restart at the end, a confirmation before running it again, the page of the modules
		Config.set("Setup.Finished", Collections.singletonList("%prefix% &aConfiguration terminée : les changements sont appliqués, pas besoin de redémarrer"));
		Config.set("Setup.Closed", Collections.singletonList("%prefix% &7Configuration fermée, rien n'a changé"));
		Config.set("SetupConfirm.Info", "&eRelancer la configuration de Hawn ?");
		Config.set("SetupConfirm.Info-Lore", java.util.Arrays.asList("&7La langue, les modules, le monde par défaut", "&7et le spawn par défaut peuvent être changés de nouveau"));
		Config.set("SetupConfirm.Yes", "&aOui, relancer la configuration");
		Config.set("SetupConfirm.No", "&cNon, annuler");
		Config.set("SetupModules.Info", "&eActivez ou désactivez les modules");
		Config.set("SetupModules.Info-Lore", java.util.Arrays.asList("&7Appliqué à la fin de la configuration, sans redémarrage", "&7Tout le reste est dans les fichiers de Hawn"));
		Config.set("SetupModules.Close-Inventory", "&cArrêter la configuration ici");
		Config.set("SetupModules.On", "&aactivé");
		Config.set("SetupModules.Off", "&cdésactivé");
		Config.set("SetupModules.Click-To-Turn-Off", "&7Cliquez pour le désactiver");
		Config.set("SetupModules.Click-To-Turn-On", "&7Cliquez pour l'activer");
		Config.set("SetupModules.Join-Items", "Items de connexion");
		Config.set("SetupModules.Tablist", "Tab list");
		Config.set("SetupModules.Boss-Bar", "Boss bar à la connexion");
		Config.set("SetupModules.Join-Title", "Titre à la connexion");
		Config.set("SetupModules.Double-Jump", "Double saut");
		Config.set("SetupModules.Void-TP", "Void TP");
		Config.set("SetupModules.Anti-Swear", "Anti-insultes");
		Config.set("SetupModules.Anti-Advertising", "Anti-pub");

		YamlConfiguration defaults = Config;
		Config = ConfigDefaults.apply(file, loaded, Config);
		// The French corrected in 1.4, in the files that still have the old texts
		ConfigDefaults.replaceOldDefaults(file, Config, defaults, FrenchFixes.SETUPLANGFILE);
	}
}