package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.hook.HooksManager;
import com.cryptomorin.xseries.XMaterial;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.JoinItemTag;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiPearlMount;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The pearl mount (since 1.5.6): a right click throws an ender pearl. With Ride, the player rides it (a right click again
 * or sneaking gets them off); without, they are teleported where it lands. The item is never used up, the pearl doesn't
 * hurt nor spawn endermites, and with Steer a ridden one turns towards where the player looks. A flight stops after Max-Seconds or
 * Max-Distance blocks, or out of the Height range (a thrown pearl then goes away without teleporting). With a cooldown,
 * a particle trail, no fall damage after the flight, and only in the worlds of its join item.
 */
public class SpecialItePearlMount implements Listener {

	private static final List<Player> Cooling = new CopyOnWriteArrayList<>();
	// Until when a player who flew takes no fall damage
	private static final Map<UUID, Long> noFall = new ConcurrentHashMap<>();
	private static final long NO_FALL_MILLIS = 10000;
	private static final Map<UUID, Flight> flights = new ConcurrentHashMap<>();
	// The players getting on their pearl right now
	private static final java.util.Set<UUID> mounting = ConcurrentHashMap.newKeySet();

	// One flight of a pearl, ridden or not: each tick until it stops
	private static final class Flight implements Runnable {
		private final Player p;
		private final EnderPearl pearl;
		private final boolean riding;
		private final Location start;
		private final long until;
		private final double speed;
		private BukkitTask task;

		private Flight(Player p, EnderPearl pearl, boolean riding, Location start, long until, double speed) {
			this.p = p;
			this.pearl = pearl;
			this.riding = riding;
			this.start = start;
			this.until = until;
			this.speed = speed;
		}

		@Override
		public void run() {
			double max = SpecialCjiPearlMount.getConfig().getDouble("PearlMount.Option.Max-Distance");
			if (!p.isOnline() || !pearl.isValid() || (riding && !pearl.getPassengers().contains(p))
					|| !pearl.getWorld().equals(start.getWorld()) || pearl.getLocation().distanceSquared(start) > max * max
					|| System.currentTimeMillis() > until || !inHeight(pearl.getLocation())) {
				stop(p);
				return;
			}

			// The ridden pearl turns towards where the player looks: 1 follows the look at once, 0 never
			if (riding && SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Option.Steer.Enable")) {
				double strength = Math.max(0, Math.min(1, SpecialCjiPearlMount.getConfig().getDouble("PearlMount.Option.Steer.Strength", 0.2)));
				Vector look = p.getLocation().getDirection().multiply(speed * strength);
				pearl.setVelocity(pearl.getVelocity().multiply(1 - strength).add(look));
			}

			if (SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Trail.Enable")) {
				ParticleUtils.spawn(pearl.getLocation(), SpecialCjiPearlMount.getConfig().getConfigurationSection("PearlMount.Trail"), "PearlMount.Trail");
			}
		}
	}

	@EventHandler
	public void onCLickInventory(InventoryClickEvent e) {

		if (!SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Enable")) {
			return;
		}

		Player p = (Player) e.getWhoClicked();

		// Check Worlds (the Worlds: of its join item, else General-Option.World)
		String joinItem = CustomJoinItem.special(p, "Special-PearlMount");
		if (joinItem == null || !p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
				&& !p.hasPermission("hawn.use.cji.item." + joinItem)) {
			return;
		}

		if (e.getSlotType() == SlotType.OUTSIDE) return;

		if (JoinItemTag.is(e.getCurrentItem(), JoinItemTag.PEARLMOUNT)) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onInteract(PlayerInteractEvent e) {
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}

		if (!JoinItemTag.is(e.getItem(), JoinItemTag.PEARLMOUNT)) {
			return;
		}

		// Never a pearl of Minecraft: the item is not used up
		e.setCancelled(true);

		Player p = e.getPlayer();
		Flight flight = flights.get(p.getUniqueId());
		if (flight != null) {
			// A right click again gets off the pearl
			if (flight.riding) {
				stop(p);
			}
			return;
		}

		if (!SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Enable") || p.isInsideVehicle()) {
			return;
		}

		// Check Worlds (the Worlds: of its join item, else General-Option.World)
		String joinItem = CustomJoinItem.special(p, "Special-PearlMount");
		if (joinItem == null || !p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
				&& !p.hasPermission("hawn.use.cji.item." + joinItem)) {
			return;
		}

		if (!inHeight(p.getLocation())) {
			messages(p, "Custom-Join-Item.Error.PearlMount.Height");
			return;
		}

		if (SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Option.Item-Delay.Enable")) {
			if (Cooling.contains(p)) {
				messages(p, "Custom-Join-Item.Error.PearlMount.Time");
				return;
			}

			Cooling.add(p);
			Tasks.later(() -> Cooling.remove(p), SpecialCjiPearlMount.getConfig().getInt("PearlMount.Option.Item-Delay.Delay") * 20L);
			Main.pearlMountCooldowns.put(p, System.currentTimeMillis());
		}

		launch(p);
	}

	// The pearl lands: a rider gets off there, a thrower is teleported there by Hawn (no damage, no endermite)
	@EventHandler(priority = EventPriority.HIGH)
	public void onPearlHit(ProjectileHitEvent e) {
		if (!(e.getEntity() instanceof EnderPearl) || !(e.getEntity().getShooter() instanceof Player)) {
			return;
		}

		Player p = (Player) e.getEntity().getShooter();
		Flight flight = flights.get(p.getUniqueId());
		if (flight == null || flight.pearl != e.getEntity()) {
			return;
		}

		Location to = flight.pearl.getLocation();
		stop(p);
		if (!flight.riding && p.isOnline()) {
			to.setYaw(p.getLocation().getYaw());
			to.setPitch(p.getLocation().getPitch());
			Tasks.teleportAsync(p, to);
		}
	}

	// The pearl of a flight never teleports by itself (nor hurts): Hawn does it when it lands
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onPearlTeleport(PlayerTeleportEvent e) {
		if (e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && flights.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

	// No fall damage just after a ride
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onFall(EntityDamageEvent e) {
		if (e.getCause() != EntityDamageEvent.DamageCause.FALL || !(e.getEntity() instanceof Player)) {
			return;
		}

		Long until = noFall.remove(e.getEntity().getUniqueId());
		if (until != null && until > System.currentTimeMillis()) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		stop(e.getPlayer());
		Cooling.remove(e.getPlayer());
		noFall.remove(e.getPlayer().getUniqueId());
	}

	private static void launch(Player p) {
		double speed = SpecialCjiPearlMount.getConfig().getDouble("PearlMount.Option.Speed", 1.5);
		boolean riding = SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Option.Ride", true);
		EnderPearl pearl = p.launchProjectile(EnderPearl.class, p.getLocation().getDirection().multiply(speed));

		long until = System.currentTimeMillis() + SpecialCjiPearlMount.getConfig().getInt("PearlMount.Option.Max-Seconds", 8) * 1000L;
		Flight flight = new Flight(p, pearl, riding, p.getLocation(), until, speed);
		flights.put(p.getUniqueId(), flight);

		if (riding) {
			// Block-Mount of PlayerEvents.yml lets this one through
			mounting.add(p.getUniqueId());
			try {
				pearl.addPassenger(p);
			} finally {
				mounting.remove(p.getUniqueId());
			}
		}

		flight.task = Tasks.timer(flight, 1, 1);

		if (SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Sound.Enable")) {
			String sound = SpecialCjiPearlMount.getConfig().getString("PearlMount.Sound.Sound");
			float volume = (float) SpecialCjiPearlMount.getConfig().getDouble("PearlMount.Sound.Volume");
			float pitch = (float) SpecialCjiPearlMount.getConfig().getDouble("PearlMount.Sound.Pitch");
			p.playSound(p.getLocation(), XParse.sound(sound, "PearlMount.Sound.Sound"), volume, pitch);
		}
	}

	/**
	 * The end of a flight: a rider gets off where the pearl is, and the pearl goes away (it would teleport them later).
	 */
	public static void stop(Player p) {
		Flight flight = flights.remove(p.getUniqueId());
		if (flight == null) {
			return;
		}

		Tasks.cancel(flight.task);
		if (flight.riding) {
			flight.pearl.removePassenger(p);
		}
		flight.pearl.remove();

		if (p.isOnline()) {
			p.setFallDistance(0);
			if (SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Option.No-Fall-Damage")) {
				noFall.put(p.getUniqueId(), System.currentTimeMillis() + NO_FALL_MILLIS);
			}
		}
	}

	/**
	 * The player is getting on the pearl of a ride: not a mount that Block-Mount stops.
	 */
	public static boolean isMounting(Player p) {
		return mounting.contains(p.getUniqueId());
	}

	/**
	 * Every flight stops (plugin disabled).
	 */
	public static void stopAll() {
		for (Flight flight : flights.values()) {
			stop(flight.p);
		}
	}

	// Between Height.Min-Y and Height.Max-Y, when Height is on
	private static boolean inHeight(Location loc) {
		if (!SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Option.Height.Enable")) {
			return true;
		}
		return loc.getY() >= SpecialCjiPearlMount.getConfig().getDouble("PearlMount.Option.Height.Min-Y")
				&& loc.getY() <= SpecialCjiPearlMount.getConfig().getDouble("PearlMount.Option.Height.Max-Y");
	}

	private static void messages(Player p, String path) {
		if (ConfigMMsg.getConfig().getBoolean(path + ".Enable")) {
			for (String msg : ConfigMMsg.getConfig().getStringList(path + ".Messages")) {
				msg = msg.replace("%arg1%", SpecialCjiPearlMount.getConfig().getString("PearlMount.Option.Height.Min-Y"))
						.replace("%arg2%", SpecialCjiPearlMount.getConfig().getString("PearlMount.Option.Height.Max-Y"));
				ConfigEventUtils.ExecuteEvent(p, msg, path + ".Messages", "SpecialItePearlMount", false);
			}
		}
	}

	public static void PlayerGivePearlMount(Player p, Integer slot) {
		if (!SpecialCjiPearlMount.getConfig().getBoolean("PearlMount.Enable")) {
			return;
		}

		// Check Worlds (the Worlds: of its join item, else General-Option.World)
		String joinItem = CustomJoinItem.special(p, "Special-PearlMount");
		if (joinItem == null || !p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
				&& !p.hasPermission("hawn.use.cji.item." + joinItem)) {
			return;
		}

		CreateItem(p, slot);
	}

	public static void CreateItem(Player p, Integer slot) {
		int amount = SpecialCjiPearlMount.getConfig().getInt("PearlMount.Item.Material.Amount", 1);
		ItemStack item = new ItemStack(XMaterial.ENDER_PEARL.parseMaterial(), amount);
		ItemMeta itemmeta = item.getItemMeta();

		if (SpecialCjiPearlMount.getConfig().isSet("PearlMount.Item.Title")) {
			String title = SpecialCjiPearlMount.getConfig().getString("PearlMount.Item.Title");

			if (HooksManager.papi()) {
				title = PlaceholderAPI.setPlaceholders(p, title);
			}

			title = PlaceHolders.ReplaceMainplaceholderP(title, p);
			itemmeta.setDisplayName(MessageUtils.colourTheStuff(title));
		}

		if (SpecialCjiPearlMount.getConfig().isSet("PearlMount.Item.Lore")) {
			List<String> lore = new ArrayList<>();
			for (String loremsg : SpecialCjiPearlMount.getConfig().getStringList("PearlMount.Item.Lore")) {
				if (HooksManager.papi()) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
				}

				loremsg = PlaceHolders.ReplaceMainplaceholderP(loremsg, p);
				lore.add(MessageUtils.colourTheStuff(loremsg));
			}
			itemmeta.setLore(lore);
		}

		item.setItemMeta(itemmeta);

		JoinItemTag.tag(item, JoinItemTag.PEARLMOUNT);
		p.getInventory().setItem(slot, item);
	}

}
