package fr.dianox.hawn.modules.holograms;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * /hawn hologram (since 1.6): create, delete, movehere, addline, setline, removeline, list, enable, disable.
 */
public final class HologramCommand {

	private HologramCommand() {}

	private static final List<String> SUBS = Arrays.asList("create", "delete", "movehere", "addline", "setline", "removeline", "list", "enable", "disable");

	public static void run(CommandSender sender, String[] args) {
		String sub = args.length > 1 ? args[1].toLowerCase(Locale.ROOT) : "";

		if (!Holograms.available()) {
			say(sender, "Holograms.No-PacketEvents");
			return;
		}

		if (sub.equals("list")) {
			if (Holograms.all().isEmpty() && Holograms.off().isEmpty()) {
				say(sender, "Holograms.None");
			} else {
				// The holograms turned off (Enable: false) are listed too, with their tag
				List<String> names = new ArrayList<>(Hologram.names(Holograms.all()));
				String tag = String.join(" ", ConfigMAdmin.getConfig(sender).getStringList("Holograms.Off-Tag"));
				for (String n : Hologram.names(Holograms.off())) {
					names.add(n + " " + tag);
				}
				say(sender, "Holograms.List", String.join("&7, &e", names));
			}
			return;
		}

		if (!SUBS.contains(sub) || args.length < 3) {
			say(sender, "Holograms.Usage");
			return;
		}

		String name = args[2].toLowerCase(Locale.ROOT);
		Hologram h = Holograms.all().get(name);
		boolean disabled = false;
		if (h == null) {
			h = Holograms.off().get(name);
			disabled = h != null;
		}

		switch (sub) {
			case "create": {
				if (!(sender instanceof Player)) {
					say(sender, "Holograms.Usage");
					return;
				}
				if (!name.matches("[a-z0-9_-]+")) {
					say(sender, "Holograms.Bad-Name");
					return;
				}
				File file = new File(Hologram.folder(), name + ".yml");
				if (h != null || file.exists()) {
					say(sender, "Holograms.Exists", name);
					return;
				}
				h = new Hologram(name, file);
				place(h, (Player) sender);
				h.lines = new ArrayList<>();
				h.lines.add(args.length > 3 ? join(args, 3) : "&e" + name);
				if (save(sender, h)) {
					Holograms.put(h);
					say(sender, "Holograms.Created", name);
				}
				return;
			}
			case "delete": {
				if (h == null) {
					say(sender, "Holograms.Not-Found", name);
					return;
				}
				if (disabled) {
					Holograms.forget(name);
				} else {
					Holograms.remove(name);
				}
				if (!h.file.delete()) {
					h.file.deleteOnExit();
				}
				say(sender, "Holograms.Deleted", name);
				return;
			}
			default:
				break;
		}

		if (h == null) {
			say(sender, "Holograms.Not-Found", name);
			return;
		}

		switch (sub) {
			case "enable":
			case "disable": {
				boolean on = sub.equals("enable");
				if (on == disabled) {
					try {
						Holograms.setEnabled(h, on);
					} catch (Exception e) {
						sender.sendMessage("§c[Hawn] Holograms/" + h.file.getName() + " could not be written: " + e.getMessage());
						return;
					}
				}
				say(sender, on ? "Holograms.Enabled" : "Holograms.Disabled", name);
				return;
			}
			case "movehere":
				if (!(sender instanceof Player)) {
					say(sender, "Holograms.Usage");
					return;
				}
				place(h, (Player) sender);
				if (save(sender, h)) {
					if (!disabled) {
						Holograms.put(h);
					}
					say(sender, "Holograms.Moved", name);
				}
				return;
			case "addline":
				if (args.length < 4) {
					say(sender, "Holograms.Usage");
					return;
				}
				h.lines.add(join(args, 3));
				break;
			case "setline":
			case "removeline": {
				Integer line = args.length > 3 ? MessageUtils.number(sender, args[3], 1) : null;
				if (line == null) {
					return;
				}
				if (line < 1 || line > h.lines.size()) {
					say(sender, "Holograms.Bad-Line", name, String.valueOf(line));
					return;
				}
				if (sub.equals("setline")) {
					if (args.length < 5) {
						say(sender, "Holograms.Usage");
						return;
					}
					h.lines.set(line - 1, join(args, 4));
				} else {
					h.lines.remove(line - 1);
				}
				break;
			}
			default:
				return;
		}

		if (save(sender, h)) {
			if (!disabled) {
				Holograms.put(h);
			}
			say(sender, "Holograms.Changed", name, String.valueOf(h.lines.size()));
		}
	}

	// Where the player is, at the height of their eyes
	private static void place(Hologram h, Player p) {
		Location l = p.getLocation();
		h.world = l.getWorld().getName();
		h.x = round(l.getX());
		h.y = round(l.getY() + p.getEyeHeight());
		h.z = round(l.getZ());
	}

	private static double round(double d) {
		return Math.round(d * 100.0) / 100.0;
	}

	private static String join(String[] args, int from) {
		return String.join(" ", Arrays.copyOfRange(args, from, args.length));
	}

	private static boolean save(CommandSender sender, Hologram h) {
		try {
			Hologram.folder().mkdirs();
			h.save();
			return true;
		} catch (Exception e) {
			sender.sendMessage("§c[Hawn] Holograms/" + h.file.getName() + " could not be written: " + e.getMessage());
			return false;
		}
	}

	public static List<String> tab(String[] args) {
		List<String> out = new ArrayList<>();
		if (args.length == 2) {
			for (String s : SUBS) {
				if (s.startsWith(args[1].toLowerCase(Locale.ROOT))) out.add(s);
			}
		} else if (args.length == 3 && !args[1].equalsIgnoreCase("create") && !args[1].equalsIgnoreCase("list")) {
			List<String> names = new ArrayList<>(Hologram.names(Holograms.all()));
			names.addAll(Hologram.names(Holograms.off()));
			for (String name : names) {
				if (name.startsWith(args[2].toLowerCase(Locale.ROOT))) out.add(name);
			}
		}
		return out;
	}

	private static void say(CommandSender sender, String key, String... args) {
		for (String msg : ConfigMAdmin.getConfig(sender).getStringList(key)) {
			for (int i = 0; i < args.length; i++) {
				msg = msg.replace("%arg " + (i + 1) + "%", ConfigEventUtils.noAction(args[i]));
			}
			if (sender instanceof Player) {
				ConfigEventUtils.ExecuteEvent((Player) sender, msg, "", "", false);
			} else {
				MessageUtils.ConsoleMessages(msg);
			}
		}
	}
}
