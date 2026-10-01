package fr.dianox.hawn.utility.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

/**
 * Marks the inventories created by Hawn (menus): players can never take, put or drag items in them,
 * whatever the title is. The click actions themselves are still handled by the menus' listeners.
 */
public final class HawnMenu implements InventoryHolder {

	private Inventory inventory;

	public static Inventory create(int size, String title) {
		HawnMenu holder = new HawnMenu();
		holder.inventory = Bukkit.createInventory(holder, size, title);
		return holder.inventory;
	}

	@Override
	public Inventory getInventory() {
		return inventory;
	}

	public static boolean isMenu(Inventory inventory) {
		return inventory != null && inventory.getHolder() instanceof HawnMenu;
	}

	public static final class Protection implements Listener {

		@EventHandler(priority = EventPriority.LOWEST)
		public void onClick(InventoryClickEvent e) {
			if (isMenu(e.getView().getTopInventory())) {
				e.setCancelled(true);
			}
		}

		@EventHandler(priority = EventPriority.LOWEST)
		public void onDrag(InventoryDragEvent e) {
			if (isMenu(e.getView().getTopInventory())) {
				e.setCancelled(true);
			}
		}
	}
}
