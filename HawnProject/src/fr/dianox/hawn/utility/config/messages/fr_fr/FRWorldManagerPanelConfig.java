package fr.dianox.hawn.utility.config.messages.fr_fr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FRWorldManagerPanelConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public FRWorldManagerPanelConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/WorldManager.yml");
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
             * Gui
             */
            // import
            Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList(new String[] {
            		"[send-title]: &cImportation commencée //n &7Attendez, s'il vous plaît..."
            		}));
            
            Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList(new String[] {
            		"%prefix% &7Le monde %arg1% a été chargé"
            		}));
            
            // tp
            Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList(new String[] {
            		"%prefix% &cLa téléportation a échoué",
            		"%prefix% &cVous êtes déjà dans ce monde"
            		}));
            
            Config.set("Gui.Tp.Success", java.util.Arrays.asList(new String[] {
            		"%prefix% &7Vous avez été téléporté dans le monde &e%arg1%"
            		}));
            
            // create
            Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList(new String[] {
            		"[send-title]: &eChoisissez un nom de monde //n &7Tapez dans le chat"
            		}));
            
            Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList(new String[] {
            		"[send-title]: &cMonde en création&7..."
            		}));
            
            Config.set("Gui.Create.World-Created", java.util.Arrays.asList(new String[] {
            		"%prefix% &7Le monde &e%arg1% &7a été crée"
            		}));
            
            Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList(new String[] {
            		"%prefix% &7Ne créer plus de monde désormais..."
            		}));
            
            // Delete
            Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList(new String[] {
            		"%prefix% &cLe monde &e%arg1% &ca été supprimé"
            		}));
            
            Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList(new String[] {
            		"%prefix% &cQuelque chose d'étrange est arrivé... rien ne se passe, c'est une erreur, ignorez-la..."
            		}));
            
            // Modify World
            Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList(new String[] {
            		"%prefix% &7L'heure du monde &e%arg1%&7 a été changé"
            		}));
            
            Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList(new String[] {
            		"%prefix% &7La météo pour le monde &e%arg1%&7 a été changé pour du soleil"
            		}));
            
            Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList(new String[] {
            		"%prefix% &7La météo pour le monde &e%arg1%&7 a été changé pour de la pluie"
            		}));
            
            Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList(new String[] {
            		"%prefix% &7La météo pour le monde &e%arg1%&7 a été changé pour une tempête"
            		}));
            
            // M. W. Dif.
            Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList(new String[] {
            		"%prefix% &7La difficulté pour le monde &e%arg1%&7 a été changé pour un mode paisible"
            		}));
            
            Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList(new String[] {
            		"%prefix% &7La difficulté pour le monde &e%arg1%&7 a été changé pour un mode facile"
            		}));
            
            Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList(new String[] {
            		"%prefix% &7La difficulté pour le monde &e%arg1%&7 a été changé pour un mode normal"
            		}));
            
            Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList(new String[] {
            		"%prefix% &7La difficulté pour le monde &e%arg1%&7 a été changé pour un mode difficile"
            		}));
            
            // Other
            Config.set("Gui.Other.WorldType.World-Type", "&7Type de monde:");
            Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
            Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
            Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");
            
            Config.set("Gui.Other.WorldFace.World-Face", "&7Visage du monde:");
            Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
            Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
            Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
            Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");
            
            Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Soleil");
            Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Pluie");
            Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Tempête");
            Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Météo actuelle:");
            
            Config.set("Gui.Other.Difficulty.Peaceful", "PAISIBLE");
            Config.set("Gui.Other.Difficulty.Easy", "FACILE");
            Config.set("Gui.Other.Difficulty.Normal", "NORMAL");
            Config.set("Gui.Other.Difficulty.Hard", "DIFFICILE");
            
            Config.set("Gui.Other.Name", "Nom:");
            Config.set("Gui.Other.Players", "Joueurs:");
            Config.set("Gui.Other.Size", "Taille:");
            Config.set("Gui.Other.Difficulty-Main", "Difficulté:");
            Config.set("Gui.Other.Difficulty-Two", "Difficulté");
            Config.set("Gui.Other.Environment", "Environnement:");
            Config.set("Gui.Other.World-Time", "L'heure du monde");
            Config.set("Gui.Other.World-Weather", "Météo du monde");
            Config.set("Gui.Other.Done", "&f&lFait");
            Config.set("Gui.Other.Main.Line-One", "&eClique gauche&7 pour rejoindre le monde");
            Config.set("Gui.Other.Main.Line-One-Second", "&eClique gauche&7 pour charger le monde");
            Config.set("Gui.Other.Main.Line-Two", "&eClique droit&7 pour changer les paramètres du monde");
            Config.set("Gui.Other.Main.Line-Three", "&eShift &7et &eclique droit&7 pour supprimer le monde");
            Config.set("Gui.Other.Main.Line-Four", "&7Le monde n'est actuellement pas chargé");
            Config.set("Gui.Other.Page.Back", "&bPrécédent");
            Config.set("Gui.Other.Page.Next", "&bSuivant");
            Config.set("Gui.Other.Create-a-new-world", "&f&lCréer un nouveau monde");
            Config.set("Gui.Other.Back.PanelAdmin", "&c&lRetour à l'admin panel");
            Config.set("Gui.Other.Back.Menu", "&c&lRetour au menu");
            Config.set("Gui.Other.Back.Main-Menu", "&c&lRetour au menu principal");
            Config.set("Gui.Other.Delete.Confirm", "&aJe sais ce que je fais");
            Config.set("Gui.Other.Delete.No", "&cFinalement... non");
            
            /*
             * Command
             */
            Config.set("Command.Unload.Error", java.util.Arrays.asList(new String[] {
            		"%prefix% &cErreur, ce monde ne peux pas être déchargé"
            		}));
            Config.set("Command.Other.World", "Monde:");
            Config.set("Command.Other.Type", "Type:");
            
            Config.set("Error.NotGoodName", java.util.Arrays.asList(new String[] {
            		"%prefix% &cErreur Hawn, le nom de monde avec un tel format de nom n'est pas autorisé",
                    "%prefix% &cLes seuls formats de noms autorisés sont : a-z | A-Z | 0-9 | _"
            		}));
            
            Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList(new String[] {
            		"%prefix% &cTu n'as pas tapé un nom de monde"
            		}));
            
            Config.set("Error.WorldCreation", java.util.Arrays.asList(new String[] {
            		"%prefix% &cVous avez manqué quelque chose dans l'écriture de la commande..."
            		}));
            
            Config.set("Error.World-Already-Exist", java.util.Arrays.asList(new String[] {
            		"%prefix% &cCe monde existe"
            		}));
            
            Config.set("Error.World-Not-Exist", java.util.Arrays.asList(new String[] {
            		"%prefix% &cCe monde n'existe pas"
            		}));
            
            saveConfigFile();

        }
    }
}