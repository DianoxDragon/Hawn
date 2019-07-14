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
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGLP;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGProtection;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerWorldChangeConfigE;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMEvents;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMPlayerOption;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.OtherAMConfig;

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
		
		if (!VanishCommandConfig.getConfig().isSet("Vanish.Action-Bar-If-Vanished")) {
			VanishCommandConfig.getConfig().set("Vanish.Action-Bar-If-Vanished", true);
			ConfigMCommands.getConfig().set("Vanish.Action-Bar", java.util.Arrays.asList(new String[] {"&aYou are vanished"}));
			
			VanishCommandConfig.saveConfigFile();
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMGeneral.getConfig().isSet("General.Prefix")) {
			ConfigMGeneral.getConfig().set("General.Prefix", " &3Hawn &7|");
			
			ConfigMGeneral.saveConfigFile();
		}
		
		if (!ConfigGeneral.getConfig().isSet("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
			ConfigGeneral.getConfig().set("Plugin.Use.MVdWPlaceholderAPI.Enable", false);
			ConfigGeneral.getConfig().set("Plugin.Use.MVdWPlaceholderAPI.Keep-The-Option", false);
            
			ConfigGeneral.saveConfigFile();
		}
		
		if (!OnChatConfig.getConfig().isSet("PlayerOption.JumpBoost.Enable.Enable")) {
			
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Enable", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Use_Permission", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Title", String.valueOf("&cSad"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Material", String.valueOf("PAPER"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ☹",
	                    "&bUse :sad: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Title", String.valueOf("&cScales"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Material", String.valueOf("OAK_PRESSURE_PLATE"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ⚖",
	                    "&bUse :scales: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Title", String.valueOf("&cRadioactive"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Material", String.valueOf("REDSTONE_TORCH"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ☢",
	                    "&bUse :radioactive: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Gui.Title", String.valueOf("&cKing"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Gui.Material", String.valueOf("GOLD_INGOT"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ♕",
	                    "&bUse :king: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Gui.Title", String.valueOf("&cEmail"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Gui.Material", String.valueOf("PAPER"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ✉",
	                    "&bUse :email: in the chat",
	                    " "
	                }));
			
			OnChatConfig.saveConfigFile();
		}
		
		if (!ConfigMPlayerOption.getConfig().isSet("PlayerOption.JumpBoost.Enable.Enable")) {
			ConfigMPlayerOption.getConfig().set("PlayerOption.JumpBoost.Enable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your jumpboost has been &aactivated"}));
			ConfigMPlayerOption.getConfig().set("PlayerOption.JumpBoost.Disable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your jumpboost has been &cdisabled"}));
		
			OptionPlayerConfigCommand.getConfig().set("PlayerOption.Option.Jumpboost.Value", 2);
			
			OptionPlayerConfigCommand.saveConfigFile();
			ConfigMPlayerOption.saveConfigFile();
		}
        
		if (!ConfigGProtection.getConfig().isSet("Protection.Construct.Anti-Place.WorldGuard.Enable")) {
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.WorldGuard.Enable", false);
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.WorldGuard.Method", "BLACKLIST");
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Break.WorldGuard.Enable", false);
            ConfigGProtection.getConfig().set("Protection.Construct.Anti-Break.WorldGuard.Method", "WHITELIST");
            ConfigGProtection.getConfig().set("Protection.Construct.Anti-Break.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            ConfigGProtection.getConfig().set("Protection.HagingBreakByEntity.WorldGuard.Enable", false);
            ConfigGProtection.getConfig().set("Protection.HagingBreakByEntity.WorldGuard.Method", "WHITELIST");
            ConfigGProtection.getConfig().set("Protection.HagingBreakByEntity.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            ConfigGProtection.getConfig().set("Protection.PlayerInteractEntity-ItemFrame.WorldGuard.Enable", false);
            ConfigGProtection.getConfig().set("Protection.PlayerInteractEntity-ItemFrame.WorldGuard.Method", "WHITELIST");
            ConfigGProtection.getConfig().set("Protection.PlayerInteractEntity-ItemFrame.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            
            ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigGeneral.getConfig().isSet("Plugin.Use.WorldGuard.Enable")) {
			ConfigGeneral.getConfig().set("Plugin.Use.WorldGuard.Enable", false);
			ConfigGeneral.getConfig().set("Plugin.Use.WorldGuard.Keep-The-Option", false);
        	
			ConfigGeneral.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Action-Bar.First-Join.Time-Stay")) {
			OnJoinConfig.getConfig().set("Action-Bar.First-Join.Time-Stay", 150); 
			OnJoinConfig.getConfig().set("Action-Bar.Join.Time-Stay", 150); 
			
			OnJoinConfig.saveConfigFile();
		}
		
		
		if (!OtherAMConfig.getConfig().isSet("Command.Hawn-Main-help.1")) {
			OtherAMConfig.getConfig().set("Command.Hawn-Main-help.1", java.util.Arrays.asList(new String[] {"§8>> §7/hawn setspawn [name] - §eSet the spawn", 
		            "§8>> §7/hawn reload §eor §7rl - §eReload some config files",
		            "§8>> §7/hawn version §eor §7v  - §eSee the version of the plugin",
		            "§8>> §7/hawn tps - §eSee the TPS of the server",
		            "§8>> §7/hawn info [all/memory/tps/disk/cpu/server/version] - §eSee the infos of the server",
		            "§8>> §7/hawn debug emoji - §eDebug config files of emoji's items",
		            "§8>> §7/hawn build - §eTo bypass protection temporary",
		            "",
		            "§8>> §7/ap §eor §7pa - §eAccess to the admin panel"}));

			OtherAMConfig.getConfig().set("Command.Hawn-Main-help.2", java.util.Arrays.asList(new String[] {"", 
		            "§8>> §7/spawn [SpawnName] - §eGo to the spawn",
		            "§8>> §7/spawn tp <player> [SpawnName] - §eTp a player to a spawn",
		            "",
		            "§8>> §7/sun or /clearw - §eClear the weather",
		            "§8>> §7/rain - §eTo make the world raining",
		            "§8>> §7/thunder - §eIf you like a bad weather",
		            "§8>> §7/day - §ePut the day",
		            "§8>> §7/night - §ePut the night",
		            ""}));

			OtherAMConfig.getConfig().set("Command.Hawn-Main-help.3", java.util.Arrays.asList(new String[] {"§8>> §7/fly [player] - §eSet the fly mode", 
		            "§8>> §7/heal [player] - §eHeal a player",
		            "§8>> §7/clearinv [player] - §eClear inventory a player",
		            "§8>> §7/ping [player] - §eShow the ping of a player",
		            "§8>> §7/v [player] - §eVanish a player",
		            "§8>> §7/gamemode or gm... or gma etc. - §eSet to player a gamemode",
		            "",
		            "§8>> §7/cc - §eShow the help of the clearchat",
		            "§8>> §7/delaychat <number> - §ePut a delay on the chat"}));

			OtherAMConfig.getConfig().set("Command.Hawn-Main-help.4", java.util.Arrays.asList(new String[] {"§8>> §7/gmute - §eMute the chat", 
		            "",
		            "§8>> §7/broadcast <message>§7 - §eBroadcast a message",
		            "§8>> §7/btcast §eor §7/ta <message>§7 - §eBroadcast a title message",
		            "",
		            "§8>> §7/help <.../...>§7 - §eShow the custom help, if enabled",
		            "",
		            "§8>> §7/emoji§7 - §eSee the gui of emojis",
		            "",
		            "§8>> §7/scoreboard§7 - §eToggle off or on the scoreboard"}));

			OtherAMConfig.getConfig().set("Command.Hawn-Main-help.5", java.util.Arrays.asList(new String[] {"§8>> §7/scoreboard set <scoreboard's file name>§7 - §eTo change the actual scoreboard", 
		            "§8>> §7/scoreboard keep§7 - §eKeep the scoreboard between servers",
		            "",
		            "§8>> §7/option§7 - §eFor main player's options"}));
		
			OtherAMConfig.saveConfigFile();
		}
		if (!ConfigMGeneral.getConfig().isSet("General.On-join.Join-Message.Just-Simply-Disable-All-Join-Messages")) {
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Just-Simply-Disable-All-Join-Messages", false);
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Just-Simply-Disable-All-Quit-Messages", false);
			
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-World.Options.Enable", false);
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-World.Options.Disable-Any-Other-Messages-On-Join", false);
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World", false);
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
				"&ctest1"}));
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
				"test2"}));
			
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-World.Options.Enable", false);
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-World.Options.Disable-Any-Other-Messages-On-Quit", false);
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World", false);
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
                    "&ctest1"}));
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
                    "test2"}));
			
			ConfigMGeneral.getConfig().set("Spawn.On-join.Per-World.Options.Enable", false);
			ConfigMGeneral.getConfig().set("Spawn.On-join.Per-World.Options.Disable-All-The-Others-Motd", false);
			ConfigMGeneral.getConfig().set("Spawn.On-join.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
				"&ctest1 - motd"}));
			ConfigMGeneral.getConfig().set("Spawn.On-join.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
				"test2 - motd"}));
	
			OtherAMConfig.getConfig().set("Command.Build-Bypass.On", java.util.Arrays.asList(new String[] {
	            		"&bYou can now bypass all the build restriction"
	            		}));
			OtherAMConfig.getConfig().set("Command.Build-Bypass.Off", java.util.Arrays.asList(new String[] {
	            		"&cYou can no longer bypass all the build restriction"
	            		}));
			
			OtherAMConfig.saveConfigFile();
			ConfigMGeneral.saveConfigFile();
		}
		
		if (!ConfigMPlayerOption.getConfig().isSet("PlayerOption.Error.Player-Visibility.Time.Enable")) {
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%timedelaypvcji% &c&lsecond(s)&7!"}));
		
			ConfigMPlayerOption.saveConfigFile();
		}
		
		if (!ConfigGCos.getConfig().isSet("Cosmetics.Firework.Options.First-Join-Only")) {
			ConfigGCos.getConfig().set("Cosmetics.Firework.Options.First-Join-Only", false);
			
			ConfigGCos.saveConfigFile();
		}
		
		if (!BetweenServersConfig.getConfig().isSet("Keep.PlayerVisibility-OnJoin.Enable")) {
			BetweenServersConfig.getConfig().set("Keep.PlayerVisibility-OnJoin.Enable", false);
			BetweenServersConfig.getConfig().set("Keep.Speed-OnJoin.Enable", false);
			
			SpecialCjiHidePlayers.getConfig().set("PV.Option.OnJoin-Priority-For-Player-Option", true);
			
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Use-SSL", false);
			ConfigGeneral.getConfig().set("Plugin.12-Hours-Or-24-Hours-Format", 24);
			
			ConfigMCommands.getConfig().set("Scoreboard.Changes.Enable", true);
			ConfigMCommands.getConfig().set("Scoreboard.Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your actual scoreboard has been set to &e%arg1%"}));
            ConfigMCommands.getConfig().set("Scoreboard.Error-Changes.Enable", true);
            ConfigMCommands.getConfig().set("Scoreboard.Error-Changes.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe scoreboard &6%arg1%&c doesn't exist"}));
			
            ConfigMCommands.getConfig().set("Scoreboard.Keep-On.Enable", true);
            ConfigMCommands.getConfig().set("Scoreboard.Keep-On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You will now keep this scoreboard"}));
            ConfigMCommands.getConfig().set("Scoreboard.Keep-Off.Enable", true);
            ConfigMCommands.getConfig().set("Scoreboard.Keep-Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You will &cno longer&7 keep this scoreboard"}));
            
            ScoreboardCommandConfig.getConfig().set("Scoreboard.Option.Keep-Scoreboard-Change", true);
            
            
            ScoreboardCommandConfig.saveConfigFile();
            ConfigMCommands.saveConfigFile();
			SpecialCjiHidePlayers.saveConfigFile();
			BetweenServersConfig.saveConfigFile();
			ConfigGeneral.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Speed.Option.Priority-For-Player-Option")) {
			OnJoinConfig.getConfig().set("Speed.Option.Priority-For-Player-Option", true);
			
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Enable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your speed has been &aactivated"}));
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Disable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your speed has been &cdisabled"}));
            
			ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Set.Enable", true);
            ConfigMPlayerOption.getConfig().set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your speed has been set to &e%arg1%"}));
            
            ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Option-Disabled.Enable", true);
            ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou disabled this option, so, you can't manage all the option"}));
            
            ConfigMPlayerOption.saveConfigFile();
			
			OnJoinConfig.saveConfigFile();
		}
		
		/*if (!BetweenServersConfig.getConfig().isSet("Keep.PlayerVisibility-OnJoin.Enable")) {
			BetweenServersConfig.getConfig().set("Keep.PlayerVisibility-OnJoin.Enable", false);
			BetweenServersConfig.getConfig().set("Keep.Speed-OnJoin.Enable", false);
			BetweenServersConfig.getConfig().set("Keep.JumpBoost-OnJoin.Enable", false);
			BetweenServersConfig.getConfig().set("Keep.Fly-Double-Jump-OnJoin.Enable", false);
	        
	        BetweenServersConfig.saveConfigFile();
		}*/
		
		if (!BroadCastCommandConfig.getConfig().isSet("DISABLE_THE_COMMAND_COMPLETELY")) {
			BroadCastCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			ClearChatCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			ClearInvCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			DelayChatCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			EmojiCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			FlyCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			
			GamemodeCommandConfig.getConfig().set("Gamemode.DISABLE_THE_COMMAND_COMPLETELY", false);
			GamemodeCommandConfig.getConfig().set("gms.DISABLE_THE_COMMAND_COMPLETELY", false);
			GamemodeCommandConfig.getConfig().set("gmc.DISABLE_THE_COMMAND_COMPLETELY", false);
			GamemodeCommandConfig.getConfig().set("gma.DISABLE_THE_COMMAND_COMPLETELY", false);
			GamemodeCommandConfig.getConfig().set("gmsp.DISABLE_THE_COMMAND_COMPLETELY", false);
			
			HealCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			HelpCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			MuteChatCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			OptionPlayerConfigCommand.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			PingCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			ScoreboardCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			SpawnCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			TitleAnnouncerConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			VanishCommandConfig.getConfig().set("DISABLE_THE_COMMAND_COMPLETELY", false);
			
			WarpSetWarpCommandConfig.getConfig().set("Warp.DISABLE_THE_COMMAND_COMPLETELY", false);
			WarpSetWarpCommandConfig.getConfig().set("WarpList.DISABLE_THE_COMMAND_COMPLETELY", false);
			WarpSetWarpCommandConfig.getConfig().set("SetWarp.DISABLE_THE_COMMAND_COMPLETELY", false);
			WarpSetWarpCommandConfig.getConfig().set("DelWarp.DISABLE_THE_COMMAND_COMPLETELY", false);
			
			WeatherTimeCommandConfig.getConfig().set("Weather.Set.Sun.DISABLE_THE_COMMAND_COMPLETELY", false);
			WeatherTimeCommandConfig.getConfig().set("Weather.Set.Rain.DISABLE_THE_COMMAND_COMPLETELY", false);
			WeatherTimeCommandConfig.getConfig().set("Weather.Set.Thunder.DISABLE_THE_COMMAND_COMPLETELY", false);
			WeatherTimeCommandConfig.getConfig().set("Time.Set.Day.DISABLE_THE_COMMAND_COMPLETELY", false);
			WeatherTimeCommandConfig.getConfig().set("Time.Set.Night.DISABLE_THE_COMMAND_COMPLETELY", false);
			
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
			ConfigMPlayerOption.getConfig().set("PlayerOption.Help.Enable", true);
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
			ConfigMOStuff.getConfig().set("Error.Command-Disable.Enable", true);
			ConfigMOStuff.getConfig().set("Error.Argument-Missing.Enable", true);
			ConfigMOStuff.getConfig().set("Error.Not-A-Player.Enable", true);
			ConfigMOStuff.getConfig().set("Error.Not-A-Player.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou are not a player"}));
		
			ConfigMOStuff.saveConfigFile();
		}
		
		if (!ConfigMOStuff.getConfig().isSet("Error.Change-Me.Enable")) {
			ConfigMOStuff.getConfig().set("Error.Change-Me.Enable", true);
			ConfigMOStuff.getConfig().set("Error.Change-Me.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou have to change the spawn/warp/etc. on &6%arg1%&c on &e%arg2%"}));
		
			ConfigMOStuff.saveConfigFile();
		}
		
		if (!WarpSetWarpCommandConfig.getConfig().isSet("Warp.Delay.Cancel-Tp-On.Any-movements")) {
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Cancel-Tp-On.Any-movements", true);
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Cancel-Tp-On.On-Damages", true);
            
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Cancel-Tp-On.Any-movements", true);
            SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Cancel-Tp-On.On-Damages", true);
            
            ConfigMEvents.getConfig().set("Cancel-Tp.Warp.Enable", true);
            ConfigMEvents.getConfig().set("Cancel-Tp.Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tp &ccancelled"}));
            ConfigMEvents.getConfig().set("Cancel-Tp.Spawn.Enable", true);
            ConfigMEvents.getConfig().set("Cancel-Tp.Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Tp &ccancelled"}));
            
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.On.Enable", true);
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.On.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The scoreboard has been &aenabled"}));
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.Off.Enable", true);
            ConfigMCommands.getConfig().set("Scoreboard.Toggle.Off.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The scoreboard has been &cdisabled"}));
            
            WarpSetWarpCommandConfig.saveConfigFile();
            SpawnCommandConfig.saveConfigFile();
            ConfigMEvents.saveConfigFile();
            ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Gamemode.Self.Survival.Enable")) {
			/* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
			ConfigMCommands.getConfig().set("Gamemode.Self.Survival.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Self.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &esurvival&7 mode"}));
            ConfigMCommands.getConfig().set("Gamemode.Self.Creative.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Self.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &ecreative&7 mode"}));
            ConfigMCommands.getConfig().set("Gamemode.Self.Adventure.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Self.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &eadventure&7 mode"}));
            ConfigMCommands.getConfig().set("Gamemode.Self.Spectator.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Self.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &espectator&7 mode"}));
            
            ConfigMCommands.getConfig().set("Gamemode.Other.Survival.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &esurvival&7 mode by &b%player%"}));
            ConfigMCommands.getConfig().set("Gamemode.Other.Creative.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &ecreative&7 mode by &b%player%"}));
            ConfigMCommands.getConfig().set("Gamemode.Other.Adventure.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &eadventure&7 mode by &b%player%"}));
            ConfigMCommands.getConfig().set("Gamemode.Other.Spectator.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your gamemode has been set in &espectator&7 mode by &b%player%"}));
            
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Survival.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Survival.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &esurvival&7 mode"}));
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Creative.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Creative.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &ecreative&7 mode"}));
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Adventure.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Adventure.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &eadventure&7 mode"}));
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Spectator.Enable", true);
            ConfigMCommands.getConfig().set("Gamemode.Other-Sender.Spectator.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%player%&7's gamemode has been set in &espectator&7 mode"}));
		
			ConfigMCommands.saveConfigFile();
		}
		
		if (!OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.coffee.Enable")) {
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Enable", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Use_Permission", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Title", String.valueOf("&cCoffee"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Material", String.valueOf("MILK_BUCKET"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : ☕",
	                "&bUse :swords: in the chat",
	                " "
	            }));
	        
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Enable", true);
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Use_Permission", true);
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
			WarpSetWarpCommandConfig.getConfig().set("DelWarp.Enable", true);
			WarpSetWarpCommandConfig.getConfig().set("DelWarp.Disable-Message", true);
	        
	        WarpSetWarpCommandConfig.saveConfigFile();
		}
		
		// Between servers
		if (!BetweenServersConfig.getConfig().isSet("TP.Last-Position-On-Join.Enable")) {
			BetweenServersConfig.getConfig().set("TP.Last-Position-On-Join.Enable", false);
			
			BetweenServersConfig.saveConfigFile();
		}
		
		// General
		if (!ConfigGeneral.getConfig().isSet("Plugin.Use.MYSQL.Enable")) {
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Enable", true);
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Host", String.valueOf("localhost"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Username", String.valueOf("root"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Password", String.valueOf("123"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Database", String.valueOf("Hawn"));
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Port", 3306);
			
			ConfigGeneral.saveConfigFile();
		}
		
		// Server list ping
		if (!ServerListConfig.getConfig().isSet("Motd.Classic.Enable")) {
			ServerListConfig.getConfig().set("Motd.Classic.Enable", true);
			ServerListConfig.getConfig().set("Motd.Classic.Line-1", String.valueOf("&cThis is a test of motd of course"));
			ServerListConfig.getConfig().set("Motd.Classic.Line-2", String.valueOf("&eThanks to choose &lhawn"));
            
			ServerListConfig.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Slots.Fake-Max-Player.Enable")) {
			ServerListConfig.getConfig().set("Slots.Fake-Max-Player.Enable", false);
			ServerListConfig.getConfig().set("Slots.Fake-Max-Player.Number", 2000);
		
			ServerListConfig.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Motd.WhiteList.Enable")) {
			ServerListConfig.getConfig().set("Motd.WhiteList.Enable", true);
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
			OnChatConfig.getConfig().set("Anti-Swear.Enable", true);
			OnChatConfig.getConfig().set("Anti-Swear.Bypass", true);
			OnChatConfig.getConfig().set("Anti-Swear.Replace-Message.Enable", true);
			OnChatConfig.getConfig().set("Anti-Swear.Replace-Message.Message", java.util.Arrays.asList(new String[] {"*****"}));
            OnChatConfig.getConfig().set("Anti-Swear.Notify-Staff", true);
            OnChatConfig.getConfig().set("Anti-Swear.List", java.util.Arrays.asList(new String[] {
                    "fuck",
                    "fuck you",
                    "shut up",
                    "shit"
                }));
            
            OnChatConfig.saveConfigFile();
		}
		
		if (!OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Enable")) {
            
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title", String.valueOf("&cClose the Gui"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material", String.valueOf("BARRIER"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "You can simply delete too",
                    "If you don't want lore",
                    " "
                }));
            
            // Option per emoji
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Title", String.valueOf("&cSmiley"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Material", String.valueOf("SKULL_ITEM"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Skull-Name", String.valueOf("%player%"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☺",
                    "&bUse :smiley: in the chat",
                    " "
                }));


                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Title", String.valueOf("&cSwords"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Material", String.valueOf("DIAMOND_SWORD"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚔",
                    "&bUse :swords: in the chat",
                    " "
                }));
            
                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Title", String.valueOf("&cHeart"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Material", String.valueOf("REDSTONE"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ❤",
                    "&bUse :heart: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Title", String.valueOf("&cNotes"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Material", String.valueOf("NOTE_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♪",
                    "&bUse :notes: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Title", String.valueOf("&cStar"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Material", String.valueOf("NETHER_STAR"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✰",
                    "&bUse :star: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Title", String.valueOf("&cPeace"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Material", String.valueOf("ORANGE_TULIP"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☮",
                    "&bUse :peace: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Title", String.valueOf("&cChess"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Material", String.valueOf("TORCH"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♖",
                    "&bUse :chess: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Title", String.valueOf("&cCopyright"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Material", String.valueOf("PAINTING"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ©",
                    "&bUse :chess: in the chat",
                    " "
                }));

            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Title", String.valueOf("&cAnchor"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Material", String.valueOf("IRON_ORE"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚓",
                    "&bUse :anchor: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Title", String.valueOf("&cSkull"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Material", String.valueOf("SKULL_ITEM"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☠",
                    "&bUse :skull: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Title", String.valueOf("&cUmbrella"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Material", String.valueOf("GREEN_CARPET"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☂",
                    "&bUse :umbrella: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Title", String.valueOf("&cDiamonds"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Material", String.valueOf("DIAMOND"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♦",
                    "&bUse :diamonds: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Title", String.valueOf("&cSnowflake"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Material", String.valueOf("SNOWBALL"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ❄",
                    "&bUse :snowflake: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Title", String.valueOf("&cSnowman"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Material", String.valueOf("SNOW_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☃",
                    "&bUse :snowman: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Title", String.valueOf("&cCheckmark"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Material", String.valueOf("EMERALD_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✔",
                    "&bUse :checkmark: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Title", String.valueOf("&cCrossmark"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Material", String.valueOf("REDSTONE_BLOCK"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✖",
                    "&bUse :crossmark: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Title", String.valueOf("&cArrow"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Material", String.valueOf("ARROW"));
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : Σ>―(´･ω･`)→",
                    "&bUse :arrow: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Title", String.valueOf("&cStrong"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Material", String.valueOf("SPLASH_POTION"));
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : (9｀･ω･)9",
	                    "&bUse :strong: in the chat",
	                    " "
	                }));
	
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Use_Permission", true);
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
			
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Enable", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Use_Permission", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Title", String.valueOf("&cShrug"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Material", String.valueOf("STRING"));
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : ¯\\_(ツ)_/¯",
	                "&bUse :shrug: in the chat",
	                " "
	            }));
	        
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Enable", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Use_Permission", true);
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
			
			OnJoinConfig.getConfig().set("Action-Bar.Enable", true);
			
			OnJoinConfig.getConfig().set("Action-Bar.First-Join.Enable", true);
			OnJoinConfig.getConfig().set("Action-Bar.First-Join.Message", "&6Welcome %player%"); 
            
			OnJoinConfig.getConfig().set("Action-Bar.First-Join.World.All_World", false);
            OnJoinConfig.getConfig().set("Action-Bar.First-Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));

            OnJoinConfig.getConfig().set("Action-Bar.Join.Enable", true);
            OnJoinConfig.getConfig().set("Action-Bar.Join.Message", "&6Welcome %player%"); 
            
            OnJoinConfig.getConfig().set("Action-Bar.Join.World.All_World", false);
            OnJoinConfig.getConfig().set("Action-Bar.Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            OnJoinConfig.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Speed.Enable")) {
			OnJoinConfig.getConfig().set("Speed.Enable", true);
			OnJoinConfig.getConfig().set("Speed.Value", 2);
			OnJoinConfig.getConfig().set("Speed.World.All_World", false);
			OnJoinConfig.getConfig().set("Speed.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            OnJoinConfig.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Potion-Effect.BLINDNESS.Enable")) {
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Enable", true);
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Duration-Second", 2);
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Amplifier", 2);
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.World.All_World", false);
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	        }));
	        
			OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Enable", true);
			OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Duration-Second", 2);
	        OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Amplifier", 2);
	        OnJoinConfig.getConfig().set("Potion-Effect.JUMP.World.All_World", false);
	        OnJoinConfig.getConfig().set("Potion-Effect.JUMP.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	        }));
	        
	        OnJoinConfig.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Potion-Effect.BLINDNESS.Use_Permission")) {
			OnJoinConfig.getConfig().set("Potion-Effect.BLINDNESS.Use_Permission", true);
			OnJoinConfig.getConfig().set("Potion-Effect.JUMP.Use_Permission", true);
	        
	        OnJoinConfig.saveConfigFile();
		}
		
		
		// Commands Messages
		
		if (!ConfigMGeneral.getConfig().isSet("General.On-join.Join-Message.Per-Group.Options.Enable")) {
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Options.Enable", true);
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Options.Disable-Any-Messages-On-Join", true);
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
                    "&cPLEASE WELCOME a owner"}));
			ConfigMGeneral.getConfig().set("General.On-join.Join-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
                    "unlimited groups of course"}));
            
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Options.Enable", true);
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit", true);
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
            		"&cPLEASE SAY GOODBYE a owner"}));
			ConfigMGeneral.getConfig().set("General.On-Quit.Quit-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
            		"unlimited groups of course"}));
            
            ConfigMGeneral.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Heal.Self.Enable")) {
			ConfigMCommands.getConfig().set("Heal.Self.Enable", true);
			ConfigMCommands.getConfig().set("Heal.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been healed"}));
			ConfigMCommands.getConfig().set("Heal.Other.Enable", true);
			ConfigMCommands.getConfig().set("Heal.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been healed by &e%player%"}));
			ConfigMCommands.getConfig().set("Heal.Other-Sender.Enable", true);
			ConfigMCommands.getConfig().set("Heal.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 has been healed"}));
			
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Tp.Self.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Tp.Self.Enable", true);
			ConfigMCommands.getConfig().set("Warp.Tp.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been teleported to the warp &e%warp%"}));
			ConfigMCommands.getConfig().set("Warp.Tp.Other.Enable", true);
			ConfigMCommands.getConfig().set("Warp.Tp.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been teleported to the warp &e%warp%&7 by &b%player%"}));
	        ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender.Enable", true);
	        ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You teleported &b%target%&7 to the warp&e %warp%"}));
	        ConfigMCommands.getConfig().set("Warp.List.Enable", true);
	        ConfigMCommands.getConfig().set("Warp.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bWarpList :&e %warplist%"}));
	        ConfigMCommands.getConfig().set("Warp.No-Warp.Enable", true);
	        ConfigMCommands.getConfig().set("Warp.No-Warp.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cI'm sorry, but there are no warp"}));
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Set.Enable", true);
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The warp has been created with the name &e%arg%"}));
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Already-Exist.Enable", true);
	        ConfigMCommands.getConfig().set("Warp.Set.Warp-Already-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe name already exists"}));
		
	        ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Vanish.Self.Enable")) {
			ConfigMCommands.getConfig().set("Vanish.Self.Enable", true);
			ConfigMCommands.getConfig().set("Vanish.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &aenabled"}));
			ConfigMCommands.getConfig().set("Vanish.Self-Disabled.Enable", true);
			ConfigMCommands.getConfig().set("Vanish.Self-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &cdisabled"}));
			/*ConfigMCommands.getConfig().set("Vanish.Self-Still.Enable", true);
			ConfigMCommands.getConfig().set("Vanish.Self-Still.Messages", java.util.Arrays.asList(new String[] {"&bVanish is still enabled"}));*/
        
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Vanish.Other-Target.Enable")) {
			ConfigMCommands.getConfig().set("Vanish.Other-Target.Enable", true);
			ConfigMCommands.getConfig().set("Vanish.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &aenabled&7 by&e %player%"}));
			ConfigMCommands.getConfig().set("Vanish.Other-Target-Disabled.Enable", true);
			ConfigMCommands.getConfig().set("Vanish.Other-Target-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The vanish has been &cdisabled&7 by&e %player%"}));
			ConfigMCommands.getConfig().set("Vanish.Other-Sender.Enable", true);
			ConfigMCommands.getConfig().set("Vanish.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The &b%target%&7's vanish has been &aenabled&7 by&e %player%"}));
			ConfigMCommands.getConfig().set("Vanish.Other-Sender-Disabled.Enable", true);
			ConfigMCommands.getConfig().set("Vanish.Other-Sender-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The &b%target%&7's vanish has been &cdisabled&7 by&e %player%"}));
        
        	ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("ClearInv.Self.Enable")) {
			ConfigMCommands.getConfig().set("ClearInv.Self.Enable", true);
			ConfigMCommands.getConfig().set("ClearInv.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your inventory has been cleaned up"}));
			ConfigMCommands.getConfig().set("ClearInv.Other-Target.Enable", true);
			ConfigMCommands.getConfig().set("ClearInv.Other-Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your inventory has been cleaned up by &e%player%"}));
			ConfigMCommands.getConfig().set("ClearInv.Other-Sender.Enable", true);
			ConfigMCommands.getConfig().set("ClearInv.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The &e%target%&7's inventory has been cleaned up"}));
        
        ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Tp.Self-Delay.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Tp.Self-Delay.Enable", true);
			ConfigMCommands.getConfig().set("Warp.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command"}));
            ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender-Delay.Enable", true);
            ConfigMCommands.getConfig().set("Warp.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command for this player"}));
           
            ConfigMCommands.saveConfigFile();
		}
        
		if (!ConfigMCommands.getConfig().isSet("Spawn.Tp.Self-Delay.Enable")) {
			ConfigMCommands.getConfig().set("Spawn.Tp.Self-Delay.Enable", true);
			ConfigMCommands.getConfig().set("Spawn.Tp.Self-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command"}));
            ConfigMCommands.getConfig().set("Spawn.Tp.Other-Sender-Delay.Enable", true);
            ConfigMCommands.getConfig().set("Spawn.Tp.Other-Sender-Delay.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%second%&c second(s)&7 before to be able to execute the command for this player"}));
           
            ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Del.Warp-Doesnt-Exist.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Doesnt-Exist.Enable", true);
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Doesnt-Exist.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe warp doesn't exist"}));
			
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Del.Warp-Delete.Enable")) {
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Delete.Enable", true);
			ConfigMCommands.getConfig().set("Warp.Del.Warp-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The warp &e%warp%&7 has been &cdeleted"}));
			
			ConfigMCommands.saveConfigFile();
		}
		// Events messages
		
		if (!ConfigMEvents.getConfig().isSet("Anti-Swear.Notify-Staff")) {
			ConfigMEvents.getConfig().set("Anti-Swear.Notify-Staff", java.util.Arrays.asList(new String[] {"&8[&eAnti&7-&eSwear&8] &b%player% &7said &e%message%"}));
		
			ConfigMEvents.saveConfigFile();
		}
		
		// Lol warp and spawn
		if (!WarpSetWarpCommandConfig.getConfig().isSet("Warp.Delay.Self.Enable")) {
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Self.Enable", true);
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Self.Delay-Seconds", 5);
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Self.Bypass-Delay", false);
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Other.Enable", true);
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Other.Delay-Seconds", 5);
			WarpSetWarpCommandConfig.getConfig().set("Warp.Delay.Other.Bypass-Delay", false);
            
            WarpSetWarpCommandConfig.saveConfigFile();
		}
		
		if (!SpawnCommandConfig.getConfig().isSet("Commands.Spawn.Delay.Self.Enable")) {
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Self.Enable", true);
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Self.Delay-Seconds", 5);
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Self.Bypass-Delay", false);
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Other.Enable", true);
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Other.Delay-Seconds", 5);
			SpawnCommandConfig.getConfig().set("Commands.Spawn.Delay.Other.Bypass-Delay", false);
            
			SpawnCommandConfig.saveConfigFile();
		}
		
		if (!ConfigMOStuff.getConfig().isSet("Commands.Spawn.Delay.Self.Enable")) {
			ConfigMOStuff.getConfig().set("Spawn.Del.Spawn-Delete.Enable", true);
			ConfigMOStuff.getConfig().set("Spawn.Del.Spawn-Delete.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The spawn &e%spawn%&7 has been &cdeleted"}));
		
			ConfigMOStuff.saveConfigFile();
		}
		
		if (!PlayerWorldChangeConfigE.getConfig().isSet("GM.Enable")) {
			PlayerWorldChangeConfigE.getConfig().set("GM.Enable", true);
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.Enable", true);
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.GameMode", 1);
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Survival", true);
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Creative", true);
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Adventure", true);
			PlayerWorldChangeConfigE.getConfig().set("GM.CustomMode.If-player-have.Spectator", true);
			PlayerWorldChangeConfigE.getConfig().set("GM.World.All_World", false);
			PlayerWorldChangeConfigE.getConfig().set("GM.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	            }));
			
			PlayerWorldChangeConfigE.saveConfigFile();
		}
		
		if(!BetweenServersConfig.getConfig().isSet("Keep.Vanish-On-Join.Enable")) {
			BetweenServersConfig.getConfig().set("Keep.Vanish-On-Join.Enable", true);
			
			BetweenServersConfig.saveConfigFile();
		}
		
	}

}
