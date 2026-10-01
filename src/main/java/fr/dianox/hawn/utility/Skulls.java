package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SkullMeta;

public final class Skulls {

	private Skulls() {}

	/**
	 * Sets the owner of a head. When the player is online, their profile (with the skin) is reused,
	 * so the server doesn't have to ask Mojang for it each time a menu is opened.
	 */
	@SuppressWarnings("deprecation")
	public static void setOwner(SkullMeta meta, String name) {
		Player online = name == null ? null : Bukkit.getPlayerExact(name);

		if (online != null) {
			meta.setOwningPlayer(online);
		} else {
			meta.setOwner(name);
		}
	}
}
