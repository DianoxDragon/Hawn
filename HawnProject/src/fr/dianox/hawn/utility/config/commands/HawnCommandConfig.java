package fr.dianox.hawn.utility.config.commands;

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
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {}
            
            Config.set("Urgent-mode.Enable", false);
            Config.set("Urgent-mode.Use-It-Only-On-The-Console", false);
            Config.set("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled", true);
            Config.set("Urgent-mode.Plugin-desactivation.Plugin-Ignored", java.util.Arrays.asList(new String[] {
            		"Hawn"
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
            
            Config.set("Maintenance.Enable", false);
            Config.set("Maintenance.Kick-Message", java.util.Arrays.asList(new String[] {
            		"&cThe multi line",
            		"&bworks like that %player%"
            }));
            Config.set("Maintenance.whitelist", java.util.Arrays.asList(new String[] {
            		"Dianox"
            }));
            
            saveConfigFile();

        }
    }

}