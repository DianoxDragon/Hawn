package fr.dianox.hawn.modules.admin;

import java.util.ArrayList;
import java.util.List;

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

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;


@SuppressWarnings("deprecation")
public class EditPlayerGui implements Listener {

	String msg_self_survival = "Gamemode.Self.Survival.";
	String msg_self_creative = "Gamemode.Self.Creative.";
	String msg_self_adventure = "Gamemode.Self.Adventure.";
	String msg_self_spectator = "Gamemode.Self.Spectator.";
	
	String msg_other_survival = "Gamemode.Other.Survival.";
	String msg_other_creative = "Gamemode.Other.Creative.";
	String msg_other_adventure = "Gamemode.Other.Adventure.";
	String msg_other_spectator = "Gamemode.Other.Spectator.";
	
	String msg_other_survival_sender = "Gamemode.Other-Sender.Survival.";
	String msg_other_creative_sender = "Gamemode.Other-Sender.Creative.";
	String msg_other_adventure_sender = "Gamemode.Other-Sender.Adventure.";
	String msg_other_spectator_sender = "Gamemode.Other-Sender.Spectator.";
	
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
		
		String titlegui = "§cEdit Player - ";
		
		if (inv.contains(titlegui)) {
			ItemStack item = e.getCurrentItem();
			
			Player target = Bukkit.getServer().getPlayer(inv.replace("§cEdit Player - ", ""));
			
			if (e.isLeftClick()) {
				if (item.getType() == XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()) {
					e.setCancelled(true);
				} else if (e.getRawSlot() == 19) {
					if (!p.hasPermission("hawn.editplayer.gamemode")) {
						MessageUtils.MessageNoPermission(p, "hawn.editplayer.gamemode");
						
						return;
					}
					
					List<String> lore = new ArrayList<>();
					if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
						if (item.getType() == XMaterial.RED_WOOL.parseMaterial()) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.CREATIVE);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_creative+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_creative+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_creative+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_creative_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_creative_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_creative_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Creative").replaceAll("&", "§"), (ArrayList<String>) lore, "ORANGE_WOOL"));
						} else if (item.getType() == XMaterial.ORANGE_WOOL.parseMaterial()) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.ADVENTURE);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_adventure+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_adventure+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_adventure+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_adventure_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_adventure_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_adventure_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Adventure").replaceAll("&", "§"), (ArrayList<String>) lore, "GREEN_WOOL"));
						} else if (item.getType() == XMaterial.GREEN_WOOL.parseMaterial()) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.SPECTATOR);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_spectator+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_spectator+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_spectator+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_spectator_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_spectator_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_spectator_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Spectator").replaceAll("&", "§"), (ArrayList<String>) lore, "YELLOW_WOOL"));
						} else if (item.getType() == XMaterial.YELLOW_WOOL.parseMaterial()) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.SURVIVAL);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_survival+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_survival+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_survival+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_survival_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_survival_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_survival_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Survival").replaceAll("&", "§"), (ArrayList<String>) lore, "RED_WOOL"));
						}
					} else {
						if (e.getCurrentItem().getDurability() == 14) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.CREATIVE);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_creative+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_creative+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_creative+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_creative_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_creative_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_creative_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Creative").replaceAll("&", "§"), (ArrayList<String>) lore, "ORANGE_WOOL"));
						} else if (e.getCurrentItem().getDurability() == 1) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.ADVENTURE);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_adventure+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_adventure+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_adventure+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_adventure_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_adventure_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_adventure_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Adventure").replaceAll("&", "§"), (ArrayList<String>) lore, "GREEN_WOOL"));
						} else if (e.getCurrentItem().getDurability() == 13) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.SPECTATOR);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_spectator+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_spectator+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_spectator+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_spectator_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_spectator_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_spectator_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Spectator").replaceAll("&", "§"), (ArrayList<String>) lore, "YELLOW_WOOL"));
						} else if (e.getCurrentItem().getDurability() == 4) {
							lore.add(" ");
							lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
							
							target.setGameMode(GameMode.SURVIVAL);
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_survival+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_survival+"Messages")) {
									ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), msg_other_survival+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							if (ConfigMMsg.getConfig().getBoolean(msg_other_survival_sender+"Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList(msg_other_survival_sender+"Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", target.getName()), msg_other_survival_sender+"Messages", "SpecialItemPlayerVisibility", false);
								}
							}
							
							p.getOpenInventory().setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Survival").replaceAll("&", "§"), (ArrayList<String>) lore, "RED_WOOL"));
						}
					}
					
					p.updateInventory();
				} else if (e.getRawSlot() == 21) {
					
					if (!p.hasPermission("hawn.editplayer.clearinv")) {
						MessageUtils.MessageNoPermission(p, "hawn.editplayer.clearinv");
						
						return;
					}
					
					target.getInventory().clear();
					target.getInventory().setArmorContents(new ItemStack[4]);
					
					if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Sender.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("ClearInv.Other-Sender.Messages")) {
							msg = msg.replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName());
							ConfigEventUtils.ExecuteEvent(p, msg, "ClearInv.Other-Sender.Messages", "SpecialItemPlayerVisibility", false);
							
						}
					}
						
					if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Target.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("ClearInv.Other-Target.Messages")) {
							msg = msg.replaceAll("%player%", p.getName());
							ConfigEventUtils.ExecuteEvent(target, msg, "ClearInv.Other-Target.Messages", "SpecialItemPlayerVisibility", false);
						}
					}
				} else if (e.getRawSlot() == 23) {
					if (!p.hasPermission("hawn.editplayer.tp")) {
						MessageUtils.MessageNoPermission(p, "hawn.editplayer.tp");
						
						return;
					}
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tp " + p.getName() + " " + target.getName());
				} else if (e.getRawSlot() == 25) {
					e.setCancelled(true);
				} else if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
					ListGui.OpenGui(p, 1);
				}
			}
			
			e.setCancelled(true);
		}
	}
	
	public static void OpenGui(Player p) {

		Inventory inv = Bukkit.createInventory(null, 54, "§cEdit Player - " + p.getName());
    	    	
    	ArrayList<String> lore = new ArrayList<>();

    	inv.setItem(13, createGuiItemSkull("§b" + p.getName(), p.getName(), (ArrayList<String>) lore));
        
    	lore.clear();
    	
    	/*
    	 * GAMEMODE
    	 */
    	
    	lore.add(" ");
		lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.LeftClick").replaceAll("&", "§"));
    	
    	if (p.getGameMode() == GameMode.SURVIVAL) {
    		inv.setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Survival").replaceAll("&", "§"),  (ArrayList<String>) lore, "RED_WOOL"));
		} else if (p.getGameMode() == GameMode.SPECTATOR) {
			inv.setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Spectator").replaceAll("&", "§"),  (ArrayList<String>) lore, "YELLOW_WOOL"));
		} else if (p.getGameMode() == GameMode.CREATIVE) {
			inv.setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Creative").replaceAll("&", "§"),  (ArrayList<String>) lore, "ORANGE_WOOL"));
		} else if (p.getGameMode() == GameMode.ADVENTURE) {
			inv.setItem(19, createGuiItemColor(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Gamemode.Adventure").replaceAll("&", "§"),  (ArrayList<String>) lore, "GREEN_WOOL"));
		}
    	    	
    	lore.clear();
    	
    	/*
    	 * INVENTORY
    	 */
    	
    	lore.add(" ");
    	lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.ClearInv.LeftClick").replaceAll("&", "§"));
    	
    	inv.setItem(21, createGuiItem(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.ClearInv.Item-Name").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.BUCKET.parseMaterial()));
    	
    	lore.clear();
    	
    	/*
    	 * TP
    	 */
    	
    	lore.add(" ");
    	lore.add(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Teleport.LeftClick").replaceAll("&", "§"));
    	
    	inv.setItem(23, createGuiItem(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.Teleport.Item-Name").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.ENDER_PEARL.parseMaterial()));
    	
    	lore.clear();
    	
    	inv.setItem(25, createGuiItem(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.MoreSoon.Item-Name").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.OAK_SIGN.parseMaterial()));
    	
    	lore.clear();
    	
		inv.setItem(36, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(37, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(38, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(39, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(40, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(41, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(42, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(43, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(44, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial())); 
        inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial())); 
        inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial())); 
        inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        		
        inv.setItem(53, createGuiItemWL(ConfigMAdmin.getConfig().getString("Command.EditPlayer.Gui.BackToPlayerList.Item-Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));

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
	
	public static ItemStack createGuiItemColor(String name, ArrayList < String > desc, String mat) {

	        mat = mat.toUpperCase();
	        ItemStack i;
	        
	        if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
	            i = new ItemStack(XMaterial.getMat(mat, "Error from the edit player system"), 1);
	            ItemMeta iMeta = i.getItemMeta();
	            iMeta.setDisplayName(name);
	            iMeta.setLore(desc);
	            i.setItemMeta(iMeta);
	            return i;
	        } else {
	            if (mat.startsWith("ORANGE") || mat.startsWith("MAGENTA") || mat.startsWith("LIGHT_BLUE") || mat.startsWith("YELLOW") ||
	                mat.startsWith("LIME") || mat.startsWith("PINK") || mat.startsWith("GRAY") || mat.startsWith("LIGHT_GRAY") ||
	                mat.startsWith("CYAN") || mat.startsWith("PURPLE") || mat.startsWith("BLUE") || mat.startsWith("BROWN") ||
	                mat.startsWith("GREEN") || mat.startsWith("RED") || mat.startsWith("BLACK")) {

	                if (mat.startsWith("ORANGE")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 1);
	                } else if (mat.startsWith("MAGENTA")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 2);
	                } else if (mat.startsWith("LIGHT_BLUE")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 3);
	                } else if (mat.startsWith("YELLOW")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 4);
	                } else if (mat.startsWith("LIME")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 5);
	                } else if (mat.startsWith("PINK")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 6);
	                } else if (mat.startsWith("GRAY")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 7);
	                } else if (mat.startsWith("LIGHT_GRAY")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 8);
	                } else if (mat.startsWith("CYAN")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 9);
	                } else if (mat.startsWith("PURPLE")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 10);
	                } else if (mat.startsWith("BLUE")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 11);
	                } else if (mat.startsWith("BROWN")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 12);
	                } else if (mat.startsWith("GREEN")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 13);
	                } else if (mat.startsWith("RED")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 14);
	                } else if (mat.startsWith("BLACK")) {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1, (short) 15);
	                } else {
	                    i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1);
	                }

	                ItemMeta iMeta = i.getItemMeta();
	                iMeta.setDisplayName(name);
	                iMeta.setLore(desc);
	                i.setItemMeta(iMeta);
	                return i;

	            } else {
	                i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1);
	                ItemMeta iMeta = i.getItemMeta();
	                iMeta.setDisplayName(name);
	                iMeta.setLore(desc);
	                i.setItemMeta(iMeta);
	                return i;
	            }
	        }
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
        
        if (Main.Spigot_Version >= 113) {
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
