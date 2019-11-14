package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.messages.ConfigMPlayerOption;
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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("deprecation")
public class SpecialItemPlayerVisibility implements Listener {
	
	HashMap<Player, ItemStack> storedItem = new HashMap<Player, ItemStack>();
	
	public static String Check = SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Title");
	public static String CheckTwo = SpecialCjiHidePlayers.getConfig().getString("PV.ON.Title");

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBreakWithItemVisibility(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Ultimate-Protection-Of-The-Items")) {
				try {
					
					String Check1 = Check;
					String Check2 = CheckTwo;
					
					if (Check1.startsWith("&f")) {
						Check1 = Check1.substring(2, Check1.length());
					}
					
					if (Check2.startsWith("&f")) {
						Check2 = Check2.substring(2, Check2.length());
					}
					
					if (p.getItemInHand() != null && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§")) || p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check2.replaceAll("&", "§")))) {
						e.setCancelled(true);
					}
				} catch (Exception e1) {}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlaceWithItemVisibility(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Ultimate-Protection-Of-The-Items")) {
				try {
					
					String Check1 = Check;
					String Check2 = CheckTwo;
					
					if (Check1.startsWith("&f")) {
						Check1 = Check1.substring(2, Check1.length());
					}
					
					if (Check2.startsWith("&f")) {
						Check2 = Check2.substring(2, Check2.length());
					}
					
					if (p.getItemInHand() != null && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§")) || p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check2.replaceAll("&", "§")))) {
						e.setCancelled(true);
					}
				} catch (Exception e1) {}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDropWithItemVisibility(PlayerDropItemEvent e) {	
		Player p = e.getPlayer();
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Ultimate-Protection-Of-The-Items")) {
				try {
					
					String Check1 = Check;
					String Check2 = CheckTwo;
					
					if (Check1.startsWith("&f")) {
						Check1 = Check1.substring(2, Check1.length());
					}
					
					if (Check2.startsWith("&f")) {
						Check2 = Check2.substring(2, Check2.length());
					}
					
					if (p.getItemInHand() != null && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§")) || p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check2.replaceAll("&", "§")))) {
						e.setCancelled(true);
					}
				} catch (Exception e1) {}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onInventoryClickWithItemVisibility(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String name = e.getWhoClicked().getName();
		
		if (e.getSlotType() == SlotType.OUTSIDE) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Use_Permission")) {
				if (!p.hasPermission("hawn.event.interact.item.playervisibility")) {
					return;
				}
			}
			
			if (e.getSlot() == 39 || e.getSlot() == 38 || e.getSlot() == 37 || e.getSlot() == 36) {
				return;
			}
			
			String Check1 = Check;
			String Check2 = CheckTwo;
			
			if (Check1.startsWith("&f")) {
				Check1 = Check1.substring(2, Check1.length());
			}
			
			if (Check2.startsWith("&f")) {
				Check2 = Check2.substring(2, Check2.length());
			}
						
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§"))) {
				if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
					if (PlayerVisibility.Cooling().contains(name)) {
						e.setCancelled(true);
						if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
							for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
					    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
					    	}
						}
					} else {
						PlayerVisibility.Cooling().add(name);
						PlayerVisibility.hidePlayer(p);
						swithPVItemsOnJoinToON(p);
						soundInventoryClickPVOJI(p);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
							messageitemPVON(p);
						}
						PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
							@Override
							public void run() {
								PlayerVisibility.Cooling().remove(name);
							}
								
						}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
						Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
						e.setCancelled(true);
					}
				} else {
					PlayerVisibility.hidePlayer(p);
					swithPVItemsOnJoinToON(p);
					soundInventoryClickPVOJI(p);
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
						messageitemPVON(p);
					}
					PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
					e.setCancelled(true);
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains(Check2.replaceAll("&", "§"))) {
				e.setCancelled(true);
				if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
					if (PlayerVisibility.Cooling().contains(name)) {
						if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
							for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
					    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
					    	}
						}
					} else {
						PlayerVisibility.Cooling().add(name);
						PlayerVisibility.showPlayer(p);
						swithPVItemsOnJoinToOFF(p);
						soundInventoryClickPVOJI(p);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
							messageitemPVOFF(p);
						}
						PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
							@Override
							public void run() {
								PlayerVisibility.Cooling().remove(name);
							}
								
						}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
						Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
					}
				} else {
					e.setCancelled(true);
					PlayerVisibility.showPlayer(p);
					swithPVItemsOnJoinToOFF(p);
					soundInventoryClickPVOJI(p);
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
						messageitemPVOFF(p);
					}
					PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
				}
			}
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		String name = e.getPlayer().getName();
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (e.getAction() == Action.RIGHT_CLICK_AIR) {
				if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Use_Permission")) {
					if (!p.hasPermission("hawn.event.interact.item.playervisibility")) {
						return;
					}
				}
				
				String Check1 = Check;
				String Check2 = CheckTwo;
				
				if (Check1.startsWith("&f")) {
					Check1 = Check1.substring(2, Check1.length());
				}
				
				if (Check2.startsWith("&f")) {
					Check2 = Check2.substring(2, Check2.length());
				}
				
				try {
				if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§"))) {
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
						if (PlayerVisibility.Cooling().contains(name)) {
							if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
								for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
						    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
						    	}
							}
						} else {
							PlayerVisibility.Cooling().add(name);
							PlayerVisibility.hidePlayer(p);
							swithPVItemsOnJoinToON(p);
							soundInteractPVOJI(p);
							if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
								messageitemPVON(p);
							}
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
									
								@Override
								public void run() {
									PlayerVisibility.Cooling().remove(name);
								}
									
							}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
							Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
						}
					} else {
						e.setCancelled(true);
						PlayerVisibility.hidePlayer(p);
						swithPVItemsOnJoinToON(p);
						soundInteractPVOJI(p);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
							messageitemPVON(p);
						}
						PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
					}
				} else if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check2.replaceAll("&", "§"))) {
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
						if (PlayerVisibility.Cooling().contains(name)) {
							if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
								for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
						    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
						    	}
							}
						} else {
							PlayerVisibility.Cooling().add(name);
							PlayerVisibility.showPlayer(p);
							swithPVItemsOnJoinToOFF(p);
							soundInteractPVOJI(p);
							if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
								messageitemPVOFF(p);
							}
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
									
								@Override
								public void run() {
									PlayerVisibility.Cooling().remove(name);
								}
									
							}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
							Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
						}
					} else {
						e.setCancelled(true);
						PlayerVisibility.showPlayer(p);
						swithPVItemsOnJoinToOFF(p);
						soundInteractPVOJI(p);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
							messageitemPVOFF(p);
						}
						PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
					}
				}
				} catch (Exception e1) {}
			}
		}
	}
	
	public static void PlayerGivePlayerVisibilityItemOnJoincji(Player p, Integer slot) {
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			return;
		}
		
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.World.All_World")) {
			if (!CjiPW.getWItemPVOnJoin().contains(p.getWorld().getName())) {
				return;
			}
		}
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Use_Permission")) {
			if (!p.hasPermission("hawn.event.interact.item.playervisibility")) {
				return;
			}
		}
		
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
				CreateItem(p, false, slot);
			} else {
				CreateItem(p, true, slot);
			}
		} else {
			String value = PlayerOptionSQLClass.getValueMysqlYaml(p);
			
			if (value.equalsIgnoreCase("FALSE")) {
				CreateItem(p, false, slot);
			} else {
				CreateItem(p, true, slot);
			}
		}
	}
	
	public static void PlayerGivePlayerVisibilityItemOnJoin(Player p) {
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			return;
		}
		
		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.World.All_World")) {
			if (!CjiPW.getWItemPVOnJoin().contains(p.getWorld().getName())) {
				return;
			}
		}

		if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
				PlayerVisibility.showPlayer(p);
				PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
			} else {
				PlayerVisibility.hidePlayer(p);
				PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
			}
		} else {
			String value = PlayerOptionSQLClass.getValueMysqlYaml(p);
					
			if (value.equalsIgnoreCase("FALSE")) {
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
								Check1 = Check1.substring(2, Check1.length());
							}
							
							if (p.getInventory().getItem(i).getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§"))) {
								p.getInventory().clear(i);
								CreateItem(p, true, i);
								break;
							}
						} catch (Exception e1) {}
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
								Check2 = Check2.substring(2, Check2.length());
							}
							
							if (p.getInventory().getItem(i).getItemMeta().getDisplayName().contains(Check2.replaceAll("&", "§"))) {
								p.getInventory().clear(i);
								CreateItem(p, false, i);
								break;
							}
						} catch (Exception e1) {}
					}
				}
			}
		} catch (Exception e) {
			Bukkit.getConsoleSender().sendMessage("§c[Hawn] Error, Unknow error, oh damn, impossible to switch item on swithPVItemsOnJoinToON");
			e.printStackTrace();	
		}
	}
	
	public static void messageitemPVON(Player p) {
		if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.PlayerVisibility.ON.Enable")) {
			for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.PlayerVisibility.ON.Messages")) {
				MessageUtils.ReplaceCharMessagePlayer(msg, p);
	    	}
		}
	}
	
	public static void messageitemPVOFF(Player p) {
		if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.PlayerVisibility.OFF.Enable")) {
			for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.PlayerVisibility.OFF.Messages")) {
	    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
	    	}
		}
	}
	
	// Sound
	public void soundInventoryClickPVOJI(Player p) {
		String sound = SpecialCjiHidePlayers.getConfig().getString("PV.Option.Inventory-Click.Sounds.Sound");
		int volume = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Inventory-Click.Sounds.Volume");
		int pitch = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Inventory-Click.Sounds.Pitch");
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Sounds.Enable")) {
			p.playSound(p.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
		}
	}
	
	public void soundInteractPVOJI(Player p) {
		String sound = SpecialCjiHidePlayers.getConfig().getString("PV.Option.Interact-With-Item.Sounds.Sound");
		int volume = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Interact-With-Item.Sounds.Volume");
		int pitch = SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Interact-With-Item.Sounds.Pitch");
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Interact-With-Item.Sounds.Enable")) {
			p.playSound(p.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
		}
	}
	
	public static void CreateItem(Player p, Boolean bool, Integer slot) {
		String material = "";
		ItemStack item = null;
		Integer amount = 1;
		ItemMeta itemmeta = null;
		SkullMeta meta = null;
		String skullname = "";
		String onoroff = "";
		
		ArrayList < String > lore = new ArrayList < String > ();
		Boolean lorecheck = false;
		
		String title = "thistitleinsnotforuseorsomethingelse";
		
		if (bool) {
			onoroff = "ON";
		} else {
			onoroff = "OFF";
		}
		
		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Title")) {
			String pretitle = SpecialCjiHidePlayers.getConfig().getString("PV."+onoroff+".Title");
				
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
				pretitle = PlaceholderAPI.setPlaceholders(p, pretitle);
			}
	
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
				pretitle = PlaceHolders.BattleLevelPO(pretitle, p);
			}
	            
			pretitle = PlaceHolders.ReplaceMainplaceholderP(pretitle, p);
				
			pretitle = pretitle.replaceAll("&", "§");
			
			title = pretitle;
		}
		
		material = SpecialCjiHidePlayers.getConfig().getString("PV."+onoroff+".Material.Material");
			
		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Amount")) {
			amount = SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Amount");
		}
			
		if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Lore")) {
			for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV."+onoroff+".Lore")) {
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
				}

				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
					loremsg = PlaceHolders.BattleLevelPO(loremsg, p);
				}
		            
				loremsg = PlaceHolders.ReplaceMainplaceholderP(loremsg, p);
					
				loremsg = loremsg.replaceAll("&", "§");
					
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
				if (Main.Spigot_Version >= 113) {
					item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), amount);
				} else {
					item = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
				}
			} else {
				if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Data-value")) {
					item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount, (short) SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Data-value"));
				} else {
					item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount);
				}
			}
		} else {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV."+onoroff+".Material.Data-value")) {
				item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount, (short) SpecialCjiHidePlayers.getConfig().getInt("PV."+onoroff+".Material.Data-value"));
			} else {
				item = new ItemStack(XMaterial.matchXMaterial(material).parseMaterial(), amount);
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