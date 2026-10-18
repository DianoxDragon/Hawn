package fr.dianox.hawn.utility.config.configs.messages.de_de;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * The German messages (Messages/de_DE), since Hawn 1.4.6: the texts of SetupLangFile translated.
 */
public class DESetupLangFile {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public DESetupLangFile() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/de_DE/SetupLang.yml");
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
		Config.set("SetupLanguage.Close-Inventory", "&cIch brauche keine Einrichtung");
		Config.set("SetupLanguage.Done", "&aJa, weiter");
		Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Sprache geändert zu %arg 1%"));

		Config.set("SetupWorld.Close-Inventory", "&cDie Einrichtung hier beenden");
		Config.set("SetupWorld.Set-Up-World", "&aJa, eine Welt auswählen");
		Config.set("SetupWorld.Done", "&aJa, weiter");
		Config.set("SetupWorld.Info", "&eWähle eine Welt");
		Config.set("SetupWorld.WARNING", Collections.singletonList("&cNICHT BEWEGEN"));
		Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Standardwelt geändert zu %arg 1%"));
		Config.set("SetupWorld.Line-1", "&eLinksklick&7, um diese Welt zu wählen");

		Config.set("SetupSpawn.Close-Inventory", "&cDie Einrichtung hier beenden");
		Config.set("SetupSpawn.Set-Up-Spawn", "&6Ja, einen Spawn erstellen");
		Config.set("SetupSpawn.Done", "&aJa, weiter");
		Config.set("SetupSpawn.Info", "&eErstelle einen Spawn");
		Config.set("SetupSpawn.WARNING", Collections.singletonList("&cDu bist noch in der Einrichtung: setze einen Spawn, um sie abzuschließen"));
		Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Standard-Spawn geändert zu %arg 1%"));

		// Since 1.4.8: the Previous / Next buttons, the teleport before the spawn
		Config.set("Setup.Previous", "&7« Vorheriger Schritt");
		Config.set("Setup.Next", "&7Nächster Schritt »");
		Config.set("SetupSpawn.Teleported", Collections.singletonList("%prefix% &7Du bist in &e%arg 1%&7, der Standardwelt: stell dich an die Stelle des Spawns und klicke dann auf den Goldblock"));
		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Bitte starte den Server neu"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6Du bist noch in der Einrichtung &7(&e/hawn setup &7öffnet sie wieder)"));

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}