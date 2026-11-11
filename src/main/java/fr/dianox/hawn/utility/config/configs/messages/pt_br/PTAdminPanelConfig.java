package fr.dianox.hawn.utility.config.configs.messages.pt_br;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


/**
 * The Portuguese (Brazil) messages (since 1.6), made from the English ones.
 */
public class PTAdminPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public PTAdminPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;

		
		file = new File(pl.getDataFolder(), "Messages/pt_BR/AdminPanel.yml");
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
        		"&6Clique para editar esta pasta"
        		}));

        Config.set("Edit.File.Next.Name", "&bPRÓXIMA");
        Config.set("Edit.File.Next.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&ePróxima"
        		}));
        Config.set("Edit.File.Previous.Name", "&bANTERIOR");
        Config.set("Edit.File.Previous.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eAnterior"
        		}));

        Config.set("Edit.File.Back-Folder-Menu.Name", "&cVoltar ao menu de pastas");
        Config.set("Edit.File.Back-Menu.Name", "&cVoltar ao menu");

        Config.set("Edit.File.Items.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eClique para editar este arquivo"
        		}));
        Config.set("Edit.File.Items-Not-Possible.Lore", java.util.Arrays.asList(new String[] {
        		" ",
        		"&cVocê não pode editar este arquivo aqui"
        		}));
        Config.set("Edit.File.Boolean.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eClique para colocar esta linha em §cfalse"
        		}));
        Config.set("Edit.File.Boolean.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&eClique para colocar esta linha em §atrue"
        		}));
        // Since 1.5.2: the numbers, the choices and the texts
        Config.set("Edit.File.Number.Lore", java.util.Arrays.asList("&7Valor: &e%value%", " ", "&eClique esquerdo: &a+%step%&e, clique direito: &c-%step%", "&eCom Shift: &a+%bigstep% &e/ &c-%bigstep%"));
        Config.set("Edit.File.Choice.Lore", java.util.Arrays.asList("&7Valor: &e%value%", " ", "&eClique: o próximo &7(%choices%)", "&eClique direito: o anterior"));
        Config.set("Edit.File.Text.Lore", java.util.Arrays.asList("&7Valor: &f%value%", " ", "&eClique e escreva o novo valor no chat", "&7(&ecancel &7para manter)"));
        Config.set("Edit.File.Text.Type", java.util.Collections.singletonList("%prefix% &7Escreva no chat o novo valor de &e%arg1% &8(agora: &f%arg2%&8)&7, ou &ecancel"));
        Config.set("Edit.File.Text.Changed", java.util.Collections.singletonList("%prefix% &e%arg1% &7agora é &f%arg2%"));
        Config.set("Edit.File.Text.Cancelled", java.util.Collections.singletonList("%prefix% &7%arg1%: nada mudou"));

        Config.set("Edit.File.Special.CustomCommand.True", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Comando:&e %ap_command%",
        		" ",
        		"&n&bPermissão:",
                "&b► &e%ap_getperm%",
                "&b► Ativo? &e%ap_getpermyesorno%",
                "&b► Mensagem sem permissão? §e%ap_nopermmsg%",
                " ",
                "&eClique para &cdesativar&e o comando"
        		}));
        Config.set("Edit.File.Special.CustomCommand.False", java.util.Arrays.asList(new String[] {
        		" ",
        		"&b► Comando:&e %ap_command%",
        		" ",
        		"&n&bPermissão:",
                "&b► &e%ap_getperm%",
                "&b► Ativo? &e%ap_getpermyesorno%",
                "&b► Mensagem sem permissão? §e%ap_nopermmsg%",
                " ",
                "&eClique para &aativar&e o comando"
        		}));

        Config.set("Edit.File.Special.WarpList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bMundo: §e%ap_getworld%",
                "&bX: §e%ap_x%§b, Y: §e%ap_y%§b, Z: §e%ap_z%",
                "&bYaw: §e%ap_yaw%",
                "&bPitch: §e%ap_pitch%",
                " ",
                "&6Aqui você não pode editar a lista de warps"
        		}));
        Config.set("Edit.File.Special.SpawnList.Lore", java.util.Arrays.asList(new String[] {
        		" ",
                "&bMundo: §e%ap_getworld%",
                "&bX: §e%ap_x%§b, Y: §e%ap_y%§b, Z: §e%ap_z%",
                "&bYaw: §e%ap_yaw%",
                "&bPitch: §e%ap_pitch%",
                " ",
                "&6Aqui você não pode editar a lista de spawns"
        		}));

        Config.set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eConfiguração do Hawn");
        Config.set("Special.Item.Reload-Hawn.Name", "&eRecarregar o Hawn");
        Config.set("Special.Item.Shutdown.Name", "&cDesligar o servidor");
        Config.set("Special.Item.Shutdown.Messages", java.util.Arrays.asList(new String[] {
        		"§cO servidor vai desligar",
        		}));
        Config.set("Special.Item.Reload.Name", "&eRecarregar o servidor");
        Config.set("Special.Item.Reload.Messages", java.util.Arrays.asList(new String[] {
        		"§eO servidor vai recarregar",
        		}));
        Config.set("Special.Item.Save-Players.Name", "&aSalvar os jogadores");
        Config.set("Special.Item.Save-Players.Messages", java.util.Arrays.asList(new String[] {
        		"§eO servidor vai salvar os dados dos jogadores",
        		}));

        Config.set("Special.Item.Notice.Name", "&aAviso");
        Config.set("Special.Item.Notice.Lore", java.util.Arrays.asList(new String[] {
        		" ", 
        		"§eNa verdade, não consigo colocar todos os arquivos de configuração aqui.", 
        		"§eSe quiser editar tudo o que falta,", 
        		"§efaça à mão"
        		}));

        Config.set("Error.Edit-Files", java.util.Arrays.asList(new String[] {
        		"&cEste arquivo... não parece poder ser mudado..",
        		"&cFaça à mão"
        		}));
        Config.set("Error.Edit-Empty", java.util.Arrays.asList(new String[] {
        		"&cO arquivo está vazio"
        		}));

        Config.set("Error.Not-listed", java.util.Arrays.asList(new String[] {
        		"%prefix% Desculpe, você não está na lista de quem pode usar o comando (arquivos de configuração)"
        		}));

        Config.set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
        		"%prefix% &7Uma mudança foi feita por &e%player%&7 no painel admin",
                "%prefix% &e%arg1%&7 no arquivo&b %arg2%"
        		}));

        Config = ConfigDefaults.apply(file, loaded, Config);
    }
}