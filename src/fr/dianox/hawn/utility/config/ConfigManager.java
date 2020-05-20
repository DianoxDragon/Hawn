package fr.dianox.hawn.utility.config;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.*;
import fr.dianox.hawn.utility.config.configs.commands.*;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.*;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiLobbyBow;
import fr.dianox.hawn.utility.config.configs.events.*;
import fr.dianox.hawn.utility.config.configs.messages.*;
import fr.dianox.hawn.utility.config.configs.messages.fr_fr.*;
import fr.dianox.hawn.utility.config.configs.scoreboard.defaultscoreboardconfig;
import fr.dianox.hawn.utility.config.configs.scoreboard.worldnetherdsc;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import fr.dianox.hawn.utility.load.Reload;
import fr.dianox.hawn.utility.load.WorldList;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ConfigManager {

	public HashMap<String, String> configfile = new HashMap<>();
	public HashMap<String, String> configfilereverse = new HashMap<>();
	public HashMap<Player, String> configfileinuse = new HashMap<>();

	public ConfigManager(Main plugin) {
		// Load config
		ConfigSpawn.loadConfig(plugin);
		configfile.put("G-spawn", "spawn.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "spawn.yml", "G-spawn");
		ConfigGeneral.loadConfig(plugin);
		configfile.put("G-general", "general.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "general.yml", "G-general");
		ConfigWorldGeneral.loadConfig(plugin);
		configfile.put("G-World-List", "World-List.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "World-List.yml", "G-World-List");
		ServerListConfig.loadConfig(plugin);
		configfile.put("G-ServerList", "ServerList.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "ServerList.yml", "G-ServerList");
		AutoBroadcastConfig.loadConfig(plugin);
		configfile.put("G-AutoBroadcast", "AutoBroadcast.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "AutoBroadcast.yml", "G-AutoBroadcast");
		PlayerOptionMainConfig.loadConfig(plugin);
		configfile.put("G-Player-Option-General", "Player-Option-General.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Player-Option-General.yml", "G-Player-Option-General");
		CommandAliasesConfig.loadConfig(plugin);
		configfile.put("G-command-aliases", "command-aliases.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "command-aliases.yml", "G-command-aliases");
		WarpListConfig.loadConfig(plugin);
		configfile.put("G-warplist", "warplist.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "warplist.yml", "G-warplist");
		ScoreboardMainConfig.loadConfig(plugin);
		configfile.put("G-Scoreboard-General", "Scoreboard-General.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Scoreboard-General.yml", "G-Scoreboard-General");

		OtherFeaturesConfig.loadConfig(plugin);
		configfile.put("E-OtherFeatures", "Events/OtherFeatures.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/OtherFeatures.yml", "E-OtherFeatures");
		WorldEventConfig.loadConfig(plugin);
		configfile.put("E-WorldEvent", "Events/WorldEvent.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/WorldEvent.yml", "E-WorldEvent");

		ConfigGProtection.loadConfig(plugin);
		configfile.put("E-ProtectionWorld", "Events/ProtectionWorld.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/ProtectionWorld.yml", "E-ProtectionWorld");

		VoidTPConfig.loadConfig(plugin);
		configfile.put("E-VoidTP", "Events/VoidTP.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/VoidTP.yml", "E-VoidTP");
		ProtectionPlayerConfig.loadConfig(plugin);
		configfile.put("E-ProtectionPlayer", "Events/ProtectionPlayer.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/ProtectionPlayer.yml", "E-ProtectionPlayer");
		PlayerEventsConfig.loadConfig(plugin);
		configfile.put("E-PlayerEvents", "Events/PlayerEvents.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/PlayerEvents.yml", "E-PlayerEvents");
		OnJoinConfig.loadConfig(plugin);
		configfile.put("E-OnJoin", "Events/OnJoin.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/OnJoin.yml", "E-OnJoin");
		CommandEventConfig.loadConfig(plugin);
		configfile.put("E-OnCommands", "Events/OnCommands.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/OnCommands.yml", "E-OnCommands");
		ConfigGJoinQuitCommand.loadConfig(plugin);
		configfile.put("E-JoinQuitCommand", "Events/JoinQuitCommand.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/JoinQuitCommand.yml", "E-JoinQuitCommand");
		OnChatConfig.loadConfig(plugin);
		configfile.put("E-Chat", "Events/Chat.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/Chat.yml", "E-Chat");
		PlayerWorldChangeConfigE.loadConfig(plugin);
		configfile.put("E-PlayerWorldChange", "Events/PlayerWorldChange.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Events/PlayerWorldChange.yml", "E-PlayerWorldChange");

		ActionbarAnnouncerConfig.loadConfig(plugin);
		configfile.put("C-ActionBarAnnouncer", "Commands/ActionBarAnnouncer.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/ActionBarAnnouncer.yml", "C-ActionBarAnnouncer");
		FlyCommandConfig.loadConfig(plugin);
		configfile.put("C-Fly", "Commands/Fly.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Fly.yml", "C-Fly");
		WeatherTimeCommandConfig.loadConfig(plugin);
		configfile.put("C-Weather-Time", "Commands/Weather-Time.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" +  "Commands/Weather-Time.yml", "C-Weather-Time");
		HelpCommandConfig.loadConfig(plugin);
		configfile.put("C-Help", "Commands/Help.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Help.yml", "C-Help");
		ClearChatCommandConfig.loadConfig(plugin);
		configfile.put("C-ClearChat", "Commands/ClearChat.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/ClearChat.yml", "C-ClearChat");
		SpawnCommandConfig.loadConfig(plugin);
		configfile.put("C-Spawn", "Commands/Spawn.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Spawn.yml", "C-Spawn");
		MuteChatCommandConfig.loadConfig(plugin);
		configfile.put("C-MuteChat", "Commands/MuteChat.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/MuteChat.yml", "C-MuteChat");
		PingCommandConfig.loadConfig(plugin);
		configfile.put("C-Ping", "Commands/Ping.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Ping.yml", "C-Ping");
		DelayChatCommandConfig.loadConfig(plugin);
		configfile.put("C-DelayChat", "Commands/DelayChat.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/DelayChat.yml", "C-DelayChat");
		BroadCastCommandConfig.loadConfig(plugin);
		configfile.put("C-Broadcast", "Commands/Broadcast.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Broadcast.yml", "C-Broadcast");
		HealCommandConfig.loadConfig(plugin);
		configfile.put("C-Heal", "Commands/Heal.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Heal.yml", "C-Heal");
		WarpSetWarpCommandConfig.loadConfig(plugin);
		configfile.put("C-Warp-SetWarp", "Commands/Warp-SetWarp.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" +  "Commands/Warp-SetWarp.yml", "C-Warp-SetWarp");
		VanishCommandConfig.loadConfig(plugin);
		configfile.put("C-Vanish", "Commands/Vanish.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Vanish.yml", "C-Vanish");
		TitleAnnouncerConfig.loadConfig(plugin);
		configfile.put("C-TitleAnnouncer", "Commands/TitleAnnouncer.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/TitleAnnouncer.yml", "C-TitleAnnouncer");
		ClearInvCommandConfig.loadConfig(plugin);
		configfile.put("C-ClearInv", "Commands/ClearInv.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/ClearInv.yml", "C-ClearInv");
		EmojiCommandConfig.loadConfig(plugin);
		configfile.put("C-Emoji", "Commands/Emoji.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Emoji.yml", "C-Emoji");
		ScoreboardCommandConfig.loadConfig(plugin);
		configfile.put("C-Scoreboard", "Commands/Scoreboard.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Scoreboard.yml", "C-Scoreboard");
		GamemodeCommandConfig.loadConfig(plugin);
		configfile.put("C-Gamemode", "Commands/Gamemode.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Gamemode.yml", "C-Gamemode");
		OptionPlayerConfigCommand.loadConfig(plugin);
		configfile.put("C-PlayerOption", "Commands/PlayerOption.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/PlayerOption.yml", "C-PlayerOption");
		WarningCommandConfig.loadConfig(plugin);
		configfile.put("C-Warning", "Commands/Warning.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Warning.yml", "C-Warning");
		FeedCommandConfig.loadConfig(plugin);
		configfile.put("C-Feed", "Commands/Feed.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Feed.yml", "C-Feed");
		GoTopCommandConfig.loadConfig(plugin);
		configfile.put("C-Gotop", "Commands/Gotop.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Gotop.yml", "C-Gotop");
		AdminPanelCommandConfig.loadConfig(plugin);
		configfile.put("C-AdminPanel", "Commands/AdminPanel.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/AdminPanel.yml", "C-AdminPanel");
		SuicideCommandConfig.loadConfig(plugin);
		configfile.put("C-Suicide", "Commands/Suicide.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Suicide.yml", "C-Suicide");
		EnderChestCommandConfig.loadConfig(plugin);
		configfile.put("C-EnderChest", "Commands/EnderChest.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/EnderChest.yml", "C-EnderChest");
		InvSeeCommandConfig.loadConfig(plugin);
		configfile.put("C-InvSee", "Commands/InvSee.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/InvSee.yml", "C-InvSee");
		RepairCommandConfig.loadConfig(plugin);
		configfile.put("C-Repair", "Commands/Repair.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Repair.yml", "C-Repair");
		HatCommandConfig.loadConfig(plugin);
		configfile.put("C-Hat", "Commands/Hat.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Hat.yml", "C-Hat");
		KickAllCommandConfig.loadConfig(plugin);
		configfile.put("C-KickAll", "Commands/KickAll.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/KickAll.yml", "C-KickAll");
		IpCommandConfig.loadConfig(plugin);
		configfile.put("C-Ip", "Commands/Ip.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Ip.yml", "C-Ip");
		GetPosCommandConfig.loadConfig(plugin);
		configfile.put("C-GetPos", "Commands/GetPos.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/GetPos.yml", "C-GetPos");
		ClearGroundItemsCommandConfig.loadConfig(plugin);
		configfile.put("C-ClearGroundItems", "Commands/ClearGroundItems.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/ClearGroundItems.yml", "C-ClearGroundItems");
		ClearMobsCommandConfig.loadConfig(plugin);
		configfile.put("C-ClearMobs", "Commands/ClearMobs.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/ClearMobs.yml", "C-ClearMobs");
		CheckAccountCommandConfig.loadConfig(plugin);
		configfile.put("C-CheckAccount", "Commands/CheckAccount.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/CheckAccount.yml", "C-CheckAccount");
		ExpCommandConfig.loadConfig(plugin);
		configfile.put("C-Exp", "Commands/Exp.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Exp.yml", "C-Exp");
		ListCommandConfig.loadConfig(plugin);
		configfile.put("C-List", "Commands/List.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/List.yml", "C-List");
		OneCommandConfig.loadConfig(plugin);
		configfile.put("C-1-WE", "Commands/1-WE.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/1-WE.yml", "C-1-WE");
		TwoCommandConfig.loadConfig(plugin);
		configfile.put("C-2-WE", "Commands/2-WE.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/2-WE.yml", "C-2-WE");
		CopyCommandConfig.loadConfig(plugin);
		configfile.put("C-C-WE", "Commands/C-WE.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/C-WE.yml", "C-C-WE");
		PasteCommandConfig.loadConfig(plugin);
		configfile.put("C-P-WE", "Commands/P-WE.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/P-WE.yml", "C-P-WE");
		HawnCommandConfig.loadConfig(plugin);
		configfile.put("C-Hawn", "Commands/Hawn.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Hawn.yml", "C-Hawn");
		WorkBenchCommandConfig.loadConfig(plugin);
		configfile.put("C-WorkBench", "Commands/WorkBench.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/WorkBench.yml", "C-WorkBench");
		BurnCommandConfig.loadConfig(plugin);
		configfile.put("C-Burn", "Commands/Burn.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Burn.yml", "C-Burn");
		SkullCommandConfig.loadConfig(plugin);
		configfile.put("C-Skull", "Commands/Skull.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Skull.yml", "C-Skull");
		FlySpeedCommandConfig.loadConfig(plugin);
		configfile.put("C-FlySpeed", "Commands/FlySpeed.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/FlySpeed.yml", "C-FlySpeed");
		SpeedCommandConfig.loadConfig(plugin);
		configfile.put("C-Speed", "Commands/Speed.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/Speed.yml", "C-Speed");
		WorldCommandConfig.loadConfig(plugin);
		configfile.put("C-World", "Commands/World.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Commands/World.yml", "C-World");

		ConfigGCos.loadConfig(plugin);
		configfile.put("CF-OnJoin", "Cosmetics-Fun/OnJoin.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Cosmetics-Fun/OnJoin.yml", "CF-OnJoin");
		ConfigGLP.loadConfig(plugin);
		configfile.put("CF-JumpPads", "Cosmetics-Fun/JumpPads.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Cosmetics-Fun/JumpPads.yml", "CF-JumpPads");
		ConfigFDoubleJump.loadConfig(plugin);
		configfile.put("CF-DoubleJump", "Cosmetics-Fun/DoubleJump.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Cosmetics-Fun/DoubleJump.yml", "CF-DoubleJump");
		FireworkListCUtility.loadConfig(plugin);
		configfile.put("CFU-Firework-List", "Cosmetics-Fun/Utility/Firework-List.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Firework-List.yml", "CFU-Firework-List");
		BookListConfiguration.loadConfig(plugin);
		configfile.put("CFU-Book-List", "Cosmetics-Fun/Utility/Book-List.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Book-List.yml", "CFU-Book-List");
		EmojisListCUtility.loadConfig(plugin);
		configfile.put("CFU-Emojis-List", "Cosmetics-Fun/Utility/Emojis-List.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Emojis-List.yml", "CFU-Emojis-List");
		SignListCUtility.loadConfig(plugin);
		configfile.put("CFU-Sign-List", "Cosmetics-Fun/Utility/Sign-List.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Sign-List.yml", "CFU-Sign-List");

		//NameTagConfig.loadConfig(plugin);
		TablistConfig.loadConfig(plugin);
		configfile.put("G-CustomCommand", "CustomCommand.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "CustomCommand.yml", "G-CustomCommand");

		CustomCommandConfig.loadConfig(plugin);
		configfile.put("T-Tablist", "Tablist/Tablist.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Tablist/Tablist.yml", "T-Tablist");

		SpecialCjiHidePlayers.loadConfig(plugin);
		configfile.put("CJI-Special-HidePlayers", "CustomJoinItem/Special-HidePlayers.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "CustomJoinItem/Special-HidePlayers.yml", "CJI-Special-HidePlayers");
		SpecialCjiLobbyBow.loadConfig(plugin);
		configfile.put("CJI-Special-LobbyBow", "CustomJoinItem/Special-LobbyBow.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "CustomJoinItem/Special-LobbyBow.yml", "CJI-Special-LobbyBow");
		SpecialCjiFunGun.loadConfig(plugin);
		configfile.put("CJI-Special-FunGun", "CustomJoinItem/Special-FunGun.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "CustomJoinItem/Special-FunGun.yml", "CJI-Special-FunGun");
		ConfigCJIGeneral.loadConfig(plugin);
		configfile.put("CJI-General", "CustomJoinItem/General.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "CustomJoinItem/General.yml", "CJI-General");

		if (!ScoreboardMainConfig.getConfig().isSet("DefaultConfigGenerated")) {
			defaultscoreboardconfig.loadConfig(plugin);
			worldnetherdsc.loadConfig(plugin);

			ScoreboardMainConfig.getConfig().set("DefaultConfigGenerated", true);
			ScoreboardMainConfig.saveConfigFile();
		}

		if (ConfigGeneral.getConfig().isSet("Plugin.Language-Type")) {
			try {
				Main.LanguageType = ConfigGeneral.getConfig().getString("Plugin.Language-Type");
			} catch (Exception ignored) {}
		} else {
			ConfigGeneral.getConfig().set("Plugin.Language-Type", "en_US");

			ConfigGeneral.saveConfigFile();
		}

		// French language

		FRAdminPanelConfig.loadConfig(plugin);
		FRConfigMMsg.loadConfig(plugin);
		FRConfigMGeneral.loadConfig(plugin);
		FRWorldManagerPanelConfig.loadConfig(plugin);
		FRAdminAMConfig.loadConfig(plugin);

		// Normal

		ConfigMGeneral.loadConfig(plugin);
		configfile.put("M-General", "Messages/" + Main.LanguageType + "/General.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Messages/" + Main.LanguageType + "/General.yml", "M-General");
		ConfigMMsg.loadConfig(plugin);
		configfile.put("M-Messages", "Messages/" + Main.LanguageType + "/Messages.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Messages/" + Main.LanguageType + "/Messages.yml", "M-Messages");

		ConfigMAdmin.loadConfig(plugin);
		configfile.put("M-Admin", "Messages/" + Main.LanguageType + "/Admin.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Messages/" + Main.LanguageType + "/Admin.yml", "M-Admin");
		AdminPanelConfig.loadConfig(plugin);
		configfile.put("M-AdminPanel", "Messages/" + Main.LanguageType + "/AdminPanel.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Messages/" + Main.LanguageType + "/AdminPanel.yml", "M-AdminPanel");
		WorldManagerPanelConfig.loadConfig(plugin);
		configfile.put("M-WorldMPC", "Messages/" + Main.LanguageType + "/WorldManager.yml");
		configfilereverse.put(plugin.getDataFolder() + "/" + "Messages/" + Main.LanguageType + "/WorldManager.yml", "M-WorldMPC");

		WorldList.setworldlist();

		Reload.configlist();

		TXTmsg.onCreateInfoMsgAdminMain();
		TXTmsg.onWriteMain();

		configfileinuse.clear();
	}

}