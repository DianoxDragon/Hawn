package fr.dianox.hawn.utility.config.configs.messages.fr_fr;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        /*
         * Gui
         */
        // import
        Config.set("Gui.Import.Importing-A-World", java.util.Arrays.asList("[send-title]: &cImportation commencée //n &7Veuillez patienter..."));
        Config.set("Gui.Import.Importing-With-A-Command", java.util.Arrays.asList("%prefix% &7Utilisez plutôt une commande : /hw import nomdumonde"));

        Config.set("Gui.Import.World-Loaded", java.util.Arrays.asList("%prefix% &7Le monde %arg1% a été chargé"));

        Config.set("Gui.Unload", java.util.Arrays.asList("%prefix% &7Le monde %arg1% est maintenant déchargé"));

        // tp
        Config.set("Gui.Tp.Error-Tp", java.util.Arrays.asList("%prefix% &cLa téléportation a échoué",
		            "%prefix% &cVous êtes déjà dans ce monde"));

        Config.set("Gui.Tp.Success", java.util.Arrays.asList("%prefix% &7Vous avez été téléporté dans le monde &e%arg1%"));

        // create
        Config.set("Gui.Create.Choose-A-Name", java.util.Arrays.asList("[send-title]: &eChoisissez un nom de monde //n &7Tapez dans le chat"));
        Config.set("Gui.Create.Choose-A-Generator", java.util.Arrays.asList("[send-title]: &eChoisissez un générateur //n &7Tapez dans le chat"));

        Config.set("Gui.Create.Creating-The-World", java.util.Arrays.asList("[send-title]: &cMonde en création&7..."));

        Config.set("Gui.Create.World-Created", java.util.Arrays.asList("%prefix% &7Le monde &e%arg1% &7a été créé"));

        Config.set("Gui.Create.World-Cancelled", java.util.Arrays.asList("%prefix% &7Création du monde annulée"));

        // Delete
        Config.set("Gui.Delete.World-Deleted", java.util.Arrays.asList("%prefix% &cLe monde &e%arg1% &ca été supprimé"));
        Config.set("Gui.Delete.Confirm-Command", java.util.Arrays.asList("%prefix% &cCela supprime le monde &e%arg1% &cet son dossier, sans retour possible. Tapez &e/hw delete %arg1% confirm &cpour le faire"));
        Config.set("Error.Protected-World", java.util.Arrays.asList("%prefix% &cLe monde &e%arg1% &cne peut pas être supprimé : le serveur en a besoin"));
        Config.set("Error.Unload-Failed", java.util.Arrays.asList("%prefix% &cLe monde &e%arg1% &cn'a pas pu être déchargé : rien n'a été supprimé"));
        Config.set("Gui.Default-World.Changed", java.util.Arrays.asList("%prefix% &e%arg1% &7est maintenant le monde par défaut : il remplace &e%arg2% &7dans &e%arg3% &7option(s) de &e%arg4% &7fichier(s)"));
        Config.set("Gui.Default-World.Already", java.util.Arrays.asList("%prefix% &e%arg1% &7est déjà le monde par défaut"));
        Config.set("Gui.Default-World.Shift-Click", java.util.Arrays.asList("%prefix% &eShift + clic &7pour faire de &e%arg1% &7le monde par défaut"));
        Config.set("Gui.Default-World.Conflict", java.util.Arrays.asList("%prefix% &6%arg1% : &e%arg2% &6avait déjà ses propres options, celles de &e%arg3% &6sont gardées à côté"));
        Config.set("Gui.Default-World.Spawn-Elsewhere", java.util.Arrays.asList("%prefix% &6Le spawn par défaut &e%arg1% &6est toujours dans &e%arg2% &6: créez-en un dans le nouveau monde avec &e/setspawn"));
        Config.set("Gui.Default-World.Yaml-Error", java.util.Arrays.asList("%prefix% &c%arg1% a une erreur YAML : il n'a pas été modifié"));
        Config.set("Command.Default-World.Current", java.util.Arrays.asList("%prefix% &7Le monde par défaut est &e%arg1% &7(&e/hw default <monde> &7pour le changer)"));
        Config.set("Error.Default-World", java.util.Arrays.asList("%prefix% &cLe monde &e%arg1% &cest le monde par défaut de Hawn : choisissez d'abord un autre monde par défaut"));
        Config.set("Gui.Other.Default-World.Title", "&6Monde par défaut");
        Config.set("Gui.Other.Default-World.Current", java.util.Arrays.asList("&7C'est le monde par défaut de Hawn :", "&7celui écrit dans les listes de mondes", "&7des fichiers de configuration"));
        Config.set("Gui.Other.Default-World.Change", java.util.Arrays.asList("&7Actuel : &e%arg1%", "&eShift + clic&7 pour en faire", "&7le monde par défaut : il remplace &e%arg1%", "&7dans les listes de mondes des fichiers"));
        Config.set("Gui.Other.Main.Default-World", "&6Monde par défaut");

        Config.set("Gui.Delete.Error-Mystery", java.util.Arrays.asList("%prefix% &cAucun monde à supprimer : recommencez depuis le menu"));

        // Modify World
        Config.set("Gui.Modify-World.Time-Changed", java.util.Arrays.asList("%prefix% &7L'heure du monde &e%arg1%&7 a été changée"));

        Config.set("Gui.Modify-World.Weather.Sun", java.util.Arrays.asList("%prefix% &7La météo du monde &e%arg1%&7 a été changée : soleil"));

        Config.set("Gui.Modify-World.Weather.Rain", java.util.Arrays.asList("%prefix% &7La météo du monde &e%arg1%&7 a été changée : pluie"));

        Config.set("Gui.Modify-World.Weather.Storm", java.util.Arrays.asList("%prefix% &7La météo du monde &e%arg1%&7 a été changée : orage"));

        // M. W. Dif.
        Config.set("Gui.Modify-World.Difficulty.Peaceful", java.util.Arrays.asList("%prefix% &7La difficulté du monde &e%arg1%&7 a été changée : paisible"));

        Config.set("Gui.Modify-World.Difficulty.Easy", java.util.Arrays.asList("%prefix% &7La difficulté du monde &e%arg1%&7 a été changée : facile"));

        Config.set("Gui.Modify-World.Difficulty.Normal", java.util.Arrays.asList("%prefix% &7La difficulté du monde &e%arg1%&7 a été changée : normale"));

        Config.set("Gui.Modify-World.Difficulty.Hard", java.util.Arrays.asList("%prefix% &7La difficulté du monde &e%arg1%&7 a été changée : difficile"));

        // Other
	        Config.set("Gui.Other.Generator", "&7Générateur");
	        Config.set("Gui.Other.Generator-Page.Void-Generator", "&7Générateur de vide");
	        Config.set("Gui.Other.Generator-Page.Custom-Generator", "&7Entrez votre propre générateur");
        // Depuis 1.4
        Config.set("Gui.Create.Failed", java.util.Arrays.asList("%prefix% &cLe monde &e%arg1% &cn'a pas pu être créé, voir la console"));
        Config.set("Gui.Modify-World.Nothing-Selected", java.util.Arrays.asList("%prefix% &7Cliquez d'abord sur un choix, puis sur le panneau"));
        Config.set("Gui.Other.Generator-Default", "&7Par défaut");
        Config.set("Gui.Other.Generator-Help", java.util.Arrays.asList("&8Clic gauche : par défaut ou vide", "&8Clic droit : taper un générateur dans le chat"));
        Config.set("Gui.Other.Info.Title", "&eInformations");
        Config.set("Gui.Other.Info.Players", "&7Joueurs :");
        Config.set("Gui.Other.Info.Environment", "&7Environnement :");
        Config.set("Gui.Other.Info.Generator", "&7Générateur :");
        Config.set("Gui.Other.Info.Chunks", "&7Chunks chargés :");
        Config.set("Gui.Other.Info.Entities", "&7Entités :");
        Config.set("Gui.Other.Info.Spawn", "&7Spawn :");
        Config.set("Gui.Other.Info.Time", "&7Heure :");
        Config.set("Gui.Other.Info.PvP", "&7PvP :");
        Config.set("Gui.Other.Info.Seed", "&7Graine :");
        Config.set("Gui.Other.WorldType.World-Type", "&7Type de monde :");
        Config.set("Gui.Other.WorldType.Nether", "&cNETHER");
        Config.set("Gui.Other.WorldType.The_End", "&5THE_END");
        Config.set("Gui.Other.WorldType.Normal", "&aNORMAL");

        Config.set("Gui.Other.WorldFace.World-Face", "&7Relief du monde :");
        Config.set("Gui.Other.WorldFace.Normal", "&aNORMAL");
        Config.set("Gui.Other.WorldFace.Large-Biomes", "&bLARGE_BIOMES");
        Config.set("Gui.Other.WorldFace.Amplified", "&cAMPLIFIED");
        Config.set("Gui.Other.WorldFace.Flat", "&eFLAT");

        Config.set("Gui.Other.ChangeWorld.Weather.Sun", "&7Soleil");
        Config.set("Gui.Other.ChangeWorld.Weather.Rain", "&7Pluie");
        Config.set("Gui.Other.ChangeWorld.Weather.Storm", "&7Tempête");
        Config.set("Gui.Other.ChangeWorld.Weather.Actual-Weather", "Météo actuelle :");

        Config.set("Gui.Other.Difficulty.Peaceful", "PAISIBLE");
        Config.set("Gui.Other.Difficulty.Easy", "FACILE");
        Config.set("Gui.Other.Difficulty.Normal", "NORMAL");
        Config.set("Gui.Other.Difficulty.Hard", "DIFFICILE");

        Config.set("Gui.Other.Name", "Nom :");
        Config.set("Gui.Other.Players", "Joueurs :");
        Config.set("Gui.Other.Size", "Taille :");
        Config.set("Gui.Other.Difficulty-Main", "Difficulté :");
        Config.set("Gui.Other.Difficulty-Two", "Difficulté");
        Config.set("Gui.Other.Environment", "Environnement :");
        Config.set("Gui.Other.World-Time", "Heure du monde");
        Config.set("Gui.Other.World-Weather", "Météo du monde");
        Config.set("Gui.Other.Done", "&f&lFait");
        Config.set("Gui.Other.Main.Line-One", "&eClic gauche&7 pour rejoindre le monde");
        Config.set("Gui.Other.Main.Line-One-Second", "&eClic gauche&7 pour charger le monde");
        Config.set("Gui.Other.Main.Line-Two", "&eClic droit&7 pour changer les paramètres du monde");
        Config.set("Gui.Other.Main.Line-Three", "&eShift &7+ &eclic droit&7 pour supprimer le monde");
        Config.set("Gui.Other.Main.Line-Four", "&7Le monde n'est actuellement pas chargé");
        Config.set("Gui.Other.Page.Back", "&bPrécédent");
        Config.set("Gui.Other.Page.Next", "&bSuivant");
        Config.set("Gui.Other.Create-a-new-world", "&f&lCréer un nouveau monde");
        Config.set("Gui.Other.Back.PanelAdmin", "&c&lRetour au panel admin");
        Config.set("Gui.Other.Back.Menu", "&c&lRetour au menu");
        Config.set("Gui.Other.Back.Main-Menu", "&c&lRetour au menu principal");
        Config.set("Gui.Other.Delete.Confirm", "&aJe sais ce que je fais");
        Config.set("Gui.Other.Delete.No", "&cFinalement... non");

        /*
         * Command
         */
        Config.set("Command.Unload.Error", java.util.Arrays.asList("%prefix% &cErreur, ce monde ne peut pas être déchargé"));
        Config.set("Command.Other.World", "Monde :");
        Config.set("Command.Other.Type", "Type :");

        Config.set("Error.NotGoodName", java.util.Arrays.asList("%prefix% &cCe nom de monde n'est pas autorisé",
		            "%prefix% &cCaractères autorisés : a-z | A-Z | 0-9 | _"));

        Config.set("Error.WorldNameNotTyped", java.util.Arrays.asList("%prefix% &cVous n'avez pas donné de nom de monde"));

        Config.set("Error.WorldCreation", java.util.Arrays.asList("%prefix% &cIl manque quelque chose dans la commande..."));

        Config.set("Error.World-Already-Exist", java.util.Arrays.asList("%prefix% &cCe monde existe déjà"));

        Config.set("Error.World-Not-Exist", java.util.Arrays.asList("%prefix% &cCe monde n'existe pas"));

        YamlConfiguration defaults = Config;
        Config = ConfigDefaults.apply(file, loaded, Config);
        // The French corrected in 1.4, in the files that still have the old texts
        ConfigDefaults.replaceOldDefaults(file, Config, defaults, FrenchFixes.WORLDMANAGERPANELCONFIG);
    }
}