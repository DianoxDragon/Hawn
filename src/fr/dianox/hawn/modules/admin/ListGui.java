package fr.dianox.hawn.modules.admin;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.commands.AdminPanelCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("deprecation")
public class ListGui implements Listener {
	
	@EventHandler
    public void onMainWorldManager(InventoryClickEvent e) {
		/*
		 * Check the needed to avoid bugs
		 */
		if (e.getSlotType() == SlotType.OUTSIDE) return;
		if (e.getCurrentItem() == null) return;
		
		/*
		 * Implement first variables
		 */
		String inv = e.getWhoClicked().getOpenInventory().getTitle();
		Player p = (Player) e.getWhoClicked();
		
		String titlegui = "§cList Gui - Page ";
		
		if (inv.contains(titlegui)) {
			ItemStack item = e.getCurrentItem();
			
			if (e.isLeftClick()) {
				if (item.getType() == XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()) {
					e.setCancelled(true);
				} else if (item.getType() == XMaterial.ARROW.parseMaterial()) {
					if (e.getRawSlot() == 45) {
						String backpage = inv.replace("§cList Gui - Page ", "");
						Integer newpage = Integer.valueOf(backpage);
						newpage--;
						
						OpenGui(p, newpage);
					} else if (e.getRawSlot() == 46) {
						String backpage = inv.replace("§cList Gui - Page ", "");
						Integer newpage = Integer.valueOf(backpage);
						newpage++;
						
						OpenGui(p, newpage);
					}
				} else if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
					p.performCommand("paneladmin");
				} else {
					if (!p.hasPermission("hawn.editplayer")) {
						MessageUtils.MessageNoPermission(p, "hawn.editplayer");
						
						return;
					} else {
						try {				
							String pname = item.getItemMeta().getDisplayName();
							pname = pname.replace("§b", "");
							
							EditPlayerGui.OpenGui(Bukkit.getPlayer(pname));
						} catch (Exception e1) {}
					}
				}
			}
			
			e.setCancelled(true);
		}
	}
	
	public static void OpenGui(Player p, Integer pagenumber) {

		if (pagenumber < 0) {
			pagenumber = 0;
		}
		
		if (pagenumber == 0) {
			pagenumber = 1;
		}
		
		Inventory inv = Bukkit.createInventory(null, 54, "§cList Gui - Page " + pagenumber);
		
		if (pagenumber == 1) {
			pagenumber--;
		}
		
		Integer number_place = 0;
    	Integer numberitems = 0;
    	
    	HashMap<Integer, String> plist = new HashMap<>();
    	
    	Integer checklist = 1;
    	
    	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
        	plist.put(checklist, all.getName());
        	checklist++;
        }
    	
    	Integer pagedemande = (pagenumber * 35) + 1;
		Integer pagedemandefinale = pagedemande + 35 - 1;
    			
    	ArrayList<String> lore = new ArrayList<>();

    	while (pagedemande <= pagedemandefinale) {
    		
    		
    		try {
    			if (plist.containsKey(pagedemande)) {
    				
    				lore.add(" ");
    				
    				String gm = "";
    				
    				if (p.getGameMode() == GameMode.SURVIVAL) {
    					gm = ConfigMAdmin.getConfig().getString("Command.List.Gui.Player.Survival").replaceAll("&", "§");
    				} else if (p.getGameMode() == GameMode.SPECTATOR) {
    					gm = ConfigMAdmin.getConfig().getString("Command.List.Gui.Player.Spectator").replaceAll("&", "§");
    				} else if (p.getGameMode() == GameMode.CREATIVE) {
    					gm = ConfigMAdmin.getConfig().getString("Command.List.Gui.Player.Creative").replaceAll("&", "§");
    				} else if (p.getGameMode() == GameMode.ADVENTURE) {
    					gm = ConfigMAdmin.getConfig().getString("Command.List.Gui.Player.Adventure").replaceAll("&", "§");
    				}
    				
    				lore.add(ConfigMAdmin.getConfig().getString("Command.List.Gui.Player.Gamemode").replaceAll("&", "§") + gm);
    				lore.add(ConfigMAdmin.getConfig().getString("Command.List.Gui.Player.World").replaceAll("&", "§") + Bukkit.getPlayer(plist.get(pagedemande)).getWorld().getName());
    				lore.add(" ");
    				lore.add(ConfigMAdmin.getConfig().getString("Command.List.Gui.Player.LeftClick").replaceAll("&", "§"));
    				
    				inv.setItem(number_place, createGuiItemSkull("§b" + plist.get(pagedemande), plist.get(pagedemande), (ArrayList<String>) lore));
    			
    				lore.clear();
    			}
    		} catch (Exception e) {}
    			
    		number_place++;
    		numberitems++;
    		pagedemande++;
        }
        
    	plist.clear();
    	
		inv.setItem(36, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(37, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(38, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(39, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(40, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(41, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(42, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(43, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(44, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial())); 
        
        inv.setItem(46, createGuiItemWL(ConfigMAdmin.getConfig().getString("Command.List.Gui.Other.Page.Next").replaceAll("&", "§"), XMaterial.ARROW.parseMaterial()));
        inv.setItem(45, createGuiItemWL(ConfigMAdmin.getConfig().getString("Command.List.Gui.Other.Page.Back").replaceAll("&", "§"), XMaterial.ARROW.parseMaterial()));
          
        inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        List<String> whitelistuse = AdminPanelCommandConfig.getConfig().getStringList("General-Options.List-Of-People-Can-Use-The-Panel");
		
        if (p.hasPermission("hawn.adminpanel") && whitelistuse.contains(p.getName())) {
        	inv.setItem(53, createGuiItemWL(ConfigMAdmin.getConfig().getString("Command.List.Gui.Other.Back.PanelAdmin").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        } else {
        	inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        }
        
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}

	public static ItemStack createGuiItemWL(String name, Material mat) {
		ItemStack i = new ItemStack(mat, 1);
		ItemMeta iMeta = i.getItemMeta();
		iMeta.setDisplayName(name);
		i.setItemMeta(iMeta);
		return i;
	}
	
	public static ItemStack createGuiItem(String name, ArrayList < String > desc, Material mat) {
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        iMeta.setLore(desc);
        i.setItemMeta(iMeta);
        return i;
    }
	
	public static ItemStack createGuiItemSkull(String name, String pname, ArrayList < String > desc) {
        ItemStack i = null;
        
        if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 113) {
			i = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1);
		} else {
			i = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
		}
        
        SkullMeta iMeta = (SkullMeta) i.getItemMeta();
        iMeta.setDisplayName(name);
        iMeta.setLore(desc);
        iMeta.setOwner(pname);
        i.setItemMeta(iMeta);
        return i;
    }
	
}
