package fr.dianox.hawn.modules.menus;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.profiles.builder.XSkull;
import com.cryptomorin.xseries.profiles.objects.Profileable;
import fr.dianox.hawn.hook.CustomItems;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.utility.Debug;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.XParse;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * The items of the menus (and the looks of the join items): the material, the heads, the items of Nexo,
 * the name and the lore with the placeholders, Item-Model, Custom-Model-Data and Glow.
 */
public final class MenuItems {

	private static final Pattern NAME = Pattern.compile("^[A-Za-z0-9_]{1,16}$");
	private static final Pattern ID = Pattern.compile("^[0-9a-fA-F]{8}-?[0-9a-fA-F]{4}-?[0-9a-fA-F]{4}-?[0-9a-fA-F]{4}-?[0-9a-fA-F]{12}$");

	// The heads with a skin, prepared once: a texture (Base64, URL), or a player not connected, found in the background
	private static final Map<String, ItemStack> heads = new ConcurrentHashMap<>();
	private static final Set<String> loading = ConcurrentHashMap.newKeySet();

	private static Method itemModel;
	private static Method glint;
	private static boolean reflected;

	private MenuItems() {}

	public static String placeholders(Player p, String s) {
		if (s == null) return "";
		String r = PlaceHolders.ReplaceMainplaceholderP(s, p);
		if (HooksManager.papi()) {
			r = PlaceholderAPI.setPlaceholders(p, r);
		}
		return r;
	}

	public static String text(Player p, String s) {
		return MessageUtils.colourTheStuff(placeholders(p, s));
	}

	static ItemStack build(Player p, MenuItem item) {
		ItemStack stack = base(p, animate(item, item.material), item.amount, "Menus " + item.path);
		ItemMeta meta = stack.getItemMeta();
		if (meta == null) {
			return stack;
		}

		if (item.name != null) {
			meta.setDisplayName(text(p, animate(item, item.name)));
		}
		if (!item.lore.isEmpty()) {
			List<String> lore = new ArrayList<>();
			for (String line : item.lore) {
				lore.add(text(p, animate(item, line)));
			}
			meta.setLore(lore);
		}
		looks(meta, item.itemModel, item.customModelData, item.glow, "Menus " + item.path);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		stack.setItemMeta(meta);
		return stack;
	}

	private static String animate(MenuItem item, String s) {
		return item.menu == null ? s : item.menu.animate(s);
	}

	/**
	 * Material: a material, head:&lt;player, %player%, Base64, URL&gt;, or nexo:&lt;id&gt;.
	 */
	public static ItemStack base(Player p, String material, int amount, String path) {
		String m = placeholders(p, material).trim();

		if (m.toLowerCase(Locale.ROOT).startsWith("head:")) {
			ItemStack head = head(m.substring("head:".length()).trim());
			head.setAmount(amount);
			return head;
		}
		if (CustomItems.isCustom(m)) {
			return CustomItems.create(m, amount, path);
		}
		return new ItemStack(XParse.material(m, path + ".Material"), amount);
	}

	/**
	 * A connected player: their profile, already in memory. A texture: prepared once. A player not connected:
	 * found in the background (never on the main thread), a head without a skin in the meantime.
	 */
	@SuppressWarnings("deprecation")
	static ItemStack head(String value) {
		ItemStack base = XMaterial.PLAYER_HEAD.parseItem();
		if (value.isEmpty()) {
			return base;
		}

		// A connected player: the profile the server already has (with the skin)
		Player online = Bukkit.getPlayerExact(value);
		if (online != null) {
			org.bukkit.inventory.meta.SkullMeta meta = (org.bukkit.inventory.meta.SkullMeta) base.getItemMeta();
			meta.setOwningPlayer(online);
			base.setItemMeta(meta);
			return base;
		}

		ItemStack ready = heads.get(value);
		if (ready != null) {
			return ready.clone();
		}

		boolean name = NAME.matcher(value).matches();
		if (name || ID.matcher(value).matches()) {
			if (loading.add(value)) {
				lookup(value, name);
			}
			return base;
		}

		// A Base64 texture, a URL or the hash of a texture: no request to Mojang, prepared once
		try {
			ItemStack item = Profiles.available() ? Profiles.textured(base.clone(), value)
					: XSkull.of(base.clone()).profile(Profileable.detect(value)).lenient().apply();
			if (item != null) {
				heads.put(value, item);
				return item.clone();
			}
		} catch (Throwable t) {
			Debug.hidden("Menus head " + value, t);
		}
		return base;
	}

	/**
	 * A player not connected: the skin found in the background, then the open menus show it.
	 */
	private static void lookup(String value, boolean name) {
		java.util.concurrent.CompletableFuture<ItemStack> future;
		try {
			if (Profiles.available()) {
				future = Profiles.lookup(XMaterial.PLAYER_HEAD.parseItem(), name ? null : uuid(value), name ? value : null);
			} else {
				Profileable profile = name ? Profileable.username(value) : Profileable.of(uuid(value));
				future = XSkull.createItem().profile(profile).lenient().applyAsync();
			}
		} catch (Throwable t) {
			loading.remove(value);
			Debug.hidden("Menus head " + value, t);
			return;
		}

		future.whenComplete((item, error) -> {
			loading.remove(value);
			if (item != null) {
				heads.put(value, item);
				Tasks.sync(Menus::refreshOpen);
			} else if (error != null) {
				Debug.hidden("Menus head " + value, error);
			}
		});
	}

	private static UUID uuid(String value) {
		String v = value.replace("-", "");
		return UUID.fromString(v.substring(0, 8) + "-" + v.substring(8, 12) + "-" + v.substring(12, 16) + "-" + v.substring(16, 20) + "-" + v.substring(20));
	}

	/**
	 * Item-Model (1.21.4+, the model of a resource pack, Nexo included), Custom-Model-Data and Glow.
	 */
	public static void looks(ItemMeta meta, String model, Integer customModelData, boolean glow, String path) {
		reflect();

		if (customModelData != null) {
			meta.setCustomModelData(customModelData);
		}

		if (model != null && !model.trim().isEmpty()) {
			NamespacedKey key = key(model.trim());
			if (itemModel == null || key == null) {
				if (Debug.firstTime("Item-Model", path)) {
					Bukkit.getConsoleSender().sendMessage("§c[Hawn] " + path + ".Item-Model: " + model
							+ (itemModel == null ? " needs Minecraft 1.21.4 or newer" : " is not a namespace:key"));
				}
			} else {
				try {
					itemModel.invoke(meta, key);
				} catch (Exception e) {
					Debug.hidden("Item-Model " + path, e);
				}
			}
		}

		if (glow) {
			if (glint != null) {
				try {
					glint.invoke(meta, Boolean.TRUE);
					return;
				} catch (Exception e) {
					Debug.hidden("Glow " + path, e);
				}
			}
			// Before 1.20.5: an enchantment, hidden
			meta.addEnchant(com.cryptomorin.xseries.XEnchantment.UNBREAKING.getEnchant(), 1, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
	}

	@SuppressWarnings("deprecation")
	private static NamespacedKey key(String model) {
		int colon = model.indexOf(':');
		String namespace = colon < 0 ? "minecraft" : model.substring(0, colon);
		String value = colon < 0 ? model : model.substring(colon + 1);
		try {
			return new NamespacedKey(namespace.toLowerCase(Locale.ROOT), value.toLowerCase(Locale.ROOT));
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	private static synchronized void reflect() {
		if (reflected) return;
		reflected = true;
		try {
			itemModel = ItemMeta.class.getMethod("setItemModel", NamespacedKey.class);
		} catch (NoSuchMethodException ignored) {
			// Before 1.21.4
		}
		try {
			glint = ItemMeta.class.getMethod("setEnchantmentGlintOverride", Boolean.class);
		} catch (NoSuchMethodException ignored) {
			// Before 1.20.5
		}
	}
}
