package fr.dianox.hawn.modules.onjoin.cji;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.configs.customjoinitem.SpecialCjiFunGun;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.CjiPW;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpecialIteFunGun implements Listener {
		
	public static String Check = SpecialCjiFunGun.getConfig().getString("FunGun.Item.Title");
	private static final List<Player> Cooling = new ArrayList<>();

	@EventHandler
	public void onCLickInventory(InventoryClickEvent e) {
		
		if (!SpecialCjiFunGun.getConfig().getBoolean("FunGun.Enable")) {
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
				if (e.getCurrentItem().getType() == XMaterial.BLAZE_ROD.parseMaterial()) {
					e.setCancelled(true);
				}
			}
		} catch (Exception e2) {}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSnowBallHit(ProjectileHitEvent e) {
		
		if (!SpecialCjiFunGun.getConfig().getBoolean("FunGun.Enable")) {
			return;
		}
		
		Projectile proj = e.getEntity();
		if (proj instanceof Snowball) {
			
			Snowball snowball = (Snowball) proj;
			if (snowball.getShooter() instanceof Player) {
				
				Player p = (Player) snowball.getShooter();
				if (!p.hasPermission("hawn.use.customjoinitem")) {
					 return;
				}
				
				if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.Use_Permission_Per_Item")) {
					if (!p.hasPermission("hawn.use.cji.item." + CustomJoinItem.itemcjislotname.get(p.getInventory().getHeldItemSlot()))) {
						return;
					}
				}
				
				// Check Worlds
				if (!ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
					if (!CjiPW.getWItemPG().contains(p.getWorld().getName())) {
						return;
					}
				}
				
				for (Player all : p.getWorld().getPlayers()) {
					if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 19) {
						all.spawnParticle(Particle.HEART, snowball.getLocation(), 1);
						all.spawnParticle(Particle.LAVA, snowball.getLocation(), 1);
					}
					
					all.playSound(snowball.getLocation(), XSound.ENTITY_CAT_AMBIENT.parseSound(), 1, 1);
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (!SpecialCjiFunGun.getConfig().getBoolean("FunGun.Enable")) {
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
		
		if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 19) {
			es = e.getHand();
		}
		
		String Check1 = Check;
		
		if (Check1.startsWith("&f")) {
			Check1 = Check1.substring(2);
		}
		
		Check1 = Check1.replaceAll("&", "§");
		
		try {
			if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 19) {
				if (es.equals(EquipmentSlot.HAND)) {
					if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
						if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
							if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-FunGun")) {
								if (p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(Check1)) {
									if (p.getInventory().getItemInMainHand().getType() == XMaterial.BLAZE_ROD.parseMaterial()) {
										if (SpecialCjiFunGun.getConfig().getBoolean("FunGun.Option.Item-Delay.Enable")) {
											if (Cooling.contains(p)) {
												if (ConfigMMsg.getConfig().getBoolean("Custom-Join-Item.Error.FunGun.Time.Enable")) {
													for (String msg: ConfigMMsg.getConfig().getStringList("Custom-Join-Item.Error.FunGun.Time.Messages")) {
											    		ConfigEventUtils.ExecuteEvent(p, msg, "Custom-Join-Item.Error.FunGun.Time.Messages", "SpecialIteFunGun", false);
											    	}
												}
											} else {
												Cooling.add(p);
												
												p.launchProjectile(Snowball.class);
												p.launchProjectile(Snowball.class);
												p.launchProjectile(Snowball.class);
												p.launchProjectile(Snowball.class);
												p.launchProjectile(Snowball.class);
												
												Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
													
													@Override
													public void run() {
														Cooling.remove(p);
													}
														
												}, SpecialCjiFunGun.getConfig().getInt("FunGun.Option.Item-Delay.Delay")*20);
												Main.fungunCooldowns.put(p, System.currentTimeMillis());
											}
										} else {
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
										}
									}
								}
							}
						}
					}
				}
			} else {
				if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
					if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
						if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-FunGun")) {
							if (p.getItemInHand().getItemMeta().getDisplayName().contains(Check1)) {
								if (p.getItemInHand().getType() == XMaterial.BLAZE_ROD.parseMaterial()) {
									if (SpecialCjiFunGun.getConfig().getBoolean("FunGun.Option.Item-Delay.Enable")) {
										if (Cooling.contains(p)) {
											if (ConfigMMsg.getConfig().getBoolean("Custom-Join-Item.Error.FunGun.Time.Enable")) {
												for (String msg: ConfigMMsg.getConfig().getStringList("Custom-Join-Item.Error.FunGun.Time.Messages")) {
													ConfigEventUtils.ExecuteEvent(p, msg, "Custom-Join-Item.Error.FunGun.Time.Messages", "SpecialIteFunGun", false);
										    	}
											}
										} else {
											Cooling.add(p);
											
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
											p.launchProjectile(Snowball.class);
											
											Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
												
												@Override
												public void run() {
													Cooling.remove(p);
												}
													
											}, SpecialCjiFunGun.getConfig().getInt("FunGun.Option.Item-Delay.Delay")*20);
											Main.fungunCooldowns.put(p, System.currentTimeMillis());
										}
									} else {
										p.launchProjectile(Snowball.class);
										p.launchProjectile(Snowball.class);
										p.launchProjectile(Snowball.class);
										p.launchProjectile(Snowball.class);
										p.launchProjectile(Snowball.class);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e2) {}
	}
	
	public static void PlayerGiveFunGun(Player p, Integer slot) {
		if (!SpecialCjiFunGun.getConfig().getBoolean("FunGun.Enable")) {
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

		if (SpecialCjiFunGun.getConfig().isSet("FunGun.Item.Title")) {
			String pretitle = SpecialCjiFunGun.getConfig().getString("FunGun.Item.Title");
				
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
					
		if (SpecialCjiFunGun.getConfig().isSet("FunGun.Item.Material.Amount")) {
			amount = SpecialCjiFunGun.getConfig().getInt("FunGun.Item.Material.Amount");
		}
			
		if (SpecialCjiFunGun.getConfig().isSet("FunGun.Item.Lore")) {
			for (String loremsg: SpecialCjiFunGun.getConfig().getStringList("FunGun.Item.Lore")) {
				
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
		
		item = new ItemStack(XMaterial.BLAZE_ROD.parseMaterial(), amount);
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
