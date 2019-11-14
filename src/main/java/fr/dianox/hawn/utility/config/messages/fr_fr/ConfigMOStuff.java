package fr.dianox.hawn.utility.config.messages.fr_fr;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigMOStuff {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMOStuff() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Classic/SomeOtherStuff.yml");
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
            Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, mais vous n'avez pas la permission : %noperm%"}));
            Config.set("Error.No-Spawn.Enable", true);
            Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa spawn n'existe pas"}));
            Config.set("Error.Change-Me.Enable", true);
            Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous devez changer le spawn/warp/etc. sur &6%arg1%&c dans &e%arg2%"}));
            Config.set("Error.No-Players.Enable", true);
            Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe joueur n'est pas en ligne ou n'existe pas"}));
            Config.set("Error.No-Page-Found.Enable", true);
            Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa page ne peut pas Ãªtre trouvée"}));
            Config.set("Error.No-Category.Enable", true);
            Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa catégorie n'existe pas"}));
            Config.set("Error.Use-Number.Enable", true);
            Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVeuillez préciser un nombre"}));
            Config.set("Error.Command-Disable.Enable", true);
            Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, cette commande est désactivée"}));
            Config.set("Error.Argument-Missing.Enable", true);
            Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe suis désolé, mais il doit manquer un ou deux arguments"}));
            Config.set("Error.Not-A-Player.Enable", true);
            Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous n'Ãªtes pas un joueur"}));
            
            saveConfigFile();

        }
    }

}