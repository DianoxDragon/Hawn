package fr.dianox.hawn.paper;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.api.events.NexoItemsLoadedEvent;
import com.nexomc.nexo.items.ItemBuilder;
import com.nexomc.nexo.items.UpdateCallback;
import fr.dianox.hawn.hook.CustomItems;
import fr.dianox.hawn.utility.JoinItemTag;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

/**
 * The items of Nexo in the join items and the menus (Material: nexo:&lt;id&gt;). Loaded by reflection from
 * fr.dianox.hawn.hook.CustomItems, only when Nexo is enabled: Nexo needs Paper 1.21.4+.
 */
public final class NexoHook implements Listener {

	private static final Key CALLBACK = Key.key("hawn", "join_items");

	private NexoHook() {}

	public static void register(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new NexoHook(), plugin);
		keepHawnItems();
	}

	/**
	 * Nexo updates its items in the inventories (name, lore, components): the items given by Hawn keep what Hawn wrote.
	 */
	private static void keepHawnItems() {
		NexoItems.registerUpdateCallback(CALLBACK, new UpdateCallback() {
			@Override
			public ItemStack preUpdate(ItemStack item) {
				return JoinItemTag.isAny(item) ? null : item;
			}
		});
	}

	public static void unregister() {
		NexoItems.unregisterUpdateCallback(CALLBACK);
	}

	/**
	 * Nexo loads its items in the background, and again at /nexo reload.
	 */
	@EventHandler
	public void onLoaded(NexoItemsLoadedEvent e) {
		keepHawnItems();
		CustomItems.nexoLoaded();
	}

	/**
	 * Nexo already loaded its items (Hawn started after it, or /hawn reload).
	 */
	public static boolean loaded() {
		return !NexoItems.itemNames().isEmpty();
	}

	public static boolean exists(String id) {
		return NexoItems.exists(id);
	}

	/**
	 * A copy of the item of Nexo, null when it does not exist (yet).
	 */
	public static ItemStack item(String id) {
		ItemBuilder builder = NexoItems.itemFromId(id);
		return builder == null ? null : builder.build();
	}
}
