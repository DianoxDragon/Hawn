package fr.dianox.hawn.event;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.commands.HawnCommand;
import fr.dianox.hawn.commands.features.warp.WarpCommand;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.Bungee;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.OtherUtils;
import fr.dianox.hawn.utility.SpawnUtils;
import fr.dianox.hawn.utility.TitleUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
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
									
								if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
									if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-LobbyBow")) {
										e.setCancelled(false);
										return;
									} else if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").contains("Special-Book")) {
										e.setCancelled(false);
										return;
									}
								}
								
								e.setCancelled(true);
								if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
									 if (p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
										 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
											 oncommand(s, p);
											 p.updateInventory();
										 }
									 }
								} else {
									for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
										oncommand(s, p);
										p.updateInventory();
									}
								}
							}
						}
					}
				} else {
					if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (CustomJoinItem.itemcjislot.containsKey(p.getInventory().getHeldItemSlot())) {
							
							if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
								if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-LobbyBow")) {
									e.setCancelled(false);
									return;
								} else if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").contains("Special-Book")) {
									e.setCancelled(false);
									return;
								}
							}
							
							e.setCancelled(true);
							if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
								 if (p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
									 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
										 oncommand(s, p);
										 p.updateInventory();
									 }
								 }
							} else {
								for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Command-List")) {
									oncommand(s, p);
									p.updateInventory();
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
							 e.setCancelled(true);
							 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
										if (p.hasPermission("hawn.use.cji.item.helmet")) {
											for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
												 oncommand(s, p);
											 }
										}
								 } else {
									 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
										 oncommand(s, p);
									 }
								 }
							 }
						 }
					 }
				 } else if (e.getSlot() == 38) {
					 
					 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Chestplate.Enable")) {
						 String path_item = "Custom-Join-Item.Items.Armor.Chestplate.Item.";
						 
						 if (CustomJoinItem.itemcjiname.containsKey("Chestplate-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"))) {
							 e.setCancelled(true);
							 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
										if (p.hasPermission("hawn.use.cji.item.chestplate")) {
											for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
												 oncommand(s, p);
											 }
										}
								 } else {
									 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
										 oncommand(s, p);
									 }
								 }
							 }
						 }
					 }
				 } else if (e.getSlot() == 37) {
					 
					 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Leggings.Enable")) {
						 String path_item = "Custom-Join-Item.Items.Armor.Leggings.Item.";
						 
						 if (CustomJoinItem.itemcjiname.containsKey("Leggings-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"))) {
							 e.setCancelled(true);
							 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
										if (p.hasPermission("hawn.use.cji.item.leggings")) {
											for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
												 oncommand(s, p);
											 }
										}
								 } else {
									 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
										 oncommand(s, p);
									 }
								 }
							 }
						 }
					 }
				 } else if (e.getSlot() == 36) {
					 
					 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Items.Armor.Boots.Enable")) {
						 String path_item = "Custom-Join-Item.Items.Armor.Boots.Item.";
						 
						 if (CustomJoinItem.itemcjiname.containsKey("Boots-" + ConfigCJIGeneral.getConfig().getString(path_item + "Material"))) {
							 e.setCancelled(true);
							 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
								 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
										if (p.hasPermission("hawn.use.cji.item.boots")) {
											for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
												 oncommand(s, p);
											 }
										}
								 } else {
									 for (String s: ConfigCJIGeneral.getConfig().getStringList(path_item + "Command-List")) {
										 oncommand(s, p);
									 }
								 }
							 }
						 }
					 }
				 }
				 
				 if (CustomJoinItem.itemcjislot.containsKey(e.getSlot())) {
					 e.setCancelled(true);
					 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Inventory-Click")) {
						 if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
							 if (p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(e.getSlot()))) {
								 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Command-List")) {
									 oncommand(s, p);
								 }
							 }
						 } else {
							 for (String s: ConfigCJIGeneral.getConfig().getStringList(CustomJoinItem.itemcjislot.get(e.getSlot()) + "Command-List")) {
								 oncommand(s, p);
							 }
						 }
					 }
				 }
			 }
		 }
	}
	
	@SuppressWarnings("deprecation")
	public static void oncommand(String s, Player p) {
		String perm = "";
        String world = "";
        
        if (s.startsWith("<world>") && s.contains("</world>")) {
        	world = StringUtils.substringBetween(s, "<world>", "</world>");
            s = s.replace("<world>" + world + "</world> ", "");

            if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                return;
            }
        }
        
        if (s.contains("<perm>") && s.contains("</perm>")) {
            perm = StringUtils.substringBetween(s, "<perm>", "</perm>");
            s = s.replace("<perm>" + perm + "</perm> ", "");

            if (!p.hasPermission(perm)) {
            	return;
            }
        }

        if (s.startsWith("[command-player]: ")) {
            s = s.replace("[command-player]: ", "");
            s = s.replaceAll("%player%", p.getName());

            p.performCommand(s);
        } else if (s.startsWith("[FWLU]: ")) {
        	if (s.startsWith("[FWLU]: ")) {
				
        		s = s.replace("[FWLU]: ", "");
				
				OtherUtils.Fireworkmethod(p, s);
			}
        } else if (s.startsWith("[customcommand-player]: ")) {
        	s = s.replace("[customcommand-player]: ", "");
            s = s.replaceAll("%player%", p.getName());
            
            OnCommandEvent.executecustomcommand(s, p);
        } else if (s.startsWith("[command-console]: ")) {
            s = s.replace("[command-console]: ", "");
            s = s.replaceAll("%player%", p.getName());

            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
        } else if (s.startsWith("[gamemode-survival]")) {
            p.setGameMode(GameMode.SURVIVAL);
        } else if (s.startsWith("[gamemode-creative]")) {
            p.setGameMode(GameMode.CREATIVE);
        } else if (s.startsWith("[gamemode-adventure]")) {
            p.setGameMode(GameMode.ADVENTURE);
        } else if (s.startsWith("[gamemode-spectator]")) {
            p.setGameMode(GameMode.SPECTATOR);
        } else if (s.startsWith("[ping]")) {
            for (String s1: ConfigMCommands.getConfig().getStringList("Ping.Self")) {
                MessageUtils.ReplaceCharMessagePlayer(s1, p);
            }
        } else if (s.startsWith("[spawn]: ")) {
            s = s.replace("[spawn]: ", "");
            SpawnUtils.teleportToSpawn(p, s);
        } else if (s.startsWith("[warp]: ")) {
            s = s.replace("[warp]: ", "");
            WarpCommand.onTp(p, s);
        } else if (s.startsWith("[bungee]: ")) {
            s = s.replace("[bungee]: ", "");
            Bungee.changeServer(p, s);
        } else if (s.startsWith("[effect[")) {
            s = s.replace("[effect[", "");

            String[] parts = s.split("]]: ");

            p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(parts[1]), 1999999999, Integer.valueOf(parts[0])));
        } else if (s.startsWith("[effectclear]: ")) {
            s = s.replace("[effectclear]: ", "");

            p.removePotionEffect(PotionEffectType.getByName(s));
        } else if (s.startsWith("[effectclearall]")) {
            for (PotionEffect effect: p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
        } else if (s.startsWith("[send-title]: ")) {
            s = s.replace("[send-title]: ", "");
            s = s.replaceAll("&", "§");

            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (s.contains("//n")) {
                String[] parts = s.split("//n");
                title = parts[0];
                subtitle = parts[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                TitleUtils.sendTitle(p, 20, 150, 75, s);
            } else {
                TitleUtils.sendTitle(p, 20, 150, 75, title);
                TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
            }
        } else if (s.startsWith("[send-title[")) {
            s = s.replace("[send-title[", "");

            String[] parts = s.split("]]: ");

            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (s.contains("//n")) {
                String[] part = parts[1].split("//n");
                title = part[0];
                subtitle = part[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
            } else {
                TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
                TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
            }
        } else if (s.startsWith("[send-actionbar]: ")) {
            s = s.replace("[send-actionbar]: ", "");
            s = s.replaceAll("&", "§");

            ActionBar.sendActionBar(p, s);
        } else if (s.startsWith("[send-actionbar[")) {
            s = s.replace("[send-actionbar[", "");

            String[] parts = s.split("]]: ");

            ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
        } else if (s.startsWith("[sounds]: ")) {
            s = s.replace("[sounds]: ", "");
            p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
        } else {
            MessageUtils.ReplaceCharMessagePlayer(s, p);
        }
		
	}
}