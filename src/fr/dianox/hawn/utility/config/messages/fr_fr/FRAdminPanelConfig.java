package fr.dianox.hawn.utility.config.messages.fr_fr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FRAdminPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public FRAdminPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/AdminPanel.yml");
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
            		"&6Cliquez pour éditer ce dossier"
            		}));
            
            Config.set("Edit.File.Next.Name", "&bSUIVANT");
            Config.set("Edit.File.Next.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eSuivant"
            		}));
            Config.set("Edit.File.Previous.Name", "&bPRÉCÉDENT");
            Config.set("Edit.File.Previous.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&ePrécédent"
            		}));
            
            Config.set("Edit.File.Back-Folder-Menu.Name", "&cRetour au menu du dossier");
            Config.set("Edit.File.Back-Menu.Name", "&cRetour au menu");
            
            Config.set("Edit.File.Items.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eCliquez pour éditer ce fichier"
            		}));
            Config.set("Edit.File.Items-Not-Possible.Lore", java.util.Arrays.asList(new String[] {
            		" ",
            		"&cVous ne pouvez pas éditer le fichier ici"
            		}));
            Config.set("Edit.File.Boolean.True", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eCliquez pour mettre cette ligne sur §cfaux"
            		}));
            Config.set("Edit.File.Boolean.False", java.util.Arrays.asList(new String[] {
            		" ",
            		"&eCliquez pour mettre cette ligne sur §avrai"
            		}));
            
            Config.set("Edit.File.Special.CustomCommand.True", java.util.Arrays.asList(new String[] {
            		" ",
            		"&b► Commande :&e %ap_command%",
            		" ",
            		"&n&bPermission :",
                    "&b► &e%ap_getperm%",
                    "&b► Activé ? &e%ap_getpermyesorno%",
                    "&b► Message d'interdiction ? §e%ap_nopermmsg%",
                    " ",
                    "&eCliquez ici pour &cdésactiver&e la commande"
            		}));
            Config.set("Edit.File.Special.CustomCommand.False", java.util.Arrays.asList(new String[] {
            		" ",
            		"&b► Commande :&e %ap_command%",
            		" ",
            		"&n&bPermission :",
                    "&b► &e%ap_getperm%",
                    "&b► Activé ? &e%ap_getpermyesorno%",
                    "&b► Message d'interdiction ? §e%ap_nopermmsg%",
                    " ",
                    "&eCliquez ici pour &aactiver&e la commande"
            		}));
            
            Config.set("Edit.File.Special.WarpList.Lore", java.util.Arrays.asList(new String[] {
            		" ",
                    "&bMonde : §e%ap_getworld%",
                    "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                    "&bYaw : §e%ap_yaw%",
                    "&bPitch : §e%ap_pitch%",
                    " ",
                    "&6Vous ne pouvez pas éditer la liste des warps ici"
            		}));
            Config.set("Edit.File.Special.SpawnList.Lore", java.util.Arrays.asList(new String[] {
            		" ",
                    "&bMonde : §e%ap_getworld%",
                    "&bX : §e%ap_x%§b, Y : §e%ap_y%§b, Z : §e%ap_z%",
                    "&bYaw : §e%ap_yaw%",
                    "&bPitch : §e%ap_pitch%",
                    " ",
                    "&6Vous ne pouvez pas modifier la liste des spawns ici"
            		}));
            
            Config.set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eConfiguration de Hawn");
            Config.set("Special.Item.Reload-Hawn.Name", "&eRecharger Hawn");
            Config.set("Special.Item.Shutdown.Name", "&cArrêter le serveur");
            Config.set("Special.Item.Shutdown.Messages", java.util.Arrays.asList(new String[] {
            		"§cLe serveur va s'arrêter",
            		}));
            Config.set("Special.Item.Reload.Name", "&eRecharger le serveur");
            Config.set("Special.Item.Reload.Messages", java.util.Arrays.asList(new String[] {
            		"§eLe serveur va recharger",
            		}));
            Config.set("Special.Item.Save-Players.Name", "&aSauvegarder les joueurs");
            Config.set("Special.Item.Save-Players.Messages", java.util.Arrays.asList(new String[] {
            		"§eLe serveur va sauvegarder les données des joueurs",
            		}));
            
            Config.set("Special.Item.Notice.Name", "&aRemarque");
            Config.set("Special.Item.Notice.Lore", java.util.Arrays.asList(new String[] {
            		" ", 
            		"§eActuellement, je ne peux pas mettre tous les", 
            		"§efichiers de configuration ici. Si vous voulez",
            		"§eéditer tout ce qui manque, veuillez le faire manuellement",
            		}));
            
            Config.set("Error.Edit-Files", java.util.Arrays.asList(new String[] {
            		"&cCe fichier... ne semble pas pouvoir être modifié..",
            		"&cFaites-le manuellement"
            		}));
            Config.set("Error.Edit-Empty", java.util.Arrays.asList(new String[] {
            		"&cLe fichier est vide"
            		}));
            
            Config.set("Error.Not-listed", java.util.Arrays.asList(new String[] {
            		"%prefix% Désolé mais vous n'êtes pas listé pour utiliser la commande (fichiers de configuration)"
            		}));
            
            Config.set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
            		"%prefix% &7Une modification a été détecté par &e%player%&7 sur le panel admin",
                    "%prefix% &e%arg1%&7 dans le fichier&b %arg2%"
            		}));
            
            saveConfigFile();

        }
    }
}