package fr.dianox.hawn.utility.config.messages.fr_fr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FRConfigMMsg {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public FRConfigMMsg() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Messages.yml");
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
             * Chat stuff
             */
            Config.set("ClearChat.No-Reason", "il n'y a aucune raison");
            Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {" &4&l* &cLe chat a été effacé parce que %reason% &4&l*"}));
            Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {" &4&l* &cLe chat a été effacé par &6%player%&c parce que %reason% &4&l*"}));
            Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"%prefix% &7Votre chat a été effacé &e%player%"}));
            Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"%prefix% &7Votre chat a été effacé &e%target%"}));
            Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"%prefix% &7Le chat de &e%player%&7 a été effacé"}));
            
            Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"%prefix% &cTu ne peux pas parler, parce que le chat est verrouillé"}));
            Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cLe chat a été verrouillé par &6%player% &4&m&l-<-=->-"}));
            Config.set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cLe chat a été verrouillé par &6%player%&c pour &6%minutes% minutes &4&m&l-<-=->-"}));
            Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bLe chat a été &adéverrouillé&b par &e%player% &3&m&l->-=-<-"}));
            
            Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Votre ping est de &e%ping%&7 ms"}));
            Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7Le ping de &b%target%&7 est de &e%ping%&7 ms"}));
            
            Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cVous ne pouvez pas parler, le chat est retardé de &6%DELAY%&c seconde(s)"}));
            Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7Le délai du chat a été fixé à &e%DELAY%&7 seconde(s)", 
            		"          &7&oN'oubliez pas d'éditer cette valeur dans la configuration", 
            		"          &7&oLa valeur n'est valide que si le serveur ne s'arrête pas"}));
            Config.set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7Le délai du chat a été &cdésactivé"}));
            
            Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eBroadcast&8]&r %broadcast%"}));
            
            /*
             * Weather and Time
             */
            Config.set("Weather.Set.Sun.Enable", true);
            Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"%prefix% &7La pluie et l'orage ont été enlevés, &evive le soleil&7!"}));
            Config.set("Weather.Set.Rain.Enable", true);
            Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"%prefix% &7Tu as mis la &bpluie dans ce monde&7, &bfaites attention de ne pas vous mouiller&7!"}));
            Config.set("Weather.Set.Thunder.Enable", true);
            Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"%prefix% &6&k!!!!&7 &eL'&eorage est un signe de désastre&7... &eAttention à ne pas être électrocuté &6&k!!!!"}));
            
            Config.set("Time.Set.Day.Enable", true);
            Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"%prefix% &7C'est le &ematin&7, l'heure d'aller à l'école"}));
            Config.set("Time.Set.Night.Enable", true);
            Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"%prefix% &f&lIl fait sombre dehors. Attention, la nuit est sombre et pleine de terreur!"}));
            
            /*
             * FLY COMMANDS
             */
            Config.set("Fly.Enable.Enable", true);
            Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de vol a été &aactivé"}));
            Config.set("Fly.Enable-Other.Enable", true);
            Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de vol a été &aactivé&7 par &e%player%"}));
            Config.set("Fly.Enable-Other-Executor.Enable", true);
            Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le mode de vol de &e%target%&7 a été &7&aactivé"}));
            
            Config.set("Fly.Disable.Enable", true);
            Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de vol a été &cdésactivé"}));
            Config.set("Fly.Disable-Other.Enable", true);
            Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de vol a été &cdésactivé&7 par &e%player%"}));
            Config.set("Fly.Disable-Other-Executor.Enable", true);
            Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le mode de vol de &e%target%&7 a été &cdésactivé"}));
            
            /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
            Config.set("Gamemode.Self.Survival.Enable", true);
            Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &esurvie"}));
            Config.set("Gamemode.Self.Creative.Enable", true);
            Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &ecréatif"}));
            Config.set("Gamemode.Self.Adventure.Enable", true);
            Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &eaventure"}));
            Config.set("Gamemode.Self.Spectator.Enable", true);
            Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &espectateur"}));
            
            Config.set("Gamemode.Other.Survival.Enable", true);
            Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &esurvie&7 par &b%player%"}));
            Config.set("Gamemode.Other.Creative.Enable", true);
            Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &ecréatif&7 par &b%player%"}));
            Config.set("Gamemode.Other.Adventure.Enable", true);
            Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &eaventure&7 par &b%player%"}));
            Config.set("Gamemode.Other.Spectator.Enable", true);
            Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre mode de jeu a été défini sur le mode &espectateur&7 par &b%player%"}));
            
            Config.set("Gamemode.Other-Sender.Survival.Enable", true);
            Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le mode de jeu du joueur &b%player%&7a été défini sur le mode &esurvive"}));
            Config.set("Gamemode.Other-Sender.Creative.Enable", true);
            Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le mode de jeu du joueur &b%player%&7a été défini sur le mode &ecréatif"}));
            Config.set("Gamemode.Other-Sender.Adventure.Enable", true);
            Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le mode de jeu du joueur &b%player%&7a été défini sur le mode &eaventure"}));
            Config.set("Gamemode.Other-Sender.Spectator.Enable", true);
            Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le mode de jeu du joueur &b%player%&7a été défini sur le mode &espectateur"}));
            
            Config.set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
            Config.set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous êtes déjà dans le bon mode de jeu"}));
            Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
            Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe joueur %target% est déjà dans le bon mode de jeu"}));
            
            /* -------------- *
			 * GOTOP COMMANDS *
			 * -------------- */
            Config.set("Gotop.Enable", true);
            Config.set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez été téléporté au bloc le plus élevé de votre position"}));
            
            /* ------------- *
			 * FEED COMMANDS *
			 * ------------- */
            Config.set("Feed.Self.Enable", true);
            Config.set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez été nourri"}));
            
            Config.set("Feed.Other.Enable", true);
            Config.set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez été nourri par &e%player%"}));
            Config.set("Feed.Other-Sender.Enable", true);
            Config.set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 a été nourri"}));
            
            /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
            Config.set("Heal.Self.Enable", true);
            Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez été guéri"}));
            
            Config.set("Heal.Other.Enable", true);
            Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez été guéri par &e%player%"}));
            Config.set("Heal.Other-Sender.Enable", true);
            Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 a été guéri"}));
            
            /* ------------------- *
			 * ENDERCHEST COMMANDS *
			 * ------------------- */
            
            Config.set("EnderChest.Self.Enable", true);
            Config.set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez ouvert votre enderchest"}));
            
            Config.set("EnderChest.Other-Sender.Enable", true);
            Config.set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez ouvert l'enderchest de &e%target%"}));
            
            /* --------------- *
			 * INVSEE COMMANDS *
			 * --------------- */
            
            Config.set("InvSee.Other-Sender.Enable", true);
            Config.set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7vous avez ouvert l'inventaire de &e%target%"}));
            
            /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
            Config.set("Scoreboard.Toggle.On.Enable", true);
            Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le scoreboard a été &aactivé"}));
            Config.set("Scoreboard.Toggle.Off.Enable", true);
            Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le scoreboard a été &cdésactivé"}));
            
            Config.set("Scoreboard.Changes.Enable", true);
            Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre scoreboard actuel a été défini sur &e%arg1%"}));
            Config.set("Scoreboard.Error-Changes.Enable", true);
            Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe scoreboard &6%arg1%&c n'existe pas"}));
            
            Config.set("Scoreboard.Keep-On.Enable", true);
            Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous allez maintenant conserver ce scoreboard"}));
            Config.set("Scoreboard.Keep-Off.Enable", true);
            Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous ne &cconserverez&7 plus ce scoreboard"}));
            
            Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
            Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous n'avez pas d'autorisation, pour aucun scoreboard"}));
            
            /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
            Config.set("Warp.Tp.Self.Enable", true);
            Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez été téléporté au warp &e%warp%"}));
            Config.set("Warp.Tp.Self-Delay.Enable", true);
            Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7S'il vous plaît, attendez &4&l%second%&c seconde(s)&7 avant de pouvoir exécuter la commande"}));
            
            Config.set("Warp.Tp.Other.Enable", true);
            Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez été téléporté au &e%warp%&7 par &b%player%"}));
            Config.set("Warp.Tp.Other-Sender.Enable", true);
            Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu as téléporté &b%target%&7 au warp&e %warp%"}));
            Config.set("Warp.Tp.Other-Sender-Delay.Enable", true);
            Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7S'il vous plaît, attendez &4&l%second%&c seconde(s)&7 avant de pouvoir exécuter la commande pour ce joueur"}));
            
            Config.set("Warp.List.Enable", true);
            Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bListe des warps :&e %warplist%"}));
            
            Config.set("Warp.No-Warp.Enable", true);
            Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe suis désolé, mais il n'y a pas de warp"}));
            
            Config.set("Warp.Set.Warp-Set.Enable", true);
            Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La warp a été créée avec le nom &e%arg%"}));
            Config.set("Warp.Set.Warp-Already-Exist.Enable", true);
            Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe nom existe déjà"}));
            
            Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
            Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe warp n'existe pas"}));
            Config.set("Warp.Del.Warp-Delete.Enable", true);
            Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le warp &e%warp%&7 a été &csupprimé"}));
            
            Config.set("Warp.Edit.Warp-Edited.Enable", true);
            Config.set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La nouvelle position du warp &e%warp% a été placé"}));
            
            /* ---------------- *
			 * SUICIDE COMMANDS *
			 * ---------------- */
            
            Config.set("Suicide.Self.Enable", true);
            Config.set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu t'es suicidé"}));
            
            Config.set("Suicide.Broadcast.Enable", true);
            Config.set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% a dit au revoir au monde entier"}));
            
            /* --------------- *
			 * REPAIR COMMANDS *
			 * --------------- */
            
            Config.set("Repair.Repaired.Enable", true);
            Config.set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7L'objet &e%item% &7a été réparé"}));
            
            Config.set("Repair.Can-t-Repair.Enable", true);
            Config.set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, mais vous ne pouvez pas réparer cette objet"}));
            
            /* ------------ *
			 * EXP COMMANDS *
			 * ------------ */
            
            Config.set("Exp.Add.Sender.Enable", true);
            Config.set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7a été ajouté au nombre total de points d'expérience à &b%target%"}));
            Config.set("Exp.Add.Target.Enable", true);
            Config.set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7a été ajouté au nombre total de vos points d'expérience"}));
            
            Config.set("Exp.Set.Sender.Enable", true);
            Config.set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le nombre total de points d'expérienc &b%target%&7a été défini sur &e%number_exp%"}));
            Config.set("Exp.Set.Target.Enable", true);
            Config.set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le total de vos points d'expérience a été défini à &e%number_exp%"}));
            
            Config.set("Exp.Take.Sender.Enable", true);
            Config.set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 a perdu &e%number_exp%&7 points d'expérience"}));
            Config.set("Exp.Take.Target.Enable", true);
            Config.set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu as perdu &e%number_exp%&7 points d'expérience"}));
            Config.set("Exp.Take.Sender-Error.Enable", true);
            Config.set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous ne pouvez pas prendre plus de &6%target_exp_points%&c points d'expérience pour le joueur &e%target%"}));
            
            Config.set("Exp.Clear.Sender.Enable", true);
            Config.set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7 a maintenant 0 point d'expérience"}));
            Config.set("Exp.Clear.Target.Enable", true);
            Config.set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vos points d'expérience ont été effacés"}));
            
            /* ------------------ *
			 * WORKBENCH COMMANDS *
			 * ------------------ */
            Config.set("WorkBench.Self.Enable", true);
            Config.set("WorkBench.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu as ouvert ton établi"}));
            
            Config.set("WorkBench.Target.Enable", true);
            Config.set("WorkBench.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Ton établi a été ouvert par &e%player%"}));
            Config.set("WorkBench.Sender.Enable", true);
            Config.set("WorkBench.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Vous avez ouvert l'établi de &e%target%&7"}));
            
            /* ------------- *
			 * BURN COMMANDS *
			 * ------------- */
            Config.set("Burn.Target.Enable", true);
            Config.set("Burn.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu es en train de brûler à cause de &e%player%&7 pendant &b%duration%&7 secondes"}));
            Config.set("Burn.Sender.Enable", true);
            Config.set("Burn.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu es en train de brûler &e%target%&7 pendant &b%duration%&7 secondes"}));
            
            /* -------------- *
			 * SKULL COMMANDS *
			 * -------------- */
            Config.set("Skull.Self.Enable", true);
            Config.set("Skull.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu as eu ta propre tête"}));
            
            Config.set("Skull.Sender.Enable", true);
            Config.set("Skull.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tu as eu la tête de &e%target%"}));
            
            /*
             * Hat
             */
            Config.set("Hat.Self.Set.Enable", true);
            Config.set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Le nouveau chapeau a été mis"}));
            Config.set("Hat.Self.Removed.Enable", true);
            Config.set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Le chapeau a été supprimé"}));
            
            Config.set("Hat.Other-Target.Set.Enable", true);
            Config.set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Tu as reçu un nouveau chapeau de &e%player%"}));
            Config.set("Hat.Other-Target.Removed.Enable", true);
            Config.set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Ton chapeau t'a été retiré par &e%player%"}));
            
            Config.set("Hat.Other-Sender.Set.Enable", true);
            Config.set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Vous avez mis un nouveau chapeau pour &e%target%"}));
            Config.set("Hat.Other-Sender.Removed.Enable", true);
            Config.set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Tu as supprimé le chapeau de &e%target%"}));
            
            Config.set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
            Config.set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cDésolé mais tu ne peux pas mettre de nouveau chapeau"}));
            Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
            Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cDésolé mais vous ne pouvez par supprimer ce chapeau, c'est vide"}));
            
            /*
             * GetPos
             */
            Config.set("GetPos.Enable", true);
            Config.set("GetPos.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La position du joueur est: &e%X% %Y% %Z%&7 dans le monde&b %world%"}));
            
            /*
             * Vanish stuff
             */
            Config.set("Vanish.Self.Enable", true);
            Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le vanish a été &aactivé"}));
            Config.set("Vanish.Self-Disabled.Enable", true);
            Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le vanish a été &cdésactivé"}));
            /*Config.set("Vanish.Self-Still.Enable", true);
            Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bVanish is still enabled"}));*/
            Config.set("Vanish.Other-Target.Enable", true);
            Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le vanish a été &aactivé&7 par&e %player%"}));
            Config.set("Vanish.Other-Target-Disabled.Enable", true);
            Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le vanish a été &cdésactivé&7 par&e %player%"}));
            Config.set("Vanish.Other-Sender.Enable", true);
            Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le vanish du joueur &b%target%&7 a été &aactivé&7 par&e %player%"}));
            Config.set("Vanish.Other-Sender-Disabled.Enable", true);
            Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Le vanish du joueur &b%target%&7 a été &cdésactivé&7 par&e %player%"}));
            Config.set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aVous êtes en vanish"}));
            
            /*
             * Clear inv stuff
             */
            Config.set("ClearInv.Self.Enable", true);
            Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre inventaire a été nettoyé"}));
            Config.set("ClearInv.Other-Target.Enable", true);
            Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre inventaire a été nettoyé par &e%player%"}));
            Config.set("ClearInv.Other-Sender.Enable", true);
            Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7L'inventaire du joueur &e%target%&7 a été nettoyé"}));
            
            /*
             * Spawn stuff
             */
            Config.set("Spawn.Tp.Self-Delay.Enable", true);
            Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7S'il vous plaît, attendez &4&l%second%&c seconde(s)&7 avant de pouvoir exécuter la commande"}));
            Config.set("Spawn.Tp.Other-Sender-Delay.Enable", true);
            Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7S'il vous plaît, attendez &4&l%second%&c seconde(s)&7 avant de pouvoir exécuter la commande pour ce joueur"}));
            
            Config.set("Spawn.List.Enable", true);
            Config.set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bListe des spawns :&e %spawnlist%"}));
            
            Config.set("Spawn.No-Spawn.Enable", true);
            Config.set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe suis désolé, mais il n'y a pas de spawn"}));
            
            /*
             * Warning stuff
             */
            Config.set("Warning", java.util.Arrays.asList(new String[] {
            		"<--center--> &4&m>-----------------------<", 
            		"", 
            		"%broadcast%",
            		"",
            		"<--center--> &4&m>-----------------------<"}));
            
            /*
             * EVENTS
             */
            
            Config.set("Teleport.VoidTP", java.util.Arrays.asList(new String[] {"%prefix% &7Oh non, vous avez essayé d'atteindre le vide"}));
            Config.set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eSwear&8] &b%player% &7a dit &e%message%"}));
            
            Config.set("LaunchPad.Cant-Use-Cooldown.Enable", true);
            Config.set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Désolé, mais actuellement vous ne pouvez pas utiliser les launchpads"}));
            
            Config.set("Cancel-Tp.Warp.Enable", true);
            Config.set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tp &cdésactivé"}));
            Config.set("Cancel-Tp.Spawn.Enable", true);
            Config.set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tp &cdésactivé"}));
            
            Config.set("Custom-Join-Item.Error.FunGun.Time.Enable", true);
            Config.set("Custom-Join-Item.Error.FunGun.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7S'il vous plaît , veuillez patienter &4&l%timedelayfunguncji% &c&lseconde(s)&7!"}));
            
            /*
             * PO
             */
            
            Config.set("PlayerOption.Help.Enable", true);
            Config.set("PlayerOption.Help.Messages", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bPlayerOption&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lAide sur l'option des joueurs",
            		"",
            		" &8>> &7/option fly - &eDéfinir le mode de vol",
            		" &8>> &7/option doublejump - &eActiver ou désactiver le double saut",
            		" &8>> &7/option speed - &eActiver ou désactiver la modification de vitesse",
            		" &8>> &7/option jumpboost - &eActiver ou désactiver le jumpboost",
            		" &8>> &7/option autobc - &eActiver ou désactiver la visibilité de l'autobroadcast",
            		" &8>> &7/option pv - &eActiver ou désactiver la visibilité des joueurs",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));
            
            Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
            Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre double saut a été &aactivé"}));
            Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
            Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre double saut a été &cdésactivé"}));
            
            Config.set("PlayerOption.Speed.Enable.Enable", true);
            Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre modification de vitesse a été &aactivé"}));
            Config.set("PlayerOption.Speed.Disable.Enable", true);
            Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre modification de vitesse a été &cdésactivé"}));
            Config.set("PlayerOption.Speed.Set.Enable", true);
            Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre vitesse a été réglée sur &e%arg1%"}));
            
            Config.set("PlayerOption.FlySpeed.Enable.Enable", true);
            Config.set("PlayerOption.FlySpeed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre vitesse de vol a été &aactivé"}));
            Config.set("PlayerOption.FlySpeed.Disable.Enable", true);
            Config.set("PlayerOption.FlySpeed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre vitesse de vol a été &cdésactivé"}));
            Config.set("PlayerOption.FlySpeed.Set.Enable", true);
            Config.set("PlayerOption.FlySpeed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre vitesse de vol a été réglée sur &e%arg1%"}));
            
            Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre jumpboost a été &aactivé"}));
            Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre jumpboost a été &cdésactivé"}));
            
            Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
            Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La visibilité de l'autobroadcast a été &aactivé"}));
            Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
            Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La visibilité de l'autobroadcast a été &cdésactivé"}));
            
            Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Pouf ! Les joueurs sont partis"}));
            Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Les joueurs sont maintenant visibles"}));

            Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
            Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe double saut est désactivé dans &6Cosmetics-Fun/DoubleJump.yml"}));
            Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
            Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe double saut n'est pas permis dans ce monde"}));
            
            Config.set("PlayerOption.Error.Option-Disabled.Enable", true);
            Config.set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous avez désactivé cette option, donc, vous ne pouvez pas gérer toutes les options"}));
            //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
            //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cCette option est déjà définie ou est la même"}));
            Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
            Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7S'il vous plaît , veuillez patienter &4&l%timedelaypvcji% &c&lseconde(s)&7!"}));
            
            Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
            Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLes options des joueurs ne sont pas activées dans ce monde"}));
            /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lPlease wait &4%timedelaypvcommands% &c&lseconds!"}));*/            
            
            /*
             * Stuff
             */
            
            Config.set("Error.No-Permissions.Enable", true);
            Config.set("Error.No-Permissions.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, mais vous n'avez pas la permission : %noperm%"}));
            Config.set("Error.No-Spawn.Enable", true);
            Config.set("Error.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa spawn n'existe pas"}));
            Config.set("Error.Change-Me.Enable", true);
            Config.set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous devez changer le spawn/warp/etc. sur &6%arg1%&c dans &e%arg2%"}));
            Config.set("Error.No-Players.Enable", true);
            Config.set("Error.No-Players.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe joueur n'est pas en ligne ou n'existe pas"}));
            Config.set("Error.No-Page-Found.Enable", true);
            Config.set("Error.No-Page-Found.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa page ne peut pas Ãªtre trouvée"}));
            Config.set("Error.No-Category.Enable", true);
            Config.set("Error.No-Category.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLa catégorie n'existe pas"}));
            Config.set("Error.Use-Number.Enable", true);
            Config.set("Error.Use-Number.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVeuillez préciser un nombre"}));
            Config.set("Error.Command-Disable.Enable", true);
            Config.set("Error.Command-Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, cette commande est désactivée"}));
            Config.set("Error.Argument-Missing.Enable", true);
            Config.set("Error.Argument-Missing.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cJe suis désolé, mais il doit manquer un ou deux arguments"}));
            Config.set("Error.Not-A-Player.Enable", true);
            Config.set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous n'Ãªtes pas un joueur"}));
            
            /*
             * Protect
             */
            
            Config.set("Protection.Anti-Place", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, vous ne pouvez pas placer de bloc ici !"}));
            Config.set("Protection.Anti-Break", java.util.Arrays.asList(new String[] {"%prefix% &cDésolé, vous ne pouvez casser de bloc ici !"}));
            
            saveConfigFile();

        }
    }

}