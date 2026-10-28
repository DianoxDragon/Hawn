package fr.dianox.hawn.utility.config.configs.messages.nl_nl;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * The Dutch messages (Messages/nl_NL), since Hawn 1.5.4: the texts of SetupLangFile translated.
 */
public class NLSetupLangFile {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public NLSetupLangFile() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/nl_NL/SetupLang.yml");
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
		Config.set("SetupLanguage.Close-Inventory", "&cIk heb geen installatie nodig");
		Config.set("SetupLanguage.Done", "&aJa, doorgaan");
		Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Taal gewijzigd naar %arg 1%"));

		Config.set("SetupWorld.Close-Inventory", "&cDe installatie hier stoppen");
		Config.set("SetupWorld.Set-Up-World", "&aJa, een wereld kiezen");
		Config.set("SetupWorld.Done", "&aJa, doorgaan");
		Config.set("SetupWorld.Info", "&eKies een wereld");
		Config.set("SetupWorld.WARNING", Collections.singletonList("&cNIET BEWEGEN"));
		Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Standaardwereld gewijzigd naar %arg 1%"));
		Config.set("SetupWorld.Line-1", "&eLinkerklik&7 om deze wereld te kiezen");

		Config.set("SetupSpawn.Close-Inventory", "&cDe installatie hier stoppen");
		Config.set("SetupSpawn.Set-Up-Spawn", "&6Ja, een spawn maken");
		Config.set("SetupSpawn.Done", "&aJa, doorgaan");
		Config.set("SetupSpawn.Info", "&eMaak een spawn");
		Config.set("SetupSpawn.WARNING", Collections.singletonList("&cJe bent nog in de installatie: zet een spawn om ze af te ronden"));
		Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Standaardspawn gewijzigd naar %arg 1%"));

		// Since 1.4.8: the Previous / Next buttons, the teleport before the spawn
		Config.set("Setup.Previous", "&7« Vorige stap");
		Config.set("Setup.Next", "&7Volgende stap »");
		Config.set("SetupSpawn.Teleported", Collections.singletonList("%prefix% &7Je bent in &e%arg 1%&7, de standaardwereld: ga op de plek van de spawn staan en klik dan op het goudblok"));
		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Start de server opnieuw op"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6Je bent nog in de installatie &7(&e/hawn setup &7opent ze opnieuw)"));

		// Since 1.5.1: no restart at the end, a confirmation before running it again, the page of the modules
		Config.set("Setup.Finished", Collections.singletonList("%prefix% &aInstallatie klaar: de wijzigingen zijn toegepast, geen herstart nodig"));
		Config.set("Setup.Closed", Collections.singletonList("%prefix% &7Installatie gesloten, er is niets gewijzigd"));
		Config.set("SetupConfirm.Info", "&eDe installatie van Hawn opnieuw starten?");
		Config.set("SetupConfirm.Info-Lore", java.util.Arrays.asList("&7De taal, de modules, de standaardwereld", "&7en de standaardspawn kunnen opnieuw worden gewijzigd"));
		Config.set("SetupConfirm.Yes", "&aJa, de installatie opnieuw starten");
		Config.set("SetupConfirm.No", "&cNee, annuleren");
		Config.set("SetupModules.Info", "&eZet de modules aan of uit");
		Config.set("SetupModules.Info-Lore", java.util.Arrays.asList("&7Toegepast aan het einde van de installatie, zonder herstart", "&7Al het andere staat in de bestanden van Hawn"));
		Config.set("SetupModules.Close-Inventory", "&cDe installatie hier stoppen");
		Config.set("SetupModules.On", "&aaan");
		Config.set("SetupModules.Off", "&cuit");
		Config.set("SetupModules.Click-To-Turn-Off", "&7Klik om uit te zetten");
		Config.set("SetupModules.Click-To-Turn-On", "&7Klik om aan te zetten");
		Config.set("SetupModules.Join-Items", "Join-items");
		Config.set("SetupModules.Tablist", "Tablijst");
		Config.set("SetupModules.Boss-Bar", "Bossbalk bij het joinen");
		Config.set("SetupModules.Join-Title", "Titel bij het joinen");
		Config.set("SetupModules.Double-Jump", "Dubbele sprong");
		Config.set("SetupModules.Void-TP", "Void TP");
		Config.set("SetupModules.Anti-Swear", "Anti-scheldwoorden");
		Config.set("SetupModules.Anti-Advertising", "Anti-reclame");

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}
