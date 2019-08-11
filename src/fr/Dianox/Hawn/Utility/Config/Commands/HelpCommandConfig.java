package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class HelpCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public HelpCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/Help.yml");
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

            Config.set("Help-Command.Enable", true);
            Config.set("Help-Command.Use-Permissions", true);
            
            Config.set("Help-Command.Categories.default", "CHANGE ME");

            // categories 1
            Config.set("Help-Command.Categories.lobbyhelp.1", java.util.Arrays.asList(new String[] {
                    "Hello the good help",
                    "want to go to the page 2 ?"
            }));
            Config.set("Help-Command.Categories.lobbyhelp.2", java.util.Arrays.asList(new String[] {
                    "don't forget that JSON works too",
                    "and papi too"
            }));
            
            // categories 2
            Config.set("Help-Command.Categories.faction.1", java.util.Arrays.asList(new String[] {
                    "&cWe love factions",
                    "&elmao %player%"
            }));
            Config.set("Help-Command.Categories.faction.2", java.util.Arrays.asList(new String[] {
                    "and yes",
                    "and no",
                    "pretty short help no ?"
            }));
            
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }

}
