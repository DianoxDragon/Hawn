package fr.dianox.hawn.paper;

import fr.dianox.hawn.utility.RichText;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;

/**
 * MiniMessage on Paper: the texts with tags become components. A text MiniMessage can't read is sent as before.
 */
public class PaperText implements RichText.Renderer {

	private final MiniMessage mini = MiniMessage.miniMessage();

	private Component parse(String text) {
		return mini.deserialize(text);
	}

	@Override
	public void send(CommandSender to, String text) {
		Component c;
		try {
			c = parse(text);
		} catch (Throwable e) {
			to.sendMessage(mini.stripTags(text));
			return;
		}
		to.sendMessage(c);
	}

	@Override
	public void title(Player p, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		try {
			Title.Times times = Title.Times.times(Duration.ofMillis(fadeIn * 50L), Duration.ofMillis(stay * 50L), Duration.ofMillis(fadeOut * 50L));
			p.showTitle(Title.title(parse(title), parse(subtitle), times));
		} catch (Throwable e) {
			p.sendTitle(mini.stripTags(title), mini.stripTags(subtitle), fadeIn, stay, fadeOut);
		}
	}

	@Override
	public void actionBar(Player p, String text) {
		try {
			p.sendActionBar(parse(text));
		} catch (Throwable e) {
			p.sendActionBar(Component.text(mini.stripTags(text)));
		}
	}
}
