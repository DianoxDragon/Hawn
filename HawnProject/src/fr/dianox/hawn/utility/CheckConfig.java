package fr.dianox.hawn.utility;

import java.io.File;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.CommandAliasesConfig;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.ServerListConfig;
import fr.dianox.hawn.utility.config.commands.BroadCastCommandConfig;
import fr.dianox.hawn.utility.config.commands.ClearChatCommandConfig;
import fr.dianox.hawn.utility.config.commands.ClearInvCommandConfig;
import fr.dianox.hawn.utility.config.commands.DelayChatCommandConfig;
import fr.dianox.hawn.utility.config.commands.EmojiCommandConfig;
import fr.dianox.hawn.utility.config.commands.FlyCommandConfig;
import fr.dianox.hawn.utility.config.commands.GamemodeCommandConfig;
import fr.dianox.hawn.utility.config.commands.HealCommandConfig;
import fr.dianox.hawn.utility.config.commands.HelpCommandConfig;
import fr.dianox.hawn.utility.config.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.commands.OptionPlayerConfigCommand;
import fr.dianox.hawn.utility.config.commands.PingCommandConfig;
import fr.dianox.hawn.utility.config.commands.ScoreboardCommandConfig;
import fr.dianox.hawn.utility.config.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.commands.TitleAnnouncerConfig;
import fr.dianox.hawn.utility.config.commands.VanishCommandConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.commands.WeatherTimeCommandConfig;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGLP;
import fr.dianox.hawn.utility.config.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.events.CommandEventConfig;
import fr.dianox.hawn.utility.config.events.ConfigGProtection;
import fr.dianox.hawn.utility.config.events.OnChatConfig;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.events.PlayerEventsConfig;
import fr.dianox.hawn.utility.config.events.PlayerWorldChangeConfigE;
import fr.dianox.hawn.utility.config.events.ProtectionPlayerConfig;
import fr.dianox.hawn.utility.config.events.VoidTPConfig;
import fr.dianox.hawn.utility.config.events.WorldEventConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMEvents;
import fr.dianox.hawn.utility.config.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.ConfigMPlayerOption;
import fr.dianox.hawn.utility.config.messages.administration.AdminPanelConfig;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;
import fr.dianox.hawn.utility.config.tab.TablistConfig;

public class CheckConfig {
	
	public static void moveClassicMessagesToenUS(String datafolder, String path) {

		try {
			File f = new File(datafolder, "/Messages/" + path);
			File moved = new File(datafolder, "/Messages/en_US/" + path);
			
			FileUtils.move(f, moved);
		} catch (Exception e) {}
	}
	
	public static void ConvertOldDataFromNew() {
		File f = new File(Main.getInstance().getDataFolder(), "/StockageInfo/PlayerConfig.yml");
		
		if (f.exists()) {
			YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
			
			Bukkit.getConsoleSender().sendMessage("PLEASE WAIT DURING THE UPDATE");
			
			Iterator < ? > iteratorpi = null;
			Iterator < ? > iteratorpv = null;
			Iterator < ? > iteratorksb = null;
			Iterator < ? > iteratorlp = null;
			Iterator < ? > iteratorpg = null;
			Iterator < ? > iteratorv = null;
			
			// Create new player info
			if (cfg.isSet("player_info")) {
				iteratorpi = cfg.getConfigurationSection("player_info").getKeys(false).iterator();
			
				while (iteratorpi.hasNext()) {
	                String string = (String) iteratorpi.next();
	                ConfigPlayerGet.writeString(string, "player_info.player_name", cfg.getString("player_info."+string+".player_name"));
	            	ConfigPlayerGet.writeString(string, "player_info.join_date", cfg.getString("player_info."+string+".join_date"));
	            	ConfigPlayerGet.writeString(string, "player_info.first_join", cfg.getString("player_info."+string+".first_join"));
	            	ConfigPlayerGet.writeString(string, "player_info.player_ip", cfg.getString("player_info."+string+".player_ip"));
				}
			}
			
			// Create player option pv
			if (cfg.isSet("player_option_pv")) {
				iteratorpv = cfg.getConfigurationSection("player_option_pv").getKeys(false).iterator();
				
				while (iteratorpv.hasNext()) {
	                String string = (String) iteratorpv.next();
	                ConfigPlayerGet.writeBoolean(string, "player_option_pv.Activate", cfg.getBoolean("player_option_pv."+string+".Activate"));
				}
			}
			
			// Create player keep sb
			if (cfg.isSet("player_option_keep_sb")) {
				iteratorksb = cfg.getConfigurationSection("player_option_keep_sb").getKeys(false).iterator();
							
				while (iteratorksb.hasNext()) {
					String string = (String) iteratorksb.next();
					ConfigPlayerGet.writeBoolean(string, "player_option_keep_sb.Activate", cfg.getBoolean("player_option_pv."+string+".Activate"));
					ConfigPlayerGet.writeString(string, "player_option_keep_sb.Scoreboard", cfg.getString("player_option_keep_sb."+string+".Scoreboard"));
				}
			}
			
			// Create player keep sb
			if (cfg.isSet("player_last_position")) {
				iteratorlp = cfg.getConfigurationSection("player_last_position").getKeys(false).iterator();
										
				while (iteratorlp.hasNext()) {
					String string = (String) iteratorlp.next();
					ConfigPlayerGet.writeString(string, "player_last_position.World", cfg.getString("player_last_position."+string+".World"));
					ConfigPlayerGet.writeDouble(string, "player_last_position.X", cfg.getDouble("player_last_position."+string+".X"));
					ConfigPlayerGet.writeDouble(string, "player_last_position.Y", cfg.getDouble("player_last_position."+string+".Y"));
					ConfigPlayerGet.writeDouble(string, "player_last_position.Z", cfg.getDouble("player_last_position."+string+".Z"));
					ConfigPlayerGet.writeFloat(string, "player_last_position.YAW", Float.valueOf(cfg.getString("player_last_position."+string+".YAW")));
					ConfigPlayerGet.writeFloat(string, "player_last_position.PITCH", Float.valueOf(cfg.getString("player_last_position."+string+".PITCH")));
				}
			}
			
			// Create player option pg
			if (cfg.isSet("player_gamemode")) {
				iteratorpg = cfg.getConfigurationSection("player_gamemode").getKeys(false).iterator();
							
				while (iteratorpg.hasNext()) {
					String string = (String) iteratorpg.next();
					ConfigPlayerGet.writeInt(string, "player_gamemode.player_gamemode", cfg.getInt("player_gamemode."+string+".player_gamemode"));
				}
			}
			
			// Create player option v
			if (cfg.isSet("player_vanish")) {
				iteratorv = cfg.getConfigurationSection("player_vanish").getKeys(false).iterator();
										
				while (iteratorv.hasNext()) {
					String string = (String) iteratorv.next();
					ConfigPlayerGet.writeBoolean(string, "player_vanish.vanished", cfg.getBoolean("player_vanish."+string+".vanished"));
				}
			}
			
			f.delete();
		}
	}
	
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
		
		if (!ConfigMCommands.getConfig().isSet("ChatDelay.Admin.Removed")) {
			ConfigMCommands.getConfig().set("ChatDelay.Admin.Removed", java.util.Arrays.asList(new String[] {"%prefix% &7The chat delay has been &cdisabled"}));
			ConfigMCommands.getConfig().set("MuteChat.Admin.On-Time", java.util.Arrays.asList(new String[] {" &4&m&l-<-=->-&r &cThe chat has been locked by &6%player%&c for &6%minutes% minutes &4&m&l-<-=->-"}));
			
			ConfigMCommands.saveConfigFile();
		}
		
		if (!OtherAMConfig.getConfig().isSet("Command.List.Part-One")) {
			OtherAMConfig.getConfig().set("Command.List.Part-One", java.util.Arrays.asList(new String[] {
        		"§8//§7§m---------------§r§8\\\\ §3[§bList§3] §8//§7§m---------------§r§8\\\\",
        		" ",
        		"  &8→ &6&lPage %number%",
        		" "
            }));
        
			OtherAMConfig.getConfig().set("Command.List.Part-Two", java.util.Arrays.asList(new String[] {
        		"§8\\\\§7§m---------------§r§8// §3[§bList§3] §8\\\\§7§m---------------§r§8//"
            }));
			
			CommandAliasesConfig.getConfig().set("List.Enable", false);
			CommandAliasesConfig.getConfig().set("List.Cannot-Be-changed.Main-Command-Is", "list");
			CommandAliasesConfig.getConfig().set("List.Aliases", java.util.Arrays.asList(new String[] {}));
			
			CommandAliasesConfig.saveConfigFile();
        	OtherAMConfig.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Exp.Add.Sender.Enable")) {
			ConfigMCommands.getConfig().set("Exp.Add.Sender.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Add.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7has been added to the &b%target%&7's total experience points"}));
			ConfigMCommands.getConfig().set("Exp.Add.Target.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Add.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%number_exp% &7has been added to your total experience points"}));
         
			ConfigMCommands.getConfig().set("Exp.Set.Sender.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Set.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7's total experience points has been set to &e%number_exp%"}));
			ConfigMCommands.getConfig().set("Exp.Set.Target.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Set.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your total experience points has been set to &e%number_exp%"}));
         
			ConfigMCommands.getConfig().set("Exp.Take.Sender.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Take.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7's lost &e%number_exp%&7 experience points"}));
			ConfigMCommands.getConfig().set("Exp.Take.Target.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Take.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You lost &e%number_exp%&7 experience points"}));
			ConfigMCommands.getConfig().set("Exp.Take.Sender-Error.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Take.Sender-Error.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou can't take more than &6%target_exp_points%&c for the player &e%target%"}));
         
			ConfigMCommands.getConfig().set("Exp.Clear.Sender.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Clear.Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &b%target%&7's has now 0 experience points"}));
			ConfigMCommands.getConfig().set("Exp.Clear.Target.Enable", true);
			ConfigMCommands.getConfig().set("Exp.Clear.Target.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your experience points has been cleared"}));
			
			CommandAliasesConfig.getConfig().set("Exp.Enable", false);
			CommandAliasesConfig.getConfig().set("Exp.Cannot-Be-changed.Main-Command-Is", "exp");
			CommandAliasesConfig.getConfig().set("Exp.Aliases", java.util.Arrays.asList(new String[] {}));
			
			CommandAliasesConfig.saveConfigFile();
			ConfigMCommands.saveConfigFile();
		}
		
		if (!OtherAMConfig.getConfig().isSet("Command.CheckAccount")) {
			OtherAMConfig.getConfig().set("Command.CheckAccount", java.util.Arrays.asList(new String[] {
					"",
                    "  &8→ &r&lPlayer info for&8:&r &b%target%",
                    "&7&lJoin date&r&8:&e %hawn_player_join_date%",
                    "&7&lFirst join date&r&8:&e %hawn_player_first_join_date%",
                    "&7&lIp&r&8:&e %player_ip%",
                    "",
                    "  &8→ &r&lOptions&8:&r &8(&aGreen &8=&7 true / &cRed&8 = &7false&8)",
                    "&7&lPlayer visibility: %pv_point%",
                    "&7&lSpeed: %ps_point% &7(&e%ps_number%&7)",
                    "&7&lFly: %pof_point%",
                    "&7&lDouble jump: %dj_point%",
                    "&7&lAuto broadcast: %ab_point%",
                    "&7&lVanished: %v_point%",
                    "&7&lKeep scoreboard: %ksb_point% &8(&e%scorename%&8)",
                    "&7&lJump Boost: %jb_point%",
                    "",
                    "&7&lGamemode: &e%gm_number%",
                    ""
                }));
			
			CommandAliasesConfig.getConfig().set("CheckAccount.Enable", true);
            CommandAliasesConfig.getConfig().set("CheckAccount.Cannot-Be-changed.Main-Command-Is", "checkaccount");
            CommandAliasesConfig.getConfig().set("CheckAccount.Aliases", java.util.Arrays.asList(new String[] {"checka"}));
			
            CommandAliasesConfig.saveConfigFile();
			OtherAMConfig.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warp.Edit.Warp-Edited.Enable")) {
			
			ConfigMCommands.getConfig().set("Warp.Edit.Warp-Edited.Enable", true);
			ConfigMCommands.getConfig().set("Warp.Edit.Warp-Edited.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The new location of the warp &e%warp% has been set"}));
			
			WarpSetWarpCommandConfig.getConfig().set("EditWarp.Enable", true);
			WarpSetWarpCommandConfig.getConfig().set("EditWarp.Disable-Message", true);
			WarpSetWarpCommandConfig.getConfig().set("EditWarp.DISABLE_THE_COMMAND_COMPLETELY", false);
			
			CommandAliasesConfig.getConfig().set("Warp.Edit-Warp.Enable", false);
			CommandAliasesConfig.getConfig().set("Warp.Edit-Warp.Warp.Cannot-Be-changed.Main-Command-Is", "editwarp");
			CommandAliasesConfig.getConfig().set("Warp.Edit-Warp.Aliases", java.util.Arrays.asList(new String[] {}));
			
			CommandAliasesConfig.saveConfigFile();
			WarpSetWarpCommandConfig.saveConfigFile();
			ConfigMCommands.saveConfigFile();
		}
		
		if (!AutoBroadcastConfig.getConfig().isSet("Config.BossBar.Enable")) {
			/*
             * Auto broadcast
             * BossBar
             */
			AutoBroadcastConfig.getConfig().set("Config.BossBar.Enable", true);
			AutoBroadcastConfig.getConfig().set("Config.BossBar.Random", false);
			AutoBroadcastConfig.getConfig().set("Config.BossBar.Interval", 60);
			AutoBroadcastConfig.getConfig().set("Config.BossBar.Use-Permission-To-Get-Messages", false);
            AutoBroadcastConfig.getConfig().set("Config.BossBar.Options-Default.Color", "PURPLE");
            AutoBroadcastConfig.getConfig().set("Config.BossBar.Options-Default.Style", "SOLID");
            AutoBroadcastConfig.getConfig().set("Config.BossBar.Options-Default.Progress", 0.7D);
            AutoBroadcastConfig.getConfig().set("Config.BossBar.World.All_World", false);
            AutoBroadcastConfig.getConfig().set("Config.BossBar.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.default.Message", "&eDefault message bossbar without settings");
            
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.totalcustom.Message", "&cAll settings has been changed here %player%");
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.totalcustom.Color", "BLUE");
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.totalcustom.Style", "SEGMENTED_20");
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.totalcustom.Progress", 1.0D);
            
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.slightchange.Message", "owo");
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.slightchange.Color", "YELLOW");
            AutoBroadcastConfig.getConfig().set("Config.BossBar.messages.slightchange.Progress", 0.1D);
            
            AutoBroadcastConfig.saveConfigFile();
		}
		
		if (!OnJoinConfig.getConfig().isSet("Boss-Bar.Enable")) {
			OnJoinConfig.getConfig().set("Boss-Bar.Enable", true);

			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Enable", true);
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Message", "&6Welcome %player% &e!!"); 
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Color", "BLUE");
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Style", "SEGMENTED_10");
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Progress", 1D);
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Time.Keep-Bar", true);
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Time.If-not.Time-Stay", 150);
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Time.If-not.Swith-To-OnJoin-BossBar.Enable", true);
			OnJoinConfig.getConfig().set("Boss-Bar.First-Join.Time.If-not.Swith-To-OnJoin-BossBar.Keep-The-BossBar", false);
            
			OnJoinConfig.getConfig().set("Boss-Bar.Join.Enable", true);
            OnJoinConfig.getConfig().set("Boss-Bar.Join.Message", "&6Hello %player%"); 
            OnJoinConfig.getConfig().set("Boss-Bar.Join.Color", "PURPLE");
            OnJoinConfig.getConfig().set("Boss-Bar.Join.Style", "SOLID");
            OnJoinConfig.getConfig().set("Boss-Bar.Join.Progress", 0.7D);
            OnJoinConfig.getConfig().set("Boss-Bar.Join.Time.Keep-Bar", true);
            OnJoinConfig.getConfig().set("Boss-Bar.Join.Time.If-not.Time-Stay", 150);
            
            OnJoinConfig.getConfig().set("Boss-Bar.World.All_World", false);
            OnJoinConfig.getConfig().set("Boss-Bar.World.Keep-BossBar-For-Theses-Worlds", true);
            OnJoinConfig.getConfig().set("Boss-Bar.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            OnJoinConfig.saveConfigFile();
		}
		
		if (!OtherAMConfig.getConfig().isSet("Command.ClearGroundItems")) {
			
			OtherAMConfig.getConfig().set("Command.ClearGroundItems", java.util.Arrays.asList(new String[] {
                "%prefix% &7All items have been cleared"
            }));
			
			OtherAMConfig.getConfig().set("Command.ClearMobs", java.util.Arrays.asList(new String[] {
                "%prefix% &7All mobs have been cleared"
            }));
        
			CommandAliasesConfig.getConfig().set("ClearGroundItems.Enable", true);
			CommandAliasesConfig.getConfig().set("ClearGroundItems.Cannot-Be-changed.Main-Command-Is", "cleargrounditems");
			CommandAliasesConfig.getConfig().set("ClearGroundItems.Aliases", java.util.Arrays.asList(new String[] {"cleargi"}));
			
			CommandAliasesConfig.getConfig().set("ClearMobs.Enable", true);
			CommandAliasesConfig.getConfig().set("ClearMobs.Cannot-Be-changed.Main-Command-Is", "clearmobs");
			CommandAliasesConfig.getConfig().set("ClearMobs.Aliases", java.util.Arrays.asList(new String[] {"clearm"}));
			
			CommandAliasesConfig.saveConfigFile();
        	OtherAMConfig.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.Construct.Anti-Place.Block-Exception.Method")) {
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.Block-Exception.Method", "WHITELIST");
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Break.Block-Exception.Method", "WHITELIST");
			 
			ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.Construct.Anti-Place.Block-Exception.Armor_Stand")) {
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.Block-Exception.Armor_Stand", false);
			 
			ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Hat.Enable")) {
			ConfigMCommands.getConfig().set("Hat.Self.Set.Enable", true);
			ConfigMCommands.getConfig().set("Hat.Self.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7The new hat has been set"}));
			ConfigMCommands.getConfig().set("Hat.Self.Removed.Enable", true);
			ConfigMCommands.getConfig().set("Hat.Self.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7The hat has been removed"}));
            
			ConfigMCommands.getConfig().set("Hat.Other-Target.Set.Enable", true);
			ConfigMCommands.getConfig().set("Hat.Other-Target.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7You got a new hat from &e%player%"}));
			ConfigMCommands.getConfig().set("Hat.Other-Target.Removed.Enable", true);
			ConfigMCommands.getConfig().set("Hat.Other-Target.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7Your hat has been removed from &e%player%"}));
            
			ConfigMCommands.getConfig().set("Hat.Other-Sender.Set.Enable", true);
			ConfigMCommands.getConfig().set("Hat.Other-Sender.Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7You set a new hat for &e%target%"}));
			ConfigMCommands.getConfig().set("Hat.Other-Sender.Removed.Enable", true);
			ConfigMCommands.getConfig().set("Hat.Other-Sender.Removed.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &7You removed the hat of &e%target%"}));
            
			ConfigMCommands.getConfig().set("Hat.Error.No-Hat-Can-Be-Set.Enable", true);
            ConfigMCommands.getConfig().set("Hat.Error.No-Hat-Can-Be-Set.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cSorry but you can't set a new hat"}));
            ConfigMCommands.getConfig().set("Hat.Error.Need-Have-NoEmpty-Helmet.Enable", true);
            ConfigMCommands.getConfig().set("Hat.Error.Need-Have-NoEmpty-Helmet.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% &cSorry but you can't remove the hat, it's empty"}));
            
			ConfigMCommands.getConfig().set("GetPos.Enable", true);
            ConfigMCommands.getConfig().set("GetPos.Messages", java.util.Arrays.asList(new String[] {"&7%prefix% The player location is: &e%X% %Y% %Z%&7 in the world&b %world%"}));
            
            OtherAMConfig.getConfig().set("Command.IP", java.util.Arrays.asList(new String[] {
                    "%prefix% &7The player's ip is: &e%getplayerip%"
                }));
            
            OtherAMConfig.getConfig().set("Command.Kickall", java.util.Arrays.asList(new String[] {
                    "%prefix% &7All player has been kick"
                }));
            
            CommandAliasesConfig.getConfig().set("Hat.Enable", false);
            CommandAliasesConfig.getConfig().set("Hat.Cannot-Be-changed.Main-Command-Is", "hat");
            CommandAliasesConfig.getConfig().set("Hat.Aliases", java.util.Arrays.asList(new String[] {}));
            
            CommandAliasesConfig.getConfig().set("GetPos.Enable", false);
            CommandAliasesConfig.getConfig().set("GetPos.Cannot-Be-changed.Main-Command-Is", "getpos");
            CommandAliasesConfig.getConfig().set("GetPos.Aliases", java.util.Arrays.asList(new String[] {}));
            
            CommandAliasesConfig.getConfig().set("Ip.Enable", false);
            CommandAliasesConfig.getConfig().set("Ip.Cannot-Be-changed.Main-Command-Is", "ip");
            CommandAliasesConfig.getConfig().set("Ip.Aliases", java.util.Arrays.asList(new String[] {}));
            
            CommandAliasesConfig.getConfig().set("KickAll.Enable", false);
            CommandAliasesConfig.getConfig().set("KickAll.Cannot-Be-changed.Main-Command-Is", "kickall");
            CommandAliasesConfig.getConfig().set("KickAll.Aliases", java.util.Arrays.asList(new String[] {}));
            
            CommandAliasesConfig.saveConfigFile();
            OtherAMConfig.saveConfigFile();
            ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Repair.Repaired.Enable")) {
			ConfigMCommands.getConfig().set("Repair.Repaired.Enable", true);
			ConfigMCommands.getConfig().set("Repair.Repaired.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The item &e%item% &7has been fixed"}));
            
			ConfigMCommands.getConfig().set("Repair.Can-t-Repair.Enable", true);
			ConfigMCommands.getConfig().set("Repair.Can-t-Repair.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cSorry, but you can't repair that item"}));
            
			CommandAliasesConfig.getConfig().set("Repair.Enable", true);
			CommandAliasesConfig.getConfig().set("Repair.Cannot-Be-changed.Main-Command-Is", "repair");
			CommandAliasesConfig.getConfig().set("Repair.Aliases", java.util.Arrays.asList(new String[] {"fix"}));
			
			CommandAliasesConfig.saveConfigFile();
			ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("EnderChest.Self.Enable")) {
			ConfigMCommands.getConfig().set("EnderChest.Self.Enable", true);
			ConfigMCommands.getConfig().set("EnderChest.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You opened your enderchest"}));
        
			ConfigMCommands.getConfig().set("EnderChest.Other-Sender.Enable", true);
			ConfigMCommands.getConfig().set("EnderChest.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You opened &e%target%&7's enderchest"}));
        
			ConfigMCommands.getConfig().set("InvSee.Other-Sender.Enable", true);
			ConfigMCommands.getConfig().set("InvSee.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You opened &e%target%&7's inventory"}));
		
			CommandAliasesConfig.getConfig().set("EnderChest.Enable", true);
			CommandAliasesConfig.getConfig().set("EnderChest.Cannot-Be-changed.Main-Command-Is", "enderchest");
			CommandAliasesConfig.getConfig().set("EnderChest.Aliases", java.util.Arrays.asList(new String[] {"ec"}));
            
			CommandAliasesConfig.getConfig().set("InvSee.Enable", false);
            CommandAliasesConfig.getConfig().set("InvSee.Cannot-Be-changed.Main-Command-Is", "invsee");
            CommandAliasesConfig.getConfig().set("InvSee.Aliases", java.util.Arrays.asList(new String[] {}));

            CommandAliasesConfig.saveConfigFile();
        	ConfigMCommands.saveConfigFile();
		}
        
		if (!ConfigMCommands.getConfig().isSet("Suicide.Self.Enable")) {
			ConfigMCommands.getConfig().set("Suicide.Self.Enable", true);
			ConfigMCommands.getConfig().set("Suicide.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You killed yourself"}));
        
			ConfigMCommands.getConfig().set("Suicide.Broadcast.Enable", true);
			ConfigMCommands.getConfig().set("Suicide.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&7%player% said goodbye to the world"}));
		
			CommandAliasesConfig.getConfig().set("Suicide.Enable", false);
			CommandAliasesConfig.getConfig().set("Suicide.Cannot-Be-changed.Main-Command-Is", "suicide");
            CommandAliasesConfig.getConfig().set("Suicide.Aliases", java.util.Arrays.asList(new String[] {}));
			
        	ConfigMCommands.saveConfigFile();
        	CommandAliasesConfig.saveConfigFile();
		}
        
		if (!PlayerOptionMainConfig.getConfig().isSet("General.Enable")) {
			PlayerOptionMainConfig.getConfig().set("General.Enable", true);
			PlayerOptionMainConfig.getConfig().set("Keep.JumpBoost-OnJoin.Enable", false);
			
			PlayerOptionMainConfig.saveConfigFile();
		}
		
		if (!PlayerEventsConfig.getConfig().isSet("Block-Mount.Enable")) {
			PlayerEventsConfig.getConfig().set("Block-Mount.Enable", true);
			PlayerEventsConfig.getConfig().set("Block-Mount.Bypass-With-Permission", false);
			PlayerEventsConfig.getConfig().set("Block-Mount.World.All_World", false);
			PlayerEventsConfig.getConfig().set("Block-Mount.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
			}));
        
			PlayerEventsConfig.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.PlayerInteract-Items-Blocks.Options.SWEET_BERRY_BUSH")) {
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.SWEET_BERRY_BUSH", true);
		
			ConfigGProtection.saveConfigFile();
		}
		
		if (!WorldEventConfig.getConfig().isSet("No-Shears.Enable")) {
			WorldEventConfig.getConfig().set("No-Shears.Enable", true);
			WorldEventConfig.getConfig().set("No-Shears.Bypass", true);
			WorldEventConfig.getConfig().set("No-Shears.WorldGuard.Enable", false);
			WorldEventConfig.getConfig().set("No-Shears.WorldGuard.Method", "WHITELIST");
			WorldEventConfig.getConfig().set("No-Shears.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                "region1",
                "whatyouwant"
            }));
			WorldEventConfig.getConfig().set("No-Shears.World.All_World", false);
			WorldEventConfig.getConfig().set("No-Shears.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
			}));
        
        	WorldEventConfig.saveConfigFile();
		}
		
		if (!OtherAMConfig.getConfig().isSet("Command.SlotView.On")) {
			OtherAMConfig.getConfig().set("Command.SlotView.On", java.util.Arrays.asList(new String[] {
                 "%prefix% &7You can now &asee&7 every slot of a gui or an inventory when you click on it"
             }));
			OtherAMConfig.getConfig().set("Command.SlotView.Off", java.util.Arrays.asList(new String[] {
                 "%prefix% &7The SlotView is &cdisabled"
             }));
         
			OtherAMConfig.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.Anti-Bucket-Use.Enable")) {
			ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.Enable", true);
			ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.Bypass", true);
			ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.WorldGuard.Enable", false);
			ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.WorldGuard.Method", "WHITELIST");
			ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                "region1",
                "whatyouwant"
            }));
			ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.World.All_World", false);
			ConfigGProtection.getConfig().set("Protection.Anti-Bucket-Use.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
			}));
		
        	ConfigGProtection.saveConfigFile();
		}
        
		if (!PlayerEventsConfig.getConfig().isSet("Block-Off-Hand.Enable")) {
			PlayerEventsConfig.getConfig().set("Block-Off-Hand.Enable", true);
			PlayerEventsConfig.getConfig().set("Block-Off-Hand.Bypass-With-Permission", false);
			PlayerEventsConfig.getConfig().set("Block-Off-Hand.World.All_World", false);
			PlayerEventsConfig.getConfig().set("Block-Off-Hand.World.Worlds", java.util.Arrays.asList(new String[] {
					"world",
					"world_nether"
			}));
        
        	PlayerEventsConfig.saveConfigFile();
		}
		
		if (!ConfigCJIGeneral.getConfig().isSet("Custom-Join-Item.General-Option.Use_In_Creative_Mode_In_Any_Case")) {
			ConfigCJIGeneral.getConfig().set("Custom-Join-Item.General-Option.Use_In_Creative_Mode_In_Any_Case", false);
			
			ConfigCJIGeneral.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.Construct.Anti-Place.Block-Exception.Enable")) {
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.Block-Exception.Enable", false);
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Place.Block-Exception.Materials", java.util.Arrays.asList(new String[] {
                    "DIRT"
                }));
            
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Break.Block-Exception.Enable", false);
			ConfigGProtection.getConfig().set("Protection.Construct.Anti-Break.Block-Exception.Materials", java.util.Arrays.asList(new String[] {
                    "DIRT"
                }));
			
			ConfigGProtection.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Urgent-mode.Use-It-Only-On-The-Console")) {
			ServerListConfig.getConfig().set("Urgent-mode.Use-It-Only-On-The-Console", false);
		
			ServerListConfig.saveConfigFile();
		}
		
		if (!HealCommandConfig.getConfig().isSet("Heal.Option.Feed")) {
			HealCommandConfig.getConfig().set("Heal.Option.Feed", true);
			
			HealCommandConfig.saveConfigFile();
		}
		
		if (!GamemodeCommandConfig.getConfig().isSet("Chat-Color-Player.Per-Color-Permission")) {
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Quick-Mode-Change.Enable", true);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Quick-Mode-Change.Default-Mode", 0);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Quick-Mode-Change.Mode1", 0);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Quick-Mode-Change.Mode2", 1);
        
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Hawn-Build-Mode.Enable", false);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too", true);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-0", false);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-1", true);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-2", false);
			GamemodeCommandConfig.getConfig().set("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-3", false);
		
        	GamemodeCommandConfig.saveConfigFile();
		}
		
		if (!OnChatConfig.getConfig().isSet("Chat-Color-Player.Per-Color-Permission")) {
			OnChatConfig.getConfig().set("Chat-Color-Player.Per-Color-Permission", false);
		
			OnChatConfig.saveConfigFile();
		}
		
		if (!CommandEventConfig.getConfig().isSet("Block-Commands.Options.Notify-Staff")) {
			CommandEventConfig.getConfig().set("Block-Commands.Options.Notify-Staff", true);
			 
			OtherAMConfig.getConfig().set("Command-Blocker.Notify-Staff", java.util.Arrays.asList(new String[] {
                    "%prefix% &e%player% tried to do this command: &b%arg1%"
            		}));
			
			OtherAMConfig.saveConfigFile();
			CommandEventConfig.saveConfigFile();
		}
		
		if (!CommandEventConfig.getConfig().isSet("Block-Commands.Options.Face-Guardian-1-13-1-14")) {
			CommandEventConfig.getConfig().set("Block-Commands.Options.Face-Guardian-1-13-1-14", true);
			
			CommandEventConfig.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_TRAPDOOR")) {
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_TRAPDOOR", true);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_TRAPDOOR", true);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_TRAPDOOR", true);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_TRAPDOOR", true);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_TRAPDOOR", true);
        
        	ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_FENCE_GATE")) {
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_FENCE_GATE", true);
			
			ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_DOOR")) {
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_DOOR", true);
			
			ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigMEvents.getConfig().isSet("LaunchPad.Cant-Use-Cooldown.Enable")) {
			ConfigMEvents.getConfig().set("LaunchPad.Cant-Use-Cooldown.Enable", true);
			ConfigMEvents.getConfig().set("LaunchPad.Cant-Use-Cooldown.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Sorry but you can't use the launchpad for now"}));
		
			ConfigGLP.getConfig().set("JumpPads.Cooldown.Enable", true);
			ConfigGLP.getConfig().set("JumpPads.Cooldown.Ticks", 60);
			
			ConfigGLP.saveConfigFile();
            ConfigMEvents.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Anti-WDL.Kick-Message")) {
			ServerListConfig.getConfig().set("Anti-WDL.Kick-Message", "&cSorry you used A world downloader");
			
			ServerListConfig.saveConfigFile();
		}
		
		if (!AdminPanelConfig.getConfig().isSet("Error.Not-listed")) {
			AdminPanelConfig.getConfig().set("Error.Not-listed", java.util.Arrays.asList(new String[] {
            		"%prefix% Sorry but you are not listed to use the command (configuration files)"
            		}));
			
			AdminPanelConfig.getConfig().set("Warning.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
            		"%prefix% &7A modification has been detected by &e%player%&7 on the admin panel",
                    "%prefix% &e%arg1%&7 in the file&b %arg2%"
            		}));
			
			AdminPanelConfig.saveConfigFile();
		}
		
		if (!OtherAMConfig.getConfig().isSet("Urgent-mode.On")) {
			OtherAMConfig.getConfig().set("Urgent-mode.On", java.util.Arrays.asList(new String[] {
                "%prefix% &7You &aenabled&7 the urgent mode"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Off", java.util.Arrays.asList(new String[] {
                "%prefix% &7You &cdisabled&7 the urgent mode"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(new String[] {
                " &4* &cThe urgent mode is &eon&4 *"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(new String[] {
        		" &4* &cThe urgent mode is &eoff&4 *"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Zip", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cA backup of Hawn has been made"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Error-Disable", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cError, you need to be on the console to disable the urgent mode"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cSorry but you can't use the command"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cA modification has been detected by %player% on the admin panel",
                "%arg1% in the file %arg2%"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cAll plugins have been disabled"
        		}));
			OtherAMConfig.getConfig().set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &7All plugins have been &aenabled",
                "&ePlease, a restart is needed to avoid any problems"
        		}));
        
        	OtherAMConfig.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Urgent-mode.Enable")) {
			
			ServerListConfig.getConfig().set("Urgent-mode.Enable", false);
			ServerListConfig.getConfig().set("Urgent-mode.Plugin-desactivation.Disable-All-Plugins-When-Enabled", true);
			ServerListConfig.getConfig().set("Urgent-mode.Plugin-desactivation.Plugin-Ignored", java.util.Arrays.asList(new String[] {
            		"Hawn"
            }));
			ServerListConfig.getConfig().set("Urgent-mode.Kick-Message", "&cThe multi line \n&bworks like that %player%");
			ServerListConfig.getConfig().set("Urgent-mode.whitelist", java.util.Arrays.asList(new String[] {
            		"Dianox"
            }));
			ServerListConfig.getConfig().set("Urgent-mode.Can-Use-Urgent-Mode", java.util.Arrays.asList(new String[] {
            		"Dianox"
            }));
            
			ServerListConfig.getConfig().set("Motd.Urgent.Enable", true);
			ServerListConfig.getConfig().set("Motd.Urgent.Line-1", "&cThe server is whitelisted for now");
			ServerListConfig.getConfig().set("Motd.Urgent.Line-2", "&ePlease come back later");
			
			ServerListConfig.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
			ConfigMCommands.getConfig().set("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable", true);
			ConfigMCommands.getConfig().set("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe player %target% is already in the right gamemode"}));
		
        	ConfigMCommands.saveConfigFile();
		}
		
		if (!AdminPanelConfig.getConfig().isSet("Special.Item.Hawn-Main-Menu-Configuration.Name")) {
			AdminPanelConfig.getConfig().set("Special.Item.Hawn-Main-Menu-Configuration.Name", "&eHawn configuration");
			AdminPanelConfig.getConfig().set("Special.Item.Reload-Hawn.Name", "&eReload Hawn");
        
        	AdminPanelConfig.saveConfigFile();
		}
		
		if (!ConfigGeneral.getConfig().isSet("Plugin.Language-Type")) {
			ConfigGeneral.getConfig().set("Plugin.Language-Type", "en_US");
			
			ConfigGeneral.saveConfigFile();
		}
		
		if (!PlayerWorldChangeConfigE.getConfig().isSet("Execute-Command.Enable")) {
			PlayerWorldChangeConfigE.getConfig().set("Execute-Command.Enable", true);
	        
			PlayerWorldChangeConfigE.getConfig().set("Execute-Command.Options.When-Enter-in-The-World.world.Enable", true);
			PlayerWorldChangeConfigE.getConfig().set("Execute-Command.Options.When-Enter-in-The-World.world.Command-List", java.util.Arrays.asList(new String[] {
	                "Enter 1"
	            }));
	        
			PlayerWorldChangeConfigE.getConfig().set("Execute-Command.Options.When-Enter-in-The-World.world_nether.Enable", true);
			PlayerWorldChangeConfigE.getConfig().set("Execute-Command.Options.When-Enter-in-The-World.world_nether.Command-List", java.util.Arrays.asList(new String[] {
	                "Enter 2"
	            }));
	        
			PlayerWorldChangeConfigE.getConfig().set("Execute-Command.World.All_World", false);
			PlayerWorldChangeConfigE.getConfig().set("Execute-Command.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	            }));
	        
	        PlayerWorldChangeConfigE.saveConfigFile();
		}
		
		if (!PlayerEventsConfig.getConfig().isSet("Items.Drop.WorldGuard.Enable")) {
			
			PlayerEventsConfig.getConfig().set("Items.Drop.WorldGuard.Enable", false);
			PlayerEventsConfig.getConfig().set("Items.Drop.WorldGuard.Method", "WHITELIST");
			PlayerEventsConfig.getConfig().set("Items.Drop.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            
			PlayerEventsConfig.getConfig().set("Items.PickUp.WorldGuard.Enable", false);
			PlayerEventsConfig.getConfig().set("Items.PickUp.WorldGuard.Method", "WHITELIST");
			PlayerEventsConfig.getConfig().set("Items.PickUp.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            
			PlayerEventsConfig.getConfig().set("Items.Move.WorldGuard.Enable", false);
			PlayerEventsConfig.getConfig().set("Items.Move.WorldGuard.Method", "WHITELIST");
			PlayerEventsConfig.getConfig().set("Items.Move.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
			
			PlayerEventsConfig.saveConfigFile();
		}
		
		if (!PlayerOptionMainConfig.getConfig().isSet("Options.Flying.Put-boots")) {
			PlayerOptionMainConfig.getConfig().set("Options.Flying.Put-boots", true);
			
			PlayerOptionMainConfig.saveConfigFile();
		}
		
		if (!TablistConfig.getConfig().isSet("Animations.Enable")) {
			
			TablistConfig.getConfig().set("Animations.Enable", true);
			
			TablistConfig.saveConfigFile();
		}
		
		if (!TablistConfig.getConfig().isSet("Animations.Enable") && !TablistConfig.getConfig().isSet("Animations.separator.refresh-time-ticks")) {
			TablistConfig.getConfig().set("Animations.Enable", true);
			TablistConfig.getConfig().set("Animations.separator.refresh-time-ticks", 2);
			TablistConfig.getConfig().set("Animations.separator.text", java.util.Arrays.asList(new String[] {
        		"&e&l>> &8&m-------------------&r &e&l<<",
        		"&7&l>&e&l> &8&m-------------------&r &e&l<&7&l<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&7&l>> &8&m-------------------&7 &7&l<<",
        		"&e&l>&7&l> &8&m-------------------&7 &7&l<&e&l<",
        		"&e&l>> &8&m-------------------&7 &e&l<<"
        		}));
        
			TablistConfig.getConfig().set("Animations.hawntitle.refresh-time-ticks", 2);
			TablistConfig.getConfig().set("Animations.hawntitle.text", java.util.Arrays.asList(new String[] {
        		"&8&l> &7&lHawn&8&l <",
        		"&8&l> &7&lHaw&8&l <",
        		"&8&l> &7&lHa&8&l <",
        		"&8&l> &7&lH&8&l <",
        		"&8&l> &8&l <",
        		"&8&l< &8&l >",
        		"&8&l< &7&lH&8&l >",
        		"&8&l< &7&lHa&8&l >",
        		"&8&l< &7&lHaw&8&l >",
        		"&8&l< &7&lHawn&8&l >",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&e&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn",
        		"&7&lHawn"
        		}));
        
			TablistConfig.getConfig().set("Animations.website.refresh-time-ticks", 60);
			TablistConfig.getConfig().set("Animations.website.text", java.util.Arrays.asList(new String[] {
        		"&7Discord&8: &eLink 1",
        		"&7Shop&8: &eLink 2",
        		"&7Website&8: &eLink 3"
        		}));
        
			TablistConfig.getConfig().set("Animations.maininformations.refresh-time-ticks", 60);
			TablistConfig.getConfig().set("Animations.maininformations.text", java.util.Arrays.asList(new String[] {
        		"&7Time&8: &e%gettime%",
        		"&e%player_x% %player_y% %player_z%"
        		}));
        
        	TablistConfig.saveConfigFile();
		}
        
		
		if (!ServerListConfig.getConfig().isSet("Motd.Maintenance.Enable")) {
			ServerListConfig.getConfig().set("Maintenance.Enable", false);
			ServerListConfig.getConfig().set("Maintenance.Kick-Message", "&cThe multi line \n&bworks like that %player%");
			ServerListConfig.getConfig().set("Maintenance.whitelist", java.util.Arrays.asList(new String[] {
					"Dianox"
			}));
	            
			ServerListConfig.getConfig().set("Motd.Maintenance.Enable", true);
			ServerListConfig.getConfig().set("Motd.Maintenance.Line-1", "&cThe server is in maintenance");
			ServerListConfig.getConfig().set("Motd.Maintenance.Line-2", "&bPlease come back later");
			
			OtherAMConfig.getConfig().set("Maintenance.On", java.util.Arrays.asList(new String[] {
                    "%prefix% &7You &aenabled&7 the maintenance"
            		}));
			OtherAMConfig.getConfig().set("Maintenance.Off", java.util.Arrays.asList(new String[] {
                    "%prefix% &7You &cdisabled&7 the maintenance"
            		}));
			
			OtherAMConfig.getConfig().set("Maintenance.Broadcast.On", java.util.Arrays.asList(new String[] {
                    " &4* &cThe maintenance is &eon&4 *"
            		}));
			OtherAMConfig.getConfig().set("Maintenance.Broadcast.Off", java.util.Arrays.asList(new String[] {
            		" &4* &cThe maintenance is &eoff&4 *"
            		}));
			
			OtherAMConfig.saveConfigFile();
			ServerListConfig.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Spawn.List.Enable")) {
			ConfigMCommands.getConfig().set("Spawn.List.Enable", true);
			ConfigMCommands.getConfig().set("Spawn.List.Messages", java.util.Arrays.asList(new String[] {"%prefix% &bSpawnlist :&e %spawnlist%"}));
        
			ConfigMCommands.getConfig().set("Spawn.No-Spawn.Enable", true);
			ConfigMCommands.getConfig().set("Spawn.No-Spawn.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cI'm sorry, but there are no spawn"}));
		
			SpawnCommandConfig.getConfig().set("SpawnList.Enable", true);
			SpawnCommandConfig.getConfig().set("SpawnList.Disable-Message", true);
			SpawnCommandConfig.getConfig().set("SpawnList.DISABLE_THE_COMMAND_COMPLETELY", false);
			
			CommandAliasesConfig.getConfig().set("SpawnList.Enable", false);
			CommandAliasesConfig.getConfig().set("SpawnList.Cannot-Be-changed.Main-Command-Is", "spawnlist");
			CommandAliasesConfig.getConfig().set("SpawnList.Aliases", java.util.Arrays.asList(new String[] {}));
			
			SpawnCommandConfig.saveConfigFile();
			CommandAliasesConfig.saveConfigFile();
        	ConfigMCommands.saveConfigFile();
		}
		
		if (!CommandAliasesConfig.getConfig().isSet("Gotop.Enable")) {
			CommandAliasesConfig.getConfig().set("Gotop.Enable", false);
			CommandAliasesConfig.getConfig().set("Gotop.Cannot-Be-changed.Main-Command-Is", "gotop");
			CommandAliasesConfig.getConfig().set("Gotop.Aliases", java.util.Arrays.asList(new String[] {}));
			
			ConfigMCommands.getConfig().set("Gotop.Enable", true);
			ConfigMCommands.getConfig().set("Gotop.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been teleported to the highest block of your position"}));
			
			ConfigMCommands.saveConfigFile();
        	CommandAliasesConfig.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Feed.Self.Enable")) {
			ConfigMCommands.getConfig().set("Feed.Self.Enable", true);
			ConfigMCommands.getConfig().set("Feed.Self.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been fed"}));
        
			ConfigMCommands.getConfig().set("Feed.Other.Enable", true);
			ConfigMCommands.getConfig().set("Feed.Other.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7You have been fed by &e%player%"}));
			ConfigMCommands.getConfig().set("Feed.Other-Sender.Enable", true);
			ConfigMCommands.getConfig().set("Feed.Other-Sender.Messages", java.util.Arrays.asList(new String[] {"%prefix% &e%target%&7 has been fed"}));
		
        	ConfigMCommands.saveConfigFile();
		}
        
		if (!CommandAliasesConfig.getConfig().isSet("Feed.Enable")) {
			
			CommandAliasesConfig.getConfig().set("Feed.Enable", false);
			CommandAliasesConfig.getConfig().set("Feed.Cannot-Be-changed.Main-Command-Is", "feed");
			CommandAliasesConfig.getConfig().set("Feed.Aliases", java.util.Arrays.asList(new String[] {}));
			
			CommandAliasesConfig.saveConfigFile();
		}
		
		if (!AutoBroadcastConfig.getConfig().isSet("Config.Messages.Options.Auto-Center")) {
			AutoBroadcastConfig.getConfig().set("Config.Messages.Options.Auto-Center", false);
			
			ConfigMPlayerOption.getConfig().set("PlayerOption.AutoBroadcast.Enable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The autobroadcast visibility has been &aactivated"}));
			ConfigMPlayerOption.getConfig().set("PlayerOption.AutoBroadcast.Disable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The autobroadcast visibility has been &cdisabled"}));
			
			ConfigMPlayerOption.saveConfigFile();
			AutoBroadcastConfig.saveConfigFile();
		}
		
		if (!WorldEventConfig.getConfig().isSet("World.Time.Always-Day.Enable")) {
			WorldEventConfig.getConfig().set("World.Time.Always-Day.Enable", true);
			WorldEventConfig.getConfig().set("World.Time.Always-Day.World.All_World", false);
			WorldEventConfig.getConfig().set("World.Time.Always-Day.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world"
            }));
			
			WorldEventConfig.getConfig().set("World.Time.Always-Night.Enable", true);
			WorldEventConfig.getConfig().set("World.Time.Always-Night.World.All_World", false);
			WorldEventConfig.getConfig().set("World.Time.Always-Night.World.Worlds", java.util.Arrays.asList(new String[] {
                    "worldtest"
            }));
            
            WorldEventConfig.saveConfigFile();
		}
		
		if (!PlayerWorldChangeConfigE.getConfig().isSet("Player-Options.If-Not-Keeping.Options-Default.JumpBoost")) {
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Options-Default.JumpBoost", false);
			
			WorldEventConfig.getConfig().set("World.Burn.Disable.Burn-Block.WorldGuard.Enable", false);
			WorldEventConfig.getConfig().set("World.Burn.Disable.Burn-Block.WorldGuard.Method", "WHITELIST");
			WorldEventConfig.getConfig().set("World.Burn.Disable.Burn-Block.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            
			WorldEventConfig.getConfig().set("World.Burn.Disable.BlockIgnite-FireSpread.WorldGuard.Enable", false);
			WorldEventConfig.getConfig().set("World.Burn.Disable.BlockIgnite-FireSpread.WorldGuard.Method", "WHITELIST");
			WorldEventConfig.getConfig().set("World.Burn.Disable.BlockIgnite-FireSpread.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
            
			WorldEventConfig.getConfig().set("World.Explosion.Disable.Explosion.WorldGuard.Enable", false);
			WorldEventConfig.getConfig().set("World.Explosion.Disable.Explosion.WorldGuard.Method", "WHITELIST");
			WorldEventConfig.getConfig().set("World.Explosion.Disable.Explosion.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                    "region1",
                    "whatyouwant"
                }));
			
            WorldEventConfig.saveConfigFile();
			PlayerWorldChangeConfigE.saveConfigFile();
		}
		
		if (VoidTPConfig.getConfig().isSet("VoidTP.Options.Fireworks.Amount")) {
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Amount", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Height", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Flicker", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Trail", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Type", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Instant-explode", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Power", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Colors", null);
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Fade", null);
			
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Firework-List", java.util.Arrays.asList(new String[] {
                    "[FWLU]: Firework1"
            }));
			
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Amount", null);
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Height", null);
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Flicker", null);
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Trail", null);
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Type", null);
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Instant-explode", null);
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Power", null);
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Colors", null);
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Fade", null);
			
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Firework-List", java.util.Arrays.asList(new String[] {
                    "[FWLU]: Firework2"
            }));
	        
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Amount", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Height", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Flicker", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Trail", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Type", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Instant-explode", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Power", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Colors", null);
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Fade", null);
	        
	        ConfigGCos.getConfig().set("Cosmetics.Firework.Options.Firework-List", java.util.Arrays.asList(new String[] {
                    "[FWLU]: Firework1"
            }));
	        
	        ConfigGCos.saveConfigFile();
	        TitleAnnouncerConfig.saveConfigFile();
			VoidTPConfig.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_DOOR")) {
		ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_DOOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_FENCE_GATE", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.ANVIL", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.BEACON", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.RED_BED", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_DOOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_FENCE_GATE", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.OAK_BOAT", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.BREWING_STAND", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.COMMAND_BLOCK", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.CHEST", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_DOOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_FENCE_GATE", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.DAYLIGHT_DETECTOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.DISPENSER", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.DROPPER", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.ENCHANTING_TABLE", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.ENDER_CHEST", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.OAK_FENCE_GATE", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.FURNACE", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.HOPPER", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.HOPPER_MINECART", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_DOOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_FENCE_GATE", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.LEVER", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.MINECART", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.NOTE_BLOCK", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.MINECART", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.COMPARATOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.OAK_SIGN", false);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.CHEST_MINECART", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.OAK_TRAPDOOR", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.TRAPPED_CHEST", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.OAK_BUTTON", true);
        ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR", true);
        
        ProtectionPlayerConfig.getConfig().set("Anti-Damage.WorldGuard.Enable", false);
        ProtectionPlayerConfig.getConfig().set("Anti-Damage.WorldGuard.Method", "WHITELIST");
        ProtectionPlayerConfig.getConfig().set("Anti-Damage.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                "region1",
                "whatyouwant"
            }));
        
        ProtectionPlayerConfig.saveConfigFile();
        ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigGProtection.getConfig().isSet("Protection.PlayerInteract-Items-Blocks.Enable")) {
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Enable", true);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.Bypass", true);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.WorldGuard.Enable", false);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.WorldGuard.Method", "WHITELIST");
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.WorldGuard.Regions", java.util.Arrays.asList(new String[] {
                "region1",
                "whatyouwant"
            }));
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.World.All_World", false);
			ConfigGProtection.getConfig().set("Protection.PlayerInteract-Items-Blocks.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
			}));
        
        	ConfigGProtection.saveConfigFile();
		}
		
		if (!ConfigGLP.getConfig().isSet("JumpPads.Sounds.Play-for-all-players")) {
			
			ConfigGLP.getConfig().set("JumpPads.Sounds.Play-for-all-players", true);
			
			ConfigGLP.getConfig().set("JumpPads.Send-Message.Enable", true);
			ConfigGLP.getConfig().set("JumpPads.Send-Message.Messages", java.util.Arrays.asList(new String[] {
                    "%prefix% &eWhoosh!"
            }));
			
			ConfigGLP.saveConfigFile();
		}
		
		if (!CommandAliasesConfig.getConfig().isSet("ActionBarAnnouncer.Enable")) {
			CommandAliasesConfig.getConfig().set("ActionBarAnnouncer.Enable", true);
			CommandAliasesConfig.getConfig().set("ActionBarAnnouncer.Cannot-Be-changed.Main-Command-Is", "actionbarannouncer");
			CommandAliasesConfig.getConfig().set("ActionBarAnnouncer.Aliases", java.util.Arrays.asList(new String[] {"bacast", "aba"}));
            
            CommandAliasesConfig.saveConfigFile();
		}
		
		if (!PlayerEventsConfig.getConfig().isSet("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.Enable")) {
			PlayerEventsConfig.getConfig().set("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.Enable", true);
			PlayerEventsConfig.getConfig().set("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.World.All_World", false);
			PlayerEventsConfig.getConfig().set("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
			}));
        	
        	PlayerEventsConfig.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
			ConfigMCommands.getConfig().set("Gamemode.Error.Alread-In-The-Good-GM.Enable", true);
			ConfigMCommands.getConfig().set("Gamemode.Error.Alread-In-The-Good-GM.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou are already in the right gamemode"}));
		
            ConfigMCommands.saveConfigFile();
		}
		
		if (!VoidTPConfig.getConfig().isSet("VoidTP.Options.Fireworks.Enable")) {
			VoidTPConfig.getConfig().set("VoidTP.Options.Fireworks.Enable", true);
        
			VoidTPConfig.getConfig().set("VoidTP.Options.Execute-Commands.Enable", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.Execute-Commands.Commands", java.util.Arrays.asList(new String[] {
                "[command-player]: you can execute your custom commands too",
                "[command-player]: to send titles for exemple",
                "[command-console]: And More commands"
            }));
        
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.Enable", true);
        
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.Enable", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.VoidTP", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.Custom-Spawn.Enable", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.Custom-Spawn.Spawn", "CHANGE ME");
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.TP-y", 0);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.Execute-Commands.Enable", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.Execute-Commands.Override-Default-Commands", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world.Execute-Commands.Commands", java.util.Arrays.asList(new String[] {
                "[command-player]: a command",
                "You can put if you want, custom commands",
                "For one world only"
            }));
        
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.Enable", false);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.VoidTP", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.Custom-Spawn.Enable", true);
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.Custom-Spawn.Spawn", "CHANGE ME");
			VoidTPConfig.getConfig().set("VoidTP.Options.VoidTP-Per-World.World-List.world_nether.TP-y", 0);
		
        	VoidTPConfig.saveConfigFile();
		}
		
		if (!TitleAnnouncerConfig.getConfig().isSet("Title-Announcer.Options.Write-In-The-Chat-The-Announce")) {
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Write-In-The-Chat-The-Announce", false);
	        
			TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Firework.Enable", true);
	        
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Sound-For-All-Players.Enable", true);
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Sound-For-All-Players.Sound", "BLOCK_NOTE_HARP");
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Sound-For-All-Players.Volume", 1);
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Options.Sound-For-All-Players.Pitch", 1);
	        
	        TitleAnnouncerConfig.getConfig().set("Title.Disable-Message", null);
	        TitleAnnouncerConfig.getConfig().set("Title-Announcer.Disable-Message", true);
	        
	        TitleAnnouncerConfig.saveConfigFile();
		}
		
		if (!VanishCommandConfig.getConfig().isSet("Vanish.Action-Bar.Message-blinking")) {
			VanishCommandConfig.getConfig().set("Vanish.Action-Bar.Message-blinking", true);
			
			VanishCommandConfig.saveConfigFile();
		}
		
		if (!OtherAMConfig.getConfig().isSet("Vanish.Vanish-On")) {
			OtherAMConfig.getConfig().set("Vanish.Vanish-On", java.util.Arrays.asList(new String[] {
                "&7[ %player% is now vanished ]"
        		}));
			OtherAMConfig.getConfig().set("Vanish.Vanish-Off", java.util.Arrays.asList(new String[] {
                "&7[ %player% is now no longer vanished ]"
        		}));
			OtherAMConfig.getConfig().set("Vanish.Vanish-On-Others", java.util.Arrays.asList(new String[] {
                "&7[ %target% is now vanished by %player% ]"
        		}));
			OtherAMConfig.getConfig().set("Vanish.Vanish-Off-Others", java.util.Arrays.asList(new String[] {
                "&7[ %target% is now no longer vanished by %player% ]"
        		}));
        
			OtherAMConfig.saveConfigFile();
		}
		
		if (!AutoBroadcastConfig.getConfig().isSet("Config.Messages.Custom-Header-Footer.Header.Enable")) {
			AutoBroadcastConfig.getConfig().set("Config.Messages.Custom-Header-Footer.Header.Enable", false);
			AutoBroadcastConfig.getConfig().set("Config.Messages.Custom-Header-Footer.Header.messages", java.util.Arrays.asList(new String[] {
                "<--center--> &8<===========================>",
                ""
			}));
			AutoBroadcastConfig.getConfig().set("Config.Messages.Custom-Header-Footer.Footer.Enable", false);
			AutoBroadcastConfig.getConfig().set("Config.Messages.Custom-Header-Footer.Footer.messages", java.util.Arrays.asList(new String[] {
        		"",
                "<--center--> &8<===========================>"
			}));
        
        	AutoBroadcastConfig.saveConfigFile();
		}
		
		if (!ConfigGCos.getConfig().isSet("Cosmetics.Lightning-Strike.Enable")) {
			ConfigGCos.getConfig().set("Cosmetics.Lightning-Strike.Enable", true);
			ConfigGCos.getConfig().set("Cosmetics.Lightning-Strike.Bypass", false);
			ConfigGCos.getConfig().set("Cosmetics.Lightning-Strike.Options.First-Join-Only", false);
			ConfigGCos.getConfig().set("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes", 3);

			ConfigGCos.getConfig().set("Cosmetics.Lightning-Strike.World.All_World", false);
			ConfigGCos.getConfig().set("Cosmetics.Lightning-Strike.World.Worlds", java.util.Arrays.asList(new String[] {
	                "world",
	                "world_nether"
	            }));
	        
	        ConfigGCos.saveConfigFile();
		}
		
		if (!PlayerWorldChangeConfigE.getConfig().isSet("Player-Options.Enable")) {
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.Enable", true);
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.Keep-Options", true);
        
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Reset-settings-on-world-change", false);
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Reset-When-Enter-Or-Leave-A-World.False-Is-Leave", false);
        
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Options-Default.GameMode.Enable", true);
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value", 1);
        
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Options-Default.Fly", true);
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Options-Default.DoubleJump", false);
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility", false);
        
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.World.All_World", false);
			PlayerWorldChangeConfigE.getConfig().set("Player-Options.World.Worlds", java.util.Arrays.asList(new String[] {
                "world",
                "world_nether"
            }));
			
			PlayerWorldChangeConfigE.saveConfigFile();
		}
		
		if (!AutoBroadcastConfig.getConfig().isSet("Config.Titles.Enable")) {
			 /*
             * Auto broadcast
             * Titles
             */
            AutoBroadcastConfig.getConfig().set("Config.Titles.Enable", true);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Random", false);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Interval", 60);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Use-Permission-To-Get-Messages", false);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Options-Default.Title.FadeIn", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Options-Default.Title.Stay", 30);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Options-Default.Title.FadeOut", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Options-Default.SubTitle.FadeIn", 30);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Options-Default.SubTitle.Stay", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.Options-Default.SubTitle.FadeOut", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.World.All_World", false);
            AutoBroadcastConfig.getConfig().set("Config.Titles.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.default.Title.Message", "Default message one");
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.default.SubTitle.Message", "With default values - Config your autobroadcast");
            
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.Title.FadeIn", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.Title.Stay", 150);
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.Title.FadeOut", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.Title.Message", "This is a full custom title");
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.SubTitle.FadeIn", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.SubTitle.Stay", 150);
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.SubTitle.FadeOut", 20);
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.custom.SubTitle.Message", "Without default values - Config your autobroadcast");
            
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.No-title-omg.SubTitle.Message", "Only subtitles - Config your autobroadcast");
            
            AutoBroadcastConfig.getConfig().set("Config.Titles.messages.No-subtitle-omgx2.SubTitle.Message", "Only titles (autobroadcast)");
            
            /*
             * Auto broadcast
             * Action bar
             */
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.Enable", true);
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.Random", false);
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.Interval", 60);
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.Use-Permission-To-Get-Messages", false);
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.Options-Default.Time-Stay", 120);
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.World.All_World", false);
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Messages
            
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.messages.default.Message", "&eDefault Action-Bar &7(autobroadcast)");
            
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.messages.custom.Time-Stay", 60);
            AutoBroadcastConfig.getConfig().set("Config.Action-Bar.messages.custom.Message", "&6custom Action-Bar &7(autobroadcast)");
            
            AutoBroadcastConfig.saveConfigFile();
		}
		
		if (!OnChatConfig.getConfig().isSet("Chat-Mention.Mentionned.Self-Mention.Enable")) {
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Self-Mention.Enable", true);
        
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Chat-Highlight.Enable", true);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Chat-Highlight.Highlighting", "&6&l");
        
        	OnChatConfig.saveConfigFile();
		}
        
		if (!OnChatConfig.getConfig().isSet("Chat-Mention.Enable")) {
			OnChatConfig.getConfig().set("Chat-Mention.Enable", true);
        
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Sound.Enable", true);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Sound.Sound", "BLOCK_NOTE_HARP");
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Sound.Volume", 1);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Sound.Pitch", 1);
        
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Message.Enable", true);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Message.Messages", java.util.Arrays.asList(new String[] {
                "%prefix% You have been mentionned by %sender%"
            }));
        
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-ActionBar.Enable", true);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-ActionBar.Options.Message", "&bYou have been mentionned by &e&l%sender%");
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-ActionBar.Options.Time-Stay", 150);
        
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Enable", true);
        
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.Title.Enable", true);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.Title.FadeIn", 20);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.Title.Stay", 150);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.Title.FadeOut", 20);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.Title.Message", "&6✉ &bYou have been &ementionned&6 ✉");
        
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.SubTitle.Enable", true);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.SubTitle.FadeIn", 20);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.SubTitle.Stay", 150);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.SubTitle.FadeOut", 20);
			OnChatConfig.getConfig().set("Chat-Mention.Mentionned.Send-Title.Options.SubTitle.Message", "&bAnswer to &e%sender%");
        
        	OnChatConfig.saveConfigFile();
		}
		
		if (!SpawnCommandConfig.getConfig().isSet("SetSpawn.Enable")) {
			SpawnCommandConfig.getConfig().set("SetSpawn.Enable", true);
			SpawnCommandConfig.getConfig().set("SetSpawn.Disable-Message", true);
			SpawnCommandConfig.getConfig().set("SetSpawn.DISABLE_THE_COMMAND_COMPLETELY", false);
            
			SpawnCommandConfig.getConfig().set("DelSpawn.Enable", true);
			SpawnCommandConfig.getConfig().set("DelSpawn.Disable-Message", true);
			SpawnCommandConfig.getConfig().set("DelSpawn.DISABLE_THE_COMMAND_COMPLETELY", false);
            
			 CommandAliasesConfig.getConfig().set("SetSpawn.Enable", true);
			 CommandAliasesConfig.getConfig().set("SetSpawn.Cannot-Be-changed.Main-Command-Is", "setspawn");
			 CommandAliasesConfig.getConfig().set("SetSpawn.Aliases", java.util.Arrays.asList(new String[] {}));
	            
			 CommandAliasesConfig.getConfig().set("DelSpawn.Enable", true);
			 CommandAliasesConfig.getConfig().set("DelSpawn.Cannot-Be-changed.Main-Command-Is", "delspawn");
			 CommandAliasesConfig.getConfig().set("DelSpawn.Aliases", java.util.Arrays.asList(new String[] {}));
			
			 CommandAliasesConfig.saveConfigFile();
            SpawnCommandConfig.saveConfigFile();
		}
		
		if (!ConfigMCommands.getConfig().isSet("Scoreboard.Error-No-Perm-For-Any-Score.Enable")) {
			ConfigMCommands.getConfig().set("Scoreboard.Error-No-Perm-For-Any-Score.Enable", true);
			ConfigMCommands.getConfig().set("Scoreboard.Error-No-Perm-For-Any-Score.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou don't have a permission, for any scoreboard"}));
		
        	ConfigMCommands.saveConfigFile();
		}
		
		if (!ConfigGeneral.getConfig().isSet("Plugin.Use.BattleLevels.Enable")) {
			ConfigGeneral.getConfig().set("Plugin.Use.BattleLevels.Enable", false);
			ConfigGeneral.getConfig().set("Plugin.Use.BattleLevels.Keep-The-Option", false);
			
			ConfigGeneral.saveConfigFile();
		}
		
		if (!ConfigMPlayerOption.getConfig().isSet("PlayerOption.Error.Not-Enable-In-A-World.Enable")) {
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cPlayer options are not enabled in this world"}));
            
            ConfigMPlayerOption.saveConfigFile();
		}
		
		if (!OptionPlayerConfigCommand.getConfig().isSet("PlayerOption.World.All_World")) {
			OptionPlayerConfigCommand.getConfig().set("PlayerOption.World.All_World", false);
			OptionPlayerConfigCommand.getConfig().set("PlayerOption.World.Worlds", java.util.Arrays.asList(new String[] {
                  "world",
                  "world_nether"
			}));
			
			OptionPlayerConfigCommand.saveConfigFile();
		}
		
		if (!ConfigMPlayerOption.getConfig().isSet("PlayerOption.DoubleJump.Enable.Enable")) {
			ConfigMPlayerOption.getConfig().set("PlayerOption.DoubleJump.Enable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your double jump has been &aactivated"}));
			ConfigMPlayerOption.getConfig().set("PlayerOption.DoubleJump.Disable.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your double jump has been &cdisabled"}));
	            
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe double jump is disabled in &6Cosmetics-Fun/DoubleJump.yml"}));
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
			ConfigMPlayerOption.getConfig().set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe double jump is not enabled in this world"}));
	        
			PlayerOptionMainConfig.getConfig().set("Keep.DoubleJump-Fly-OnJoin.Enable", false);
			
			PlayerOptionMainConfig.saveConfigFile();
	        ConfigMPlayerOption.saveConfigFile();   
		}
		
		if (!ConfigMCommands.getConfig().isSet("Warning")) {
			ConfigMCommands.getConfig().set("Warning", java.util.Arrays.asList(new String[] {
            		"<--center--> &4&m>-----------------------<", 
            		"", 
            		"%broadcast%",
            		"",
            		"<--center--> &4&m>-----------------------<"}));
			
			ConfigMCommands.saveConfigFile();
		}
		
		if (!CommandAliasesConfig.getConfig().isSet("Warning.Enable")) {
			CommandAliasesConfig.getConfig().set("Warning.Enable", true);
			CommandAliasesConfig.getConfig().set("Warning.Cannot-Be-changed.Main-Command-Is", "warning");
			CommandAliasesConfig.getConfig().set("Warning.Aliases", java.util.Arrays.asList(new String[] {"warn"}));
			
			CommandAliasesConfig.saveConfigFile();
		}
		
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
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Title", "&cSad");
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Material", "PAPER");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Sad.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ☹",
	                    "&bUse :sad: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Title", "&cScales");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Material", "OAK_PRESSURE_PLATE");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Scales.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ⚖",
	                    "&bUse :scales: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Title", "&cRadioactive");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Material", "REDSTONE_TORCH");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Radioactive.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ☢",
	                    "&bUse :radioactive: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Gui.Title", "&cKing");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Gui.Material", "GOLD_INGOT");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.King.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ♕",
	                    "&bUse :king: in the chat",
	                    " "
	                }));
	            
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Gui.Title", "&cEmail");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Email.Gui.Material", "PAPER");
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
		
		if (!PlayerOptionMainConfig.getConfig().isSet("Keep.PlayerVisibility-OnJoin.Enable")) {
			PlayerOptionMainConfig.getConfig().set("Keep.PlayerVisibility-OnJoin.Enable", false);
			PlayerOptionMainConfig.getConfig().set("Keep.Speed-OnJoin.Enable", false);
			
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
			PlayerOptionMainConfig.saveConfigFile();
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
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Title", "&cCoffee");
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Material", "MILK_BUCKET");
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.coffee.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : ☕",
	                "&bUse :swords: in the chat",
	                " "
	            }));
	        
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Enable", true);
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Use_Permission", true);
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Title", "&cAirplane");
	        OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.airplane.Gui.Material", "FEATHER");
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
		if (!PlayerOptionMainConfig.getConfig().isSet("TP.Last-Position-On-Join.Enable")) {
			PlayerOptionMainConfig.getConfig().set("TP.Last-Position-On-Join.Enable", false);
			
			PlayerOptionMainConfig.saveConfigFile();
		}
		
		// General
		if (!ConfigGeneral.getConfig().isSet("Plugin.Use.MYSQL.Enable")) {
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Enable", true);
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Host", "localhost");
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Username", "root");
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Password", "123");
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Database", "Hawn");
			ConfigGeneral.getConfig().set("Plugin.Use.MYSQL.Port", 3306);
			
			ConfigGeneral.saveConfigFile();
		}
		
		// Server list ping
		if (!ServerListConfig.getConfig().isSet("Motd.Classic.Enable")) {
			ServerListConfig.getConfig().set("Motd.Classic.Enable", true);
			ServerListConfig.getConfig().set("Motd.Classic.Line-1", "&cThis is a test of motd of course");
			ServerListConfig.getConfig().set("Motd.Classic.Line-2", "&eThanks to choose &lhawn");
            
			ServerListConfig.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Slots.Fake-Max-Player.Enable")) {
			ServerListConfig.getConfig().set("Slots.Fake-Max-Player.Enable", false);
			ServerListConfig.getConfig().set("Slots.Fake-Max-Player.Number", 2000);
		
			ServerListConfig.saveConfigFile();
		}
		
		if (!ServerListConfig.getConfig().isSet("Motd.WhiteList.Enable")) {
			ServerListConfig.getConfig().set("Motd.WhiteList.Enable", true);
			ServerListConfig.getConfig().set("Motd.WhiteList.Line-1", "&eServer is on whitelist");
			ServerListConfig.getConfig().set("Motd.WhiteList.Line-2", "&bPlease come back later");
            
			ServerListConfig.saveConfigFile();
		}
		
		// Config launch pad
		if (!ConfigGLP.getConfig().isSet("JumpPads.Options.Block")) {
			ConfigGLP.getConfig().set("JumpPads.Options.Block", "REDSTONE_BLOCK");
			ConfigGLP.getConfig().set("JumpPads.Options.Plate", "GOLD_PLATE");
            
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
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title", "&cClose the Gui");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material", "BARRIER");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "You can simply delete too",
                    "If you don't want lore",
                    " "
                }));
            
            // Option per emoji
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Title", "&cSmiley");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Material", "SKULL_ITEM");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Skull-Name", "%player%");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Smiley.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☺",
                    "&bUse :smiley: in the chat",
                    " "
                }));


                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Title", "&cSwords");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Material", "DIAMOND_SWORD");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Swords.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚔",
                    "&bUse :swords: in the chat",
                    " "
                }));
            
                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Title", "&cHeart");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Material", "REDSTONE");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Heart.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : �?�",
                    "&bUse :heart: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Title", "&cNotes");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Material", "NOTE_BLOCK");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Notes.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♪",
                    "&bUse :notes: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Title", "&cStar");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Material", "NETHER_STAR");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Star.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✰",
                    "&bUse :star: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Title", "&cPeace");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Material", "ORANGE_TULIP");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Peace.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☮",
                    "&bUse :peace: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Title", "&cChess");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Material", "TORCH");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Chess.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♖",
                    "&bUse :chess: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Title", "&cCopyright");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Material", "PAINTING");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Copyright.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ©",
                    "&bUse :chess: in the chat",
                    " "
                }));

            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Title", "&cAnchor");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Material", "IRON_ORE");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Anchor.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ⚓",
                    "&bUse :anchor: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Title", "&cSkull");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Material", "SKULL_ITEM");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Skull.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☠",
                    "&bUse :skull: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Title", "&cUmbrella");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Material", "GREEN_CARPET");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Umbrella.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☂",
                    "&bUse :umbrella: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Title", "&cDiamonds");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Material", "DIAMOND");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Diamonds.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ♦",
                    "&bUse :diamonds: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Title", "&cSnowflake");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Material", "SNOWBALL");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowflake.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : �?�",
                    "&bUse :snowflake: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Title", "&cSnowman");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Material", "SNOW_BLOCK");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Snowman.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ☃",
                    "&bUse :snowman: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Title", "&cCheckmark");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Material", "EMERALD_BLOCK");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Checkmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✔",
                    "&bUse :checkmark: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Title", "&cCrossmark");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Material", "REDSTONE_BLOCK");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Crossmark.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : ✖",
                    "&bUse :crossmark: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Enable", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Use_Permission", true);
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Title", "&cArrow");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Material", "ARROW");
            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Arrow.Gui.Lore", java.util.Arrays.asList(new String[] {
                    " ",
                    "&bTo use this emoji : Σ>―(´･ω･`)→",
                    "&bUse :arrow: in the chat",
                    " "
                }));

                OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Title", "&cStrong");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Material", "POTION");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Strong.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : (9｀･ω･)9",
	                    "&bUse :strong: in the chat",
	                    " "
	                }));
	
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Enable", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Use_Permission", true);
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Title", "&cPushups");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Material", "SPONGE");
	            OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.Pushups.Gui.Lore", java.util.Arrays.asList(new String[] {
	                    " ",
	                    "&bTo use this emoji : ┌(◣�?◢)�?",
	                    "&bUse :pushups: in the chat",
	                    " "
	                }));
	
	            OnChatConfig.saveConfigFile();
        }
		
		if (!OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.shrug.Enable")) {
			
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Enable", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Use_Permission", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Title", "&cShrug");
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Material", "STRING");
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.shrug.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : ¯\\_(ツ)_/¯",
	                "&bUse :shrug: in the chat",
	                " "
	            }));
	        
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Enable", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Use_Permission", true);
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Title", "&cFliptable");
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Material", "OAK_WOOD");
			OnChatConfig.getConfig().set("Chat-Emoji-Player.Emojis-list.fliptable.Gui.Lore", java.util.Arrays.asList(new String[] {
	                " ",
	                "&bTo use this emoji : (╯°□°）╯︵ ┻�?┻",
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
		
		if(!PlayerOptionMainConfig.getConfig().isSet("Keep.Vanish-On-Join.Enable")) {
			PlayerOptionMainConfig.getConfig().set("Keep.Vanish-On-Join.Enable", true);
			
			PlayerOptionMainConfig.saveConfigFile();
		}
		
	}

}