package fr.dianox.hawn.paper;

import fr.dianox.hawn.utility.RichText;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * Entry point of the Paper listeners, called by reflection from fr.dianox.hawn.event.PlatformEvents.
 */
public final class PaperEvents {

	private PaperEvents() {}

	public static void registerChat(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new PaperChat(), plugin);
	}

	public static void registerLogin(Plugin plugin) {
		Bukkit.getPluginManager().registerEvents(new PaperLogin(), plugin);
	}

	public static void registerText(Plugin plugin) {
		RichText.setRenderer(new PaperText());
	}
}
