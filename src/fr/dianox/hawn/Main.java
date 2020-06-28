package fr.dianox.hawn;

import fr.dianox.hawn.command.CommandManager;
import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.command.commands.HawnCommand;
import fr.dianox.hawn.event.BasicFeatures;
import fr.dianox.hawn.event.FunFeatures;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.event.OnJoin;
import fr.dianox.hawn.event.world.AlwaysDayTask;
import fr.dianox.hawn.event.world.AlwaysNightTask;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.modules.autobroadcast.AutoBroadcastManager;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.modules.scoreboard.ScoreManager;
import fr.dianox.hawn.modules.tablist.TabManager;
import fr.dianox.hawn.modules.worldsystem.GuiSystem;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.config.ConfigManager;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import fr.dianox.hawn.utility.config.configs.commands.FlyCommandConfig;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.events.ConfigGProtection;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.events.VoidTPConfig;
import fr.dianox.hawn.utility.config.configs.events.WorldEventConfig;
import fr.dianox.hawn.utility.server.Tps;
import fr.dianox.hawn.utility.server.WarnTPS;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class Main extends JavaPlugin implements Listener {

	private static Main instance;
	private VersionUtils versionUtils;
	private BungeeApi bungApi;
	private HooksManager hooksManager;
	private ScoreManager scoreManager;
	private ConfigManager configManager;
	private TabManager tabManager;
	private SQL sql;

	private static String versions = "1.1.0-Beta";
	public static Boolean devbuild = false;
	public static Integer devbuild_number = 0;
	public static String date = "";
	
	public static String LanguageType = "en_US";
	
	public static String UpToDate, nmsver;
	public static boolean useOldMethods = false;
	public static List<String> fileconfiglist = new ArrayList<>();
	
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

	public static List<Player> injumpwithjumppad = new ArrayList<>();
	
	public static HashMap<UUID, Integer> player_spawnwarpdelay = new HashMap<>();
	public static List<Player> inwarpd = new ArrayList<>();
	public static List<Player> inspawnd = new ArrayList<>();

    public static List<Player> buildbypasscommand = new ArrayList<>();
    public static List<Player> avoidtitles = new ArrayList<>();

    public static HashMap<Player, Long> hiderCooldowns = new HashMap<>();
    public static HashMap<Player, Long> fungunCooldowns = new HashMap<>();
    
    public static HashMap<Player, Integer> TaskVanishAB = new HashMap<>();
    
    public static List<Material> block_exception_place = new ArrayList<>();
    public static List<Material> block_exception_break = new ArrayList<>();
    public static List<Material> interactables = new ArrayList<>();

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

	    scoreManager = new ScoreManager(this);

	    tabManager = new TabManager(this);

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
}