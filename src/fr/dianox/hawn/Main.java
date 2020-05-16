package fr.dianox.hawn;

import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import fr.dianox.hawn.command.CommandManager;
import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.command.commands.HawnCommand;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.utility.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;

import fr.dianox.hawn.event.BasicFeatures;
import fr.dianox.hawn.event.FunFeatures;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.event.OnJoin;
import fr.dianox.hawn.event.world.AlwaysDayTask;
import fr.dianox.hawn.event.world.AlwaysNightTask;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.modules.worldsystem.GuiSystem;
import fr.dianox.hawn.utility.config.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.CommandAliasesConfig;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.ConfigSpawn;
import fr.dianox.hawn.utility.config.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.CustomCommandConfig;
import fr.dianox.hawn.utility.config.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.ScoreboardMainConfig;
import fr.dianox.hawn.utility.config.ServerListConfig;
import fr.dianox.hawn.utility.config.WarpListConfig;
import fr.dianox.hawn.utility.config.commands.ActionbarAnnouncerConfig;
import fr.dianox.hawn.utility.config.commands.AdminPanelCommandConfig;
import fr.dianox.hawn.utility.config.commands.BroadCastCommandConfig;
import fr.dianox.hawn.utility.config.commands.BurnCommandConfig;
import fr.dianox.hawn.utility.config.commands.CheckAccountCommandConfig;
import fr.dianox.hawn.utility.config.commands.ClearChatCommandConfig;
import fr.dianox.hawn.utility.config.commands.ClearGroundItemsCommandConfig;
import fr.dianox.hawn.utility.config.commands.ClearInvCommandConfig;
import fr.dianox.hawn.utility.config.commands.ClearMobsCommandConfig;
import fr.dianox.hawn.utility.config.commands.CopyCommandConfig;
import fr.dianox.hawn.utility.config.commands.DelayChatCommandConfig;
import fr.dianox.hawn.utility.config.commands.EmojiCommandConfig;
import fr.dianox.hawn.utility.config.commands.EnderChestCommandConfig;
import fr.dianox.hawn.utility.config.commands.ExpCommandConfig;
import fr.dianox.hawn.utility.config.commands.FeedCommandConfig;
import fr.dianox.hawn.utility.config.commands.FlyCommandConfig;
import fr.dianox.hawn.utility.config.commands.FlySpeedCommandConfig;
import fr.dianox.hawn.utility.config.commands.GamemodeCommandConfig;
import fr.dianox.hawn.utility.config.commands.GetPosCommandConfig;
import fr.dianox.hawn.utility.config.commands.GoTopCommandConfig;
import fr.dianox.hawn.utility.config.commands.HatCommandConfig;
import fr.dianox.hawn.utility.config.commands.HawnCommandConfig;
import fr.dianox.hawn.utility.config.commands.HealCommandConfig;
import fr.dianox.hawn.utility.config.commands.HelpCommandConfig;
import fr.dianox.hawn.utility.config.commands.InvSeeCommandConfig;
import fr.dianox.hawn.utility.config.commands.IpCommandConfig;
import fr.dianox.hawn.utility.config.commands.KickAllCommandConfig;
import fr.dianox.hawn.utility.config.commands.ListCommandConfig;
import fr.dianox.hawn.utility.config.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.commands.OneCommandConfig;
import fr.dianox.hawn.utility.config.commands.OptionPlayerConfigCommand;
import fr.dianox.hawn.utility.config.commands.PasteCommandConfig;
import fr.dianox.hawn.utility.config.commands.PingCommandConfig;
import fr.dianox.hawn.utility.config.commands.RepairCommandConfig;
import fr.dianox.hawn.utility.config.commands.ScoreboardCommandConfig;
import fr.dianox.hawn.utility.config.commands.SkullCommandConfig;
import fr.dianox.hawn.utility.config.commands.SpawnCommandConfig;
import fr.dianox.hawn.utility.config.commands.SpeedCommandConfig;
import fr.dianox.hawn.utility.config.commands.SuicideCommandConfig;
import fr.dianox.hawn.utility.config.commands.TitleAnnouncerConfig;
import fr.dianox.hawn.utility.config.commands.TwoCommandConfig;
import fr.dianox.hawn.utility.config.commands.VanishCommandConfig;
import fr.dianox.hawn.utility.config.commands.WarningCommandConfig;
import fr.dianox.hawn.utility.config.commands.WarpSetWarpCommandConfig;
import fr.dianox.hawn.utility.config.commands.WeatherTimeCommandConfig;
import fr.dianox.hawn.utility.config.commands.WorkBenchCommandConfig;
import fr.dianox.hawn.utility.config.commands.WorldCommandConfig;
import fr.dianox.hawn.utility.config.cosmeticsfun.BookListConfiguration;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGLP;
import fr.dianox.hawn.utility.config.cosmeticsfun.EmojisListCUtility;
import fr.dianox.hawn.utility.config.cosmeticsfun.FireworkListCUtility;
import fr.dianox.hawn.utility.config.cosmeticsfun.SignListCUtility;
import fr.dianox.hawn.utility.config.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiLobbyBow;
import fr.dianox.hawn.utility.config.events.CommandEventConfig;
import fr.dianox.hawn.utility.config.events.ConfigGJoinQuitCommand;
import fr.dianox.hawn.utility.config.events.ConfigGProtection;
import fr.dianox.hawn.utility.config.events.OnChatConfig;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.events.OtherFeaturesConfig;
import fr.dianox.hawn.utility.config.events.PlayerEventsConfig;
import fr.dianox.hawn.utility.config.events.PlayerWorldChangeConfigE;
import fr.dianox.hawn.utility.config.events.ProtectionPlayerConfig;
import fr.dianox.hawn.utility.config.events.VoidTPConfig;
import fr.dianox.hawn.utility.config.events.WorldEventConfig;
import fr.dianox.hawn.utility.config.messages.AdminPanelConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.config.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.messages.TXTmsg;
import fr.dianox.hawn.utility.config.messages.WorldManagerPanelConfig;
import fr.dianox.hawn.utility.config.messages.fr_fr.FRAdminAMConfig;
import fr.dianox.hawn.utility.config.messages.fr_fr.FRAdminPanelConfig;
import fr.dianox.hawn.utility.config.messages.fr_fr.FRConfigMGeneral;
import fr.dianox.hawn.utility.config.messages.fr_fr.FRConfigMMsg;
import fr.dianox.hawn.utility.config.messages.fr_fr.FRWorldManagerPanelConfig;
import fr.dianox.hawn.utility.config.scoreboard.defaultscoreboardconfig;
import fr.dianox.hawn.utility.config.scoreboard.worldnetherdsc;
import fr.dianox.hawn.utility.config.tab.TablistConfig;
import fr.dianox.hawn.utility.load.Reload;
import fr.dianox.hawn.utility.load.WorldList;
import fr.dianox.hawn.utility.scoreboard.PlayerBoard;
import fr.dianox.hawn.utility.scoreboard.ScoreboardInfo;
import fr.dianox.hawn.utility.server.Tps;
import fr.dianox.hawn.utility.server.WarnTPS;
import fr.dianox.hawn.utility.tab.AnimationTabTask;
import fr.dianox.hawn.utility.tab.MainTablist;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;
	VersionUtils versionUtils = new VersionUtils();
	AutoBroadcastManager auto;
	BungeeApi bungApi = new BungeeApi(Main.getInstance());
	HooksManager hooksManager;
	CommandManager commandManager;

	private static String versions = "1.0.4-Beta";
	public static Integer Spigot_Version = 0;
	public static Boolean devbuild = false;
	public static Integer devbuild_number = 0;
	public static String date = "";
	
	public static String LanguageType = "en_US";
	
	public static String UpToDate, nmsver;
	public static boolean useOldMethods = false;
	public static List<String> fileconfiglist = new ArrayList<>();
	
	public static Connection connection;
	private String host,
					database,
					username, 
					password;
	private int port;
	private static Statement statement;
	public static boolean useyamllistplayer = false;
	
	public static HashMap<Integer, String> motd_sl = new HashMap<>();
	public static Integer motd_total_sl = 0;
	
    public static HashMap<Integer, String> autobroadcast = new HashMap<>();
    public static HashMap<Integer, String> autobroadcast_titles = new HashMap<>();
    public static HashMap<Integer, String> autobroadcast_ab = new HashMap<>();
    public static HashMap<Integer, String> autobroadcast_bb = new HashMap<>();
    public static Integer autobroadcast_total= 0;
    public static Integer autobroadcast_total_titles = 0;
    public static Integer autobroadcast_total_ab = 0;
    public static Integer autobroadcast_total_bb = 0;
    public static int curMsg = 0;
    public static int curMsg_ab = 0;
    public static int curMsg_bb = 0;
    public static int curMsg_titles = 0;
	public Scoreboard board;

	public static HashMap<UUID, PlayerBoard> boards = new HashMap<>();
	public static List<PlayerBoard> allboards = new ArrayList<>();
	public HashMap<String, ScoreboardInfo> info = new HashMap<>();
	public HashMap<String, String> infoname = new HashMap<>();
	public HashMap<String, String> infoname2 = new HashMap<>();
	public static HashMap<Player, Long> playerWorldTimer = new HashMap<>();
	public static List<Player> nosb = new ArrayList<>();
	public static List<Player> injumpwithjumppad = new ArrayList<>();
	
	public static HashMap<UUID, Integer> player_spawnwarpdelay = new HashMap<>();
	public static List<Player> inwarpd = new ArrayList<>();
	public static List<Player> inspawnd = new ArrayList<>();
	
	public String hea = "";
	public String foo = "";

    private static Class<?> PacketPlayOutPlayerListHeaderFooter;
    private static Class<?> ChatComponentText;
    private static Constructor<?> newPacketPlayOutPlayerListHeaderFooter;

    public static List<Player> buildbypasscommand = new ArrayList<>();
    public static List<Player> avoidtitles = new ArrayList<>();

    public static HashMap<Player, Long> hiderCooldowns = new HashMap<>();
    public static HashMap<Player, Long> fungunCooldowns = new HashMap<>();
    
    public static HashMap<Player, Integer> TaskVanishAB = new HashMap<>();
    
    public static HashMap<String, String> configfile = new HashMap<>();
    public static HashMap<String, String> configfilereverse = new HashMap<>();
    public static HashMap<Player, String> configfileinuse = new HashMap<>();
    
    public static List<Material> block_exception_place = new ArrayList<>();
    public static List<Material> block_exception_break = new ArrayList<>();
    public static List<Material> interactables = new ArrayList<>();

    public static List<Player> indj = new ArrayList<>();
    
    public static HashMap<String, Integer> animationtab = new HashMap<>();
    public static HashMap<String, Integer> animationtabtask = new HashMap<>();
    public static Integer tablistnumber = 0;
    
    public static PluginChannelListener pcl;
    
    public static HashMap<String, Integer> tasklist = new HashMap<>();

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
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"When the dawn was visible, a new plugin was born");
		gcs(ChatColor.BLUE+"| ");

		try {
			@SuppressWarnings("unused")
			Metrics metrics = new Metrics(this);
		} catch (Exception e) {
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"An error made it impossible for the metrics to be activated");
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"You can restart your server if you want, but it's optional, metrics are just stats");
			gcs(ChatColor.YELLOW+"| ");
		}
		
		configfile.clear();
		 configfilereverse.clear();
		
			// Load config
			ConfigSpawn.loadConfig(this);
			configfile.put("G-spawn", "spawn.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "spawn.yml", "G-spawn");
			ConfigGeneral.loadConfig(this);
			configfile.put("G-general", "general.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "general.yml", "G-general");
			ConfigWorldGeneral.loadConfig(this);
			configfile.put("G-World-List", "World-List.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "World-List.yml", "G-World-List");
			ServerListConfig.loadConfig(this);
			configfile.put("G-ServerList", "ServerList.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "ServerList.yml", "G-ServerList");
			AutoBroadcastConfig.loadConfig(this);
			configfile.put("G-AutoBroadcast", "AutoBroadcast.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "AutoBroadcast.yml", "G-AutoBroadcast");
			PlayerOptionMainConfig.loadConfig(this);
			configfile.put("G-Player-Option-General", "Player-Option-General.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Player-Option-General.yml", "G-Player-Option-General");
			CommandAliasesConfig.loadConfig(this);
			configfile.put("G-command-aliases", "command-aliases.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "command-aliases.yml", "G-command-aliases");
			WarpListConfig.loadConfig(this);
			configfile.put("G-warplist", "warplist.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "warplist.yml", "G-warplist");
			ScoreboardMainConfig.loadConfig(this);
			configfile.put("G-Scoreboard-General", "Scoreboard-General.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Scoreboard-General.yml", "G-Scoreboard-General");
			
			OtherFeaturesConfig.loadConfig(this);
			configfile.put("E-OtherFeatures", "Events/OtherFeatures.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/OtherFeatures.yml", "E-OtherFeatures");
			WorldEventConfig.loadConfig(this);
			configfile.put("E-WorldEvent", "Events/WorldEvent.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/WorldEvent.yml", "E-WorldEvent");
			
			ConfigGProtection.loadConfig(this);
			configfile.put("E-ProtectionWorld", "Events/ProtectionWorld.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/ProtectionWorld.yml", "E-ProtectionWorld");
			
			VoidTPConfig.loadConfig(this);
			configfile.put("E-VoidTP", "Events/VoidTP.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/VoidTP.yml", "E-VoidTP");
			ProtectionPlayerConfig.loadConfig(this);
			configfile.put("E-ProtectionPlayer", "Events/ProtectionPlayer.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/ProtectionPlayer.yml", "E-ProtectionPlayer");
			PlayerEventsConfig.loadConfig(this);
			configfile.put("E-PlayerEvents", "Events/PlayerEvents.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/PlayerEvents.yml", "E-PlayerEvents");
			OnJoinConfig.loadConfig(this);
			configfile.put("E-OnJoin", "Events/OnJoin.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/OnJoin.yml", "E-OnJoin");
			CommandEventConfig.loadConfig(this);
			configfile.put("E-OnCommands", "Events/OnCommands.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/OnCommands.yml", "E-OnCommands");
			ConfigGJoinQuitCommand.loadConfig(this);
			configfile.put("E-JoinQuitCommand", "Events/JoinQuitCommand.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/JoinQuitCommand.yml", "E-JoinQuitCommand");
			OnChatConfig.loadConfig(this);
			configfile.put("E-Chat", "Events/Chat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/Chat.yml", "E-Chat");
			PlayerWorldChangeConfigE.loadConfig(this);
			configfile.put("E-PlayerWorldChange", "Events/PlayerWorldChange.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Events/PlayerWorldChange.yml", "E-PlayerWorldChange");

			ActionbarAnnouncerConfig.loadConfig(this);
			configfile.put("C-ActionBarAnnouncer", "Commands/ActionBarAnnouncer.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ActionBarAnnouncer.yml", "C-ActionBarAnnouncer");
			FlyCommandConfig.loadConfig(this);
			configfile.put("C-Fly", "Commands/Fly.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Fly.yml", "C-Fly");
			WeatherTimeCommandConfig.loadConfig(this);
			configfile.put("C-Weather-Time", "Commands/Weather-Time.yml");
			configfilereverse.put(this.getDataFolder() + "/" +  "Commands/Weather-Time.yml", "C-Weather-Time");
			HelpCommandConfig.loadConfig(this);
			configfile.put("C-Help", "Commands/Help.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Help.yml", "C-Help");
			ClearChatCommandConfig.loadConfig(this);
			configfile.put("C-ClearChat", "Commands/ClearChat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ClearChat.yml", "C-ClearChat");
			SpawnCommandConfig.loadConfig(this);
			configfile.put("C-Spawn", "Commands/Spawn.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Spawn.yml", "C-Spawn");
			MuteChatCommandConfig.loadConfig(this);
			configfile.put("C-MuteChat", "Commands/MuteChat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/MuteChat.yml", "C-MuteChat");
			PingCommandConfig.loadConfig(this);
			configfile.put("C-Ping", "Commands/Ping.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Ping.yml", "C-Ping");
			DelayChatCommandConfig.loadConfig(this);
			configfile.put("C-DelayChat", "Commands/DelayChat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/DelayChat.yml", "C-DelayChat");
			BroadCastCommandConfig.loadConfig(this);
			configfile.put("C-Broadcast", "Commands/Broadcast.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Broadcast.yml", "C-Broadcast");
			HealCommandConfig.loadConfig(this);
			configfile.put("C-Heal", "Commands/Heal.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Heal.yml", "C-Heal");
			WarpSetWarpCommandConfig.loadConfig(this);
			configfile.put("C-Warp-SetWarp", "Commands/Warp-SetWarp.yml");
			configfilereverse.put(this.getDataFolder() + "/" +  "Commands/Warp-SetWarp.yml", "C-Warp-SetWarp");
			VanishCommandConfig.loadConfig(this);
			configfile.put("C-Vanish", "Commands/Vanish.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Vanish.yml", "C-Vanish");
			TitleAnnouncerConfig.loadConfig(this);
			configfile.put("C-TitleAnnouncer", "Commands/TitleAnnouncer.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/TitleAnnouncer.yml", "C-TitleAnnouncer");
			ClearInvCommandConfig.loadConfig(this);
			configfile.put("C-ClearInv", "Commands/ClearInv.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ClearInv.yml", "C-ClearInv");
			EmojiCommandConfig.loadConfig(this);
			configfile.put("C-Emoji", "Commands/Emoji.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Emoji.yml", "C-Emoji");
			ScoreboardCommandConfig.loadConfig(this);
			configfile.put("C-Scoreboard", "Commands/Scoreboard.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Scoreboard.yml", "C-Scoreboard");
			GamemodeCommandConfig.loadConfig(this);
			configfile.put("C-Gamemode", "Commands/Gamemode.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Gamemode.yml", "C-Gamemode");
			OptionPlayerConfigCommand.loadConfig(this);
			configfile.put("C-PlayerOption", "Commands/PlayerOption.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/PlayerOption.yml", "C-PlayerOption");
			WarningCommandConfig.loadConfig(this);
			configfile.put("C-Warning", "Commands/Warning.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Warning.yml", "C-Warning");
			FeedCommandConfig.loadConfig(this);
			configfile.put("C-Feed", "Commands/Feed.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Feed.yml", "C-Feed");
			GoTopCommandConfig.loadConfig(this);
			configfile.put("C-Gotop", "Commands/Gotop.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Gotop.yml", "C-Gotop");
			AdminPanelCommandConfig.loadConfig(this);
			configfile.put("C-AdminPanel", "Commands/AdminPanel.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/AdminPanel.yml", "C-AdminPanel");
			SuicideCommandConfig.loadConfig(this);
			configfile.put("C-Suicide", "Commands/Suicide.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Suicide.yml", "C-Suicide");
			EnderChestCommandConfig.loadConfig(this);
			configfile.put("C-EnderChest", "Commands/EnderChest.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/EnderChest.yml", "C-EnderChest");
			InvSeeCommandConfig.loadConfig(this);
			configfile.put("C-InvSee", "Commands/InvSee.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/InvSee.yml", "C-InvSee");
			RepairCommandConfig.loadConfig(this);
			configfile.put("C-Repair", "Commands/Repair.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Repair.yml", "C-Repair");
			HatCommandConfig.loadConfig(this);
			configfile.put("C-Hat", "Commands/Hat.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Hat.yml", "C-Hat");
			KickAllCommandConfig.loadConfig(this);
			configfile.put("C-KickAll", "Commands/KickAll.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/KickAll.yml", "C-KickAll");
			IpCommandConfig.loadConfig(this);
			configfile.put("C-Ip", "Commands/Ip.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Ip.yml", "C-Ip");
			GetPosCommandConfig.loadConfig(this);
			configfile.put("C-GetPos", "Commands/GetPos.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/GetPos.yml", "C-GetPos");
			ClearGroundItemsCommandConfig.loadConfig(this);
			configfile.put("C-ClearGroundItems", "Commands/ClearGroundItems.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ClearGroundItems.yml", "C-ClearGroundItems");
			ClearMobsCommandConfig.loadConfig(this);
			configfile.put("C-ClearMobs", "Commands/ClearMobs.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/ClearMobs.yml", "C-ClearMobs");
			CheckAccountCommandConfig.loadConfig(this);
			configfile.put("C-CheckAccount", "Commands/CheckAccount.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/CheckAccount.yml", "C-CheckAccount");
			ExpCommandConfig.loadConfig(this);
			configfile.put("C-Exp", "Commands/Exp.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Exp.yml", "C-Exp");
			ListCommandConfig.loadConfig(this);
			configfile.put("C-List", "Commands/List.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/List.yml", "C-List");
			OneCommandConfig.loadConfig(this);
			configfile.put("C-1-WE", "Commands/1-WE.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/1-WE.yml", "C-1-WE");
			TwoCommandConfig.loadConfig(this);
			configfile.put("C-2-WE", "Commands/2-WE.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/2-WE.yml", "C-2-WE");
			CopyCommandConfig.loadConfig(this);
			configfile.put("C-C-WE", "Commands/C-WE.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/C-WE.yml", "C-C-WE");
			PasteCommandConfig.loadConfig(this);
			configfile.put("C-P-WE", "Commands/P-WE.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/P-WE.yml", "C-P-WE");
			HawnCommandConfig.loadConfig(this);
			configfile.put("C-Hawn", "Commands/Hawn.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Hawn.yml", "C-Hawn");
			WorkBenchCommandConfig.loadConfig(this);
			configfile.put("C-WorkBench", "Commands/WorkBench.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/WorkBench.yml", "C-WorkBench");
			BurnCommandConfig.loadConfig(this);
			configfile.put("C-Burn", "Commands/Burn.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Burn.yml", "C-Burn");
			SkullCommandConfig.loadConfig(this);
			configfile.put("C-Skull", "Commands/Skull.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Skull.yml", "C-Skull");
			FlySpeedCommandConfig.loadConfig(this);
			configfile.put("C-FlySpeed", "Commands/FlySpeed.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/FlySpeed.yml", "C-FlySpeed");
			SpeedCommandConfig.loadConfig(this);
			configfile.put("C-Speed", "Commands/Speed.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/Speed.yml", "C-Speed");
			WorldCommandConfig.loadConfig(this);
			configfile.put("C-World", "Commands/World.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Commands/World.yml", "C-World");
			
			ConfigGCos.loadConfig(this);
			configfile.put("CF-OnJoin", "Cosmetics-Fun/OnJoin.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/OnJoin.yml", "CF-OnJoin");
			ConfigGLP.loadConfig(this);
			configfile.put("CF-JumpPads", "Cosmetics-Fun/JumpPads.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/JumpPads.yml", "CF-JumpPads");
			ConfigFDoubleJump.loadConfig(this);
			configfile.put("CF-DoubleJump", "Cosmetics-Fun/DoubleJump.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/DoubleJump.yml", "CF-DoubleJump");
			FireworkListCUtility.loadConfig(this);
			configfile.put("CFU-Firework-List", "Cosmetics-Fun/Utility/Firework-List.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Firework-List.yml", "CFU-Firework-List");
			BookListConfiguration.loadConfig(this);
			configfile.put("CFU-Book-List", "Cosmetics-Fun/Utility/Book-List.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Book-List.yml", "CFU-Book-List");
			EmojisListCUtility.loadConfig(this);
			configfile.put("CFU-Emojis-List", "Cosmetics-Fun/Utility/Emojis-List.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Emojis-List.yml", "CFU-Emojis-List");
			SignListCUtility.loadConfig(this);
			configfile.put("CFU-Sign-List", "Cosmetics-Fun/Utility/Sign-List.yml");
			configfilereverse.put(this.getDataFolder() + "/" + "Cosmetics-Fun/Utility/Sign-List.yml", "CFU-Sign-List");
			
		//NameTagConfig.loadConfig(this);
		TablistConfig.loadConfig(this);
		configfile.put("G-CustomCommand", "CustomCommand.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "CustomCommand.yml", "G-CustomCommand");
		
		CustomCommandConfig.loadConfig(this);
		configfile.put("T-Tablist", "Tablist/Tablist.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Tablist/Tablist.yml", "T-Tablist");
		
		SpecialCjiHidePlayers.loadConfig(this);
		configfile.put("CJI-Special-HidePlayers", "CustomJoinItem/Special-HidePlayers.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "CustomJoinItem/Special-HidePlayers.yml", "CJI-Special-HidePlayers");
		SpecialCjiLobbyBow.loadConfig(this);
		configfile.put("CJI-Special-LobbyBow", "CustomJoinItem/Special-LobbyBow.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "CustomJoinItem/Special-LobbyBow.yml", "CJI-Special-LobbyBow");
		SpecialCjiFunGun.loadConfig(this);
		configfile.put("CJI-Special-FunGun", "CustomJoinItem/Special-FunGun.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "CustomJoinItem/Special-FunGun.yml", "CJI-Special-FunGun");
		ConfigCJIGeneral.loadConfig(this);
		configfile.put("CJI-General", "CustomJoinItem/General.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "CustomJoinItem/General.yml", "CJI-General");
		
		if (!ScoreboardMainConfig.getConfig().isSet("DefaultConfigGenerated")) {
			defaultscoreboardconfig.loadConfig(this);
			worldnetherdsc.loadConfig(this);

			ScoreboardMainConfig.getConfig().set("DefaultConfigGenerated", true);
			ScoreboardMainConfig.saveConfigFile();
		}

		if (ConfigGeneral.getConfig().isSet("Plugin.Language-Type")) {
			try {
				LanguageType = ConfigGeneral.getConfig().getString("Plugin.Language-Type");
			} catch (Exception ignored) {}
		} else {
			ConfigGeneral.getConfig().set("Plugin.Language-Type", "en_US");
			
			ConfigGeneral.saveConfigFile();
		}
		
		// French language
		
		FRAdminPanelConfig.loadConfig(this);
		FRConfigMMsg.loadConfig(this);
		FRConfigMGeneral.loadConfig(this);
		FRWorldManagerPanelConfig.loadConfig(this);
		FRAdminAMConfig.loadConfig(this);
		
		// Normal
		
		ConfigMGeneral.loadConfig(this);
		configfile.put("M-General", "Messages/" + LanguageType + "/General.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/" + LanguageType + "/General.yml", "M-General");
		ConfigMMsg.loadConfig(this);
		configfile.put("M-Messages", "Messages/" + LanguageType + "/Messages.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/" + LanguageType + "/Messages.yml", "M-Messages");
		
		ConfigMAdmin.loadConfig(this);
		configfile.put("M-Admin", "Messages/" + LanguageType + "/Admin.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/" + LanguageType + "/Admin.yml", "M-Admin");
		AdminPanelConfig.loadConfig(this);
		configfile.put("M-AdminPanel", "Messages/" + LanguageType + "/AdminPanel.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/" + LanguageType + "/AdminPanel.yml", "M-AdminPanel");
		WorldManagerPanelConfig.loadConfig(this);
		configfile.put("M-WorldMPC", "Messages/" + LanguageType + "/WorldManager.yml");
		configfilereverse.put(this.getDataFolder() + "/" + "Messages/" + LanguageType + "/WorldManager.yml", "M-WorldMPC");
		
		instance = this;

		WorldList.setworldlist();
	
		Reload.configlist();

		TXTmsg.onCreateInfoMsgAdminMain();
		TXTmsg.onWriteMain();
		
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Configurations files loaded");
		gcs(ChatColor.BLUE+"| ");

	    try {
		    commandManager = new CommandManager(this);
	    } catch (NoSuchFieldException | IllegalAccessException e) {
		    e.printStackTrace();
	    }

	    new Manager(this).registerEvents();
		
		getServer().getMessenger().registerIncomingPluginChannel(this, "wdl:init", pcl = new PluginChannelListener());
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "wdl:control");
	    
	    try {
	    	getServer().getMessenger().registerIncomingPluginChannel(this, "WDL|INIT", pcl = new PluginChannelListener());
	        getServer().getMessenger().registerOutgoingPluginChannel(this, "WDL|CONTROL");
	    } catch (Exception ignored) {}
	    	    
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", bungApi);

	    new BukkitRunnable() {
		    @Override
		    public void run() {
			    try {
					for (String s : bungApi.servers) {
						bungApi.getCount(s);
					}

					bungApi.getCount("ALL");
			    } catch (Exception ignored) {
			    	System.out.println("error");
			    }
		    }
	    }.runTaskTimer(this, 100, 100);

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

		hooksManager = new HooksManager(this);

		// check
		UpdateCheck();

		OnJoin.player_list.clear();
	    OnJoin.player_list.addAll(Bukkit.getServer().getOnlinePlayers());
		FlyCommand.player_list_flyc.clear();
		FunFeatures.player_list_dbenable.clear();

		ChatEmojisLoad.onLoad();
		
		// Versions
	    if (Bukkit.getVersion().contains("1.16")) {
		    Spigot_Version = 116;
	    } else if (Bukkit.getVersion().contains("1.15")) {
			Spigot_Version = 115;
		} else if (Bukkit.getVersion().contains("1.14")) {
			Spigot_Version = 114;
		} else if (Bukkit.getVersion().contains("1.13")) {
			Spigot_Version = 113;
		} else if (Bukkit.getVersion().contains("1.12")) {
			Spigot_Version = 112;
		} else if (Bukkit.getVersion().contains("1.11")) {
			Spigot_Version = 111;
		} else if (Bukkit.getVersion().contains("1.10")) {
			Spigot_Version = 110;
		} else if (Bukkit.getVersion().contains("1.9")) {
			Spigot_Version = 19;
		} else if (Bukkit.getVersion().contains("1.8")) {
			Spigot_Version = 18;
		}

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100L, 1L);

		if (ConfigGeneral.getConfig().getBoolean("Plugin.Tps.Warn-system")) {
			WarnTPS.runWarnSystemTask(this);
		}
		
		HawnCommand.noclip.clear();

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
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_DOOR")) {
	    		interactables.add(XMaterial.SPRUCE_DOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_FENCE_GATE")) {
	    		interactables.add(XMaterial.SPRUCE_FENCE_GATE.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SPRUCE_TRAPDOOR")) {
	    		interactables.add(XMaterial.SPRUCE_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.BIRCH_TRAPDOOR")) {
	    		interactables.add(XMaterial.BIRCH_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.JUNGLE_TRAPDOOR")) {
	    		interactables.add(XMaterial.JUNGLE_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.ACACIA_TRAPDOOR")) {
	    		interactables.add(XMaterial.ACACIA_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.DARK_OAK_TRAPDOOR")) {
	    		interactables.add(XMaterial.DARK_OAK_TRAPDOOR.parseMaterial());
	    	}
	    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Options.SWEET_BERRY_BUSH")) {
	    		interactables.add(XMaterial.SWEET_BERRY_BUSH.parseMaterial());
	    	}
	    }
	    
	    block_exception_break.clear();
	    block_exception_place.clear();
	    
	    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Block-Exception.Enable")) {
	    	for (String str: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Place.Block-Exception.Materials")) {
	    		try {
	    			block_exception_place.add(XMaterial.getMat(str, "Protection.Construct.Anti-Place.Block-Exception.Materials"));
	    		} catch (Exception ignored) {}
	    	}
	    }
	    
	    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Block-Exception.Enable")) {
	    	for (String str: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Break.Block-Exception.Materials")) {
	    		try {
	    			block_exception_break.add(XMaterial.getMat(str, "Protection.Construct.Anti-Break.Block-Exception.Materials"));
	    		} catch (Exception ignored) {}
	    	}
	    }
	    
	    /*
	     * Voidtp per world
	     */
	    if (VoidTPConfig.getConfig().getBoolean("VoidTP.Enable") && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.Enable")) {
	    	
	    	BasicFeatures.world_voidtp.clear();

		    BasicFeatures.world_voidtp.addAll(Objects.requireNonNull(VoidTPConfig.getConfig().getConfigurationSection("VoidTP.Options.VoidTP-Per-World.World-List")).getKeys(false));
	    }
	    
	    /*
	     * MOTD
	     */
	    if (ServerListConfig.getConfig().getBoolean("Motd.Classic.Enable")) {
		    Iterator<?> iterator5 = Objects.requireNonNull(ServerListConfig.getConfig().getConfigurationSection("Motd.Classic.Random-List")).getKeys(false).iterator();

		    Integer bbnumberput = 0;

		    while (iterator5.hasNext()) {
				String string = (String) iterator5.next();
				motd_sl.put(bbnumberput, string);
				bbnumberput++;
				motd_total_sl++;
		    }

		    motd_total_sl--;
	    }
	    
	    // broadcast
	    auto = new AutoBroadcastManager();
	    
	    /*
	     * --------------
	     *   Scoreboard
	     * --------------
	     */
	    if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
	    	
	    	File folder = new File(getDataFolder().getAbsolutePath() + "/Scoreboard/");

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
	    
	    new BukkitRunnable() {
	    	@Override
	    	public void run() {
	    		UpdateCheck();
	    	}
	    }.runTaskTimer(this, 0, 360000);
	    
	    indj.clear();
	    OnCommandEvent.cooldowncommands.clear();
	    
	    // Tablist
	    if (TablistConfig.getConfig().getBoolean("Tablist.enable")) {
		    this.PacketPlayOutPlayerListHeaderFooter = NMSClass.getNMSClass("PacketPlayOutPlayerListHeaderFooter");
		    try {
			    assert this.PacketPlayOutPlayerListHeaderFooter != null;
			    this.newPacketPlayOutPlayerListHeaderFooter = this.PacketPlayOutPlayerListHeaderFooter.getConstructor();
			} catch (NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		    this.ChatComponentText = NMSClass.getNMSClass("ChatComponentText");

		    Iterator<?> iteanimtab = Objects.requireNonNull(TablistConfig.getConfig().getConfigurationSection("Animations")).getKeys(false).iterator();
		    
		    animationtab.clear();
	    	while (iteanimtab.hasNext()) {
	    		String string = (String) iteanimtab.next();
	    		
	    		if (string.contentEquals("Enable")) continue;
	    		
	    		if (TablistConfig.getConfig().getBoolean("Animations.Enable")) {
		    		BukkitTask task = new AnimationTabTask(string).runTaskTimer(this, 20, TablistConfig.getConfig().getInt("Animations." + string + ".refresh-time-ticks"));
		    		animationtabtask.put(string, task.getTaskId());
	    		}
	    	}
	    	
	    	BukkitTask tablistmain = new MainTablist(hea, foo, this.PacketPlayOutPlayerListHeaderFooter, this.ChatComponentText, this.newPacketPlayOutPlayerListHeaderFooter).runTaskTimer(this, 20L, TablistConfig.getConfig().getLong("Tablist.refresh-time-ticks"));
	    	
	    	tablistnumber = tablistmain.getTaskId();
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
	    	}

	    }.runTaskTimer(this, 0, 60);

	    date = OtherUtils.getDate();
	    
	    new BukkitRunnable() {

	    	@Override
			public void run() {
	    		date = OtherUtils.getDate();	    		
	    	}

	    }.runTaskTimer(this, 0, 600);
	    
	    HawnCommand.slotview.clear();
	    injumpwithjumppad.clear();
	    avoidtitles.clear();
	    
	    if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Day.Enable")) {
	    	new AlwaysDayTask().runTaskTimer(this, 20, 10000L);
	    }
	    
	    if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Night.Enable")) {
	    	new AlwaysNightTask().runTaskTimer(this, 20, 7000L);
	    }
	    
	    /*
	     * Check Worlds
	     */
	    if (!ConfigWorldGeneral.getConfig().isSet("World.CheckConfig")) {
	    	String pathname = new File(".").getAbsolutePath();
			File directory = new File(pathname);
			GuiSystem.getFileList(directory);
			
			for (File directorfile : GuiSystem.fileList) {
				if (GuiSystem.checkIfIsWorld(directorfile)) {
					String worldname = directorfile.getName();

					ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", Bukkit.getWorld(worldname) != null);
					ConfigWorldGeneral.saveConfigFile();
				}
			}
			
			ConfigWorldGeneral.getConfig().set("World.CheckConfig", true);
			ConfigWorldGeneral.saveConfigFile();
			
			GuiSystem.fileList.clear();
	    }
	    
	    if (ConfigWorldGeneral.getConfig().isSet("World.CheckConfig")) {
	    	String pathname = new File(".").getAbsolutePath();
			File directory = new File(pathname);
			GuiSystem.getFileList(directory);
			
			for (File directorfile : GuiSystem.fileList) {
				if (GuiSystem.checkIfIsWorld(directorfile)) {
					String worldname = directorfile.getName();
					
					if (ConfigWorldGeneral.getConfig().getBoolean("World-List." + worldname + ".Load")) {
						if (Bukkit.getWorld(worldname) == null) {
							Bukkit.getServer().createWorld((new WorldCreator(worldname)));
						}
					}
				}
			}
			
			GuiSystem.fileList.clear();
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

		    for (String string : Objects.requireNonNull(ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items")).getKeys(false)) {
			    String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";

			    CustomJoinItem.itemcjislot.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), path_item);
			    CustomJoinItem.itemcjislotname.put(ConfigCJIGeneral.getConfig().getInt(path_item + "Slot"), string);
		    }
	    }
	    
	    new BukkitRunnable() {
            @Override
            public void run() {
            	if (ScoreboardMainConfig.getConfig().getBoolean("Scoreboard.Enable")) {
        			File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Scoreboard/");
        			Main.getInstance().loadScoreboards(folder);
        			
        			for (Player p : Bukkit.getOnlinePlayers()) {
        	            if (Main.getInstance().getBoards().containsKey(p.getUniqueId())) {
        	            	Main.getInstance().getBoards().get(p.getUniqueId()).remove();
        	            	Main.getInstance().getBoards().remove(p.getUniqueId());
        	            }
        	            Main.getInstance().createDefaultScoreboard(p);
        			}
        		}
            }
	    }.runTaskLater(this, 150);
	    
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The last remaining things to be loaded have been loaded");
		gcs(ChatColor.BLUE+"| ");

		// Check version
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"This server is running on " + versionUtils.get());
		gcs(ChatColor.BLUE+"| ");

		// Warning
		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable") && OnJoinConfig.getConfig().getBoolean("Fly.Enable")) {
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"Please note that if a player can both fly and make a double jump");
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

		fileconfiglist.clear();

		for (Player p : Bukkit.getOnlinePlayers()) {
            p.setScoreboard(Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard());
            
            try {
            	BossBarApi.deletebar(p);
            } catch (Exception ignored) {}
		}
		
		getServer().getMessenger().unregisterIncomingPluginChannel(this, "wdl:init");
	    getServer().getMessenger().unregisterOutgoingPluginChannel(this, "wdl:control");
	    try {
	      getServer().getMessenger().unregisterIncomingPluginChannel(this, "WDL|INIT");
	      getServer().getMessenger().unregisterOutgoingPluginChannel(this, "WDL|CONTROL");
	    } catch (Exception ignored) {}
	    
		gcs(ChatColor.RED+"Hawn - Good bye");
	}

	public static Main getInstance() {
		return instance;
	}
	
	public VersionUtils getVersionClass() {
		return versionUtils;
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
					gcs(ChatColor.YELLOW+"| "+ChatColor.RED+"Old version of Hawn detected on your server");
					gcs(ChatColor.YELLOW+"| "+ChatColor.RED+"The new version is " + ChatColor.YELLOW + UpdateChecker.new_number_version + ChatColor.RED +" and you are on the version " + ChatColor.GOLD + versions);
					gcs(ChatColor.YELLOW+"| "+ChatColor.RED+"Download the new version on the Hawn spigot page");
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

		if (Objects.requireNonNull(fo.listFiles()).length <= 0) {
	    	return;
		}

		for (File f : Objects.requireNonNull(fo.listFiles())) {
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
			if (boards.containsKey(player.getUniqueId())) {
				ScoreboardInfo in = this.info.get("hawn.scoreboard."+sb);
				boards.get(player.getUniqueId()).createNew(in);
			} else {
				new PlayerBoard(this, player.getUniqueId(), info.get("hawn.scoreboard."+sb));
			}
		} else {
			for (String s : info.keySet()) {
	            ScoreboardInfo in = info.get(s);
	            
	            String playerWorld = player.getWorld().getName();
	            
	            if (in.getEnabledWorlds() == null) {
	            	if (player.hasPermission(s)) {
	                    if (boards.containsKey(player.getUniqueId())) {
	                        if (boards.get(player.getUniqueId()).getList().equals(in.getText())) {
	                            player.setScoreboard(boards.get(player.getUniqueId()).getBoard());
	                            PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                            return;
	                        }
	                        boards.get(player.getUniqueId()).createNew(in);
	                    } else {
	                        new PlayerBoard(this, player.getUniqueId(), info.get(s));
	                    }
			            PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    return;
	                }
	            } else if (in.getEnabledWorlds() != null && in.getEnabledWorlds().contains(playerWorld)) {
	                if (player.hasPermission(s)) {
	                    if (boards.containsKey(player.getUniqueId())) {
	                        if (boards.get(player.getUniqueId()).getList().equals(in.getText())) {
	                            player.setScoreboard(boards.get(player.getUniqueId()).getBoard());
	                            PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                            return;
	                        }
	                        boards.get(player.getUniqueId()).createNew(in);
	                    } else {
	                        new PlayerBoard(this, player.getUniqueId(), info.get(s));
	                    }
		                PlayerOptionSQLClass.saveSBmysqlyaml(player, this.infoname2.get(s), "FALSE");
	                    return;
	                }
	            }
	        }
			
			PlayerBoard board = boards.getOrDefault(player.getUniqueId(), null);
			if (board != null) {
				board.remove();
			}
		}
	}

	@SuppressWarnings("static-access")
	public HashMap<UUID, PlayerBoard> getBoards() {
		return this.boards;
	}

	@SuppressWarnings("static-access")
	public List<PlayerBoard> getAllboards() {
		return this.allboards;
	}

	public HashMap<String, ScoreboardInfo> getInfo() {
		return this.info;
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

	public BungeeApi getBungApi() {
		return bungApi;
	}

	public HooksManager getHooksManager() {
    	return hooksManager;
	}

}