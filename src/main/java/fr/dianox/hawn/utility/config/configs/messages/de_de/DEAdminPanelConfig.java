package fr.dianox.hawn.utility.config.configs.messages.de_de;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


/**
 * The German messages (Messages/de_DE), since Hawn 1.4.6: the texts of AdminPanelConfig translated.
 */
public class DEAdminPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public DEAdminPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/de_DE/AdminPanel.yml");
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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
        Config.set("Edit.Folder.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&6Klicke, um diesen Ordner zu bearbeiten"
        		}));

        Config.set("Edit.File.Next.Name", "&bWEITER");
        Config.set("Edit.File.Next.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eWeiter"
        		}));
        Config.set("Edit.File.Previous.Name", "&bZURÜCK");
        Config.set("Edit.File.Previous.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eZurück"
        		}));

        Config.set("Edit.File.Back-Folder-Menu.Name", "&cZurück zum Ordnermenü");
        Config.set("Edit.File.Back-Menu.Name", "&cZurück zum Menü");

        Config.set("Edit.File.Items.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eKlicke, um diese Datei zu bearbeiten"
        		}));
        Config.set("Edit.File.Items-Not-Possible.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&cDiese Datei kann hier nicht bearbeitet werden"
        		}));
        Config.set("Edit.File.Boolean.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eKlicke, um diese Zeile auf §cfalse &ezu setzen"
        		}));
        Config.set("Edit.File.Boolean.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eKlicke, um diese Zeile auf §atrue &ezu setzen"
        		}));

        Config.set("Edit.File.Special.CustomCommand.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Befehl:&e %ap_command%",
        		" ",
        		"&n&bBerechtigung:",
                "&b► &e%ap_getperm%",
                "&b► Aktiv? &e%ap_getpermyesorno%",
                "&b► Nachricht ohne Berechtigung? §e%ap_nopermmsg%",
                " ",
                "&eKlicke, um den Befehl zu &cdeaktivieren"
        		}));
        Config.set("Edit.File.Special.CustomCommand.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Befehl:&e %ap_command%",
        		" ",
        		"&n&bBerechtigung:",
                "&b► &e%ap_getperm%",
                "&b► Aktiv? &e%ap_getpermyesorno%",
                "&b► Nachricht ohne Berechtigung? §e%ap_nopermmsg%",
                " ",
                "&eKlicke, um den Befehl zu &aaktivieren"
        		}));

        Config.set("Edit.File.Special.WarpList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bWelt: §e%ap_getworld%",
                "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                "&bYaw : §e%ap_yaw%",
                "&bPitch : §e%ap_pitch%",
                " ",
                "&6Die Warp-Liste kann hier nicht bearbeitet werden"
        		}));
        Config.set("Edit.File.Special.SpawnList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bWelt: §e%ap_getworld%",
                "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                "&bYaw : §e%ap_yaw%",
                "&bPitch : §e%ap_pitch%",
                " ",
                "&6Die Spawn-Liste kann hier nicht bearbeitet werden"
        		}));

        Config.set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eHawn-Konfiguration");
        Config.set("Special.Item.Reload-Hawn.Name", "&eHawn neu laden");
        Config.set("Special.Item.Shutdown.Name", "&cServer herunterfahren");
        Config.set("Special.Item.Shutdown.Messages", java.util.Arrays.asList(new String[] {
        		"§cDer Server wird heruntergefahren",
        		}));
        Config.set("Special.Item.Reload.Name", "&eServer neu laden");
        Config.set("Special.Item.Reload.Messages", java.util.Arrays.asList(new String[] {
        		"§eDer Server wird neu geladen",
        		}));
        Config.set("Special.Item.Save-Players.Name", "&aSpieler speichern");
        Config.set("Special.Item.Save-Players.Messages", java.util.Arrays.asList(new String[] {
        		"§eDer Server speichert die Spielerdaten",
        		}));

        Config.set("Special.Item.Notice.Name", "&aHinweis");
        Config.set("Special.Item.Notice.Lore", java.util.Arrays.asList(new String[] {
        		" ", 
        		"§eNicht alle Konfigurationsdateien sind hier verfügbar.", 
        		"§eUm alles Fehlende zu bearbeiten,", 
        		"§eändere die Dateien bitte von Hand"
        		}));

        Config.set("Error.Edit-Files", java.util.Arrays.asList(new String[] {
        		"&cDiese Datei kann anscheinend nicht geändert werden...",
        		"&cÄndere sie von Hand"
        		}));
        Config.set("Error.Edit-Empty", java.util.Arrays.asList(new String[] {
        		"&cDie Datei ist leer"
        		}));

        Config.set("Error.Not-listed", java.util.Arrays.asList(new String[] {
        		"%prefix% Du stehst nicht auf der Liste für diesen Befehl (Konfigurationsdateien)"
        		}));

        Config.set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
        		"%prefix% &7Eine Änderung von &e%player%&7 im Admin-Panel wurde erkannt",
                "%prefix% &e%arg1%&7 in der Datei &b%arg2%"
        		}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}