package fr.dianox.hawn.event;

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

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.HawnCommand;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiLobbyBow;
import fr.dianox.hawn.utility.world.CjiPW;

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
			
			if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
				if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
					return;
				}
			}
			
			if (!p.hasPermission("hawn.use.customjoinitem")) {
				 return;
			}
			
			e.setCancelled(true);
		}
	}
	
	@SuppressWarnings("deprecation")
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
		
		EquipmentSlot es = null;
		
		if (Main.Spigot_Version >= 19) {
			es = e.getHand();
		}
		
		if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable")) {
			
			if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
				if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
					return;
				}
			}
			
			if (!p.hasPermission("hawn.use.customjoinitem")) {
				 return;
			}

			try {
				if (Main.Spigot_Version >= 19) {
					if (es.equals(EquipmentSlot.HAND)) {
						if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (CustomJoinItem.itemcjislot.containsKey(p.getInventory().getHeldItemSlot())) {
								
								if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
									if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
										return;
									}
								}
								
								// SPECIAL ITEMS + 1.9
								if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
									if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-LobbyBow")) {
										String Check1 = SpecialCjiLobbyBow.getConfig().getString("LobbyBow.Item.Title");
										
										if (Check1.startsWith("&f")) {
											Check1 = Check1.substring(2, Check1.length());
										}
										
										Check1 = Check1.replaceAll("&", "§");
																				
										if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(Check1)) {
											if (p.getInventory().getItemInMainHand().getType() == XMaterial.BOW.parseMaterial()) {
												e.setCancelled(false);
												return;
											}
										}
									} else if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").contains("Special-Book")) {
										if (p.getInventory().getItemInMainHand().getType() == XMaterial.BOOK.parseMaterial()) {
											e.setCancelled(false);
											return;
										}
									}
								}
								
								// CLASSIC ITEMS + 1.9
								String Check1 = ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Title");
								
								if (Check1.startsWith("&f")) {
									Check1 = Check1.substring(2, Check1.length());
								}
								
								Check1 = Check1.replaceAll("&", "§");
								
								if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(Check1)) {
									if (!ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Skull-Name")) {
										if (p.getInventory().getItemInMainHand().getType() == XMaterial.getMat(ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Material"), "Custom Join Item")) {
											for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
												ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
												p.updateInventory();
											}
											
											e.setCancelled(true);
										}
									} else {
										for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
											ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
											p.updateInventory();
										}
										
										e.setCancelled(true);
									}
								}
							}
						}
					}
				} else {
					if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (CustomJoinItem.itemcjislot.containsKey(p.getInventory().getHeldItemSlot())) {
							
							// SPECIAL ITEMS 1.8
							if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
								if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-LobbyBow")) {
									String Check1 = SpecialCjiLobbyBow.getConfig().getString("LobbyBow.Item.Title");
									
									if (Check1.startsWith("&f")) {
										Check1 = Check1.substring(2, Check1.length());
									}
									
									Check1 = Check1.replaceAll("&", "§");
									
									if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check1)) {
										if (p.getItemInHand().getType() == XMaterial.BOW.parseMaterial()) {
											e.setCancelled(false);
											return;
										}
									}
								} else if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").contains("Special-Book")) {
									if (p.getItemInHand().getType() == XMaterial.BOOK.parseMaterial()) {
										e.setCancelled(false);
										return;
									}
								}
							}

							// CLASSIC ITEMS 1.8
							String Check1 = ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Title");
							
							if (Check1.startsWith("&f")) {
								Check1 = Check1.substring(2, Check1.length());
							}
							
							Check1 = Check1.replaceAll("&", "§");
							
							if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check1)) {
								if (!ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Skull-Name")) {
									if (p.getItemInHand().getType() == XMaterial.getMat(ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Material"), "Custom Join Item")) {
										for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
											ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
											p.updateInventory();
										}
										
										e.setCancelled(true);
									}
								} else {
									for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
										ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
										p.updateInventory();
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
					 if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
							if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
								return;
							}
					 }
					 
					 if (!p.hasPermission("hawn.use.customjoinitem")) {
						 return;
					 }
					 
					 // Interaction
					 
					 if (e.getSlot() == 39) {
						 
						 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Helmet.Enable")) {
							 String path_item = "Custom-Join-Item.Items.Armor.Helmet.Item.";
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Helmet-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"))) {
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
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Chestplate-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"))) {
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
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Leggings-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"))) {
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
							 
							 if (CustomJoinItem.itemcjiname.containsKey("Boots-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"))) {
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
					 
					 if (CustomJoinItem.itemcjislot.containsKey(e.getSlot())) {
						 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
							 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
								 if (p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(e.getSlot()))) {
									 if (!ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Special-Items")) {
										 String Check1 = ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Title");
										 
										 if (Check1.startsWith("&f")) {
											 Check1 = Check1.substring(2, Check1.length());
										 }
											
										 Check1 = Check1.replaceAll("&", "§");
										 									 
										 if (e.getCurrentItem().getItemMeta().getDisplayName().contains(Check1)) {
											 if (!ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Skull-Name")) {
												 if (e.getCurrentItem().getType() == XMaterial.getMat(ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Material"), "Custom Join Item")) {
													 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Command-List")) {
														 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
													 }
													 
													 e.setCancelled(true);
												 }
											 } else {
												 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Command-List")) {
													 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
												 }
												 
												 e.setCancelled(true);
											 }
										 }
									 }
								 }
							 } else {
								 if (!ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Special-Items")) {
									 String Check1 = ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Title");
									 
									 if (Check1.startsWith("&f")) {
										 Check1 = Check1.substring(2, Check1.length());
									 }
										
									 Check1 = Check1.replaceAll("&", "§");
									 
									 if (e.getCurrentItem().getItemMeta().getDisplayName().contains(Check1)) {
										 if (!ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Skull-Name")) {
											 if (e.getCurrentItem().getType() == XMaterial.getMat(ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Material"), "Custom Join Item")) {
												 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Command-List")) {
													 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
												 }
												 
												 e.setCancelled(true);
											 }
										 } else {
											 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Command-List")) {
												 ConfigEventUtils.ExecuteEvent(p, s, "none", "CustomJoinItem", false);
											 }
											 
											 e.setCancelled(true);
										 }
									 }
								 }
							 }
						 }
					 }
				 }
			 }
		 } catch (Exception e2) {}
	}
}