package fr.dianox.hawn.hook.hooklist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

/**
 * The placeholders of Hawn for the other plugins (since 1.4.6): %hawn_<placeholder>% is the %<placeholder>% of Hawn,
 * for example %hawn_tps%, %hawn_online_world_nether%, %hawn_player_join_number%, %hawn_player_join_date%.
 * Only loaded when PlaceholderAPI is there.
 */
public class HawnExpansion extends PlaceholderExpansion {

	@Override
	public String getIdentifier() {
		return "hawn";
	}

	@Override
	public String getAuthor() {
		return "Dianox";
	}

	@Override
	public String getVersion() {
		return Main.getVersion();
	}

	// Kept by /papi reload: Hawn registers it itself
	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public String onRequest(OfflinePlayer player, String params) {
		if (params == null || params.isEmpty()) {
			return null;
		}

		// The placeholders kept inside Hawn (Plugin.Use.Hook.PlaceholderAPI.Expansion.Hidden)
		for (String hidden : ConfigGeneral.getConfig().getStringList("Plugin.Use.Hook.PlaceholderAPI.Expansion.Hidden")) {
			if (hidden.replace("%", "").equalsIgnoreCase(params) || ("hawn_" + hidden.replace("%", "")).equalsIgnoreCase(params)) {
				return null;
			}
		}

		Player online = player == null ? null : player.getPlayer();

		// %hawn_delay% as well as %hawn_DELAY%; %hawn_player_join_date% is %hawn_player_join_date% of Hawn
		String name = params.equalsIgnoreCase("delay") ? "DELAY" : params;
		String value = value("%" + name + "%", online);
		if (value == null) {
			value = value("%hawn_" + name + "%", online);
		}

		return value == null ? null : MessageUtils.colourTheStuff(value);
	}

	// The value, or null when Hawn has no such placeholder (PlaceholderAPI then leaves the text as it is)
	private static String value(String placeholder, Player p) {
		try {
			String value;
			if (p != null) {
				value = PlaceHolders.ReplaceMainplaceholderP(placeholder, p);
			} else if (needsPlayer(placeholder)) {
				// No made-up name or ping for an offline player (a hologram of the server, the console...)
				return null;
			} else {
				value = PlaceHolders.ReplaceMainplaceholderC(placeholder);
			}
			return value.equals(placeholder) ? null : value;
		} catch (Exception e) {
			return null;
		}
	}

	private static boolean needsPlayer(String placeholder) {
		String name = placeholder.replace("%", "").toLowerCase();
		return name.startsWith("player") || name.startsWith("hawn_player") || name.equals("target") || name.equals("ping")
				|| name.startsWith("timedelay");
	}
}
