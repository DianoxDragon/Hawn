package fr.dianox.hawn.utility.config.configs.messages.it_it;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


/**
 * The Italian messages (since 1.6), made from the English ones.
 */
public class ITAdminPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ITAdminPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/it_IT/AdminPanel.yml");
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
        		"&6Clicca per modificare questa cartella"
        		}));

        Config.set("Edit.File.Next.Name", "&bAVANTI");
        Config.set("Edit.File.Next.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eAvanti"
        		}));
        Config.set("Edit.File.Previous.Name", "&bINDIETRO");
        Config.set("Edit.File.Previous.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eIndietro"
        		}));

        Config.set("Edit.File.Back-Folder-Menu.Name", "&cTorna al menu delle cartelle");
        Config.set("Edit.File.Back-Menu.Name", "&cTorna al menu");

        Config.set("Edit.File.Items.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eClicca per modificare questo file"
        		}));
        Config.set("Edit.File.Items-Not-Possible.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&cNon puoi modificare questo file qui"
        		}));
        Config.set("Edit.File.Boolean.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eClicca per mettere questa riga a §cfalse"
        		}));
        Config.set("Edit.File.Boolean.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eClicca per mettere questa riga a §atrue"
        		}));
        // Since 1.5.2: the numbers, the choices and the texts
        Config.set("Edit.File.Number.Lore", java.util.Arrays.asList("&7Valore: &e%value%", " ", "&eClic sinistro: &a+%step%&e, clic destro: &c-%step%", "&eCon Shift: &a+%bigstep% &e/ &c-%bigstep%"));
        Config.set("Edit.File.Choice.Lore", java.util.Arrays.asList("&7Valore: &e%value%", " ", "&eClic: il successivo &7(%choices%)", "&eClic destro: quello precedente"));
        Config.set("Edit.File.Text.Lore", java.util.Arrays.asList("&7Valore: &f%value%", " ", "&eClicca, poi scrivi il nuovo valore in chat", "&7(&ecancel &7per tenerlo)"));
        Config.set("Edit.File.Text.Type", java.util.Collections.singletonList("%prefix% &7Scrivi in chat il nuovo valore di &e%arg1% &8(ora: &f%arg2%&8)&7, oppure &ecancel"));
        Config.set("Edit.File.Text.Changed", java.util.Collections.singletonList("%prefix% &e%arg1% &7è ora &f%arg2%"));
        Config.set("Edit.File.Text.Cancelled", java.util.Collections.singletonList("%prefix% &7%arg1%: nessun cambiamento"));

        Config.set("Edit.File.Special.CustomCommand.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Comando:&e %ap_command%",
        		" ",
        		"&n&bPermesso:",
                "&b► &e%ap_getperm%",
                "&b► Attivo? &e%ap_getpermyesorno%",
                "&b► Messaggio senza permesso? §e%ap_nopermmsg%",
                " ",
                "&eClicca per &cdisattivare&e il comando"
        		}));
        Config.set("Edit.File.Special.CustomCommand.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Comando:&e %ap_command%",
        		" ",
        		"&n&bPermesso:",
                "&b► &e%ap_getperm%",
                "&b► Attivo? &e%ap_getpermyesorno%",
                "&b► Messaggio senza permesso? §e%ap_nopermmsg%",
                " ",
                "&eClicca per &aattivare&e il comando"
        		}));

        Config.set("Edit.File.Special.WarpList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bMondo: §e%ap_getworld%",
                "&bX: §e%ap_x%§b, Y: §e%ap_y%§b, Z: §e%ap_z%",
                "&bYaw: §e%ap_yaw%",
                "&bPitch: §e%ap_pitch%",
                " ",
                "&6Qui non puoi modificare la lista dei warp"
        		}));
        Config.set("Edit.File.Special.SpawnList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bMondo: §e%ap_getworld%",
                "&bX: §e%ap_x%§b, Y: §e%ap_y%§b, Z: §e%ap_z%",
                "&bYaw: §e%ap_yaw%",
                "&bPitch: §e%ap_pitch%",
                " ",
                "&6Qui non puoi modificare la lista degli spawn"
        		}));

        Config.set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eConfigurazione di Hawn");
        Config.set("Special.Item.Reload-Hawn.Name", "&eRicarica Hawn");
        Config.set("Special.Item.Shutdown.Name", "&cSpegni il server");
        Config.set("Special.Item.Shutdown.Messages", java.util.Arrays.asList(new String[] {
        		"§cIl server si spegnerà",
        		}));
        Config.set("Special.Item.Reload.Name", "&eRicarica il server");
        Config.set("Special.Item.Reload.Messages", java.util.Arrays.asList(new String[] {
        		"§eIl server verrà ricaricato",
        		}));
        Config.set("Special.Item.Save-Players.Name", "&aSalva i giocatori");
        Config.set("Special.Item.Save-Players.Messages", java.util.Arrays.asList(new String[] {
        		"§eIl server salverà i dati dei giocatori",
        		}));

        Config.set("Special.Item.Notice.Name", "&aNota");
        Config.set("Special.Item.Notice.Lore", java.util.Arrays.asList(new String[] {
        		" ", 
        		"§eIn realtà, non posso mettere qui tutti i file di configurazione.", 
        		"§eSe vuoi modificare tutto quello che manca,", 
        		"§efallo a mano"
        		}));

        Config.set("Error.Edit-Files", java.util.Arrays.asList(new String[] {
        		"&cQuesto file... non sembra modificabile..",
        		"&cFallo a mano"
        		}));
        Config.set("Error.Edit-Empty", java.util.Arrays.asList(new String[] {
        		"&cIl file è vuoto"
        		}));

        Config.set("Error.Not-listed", java.util.Arrays.asList(new String[] {
        		"%prefix% Mi dispiace, non sei nella lista di chi può usare il comando (file di configurazione)"
        		}));

        Config.set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
        		"%prefix% &7Una modifica è stata fatta da &e%player%&7 nel pannello admin",
                "%prefix% &e%arg1%&7 nel file&b %arg2%"
        		}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}