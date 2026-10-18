package fr.dianox.hawn.utility.config.configs.messages;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import fr.dianox.hawn.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class SetupLangFile {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public SetupLangFile() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/SetupLang.yml");
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
		Config.set("SetupLanguage.Close-Inventory", "&cI don't need a setup");
		Config.set("SetupLanguage.Done", "&aYes continue");
		Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Language changed to %arg 1%"));

		Config.set("SetupWorld.Close-Inventory", "&cWe can stop the setup here");
		Config.set("SetupWorld.Set-Up-World", "&aYes, let's choose a world");
		Config.set("SetupWorld.Done", "&aYes, let's continue");
		Config.set("SetupWorld.Info", "&eChoose a world");
		Config.set("SetupWorld.WARNING", Collections.singletonList("&cDON'T MOVE"));
		Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Default world changed to %arg 1%"));
		Config.set("SetupWorld.Line-1", "&eLeft-Click&7 to choose this world");

		Config.set("SetupSpawn.Close-Inventory", "&cWe can stop the setup here");
		Config.set("SetupSpawn.Set-Up-Spawn", "&6Yes, let's create a spawn");
		Config.set("SetupSpawn.Done", "&aYes, let's continue");
		Config.set("SetupSpawn.Info", "&eCreate a spawn");
		Config.set("SetupSpawn.WARNING", Collections.singletonList("&cYou are still in setup, don't forget to set a spawn, to finish it"));
		Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Default spawn changed to %arg 1%"));

		// Since 1.4.8: the Previous / Next buttons, the teleport before the spawn
		Config.set("Setup.Previous", "&7« Previous step");
		Config.set("Setup.Next", "&7Next step »");
		Config.set("SetupSpawn.Teleported", Collections.singletonList("%prefix% &7You are in &e%arg 1%&7, the default world: stand where the spawn must be, then click the gold block"));
		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Please restart the server"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6You are still in the setup &7(&e/hawn setup &7to open it again)"));

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}