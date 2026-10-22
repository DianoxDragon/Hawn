package fr.dianox.hawn.utility;

import fr.dianox.hawn.modules.menus.Menus;

import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fr.dianox.hawn.command.commands.WarpCommand;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;


public class ConfigEventUtils {

	/**
	 * Text typed by a player (a broadcast, a reason, a name...) put in a message line must stay a message:
	 * ExecuteEvent reads the actions ([command-console]:, json:, <perm>...) at the start of the line.
	 * A colour reset in front of the text breaks these prefixes and does not show.
	 */
	public static String noAction(String text) {
		if (text == null) {
			return "";
		}

		// Nor a MiniMessage tag (a <click> in a broadcast...): the "<" is shown, never read
		text = text.replace('<', RichText.LT);

		String start = text.trim();
		if (start.startsWith("[") || start.startsWith("<") || start.regionMatches(true, 0, "json:", 0, 5)) {
			return "\u00A7r" + text;
		}
		return text;
	}
	
	public static void ExecuteEvent(Player p, String event, String Informatif, String AdditionalMessageError, Boolean Console) {
		execute(p, null, event, Informatif, AdditionalMessageError, Console);
	}

	/**
	 * The same, with the placeholders of target (the player the message is about) in the texts.
	 */
	public static void ExecuteEventTargetPlaceHolder(Player p, Player target, String event, String Informatif, String AdditionalMessageError, Boolean Console) {
		execute(p, target, event, Informatif, AdditionalMessageError, Console);
	}

	// 90: one method for both (they were copies of each other). target: null for the placeholders of p
	private static void execute(Player p, Player target, String event, String Informatif, String AdditionalMessageError, Boolean Console) {
		Player who = target == null ? p : target;

		if (event.startsWith("<world>") && event.contains("</world>")) {
			String world = StringUtils.substringBetween(event, "<world>", "</world>");
			event = event.replace("<world>" + world + "</world> ", "");

			if (!p.getWorld().getName().equalsIgnoreCase(world)) {
				return;
			}
		}

		if (event.contains("<perm>") && event.contains("</perm>")) {
			String perm = StringUtils.substringBetween(event, "<perm>", "</perm>");
			event = event.replace("<perm>" + perm + "</perm> ", "");

			if (!p.hasPermission(perm)) {
				return;
			}
		}

		// [delay[ticks]]: and [chance[percent]]: in front of the line
		if (Actions.prefix(p, event, rest -> execute(p, target, rest, Informatif, AdditionalMessageError, Console))) {
			return;
		}

		// [broadcast]:, [sound]: and [sounds]:, [title]:
		if (Actions.run(p, event, AdditionalMessageError)) {
			return;
		}

		if (event.startsWith("[command-player]: ")) {
			p.performCommand(event.substring("[command-player]: ".length()).replace("%player%", p.getName()));
		} else if (event.startsWith("[FWLU]: ")) {
			OtherUtils.Fireworkmethod(p, event.substring("[FWLU]: ".length()));
		} else if (event.startsWith("[command-console]: ")) {
			Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), event.substring("[command-console]: ".length()).replace("%player%", p.getName()));
		} else if (event.startsWith("[gamemode-survival]")) {
			p.setGameMode(GameMode.SURVIVAL);
		} else if (event.startsWith("[gamemode-creative]")) {
			p.setGameMode(GameMode.CREATIVE);
		} else if (event.startsWith("[gamemode-adventure]")) {
			p.setGameMode(GameMode.ADVENTURE);
		} else if (event.startsWith("[gamemode-spectator]")) {
			p.setGameMode(GameMode.SPECTATOR);
		} else if (event.startsWith("[customcommand-player]: ")) {
			OnCommandEvent.executeCustomCommand(event.substring("[customcommand-player]: ".length()).replace("%player%", p.getName()), p);
		} else if (event.startsWith("[ping]")) {
			for (String event1: ConfigMMsg.getConfig().getStringList("Ping.Self")) {
				MessageUtils.ClassicMessages(event1, p);
			}
		} else if (event.startsWith("[spawn]: ")) {
			SpawnUtils.teleportToSpawn(p, event.substring("[spawn]: ".length()));
		} else if (event.startsWith("[warp]: ")) {
			WarpCommand.onTp(p, event.substring("[warp]: ".length()));
		} else if (event.startsWith("[bungee]: ")) {
			Main.getInstance().getBungApi().ConnectOthers(event.substring("[bungee]: ".length()), p);
		// The menus of Menus/ (since 1.5)
		} else if (event.startsWith("[menu]: ")) {
			Menus.openLater(p, event.substring("[menu]: ".length()));
		} else if (event.startsWith("[close]")) {
			Menus.closeLater(p);
		} else if (event.startsWith("[refresh]")) {
			Menus.refresh(p);
		} else if (event.startsWith("[back]")) {
			Menus.back(p);
		} else if (event.startsWith("[effect[")) {
			// [effect[<amplifier>]]: <effect>; a wrong amplifier or a missing "]]: " no longer stops the line
			effect(p, event.substring("[effect[".length()), AdditionalMessageError);
		} else if (event.startsWith("[effectclear]: ")) {
			p.removePotionEffect(XParse.potion(event.substring("[effectclear]: ".length()), AdditionalMessageError));
		} else if (event.startsWith("[effectclearall]")) {
			for (PotionEffect effect: p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}
		} else if (event.startsWith("[send-title]: ")) {
			// A title alone (no //n, or nothing after it) has a blank subtitle
			title(p, fill(event.substring("[send-title]: ".length()), who), 150);
		} else if (event.startsWith("[send-title[")) {
			// [send-title[<ticks>]]: title//nsubtitle; a wrong number or a missing "]]: ": 150 ticks
			Timed timed = timed(event.substring("[send-title[".length()), 150);
			title(p, fill(timed.text, who), timed.number);
		} else if (event.startsWith("[send-actionbar]: ")) {
			ActionBar.sendActionBar(p, fill(event.substring("[send-actionbar]: ".length()), who));
		} else if (event.startsWith("[send-actionbar[")) {
			Timed timed = timed(event.substring("[send-actionbar[".length()), 60);
			ActionBar.sendActionBar(Main.getInstance(), p, fill(timed.text, who), timed.number);
		} else {
			if (target != null) {
				event = PlaceHolders.ReplaceMainplaceholderP(event, target);
				if (HooksManager.papi()) {
					event = PlaceholderAPI.setPlaceholders(target, event);
				}
			}

			if (Console) {
				if (AdditionalMessageError.contains("STRICTSCONSOLE")) {
					// The console copy of a message about a player (join, quit...): the placeholders of this player too
					// (%player_join_number% stayed as it was in the console)
					if (who != null && target == null) {
						event = PlaceHolders.ReplaceMainplaceholderP(event, who);
						if (HooksManager.papi()) {
							event = PlaceholderAPI.setPlaceholders(who, event);
						}
					}
					MessageUtils.ConsoleMessages(event);
				} else {
					MessageUtils.ClassicMessagesConsoleSupport(event, p);
				}
			} else {
				if (Informatif.equals("AUTOCENTER")) {
					event = "<--center--> " + event;
				}

				MessageUtils.ClassicMessages(event, p);
			}
		}
	}

	/**
	 * The actions a message line can start with (for /hawn debug): the line is an action when it starts with one of them.
	 */
	public static final String[] ACTIONS = {
		"[command-player]: ", "[FWLU]: ", "[command-console]: ", "[gamemode-survival]", "[gamemode-creative]", "[gamemode-adventure]",
		"[gamemode-spectator]", "[customcommand-player]: ", "[ping]", "[spawn]: ", "[warp]: ", "[bungee]: ", "[effect[", "[effectclear]: ",
		"[effectclearall]", "[send-title]: ", "[send-title[", "[send-actionbar]: ", "[send-actionbar[", "[broadcast]: ", "[sound]: ",
		"[sounds]: ", "[title]: ", "[title[", "[delay[", "[chance[", "[menu]: ", "[close]", "[refresh]", "[back]"
	};

	public static void ExecuteEventAllPlayers(String event, String Informatif, String AdditionalMessageError, Player target, Boolean targetbool) {
		if (targetbool) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				ExecuteEventTargetPlaceHolder(p, target, event, Informatif, AdditionalMessageError, false);
			}
		} else {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				ExecuteEvent(p, event, Informatif, AdditionalMessageError, false);
			}
		}
	}

	public static void ExecuteEventAllPlayersConsole(String event, String Informatif, String AdditionalMessageError) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			ExecuteEvent(p, event, Informatif, AdditionalMessageError, false);
		}
	}
	
	// Colours and placeholders of a title or an action bar (of who: the player, or the player the message is about)
	private static String fill(String text, Player who) {
		text = MessageUtils.colourTheStuff(text);
		text = PlaceHolders.ReplaceMainplaceholderP(text, who);
		if (HooksManager.papi()) {
			text = PlaceholderAPI.setPlaceholders(who, text);
		}
		return text;
	}

	// The number and the text of "[action[<number>]]: text"
	private static final class Timed {
		final int number;
		final String text;

		Timed(int number, String text) {
			this.number = number;
			this.text = text;
		}
	}

	// The default number when it is missing or wrong; the whole text when "]]:" is missing
	private static Timed timed(String rest, int byDefault) {
		int end = rest.indexOf("]]:");
		if (end < 0) {
			return new Timed(byDefault, rest);
		}

		String text = rest.substring(end + 3);
		if (text.startsWith(" ")) {
			text = text.substring(1);
		}
		int number = StringUtils.toInt(rest.substring(0, end), byDefault);
		return new Timed(number < 0 ? byDefault : number, text);
	}

	// A title, and its subtitle after //n: none, a blank subtitle
	private static void title(Player p, String text, int stay) {
		String[] parts = text.split("//n", 2);
		String subtitle = parts.length > 1 && !parts[1].isEmpty() ? parts[1] : " ";
		Titles.sendTitle(p, 20, stay, 75, parts[0], subtitle);
	}

	// [effect[<amplifier>]]: <effect>, an amplifier 0 when it is wrong
	private static void effect(Player p, String rest, String additionalMessageError) {
		Timed timed = timed(rest, 0);
		p.addPotionEffect(new PotionEffect(XParse.potion(timed.text.trim(), additionalMessageError), 1999999999, Math.min(timed.number, 255)));
	}
}
