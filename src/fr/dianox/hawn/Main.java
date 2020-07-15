package fr.dianox.hawn;

import fr.dianox.hawn.command.CommandManager;
import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.command.commands.HawnCommand;
import fr.dianox.hawn.event.FunFeatures;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.event.world.AlwaysDayTask;
import fr.dianox.hawn.event.world.AlwaysNightTask;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.modules.Events.EventManager;
import fr.dianox.hawn.modules.VoidTP.VoidTPManager;
import fr.dianox.hawn.modules.autobroadcast.AutoBroadcastManager;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.onjoin.OnJoin;
import fr.dianox.hawn.modules.onjoin.cji.CjiManager;
import fr.dianox.hawn.modules.scoreboard.ScoreManager;
import fr.dianox.hawn.modules.serverlist.ServerListManager;
import fr.dianox.hawn.modules.tablist.TabManager;
import fr.dianox.hawn.modules.world.WorldManager;
import fr.dianox.hawn.modules.world.protection.BlockExceptions;
import fr.dianox.hawn.modules.world.protection.Interactables;
import fr.dianox.hawn.utility.BossBarApi;
import fr.dianox.hawn.utility.BungeeApi;
import fr.dianox.hawn.utility.OtherUtils;
import fr.dianox.hawn.utility.VersionUtils;
import fr.dianox.hawn.utility.config.ConfigManager;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.commands.FlyCommandConfig;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.events.WorldEventConfig;
import fr.dianox.hawn.utility.server.Tps;
import fr.dianox.hawn.utility.server.WarnTPS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Main extends JavaPlugin implements Listener {

	private static Main instance;
	private VersionUtils versionUtils;
	private BungeeApi bungApi;
	private HooksManager hooksManager;
	private ScoreManager scoreManager;
	private ConfigManager configManager;
	private TabManager tabManager;
	private Interactables interactables;
	private BlockExceptions blockExceptions;
	private ServerListManager serverListManager;
	private WorldManager worldManager;
	private CjiManager cjiManager;
	private VoidTPManager voidTPManager;
	private EventManager eventManager;
	private SQL sql;

	private static String versions = "1.1.4-Beta";
	public static Boolean devbuild = false;
	public static Integer devbuild_number = 0;
	public static String date = "";
	
	public static String LanguageType = "en_US";
	
	public static String UpToDate, nmsver;
	public static boolean useOldMethods = false;
	public static List<String> fileconfiglist = new ArrayList<>();
	
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

	public static List<Player> injumpwithjumppad = new ArrayList<>();
	
	public static HashMap<UUID, Integer> player_spawnwarpdelay = new HashMap<>();
	public static List<Player> inwarpd = new ArrayList<>();
	public static List<Player> inspawnd = new ArrayList<>();

    public static List<Player> buildbypasscommand = new ArrayList<>();
    public static List<Player> avoidtitles = new ArrayList<>();

    public static HashMap<Player, Long> hiderCooldowns = new HashMap<>();
    public static HashMap<Player, Long> fungunCooldowns = new HashMap<>();
    
    public static HashMap<Player, Integer> TaskVanishAB = new HashMap<>();

    public static List<Player> indj = new ArrayList<>();
    
    public static PluginChannelListener pcl;
    
    public static HashMap<String, Integer> tasklist = new HashMap<>();

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
			new Metrics(this, 4563);
		} catch (Exception e) {
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"An error made it impossible for the metrics to be activated");
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"You can restart your server if you want, but it's optional, metrics are just stats");
			gcs(ChatColor.YELLOW+"| ");
		}

		instance = this;

		versionUtils = new VersionUtils();

	    configManager = new ConfigManager(this);
		
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Configurations files loaded");
		gcs(ChatColor.BLUE+"| ");

	    try {
		    new CommandManager(this);
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

		bungApi = new BungeeApi(this);

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", bungApi);

		eventManager = new EventManager();

		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"Events loaded");
		gcs(ChatColor.BLUE+"| ");

		// MYSQL

		sql = new SQL(this);

		hooksManager = new HooksManager(this);

		// check
		UpdateCheck();

		OnJoin.player_list.clear();
	    OnJoin.player_list.addAll(Bukkit.getServer().getOnlinePlayers());
		FlyCommand.player_list_flyc.clear();
		FunFeatures.player_list_dbenable.clear();

		ChatEmojisLoad.onLoad();
		
		// Version
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Tps(), 100L, 1L);

		if (ConfigGeneral.getConfig().getBoolean("Plugin.Tps.Warn-system")) {
			WarnTPS.runWarnSystemTask(this);
		}
		
		HawnCommand.noclip.clear();

		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable") && FlyCommand.player_list_flyc.contains(p)) {
				FlyCommand.player_list_flyc.remove(p);
				p.setAllowFlight(false);
				p.setFlying(false);
			}
		}

		nmsver = Bukkit.getServer().getClass().getPackage().getName();
	    nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);

	    if (nmsver.equalsIgnoreCase("v1_8_R1")) {
	      useOldMethods = true;
	    }

	    player_spawnwarpdelay.clear();

	    /*
	     * Protection interactable
	     */
	    interactables = new Interactables();
	    blockExceptions = new BlockExceptions();
	    
	    /*
	     * Voidtp per world
	     */
		voidTPManager = new VoidTPManager();

	    /*
	     * MOTD
	     */
		serverListManager = new ServerListManager();
	    
	    // broadcast
		new AutoBroadcastManager();
	    
	    new BukkitRunnable() {
	    	@Override
	    	public void run() {
	    		UpdateCheck();
	    	}
	    }.runTaskTimer(this, 0, 360000);
	    
	    indj.clear();
	    OnCommandEvent.cooldowncommands.clear();

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
	    worldManager = new WorldManager();

	    scoreManager = new ScoreManager(this);

	    tabManager = new TabManager(this);

	    /*
	     * Custom join item
	     */
		cjiManager = new CjiManager();

		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"The last remaining things to be loaded have been loaded");
		gcs(ChatColor.BLUE+"| ");

		// Check version
		gcs(ChatColor.BLUE+"| "+ChatColor.YELLOW+"This server is running on " + versionUtils.getVersionsS());
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
				UpToDate = updater.checkForUpdates() ? "§cOld Version detected" : "§aPlugin up to date";
			} catch (Exception e) {
				System.out.println("Could not check for updates! Stacktrace:");
				e.printStackTrace();
			}
		}
	}

	public BungeeApi getBungApi() {
		return bungApi;
	}

	public HooksManager getHooksManager() {
    	return hooksManager;
	}

	public ScoreManager getScoreManager() {
    	return scoreManager;
    }

    public ConfigManager getConfigManager() {
    	return configManager;
    }

	public TabManager getTabManager() {
		return tabManager;
	}

	public SQL getSql() {
		return sql;
	}

	public VersionUtils getVersionUtils() {
		return versionUtils;
	}

	public Interactables getInteractables() {
		return interactables;
	}

	public BlockExceptions getBlockExceptions() {
		return blockExceptions;
	}

	public ServerListManager getServerListManager() {
		return serverListManager;
	}

	public WorldManager getWorldManager() {
		return worldManager;
	}

	public CjiManager getCjiManager() {
		return cjiManager;
	}

	public VoidTPManager getVoidTPManager() {
		return voidTPManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}
}