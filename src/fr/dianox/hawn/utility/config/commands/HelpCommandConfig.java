package fr.dianox.hawn.utility.config.commands;

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
            
            Config.set("Help-Command.Categories.default", "lobbyhelp");

            // categories 1
            Config.set("Help-Command.Categories.lobbyhelp.1", java.util.Arrays.asList(new String[] {
                    "&8----------------------------------",
                    " ",
                    " - &e&lHelp sample page",
                    " ",
                    "This is much more than a simple remodeling of the help",
                    "You can create categories, pages, whatever you want with all possible events.",
                    " ",
                    "json:{\"text\":\"§eClick here to go to page 2\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/help lobbyhelp 2\"}}",
                    "json:{\"text\":\"§cClick here to go to the faction category\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/help faction\"}}",
                    " ",
                    "&8----------------------------------"
            }));
            Config.set("Help-Command.Categories.lobbyhelp.2", java.util.Arrays.asList(new String[] {
            		"&8----------------------------------",
                    "Lobbyhelp page 2 :",
                    "json:{\"text\":\"Click here to go to page 1\",\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/help\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Yes you can go back\"}}",
                    "&8----------------------------------"
            }));
            
            // categories 2
            Config.set("Help-Command.Categories.faction.1", java.util.Arrays.asList(new String[] {
            		"&8----------------------------------",
                    "Faction help page",
                    "&8----------------------------------"
            }));
            
            Config.set("DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }

}