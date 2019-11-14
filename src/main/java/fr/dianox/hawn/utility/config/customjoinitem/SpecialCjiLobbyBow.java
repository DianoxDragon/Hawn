package fr.dianox.hawn.utility.config.customjoinitem;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

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
            Config.set("LobbyBow.Use_Permission", false);
            Config.set("LobbyBow.Option.Ultimate-Protection-Of-The-Items", true);
            
            Config.set("LobbyBow.Item.Title", "&6Lobby bow");
            Config.set("LobbyBow.Item.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&c&lAaaaaahhhh"
                }));
            Config.set("LobbyBow.Item.Material.Amount", 1);

            Config.set("LobbyBow.World.All_World", false);
            Config.set("LobbyBow.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            saveConfigFile();

        }
    }

}