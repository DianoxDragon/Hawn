package fr.dianox.hawn.modules.chat;

import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

/**
 * Chat-Format: the prefix and the format of the messages (Events/Chat.yml, or the group of worlds).
 * AUTO: applied early, so a chat plugin that comes after wins; off when a known chat plugin is installed.
 * true: applied last, over the other plugins. false: the chat plugins (or the server) keep the format.
 */
public final class ChatFormat {

	public enum Mode { OFF, AUTO, ON }

	// Chat plugins that format the chat themselves
	private static final String[] CHAT_PLUGINS = {"EssentialsChat", "ChatControl", "ChatControlRed", "VentureChat", "LPC", "DeluxeChat",
			"ChatEx", "ChatManager", "CarbonChat", "HeroChat", "UltimateChat", "ChatFormatter", "TownyChat", "ChatColor2", "InteractiveChat"};

	private static Mode mode;
	// The chat plugin found, null when none
	private static String chatPlugin;
	private static volatile boolean warned = false;

	private ChatFormat() {}

	/**
	 * At startup (once every plugin is loaded) and on /hawn reload.
	 */
	public static void reload() {
		String value = OnChatConfig.getConfig().getString("Chat-Format.Enable", "AUTO");
		warned = false;
		chatPlugin = null;
		for (String name : CHAT_PLUGINS) {
			Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
			if (plugin != null) {
				chatPlugin = plugin.getName();
				break;
			}
		}

		if (value.equalsIgnoreCase("true")) {
			mode = Mode.ON;
		} else if (value.equalsIgnoreCase("false")) {
			mode = Mode.OFF;
		} else {
			// The startup report says it
			mode = chatPlugin == null ? Mode.AUTO : Mode.OFF;
		}
	}

	public static String chatPlugin() {
		if (mode == null) {
			reload();
		}
		return chatPlugin;
	}

	public static Mode mode() {
		if (mode == null) {
			reload();
		}
		return mode;
	}

	/**
	 * @return the format of the player, coloured, with %message% where the message goes; null when Hawn doesn't format
	 */
	public static String build(Player p, ChatSettings s) {
		if (mode() == Mode.OFF) {
			return null;
		}

		String format = s.string("Chat-Format.Format", "");
		if (format.isEmpty() || !format.contains("%message%")) {
			return null;
		}

		if (HooksManager.papi()) {
			format = PlaceholderAPI.setPlaceholders(p, format);
		}
		format = PlaceHolders.ReplaceMainplaceholderP(format, p);
		return MessageUtils.colourTheStuff(format);
	}

	/**
	 * A chat plugin changed the format after Hawn: said once in the console.
	 */
	public static void replacedBy(String what) {
		if (warned) {
			return;
		}
		warned = true;
		Bukkit.getLogger().warning("[Hawn] Another plugin changes the chat format after Hawn (" + what + "): the Chat-Format of Hawn is not shown. "
				+ "Set Chat-Format.Enable to false to leave the format to the other plugin, or to true to use the one of Hawn.");
	}
}
