package fr.dianox.hawn.utility.load;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.event.FunFeatures;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.onjoin.OnJoin;
import fr.dianox.hawn.modules.tablist.tab.AnimationTabTask;
import fr.dianox.hawn.modules.tablist.tab.MainTablist;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.NMSClass;
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
import org.bukkit.scheduler.BukkitTask;

import java.lang.reflect.Constructor;

public class Reload {
	
	private static Class<?> PacketPlayOutPlayerListHeaderFooter;
    private static Class<?> ChatComponentText;
    private static Constructor<?> newPacketPlayOutPlayerListHeaderFooter;

	public static String hea = "";
	public static String foo = "";
    
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
		EmojiCommandConfig.reloadConfig();
		ScoreboardMainConfig.reloadConfig();
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
		
		Main.UpdateCheckReload();
		
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
			if (!Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				Bukkit.getConsoleSender().sendMessage("| Please note that to remove the PlaceHolderAPI support, you must restart the server");
				Bukkit.getConsoleSender().sendMessage("| The plugin supports fast removal, but does not guarantee a return to normal with a hawn reload");
				ConfigGeneral.getConfig().set("Plugin.Use.PlaceholderAPI", false);
				ConfigGeneral.saveConfigFile();
			}
		}
				
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
			
			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
	        	if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
	        		if (PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
	        			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
	        				if (p.hasPermission("hawn.fun.doublejump.double")) {
	        					p.setAllowFlight(true);
	        				}
	        			} else {
	        				p.setAllowFlight(true);
	        			}
	        		}
	        	} else {
	        		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
	    				if (p.hasPermission("hawn.fun.doublejump.double")) {
	    					p.setAllowFlight(true);
	    				}
	    			} else {
	    				p.setAllowFlight(true);
	    			}
	        	} 	
	        }
		}
		
		Main.getInstance().getInteractables().load();

		Main.getInstance().getServerListManager().load();

		FunFeatures.incooldownjumppads.clear();
		Main.avoidtitles.clear();
		
		if (TablistConfig.getConfig().getBoolean("Tablist.header.enabled")) {
	    	Main.getInstance().getTabManager().hea = String.valueOf(TablistConfig.getConfig().getStringList("Tablist.header.message"));
	    	
	    	Main.getInstance().getTabManager().hea = Main.getInstance().getTabManager().hea.substring(1, Main.getInstance().getTabManager().hea.length() - 1).replaceAll(", ", "\n");
			Main.getInstance().getTabManager().hea = MessageUtils.colourTheStuff(Main.getInstance().getTabManager().hea);
	    }
	    
	    if (TablistConfig.getConfig().getBoolean("Tablist.footer.enabled")) {
	    	Main.getInstance().getTabManager().foo = String.valueOf(TablistConfig.getConfig().getStringList("Tablist.footer.message"));
	    	
	    	Main.getInstance().getTabManager().foo = Main.getInstance().getTabManager().foo.substring(1, Main.getInstance().getTabManager().foo.length() - 1).replaceAll(", ", "\n");
		    Main.getInstance().getTabManager().foo = MessageUtils.colourTheStuff(Main.getInstance().getTabManager().foo);
	    }
		
		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable") && OnJoinConfig.getConfig().getBoolean("Fly.Enable")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"Please note that if a player can both fly, or make a double jump");
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| "+ChatColor.GOLD+"It can cause problems");
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"| ");
		}

		Main.indj.clear();
		
		Bukkit.getScheduler().cancelTask(Main.getInstance().getTabManager().tablistnumber);
		
		for (String s: Main.getInstance().getTabManager().animationtabtask.keySet()) {
			Bukkit.getScheduler().cancelTask(Main.getInstance().getTabManager().animationtabtask.get(s));
			
		}
		
		 /*
	     * Custom join item
	     */

		Main.getInstance().getCjiManager().load();
		
	    OnCommandEvent.cooldowncommands.clear();
		Main.getInstance().getTabManager().animationtabtask.clear();
		Main.getInstance().getTabManager().animationtab.clear();
		
		if (TablistConfig.getConfig().getBoolean("Tablist.enable")) {
			PacketPlayOutPlayerListHeaderFooter = NMSClass.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
		    try {
				newPacketPlayOutPlayerListHeaderFooter = PacketPlayOutPlayerListHeaderFooter.getConstructor(new Class[0]);
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		    ChatComponentText = NMSClass.getNMSClass("ChatComponentText");

			for (String string : TablistConfig.getConfig().getConfigurationSection("Animations").getKeys(false)) {
				if (string.contentEquals("Enable")) continue;

				if (TablistConfig.getConfig().getBoolean("Animations.Enable")) {
					BukkitTask task = new AnimationTabTask(string).runTaskTimer(Main.getInstance(), 20, TablistConfig.getConfig().getInt("Animations." + string + ".refresh-time-ticks"));
					Main.getInstance().getTabManager().animationtabtask.put(string, task.getTaskId());
				}
			}

			BukkitTask tablistmain = new MainTablist(hea, foo, PacketPlayOutPlayerListHeaderFooter, ChatComponentText, newPacketPlayOutPlayerListHeaderFooter).runTaskTimer(Main.getInstance(), 20L, TablistConfig.getConfig().getLong("Tablist.refresh-time-ticks"));
		
			Main.getInstance().getTabManager().tablistnumber = tablistmain.getTaskId();
		}
    			
		Main.getInstance().getBlockExceptions().load();
	}

}