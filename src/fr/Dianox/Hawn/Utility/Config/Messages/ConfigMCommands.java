package fr.Dianox.Hawn.Utility.Config.Messages;

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
		
		file = new File(pl.getDataFolder(), "Messages/Classic/Commands.yml");
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
            
            Config.set("ClearChat.No-Reason", "It has been held that");
            Config.set("ClearChat.Anonymously", java.util.Arrays.asList(new String[] {"&cChat clear because %reason%"}));
            Config.set("ClearChat.Normal", java.util.Arrays.asList(new String[] {"&cChat clear by %player% because %reason%"}));
            Config.set("ClearChat.Own", java.util.Arrays.asList(new String[] {"&cYour chat has been clear %player%"}));
            Config.set("ClearChat.Other.Target", java.util.Arrays.asList(new String[] {"&cYour chat has been clear %target%"}));
            Config.set("ClearChat.Other.Sender", java.util.Arrays.asList(new String[] {"&cThe %player%'s chat has been clear"}));
            
            Config.set("MuteChat.Can-t-Speak", java.util.Arrays.asList(new String[] {"&cThe chat is disable"}));
            Config.set("MuteChat.Admin.On", java.util.Arrays.asList(new String[] {"&cThe chat has been disabled by %player%!"}));
            Config.set("MuteChat.Admin.Off", java.util.Arrays.asList(new String[] {"&aThe chat has been enabled by %player%!"}));
            
            Config.set("Ping.Self", java.util.Arrays.asList(new String[] {"&c%player% &7>> &e%ping% ms"}));
            Config.set("Ping.Other", java.util.Arrays.asList(new String[] {"&c%target% &7>> &e%ping% ms"}));
            
            Config.set("ChatDelay.Delay", java.util.Arrays.asList(new String[] {"&cChat is currently dealyed of %DELAY% seconds"}));
            Config.set("ChatDelay.Admin.Set", java.util.Arrays.asList(new String[] {"&cDelay set to &e%DELAY%.", "&7&oDon't forget to edit this value in the config. Yes this command is only valid if the server does not shut down"}));
            
            Config.set("Broadcast", java.util.Arrays.asList(new String[] {"&8[&eBroadcast&8]&r %broadcast%"}));
            
            Config.set("Weather.Set.Sun.Enable", Boolean.valueOf(true));
            Config.set("Weather.Set.Sun.Message", java.util.Arrays.asList(new String[] {"&6The rain and thunderstorm has been removed, long live the sun!"}));
            Config.set("Weather.Set.Rain.Enable", Boolean.valueOf(true));
            Config.set("Weather.Set.Rain.Message", java.util.Arrays.asList(new String[] {"&3You set the rain in this world, be careful not to get wet!"}));
            Config.set("Weather.Set.Thunder.Enable", Boolean.valueOf(true));
            Config.set("Weather.Set.Thunder.Message", java.util.Arrays.asList(new String[] {"&6&k!!!!&r &eThe storm is a sign of disasters... Be careful not to get electrocuted &6&k!!!!"}));
            
            Config.set("Time.Set.Day.Enable", Boolean.valueOf(true));
            Config.set("Time.Set.Day.Message", java.util.Arrays.asList(new String[] {"&eIt's morning, time to go to school."}));
            Config.set("Time.Set.Night.Enable", Boolean.valueOf(true));
            Config.set("Time.Set.Night.Message", java.util.Arrays.asList(new String[] {"&f&lIt's dark outside. Be careful, the night is dark and full of terror!"}));
            
            Config.set("Fly.Enable.Enable", Boolean.valueOf(true));
            Config.set("Fly.Enable.Messages", java.util.Arrays.asList(new String[] {"&bFly mode enabled"}));
            Config.set("Fly.Enable-Other.Enable", Boolean.valueOf(true));
            Config.set("Fly.Enable-Other.Messages", java.util.Arrays.asList(new String[] {"&bYour fly mode has been enabled by %player%"}));
            Config.set("Fly.Enable-Other-Executor.Enable", Boolean.valueOf(true));
            Config.set("Fly.Enable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"&bFly mode for %target% has been enabled"}));
            Config.set("Fly.Disable.Enable", Boolean.valueOf(true));
            Config.set("Fly.Disable.Messages", java.util.Arrays.asList(new String[] {"&cFly mode disabled"}));
            Config.set("Fly.Disable-Other.Enable", Boolean.valueOf(true));
            Config.set("Fly.Disable-Other.Messages", java.util.Arrays.asList(new String[] {"&cYour fly mode has been disabled by %player%"}));
            Config.set("Fly.Disable-Other-Executor.Enable", Boolean.valueOf(true));
            Config.set("Fly.Disable-Other-Executor.Messages", java.util.Arrays.asList(new String[] {"&cFly mode for %target% has been disabled"}));
            
            /* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
            Config.set("Gamemode.Self.Survival.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &esurvival&b mode"}));
            Config.set("Gamemode.Self.Creative.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &ecreative&b mode"}));
            Config.set("Gamemode.Self.Adventure.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &eadventure&b mode"}));
            Config.set("Gamemode.Self.Spectator.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &espectator&b mode"}));
            
            Config.set("Gamemode.Other.Survival.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &esurvival&b mode by %player%"}));
            Config.set("Gamemode.Other.Creative.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &ecreative&b mode by %player%"}));
            Config.set("Gamemode.Other.Adventure.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &eadventure&b mode by %player%"}));
            Config.set("Gamemode.Other.Spectator.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &espectator&b mode by %player%"}));
            
            Config.set("Gamemode.Other-Sender.Survival.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &esurvival&b mode"}));
            Config.set("Gamemode.Other-Sender.Creative.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &ecreative&b mode"}));
            Config.set("Gamemode.Other-Sender.Adventure.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &eadventure&b mode"}));
            Config.set("Gamemode.Other-Sender.Spectator.Enable", Boolean.valueOf(true));
            Config.set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &espectator&b mode"}));
            
            /* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
            Config.set("Heal.Self.Enable", Boolean.valueOf(true));
            Config.set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"&bYou have been healed"}));
            
            Config.set("Heal.Other.Enable", Boolean.valueOf(true));
            Config.set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"&bYou have been healed by %player%"}));
            Config.set("Heal.Other-Sender.Enable", Boolean.valueOf(true));
            Config.set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&b%target% has been healed"}));
            
            /* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
            Config.set("Scoreboard.Toggle.On.Enable", Boolean.valueOf(true));
            Config.set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"&bScoreboard enabled"}));
            Config.set("Scoreboard.Toggle.Off.Enable", Boolean.valueOf(true));
            Config.set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"&cScoreboard disabled"}));
            
            Config.set("Scoreboard.Changes.Enable", Boolean.valueOf(true));
            Config.set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"&bYour scoreboard was set to %arg1%"}));
            Config.set("Scoreboard.Error-Changes.Enable", Boolean.valueOf(true));
            Config.set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"&cThe scoreboard %arg1% doesn't exist"}));
            
            Config.set("Scoreboard.Keep-On.Enable", Boolean.valueOf(true));
            Config.set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"&bYou will now keep this scoreboard"}));
            Config.set("Scoreboard.Keep-Off.Enable", Boolean.valueOf(true));
            Config.set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"&cYou no longer keep this scoreboard"}));
            
            /* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
            Config.set("Warp.Tp.Self.Enable", Boolean.valueOf(true));
            Config.set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"&bYou have been teleported to the warp %warp%"}));
            Config.set("Warp.Tp.Self-Delay.Enable", Boolean.valueOf(true));
            Config.set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before be tp."}));
            
            Config.set("Warp.Tp.Other.Enable", Boolean.valueOf(true));
            Config.set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"&bYou have been teleported to the warp %warp% by %player%"}));
            Config.set("Warp.Tp.Other-Sender.Enable", Boolean.valueOf(true));
            Config.set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&bYou have been teleported %target% to the warp %warp%"}));
            Config.set("Warp.Tp.Other-Sender-Delay.Enable", Boolean.valueOf(true));
            Config.set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before the player is tp."}));
            
            Config.set("Warp.List.Enable", Boolean.valueOf(true));
            Config.set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"&bWarpList :&e %warplist%"}));
            
            Config.set("Warp.No-Warp.Enable", Boolean.valueOf(true));
            Config.set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"&cNo warp set"}));
            
            Config.set("Warp.Set.Warp-Set.Enable", Boolean.valueOf(true));
            Config.set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"&eWarp set on behalf of %arg%"}));
            Config.set("Warp.Set.Warp-Already-Exist.Enable", Boolean.valueOf(true));
            Config.set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"&cThe name already exist"}));
            
            Config.set("Warp.Del.Warp-Doesnt-Exist.Enable", Boolean.valueOf(true));
            Config.set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"&cThe warp doesn't exist"}));
            Config.set("Warp.Del.Warp-Delete.Enable", Boolean.valueOf(true));
            Config.set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"&bThe warp &e%warp%&b has been deleted"}));
            
            Config.set("Vanish.Self.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"&bVanish has been enabled"}));
            Config.set("Vanish.Self-Disabled.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cVanish has been disabled"}));
            /*Config.set("Vanish.Self-Still.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bVanish is still enabled"}));*/
            Config.set("Vanish.Other-Target.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"&bVanish has been enabled by %player%"}));
            Config.set("Vanish.Other-Target-Disabled.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cVanish has been disabled by %player%"}));
            Config.set("Vanish.Other-Sender.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&bVanish has been enabled by %player% for %target%"}));
            Config.set("Vanish.Other-Sender-Disabled.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cVanish has been disabled by %player% for %target%"}));
            
            Config.set("ClearInv.Self.Enable", Boolean.valueOf(true));
            Config.set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"&bYour inventory has been cleaned up"}));
            Config.set("ClearInv.Other-Target.Enable", Boolean.valueOf(true));
            Config.set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"&bYour inventory has been cleaned up by %player%"}));
            Config.set("ClearInv.Other-Sender.Enable", Boolean.valueOf(true));
            Config.set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&bThe %target% inventory has been cleaned up"}));
            
            Config.set("Spawn.Tp.Self-Delay.Enable", Boolean.valueOf(true));
            Config.set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before be tp."}));
            Config.set("Spawn.Tp.Other-Sender-Delay.Enable", Boolean.valueOf(true));
            Config.set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before the player is tp."}));
            
            saveConfigFile();

        }
    }

}