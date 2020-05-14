package fr.dianox.hawn.modules.onjoin.cji;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiLobbyBow;
import fr.dianox.hawn.utility.world.CjiPW;
import me.clip.placeholderapi.PlaceholderAPI;

@SuppressWarnings("deprecation")
public class SpecialIteLobbyBow implements Listener {
	
	HashMap<Player, ItemStack> storedItem = new HashMap<Player, ItemStack>();
	
	public static String Check = SpecialCjiLobbyBow.getConfig().getString("LobbyBow.Item.Title");

	@EventHandler
	public void onCLickInventory(InventoryClickEvent e) {
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			return;
		}
		
		// Check Worlds
		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
			if (!CjiPW.getWItemPG().contains(e.getWhoClicked().getWorld().getName())) {
				return;
			}
		}
		
		Player p = (Player) e.getWhoClicked();
		
		if (!p.hasPermission("hawn.use.customjoinitem")) {
			 return;
		}
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
			if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(e.getSlot()))) {
				p.sendMessage("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(e.getSlot()));
				return;
			}
		}
		
		if (e.getSlotType() == SlotType.OUTSIDE) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		String Check1 = Check;
		
		if (Check1.startsWith("&f")) {
			Check1 = Check1.substring(2, Check1.length());
		}
		
		Check1 = Check1.replaceAll("&", "§");
		
		try {
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains(Check1)) {
				if (e.getCurrentItem().getType() == XMaterial.BOW.parseMaterial()) {
					e.setCancelled(true);
				}
			}
		} catch (Exception e2) {}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onArrowHit(ProjectileHitEvent e) {
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			return;
		}
		
		Projectile proj = e.getEntity();
		if (proj instanceof Arrow) {
			
			Arrow arrow = (Arrow) proj;
			if (arrow.getShooter() instanceof Player) {
				
				Player p = (Player) arrow.getShooter();
				
				// Check Worlds
				if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
					if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
						return;
					}
				}
				
				if (!p.hasPermission("hawn.use.customjoinitem")) {
					 return;
				}
				
				if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
					if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
						return;
					}
				}
				
				ItemStack item = null;
				 
				 if (Main.Spigot_Version > 18) {
					 item = p.getInventory().getItemInMainHand();
				 } else {
					 item = p.getInventory().getItemInHand();
				 }
								 
				item.setDurability((short) 0);
				Location destination = arrow.getLocation();
				destination.setPitch(p.getLocation().getPitch());
				destination.setYaw(p.getLocation().getYaw());
				p.teleport(destination);
				e.getEntity().remove();
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
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
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
			if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
				return;
			}
		}
		
		EquipmentSlot es = null;
		
		if (Main.Spigot_Version >= 19) {
			es = e.getHand();
		}
		
		String Check1 = Check;
		
		if (Check1.startsWith("&f")) {
			Check1 = Check1.substring(2, Check1.length());
		}
		
		Check1 = Check1.replaceAll("&", "§");
		
		try {
			if (Main.Spigot_Version >= 19) {
				if (es.equals(EquipmentSlot.HAND)) {
					if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
							if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-LobbyBow")) {
								if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(Check1)) {
									if (p.getInventory().getItemInMainHand().getType() == XMaterial.BOW.parseMaterial()) {
										if(storedItem.containsKey(p)) return;
										int slot = p.getInventory().getSize() - 10;
										ItemStack item = p.getInventory().getItem(slot);
										storedItem.put(p, item);
										p.getInventory().setItem(slot, new ItemStack(XMaterial.ARROW.parseMaterial(), 1));
									}
								}
							}
						}
					}
				}
			} else {
				if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
						if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-LobbyBow")) {
							if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check1)) {
								if (p.getItemInHand().getType() == XMaterial.BOW.parseMaterial()) {
									if(storedItem.containsKey(p)) return;
									int slot = p.getInventory().getSize() - 10;
									ItemStack item = p.getInventory().getItem(slot);
									storedItem.put(p, item);
									p.getInventory().setItem(slot, new ItemStack(XMaterial.ARROW.parseMaterial(), 1));
								}
							}
						}
					}
				}
			}
		} catch (Exception e2) {}
	}
	
	@EventHandler
	public void PlayerDropItemEvent(PlayerItemHeldEvent e) {
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			return;
		}
		
		returnItem(e.getPlayer());
	}
	
	@EventHandler
	public void EntityShootBowEvent(EntityShootBowEvent e) {
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			return;
		}
		
		if(e.getEntity() instanceof Player) {
			returnItem((Player) e.getEntity());
		}
	}
	
	private void returnItem(Player p) {
		if (storedItem.containsKey(p.getPlayer())) {
			int slot = p.getInventory().getSize() - 10;
			p.getInventory().setItem(slot, storedItem.get(p));
			p.updateInventory();
			storedItem.remove(p);
		}
	}
	
	public static void PlayerGiveLobbyBow(Player p, Integer slot) {
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
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
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
			if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(slot))) {
				return;
			}
		}
		
		CreateItem(p, slot);
	}
	
	public static void CreateItem(Player p, Integer slot) {
		ItemStack item = null;
		Integer amount = 1;
		ItemMeta itemmeta = null;
		
		ArrayList < String > lore = new ArrayList < String > ();
		Boolean lorecheck = false;
		
		String title = "thistitleinsnotforuseorsomethingelse";

		if (SpecialCjiLobbyBow.getConfig().isSet("LobbyBow.Item.Title")) {
			String pretitle = SpecialCjiLobbyBow.getConfig().getString("LobbyBow.Item.Title");
				
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
					
		if (SpecialCjiLobbyBow.getConfig().isSet("LobbyBow.Item.Material.Amount")) {
			amount = SpecialCjiLobbyBow.getConfig().getInt("LobbyBow.Item.Material.Amount");
		}
			
		if (SpecialCjiLobbyBow.getConfig().isSet("LobbyBow.Item.Lore")) {
			for (String loremsg: SpecialCjiLobbyBow.getConfig().getStringList("LobbyBow.Item.Lore")) {
				
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
		
		item = new ItemStack(XMaterial.BOW.parseMaterial(), amount);
		itemmeta = item.getItemMeta();

		if (!title.contentEquals("thistitleinsnotforuseorsomethingelse")) {
			itemmeta.setDisplayName(title);
		}
			
		if (lorecheck) {
			itemmeta.setLore(lore);
		}
			
		item.setItemMeta(itemmeta);
		
		p.getInventory().setItem(slot, item);
	}
	
}
