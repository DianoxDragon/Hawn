package fr.dianox.hawn.modules.menus;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The heads with a skin through the profiles of Bukkit (1.18.1+), reached by reflection: Hawn is compiled against 1.16.5.
 * Before 1.18.1, XSkull does it (MenuItems).
 */
final class Profiles {

	private static final Pattern URL_IN_JSON = Pattern.compile("\"url\"\\s*:\\s*\"([^\"]+)\"");
	private static final Pattern HASH = Pattern.compile("^[0-9a-fA-F]{40,70}$");

	private static Method create;
	private static Method getTextures;
	private static Method setSkin;
	private static Method setTextures;
	private static Method update;
	private static Method setOwnerProfile;
	private static boolean ready;

	static {
		try {
			Class<?> profile = Class.forName("org.bukkit.profile.PlayerProfile");
			Class<?> textures = Class.forName("org.bukkit.profile.PlayerTextures");
			create = Bukkit.class.getMethod("createPlayerProfile", UUID.class, String.class);
			getTextures = profile.getMethod("getTextures");
			setSkin = textures.getMethod("setSkin", URL.class);
			setTextures = profile.getMethod("setTextures", textures);
			update = profile.getMethod("update");
			setOwnerProfile = SkullMeta.class.getMethod("setOwnerProfile", profile);
			ready = true;
		} catch (Throwable ignored) {
			// Before 1.18.1
		}
	}

	private Profiles() {}

	static boolean available() {
		return ready;
	}

	/**
	 * A head with the skin of a Base64 texture, a URL or the hash of a texture; null when it is none of them.
	 */
	static ItemStack textured(ItemStack head, String value) throws Exception {
		String url = url(value);
		if (url == null) {
			return null;
		}
		// A name too: some versions (Paper 1.20.6) ignore a head whose profile has no name
		Object profile = create.invoke(null, UUID.nameUUIDFromBytes(value.getBytes(StandardCharsets.UTF_8)), "HawnHead");
		Object textures = getTextures.invoke(profile);
		setSkin.invoke(textures, new URL(url));
		setTextures.invoke(profile, textures);
		return apply(head, profile);
	}

	/**
	 * A player not connected (a name or a UUID): their skin asked to Mojang in the background.
	 */
	@SuppressWarnings("unchecked")
	static CompletableFuture<ItemStack> lookup(ItemStack head, UUID id, String name) throws Exception {
		Object profile = create.invoke(null, id, name);
		CompletableFuture<Object> done = (CompletableFuture<Object>) update.invoke(profile);
		return done.thenApply(complete -> {
			try {
				return apply(head, complete);
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		});
	}

	private static ItemStack apply(ItemStack head, Object profile) throws Exception {
		SkullMeta meta = (SkullMeta) head.getItemMeta();
		setOwnerProfile.invoke(meta, profile);
		head.setItemMeta(meta);
		return head;
	}

	static String url(String value) {
		String v = value.trim();
		if (v.startsWith("http://") || v.startsWith("https://")) {
			return v;
		}
		if (HASH.matcher(v).matches()) {
			return "http://textures.minecraft.net/texture/" + v;
		}
		try {
			String json = new String(Base64.getDecoder().decode(v), StandardCharsets.UTF_8);
			Matcher m = URL_IN_JSON.matcher(json);
			return m.find() ? m.group(1) : null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
