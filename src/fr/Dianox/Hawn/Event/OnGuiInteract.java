package fr.Dianox.Hawn.Event;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Tasks.TaskReloadServer;
import fr.Dianox.Hawn.Utility.Tasks.TaskSavePlayerServer;
import fr.Dianox.Hawn.Utility.Tasks.TaskShutdownServer;
import me.clip.placeholderapi.PlaceholderAPI;

public class OnGuiInteract implements Listener {

    BetweenServersConfig bs = new BetweenServersConfig();
    
    
    @SuppressWarnings({ "deprecation", "unused" })
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getSlotType() == SlotType.OUTSIDE) return;
        
        if (e.getCurrentItem() == null) {
        	return;
        }
        
        String inv = e.getWhoClicked().getOpenInventory().getTitle();
        Player p = (Player) e.getWhoClicked();
        String Displayname = "";

        String titlegui = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Title");
        titlegui = titlegui.replaceAll("&", "§");
        
        
        if (inv.equals(titlegui)) {
            ItemStack clickedItem = e.getCurrentItem();

            if (e.getCurrentItem().getType() == XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material")).parseMaterial()) {

                Displayname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title");
                Displayname = Displayname.replaceAll("&", "§");
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                    Displayname = PlaceholderAPI.setPlaceholders(p, Displayname);
                }

                if (clickedItem.getItemMeta().getDisplayName().contentEquals(Displayname)) {
                    p.closeInventory();
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
            /// ADMIN PANEL
        } else if (inv.equals("§cAP")) {

            if (e.getCurrentItem().getType() == XMaterial.CHEST.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cCommands")) {
                    p.performCommand("ap edit folder Commands");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cCosmetics-Fun")) {
                    p.performCommand("ap edit folder Cosmetics-Fun");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cCustomJoinItem")) {
                	p.sendMessage("§cAvailable soon");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cEvents")) {
                    p.performCommand("ap edit folder Events");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cMessages")) {
                	p.sendMessage("§cAvailable soon");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cScoreboard")) {
                    p.sendMessage("§cEdit theses files manually");
                    e.setCancelled(true);
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cStockageInfo")) {
                	p.sendMessage("§cAvailable soon");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cTablist")) {
                    p.sendMessage("§cEdit theses files manually");
                    e.setCancelled(true);
                }
            } else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                String nameinv = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§b", "");
                
                if (nameinv.contentEquals("AutoBroadcast")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("CustomCommand")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("general")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("Scoreboard-General")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("ServerList")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("spawn")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("warplist")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("between-servers")) {
                    p.performCommand("ap edit file G-" + nameinv);
                } else if (nameinv.contentEquals("command-aliases")) {
                    p.performCommand("ap edit file G-" + nameinv);
                }
            } else if (e.getCurrentItem().getType() == XMaterial.REDSTONE_BLOCK.parseMaterial()) {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cShutdown the server")) {
            		BukkitTask task = new TaskShutdownServer().runTaskLater(Main.getInstance(), 5);
            	}
            } else if (e.getCurrentItem().getType() == XMaterial.NETHER_STAR.parseMaterial()) {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§eReload The server")) {
            		BukkitTask task = new TaskReloadServer().runTaskLater(Main.getInstance(), 5);
            	}
            } else if (e.getCurrentItem().getType() == XMaterial.PLAYER_HEAD.parseMaterial()) {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§aSave players")) {
            		BukkitTask task = new TaskSavePlayerServer().runTaskLater(Main.getInstance(), 5);
            	}
            }

            e.setCancelled(true);
            // FOLDER LIST
        } else if (inv.equals("§cAP - Folder commands") || inv.equals("§cAP - Folder Cosmetics-Fun") ||
            inv.equals("§cAP - Folder Events") || inv.equals("§cAP - Folder Messages") ||
            inv.equals("§cAP - Folder Tablist")) {

            if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                name = name.replaceAll("§b", "");

                if (inv.equals("§cAP - Folder commands")) {
                    p.performCommand("ap edit file C-" + name);
                } else if (inv.equals("§cAP - Folder Cosmetics-Fun")) {
                    p.performCommand("ap edit file CF-" + name);
                } else if (inv.equals("§cAP - Folder Events")) {
                    p.performCommand("ap edit file E-" + name);
                } else if (inv.equals("§cAP - Folder Messages")) {
                    p.performCommand("ap edit file M-" + name);
                } else {
                    p.performCommand("ap edit file " + name);
                }

            } else if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the menu")) {
                    p.performCommand("ap");
                }
            }

            e.setCancelled(true);
        } else if (inv.equals("§cAP - Folder Scoreboard")) {
            if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the menu")) {
                    p.performCommand("ap");
                }
            }

            e.setCancelled(true);
        } else if (inv.contains("AP")) {
        	
        	String cfinuse = Main.configfileinuse.get(p);
        	
        	File f = new File(Main.getInstance().getDataFolder(), cfinuse);
        	
        	if (!f.exists()) {
        		return;
        	} 
        	
        	YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            
            if (e.getCurrentItem().getType() != XMaterial.BARRIER.parseMaterial()) {
            	if (Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
                    if (e.getCurrentItem().getType() == XMaterial.GREEN_WOOL.parseMaterial()) {
                        String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                        nameitem = nameitem.replaceAll("§b", "");
                        if (inv.contains("CustomCommand")) {
                        	if (e.getCurrentItem().getItemMeta().getDisplayName().contains("commands-general.enable")) {
                        		cfg.set(nameitem, false);
                        	} else {
                        		cfg.set("commands."+nameitem+".enable", false);
                        	}
                        } else {
                        	cfg.set(nameitem, false);
                        }
                        try {
							cfg.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                        String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                        p.performCommand("ap edit file " + cfinuse + " " + invnamenum);
                    } else if (e.getCurrentItem().getType() == XMaterial.RED_WOOL.parseMaterial()) {
                        String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                        nameitem = nameitem.replaceAll("§b", "");
                        if (inv.contains("CustomCommand")) {
                        	if (e.getCurrentItem().getItemMeta().getDisplayName().contains("commands-general.enable")) {
                        		cfg.set(nameitem, true);
                        	} else {
                        		cfg.set("commands."+nameitem+".enable", true);
                        	}
                        } else {
                        	cfg.set(nameitem, true);
                        }
                        try {
							cfg.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                        String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                        p.performCommand("ap edit file " + cfinuse + " " + invnamenum);
                    } else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                    	String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                    	String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                    	if (nameitem.contains("NEXT")) {
                    		Integer numberfinal = Integer.valueOf(invnamenum);
                    		numberfinal++;
                    		p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
                    	} else if (nameitem.contains("PREVIOUS")) {
                    		Integer numberfinal = Integer.valueOf(invnamenum);
                    		numberfinal--;
                    		p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
                    	}
                    }
            	} else {
            		if (e.getCurrentItem().getDurability() == 13) {
                        String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                        nameitem = nameitem.replaceAll("§b", "");
                        if (inv.contains("CustomCommand")) {
                        	if (e.getCurrentItem().getItemMeta().getDisplayName().contains("commands-general.enable")) {
                        		cfg.set(nameitem, false);
                        	} else {
                        		cfg.set("commands."+nameitem+".enable", false);
                        	}
                        } else {
                        	cfg.set(nameitem, false);
                        }
                        try {
							cfg.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                        String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                        p.performCommand("ap edit file " + cfinuse + " " + invnamenum);
                    } else if (e.getCurrentItem().getDurability() == 14) {
                        String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                        nameitem = nameitem.replaceAll("§b", "");
                        if (inv.contains("CustomCommand")) {
                        	if (e.getCurrentItem().getItemMeta().getDisplayName().contains("commands-general.enable")) {
                        		cfg.set(nameitem, true);
                        	} else {
                        		cfg.set("commands."+nameitem+".enable", true);
                        	}
                        } else {
                        	cfg.set(nameitem, true);
                        }
                        try {
							cfg.save(f);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
                        String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                        p.performCommand("ap edit file " + cfinuse + " " + invnamenum);
                    } else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                    	String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                    	String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                    	if (nameitem.contains("NEXT")) {
                    		Integer numberfinal = Integer.valueOf(invnamenum);
                    		numberfinal++;
                    		p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
                    	} else if (nameitem.contains("PREVIOUS")) {
                    		Integer numberfinal = Integer.valueOf(invnamenum);
                    		numberfinal--;
                    		p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
                    	}
                    }
            	}
            } else {
                if (inv.contains("CF-")) {
	            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
	                    p.performCommand("ap edit folder Cosmetics-Fun");
	                }
                } else if (inv.contains("E-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
                		p.performCommand("ap edit folder Events");
                	}
                } else if (inv.contains("G-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
                		p.performCommand("ap");
                	}
                } else if (inv.contains("C-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
                		p.performCommand("ap edit folder commands");
                	}
                }
            }

            e.setCancelled(true);
        }
    }

}
