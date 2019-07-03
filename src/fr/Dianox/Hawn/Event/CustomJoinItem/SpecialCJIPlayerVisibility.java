package fr.Dianox.Hawn.Event.CustomJoinItem;

import java.util.ArrayList;
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

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.SQL;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.PlayerVisibility;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.XSound;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.PlayerConfig;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMPlayerOption;
import fr.Dianox.Hawn.Utility.World.CjiPW;
import me.clip.placeholderapi.PlaceholderAPI;

@SuppressWarnings("deprecation")
public class SpecialCJIPlayerVisibility implements Listener {
	
	public static String Check = SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Title");
	public static String CheckTwo = SpecialCjiHidePlayers.getConfig().getString("PV.ON.Title");

	public static String getCheck() {
		return Check;
	}
	
	public static String getCheckTwo() {
		return CheckTwo;
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBreakWithItemVisibility(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Ultimate-Protection-Of-The-Items")) {
				if ((p.getItemInHand().getItemMeta().getDisplayName().contains(getCheck().replaceAll("&", "§"))) || (p.getItemInHand().getItemMeta().getDisplayName().contains(getCheckTwo().replaceAll("&", "§")))) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlaceWithItemVisibility(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Ultimate-Protection-Of-The-Items")) {
				if ((p.getItemInHand().getItemMeta().getDisplayName().contains(getCheck().replaceAll("&", "§"))) || (p.getItemInHand().getItemMeta().getDisplayName().contains(getCheckTwo().replaceAll("&", "§")))) {
					e.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDropWithItemVisibility(PlayerDropItemEvent e) {		
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Ultimate-Protection-Of-The-Items")) {
				if ((e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains(getCheck().replaceAll("&", "§"))) || (e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains(getCheckTwo().replaceAll("&", "§")))) {
					e.setCancelled(true);
				}
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
			
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains(getCheck().replaceAll("&", "§"))) {
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
						PlayerVisibility.hidePlayer(p);
						swithPVItemsOnJoinToON(p);
						soundInventoryClickPVOJI(p);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
							messageitemPVON(p);
						}
						onMysqlYamlCJIChange(p, "TRUE");
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
							@Override
							public void run() {
								PlayerVisibility.Cooling().remove(name);
							}
								
						}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
						
					}
				} else {
					e.setCancelled(true);
					PlayerVisibility.hidePlayer(p);
					swithPVItemsOnJoinToON(p);
					soundInventoryClickPVOJI(p);
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
						messageitemPVON(p);
					}
					onMysqlYamlCJIChange(p, "TRUE");
				}
			} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains(getCheckTwo().replaceAll("&", "§"))) {
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
						onMysqlYamlCJIChange(p, "FALSE");
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								
							@Override
							public void run() {
								PlayerVisibility.Cooling().remove(name);
							}
								
						}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
						
					}
				} else {
					e.setCancelled(true);
					PlayerVisibility.showPlayer(p);
					swithPVItemsOnJoinToOFF(p);
					soundInventoryClickPVOJI(p);
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
						messageitemPVOFF(p);
					}
					onMysqlYamlCJIChange(p, "FALSE");
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
				
				if (p.getItemInHand().getItemMeta().getDisplayName().contains(getCheck().replaceAll("&", "§"))) {
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
							onMysqlYamlCJIChange(p, "TRUE");
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
									
								@Override
								public void run() {
									PlayerVisibility.Cooling().remove(name);
								}
									
							}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
							
						}
					} else {
						e.setCancelled(true);
						PlayerVisibility.hidePlayer(p);
						swithPVItemsOnJoinToON(p);
						soundInteractPVOJI(p);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
							messageitemPVON(p);
						}
						onMysqlYamlCJIChange(p, "TRUE");
					}
				} else if (p.getItemInHand().getItemMeta().getDisplayName().contains(getCheckTwo().replaceAll("&", "§"))) {
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
							onMysqlYamlCJIChange(p, "FALSE");
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
									
								@Override
								public void run() {
									PlayerVisibility.Cooling().remove(name);
								}
									
							}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
							
						}
					} else {
						e.setCancelled(true);
						PlayerVisibility.showPlayer(p);
						swithPVItemsOnJoinToOFF(p);
						soundInteractPVOJI(p);
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Inventory-Click.Show-Messages")) {
							messageitemPVOFF(p);
						}
						onMysqlYamlCJIChange(p, "FALSE");
					}
				}
			}
		}
	}
	
	public static void PlayerGivePlayerVisibilityItemOnJoin(Player p) {
		if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
			if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.World.All_World")) {
				if (CjiPW.getWItemPVOnJoin().contains(p.getWorld().getName())) {
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Use_Permission")) {
						if (p.hasPermission("hawn.event.interact.item.playervisibility")) {
							if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
								if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
									CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
								} else {
									CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
								}
							} else {
								String value = getValueMysqlYaml(p);
								
								if (value.equalsIgnoreCase("FALSE")) {
									CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
								} else {
									CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
								}
							}
						}
					} else {
						if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
							if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
								CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
							} else {
								CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
							}
						} else {
							String value = getValueMysqlYaml(p);
							
							if (value.equalsIgnoreCase("FALSE")) {
								CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
							} else {
								CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
							}
						}
					}
					
					if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
							PlayerVisibility.showPlayer(p);
							onMysqlYamlCJIChange(p, "FALSE");
						} else {
							PlayerVisibility.hidePlayer(p);
							onMysqlYamlCJIChange(p, "TRUE");
						}
					} else {
						String value = getValueMysqlYaml(p);
						
						if (value.equalsIgnoreCase("FALSE")) {
							PlayerVisibility.showPlayer(p);
							onMysqlYamlCJIChange(p, "FALSE");
						} else {
							PlayerVisibility.hidePlayer(p);
							onMysqlYamlCJIChange(p, "TRUE");
						}
					}
				}
			} else {
				if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Use_Permission")) {
					if (p.hasPermission("hawn.event.interact.item.playervisibility")) {
						if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
							if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
								CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
							} else {
								CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
							}
						} else {
							String value = getValueMysqlYaml(p);
							
							if (value.equalsIgnoreCase("FALSE")) {
								CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
							} else {
								CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
							}
						}
					}
				} else {
					if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
							CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
						} else {
							CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
						}
					} else {
						String value = getValueMysqlYaml(p);
						
						if (value.equalsIgnoreCase("FALSE")) {
							CreateItemsOff(p, SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Slot"));
						} else {
							CreateItemsOn(p, SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Slot"));
						}
					}
				}
				
				if (!SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-Priority-For-Player-Option")) {
					if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.OnJoin-ShowPlayers")) {
						PlayerVisibility.showPlayer(p);
						onMysqlYamlCJIChange(p, "FALSE");
					} else {
						PlayerVisibility.hidePlayer(p);
						onMysqlYamlCJIChange(p, "TRUE");
					}
				} else {
					String value = getValueMysqlYaml(p);
					
					if (value.equalsIgnoreCase("FALSE")) {
						PlayerVisibility.showPlayer(p);
						onMysqlYamlCJIChange(p, "FALSE");
					} else {
						PlayerVisibility.hidePlayer(p);
						onMysqlYamlCJIChange(p, "TRUE");
					}
				}
			}
		}
	}
	
	public static void swithPVItemsOnJoinToON(Player p) {
		try {
			if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable")) {
				for (int i = 0 ; i <= 35; ++i) {
					if (p.getInventory().getItem(i) != null) {
						if (p.getInventory().getItem(i).getItemMeta().getDisplayName().contains(getCheck().replaceAll("&", "§"))) {
							p.getInventory().clear(i);
							CreateItemsOn(p, i);
							break;
						}
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
						if (p.getInventory().getItem(i).getItemMeta().getDisplayName().contains(getCheckTwo().replaceAll("&", "§"))) {
							p.getInventory().clear(i);
							CreateItemsOff(p, i);
							break;
						}
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
	
	public static void CreateItemsOn(Player p, Integer i) {
		
		ItemStack ref1 = null;
        ItemMeta metaref1 = null;
        SkullMeta meta = null;
        ArrayList < String > lore = new ArrayList < String > ();
		String displayname = SpecialCjiHidePlayers.getConfig().getString("PV.ON.Title");
		displayname = displayname.replaceAll("&", "§");
        
		if (SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material").equalsIgnoreCase("SKULL") ||
				SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material").equalsIgnoreCase("LEGACY_SKULL") ||
				SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material").equalsIgnoreCase("SKULL_ITEM") ||
				SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV.ON.Material.Material.Skull-Name")) {
				String skullname = SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material.Skull-Name");
				skullname = skullname.replaceAll("%player%", p.getName());
				ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Amount"), (short) SkullType.PLAYER.ordinal());
				metaref1 = ref1.getItemMeta();
                meta = (SkullMeta) ref1.getItemMeta();
                meta.setOwner(skullname);
                
                if (SpecialCjiHidePlayers.getConfig().isSet("PV.ON.Lore")) {
	                for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV.ON.Lore")) {
	                    loremsg = loremsg.replaceAll("&", "§");
	                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
	                    }
	
	                    lore.add(loremsg);
	                }
	
	                meta.setLore(lore);
	            }
		
	            meta.setDisplayName(displayname);
	
	
	            ref1.setItemMeta(meta);
	            p.getInventory().setItem(i, ref1);

			} else {
				ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Amount"));
                metaref1 = ref1.getItemMeta();
                
                if (SpecialCjiHidePlayers.getConfig().isSet("PV.ON.Lore")) {
	                for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV.ON.Lore")) {
	                    loremsg = loremsg.replaceAll("&", "§");
	                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
	                    }
	
	                    lore.add(loremsg);
	                }
	                metaref1.setLore(lore);
	            }
                
                metaref1.setDisplayName(displayname);
	
	
	            ref1.setItemMeta(metaref1);
	            p.getInventory().setItem(i, ref1);
			}
		} else {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV.ON.Material.Data-value")) {
				if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
					ref1 = new ItemStack(XMaterial.matchXMaterial(SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material")).parseMaterial(), 
	    					SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Amount"));
				} else {
					ref1 = new ItemStack(XMaterial.matchXMaterial(SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material")).parseMaterial(), 
    					SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Amount"), 
    					(short) SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Data-value"));
				}
    		} else {
    			ref1 = new ItemStack(XMaterial.matchXMaterial(SpecialCjiHidePlayers.getConfig().getString("PV.ON.Material.Material")).parseMaterial(), 
    					SpecialCjiHidePlayers.getConfig().getInt("PV.ON.Material.Amount"));
    		}
            metaref1 = ref1.getItemMeta();
            
            if (SpecialCjiHidePlayers.getConfig().isSet("PV.ON.Lore")) {
                for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV.ON.Lore")) {
                    loremsg = loremsg.replaceAll("&", "§");
                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                    }
                    lore.add(loremsg);
                }
                
                metaref1.setLore(lore);
            }
            metaref1.setDisplayName(displayname);


            ref1.setItemMeta(metaref1);
            p.getInventory().setItem(i, ref1);
		}
	}

	public static void CreateItemsOff(Player p, Integer i) {
		
		ItemStack ref1 = null;
        ItemMeta metaref1 = null;
        SkullMeta meta = null;
        ArrayList < String > lore = new ArrayList < String > ();
        String displayname = SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Title");
		displayname = displayname.replaceAll("&", "§");
		
		if (SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material").equalsIgnoreCase("SKULL") ||
				SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material").equalsIgnoreCase("LEGACY_SKULL") ||
				SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material").equalsIgnoreCase("SKULL_ITEM") ||
				SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV.OFF.Material.Material.Skull-Name")) {
				String skullname = SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material.Skull-Name");
				skullname = skullname.replaceAll("%player%", p.getName());
				ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Amount"), (short) SkullType.PLAYER.ordinal());
				metaref1 = ref1.getItemMeta();
                meta = (SkullMeta) ref1.getItemMeta();
                meta.setOwner(skullname);
                
                if (SpecialCjiHidePlayers.getConfig().isSet("PV.OFF.Lore")) {
	                for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV.OFF.Lore")) {
	                    loremsg = loremsg.replaceAll("&", "§");
	                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
	                    }
	
	                    lore.add(loremsg);
	                }
	
	                meta.setLore(lore);
	            }
		
	            meta.setDisplayName(displayname);
	
	
	            ref1.setItemMeta(meta);
	            p.getInventory().setItem(i, ref1);

			} else {
				ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Amount"));
                metaref1 = ref1.getItemMeta();
                
                if (SpecialCjiHidePlayers.getConfig().isSet("PV.OFF.Lore")) {
	                for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV.OFF.Lore")) {
	                    loremsg = loremsg.replaceAll("&", "§");
	                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
	                    }
	
	                    lore.add(loremsg);
	                }
	
	                metaref1.setLore(lore);
	            }
		
                metaref1.setDisplayName(displayname);
	
	
	            ref1.setItemMeta(metaref1);
	            p.getInventory().setItem(i, ref1);
			}
		} else {
			if (SpecialCjiHidePlayers.getConfig().isSet("PV.OFF.Material.Data-value")) {
				if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
					ref1 = new ItemStack(XMaterial.matchXMaterial(SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material")).parseMaterial(), 
	    					SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Amount"));
				} else {
					ref1 = new ItemStack(XMaterial.matchXMaterial(SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material")).parseMaterial(), 
    					SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Amount"), 
    					(short) SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Data-value"));
				}
    		} else {
    			ref1 = new ItemStack(XMaterial.matchXMaterial(SpecialCjiHidePlayers.getConfig().getString("PV.OFF.Material.Material")).parseMaterial(), 
    					SpecialCjiHidePlayers.getConfig().getInt("PV.OFF.Material.Amount"));
    		}
            metaref1 = ref1.getItemMeta();
            
            if (SpecialCjiHidePlayers.getConfig().isSet("PV.OFF.Lore")) {
                for (String loremsg: SpecialCjiHidePlayers.getConfig().getStringList("PV.OFF.Lore")) {
                    loremsg = loremsg.replaceAll("&", "§");
                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                    }

                    lore.add(loremsg);
                }

                metaref1.setLore(lore);
            }
	
            metaref1.setDisplayName(displayname);


            ref1.setItemMeta(metaref1);
            p.getInventory().setItem(i, ref1);
		}
	}
	
	public static String getValueMysqlYaml(Player p) {
		String value = "";
		
		if (!PlayerConfig.getConfig().isSet("player_option_pv."+p.getUniqueId()+".player_name")) {
			if (PlayerVisibility.PVPlayer.contains(p)) {
				PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".Activate", Boolean.valueOf(true));
				PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
			} else {
				PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
				PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
			}
			
            PlayerConfig.saveConfigFile();
        }
		
		PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		PlayerConfig.saveConfigFile();
		
		if (Main.useyamllistplayer) {
			if (PlayerConfig.getConfig().getBoolean("player_option_pv."+p.getUniqueId()+".Activate"))  {
				value = "TRUE";
			} else {
				value = "FALSE";
			}
		} else {
			if (!SQL.tableExists("player_option_pv")) {
				SQL.createTable("player_option_pv", "player TEXT, player_UUID TEXT, Activate TEXT");
			}
			
			if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_pv")) {
				value = String.valueOf(SQL.getInfoString("player_option_pv", "Activate", "" + p.getUniqueId() + ""));
				SQL.set("player_option_pv", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				if (PlayerConfig.getConfig().getBoolean("player_option_pv."+p.getUniqueId()+".Activate"))  {
					value = "TRUE";
					SQL.insertData("player, player_UUID, Activate",
	                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_pv");
				} else {
					value = "FALSE";
					SQL.insertData("player, player_UUID, Activate",
	                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + value + "' ", "player_option_pv");
				}
			}
		}
		
		return value;
	}
	
	public static void onMysqlYamlCJIChange(Player p, String boolea) {
		
		PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
		if (boolea.equalsIgnoreCase("FALSE")) {
			PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
		} else {
			PlayerConfig.getConfig().set("player_option_pv."+p.getUniqueId()+".Activate", Boolean.valueOf(true));
		}
		
		PlayerConfig.saveConfigFile();
		
		if (!Main.useyamllistplayer) {
			if (!SQL.tableExists("player_option_pv")) {
				SQL.createTable("player_option_pv", "player TEXT, player_UUID TEXT, Activate TEXT");
			}
			
			if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_option_pv")) {
				SQL.set("player_option_pv", "Activate", ""+boolea+"", "player_UUID", "" + p.getUniqueId() + "");
				SQL.set("player_option_pv", "player", ""+p.getName()+"", "player_UUID", "" + p.getUniqueId() + "");
			} else {
				SQL.insertData("player, player_UUID, Activate",
                        " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + boolea + "' ", "player_option_pv");
			}
		}
		
	}
}
