package fr.Dianox.Hawn.Utility.Config.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class PlayerEventsConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PlayerEventsConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Events/PlayerEvents.yml");
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
            
            Config.set("Items.Drop.Disable", Boolean.valueOf(true));
            Config.set("Items.Drop.Bypass", Boolean.valueOf(true));
            Config.set("Items.Drop.World.All_World", Boolean.valueOf(false));
            Config.set("Items.Drop.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Items.PickUp.Disable", Boolean.valueOf(true));
            Config.set("Items.PickUp.Bypass", Boolean.valueOf(true));
            Config.set("Items.PickUp.World.All_World", Boolean.valueOf(false));
            Config.set("Items.PickUp.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Items.Move.Disable", Boolean.valueOf(true));
            Config.set("Items.Move.Bypass", Boolean.valueOf(true));
            Config.set("Items.Move.World.All_World", Boolean.valueOf(false));
            Config.set("Items.Move.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Items.Damage-Item.Disable", Boolean.valueOf(true));
            Config.set("Items.Damage-Item.Bypass", Boolean.valueOf(true));
            Config.set("Items.Damage-Item.World.All_World", Boolean.valueOf(false));
            Config.set("Items.Damage-Item.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Items.Clear-Drops-On-Death.Enable", Boolean.valueOf(true));
            Config.set("Items.Clear-Drops-On-Death.Bypass", Boolean.valueOf(true));
            Config.set("Items.Clear-Drops-On-Death.World.All_World", Boolean.valueOf(false));
            Config.set("Items.Clear-Drops-On-Death.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Death.Respawn.Enable", Boolean.valueOf(true));
            Config.set("Death.Respawn.Use_Permission", Boolean.valueOf(false));
            Config.set("Death.Respawn.Player.Respawn-After", Integer.valueOf(5));
            Config.set("Death.Respawn.Player.Teleport-Spawn", Boolean.valueOf(true));
            Config.set("Death.Respawn.Player.Custom-Spawn.Enable", Boolean.valueOf(true));
            Config.set("Death.Respawn.Player.Custom-Spawn.Spawn", String.valueOf("CHANGE ME"));
            Config.set("Death.Respawn.Player.World.All_World", Boolean.valueOf(false));
            Config.set("Death.Respawn.Player.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Death.Death-Message.Disable", Boolean.valueOf(true));
            Config.set("Death.Death-Message.World.All_World", Boolean.valueOf(false));
            Config.set("Death.Death-Message.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Keep-Gamemode.Enable", Boolean.valueOf(false));
            Config.set("Keep-Gamemode.Bypass-With-Permission", Boolean.valueOf(true));
            Config.set("Keep-Gamemode.World.All_World", Boolean.valueOf(false));
            Config.set("Keep-Gamemode.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("Keep.Food.Enable", Boolean.valueOf(true));
            Config.set("Keep.Food.Bypass-With-Permission", Boolean.valueOf(false));
            Config.set("Keep.Food.World.All_World", Boolean.valueOf(false));
            Config.set("Keep.Food.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            
            saveConfigFile();

        }
    }

}