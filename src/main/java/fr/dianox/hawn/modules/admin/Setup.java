package fr.dianox.hawn.modules.admin;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup1Language;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup2World;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup3Spawn;
import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.messages.SetupLangFile;
import com.cryptomorin.xseries.XPotion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
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
	public static boolean needsetup = false;
	public static int setupplace = 1;

	// Admins to whom the setup has already been shown during this session
	private static final Set<UUID> inSetup = new HashSet<>();
	private static BukkitTask detectionTask;

	public Setup(Plugin plugin) {
		pl = plugin;

		// Detect if the plugin needs to be setup
		File file = new File(pl.getDataFolder(), "StockageInfo/Setup.lock");

		if (!file.exists()) {
			needsetup = true;
		}

		if (needsetup) {
			Bukkit.getPluginManager().registerEvents(this, pl);
			Bukkit.getPluginManager().registerEvents(new Setup1Language(), pl);
			Bukkit.getPluginManager().registerEvents(new Setup2World(), pl);
			Bukkit.getPluginManager().registerEvents(new Setup3Spawn(), pl);

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
	}

	@EventHandler
	public void onInventory(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();

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
