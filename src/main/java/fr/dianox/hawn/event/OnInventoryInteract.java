package fr.dianox.hawn.event;


import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.HawnCommand;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.JoinItemTag;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.config.configs.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class OnInventoryInteract implements Listener {
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		
		if (Main.buildbypasscommand.contains(p)) {
			return;
        }
		
		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_In_Creative_Mode_In_Any_Case")) {
			if (p.getGameMode() == GameMode.CREATIVE) {
				 return;
			}
		}
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {
			
			// 115: the worlds of General-Option.World, and the Worlds: of each item
			if (!CustomJoinItem.anyHere(p)) {
				return;
			}
			
			if (!p.hasPermission("hawn.use.customjoinitem")) {
				 return;
			}

			// ALL by default: the inventory of the lobby stays clean. JOIN-ITEMS: the other items can be dropped. NONE: nothing blocked
			String mode = ConfigCJIGeneral.getConfig().getString("Custom-Join-Item.General-Option.Block-Drop", "ALL");
			if (mode.equalsIgnoreCase("NONE") || (mode.equalsIgnoreCase("JOIN-ITEMS") && !JoinItemTag.isAny(e.getItemDrop().getItemStack()))) {
				return;
			}

			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onclick(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (Main.buildbypasscommand.contains(p)) {
			return;
		}

		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_In_Creative_Mode_In_Any_Case")) {
			if (p.getGameMode() == GameMode.CREATIVE) {
				return;
			}
		}

		if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {
			return;
		}

		// 115: the worlds of General-Option.World, and the Worlds: of each item
		if (!CustomJoinItem.anyHere(p) || !p.hasPermission("hawn.use.customjoinitem")) {
			return;
		}

		if (e.getHand() != EquipmentSlot.HAND || (e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK)) {
			return;
		}

		ItemStack held = p.getInventory().getItemInMainHand();

		try {
			// The lobby bow shoots
			if (JoinItemTag.is(held, JoinItemTag.LOBBYBOW) && held.getType() == XMaterial.BOW.parseMaterial()) {
				e.setCancelled(false);
				return;
			}

			// 116: the join item is found by its tag, not by its slot (two items of two worlds can share a slot)
			String path = CustomJoinItem.pathOf(held);
			if (path == null || !CustomJoinItem.inWorld(path, p)) {
				return;
			}

			if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
					&& !p.hasPermission("hawn.use.cji.item." + CustomJoinItem.keyOf(path))) {
				return;
			}

			String special = ConfigCJIGeneral.getConfig().getString(path + "Special-Items");
			if (special != null && special.contains("Special-Book") && held.getType() == XMaterial.BOOK.parseMaterial()) {
				e.setCancelled(false);
				return;
			}

			// A head keeps its skin, not its material: only the tag for it
			if (ConfigCJIGeneral.getConfig().isSet(path + "Skull-Name") || CustomJoinItem.sameMaterial(held, path)) {
				for (String s: ConfigCJIGeneral.getConfig().getStringList(path + "Command-List")) {
					ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
					p.updateInventory();
				}

				e.setCancelled(true);
			}
		} catch (Exception ignored) {}
	}
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
		
		 if (e.getSlotType() == SlotType.OUTSIDE) return;
	     
		 if (HawnCommand.slotview.contains(e.getWhoClicked())) {
			 e.getWhoClicked().sendMessage(String.valueOf("§7Slot§8: §e" + e.getSlot()));
		 }
		 
		 if (e.getCurrentItem() == null) {
			 return;
		 }
		 
		 if (Main.buildbypasscommand.contains(e.getWhoClicked())) {
			 return;
		 }
		 
		 if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_In_Creative_Mode_In_Any_Case")) {
				if (e.getWhoClicked().getGameMode() == GameMode.CREATIVE) {
					 return;
				}
			}
		 
		 Player p = (Player) e.getWhoClicked();
		 
		 if (PlayerOptionMainConfig.getConfig().getBoolean("Options.Flying.Put-boots") && p.hasPermission("hawn.fun.boots.flying")) {
			 if (p.isFlying()) {
				 if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
					 try {
						 if (e.getCurrentItem().getType() == XMaterial.DIAMOND_BOOTS.parseMaterial()) {
							 if (e.getCurrentItem().getItemMeta().getDisplayName().contains("§eI'm flyyyyinggggggg")) {
								 e.setCancelled(true);
							 }
						 }
					 } catch (Exception e2) {}
			 	}
			 }
		 }
		 
		 try {
			 if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
				 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {
					 
					 // world
					 // 115: the worlds of General-Option.World, and the Worlds: of each item
					 if (!CustomJoinItem.anyHere(p)) {
						 return;
					 }
					 
					 if (!p.hasPermission("hawn.use.customjoinitem")) {
						 return;
					 }
					 
					 // Interaction
					 
					 if (e.getSlot() == 39) {
						 
						 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Helmet.Enable")) {
							 String path_item = "Custom-Join-Item.Items.Armor.Helmet.Item.";
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Helmet-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material")) && JoinItemTag.is(e.getCurrentItem(), path_item) && CustomJoinItem.inWorld(path_item, p)) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
									 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
											if (p.hasPermission("hawn.use.cji.item.helmet")) {
												for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
													ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
												 }
												
												e.setCancelled(true);
											}
									 } else {
										 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
											 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
										 }
										 
										 e.setCancelled(true);
									 }
								 }
							 }
						 }
					 } else if (e.getSlot() == 38) {
						 
						 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Chestplate.Enable")) {
							 String path_item = "Custom-Join-Item.Items.Armor.Chestplate.Item.";
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Chestplate-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material")) && JoinItemTag.is(e.getCurrentItem(), path_item) && CustomJoinItem.inWorld(path_item, p)) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
									 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
											if (p.hasPermission("hawn.use.cji.item.chestplate")) {
												for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
													ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
												 }
												
												e.setCancelled(true);
											}
									 } else {
										 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
											 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
										 }
										 
										 e.setCancelled(true);
									 }
								 }
							 }
						 }
					 } else if (e.getSlot() == 37) {
						 
						 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Leggings.Enable")) {
							 String path_item = "Custom-Join-Item.Items.Armor.Leggings.Item.";
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Leggings-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material")) && JoinItemTag.is(e.getCurrentItem(), path_item) && CustomJoinItem.inWorld(path_item, p)) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
									 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
											if (p.hasPermission("hawn.use.cji.item.leggings")) {
												for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
													ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
												}
												
												e.setCancelled(true);
											}
									 } else {
										 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
											 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
										 }
										 
										 e.setCancelled(true);
									 }
								 }
							 }
						 }
					 } else if (e.getSlot() == 36) {
						 
						 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Boots.Enable")) {
							 String path_item = "Custom-Join-Item.Items.Armor.Boots.Item.";
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Boots-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material")) && JoinItemTag.is(e.getCurrentItem(), path_item) && CustomJoinItem.inWorld(path_item, p)) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
									 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
											if (p.hasPermission("hawn.use.cji.item.boots")) {
												for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
													ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
												 }
												
												e.setCancelled(true);
											}
									 } else {
										 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
											 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
										 }
										 
										 e.setCancelled(true);
									 }
								 }
							 }
						 }
					 }
					 
					 // 116: the join item is found by its tag, not by its slot
					 String path = CustomJoinItem.pathOf(e.getCurrentItem());
					 if (path != null && ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")
							 && CustomJoinItem.inWorld(path, p) && !ConfigCJIGeneral.getConfig().isSet(path + "Special-Items")) {
						 if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")
								 || p.hasPermission("hawn.use.cji.item." + CustomJoinItem.keyOf(path))) {
							 if (ConfigCJIGeneral.getConfig().isSet(path + "Skull-Name") || CustomJoinItem.sameMaterial(e.getCurrentItem(), path)) {
								 for (String s: ConfigCJIGeneral.getConfig().getStringList(path + "Command-List")) {
									 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
								 }

								 e.setCancelled(true);
							 }
						 }
					 }
				 }
			 }
		 } catch (Exception e2) {}
	}
}