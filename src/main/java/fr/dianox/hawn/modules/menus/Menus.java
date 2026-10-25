package fr.dianox.hawn.modules.menus;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.Debug;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.gui.HawnMenu;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * The menus of the Menus/ folder (since 1.5): one file per menu, opened by a command, a join item or an action
 * ([menu]: &lt;name&gt;). Recognized by their type (HawnMenu), never by their title.
 */
public final class Menus implements Listener {

	private static volatile Map<String, Menu> menus = Collections.emptyMap();
	private static volatile Map<String, String> commands = Collections.emptyMap();
	// The menus opened before the current one, the last one on top, for [back] (117: a stack, not only the last one)
	private static final Map<UUID, java.util.Deque<String>> previous = new HashMap<>();

	/**
	 * What an open menu needs: the menu, its player, the item shown in each slot, the refresh task.
	 */
	static final class Session {
		final Menu menu;
		final Player player;
		final Map<Integer, MenuItem> shown = new HashMap<>();
		// The slots of the Fill item, and what each animated slot shows (to send only what changed)
		final java.util.Set<Integer> filled = new java.util.HashSet<>();
		final Map<Integer, String> looks = new HashMap<>();
		BukkitTask task;
		BukkitTask animation;
		Inventory inventory;
		String title;
		boolean switching;

		Session(Menu menu, Player player) {
			this.menu = menu;
			this.player = player;
		}
	}

	/* ------------------------------------------------------------------ loading */

	public static File folder() {
		return new File(Main.getInstance().getDataFolder(), "Menus");
	}

	/**
	 * At startup and /hawn reload: the example menus the first time, then every file of Menus/. The open menus are closed.
	 */
	public static void load() {
		closeAll();

		File folder = folder();
		if (!folder.exists()) {
			DefaultMenus.write(folder);
		}

		Map<String, Menu> loaded = new LinkedHashMap<>();
		Map<String, String> cmds = new HashMap<>();
		File[] files = folder.listFiles((dir, name) -> name.toLowerCase(Locale.ROOT).endsWith(".yml"));
		if (files != null) {
			java.util.Arrays.sort(files);
			for (File f : files) {
				String name = f.getName().substring(0, f.getName().length() - 4).toLowerCase(Locale.ROOT);
				YamlConfiguration c = new YamlConfiguration();
				try {
					c.load(f);
				} catch (Exception e) {
					// Listed by /hawn debug
					Debug.hidden("Menus/" + f.getName(), e);
					continue;
				}
				Menu menu = new Menu(name, c);
				loaded.put(name, menu);
				for (String command : menu.commands) {
					if (!command.isEmpty()) cmds.put(command, name);
				}
			}
		}
		menus = loaded;
		commands = cmds;
	}

	public static Map<String, Menu> all() {
		return menus;
	}

	public static boolean exists(String name) {
		return name != null && menus.containsKey(name.trim().toLowerCase(Locale.ROOT));
	}

	/* ------------------------------------------------------------------ opening */

	/**
	 * Opens a menu for a player, with its permission and its worlds. False when it is not opened (a message says why).
	 */
	public static boolean open(Player p, String name) {
		return open(p, name, false);
	}

	/**
	 * @param back opened by [back]: the menu closed now is not put on the stack of the menus to go back to
	 */
	private static boolean open(Player p, String name, boolean back) {
		Menu menu = name == null ? null : menus.get(name.trim().toLowerCase(Locale.ROOT));
		if (menu == null) {
			say(p, "Menus.Not-Found", name == null ? "" : name.trim());
			return false;
		}

		if (menu.permission != null && !menu.permission.isEmpty() && !p.hasPermission(menu.permission)) {
			MessageUtils.MessageNoPermission(p, menu.permission);
			return false;
		}

		if (!menu.worlds.isEmpty()) {
			boolean in = false;
			for (String w : menu.worlds) {
				if (w.equalsIgnoreCase(p.getWorld().getName())) in = true;
			}
			if (!in) {
				say(p, "Menus.Not-In-This-World", menu.name);
				return false;
			}
		}

		Session old = session(p.getOpenInventory().getTopInventory());
		if (old != null) {
			old.switching = true;
			if (!back) {
				java.util.Deque<String> stack = previous.computeIfAbsent(p.getUniqueId(), id -> new java.util.ArrayDeque<>());
				stack.push(old.menu.name);
				// A loop of [menu] actions doesn't grow it for ever
				while (stack.size() > 20) stack.removeLast();
			}
		} else {
			previous.remove(p.getUniqueId());
		}

		Session session = new Session(menu, p);
		session.title = MenuItems.text(p, menu.animate(menu.title));
		session.inventory = HawnMenu.create(menu.size, menu.type, session.title, session);
		fill(session, false);
		p.openInventory(session.inventory);

		run(p, menu.openActions);

		if (menu.refresh > 0 && menu.hasUpdates()) {
			session.task = Tasks.timer(() -> {
				if (!p.isOnline() || session(p.getOpenInventory().getTopInventory()) != session) {
					Tasks.cancel(session.task);
					return;
				}
				fill(session, true);
			}, menu.refresh, menu.refresh);
		}

		// The animations: only what changed is sent again
		if (menu.animationTicks > 0) {
			session.animation = Tasks.timer(() -> animate(session), menu.animationTicks, menu.animationTicks);
		}
		return true;
	}

	/**
	 * The next frame of the animations of an open menu: the title (1.20+), the animated items and the Fill item.
	 */
	static void animate(Session s) {
		Player p = s.player;
		if (!p.isOnline() || session(p.getOpenInventory().getTopInventory()) != s) {
			Tasks.cancel(s.animation);
			return;
		}

		if (s.menu.titleAnimated) {
			String title = MenuItems.text(p, s.menu.animate(s.menu.title));
			if (!title.equals(s.title) && Titles.set(p, title)) {
				s.title = title;
			}
		}

		for (Map.Entry<Integer, MenuItem> entry : s.shown.entrySet()) {
			if (entry.getValue().animated) {
				show(s, entry.getKey(), entry.getValue());
			}
		}
		if (s.menu.fill != null && s.menu.fill.animated) {
			for (int slot : s.filled) {
				show(s, slot, s.menu.fill);
			}
		}
	}

	// The item again, sent only when its look changed
	private static void show(Session s, int slot, MenuItem item) {
		ItemStack built = MenuItems.build(s.player, item);
		org.bukkit.inventory.meta.ItemMeta meta = built.getItemMeta();
		String look = built.getType() + "|" + (meta == null ? "" : meta.getDisplayName() + "|" + meta.getLore());
		if (!look.equals(s.looks.get(slot))) {
			s.looks.put(slot, look);
			s.inventory.setItem(slot, built);
		}
	}

	/**
	 * The items of each slot: the first one (by priority) whose View-Requirements are met. With onlyUpdates, only the
	 * slots with an item Update: true are built again.
	 */
	static void fill(Session s, boolean onlyUpdates) {
		for (Map.Entry<Integer, List<MenuItem>> entry : s.menu.bySlot.entrySet()) {
			if (onlyUpdates && !anyUpdate(entry.getValue())) continue;

			MenuItem chosen = null;
			for (MenuItem item : entry.getValue()) {
				if (Requirements.all(s.player, item.view)) {
					chosen = item;
					break;
				}
			}

			int slot = entry.getKey();
			s.looks.remove(slot);
			if (chosen == null) {
				s.shown.remove(slot);
				s.inventory.setItem(slot, s.menu.fill == null ? null : MenuItems.build(s.player, s.menu.fill));
				if (s.menu.fill != null) s.filled.add(slot);
			} else {
				s.shown.put(slot, chosen);
				s.filled.remove(slot);
				s.inventory.setItem(slot, MenuItems.build(s.player, chosen));
			}
		}

		if (!onlyUpdates && s.menu.fill != null) {
			ItemStack fill = MenuItems.build(s.player, s.menu.fill);
			for (int i = 0; i < s.inventory.getSize(); i++) {
				if (s.inventory.getItem(i) == null) {
					s.inventory.setItem(i, fill.clone());
					s.filled.add(i);
				}
			}
		}
	}

	private static boolean anyUpdate(List<MenuItem> items) {
		for (MenuItem item : items) {
			if (item.update) return true;
		}
		return false;
	}

	static Session session(Inventory inventory) {
		Object data = HawnMenu.dataOf(inventory);
		return data instanceof Session ? (Session) data : null;
	}

	/**
	 * A head was found in the background: the open menus show it.
	 */
	static void refreshOpen() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Session s = session(p.getOpenInventory().getTopInventory());
			if (s != null) fill(s, false);
		}
	}

	private static void closeAll() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (session(p.getOpenInventory().getTopInventory()) != null) {
				p.closeInventory();
			}
		}
	}

	/* ------------------------------------------------------------------ the actions [menu], [close], [refresh], [back] */

	/**
	 * [menu]: &lt;name&gt;. One tick later: a menu is never opened inside the click of another one.
	 */
	public static void openLater(Player p, String name) {
		Tasks.later(() -> {
			if (p.isOnline()) open(p, name);
		}, 1L);
	}

	public static void closeLater(Player p) {
		Tasks.later(() -> {
			if (p.isOnline() && session(p.getOpenInventory().getTopInventory()) != null) p.closeInventory();
		}, 1L);
	}

	public static void refresh(Player p) {
		Session s = session(p.getOpenInventory().getTopInventory());
		if (s != null) fill(s, false);
	}

	/**
	 * [back]: the menu opened before this one (A, B, C: C goes back to B, then B to A), or closed when there is none.
	 */
	public static void back(Player p) {
		java.util.Deque<String> stack = previous.get(p.getUniqueId());
		String name = stack == null ? null : stack.poll();
		if (name != null) {
			Tasks.later(() -> {
				if (p.isOnline()) open(p, name, true);
			}, 1L);
		} else {
			closeLater(p);
		}
	}

	private static void run(Player p, List<String> actions) {
		for (String action : actions) {
			ConfigEventUtils.ExecuteEvent(p, action, "none", "Menus", false);
		}
	}

	/* ------------------------------------------------------------------ events */

	@EventHandler(priority = EventPriority.HIGH)
	public void onClick(InventoryClickEvent e) {
		Session s = session(e.getView().getTopInventory());
		if (s == null || !(e.getWhoClicked() instanceof Player)) return;

		// Every click is cancelled (HawnMenu.Protection): the actions only for a slot of the menu
		e.setCancelled(true);
		if (e.getRawSlot() < 0 || e.getRawSlot() >= s.inventory.getSize()) return;
		// The second click of a double click comes with its own two clicks
		if (e.getClick() == ClickType.DOUBLE_CLICK) return;

		MenuItem item = s.shown.get(e.getRawSlot());
		if (item == null) return;

		Player p = (Player) e.getWhoClicked();
		if (!Requirements.all(p, item.click)) {
			run(p, item.deny);
			return;
		}
		run(p, item.actionsFor(e.getClick()));
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Session s = session(e.getInventory());
		if (s == null) return;
		Tasks.cancel(s.task);
		Tasks.cancel(s.animation);
		if (!s.switching && e.getPlayer() instanceof Player) {
			run((Player) e.getPlayer(), s.menu.closeActions);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		previous.remove(e.getPlayer().getUniqueId());
	}

	/**
	 * Open.Commands: /servers opens the menu, before the commands of the server and of the other plugins.
	 */
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (commands.isEmpty()) return;
		String message = e.getMessage();
		int space = message.indexOf(' ');
		String label = (space < 0 ? message.substring(1) : message.substring(1, space)).toLowerCase(Locale.ROOT);
		String menu = commands.get(label);
		if (menu != null) {
			e.setCancelled(true);
			open(e.getPlayer(), menu);
		}
	}

	/* ------------------------------------------------------------------ messages */

	static void say(Player p, String key, String arg) {
		for (String msg : ConfigMMsg.getConfig().getStringList(key)) {
			ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg%", ConfigEventUtils.noAction(arg)), "", "", false);
		}
	}

	/* ------------------------------------------------------------------ /hawn debug */

	/**
	 * The mistakes of a file of Menus/ that the YAML alone doesn't show: slots outside the menu, unknown materials,
	 * conditions written wrong, [menu]: to a menu that doesn't exist.
	 */
	public static List<String> check(String file, YamlConfiguration c) {
		List<String> errors = new ArrayList<>();
		InventoryTypeCheck type = new InventoryTypeCheck(c);

		// {anim_<name>} without its animation in Animations
		java.util.Set<String> known = new java.util.HashSet<>();
		org.bukkit.configuration.ConfigurationSection anims = c.getConfigurationSection("Animations");
		if (anims != null) {
			for (String key : anims.getKeys(false)) {
				known.add(key.toLowerCase(Locale.ROOT));
				if (anims.getStringList(key + ".text").isEmpty()) errors.add(file + ": Animations." + key + " has no text");
			}
		}
		for (String key : c.getKeys(true)) {
			List<String> texts = c.isList(key) ? c.getStringList(key) : c.isString(key) ? Collections.singletonList(c.getString(key)) : Collections.<String>emptyList();
			if (key.startsWith("Animations.")) continue;
			for (String text : texts) {
				int start = text.indexOf("{anim_");
				while (start >= 0) {
					int end = text.indexOf('}', start);
					if (end < 0) break;
					String name = text.substring(start + 6, end);
					if (!known.contains(name.toLowerCase(Locale.ROOT))) errors.add(file + ": " + key + " uses {anim_" + name + "}, but there is no Animations." + name);
					start = text.indexOf("{anim_", end);
				}
			}
		}
		org.bukkit.configuration.ConfigurationSection items = c.getConfigurationSection("Items");
		if (items == null) {
			errors.add(file + ": Items is missing (a menu without items)");
			return errors;
		}
		for (String key : items.getKeys(false)) {
			org.bukkit.configuration.ConfigurationSection item = items.getConfigurationSection(key);
			if (item == null) continue;
			List<String> parts = new ArrayList<>();
			if (item.isList("Slots")) {
				for (Object o : item.getList("Slots")) parts.add(String.valueOf(o));
			} else if (item.isSet("Slots")) {
				Collections.addAll(parts, String.valueOf(item.get("Slots")).split(","));
			} else if (item.isSet("Slot")) {
				parts.add(String.valueOf(item.get("Slot")));
			} else {
				errors.add(file + ": Items." + key + " has no Slot");
			}
			for (String part : parts) {
				int[] range = MenuItem.range(part);
				if (range == null) {
					errors.add(file + ": Items." + key + " slot " + part.trim() + " is not a number");
				} else if (range[0] < 0 || range[1] >= type.size) {
					errors.add(file + ": Items." + key + " slot " + part.trim() + " is outside the menu (0 to " + (type.size - 1) + ")");
				}
			}
			for (String path : new String[] {"View-Requirements", "Click-Requirements", "Click-Requirements.Conditions"}) {
				for (String condition : item.getStringList(path)) {
					String problem = Requirements.problem(condition);
					if (problem != null) errors.add(file + ": Items." + key + "." + path + " = " + condition + " (" + problem + ")");
				}
			}
		}
		return errors;
	}

	private static final class InventoryTypeCheck {
		final int size;

		InventoryTypeCheck(YamlConfiguration c) {
			org.bukkit.event.inventory.InventoryType type = Menu.type(c.getString("Type", null));
			size = type == null ? 9 * Math.max(1, Math.min(6, c.getInt("Rows", 3))) : type.getDefaultSize();
		}
	}
}
