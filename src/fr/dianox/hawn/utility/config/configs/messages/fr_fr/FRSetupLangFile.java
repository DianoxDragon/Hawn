package fr.dianox.hawn.utility.config.configs.messages.fr_fr;

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
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {}

			/*
			 * Gui
			 */
			// import
			Config.set("SetupLanguage.Close-Inventory", "&cJe n'ai pas besoin d'une installation");
			Config.set("SetupLanguage.Done", "&aOui continuez");
			Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Langue changée en %arg 1%"));

			Config.set("SetupWorld.Close-Inventory", "&cNous pouvons arrêter la mise en place ici");
			Config.set("SetupWorld.Set-Up-World", "&aOui, choisissons un monde");
			Config.set("SetupWorld.Done", "&aOui, continuons");
			Config.set("SetupWorld.Info", "&eChoisissez un monde");
			Config.set("SetupWorld.WARNING", Collections.singletonList("&cNE BOUGEZ PAS"));
			Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Le monde par défaut est passé à %arg 1%"));
			Config.set("SetupWorld.Line-1", "&eClique gauche&7 pour choisir ce monde");

			Config.set("SetupSpawn.Close-Inventory", "&cNous pouvons arrêter la mise en place ici");
			Config.set("SetupSpawn.Set-Up-Spawn", "&6Oui, créons un spawn");
			Config.set("SetupSpawn.Done", "&aOui, continuons");
			Config.set("SetupSpawn.Info", "&eCréer un spawn");
			Config.set("SetupSpawn.WARNING", Collections.singletonList("&cVous êtes toujours dans l'installation, n'oubliez pas de mettre un spawn, pour la finir"));
			Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Le spawn par défaut est passé à %arg 1%"));

			Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Veuillez redémarrer le serveur"));

			saveConfigFile();

		}
	}
}