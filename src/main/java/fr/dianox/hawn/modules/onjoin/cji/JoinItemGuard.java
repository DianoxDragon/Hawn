package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.JoinItemTag;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Since 1.6.1: the join items stay with their player. They no longer fall on the ground when the player dies (another
 * player could pick them up), and they can't be put in a chest, a furnace or any other inventory.
 */
public class JoinItemGuard implements Listener {

	private static boolean on() {
		return ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable");
	}

	private static boolean free(HumanEntity who) {
		return who instanceof Player && Main.buildbypasscommand.contains(who);
	}

	// The inventory of the player alone (and its crafting grid): nothing can leave it there
	private static boolean ownInventory(Inventory top) {
		InventoryType type = top.getType();
		return type == InventoryType.CRAFTING || type == InventoryType.CREATIVE || type == InventoryType.PLAYER;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onDeath(PlayerDeathEvent e) {
		if (!on() || e.getKeepInventory()) return;
		e.getDrops().removeIf(JoinItemTag::isAny);
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onClick(InventoryClickEvent e) {
		if (!on() || free(e.getWhoClicked())) return;
		Inventory top = e.getView().getTopInventory();
		if (ownInventory(top) || e.getClickedInventory() == null) return;

		boolean inTop = e.getClickedInventory().equals(top);
		ItemStack moved = null;

		if (!inTop && e.isShiftClick()) {
			// From the inventory of the player to the other one
			moved = e.getCurrentItem();
		} else if (inTop && e.getClick() == ClickType.NUMBER_KEY && e.getHotbarButton() >= 0) {
			// The item of the hotbar goes in the slot
			moved = e.getWhoClicked().getInventory().getItem(e.getHotbarButton());
		} else if (inTop && e.getClick().name().equals("SWAP_OFFHAND")) {
			// F on a slot (1.16+): the item of the off hand goes in it
			moved = e.getWhoClicked().getInventory().getItemInOffHand();
		} else if (inTop) {
			// The item of the cursor put in the slot
			moved = e.getCursor();
		}

		if (moved != null && JoinItemTag.isAny(moved)) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onDrag(InventoryDragEvent e) {
		if (!on() || free(e.getWhoClicked())) return;
		Inventory top = e.getView().getTopInventory();
		if (ownInventory(top) || !JoinItemTag.isAny(e.getOldCursor())) return;

		for (int slot : e.getRawSlots()) {
			if (slot < top.getSize()) {
				e.setCancelled(true);
				return;
			}
		}
	}
}
