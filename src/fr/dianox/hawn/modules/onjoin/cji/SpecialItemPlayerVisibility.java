package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.CjiPW;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
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
import java.util.HashMap;

public class SpecialItemPlayerVisibility implements Listener {
	
	HashMap<Player, ItemStack> storedItem = new HashMap<>();
	
	public static String Check = SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Title");
	public static String CheckTwo = SpecialCjiHidePlayers.getConfig().getString("PV.ON.Title");
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInventoryClickWithItemVisibility(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();

		if (e.getSlotType() == SlotType.OUTSIDE) return;
		if (item == null || item.getType() == Material.AIR) return;
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
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
				if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(e.getSlot()))) {
					p.sendMessage("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(e.getSlot()));
					return;
				}
			}

			if (e.getSlot() >= 36 && e.getSlot() <= 39) {
				return;
			}

			String Check1 = Check;
			String Check2 = CheckTwo;
			
			if (Check1.startsWith("&f")) {
				Check1 = Check1.substring(2);
			}
			
			Check1 = MessageUtils.colourTheStuff(Check1);
			
			if (Check2.startsWith("&f")) {
				Check2 = Check2.substring(2);
			}
			
			Check2 = MessageUtils.colourTheStuff(Check2);
			
			try {
				if (item.getItemMeta().getDisplayName().contains(Check1)) {
					if (item.getType() == XMaterial.getMat(SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material"), "custom join item - special item - pv")) {
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
							if (PlayerVisibility.Cooling().contains(p)) {
								e.setCancelled(true);
								if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
							    		ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.Error.Player-Visibility.Time.Messages", "SpecialItemPlayerVisibility", false);
							    	}
								}
							} else {
								PlayerVisibility.Cooling().add(p);
								SwitchTrue(p, true);
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerVisibility.Cooling().remove(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
								Main.hiderCooldowns.put(p, System.currentTimeMillis());
								e.setCancelled(true);
							}
						} else {
							SwitchTrue(p, true);
							e.setCancelled(true);
						}
					}
				} else if (item.getItemMeta().getDisplayName().contains(Check2)) {
					if (item.getType() == XMaterial.getMat(SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material"), "custom join item - special item - pv")) {
						e.setCancelled(true);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
							if (PlayerVisibility.Cooling().contains(p)) {
								if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
							    		ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.Error.Player-Visibility.Time.Messages", "SpecialItemPlayerVisibility", false);
							    	}
								}
							} else {
								PlayerVisibility.Cooling().add(p);
								SwitchFalse(p, true);
								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerVisibility.Cooling().remove(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
								Main.hiderCooldowns.put(p, System.currentTimeMillis());
							}
						} else {
							e.setCancelled(true);
							SwitchFalse(p, true);
						}
					}
				}
			} catch (Exception ignored) {}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			
			// Check Worlds
			if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
				if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
					return;
				}
			}
			
			if (!p.hasPermission("hawn.use.customjoinitem")) {
				 return;
			}
			
			EquipmentSlot es = null;
			
			if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 19) {
				es = e.getHand();
			}
			
			try {
				if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 19) {
					if (es.equals(EquipmentSlot.HAND)) {
						if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
							if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
								if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
									return;
								}
							}
							
							String Check1 = Check;
							String Check2 = CheckTwo;
							
							if (Check1.startsWith("&f")) {
								Check1 = Check1.substring(2);
							}
							
							Check1 = MessageUtils.colourTheStuff(Check1);
							
							if (Check2.startsWith("&f")) {
								Check2 = Check2.substring(2);
							}
							
							Check2 = MessageUtils.colourTheStuff(Check2);
							
							try {
							if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(Check1)) {
								if (p.getInventory().getItemInMainHand().getType() == XMaterial.getMat(SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material"), "custom join item - special item - pv")) {
									if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
										if (PlayerVisibility.Cooling().contains(p)) {
											if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
												for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
										    		ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.Error.Player-Visibility.Time.Messages", "SpecialItemPlayerVisibility", false);
										    	}
											}
										} else {
											PlayerVisibility.Cooling().add(p);
											SwitchTrue(p, false);
											Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerVisibility.Cooling().remove(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
											Main.hiderCooldowns.put(p, System.currentTimeMillis());
											e.setCancelled(true);
										}
									} else {
										SwitchTrue(p, false);
										e.setCancelled(true);
									}
								}
							} else if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(Check2)) {
								if (p.getInventory().getItemInMainHand().getType() == XMaterial.getMat(SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material"), "custom join item - special item - pv")) {
									if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
										if (PlayerVisibility.Cooling().contains(p)) {
											if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
												for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
										    		ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.Error.Player-Visibility.Time.Messages", "SpecialItemPlayerVisibility", false);
										    	}
											}
										} else {
											PlayerVisibility.Cooling().add(p);
											SwitchFalse(p, false);
											Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerVisibility.Cooling().remove(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
											Main.hiderCooldowns.put(p, System.currentTimeMillis());
											e.setCancelled(true);
										}
									} else {
										SwitchFalse(p, false);
										e.setCancelled(true);
									}
								}
							}
							} catch (Exception ignored) {}
						}
					}
				} else {
					if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
							if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
								return;
							}
						}
						
						String Check1 = Check;
						String Check2 = CheckTwo;
						
						if (Check1.startsWith("&f")) {
							Check1 = Check1.substring(2);
						}
						
						Check1 = MessageUtils.colourTheStuff(Check1);
						
						if (Check2.startsWith("&f")) {
							Check2 = Check2.substring(2);
						}
						
						Check2 = MessageUtils.colourTheStuff(Check2);
						
						try {
						if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check1)) {
							if (p.getItemInHand().getType() == XMaterial.getMat(SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material"), "custom join item - special item - pv")) {
								if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
									if (PlayerVisibility.Cooling().contains(p)) {
										if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
											for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
									    		ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.Error.Player-Visibility.Time.Messages", "SpecialItemPlayerVisibility", false);
									    	}
										}
									} else {
										PlayerVisibility.Cooling().add(p);
										SwitchTrue(p, false);
										Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerVisibility.Cooling().remove(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
										Main.hiderCooldowns.put(p, System.currentTimeMillis());
										e.setCancelled(true);
									}
								} else {
									SwitchTrue(p, false);
									e.setCancelled(true);
								}
							}
						} else if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check2)) {
							if (p.getItemInHand().getType() == XMaterial.getMat(SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material"), "custom join item - special item - pv")) {
								if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
									if (PlayerVisibility.Cooling().contains(p)) {
										if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
											for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
									    		ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.Error.Player-Visibility.Time.Messages", "SpecialItemPlayerVisibility", false);
									    	}
										}
									} else {
										PlayerVisibility.Cooling().add(p);
										SwitchFalse(p, false);
										Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PlayerVisibility.Cooling().remove(p), SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
										Main.hiderCooldowns.put(p, System.currentTimeMillis());
									}
								} else {
									SwitchFalse(p, false);
									e.setCancelled(true);
								}
							}
						}
						} catch (Exception ignored) {}
					}
				}
			} catch (Exception ignored) {}
		}
	}

	private void SwitchTrue(Player p, Boolean InventoryClick) {
		// Hide Players
		PlayerVisibility.hidePlayer(p);

		// Change item in the inventory
		swithPVItemsOnJoinToON(p);

		// Sound
		if (InventoryClick) {
			soundInventoryClickPVOJI(p);
		} else {
			soundInteractPVOJI(p);
		}

		// Message
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
			messageitemPVON(p);
		}

		// DataBase
		PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
	}

	private void SwitchFalse(Player p, Boolean InventoryClick) {
		// Show players
		PlayerVisibility.showPlayer(p);

		// Change item in the inventory
		swithPVItemsOnJoinToOFF(p);

		// Sound
		if (InventoryClick) {
			soundInventoryClickPVOJI(p);
		} else {
			soundInteractPVOJI(p);
		}

		// Message
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
			messageitemPVOFF(p);
		}

		// DataBase
		PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
	}
	
	public static void PlayerGivePlayerVisibilityItemOnJoincji(Player p, Integer slot) {
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
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

		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
			String value = PlayerOptionSQLClass.getValueMysqlYaml(p);

			CreateItem(p, ! value.equalsIgnoreCase("FALSE"), slot);
		} else {
			CreateItem(p, ! SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers"), slot);
		}
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

		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
			String value = PlayerOptionSQLClass.getValueMysqlYaml(p);

			if (value.equalsIgnoreCase("FALSE")) {
				PlayerVisibility.showPlayer(p);
				PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
			} else {
				PlayerVisibility.hidePlayer(p);
				PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
			}
		} else {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
				PlayerVisibility.showPlayer(p);
				PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
			} else {
				PlayerVisibility.hidePlayer(p);
				PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
			}
		}
	}
	
	public static void swithPVItemsOnJoinToON(Player p) {
		try {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
				for (int i = 0 ; i <= 35; ++i) {
					if (p.getInventory().getItem(i) != null) {
						try {
							
							String Check1 = Check;
							
							if (Check1.startsWith("&f")) {
								Check1 = Check1.substring(2);
							}
							
							if (p.getInventory().getItem(i).getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§"))) {
								p.getInventory().clear(i);
								CreateItem(p, true, i);
								break;
							}
						} catch (Exception ignored) {}
					}
				}
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§c[Hawn] Error, Unknow error, oh damn, impossible to switch item on swithPVItemsOnJoinToON");
			e.printStackTrace();		
		}
	}
	
	public static void swithPVItemsOnJoinToOFF(Player p) {
		try {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
				for (int i = 0 ; i <= 35; ++i) {
					if (p.getInventory().getItem(i) != null) {
						try {
							
							String Check2 = CheckTwo;
							
							if (Check2.startsWith("&f")) {
								Check2 = Check2.substring(2);
							}
							
							if (p.getInventory().getItem(i).getItemMeta().getDisplayName().contains(Check2.replaceAll("&", "§"))) {
								p.getInventory().clear(i);
								CreateItem(p, false, i);
								break;
							}
						} catch (Exception ignored) {}
					}
				}
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§c[Hawn] Error, Unknow error, oh damn, impossible to switch item on swithPVItemsOnJoinToON");
			e.printStackTrace();	
		}
	}
	
	public static void messageitemPVON(Player p) {
		if (ConfigMMsg.getConfig().getBoolean("PlayerOption.PlayerVisibility.ON.Enable")) {
			for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.PlayerVisibility.ON.Messages")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.PlayerVisibility.ON.Messages", "SpecialItemPlayerVisibility", false);
	    	}
		}
	}
	
	public static void messageitemPVOFF(Player p) {
		if (ConfigMMsg.getConfig().getBoolean("PlayerOption.PlayerVisibility.OFF.Enable")) {
			for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.PlayerVisibility.OFF.Messages")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "PlayerOption.PlayerVisibility.OFF.Messages", "SpecialItemPlayerVisibility", false);
	    	}
		}
	}
	
	// Sound
	public void soundInventoryClickPVOJI(Player p) {
		String sound = SpecialCjiHidePlayers.getConfig().getString("PV.Option.Inventory-Click.Sounds.Sound");
		int volume = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Inventory-Click.Sounds.Volume");
		int pitch = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Inventory-Click.Sounds.Pitch");
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Sounds.Enable")) {
			p.playSound(p.getLocation(), XSound.getSound(sound, "PV.Option.Inventory-Click.Sounds.Sound"), volume, pitch);
			
		}
	}
	
	public void soundInteractPVOJI(Player p) {
		String sound = SpecialCjiHidePlayers.getConfig().getString("PV.Option.Interact-With-Item.Sounds.Sound");
		int volume = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Interact-With-Item.Sounds.Volume");
		int pitch = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Interact-With-Item.Sounds.Pitch");
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Interact-With-Item.Sounds.Enable")) {
			p.playSound(p.getLocation(), XSound.getSound(sound, "PV.Option.Interact-With-Item.Sounds.Sound"), volume, pitch);
		}
	}
	
	public static void CreateItem(Player p, Boolean bool, Integer slot) {
		String material = "";
		ItemStack item = null;
		int amount = 1;
		ItemMeta itemmeta = null;
		SkullMeta meta = null;
		String skullname = "";
		String onoroff = "";
		
		ArrayList <String> lore = new ArrayList <> ();
		boolean lorecheck = false;
		
		String title = "thistitleinsnotforuseorsomethingelse";
		
		if (bool) {
			onoroff = "ON";
		} else {
			onoroff = "OFF";
		}
		
		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Title")) {
			String pretitle = SpecialCjiHidePlayers.getConfig().getString("PV."+onoroff+".Title");
				
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				pretitle = PlaceholderAPI.setPlaceholders(p, pretitle);
			}
	
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				pretitle = PlaceHolders.BattleLevelPO(pretitle, p);
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
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
				}

				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
					loremsg = PlaceHolders.BattleLevelPO(loremsg, p);
				}
		            
				loremsg = PlaceHolders.ReplaceMainplaceholderP(loremsg, p);
					
				loremsg = MessageUtils.colourTheStuff(loremsg);
					
				lore.add(loremsg);
					
				lorecheck = true;
			}
		}
			
		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Skull-Name")) {	
			skullname = SpecialCjiHidePlayers.getConfig().getString("PV."+onoroff+".Material.Skull-Name");
				
			skullname = skullname.replaceAll("%player%", p.getName());
		}
			
		if (material.contains("SKULL")) {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Skull-Name")) {
				if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 113) {
					item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), amount);
				} else {
					item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
				}
			} else {
				if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Data-value")) {
					item = new ItemStack(XMaterial.getMat(material, "PV."+onoroff+".Material"), amount, (short) SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Data-value"));
				} else {
					item = new ItemStack(XMaterial.getMat(material, "PV."+onoroff+".Material"), amount);
				}
			}
		} else {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Data-value")) {
				item = new ItemStack(XMaterial.getMat(material, "PV."+onoroff+".Material"), amount, (short) SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Data-value"));
			} else {
				item = new ItemStack(XMaterial.getMat(material, "PV."+onoroff+".Material"), amount);
			}
		}
		
		if (!SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Skull-Name") || !material.contains("SKULL")) {
			itemmeta = item.getItemMeta();
		} else {
			itemmeta = item.getItemMeta();
			meta = (SkullMeta) item.getItemMeta();
			meta.setOwner(skullname);
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
		
		p.getInventory().setItem(slot, item);
	}
}