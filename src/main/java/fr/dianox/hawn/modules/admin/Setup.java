package fr.dianox.hawn.modules.admin;

import fr.dianox.hawn.utility.gui.HawnMenu;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup1Language;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup2World;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup3Spawn;
import fr.dianox.hawn.modules.admin.SetupUtils.SetupModules;
import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.Tasks;
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

	// First start of Hawn (no Setup.lock): the setup is for every admin. Opened again with /hawn setup: only for this admin
	private static boolean forEveryAdmin = false;

	// Admins to whom the setup has already been shown during this session
	private static final Set<UUID> inSetup = new HashSet<>();

	// A click is being handled: a menu closed now is closed by Hawn (next step, chat, setspawn...), not by Escape
	private static final Set<UUID> clicking = new HashSet<>();

	// The Escape message: a check waiting, and the time of the last message
	private static final Set<UUID> escapeCheck = new HashSet<>();
	private static final java.util.Map<UUID, Long> lastWarning = new java.util.HashMap<>();
	private static BukkitTask detectionTask;

	// Something was changed (language, modules, world, spawn): Hawn reloads at the end, no restart (since 1.5.1)
	private static boolean changed = false;

	public Setup(Plugin plugin) {
		pl = plugin;
		instance = this;

		// Detect if the plugin needs to be setup
		File file = new File(pl.getDataFolder(), "StockageInfo/Setup.lock");

		if (!file.exists()) {
			needsetup = true;
		}

		if (needsetup) {
			forEveryAdmin = true;
			registerListeners();

			// Also starts the setup for the players who become operator while they are online
			detectionTask = Tasks.timer(() -> {
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
		Bukkit.getPluginManager().registerEvents(new SetupModules(), pl);
	}

	/**
	 * /hawn setup: opens the setup for this player. When it was already done, a question first (since 1.5.1): a /hawn setup
	 * typed by mistake changes nothing.
	 */
	public static void open(Player p) {
		registerListeners();

		if (!needsetup) {
			openConfirm(p);
			return;
		}

		inSetup.add(p.getUniqueId());
		openCurrentStep(p);
	}

	// The setup again, from the first step
	private static void restart(Player p) {
		if (!needsetup) {
			needsetup = true;
			setupplace = 1;
			changed = false;
		}

		inSetup.add(p.getUniqueId());
		openCurrentStep(p);
	}

	public static final String CONFIRM_NAME = "§cHawn Setup";

	private static void openConfirm(Player p) {
		org.bukkit.inventory.Inventory inv = HawnMenu.create(27, CONFIRM_NAME, HawnMenu.Type.SETUP_CONFIRM);
		for (int i = 0; i < 27; i++) {
			inv.setItem(i, item(" ", null, com.cryptomorin.xseries.XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		java.util.List<String> lore = new java.util.ArrayList<>();
		for (String line : SetupLangFile.getConfig().getStringList("SetupConfirm.Info-Lore")) {
			lore.add(MessageUtils.colourTheStuff(line));
		}
		inv.setItem(4, item(text("SetupConfirm.Info"), lore, com.cryptomorin.xseries.XMaterial.OAK_SIGN.parseMaterial()));
		inv.setItem(11, item(text("SetupConfirm.Yes"), null, com.cryptomorin.xseries.XMaterial.EMERALD_BLOCK.parseMaterial()));
		inv.setItem(15, item(text("SetupConfirm.No"), null, com.cryptomorin.xseries.XMaterial.BARRIER.parseMaterial()));
		p.openInventory(inv);
	}

	private static String text(String path) {
		return MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString(path, ""));
	}

	private static org.bukkit.inventory.ItemStack item(String title, java.util.List<String> lore, org.bukkit.Material material) {
		org.bukkit.inventory.ItemStack stack = new org.bukkit.inventory.ItemStack(material);
		org.bukkit.inventory.meta.ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(title);
		if (lore != null) {
			meta.setLore(lore);
		}
		stack.setItemMeta(meta);
		return stack;
	}

	@EventHandler
	public void onConfirm(InventoryClickEvent e) {
		if (HawnMenu.typeOf(e.getView()) != HawnMenu.Type.SETUP_CONFIRM) {
			return;
		}

		e.setCancelled(true);
		if (!e.isLeftClick() || !(e.getWhoClicked() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getWhoClicked();
		if (e.getRawSlot() == 11) {
			p.closeInventory();
			Tasks.later(() -> {
				if (p.isOnline()) restart(p);
			}, 5L);
		} else if (e.getRawSlot() == 15) {
			p.closeInventory();
		}
	}

	/**
	 * A change made in the setup: Hawn reloads at the end.
	 */
	public static void changed() {
		changed = true;
	}

	/**
	 * This player is doing the setup (or will, when joining during the first one): no join effects for this player.
	 */
	public static boolean isInSetup(Player p) {
		return needsetup && p.hasPermission("hawn.setup") && (forEveryAdmin || inSetup.contains(p.getUniqueId()));
	}

	/**
	 * This admin has the setup open (an admin who joins during the first setup gets it too, a few seconds later).
	 */
	public static boolean doingSetup(Player p) {
		return needsetup && inSetup.contains(p.getUniqueId());
	}

	/**
	 * The World Manager is the step of the setup for this admin (its Done button, its messages); not for the other admins.
	 */
	public static boolean inWorldStep(Player p) {
		return needsetup && setupplace == 21 && inSetup.contains(p.getUniqueId());
	}

	private static void start(Player p) {
		inSetup.add(p.getUniqueId());
		p.addPotionEffect(new PotionEffect(XPotion.BLINDNESS.get(), 5 * 20, 1));
		openCurrentStep(p);
	}

	/**
	 * Previous and Next of the menus of the setup: this step, opened a few ticks later (after the click).
	 */
	public static void goTo(Player p, int step) {
		if (!doingSetup(p)) {
			return;
		}

		setupplace = step;
		Tasks.later(() -> openCurrentStep(p), 5L);
	}

	// The item of the Previous / Next buttons: a door, never an arrow (the arrows of the World Manager change its pages)
	public static final org.bukkit.Material NAVIGATION = com.cryptomorin.xseries.XMaterial.OAK_DOOR.parseMaterial();

	private static void openCurrentStep(Player p) {
		if (!needsetup || !p.isOnline()) {
			return;
		}

		if (setupplace == 1) {
			Setup1Language.OpenInventory(p);
		} else if (setupplace == 11) {
			SetupModules.OpenInventory(p);
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
		forEveryAdmin = false;
		inSetup.clear();

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

		// 124: no restart since 1.5.1, the changes are applied by a reload
		boolean reload = changed;
		changed = false;
		if (reload) {
			fr.dianox.hawn.utility.load.Reload.reloadconfig();
		}

		if (p != null) {
			p.closeInventory();
			for (String msg : SetupLangFile.getConfig().getStringList(reload ? "Setup.Finished" : "Setup.Closed")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
		}
	}

	// Events
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (needsetup && forEveryAdmin && p.hasPermission("hawn.setup")) {
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
		HawnMenu.Type type = HawnMenu.typeOf(e.getView());
		boolean setupMenu = type != null && (type.isSetup() || (setupplace == 21 && type.isWorldManager()));
		// One check at a time, and one message per second: a menu can be closed several times in a row (reopened by a move...)
		if (needsetup && setupMenu && inSetup.contains(p.getUniqueId()) && !clicking.contains(p.getUniqueId()) && escapeCheck.add(p.getUniqueId())) {
			Tasks.later(() -> {
				escapeCheck.remove(p.getUniqueId());
				Long last = lastWarning.get(p.getUniqueId());
				if (last != null && System.currentTimeMillis() - last < 1000) return;

				if (needsetup && p.isOnline() && p.getOpenInventory().getTopInventory().getType() == InventoryType.CRAFTING) {
					// 124: a setup opened again with /hawn setup ends with Escape, on the steps without a teleport or a chat
					if (!forEveryAdmin && (setupplace == 1 || setupplace == 11 || setupplace == 2)) {
						finish(p);
						return;
					}

					lastWarning.put(p.getUniqueId(), System.currentTimeMillis());
					for (String msg : SetupLangFile.getConfig().getStringList("Setup.Still-In-Setup")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
					try {
						p.playSound(p.getLocation(), XParse.sound("BLOCK_NOTE_BLOCK_BASS", "Setup"), 0.8f, 0.8f);
					} catch (Exception ignored) {}
				}
			}, 2L);
		}

		// Only the admins doing the setup: the others can be op too (bug 61)
		if (needsetup && inSetup.contains(p.getUniqueId()) && (setupplace == 1 || setupplace == 11 || setupplace == 2)) {
			// The inventory is also closed when the player leaves or when the setup ends
			Tasks.later(() -> {
				// Only when no menu of Hawn is open: opening the step closes the menu open now, which would open it again
				// every 5 ticks (the menu blinked)
				if (needsetup && p.isOnline() && (setupplace == 1 || setupplace == 11 || setupplace == 2)
						&& HawnMenu.typeOf(p.getOpenInventory()) == null) {
					openCurrentStep(p);
				}
			}, 5L);
		}
	}

	@EventHandler
	public void onInventory(PlayerMoveEvent e) {
		Player p = e.getPlayer();

		if (needsetup) {
			if (inSetup.contains(p.getUniqueId())) {
				if (setupplace == 21) {
					if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) return;
					GuiSystem.FirstPage(p);
				}
			}
		}
	}
}
