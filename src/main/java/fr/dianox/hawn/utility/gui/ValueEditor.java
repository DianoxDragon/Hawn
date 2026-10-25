package fr.dianox.hawn.utility.gui;

import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.config.configs.commands.HawnCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.AdminPanelConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The values of a file in the admin panel (since 1.5.2), more than yes or no: a number (clicks to add or remove), a
 * choice among known values (a click for the next one), a text (typed in the chat). The yes / no values keep their wool.
 * Made to be used again by the other menus (Edit Player in 1.5.3).
 */
public final class ValueEditor {

	public enum Kind { NUMBER, CHOICE, TEXT }

	// The texts that are a choice among a few values, for the options whose name ends this way (a database named "Hawn"
	// is a text, Ranks.Source: HAWN a choice): a click goes to the next value
	private static final String[][] CHOICES = {
		{"Enable", "AUTO", "true", "false"},
		{"Block-Drop", "ALL", "JOIN-ITEMS", "NONE"},
		{"Source", "HAWN", "LUCKPERMS"},
		{"GameMode", "SURVIVAL", "CREATIVE", "ADVENTURE", "SPECTATOR"},
		{"Environment", "NORMAL", "NETHER", "THE_END"}
	};

	private static final Material NUMBER = XMaterial.LIGHT_BLUE_WOOL.parseMaterial();
	private static final Material CHOICE = XMaterial.YELLOW_WOOL.parseMaterial();
	private static final Material TEXT = XMaterial.WHITE_WOOL.parseMaterial();

	// The players typing a new text in the chat: the file, the option and how to open the page again
	private static final class Pending {
		final String file;
		final String key;
		final Runnable reopen;

		Pending(String file, String key, Runnable reopen) {
			this.file = file;
			this.key = key;
			this.reopen = reopen;
		}
	}

	private static final Map<UUID, Pending> typing = new ConcurrentHashMap<>();

	private ValueEditor() {}

	/**
	 * What kind of value this option is, null for a yes / no (the wool of before), a list or a section.
	 */
	public static Kind kind(YamlConfiguration cfg, String key) {
		if (cfg.isBoolean(key) || cfg.isConfigurationSection(key) || cfg.isList(key)) {
			return null;
		}
		if (cfg.isInt(key) || cfg.isLong(key) || cfg.isDouble(key)) {
			return Kind.NUMBER;
		}
		if (cfg.isString(key)) {
			return choices(key, cfg.getString(key)) != null ? Kind.CHOICE : Kind.TEXT;
		}
		return null;
	}

	// The values this option can take, null for a text
	private static String[] choices(String key, String value) {
		String last = key.substring(key.lastIndexOf('.') + 1);
		for (String[] set : CHOICES) {
			if (!last.toLowerCase(java.util.Locale.ROOT).endsWith(set[0].toLowerCase(java.util.Locale.ROOT))) continue;
			for (int i = 1; i < set.length; i++) {
				if (set[i].equalsIgnoreCase(value.trim())) return java.util.Arrays.copyOfRange(set, 1, set.length);
			}
		}
		return null;
	}

	public static boolean isEditorItem(Material material) {
		return material == NUMBER || material == CHOICE || material == TEXT;
	}

	/**
	 * The item of an option in the page of its file.
	 */
	public static ItemStack item(YamlConfiguration cfg, String key) {
		Kind kind = kind(cfg, key);
		String value = String.valueOf(cfg.get(key));
		Material material;
		String lorePath;
		String step = "1";
		String bigStep = "10";
		String choiceList = "";

		if (kind == Kind.NUMBER) {
			material = NUMBER;
			lorePath = "Edit.File.Number.Lore";
			if (cfg.isDouble(key)) {
				step = "0.1";
				bigStep = "1";
			}
		} else if (kind == Kind.CHOICE) {
			material = CHOICE;
			lorePath = "Edit.File.Choice.Lore";
			choiceList = String.join(", ", choices(key, value));
		} else {
			material = TEXT;
			lorePath = "Edit.File.Text.Lore";
			if (value.length() > 40) value = value.substring(0, 40) + "...";
		}

		List<String> lore = new ArrayList<>();
		for (String line : AdminPanelConfig.getConfig().getStringList(lorePath)) {
			lore.add(MessageUtils.colourTheStuff(line.replace("%choices%", choiceList).replace("%step%", step).replace("%bigstep%", bigStep))
					.replace("%value%", value));
		}

		ItemStack stack = new ItemStack(material);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName("§b" + key);
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}

	/**
	 * A click on the item of an option: a number changes, a choice goes to the next one, a text is asked in the chat.
	 */
	public static void click(InventoryClickEvent e, Player p, String file, YamlConfiguration cfg, File f, Runnable reopen) {
		String key = e.getCurrentItem().getItemMeta().getDisplayName().replace("§b", "");
		Kind kind = kind(cfg, key);
		if (kind == null) {
			return;
		}

		if (kind == Kind.NUMBER) {
			int sign = e.isRightClick() ? -1 : 1;
			if (cfg.isDouble(key)) {
				double step = e.isShiftClick() ? 1 : 0.1;
				cfg.set(key, Math.round((cfg.getDouble(key) + sign * step) * 100.0) / 100.0);
			} else if (cfg.isLong(key) && !cfg.isInt(key)) {
				cfg.set(key, cfg.getLong(key) + sign * (e.isShiftClick() ? 10L : 1L));
			} else {
				cfg.set(key, cfg.getInt(key) + sign * (e.isShiftClick() ? 10 : 1));
			}
		} else if (kind == Kind.CHOICE) {
			String value = cfg.getString(key).trim();
			String[] set = choices(key, value);
			int at = 0;
			for (int i = 0; i < set.length; i++) {
				if (set[i].equalsIgnoreCase(value)) at = i;
			}
			cfg.set(key, set[(at + (e.isRightClick() ? set.length - 1 : 1)) % set.length]);
		} else {
			typing.put(p.getUniqueId(), new Pending(file, key, reopen));
			p.closeInventory();
			say(p, "Edit.File.Text.Type", key, String.valueOf(cfg.get(key)));
			return;
		}

		save(cfg, f);
		spy(p, key, file);
		reopen.run();
	}

	/**
	 * The chat (first listener): the new text of the option the player clicked. True when the message is taken.
	 */
	public static boolean captureChat(Player p, String text) {
		Pending pending = typing.remove(p.getUniqueId());
		if (pending == null) {
			return false;
		}

		Tasks.next(() -> {
			if (!p.isOnline()) return;

			if (text.trim().equalsIgnoreCase("cancel")) {
				say(p, "Edit.File.Text.Cancelled", pending.key, "");
			} else {
				File f = new File(fr.dianox.hawn.Main.getInstance().getDataFolder(), pending.file);
				YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
				cfg.set(pending.key, text);
				save(cfg, f);
				spy(p, pending.key, pending.file);
				say(p, "Edit.File.Text.Changed", pending.key, text);
			}
			pending.reopen.run();
		});
		return true;
	}

	public static void forget(Player p) {
		typing.remove(p.getUniqueId());
	}

	private static void save(YamlConfiguration cfg, File f) {
		try {
			cfg.save(f);
		} catch (IOException e) {
			Bukkit.getLogger().warning("[Hawn] " + f.getName() + " could not be saved: " + e.getMessage());
		}
	}

	private static void say(Player p, String path, String arg1, String arg2) {
		for (String msg : AdminPanelConfig.getConfig().getStringList(path)) {
			ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(arg1)).replace("%arg2%", ConfigEventUtils.noAction(arg2)), "", "", false);
		}
	}

	// The emergency mode: the staff and the console see what the admin panel changed, as for the yes / no values
	private static void spy(Player p, String key, String file) {
		if (!HawnCommandConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
			return;
		}
		for (Player all : Bukkit.getServer().getOnlinePlayers()) {
			if (all.hasPermission("hawn.urgent.spy.adminpanel")) {
				for (String msg : ConfigMAdmin.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
					ConfigEventUtils.ExecuteEvent(all, msg.replace("%player%", p.getName()).replace("%arg1%", key).replace("%arg2%", file), "", "", false);
				}
			}
		}
		for (String msg : ConfigMAdmin.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
			ConfigEventUtils.ExecuteEvent(p, msg.replace("%player%", p.getName()).replace("%arg1%", key).replace("%arg2%", file), "", "STRICTSCONSOLE", true);
		}
	}
}
