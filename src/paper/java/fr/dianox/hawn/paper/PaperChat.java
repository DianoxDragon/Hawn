package fr.dianox.hawn.paper;

import fr.dianox.hawn.modules.chat.ChatFormat;
import fr.dianox.hawn.modules.chat.ChatProcessor;
import fr.dianox.hawn.utility.RichText;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * The chat on a recent Paper (AsyncChatEvent). Same steps as OnChatEvent on Spigot.
 */
public class PaperChat implements Listener {

	// § colours, hex included (§x§R§R§G§G§B§B), both ways
	static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.builder()
			.character('§').hexColors().useUnusualXRepeatedCharacterHexFormat().build();

	private final Map<AsyncChatEvent, ChatProcessor.Result> pending = Collections.synchronizedMap(new WeakHashMap<>());
	// The renderer Hawn gave (Chat-Format), to see if another plugin replaced it
	private final Map<AsyncChatEvent, ChatRenderer> renderers = Collections.synchronizedMap(new WeakHashMap<>());
	private final Map<ChatRenderer, String> minis = Collections.synchronizedMap(new WeakHashMap<>());

	@EventHandler(priority = EventPriority.LOWEST)
	public void onChatFirst(AsyncChatEvent e) {
		if (ChatProcessor.intercept(e.getPlayer(), LEGACY.serialize(e.message()))) {
			e.setCancelled(true);
		}
	}

	// Chat-Format AUTO: early, a chat plugin that comes after wins
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onFormatEarly(AsyncChatEvent e) {
		if (ChatFormat.mode() == ChatFormat.Mode.AUTO) {
			format(e);
		}
	}

	// Chat-Format true: last, over the other plugins
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFormatLast(AsyncChatEvent e) {
		if (ChatFormat.mode() == ChatFormat.Mode.ON) {
			format(e);
		}
	}

	// HIGH and ignoreCancelled: the mutes of the other plugins (LiteBans...) are already applied
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onChat(AsyncChatEvent e) {
		String text = LEGACY.serialize(e.message());
		ChatProcessor.Result r = ChatProcessor.handle(e.getPlayer(), text, players(e));

		if (r.cancelled) {
			e.setCancelled(true);
			return;
		}

		// Unchanged: the message of the other plugins is kept as it is (their clicks, hover texts...)
		if (!r.message.equals(text)) {
			e.message(LEGACY.deserialize(r.message));
		}
		e.viewers().removeIf(viewer -> viewer instanceof Player && !r.recipients.contains(viewer));

		if (!r.perViewer.isEmpty()) {
			pending.put(e, r);
		}
	}

	// The renderer of the other plugins (or of the server) is kept: only the message changes for some players.
	// Paper can render a message once for everybody, so these players get their own version, out of the viewers.
	@EventHandler(priority = EventPriority.MONITOR)
	public void onChatSent(AsyncChatEvent e) {
		ChatRenderer ours = renderers.remove(e);
		ChatProcessor.Result r = pending.remove(e);
		if (e.isCancelled()) {
			return;
		}

		if (ours != null && e.renderer() != ours) {
			ChatFormat.replacedBy("renderer " + e.renderer().getClass().getName());
		}

		if (r == null) {
			return;
		}

		// The renderers of Paper keep their first result for every viewer: rendering the highlighted message with the
		// renderer of the event would give it to everybody. Each mentioned player gets it from a new renderer.
		String mini = ours != null && e.renderer() == ours ? minis.get(ours) : null;
		boolean byDefault = e.renderer().getClass() == ChatRenderer.defaultRenderer().getClass();
		if (mini == null && !byDefault) {
			return; // the format of another plugin: the mentioned player gets the normal message (and the sound, title...)
		}

		Component displayName = e.getPlayer().displayName();
		for (Map.Entry<Player, String> entry : r.perViewer.entrySet()) {
			Player viewer = entry.getKey();
			if (e.viewers().remove(viewer)) {
				ChatRenderer fresh = mini != null ? renderer(mini) : ChatRenderer.defaultRenderer();
				viewer.sendMessage(fresh.render(e.getPlayer(), displayName, LEGACY.deserialize(entry.getValue()), viewer));
			}
		}
	}

	private static ChatRenderer renderer(String mini) {
		return ChatRenderer.viewerUnaware((source, displayName, message) ->
				MiniMessage.miniMessage().deserialize(mini, Placeholder.component("hawn_message", message)));
	}

	// The format of the sender, read by MiniMessage (& codes and hex colours converted), the message in place of %message%
	private void format(AsyncChatEvent e) {
		String format = ChatProcessor.format(e.getPlayer());
		if (format == null) {
			return;
		}

		String mini = RichText.toMiniMessage(format).replace("%message%", "<hawn_message>");
		ChatRenderer ours = renderer(mini);
		e.renderer(ours);
		renderers.put(e, ours);
		minis.put(ours, mini);
	}

	private static List<Player> players(AsyncChatEvent e) {
		List<Player> players = new ArrayList<>();
		for (Audience viewer : e.viewers()) {
			if (viewer instanceof Player) {
				players.add((Player) viewer);
			}
		}
		return players;
	}

	static Component legacy(String text) {
		return LEGACY.deserialize(text);
	}
}
