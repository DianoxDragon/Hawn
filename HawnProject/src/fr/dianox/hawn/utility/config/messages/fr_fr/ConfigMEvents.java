package fr.dianox.hawn.utility.config.messages.fr_fr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMEvents {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMEvents() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Classic/Events.yml");
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

            Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"%prefix% &7Oh non, vous avez essayé d'atteindre le vide"}));
            Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eSwear&8] &b%player% &7a dit &e%message%"}));
            
            Config.set("LaunchPad.Cant-Use-Cooldown.Enable", true);
            Config.set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Désolé, mais actuellement vous ne pouvez pas utiliser les launchpads"}));
            
            Config.set("Cancel-Tp.Warp.Enable", true);
            Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tp &cdésactivé"}));
            Config.set("Cancel-Tp.Spawn.Enable", true);
            Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tp &cdésactivé"}));

            saveConfigFile();

        }
    }

}