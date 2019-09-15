package fr.dianox.hawn.utility.config.messages.fr_fr;

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
                "&aConfiguration rechargÃ©e"
            }));

            Config.set("Command.Build-Bypass.On", java.util.Arrays.asList(new String[] {
                "&bVous pouvez maintenant contourner toutes les restrictions de construction"
            }));
            Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList(new String[] {
                "&cVous ne pouvez plus contourner toutes les restrictions de construction"
            }));

            Config.set("Command.Hawn-Main-help.1", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/hawn setspawn [name] - Â§ePlace le spawn",
                "Â§8>> Â§7/hawn delspawn <name> - Â§eSupprime le spawn",
                "Â§8>> Â§7/hawn reload Â§eou Â§7rl - Â§eRecharge certains fichiers de configuration",
                "Â§8>> Â§7/hawn version Â§eou Â§7v  - Â§eVoir la version du plugin",
                "Â§8>> Â§7/hawn tps - Â§eVoir le TPS du serveur",
                "Â§8>> Â§7/hawn info [all/memouy/tps/disk/cpu/server/version] - Â§eVoir les infos du serveur",
                "Â§8>> Â§7/hawn debug emoji - Â§eDÃ©boguer le fichier de configuration des objets du menu d'emoji",
                "Â§8>> Â§7/hawn build - Â§ePour contourner temporairement la protection",
                "Â§8>> Â§7/hawn hooks - Â§ePour vÃ©rifier les sous-dÃ©pendances du plugin, s'ils sont activÃ©s ou non",
                "Â§8>> Â§7/hawn urgent - Â§ePour utiliser le mode d'urgence",
                "Â§8>> Â§7/hawn [maintenance/m] - Â§ePour utiliser le mode de maintenance"
            }));

            Config.set("Command.Hawn-Main-help.2", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/hawn dornor - Â§eMontre tous les donneurs du plugin",
                "Â§8>> Â§7/hawn about - Â§eMontre certaines informations",
                "",
                "Â§8>> Â§7/ap Â§eou Â§7pa - Â§eAccÃ¨s au panneau d'administration",
                "",
                "Â§8>> Â§7/spawn [SpawnName] - Â§eAller au spawn",
                "Â§8>> Â§7/spawn tp <joueur> [SpawnName] - Â§eTp un joueur au spawn",
                "Â§8>> Â§7/spawnlist - Â§eVoir la liste des spawns",
                "",
                "Â§8>> Â§7/warp <WarpName> [joueur] - Â§eAller au warp",
                "Â§8>> Â§7/warplist - Â§eVoir la liste des warps",
                "Â§8>> Â§7/setwarp <warpname> - Â§eCrÃ©er un warp"
            }));

            Config.set("Command.Hawn-Main-help.3", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/delwarp <warpname> - Â§eSupprimer un warp",
                "",
                "Â§8>> Â§7/sun ou /clearw - Â§eDÃ©gagez le temps",
                "Â§8>> Â§7/rain - Â§eFaire pleuvoir le monde",
                "Â§8>> Â§7/thunder - Â§eSi vous aimez le mauvais temps",
                "Â§8>> Â§7/day - Â§eMet le jour",
                "Â§8>> Â§7/night - Â§eMet la nuit",
                "",
                "Â§8>> Â§7/fly [joueur] - Â§eDÃ©finit le mode de vol",
                "Â§8>> Â§7/heal [joueur] - Â§eGuÃ©ris un joueur",
                "Â§8>> Â§7/feed [joueur] - Â§eNourrir le joueur"
            }));

            Config.set("Command.Hawn-Main-help.4", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/clearinv [joueur] - Â§eEfface l'inventaire d'un joueur",
                "Â§8>> Â§7/ping [joueur] - Â§eAffiche le ping d'un joueur",
                "Â§8>> Â§7/v [joueur] - Â§eVanish le joueur",
                "Â§8>> Â§7/gamemode ou gm... ou gma etc. - Â§eDÃ©finit le mode de jeu du joueur",
                "",
                "Â§8>> Â§7/cc - Â§eVoir l'aide du clearchat",
                "Â§8>> Â§7/delaychat <nombre> - Â§eMettre un dÃ©lai sur le chat",
                "Â§8>> Â§7/gmute - Â§eDÃ©sactive le chat",
                "",
                "Â§8>> Â§7/broadcast <message>Â§7 - Â§eÃ‰mettre un message",
                "Â§8>> Â§7/btcast Â§eou Â§7/ta <message>Â§7 - Â§eDiffuser un message de titre"
            }));

            Config.set("Command.Hawn-Main-help.5", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/bacast Â§eou Â§7/aba <message>Â§7 - Â§eDiffuser un message d'action bar",
                "",
                "Â§8>> Â§7/help <.../...>Â§7 - Â§eAfficher l'aide personnalisÃ©e, si elle est activÃ©e",
                "Â§8>> Â§7/gotopÂ§7 - Â§eAller au bloc le plus haut de votre position",
                "",
                "Â§8>> Â§7/emojiÂ§7 - Â§eVoir le menu des emojis",
                "",
                "Â§8>> Â§7/scoreboardÂ§7 - Â§eActive ou dÃ©sactive le scoreboard",
                "Â§8>> Â§7/scoreboard set <nom de fichier du scoreboard>Â§7 - Â§ePour modifier le scoreboard actuel",
                "Â§8>> Â§7/scoreboard keepÂ§7 - Â§eGarder le scoreboard entre les serveurs/mondes",
                "Â§8>> Â§7/scoreboard listÂ§7 - Â§eVoir tous les scoreboard enregistrÃ©s"
            }));

            Config.set("Command.Hawn-Main-help.6", java.util.Arrays.asList(new String[] {
            	"",
                "Â§8>> Â§7/optionÂ§7 - Â§ePour les options du joueur principal"
            }));

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
                "&7[ %target% a Ã©tÃ© vanish par %player% ]"
            }));
            Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList(new String[] {
                "&7[ %target% n'est plus vanish par %player% ]"
            }));

            Config.set("Maintenance.On", java.util.Arrays.asList(new String[] {
                "%prefix% &7Tu as &aactivÃ©&7 la maintenance"
            }));
            Config.set("Maintenance.Off", java.util.Arrays.asList(new String[] {
                "%prefix% &7Tu as &cactivÃ©&7 la maintenance"
            }));
            Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(new String[] {
                " &4* &cLa maintenance est &eactif&4 *"
            }));
            Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(new String[] {
                " &4* &cLa maintenance est &einactif&4 *"
            }));

            Config.set("Urgent-mode.On", java.util.Arrays.asList(new String[] {
                "%prefix% &7Tu as &aactivÃ©&7 le mode d'urgence"
            }));
            Config.set("Urgent-mode.Off", java.util.Arrays.asList(new String[] {
                "%prefix% &7Tu as &cdÃ©sactivÃ©&7 le mode d'urgence"
            }));
            Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(new String[] {
                " &4* &cLe mode d'urgence est &eactif&4 *"
            }));
            Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(new String[] {
                " &4* &cLe mode d'urgence est &einactif&4 *"
            }));
            Config.set("Urgent-mode.Zip", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &c&cUne sauvegarde de Hawn a Ã©tÃ© faite"
            }));
            Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cErreur, vous devez Ãªtre sur la console pour dÃ©sactiver le mode urgent"
            }));
            Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cDÃ©solÃ© mais vous ne pouvez pas utiliser la commande"
            }));
            Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cUne modification a Ã©tÃ© dÃ©tectÃ©e par %player% sur le panneau d'administration",
                "%arg1% dans le fichier %arg2%"
            }));
            Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cTous les plugins ont Ã©tÃ© dÃ©sactivÃ©s"
            }));
            Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &7Tous les plugins ont Ã©tÃ© &aactivÃ©s",
                "&eS'il vous plaÃ®t, un redÃ©marrage est nÃ©cessaire pour Ã©viter tout problÃ¨me"
            }));

            Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList(new String[] {
                "%prefix% &e%player%&7 a essayÃ© d'utiliser la commande : &b%arg1%"
            }));
            
            Config.set("Command.SlotView.On", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Vous pouvez maintenant voir quel slot vous avez cliquÃ© sur votre inventaire etc."
                }));
            Config.set("Command.SlotView.Off", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Le SlotView est &cdÃ©sactivÃ©"
                }));

            saveConfigFile();

        }
    }
}