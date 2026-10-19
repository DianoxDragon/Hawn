package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.hook.HooksManager;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.XParse;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.JoinItemTag;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.PlayerVisibility.Mode;
import fr.dianox.hawn.utility.config.configs.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.CjiPW;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

/**
 * The item of the player hider. Since 1.4.9, a click goes to the next state of the player (everyone, the staff and the VIP,
 * nobody), whatever the item shows: the item is found by its tag, not by its title and material.
 */
public class SpecialItemPlayerVisibility implements Listener {

	/**
	 * Where the change comes from: the sound and the message depend on it.
	 */
	public enum Source { INVENTORY, INTERACT, COMMAND, WORLD }

	// The join items are only for the worlds of the join items, with their permissions
	private static boolean allowed(Player p, String joinItem) {
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			return false;
		}

		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")
				&& !CjiPW.getWItemPG().contains(p.getWorld().getName())) {
			return false;
		}

		if (!p.hasPermission("hawn.use.customjoinitem")) {
			return false;
		}

		// 53: only for a slot with a join item, and without the debug message sent at every click
		return !ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
				|| joinItem == null || p.hasPermission("hawn.use.cji.item." + joinItem);
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInventoryClickWithItemVisibility(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if (e.getSlotType() == SlotType.OUTSIDE) return;
		if (item == null || item.getType() == Material.AIR) return;
		if (e.getSlot() >= 36 && e.getSlot() <= 39) return;
		if (!JoinItemTag.is(item, JoinItemTag.HIDEPLAYERS)) return;
		if (!allowed(p, CustomJoinItem.itemcjislotname.get(e.getSlot()))) return;

		e.setCancelled(true);
		// false: the item does not move, only a right click with it changes the state
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Interact-With-The-Object", true)) {
			use(p, Source.INVENTORY);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (e.getHand() != EquipmentSlot.HAND) return;
		if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!JoinItemTag.is(p.getInventory().getItemInMainHand(), JoinItemTag.HIDEPLAYERS)) return;
		if (!allowed(p, CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) return;

		// Also during the delay: the item is never placed or used
		e.setCancelled(true);
		use(p, Source.INTERACT);
	}

	/**
	 * The item or /option pv: the next state, after the delay of PV.Option.Item-Delay.
	 */
	public static void use(Player p, Source source) {
		use(p, PlayerVisibility.next(PlayerVisibility.mode(p)), source);
	}

	public static void use(Player p, Mode mode, Source source) {
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
			if (PlayerVisibility.Cooling().contains(p)) {
				if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.Error.Player-Visibility.Time.Messages", "SpecialItemPlayerVisibility", false);
					}
				}
				return;
			}

			PlayerVisibility.Cooling().add(p);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerVisibility.Cooling().remove(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
			Main.hiderCooldowns.put(p, System.currentTimeMillis());
		}

		set(p, mode, source);
	}

	/**
	 * The state applied, the items changed, the sound, the message and the saved value.
	 */
	public static void set(Player p, Mode mode, Source source) {
		PlayerVisibility.setMode(p, mode);
		refreshItems(p);

		if (source == Source.INVENTORY) {
			sound(p, "PV.Option.Inventory-Click.Sounds.");
		} else if (source == Source.INTERACT) {
			sound(p, "PV.Option.Interact-With-Item.Sounds.");
		}

		if (source == Source.COMMAND || source == Source.WORLD || SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
			message(p, mode);
		}

		PlayerOptionSQLClass.onMysqlYamlCJIChange(p, PlayerVisibility.value(mode));
	}

	/**
	 * The choice of the player is restored on join with PV.Option.OnJoin-Priority-For-Player-Option
	 * or with Keep.PlayerVisibility-OnJoin of Player-Option-General.yml.
	 */
	private static boolean keepPlayerChoice() {
		return SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")
				|| PlayerOptionMainConfig.getConfig().getBoolean("Keep.PlayerVisibility-OnJoin.Enable");
	}

	private static Mode joinMode(Player p) {
		if (keepPlayerChoice()) {
			return PlayerVisibility.fromValue(PlayerOptionSQLClass.getValueMysqlYaml(p));
		}
		return SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers") ? Mode.ALL : Mode.NONE;
	}

	public static void PlayerGivePlayerVisibilityItemOnJoincji(Player p, Integer slot) {
		if (!allowed(p, CustomJoinItem.itemcjislotname.get(slot))) {
			return;
		}

		CreateItem(p, joinMode(p), slot);
	}

	public static void PlayerGivePlayerVisibilityItemOnJoin(Player p) {
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			return;
		}

		// Check Worlds
		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
			if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
				return;
			}
		}

		Mode mode = joinMode(p);
		PlayerVisibility.setMode(p, mode);
		PlayerOptionSQLClass.onMysqlYamlCJIChange(p, PlayerVisibility.value(mode));
		refreshItems(p);
	}

	/**
	 * The items of the player hider show the state of the player.
	 */
	public static void refreshItems(Player p) {
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			return;
		}

		Mode mode = PlayerVisibility.mode(p);
		for (int i = 0; i <= 35; ++i) {
			if (JoinItemTag.is(p.getInventory().getItem(i), JoinItemTag.HIDEPLAYERS)) {
				try {
					CreateItem(p, mode, i);
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage("§c[Hawn] The item of the player hider could not be changed: " + e);
				}
			}
		}
	}

	public static void message(Player p, Mode mode) {
		String path = "PlayerOption.PlayerVisibility." + (mode == Mode.ALL ? "OFF" : mode == Mode.STAFF ? "STAFF" : "ON");
		if (ConfigMMsg.getConfig().getBoolean(path + ".Enable")) {
			for (String msg: ConfigMMsg.getConfig().getStringList(path + ".Messages")) {
				ConfigEventUtils.ExecuteEvent(p, msg, path + ".Messages", "SpecialItemPlayerVisibility", false);
			}
		}
	}

	private static void sound(Player p, String path) {
		if (SpecialCjiHidePlayers.getConfig().getBoolean(path + "Enable")) {
			float volume = (float) SpecialCjiHidePlayers.getConfig().getDouble(path + "Volume");
			float pitch = (float) SpecialCjiHidePlayers.getConfig().getDouble(path + "Pitch");
			p.playSound(p.getLocation(), XParse.sound(SpecialCjiHidePlayers.getConfig().getString(path + "Sound"), path + "Sound"), volume, pitch);
		}
	}

	/**
	 * PV.OFF: the players are shown, PV.STAFF: only the staff and the VIP, PV.ON: hidden.
	 */
	public static void CreateItem(Player p, Mode mode, Integer slot) {
		String onoroff = mode == Mode.ALL ? "OFF" : mode == Mode.STAFF ? "STAFF" : "ON";
		if (!SpecialCjiHidePlayers.getConfig().isSet("PV." + onoroff + ".Material.Material")) {
			onoroff = "ON";
		}

		String material = "";
		ItemStack item = null;
		int amount = 1;
		ItemMeta itemmeta = null;
		SkullMeta meta = null;
		String skullname = "";

		ArrayList <String> lore = new ArrayList <> ();
		boolean lorecheck = false;

		String title = "thistitleinsnotforuseorsomethingelse";

		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Title")) {
			String pretitle = SpecialCjiHidePlayers.getConfig().getString("PV."+onoroff+".Title");

			if (HooksManager.papi()) {
				pretitle = PlaceholderAPI.setPlaceholders(p, pretitle);
			}


			pretitle = PlaceHolders.ReplaceMainplaceholderP(pretitle, p);

			pretitle = MessageUtils.colourTheStuff(pretitle);

			title = pretitle;
		}

		material = SpecialCjiHidePlayers.getConfig().getString("PV."+onoroff+".Material.Material");

		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Amount")) {
			amount = SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Amount");
		}

		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Lore")) {
			for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV."+onoroff+".Lore")) {

				if (HooksManager.papi()) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
				}


				loremsg = PlaceHolders.ReplaceMainplaceholderP(loremsg, p);

				loremsg = MessageUtils.colourTheStuff(loremsg);

				lore.add(loremsg);

				lorecheck = true;
			}
		}

		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Skull-Name")) {
			skullname = SpecialCjiHidePlayers.getConfig().getString("PV."+onoroff+".Material.Skull-Name");

			skullname = skullname.replace("%player%", p.getName());
		}

		if (material.contains("SKULL")) {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Skull-Name")) {
				item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), amount);
			} else {
				if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Data-value")) {
					item = new ItemStack(XParse.material(material, "PV."+onoroff+".Material"), amount, (short) SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Data-value"));
				} else {
					item = new ItemStack(XParse.material(material, "PV."+onoroff+".Material"), amount);
				}
			}
		} else {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Data-value")) {
				item = new ItemStack(XParse.material(material, "PV."+onoroff+".Material"), amount, (short) SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Data-value"));
			} else {
				item = new ItemStack(XParse.material(material, "PV."+onoroff+".Material"), amount);
			}
		}

		if (!SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Skull-Name") || !material.contains("SKULL")) {
			itemmeta = item.getItemMeta();
		} else {
			itemmeta = item.getItemMeta();
			meta = (SkullMeta) item.getItemMeta();
			Skulls.setOwner(meta, skullname);
		}

		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Skull-Name") && material.contains("SKULL")) {
			if (!title.contentEquals("thistitleinsnotforuseorsomethingelse")) {
				meta.setDisplayName(title);
			}

			if (lorecheck) {
				meta.setLore(lore);
			}

			item.setItemMeta(meta);
		} else {
			if (!title.contentEquals("thistitleinsnotforuseorsomethingelse")) {
				itemmeta.setDisplayName(title);
			}

			if (lorecheck) {
				itemmeta.setLore(lore);
			}

			item.setItemMeta(itemmeta);
		}

		JoinItemTag.tag(item, JoinItemTag.HIDEPLAYERS);
		p.getInventory().setItem(slot, item);
	}
}
