package fr.dianox.hawn.utility.config.configs.messages.pt_br;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

/**
 * The Portuguese (Brazil) messages (since 1.6), made from the English ones.
 */
public class PTSetupLangFile {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public PTSetupLangFile() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;


		file = new File(pl.getDataFolder(), "Messages/pt_BR/SetupLang.yml");
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
		Config.set("SetupLanguage.Close-Inventory", "&cNão preciso de um setup");
		Config.set("SetupLanguage.Done", "&aSim, continuar");
		Config.set("SetupLanguage.Language-Changed", Collections.singletonList("%prefix% &7Idioma mudado para %arg 1%"));

		Config.set("SetupWorld.Close-Inventory", "&cPodemos parar o setup aqui");
		Config.set("SetupWorld.Set-Up-World", "&aSim, vamos escolher um mundo");
		Config.set("SetupWorld.Done", "&aSim, vamos continuar");
		Config.set("SetupWorld.Info", "&eEscolha um mundo");
		Config.set("SetupWorld.WARNING", Collections.singletonList("&cNÃO SE MEXA"));
		Config.set("SetupWorld.World-Changed", Collections.singletonList("%prefix% &7Mundo padrão mudado para %arg 1%"));
		Config.set("SetupWorld.Line-1", "&eClique esquerdo&7 para escolher este mundo");

		Config.set("SetupSpawn.Close-Inventory", "&cPodemos parar o setup aqui");
		Config.set("SetupSpawn.Set-Up-Spawn", "&6Sim, vamos criar um spawn");
		Config.set("SetupSpawn.Done", "&aSim, vamos continuar");
		Config.set("SetupSpawn.Info", "&eCriar um spawn");
		Config.set("SetupSpawn.WARNING", Collections.singletonList("&cVocê ainda está no setup: não esqueça de definir um spawn para terminar"));
		Config.set("SetupSpawn.Spawn-Changed", Collections.singletonList("%prefix% &7Spawn padrão mudado para %arg 1%"));

		// Since 1.4.8: the Previous / Next buttons, the teleport before the spawn
		Config.set("Setup.Previous", "&7« Etapa anterior");
		Config.set("Setup.Next", "&7Próxima etapa »");
		Config.set("SetupSpawn.Teleported", Collections.singletonList("%prefix% &7Você está em &e%arg 1%&7, o mundo padrão: fique onde o spawn deve ficar e clique no bloco de ouro"));
		Config.set("Setup.Restart-Server", Collections.singletonList("%prefix% &6Reinicie o servidor"));
		Config.set("Setup.Still-In-Setup", Collections.singletonList("%prefix% &6Você ainda está no setup &7(&e/hawn setup &7para abrir de novo)"));

		// Since 1.5.1: no restart at the end, a confirmation before running it again, the page of the modules
		Config.set("Setup.Finished", Collections.singletonList("%prefix% &aSetup concluído: as mudanças foram aplicadas, sem precisar reiniciar"));
		Config.set("Setup.Closed", Collections.singletonList("%prefix% &7Setup fechado, nada foi mudado"));
		Config.set("SetupConfirm.Info", "&eFazer o setup do Hawn de novo?");
		Config.set("SetupConfirm.Info-Lore", java.util.Arrays.asList("&7O idioma, os módulos, o mundo padrão", "&7e o spawn padrão podem ser mudados de novo"));
		Config.set("SetupConfirm.Yes", "&aSim, fazer o setup de novo");
		Config.set("SetupConfirm.No", "&cNão, cancelar");
		Config.set("SetupModules.Info", "&eLigue ou desligue os módulos");
		Config.set("SetupModules.Info-Lore", java.util.Arrays.asList("&7Aplicado no fim do setup, sem reiniciar", "&7Todo o resto está nos arquivos do Hawn"));
		Config.set("SetupModules.Close-Inventory", "&cPodemos parar o setup aqui");
		Config.set("SetupModules.On", "&aligado");
		Config.set("SetupModules.Off", "&cdesligado");
		Config.set("SetupModules.Click-To-Turn-Off", "&7Clique para desligar");
		Config.set("SetupModules.Click-To-Turn-On", "&7Clique para ligar");
		Config.set("SetupModules.Join-Items", "Itens ao entrar");
		Config.set("SetupModules.Tablist", "Tab list");
		Config.set("SetupModules.Boss-Bar", "Boss bar ao entrar");
		Config.set("SetupModules.Join-Title", "Título ao entrar");
		Config.set("SetupModules.Double-Jump", "Pulo duplo");
		Config.set("SetupModules.Void-TP", "Void TP");
		Config.set("SetupModules.Anti-Swear", "Anti-swear");
		Config.set("SetupModules.Anti-Advertising", "Anti-advertising");

		Config = ConfigDefaults.apply(file, loaded, Config);
	}
}