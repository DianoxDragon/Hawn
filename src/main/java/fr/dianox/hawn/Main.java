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
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.VersionUtils;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;
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

	// The fun features and the special items: safe for several threads (since 1.5.5)
	public static List<Player> injumpwithjumppad = new java.util.concurrent.CopyOnWriteArrayList<>();
	
	// The /spawn and /warp delays: safe for several threads (since 1.5.6)
	public static Map<UUID, Integer> player_spawnwarpdelay = new java.util.concurrent.ConcurrentHashMap<>();
	public static List<Player> inwarpd = new java.util.concurrent.CopyOnWriteArrayList<>();
	public static List<Player> inspawnd = new java.util.concurrent.CopyOnWriteArrayList<>();

	// A /spawn or /warp delay is over (teleported, cancelled or failed): moving no longer says "teleport cancelled"
	public static void endTeleportDelay(Player p) {
		player_spawnwarpdelay.remove(p.getUniqueId());
		inspawnd.remove(p);
		inwarpd.remove(p);
	}

    public static List<Player> buildbypasscommand = new ArrayList<>();
    public static List<Player> avoidtitles = new ArrayList<>();

    // Read by %timedelaypvcji%, which can be asked outside the main thread (the chat, PlaceholderAPI)
    public static Map<Player, Long> hiderCooldowns = new java.util.concurrent.ConcurrentHashMap<>();
    public static Map<Player, Long> fungunCooldowns = new java.util.concurrent.ConcurrentHashMap<>();
    public static Map<Player, Long> grapplingCooldowns = new java.util.concurrent.ConcurrentHashMap<>();
    public static Map<Player, Long> pearlMountCooldowns = new java.util.concurrent.ConcurrentHashMap<>();
    
    public static HashMap<Player, Integer> TaskVanishAB = new HashMap<>();

    public static List<Player> indj = new java.util.concurrent.CopyOnWriteArrayList<>();
    
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
			fr.dianox.hawn.utility.HawnMetrics.start(this);
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
		fr.dianox.hawn.modules.onjoin.JoinNumbers.start(this);

		hooksManager = new HooksManager(this);

		// check
		UpdateCheck();

		OnJoin.player_list.clear();
	    OnJoin.player_list.addAll(Bukkit.getServer().getOnlinePlayers());
		FlyCommand.player_list_flyc.clear();
		FunFeatures.player_list_dbenable.clear();

		ChatEmojisLoad.onLoad();
		
		// Version
		Tasks.timer(new Tps(), 100L, 1L);

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

	    // The update check runs again every 6 hours (UpdateCheck), not twice (since 1.5.7)
	    
	    indj.clear();
	    OnCommandEvent.cooldowncommands.clear();

	    buildbypasscommand.clear();

	    // Variable set
	    
	    OtherUtils.totalMemory();
	    OtherUtils.totalDisk();
	    OtherUtils.getOperatingSystem();
	    OtherUtils.javaver = String.valueOf(OtherUtils.getJavaVersion());

	    // Outside the main thread (since 1.5.4): reading the disk space can hang for seconds on Windows, and the server with it
	    Tasks.asyncTimer(() -> {
	    	OtherUtils.getMemoryUsageBar();
	    	OtherUtils.getCPUUsageBar();
	    	OtherUtils.getDiskUsageBar();
	    	OtherUtils.maxMemory();
	    	OtherUtils.freeMemory();
	    	OtherUtils.freeDisk();
	    }, 0, 60);

	    date = OtherUtils.getDate();
	    
	    Tasks.timer(() -> date = OtherUtils.getDate(), 0, 600);
	    
	    HawnCommand.slotview.clear();
	    injumpwithjumppad.clear();
	    avoidtitles.clear();
	    
	    if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Day.Enable")) {
	    	Tasks.timer(new AlwaysDayTask(), 20, 10000L);
	    }
	    
	    if (WorldEventConfig.getConfig().getBoolean("World.Time.Always-Night.Enable")) {
	    	Tasks.timer(new AlwaysNightTask(), 20, 7000L);
	    }
	    
	    /*
	     * Check Worlds
	     */
    	worldManager = new WorldManager();

	    scoreManager = new ScoreManager(this);

	    tabManager = new TabManager(this);

	    // Ranks.yml; one tick later, again if TAB or NametagEdit was enabled after Hawn (Enable: AUTO)
	    fr.dianox.hawn.modules.ranks.Ranks.load();
	    Tasks.next(fr.dianox.hawn.modules.ranks.Ranks::recheckPlugins);

	    // The menus of Menus/ (since 1.5), and the items of Nexo in the join items and the menus (one tick later: Nexo
	    // may be enabled after Hawn)
	    // The texts of {lang_<key>} (since 1.6), before the menus that use them
	    fr.dianox.hawn.utility.Translations.load();
	    fr.dianox.hawn.modules.menus.Menus.load();
	    // The holograms of Holograms/ (since 1.6), with PacketEvents
	    fr.dianox.hawn.modules.holograms.Holograms.load();
	    fr.dianox.hawn.hook.CustomItems.load();
	    Tasks.next(fr.dianox.hawn.hook.CustomItems::load);

	    /*
	     * Custom join item
	     */
		cjiManager = new CjiManager();

		setup = new Setup(this);

		WorldList.setworldlist();
		Reload.configlist();

		// The options added to the files of every language, on one line per file
		fr.dianox.hawn.utility.config.ConfigDefaults.logGrouped();

		// Server, hooks, storage, what is loaded, what is on, the options that contradict each other
		fr.dianox.hawn.utility.StartupReport.print(start);
	}

	@Override
	public void onDisable() {
		super.onDisable();

		// The holograms leave the screens of the players (since 1.6)
		try {
			fr.dianox.hawn.modules.holograms.Holograms.stop();
		} catch (Throwable ignored) {}
		// The tasks planned by Hawn: Folia does not cancel them all by itself (since 1.6)
		Tasks.cancelAll();

		fr.dianox.hawn.modules.onjoin.JoinNumbers.stop();
		// Folia stops the whole server with the plugin, and the players can only be touched from their own thread
		if (!Tasks.isFolia()) {
			// The riders of a pearl mount get off: the pearl would teleport them later
			fr.dianox.hawn.modules.onjoin.cji.SpecialItePearlMount.stopAll();
			// The menus of Hawn are closed: no menu stays open without its plugin
			for (org.bukkit.entity.Player p : Bukkit.getOnlinePlayers()) {
				if (fr.dianox.hawn.utility.gui.HawnMenu.isMenu(p.getOpenInventory().getTopInventory())) {
					p.closeInventory();
				}
			}
		}
		try {
			fr.dianox.hawn.modules.ranks.Ranks.clearAll();
		} catch (Exception ignored) {}
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

		// Again every 6 hours: a server that runs for days still learns that a new version is out
		Tasks.asyncTimer(() -> {
			boolean before = updateAvailable();
			UpdateCheckReload();
			if (!before && updateAvailable()) {
				gcs(ChatColor.YELLOW+"[Hawn] "+ChatColor.RED+"A new version of Hawn is available: " + ChatColor.YELLOW + UpdateChecker.new_number_version + ChatColor.RED +" (you are on " + ChatColor.GOLD + versions + ChatColor.RED + ")");
			}
		}, 6 * 60 * 60 * 20L, 6 * 60 * 60 * 20L);

		if (!ConfigGeneral.getConfig().getBoolean("Plugin.Update.Check-Update")) {
			return;
		}

		Tasks.async(() -> {
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

	public static boolean updateAvailable() {
		return "§cOld Version detected".equals(UpToDate);
	}

	/**
	 * A new version is out: the admins are told when they join (Plugin.Update.Notify-Admins), after the join messages.
	 */
	public static void notifyUpdate(Player p) {
		if (!updateAvailable() || !p.hasPermission("hawn.admin") || !ConfigGeneral.getConfig().getBoolean("Plugin.Update.Notify-Admins")) {
			return;
		}

		Tasks.later(() -> {
			if (!p.isOnline()) {
				return;
			}
			for (String msg : ConfigMGeneral.getConfig(p).getStringList("Update.Notify-Admins.Messages")) {
				ConfigEventUtils.ExecuteEvent(p, msg.replace("%new_version%", UpdateChecker.new_number_version).replace("%current_version%", versions), "", "", false);
			}
		}, 60L);
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