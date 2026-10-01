package fr.dianox.hawn.utility.config.configs.messages.fr_fr;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FRAdminAMConfig {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public FRAdminAMConfig() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Messages/fr_FR/Admin.yml");
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
            Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList("&cVous n'êtes pas un joueur"));
            Config.set("Error.Command.Hawn", java.util.Arrays.asList("&cErreur, Essayez de faire /hawn"));
            Config.set("Error.Command.Delspawn", java.util.Arrays.asList("&c/hawn delspawn <spawn>"));
            Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList("&cLe nom existe déjà"));
            Config.set("Error.Argument-Missing", java.util.Arrays.asList("&cJe suis désolé, mais il doit manquer un ou deux arguments"));
            Config.set("Error.No-Spawn", java.util.Arrays.asList("&cLe spawn n'existe pas"));

	        /* ------------ *
	         * HELP COMMAND *
	         * ------------ */

	        Config.set("Command.Help.Hawn-SpawnManager", "Gérer le spawn");
	        Config.set("Command.Help.Hawn-Reload", "Recharger quelques fichiers de configuration");
	        Config.set("Command.Help.Hawn-Version", "Voir la version du plugin");
	        Config.set("Command.Help.Hawn-Tps", "Voir le TPS du serveur");
	        Config.set("Command.Help.Hawn-Info", "Voir les infos du serveur");
	        Config.set("Command.Help.Hawn-Build", "Pour contourner la protection à titre temporaire");
	        Config.set("Command.Help.Hawn-Hooks", "Pour vérifier les sous-dépendances du plugin, si elles sont activées");
	        Config.set("Command.Help.Hawn-Urgent", "Pour utiliser le mode urgent");
	        Config.set("Command.Help.Hawn-Maintenance", "Pour utiliser le mode de maintenance");
	        Config.set("Command.Help.Hawn-Donors", "Voir ceux qui ont fait des dons pour le plugin");
	        Config.set("Command.Help.Hawn-About", "Montrez juste quelques informations");
	        Config.set("Command.Help.Hawn-Parse", "Voir ce que rapporte un placeholder");
	        Config.set("Command.Help.Hawn-NightVision", "Voir dans l'obscurité");
	        Config.set("Command.Help.Hawn-NoClip", "Traverser les blocs tout en restant créatif");
	        Config.set("Command.Help.Hawn-SlotView", "Voir le numéro du créneau en cliquant dessus");
	        Config.set("Command.Help.Hawn-EditPlayer", "Modifier un joueur");
	        Config.set("Command.Help.Hawn-Setup", "Paramétrez votre configuration de Hawn pour la première fois");
	        Config.set("Command.Help.Broadcast", "Diffuser un message");
	        Config.set("Command.Help.Warning", "Diffuser un avertissement");
	        Config.set("Command.Help.Broadcast-Title", "Diffuser un message de titre");
	        Config.set("Command.Help.Broadcast-ActionBar", "Diffuser un message de la barre d'action");
	        Config.set("Command.Help.AdminPanel", "Accès au panel d'administration");
	        Config.set("Command.Help.CheckAccount", "Vérifier un joueur");
	        Config.set("Command.Help.ClearInv", "Réinitialiser l'inv. du joueur");
	        Config.set("Command.Help.InvSee", "Voir l'inventaire d'un joueur");
	        Config.set("Command.Help.Ip", "Voir l'IP d'un joueur");
	        Config.set("Command.Help.List", "Obtenez la liste des joueurs dans le hall d'entrée");
	        Config.set("Command.Help.Spawn", "Aller au spawn");
	        Config.set("Command.Help.Spawn-Tp", "Tp un joueur à un spawn");
	        Config.set("Command.Help.DelSpawn", "Enlever un spawn");
	        Config.set("Command.Help.SpawnList", "Voir la liste des spawn");
	        Config.set("Command.Help.SetSpawn", "Définir un spawn");
	        Config.set("Command.Help.SetWarp", "Mettre en place un warp");
	        Config.set("Command.Help.Warp", "Aller au warp");
	        Config.set("Command.Help.WarpList", "Voir la liste des warps");
	        Config.set("Command.Help.DelWarp", "Supprimer un warp");
	        Config.set("Command.Help.EditWarp", "Modifier l'emplacement du warp");
	        Config.set("Command.Help.Sun", "Dégager le temps");
	        Config.set("Command.Help.Rain", "Pour faire pleuvoir le monde");
	        Config.set("Command.Help.Thunder", "Si vous aimez le mauvais temps");
	        Config.set("Command.Help.Day", "Mettre le jour");
	        Config.set("Command.Help.Night", "Mettre la nuit");
	        Config.set("Command.Help.Fly", "Mettre le mode de vol");
	        Config.set("Command.Help.FlySpeed", "Modifier la vitesse de vol d'un joueur");
	        Config.set("Command.Help.Speed", "Modifier ou activer/désactiver la vitesse");
	        Config.set("Command.Help.Heal", "Guérir un joueur");
	        Config.set("Command.Help.Feed", "Nourrir un joueur");
	        Config.set("Command.Help.Ping", "Montrer le ping d'un joueur");
	        Config.set("Command.Help.Vanish", "Faire disparaître un joueur");
	        Config.set("Command.Help.Gamemode", "Régler le gamemode d'un joueur");
	        Config.set("Command.Help.Burn", "Brûler un joueur");
	        Config.set("Command.Help.Cleargrounditems", "Enlever les items au sol");
	        Config.set("Command.Help.Clearmobs", "Supprimer les mobs");
	        Config.set("Command.Help.EnderChest", "Ouvrir l'EnderChest");
	        Config.set("Command.Help.Exp", "Modifier l'exp du joueur");
	        Config.set("Command.Help.Getpos", "Obtenir la localisation d'un joueur");
	        Config.set("Command.Help.Hat", "Prendre un chapeau");
	        Config.set("Command.Help.Kickall", "Virer tout le monde");
	        Config.set("Command.Help.Repair", "Réparer un item");
	        Config.set("Command.Help.Skull", "Prenez la tête d'un joueur");
	        Config.set("Command.Help.Suicide", "Tuez-vous");
	        Config.set("Command.Help.Workbench", "Ouvrir l'établi du joueur");
	        Config.set("Command.Help.ClearChat", "Montrez l'aide du clearchat");
	        Config.set("Command.Help.DelayChat", "Retarder le chat");
	        Config.set("Command.Help.Gmute", "Mettez le chat en sourdine");
	        Config.set("Command.Help.Help", "Afficher l'aide personnalisée, si elle est activée");
	        Config.set("Command.Help.Gotop", "Allez au bloc le plus élevé de votre position");
	        Config.set("Command.Help.Emoji", "Voir le gui des emojis");
	        Config.set("Command.Help.Scoreboard", "Affichage ou non du du scoreboard");
	        Config.set("Command.Help.Scoreboard-Set", "Pour modifier le scoreboard actuel");
	        Config.set("Command.Help.Scoreboard-Keep", "Gardez le scoreboard entre les serveurs");
	        Config.set("Command.Help.Scoreboard-List", "Voir tous les scoreboards enregistrés");
	        Config.set("Command.Help.Option", "Pour les options du joueur principal");
	        Config.set("Command.Help.Hworld", "Gérer le système de monde");

            /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
            Config.set("Command.Server-Info.General", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformations sur le serveur (Tous)",
		            "        &3créé par Dianox",
		            "",
		            "  &8→ &6&lServeur",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersion de java&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lVérification des mises à jour de Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersion du serveur&r&8:&r %serverversion%",
		            "",
		            "  &8→ &6&lMémoire",
		            "&7&lMémoire max &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLibre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "  &8→ &6&lCPU (Processeur)",
		            "&7&lCharge moyenne&8:&r %averagecpuload%&8%",
		            "&7&lCharge réelle&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "  &8→ &6&lEspace disque",
		            "&7&lDisque total&r&8:&r %totalspace%&8MB",
		            "&a&lDisque libre&7&l/&c&lDisque total&7&l utilisable&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Memory", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformations sur le serveur (Mémoire)",
		            "",
		            "  &8→ &6&lMémoire",
		            "&7&lMémoire max &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lLibre&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.CPU", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformations sur le serveur (CPU)",
		            "",
		            "  &8→ &6&lCPU (Processeur)",
		            "&7&lCharge moyenne&8:&r %averagecpuload%&8%",
		            "&7&lCharge réelle&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Disk", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformations sur le serveur (Disque)",
		            "",
		            "  &8→ &6&lEspace disque",
		            "&7&lDisque total&r&8:&r %totalspace%&8MB",
		            "&a&lDisque libre&7&l/&c&lDisque total&7&l utilisable&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Server", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lInformations sur le serveur (Serveur)",
		            "",
		            "  &8→ &6&lServeur",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lVersion de java&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lVérification des mises à jour de Hawn&r&8:&r %checkupdatehawn%",
		            "&7&lVersion du serveur&r&8:&r %serverversion%",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Tps", java.util.Arrays.asList("  &8→ &6&lTps&8: &r%tps%"));
            
            Config.set("Command.Version", java.util.Arrays.asList("  &8→ &6&lVersion de Hawn (créé par Dianox)&8: &r%gethawnversion%"));
            
            Config.set("TPS.Check.15", java.util.Arrays.asList("&cVos TPS sont à moins de 15, faites quelque chose pour améliorer la stabilité de votre Lobby"));
            Config.set("TPS.Check.5", java.util.Arrays.asList("&cVos TPS sont à moins de 5, votre serveur peut s'éteindre, fait /stop pour éviter tout problème.", "&cETAT CRITIQUE DU SERVEUR"));
            
            
            /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
            Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList("&cVous n'avez pas mis de nom pour ce spawn, un nom automatique a été choisi.",
		            "§eLe spawn a été placé sous le nom de %spawnName%"));
            
            Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList("§eLe spawn a été placé sous le nom de %spawnName%"));
            
            Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList("&bLe spawn &e%spawn%&b a été supprimée"));
            
            /* --------------- *
             * RELOAD COMMANDS *
             * --------------- */
            Config.set("Command.Reload", java.util.Arrays.asList("&aConfiguration rechargée"));

            Config.set("Command.Build-Bypass.On", java.util.Arrays.asList("&bVous pouvez maintenant contourner toutes les restrictions de construction"));
            Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList("&cVous ne pouvez plus contourner toutes les restrictions de construction"));

            /*
             * Vanish
             */
            Config.set("Vanish.Vanish-On.Enable", true);
            Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList("&7[ %player% est maintenant vanish ]"));
            Config.set("Vanish.Vanish-Off.Enable", true);
            Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList("&7[ %player% n'est plus vanish ]"));
            
            Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList("&7[ %target% a été vanish par %player% ]"));
            Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList("&7[ %target% n'est plus vanish par %player% ]"));

            Config.set("Maintenance.On", java.util.Arrays.asList("%prefix% &7Tu as &aactivé&7 la maintenance"));
            Config.set("Maintenance.Off", java.util.Arrays.asList("%prefix% &7Tu as &cactivé&7 la maintenance"));
            Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(" &4* &cLa maintenance est &eactif&4 *"));
            Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(" &4* &cLa maintenance est &einactif&4 *"));

            Config.set("Urgent-mode.On", java.util.Arrays.asList("%prefix% &7Tu as &aactivé&7 le mode d'urgence"));
            Config.set("Urgent-mode.Off", java.util.Arrays.asList("%prefix% &7Tu as &cdésactivé&7 le mode d'urgence"));
            Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(" &4* &cLe mode d'urgence est &eactif&4 *"));
            Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(" &4* &cLe mode d'urgence est &einactif&4 *"));
            Config.set("Urgent-mode.Zip", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cUne sauvegarde de Hawn a été faite"));
            Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cErreur, vous devez Ãªtre sur la console pour désactiver le mode urgent"));
            Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cDésolé mais vous ne pouvez pas utiliser la commande"));
            Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cUne modification a été détectée par %player% sur le panneau d'administration",
		            "%arg1% dans le fichier %arg2%"));
            Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cTous les plugins ont été désactivés"));
            Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7Tous les plugins ont été &aactivés",
		            "&eS'il vous plaÃ®t, un redémarrage est nécessaire pour éviter tout problème"));

            Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList("%prefix% &e%player%&7 a essayé d'utiliser la commande : &b%arg1%"));
            
            Config.set("Command.SlotView.On", java.util.Arrays.asList("%prefix% &7Vous pouvez maintenant voir quel slot vous avez cliqué sur votre inventaire etc."));
            Config.set("Command.SlotView.Off", java.util.Arrays.asList("%prefix% &7Le SlotView est &cdésactivé"));
            
            Config.set("Command.IP", java.util.Arrays.asList("%prefix% &7L'ip du joueur est: &e%getplayerip%"));
            
            Config.set("Command.Kickall", java.util.Arrays.asList("%prefix% &7Tous les joueurs ont été kick du serveur"));
            
            Config.set("Command.ClearGroundItems", java.util.Arrays.asList("%prefix% &7Tous les objets ont été effacés"));
            
            Config.set("Command.ClearMobs", java.util.Arrays.asList("%prefix% &7Toutes les monstres ont été éliminés"));
            
            Config.set("Command.CheckAccount", java.util.Arrays.asList("",
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
		            ""));

            Config.set("Command.List.Part-One", java.util.Arrays.asList("§8//§7§m---------------§r§8\\\\ §3[§bList§3] §8//§7§m---------------§r§8\\\\",
		            " ",
		            "  &8→ &6&lPage %number%",
		            " "));
            
            Config.set("Command.List.Part-Two", java.util.Arrays.asList("§8\\\\§7§m---------------§r§8// §3[§bList§3] §8\\\\§7§m---------------§r§8//"));
            
            Config.set("Command.List.Gui.Other.Page.Next", "&bSuivant");
            Config.set("Command.List.Gui.Other.Page.Back", "&bPrécédent");
            Config.set("Command.List.Gui.Other.Back.PanelAdmin", "&c&lRetour au panel admin");
            Config.set("Command.List.Gui.Player.Survival", "&cSURVIE");
            Config.set("Command.List.Gui.Player.Spectator", "&eSPECTATEUR");
            Config.set("Command.List.Gui.Player.Creative", "&6CREATIF");
            Config.set("Command.List.Gui.Player.Adventure", "&aAVENTURE");
            Config.set("Command.List.Gui.Player.Gamemode", "&7Gamemode: ");
            Config.set("Command.List.Gui.Player.World", "&7Monde: &e");
            Config.set("Command.List.Gui.Player.LeftClick", "&eClique gauche&7 pour gérer le joueur");
            
            // EDIT PLAYER
            
            Config.set("Command.EditPlayer.Gui.Gamemode.LeftClick", "&eClique gauche&7 pour changer le gamemode du joueur");
            Config.set("Command.EditPlayer.Gui.Gamemode.Survival", "&cSURVIE");
            Config.set("Command.EditPlayer.Gui.Gamemode.Spectator", "&eSPECTATEUR");
            Config.set("Command.EditPlayer.Gui.Gamemode.Creative", "&6CREATIF");
            Config.set("Command.EditPlayer.Gui.Gamemode.Adventure", "&aAVENTURE");
            
            Config.set("Command.EditPlayer.Gui.ClearInv.LeftClick", "&eClique gauche&7 pour vider l'inventaire/armure du joueur");
            Config.set("Command.EditPlayer.Gui.ClearInv.Item-Name", "&6Vider l'inventaire");
            
            Config.set("Command.EditPlayer.Gui.Teleport.LeftClick", "&eClique gauche&7 pour être téléporté au joueur");
            Config.set("Command.EditPlayer.Gui.Teleport.Item-Name", "&6Se téléporter à ce joueur");
            
            Config.set("Command.EditPlayer.Gui.MoreSoon.Item-Name", "Bien plus bientôt...");
            
            Config.set("Command.EditPlayer.Gui.BackToPlayerList.Item-Name", "&cRetour à la liste des joueurs");
            
            //
            
            Config.set("Command.No-Clip.Enable", java.util.Arrays.asList("%prefix% &7Le No clip est &aactivé"));
            
            Config.set("Command.No-Clip.Disable", java.util.Arrays.asList("%prefix% &7Le No clip est &cdésactivé"));
            
            Config.set("Command.NightVision", java.util.Arrays.asList("%prefix% &7Vous pouvez voir dans le noir"));
            
            saveConfigFile();

        }
    }
}