package fr.dianox.hawn.utility.load;

import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.autobroadcast.AutoBroadcastManager;
import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.event.FunFeatures;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.onjoin.OnJoin;
import fr.dianox.hawn.utility.config.configs.*;
import fr.dianox.hawn.utility.config.configs.commands.*;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.*;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiLobbyBow;
import fr.dianox.hawn.utility.config.configs.events.*;
import fr.dianox.hawn.utility.config.configs.messages.*;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import fr.dianox.hawn.utility.world.PlayerEventsPW;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Reload {
	
	public static void configlist() {
		ConfigSpawn.reloadConfig();
		ConfigGeneral.reloadConfig();
		ConfigMGeneral.reloadConfig();
		ConfigMMsg.reloadConfig();
		VoidTPConfig.reloadConfig();
		ConfigGProtection.reloadConfig();
		ConfigMMsg.reloadConfig();
		ProtectionPlayerConfig.reloadConfig();
		OtherFeaturesConfig.reloadConfig();
		WorldEventConfig.reloadConfig();
		ConfigMMsg.reloadConfig();
		HelpCommandConfig.reloadConfig();
		PlayerEventsConfig.reloadConfig();
		ConfigGCos.reloadConfig();
		OnJoinConfig.reloadConfig();
		CommandEventConfig.reloadConfig();
		ConfigGLP.reloadConfig();
		ClearChatCommandConfig.reloadConfig();
		ConfigMMsg.reloadConfig();
		SpawnCommandConfig.reloadConfig();
		MuteChatCommandConfig.reloadConfig();
		PingCommandConfig.reloadConfig();
		DelayChatCommandConfig.reloadConfig();
		ServerListConfig.reloadConfig();
		ConfigGJoinQuitCommand.reloadConfig();
		BroadCastCommandConfig.reloadConfig();
		WeatherTimeCommandConfig.reloadConfig();
		FlyCommandConfig.reloadConfig();
		ConfigFDoubleJump.reloadConfig();
		OnChatConfig.reloadConfig();
		HealCommandConfig.reloadConfig();
		WarpSetWarpCommandConfig.reloadConfig();
		WarpListConfig.reloadConfig();
		CustomCommandConfig.reloadConfig();
		VanishCommandConfig.reloadConfig();
		TitleAnnouncerConfig.reloadConfig();
		ClearInvCommandConfig.reloadConfig();
		AutoBroadcastConfig.reloadConfig();
		AutoBroadcastManager.restart();
		EmojiCommandConfig.reloadConfig();
		ScoreboardMainConfig.reloadConfig();
		if (Main.getInstance().getScoreManager() != null) {
			Main.getInstance().getScoreManager().reloadFiles();
		}
		PlayerOptionMainConfig.reloadConfig();
		PlayerWorldChangeConfigE.reloadConfig();
		ScoreboardCommandConfig.reloadConfig();
		GamemodeCommandConfig.reloadConfig();
		SpecialCjiHidePlayers.reloadConfig();
		ConfigMMsg.reloadConfig();
		OptionPlayerConfigCommand.reloadConfig();
		ConfigMAdmin.reloadConfig();
		ConfigMAdmin.reloadConfig();
		ConfigMMsg.reloadConfig();
		TablistConfig.reloadConfig();
		CommandAliasesConfig.reloadConfig();
		WarningCommandConfig.reloadConfig();
		AdminPanelConfig.reloadConfig();
		ActionbarAnnouncerConfig.reloadConfig();
		FireworkListCUtility.reloadConfig();
		FeedCommandConfig.reloadConfig();
		GoTopCommandConfig.reloadConfig();
		ConfigCJIGeneral.reloadConfig();
		AdminPanelCommandConfig.reloadConfig();
		SuicideCommandConfig.reloadConfig();
		EnderChestCommandConfig.reloadConfig();
		InvSeeCommandConfig.reloadConfig();
		RepairCommandConfig.reloadConfig();
		HatCommandConfig.reloadConfig();
		KickAllCommandConfig.reloadConfig();
		GetPosCommandConfig.reloadConfig();
		IpCommandConfig.reloadConfig();
		ClearGroundItemsCommandConfig.reloadConfig();
		ClearMobsCommandConfig.reloadConfig();
		SpecialCjiLobbyBow.reloadConfig();
		BookListConfiguration.reloadConfig();
		CheckAccountCommandConfig.reloadConfig();
		ExpCommandConfig.reloadConfig();
		ListCommandConfig.reloadConfig();
		OneCommandConfig.reloadConfig();
		TwoCommandConfig.reloadConfig();
		CopyCommandConfig.reloadConfig();
		PasteCommandConfig.reloadConfig();
		EmojisListCUtility.reloadConfig();
		HawnCommandConfig.reloadConfig();
		WorkBenchCommandConfig.reloadConfig();
		SkullCommandConfig.reloadConfig();
		BurnCommandConfig.reloadConfig();
		FlySpeedCommandConfig.reloadConfig();
		SpeedCommandConfig.reloadConfig();
		SpecialCjiFunGun.reloadConfig();
		WorldCommandConfig.reloadConfig();
		ConfigWorldGeneral.reloadConfig();
		WorldManagerPanelConfig.reloadConfig();
		SignListCUtility.reloadConfig();
	}
	
	public static void reloadconfig() {
		configlist();
		
		WorldList.clearworldlist();
		WorldList.setworldlist();
		
		ChatEmojisLoad.onLoad();
		
		Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), Main::UpdateCheckReload);
		
		HooksManager.reload();
		fr.dianox.hawn.modules.chat.ChatFormat.reload();

		Main.getInstance().getVoidTPManager().load();
		Main.getInstance().getEventManager().loaddamageEvent();
		
		Main.injumpwithjumppad.clear();
		OnJoin.player_list.clear();
		
		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			OnJoin.player_list.add(p);
			
			if (FlyCommand.player_list_flyc.contains(p)) {
				if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable")) {
					FlyCommand.player_list_flyc.remove(p);
					p.setAllowFlight(false);
					p.setFlying(false);
				}
			}
			
			if (FunFeatures.canDoubleJumpHere(p)) {
				FunFeatures.allowDoubleJumpFlight(p);
			}
		}
		
		Main.getInstance().getInteractables().load();

		Main.getInstance().getServerListManager().load();

		FunFeatures.incooldownjumppads.clear();
		Main.avoidtitles.clear();
		
		Main.indj.clear();
		
		Main.getInstance().getTabManager().stop();

		 /*
	     * Custom join item
	     */

		Main.getInstance().getCjiManager().load();
		
	    OnCommandEvent.cooldowncommands.clear();
		
		Main.getInstance().getTabManager().start(Main.getInstance());

		Main.getInstance().getBlockExceptions().load();

		// The same warnings as at startup
		fr.dianox.hawn.utility.StartupReport.printWarnings();
	}

}