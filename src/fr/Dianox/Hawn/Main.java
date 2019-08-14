package fr.Dianox.Hawn;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.bukkit.Material;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import fr.Dianox.Hawn.Commands.DelSpawnCommand;
import fr.Dianox.Hawn.Commands.HawnCommand;
import fr.Dianox.Hawn.Commands.PanelAdminCommand;
import fr.Dianox.Hawn.Commands.PingCommand;
import fr.Dianox.Hawn.Commands.SetSpawnCommand;
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
import fr.Dianox.Hawn.Commands.Features.Chat.WarningCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.ClassicGMCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmaCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmcCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmsCommand;
import fr.Dianox.Hawn.Commands.Features.GameMode.gmspCommand;
import fr.Dianox.Hawn.Commands.Features.OptionPlayers.MainCommandOptionPlayer;
import fr.Dianox.Hawn.Commands.Features.Specials.ABAnnouncerCommand;
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
import fr.Dianox.Hawn.Event.AutoBroadcast_AB;
import fr.Dianox.Hawn.Event.AutoBroadcast_Title;
import fr.Dianox.Hawn.Event.BasicFeatures;
import fr.Dianox.Hawn.Event.FunFeatures;
import fr.Dianox.Hawn.Event.OnJoin;
import fr.Dianox.Hawn.Event.World.AlwaysDayTask;
import fr.Dianox.Hawn.Event.World.AlwaysNightTask;
import fr.Dianox.Hawn.Utility.CheckConfig;
import fr.Dianox.Hawn.Utility.EmojiesUtility;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.NMSClass;
import fr.Dianox.Hawn.Utility.OtherUtils;
import fr.Dianox.Hawn.Utility.PlayerOptionSQLClass;
import fr.Dianox.Hawn.Utility.TitleUtils;
import fr.Dianox.Hawn.Utility.VersionUtils;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.CommandAliasesConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.CustomCommandConfig;
import fr.Dianox.Hawn.Utility.Config.ScoreboardMainConfig;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ActionbarAnnouncerConfig;
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
import fr.Dianox.Hawn.Utility.Config.Commands.WarningCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WarpSetWarpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WeatherTimeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGLP;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.FireworkListCUtility;
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
import fr.Dianox.Hawn.Utility.Config.Messages.Adminstration.AdminPanelConfig;
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

	private static String versions = "0.7.7-Alpha";
	public static Boolean devbuild = false;
	public static Integer devbuild_number = 0;
	
	public static String UpToDate, MaterialMethod, nmsver;
	public static boolean useOldMethods = false;
	public static List<String> fileconfiglist = new ArrayList<String>();

	public static Connection connection;
	private String host,
					database,
					username, 
					password;
	private int port;
	private static Statement statement;
	public static boolean useyamllistplayer = false;

    public static HashMap<Integer, String> autobroadcast = new HashMap<>();
    public static HashMap<Integer, String> autobroadcast_titles = new HashMap<>();
    public static HashMap<Integer, String> autobroadcast_ab = new HashMap<>();
    public static Integer autobroadcast_total= 0;
    public static Integer autobroadcast_total_titles = 0;
    public static Integer autobroadcast_total_ab = 0;
    public static int interval = 0;
    public static int interval_titles = 0;
    public static int interval_ab = 0;
    public static int curMsg = 0;
    public static int curMsg_ab = 0;
    public static int curMsg_titles = 0;
	public Scoreboard board;

	public static HashMap<Player, PlayerBoard> boards = new HashMap<Player, PlayerBoard>();
	public static List<PlayerBoard> allboards = new ArrayList<PlayerBoard>();
	public HashMap<String, ScoreboardInfo> info = new HashMap<String, ScoreboardInfo>();
	public HashMap<String, String> infoname = new HashMap<String, String>();
	public HashMap<String, String> infoname2 = new HashMap<String, String>();
	public static HashMap<Player, Long> playerWorldTimer = new HashMap<Player, Long>();
	public static List<Player> nosb = new ArrayList<Player>();
	public static List<Player> injumpwithjumppad = new ArrayList<Player>();
	public static boolean newmethodver = false;
	
	public static HashMap<UUID, Integer> player_spawnwarpdelay = new HashMap<UUID, Integer>();
	public static List<Player> inwarpd = new ArrayList<Player>();
	public static List<Player> inspawnd = new ArrayList<Player>();
	
	public String hea = "";
	public String foo = "";

    private static Class<?> PacketPlayOutPlayerListHeaderFooter;
    private static Class<?> ChatComponentText;
    private static Constructor<?> newPacketPlayOutPlayerListHeaderFooter;

    public static List<Player> buildbypasscommand = new ArrayList<Player>();

    public static HashMap<Player, Long> hiderCooldowns = new HashMap<Player, Long>();
    
    public static HashMap<Player, Integer> TaskVanishAB = new HashMap<Player, Integer>();
    
    public static HashMap<String, String> configfile = new HashMap<String, String>();
    public static HashMap<String, String> configfilereverse = new HashMap<String, String>();
    public static HashMap<Player, String> configfileinuse = new HashMap<Player, String>();
    
    public static List<Material> interactables = new ArrayList<Material>();
    
    private WorldGuardPlugin worldGuard;
    public Boolean worldGuard_recent_version = false;
    
    @SuppressWarnings("static-access")
	@Override
	public void onEnable() {
		super.onEnable();

		if (devbuild) {
			versions = versions + " " + "DevBuild" + " " + devbuild_number;
		}

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

		 configfile.clear();
		 configfilereverse.clear();
		
			// Load config
			ConfigSpawn.loadConfig((Plugin) this);
			configfile.put("G-spawn", "spawn.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "spawn.yml", "G-spawn");
			ConfigGeneral.loadConfig((Plugin) this);
			configfile.put("G-general", "general.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "general.yml", "G-general");
			ServerListConfig.loadConfig((Plugin) this);
			configfile.put("G-ServerList", "ServerList.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "ServerList.yml", "G-ServerList");
			AutoBroadcastConfig.loadConfig((Plugin) this);
			configfile.put("G-AutoBroadcast", "AutoBroadcast.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "AutoBroadcast.yml", "G-AutoBroadcast");
			BetweenServersConfig.loadConfig((Plugin) this);
			configfile.put("G-between-servers", "between-servers.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "between-servers.yml", "G-between-servers");
			CommandAliasesConfig.loadConfig((Plugin) this);
			configfile.put("G-command-aliases", "command-aliases.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "command-aliases.yml", "G-command-aliases");
			WarpListConfig.loadConfig((Plugin) this);
			configfile.put("G-warplist", "warplist.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "warplist.yml", "G-warplist");
			ScoreboardMainConfig.loadConfig((Plugin) this);
			configfile.put("G-Scoreboard-General", "Scoreboard-General.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Scoreboard-General.yml", "G-Scoreboard-General");
			
			OtherFeaturesConfig.loadConfig((Plugin) this);
			configfile.put("E-OtherFeatures", "Events/OtherFeatures.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/OtherFeatures.yml", "E-OtherFeatures");
			WorldEventConfig.loadConfig((Plugin) this);
			configfile.put("E-WorldEvent", "Events/WorldEvent.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/WorldEvent.yml", "E-WorldEvent");
			
			ConfigGProtection.loadConfig((Plugin) this);
			configfile.put("E-ProtectionWorld", "Events/ProtectionWorld.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/ProtectionWorld.yml", "E-ProtectionWorld");
			
			VoidTPConfig.loadConfig((Plugin) this);
			configfile.put("E-VoidTP", "Events/VoidTP.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/VoidTP.yml", "E-VoidTP");
			ProtectionPlayerConfig.loadConfig((Plugin) this);
			configfile.put("E-ProtectionPlayer", "Events/ProtectionPlayer.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/ProtectionPlayer.yml", "E-ProtectionPlayer");
			PlayerEventsConfig.loadConfig((Plugin) this);
			configfile.put("E-PlayerEvents", "Events/PlayerEvents.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/PlayerEvents.yml", "E-PlayerEvents");
			OnJoinConfig.loadConfig((Plugin) this);
			configfile.put("E-OnJoin", "Events/OnJoin.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/OnJoin.yml", "E-OnJoin");
			CommandEventConfig.loadConfig((Plugin) this);
			configfile.put("E-OnCommands", "Events/OnCommands.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/OnCommands.yml", "E-OnCommands");
			ConfigGJoinQuitCommand.loadConfig((Plugin) this);
			configfile.put("E-JoinQuitCommand", "Events/JoinQuitCommand.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/JoinQuitCommand.yml", "E-JoinQuitCommand");
			OnChatConfig.loadConfig((Plugin) this);
			configfile.put("E-Chat", "Events/Chat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/Chat.yml", "E-Chat");
			PlayerWorldChangeConfigE.loadConfig((Plugin) this);
			configfile.put("E-PlayerWorldChange", "Events/PlayerWorldChange.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/PlayerWorldChange.yml", "E-PlayerWorldChange");
			
			ConfigMGeneral.loadConfig((Plugin) this);
			configfile.put("MC-General", "Messages/Classic/General.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Messages/Classic/General.yml", "MC-General");
			ConfigMEvents.loadConfig((Plugin) this);
			configfile.put("MC-Events", "Messages/Classic/Events.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Messages/Classic/Events.yml", "MC-Events");
			ConfigMProtection.loadConfig((Plugin) this);
			configfile.put("MC-Protection", "Messages/Classic/Protection.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Messages/Classic/Protection.yml", "MC-Protection");
			ConfigMOStuff.loadConfig((Plugin) this);
			configfile.put("MC-SomeOtherStuff", "Messages/Classic/SomeOtherStuff.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Messages/Classic/SomeOtherStuff.yml", "MC-SomeOtherStuff");
			ConfigMCommands.loadConfig((Plugin) this);
			configfile.put("MC-Commands", "Messages/Classic/Commands.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Messages/Classic/Commands.yml", "MC-Commands");
			ConfigMPlayerOption.loadConfig((Plugin) this);
			configfile.put("MC-PlayerOption", "Messages/Classic/PlayerOption.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Messages/Classic/PlayerOption.yml", "MC-PlayerOption");

			ActionbarAnnouncerConfig.loadConfig((Plugin) this);
			configfile.put("C-ActionBarAnnouncer", "Commands/ActionBarAnnouncer.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ActionBarAnnouncer.yml", "C-ActionBarAnnouncer");
			FlyCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Fly", "Commands/Fly.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Fly.yml", "C-Fly");
			WeatherTimeCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Weather-Time", "Commands/Weather-Time.yml");
			configfilereverse.put(this.getDataFolder() + "/" +  "Commands/Weather-Time.yml", "C-Weather-Time");
			HelpCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Help", "Commands/Help.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Help.yml", "C-Help");
			ClearChatCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-ClearChat", "Commands/ClearChat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ClearChat.yml", "C-ClearChat");
			SpawnCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Spawn", "Commands/Spawn.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Spawn.yml", "C-Spawn");
			MuteChatCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-MuteChat", "Commands/MuteChat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/MuteChat.yml", "C-MuteChat");
			PingCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Ping", "Commands/Ping.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Ping.yml", "C-Ping");
			DelayChatCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-DelayChat", "Commands/DelayChat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/DelayChat.yml", "C-DelayChat");
			BroadCastCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Broadcast", "Commands/Broadcast.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Broadcast.yml", "C-Broadcast");
			HealCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Heal", "Commands/Heal.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Heal.yml", "C-Heal");
			WarpSetWarpCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Warp-SetWarp", "Commands/Warp-SetWarp.yml");
			configfilereverse.put(this.getDataFolder() + "/" +  "Commands/Warp-SetWarp.yml", "C-Warp-SetWarp");
			VanishCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Vanish", "Commands/Vanish.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Vanish.yml", "C-Vanish");
			TitleAnnouncerConfig.loadConfig((Plugin) this);
			configfile.put("C-TitleAnnouncer", "Commands/TitleAnnouncer.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/TitleAnnouncer.yml", "C-TitleAnnouncer");
			ClearInvCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-ClearInv", "Commands/ClearInv.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ClearInv.yml", "C-ClearInv");
			EmojiCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Emoji", "Commands/Emoji.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Emoji.yml", "C-Emoji");
			ScoreboardCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Scoreboard", "Commands/Scoreboard.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Scoreboard.yml", "C-Scoreboard");
			GamemodeCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Gamemode", "Commands/Gamemode.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Gamemode.yml", "C-Gamemode");
			OptionPlayerConfigCommand.loadConfig((Plugin) this);
			configfile.put("C-PlayerOption", "Commands/PlayerOption.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/PlayerOption.yml", "C-PlayerOption");
			WarningCommandConfig.loadConfig((Plugin) this);
			configfile.put("C-Warning", "Commands/Warning.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Warning.yml", "C-Warning");
			
			ConfigGCos.loadConfig((Plugin) this);
			configfile.put("CF-OnJoin", "Cosmetics-Fun/OnJoin.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/OnJoin.yml", "CF-OnJoin");
			ConfigGLP.loadConfig((Plugin) this);
			configfile.put("CF-JumpPads", "Cosmetics-Fun/JumpPads.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/JumpPads.yml", "CF-JumpPads");
			ConfigFDoubleJump.loadConfig((Plugin) this);
			configfile.put("CF-DoubleJump", "Cosmetics-Fun/DoubleJump.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/DoubleJump.yml", "CF-DoubleJump");
			FireworkListCUtility.loadConfig((Plugin) this);
			configfile.put("CFU-Firework-List", "Cosmetics-Fun/Utility/Firework-List.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Firework-List.yml", "CFU-Firework-List");
			
		//NameTagConfig.loadConfig((Plugin) this);
		TablistConfig.loadConfig((Plugin) this);
		configfile.put("G-CustomCommand", "CustomCommand.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "CustomCommand.yml", "G-CustomCommand");
		
		CustomCommandConfig.loadConfig((Plugin) this);
		configfile.put("T-Tablist", "Tablist/Tablist.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Tablist/Tablist.yml", "T-Tablist");
		
		SpecialCjiHidePlayers.loadConfig((Plugin) this);
		configfile.put("CJI-Special-HidePlayers", "CustomJoinItem/Special-HidePlayers.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "CustomJoinItem/Special-HidePlayers.yml", "CJI-Special-HidePlayers");
		
		if (!ScoreboardMainConfig.getConfig().isSet("DefaultConfigGenerated")) {
			defaultscoreboardconfig.loadConfig((Plugin) this);
			worldnetherdsc.loadConfig((Plugin) this);

			ScoreboardMainConfig.getConfig().set("DefaultConfigGenerated", true);
			ScoreboardMainConfig.saveConfigFile();
		}

		InfoServerOverviewC.loadConfig((Plugin) this);
		configfile.put("MA-Server-Info", "Messages/Administration/Server-Info.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/Administration/Server-Info.yml", "MA-Server-Info");
		ErrorConfigAM.loadConfig((Plugin) this);
		configfile.put("MA-Errors", "Messages/Administration/Errors.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/Administration/Errors.yml", "MA-Errors");
		OtherAMConfig.loadConfig((Plugin) this);
		configfile.put("MA-Others", "Messages/Administration/Others.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/Administration/Others.yml", "MA-Others");
		SpawnMConfig.loadConfig((Plugin) this);
		configfile.put("MA-Spawn", "Messages/Administration/Spawn.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/Administration/Spawn.yml", "MA-Spawn");
		AdminPanelConfig.loadConfig((Plugin) this);
		configfile.put("MA-AdminPanel", "Messages/Administration/AdminPanel.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/Administration/AdminPanel.yml", "MA-AdminPanel");
		
		instance = this;

		GetSetWorld();

		CheckConfig.Check();

		HawnCommand.configlist();

		TXTmsg.onCreateInfoMsgAdmin();
		TXTmsg.onWrite();

		CheckConfig.ConvertOldDataFromNew();
		
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Configurations files loaded");
		gcs(ChatColor.BLUE+"| ");
		
		// Commands
		getCommand("hawn").setExecutor(new HawnCommand());

		Field bukkitCommandMap;

		try {
			bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

			bukkitCommandMap.setAccessible(true);
			CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

			/*
			 * 
			 * ADMINISTRATION
			 * 
			 */
			commandMap.register("paneladmin", new PanelAdminCommand("paneladmin"));
			commandMap.register("adminpanel", new PanelAdminCommand("adminpanel"));
			commandMap.register("pa", new PanelAdminCommand("pa"));
			commandMap.register("ap", new PanelAdminCommand("ap"));
			
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
			// >> Action bar broadcast
			if (!ActionbarAnnouncerConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("actionbarannouncer", new ABAnnouncerCommand("actionbarannouncer"));
				if (CommandAliasesConfig.getConfig().getBoolean("ActionBarAnnouncer.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("ActionBarAnnouncer.Aliases")) {
						commandMap.register(s, new ABAnnouncerCommand(s));
					}
				}
			}
			// Warning
			if (!WarningCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("warning", new WarningCommand("warning"));
				if (CommandAliasesConfig.getConfig().getBoolean("Warning.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("Warning.Aliases")) {
						commandMap.register(s, new WarningCommand(s));
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
			// >> SetSpawn
			if (!SpawnCommandConfig.getConfig().getBoolean("SetSpawn.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("setspawn", new SetSpawnCommand("setspawn"));
				if (CommandAliasesConfig.getConfig().getBoolean("SetSpawn.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("SetSpawn.Aliases")) {
						commandMap.register(s, new SetSpawnCommand(s));
					}
				}
			}
			// >> DelSpawn
			if (!SpawnCommandConfig.getConfig().getBoolean("DelSpawn.DISABLE_THE_COMMAND_COMPLETELY")) {
				commandMap.register("delspawn", new DelSpawnCommand("delspawn"));
				if (CommandAliasesConfig.getConfig().getBoolean("DelSpawn.Enable")) {
					for (String s : CommandAliasesConfig.getConfig().getStringList("DelSpawn.Aliases")) {
						commandMap.register(s, new DelSpawnCommand(s));
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
	                    gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information (ClassNotFoundException)");
	        			gcs(ChatColor.BLUE+"| ");
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                    useyamllistplayer = true;
	                    gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information (SQLException)");
	        			gcs(ChatColor.BLUE+"| ");
	                }
	            }
	        };

	        r.runTaskAsynchronously(this);
		} else {
			useyamllistplayer = true;
			gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use YAML as method for information (MySQL not enabled)");
			gcs(ChatColor.BLUE+"| ");
		}

		// Keep option placeholderAPI

		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Keep-The-Option")) {
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"PlaceHolderAPI detected");
					Bukkit.getPluginManager().registerEvents(this, this);
				}
			} else {
				gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"PlaceHolderAPI detected");
				ConfigGeneral.getConfig().set("Plugin.Use.PlaceholderAPI", true);
				ConfigGeneral.saveConfigFile();
				Bukkit.getPluginManager().registerEvents(this, this);
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.PlaceholderAPI", false);
				ConfigGeneral.saveConfigFile();
			}
		}
		
		// Keep option MVdWPlaceholderAPI

				if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
					if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Keep-The-Option")) {
						if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
							gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"MVdWPlaceholderAPI detected");
							gcs("| "+ChatColor.YELLOW+"MAKE SURE you have at LEAST one of the Maximvdw's up-to-date and purchased premium placeholder plugins in the server such as FeatherBoard, AnimatedNames..");
							gcs("| "+ChatColor.YELLOW+"Otherwise, you will get a good spam in the console");
							Bukkit.getPluginManager().registerEvents(this, this);
						}
					} else {
						gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"MVdWPlaceholderAPI detected");
						gcs("| "+ChatColor.YELLOW+"MAKE SURE you have at LEAST one of the Maximvdw's up-to-date and purchased premium placeholder plugins in the server such as FeatherBoard, AnimatedNames..");
						gcs("| "+ChatColor.YELLOW+"Otherwise, you will get a good spam in the console");
						ConfigGeneral.getConfig().set("Plugin.Use.MVdWPlaceholderAPI.Enable", true);
						ConfigGeneral.saveConfigFile();
						Bukkit.getPluginManager().registerEvents(this, this);
					}
				} else {
					if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Keep-The-Option")) {
						ConfigGeneral.getConfig().set("Plugin.Use.MVdWPlaceholderAPI.Enable", false);
						ConfigGeneral.saveConfigFile();
					}
				}

		// Keep option Worldguard
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Keep-The-Option")) {
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
					setWorldGuard((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard"));
					gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard detected");
					try {
						Class<?> worldGuardClass = Class.forName("com.sk89q.worldguard.WorldGuard");
						Method getInstanceMethod = worldGuardClass.getMethod("getInstance");
						getInstanceMethod.invoke(null);
						gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard 7+ detected");
						worldGuard_recent_version = true;
					} catch (Exception e) {
						gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard <7 detected");
						worldGuard_recent_version = false;
					}
				}
			} else {
				ConfigGeneral.getConfig().set("Plugin.Use.WorldGuard.Enable", true);
				ConfigGeneral.saveConfigFile();
				setWorldGuard((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard"));
				gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard detected");
				try {
					Class<?> worldGuardClass = Class.forName("com.sk89q.worldguard.WorldGuard");
					Method getInstanceMethod = worldGuardClass.getMethod("getInstance");
					getInstanceMethod.invoke(null);
					gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard 7+ detected");
					worldGuard_recent_version = true;
				} catch (Exception e) {
					gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard <7 detected");
					worldGuard_recent_version = false;
				}
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.WorldGuard.Enable", false);
				ConfigGeneral.saveConfigFile();
			}
		}
		
		// BattleLevel
		if (Bukkit.getPluginManager().isPluginEnabled("BattleLevels")) {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Keep-The-Option")) {
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
					gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"BattleLevels detected");
					Bukkit.getPluginManager().registerEvents(this, this);
				}
			} else {
				gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"BattleLevels detected");
				ConfigGeneral.getConfig().set("Plugin.Use.BattleLevels.Enable", true);
				ConfigGeneral.saveConfigFile();
				Bukkit.getPluginManager().registerEvents(this, this);
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.BattleLevels.Enable", false);
				ConfigGeneral.saveConfigFile();
			}
		}
		
		if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI") || ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable") ||
				ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
			gcs(ChatColor.BLUE+"| ");
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
			if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable") && FlyCommand.player_list_flyc.contains(p)) {
				FlyCommand.player_list_flyc.remove(p);
				p.setAllowFlight(false);
				p.setFlying(false);
			}
		}

		EmojiesUtility.setaliaseslist();

		nmsver = Bukkit.getServer().getClass().getPackage().getName();
	    nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);

	    if (nmsver.equalsIgnoreCase("v1_8_R1")) {
	      useOldMethods = true;
	    }

	    player_spawnwarpdelay.clear();

	    /*
	     * Protection interactable
	     */
	    
	    if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Enable")) {
	    	
	    	interactables.clear();
	    	
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_DOOR")) {
	    		interactables.add(XMaterial.ACACIA_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_FENCE_GATE")) {
	    		interactables.add(XMaterial.ACACIA_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ANVIL")) {
	    		interactables.add(XMaterial.ANVIL.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BEACON")) {
	    		interactables.add(XMaterial.BEACON.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.RED_BED")) {
	    		interactables.add(XMaterial.RED_BED.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_DOOR")) {
	    		interactables.add(XMaterial.BIRCH_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_FENCE_GATE")) {
	    		interactables.add(XMaterial.BIRCH_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BOAT")) {
	    		interactables.add(XMaterial.OAK_BOAT.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BREWING_STAND")) {
	    		interactables.add(XMaterial.BREWING_STAND.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMMAND_BLOCK")) {
	    		interactables.add(XMaterial.COMMAND_BLOCK.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST")) {
	    		interactables.add(XMaterial.CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_DOOR")) {
	    		interactables.add(XMaterial.DARK_OAK_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_FENCE_GATE")) {
	    		interactables.add(XMaterial.DARK_OAK_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DAYLIGHT_DETECTOR")) {
	    		interactables.add(XMaterial.DAYLIGHT_DETECTOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DISPENSER")) {
	    		interactables.add(XMaterial.DISPENSER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DROPPER")) {
	    		interactables.add(XMaterial.DROPPER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENCHANTING_TABLE")) {
	    		interactables.add(XMaterial.ENCHANTING_TABLE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ENDER_CHEST")) {
	    		interactables.add(XMaterial.ENDER_CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_FENCE_GATE")) {
	    		interactables.add(XMaterial.OAK_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.FURNACE")) {
	    		interactables.add(XMaterial.FURNACE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER")) {
	    		interactables.add(XMaterial.HOPPER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.HOPPER_MINECART")) {
	    		interactables.add(XMaterial.HOPPER_MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_DOOR")) {
	    		interactables.add(XMaterial.JUNGLE_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_FENCE_GATE")) {
	    		interactables.add(XMaterial.JUNGLE_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.LEVER")) {
	    		interactables.add(XMaterial.LEVER.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
	    		interactables.add(XMaterial.MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.NOTE_BLOCK")) {
	    		interactables.add(XMaterial.NOTE_BLOCK.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.MINECART")) {
	    		interactables.add(XMaterial.MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.COMPARATOR")) {
	    		interactables.add(XMaterial.COMPARATOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_SIGN")) {
	    		interactables.add(XMaterial.OAK_SIGN.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.CHEST_MINECART")) {
	    		interactables.add(XMaterial.CHEST_MINECART.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
	    		interactables.add(XMaterial.OAK_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_TRAPDOOR")) {
	    		interactables.add(XMaterial.OAK_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.TRAPPED_CHEST")) {
	    		interactables.add(XMaterial.TRAPPED_CHEST.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_BUTTON")) {
	    		interactables.add(XMaterial.OAK_BUTTON.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.OAK_DOOR")) {
	    		interactables.add(XMaterial.OAK_DOOR.parseMaterial());
	    	}
	    }
	    
	    /*
	     * -------------------
	     *   AUTO BROADCAST
	     * -------------------
	     */
	    // >> Messages
	    if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Enable")) {
	    	
	    	interval = AutoBroadcastConfig.getConfig().getInt("Config.Messages.Interval");

		    Iterator<?> iterator2 = AutoBroadcastConfig.getConfig().getConfigurationSection("Config.Messages.messages").getKeys(false).iterator();

		    Integer abnumberput = 0;

		    while (iterator2.hasNext()) {
				String string = (String) iterator2.next();
				autobroadcast.put(abnumberput, string);
				abnumberput++;
				autobroadcast_total++;
		    }

		    autobroadcast_total--;

		    @SuppressWarnings("unused")
			BukkitTask TaskName = (new AutoBroadcast(this)).runTaskTimer(this, 20L, AutoBroadcastConfig.getConfig().getInt("Config.Messages.Interval") * 20);
	    }

	    // >> Titles
	    if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Enable")) {

	    	interval_titles = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Interval");

		    Iterator<?> iterator3 = AutoBroadcastConfig.getConfig().getConfigurationSection("Config.Titles.messages").getKeys(false).iterator();

		    Integer abnumberput = 0;

		    while (iterator3.hasNext()) {
				String string = (String) iterator3.next();
				autobroadcast_titles.put(abnumberput, string);
				abnumberput++;
				autobroadcast_total_titles++;
		    }

		    autobroadcast_total_titles--;
		    
		    @SuppressWarnings("unused")
			BukkitTask TaskName = (new AutoBroadcast_Title(this)).runTaskTimer(this, 20L, AutoBroadcastConfig.getConfig().getInt("Config.Titles.Interval") * 20);
	    }
	    
	    // >> Action-Bar
	    if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Enable")) {

	    	interval_ab = AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Interval");

		    Iterator<?> iterator4 = AutoBroadcastConfig.getConfig().getConfigurationSection("Config.Action-Bar.messages").getKeys(false).iterator();

		    Integer abnumberput = 0;

		    while (iterator4.hasNext()) {
				String string = (String) iterator4.next();
				autobroadcast_ab.put(abnumberput, string);
				abnumberput++;
				autobroadcast_total_ab++;
		    }

		    autobroadcast_total_ab--;
		    
		    @SuppressWarnings("unused")
			BukkitTask TaskName = (new AutoBroadcast_AB(this)).runTaskTimer(this, 20L, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Interval") * 20);
	    }
	    
	    /*
	     * Voidtp per world
	     */
	    if (VoidTPConfig.getConfig().getBoolean("VoidTP.Enable") && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.Enable")) {
	    	
	    	BasicFeatures.world_voidtp.clear();
	    	
	    	Iterator<?> iterator5 = VoidTPConfig.getConfig().getConfigurationSection("VoidTP.Options.VoidTP-Per-World.World-List").getKeys(false).iterator();
	    	
	    	while (iterator5.hasNext()) {
	    		String string = (String) iterator5.next();
	    		
	    		BasicFeatures.world_voidtp.add(string);
	    	}
	    }
	    
	    /*
	     * --------------
	     *   Scoreboard
	     * --------------
	     */
	    if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
	    	
	    	if (Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.15")) {
	        	newmethodver = true;
	        }
	    	
	    	
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
							 
							hea2 = MessageUtils.ReplaceMainplaceholderP(hea, p);
							foo2 = MessageUtils.ReplaceMainplaceholderP(foo, p);
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								foo2 = PlaceholderAPI.setPlaceholders(p, foo2);
								hea2 = PlaceholderAPI.setPlaceholders(p, hea2);
							}
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								foo2 = MessageUtils.BattleLevelPO(foo2, p);
								hea2 = MessageUtils.BattleLevelPO(hea2, p);
							}
							
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

	    configfileinuse.clear();
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

	    injumpwithjumppad.clear();
	    
	    if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Day.Enable")) {
	    	new AlwaysDayTask().runTaskTimer(this, 20, 10000L);
	    }
	    
	    if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Night.Enable")) {
	    	new AlwaysNightTask().runTaskTimer(this, 20, 7000L);
	    }
	    
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
		
		if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Enable") && WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.Disable")) {
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"You enabled the lightning strike on join, but, the anti lightning strike is enabled too");
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"Lightning strikes on join will not work");
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
		if (devbuild) {
			gcs(ChatColor.BLUE+"| "+ChatColor.GOLD+"You are in a development build");
			gcs(ChatColor.BLUE+"| ");
			UpToDate = "§eDevelopment build";
			return;
		}
		
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
		if (devbuild) {
			UpToDate = "§eDevelopment build";
			return;
		}
		
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
				ScoreboardInfo info = new ScoreboardInfo(cfg, perm);
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
		String bool = PlayerOptionSQLClass.getYmlaMysqlsb(player, "keepsb");

		if (bool.equalsIgnoreCase("TRUE") && ScoreboardCommandConfig.getConfig().getBoolean("Scoreboard.Option.Keep-Scoreboard-Change")) {
			String sb = PlayerOptionSQLClass.getYmlaMysqlsb(player, "scoreboard");
			if (boards.containsKey(player)) {
				ScoreboardInfo in = (ScoreboardInfo)this.info.get("hawn.scoreboard."+sb);
				((PlayerBoard)boards.get(player)).createNew(in);
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
	                            PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                            return;
	                        }
	                        boards.get(player).createNew(in);
	                        PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    } else {
	                        new PlayerBoard(this, player, info.get(s));
	                        PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    }
	                    PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    return;
	                }
	            }
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
		CommandsPW.setWGetWorldJoinCommandNew();
		CommandsPW.setWGetWorldJoinCommandNoNew();
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
		PlayerEventsPW.setPlayerOptionJoin();
		BasicEventsPW.setWGetWorldautobroadcast_ab();
		BasicEventsPW.setWGetWorldautobroadcast_title();
		ChangeWorldPW.setWPO();
		CosmeticsPW.setWGetWorldls();
		PlayerEventsPW.setWGetWorldRCJI();
		ProtectionPW.setWGetWorldProtectionPlayerInteractItemsBlocks();
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
	        	gcs(ChatColor.BLUE+"| ------------------------------------");
	        	gcs(ChatColor.BLUE+"| ");
	        	gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The plugin will now use MySQL as method for information");
    			gcs(ChatColor.BLUE+"| ");
    			gcs(ChatColor.BLUE+"| ------------------------------------");
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

	public WorldGuardPlugin getWorldGuard() {
		return worldGuard;
	}

	public void setWorldGuard(WorldGuardPlugin worldGuard) {
		this.worldGuard = worldGuard;
	}

}
