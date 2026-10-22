package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.hook.CustomItems;
import fr.dianox.hawn.hook.HooksManager;
import fr.dianox.hawn.modules.menus.MenuItems;
import fr.dianox.hawn.utility.Skulls;
import fr.dianox.hawn.utility.JoinItemTag;

import fr.dianox.hawn.utility.XParse;

import com.google.common.base.Strings;
import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.BookListConfiguration;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.world.CjiPW;
import me.clip.placeholderapi.PlaceholderAPI;
import fr.dianox.hawn.utility.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

@SuppressWarnings("deprecation")
public class CustomJoinItem {
	
	public static HashMap<String, String> itemcjiname = new HashMap<>();
	public static HashMap<Integer, String> itemcjislot = new HashMap<>();
	public static HashMap<Integer, String> itemcjislotname = new HashMap<>();
	
	public static void onItemGive(Player p) {
		give(p, false);
	}

	private static final String[] ARMOR = {"Helmet", "Chestplate", "Leggings", "Boots"};

	/**
	 * The join items of the world of the player. With onlyFree (world change), a slot holding an object of the player is
	 * never replaced: only an empty slot or an item of Hawn.
	 */
	private static void give(Player p, boolean onlyFree) {
		// If CJi is not enabled
		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {
			return;
		}

		if (!p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		boolean perItem = ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item");

		// Give armor
		for (String piece : ARMOR) {
			if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor." + piece + ".Enable")) continue;
			String path_item = "Custom-Join-Item.Items.Armor." + piece + ".Item.";
			if (perItem && !p.hasPermission("hawn.use.cji.item." + piece.toLowerCase(java.util.Locale.ROOT))) continue;
			if (!inWorld(path_item, p)) continue;

			ItemsMethod(path_item, p, piece, onlyFree);
		}

		// Give items
		org.bukkit.configuration.ConfigurationSection items = ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items");
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Inventory.Enable") && items != null) {
			for (String string : items.getKeys(false)) {
				String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";
				if (perItem && !p.hasPermission("hawn.use.cji.item." + string)) continue;
				if (!inWorld(path_item, p)) continue;

				ItemsMethod(path_item, p, "no", onlyFree);
			}
		}
	}

	/**
	 * Worlds: of the item (since 1.5), else the worlds of all the join items (General-Option.World).
	 */
	public static boolean inWorld(String path_item, Player p) {
		java.util.List<String> worlds = ConfigCJIGeneral.getConfig().getStringList(path_item + "Worlds");
		if (!worlds.isEmpty()) {
			for (String world : worlds) {
				if (world.equalsIgnoreCase(p.getWorld().getName())) return true;
			}
			return false;
		}
		return ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")
				|| CjiPW.getWItemPG().contains(p.getWorld().getName());
	}

	/**
	 * General-Option.Change-World.Enable (since 1.5): the items of the new world are given, the ones that are not for it
	 * are removed. The objects of the player are never touched.
	 */
	public static void onWorldChange(Player p) {
		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")
				|| !ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Change-World.Enable")
				|| !p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		for (String piece : ARMOR) {
			String path_item = "Custom-Join-Item.Items.Armor." + piece + ".Item.";
			ItemStack worn = armor(p, piece);
			if (JoinItemTag.is(worn, path_item) && !inWorld(path_item, p)) {
				setArmor(p, piece, null);
			}
		}

		for (java.util.Map.Entry<Integer, String> entry : itemcjislot.entrySet()) {
			ItemStack current = p.getInventory().getItem(entry.getKey());
			if (JoinItemTag.isAny(current) && !inWorld(entry.getValue(), p)) {
				p.getInventory().setItem(entry.getKey(), null);
			}
		}

		give(p, true);
	}

	/**
	 * A join item uses an item of another plugin (nexo:...): it is given again when Nexo has loaded its items.
	 */
	public static boolean usesCustomItems() {
		for (String path : itemcjislot.values()) {
			if (CustomItems.isCustom(ConfigCJIGeneral.getConfig().getString(path + "Material"))) return true;
		}
		for (String piece : ARMOR) {
			if (CustomItems.isCustom(ConfigCJIGeneral.getConfig().getString("Custom-Join-Item.Items.Armor." + piece + ".Item.Material"))) return true;
		}
		return false;
	}

	/**
	 * The item clicked is the item of this join item: its tag, and its material (an item of another plugin keeps its own).
	 */
	public static boolean sameMaterial(ItemStack item, String path_item) {
		String material = ConfigCJIGeneral.getConfig().getString(path_item + "Material");
		return item != null && (CustomItems.isCustom(material) || item.getType() == XParse.material(material, "Custom Join Item"));
	}

	private static ItemStack armor(Player p, String piece) {
		switch (piece) {
			case "Helmet": return p.getInventory().getHelmet();
			case "Chestplate": return p.getInventory().getChestplate();
			case "Leggings": return p.getInventory().getLeggings();
			default: return p.getInventory().getBoots();
		}
	}

	private static void setArmor(Player p, String piece, ItemStack item) {
		switch (piece) {
			case "Helmet": p.getInventory().setHelmet(item); break;
			case "Chestplate": p.getInventory().setChestplate(item); break;
			case "Leggings": p.getInventory().setLeggings(item); break;
			default: p.getInventory().setBoots(item); break;
		}
	}

	// A slot that can take a join item: empty, or an item of Hawn
	private static boolean free(ItemStack current) {
		return current == null || current.getType() == org.bukkit.Material.AIR || JoinItemTag.isAny(current);
	}

	private static void ItemsMethod(String path_item, Player p, String specialvalue, boolean onlyFree) {
		
		String material = "";
		ItemStack item = null;
		int amount = 1;
		ItemMeta itemmeta = null;
		int slot = 0;
		SkullMeta meta = null;
		String skullname = "";
		
		ArrayList < String > lore = new ArrayList <> ();
		boolean lorecheck = false;
		
		String title = "thistitleinsnotforuseorsomethingelse";
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Special-Items")) {
			material = ConfigCJIGeneral.getConfig().getString(path_item + "Special-Items");
		} else {
			material = ConfigCJIGeneral.getConfig().getString(path_item + "Material");
		}

		// 51: PLAYER_HEAD (the name of the wiki) as well as the old SKULL_ITEM
		boolean head = material != null && (material.toUpperCase().contains("SKULL") || material.toUpperCase().contains("PLAYER_HEAD"));
		
		if (specialvalue.contentEquals("no")) {
			if (ConfigCJIGeneral.getConfig().isSet(path_item + "Slot")) {
				slot = ConfigCJIGeneral.getConfig().getInt(path_item + "Slot");

				// 0-35 the inventory, 36-39 the armor, 40 the second hand
				if (slot < 0 || slot > 40) {
					warnOnce(path_item + "Slot", "§cError in the configuration file: the slot " + slot + " of §e" + path_item + "§c is not between 0 and 40");
					return;
				}
			} else {
				Bukkit.getConsoleSender().sendMessage("§cError in the configuration file, you didn't set a slot number for §e" + path_item);
				return;
			}
		}

		if (onlyFree && !free(specialvalue.contentEquals("no") ? p.getInventory().getItem(slot) : armor(p, specialvalue))) {
			return;
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Amount")) {
			amount = ConfigCJIGeneral.getConfig().getInt(path_item + "Amount");
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Title")) {
			
			String pretitle = ConfigCJIGeneral.getConfig().getString(path_item + "Title");
			
			if (HooksManager.papi()) {
				pretitle = PlaceholderAPI.setPlaceholders(p, pretitle);
            }

            
            pretitle = PlaceHolders.ReplaceMainplaceholderP(pretitle, p);
			
			pretitle = MessageUtils.colourTheStuff(pretitle);
			
			title = pretitle;
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Lore")) {
			for (String loremsg: ConfigCJIGeneral.getConfig().getStringList(path_item + "Lore")) {
				
				if (HooksManager.papi()) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
	            }

	            
	            loremsg = PlaceHolders.ReplaceMainplaceholderP(loremsg, p);
				
				loremsg = MessageUtils.colourTheStuff(loremsg);
				
				lore.add(loremsg);
				
				lorecheck = true;
			}
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name")) {
			
			skullname = ConfigCJIGeneral.getConfig().getString(path_item + "Skull-Name");
			
			skullname = skullname.replace("%player%", p.getName());
		}
		
		if (material.contains("Special-HidePlayers")) {
			SpecialItemPlayerVisibility.PlayerGivePlayerVisibilityItemOnJoincji(p, slot);
			return;
		} else if (material.contains("Special-FunGun")) {
			SpecialIteFunGun.PlayerGiveFunGun(p, slot);
			return;
		} else if (material.contains("Special-LobbyBow")) {
			SpecialIteLobbyBow.PlayerGiveLobbyBow(p, slot);
			return;
		} else if (material.contains("Special-Book")) {
			
			material = material.replace("Special-Book:", "");
			
			item = new ItemStack(XMaterial.WRITTEN_BOOK.parseMaterial(), amount);
			
			BookMeta metabook = (BookMeta) item.getItemMeta();
			
			// author
			metabook.setAuthor(BookListConfiguration.getConfig().getString("Book-List." + material + ".Author"));
			
			// title
			String booktitle = BookListConfiguration.getConfig().getString("Book-List." + material + ".Title");
			
			booktitle = PlaceHolders.ReplaceMainplaceholderP(booktitle, p);
			
			if (HooksManager.papi()) {
				booktitle = PlaceholderAPI.setPlaceholders(p, booktitle);
			}
			
			
			booktitle = MessageUtils.colourTheStuff(booktitle);
			
			metabook.setTitle(booktitle);
			
			// pages
			Iterator<?> iterator = BookListConfiguration.getConfig().getConfigurationSection("Book-List." + material).getKeys(false).iterator();
			
			ArrayList<String> pages = new ArrayList<>();

			int number = 0;
			
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				
				String page = "";
				boolean check = false;
				
				if (string.equals("Title") || string.equals("Author")) {
					continue;
				}
				
				for (String s: BookListConfiguration.getConfig().getStringList("Book-List." + material + "." + string + ".page")) {
					s = MessageUtils.colourTheStuff(s);
					
					s = PlaceHolders.ReplaceMainplaceholderP(s, p);
					
					if (HooksManager.papi()) {
						s = PlaceholderAPI.setPlaceholders(p, s);
					}
					
					
					if (!check) {
						page = s;
						check = true;
					} else {
						page = page + "\n" + s;
					}
				}
				
				pages.add(number, page);
				number++;
			}
			
			metabook.setPages(pages);
			
			if (lorecheck) {
				metabook.setLore(lore);
			}
			
			item.setItemMeta(metabook);
			JoinItemTag.tag(item, path_item);
			p.getInventory().setItem(slot, item);
			
			return;
		} else if (CustomItems.isCustom(material)) {
			item = CustomItems.create(material, amount, path_item + "Material");
		} else {
			if (head) {
				if (ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name")) {
					if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 113) {
						item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), amount);
					} else {
						item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1);
					}
				} else {
					if (ConfigCJIGeneral.getConfig().isSet(path_item + "Data-value")) {
						item = new ItemStack(XParse.material(material, path_item + "Material"), amount, (short) ConfigCJIGeneral.getConfig().getInt(path_item + "Data-value"));
					} else {
						item = new ItemStack(XParse.material(material, path_item + "Material"), amount);
					}
				}
			} else {
				if (ConfigCJIGeneral.getConfig().isSet(path_item + "Data-value")) {
					item = new ItemStack(XParse.material(material, path_item + "Material"), amount, (short) ConfigCJIGeneral.getConfig().getInt(path_item + "Data-value"));
				} else {
					item = new ItemStack(XParse.material(material, path_item + "Material"), amount);
				}
			}
		}
		
			if (!ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name") || !head) {
				itemmeta = item.getItemMeta();
			} else {
				itemmeta = item.getItemMeta();
				meta = (SkullMeta) item.getItemMeta();
				Skulls.setOwner(meta, skullname);
			}
			
			if (ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name") && head) {
				if (!title.contentEquals("thistitleinsnotforuseorsomethingelse")) {
					meta.setDisplayName(title);
				}
				
				if (lorecheck) {
					meta.setLore(lore);
				}
							
				item.setItemMeta(meta);
			} else if (material.contains("LEATHER")) {
				if (!title.contentEquals("thistitleinsnotforuseorsomethingelse")) {
					itemmeta.setDisplayName(title);
				}
				
				if (lorecheck) {
					itemmeta.setLore(lore);
				}
				
				LeatherArmorMeta leather = (LeatherArmorMeta) itemmeta;
								
				if (ConfigCJIGeneral.getConfig().isSet(path_item + "Color")) {
					leather.setColor(parseColor(ConfigCJIGeneral.getConfig().getString(path_item + "Color"), path_item + "Color"));
				}
				
				item.setItemMeta(leather);
			} else {
				if (!title.contentEquals("thistitleinsnotforuseorsomethingelse")) {
					itemmeta.setDisplayName(title);
				}
				
				if (lorecheck) {
					itemmeta.setLore(lore);
				}
				
				item.setItemMeta(itemmeta);
			}
		
		ItemMeta looks = item.getItemMeta();
		if (looks != null && (ConfigCJIGeneral.getConfig().isSet(path_item + "Item-Model") || ConfigCJIGeneral.getConfig().isInt(path_item + "Custom-Model-Data")
				|| ConfigCJIGeneral.getConfig().getBoolean(path_item + "Glow"))) {
			MenuItems.looks(looks, ConfigCJIGeneral.getConfig().getString(path_item + "Item-Model"),
					ConfigCJIGeneral.getConfig().isInt(path_item + "Custom-Model-Data") ? ConfigCJIGeneral.getConfig().getInt(path_item + "Custom-Model-Data") : null,
					ConfigCJIGeneral.getConfig().getBoolean(path_item + "Glow"), path_item);
			item.setItemMeta(looks);
		}

		JoinItemTag.tag(item, path_item);

		if (specialvalue.contentEquals("Helmet")) {
			p.getInventory().setHelmet(item);
		} else if (specialvalue.contentEquals("Chestplate")) {
			p.getInventory().setChestplate(item);
		} else if (specialvalue.contentEquals("Leggings")) {
			p.getInventory().setLeggings(item);
		} else if (specialvalue.contentEquals("Boots")) {
			p.getInventory().setBoots(item);
		} else {
			p.getInventory().setItem(slot, item);
		}
		
	}
	
	/**
	 * "red,green,blue", each from 0 to 255. A missing or wrong number becomes 0 (255 above it), with a warning in the console.
	 */
	public static Color parseColor(String str, String path) {
        if (Strings.isNullOrEmpty(str)) return Color.BLACK;
        String[] rgb = StringUtils.deleteWhitespace(str).split(",");
        int[] values = new int[3];
        boolean wrong = rgb.length != 3;
        for (int i = 0; i < 3; i++) {
            int value = i < rgb.length ? StringUtils.toInt(rgb[i], -1) : -1;
            if (value < 0 || value > 255) {
                wrong = true;
                value = Math.max(0, Math.min(255, value));
            }
            values[i] = value;
        }
        if (wrong) {
            warnOnce(path, "§cError in the configuration file: §e" + path + ": " + str + "§c is not a colour red,green,blue (0 to 255 each)");
        }
        return Color.fromRGB(values[0], values[1], values[2]);
    }

	// Once per option until the next /hawn reload, not at every join
	static final java.util.Set<String> warned = new java.util.HashSet<>();

	private static void warnOnce(String path, String message) {
		if (warned.add(path)) {
			Bukkit.getConsoleSender().sendMessage(message);
		}
	}
}
