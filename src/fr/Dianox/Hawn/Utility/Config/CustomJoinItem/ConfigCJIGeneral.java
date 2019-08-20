package fr.Dianox.Hawn.Utility.Config.CustomJoinItem;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigCJIGeneral {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigCJIGeneral() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "CustomJoinItem/General.yml");
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
            
            /*
             * General configuration
             */
            Config.set("Custom-Join-Item.Enable", true);
            Config.set("Custom-Join-Item.General-Option.Use_Permission_Per_Item", false);
            Config.set("Custom-Join-Item.General-Option.Inventory-Click", true);
            Config.set("Custom-Join-Item.General-Option.World.All_World", false);
            Config.set("Custom-Join-Item.General-Option.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            /*
             * For armor
             */
            Config.set("Custom-Join-Item.Items.Armor.Helmet.Enable", true);
            Config.set("Custom-Join-Item.Items.Armor.Helmet.Item.Material", "DIAMOND_HELMET");
            Config.set("Custom-Join-Item.Items.Armor.Chestplate.Enable", true);
            Config.set("Custom-Join-Item.Items.Armor.Chestplate.Item.Material", "DIAMOND_CHESTPLATE");
            Config.set("Custom-Join-Item.Items.Armor.Chestplate.Item.Amount", 50);
            Config.set("Custom-Join-Item.Items.Armor.Chestplate.Item.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&a&lLook ! I have a lore, and 50"
                }));
            Config.set("Custom-Join-Item.Items.Armor.Chestplate.Item.Command-List", java.util.Arrays.asList(new String[] {
                    "[command-player]: heal"
                }));
            
            Config.set("Custom-Join-Item.Items.Armor.Leggings.Enable", true);
            Config.set("Custom-Join-Item.Items.Armor.Leggings.Item.Material", "DIAMOND_LEGGINGS");
            Config.set("Custom-Join-Item.Items.Armor.Leggings.Item.Data-value", 0);
            Config.set("Custom-Join-Item.Items.Armor.Leggings.Item.Amount", 50);
            Config.set("Custom-Join-Item.Items.Armor.Leggings.Item.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&a&lIf you are not 1.13",
                    "You can still, use Data-value for ID",
                    "&cTo get a red wool for example"
                }));
            Config.set("Custom-Join-Item.Items.Armor.Leggings.Item.Command-List", java.util.Arrays.asList(new String[] {
                    "classic message"
                }));
            
            Config.set("Custom-Join-Item.Items.Armor.Boots.Enable", true);
            Config.set("Custom-Join-Item.Items.Armor.Boots.Item.Material", "DIAMOND_BOOTS");
            Config.set("Custom-Join-Item.Items.Armor.Boots.Item.Data-value", 0);
            Config.set("Custom-Join-Item.Items.Armor.Boots.Item.Amount", 1);
            Config.set("Custom-Join-Item.Items.Armor.Boots.Item.Title", "&6Yes I have a title");
            Config.set("Custom-Join-Item.Items.Armor.Boots.Item.Lore", java.util.Arrays.asList(new String[] {}));
            Config.set("Custom-Join-Item.Items.Armor.Boots.Item.Command-List", java.util.Arrays.asList(new String[] {
                    "[customcommand-player]: website",
                    "[sounds]: BLOCK_ANVIL_LAND"
                }));
            
            /*
             * For inventory item
             */
            Config.set("Custom-Join-Item.Items.Inventory.Enable", true);
            
            Config.set("Custom-Join-Item.Items.Inventory.Items.CompassSelectyourServer.Material", "COMPASS");
            Config.set("Custom-Join-Item.Items.Inventory.Items.CompassSelectyourServer.Slot", 0);
            Config.set("Custom-Join-Item.Items.Inventory.Items.CompassSelectyourServer.Title", "&cServers");
            Config.set("Custom-Join-Item.Items.Inventory.Items.CompassSelectyourServer.Command-List", java.util.Arrays.asList(new String[] {
                    "[command-player]: Serverlist",
                    "[sounds]: NOTE_PIANO"
                }));
            
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Material.Material", "SKULL_ITEM");
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Material.Skull-Name", "%player%");
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Material.Slot", 1);
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Material.Title", "&cProfil");
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Material.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "You can add the head of the player"
                }));
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Material.Command-List", java.util.Arrays.asList(new String[] {
                    "[sounds]: NOTE_PIANO"
                }));
            
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Visibility.Special-Items", "Special-HidePlayers");
            Config.set("Custom-Join-Item.Items.Inventory.Items.Player-Visibility.Slot", 8);
            
            saveConfigFile();

        }
    }

}
