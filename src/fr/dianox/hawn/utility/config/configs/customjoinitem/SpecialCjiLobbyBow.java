package fr.dianox.hawn.utility.config.configs.customjoinitem;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SpecialCjiLobbyBow {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public SpecialCjiLobbyBow() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "CustomJoinItem/Special-LobbyBow.yml");
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
            
            Config.set("LobbyBow.Enable", true);
            
            Config.set("LobbyBow.Item.Title", "&6Lobby bow");
            Config.set("LobbyBow.Item.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&c&lAaaaaahhhh"
                }));
            Config.set("LobbyBow.Item.Material.Amount", 1);
            
            saveConfigFile();

        }
    }

}