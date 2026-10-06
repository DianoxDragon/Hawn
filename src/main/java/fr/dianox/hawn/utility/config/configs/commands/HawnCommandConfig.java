package fr.dianox.hawn.utility.config.configs.commands;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class HawnCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public HawnCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/Hawn.yml");
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

        Config.set("Urgent-mode.Enable", false);
        Config.set("Urgent-mode.Use-It-Only-On-The-Console", false);
        // Disabling the plugins while the server runs can lose their data: a lockdown is used instead
        Config.set("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled", false);
        Config.set("Urgent-mode.Plugin-desactivation.Plugin-Ignored", java.util.Arrays.asList(new String[] {
        		"Hawn", "LuckPerms"
        }));
        Config.set("Urgent-mode.Kick-Message", java.util.Arrays.asList(new String[] {
        		"&cThe multi line",
        		"&bworks like that %player%"
        }));
        Config.set("Urgent-mode.whitelist", java.util.Arrays.asList(new String[] {
        		"Dianox"
        }));
        Config.set("Urgent-mode.Can-Use-Urgent-Mode", java.util.Arrays.asList(new String[] {
        		"Dianox"
        }));

        Config.set("Urgent-mode.Remove-Op", true);
        Config.set("Urgent-mode.Lockdown.Block-Commands", true);
        Config.set("Urgent-mode.Lockdown.Block-Chat", true);
        Config.set("Urgent-mode.Lockdown.Allowed-Commands", new java.util.ArrayList<String>());
        Config.set("Urgent-mode.Backup.Enable", true);
        Config.set("Urgent-mode.Backup.Keep", 3);
        Config.set("Urgent-mode.Discord-Webhook", "");

        Config.set("Maintenance.Enable", false);
        Config.set("Maintenance.Kick-Message", java.util.Arrays.asList(new String[] {
        		"&cThe multi line",
        		"&bworks like that %player%"
        }));
        Config.set("Maintenance.whitelist", java.util.Arrays.asList(new String[] {
        		"Dianox"
        }));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }

}