package fr.dianox.hawn.event;

import fr.dianox.hawn.modules.chat.ChatFormat;
import fr.dianox.hawn.modules.chat.ChatProcessor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * The chat on Spigot (and on a Paper without AsyncChatEvent). On a recent Paper, PaperChat does the same with AsyncChatEvent.
 */
@SuppressWarnings("deprecation")
public class OnChatEvent implements Listener {

	// The messages that some players see differently, sent once the other plugins have set the format
	private final Map<AsyncPlayerChatEvent, ChatProcessor.Result> pending = Collections.synchronizedMap(new WeakHashMap<>());
	// The format Hawn gave, to see if another plugin replaced it
	private final Map<AsyncPlayerChatEvent, String> formats = Collections.synchronizedMap(new WeakHashMap<>());

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChatFirst(AsyncPlayerChatEvent e) {
		if (ChatProcessor.intercept(e.getPlayer(), e.getMessage())) {
			e.setCancelled(true);
		}
	}

	// Chat-Format AUTO: early, a chat plugin that comes after wins
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onFormatEarly(AsyncPlayerChatEvent e) {
		if (ChatFormat.mode() == ChatFormat.Mode.AUTO) {
			format(e);
		}
	}

	// HIGH and ignoreCancelled: the mutes of the other plugins (LiteBans...) are already applied
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent e) {
		ChatProcessor.Result r = ChatProcessor.handle(e.getPlayer(), e.getMessage(), e.getRecipients());

		if (r.cancelled) {
			e.setCancelled(true);
			return;
		}

		e.setMessage(r.message);

		// The recipients can't be changed when the event comes from Player#chat of a plugin
		try {
			if (!r.recipients.containsAll(e.getRecipients())) {
				e.getRecipients().retainAll(r.recipients);
			}

			if (!r.perViewer.isEmpty()) {
				e.getRecipients().removeAll(r.perViewer.keySet());
				pending.put(e, r);
			}
		} catch (UnsupportedOperationException ignored) {}
	}

	// Chat-Format true: last, over the other plugins
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFormatLast(AsyncPlayerChatEvent e) {
		if (ChatFormat.mode() == ChatFormat.Mode.ON) {
			format(e);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onChatSent(AsyncPlayerChatEvent e) {
		String ours = formats.remove(e);
		ChatProcessor.Result r = pending.remove(e);
		if (e.isCancelled()) {
			return;
		}

		if (ours != null && !ours.equals(e.getFormat())) {
			ChatFormat.replacedBy("format " + e.getFormat());
		}

		if (r != null) {
			for (Map.Entry<Player, String> entry : r.perViewer.entrySet()) {
				entry.getKey().sendMessage(String.format(e.getFormat(), e.getPlayer().getDisplayName(), entry.getValue()));
			}
		}
	}

	private void format(AsyncPlayerChatEvent e) {
		String format = ChatProcessor.format(e.getPlayer());
		if (format == null) {
			return;
		}

		int i = format.indexOf("%message%");
		String bukkit = escape(format.substring(0, i)) + "%2$s" + escape(format.substring(i + "%message%".length()).replace("%message%", ""));
		e.setFormat(bukkit);
		formats.put(e, bukkit);
	}

	// The format of Bukkit is a String.format: a % of a prefix must be doubled
	private static String escape(String text) {
		return text.replace("%", "%%");
	}
}
