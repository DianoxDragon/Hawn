package fr.dianox.hawn.utility.config.messages;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.dianox.hawn.Main;

public class ConfigMCommands {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMCommands() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/Classic/Commands.yml");
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
            Config.set("ClearChat.No-Reason", "there no reasons");
            Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {" &4&l* &cThe chat has been cleared because %reason% &4&l*"}));
            Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {" &4&l* &cThe chat has been cleared by &6%player%&c because %reason% &4&l*"}));
            Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"%prefix% &7Your chat has been cleared &e%player%"}));
            Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"%prefix% &7Your chat has been cleared &e%target%"}));
            Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"%prefix% &7The &e%player%&7's chat has been cleared"}));
            
            Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"%prefix% &cYou can't talk, because the chat is locked"}));
            Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cThe chat has been locked by &6%player% &4&m&l-<-=->-"}));
            Config.set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cThe chat has been locked by &6%player%&c for &6%minutes% minutes &4&m&l-<-=->-"}));
            Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {" &3&m&l->-=-<-&r &bThe chat has been &aunlocked&b by &e%player% &3&m&l->-=-<-"}));
            
            Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"%prefix% &7Your ping is &e%ping%&7 ms"}));
            Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"%prefix% &7The &b%target%&7's ping is &e%ping%&7 ms"}));
            
            Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"%prefix% &cYou can't talk, the chat is delayed of &6%DELAY%&c second(s)"}));
            Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"%prefix% &7The delay of the chat was set to &e%DELAY%&7 second(s)", 
            		"          &7&oDon't forget to edit this value in the config", 
            		"          &7&oThe value is only valid if the server doesn't shut down"}));
            Config.set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7The chat delay has been &cdisabled"}));
            
            Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eBroadcast&8]&r %broadcast%"}));
            
            /*
             * Weather and Time
             */
            Config.set("Weather.Set.Sun.Enable", true);
            Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"%prefix% &7The rain and thunderstorm has been removed, &elong live the sun&7!"}));
            Config.set("Weather.Set.Rain.Enable", true);
            Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"%prefix% &7You set the &brain in this world&7, &bbe careful not to get wet&7!"}));
            Config.set("Weather.Set.Thunder.Enable", true);
            Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"%prefix% &6&k!!!!&7 &eThe &estorm is a sign of disasters&7... &eBe careful not to get electrocuted &6&k!!!!"}));
            
            Config.set("Time.Set.Day.Enable", true);
            Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"%prefix% &7It's &emorning&7, time to go to school"}));
            Config.set("Time.Set.Night.Enable", true);
            Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"%prefix% &f&lIt's dark outside. Be careful, the night is dark and full of terror!"}));
            
            /*
             * FLY COMMANDS
             */
            Config.set("Fly.Enable.Enable", true);
            Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your fly mode has been &aenabled"}));
            Config.set("Fly.Enable-Other.Enable", true);
            Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your fly mode has been &aenabled&7 by &e%player%"}));
            Config.set("Fly.Enable-Other-Executor.Enable", true);
            Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7's fly mode has been &aenabled"}));
            
            Config.set("Fly.Disable.Enable", true);
            Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your fly mode has been &cdisabled"}));
            Config.set("Fly.Disable-Other.Enable", true);
            Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your fly mode has been &cdisabled&7 by &e%player%"}));
            Config.set("Fly.Disable-Other-Executor.Enable", true);
            Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7's fly mode has been &cdisabled"}));
            
            /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
            Config.set("Gamemode.Self.Survival.Enable", true);
            Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &esurvival&7 mode"}));
            Config.set("Gamemode.Self.Creative.Enable", true);
            Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &ecreative&7 mode"}));
            Config.set("Gamemode.Self.Adventure.Enable", true);
            Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &eadventure&7 mode"}));
            Config.set("Gamemode.Self.Spectator.Enable", true);
            Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &espectator&7 mode"}));
            
            Config.set("Gamemode.Other.Survival.Enable", true);
            Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &esurvival&7 mode by &b%player%"}));
            Config.set("Gamemode.Other.Creative.Enable", true);
            Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &ecreative&7 mode by &b%player%"}));
            Config.set("Gamemode.Other.Adventure.Enable", true);
            Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &eadventure&7 mode by &b%player%"}));
            Config.set("Gamemode.Other.Spectator.Enable", true);
            Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &espectator&7 mode by &b%player%"}));
            
            Config.set("Gamemode.Other-Sender.Survival.Enable", true);
            Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &esurvival&7 mode"}));
            Config.set("Gamemode.Other-Sender.Creative.Enable", true);
            Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &ecreative&7 mode"}));
            Config.set("Gamemode.Other-Sender.Adventure.Enable", true);
            Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &eadventure&7 mode"}));
            Config.set("Gamemode.Other-Sender.Spectator.Enable", true);
            Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &espectator&7 mode"}));
            
            Config.set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
            Config.set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou are already in the right gamemode"}));
            Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
            Config.set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe player %target% is already in the right gamemode"}));
            
            /* -------------- *
			 * GOTOP COMMANDS *
			 * -------------- */
            Config.set("Gotop.Enable", true);
            Config.set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been teleported to the highest block of your position"}));
            
            /* ------------- *
			 * FEED COMMANDS *
			 * ------------- */
            Config.set("Feed.Self.Enable", true);
            Config.set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been fed"}));
            
            Config.set("Feed.Other.Enable", true);
            Config.set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been fed by &e%player%"}));
            Config.set("Feed.Other-Sender.Enable", true);
            Config.set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 has been fed"}));
            
            /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
            Config.set("Heal.Self.Enable", true);
            Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been healed"}));
            
            Config.set("Heal.Other.Enable", true);
            Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been healed by &e%player%"}));
            Config.set("Heal.Other-Sender.Enable", true);
            Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 has been healed"}));
            
            /* ------------------- *
			 * ENDERCHEST COMMANDS *
			 * ------------------- */
            
            Config.set("EnderChest.Self.Enable", true);
            Config.set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You opened your enderchest"}));
            
            Config.set("EnderChest.Other-Sender.Enable", true);
            Config.set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You opened &e%target%&7's enderchest"}));
            
            /* --------------- *
			 * INVSEE COMMANDS *
			 * --------------- */
            
            Config.set("InvSee.Other-Sender.Enable", true);
            Config.set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You opened &e%target%&7's inventory"}));
            
            /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
            Config.set("Scoreboard.Toggle.On.Enable", true);
            Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The scoreboard has been &aenabled"}));
            Config.set("Scoreboard.Toggle.Off.Enable", true);
            Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The scoreboard has been &cdisabled"}));
            
            Config.set("Scoreboard.Changes.Enable", true);
            Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your actual scoreboard has been set to &e%arg1%"}));
            Config.set("Scoreboard.Error-Changes.Enable", true);
            Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe scoreboard &6%arg1%&c doesn't exist"}));
            
            Config.set("Scoreboard.Keep-On.Enable", true);
            Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You will now keep this scoreboard"}));
            Config.set("Scoreboard.Keep-Off.Enable", true);
            Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You will &cno longer&7 keep this scoreboard"}));
            
            Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
            Config.set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou don't have a permission, for any scoreboard"}));
            
            /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
            Config.set("Warp.Tp.Self.Enable", true);
            Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been teleported to the warp &e%warp%"}));
            Config.set("Warp.Tp.Self-Delay.Enable", true);
            Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command"}));
            
            Config.set("Warp.Tp.Other.Enable", true);
            Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been teleported to the warp &e%warp%&7 by &b%player%"}));
            Config.set("Warp.Tp.Other-Sender.Enable", true);
            Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You teleported &b%target%&7 to the warp&e %warp%"}));
            Config.set("Warp.Tp.Other-Sender-Delay.Enable", true);
            Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command for this player"}));
            
            Config.set("Warp.List.Enable", true);
            Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bWarpList :&e %warplist%"}));
            
            Config.set("Warp.No-Warp.Enable", true);
            Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cI'm sorry, but there are no warp"}));
            
            Config.set("Warp.Set.Warp-Set.Enable", true);
            Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The warp has been created with the name &e%arg%"}));
            Config.set("Warp.Set.Warp-Already-Exist.Enable", true);
            Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe name already exists"}));
            
            Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
            Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe warp doesn't exist"}));
            Config.set("Warp.Del.Warp-Delete.Enable", true);
            Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The warp &e%warp%&7 has been &cdeleted"}));
            
            Config.set("Warp.Edit.Warp-Edited.Enable", true);
            Config.set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The new location of the warp &e%warp% has been set"}));
            
            /* ---------------- *
			 * SUICIDE COMMANDS *
			 * ---------------- */
            
            Config.set("Suicide.Self.Enable", true);
            Config.set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You killed yourself"}));
            
            Config.set("Suicide.Broadcast.Enable", true);
            Config.set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% said goodbye to the world"}));
            
            /* --------------- *
			 * REPAIR COMMANDS *
			 * --------------- */
            
            Config.set("Repair.Repaired.Enable", true);
            Config.set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The item &e%item% &7has been fixed"}));
            
            Config.set("Repair.Can-t-Repair.Enable", true);
            Config.set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cSorry, but you can't repair that item"}));
            
            /* ------------ *
			 * EXP COMMANDS *
			 * ------------ */
            
            Config.set("Exp.Add.Sender.Enable", true);
            Config.set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7has been added to the &b%target%&7's total experience points"}));
            Config.set("Exp.Add.Target.Enable", true);
            Config.set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7has been added to your total experience points"}));
            
            Config.set("Exp.Set.Sender.Enable", true);
            Config.set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7's total experience points has been set to &e%number_exp%"}));
            Config.set("Exp.Set.Target.Enable", true);
            Config.set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your total experience points has been set to &e%number_exp%"}));
            
            Config.set("Exp.Take.Sender.Enable", true);
            Config.set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7's lost &e%number_exp%&7 experience points"}));
            Config.set("Exp.Take.Target.Enable", true);
            Config.set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You lost &e%number_exp%&7 experience points"}));
            Config.set("Exp.Take.Sender-Error.Enable", true);
            Config.set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou can't take more than &6%target_exp_points%&c for the player &e%target%"}));
            
            Config.set("Exp.Clear.Sender.Enable", true);
            Config.set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7's has now 0 experience points"}));
            Config.set("Exp.Clear.Target.Enable", true);
            Config.set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your experience points has been cleared"}));

            /*
             * Hat
             */
            Config.set("Hat.Self.Set.Enable", true);
            Config.set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7The new hat has been set"}));
            Config.set("Hat.Self.Removed.Enable", true);
            Config.set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7The hat has been removed"}));
            
            Config.set("Hat.Other-Target.Set.Enable", true);
            Config.set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7You got a new hat from &e%player%"}));
            Config.set("Hat.Other-Target.Removed.Enable", true);
            Config.set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Your hat has been removed from &e%player%"}));
            
            Config.set("Hat.Other-Sender.Set.Enable", true);
            Config.set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7You set a new hat for &e%target%"}));
            Config.set("Hat.Other-Sender.Removed.Enable", true);
            Config.set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7You removed the hat of &e%target%"}));
            
            Config.set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
            Config.set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cSorry but you can't set a new hat"}));
            Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
            Config.set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cSorry but you can't remove the hat, it's empty"}));
            
            /*
             * GetPos
             */
            Config.set("GetPos.Enable", true);
            Config.set("GetPos.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% The player location is: &e%X% %Y% %Z%&7 in the world&b %world%"}));
            
            /*
             * Vanish stuff
             */
            Config.set("Vanish.Self.Enable", true);
            Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &aenabled"}));
            Config.set("Vanish.Self-Disabled.Enable", true);
            Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &cdisabled"}));
            /*Config.set("Vanish.Self-Still.Enable", true);
            Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bVanish is still enabled"}));*/
            Config.set("Vanish.Other-Target.Enable", true);
            Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &aenabled&7 by&e %player%"}));
            Config.set("Vanish.Other-Target-Disabled.Enable", true);
            Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &cdisabled&7 by&e %player%"}));
            Config.set("Vanish.Other-Sender.Enable", true);
            Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The &b%target%&7's vanish has been &aenabled&7 by&e %player%"}));
            Config.set("Vanish.Other-Sender-Disabled.Enable", true);
            Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The &b%target%&7's vanish has been &cdisabled&7 by&e %player%"}));
            Config.set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aYou are vanished"}));
            
            /*
             * Clear inv stuff
             */
            Config.set("ClearInv.Self.Enable", true);
            Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your inventory has been cleaned up"}));
            Config.set("ClearInv.Other-Target.Enable", true);
            Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your inventory has been cleaned up by &e%player%"}));
            Config.set("ClearInv.Other-Sender.Enable", true);
            Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The &e%target%&7's inventory has been cleaned up"}));
            
            /*
             * Spawn stuff
             */
            Config.set("Spawn.Tp.Self-Delay.Enable", true);
            Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command"}));
            Config.set("Spawn.Tp.Other-Sender-Delay.Enable", true);
            Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command for this player"}));
            
            Config.set("Spawn.List.Enable", true);
            Config.set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bSpawnlist :&e %spawnlist%"}));
            
            Config.set("Spawn.No-Spawn.Enable", true);
            Config.set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cI'm sorry, but there are no spawn"}));
            
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