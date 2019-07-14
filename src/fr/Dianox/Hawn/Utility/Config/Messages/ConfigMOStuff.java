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
            
            Config.set("Error.No-Permissions.Enable", true);
            Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cSorry, but you don't have the permission : %noperm%"}));
            Config.set("Error.No-Spawn.Enable", true);
            Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe spawn doesn't exist"}));
            Config.set("Error.Change-Me.Enable", true);
            Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou have to change the spawn/warp/etc. on &6%arg1%&c on &e%arg2%"}));
            Config.set("Error.No-Players.Enable", true);
            Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe player is not online or doesn't exist"}));
            Config.set("Error.No-Page-Found.Enable", true);
            Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe page can't be found"}));
            Config.set("Error.No-Category.Enable", true);
            Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe category doesn't exist"}));
            Config.set("Error.Use-Number.Enable", true);
            Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cPlease specify a number"}));
            Config.set("Error.Command-Disable.Enable", true);
            Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cSorry, this command is disable"}));
            Config.set("Error.Argument-Missing.Enable", true);
            Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cI'm sorry, but there must be one or two arguments missing"}));
            Config.set("Error.Not-A-Player.Enable", true);
            Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou are not a player"}));
            
            saveConfigFile();

        }
    }

}
