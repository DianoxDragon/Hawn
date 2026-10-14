package fr.dianox.hawn.utility;

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
		String perm;
        String world;
        
        if (event.startsWith("<world>") && event.contains("</world>")) {
        	world = StringUtils.substringBetween(event, "<world>", "</world>");
        	event = event.replace("<world>" + world + "</world> ", "");

            if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                return;
            }
        }
        
        if (event.contains("<perm>") && event.contains("</perm>")) {
            perm = StringUtils.substringBetween(event, "<perm>", "</perm>");
            event = event.replace("<perm>" + perm + "</perm> ", "");

            if (!p.hasPermission(perm)) {
            	return;
            }
        }

        // [delay[ticks]]: and [chance[percent]]: in front of the line
        if (Actions.prefix(p, event, rest -> ExecuteEvent(p, rest, Informatif, AdditionalMessageError, Console))) {
        	return;
        }

        if (Actions.run(p, event, AdditionalMessageError)) {
        	return;
        }
        
        if (event.startsWith("[command-player]: ")) {
            event = event.replace("[command-player]: ", "");
            event = event.replace("%player%", p.getName());

            p.performCommand(event);
        } else if (event.startsWith("[FWLU]: ")) {
        	if (event.startsWith("[FWLU]: ")) {
				
        		event = event.replace("[FWLU]: ", "");
				
				OtherUtils.Fireworkmethod(p, event);
			}
        } else if (event.startsWith("[command-console]: ")) {
            event = event.replace("[command-console]: ", "");
            event = event.replace("%player%", p.getName());

            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), event);
        } else if (event.startsWith("[gamemode-survival]")) {
            p.setGameMode(GameMode.SURVIVAL);
        } else if (event.startsWith("[gamemode-creative]")) {
            p.setGameMode(GameMode.CREATIVE);
        } else if (event.startsWith("[gamemode-adventure]")) {
            p.setGameMode(GameMode.ADVENTURE);
        } else if (event.startsWith("[gamemode-spectator]")) {
            p.setGameMode(GameMode.SPECTATOR);
        } else if (event.startsWith("[customcommand-player]: ")) {
        	event = event.replace("[customcommand-player]: ", "");
        	event = event.replace("%player%", p.getName());
            
        	OnCommandEvent.executeCustomCommand(event, p);
        } else if (event.startsWith("[ping]")) {
            for (String event1: ConfigMMsg.getConfig().getStringList("Ping.Self")) {
                MessageUtils.ClassicMessages(event1, p);
            }
        } else if (event.startsWith("[spawn]: ")) {
            event = event.replace("[spawn]: ", "");
            SpawnUtils.teleportToSpawn(p, event);
        } else if (event.startsWith("[warp]: ")) {
            event = event.replace("[warp]: ", "");
            WarpCommand.onTp(p, event);
        } else if (event.startsWith("[bungee]: ")) {
            event = event.replace("[bungee]: ", "");
	        Main.getInstance().getBungApi().ConnectOthers(event, p);
        } else if (event.startsWith("[effect[")) {
            // [effect[<amplifier>]]: <effect>; a wrong amplifier or a missing "]]: " no longer stops the line
            effect(p, event.substring("[effect[".length()), AdditionalMessageError);
        } else if (event.startsWith("[effectclear]: ")) {
            event = event.replace("[effectclear]: ", "");
            
            p.removePotionEffect(XParse.potion(event, AdditionalMessageError));
        } else if (event.startsWith("[effectclearall]")) {
            for (PotionEffect effect: p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
        } else if (event.startsWith("[send-title]: ")) {
            // A title alone (no //n, or nothing after it) has a blank subtitle
            title(p, fill(event.substring("[send-title]: ".length()), p), 150);
        } else if (event.startsWith("[send-title[")) {
            // [send-title[<ticks>]]: title//nsubtitle; a wrong number or a missing "]]: ": 150 ticks
            Timed timed = timed(event.substring("[send-title[".length()), 150);
            title(p, fill(timed.text, p), timed.number);
        } else if (event.startsWith("[send-actionbar]: ")) {
            ActionBar.sendActionBar(p, fill(event.substring("[send-actionbar]: ".length()), p));
        } else if (event.startsWith("[send-actionbar[")) {
            Timed timed = timed(event.substring("[send-actionbar[".length()), 60);
            ActionBar.sendActionBar(Main.getInstance(), p, fill(timed.text, p), timed.number);
        } else if (event.startsWith("[sounds]: ")) {
            event = event.replace("[sounds]: ", "");
            p.playSound(p.getLocation(), XParse.sound(event, AdditionalMessageError), 1, 1);
        } else {
        	if (Console) {
        		if (AdditionalMessageError.contains("STRICTSCONSOLE")) {
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

	public static void ExecuteEventTargetPlaceHolder(Player p, Player target, String event, String Informatif, String AdditionalMessageError, Boolean Console) {
		String perm;
		String world;

		if (event.startsWith("<world>") && event.contains("</world>")) {
			world = StringUtils.substringBetween(event, "<world>", "</world>");
			event = event.replace("<world>" + world + "</world> ", "");

			if (!p.getWorld().getName().equalsIgnoreCase(world)) {
				return;
			}
		}

		if (event.contains("<perm>") && event.contains("</perm>")) {
			perm = StringUtils.substringBetween(event, "<perm>", "</perm>");
			event = event.replace("<perm>" + perm + "</perm> ", "");

			if (!p.hasPermission(perm)) {
				return;
			}
		}

		if (Actions.prefix(p, event, rest -> ExecuteEventTargetPlaceHolder(p, target, rest, Informatif, AdditionalMessageError, Console))) {
			return;
		}

		if (Actions.run(p, event, AdditionalMessageError)) {
			return;
		}

		if (event.startsWith("[command-player]: ")) {
			event = event.replace("[command-player]: ", "");
			event = event.replace("%player%", p.getName());

			p.performCommand(event);
		} else if (event.startsWith("[FWLU]: ")) {
			if (event.startsWith("[FWLU]: ")) {

				event = event.replace("[FWLU]: ", "");

				OtherUtils.Fireworkmethod(p, event);
			}
		} else if (event.startsWith("[command-console]: ")) {
			event = event.replace("[command-console]: ", "");
			event = event.replace("%player%", p.getName());

			Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), event);
		} else if (event.startsWith("[gamemode-survival]")) {
			p.setGameMode(GameMode.SURVIVAL);
		} else if (event.startsWith("[gamemode-creative]")) {
			p.setGameMode(GameMode.CREATIVE);
		} else if (event.startsWith("[gamemode-adventure]")) {
			p.setGameMode(GameMode.ADVENTURE);
		} else if (event.startsWith("[gamemode-spectator]")) {
			p.setGameMode(GameMode.SPECTATOR);
		} else if (event.startsWith("[customcommand-player]: ")) {
			event = event.replace("[customcommand-player]: ", "");
			event = event.replace("%player%", p.getName());

			OnCommandEvent.executeCustomCommand(event, p);
		} else if (event.startsWith("[ping]")) {
			for (String event1: ConfigMMsg.getConfig().getStringList("Ping.Self")) {
				MessageUtils.ClassicMessages(event1, p);
			}
		} else if (event.startsWith("[spawn]: ")) {
			event = event.replace("[spawn]: ", "");
			SpawnUtils.teleportToSpawn(p, event);
		} else if (event.startsWith("[warp]: ")) {
			event = event.replace("[warp]: ", "");
			WarpCommand.onTp(p, event);
		} else if (event.startsWith("[bungee]: ")) {
			event = event.replace("[bungee]: ", "");
			Main.getInstance().getBungApi().ConnectOthers(event, p);
		} else if (event.startsWith("[effect[")) {
			// [effect[<amplifier>]]: <effect>; a wrong amplifier or a missing "]]: " no longer stops the line
			effect(p, event.substring("[effect[".length()), AdditionalMessageError);
		} else if (event.startsWith("[effectclear]: ")) {
			event = event.replace("[effectclear]: ", "");

			p.removePotionEffect(XParse.potion(event, AdditionalMessageError));
		} else if (event.startsWith("[effectclearall]")) {
			for (PotionEffect effect: p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}
		} else if (event.startsWith("[send-title]: ")) {
			// A title alone (no //n, or nothing after it) has a blank subtitle
			title(p, fill(event.substring("[send-title]: ".length()), target), 150);
		} else if (event.startsWith("[send-title[")) {
			// [send-title[<ticks>]]: title//nsubtitle; a wrong number or a missing "]]: ": 150 ticks
			Timed timed = timed(event.substring("[send-title[".length()), 150);
			title(p, fill(timed.text, target), timed.number);
		} else if (event.startsWith("[send-actionbar]: ")) {
			ActionBar.sendActionBar(p, fill(event.substring("[send-actionbar]: ".length()), target));
		} else if (event.startsWith("[send-actionbar[")) {
			Timed timed = timed(event.substring("[send-actionbar[".length()), 60);
			ActionBar.sendActionBar(Main.getInstance(), p, fill(timed.text, target), timed.number);
		} else if (event.startsWith("[sounds]: ")) {
			event = event.replace("[sounds]: ", "");
			p.playSound(p.getLocation(), XParse.sound(event, AdditionalMessageError), 1, 1);
		} else {
			event = PlaceHolders.ReplaceMainplaceholderP(event, target);
			if (HooksManager.papi()) {
				event = PlaceholderAPI.setPlaceholders(target, event);
			}
			if (HooksManager.mvdw()) {
				event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, event);
			}

			if (Console) {
				if (AdditionalMessageError.contains("STRICTSCONSOLE")) {
					MessageUtils.ConsoleMessages(event);
				} else {
					MessageUtils.ClassicMessagesConsoleSupport(event, p);
				}
			} else {
				MessageUtils.ClassicMessages(event, p);
			}
		}
	}

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
		if (HooksManager.mvdw()) {
			text = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(who, text);
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
