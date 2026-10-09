package fr.dianox.hawn.modules.chat;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.DelaychatCommand;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.modules.admin.UrgentMode;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.modules.world.GuiSystem;
import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.Titles;
import fr.dianox.hawn.utility.XParse;
import fr.dianox.hawn.utility.config.configs.commands.DelayChatCommandConfig;
import fr.dianox.hawn.utility.config.configs.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The chat of Hawn, the same on Spigot (AsyncPlayerChatEvent) and on Paper (AsyncChatEvent).
 * The listeners only translate their event to and from this class.
 * The options are read through ChatSettings: those of the group of worlds of the player first.
 */
public final class ChatProcessor {

	private static final Set<String> cooling = ConcurrentHashMap.newKeySet();
	// Anti spam: the last message of each player, gone with the player
	private static final Map<Player, String> lastMessage = Collections.synchronizedMap(new WeakHashMap<>());
	private static final Map<Player, Long> lastTime = Collections.synchronizedMap(new WeakHashMap<>());

	// Chat colours: the codes and the permission that unlocks them when Per-Color-Permission is false
	private static final String LIGHT = "ceab3df7";
	private static final String DARK = "46219580";
	private static final String FORMAT = "lmnor";
	private static final String MAGIC = "k";

	private static final Pattern CODES = Pattern.compile("[&§]([0-9a-fk-orA-FK-ORxX]|#[0-9a-fA-F]{6})");
	private static final Pattern WORD = Pattern.compile("\\S+");

	private ChatProcessor() {}

	/**
	 * What Hawn does with a message.
	 */
	public static final class Result {
		public boolean cancelled = false;
		// The message, with § colours
		public String message;
		// The players who see the message
		public final Set<Player> recipients = new LinkedHashSet<>();
		// The players who see another version of the message (a mention highlighted for them)
		public final Map<Player, String> perViewer = new HashMap<>();
		// The format of the sender (Chat-Format), coloured, with %message%; null when Hawn doesn't format
		public String format;
	}

	/**
	 * First listener (LOWEST): the chat used by Hawn itself, before the other plugins.
	 * @return true when the message must be cancelled
	 */
	public static boolean intercept(Player p, String text) {
		if (UrgentMode.blockChat(p)) {
			return true;
		}
		return GuiSystem.captureChat(p, text);
	}

	/**
	 * Chat-Format of the player, computed on the main options (or their group).
	 */
	public static String format(Player p) {
		return ChatFormat.build(p, ChatSettings.of(p));
	}

	/**
	 * The chat features of Hawn (HIGH, after the mutes of the other plugins).
	 * @param text       the message, § colours of the other plugins kept, & codes typed by the player
	 * @param recipients the players who would see it
	 */
	public static Result process(Player p, String text, Collection<Player> recipients) {
		ChatSettings s = ChatSettings.of(p);
		Result r = new Result();
		r.message = text;

		// Chat per group of worlds: only the players of the same group
		for (Player viewer : recipients) {
			if (s.sameChat(viewer)) {
				r.recipients.add(viewer);
			}
		}

		if (muted(p, s) || delayed(p, s) || spam(p, s, text)) {
			r.cancelled = true;
			return r;
		}

		String original = caps(p, s, text);
		String typed = original;

		if (s.bool("Anti-Swear.Enable") && (!s.bool("Anti-Swear.Bypass") || !p.hasPermission("hawn.bypass.antiswear"))) {
			original = antiSwear(p, s, typed, original);
		}

		if (s.bool("Chat-Color-Player.Enable")) {
			original = colours(p, s, original);

			// Hex colours: &#RRGGBB or #<RRGGBB>
			if (p.hasPermission("hawn.use.chatcolor.chat.hex")) {
				original = MessageUtils.colourHex(original);
			}
		}

		if (s.bool("Chat-Emoji-Player.Enable") && p.hasPermission("hawn.chat.emoji")) {
			for (Map.Entry<String, String> emoji : ChatEmojisLoad.emojislist.entrySet()) {
				String check = emoji.getKey();

				if (ChatEmojisLoad.emojislistperm.containsKey(check) && !p.hasPermission(ChatEmojisLoad.emojislistperm.get(check))) {
					continue;
				}

				if (typed.toLowerCase().contains(check.toLowerCase())) {
					original = original.replaceAll("(?i)" + Pattern.quote(check), Matcher.quoteReplacement(emoji.getValue()));
				}
			}
		}

		r.message = original;

		if (s.bool("Chat-Mention.Enable") && p.hasPermission("hawn.chat.can.mention") && original.contains("@")) {
			mentions(p, s, r);
		}

		return r;
	}

	/* ------------------------------------------------------------------ mute and delay */

	private static boolean muted(Player p, ChatSettings s) {
		// /mutechat for the whole server, or Mute: true in the group of worlds
		boolean muted = MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable") || s.inGroup("Mute") && s.bool("Mute");
		if (!muted) {
			return false;
		}
		if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Bypass") && p.hasPermission("hawn.event.chat.bypass.mutechat")) {
			return false;
		}

		tell(p, ConfigMMsg.getConfig().getStringList("MuteChat.Can-t-Speak"), "");
		return true;
	}

	private static boolean delayed(Player p, ChatSettings s) {
		boolean enabled = s.inGroup("Chat-Delay.Enable") ? s.bool("Chat-Delay.Enable") : DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Enable");
		if (!enabled) {
			return false;
		}
		if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Bypass") && p.hasPermission("hawn.event.chat.bypass.chatdelay")) {
			return false;
		}

		int seconds = s.inGroup("Chat-Delay.Seconds") ? s.integer("Chat-Delay.Seconds", DelaychatCommand.delay) : DelaychatCommand.delay;
		if (seconds <= 0) {
			return false;
		}

		String name = p.getName();
		if (!cooling.add(name)) {
			// %DELAY% is the delay of /delaychat, the group can have its own
			java.util.List<String> lines = new java.util.ArrayList<>();
			for (String line : ConfigMMsg.getConfig().getStringList("ChatDelay.Delay")) {
				lines.add(line.replace("%DELAY%", String.valueOf(seconds)));
			}
			tell(p, lines, "");
			return true;
		}

		Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> cooling.remove(name), seconds * 20L);
		return false;
	}

	/* ------------------------------------------------------------------ anti spam */

	// The same message again (colours, case, spaces and punctuation ignored) within Repeat.Seconds
	private static boolean spam(Player p, ChatSettings s, String text) {
		if (!s.bool("Anti-Spam.Enable") || !s.bool("Anti-Spam.Repeat.Enable") || bypassSpam(p, s)) {
			return false;
		}

		String normal = CODES.matcher(text).replaceAll("").toLowerCase(Locale.ROOT).replaceAll("[^\\p{L}\\p{N}]", "");
		long now = System.currentTimeMillis();
		long window = s.integer("Anti-Spam.Repeat.Seconds", 30) * 1000L;

		synchronized (lastMessage) {
			String previous = lastMessage.get(p);
			Long when = lastTime.get(p);
			if (!normal.isEmpty() && normal.equals(previous) && when != null && now - when < window) {
				tell(p, ConfigMMsg.getConfig().getStringList("Anti-Spam.Repeat"), "");
				return true;
			}
			lastMessage.put(p, normal);
			lastTime.put(p, now);
		}
		return false;
	}

	// Too many capital letters: put in lower case (the mentions are kept), or blocked with Caps.Block
	private static String caps(Player p, ChatSettings s, String text) {
		if (!s.bool("Anti-Spam.Enable") || !s.bool("Anti-Spam.Caps.Enable") || bypassSpam(p, s)) {
			return text;
		}

		String letters = CODES.matcher(text).replaceAll("").replaceAll("@\\S+", "").replaceAll("[^\\p{L}]", "");
		if (letters.length() < s.integer("Anti-Spam.Caps.Min-Letters", 6)) {
			return text;
		}

		int upper = 0;
		for (char c : letters.toCharArray()) {
			if (Character.isUpperCase(c)) upper++;
		}
		if (upper * 100 <= s.integer("Anti-Spam.Caps.Max-Percent", 70) * letters.length()) {
			return text;
		}

		if (s.bool("Anti-Spam.Caps.Block")) {
			tell(p, ConfigMMsg.getConfig().getStringList("Anti-Spam.Caps"), "");
			throw new Blocked();
		}

		Matcher m = WORD.matcher(text);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String word = m.group();
			m.appendReplacement(sb, Matcher.quoteReplacement(word.startsWith("@") ? word : word.toLowerCase(Locale.ROOT)));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	private static boolean bypassSpam(Player p, ChatSettings s) {
		return s.bool("Anti-Spam.Bypass") && p.hasPermission("hawn.bypass.antispam");
	}

	// A message blocked in the middle of the steps
	private static final class Blocked extends RuntimeException {
		Blocked() {
			super(null, null, false, false);
		}
	}

	/**
	 * process(), with a blocked message as a cancelled result.
	 */
	public static Result handle(Player p, String text, Collection<Player> recipients) {
		try {
			return process(p, text, recipients);
		} catch (Blocked b) {
			Result r = new Result();
			r.message = text;
			r.cancelled = true;
			return r;
		}
	}

	/* ------------------------------------------------------------------ anti swear */

	private static String antiSwear(Player p, ChatSettings s, String typed, String original) {
		for (String word : s.list("Anti-Swear.List")) {
			if (!typed.toLowerCase().contains(word.toLowerCase())) {
				continue;
			}

			if (s.bool("Anti-Swear.Notify-Staff")) {
				for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
					if (staff.hasPermission("hawn.antiswear.benotified")) {
						for (String msg : ConfigMMsg.getConfig().getStringList("Anti-Swear.Notify-Staff")) {
							String message = msg.replace("%player%", p.getName()).replace("%message%", ConfigEventUtils.noAction(typed));
							runSync(() -> ConfigEventUtils.ExecuteEvent(staff, message, "", "", false));
						}
					}
				}
			}

			if (s.bool("Anti-Swear.Replace-Message.Enable")) {
				String replacement = s.string("Anti-Swear.Replace-Message.Message", "*****").replace("[", "").replace("]", "");
				original = original.replaceAll("(?i)" + Pattern.quote(word), Matcher.quoteReplacement(replacement));
			}
		}

		return original;
	}

	/* ------------------------------------------------------------------ colours */

	private static String colours(Player p, ChatSettings s, String message) {
		boolean perColour = s.bool("Chat-Color-Player.Per-Color-Permission");

		for (char code : (LIGHT + DARK + FORMAT + MAGIC).toCharArray()) {
			String typed = "&" + code;
			if (!message.contains(typed)) {
				continue;
			}

			String permission;
			if (perColour) {
				permission = "hawn.use.chatcolor.chat.code." + code;
			} else if (LIGHT.indexOf(code) >= 0) {
				permission = "hawn.use.chatcolor.chat.basic.light";
			} else if (DARK.indexOf(code) >= 0) {
				permission = "hawn.use.chatcolor.chat.basic.dark";
			} else if (FORMAT.indexOf(code) >= 0) {
				permission = "hawn.use.chatcolor.chat.special.format";
			} else {
				permission = "hawn.use.chatcolor.chat.special.magic";
			}

			if (p.hasPermission(permission)) {
				message = message.replace(typed, "§" + code);
			}
		}

		return message;
	}

	/* ------------------------------------------------------------------ mentions */

	private static void mentions(Player p, ChatSettings s, Result r) {
		boolean highlight = s.bool("Chat-Mention.Mentionned.Chat-Highlight.Enable");
		boolean self = s.bool("Chat-Mention.Mentionned.Self-Mention.Enable");
		String highlighting = MessageUtils.colourTheStuff(s.string("Chat-Mention.Mentionned.Chat-Highlight.Highlighting", "&6&l"));

		for (Player target : r.recipients) {
			String tag = "@" + target.getName();
			// The whole name: @Bot is not a mention of BotAdmin
			Pattern whole = Pattern.compile("(?<![A-Za-z0-9_])" + Pattern.quote(tag) + "(?![A-Za-z0-9_])");
			if (!whole.matcher(r.message).find()) {
				continue;
			}
			if (target.equals(p) && !self) {
				continue;
			}

			runSync(() -> mentioned(target, p, s));

			if (highlight) {
				r.perViewer.put(target, whole.matcher(r.message).replaceAll(Matcher.quoteReplacement(highlighting + tag + "§r")));
			}
		}
	}

	private static void mentioned(Player p, Player sender, ChatSettings s) {
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
			if (s.bool("Chat-Mention.Mentionned.Send-Message.Enable")) {
				for (String msg : s.list("Chat-Mention.Mentionned.Send-Message.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replace("%sender%", sender.getName()).replace("%player%", p.getName()), "", "", false);
				}
			}

			if (s.bool("Chat-Mention.Mentionned.Send-ActionBar.Enable")) {
				String actionbar = text(p, sender, s, "Chat-Mention.Mentionned.Send-ActionBar.Options.Message");
				ActionBar.sendActionBar(Main.getInstance(), p, actionbar, s.integer("Chat-Mention.Mentionned.Send-ActionBar.Options.Time-Stay", 150));
			}

			if (s.bool("Chat-Mention.Mentionned.Send-Title.Enable") && s.bool("Chat-Mention.Mentionned.Send-Title.Options.Enable")) {
				Titles.sendTitle(p, s.integer("Chat-Mention.Mentionned.Send-Title.Options.FadeIn", 20),
						s.integer("Chat-Mention.Mentionned.Send-Title.Options.Stay", 150),
						s.integer("Chat-Mention.Mentionned.Send-Title.Options.FadeOut", 20),
						text(p, sender, s, "Chat-Mention.Mentionned.Send-Title.Options.Title"),
						text(p, sender, s, "Chat-Mention.Mentionned.Send-Title.Options.SubTitle"));
			}

			if (s.bool("Chat-Mention.Mentionned.Sound.Enable")) {
				String sound = s.string("Chat-Mention.Mentionned.Sound.Sound", "BLOCK_NOTE_HARP");
				float volume = (float) s.decimal("Chat-Mention.Mentionned.Sound.Volume", 1);
				float pitch = (float) s.decimal("Chat-Mention.Mentionned.Sound.Pitch", 1);
				p.playSound(p.getLocation(), XParse.sound(sound, "Chat-Mention.Mentionned.Sound.Sound"), volume, pitch);
			}
		}, 10);
	}

	private static String text(Player p, Player sender, ChatSettings s, String path) {
		String text = s.string(path, "");
		if (HooksManager.papi()) {
			text = PlaceholderAPI.setPlaceholders(p, text);
		}
		text = MessageUtils.colourTheStuff(text);
		text = text.replace("%sender%", sender.getName()).replace("%player%", p.getName());
		return PlaceHolders.ReplaceMainplaceholderP(text, p);
	}

	private static void tell(Player p, Iterable<String> lines, String arg) {
		for (String msg : lines) {
			runSync(() -> ConfigEventUtils.ExecuteEvent(p, msg, arg, "", false));
		}
	}

	// The chat runs outside the main thread: the actions (commands, sounds, titles...) go back to it
	public static void runSync(Runnable task) {
		if (Bukkit.isPrimaryThread()) {
			task.run();
		} else {
			Bukkit.getScheduler().runTask(Main.getInstance(), task);
		}
	}
}
