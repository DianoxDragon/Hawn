package fr.dianox.hawn.modules.menus;

import fr.dianox.hawn.utility.Debug;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import java.lang.reflect.Method;

/**
 * The title of an open menu changed without opening it again: InventoryView#setTitle (Bukkit 1.20+), by reflection
 * (Hawn is compiled against 1.16.5). Before 1.20, an animated title keeps its first frame.
 */
final class Titles {

	private static final Method SET_TITLE = method();

	private Titles() {}

	private static Method method() {
		try {
			return InventoryView.class.getMethod("setTitle", String.class);
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	static boolean available() {
		return SET_TITLE != null;
	}

	static boolean set(Player p, String title) {
		if (SET_TITLE == null) {
			return false;
		}
		try {
			SET_TITLE.invoke(p.getOpenInventory(), title);
			return true;
		} catch (Exception e) {
			Debug.hidden("Menus title", e);
			return false;
		}
	}
}
