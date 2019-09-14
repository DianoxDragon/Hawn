package fr.Dianox.Hawn.Utility.Config.Messages.Administration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.Dianox.Hawn.Main;

public class AdminPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public AdminPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/Administration/AdminPanel.yml");
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
                        
            /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
            Config.set("Edit.Folder.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&6Click to edit this folder"
            		}));
            
            Config.set("Edit.File.Next.Name", "&bNEXT");
            Config.set("Edit.File.Next.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eNext"
            		}));
            Config.set("Edit.File.Previous.Name", "&bPREVIOUS");
            Config.set("Edit.File.Previous.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&ePrevious"
            		}));
            
            Config.set("Edit.File.Back-Folder-Menu.Name", "&cBack to the folder menu");
            Config.set("Edit.File.Back-Menu.Name", "&cBack to the menu");
            
            Config.set("Edit.File.Items.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eClick to edit this file"
            		}));
            Config.set("Edit.File.Items-Not-Possible.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&cYou can't edit the file here"
            		}));
            Config.set("Edit.File.Boolean.True", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eClick to put this line to §cfalse"
            		}));
            Config.set("Edit.File.Boolean.False", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eClick to put this line to §atrue"
            		}));
            
            Config.set("Edit.File.Special.CustomCommand.True", java.util.Arrays.asList(new String[] {
            		" ",
            		"&b► Command :&e %ap_command%",
            		" ",
            		"&n&bPermission :",
                    "&b► &e%ap_getperm%",
                    "&b► Enable ? &e%ap_getpermyesorno%",
                    "&b► No permission message ? §e%ap_nopermmsg%",
                    " ",
                    "&eClick to &cdisable&e the command"
            		}));
            Config.set("Edit.File.Special.CustomCommand.False", java.util.Arrays.asList(new String[] {
            		" ",
            		"&b► Command :&e %ap_command%",
            		" ",
            		"&n&bPermission :",
                    "&b► &e%ap_getperm%",
                    "&b► Enable ? &e%ap_getpermyesorno%",
                    "&b► No permission message ? §e%ap_nopermmsg%",
                    " ",
                    "&eClick to &aenable&e the command"
            		}));
            
            Config.set("Edit.File.Special.WarpList.Lore", java.util.Arrays.asList(new String[] {
            		" ",
                    "&bWorld : §e%ap_getworld%",
                    "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                    "&bYaw : §e%ap_yaw%",
                    "&bPitch : §e%ap_pitch%",
                    " ",
                    "&6You can't edit warp list here"
            		}));
            Config.set("Edit.File.Special.SpawnList.Lore", java.util.Arrays.asList(new String[] {
            		" ",
                    "&bWorld : §e%ap_getworld%",
                    "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                    "&bYaw : §e%ap_yaw%",
                    "&bPitch : §e%ap_pitch%",
                    " ",
                    "&6You can't edit spawn list here"
            		}));
            
            Config.set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eHawn configuration");
            Config.set("Special.Item.Reload-Hawn.Name", "&eReload Hawn");
            Config.set("Special.Item.Shutdown.Name", "&cShutdown the server");
            Config.set("Special.Item.Shutdown.Messages", java.util.Arrays.asList(new String[] {
            		"§cThe server will shutdown",
            		}));
            Config.set("Special.Item.Reload.Name", "&eReload The server");
            Config.set("Special.Item.Reload.Messages", java.util.Arrays.asList(new String[] {
            		"§eThe server will reload",
            		}));
            Config.set("Special.Item.Save-Players.Name", "&aSave players");
            Config.set("Special.Item.Save-Players.Messages", java.util.Arrays.asList(new String[] {
            		"§eThe server will save players data",
            		}));
            
            Config.set("Special.Item.Notice.Name", "&aNotice");
            Config.set("Special.Item.Notice.Lore", java.util.Arrays.asList(new String[] {
            		" ", 
            		"§eActually, I can't put all the config file here.", 
            		"§eIf you want to edit everything that is missing", 
            		"§eplease do it manually"
            		}));
            
            Config.set("Error.Edit-Files", java.util.Arrays.asList(new String[] {
            		"&cThis file... does not seem to be able to be changed..",
            		"&cDo it manually"
            		}));
            Config.set("Error.Edit-Empty", java.util.Arrays.asList(new String[] {
            		"&cThe file is empty"
            		}));
            
            Config.set("Error.Not-listed", java.util.Arrays.asList(new String[] {
            		"%prefix% Sorry but you are not listed to use the command (configuration files)"
            		}));
            
            Config.set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
            		"%prefix% &7A modification has been detected by &e%player%&7 on the admin panel",
                    "%prefix% &e%arg1%&7 in the file&b %arg2%"
            		}));
            
            saveConfigFile();

        }
    }
}