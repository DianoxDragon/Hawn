package fr.Dianox.Hawn.Utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.BroadCastCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ClearChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ClearInvCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.DelayChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.EmojiCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.FlyCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.GamemodeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.HealCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.HelpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.MuteChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.OptionPlayerConfigCommand;
import fr.Dianox.Hawn.Utility.Config.Commands.PingCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ScoreboardCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.TitleAnnouncerConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.VanishCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WarpSetWarpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WeatherTimeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGLP;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerWorldChangeConfigE;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMEvents;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMPlayerOption;

public class CheckConfig {
	
	public static void warnhawnreload() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Enable")) {
			if (Main.interval != AutoBroadcastConfig.getConfig().getInt("Config.Interval")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"You changed the value for Config.Interval in the autobroadcast config");
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"The change will only work if you reload/restart the server");
				Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| ");
			}
		}
	}
	
	public static void Check() {
		
		if (!BetweenServersConfig.getConfig().isSet("Keep.PlayerVisibility-OnJoin.Enable")) {
			BetweenServersConfig.getConfig().set("Keep.PlayerVisibility-OnJoin.Enable", Boolean.valueOf(false));
			BetweenServersConfig.getConfig().set("Keep.Speed-OnJoin.Enable", Boolean.valueOf(false));
			
			SpecialCjiHidePlayers.getConfig().set("PV.Option.OnJoin-Priority-For-Player-Option", Boolean.valueOf(true));
			
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Use-SSL", Boolean.valueOf(false));
			ConfigGeneral.getConfig().set("Plugin.12-Hours-Or-24-Hours-Format", Integer.valueOf(24));
			
			ConfigMCommands.getConfig().set("Scoreboard.Changes.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"&bYour scoreboard was set to %arg1%"}));
            ConfigMCommands.getConfig().set("Scoreboard.Error-Changes.Enable", Boolean.valueOf(true));
            ConfigMCommands.getConfig().set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"&cThe scoreboard %arg1% doesn't exist"}));
			
            ConfigMCommands.getConfig().set("Scoreboard.Keep-On.Enable", Boolean.valueOf(true));
            ConfigMCommands.getConfig().set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"&bYou will now keep this scoreboard"}));
            ConfigMCommands.getConfig().set("Scoreboard.Keep-Off.Enable", Boolean.valueOf(true));
            ConfigMCommands.getConfig().set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"&cYou no longer keep this scoreboard"}));
            
            ScoreboardCommandConfig.getConfig().set("Scoreboard.Option.Keep-Scoreboard-Change", Boolean.valueOf(true));
            
            
            ScoreboardCommandConfig.saveConfigFile();
            ConfigMCommands.saveConfigFile();
			SpecialCjiHidePlayers.saveConfigFile();
			BetweenServersConfig.saveConfigFile();
			ConfigGeneral.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Speed.Option.Priority-For-Player-Option")) {
			OnJoinConfig.getConfig().set("Speed.Option.Priority-For-Player-Option", Boolean.valueOf(true));
			
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Enable.Enable", Boolean.valueOf(true));
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"&aYour speed has been activated"}));
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Disable.Enable", Boolean.valueOf(true));
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"&cYour speed has been disabled"}));
            
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Set.Enable", Boolean.valueOf(true));
            ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"&bSpeed has been set to %arg1%"}));
            
            ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Option-Disabled.Enable", Boolean.valueOf(true));
            ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cYou disabled this option, so, you can't manage all the option"}));
            
            ConfigMPlayerOption.saveConfigFile();
			
			OnJoinConfig.saveConfigFile();
		}
		
		/*if (!BetweenServersConfig.getConfig().isSet("Keep.PlayerVisibility-OnJoin.Enable")) {
			BetweenServersConfig.getConfig().set("Keep.PlayerVisibility-OnJoin.Enable", Boolean.valueOf(false));
			BetweenServersConfig.getConfig().set("Keep.Speed-OnJoin.Enable", Boolean.valueOf(false));
			BetweenServersConfig.getConfig().set("Keep.JumpBoost-OnJoin.Enable", Boolean.valueOf(false));
			BetweenServersConfig.getConfig().set("Keep.Fly-Double-Jump-OnJoin.Enable", Boolean.valueOf(false));
	        
	        BetweenServersConfig.saveConfigFile();
		}*/
		
		if (!BroadCastCommandConfig.getConfig().isSet("DISABLE_THE_COMMAND_COMPLETELY")) {
			BroadCastCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			ClearChatCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			ClearInvCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			DelayChatCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			EmojiCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			FlyCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			
			GamemodeCommandConfig.getConfig().set("Gamemode.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			GamemodeCommandConfig.getConfig().set("gms.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			GamemodeCommandConfig.getConfig().set("gmc.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			GamemodeCommandConfig.getConfig().set("gma.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			GamemodeCommandConfig.getConfig().set("gmsp.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			
			HealCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			HelpCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			MuteChatCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			OptionPlayerConfigCommand.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			PingCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			ScoreboardCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			SpawnCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			TitleAnnouncerConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			VanishCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			
			WarpSetWarpCommandConfig.getConfig().set("Warp.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			WarpSetWarpCommandConfig.getConfig().set("WarpList.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			WarpSetWarpCommandConfig.getConfig().set("SetWarp.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			WarpSetWarpCommandConfig.getConfig().set("DelWarp.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			
			WeatherTimeCommandConfig.getConfig().set("Weather.Set.Sun.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			WeatherTimeCommandConfig.getConfig().set("Weather.Set.Rain.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			WeatherTimeCommandConfig.getConfig().set("Weather.Set.Thunder.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			WeatherTimeCommandConfig.getConfig().set("Time.Set.Day.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			WeatherTimeCommandConfig.getConfig().set("Time.Set.Night.DISABLE_THE_COMMAND_COMPLETELY", Boolean.valueOf(false));
			
			BroadCastCommandConfig.saveConfigFile();
			ClearChatCommandConfig.saveConfigFile();
			ClearInvCommandConfig.saveConfigFile();
			DelayChatCommandConfig.saveConfigFile();
			EmojiCommandConfig.saveConfigFile();
			FlyCommandConfig.saveConfigFile();
			GamemodeCommandConfig.saveConfigFile();
			HealCommandConfig.saveConfigFile();
			HelpCommandConfig.saveConfigFile();
			MuteChatCommandConfig.saveConfigFile();
			OptionPlayerConfigCommand.saveConfigFile();
			PingCommandConfig.saveConfigFile();
			ScoreboardCommandConfig.saveConfigFile();
			SpawnCommandConfig.saveConfigFile();
			TitleAnnouncerConfig.saveConfigFile();
			VanishCommandConfig.saveConfigFile();
			WarpSetWarpCommandConfig.saveConfigFile();
			WeatherTimeCommandConfig.saveConfigFile();
		
		}
		if (!ConfigMPlayerOption.getConfig().isSet("PlayerOption.Help.Messages")) {
			ConfigMPlayerOption.getConfig().set("PlayerOption.Help.Enable", Boolean.valueOf(true));
			ConfigMPlayerOption.getConfig().set("PlayerOption.Help.Messages", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bPlayerOption&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lPlayer option Help",
            		"",
            		//" &8>> &7/option fly - &eSet the fly",
            		//" &8>> &7/option doublejump - &eEnable or disable the doublejump",
            		//" &8>> &7/option speed - &eEnable or disable the speed",
            		//" &8>> &7/option jumpboost - &eEnable or disable the jumpboost",
            		" &8>> &7/option pv - &eEnable or disable player visibility",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));
			
			ConfigMPlayerOption.saveConfigFile();
		}
		
		if (!ConfigMOStuff.getConfig().isSet("Error.Command-Disable.Enable")) {
			ConfigMOStuff.getConfig().set("Error.Command-Disable.Enable", Boolean.valueOf(true));
			ConfigMOStuff.getConfig().set("Error.Argument-Missing.Enable", Boolean.valueOf(true));
			ConfigMOStuff.getConfig().set("Error.Not-A-Player.Enable", Boolean.valueOf(true));
			ConfigMOStuff.getConfig().set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"&cYou are not a player"}));
		
			ConfigMOStuff.saveConfigFile();
		}
		
		if (!ConfigMOStuff.getConfig().isSet("Error.Change-Me.Enable")) {
			ConfigMOStuff.getConfig().set("Error.Change-Me.Enable", Boolean.valueOf(true));
			ConfigMOStuff.getConfig().set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"&cYou have to change the spawn/warp/etc on %arg1% on %arg2%"}));
		
			ConfigMOStuff.saveConfigFile();
		}
		
		if (!WarpSetWarpCommandConfig.getConfig().isSet("Warp.Delay.Cancel-Tp-On.Any-movements")) {
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Cancel-Tp-On.Any-movements", Boolean.valueOf(true));
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Cancel-Tp-On.On-Damages", Boolean.valueOf(true));
            
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Cancel-Tp-On.Any-movements", Boolean.valueOf(true));
            SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Cancel-Tp-On.On-Damages", Boolean.valueOf(true));
            
            ConfigMEvents.getConfig().set("Cancel-Tp.Warp.Enable", Boolean.valueOf(true));
            ConfigMEvents.getConfig().set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"&cTp cancelled"}));
            ConfigMEvents.getConfig().set("Cancel-Tp.Spawn.Enable", Boolean.valueOf(true));
            ConfigMEvents.getConfig().set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"&cTp cancelled"}));
            
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.On.Enable", Boolean.valueOf(true));
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"&bScoreboard enabled"}));
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.Off.Enable", Boolean.valueOf(true));
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"&cScoreboard disabled"}));
            
            WarpSetWarpCommandConfig.saveConfigFile();
            SpawnCommandConfig.saveConfigFile();
            ConfigMEvents.saveConfigFile();
            ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Gamemode.Self.Survival.Enable")) {
			/* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
			ConfigMCommands.getConfig().set("Gamemode.Self.Survival.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &esurvival&b mode"}));
			ConfigMCommands.getConfig().set("Gamemode.Self.Creative.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &ecreative&b mode"}));
			ConfigMCommands.getConfig().set("Gamemode.Self.Adventure.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &eadventure&b mode"}));
			ConfigMCommands.getConfig().set("Gamemode.Self.Spectator.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &espectator&b mode"}));
        
			ConfigMCommands.getConfig().set("Gamemode.Other.Survival.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &esurvival&b mode by %player%"}));
			ConfigMCommands.getConfig().set("Gamemode.Other.Creative.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &ecreative&b mode by %player%"}));
			ConfigMCommands.getConfig().set("Gamemode.Other.Adventure.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &eadventure&b mode by %player%"}));
			ConfigMCommands.getConfig().set("Gamemode.Other.Spectator.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"&bYour gamemode was set in &espectator&b mode by %player%"}));
        
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Survival.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &esurvival&b mode"}));
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Creative.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &ecreative&b mode"}));
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Adventure.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &eadventure&b mode"}));
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Spectator.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"&b%player%'s gamemode was set in &espectator&b mode"}));
		
			ConfigMCommands.saveConfigFile();
		}
		
		if (!OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.coffee.Enable")) {
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Enable", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Use_Permission", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Title", String.valueOf("&cCoffee"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Material", String.valueOf("MILK_BUCKET"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : ☕",
	                "&bUse :swords: in the chat",
	                " "
	            }));
	        
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Enable", Boolean.valueOf(true));
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Use_Permission", Boolean.valueOf(true));
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Title", String.valueOf("&cAirplane"));
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Material", String.valueOf("FEATHER"));
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : ✈",
	                "&bUse :swords: in the chat",
	                " "
	            }));
	        
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Aliases", java.util.Arrays.asList(new String[] {
                    "<3"
                }));
	        
	        OnChatConfig.saveConfigFile();
		}
		
		if (!WarpSetWarpCommandConfig.getConfig().isSet("DelWarp.Enable")) {
			WarpSetWarpCommandConfig.getConfig().set("DelWarp.Enable", Boolean.valueOf(true));
			WarpSetWarpCommandConfig.getConfig().set("DelWarp.Disable-Message", Boolean.valueOf(true));
	        
	        WarpSetWarpCommandConfig.saveConfigFile();
		}
		
		// Between servers
		if (!BetweenServersConfig.getConfig().isSet("TP.Last-Position-On-Join.Enable")) {
			BetweenServersConfig.getConfig().set("TP.Last-Position-On-Join.Enable", Boolean.valueOf(false));
			
			BetweenServersConfig.saveConfigFile();
		}
		
		// General
		if (!ConfigGeneral.getConfig().isSet("Plugin.Use.MYSQL.Enable")) {
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Enable", Boolean.valueOf(true));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Host", String.valueOf("localhost"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Username", String.valueOf("root"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Password", String.valueOf("123"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Database", String.valueOf("Hawn"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Port", Integer.valueOf(3306));
			
			ConfigGeneral.saveConfigFile();
		}
		
		// Server list ping
		if (!ServerListConfig.getConfig().isSet("Motd.Classic.Enable")) {
			ServerListConfig.getConfig().set("Motd.Classic.Enable", Boolean.valueOf(true));
			ServerListConfig.getConfig().set("Motd.Classic.Line-1", String.valueOf("&cThis is a test of motd of course"));
			ServerListConfig.getConfig().set("Motd.Classic.Line-2", String.valueOf("&eThanks to choose &lhawn"));
            
			ServerListConfig.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Slots.Fake-Max-Player.Enable")) {
			ServerListConfig.getConfig().set("Slots.Fake-Max-Player.Enable", Boolean.valueOf(false));
			ServerListConfig.getConfig().set("Slots.Fake-Max-Player.Number", Integer.valueOf(2000));
		
			ServerListConfig.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Motd.WhiteList.Enable")) {
			ServerListConfig.getConfig().set("Motd.WhiteList.Enable", Boolean.valueOf(true));
			ServerListConfig.getConfig().set("Motd.WhiteList.Line-1", String.valueOf("&eServer is on whitelist"));
			ServerListConfig.getConfig().set("Motd.WhiteList.Line-2", String.valueOf("&bPlease come back later"));
            
			ServerListConfig.saveConfigFile();
		}
		
		// Config launch pad
		if (!ConfigGLP.getConfig().isSet("JumpPads.Options.Block")) {
			ConfigGLP.getConfig().set("JumpPads.Options.Block", String.valueOf("REDSTONE_BLOCK"));
			ConfigGLP.getConfig().set("JumpPads.Options.Plate", String.valueOf("GOLD_PLATE"));
            
			ConfigGLP.saveConfigFile();
		}
		
		// On chat events
		if (!OnChatConfig.getConfig().isSet("Anti-Swear.Enable")) {
			OnChatConfig.getConfig().set("Anti-Swear.Enable", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Anti-Swear.Bypass", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Anti-Swear.Replace-Message.Enable", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Anti-Swear.Replace-Message.Message", java.util.Arrays.asList(new String[] {"*****"}));
            OnChatConfig.getConfig().set("Anti-Swear.Notify-Staff", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Anti-Swear.List", java.util.Arrays.asList(new String[] {
                    "fuck",
                    "fuck you",
                    "shut up",
                    "shit"
                }));
            
            OnChatConfig.saveConfigFile();
		}
		
		if (!OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Enable")) {
            
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title", String.valueOf("&cClose the Gui"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material", String.valueOf("BARRIER"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "You can simply delete too",
                    "If you don't want lore",
                    " "
                }));
            
            // Option per emoji
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Title", String.valueOf("&cSmiley"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Material", String.valueOf("SKULL_ITEM"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Skull-Name", String.valueOf("%player%"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☺",
                    "&bUse :smiley: in the chat",
                    " "
                }));


                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Title", String.valueOf("&cSwords"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Material", String.valueOf("DIAMOND_SWORD"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚔",
                    "&bUse :swords: in the chat",
                    " "
                }));
            
                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Title", String.valueOf("&cHeart"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Material", String.valueOf("REDSTONE"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ❤",
                    "&bUse :heart: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Title", String.valueOf("&cNotes"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Material", String.valueOf("NOTE_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♪",
                    "&bUse :notes: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Title", String.valueOf("&cStar"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Material", String.valueOf("NETHER_STAR"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✰",
                    "&bUse :star: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Title", String.valueOf("&cPeace"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Material", String.valueOf("ORANGE_TULIP"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☮",
                    "&bUse :peace: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Title", String.valueOf("&cChess"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Material", String.valueOf("TORCH"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♖",
                    "&bUse :chess: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Title", String.valueOf("&cCopyright"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Material", String.valueOf("PAINTING"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ©",
                    "&bUse :chess: in the chat",
                    " "
                }));

            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Title", String.valueOf("&cAnchor"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Material", String.valueOf("IRON_ORE"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚓",
                    "&bUse :anchor: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Title", String.valueOf("&cSkull"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Material", String.valueOf("SKULL_ITEM"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☠",
                    "&bUse :skull: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Title", String.valueOf("&cUmbrella"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Material", String.valueOf("GREEN_CARPET"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☂",
                    "&bUse :umbrella: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Title", String.valueOf("&cDiamonds"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Material", String.valueOf("DIAMOND"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♦",
                    "&bUse :diamonds: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Title", String.valueOf("&cSnowflake"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Material", String.valueOf("SNOWBALL"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ❄",
                    "&bUse :snowflake: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Title", String.valueOf("&cSnowman"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Material", String.valueOf("SNOW_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☃",
                    "&bUse :snowman: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Title", String.valueOf("&cCheckmark"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Material", String.valueOf("EMERALD_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✔",
                    "&bUse :checkmark: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Title", String.valueOf("&cCrossmark"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Material", String.valueOf("REDSTONE_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✖",
                    "&bUse :crossmark: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Enable", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Use_Permission", Boolean.valueOf(true));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Title", String.valueOf("&cArrow"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Material", String.valueOf("ARROW"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : Σ>―(´･ω･`)→",
                    "&bUse :arrow: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Enable", Boolean.valueOf(true));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Use_Permission", Boolean.valueOf(true));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Title", String.valueOf("&cStrong"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Material", String.valueOf("SPLASH_POTION"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : (9｀･ω･)9",
	                    "&bUse :strong: in the chat",
	                    " "
	                }));
	
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Enable", Boolean.valueOf(true));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Use_Permission", Boolean.valueOf(true));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Title", String.valueOf("&cPushups"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Material", String.valueOf("SPONGE"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ┌(◣﹏◢)┐",
	                    "&bUse :pushups: in the chat",
	                    " "
	                }));
	
	            OnChatConfig.saveConfigFile();
        }
		
		if (!OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.shrug.Enable")) {
			
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Enable", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Use_Permission", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Title", String.valueOf("&cShrug"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Material", String.valueOf("STRING"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : ¯\\_(ツ)_/¯",
	                "&bUse :shrug: in the chat",
	                " "
	            }));
	        
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Enable", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Use_Permission", Boolean.valueOf(true));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Title", String.valueOf("&cFliptable"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Material", String.valueOf("OAK_WOOD"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : (╯°□°）╯︵ ┻━┻",
	                "&bUse :fliptable: in the chat",
	                " "
	            }));
	        
	        OnChatConfig.saveConfigFile();
		}
		
		// On join Events
		
		if (!OnJoinConfig.getConfig().isSet("Action-Bar.Enable")) {
			
			OnJoinConfig.getConfig().set("Action-Bar.Enable", Boolean.valueOf(true));
			
			OnJoinConfig.getConfig().set("Action-Bar.First-Join.Enable", Boolean.valueOf(true));
			OnJoinConfig.getConfig().set("Action-Bar.First-Join.Message", "&6Welcome %player%"); 
            
			OnJoinConfig.getConfig().set("Action-Bar.First-Join.World.All_World", Boolean.valueOf(false));
            OnJoinConfig.getConfig().set("Action-Bar.First-Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));

            OnJoinConfig.getConfig().set("Action-Bar.Join.Enable", Boolean.valueOf(true));
            OnJoinConfig.getConfig().set("Action-Bar.Join.Message", "&6Welcome %player%"); 
            
            OnJoinConfig.getConfig().set("Action-Bar.Join.World.All_World", Boolean.valueOf(false));
            OnJoinConfig.getConfig().set("Action-Bar.Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            OnJoinConfig.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Speed.Enable")) {
			OnJoinConfig.getConfig().set("Speed.Enable", Boolean.valueOf(true));
			OnJoinConfig.getConfig().set("Speed.Value", Integer.valueOf(2));
			OnJoinConfig.getConfig().set("Speed.World.All_World", Boolean.valueOf(false));
			OnJoinConfig.getConfig().set("Speed.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            OnJoinConfig.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Potion-Effect.BLINDNESS.Enable")) {
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Enable", Boolean.valueOf(true));
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Duration-Second", Integer.valueOf(2));
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Amplifier", Integer.valueOf(2));
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.World.All_World", Boolean.valueOf(false));
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	        }));
	        
			OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Enable", Boolean.valueOf(true));
			OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Duration-Second", Integer.valueOf(2));
	        OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Amplifier", Integer.valueOf(2));
	        OnJoinConfig.getConfig().set("Potion-Effect.JUMP.World.All_World", Boolean.valueOf(false));
	        OnJoinConfig.getConfig().set("Potion-Effect.JUMP.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	        }));
	        
	        OnJoinConfig.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Potion-Effect.BLINDNESS.Use_Permission")) {
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Use_Permission", Boolean.valueOf(true));
			OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Use_Permission", Boolean.valueOf(true));
	        
	        OnJoinConfig.saveConfigFile();
		}
		
		
		// Commands Messages
		
		if (!ConfigMGeneral.getConfig().isSet("General.On-join.Join-Message.Per-Group.Options.Enable")) {
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Options.Enable", Boolean.valueOf(true));
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Options.Disable-Any-Messages-On-Join", Boolean.valueOf(true));
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
                    "&cPLEASE WELCOME a owner"}));
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
                    "unlimited groups of course"}));
            
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Options.Enable", Boolean.valueOf(true));
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit", Boolean.valueOf(true));
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
            		"&cPLEASE SAY GOODBYE a owner"}));
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
            		"unlimited groups of course"}));
            
            ConfigMGeneral.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Heal.Self.Enable")) {
			ConfigMCommands.getConfig().set("Heal.Self.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"&bYou have been healed"}));
			ConfigMCommands.getConfig().set("Heal.Other.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"&bYou have been healed by %player%"}));
			ConfigMCommands.getConfig().set("Heal.Other-Sender.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&b%target% has been healed"}));
			
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Tp.Self.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Tp.Self.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"&bYou have been teleported to the warp %warp%"}));
			ConfigMCommands.getConfig().set("Warp.Tp.Other.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"&bYou have been teleported to the warp %warp% by %player%"}));
	        ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender.Enable", Boolean.valueOf(true));
	        ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&bYou have been teleported %target% to the warp %warp%"}));
	        ConfigMCommands.getConfig().set("Warp.List.Enable", Boolean.valueOf(true));
	        ConfigMCommands.getConfig().set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"&bWarpList :&e %warplist%"}));
	        ConfigMCommands.getConfig().set("Warp.No-Warp.Enable", Boolean.valueOf(true));
	        ConfigMCommands.getConfig().set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"&cNo warp set"}));
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Set.Enable", Boolean.valueOf(true));
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"&eWarp set on behalf of %arg%"}));
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Already-Exist.Enable", Boolean.valueOf(true));
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"&cThe name already exist"}));
		
	        ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Vanish.Self.Enable")) {
			ConfigMCommands.getConfig().set("Vanish.Self.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"&bVanish has been enabled"}));
			ConfigMCommands.getConfig().set("Vanish.Self-Disabled.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cVanish has been disabled"}));
			/*ConfigMCommands.getConfig().set("Vanish.Self-Still.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bVanish is still enabled"}));*/
        
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Vanish.Other-Target.Enable")) {
			ConfigMCommands.getConfig().set("Vanish.Other-Target.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"&bVanish has been enabled by %player%"}));
			ConfigMCommands.getConfig().set("Vanish.Other-Target-Disabled.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cVanish has been disabled by %player%"}));
			ConfigMCommands.getConfig().set("Vanish.Other-Sender.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&bVanish has been enabled by %player% for %target%"}));
			ConfigMCommands.getConfig().set("Vanish.Other-Sender-Disabled.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cVanish has been disabled by %player% for %target%"}));
        
        	ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("ClearInv.Self.Enable")) {
			ConfigMCommands.getConfig().set("ClearInv.Self.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"&bYour inventory has been cleaned up"}));
			ConfigMCommands.getConfig().set("ClearInv.Other-Target.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"&bYour inventory has been cleaned up by %player%"}));
			ConfigMCommands.getConfig().set("ClearInv.Other-Sender.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"&bThe %target% inventory has been cleaned up"}));
        
        ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Tp.Self-Delay.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Tp.Self-Delay.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before be tp."}));
            ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender-Delay.Enable", Boolean.valueOf(true));
            ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before the player is tp."}));
           
            ConfigMCommands.saveConfigFile();
		}
        
		if (!ConfigMCommands.getConfig().isSet("Spawn.Tp.Self-Delay.Enable")) {
			ConfigMCommands.getConfig().set("Spawn.Tp.Self-Delay.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before be tp."}));
            ConfigMCommands.getConfig().set("Spawn.Tp.Other-Sender-Delay.Enable", Boolean.valueOf(true));
            ConfigMCommands.getConfig().set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"&bWait %second% seconds before the player is tp."}));
           
            ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Del.Warp-Doesnt-Exist.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Doesnt-Exist.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"&cThe warp doesn't exist"}));
			
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Del.Warp-Delete.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Delete.Enable", Boolean.valueOf(true));
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"&bThe warp &e%warp%&b has been deleted"}));
			
			ConfigMCommands.saveConfigFile();
		}
		// Events messages
		
		if (!ConfigMEvents.getConfig().isSet("Anti-Swear.Notify-Staff")) {
			ConfigMEvents.getConfig().set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&e[Anti-Swear] &c%player% &esaid &6%message%"}));
		
			ConfigMEvents.saveConfigFile();
		}
		
		// Lol warp and spawn
		if (!WarpSetWarpCommandConfig.getConfig().isSet("Warp.Delay.Self.Enable")) {
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Self.Enable", Boolean.valueOf(true));
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Self.Delay-Seconds", Integer.valueOf(5));
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Self.Bypass-Delay", Boolean.valueOf(false));
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Other.Enable", Boolean.valueOf(true));
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Other.Delay-Seconds", Integer.valueOf(5));
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Other.Bypass-Delay", Boolean.valueOf(false));
            
            WarpSetWarpCommandConfig.saveConfigFile();
		}
		
		if (!SpawnCommandConfig.getConfig().isSet("Commands.Spawn.Delay.Self.Enable")) {
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Self.Enable", Boolean.valueOf(true));
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Self.Delay-Seconds", Integer.valueOf(5));
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Self.Bypass-Delay", Boolean.valueOf(false));
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Other.Enable", Boolean.valueOf(true));
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Other.Delay-Seconds", Integer.valueOf(5));
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Other.Bypass-Delay", Boolean.valueOf(false));
            
			SpawnCommandConfig.saveConfigFile();
		}
		
		if (!ConfigMOStuff.getConfig().isSet("Commands.Spawn.Delay.Self.Enable")) {
			ConfigMOStuff.getConfig().set("Spawn.Del.Spawn-Delete.Enable", Boolean.valueOf(true));
			ConfigMOStuff.getConfig().set("Spawn.Del.Spawn-Delete.Messages", java.util.Arrays.asList(new String[] {"&bThe spawn &e%spawn%&b has been deleted"}));
		
			ConfigMOStuff.saveConfigFile();
		}
		
		if (!PlayerWorldChangeConfigE.getConfig().isSet("GM.Enable")) {
			PlayerWorldChangeConfigE.getConfig().set("GM.Enable", Boolean.valueOf(true));
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.Enable", Boolean.valueOf(true));
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.GameMode", Integer.valueOf(1));
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Survival", Boolean.valueOf(true));
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Creative", Boolean.valueOf(true));
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Adventure", Boolean.valueOf(true));
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Spectator", Boolean.valueOf(true));
			PlayerWorldChangeConfigE.getConfig().set("GM.World.All_World", Boolean.valueOf(false));
			PlayerWorldChangeConfigE.getConfig().set("GM.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	            }));
			
			PlayerWorldChangeConfigE.saveConfigFile();
		}
		
		if(!BetweenServersConfig.getConfig().isSet("Keep.Vanish-On-Join.Enable")) {
			BetweenServersConfig.getConfig().set("Keep.Vanish-On-Join.Enable", Boolean.valueOf(true));
			
			BetweenServersConfig.saveConfigFile();
		}
		
	}

}
