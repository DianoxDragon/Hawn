package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

/**
 * Marks the items given by Hawn (join items and special items).
 * A renamed item from an anvil has the same name, but not this tag.
 */
public final class JoinItemTag {

	public static final String FUNGUN = "special-fungun";
	public static final String LOBBYBOW = "special-lobbybow";
	public static final String HIDEPLAYERS = "special-hideplayers";

	private static NamespacedKey key;

	private JoinItemTag() {}

	private static NamespacedKey key() {
		if (key == null) {
			key = new NamespacedKey(Main.getInstance(), "join_item");
		}
		return key;
	}

	public static void tag(ItemStack item, String id) {
		if (item == null) {
			return;
		}

		ItemMeta meta = item.getItemMeta();
		if (meta == null) {
			return;
		}

		meta.getPersistentDataContainer().set(key(), PersistentDataType.STRING, id);
		item.setItemMeta(meta);
	}

	public static boolean is(ItemStack item, String id) {
		if (item == null || id == null || !item.hasItemMeta()) {
			return false;
		}

		return id.equals(item.getItemMeta().getPersistentDataContainer().get(key(), PersistentDataType.STRING));
	}

}
