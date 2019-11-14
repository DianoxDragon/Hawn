package fr.dianox.hawn.event.modules;

import fr.dianox.hawn.utility.config.events.PlayerEventsConfig;
import fr.dianox.hawn.utility.world.PlayerEventsPW;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class DisableOffHand implements Listener {
		
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onSwapHandItems(PlayerSwapHandItemsEvent e) {
		if (!PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.Enable")) {
			return;
		}
		
		if (!PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.World.All_World")) {
			if (!PlayerEventsPW.getWBOH().contains(e.getPlayer().getWorld().getName())) {
				return;
			}
		}
		
		if (PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.Bypass-With-Permission")) {
			if (e.getPlayer().hasPermission("hawn.bypass.block.offhand")) {
				return;
			}
		}
		
		e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (!PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.Enable")) {
			return;
		}
		
		if (!PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.World.All_World")) {
			if (!PlayerEventsPW.getWBOH().contains(e.getWhoClicked().getWorld().getName())) {
				return;
			}
		}
		
		if (PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.Bypass-With-Permission")) {
			if (e.getWhoClicked().hasPermission("hawn.bypass.block.offhand")) {
				return;
			}
		}
				
		if (e.getInventory().getType() != InventoryType.CRAFTING || e.getSlot() != 40) return; 
		
		e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryDrag(InventoryDragEvent e) {
		
		if (!PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.Enable")) {
			return;
		}
		
		if (!PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.World.All_World")) {
			if (!PlayerEventsPW.getWBOH().contains(e.getWhoClicked().getWorld().getName())) {
				return;
			}
		}
		
		if (PlayerEventsConfig.getConfig().getBoolean("Block-Off-Hand.Bypass-With-Permission")) {
			if (e.getWhoClicked().hasPermission("hawn.bypass.block.offhand")) {
				return;
			}
		}
		
		if (e.getInventory().getType() != InventoryType.CRAFTING || e.getInventorySlots().contains(Integer.valueOf(40))) return; 
		
		e.setCancelled(true);
	}
}