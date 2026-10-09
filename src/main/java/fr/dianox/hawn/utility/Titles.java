package fr.dianox.hawn.utility;

import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Titles through the Bukkit API (no NMS, works on every supported version).
 */
public final class Titles {

	private Titles() {}

	public static void clearTitle(Player player) {
		player.resetTitle();
	}

	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
		TitleSendEvent titleSendEvent = new TitleSendEvent(player, title, subtitle);
		Bukkit.getPluginManager().callEvent(titleSendEvent);
		if (titleSendEvent.isCancelled()) {
			return;
		}

		if (Main.avoidtitles.contains(player)) {
			return;
		}

		title = format(titleSendEvent.getTitle(), player);
		subtitle = format(titleSendEvent.getSubtitle(), player);

		if (title == null && subtitle == null) {
			return;
		}

		if (RichText.isRich(title) || RichText.isRich(subtitle)) {
			RichText.title(player, title, subtitle, fadeIn, stay, fadeOut);
			return;
		}

		// A subtitle is only displayed along with a title
		player.sendTitle(title == null ? "" : MessageUtils.plain(title), MessageUtils.plain(subtitle), fadeIn, stay, fadeOut);
	}

	private static String format(String text, Player player) {
		if (text == null) {
			return null;
		}

		text = PlaceHolders.ReplaceMainplaceholderP(text, player);

		if (HooksManager.papi()) {
			text = PlaceholderAPI.setPlaceholders(player, text);
		}


		text = MessageUtils.colourTheStuff(text);
		return text.replace("%player%", player.getDisplayName());
	}

	public static void time(Player p, Integer ticks) {
		Main.avoidtitles.add(p);

		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.avoidtitles.remove(p), ticks);
	}
}
