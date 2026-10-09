package fr.dianox.hawn.modules.admin;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup1Language;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup2World;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup3Spawn;
import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.XParse;
import fr.dianox.hawn.utility.config.configs.messages.SetupLangFile;
import com.cryptomorin.xseries.XPotion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Setup implements Listener {

	private static Plugin pl;
	private static Setup instance;
	private static boolean listenersRegistered = false;
	public static boolean needsetup = false;
	public static int setupplace = 1;

	// Admins to whom the setup has already been shown during this session
	private static final Set<UUID> inSetup = new HashSet<>();

	// A click is being handled: a menu closed now is closed by Hawn (next step, chat, setspawn...), not by Escape
	private static final Set<UUID> clicking = new HashSet<>();

	// The Escape message: a check waiting, and the time of the last message
	private static final Set<UUID> escapeCheck = new HashSet<>();
	private static final java.util.Map<UUID, Long> lastWarning = new java.util.HashMap<>();
	private static BukkitTask detectionTask;

	public Setup(Plugin plugin) {
		pl = plugin;
		instance = this;

		// Detect if the plugin needs to be setup
		File file = new File(pl.getDataFolder(), "StockageInfo/Setup.lock");

		if (!file.exists()) {
			needsetup = true;
		}

		if (needsetup) {
			registerListeners();

			// Also starts the setup for the players who become operator while they are online
			detectionTask = Bukkit.getScheduler().runTaskTimer(pl, () -> {
				for (Player p : Bukkit.getOnlinePlayers()) {
					if (p.hasPermission("hawn.setup") && !inSetup.contains(p.getUniqueId())) {
						start(p);
					}
				}
			}, 40L, 40L);
		}
	}

	private static void registerListeners() {
		if (listenersRegistered) {
			return;
		}

		listenersRegistered = true;
		Bukkit.getPluginManager().registerEvents(instance, pl);
		Bukkit.getPluginManager().registerEvents(new Setup1Language(), pl);
		Bukkit.getPluginManager().registerEvents(new Setup2World(), pl);
		Bukkit.getPluginManager().registerEvents(new Setup3Spawn(), pl);
	}

	/**
	 * /hawn setup: opens the setup for this player, from the first step when it was already done.
	 */
	public static void open(Player p) {
		if (!needsetup) {
			needsetup = true;
			setupplace = 1;
			registerListeners();
		}

		inSetup.add(p.getUniqueId());
		openCurrentStep(p);
	}

	private static void start(Player p) {
		inSetup.add(p.getUniqueId());
		p.addPotionEffect(new PotionEffect(XPotion.BLINDNESS.get(), 5 * 20, 1));
		openCurrentStep(p);
	}

	private static void openCurrentStep(Player p) {
		if (!needsetup || !p.isOnline()) {
			return;
		}

		if (setupplace == 1) {
			Setup1Language.OpenInventory(p);
		} else if (setupplace == 2) {
			Setup2World.OpenInventory(p);
		} else if (setupplace == 21) {
			GuiSystem.FirstPage(p);
		} else if (setupplace == 3) {
			Setup3Spawn.OpenInventory(p);
		}
	}

	/**
	 * Ends the setup: it will not be shown again, even after a restart.
	 */
	public static void finish(Player p) {
		if (!needsetup) {
			return;
		}

		// Before closing the inventory, otherwise the close event reopens the setup
		needsetup = false;

		if (detectionTask != null) {
			detectionTask.cancel();
			detectionTask = null;
		}

		File file = new File(pl.getDataFolder(), "StockageInfo/Setup.lock");
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
		} catch (IOException e) {
			pl.getLogger().warning("Could not create " + file.getPath() + ": " + e.getMessage());
		}

		if (p != null) {
			p.closeInventory();
			for (String msg : SetupLangFile.getConfig().getStringList("Setup.Restart-Server")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		}
	}

	// Events
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (needsetup && p.hasPermission("hawn.setup")) {
			start(p);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		inSetup.remove(e.getPlayer().getUniqueId());
		clicking.remove(e.getPlayer().getUniqueId());
		escapeCheck.remove(e.getPlayer().getUniqueId());
		lastWarning.remove(e.getPlayer().getUniqueId());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onClickStart(InventoryClickEvent e) {
		clicking.add(e.getWhoClicked().getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onClickEnd(InventoryClickEvent e) {
		clicking.remove(e.getWhoClicked().getUniqueId());
	}

	@EventHandler
	public void onInventory(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();

		// Escape on a menu of the setup: a message and a sound, if no other menu opens after it
		String title = e.getView().getTitle();
		boolean setupMenu = title.startsWith("\u00a7cHawn Setup") || (setupplace == 21 && title.startsWith("\u00a7cWorld Manager"));
		// One check at a time, and one message per second: a menu can be closed several times in a row (reopened by a move...)
		if (needsetup && setupMenu && p.hasPermission("hawn.setup") && !clicking.contains(p.getUniqueId()) && escapeCheck.add(p.getUniqueId())) {
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
				escapeCheck.remove(p.getUniqueId());
				Long last = lastWarning.get(p.getUniqueId());
				if (last != null && System.currentTimeMillis() - last < 1000) return;

				if (needsetup && p.isOnline() && p.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING) {
					lastWarning.put(p.getUniqueId(), System.currentTimeMillis());
					for (String msg : SetupLangFile.getConfig().getStringList("Setup.Still-In-Setup")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
					try {
						p.playSound(p.getLocation(), XParse.sound("BLOCK_NOTE_BLOCK_BASS", "Setup"), 0.8f, 0.8f);
					} catch (Exception ignored) {}
				}
			}, 2);
		}

		if (needsetup && p.hasPermission("hawn.setup") && (setupplace == 1 || setupplace == 2)) {
			// The inventory is also closed when the player leaves or when the setup ends
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
				if (needsetup && p.isOnline() && (setupplace == 1 || setupplace == 2)) {
					openCurrentStep(p);
				}
			}, 5);
		}
	}

	@EventHandler
	public void onInventory(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (needsetup) {
			if (p.hasPermission("hawn.setup")) {
				if (setupplace == 21) {
					if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) return;
					GuiSystem.FirstPage(p);
				}
			}
		}
	}
}
