package fr.dianox.hawn.utility.gui;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

/**
 * Marks the inventories created by Hawn (menus): players can never take, put or drag items in them,
 * whatever the title is. The click actions themselves are still handled by the menus' listeners.
 * <p>
 * Since 1.4.8 (88), each menu also has its type: the listeners recognise their menus by it, never by the title
 * (a chest or the menu of another plugin with the same title does nothing). The base of the menus of 1.5.
 */
public final class HawnMenu implements InventoryHolder {

	public enum Type {
		EMOJIS,
		ADMIN_PANEL, ADMIN_PANEL_EDIT, ADMIN_PANEL_FOLDER, ADMIN_PANEL_FILE,
		EDIT_PLAYER, PLAYER_LIST,
		SETUP_LANGUAGE, SETUP_WORLD, SETUP_SPAWN,
		WORLD_LIST, WORLD_CREATE, WORLD_DELETE, WORLD_SETTINGS, WORLD_TIME, WORLD_WEATHER, WORLD_DIFFICULTY;

		public boolean isWorldManager() {
			return name().startsWith("WORLD_");
		}

		public boolean isSetup() {
			return name().startsWith("SETUP_");
		}

		public boolean isAdminPanel() {
			return name().startsWith("ADMIN_PANEL");
		}
	}

	private Inventory inventory;
	private Type type;
	private int page;

	public static Inventory create(int size, String title, Type type) {
		return create(size, title, type, 0);
	}

	/**
	 * @param page the page of a menu with several pages (the world list), 0 for the first one
	 */
	public static Inventory create(int size, String title, Type type, int page) {
		HawnMenu holder = new HawnMenu();
		holder.type = type;
		holder.page = page;
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

	/**
	 * The type of a menu of Hawn, null for any other inventory.
	 */
	public static Type typeOf(Inventory inventory) {
		return isMenu(inventory) ? ((HawnMenu) inventory.getHolder()).type : null;
	}

	public static Type typeOf(InventoryView view) {
		return view == null ? null : typeOf(view.getTopInventory());
	}

	public static int pageOf(Inventory inventory) {
		return isMenu(inventory) ? ((HawnMenu) inventory.getHolder()).page : 0;
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
