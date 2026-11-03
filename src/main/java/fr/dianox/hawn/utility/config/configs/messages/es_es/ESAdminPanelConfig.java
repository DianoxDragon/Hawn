package fr.dianox.hawn.utility.config.configs.messages.es_es;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


/**
 * The Spanish messages (Messages/es_ES), since Hawn 1.5.6: the texts of AdminPanelConfig translated.
 */
public class ESAdminPanelConfig {

	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;

	public ESAdminPanelConfig() {}

	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		file = new File(pl.getDataFolder(), "Messages/es_ES/AdminPanel.yml");
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
        		"&6Haz clic para editar esta carpeta"
        		}));

        Config.set("Edit.File.Next.Name", "&bSIGUIENTE");
        Config.set("Edit.File.Next.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eSiguiente"
        		}));
        Config.set("Edit.File.Previous.Name", "&bANTERIOR");
        Config.set("Edit.File.Previous.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eAnterior"
        		}));

        Config.set("Edit.File.Back-Folder-Menu.Name", "&cVolver al menú de carpetas");
        Config.set("Edit.File.Back-Menu.Name", "&cVolver al menú");

        Config.set("Edit.File.Items.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eHaz clic para editar este archivo"
        		}));
        Config.set("Edit.File.Items-Not-Possible.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&cNo puedes editar este archivo aquí"
        		}));
        Config.set("Edit.File.Boolean.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eHaz clic para poner esta línea en §cfalse"
        		}));
        Config.set("Edit.File.Boolean.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eHaz clic para poner esta línea en §atrue"
        		}));
        // Since 1.5.2: the numbers, the choices and the texts
        Config.set("Edit.File.Number.Lore", java.util.Arrays.asList("&7Valor: &e%value%", " ", "&eClic izquierdo: &a+%step%&e, clic derecho: &c-%step%", "&eCon Shift: &a+%bigstep% &e/ &c-%bigstep%"));
        Config.set("Edit.File.Choice.Lore", java.util.Arrays.asList("&7Valor: &e%value%", " ", "&eClic: el siguiente &7(%choices%)", "&eClic derecho: el anterior"));
        Config.set("Edit.File.Text.Lore", java.util.Arrays.asList("&7Valor: &f%value%", " ", "&eHaz clic y escribe el nuevo valor en el chat", "&7(&ecancel &7para conservarlo)"));
        Config.set("Edit.File.Text.Type", java.util.Collections.singletonList("%prefix% &7Escribe el nuevo valor de &e%arg1% &7en el chat &8(ahora: &f%arg2%&8)&7, o &ecancel"));
        Config.set("Edit.File.Text.Changed", java.util.Collections.singletonList("%prefix% &e%arg1% &7ahora es &f%arg2%"));
        Config.set("Edit.File.Text.Cancelled", java.util.Collections.singletonList("%prefix% &7%arg1%: no ha cambiado nada"));

        Config.set("Edit.File.Special.CustomCommand.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Comando :&e %ap_command%",
        		" ",
        		"&n&bPermiso :",
                "&b► &e%ap_getperm%",
                "&b► ¿Activado? &e%ap_getpermyesorno%",
                "&b► ¿Mensaje sin permiso? §e%ap_nopermmsg%",
                " ",
                "&eHaz clic para &cdesactivar&e el comando"
        		}));
        Config.set("Edit.File.Special.CustomCommand.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Comando :&e %ap_command%",
        		" ",
        		"&n&bPermiso :",
                "&b► &e%ap_getperm%",
                "&b► ¿Activado? &e%ap_getpermyesorno%",
                "&b► ¿Mensaje sin permiso? §e%ap_nopermmsg%",
                " ",
                "&eHaz clic para &aactivar&e el comando"
        		}));

        Config.set("Edit.File.Special.WarpList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bMundo : §e%ap_getworld%",
                "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                "&bYaw : §e%ap_yaw%",
                "&bPitch : §e%ap_pitch%",
                " ",
                "&6No puedes editar la lista de warps aquí"
        		}));
        Config.set("Edit.File.Special.SpawnList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bMundo : §e%ap_getworld%",
                "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                "&bYaw : §e%ap_yaw%",
                "&bPitch : §e%ap_pitch%",
                " ",
                "&6No puedes editar la lista de spawns aquí"
        		}));

        Config.set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eConfiguración de Hawn");
        Config.set("Special.Item.Reload-Hawn.Name", "&eRecargar Hawn");
        Config.set("Special.Item.Shutdown.Name", "&cApagar el servidor");
        Config.set("Special.Item.Shutdown.Messages", java.util.Arrays.asList(new String[] {
        		"§cEl servidor se va a apagar",
        		}));
        Config.set("Special.Item.Reload.Name", "&eRecargar el servidor");
        Config.set("Special.Item.Reload.Messages", java.util.Arrays.asList(new String[] {
        		"§eEl servidor se va a recargar",
        		}));
        Config.set("Special.Item.Save-Players.Name", "&aGuardar los jugadores");
        Config.set("Special.Item.Save-Players.Messages", java.util.Arrays.asList(new String[] {
        		"§eEl servidor va a guardar los datos de los jugadores",
        		}));

        Config.set("Special.Item.Notice.Name", "&aAviso");
        Config.set("Special.Item.Notice.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"§eEn realidad, no puedo poner aquí todos los archivos de configuración.",
        		"§eSi quieres editar todo lo que falta",
        		"§ehazlo a mano, por favor"
        		}));

        Config.set("Error.Edit-Files", java.util.Arrays.asList(new String[] {
        		"&cEste archivo... no parece que se pueda cambiar..",
        		"&cHazlo a mano"
        		}));
        Config.set("Error.Edit-Empty", java.util.Arrays.asList(new String[] {
        		"&cEl archivo está vacío"
        		}));

        Config.set("Error.Not-listed", java.util.Arrays.asList(new String[] {
        		"%prefix% Lo siento, pero no estás en la lista para usar este comando (archivos de configuración)"
        		}));

        Config.set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
        		"%prefix% &e%player%&7 ha hecho un cambio en el panel de administración",
                "%prefix% &e%arg1%&7 en el archivo&b %arg2%"
        		}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}
