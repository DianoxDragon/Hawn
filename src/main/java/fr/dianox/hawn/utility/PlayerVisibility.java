package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.VanishCommand;
import fr.dianox.hawn.modules.ranks.Ranks;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The player hider (item, /option pv, world change): who each player sees.
 * Since 1.4.9, a third state shows only the staff and the VIP (PV.Option.Three-States).
 */
public class PlayerVisibility {

	/**
	 * Who the player sees: everyone, only the staff and the VIP, nobody.
	 */
	public enum Mode { ALL, STAFF, NONE }

	private static final Map<UUID, Mode> modes = new ConcurrentHashMap<>();
	// Safe for several threads (since 1.5.3)
	private static final List<Player> Cooling = new java.util.concurrent.CopyOnWriteArrayList<>();

	public static Mode mode(Player p) {
		return modes.getOrDefault(p.getUniqueId(), Mode.ALL);
	}

	/**
	 * The player hides someone (STAFF or NONE).
	 */
	public static boolean hides(Player p) {
		return mode(p) != Mode.ALL;
	}

	public static boolean threeStates() {
		return SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Three-States.Enable");
	}

	/**
	 * The next state of the item and of /option pv: everyone, the staff and the VIP, nobody (or everyone and nobody).
	 */
	public static Mode next(Mode mode) {
		if (mode == Mode.ALL) {
			return threeStates() ? Mode.STAFF : Mode.NONE;
		}
		return mode == Mode.STAFF ? Mode.NONE : Mode.ALL;
	}

	/**
	 * Shown to the players who see only the staff and the VIP: a rank of PV.Option.Three-States.Staff-And-VIP-Ranks,
	 * or hawn.playervisibility.shown (servers without ranks).
	 */
	public static boolean staffOrVip(Player target) {
		if (target.hasPermission("hawn.playervisibility.shown")) {
			return true;
		}

		Ranks.Rank rank = Ranks.rankOf(target);
		if (rank == null) {
			return false;
		}

		for (String name : SpecialCjiHidePlayers.getConfig().getStringList("PV.Option.Three-States.Staff-And-VIP-Ranks")) {
			if (name.equalsIgnoreCase(rank.name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * The players vanished with /v stay hidden: they are hidden with the same plugin key, so showing them would end
	 * their vanish for this player.
	 */
	private static boolean shown(Player viewer, Player target) {
		if (VanishCommand.player_list_vanish.contains(target) && !VanishCommand.seesVanished(viewer)) {
			return false;
		}

		switch (mode(viewer)) {
			case NONE:
				return false;
			case STAFF:
				return staffOrVip(target);
			default:
				return true;
		}
	}

	@SuppressWarnings("deprecation")
	private static void apply(Player viewer, Player target) {
		if (viewer == target) {
			return;
		}

		boolean modern = Main.getInstance().getVersionUtils().getSpigot_Version() >= 113;
		if (shown(viewer, target)) {
			if (modern) {
				viewer.showPlayer(Main.getInstance(), target);
			} else {
				viewer.showPlayer(target);
			}
		} else {
			if (modern) {
				viewer.hidePlayer(Main.getInstance(), target);
			} else {
				viewer.hidePlayer(target);
			}
		}
	}

	/**
	 * The state of the player, applied to every online player. Not saved: see PlayerOptionSQLClass#onMysqlYamlCJIChange.
	 */
	public static void setMode(Player viewer, Mode mode) {
		if (mode == Mode.ALL) {
			modes.remove(viewer.getUniqueId());
		} else {
			modes.put(viewer.getUniqueId(), mode);
		}

		for (Player target : Bukkit.getOnlinePlayers()) {
			apply(viewer, target);
		}
	}

	public static void showPlayer(Player p) {
		setMode(p, Mode.ALL);
	}

	public static void hidePlayer(Player p) {
		setMode(p, Mode.NONE);
	}

	/**
	 * A player who joins, or whose rank changed: hidden or shown again for the players who hide someone.
	 */
	public static void updateTarget(Player target) {
		for (Player viewer : Bukkit.getOnlinePlayers()) {
			if (viewer != target && hides(viewer)) {
				apply(viewer, target);
			}
		}
	}

	public static void forget(Player p) {
		modes.remove(p.getUniqueId());
		Cooling.remove(p);
	}

	/* ------------------------------------------------------------------ saved value (player_option_pv.Activate) */

	/**
	 * TRUE (nobody), FALSE (everyone) or STAFF: the value saved in MySQL. An older Hawn reads STAFF as TRUE.
	 */
	public static String value(Mode mode) {
		return mode == Mode.ALL ? "FALSE" : mode == Mode.STAFF ? "STAFF" : "TRUE";
	}

	/**
	 * The saved value read again: STAFF only when the three states are on (nobody otherwise).
	 */
	public static Mode fromValue(String value) {
		if (value == null || value.equalsIgnoreCase("FALSE") || value.equalsIgnoreCase("ALL")) {
			return Mode.ALL;
		}
		if (value.toUpperCase(Locale.ROOT).equals("STAFF")) {
			return threeStates() ? Mode.STAFF : Mode.NONE;
		}
		return Mode.NONE;
	}

	public static List<Player> Cooling() {
		return Cooling;
	}
}
