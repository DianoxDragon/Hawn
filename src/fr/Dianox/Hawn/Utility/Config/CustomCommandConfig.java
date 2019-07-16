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
            
            Config.set("commands-general.enable", true);
            Config.set("commands.rules.enable", true);
            Config.set("commands.rules.command", "/rules");
            Config.set("commands.rules.permission.enable", true);
            Config.set("commands.rules.permission.message", "hawn.command.rules");
            Config.set("commands.rules.no-permission-message-enable", true);
            Config.set("commands.rules.message", java.util.Arrays.asList(new String[] {
            		"<--center--> &4&m>-------------<",
            	    "",
            	    "<--center--> &c&nPlayers",
            	    "",
            	    " &8→ &ePlease be nice",
            	    " &8→ &eDon't cheat and more",
            	    "",
            	    "<perm>hawn.whatyouwant.more</perm> <--center--> &c&nVIP",
            	    "<perm>hawn.whatyouwant.more</perm> ",
            	    "<perm>hawn.whatyouwant.more</perm> &bIf you can see the VIP section, It's because you have the permission for it!",
            	    "<perm>hawn.whatyouwant.more</perm> &bCheck the config file for custom commands to see how it's work!",
            	    "<perm>hawn.whatyouwant.more</perm> ",
            	    "<--center--> &4&m>-------------<",
            	    "<perm>hawn.whatyouwant.more</perm> [send-actionbar[50]]: Actually You can see that if you have ther permission",
            	    "[send-title[50]]: But that's good //n &6to have a real custom commands",
            	    "[sounds]: BLOCK_ANVIL_LAND",
            	    "&eJust check spigot page for more events"
                }));
            
            saveConfigFile();

        }
    }

}
