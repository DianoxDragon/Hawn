package fr.dianox.hawn.utility;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.util.Tristate;
import org.bukkit.Bukkit;

import java.util.UUID;

/**
 * The permissions of a player who is not connected yet (the new Paper connection event has no Player).
 * With LuckPerms, its data loaded for the login is used; otherwise, like the permissions Hawn does not declare,
 * only the operators have them.
 */
public final class OfflinePermissions {

	private OfflinePermissions() {}

	public static boolean has(UUID uuid, String permission) {
		if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
			try {
				Boolean value = LuckPerms.check(uuid, permission);
				if (value != null) {
					return value;
				}
			} catch (Throwable ignored) {
				// LuckPerms not ready: the operators below
			}
		}

		return Bukkit.getOfflinePlayer(uuid).isOp();
	}

	// Its own class: the LuckPerms classes are only loaded when LuckPerms is there
	private static final class LuckPerms {
		static Boolean check(UUID uuid, String permission) {
			User user = LuckPermsProvider.get().getUserManager().getUser(uuid);
			if (user == null) {
				return null;
			}

			Tristate value = user.getCachedData().getPermissionData().checkPermission(permission);
			return value == Tristate.UNDEFINED ? null : value.asBoolean();
		}
	}
}
