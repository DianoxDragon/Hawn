package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.hook.HooksManager;
import com.cryptomorin.xseries.XMaterial;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.JoinItemTag;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiGrappling;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Location;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The grappling hook (since 1.5.5): a fishing rod thrown on a block pulls the player to it when reeled in. With a
 * cooldown, no fall damage after the pull, and only in the worlds of its join item, within Max-Distance blocks.
 */
public class SpecialIteGrappling implements Listener {

	private static final List<Player> Cooling = new CopyOnWriteArrayList<>();
	// Until when a pulled player takes no fall damage
	private static final Map<UUID, Long> noFall = new ConcurrentHashMap<>();
	private static final long NO_FALL_MILLIS = 10000;

	@EventHandler
	public void onCLickInventory(InventoryClickEvent e) {

		if (!SpecialCjiGrappling.getConfig().getBoolean("Grappling.Enable")) {
			return;
		}

		Player p = (Player) e.getWhoClicked();

		// Check Worlds (the Worlds: of its join item, else General-Option.World)
		String joinItem = CustomJoinItem.special(p, "Special-Grappling");
		if (joinItem == null || !p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
				&& !p.hasPermission("hawn.use.cji.item." + joinItem)) {
			return;
		}

		if (e.getSlotType() == SlotType.OUTSIDE) return;

		if (JoinItemTag.is(e.getCurrentItem(), JoinItemTag.GRAPPLING)) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onFish(PlayerFishEvent e) {
		Player p = e.getPlayer();

		if (!SpecialCjiGrappling.getConfig().getBoolean("Grappling.Enable") || !holding(p)) {
			return;
		}

		// Check Worlds (the Worlds: of its join item, else General-Option.World)
		String joinItem = CustomJoinItem.special(p, "Special-Grappling");
		if (joinItem == null || !p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
				&& !p.hasPermission("hawn.use.cji.item." + joinItem)) {
			return;
		}

		FishHook hook = e.getHook();
		String state = e.getState().name();

		// The hook does not pull the other players (nor the mobs) to the player
		if (state.equals("CAUGHT_ENTITY")) {
			e.setCancelled(true);
			hook.remove();
			return;
		}

		if (!state.equals("IN_GROUND") && !((state.equals("REEL_IN") || state.equals("FAILED_ATTEMPT")) && onBlock(hook.getLocation()))) {
			return;
		}

		Location from = p.getLocation();
		Location to = hook.getLocation();
		if (!from.getWorld().equals(to.getWorld())) {
			return;
		}

		double distance = to.distance(from);
		if (distance > SpecialCjiGrappling.getConfig().getDouble("Grappling.Option.Max-Distance")) {
			messages(p, "Custom-Join-Item.Error.Grappling.Too-Far");
			return;
		}

		if (SpecialCjiGrappling.getConfig().getBoolean("Grappling.Option.Item-Delay.Enable")) {
			if (Cooling.contains(p)) {
				messages(p, "Custom-Join-Item.Error.Grappling.Time");
				return;
			}

			Cooling.add(p);
			Tasks.later(() -> Cooling.remove(p), SpecialCjiGrappling.getConfig().getInt("Grappling.Option.Item-Delay.Delay") * 20L);
			Main.grapplingCooldowns.put(p, System.currentTimeMillis());
		}

		pull(p, from, to);
	}

	// No fall damage just after a pull
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
		Cooling.remove(e.getPlayer());
		noFall.remove(e.getPlayer().getUniqueId());
	}

	/**
	 * Throws the player to the hook, on a curve that lands near it (the gravity of a player is 0.08 per tick).
	 */
	private static void pull(Player p, Location from, Location to) {
		Vector gap = to.toVector().subtract(from.toVector());
		double t = gap.length();
		if (t < 1) {
			return;
		}

		double force = SpecialCjiGrappling.getConfig().getDouble("Grappling.Option.Force", 1.0);
		Vector velocity = new Vector(
				(1.0 + 0.07 * t) * gap.getX() / t,
				(1.0 + 0.03 * t) * gap.getY() / t + 0.04 * t,
				(1.0 + 0.07 * t) * gap.getZ() / t).multiply(force);

		// The server refuses a speed above 4 blocks per tick on an axis
		velocity.setX(Math.max(-4, Math.min(4, velocity.getX())));
		velocity.setY(Math.max(-4, Math.min(4, velocity.getY())));
		velocity.setZ(Math.max(-4, Math.min(4, velocity.getZ())));

		p.setFallDistance(0);
		p.setVelocity(velocity);

		if (SpecialCjiGrappling.getConfig().getBoolean("Grappling.Option.No-Fall-Damage")) {
			noFall.put(p.getUniqueId(), System.currentTimeMillis() + NO_FALL_MILLIS);
		}

		if (SpecialCjiGrappling.getConfig().getBoolean("Grappling.Sound.Enable")) {
			String sound = SpecialCjiGrappling.getConfig().getString("Grappling.Sound.Sound");
			float volume = (float) SpecialCjiGrappling.getConfig().getDouble("Grappling.Sound.Volume");
			float pitch = (float) SpecialCjiGrappling.getConfig().getDouble("Grappling.Sound.Pitch");
			p.playSound(p.getLocation(), XParse.sound(sound, "Grappling.Sound.Sound"), volume, pitch);
		}
	}

	// The rod in use is the grappling hook: the main hand first, as Minecraft does
	private static boolean holding(Player p) {
		ItemStack main = p.getInventory().getItemInMainHand();
		if (main.getType() == XMaterial.FISHING_ROD.parseMaterial()) {
			return JoinItemTag.is(main, JoinItemTag.GRAPPLING);
		}
		return JoinItemTag.is(p.getInventory().getItemInOffHand(), JoinItemTag.GRAPPLING);
	}

	// A reeled hook touches a block: it lies on the ground or against a wall
	private static boolean onBlock(Location hook) {
		for (int x = -1; x <= 1; x++) {
			for (int y = -1; y <= 1; y++) {
				for (int z = -1; z <= 1; z++) {
					if (hook.clone().add(x * 0.3, y * 0.3, z * 0.3).getBlock().getType().isSolid()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private static void messages(Player p, String path) {
		if (ConfigMMsg.getConfig().getBoolean(path + ".Enable")) {
			for (String msg : ConfigMMsg.getConfig().getStringList(path + ".Messages")) {
				ConfigEventUtils.ExecuteEvent(p, msg, path + ".Messages", "SpecialIteGrappling", false);
			}
		}
	}

	public static void PlayerGiveGrappling(Player p, Integer slot) {
		if (!SpecialCjiGrappling.getConfig().getBoolean("Grappling.Enable")) {
			return;
		}

		// Check Worlds (the Worlds: of its join item, else General-Option.World)
		String joinItem = CustomJoinItem.special(p, "Special-Grappling");
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
		int amount = SpecialCjiGrappling.getConfig().getInt("Grappling.Item.Material.Amount", 1);
		ItemStack item = new ItemStack(XMaterial.FISHING_ROD.parseMaterial(), amount);
		ItemMeta itemmeta = item.getItemMeta();

		if (SpecialCjiGrappling.getConfig().isSet("Grappling.Item.Title")) {
			String title = SpecialCjiGrappling.getConfig().getString("Grappling.Item.Title");

			if (HooksManager.papi()) {
				title = PlaceholderAPI.setPlaceholders(p, title);
			}

			title = PlaceHolders.ReplaceMainplaceholderP(title, p);
			itemmeta.setDisplayName(MessageUtils.colourTheStuff(title));
		}

		if (SpecialCjiGrappling.getConfig().isSet("Grappling.Item.Lore")) {
			List<String> lore = new ArrayList<>();
			for (String loremsg : SpecialCjiGrappling.getConfig().getStringList("Grappling.Item.Lore")) {
				if (HooksManager.papi()) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
				}

				loremsg = PlaceHolders.ReplaceMainplaceholderP(loremsg, p);
				lore.add(MessageUtils.colourTheStuff(loremsg));
			}
			itemmeta.setLore(lore);
		}

		// The rod does not wear out
		itemmeta.setUnbreakable(true);
		item.setItemMeta(itemmeta);

		JoinItemTag.tag(item, JoinItemTag.GRAPPLING);
		p.getInventory().setItem(slot, item);
	}

}
