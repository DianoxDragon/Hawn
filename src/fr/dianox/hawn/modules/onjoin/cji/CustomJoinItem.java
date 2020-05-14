package fr.dianox.hawn.modules.onjoin.cji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.base.Strings;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.cosmeticsfun.BookListConfiguration;
import fr.dianox.hawn.utility.config.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.world.CjiPW;
import me.clip.placeholderapi.PlaceholderAPI;

@SuppressWarnings("deprecation")
public class CustomJoinItem {
	
	public static HashMap<String, String> itemcjiname = new HashMap<String, String>();
	public static HashMap<Integer, String> itemcjislot = new HashMap<Integer, String>();
	public static HashMap<Integer, String> itemcjislotname = new HashMap<Integer, String>();
	
	public static void onItemGive(Player p) {
		
		// If CJi is not enabled
		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {
			return;
		}
		
		// Check Worlds
		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
			if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
				return;
			}
		}
		
		if (!p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}
		
		// Give armor
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Helmet.Enable")) {
			
			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
				if (p.hasPermission("hawn.use.cji.item.helmet")) {
					String path_item = "Custom-Join-Item.Items.Armor.Helmet.Item.";
					
					ItemsMethod(path_item, p, "Helmet");
				}
			} else {
				String path_item = "Custom-Join-Item.Items.Armor.Helmet.Item.";
				
				ItemsMethod(path_item, p, "Helmet");
			}
		}
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Chestplate.Enable")) {
			
			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
				if (p.hasPermission("hawn.use.cji.item.chestplate")) {
					String path_item = "Custom-Join-Item.Items.Armor.Chestplate.Item.";
					
					ItemsMethod(path_item, p, "Chestplate");
				}
			} else {
				String path_item = "Custom-Join-Item.Items.Armor.Chestplate.Item.";
				
				ItemsMethod(path_item, p, "Chestplate");
			}
		}
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Leggings.Enable")) {
			
			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
				if (p.hasPermission("hawn.use.cji.item.leggings")) {
					String path_item = "Custom-Join-Item.Items.Armor.Leggings.Item.";
					
					ItemsMethod(path_item, p, "Leggings");
				}
			} else {
				String path_item = "Custom-Join-Item.Items.Armor.Leggings.Item.";
				
				ItemsMethod(path_item, p, "Leggings");
			}
		}
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Boots.Enable")) {
			
			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
				if (p.hasPermission("hawn.use.cji.item.boots")) {
					String path_item = "Custom-Join-Item.Items.Armor.Boots.Item.";
					
					ItemsMethod(path_item, p, "Boots");
				}
			} else {
				String path_item = "Custom-Join-Item.Items.Armor.Boots.Item.";
				
				ItemsMethod(path_item, p, "Boots");
			}
		}
		
		// Give items
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Inventory.Enable")) {
			Iterator < ? > iterator = ConfigCJIGeneral.getConfig().getConfigurationSection("Custom-Join-Item.Items.Inventory.Items").getKeys(false).iterator();
			
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				
				if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
					if (p.hasPermission("hawn.use.cji.item." + string)) {
						String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";
						
						ItemsMethod(path_item, p, "no");
					}
				} else {
					String path_item = "Custom-Join-Item.Items.Inventory.Items." + string + ".";
					
					ItemsMethod(path_item, p, "no");
				}
			}
		}
		
	}
	
	private static void ItemsMethod(String path_item, Player p, String specialvalue) {
		
		String material = "";
		ItemStack item = null;
		Integer amount = 1;
		ItemMeta itemmeta = null;
		Integer slot = 0;
		SkullMeta meta = null;
		String skullname = "";
		
		ArrayList < String > lore = new ArrayList < String > ();
		Boolean lorecheck = false;
		
		String title = "thistitleinsnotforuseorsomethingelse";
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Special-Items")) {
			material = ConfigCJIGeneral.getConfig().getString(path_item + "Special-Items");
		} else {
			material = ConfigCJIGeneral.getConfig().getString(path_item + "Material");
		}
		
		if (specialvalue.contentEquals("no")) {
			if (ConfigCJIGeneral.getConfig().isSet(path_item + "Slot")) {
				slot = ConfigCJIGeneral.getConfig().getInt(path_item + "Slot");
			} else {
				Bukkit.getConsoleSender().sendMessage("§cError in the configuration file, you didn't set a slot number for §e" + path_item);
				return;
			}
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Amount")) {
			amount = ConfigCJIGeneral.getConfig().getInt(path_item + "Amount");
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Title")) {
			
			String pretitle = ConfigCJIGeneral.getConfig().getString(path_item + "Title");
			
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				pretitle = PlaceholderAPI.setPlaceholders(p, pretitle);
            }

            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
            	pretitle = PlaceHolders.BattleLevelPO(pretitle, p);
            }
            
            pretitle = PlaceHolders.ReplaceMainplaceholderP(pretitle, p);
			
			pretitle = pretitle.replaceAll("&", "§");
			
			title = pretitle;
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Lore")) {
			for (String loremsg: ConfigCJIGeneral.getConfig().getStringList(path_item + "Lore")) {
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
	            }

	            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
	            	loremsg = PlaceHolders.BattleLevelPO(loremsg, p);
	            }
	            
	            loremsg = PlaceHolders.ReplaceMainplaceholderP(loremsg, p);
				
				loremsg = loremsg.replaceAll("&", "§");
				
				lore.add(loremsg);
				
				lorecheck = true;
			}
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name")) {
			
			skullname = ConfigCJIGeneral.getConfig().getString(path_item + "Skull-Name");
			
			skullname = skullname.replaceAll("%player%", p.getName());
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
			
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				booktitle = PlaceholderAPI.setPlaceholders(p, booktitle);
			}
			
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				booktitle = PlaceHolders.BattleLevelPO(booktitle, p);
			}
			
			booktitle = booktitle.replaceAll("&", "§");
			
			metabook.setTitle(booktitle);
			
			// pages
			Iterator<?> iterator = BookListConfiguration.getConfig().getConfigurationSection("Book-List." + material).getKeys(false).iterator();
			
			ArrayList<String> pages = new ArrayList<String>();
			pages.clear();
			
			Integer number = 0;
			
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				
				String page = "";
				Boolean check = false;
				
				if (string.equals("Title") || string.equals("Author")) {
					continue;
				}
				
				for (String s: BookListConfiguration.getConfig().getStringList("Book-List." + material + "." + string + ".page")) {
					s = s.replaceAll("&", "§");
					
					s = PlaceHolders.ReplaceMainplaceholderP(s, p);
					
					if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
						s = PlaceholderAPI.setPlaceholders(p, s);
					}
					
					if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
						s = PlaceHolders.BattleLevelPO(s, p);
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
			p.getInventory().setItem(slot, item);
			
			return;
		} else {
			if (material.contains("SKULL")) {
				if (ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name")) {
					if (Main.Spigot_Version >= 113) {
						item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), amount);
					} else {
						item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
					}
				} else {
					if (ConfigCJIGeneral.getConfig().isSet(path_item + "Data-value")) {
						item = new ItemStack(XMaterial.getMat(material, path_item + "Material"), amount, (short) ConfigCJIGeneral.getConfig().getInt(path_item + "Data-value"));
					} else {
						item = new ItemStack(XMaterial.getMat(material, path_item + "Material"), amount);
					}
				}
			} else {
				if (ConfigCJIGeneral.getConfig().isSet(path_item + "Data-value")) {
					item = new ItemStack(XMaterial.getMat(material, path_item + "Material"), amount, (short) ConfigCJIGeneral.getConfig().getInt(path_item + "Data-value"));
				} else {
					item = new ItemStack(XMaterial.getMat(material, path_item + "Material"), amount);
				}
			}
		}
		
			if (!ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name") || !material.contains("SKULL")) {
				itemmeta = item.getItemMeta();
			} else {
				itemmeta = item.getItemMeta();
				meta = (SkullMeta) item.getItemMeta();
				meta.setOwner(skullname);
			}
			
			if (ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name") && material.contains("SKULL")) {
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
					leather.setColor(parseColor(ConfigCJIGeneral.getConfig().getString(path_item + "Color")));
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
	
	public static Color parseColor(String str) {
        if (Strings.isNullOrEmpty(str)) return Color.BLACK;
        String[] rgb = StringUtils.split(StringUtils.deleteWhitespace(str), ',');
        return Color.fromRGB(NumberUtils.toInt(rgb[0], 0), NumberUtils.toInt(rgb[1], 0), NumberUtils.toInt(rgb[2], 0));
    }

}
