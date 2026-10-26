package fr.dianox.hawn.modules.admin;

import fr.dianox.hawn.command.commands.VanishCommand;
import fr.dianox.hawn.modules.onjoin.cji.SpecialItemPlayerVisibility;
import fr.dianox.hawn.modules.ranks.Ranks;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerVisibility;
import fr.dianox.hawn.utility.Skulls;
import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.gui.HawnMenu;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Edit Player: /hawn editplayer &lt;player&gt;, or a click in the player list of the admin panel. Since 1.5.3: the gamemode,
 * the fly, the speeds, the vanish, heal and feed, the inventory, the teleports both ways, the rank given by hand and who
 * the player sees. The fly, the vanish, heal and feed run the commands of Hawn (their permissions and their messages).
 */
public class EditPlayerGui implements Listener {

	private static final String TITLE = "§cEdit Player - ";

	private static final int HEAD = 4;
	private static final int GAMEMODE = 19;
	private static final int FLY = 20;
	private static final int WALK_SPEED = 21;
	private static final int FLY_SPEED = 22;
	private static final int VANISH = 23;
	private static final int HEAL = 24;
	private static final int CLEAR = 25;
	private static final int TP_TO = 29;
	private static final int TP_HERE = 30;
	private static final int RANK = 32;
	private static final int HIDER = 33;
	private static final int BACK = 53;

	private static final GameMode[] MODES = {GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR};
	// The wool of each gamemode, as before 1.5.3
	private static final String[] WOOLS = {"RED_WOOL", "ORANGE_WOOL", "GREEN_WOOL", "YELLOW_WOOL"};

	/**
	 * Opens the menu of the player p for the viewer (it was opened for p before 1.4.1).
	 */
	public static void OpenGui(Player viewer, Player p) {
		Inventory inv = HawnMenu.create(54, TITLE + p.getName(), HawnMenu.Type.EDIT_PLAYER);
		fill(inv, p);
		viewer.openInventory(inv);
	}

	// The items, from what the player has now
	private static void fill(Inventory inv, Player p) {
		ItemStack pane = createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial());
		for (int i = 36; i <= 52; i++) {
			inv.setItem(i, pane);
		}

		Ranks.Rank rank = Ranks.rankOf(p);
		List<String> head = new ArrayList<>();
		for (String line : ConfigMAdmin.getConfig().getStringList("Command.EditPlayer.Gui.Head.Lore")) {
			head.add(colour(line.replace("%world%", p.getWorld().getName()).replace("%gamemode%", p.getGameMode().name())
					.replace("%rank%", rank == null ? "-" : rank.name)));
		}
		inv.setItem(HEAD, createGuiItemSkull("§b" + p.getName(), p.getName(), (ArrayList<String>) head));

		int mode = modeIndex(p.getGameMode());
		inv.setItem(GAMEMODE, createGuiItemColor(text("Command.EditPlayer.Gui.Gamemode." + name(MODES[mode])),
				lines("Command.EditPlayer.Gui.Gamemode.Lore"), WOOLS[mode]));

		inv.setItem(FLY, createGuiItem(text("Command.EditPlayer.Gui.Fly.Item-Name").replace("%value%", onOff(flying(p))),
				lines("Command.EditPlayer.Gui.Toggle.Lore"), XMaterial.FEATHER.parseMaterial()));
		inv.setItem(WALK_SPEED, createGuiItem(text("Command.EditPlayer.Gui.Walk-Speed.Item-Name").replace("%value%", String.valueOf(level(p.getWalkSpeed()))),
				lines("Command.EditPlayer.Gui.Speed.Lore"), XMaterial.SUGAR.parseMaterial()));
		inv.setItem(FLY_SPEED, createGuiItem(text("Command.EditPlayer.Gui.Fly-Speed.Item-Name").replace("%value%", String.valueOf(level(p.getFlySpeed()))),
				lines("Command.EditPlayer.Gui.Speed.Lore"), XMaterial.PHANTOM_MEMBRANE.parseMaterial()));
		inv.setItem(VANISH, createGuiItem(text("Command.EditPlayer.Gui.Vanish.Item-Name").replace("%value%", onOff(VanishCommand.player_list_vanish.contains(p))),
				lines("Command.EditPlayer.Gui.Toggle.Lore"), XMaterial.ENDER_EYE.parseMaterial()));
		inv.setItem(HEAL, createGuiItem(text("Command.EditPlayer.Gui.Heal.Item-Name"), lines("Command.EditPlayer.Gui.Heal.Lore"), XMaterial.GOLDEN_APPLE.parseMaterial()));
		inv.setItem(CLEAR, createGuiItem(text("Command.EditPlayer.Gui.ClearInv.Item-Name"), single("Command.EditPlayer.Gui.ClearInv.LeftClick"), XMaterial.BUCKET.parseMaterial()));

		inv.setItem(TP_TO, createGuiItem(text("Command.EditPlayer.Gui.Teleport.Item-Name"), single("Command.EditPlayer.Gui.Teleport.LeftClick"), XMaterial.ENDER_PEARL.parseMaterial()));
		inv.setItem(TP_HERE, createGuiItem(text("Command.EditPlayer.Gui.Teleport-Here.Item-Name"), single("Command.EditPlayer.Gui.Teleport-Here.LeftClick"), XMaterial.COMPASS.parseMaterial()));

		String hand = Ranks.byHandOf(p.getName());
		inv.setItem(RANK, createGuiItem(text("Command.EditPlayer.Gui.Rank.Item-Name").replace("%value%", hand == null ? "-" : hand),
				lines(Ranks.luckPermsSource() ? "Command.EditPlayer.Gui.Rank.LuckPerms" : "Command.EditPlayer.Gui.Rank.Lore"), XMaterial.NAME_TAG.parseMaterial()));

		inv.setItem(HIDER, createGuiItem(text("Command.EditPlayer.Gui.Hider.Item-Name").replace("%value%", hider(PlayerVisibility.mode(p))),
				lines("Command.EditPlayer.Gui.Hider.Lore"), XMaterial.CLOCK.parseMaterial()));

		inv.setItem(BACK, createGuiItemWL(text("Command.EditPlayer.Gui.BackToPlayerList.Item-Name"), XMaterial.BARRIER.parseMaterial()));
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getSlotType() == SlotType.OUTSIDE || e.getCurrentItem() == null) return;
		// 88: the menu by its type; the title only gives the name of the player
		if (HawnMenu.typeOf(e.getView()) != HawnMenu.Type.EDIT_PLAYER) return;

		e.setCancelled(true);
		if (e.getRawSlot() >= e.getView().getTopInventory().getSize()) return;

		Player p = (Player) e.getWhoClicked();
		Player target = Bukkit.getServer().getPlayerExact(e.getView().getTitle().replace(TITLE, ""));

		// The player left while the menu was open
		if (target == null) {
			p.closeInventory();
			MessageUtils.PlayerDoesntExist(p);
			return;
		}

		boolean right = e.isRightClick();
		switch (e.getRawSlot()) {
			case GAMEMODE:
				if (!allowed(p, "hawn.editplayer.gamemode")) return;
				gamemode(p, target, MODES[(modeIndex(target.getGameMode()) + (right ? MODES.length - 1 : 1)) % MODES.length]);
				break;
			case FLY:
				p.performCommand("fly " + target.getName());
				break;
			case WALK_SPEED:
			case FLY_SPEED:
				if (!allowed(p, "hawn.editplayer.speed")) return;
				speed(p, target, e.getRawSlot() == FLY_SPEED, e.isShiftClick() ? 0 : right ? -1 : 1);
				break;
			case VANISH:
				p.performCommand("v " + target.getName());
				break;
			case HEAL:
				p.performCommand((right ? "feed " : "heal ") + target.getName());
				break;
			case CLEAR:
				if (!allowed(p, "hawn.editplayer.clearinv")) return;
				clear(p, target);
				break;
			case TP_TO:
				if (!allowed(p, "hawn.editplayer.tp")) return;
				p.teleport(target.getLocation());
				break;
			case TP_HERE:
				if (!allowed(p, "hawn.editplayer.tp")) return;
				target.teleport(p.getLocation());
				changed(p, target, "Teleport-Here", "");
				break;
			case RANK:
				if (!allowed(p, "hawn.admin.command.rank")) return;
				rank(p, target, right);
				break;
			case HIDER:
				if (!allowed(p, "hawn.editplayer.options")) return;
				PlayerVisibility.Mode mode = PlayerVisibility.next(PlayerVisibility.mode(target));
				SpecialItemPlayerVisibility.set(target, mode, SpecialItemPlayerVisibility.Source.COMMAND);
				changed(p, target, "Hider", hider(mode));
				break;
			case BACK:
				ListGui.OpenGui(p, 1);
				return;
			default:
				return;
		}

		// The items again, once the commands are done
		Tasks.later(() -> {
			if (p.isOnline() && target.isOnline() && HawnMenu.typeOf(p.getOpenInventory()) == HawnMenu.Type.EDIT_PLAYER
					&& p.getOpenInventory().getTitle().equals(TITLE + target.getName())) {
				fill(p.getOpenInventory().getTopInventory(), target);
			}
		}, 2L);
	}

	private static boolean allowed(Player p, String permission) {
		if (p.hasPermission(permission)) return true;
		MessageUtils.MessageNoPermission(p, permission);
		return false;
	}

	private static void gamemode(Player p, Player target, GameMode mode) {
		target.setGameMode(mode);
		String name = name(mode);
		send(target, "Gamemode.Other." + name + ".", p.getName());
		send(p, "Gamemode.Other-Sender." + name + ".", target.getName());
	}

	private static void send(Player to, String path, String player) {
		if (ConfigMMsg.getConfig().getBoolean(path + "Enable")) {
			for (String msg : ConfigMMsg.getConfig().getStringList(path + "Messages")) {
				ConfigEventUtils.ExecuteEvent(to, msg.replace("%player%", player), path + "Messages", "EditPlayerGui", false);
			}
		}
	}

	// A level from 0 to 10 (the speed of Minecraft times 10); 0: back to normal (2 to walk, 1 to fly)
	private static void speed(Player p, Player target, boolean fly, int change) {
		int now = level(fly ? target.getFlySpeed() : target.getWalkSpeed());
		int level = change == 0 ? (fly ? 1 : 2) : Math.max(0, Math.min(10, now + change));
		if (fly) {
			target.setFlySpeed(level / 10f);
		} else {
			target.setWalkSpeed(level / 10f);
		}
		changed(p, target, fly ? "Fly-Speed" : "Walk-Speed", String.valueOf(level));
	}

	private static void clear(Player p, Player target) {
		target.getInventory().clear();
		target.getInventory().setArmorContents(new ItemStack[4]);

		if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Sender.Enable")) {
			for (String msg : ConfigMMsg.getConfig().getStringList("ClearInv.Other-Sender.Messages")) {
				ConfigEventUtils.ExecuteEvent(p, msg.replace("%player%", p.getName()).replace("%target%", target.getName()), "ClearInv.Other-Sender.Messages", "EditPlayerGui", false);
			}
		}
		if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Target.Enable")) {
			for (String msg : ConfigMMsg.getConfig().getStringList("ClearInv.Other-Target.Messages")) {
				ConfigEventUtils.ExecuteEvent(target, msg.replace("%player%", p.getName()), "ClearInv.Other-Target.Messages", "EditPlayerGui", false);
			}
		}
	}

	// The rank given by hand (Ranks.Players): the ranks in their order, then none ("-")
	private static void rank(Player p, Player target, boolean back) {
		if (!Ranks.enabled() || Ranks.luckPermsSource()) {
			return;
		}

		List<String> names = new ArrayList<>(Ranks.all().keySet());
		names.add(null);
		String hand = Ranks.byHandOf(target.getName());
		int at = names.indexOf(hand);
		if (at < 0) at = names.size() - 1;
		String next = names.get((at + (back ? names.size() - 1 : 1)) % names.size());

		Ranks.setByHand(target.getName(), next == null ? null : Ranks.byName(next).name);
		changed(p, target, "Rank", next == null ? "-" : next);
	}

	// What changed, said to the admin with the name of its item in the menu (its language): "Walking speed: 3/10"
	private static void changed(Player p, Player target, String item, String value) {
		String what = text("Command.EditPlayer.Gui." + item + ".Item-Name").replace("%value%", value);
		for (String msg : ConfigMAdmin.getConfig().getStringList("Command.EditPlayer.Changed")) {
			ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", ConfigEventUtils.noAction(what)).replace("%arg2%", target.getName()), "", "", false);
		}
	}

	private static int modeIndex(GameMode mode) {
		for (int i = 0; i < MODES.length; i++) {
			if (MODES[i] == mode) return i;
		}
		return 0;
	}

	// SURVIVAL -> Survival, the name in the messages
	private static String name(GameMode mode) {
		return mode.name().charAt(0) + mode.name().substring(1).toLowerCase(java.util.Locale.ROOT);
	}

	// The fly as /fly sees it: the flight allowed by /fly or the fly option, not the one the double jump needs
	private static boolean flying(Player p) {
		return p.getAllowFlight() && (fr.dianox.hawn.command.commands.FlyCommand.player_list_flyc.contains(p)
				|| fr.dianox.hawn.utility.ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_option_fly.Activate"));
	}

	private static int level(float speed) {
		return Math.round(speed * 10);
	}

	private static String hider(PlayerVisibility.Mode mode) {
		return text("Command.EditPlayer.Gui.Hider." + (mode == PlayerVisibility.Mode.ALL ? "Everyone" : mode == PlayerVisibility.Mode.STAFF ? "Staff" : "Nobody"));
	}

	private static String onOff(boolean on) {
		return text(on ? "Command.EditPlayer.Gui.On" : "Command.EditPlayer.Gui.Off");
	}

	private static String colour(String s) {
		return MessageUtils.colourTheStuff(s);
	}

	private static String text(String path) {
		return colour(ConfigMAdmin.getConfig().getString(path, ""));
	}

	private static ArrayList<String> lines(String path) {
		ArrayList<String> lines = new ArrayList<>();
		for (String line : ConfigMAdmin.getConfig().getStringList(path)) {
			lines.add(colour(line));
		}
		return lines;
	}

	private static ArrayList<String> single(String path) {
		ArrayList<String> lines = new ArrayList<>();
		lines.add(" ");
		lines.add(text(path));
		return lines;
	}

	public static ItemStack createGuiItemWL(String name, Material mat) {
		ItemStack i = new ItemStack(mat, 1);
		ItemMeta iMeta = i.getItemMeta();
		iMeta.setDisplayName(name);
		i.setItemMeta(iMeta);
		return i;
	}

	public static ItemStack createGuiItemColor(String name, ArrayList<String> desc, String mat) {
		return createGuiItem(name, desc, XMaterial.matchXMaterial(mat).map(XMaterial::parseMaterial).orElse(Material.STONE));
	}

	public static ItemStack createGuiItem(String name, ArrayList<String> desc, Material mat) {
		ItemStack i = new ItemStack(mat, 1);
		ItemMeta iMeta = i.getItemMeta();
		iMeta.setDisplayName(name);
		iMeta.setLore(desc);
		i.setItemMeta(iMeta);
		return i;
	}

	public static ItemStack createGuiItemSkull(String name, String pname, ArrayList<String> desc) {
		ItemStack i = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1);
		SkullMeta iMeta = (SkullMeta) i.getItemMeta();
		iMeta.setDisplayName(name);
		iMeta.setLore(desc);
		Skulls.setOwner(iMeta, pname);
		i.setItemMeta(iMeta);
		return i;
	}
}
