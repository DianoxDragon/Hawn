package fr.dianox.hawn.hook;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.utility.Debug;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

/**
 * The items of other plugins in the join items and the menus (since 1.5): Material: nexo:&lt;id&gt;.
 * Nexo (Paper 1.21.4+) is reached through fr.dianox.hawn.paper.NexoHook, loaded only when Nexo is enabled.
 */
public final class CustomItems {

	private static final String NEXO = "nexo:";
	private static final String HOOK = "fr.dianox.hawn.paper.NexoHook";

	private static boolean nexo;
	private static volatile boolean nexoLoaded;

	private CustomItems() {}

	/**
	 * At startup and /hawn reload: the hook of Nexo is registered once, when Nexo is there.
	 */
	public static void load() {
		if (nexo || !Bukkit.getPluginManager().isPluginEnabled("Nexo")) {
			return;
		}

		try {
			Class.forName(HOOK).getMethod("register", org.bukkit.plugin.Plugin.class).invoke(null, Main.getInstance());
			nexo = true;
			// Already loaded when Hawn starts after Nexo, or at /hawn reload
			nexoLoaded = (Boolean) Class.forName(HOOK).getMethod("loaded").invoke(null);
		} catch (Throwable t) {
			Main.getInstance().getLogger().warning("Nexo is installed, but its items can't be used in Hawn: " + t);
		}
	}

	public static boolean nexo() {
		return nexo;
	}

	public static boolean isCustom(String material) {
		return material != null && material.toLowerCase(Locale.ROOT).startsWith(NEXO);
	}

	/**
	 * nexo:&lt;id&gt; exists (false without Nexo). For /hawn debug.
	 */
	public static boolean exists(String material) {
		if (!nexo || !isCustom(material)) {
			return false;
		}

		try {
			return (Boolean) Class.forName(HOOK).getMethod("exists", String.class).invoke(null, id(material));
		} catch (Throwable t) {
			return false;
		}
	}

	/**
	 * Why nexo:&lt;id&gt; can't be given, null when it can (for /hawn debug).
	 */
	public static String problem(String material) {
		if (!nexo) {
			return "Nexo is not installed";
		}
		if (nexoLoaded && !exists(material)) {
			return "no item " + id(material) + " in Nexo";
		}
		return null;
	}

	/**
	 * The item of the other plugin, or a barrier (with a warning, once) when it can't be given.
	 */
	public static ItemStack create(String material, int amount, String path) {
		ItemStack item = null;

		if (nexo) {
			try {
				item = (ItemStack) Class.forName(HOOK).getMethod("item", String.class).invoke(null, id(material));
			} catch (Throwable t) {
				Debug.hidden("Nexo item " + material, t);
			}
		}

		if (item == null) {
			if (Debug.firstTime("custom item", material)) {
				Bukkit.getConsoleSender().sendMessage("§c[Hawn] " + path + ": " + material + " can't be given ("
						+ (nexo ? (nexoLoaded ? "no such item in Nexo" : "Nexo has not loaded its items yet") : "Nexo is not installed") + ")");
			}
			return new ItemStack(Material.BARRIER, Math.max(1, amount));
		}

		item.setAmount(Math.max(1, amount));
		return item;
	}

	/**
	 * Nexo loaded its items (startup or /nexo reload): the join items with an item of Nexo are given again.
	 */
	public static void nexoLoaded() {
		boolean first = !nexoLoaded;
		nexoLoaded = true;
		if (first && CustomJoinItem.usesCustomItems()) {
			for (Player p : Bukkit.getOnlinePlayers()) {
				CustomJoinItem.onItemGive(p);
			}
		}
	}

	private static String id(String material) {
		return material.substring(NEXO.length()).trim();
	}
}
