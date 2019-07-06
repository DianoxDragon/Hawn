package fr.Dianox.Hawn;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import fr.Dianox.Hawn.Commands.HawnCommand;
import fr.Dianox.Hawn.Commands.PanelAdminCommand;
import fr.Dianox.Hawn.Commands.PingCommand;
import fr.Dianox.Hawn.Commands.SpawnCommand;
import fr.Dianox.Hawn.Commands.Features.ClearInvCommand;
import fr.Dianox.Hawn.Commands.Features.FlyCommand;
import fr.Dianox.Hawn.Commands.Features.ScoreboardCommand;
import fr.Dianox.Hawn.Commands.Features.VanishCommand;
import fr.Dianox.Hawn.Commands.Features.Chat.BroadCastCommand;
import fr.Dianox.Hawn.Commands.Features.Chat.ClearChatCommand;
import fr.Dianox.Hawn.Commands.Features.Chat.DelaychatCommand;
import fr.Dianox.Hawn.Commands.Features.Chat.EmojiesCommand;
import fr.Dianox.Hawn.Commands.Features.Chat.MuteChatCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.ClassicGMCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmaCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmcCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmsCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmspCommand;
import fr.Dianox.Hawn.Commands.Features.OptionPlayers.MainCommandOptionPlayer;
import fr.Dianox.Hawn.Commands.Features.Specials.TitleAnnouncerCommand;
import fr.Dianox.Hawn.Commands.Features.Warp.DelWarpCommand;
import fr.Dianox.Hawn.Commands.Features.Warp.SetWarpCommand;
import fr.Dianox.Hawn.Commands.Features.Warp.WarpCommand;
import fr.Dianox.Hawn.Commands.Features.Warp.WarpListCommand;
import fr.Dianox.Hawn.Commands.Others.HealCommand;
import fr.Dianox.Hawn.Commands.Others.Time.DayCommand;
import fr.Dianox.Hawn.Commands.Others.Time.NightCommand;
import fr.Dianox.Hawn.Commands.Others.Weather.RainCommand;
import fr.Dianox.Hawn.Commands.Others.Weather.SunCommand;
import fr.Dianox.Hawn.Commands.Others.Weather.ThunderCommand;
import fr.Dianox.Hawn.Event.AutoBroadcast;
import fr.Dianox.Hawn.Event.FunFeatures;
import fr.Dianox.Hawn.Event.OnJoin;
import fr.Dianox.Hawn.Utility.CheckConfig;
import fr.Dianox.Hawn.Utility.EmojiesUtility;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.NMSClass;
import fr.Dianox.Hawn.Utility.OtherUtils;
import fr.Dianox.Hawn.Utility.TitleUtils;
import fr.Dianox.Hawn.Utility.VersionUtils;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.CommandAliasesConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.CustomCommandConfig;
import fr.Dianox.Hawn.Utility.Config.PlayerConfig;
import fr.Dianox.Hawn.Utility.Config.ScoreboardMainConfig;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
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
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGLP;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Events.ProtectionPlayerConfig;
import fr.Dianox.Hawn.Utility.Config.Events.CommandEventConfig;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGJoinQuitCommand;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGProtection;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OtherFeaturesConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerEventsConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerWorldChangeConfigE;
import fr.Dianox.Hawn.Utility.Config.Events.VoidTPConfig;
import fr.Dianox.Hawn.Utility.Config.Events.WorldEventConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMEvents;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMPlayerOption;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMProtection;
import fr.Dianox.Hawn.Utility.Config.Messages.TXTmsg;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.ErrorConfigAM;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.InfoServerOverviewC;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.OtherAMConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.SpawnMConfig;
import fr.Dianox.Hawn.Utility.Config.Scoreboard.defaultscoreboardconfig;
import fr.Dianox.Hawn.Utility.Config.Scoreboard.worldnetherdsc;
import fr.Dianox.Hawn.Utility.Config.Tab.TablistConfig;
import fr.Dianox.Hawn.Utility.Scoreboard.PlayerBoard;
import fr.Dianox.Hawn.Utility.Scoreboard.ScoreboardInfo;
import fr.Dianox.Hawn.Utility.Server.Tps;
import fr.Dianox.Hawn.Utility.Server.WarnTPS;
import fr.Dianox.Hawn.Utility.World.BasicEventsPW;
import fr.Dianox.Hawn.Utility.World.ChangeWorldPW;
import fr.Dianox.Hawn.Utility.World.CjiPW;
import fr.Dianox.Hawn.Utility.World.CommandsPW;
import fr.Dianox.Hawn.Utility.World.CosmeticsPW;
import fr.Dianox.Hawn.Utility.World.OnJoinPW;
import fr.Dianox.Hawn.Utility.World.OnQuitPW;
import fr.Dianox.Hawn.Utility.World.OtherFeaturesPW;
import fr.Dianox.Hawn.Utility.World.PlayerEventsPW;
import fr.Dianox.Hawn.Utility.World.ProtectionPW;
import fr.Dianox.Hawn.Utility.World.WorldPW;
import me.clip.placeholderapi.PlaceholderAPI;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;
	
	static String versions = "0.6.3-Alpha";
	public static String UpToDate, MaterialMethod, nmsver;
	public static boolean useOldMethods = false;
	public static List<String> fileconfiglist = new ArrayList<String>();
	
	static Connection connection;
	private String host, database, username, password;
	private int port;
	static Statement statement;
	public static boolean useyamllistplayer = false;
	
    public static HashMap<Integer, String> autobroadcast = new HashMap<>();
    public static Integer autobroadcast_total = 0;
    public static int interval, curMsg = 0;
	public Scoreboard board;
	
	public static HashMap<Player, PlayerBoard> boards = new HashMap<Player, PlayerBoard>();
	public static List<PlayerBoard> allboards = new ArrayList<PlayerBoard>();
	public HashMap<String, ScoreboardInfo> info = new HashMap<String, ScoreboardInfo>();
	public HashMap<String, String> infoname = new HashMap<String, String>();
	public HashMap<String, String> infoname2 = new HashMap<String, String>();
	public static HashMap<Player, Long> playerWorldTimer = new HashMap<Player, Long>();
	public static List<Player> nosb = new ArrayList<Player>(); 
	
	public static HashMap<UUID, Integer> player_spawnwarpdelay = new HashMap<UUID, Integer>();
	public static List<Player> inwarpd = new ArrayList<Player>(); 
	public static List<Player> inspawnd = new ArrayList<Player>(); 
	
	public String hea = "";
	public String foo = "";
	
    private static Class<?> PacketPlayOutPlayerListHeaderFooter;
    private static Class<?> ChatComponentText;
    private static Constructor<?> newPacketPlayOutPlayerListHeaderFooter;
    
    public static List<Player> buildbypasscommand = new ArrayList<Player>();
    
	@SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		super.onEnable();
		
		gcs(ChatColor.BLUE+"| ------------------------------------");
		gcs(ChatColor.BLUE+"| ");
		
		gcs(ChatColor.BLUE+"| "+ChatColor.AQUA+" _   _       ___   _          __  __   _  ");
		gcs(ChatColor.BLUE+"| "+ChatColor.AQUA+"| | | |     /   | | |        / / |  \\ | |");
		gcs(ChatColor.BLUE+"| "+ChatColor.AQUA+"| |_| |    / /| | | |  __   / /  |   \\| | ");
		gcs(ChatColor.BLUE+"| "+ChatColor.AQUA+"|  _  |   / / | | | | /  | / /   | |\\   | ");
		gcs(ChatColor.BLUE+"| "+ChatColor.AQUA+"| | | |  / /  | | | |/   |/ /    | | \\  | ");
		gcs(ChatColor.BLUE+"| "+ChatColor.AQUA+"|_| |_| /_/   |_| |___/|___/     |_|  \\_| ");
		gcs(ChatColor.BLUE+"| ");
		
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Version "+versions+" - Created by Dianox");
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"May the "+ChatColor.RED+"Phoenix"+ChatColor.YELLOW+" fly with you!");
		gcs(ChatColor.BLUE+"| ");
		
		@SuppressWarnings("unused")
		Metrics metrics = new Metrics(this);
		
		// Load config
		ConfigSpawn.loadConfig((Plugin) this);
		ConfigGeneral.loadConfig((Plugin) this);
		ServerListConfig.loadConfig((Plugin) this);
		PlayerConfig.loadConfig((Plugin) this);
		AutoBroadcastConfig.loadConfig((Plugin) this);
		BetweenServersConfig.loadConfig((Plugin) this);
		CommandAliasesConfig.loadConfig((Plugin) this);
		
		OtherFeaturesConfig.loadConfig((Plugin) this);
		WorldEventConfig.loadConfig((Plugin) this);
		
		ConfigGProtection.loadConfig((Plugin) this);
		
		VoidTPConfig.loadConfig((Plugin) this);
		ProtectionPlayerConfig.loadConfig((Plugin) this);
		PlayerEventsConfig.loadConfig((Plugin) this);
		OnJoinConfig.loadConfig((Plugin) this);
		CommandEventConfig.loadConfig((Plugin) this);
		ConfigGJoinQuitCommand.loadConfig((Plugin) this);
		WeatherTimeCommandConfig.loadConfig((Plugin) this);
		FlyCommandConfig.loadConfig((Plugin) this);
		OnChatConfig.loadConfig((Plugin) this);
		PlayerWorldChangeConfigE.loadConfig((Plugin) this);
		
		ConfigMGeneral.loadConfig((Plugin) this);
		ConfigMEvents.loadConfig((Plugin) this);
		ConfigMProtection.loadConfig((Plugin) this);
		ConfigMOStuff.loadConfig((Plugin) this);
		ConfigMCommands.loadConfig((Plugin) this);
		ConfigMPlayerOption.loadConfig((Plugin) this);
		
		HelpCommandConfig.loadConfig((Plugin) this);
		ClearChatCommandConfig.loadConfig((Plugin) this);
		SpawnCommandConfig.loadConfig((Plugin) this);
		MuteChatCommandConfig.loadConfig((Plugin) this);
		PingCommandConfig.loadConfig((Plugin) this);
		DelayChatCommandConfig.loadConfig((Plugin) this);
		BroadCastCommandConfig.loadConfig((Plugin) this);
		HealCommandConfig.loadConfig((Plugin) this);
		WarpSetWarpCommandConfig.loadConfig((Plugin) this);
		WarpListConfig.loadConfig((Plugin) this);
		VanishCommandConfig.loadConfig((Plugin) this);
		TitleAnnouncerConfig.loadConfig((Plugin) this);
		ClearInvCommandConfig.loadConfig((Plugin) this);
		EmojiCommandConfig.loadConfig((Plugin) this);
		ScoreboardMainConfig.loadConfig((Plugin) this);
		ScoreboardCommandConfig.loadConfig((Plugin) this);
		GamemodeCommandConfig.loadConfig((Plugin) this);
		OptionPlayerConfigCommand.loadConfig((Plugin) this);
		
		ConfigGCos.loadConfig((Plugin) this);
		ConfigGLP.loadConfig((Plugin) this);
		ConfigFDoubleJump.loadConfig((Plugin) this);
		
		//NameTagConfig.loadConfig((Plugin) this);
		TablistConfig.loadConfig((Plugin) this);
		
		CustomCommandConfig.loadConfig((Plugin) this);
		
		SpecialCjiHidePlayers.loadConfig((Plugin) this);
		
		if (!ScoreboardMainConfig.getConfig().isSet("DefaultConfigGenerated")) {
			defaultscoreboardconfig.loadConfig((Plugin) this);
			worldnetherdsc.loadConfig((Plugin) this);
			
			ScoreboardMainConfig.getConfig().set("DefaultConfigGenerated", Boolean.valueOf(true));
			ScoreboardMainConfig.saveConfigFile();
		}
		
		InfoServerOverviewC.loadConfig((Plugin) this);
		ErrorConfigAM.loadConfig((Plugin) this);
		OtherAMConfig.loadConfig((Plugin) this);
		SpawnMConfig.loadConfig((Plugin) this);
		
		instance = this;
		
		GetSetWorld();
		
		CheckConfig.Check();
				
		HawnCommand.configlist();
		
		TXTmsg.onCreateInfoMsgAdmin();
		TXTmsg.onWrite();
		
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Configurations files loaded");
		gcs(ChatColor.BLUE+"| ");
		
		// Commands
		getCommand("hawn").setExecutor(new HawnCommand());
		getCommand("paneladmin").setExecutor(new PanelAdminCommand());
		
		Field bukkitCommandMap;
		
		try {
			bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			
			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
			
			/* ------------------ *
			 * BROADCAST COMMANDS *
			 * ------------------ */
			// >> BroadCast
			if (!BroadCastCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("broadcast", new BroadCastCommand("broadcast"));
				if (CommandAliasesConfig.getConfig().getBoolean("Broadcast.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Broadcast.Aliases")) {
						commandMap.register(s, new BroadCastCommand(s));
					}
				}
			}
			// >> Title broadcast
			if (!TitleAnnouncerConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("titleannouncer", new TitleAnnouncerCommand("titleannouncer"));
				if (CommandAliasesConfig.getConfig().getBoolean("TitleAnnouncer.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("TitleAnnouncer.Aliases")) {
						commandMap.register(s, new TitleAnnouncerCommand(s));
					}
				}
			}
			
			/* ------------- *
			 * CHAT COMMANDS *
			 * ------------- */
			// >> ClearChat
			if (!ClearChatCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("cc", new ClearChatCommand("cc"));
				if (CommandAliasesConfig.getConfig().getBoolean("ClearChat.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("ClearChat.Aliases")) {
						commandMap.register(s, new ClearChatCommand(s));
					}
				}
			}
			// >> DelayChat
			if (!DelayChatCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("delaychat", new DelaychatCommand("delaychat"));
				if (CommandAliasesConfig.getConfig().getBoolean("DelayChat.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("DelayChat.Aliases")) {
						commandMap.register(s, new DelaychatCommand(s));
					}
				}
			}
			// >> Global mute
			if (!MuteChatCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("globalmute", new MuteChatCommand("globalmute"));
				if (CommandAliasesConfig.getConfig().getBoolean("MuteChat.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("MuteChat.Aliases")) {
						commandMap.register(s, new MuteChatCommand(s));
					}
				}
			}
			
			/* ------------------------ *
			 * CLEAR INVENTORY COMMANDS *
			 * ------------------------ */
			// >> Clear inventory
			if (!ClearInvCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("clearinventory", new ClearInvCommand("clearinventory"));
				if (CommandAliasesConfig.getConfig().getBoolean("ClearInv.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("ClearInv.Aliases")) {
						commandMap.register(s, new ClearInvCommand(s));
					}
				}
			}
			
			/* --------------- *
			 * EMOJIS COMMANDS *
			 * --------------- */
			// >> Emojis
			if (!EmojiCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("emoji", new EmojiesCommand("emoji"));
				if (CommandAliasesConfig.getConfig().getBoolean("Emojis.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Emojis.Aliases")) {
						commandMap.register(s, new EmojiesCommand(s));
					}
				}
			}
			
			/* ------------ *
			 * FLY COMMANDS *
			 * ------------ */
			// >> Fly
			if (!FlyCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("fly", new FlyCommand("fly"));
				if (CommandAliasesConfig.getConfig().getBoolean("Fly.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Fly.Aliases")) {
						commandMap.register(s, new FlyCommand(s));
					}
				}
			}
			
			/* ------------- *
			 * HEAL COMMANDS *
			 * ------------- */
			// >> Heal
			if (!HealCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("heal", new HealCommand("heal"));
				if (CommandAliasesConfig.getConfig().getBoolean("Heal.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Heal.Aliases")) {
						commandMap.register(s, new HealCommand(s));
					}
				}
			}
			
			/* ------------- *
			 * HELP COMMANDS *
			 * ------------- */
			// >> Help
			if (!HelpCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("help", new fr.Dianox.Hawn.Commands.Features.HelpCommand("help"));
				if (CommandAliasesConfig.getConfig().getBoolean("Help.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Help.Aliases")) {
						commandMap.register(s, new fr.Dianox.Hawn.Commands.Features.HelpCommand(s));
					}
				}
			}
			
			/* ----------------- *
			 * GAMEMODE COMMANDS *
			 * ----------------- */
			// >> Classic command
			if (!GamemodeCommandConfig.getConfig().getBoolean("Gamemode.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("gamemode", new ClassicGMCommand("gamemode"));
				if (CommandAliasesConfig.getConfig().getBoolean("Gamemode-Classic.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Gamemode-Classic.Aliases")) {
						commandMap.register(s, new ClassicGMCommand(s));
					}
				}
			}
			// >> Gamemode survival
			if (!GamemodeCommandConfig.getConfig().getBoolean("gms.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("gms", new gmsCommand("gms"));
				if (CommandAliasesConfig.getConfig().getBoolean("Gms.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Gms.Aliases")) {
						commandMap.register(s, new gmsCommand(s));
					}
				}
			}
			// >> Gamemode creative
			if (!GamemodeCommandConfig.getConfig().getBoolean("gmc.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("gmc", new gmcCommand("gmc"));
				if (CommandAliasesConfig.getConfig().getBoolean("Gmc.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Gmc.Aliases")) {
						commandMap.register(s, new gmcCommand(s));
					}
				}
			}
			// >> Gamemode adventure
			if (!GamemodeCommandConfig.getConfig().getBoolean("gma.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("gma", new gmaCommand("gma"));
				if (CommandAliasesConfig.getConfig().getBoolean("Gma.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Gma.Aliases")) {
						commandMap.register(s, new gmaCommand(s));
					}
				}
			}
			// >> Gamemode spectator
			if (!GamemodeCommandConfig.getConfig().getBoolean("gmsp.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("gmsp", new gmspCommand("gmsp"));
				if (CommandAliasesConfig.getConfig().getBoolean("Gmsp.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Gmsp.Aliases")) {
						commandMap.register(s, new gmspCommand(s));
					}
				}
			}
			
			/* ------------------- *
			 * SCOREBOARD COMMANDS *
			 * ------------------- */
			// >> Scoreboard
			if (!ScoreboardCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("scoreboard", new ScoreboardCommand("scoreboard"));
				if (CommandAliasesConfig.getConfig().getBoolean("Scoreboard.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Scoreboard.Aliases")) {
						commandMap.register(s, new ScoreboardCommand(s));
					}
				}
			}
			
			/* ------------- *
			 * PING COMMANDS *
			 * ------------- */
			// >> Ping
			if (!PingCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("ping", new PingCommand("ping"));
				if (CommandAliasesConfig.getConfig().getBoolean("Ping.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Ping.Aliases")) {
						commandMap.register(s, new PingCommand(s));
					}
				}
			}
			
			/* ---------------------- *
			 * PLAYER OPTION COMMANDS *
			 * ---------------------- */
			// >> Main command
			if (!OptionPlayerConfigCommand.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("option", new MainCommandOptionPlayer("option"));
				if (CommandAliasesConfig.getConfig().getBoolean("Player-Option.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Player-Option.Aliases")) {
						commandMap.register(s, new MainCommandOptionPlayer(s));
					}
				}
			}
			
			/* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
			// >> Spawn
			if (!SpawnCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("spawn", new SpawnCommand("spawn"));
				if (CommandAliasesConfig.getConfig().getBoolean("Spawn.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Spawn.Aliases")) {
						commandMap.register(s, new SpawnCommand(s));
					}
				}
			}
			
			/* ------------- *
			 * TIME COMMANDS *
			 * ------------- */
			// >> Day
			if (!WeatherTimeCommandConfig.getConfig().getBoolean("Time.Set.Day.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("day", new DayCommand("day"));
				if (CommandAliasesConfig.getConfig().getBoolean("Day.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Day.Aliases")) {
						commandMap.register(s, new DayCommand(s));
					}
				}
			}
			// >> Night
			if (!WeatherTimeCommandConfig.getConfig().getBoolean("Time.Set.Night.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("night", new NightCommand("night"));
				if (CommandAliasesConfig.getConfig().getBoolean("Night.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Night.Aliases")) {
						commandMap.register(s, new NightCommand(s));
					}
				}
			}
			
			/* --------------- *
			 * VANISH COMMANDS *
			 * --------------- */
			// >> Vanish
			if (!VanishCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("vanish", new VanishCommand("vanish"));
				if (CommandAliasesConfig.getConfig().getBoolean("Vanish.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Vanish.Aliases")) {
						commandMap.register(s, new VanishCommand(s));
					}
				}
			}
			
			/* ------------- *
			 * WARP COMMANDS *
			 * ------------- */
			// >> Warp
			if (!WarpSetWarpCommandConfig.getConfig().getBoolean("Warp.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("warp", new WarpCommand("warp"));
				if (CommandAliasesConfig.getConfig().getBoolean("Warp.Warp.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Warp.Aliases")) {
						commandMap.register(s, new WarpCommand(s));
					}
				}
			}
			// >> Set Warp
			if (!WarpSetWarpCommandConfig.getConfig().getBoolean("SetWarp.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("setwarp", new SetWarpCommand("setwarp"));
				if (CommandAliasesConfig.getConfig().getBoolean("Warp.Set-Warp.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Set-Warp.Aliases")) {
						commandMap.register(s, new SetWarpCommand(s));
					}
				}
			}
			// >> Del warp
			if (!WarpSetWarpCommandConfig.getConfig().getBoolean("DelWarp.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("delwarp", new DelWarpCommand("delwarp"));
				if (CommandAliasesConfig.getConfig().getBoolean("Warp.Del-Warp.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Del-Warp.Aliases")) {
						commandMap.register(s, new DelWarpCommand(s));
					}
				}
			}
			// >> Warp list
			if (!WarpSetWarpCommandConfig.getConfig().getBoolean("WarpList.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("warplist", new WarpListCommand("warplist"));
				if (CommandAliasesConfig.getConfig().getBoolean("Warp.Warp-list.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Warp.Warp-list.Aliases")) {
						commandMap.register(s, new DelWarpCommand(s));
					}
				}
			}
			
			/* ---------------- *
			 * WEATHER COMMANDS *
			 * ---------------- */
			// >> Rain
			if (!WeatherTimeCommandConfig.getConfig().getBoolean("Weather.Set.Rain.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("rain", new RainCommand("rain"));
				if (CommandAliasesConfig.getConfig().getBoolean("Rain.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Rain.Aliases")) {
						commandMap.register(s, new RainCommand(s));
					}
				}
			}
			// >> Sun
			if (!WeatherTimeCommandConfig.getConfig().getBoolean("Weather.Set.Sun.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("sun", new SunCommand("sun"));
				if (CommandAliasesConfig.getConfig().getBoolean("Sun.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Sun.Aliases")) {
						commandMap.register(s, new SunCommand(s));
					}
				}
			}
			// >> Thunder
			if (!WeatherTimeCommandConfig.getConfig().getBoolean("Weather.Set.Thunder.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("thunder", new ThunderCommand("thunder"));
				if (CommandAliasesConfig.getConfig().getBoolean("Thunder.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Thunder.Aliases")) {
						commandMap.register(s, new ThunderCommand(s));
					}
				}
			}
			
			
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
		}
		
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Commands loaded");
		gcs(ChatColor.BLUE+"| ");
		
		new Manager(this).registerEvents();
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Events loaded");
		gcs(ChatColor.BLUE+"| ");
		
		// MYSQL
		
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Enable")) {
			host = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Host");
	        port = ConfigGeneral.getConfig().getInt("Plugin.Use.MYSQL.Port");
	        database = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Database");
	        username = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Username");
	        password = ConfigGeneral.getConfig().getString("Plugin.Use.MYSQL.Password");
				        
	        BukkitRunnable r = new BukkitRunnable() {
	            @Override
	            public void run() {
	            	try {
	            		useyamllistplayer = true;
	                    openConnection();
	                    statement = connection.createStatement();
	                } catch (ClassNotFoundException e) {
	                    e.printStackTrace();
	                    useyamllistplayer = true;
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    useyamllistplayer = true;
	                }
	            }
	        };
	        
	        r.runTaskAsynchronously(this);
		} else {
			useyamllistplayer = true;
		}
		        		
		// Keep option p
		if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Keep-The-Option")) {
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"PlaceHolderAPI detected");
				gcs(ChatColor.BLUE+"| ");
				ConfigGeneral.getConfig().set("Plugin.Use.PlaceholderAPI", Boolean.valueOf(true));
				ConfigGeneral.saveConfigFile();
				Bukkit.getPluginManager().registerEvents(this, this);
			} else {
				ConfigGeneral.getConfig().set("Plugin.Use.PlaceholderAPI", Boolean.valueOf(false));
				ConfigGeneral.saveConfigFile();
			}
		}
		
		// check
		UpdateCheck();
		VersionUtils.onGetServerVersiononLoad();
						
		OnJoin.player_list.clear();
		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			OnJoin.player_list.add(p);
		}
		FlyCommand.player_list_flyc.clear();
		FunFeatures.player_list_dbenable.clear();
		
		// Materials
		if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
			MaterialMethod = "true";
		} else {
			MaterialMethod = "false";
		}
				
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100L, 1L);
		
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Tps.Warn-system")) {
			WarnTPS.runWarnSystemTask(this);
		}
		
		/*if (NameTagConfig.getConfig().getBoolean("nametag-general.enable")) {
			Tablist.ScoreboardManager();
		}*/
		
		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable")) {
				if (FlyCommand.player_list_flyc.contains(p)) {
					FlyCommand.player_list_flyc.remove(p);
					p.setAllowFlight(false);
					p.setFlying(false);
				}
			}
		}
		
		EmojiesUtility.setaliaseslist();
		
		nmsver = Bukkit.getServer().getClass().getPackage().getName();
	    nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
	    
	    if (nmsver.equalsIgnoreCase("v1_8_R1")) {
	      useOldMethods = true;
	    }
	    
	    player_spawnwarpdelay.clear();
	    
	    // Auto broadcast
	    if (AutoBroadcastConfig.getConfig().getBoolean("Config.Enable")) {
	    	
	    	interval = AutoBroadcastConfig.getConfig().getInt("Config.Interval");
	    	
		    Iterator<?> iterator2 = AutoBroadcastConfig.getConfig().getConfigurationSection("messages").getKeys(false).iterator();
		    
		    Integer abnumberput = 0;
		    
		    while (iterator2.hasNext()) {
				String string = (String)iterator2.next();
				autobroadcast.put(abnumberput, string);
				abnumberput++;
				autobroadcast_total++;
		    }
		    
		    autobroadcast_total--;
		    
		    @SuppressWarnings("unused")
			BukkitTask TaskName = (new AutoBroadcast(this)).runTaskTimer(this, 20L, AutoBroadcastConfig.getConfig().getInt("Config.Interval") * 20);
	    }
	    
		
	    // Scoreboard
	    if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
	    	File folder = new File(getDataFolder().getAbsolutePath() + "/Scoreboard/");
		    
		    /*File[] listOfFiles = folder.listFiles();

		    for (int i = 0; i < listOfFiles.length; i++) {
		    	if (listOfFiles[i].isFile()) {
		    		System.out.println("File " + listOfFiles[i].getName());
		    	} else if (listOfFiles[i].isDirectory()) {
		    		System.out.println("Directory " + listOfFiles[i].getName());
		    	}
		    }*/
		    
		    loadScoreboards(folder);
		    
		    for (Player p : Bukkit.getOnlinePlayers()) {
	            playerWorldTimer.put(p, System.currentTimeMillis() + 5000);
		    }
		    
		    new BukkitRunnable() {
	            @Override
	            public void run() {
	                for (Player player : playerWorldTimer.keySet()) {
	                	if (!nosb.contains(player)) {
		                    long time = playerWorldTimer.get(player);
	
		                    if (time < System.currentTimeMillis())
		                        continue;
	
		                    createDefaultScoreboard(player);
	                	}
	                }
	            }
		    }.runTaskTimer(this, 0, 20);
	    }
	    
	    // Tablist
	    if (TablistConfig.getConfig().getBoolean("Tablist.enable")) {
		    this.PacketPlayOutPlayerListHeaderFooter = NMSClass.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
		    try {
				this.newPacketPlayOutPlayerListHeaderFooter = this.PacketPlayOutPlayerListHeaderFooter.getConstructor(new Class[0]);
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		    this.ChatComponentText = NMSClass.getNMSClass("ChatComponentText");
		    
		    if (TablistConfig.getConfig().getBoolean("Tablist.header.enabled")) {
		    	hea = String.valueOf(TablistConfig.getConfig().getStringList("Tablist.header.message"));
		    	
		    	hea = hea.substring(1, hea.length() - 1).replaceAll(", ", "\n");
		    	hea = hea.replaceAll("&", "§");
		    }
		    
		    if (TablistConfig.getConfig().getBoolean("Tablist.footer.enabled")) {
		    	foo = String.valueOf(TablistConfig.getConfig().getStringList("Tablist.footer.message"));
		    	
		    	foo = foo.substring(1, foo.length() - 1).replaceAll(", ", "\n");
		    	foo = foo.replaceAll("&", "§");
		    }

		    new BukkitRunnable() {
				
				@Override
				public void run() {
					
					try {
						for (Player p : Bukkit.getServer().getOnlinePlayers()) {
							
							String hea2 = "";
							String foo2 = "";
							Object packet = null;
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								hea2 = PlaceholderAPI.setPlaceholders(p, hea);
								foo2 = PlaceholderAPI.setPlaceholders(p, foo);
							}
							hea2 = MessageUtils.ReplaceMainplaceholderP(hea, p);
							foo2 = MessageUtils.ReplaceMainplaceholderP(foo, p);
							
							Constructor<?> constructor = ChatComponentText.getConstructors()[0];
							Object header = constructor.newInstance(hea2);
							Object footer = constructor.newInstance(foo2);
							
							try {
								Field a = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("a");
								a.setAccessible(true);
								Field b = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("b");
								b.setAccessible(true);
								
								packet = newPacketPlayOutPlayerListHeaderFooter.newInstance(new Object[0]);
								
								a.set(packet, header);
								b.set(packet, footer);
							} catch (Exception e) {
								Field a = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("header");
								a.setAccessible(true);
								Field b = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("footer");
								b.setAccessible(true);
								
								try {
									packet = newPacketPlayOutPlayerListHeaderFooter.newInstance(new Object[0]);
								} catch (InstantiationException | InvocationTargetException e1) {
									e1.printStackTrace();
								}
								
								a.set(packet, header);
								b.set(packet, footer);
							}
							
							TitleUtils.sendPacket(p, packet);
						}
					} catch (IllegalAccessException | NoSuchFieldException | SecurityException | 
							IllegalArgumentException | InstantiationException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}.runTaskTimer(this, 20L, 20);
	    }
	    
	    buildbypasscommand.clear();
	    
	    // Variable set
	    
	    OtherUtils.totalMemory();
	    OtherUtils.totalDisk();
	    OtherUtils.getOperatingSystem();
	    OtherUtils.javaver = String.valueOf(OtherUtils.getJavaVersion());
	    
	    new BukkitRunnable() {
	    	
	    	@Override
			public void run() {
	    		OtherUtils.getMemoryUsageBar();
	    		OtherUtils.getCPUUsageBar();
	    		OtherUtils.getDiskUsageBar();
	    		OtherUtils.maxMemory();
	    		OtherUtils.freeMemory();
	    		OtherUtils.freeDisk();
	    		OtherUtils.usableDisk();
	    	}
	    	
	    }.runTaskTimer(this, 0, 60);
	    
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The last remaining things to be loaded have been loaded");
		gcs(ChatColor.BLUE+"| ");
		
		// Check version
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"This server is running on "+VersionUtils.getVersionS());
		gcs(ChatColor.BLUE+"| ");
		
		// Warning
		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable") && OnJoinConfig.getConfig().getBoolean("Fly.Enable")) {
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"Please note that if a player can both fly, or make a double jump");
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"It can cause problems");
			gcs(ChatColor.YELLOW+"| ");
		}
		
		gcs(ChatColor.BLUE+"| "+ChatColor.DARK_RED+"License:"+ChatColor.RESET);
		gcs(ChatColor.BLUE+"| ");
		gcs(ChatColor.BLUE+"| "+ChatColor.GREEN+"YOU CAN:");
		gcs(ChatColor.BLUE+"| "+ChatColor.RESET+"- Modify the plugin");
		gcs(ChatColor.BLUE+"| "+ChatColor.RESET+"- Decompile it");
		gcs(ChatColor.BLUE+"| "+ChatColor.RESET+"- Upload it and share it");
		gcs(ChatColor.BLUE+"| ");
		gcs(ChatColor.BLUE+"| "+ChatColor.RED+"YOU CAN'T:");
		gcs(ChatColor.BLUE+"| "+ChatColor.RESET+"- Claim the plugin \"hawn\" as your property");
		gcs(ChatColor.BLUE+"| "+ChatColor.RESET+"- Use it for commercial purposes");
		gcs(ChatColor.BLUE+"| ");
		
		gcs(ChatColor.BLUE+"| ------------------------------------");
		gcs(ChatColor.BLUE+"| ");
		gcs(ChatColor.BLUE+"| "+ChatColor.GREEN+"Hawn ready !");
		gcs(ChatColor.BLUE+"| ");
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		
		// Tablist.disable();
		
		fileconfiglist.clear();
		
		for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		
		gcs(ChatColor.RED+"Hawn - Good bye");
	}
	
	public static Main getInstance() {
		return instance;
	}
	
	public static String getVersion() {
		return versions;
	}
	
	private static void gcs(String str) {
		Bukkit.getConsoleSender().sendMessage(str);
	}
	
	public static String getVersionUpdate() {
		
		UpdateCheckReload();
		
		return UpToDate;
	}
	
	public static void UpdateCheck() {
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Update.Check-Update")) {
			UpdateChecker updater = new UpdateChecker(Main.getInstance(), 66907);
			try {
				if (updater.checkForUpdates()) {
					gcs(ChatColor.BLUE+"| "+ChatColor.RED+"Old version of Hawn detected");
					gcs(ChatColor.BLUE+"| ");
					UpToDate = "§cOld Version detected";
				} else {
					gcs(ChatColor.BLUE+"| "+ChatColor.GREEN+"Plugin is up to date");
					gcs(ChatColor.BLUE+"| ");
					UpToDate = "§aPlugin up to date";
				}
			} catch (Exception e) {
				System.out.println("Could not check for updates! Stacktrace:");
				e.printStackTrace();
			}
		}
	}
	
	public static void UpdateCheckReload() {
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Update.Check-Update")) {
			UpdateChecker updater = new UpdateChecker(Main.getInstance(), 66907);
			try {
				if (updater.checkForUpdates()) {
					UpToDate = "§cOld Version detected";
				} else {
					UpToDate = "§aPlugin up to date";
				}
			} catch (Exception e) {
				System.out.println("Could not check for updates! Stacktrace:");
				e.printStackTrace();
			}
		}
	}
	
	public void loadScoreboards(File fo) {
		if (fo.listFiles() == null) {
			return;
		}
		
		if (fo.listFiles().length <= 0) {
	    	return;
		}
		
		for (File f : fo.listFiles()) {
			if (f.getName().endsWith(".yml")) {
				String perm = "hawn.scoreboard." + f.getName().replace(".yml", "");
				String filename = f.getName().replace(".yml", "");
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
				fr.Dianox.Hawn.Utility.Scoreboard.ScoreboardInfo info = new fr.Dianox.Hawn.Utility.Scoreboard.ScoreboardInfo(cfg, perm);
				this.infoname.put(filename, perm);
				this.infoname2.put(perm, filename);
				this.info.put(perm, info);
				gcs(ChatColor.BLUE+"| "+ChatColor.GRAY+"Loaded the scoreboard : " + ChatColor.GREEN + f.getName() + ChatColor.GRAY +" with the permission : " + ChatColor.GREEN + perm);
			} else {
				gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"The file : "+ f.getName() + "is not accepted. Accepted only '.yml' files (YAML)");
			} 
		} 
	}
	
	public void createDefaultScoreboard(Player player) {
		
		if (nosb.contains(player)) {
			return;
		}
		String bool = getYmlaMysqlsb(player, "keepsb");

		if (bool.equalsIgnoreCase("TRUE") && ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Option.Keep-Scoreboard-Change")) {
			String sb = getYmlaMysqlsb(player, "scoreboard");
			if (boards.containsKey(player)) {
				ScoreboardInfo in = (ScoreboardInfo)this.info.get("hawn.scoreboard."+sb);
				((PlayerBoard)boards.get(player)).createNew(in.getText(), in.getTitle(), in.getTitleUpdate(), in.getTextUpdate());
			} else {
				new PlayerBoard(this, player, (ScoreboardInfo)info.get("hawn.scoreboard."+sb));
			}
		} else {
			for (String s : info.keySet()) {
	            ScoreboardInfo in = info.get(s);
	            if (in.getEnabledWorlds() != null && in.getEnabledWorlds().contains(player.getWorld().getName())) {
	                if (player.hasPermission(s)) {
	                    if (boards.containsKey(player)) {
	                        if (boards.get(player).getList().equals(in.getText())) {
	                            player.setScoreboard(boards.get(player).getBoard());
	                            saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                            return;
	                        }
	                        boards.get(player).createNew(in.getText(), in.getTitle(), in.getTitleUpdate(), in.getTextUpdate());
	                        saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    } else {
	                        new PlayerBoard(this, player, info.get(s));
	                        saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    }
	                    saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    return;
	                }
	            }
	        }
		}
	}
	
	public static String getYmlaMysqlsb(Player p, String option) {
		String value = "";
		
		PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		PlayerConfig.saveConfigFile();
		
		if (Main.useyamllistplayer) {
			if (option.equalsIgnoreCase("scoreboard")) {
				if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));
					
		            PlayerConfig.saveConfigFile();
		        }
				
				value = PlayerConfig.getConfig().getString("player_option_keep_sb."+p.getUniqueId()+".Scoreboard");
			} else if (option.equalsIgnoreCase("keepsb")) {
				
				if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));
					
		            PlayerConfig.saveConfigFile();
				}
				
				if (PlayerConfig.getConfig().getBoolean("player_option_keep_sb."+p.getUniqueId()+".Activate"))  {
					value = "TRUE";
				} else {
					value = "FALSE";
				}
			}
		} else {
			if (!SQL.tableExists("player_option_keep_sb")) {
				SQL.createTable("player_option_keep_sb", "player TEXT, player_UUID TEXT, Activate TEXT, Scoreboard TEXT");
			}
			
			if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_keep_sb")) {
				if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
					PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));
					
		            PlayerConfig.saveConfigFile();
				}
				
				if (option.equalsIgnoreCase("scoreboard")) {
					value = String.valueOf(SQL.getInfoString("player_option_keep_sb", "Scoreboard", "" + p.getUniqueId() + ""));
				} else if (option.equalsIgnoreCase("keepsb")) {
					value = String.valueOf(SQL.getInfoString("player_option_keep_sb", "Activate", "" + p.getUniqueId() + ""));
				}
				SQL.set("player_option_pv", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				if (option.equalsIgnoreCase("scoreboard")) {
					if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));
						
			            PlayerConfig.saveConfigFile();
					}
					
					if (PlayerConfig.getConfig().getBoolean("player_option_pv."+p.getUniqueId()+".Activate"))  {
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', 'TRUE', '"+PlayerConfig.getConfig().getString("player_option_pv."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_pv");
					} else {
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', 'FALSE', '"+PlayerConfig.getConfig().getString("player_option_pv."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_pv");
					}
					value = PlayerConfig.getConfig().getString("player_option_pv."+p.getUniqueId()+".Scoreboard");
				} else if (option.equalsIgnoreCase("keepsb")) {
					if (!PlayerConfig.getConfig().isSet("player_option_keep_sb."+p.getUniqueId()+".player_name")) {
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
						PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(null));
						
			            PlayerConfig.saveConfigFile();
					}
					
					if (PlayerConfig.getConfig().getBoolean("player_option_pv."+p.getUniqueId()+".Activate"))  {
						value = "TRUE";
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '"+PlayerConfig.getConfig().getString("player_option_pv."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_pv");
					} else {
						value = "FALSE";
						SQL.insertData("player, player_UUID, Activate, Scoreboard",
		                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "', '"+PlayerConfig.getConfig().getString("player_option_pv."+p.getUniqueId()+".Scoreboard")+"' ", "player_option_pv");
					}
				}
			}
		}
		
		return value;
	}
	
	public void saveSBmysqlyaml(Player p, String sb, String boolea) {
		PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		if (boolea.equalsIgnoreCase("FALSE")) {
			PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
		} else {
			PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Activate", Boolean.valueOf(true));
		}
		PlayerConfig.getConfig().set("player_option_keep_sb."+p.getUniqueId()+".Scoreboard", String.valueOf(sb));
		
		PlayerConfig.saveConfigFile();
		
		if (!Main.useyamllistplayer) {
			if (!SQL.tableExists("player_option_keep_sb")) {
				SQL.createTable("player_option_keep_sb", "player TEXT, player_UUID TEXT, Activate TEXT, Scoreboard TEXT");
			}
			
			if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_keep_sb")) {
				SQL.set("player_option_keep_sb", "Activate", ""+boolea+"", "player_UUID", "" + p.getUniqueId() + "");
				SQL.set("player_option_keep_sb", "Scoreboard", ""+sb+"", "player_UUID", "" + p.getUniqueId() + "");
				SQL.set("player_option_keep_sb", "player", ""+p.getName()+"", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				SQL.insertData("player, player_UUID, Activate, Scoreboard",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "', '"+sb+"' ", "player_option_keep_sb");
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public HashMap<Player, PlayerBoard> getBoards() {
		return this.boards;
	}
	  
	@SuppressWarnings("static-access")
	public List<PlayerBoard> getAllboards() {
		return this.allboards;
	}
	  
	public HashMap<String, ScoreboardInfo> getInfo() {
		return this.info; 
	}
	  
	@SuppressWarnings("static-access")
	public HashMap<Player, Long> getPlayerWorldTimer() {
		return this.playerWorldTimer;
	}

	public static void GetSetWorld() {
		OnJoinPW.setGetWorldforJoinMessage();
		OnQuitPW.setGetWorldforQuitMessage();
		OnJoinPW.setWGetWorldforMOTD();
		BasicEventsPW.setWGetWorldforGM();
		BasicEventsPW.setWGetWorldforKGM();
		BasicEventsPW.setWGetWorldforVOIDTP();
		ProtectionPW.setWGetWorldProtectionBreak();
		ProtectionPW.setWGetWorldProtectionPlace();
		OnJoinPW.setWGetWorldFood();
		OnJoinPW.setWGetWorldHealth();
		BasicEventsPW.setWGetWorldkFood();
		BasicEventsPW.setWGetWorldANTIDAMAGE();
		ProtectionPW.setWGetWorldProtectionHagingBreakByEntity();
		ProtectionPW.setWGetWorldProtectionPlayerInteractEntityItemFrame();
		OtherFeaturesPW.setWGetWorldEventColorSign();
		WorldPW.setWGetWorldServerDisableLightningStrike();
		WorldPW.setWGetWorldServerDisableThunderChange();
		WorldPW.setWGetWorldServerDisableWeather();
		WorldPW.setWGetWorldServerDisableBurnBlock();
		PlayerEventsPW.setWGetWorldItemDrop();
		PlayerEventsPW.setWGetWorldItemPickUP();
		PlayerEventsPW.setWGetWorldMoveItem();
		PlayerEventsPW.setWGetWorldItemDamage();
		WorldPW.setWGetWorldServerDisableExplosion();
		WorldPW.setWGetWorldServerDisableLeaveDecay();
		CosmeticsPW.setWGetWorldFirework();
		WorldPW.setWGetWorldServerDisableFireSpread();
		WorldPW.setWGetWorldServerDisableSpawningMobAnimals();
		PlayerEventsPW.setWGetWorldServerDisableDeathMessage();
		PlayerEventsPW.setWRespawnEvent();
		PlayerEventsPW.setWGetWorldForceSelectedJoin();
		WorldPW.setWGetWorldServerDisableblockFade();
		PlayerEventsPW.setWGetWorldClearDropOnDeath();
		OnJoinPW.setWGetWorldInventory();
		OnJoinPW.setWGetWorldClearChat();
		OnJoinPW.setWGetWorldResetExperience();
		OnJoinPW.setWGetWorldResetLevel();
		OnJoinPW.setWGetWorldSoundJoin();
		CosmeticsPW.setWGetWorldJumpPads();
		CommandsPW.setWGetWorldJoinCommandConsoleNew();
		CommandsPW.setWGetWorldJoinCommandConsoleNoNew();
		CommandsPW.setWGetWorldJoinCommandPlayerNew();
		CommandsPW.setWGetWorldJoinCommandPlayerNoNew();
		CommandsPW.setWGetWorldQuitCommandConsole();
		OnJoinPW.setWGetWorldflyoj();
		PlayerEventsPW.setWGetFunDoubleJump();
		OnJoinPW.setWGetWorldJoinTitle();
		OnJoinPW.setWGetWorldFirstJoinTitle();
		OnJoinPW.setWSOJ();
		OnJoinPW.setWGetWorldFirstJoinab();
		OnJoinPW.setWGetWorldJoinab();
		BasicEventsPW.setWGetWorldautobroadcast();
		OnJoinPW.setWGetWorldblindess();
		OnJoinPW.setWGetWorldjump();
		ChangeWorldPW.setWKEEPFLY();
		ChangeWorldPW.setWGetWorldGamemodeChangeWorld();
		CjiPW.setItemPlayerVisibility();
	}
	
	
	// MYSQL
	public void openConnection() throws SQLException, ClassNotFoundException {
	    if (connection != null && !connection.isClosed()) {
	        return;
	    }
	 
	    if (host == null || username == null || password == null || database == null) {
	        return;
	    }
	    
	    synchronized (this) {
	        if (connection != null && !connection.isClosed()) {
	            return;
	        }
	        Class.forName("com.mysql.jdbc.Driver");
	        try {
	        	if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Use-SSL")) {
	        		connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database, this.username, this.password);
	        	} else {
	        		connection = DriverManager.getConnection("jdbc:mysql://" + this.host+ ":" + this.port + "/" + this.database + "?useSSL=false", this.username, this.password);
	        	}
	        	useyamllistplayer = false;
	        } catch (Exception e) {
	        	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "MySQL Connect Error: " + e.getMessage());
	        	useyamllistplayer = true;
			}
	    }
	}
	
	public static void updateSQL(String command) {
		if (command == null) {
			return;
		}
		
		try {
			statement.executeUpdate(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet querySQL(String command) {
		if (command == null) {
			return null;
		}
		
		ResultSet rs = null;
		
		try {
			rs = statement.executeQuery(command);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}

}