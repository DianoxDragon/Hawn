package fr.dianox.hawn;

import fr.dianox.hawn.utility.ConfigPlayerGet;

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
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.modules.autobroadcast.AutoBroadcastManager;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.onjoin.OnJoin;
import fr.dianox.hawn.modules.onjoin.cji.CjiManager;
import fr.dianox.hawn.modules.scoreboard.ScoreManager;
import fr.dianox.hawn.modules.serverlist.ServerListManager;
import fr.dianox.hawn.modules.tablist.TabManager;
import fr.dianox.hawn.modules.world.WorldManager;
import fr.dianox.hawn.modules.world.generator.Generators;
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
import fr.dianox.hawn.utility.load.Reload;
import fr.dianox.hawn.utility.load.WorldList;
import fr.dianox.hawn.utility.server.Tps;
import fr.dianox.hawn.utility.server.WarnTPS;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.ChunkGenerator;
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
	private Setup setup;
	private SQL sql;

	private static String versions = "";
	public static Boolean devbuild = false;
	public static Integer devbuild_number = 0;
	public static String date = "";
	
	public static String LanguageType = "en_US";
	
	public static volatile String UpToDate = "§7Unknown";
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
		long start = System.nanoTime();

		versions = getDescription().getVersion();

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
			new org.bstats.bukkit.Metrics(this, 4563);
		} catch (Exception | LinkageError e) {
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"An error made it impossible for the metrics to be activated");
			gcs(ChatColor.YELLOW+"| "+ChatColor.GOLD+"You can restart your server if you want, but it's optional, metrics are just stats");
			gcs(ChatColor.YELLOW+"| ");
		}

		instance = this;

		versionUtils = new VersionUtils();

		fr.dianox.hawn.utility.config.ConfigDefaults.reset();
	    configManager = new ConfigManager(this);

	    try {
		    new CommandManager(this);
	    } catch (NoSuchFieldException | IllegalAccessException e) {
		    e.printStackTrace();
	    }

	    new Manager(this).registerEvents();
		
		getServer().getMessenger().registerIncomingPluginChannel(this, "wdl:init", pcl = new PluginChannelListener());
	    getServer().getMessenger().registerOutgoingPluginChannel(this, "wdl:control");

		bungApi = new BungeeApi(this);

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
	    getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", bungApi);

		eventManager = new EventManager();


		// MYSQL

		sql = new SQL(this);
		ConfigPlayerGet.start(this);

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
	    
	    // Every 5 hours, off the main thread
	    new BukkitRunnable() {
	    	@Override
	    	public void run() {
	    		UpdateCheckReload();
	    	}
	    }.runTaskTimerAsynchronously(this, 360000, 360000);
	    
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

		setup = new Setup(this);

		WorldList.setworldlist();
		Reload.configlist();

		// Server, hooks, storage, what is loaded, what is on, the options that contradict each other
		fr.dianox.hawn.utility.StartupReport.print(start);
	}

	@Override
	public void onDisable() {
		super.onDisable();

		ConfigPlayerGet.shutdown();
		SQL.close();

		fileconfiglist.clear();

		// Only the sidebar shown by Hawn is removed: the scoreboard of the players (teams, other plugins) is kept
		if (scoreManager != null) {
			for (FastBoard board : scoreManager.playerboard.values()) {
				try {
					if (!board.isDeleted()) board.delete();
				} catch (Exception ignored) {}
			}
		}

		for (Player p : Bukkit.getOnlinePlayers()) {
            try {
            	BossBarApi.deletebar(p);
            } catch (Exception ignored) {}
		}
		
		getServer().getMessenger().unregisterIncomingPluginChannel(this);
	    getServer().getMessenger().unregisterOutgoingPluginChannel(this);
	    
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

	/**
	 * @return the cached result of the last update check (never blocks)
	 */
	public static String getVersionUpdate() {
		return UpToDate;
	}

	/**
	 * Checks for an update asynchronously and logs the result in the console.
	 */
	public static void UpdateCheck() {
		if (devbuild) {
			gcs(ChatColor.BLUE+"| "+ChatColor.GOLD+"You are in a development build");
			gcs(ChatColor.BLUE+"| ");
			UpToDate = "§eDevelopment build";
			return;
		}

		if (!ConfigGeneral.getConfig().getBoolean("Plugin.Update.Check-Update")) {
			return;
		}

		Bukkit.getScheduler().runTaskAsynchronously(getInstance(), () -> {
			UpdateChecker updater = new UpdateChecker(Main.getInstance(), 66907);
			try {
				if (updater.checkForUpdates()) {
					UpToDate = "§cOld Version detected";
					gcs(ChatColor.YELLOW+"[Hawn] "+ChatColor.RED+"A new version of Hawn is available: " + ChatColor.YELLOW + UpdateChecker.new_number_version + ChatColor.RED +" (you are on " + ChatColor.GOLD + versions + ChatColor.RED + ")");
					gcs(ChatColor.YELLOW+"[Hawn] "+ChatColor.RED+"Download it on the Hawn spigot page");
				} else {
					UpToDate = "§aPlugin up to date";
					gcs(ChatColor.YELLOW+"[Hawn] "+ChatColor.GREEN+"Plugin is up to date");
				}
			} catch (Exception e) {
				getInstance().getLogger().warning("Could not check for updates: " + e.getMessage());
			}
		});
	}

	/**
	 * Refreshes the cached update status. Blocking: call it asynchronously.
	 */
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
				getInstance().getLogger().warning("Could not check for updates: " + e.getMessage());
			}
		}
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return Generators.voidGenerator();
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

	public Setup getSetup() {
		return setup;
	}
}