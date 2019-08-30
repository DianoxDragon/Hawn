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
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.AdminPanelCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.Administration.AdminPanelConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.Administration.OtherAMConfig;
import fr.Dianox.Hawn.Utility.Tasks.TaskReloadServer;
import fr.Dianox.Hawn.Utility.Tasks.TaskSavePlayerServer;
import fr.Dianox.Hawn.Utility.Tasks.TaskShutdownServer;
import me.clip.placeholderapi.PlaceholderAPI;

public class OnGuiInteract implements Listener {
    
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

            if (e.getCurrentItem().getType() == XMaterial.REDSTONE.parseMaterial()) {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Special.Item.Shutdown.Name").replaceAll("&", "§"))) {
            		
            		for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Shutdown.Messages")) {
            			MessageUtils.ReplaceCharMessagePlayer(msg, p);
            		}
            		
            		BukkitTask task = new TaskShutdownServer().runTaskLater(Main.getInstance(), 5);
            	}
            } else if (e.getCurrentItem().getType() == XMaterial.NETHER_STAR.parseMaterial()) {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Special.Item.Reload.Name").replaceAll("&", "§"))) {
            		
            		for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Reload.Messages")) {
            			MessageUtils.ReplaceCharMessagePlayer(msg, p);
            		}
            		
            		BukkitTask task = new TaskReloadServer(p).runTaskLater(Main.getInstance(), 5);
            	}
            } else if (e.getCurrentItem().getType() == XMaterial.PLAYER_HEAD.parseMaterial()) {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Special.Item.Save-Players.Name").replaceAll("&", "§"))) {
            		
            		for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Save-Players.Messages")) {
            			MessageUtils.ReplaceCharMessagePlayer(msg, p);
            		}
            		
            		BukkitTask task = new TaskSavePlayerServer(p).runTaskLater(Main.getInstance(), 5);
            	}
            } else if (e.getCurrentItem().getType() == XMaterial.EMERALD.parseMaterial()) {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Special.Item.Reload-Hawn.Name").replaceAll("&", "§"))) {
            		
            		BukkitTask task = Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
            			public void run() {
            				p.performCommand("hawn reload");
            			}
            		}, 5);
            	}
            } else if (e.getCurrentItem().getType() == XMaterial.CHEST.parseMaterial()) {
            	p.performCommand("ap edithawnmainmenu");
            }

            e.setCancelled(true);
            // FOLDER LIST
        } else if (inv.equals("§cAP - Hawn edit menu")) {
        	if (e.getCurrentItem().getType() == XMaterial.CHEST.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cCommands")) {
                    p.performCommand("ap folder Commands");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cCosmetics-Fun")) {
                    p.performCommand("ap folder Cosmetics-Fun");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cCustomJoinItem")) {
                	p.performCommand("ap folder CustomJoinItem");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cEvents")) {
                    p.performCommand("ap folder Events");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cMessages")) {
                	p.performCommand("ap folder Messages");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cScoreboard")) {
                    p.sendMessage("§cEdit theses files manually");
                    e.setCancelled(true);
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cStockageInfo")) {
                	p.sendMessage("§cAvailable soon");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cTablist")) {
                	p.performCommand("ap folder Tablist");
                }
            } else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                String nameinv = e.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§b", "");
                
                p.performCommand("ap edit file G-" + nameinv);
                
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"))) {
            	p.performCommand("ap");
            }
        	
        	e.setCancelled(true);
        } else if (inv.equals("§cAP - Folder commands") || inv.equals("§cAP - Folder Cosmetics-Fun") ||
            inv.equals("§cAP - Folder Events") || inv.equals("§cAP - Folder Messages") ||
            inv.equals("§cAP - Folder Tablist") || inv.equals("§cAP - Folder CF-Utility") || 
            inv.equals("§cAP - Folder M-Classic") || inv.equals("§cAP - Folder M-Administration") ||
            inv.equals("§cAP - Folder CustomJoinItem")) {

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
                } else if (inv.equals("§cAP - Folder Tablist")) {
                    p.performCommand("ap edit file T-" + name);
                } else if (inv.equals("§cAP - Folder Tablist")) {
                    p.performCommand("ap edit file T-" + name);
                } else if (inv.equals("§cAP - Folder CF-Utility")) {
                    p.performCommand("ap edit file CFU-" + name);
                } else if (inv.equals("§cAP - Folder M-Classic")) {
                	p.performCommand("ap edit file MC-" + name);
                } else if (inv.equals("§cAP - Folder M-Administration")) {
                	p.performCommand("ap edit file MA-" + name);
                } else if (inv.equals("§cAP - Folder CustomJoinItem")) {
                	p.performCommand("ap edit file CJI-" + name);
                } else {
                    p.performCommand("ap edit file " + name);
                }
                
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cUtility")) {
            	p.performCommand("ap folder CF-Utility");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cAdministration")) {
            	p.performCommand("ap folder M-Administration");
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cClassic")) {
            	p.performCommand("ap folder M-Classic");
            } else if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"))) {
                	if (inv.equals("§cAP - Folder CF-Utility")) {
                		p.performCommand("ap folder Cosmetics-Fun");
                	} else if (inv.equals("§cAP - Folder M-Classic") || inv.equals("§cAP - Folder M-Administration")) {
                		p.performCommand("ap folder Messages");
                	
                	} else {
                		p.performCommand("ap edithawnmainmenu");
                	}
                }
            }

            e.setCancelled(true);
        } else if (inv.equals("§cAP - Folder Scoreboard")) {
            if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"))) {
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
                   
                        if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.urgent.spy.adminpanel")) {
                        			for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        } else if (AdminPanelCommandConfig.getConfig().getBoolean("General-Options.Warn-when-people-make-change")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.spy.adminpanel")) {
                        			for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        }
                        
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
                        
                        if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.urgent.spy.adminpanel")) {
                        			for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        } else if (AdminPanelCommandConfig.getConfig().getBoolean("General-Options.Warn-when-people-make-change")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.spy.adminpanel")) {
                        			for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        }
                    } else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                    	String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                    	String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                    	if (nameitem.contains(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"))) {
                    		Integer numberfinal = Integer.valueOf(invnamenum);
                    		numberfinal++;
                    		p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
                    	} else if (nameitem.contains(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"))) {
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
                    
                        if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.urgent.spy.adminpanel")) {
                        			for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        } else if (AdminPanelCommandConfig.getConfig().getBoolean("General-Options.Warn-when-people-make-change")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.spy.adminpanel")) {
                        			for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        }
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
                    
                        if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.urgent.spy.adminpanel")) {
                        			for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: OtherAMConfig.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        } else if (AdminPanelCommandConfig.getConfig().getBoolean("General-Options.Warn-when-people-make-change")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.spy.adminpanel")) {
                        			for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
            							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName())
            									.replaceAll("%arg1%", nameitem)
            									.replaceAll("%arg2%", cfinuse), all);
            						}
                        		}
                        	}
                        	
                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
    							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName())
    									.replaceAll("%arg1%", nameitem)
    									.replaceAll("%arg2%", cfinuse));
    						}
                        }
            		} else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                    	String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                    	String invnamenum = inv.replace("§cAP - File " + Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
                    	if (nameitem.contains(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"))) {
                    		Integer numberfinal = Integer.valueOf(invnamenum);
                    		numberfinal++;
                    		p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
                    	} else if (nameitem.contains(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"))) {
                    		Integer numberfinal = Integer.valueOf(invnamenum);
                    		numberfinal--;
                    		p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
                    	}
                    }
            	}
            } else {
                if (inv.contains("CF-")) {
	            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
	                    p.performCommand("ap folder Cosmetics-Fun");
	                }
                } else if (inv.contains("E-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder Events");
                	}
                } else if (inv.contains("G-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap edithawnmainmenu");
                	}
                } else if (inv.contains("MC-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder M-Classic");
                	}
                } else if (inv.contains("C-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder commands");
                	}
                } else if (inv.contains("T-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder Tablist");
                	}
                } else if (inv.contains("CFU-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder CF-Utility");
                	}
                } else if (inv.contains("MA-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder M-Administration");
                	}
                }  else if (inv.contains("CJI-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder CustomJoinItem");
                	}
                }
            }

            e.setCancelled(true);
        }
    }

}
