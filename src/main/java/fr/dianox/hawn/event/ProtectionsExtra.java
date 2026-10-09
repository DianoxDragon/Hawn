package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.utility.config.configs.events.ConfigGProtection;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * The protections added in 1.4 (Events/ProtectionWorld.yml): armor stands, item frames and paintings placed,
 * blocks broken by explosions, liquids that flow, farmland trampled.
 * Same options as the other protections: Enable, Bypass, WorldGuard, World.
 */
public class ProtectionsExtra implements Listener {

	private static YamlConfiguration cfg() {
		return ConfigGProtection.getConfig();
	}

	/**
	 * @param path       "Protection.Armor-Stand." for example
	 * @param where      where it happens (world and WorldGuard regions)
	 * @param p          the player who does it, null when it is not a player (no bypass then)
	 * @param permission the bypass permission
	 * @return true when the protection applies here
	 */
	public static boolean protects(String path, Location where, Player p, String permission) {
		if (!cfg().getBoolean(path + "Enable") || where == null || where.getWorld() == null) {
			return false;
		}

		if (!cfg().getBoolean(path + "World.All_World")) {
			boolean listed = false;
			for (String world : cfg().getStringList(path + "World.Worlds")) {
				if (world.equalsIgnoreCase(where.getWorld().getName())) {
					listed = true;
					break;
				}
			}
			if (!listed) {
				return false;
			}
		}

		if (p != null) {
			if (Main.buildbypasscommand.contains(p)) {
				return false;
			}
			if (cfg().getBoolean(path + "Bypass") && p.hasPermission(permission)) {
				return false;
			}
		}

		if (cfg().getBoolean(path + "WorldGuard.Enable") && HooksManager.worldGuard()) {
			boolean inRegion = false;
			for (String region : cfg().getStringList(path + "WorldGuard.Regions")) {
				if (Main.getInstance().getHooksManager().getWg().isInRegion(where, region)) {
					inRegion = true;
					break;
				}
			}
			// WHITELIST: only in these regions. BLACKLIST: everywhere but these regions.
			return "BLACKLIST".equalsIgnoreCase(cfg().getString(path + "WorldGuard.Method")) != inRegion;
		}

		return true;
	}

	/* ------------------------------------------------------------------ armor stands */

	@EventHandler(ignoreCancelled = true)
	public void onArmorStand(PlayerArmorStandManipulateEvent e) {
		if (protects("Protection.Armor-Stand.", e.getRightClicked().getLocation(), e.getPlayer(), "hawn.bypass.armorstand")) {
			e.setCancelled(true);
		}
	}

	// Hit by a player, or by an arrow of a player
	@EventHandler(ignoreCancelled = true)
	public void onArmorStandHit(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof ArmorStand)) {
			return;
		}

		Player p = null;
		if (e.getDamager() instanceof Player) {
			p = (Player) e.getDamager();
		} else if (e.getDamager() instanceof Projectile && ((Projectile) e.getDamager()).getShooter() instanceof Player) {
			p = (Player) ((Projectile) e.getDamager()).getShooter();
		}

		if (p != null && protects("Protection.Armor-Stand.", e.getEntity().getLocation(), p, "hawn.bypass.armorstand")) {
			e.setCancelled(true);
		}
	}

	/* ------------------------------------------------------------------ item frames and paintings */

	@EventHandler(ignoreCancelled = true)
	public void onHangingPlace(HangingPlaceEvent e) {
		if (protects("Protection.Hanging-Place.", e.getEntity().getLocation(), e.getPlayer(), "hawn.bypass.hangingplace")) {
			e.setCancelled(true);
		}
	}

	/* ------------------------------------------------------------------ explosions */

	// The explosion still happens (damage, knockback), only the blocks stay
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent e) {
		if (protects("Protection.Explosion-Blocks.", e.getLocation(), null, null)) {
			e.blockList().clear();
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockExplode(BlockExplodeEvent e) {
		if (protects("Protection.Explosion-Blocks.", e.getBlock().getLocation(), null, null)) {
			e.blockList().clear();
		}
	}

	/* ------------------------------------------------------------------ liquids */

	@EventHandler(ignoreCancelled = true)
	public void onFlow(BlockFromToEvent e) {
		Material type = e.getBlock().getType();
		if ((type == Material.WATER || type == Material.LAVA) && protects("Protection.Liquid-Flow.", e.getToBlock().getLocation(), null, null)) {
			e.setCancelled(true);
		}
	}

	/* ------------------------------------------------------------------ farmland */

	@EventHandler(ignoreCancelled = true)
	public void onTrample(PlayerInteractEvent e) {
		if (e.getAction() == Action.PHYSICAL && e.getClickedBlock() != null && e.getClickedBlock().getType() == Material.FARMLAND
				&& protects("Protection.Anti-Trample.", e.getClickedBlock().getLocation(), e.getPlayer(), "hawn.bypass.trample")) {
			e.setCancelled(true);
		}
	}

	// The mobs too
	@EventHandler(ignoreCancelled = true)
	public void onTrampleByMob(EntityInteractEvent e) {
		if (e.getBlock().getType() == Material.FARMLAND && protects("Protection.Anti-Trample.", e.getBlock().getLocation(), null, null)) {
			e.setCancelled(true);
		}
	}
}
