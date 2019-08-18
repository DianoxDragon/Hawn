package fr.Dianox.Hawn.Event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.PlayerOptionMainConfig;

public class OnInventoryInteract implements Listener {
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
		
		 if (e.getSlotType() == SlotType.OUTSIDE) return;
	        
		 if (e.getCurrentItem() == null) {
			 return;
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
	}

}
