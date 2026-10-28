package fr.dianox.hawn.utility.config.configs.messages.nl_nl;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


/**
 * The Dutch messages (Messages/nl_NL), since Hawn 1.5.4: the texts of AdminPanelConfig translated.
 */
public class NLAdminPanelConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public NLAdminPanelConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/nl_NL/AdminPanel.yml");
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
        		"&6Klik om deze map te bewerken"
        		}));

        Config.set("Edit.File.Next.Name", "&bVOLGENDE");
        Config.set("Edit.File.Next.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eVolgende"
        		}));
        Config.set("Edit.File.Previous.Name", "&bVORIGE");
        Config.set("Edit.File.Previous.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eVorige"
        		}));

        Config.set("Edit.File.Back-Folder-Menu.Name", "&cTerug naar het mappenmenu");
        Config.set("Edit.File.Back-Menu.Name", "&cTerug naar het menu");

        Config.set("Edit.File.Items.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eKlik om dit bestand te bewerken"
        		}));
        Config.set("Edit.File.Items-Not-Possible.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&cDit bestand kan hier niet worden bewerkt"
        		}));
        Config.set("Edit.File.Boolean.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eKlik om deze regel op §cfalse &ete zetten"
        		}));
        Config.set("Edit.File.Boolean.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eKlik om deze regel op §atrue &ete zetten"
        		}));
        // Since 1.5.2: the numbers, the choices and the texts
        Config.set("Edit.File.Number.Lore", java.util.Arrays.asList("&7Waarde: &e%value%", " ", "&eLinkerklik: &a+%step%&e, rechterklik: &c-%step%", "&eMet Shift: &a+%bigstep% &e/ &c-%bigstep%"));
        Config.set("Edit.File.Choice.Lore", java.util.Arrays.asList("&7Waarde: &e%value%", " ", "&eKlik: de volgende &7(%choices%)", "&eRechterklik: de vorige"));
        Config.set("Edit.File.Text.Lore", java.util.Arrays.asList("&7Waarde: &f%value%", " ", "&eKlik en typ dan de nieuwe waarde in de chat", "&7(&ecancel &7houdt ze)"));
        Config.set("Edit.File.Text.Type", java.util.Collections.singletonList("%prefix% &7Typ de nieuwe waarde van &e%arg1% &7in de chat &8(nu: &f%arg2%&8)&7, of &ecancel"));
        Config.set("Edit.File.Text.Changed", java.util.Collections.singletonList("%prefix% &e%arg1% &7is nu &f%arg2%"));
        Config.set("Edit.File.Text.Cancelled", java.util.Collections.singletonList("%prefix% &7%arg1%: niets gewijzigd"));

        Config.set("Edit.File.Special.CustomCommand.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Commando:&e %ap_command%",
        		" ",
        		"&n&bPermissie:",
                "&b► &e%ap_getperm%",
                "&b► Actief? &e%ap_getpermyesorno%",
                "&b► Bericht zonder permissie? §e%ap_nopermmsg%",
                " ",
                "&eKlik om het commando &cuit te zetten"
        		}));
        Config.set("Edit.File.Special.CustomCommand.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Commando:&e %ap_command%",
        		" ",
        		"&n&bPermissie:",
                "&b► &e%ap_getperm%",
                "&b► Actief? &e%ap_getpermyesorno%",
                "&b► Bericht zonder permissie? §e%ap_nopermmsg%",
                " ",
                "&eKlik om het commando &aaan te zetten"
        		}));

        Config.set("Edit.File.Special.WarpList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bWereld: §e%ap_getworld%",
                "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                "&bYaw : §e%ap_yaw%",
                "&bPitch : §e%ap_pitch%",
                " ",
                "&6De warplijst kan hier niet worden bewerkt"
        		}));
        Config.set("Edit.File.Special.SpawnList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bWereld: §e%ap_getworld%",
                "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                "&bYaw : §e%ap_yaw%",
                "&bPitch : §e%ap_pitch%",
                " ",
                "&6De spawnlijst kan hier niet worden bewerkt"
        		}));

        Config.set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eConfiguratie van Hawn");
        Config.set("Special.Item.Reload-Hawn.Name", "&eHawn herladen");
        Config.set("Special.Item.Shutdown.Name", "&cServer afsluiten");
        Config.set("Special.Item.Shutdown.Messages", java.util.Arrays.asList(new String[] {
        		"§cDe server wordt afgesloten",
        		}));
        Config.set("Special.Item.Reload.Name", "&eServer herladen");
        Config.set("Special.Item.Reload.Messages", java.util.Arrays.asList(new String[] {
        		"§eDe server wordt herladen",
        		}));
        Config.set("Special.Item.Save-Players.Name", "&aSpelers opslaan");
        Config.set("Special.Item.Save-Players.Messages", java.util.Arrays.asList(new String[] {
        		"§eDe server slaat de gegevens van de spelers op",
        		}));

        Config.set("Special.Item.Notice.Name", "&aLet op");
        Config.set("Special.Item.Notice.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"§eNiet alle configuratiebestanden staan hier.",
        		"§eOm de rest te bewerken,",
        		"§ewijzig je de bestanden met de hand"
        		}));

        Config.set("Error.Edit-Files", java.util.Arrays.asList(new String[] {
        		"&cDit bestand kan blijkbaar niet worden gewijzigd...",
        		"&cWijzig het met de hand"
        		}));
        Config.set("Error.Edit-Empty", java.util.Arrays.asList(new String[] {
        		"&cHet bestand is leeg"
        		}));

        Config.set("Error.Not-listed", java.util.Arrays.asList(new String[] {
        		"%prefix% Je staat niet op de lijst voor dit commando (configuratiebestanden)"
        		}));

        Config.set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
        		"%prefix% &7Een wijziging van &e%player%&7 in het adminpaneel is gezien",
                "%prefix% &e%arg1%&7 in het bestand &b%arg2%"
        		}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}
