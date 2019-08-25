package fr.Dianox.Hawn.Utility.Config.Messages.fr_FR;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AdminOtherAMConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public AdminOtherAMConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Administration/Others.yml");
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
                        
            /* --------------- *
			 * RELOAD COMMANDS *
			 * --------------- */
            Config.set("Command.Reload", java.util.Arrays.asList(new String[] {
            		"&aConfiguration rechargée"
            		}));
            
            Config.set("Command.Build-Bypass.On", java.util.Arrays.asList(new String[] {
            		"&bVous pouvez maintenant contourner toutes les restrictions de construction"
            		}));
            Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList(new String[] {
            		"&cVous ne pouvez plus contourner toutes les restrictions de construction"
            		}));
            
            Config.set("Command.Hawn-Main-help.1", java.util.Arrays.asList(new String[] {"§8>> §7/hawn setspawn [name] - §ePlace le spawn", 
            "§8>> §7/hawn reload §eou §7rl - §eRecharge certains fichiers de configuration",
            "§8>> §7/hawn version §eou §7v  - §eVoir la version du plugin",
            "§8>> §7/hawn tps - §eVoir le TPS du serveur",
            "§8>> §7/hawn info [all/memouy/tps/disk/cpu/server/version] - §eVoir les infos du serveur",
            "§8>> §7/hawn debug emoji - §eDéboguer le fichier de configuration des objets du menu d'emoji",
            "§8>> §7/hawn build - §ePour contourner temporairement la protection",
            "",
            "§8>> §7/ap §eou §7pa - §eAccès au panneau d'administration"}));

        Config.set("Command.Hawn-Main-help.2", java.util.Arrays.asList(new String[] {"", 
            "§8>> §7/spawn [SpawnName] - §eAller au spawn",
            "§8>> §7/spawn tp <joueur> [SpawnName] - §eTp un joueur au spawn",
            "",
            "§8>> §7/sun ou /clearw - §eDégagez le temps",
            "§8>> §7/rain - §eFaire pleuvoir le monde",
            "§8>> §7/thunder - §eSi vous aimez le mauvais temps",
            "§8>> §7/day - §eMet le jour",
            "§8>> §7/night - §eMet la nuit",
            ""}));

            Config.set("Command.Hawn-Main-help.3", java.util.Arrays.asList(new String[] {"§8>> §7/fly [joueur] - §eDéfinit le mode de vol", 
            "§8>> §7/heal [joueur] - §eGuéris un joueur",
            "§8>> §7/clearinv [joueur] - §eEfface l'inventaire d'un joueur",
            "§8>> §7/ping [joueur] - §eAffiche le ping d'un joueur",
            "§8>> §7/v [joueur] - §eVanish le joueur",
            "§8>> §7/gamemode ou gm... ou gma etc. - §eDéfinit le mode de jeu du joueur",
            "",
            "§8>> §7/cc - §eVoir l'aide du clearchat",
            "§8>> §7/delaychat <nombre> - §eMettre un délai sur le chat"}));

            Config.set("Command.Hawn-Main-help.4", java.util.Arrays.asList(new String[] {"§8>> §7/gmute - §eDésactive le chat", 
            "",
            "§8>> §7/broadcast <message>§7 - §eÉmettre un message",
            "§8>> §7/btcast §eou §7/ta <message>§7 - §eDiffuser un message de titre",
            "",
            "§8>> §7/help <.../...>§7 - §eAfficher l'aide personnalisée, si elle est activée",
            "",
            "§8>> §7/emoji§7 - §eVoir le menu des emojis",
            "",
            "§8>> §7/scoreboard§7 - §eActive ou désactive le scoreboard"}));

            Config.set("Command.Hawn-Main-help.5", java.util.Arrays.asList(new String[] {"§8>> §7/scoreboard set <nom de fichier du scoreboard>§7 - §ePour modifier le scoreboard actuel", 
            "§8>> §7/scoreboard keep§7 - §eGarder le scoreboard entre les serveurs/mondes",
            "",
            "§8>> §7/option§7 - §ePour les options du joueur principal"}));
            
            /*
             * Vanish
             */
            Config.set("Vanish.Vanish-On", java.util.Arrays.asList(new String[] {
                    "&7[ %player% est maintenant vanish ]"
            		}));
            Config.set("Vanish.Vanish-Off", java.util.Arrays.asList(new String[] {
                    "&7[ %player% n'est plus vanish ]"
            		}));
            Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList(new String[] {
                    "&7[ %target% a été vanish par %player% ]"
            		}));
            Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList(new String[] {
                    "&7[ %target% n'est plus vanish par %player% ]"
            		}));
            
            Config.set("Maintenance.On", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Tu as &aactivé&7 la maintenance"
            		}));
            Config.set("Maintenance.Off", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Tu as &cactivé&7 la maintenance"
            		}));
            Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(new String[] {
                    " &4* &cLa maintenance est &eactif&4 *"
            		}));
            Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(new String[] {
            		" &4* &cLa maintenance est &einactif&4 *"
            		}));
            
            saveConfigFile();

        }
    }
}