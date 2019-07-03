package fr.Dianox.Hawn.Utility.Config.Messages;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMOStuff {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMOStuff() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/Classic/SomeOtherStuff.yml");
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
            
            Config.set("Error.No-Permissions.Enable", Boolean.valueOf(true));
            Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"&cSorry, but you don't have the permission : %noperm%"}));
            Config.set("Error.No-Spawn.Enable", Boolean.valueOf(true));
            Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"&cSpawn doesn't exist"}));
            Config.set("Error.Change-Me.Enable", Boolean.valueOf(true));
            Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"&cYou have to change the spawn/warp/etc on %arg1% on %arg2%"}));
            Config.set("Error.No-Players.Enable", Boolean.valueOf(true));
            Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"&cPlayer is not online or doesn't exist"}));
            Config.set("Error.No-Page-Found.Enable", Boolean.valueOf(true));
            Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"&cPage not found"}));
            Config.set("Error.No-Category.Enable", Boolean.valueOf(true));
            Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"&cThe category does not exist"}));
            Config.set("Error.Use-Number.Enable", Boolean.valueOf(true));
            Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"&cPlease use a number"}));
            Config.set("Error.Command-Disable.Enable", Boolean.valueOf(true));
            Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"&cSorry, this command is disable"}));
            Config.set("Error.Argument-Missing.Enable", Boolean.valueOf(true));
            Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"&cI'm sorry, but there must be one or two arguments missing."}));
            Config.set("Error.Not-A-Player.Enable", Boolean.valueOf(true));
            Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"&cYou are not a player"}));
            
            saveConfigFile();

        }
    }

}