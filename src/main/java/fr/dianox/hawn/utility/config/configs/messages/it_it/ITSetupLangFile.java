package fr.dianox.hawn.utility.config.configs.messages.it_it;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * The Italian messages (since 1.6), made from the English ones.
 */
public class ITSetupLangFile {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public ITSetupLangFile() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;


		file = new File(pl.getDataFolder(), "Messages/it_IT/SetupLang.yml");
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
		Config.set("SetupLanguage.Close-Inventory", "&cNon mi serve un setup");
		Config.set("SetupLanguage.Done", "&aSì, continua");
		Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Lingua cambiata in %arg 1%"));

		Config.set("SetupWorld.Close-Inventory", "&cPossiamo fermare il setup qui");
		Config.set("SetupWorld.Set-Up-World", "&aSì, scegliamo un mondo");
		Config.set("SetupWorld.Done", "&aSì, continuiamo");
		Config.set("SetupWorld.Info", "&eScegli un mondo");
		Config.set("SetupWorld.WARNING", Collections.singletonList("&cNON MUOVERTI"));
		Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Mondo predefinito cambiato in %arg 1%"));
		Config.set("SetupWorld.Line-1", "&eClic sinistro&7 per scegliere questo mondo");

		Config.set("SetupSpawn.Close-Inventory", "&cPossiamo fermare il setup qui");
		Config.set("SetupSpawn.Set-Up-Spawn", "&6Sì, creiamo uno spawn");
		Config.set("SetupSpawn.Done", "&aSì, continuiamo");
		Config.set("SetupSpawn.Info", "&eCrea uno spawn");
		Config.set("SetupSpawn.WARNING", Collections.singletonList("&cSei ancora nel setup: non dimenticare di impostare uno spawn per finirlo"));
		Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Spawn predefinito cambiato in %arg 1%"));

		// Since 1.4.8: the Previous / Next buttons, the teleport before the spawn
		Config.set("Setup.Previous", "&7« Passo precedente");
		Config.set("Setup.Next", "&7Passo successivo »");
		Config.set("SetupSpawn.Teleported", Collections.singletonList("%prefix% &7Sei in &e%arg 1%&7, il mondo predefinito: mettiti dove deve essere lo spawn, poi clicca il blocco d'oro"));
		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Riavvia il server"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6Sei ancora nel setup &7(&e/hawn setup &7per riaprirlo)"));

		// Since 1.5.1: no restart at the end, a confirmation before running it again, the page of the modules
		Config.set("Setup.Finished", Collections.singletonList("%prefix% &aSetup finito: le modifiche sono applicate, nessun riavvio necessario"));
		Config.set("Setup.Closed", Collections.singletonList("%prefix% &7Setup chiuso, non è cambiato niente"));
		Config.set("SetupConfirm.Info", "&eRifare il setup di Hawn?");
		Config.set("SetupConfirm.Info-Lore", java.util.Arrays.asList("&7La lingua, i moduli, il mondo predefinito", "&7e lo spawn predefinito si possono cambiare di nuovo"));
		Config.set("SetupConfirm.Yes", "&aSì, rifai il setup");
		Config.set("SetupConfirm.No", "&cNo, annulla");
		Config.set("SetupModules.Info", "&eAttiva o disattiva i moduli");
		Config.set("SetupModules.Info-Lore", java.util.Arrays.asList("&7Applicato alla fine del setup, senza riavvio", "&7Tutto il resto è nei file di Hawn"));
		Config.set("SetupModules.Close-Inventory", "&cPossiamo fermare il setup qui");
		Config.set("SetupModules.On", "&aattivo");
		Config.set("SetupModules.Off", "&cspento");
		Config.set("SetupModules.Click-To-Turn-Off", "&7Clicca per disattivarlo");
		Config.set("SetupModules.Click-To-Turn-On", "&7Clicca per attivarlo");
		Config.set("SetupModules.Join-Items", "Oggetti all'accesso");
		Config.set("SetupModules.Tablist", "Tab list");
		Config.set("SetupModules.Boss-Bar", "Boss bar all'accesso");
		Config.set("SetupModules.Join-Title", "Titolo all'accesso");
		Config.set("SetupModules.Double-Jump", "Doppio salto");
		Config.set("SetupModules.Void-TP", "Void TP");
		Config.set("SetupModules.Anti-Swear", "Anti-swear");
		Config.set("SetupModules.Anti-Advertising", "Anti-advertising");

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}