package fr.dianox.hawn.event;

import fr.dianox.hawn.modules.serverlist.ServerPingEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

/**
 * The chat and the join use the new Paper events when the server has them, the Bukkit ones otherwise.
 */
public final class PlatformEvents {

	private static boolean paperChat = false;
	private static boolean paperLogin = false;
	private static boolean miniMessage = false;

	private PlatformEvents() {}

	public static void register(Plugin plugin) {
		// AsyncChatEvent with signed messages (Paper 1.19.1+)
		paperChat = hasMethod("io.papermc.paper.event.player.AbstractChatEvent", "signedMessage")
				&& register(plugin, "registerChat");
		if (!paperChat) {
			Bukkit.getPluginManager().registerEvents(new OnChatEvent(), plugin);
		}

		// Paper 1.21.7+
		paperLogin = hasClass("io.papermc.paper.event.connection.PlayerConnectionValidateLoginEvent")
				&& register(plugin, "registerLogin");
		if (!paperLogin) {
			Bukkit.getPluginManager().registerEvents(new ServerPingEvent.SpigotLogin(), plugin);
		}

		// MiniMessage in the messages (Paper 1.19.1+, where it comes with the server)
		miniMessage = paperChat && hasClass("net.kyori.adventure.text.minimessage.MiniMessage")
				&& register(plugin, "registerText");

	}

	public static boolean isPaperChat() {
		return paperChat;
	}

	public static boolean isMiniMessage() {
		return miniMessage;
	}

	public static boolean isPaperLogin() {
		return paperLogin;
	}

	private static boolean register(Plugin plugin, String method) {
		try {
			Class.forName("fr.dianox.hawn.paper.PaperEvents").getMethod(method, Plugin.class).invoke(null, plugin);
			return true;
		} catch (Throwable e) {
			plugin.getLogger().warning("Paper listener " + method + " not available, the Bukkit event is used: " + e);
			return false;
		}
	}

	private static boolean hasClass(String name) {
		try {
			Class.forName(name);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	private static boolean hasMethod(String className, String method) {
		try {
			Class.forName(className).getMethod(method);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}
}
