package fr.Dianox.Hawn.Utility.Config.CustomJoinItem;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SpecialCjiHidePlayers {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public SpecialCjiHidePlayers() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "CustomJoinItem/Special-HidePlayers.yml");
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
            
            Config.set("PV.Enable", Boolean.valueOf(true));
            Config.set("PV.Use_Permission", Boolean.valueOf(false));
            Config.set("PV.Option.OnJoin-ShowPlayers", Boolean.valueOf(true));
            Config.set("PV.Option.OnJoin-Priority-For-Player-Option", Boolean.valueOf(true));
            Config.set("PV.Option.Item-Delay.Enable", Boolean.valueOf(true));
            Config.set("PV.Option.Item-Delay.Delay", Integer.valueOf(5));
            Config.set("PV.Option.Ultimate-Protection-Of-The-Items", Boolean.valueOf(true));
            Config.set("PV.Option.Inventory-Click.Interact-With-The-Object", Boolean.valueOf(true));
            Config.set("PV.Option.Inventory-Click.Show-Messages", Boolean.valueOf(true));
            Config.set("PV.Option.Inventory-Click.Sounds.Enable", Boolean.valueOf(true));
            Config.set("PV.Option.Inventory-Click.Sounds.Sound", "NOTE_PIANO");
            Config.set("PV.Option.Inventory-Click.Sounds.Volume", Integer.valueOf(10));
            Config.set("PV.Option.Inventory-Click.Sounds.Pitch", Integer.valueOf(1));
            Config.set("PV.Option.Interact-With-Item.Sounds.Enable", Boolean.valueOf(true));
            Config.set("PV.Option.Interact-With-Item.Sounds.Sound", "NOTE_PIANO");
            Config.set("PV.Option.Interact-With-Item.Sounds.Volume", Integer.valueOf(10));
            Config.set("PV.Option.Interact-With-Item.Sounds.Pitch", Integer.valueOf(1));
            Config.set("PV.OFF.Title", String.valueOf("&6Invisible player &8→ §cDisabled"));
            Config.set("PV.OFF.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&c&lRight click to hide players"
                }));
            Config.set("PV.OFF.Material.Material", String.valueOf("COMPASS"));
            Config.set("PV.OFF.Material.Amount", Integer.valueOf("1"));
            Config.set("PV.OFF.Material.Data-value", Integer.valueOf("0"));
            Config.set("PV.OFF.Material.Slot", Integer.valueOf("0"));
            Config.set("PV.ON.Title", String.valueOf("&6Invisible player &8→ §aEnabled"));
            Config.set("PV.ON.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&a&lRight click to show players"
                }));
            Config.set("PV.ON.Material.Material", String.valueOf("COMPASS"));
            Config.set("PV.ON.Material.Amount", Integer.valueOf("1"));
            Config.set("PV.ON.Material.Slot", Integer.valueOf("0"));
            Config.set("PV.ON.Material.Data-value", Integer.valueOf("0"));
            Config.set("PV.World.All_World", Boolean.valueOf(false));
            Config.set("PV.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            saveConfigFile();

        }
    }

}