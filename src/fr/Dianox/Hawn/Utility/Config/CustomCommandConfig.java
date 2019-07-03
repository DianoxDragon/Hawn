package fr.Dianox.Hawn.Utility.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CustomCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public CustomCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "CustomCommand.yml");
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
            
            Config.set("commands-general.enable", Boolean.valueOf(true));
            Config.set("commands.rules.enable", Boolean.valueOf(true));
            Config.set("commands.rules.command", String.valueOf("/rules"));
            Config.set("commands.rules.permission.enable", Boolean.valueOf(true));
            Config.set("commands.rules.permission.message", String.valueOf("hawn.command.rules"));
            Config.set("commands.rules.no-permission-message-enable", Boolean.valueOf(true));
            Config.set("commands.rules.message", java.util.Arrays.asList(new String[] {
                    "this is a test message for the command /rules",
                    "please follow this format for all commands, with all",
                    "these parameters, of course placeholderapi and json work",
                    "&eyou can create as much commands as you want !",
                    "Let people be informed !"
                }));
            
            saveConfigFile();

        }
    }

}