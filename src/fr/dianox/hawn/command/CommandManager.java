package fr.dianox.hawn.command;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.*;
import fr.dianox.hawn.command.commands.specials.worldedit.CopyCommand;
import fr.dianox.hawn.command.commands.specials.worldedit.OneCommand;
import fr.dianox.hawn.command.commands.specials.worldedit.PasteCommand;
import fr.dianox.hawn.command.commands.specials.worldedit.TwoCommand;
import fr.dianox.hawn.command.commands.tab.HawnTabCompletion;
import fr.dianox.hawn.utility.config.configs.CommandAliasesConfig;
import fr.dianox.hawn.utility.config.configs.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.Objects;

public class CommandManager {

	public CommandManager(Main plugin) throws NoSuchFieldException, IllegalAccessException {
		// Basics
		Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
		bukkitCommandMap.setAccessible(true);
		CommandMap cm = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

		/*
		 *
		 * ADMINISTRATION
		 *
		 */
		cm.register("paneladmin", new PanelAdminCommand("paneladmin"));
		cm.register("adminpanel", new PanelAdminCommand("adminpanel"));
		cm.register("pa", new PanelAdminCommand("pa"));
		cm.register("ap", new PanelAdminCommand("ap"));

		if (! WorldCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("hw", new WorldCommand("hw"));
			cm.register("hworld", new WorldCommand("hworld"));
		}

		Objects.requireNonNull(plugin.getCommand("hawn")).setExecutor(new HawnCommand());
		Objects.requireNonNull(plugin.getCommand("hawn")).setTabCompleter(new HawnTabCompletion());

		/* --------------------------- *
		 * WORLD EDIT ALIASES COMMANDS *
		 * --------------------------- */
		// >> Pos 1
		if (! OneCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("1", new OneCommand("1"));
			if (CommandAliasesConfig.getConfig().getBoolean("WorldEdit-Aliases.1.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("WorldEdit-Aliases.1.Aliases")) {
					cm.register(s, new OneCommand(s));
				}
			}
		}

		// >> Pos 2
		if (!TwoCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("2", new TwoCommand("2"));
			if (CommandAliasesConfig.getConfig().getBoolean("WorldEdit-Aliases.2.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("WorldEdit-Aliases.2.Aliases")) {
					cm.register(s, new TwoCommand(s));
				}
			}
		}

		// >> Copy
		if (! CopyCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("c", new CopyCommand("c"));
			if (CommandAliasesConfig.getConfig().getBoolean("WorldEdit-Aliases.C.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("WorldEdit-Aliases.C.Aliases")) {
					cm.register(s, new CopyCommand(s));
				}
			}
		}

		// >> Paste
		if (! PasteCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("p", new PasteCommand("p"));
			if (CommandAliasesConfig.getConfig().getBoolean("WorldEdit-Aliases.P.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("WorldEdit-Aliases.P.Aliases")) {
					cm.register(s, new PasteCommand(s));
				}
			}
		}

		/* ------------------ *
		 * BROADCAST COMMANDS *
		 * ------------------ */
		// >> BroadCast
		if (!BroadCastCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("broadcast", new BroadCastCommand("broadcast"));
			if (CommandAliasesConfig.getConfig().getBoolean("Broadcast.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Broadcast.Aliases")) {
					cm.register(s, new BroadCastCommand(s));
				}
			}
		}
		// >> Title broadcast
		if (!TitleAnnouncerConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("titleannouncer", new TitleAnnouncerCommand("titleannouncer"));
			if (CommandAliasesConfig.getConfig().getBoolean("TitleAnnouncer.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("TitleAnnouncer.Aliases")) {
					cm.register(s, new TitleAnnouncerCommand(s));
				}
			}
		}
		// >> Action bar broadcast
		if (!ActionbarAnnouncerConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("actionbarannouncer", new ABAnnouncerCommand("actionbarannouncer"));
			if (CommandAliasesConfig.getConfig().getBoolean("ActionBarAnnouncer.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("ActionBarAnnouncer.Aliases")) {
					cm.register(s, new ABAnnouncerCommand(s));
				}
			}
		}
		// Warning
		if (!WarningCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("warning", new WarningCommand("warning"));
			if (CommandAliasesConfig.getConfig().getBoolean("Warning.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Warning.Aliases")) {
					cm.register(s, new WarningCommand(s));
				}
			}
		}

		/* ------------- *
		 * CHAT COMMANDS *
		 * ------------- */
		// >> ClearChat
		if (!ClearChatCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("cc", new ClearChatCommand("cc"));
			if (CommandAliasesConfig.getConfig().getBoolean("ClearChat.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("ClearChat.Aliases")) {
					cm.register(s, new ClearChatCommand(s));
				}
			}
		}
		// >> DelayChat
		if (!DelayChatCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("delaychat", new DelaychatCommand("delaychat"));
			if (CommandAliasesConfig.getConfig().getBoolean("DelayChat.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("DelayChat.Aliases")) {
					cm.register(s, new DelaychatCommand(s));
				}
			}
		}
		// >> Global mute
		if (!MuteChatCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("globalmute", new MuteChatCommand("globalmute"));
			if (CommandAliasesConfig.getConfig().getBoolean("MuteChat.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("MuteChat.Aliases")) {
					cm.register(s, new MuteChatCommand(s));
				}
			}
		}

		/* ---------------------- *
		 * CHECK ACCOUNT COMMANDS *
		 * ---------------------- */
		// >> Check Account
		if (!CheckAccountCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("checkaccount", new CheckAccountCommand("checkaccount"));
			if (CommandAliasesConfig.getConfig().getBoolean("CheckAccount.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("CheckAccount.Aliases")) {
					cm.register(s, new CheckAccountCommand(s));
				}
			}
		}

		/* -------------- *
		 * CLEAR COMMANDS *
		 * -------------- */
		// >> Clear Ground Items
		if (!ClearGroundItemsCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("cleargrounditems", new ClearGroundItemsCommand("cleargrounditems"));
			if (CommandAliasesConfig.getConfig().getBoolean("ClearGroundItems.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("ClearGroundItems.Aliases")) {
					cm.register(s, new ClearGroundItemsCommand(s));
				}
			}
		}

		// >> Clear inventory
		if (!ClearInvCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("clearinventory", new ClearInvCommand("clearinventory"));
			if (CommandAliasesConfig.getConfig().getBoolean("ClearInv.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("ClearInv.Aliases")) {
					cm.register(s, new ClearInvCommand(s));
				}
			}
		}

		// >> Clear Mobs
		if (!ClearMobsCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("clearmobs", new ClearMobsCommand("clearmobs"));
			if (CommandAliasesConfig.getConfig().getBoolean("ClearMobs.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("ClearMobs.Aliases")) {
					cm.register(s, new ClearMobsCommand(s));
				}
			}
		}

		/* --------------- *
		 * EMOJIS COMMANDS *
		 * --------------- */
		// >> Emojis
		if (!EmojiCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("emoji", new EmojiesCommand("emoji"));
			if (CommandAliasesConfig.getConfig().getBoolean("Emojis.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Emojis.Aliases")) {
					cm.register(s, new EmojiesCommand(s));
				}
			}
		}

		/* ------------------- *
		 * ENDERCHEST COMMANDS *
		 * ------------------- */
		// >> EnderChest
		if (!EnderChestCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("invsee", new EnderChestCommand("enderchest"));
			if (CommandAliasesConfig.getConfig().getBoolean("EnderChest.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("EnderChest.Aliases")) {
					cm.register(s, new EnderChestCommand(s));
				}
			}
		}

		/* ------------ *
		 * EXP COMMANDS *
		 * ------------ */
		// >> Exp
		if (!ExpCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("exp", new ExpCommand("exp"));
			if (CommandAliasesConfig.getConfig().getBoolean("Exp.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Exp.Aliases")) {
					cm.register(s, new ExpCommand(s));
				}
			}
		}

		/* ------------------ *
		 * WORKBENCH COMMANDS *
		 * ------------------ */
		// >> Workbench
		if (!WorkBenchCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("workbench", new WorkBenchCommand("workbench"));
			if (CommandAliasesConfig.getConfig().getBoolean("WorkBench.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("WorkBench.Aliases")) {
					cm.register(s, new WorkBenchCommand(s));
				}
			}
		}

		/* ------------- *
		 * BURN COMMANDS *
		 * ------------- */
		// >> Burn
		if (!BurnCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("burn", new BurnCommand("burn"));
			if (CommandAliasesConfig.getConfig().getBoolean("Burn.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Burn.Aliases")) {
					cm.register(s, new BurnCommand(s));
				}
			}
		}

		/* -------------- *
		 * SKULL COMMANDS *
		 * -------------- */
		// >> Skull
		if (!SkullCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("skull", new SkullCommand("skull"));
			if (CommandAliasesConfig.getConfig().getBoolean("Skull.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Skull.Aliases")) {
					cm.register(s, new SkullCommand(s));
				}
			}
		}

		/* --------------- *
		 * SPEEDS COMMANDS *
		 * --------------- */
		// >> Flyspeed
		if (!FlySpeedCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("flyspeed", new FlySpeedCommand("flyspeed"));
			if (CommandAliasesConfig.getConfig().getBoolean("FlySpeed.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("FlySpeed.Aliases")) {
					cm.register(s, new FlySpeedCommand(s));
				}
			}
		}

		// >> Speed
		if (!SpeedCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("speed", new SpeedCommand("speed"));
			if (CommandAliasesConfig.getConfig().getBoolean("Speed.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Speed.Aliases")) {
					cm.register(s, new SpeedCommand(s));
				}
			}
		}

		/* ------------- *
		 * FEED COMMANDS *
		 * ------------- */
		// >> Heal
		if (!FeedCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("feed", new FeedCommand("feed"));
			if (CommandAliasesConfig.getConfig().getBoolean("Feed.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Feed.Aliases")) {
					cm.register(s, new FeedCommand(s));
				}
			}
		}

		/* ------------ *
		 * FLY COMMANDS *
		 * ------------ */
		// >> Fly
		if (!FlyCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("fly", new FlyCommand("fly"));
			if (CommandAliasesConfig.getConfig().getBoolean("Fly.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Fly.Aliases")) {
					cm.register(s, new FlyCommand(s));
				}
			}
		}

		/* ------------ *
		 * HAT COMMANDS *
		 * ------------ */
		// >> Hat
		if (!HatCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("hat", new HatCommand("hat"));
			if (CommandAliasesConfig.getConfig().getBoolean("Hat.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Hat.Aliases")) {
					cm.register(s, new HatCommand(s));
				}
			}
		}

		/* ------------- *
		 * HEAL COMMANDS *
		 * ------------- */
		// >> Heal
		if (!HealCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("heal", new HealCommand("heal"));
			if (CommandAliasesConfig.getConfig().getBoolean("Heal.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Heal.Aliases")) {
					cm.register(s, new HealCommand(s));
				}
			}
		}

		/* ------------- *
		 * HELP COMMANDS *
		 * ------------- */
		// >> Help
		if (!HelpCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("help", new HelpCommand("help"));
			if (CommandAliasesConfig.getConfig().getBoolean("Help.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Help.Aliases")) {
					cm.register(s, new HelpCommand(s));
				}
			}
		}

		/* ----------------- *
		 * GAMEMODE COMMANDS *
		 * ----------------- */
		// >> Classic command
		if (!GamemodeCommandConfig.getConfig().getBoolean("Gamemode.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("gamemode", new ClassicGMCommand("gamemode"));
			if (CommandAliasesConfig.getConfig().getBoolean("Gamemode-Classic.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Gamemode-Classic.Aliases")) {
					cm.register(s, new ClassicGMCommand(s));
				}
			}
		}
		// >> Gamemode survival
		if (!GamemodeCommandConfig.getConfig().getBoolean("gms.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("gms", new gmsCommand("gms"));
			if (CommandAliasesConfig.getConfig().getBoolean("Gms.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Gms.Aliases")) {
					cm.register(s, new gmsCommand(s));
				}
			}
		}
		// >> Gamemode creative
		if (!GamemodeCommandConfig.getConfig().getBoolean("gmc.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("gmc", new gmcCommand("gmc"));
			if (CommandAliasesConfig.getConfig().getBoolean("Gmc.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Gmc.Aliases")) {
					cm.register(s, new gmcCommand(s));
				}
			}
		}
		// >> Gamemode adventure
		if (!GamemodeCommandConfig.getConfig().getBoolean("gma.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("gma", new gmaCommand("gma"));
			if (CommandAliasesConfig.getConfig().getBoolean("Gma.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Gma.Aliases")) {
					cm.register(s, new gmaCommand(s));
				}
			}
		}
		// >> Gamemode spectator
		if (!GamemodeCommandConfig.getConfig().getBoolean("gmsp.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("gmsp", new gmspCommand("gmsp"));
			if (CommandAliasesConfig.getConfig().getBoolean("Gmsp.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Gmsp.Aliases")) {
					cm.register(s, new gmspCommand(s));
				}
			}
		}

		/* --------------- *
		 * GETPOS COMMANDS *
		 * --------------- */
		// >> GetPos
		if (!GetPosCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("getpos", new GetPosCommand("getpos"));
			if (CommandAliasesConfig.getConfig().getBoolean("GetPos.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("GetPos.Aliases")) {
					cm.register(s, new GetPosCommand(s));
				}
			}
		}

		/* -------------- *
		 * GOTOP COMMANDS *
		 * -------------- */
		// >> GoTop
		if (!GoTopCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("gotop", new GoTopCommand("gotop"));
			if (CommandAliasesConfig.getConfig().getBoolean("Gotop.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Gotop.Aliases")) {
					cm.register(s, new GoTopCommand(s));
				}
			}
		}

		/* --------------- *
		 * INVSEE COMMANDS *
		 * --------------- */
		// >> InvSee
		if (!InvSeeCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("invsee", new InvSeeCommand("invsee"));
			if (CommandAliasesConfig.getConfig().getBoolean("InvSee.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("InvSee.Aliases")) {
					cm.register(s, new InvSeeCommand(s));
				}
			}
		}

		/* ----------- *
		 * IP COMMANDS *
		 * ----------- */
		// >> Hat
		if (!IpCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("ip", new IpCommand("ip"));
			if (CommandAliasesConfig.getConfig().getBoolean("Ip.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Ip.Aliases")) {
					cm.register(s, new IpCommand(s));
				}
			}
		}

		/* ---------------- *
		 * KICKALL COMMANDS *
		 * ---------------- */
		// >> Kickall
		if (!KickAllCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("kickall", new KickAllCommand("kickall"));
			if (CommandAliasesConfig.getConfig().getBoolean("KickAll.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("KickAll.Aliases")) {
					cm.register(s, new KickAllCommand(s));
				}
			}
		}

		/* ------------- *
		 * LIST COMMANDS *
		 * ------------- */
		// >> List
		if (!ListCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("list", new ListCommand("list"));
			if (CommandAliasesConfig.getConfig().getBoolean("List.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("List.Aliases")) {
					cm.register(s, new ListCommand(s));
				}
			}
		}

		/* ------------------- *
		 * SCOREBOARD COMMANDS *
		 * ------------------- */
		// >> Scoreboard
		if (!ScoreboardCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("scoreboard", new ScoreboardCommand("scoreboard"));
			if (CommandAliasesConfig.getConfig().getBoolean("Scoreboard.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Scoreboard.Aliases")) {
					cm.register(s, new ScoreboardCommand(s));
				}
			}
		}

		/* ------------- *
		 * PING COMMANDS *
		 * ------------- */
		// >> Ping
		if (!PingCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("ping", new PingCommand("ping"));
			if (CommandAliasesConfig.getConfig().getBoolean("Ping.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Ping.Aliases")) {
					cm.register(s, new PingCommand(s));
				}
			}
		}

		/* ---------------------- *
		 * PLAYER OPTION COMMANDS *
		 * ---------------------- */
		// >> Main command
		if (!OptionPlayerConfigCommand.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("option", new OptionCommand("option"));
			if (CommandAliasesConfig.getConfig().getBoolean("Player-Option.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Player-Option.Aliases")) {
					cm.register(s, new OptionCommand(s));
				}
			}
		}

		/* --------------- *
		 * REPAIR COMMANDS *
		 * --------------- */
		// >> Repair
		if (!RepairCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("repair", new RepairCommand("repair"));
			if (CommandAliasesConfig.getConfig().getBoolean("Repair.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Repair.Aliases")) {
					cm.register(s, new RepairCommand(s));
				}
			}
		}

		/* -------------- *
		 * SPAWN COMMANDS *
		 * -------------- */
		// >> Spawn
		if (!SpawnCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("spawn", new SpawnCommand("spawn"));
			if (CommandAliasesConfig.getConfig().getBoolean("Spawn.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Spawn.Aliases")) {
					cm.register(s, new SpawnCommand(s));
				}
			}
		}
		// >> SetSpawn
		if (!SpawnCommandConfig.getConfig().getBoolean("SetSpawn.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("setspawn", new SetSpawnCommand("setspawn"));
			if (CommandAliasesConfig.getConfig().getBoolean("SetSpawn.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("SetSpawn.Aliases")) {
					cm.register(s, new SetSpawnCommand(s));
				}
			}
		}
		// >> DelSpawn
		if (!SpawnCommandConfig.getConfig().getBoolean("DelSpawn.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("delspawn", new DelSpawnCommand("delspawn"));
			if (CommandAliasesConfig.getConfig().getBoolean("DelSpawn.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("DelSpawn.Aliases")) {
					cm.register(s, new DelSpawnCommand(s));
				}
			}
		}
		// >> Spawnlist
		if (!SpawnCommandConfig.getConfig().getBoolean("SpawnList.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("spawnlist", new SpawnListCommand("spawnlist"));
			if (CommandAliasesConfig.getConfig().getBoolean("SpawnList.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("SpawnList.Aliases")) {
					cm.register(s, new SpawnListCommand(s));
				}
			}
		}

		/* ---------------- *
		 * SUICIDE COMMANDS *
		 * ---------------- */
		// >> Suicide
		if (!SuicideCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("suicide", new SuicideCommand("suicide"));
			if (CommandAliasesConfig.getConfig().getBoolean("Suicide.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Suicide.Aliases")) {
					cm.register(s, new SuicideCommand(s));
				}
			}
		}

		/* ------------- *
		 * TIME COMMANDS *
		 * ------------- */
		// >> Day
		if (!WeatherTimeCommandConfig.getConfig().getBoolean("Time.Set.Day.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("day", new DayCommand("day"));
			if (CommandAliasesConfig.getConfig().getBoolean("Day.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Day.Aliases")) {
					cm.register(s, new DayCommand(s));
				}
			}
		}
		// >> Night
		if (!WeatherTimeCommandConfig.getConfig().getBoolean("Time.Set.Night.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("night", new NightCommand("night"));
			if (CommandAliasesConfig.getConfig().getBoolean("Night.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Night.Aliases")) {
					cm.register(s, new NightCommand(s));
				}
			}
		}

		/* --------------- *
		 * VANISH COMMANDS *
		 * --------------- */
		// >> Vanish
		if (!VanishCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("vanish", new VanishCommand("vanish"));
			if (CommandAliasesConfig.getConfig().getBoolean("Vanish.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Vanish.Aliases")) {
					cm.register(s, new VanishCommand(s));
				}
			}
		}

		/* ------------- *
		 * WARP COMMANDS *
		 * ------------- */
		// >> Warp
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("warp", new WarpCommand("warp"));
			if (CommandAliasesConfig.getConfig().getBoolean("Warp.Warp.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Warp.Aliases")) {
					cm.register(s, new WarpCommand(s));
				}
			}
		}
		// >> Set Warp
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("SetWarp.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("setwarp", new SetWarpCommand("setwarp"));
			if (CommandAliasesConfig.getConfig().getBoolean("Warp.Set-Warp.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Set-Warp.Aliases")) {
					cm.register(s, new SetWarpCommand(s));
				}
			}
		}
		// >> Del warp
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("DelWarp.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("delwarp", new DelWarpCommand("delwarp"));
			if (CommandAliasesConfig.getConfig().getBoolean("Warp.Del-Warp.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Del-Warp.Aliases")) {
					cm.register(s, new DelWarpCommand(s));
				}
			}
		}
		// >> Warp list
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("WarpList.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("warplist", new WarpListCommand("warplist"));
			if (CommandAliasesConfig.getConfig().getBoolean("Warp.Warp-list.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Warp-list.Aliases")) {
					cm.register(s, new WarpListCommand(s));
				}
			}
		}
		// >> Edit warp
		if (!WarpSetWarpCommandConfig.getConfig().getBoolean("EditWarp.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("editwarp", new EditWarpCommand("editwarp"));
			if (CommandAliasesConfig.getConfig().getBoolean("Warp.Edit-Warp.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Edit-Warp.Aliases")) {
					cm.register(s, new EditWarpCommand(s));
				}
			}
		}

		/* ---------------- *
		 * WEATHER COMMANDS *
		 * ---------------- */
		// >> Rain
		if (!WeatherTimeCommandConfig.getConfig().getBoolean("Weather.Set.Rain.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("rain", new RainCommand("rain"));
			if (CommandAliasesConfig.getConfig().getBoolean("Rain.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Rain.Aliases")) {
					cm.register(s, new RainCommand(s));
				}
			}
		}
		// >> Sun
		if (!WeatherTimeCommandConfig.getConfig().getBoolean("Weather.Set.Sun.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("sun", new SunCommand("sun"));
			if (CommandAliasesConfig.getConfig().getBoolean("Sun.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Sun.Aliases")) {
					cm.register(s, new SunCommand(s));
				}
			}
		}
		// >> Thunder
		if (!WeatherTimeCommandConfig.getConfig().getBoolean("Weather.Set.Thunder.DISABLE_THE_COMMAND_COMPLETELY")) {
			cm.register("thunder", new ThunderCommand("thunder"));
			if (CommandAliasesConfig.getConfig().getBoolean("Thunder.Enable")) {
				for (String s : CommandAliasesConfig.getConfig().getStringList("Thunder.Aliases")) {
					cm.register(s, new ThunderCommand(s));
				}
			}
		}

		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Commands loaded");
		Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| ");
	}

}
