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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
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
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Option.Ultimate-Protection-Of-The-Items")) {
				try {
					
					String Check1 = Check;
					
					if (Check1.startsWith("&f")) {
						Check1 = Check1.substring(2, Check1.length());
					}
					
					if (p.getItemInHand() != null && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§")))) {
						e.setCancelled(true);
					}
				} catch (Exception e1) {}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Option.Ultimate-Protection-Of-The-Items")) {
				try {
					
					String Check1 = Check;
					
					if (Check1.startsWith("&f")) {
						Check1 = Check1.substring(2, Check1.length());
					}
					
					
					if (p.getItemInHand() != null && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§")))) {
						e.setCancelled(true);
					}
				} catch (Exception e1) {}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDropWithItemVisibility(PlayerDropItemEvent e) {	
		Player p = e.getPlayer();
		
		if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Option.Ultimate-Protection-Of-The-Items")) {
				try {
					
					String Check1 = Check;
					
					if (Check1.startsWith("&f")) {
						Check1 = Check1.substring(2, Check1.length());
					}
					
					
					if (p.getItemInHand() != null && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§")))) {
						e.setCancelled(true);
					}
					
					returnItem(e.getPlayer());
					
				} catch (Exception e1) {}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onCLickInventory(InventoryClickEvent e) {
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable")) {
			return;
		}
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.World.All_World")) {
			if (!CjiPW.getLobbyBow().contains(e.getWhoClicked().getWorld().getName())) {
				return;
			}
		}
		
		Player p = (Player) e.getWhoClicked();
		
		if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Use_Permission")) {
			if (!p.hasPermission("hawn.event.interact.item.lobbybow")) {
				return;
			}
		}
		
		if (e.getSlotType() == SlotType.OUTSIDE) return;
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		
		String Check1 = Check;
		
		if (Check1.startsWith("&f")) {
			Check1 = Check1.substring(2, Check1.length());
		}
		
		
		if (p.getItemInHand() != null && (p.getInventory().getItemInHand().getItemMeta().getDisplayName().contains(Check1.replaceAll("&", "§")))) {
			e.setCancelled(true);
		}
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
				if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Use_Permission")) {
					if (!p.hasPermission("hawn.event.interact.item.lobbybow")) {
						return;
					}
				}
				
				if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.World.All_World")) {
					if (!CjiPW.getLobbyBow().contains(p.getWorld().getName())) {
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
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.World.All_World")) {
			if (!CjiPW.getLobbyBow().contains(p.getWorld().getName())) {
				return;
			}
		}
		
		if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Use_Permission")) {
			if (!p.hasPermission("hawn.event.interact.item.lobbybow")) {
				return;
			}
		}
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR) {
			if (ConfigCJIGeneral.getConfig().isSet(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items")) {
				if (ConfigCJIGeneral.getConfig().getString(CustomJoinItem.itemcjislot.get(p.getInventory().getHeldItemSlot()) + "Special-Items").equals("Special-LobbyBow")) {
					if(storedItem.containsKey(p)) return;
					int slot = p.getInventory().getSize() - 10;
					ItemStack item = p.getInventory().getItem(slot);
					storedItem.put(p, item);
					p.getInventory().setItem(slot, new ItemStack(XMaterial.ARROW.parseMaterial(), 1));
				}
			}
		}
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
		
		if (!SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.World.All_World")) {
			if (!CjiPW.getLobbyBow().contains(p.getWorld().getName())) {
				return;
			}
		}
		
		if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Use_Permission")) {
			if (!p.hasPermission("hawn.event.interact.item.lobbybow")) {
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
					
		if (SpecialCjiLobbyBow.getConfig().isSet("LobbyBow.Item.Material.Amount")) {
			amount = SpecialCjiLobbyBow.getConfig().getInt("LobbyBow.Item.Material.Amount");
		}
			
		if (SpecialCjiLobbyBow.getConfig().isSet("LobbyBow.Item.Lore")) {
			for (String loremsg: SpecialCjiLobbyBow.getConfig().getStringList("LobbyBow.Item.Lore")) {
				
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
