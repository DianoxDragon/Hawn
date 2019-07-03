package fr.Dianox.Hawn.Utility.Config.Commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

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

            Config.set("Warp.Enable", Boolean.valueOf(true));
            Config.set("Warp.Disable-Message", Boolean.valueOf(true));
            
            Config.set("Warp.Delay.Self.Enable", Boolean.valueOf(true));
            Config.set("Warp.Delay.Self.Delay-Seconds", Integer.valueOf(5));
            Config.set("Warp.Delay.Self.Bypass-Delay", Boolean.valueOf(false));
            
            Config.set("Warp.Delay.Other.Enable", Boolean.valueOf(true));
            Config.set("Warp.Delay.Other.Delay-Seconds", Integer.valueOf(5));
            Config.set("Warp.Delay.Other.Bypass-Delay", Boolean.valueOf(false));
            
            Config.set("Warp.Delay.Cancel-Tp-On.Any-movements", Boolean.valueOf(true));
            Config.set("Warp.Delay.Cancel-Tp-On.On-Damages", Boolean.valueOf(true));
            Config.set("Warp.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));

            Config.set("WarpList.Enable", Boolean.valueOf(true));
            Config.set("WarpList.Disable-Message", Boolean.valueOf(true));
            Config.set("WarpList.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));

            Config.set("SetWarp.Enable", Boolean.valueOf(true));
            Config.set("SetWarp.Disable-Message", Boolean.valueOf(true));
            Config.set("SetWarp.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));

            Config.set("DelWarp.Enable", Boolean.valueOf(true));
            Config.set("DelWarp.Disable-Message", Boolean.valueOf(true));
            Config.set("DelWarp.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));

            saveConfigFile();

        }
    }

}