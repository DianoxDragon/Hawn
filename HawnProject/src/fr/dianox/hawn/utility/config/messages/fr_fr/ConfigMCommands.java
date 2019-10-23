package fr.dianox.hawn.utility.config.messages.fr_fr;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMCommands {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMCommands() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/fr_FR/Classic/Commands.yml");
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
            Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bLe chat a été &adéverrouillé&b par &e%player% &3&m&l->-=-<-"}));
            
            Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Votre ping est de &e%ping%&7 ms"}));
            Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7Le ping de &b%target%&7 est de &e%ping%&7 ms"}));
            
            Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cVous ne pouvez pas parler, le chat est retardé de &6%DELAY%&c seconde(s)"}));
            Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7Le délai du chat a été fixé à &e%DELAY%&7 seconde(s)", 
            		"          &7&oN'oubliez pas d'éditer cette valeur dans la configuration", 
            		"          &7&oLa valeur n'est valide que si le serveur ne s'arrête pas"}));
            
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
            
            saveConfigFile();

        }
    }

}