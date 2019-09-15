package fr.Dianox.Hawn.Event.OnJoinE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.Event.CustomJoinItem.SpecialCJIPlayerVisibility;
import fr.dianox.hawn.Utility.MessageUtils;
import fr.dianox.hawn.Utility.XMaterial;
import fr.dianox.hawn.Utility.Config.ConfigGeneral;
import fr.dianox.hawn.Utility.Config.CustomJoinItem.ConfigCJIGeneral;
import fr.dianox.hawn.Utility.World.CjiPW;
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
			
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				pretitle = PlaceholderAPI.setPlaceholders(p, pretitle);
            }

            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
            	pretitle = MessageUtils.BattleLevelPO(pretitle, p);
            }
            
            pretitle = MessageUtils.ReplaceMainplaceholderP(pretitle, p);
			
			pretitle = pretitle.replaceAll("&", "§");
			
			title = pretitle;
		}
		
		if (ConfigCJIGeneral.getConfig().isSet(path_item + "Lore")) {
			for (String loremsg: ConfigCJIGeneral.getConfig().getStringList(path_item + "Lore")) {
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
	            }

	            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
	            	loremsg = MessageUtils.BattleLevelPO(loremsg, p);
	            }
	            
	            loremsg = MessageUtils.ReplaceMainplaceholderP(loremsg, p);
				
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
			SpecialCJIPlayerVisibility.PlayerGivePlayerVisibilityItemOnJoincji(p, slot);
			return;
		} else {
			if (material.contains("SKULL")) {
				if (ConfigCJIGeneral.getConfig().isSet(path_item + "Skull-Name")) {
					if (Main.newmethodver) {
						item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), amount);
					} else {
						item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
					}
				} else {
					if (ConfigCJIGeneral.getConfig().isSet(path_item + "Data-value")) {
						item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount, (short) ConfigCJIGeneral.getConfig().getInt(path_item + "Data-value"));
					} else {
						item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount);
					}
				}
			} else {
				if (ConfigCJIGeneral.getConfig().isSet(path_item + "Data-value")) {
					item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount, (short) ConfigCJIGeneral.getConfig().getInt(path_item + "Data-value"));
				} else {
					item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount);
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

}
