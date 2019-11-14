package fr.dianox.hawn.utility.config.commands;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class WarpSetWarpCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public WarpSetWarpCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Commands/Warp-SetWarp.yml");
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

            Config.set("Warp.Enable", true);
            Config.set("Warp.Disable-Message", true);
            
            Config.set("Warp.Delay.Self.Enable", true);
            Config.set("Warp.Delay.Self.Delay-Seconds", 5);
            Config.set("Warp.Delay.Self.Bypass-Delay", false);
            
            Config.set("Warp.Delay.Other.Enable", true);
            Config.set("Warp.Delay.Other.Delay-Seconds", 5);
            Config.set("Warp.Delay.Other.Bypass-Delay", false);
            
            Config.set("Warp.Delay.Cancel-Tp-On.Any-movements", true);
            Config.set("Warp.Delay.Cancel-Tp-On.On-Damages", true);
            Config.set("Warp.DISABLE_THE_COMMAND_COMPLETELY", false);

            Config.set("WarpList.Enable", true);
            Config.set("WarpList.Disable-Message", true);
            Config.set("WarpList.DISABLE_THE_COMMAND_COMPLETELY", false);

            Config.set("SetWarp.Enable", true);
            Config.set("SetWarp.Disable-Message", true);
            Config.set("SetWarp.DISABLE_THE_COMMAND_COMPLETELY", false);

            Config.set("DelWarp.Enable", true);
            Config.set("DelWarp.Disable-Message", true);
            Config.set("DelWarp.DISABLE_THE_COMMAND_COMPLETELY", false);
            
            Config.set("EditWarp.Enable", true);
            Config.set("EditWarp.Disable-Message", true);
            Config.set("EditWarp.DISABLE_THE_COMMAND_COMPLETELY", false);

            saveConfigFile();

        }
    }

}