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
                "&aConfiguration rechargée"
            }));

            Config.set("Command.Build-Bypass.On", java.util.Arrays.asList(new String[] {
                "&bVous pouvez maintenant contourner toutes les restrictions de construction"
            }));
            Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList(new String[] {
                "&cVous ne pouvez plus contourner toutes les restrictions de construction"
            }));

            Config.set("Command.Hawn-Main-help.1", java.util.Arrays.asList(new String[] {
                "§8>> §7/hawn setspawn [name] - §ePlace le spawn",
                "§8>> §7/hawn delspawn <name> - §eSupprime le spawn",
                "§8>> §7/hawn reload §eou §7rl - §eRecharge certains fichiers de configuration",
                "§8>> §7/hawn version §eou §7v  - §eVoir la version du plugin",
                "§8>> §7/hawn tps - §eVoir le TPS du serveur",
                "§8>> §7/hawn info [all/memouy/tps/disk/cpu/server/version] - §eVoir les infos du serveur",
                "§8>> §7/hawn debug emoji - §eDéboguer le fichier de configuration des objets du menu d'emoji",
                "§8>> §7/hawn build - §ePour contourner temporairement la protection",
                "§8>> §7/hawn hooks - §ePour vérifier les sous-dépendances du plugin, s'ils sont activés ou non",
                "§8>> §7/hawn urgent - §ePour utiliser le mode d'urgence",
                "§8>> §7/hawn [maintenance/m] - §ePour utiliser le mode de maintenance"
            }));

            Config.set("Command.Hawn-Main-help.2", java.util.Arrays.asList(new String[] {
                "§8>> §7/hawn dornor - §eMontre tous les donneurs du plugin",
                "§8>> §7/hawn about - §eMontre certaines informations",
                "",
                "§8>> §7/ap §eou §7pa - §eAccès au panneau d'administration",
                "",
                "§8>> §7/spawn [SpawnName] - §eAller au spawn",
                "§8>> §7/spawn tp <joueur> [SpawnName] - §eTp un joueur au spawn",
                "§8>> §7/spawnlist - §eVoir la liste des spawns",
                "",
                "§8>> §7/warp <WarpName> [joueur] - §eAller au warp",
                "§8>> §7/warplist - §eVoir la liste des warps",
                "§8>> §7/setwarp <warpname> - §eCréer un warp"
            }));

            Config.set("Command.Hawn-Main-help.3", java.util.Arrays.asList(new String[] {
                "§8>> §7/delwarp <warpname> - §eSupprimer un warp",
                "",
                "§8>> §7/sun ou /clearw - §eDégagez le temps",
                "§8>> §7/rain - §eFaire pleuvoir le monde",
                "§8>> §7/thunder - §eSi vous aimez le mauvais temps",
                "§8>> §7/day - §eMet le jour",
                "§8>> §7/night - §eMet la nuit",
                "",
                "§8>> §7/fly [joueur] - §eDéfinit le mode de vol",
                "§8>> §7/heal [joueur] - §eGuéris un joueur",
                "§8>> §7/feed [joueur] - §eNourrir le joueur"
            }));

            Config.set("Command.Hawn-Main-help.4", java.util.Arrays.asList(new String[] {
                "§8>> §7/clearinv [joueur] - §eEfface l'inventaire d'un joueur",
                "§8>> §7/ping [joueur] - §eAffiche le ping d'un joueur",
                "§8>> §7/v [joueur] - §eVanish le joueur",
                "§8>> §7/gamemode ou gm... ou gma etc. - §eDéfinit le mode de jeu du joueur",
                "",
                "§8>> §7/cc - §eVoir l'aide du clearchat",
                "§8>> §7/delaychat <nombre> - §eMettre un délai sur le chat",
                "§8>> §7/gmute - §eDésactive le chat",
                "",
                "§8>> §7/broadcast <message>§7 - §eÃ‰mettre un message",
                "§8>> §7/btcast §eou §7/ta <message>§7 - §eDiffuser un message de titre"
            }));

            Config.set("Command.Hawn-Main-help.5", java.util.Arrays.asList(new String[] {
                "§8>> §7/bacast §eou §7/aba <message>§7 - §eDiffuser un message d'action bar",
                "",
                "§8>> §7/help <.../...>§7 - §eAfficher l'aide personnalisée, si elle est activée",
                "§8>> §7/gotop§7 - §eAller au bloc le plus haut de votre position",
                "",
                "§8>> §7/emoji§7 - §eVoir le menu des emojis",
                "",
                "§8>> §7/scoreboard§7 - §eActive ou désactive le scoreboard",
                "§8>> §7/scoreboard set <nom de fichier du scoreboard>§7 - §ePour modifier le scoreboard actuel",
                "§8>> §7/scoreboard keep§7 - §eGarder le scoreboard entre les serveurs/mondes",
                "§8>> §7/scoreboard list§7 - §eVoir tous les scoreboard enregistrés"
            }));

            Config.set("Command.Hawn-Main-help.6", java.util.Arrays.asList(new String[] {
            	"",
                "§8>> §7/option§7 - §ePour les options du joueur principal"
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

            Config.set("Urgent-mode.On", java.util.Arrays.asList(new String[] {
                "%prefix% &7Tu as &aactivé&7 le mode d'urgence"
            }));
            Config.set("Urgent-mode.Off", java.util.Arrays.asList(new String[] {
                "%prefix% &7Tu as &cdésactivé&7 le mode d'urgence"
            }));
            Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(new String[] {
                " &4* &cLe mode d'urgence est &eactif&4 *"
            }));
            Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(new String[] {
                " &4* &cLe mode d'urgence est &einactif&4 *"
            }));
            Config.set("Urgent-mode.Zip", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &c&cUne sauvegarde de Hawn a été faite"
            }));
            Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cErreur, vous devez Ãªtre sur la console pour désactiver le mode urgent"
            }));
            Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cDésolé mais vous ne pouvez pas utiliser la commande"
            }));
            Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cUne modification a été détectée par %player% sur le panneau d'administration",
                "%arg1% dans le fichier %arg2%"
            }));
            Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cTous les plugins ont été désactivés"
            }));
            Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &7Tous les plugins ont été &aactivés",
                "&eS'il vous plaÃ®t, un redémarrage est nécessaire pour éviter tout problème"
            }));

            Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList(new String[] {
                "%prefix% &e%player%&7 a essayé d'utiliser la commande : &b%arg1%"
            }));
            
            Config.set("Command.SlotView.On", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Vous pouvez maintenant voir quel slot vous avez cliqué sur votre inventaire etc."
                }));
            Config.set("Command.SlotView.Off", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Le SlotView est &cdésactivé"
                }));
            
            Config.set("Command.IP", java.util.Arrays.asList(new String[] {
                    "%prefix% &7L'ip du joueur est: &e%getplayerip%"
                }));
            
            Config.set("Command.Kickall", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Tous les joueurs ont été kick du serveur"
                }));
            
            Config.set("Command.ClearGroundItems", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Tous les objets ont été effacés"
                }));
            
            Config.set("Command.ClearMobs", java.util.Arrays.asList(new String[] {
                    "%prefix% &7Toutes les monstres ont été éliminés"
                }));
            
            Config.set("Command.CheckAccount", java.util.Arrays.asList(new String[] {
            		"",
                    "  &8→ &r&lInfos joueur pour&8:&r &b%target%",
                    "&7&lDate de connexion&r&8:&e %hawn_player_join_date%",
                    "&7&lDate de première connexion&r&8:&e %hawn_player_first_join_date%",
                    "&7&lIp&r&8:&e %player_ip%",
                    "",
                    "  &8→ &r&lOptions&8:&r &8(&aVert &8=&7 vrai / &cRouge&8 = &7faux&8)",
                    "&7&lVisibilité des joueurs: %pv_point%",
                    "&7&lVitesse: %ps_point% &7(&e%ps_number%&7)",
                    "&7&lVol: %pof_point%",
                    "&7&lDouble saut: %dj_point%",
                    "&7&lAuto broadcast: %ab_point%",
                    "&7&lVanish: %v_point%",
                    "&7&lConserver le scoreboard: %ksb_point% &8(&e%scorename%&8)",
                    "&7&lJump Boost: %jb_point%",
                    "",
                    "&7&lGamemode: &e%gm_number%",
                    ""
                }));

            saveConfigFile();

        }
    }
}