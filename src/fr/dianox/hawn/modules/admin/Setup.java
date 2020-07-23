package fr.dianox.hawn.modules.admin;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup1Language;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup2World;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup3Spawn;
import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.utility.XPotion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;

public class Setup implements Listener {

	private static Plugin pl;
	public static boolean needsetup = false;
	public static int setupplace = 1;

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
		}
	}

	// Events
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		if (needsetup) {
			if (p.hasPermission("hawn.setup")) {
				p.addPotionEffect(new PotionEffect(XPotion.BLINDNESS.parsePotionEffectType(), 5 * 20, 1));
				Setup1Language.OpenInventory(p);
			}
		}
	}

	@EventHandler
	public void onInventory(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();

		if (needsetup) {
			if (p.hasPermission("hawn.setup")) {
				if (setupplace == 1) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Setup1Language.OpenInventory(p), 5);
				} else if (setupplace == 2) {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Setup2World.OpenInventory(p), 5);
				}
			}
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
