package fr.dianox.hawn.utility.load;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.event.BasicFeatures;
import fr.dianox.hawn.event.FunFeatures;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.event.OnJoin;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.modules.tablist.tab.AnimationTabTask;
import fr.dianox.hawn.modules.tablist.tab.MainTablist;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.NMSClass;
import fr.dianox.hawn.utility.XMaterial;
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
				
		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Enable") && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.Enable")) {
	    	
	    	BasicFeatures.world_voidtp.clear();

			BasicFeatures.world_voidtp.addAll(VoidTPConfig.getConfig().getConfigurationSection("VoidTP.Options.VoidTP-Per-World.World-List").getKeys(false));
	    }
		
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
		
		if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Enable")) {
	    	
	    	Main.interactables.clear();
	    	
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_DOOR")) {
	    		Main.interactables.add(XMaterial.ACACIA_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.ACACIA_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ANVIL")) {
	    		Main.interactables.add(XMaterial.ANVIL.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BEACON")) {
	    		Main.interactables.add(XMaterial.BEACON.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.RED_BED")) {
	    		Main.interactables.add(XMaterial.RED_BED.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_DOOR")) {
	    		Main.interactables.add(XMaterial.BIRCH_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.BIRCH_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BOAT")) {
	    		Main.interactables.add(XMaterial.OAK_BOAT.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BREWING_STAND")) {
	    		Main.interactables.add(XMaterial.BREWING_STAND.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMMAND_BLOCK")) {
	    		Main.interactables.add(XMaterial.COMMAND_BLOCK.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST")) {
	    		Main.interactables.add(XMaterial.CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_DOOR")) {
	    		Main.interactables.add(XMaterial.DARK_OAK_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.DARK_OAK_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DAYLIGHT_DETECTOR")) {
	    		Main.interactables.add(XMaterial.DAYLIGHT_DETECTOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DISPENSER")) {
	    		Main.interactables.add(XMaterial.DISPENSER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DROPPER")) {
	    		Main.interactables.add(XMaterial.DROPPER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENCHANTING_TABLE")) {
	    		Main.interactables.add(XMaterial.ENCHANTING_TABLE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENDER_CHEST")) {
	    		Main.interactables.add(XMaterial.ENDER_CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.OAK_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.FURNACE")) {
	    		Main.interactables.add(XMaterial.FURNACE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER")) {
	    		Main.interactables.add(XMaterial.HOPPER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER_MINECART")) {
	    		Main.interactables.add(XMaterial.HOPPER_MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_DOOR")) {
	    		Main.interactables.add(XMaterial.JUNGLE_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.JUNGLE_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.LEVER")) {
	    		Main.interactables.add(XMaterial.LEVER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
	    		Main.interactables.add(XMaterial.MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.NOTE_BLOCK")) {
	    		Main.interactables.add(XMaterial.NOTE_BLOCK.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
	    		Main.interactables.add(XMaterial.MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMPARATOR")) {
	    		Main.interactables.add(XMaterial.COMPARATOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_SIGN")) {
	    		Main.interactables.add(XMaterial.OAK_SIGN.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST_MINECART")) {
	    		Main.interactables.add(XMaterial.CHEST_MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
	    		Main.interactables.add(XMaterial.OAK_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_TRAPDOOR")) {
	    		Main.interactables.add(XMaterial.OAK_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.TRAPPED_CHEST")) {
	    		Main.interactables.add(XMaterial.TRAPPED_CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BUTTON")) {
	    		Main.interactables.add(XMaterial.OAK_BUTTON.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
	    		Main.interactables.add(XMaterial.OAK_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_DOOR")) {
	    		Main.interactables.add(XMaterial.SPRUCE_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_FENCE_GATE")) {
	    		Main.interactables.add(XMaterial.SPRUCE_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_TRAPDOOR")) {
	    		Main.interactables.add(XMaterial.SPRUCE_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_TRAPDOOR")) {
	    		Main.interactables.add(XMaterial.BIRCH_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_TRAPDOOR")) {
	    		Main.interactables.add(XMaterial.JUNGLE_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_TRAPDOOR")) {
	    		Main.interactables.add(XMaterial.ACACIA_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_TRAPDOOR")) {
	    		Main.interactables.add(XMaterial.DARK_OAK_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SWEET_BERRY_BUSH")) {
	    		Main.interactables.add(XMaterial.SWEET_BERRY_BUSH.parseMaterial());
	    	}
	    }
		
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
	    if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {
	    	
	    	CustomJoinItem.itemcjiname.clear();
	    	CustomJoinItem.itemcjislot.clear();
	    	
		    if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Helmet.Enable")) {
				
				String path_item = "Custom-Join-Item.Items.Armor.Helmet.Item.";
				
				CustomJoinItem.itemcjiname.put("Helmet-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
		    }
			
			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Chestplate.Enable")) {
						
				String path_item = "Custom-Join-Item.Items.Armor.Chestplate.Item.";
				
				CustomJoinItem.itemcjiname.put("Chestplate-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
			}
			
			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Leggings.Enable")) {
				
				String path_item = "Custom-Join-Item.Items.Armor.Leggings.Item.";
				
				CustomJoinItem.itemcjiname.put("Leggings-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
			}
			
			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Boots.Enable")) {
				
				String path_item = "Custom-Join-Item.Items.Armor.Boots.Item.";
				
				CustomJoinItem.itemcjiname.put("Boots-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"), path_item);
			}
			
			// Give items

		    for (String string : ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items").getKeys(false)) {
			    String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";

			    CustomJoinItem.itemcjislot.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), path_item);
			    CustomJoinItem.itemcjislotname.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), string);
		    }
	    }
		
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
    			
		Main.block_exception_break.clear();
		Main.block_exception_place.clear();
	    
	    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Block-Exception.Enable")) {
	    	for (String str: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Place.Block-Exception.Materials")) {
	    		try {
	    			Main.block_exception_place.add(XMaterial.getMat(str, "Protection.Construct.Anti-Place.Block-Exception.Materials"));
	    		} catch (Exception ignored) {}
	    	}
	    }
	    
	    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Block-Exception.Enable")) {
	    	for (String str: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Break.Block-Exception.Materials")) {
	    		try {
	    			Main.block_exception_break.add(XMaterial.getMat(str, "Protection.Construct.Anti-Break.Block-Exception.Materials"));
	    		} catch (Exception ignored) {}
	    	}
	    }
	}

}