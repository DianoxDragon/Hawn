package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.ListGui;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import fr.dianox.hawn.utility.config.configs.commands.AdminPanelCommandConfig;
import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import fr.dianox.hawn.utility.config.configs.messages.AdminPanelConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.tasks.TaskReloadServer;
import fr.dianox.hawn.utility.tasks.TaskSavePlayerServer;
import fr.dianox.hawn.utility.tasks.TaskShutdownServer;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

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
        String Displayname;

        String titlegui = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title");
	    assert titlegui != null;
	    titlegui = titlegui.replaceAll("&", "§");
        
        
        if (inv.equals(titlegui)) {
            ItemStack clickedItem = e.getCurrentItem();
            
            if (e.getCurrentItem().getType() == XMaterial.getMat(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material"), "Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material")) {

                Displayname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title");
	            assert Displayname != null;
	            Displayname = Displayname.replaceAll("&", "§");
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                    Displayname = PlaceholderAPI.setPlaceholders(p, Displayname);
                }

                if (Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName().contentEquals(Displayname)) {
                    p.closeInventory();
                } else {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
            /// ADMIN PANEL
        } else if (inv.equals("§cAP")) {

        	if (e.getRawSlot() == 12) {
        		for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Shutdown.Messages")) {
        			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
        		}
            		
        		BukkitTask task = new TaskShutdownServer().runTaskLater(Main.getInstance(), 5);
            } else if (e.getRawSlot() == 13) {
            	for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Reload.Messages")) {
            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
            	}
            		
            	BukkitTask task = new TaskReloadServer(p).runTaskLater(Main.getInstance(), 5);
            } else if (e.getRawSlot() == 14) {
            	for (String msg : AdminPanelConfig.getConfig().getStringList("Special.Item.Save-Players.Messages")) {
            		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
            	}
            	
            	BukkitTask task = new TaskSavePlayerServer(p).runTaskLater(Main.getInstance(), 5);
            } else if (e.getRawSlot() == 16) {
            	BukkitTask task = Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> p.performCommand("hawn reload"), 5);
            } else if (e.getRawSlot() == 33) {
            	p.performCommand("hw");
            } else if (e.getRawSlot() == 29) {
            	ListGui.OpenGui(p, 1);
            } else if (e.getRawSlot() == 10) {
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
        } else if (inv.contains("§cAP - Folder commands") || inv.contains("§cAP - Folder Cosmetics-Fun") ||
            inv.contains("§cAP - Folder Events") || inv.contains("§cAP - Folder Messages") ||
            inv.contains("§cAP - Folder Tablist") || inv.contains("§cAP - Folder CF-Utility") || 
            inv.contains("§cAP - Folder CustomJoinItem")) {

            if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                String name = e.getCurrentItem().getItemMeta().getDisplayName();
                name = name.replaceAll("§b", "");

                if (inv.contains("§cAP - Folder commands")) {
                    if (e.getRawSlot() == 47) {
                    	String invnamenum = inv.replace("§cAP - Folder commands ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal++;
                		p.performCommand("ap folder Commands " + numberfinal);
                    } else if (e.getRawSlot() == 45) {
                    	String invnamenum = inv.replace("§cAP - Folder commands ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal--;
                		p.performCommand("ap folder Commands " + numberfinal);
                    } else {
                    	p.performCommand("ap edit file C-" + name);
                    }
                } else if (inv.contains("§cAP - Folder Cosmetics-Fun")) {
                	if (e.getRawSlot() == 47) {
                    	String invnamenum = inv.replace("§cAP - Folder Cosmetics-Fun ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal++;
                		p.performCommand("ap folder Cosmetics-Fun " + numberfinal);
                    } else if (e.getRawSlot() == 45) {
                    	String invnamenum = inv.replace("§cAP - Folder Cosmetics-Fun ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal--;
                		p.performCommand("ap folder Cosmetics-Fun " + numberfinal);
                    } else {
                    	p.performCommand("ap edit file CF-" + name);
                    }
                } else if (inv.contains("§cAP - Folder Events")) {
                	if (e.getRawSlot() == 47) {
                    	String invnamenum = inv.replace("§cAP - Folder Events ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal++;
                		p.performCommand("ap folder Events " + numberfinal);
                    } else if (e.getRawSlot() == 45) {
                    	String invnamenum = inv.replace("§cAP - Folder Events ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal--;
                		p.performCommand("ap folder Events " + numberfinal);
                    } else {
                    	p.performCommand("ap edit file E-" + name);
                    }
                } else if (inv.contains("§cAP - Folder Messages")) {
                	if (e.getRawSlot() == 47) {
                    	String invnamenum = inv.replace("§cAP - Folder Messages ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal++;
                		p.performCommand("ap folder Messages " + numberfinal);
                    } else if (e.getRawSlot() == 45) {
                    	String invnamenum = inv.replace("§cAP - Folder Messages ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal--;
                		p.performCommand("ap folder Messages " + numberfinal);
                    } else {
                    	p.performCommand("ap edit file M-" + name);
                    }
                } else if (inv.contains("§cAP - Folder Tablist")) {
                	if (e.getRawSlot() == 47) {
                    	String invnamenum = inv.replace("§cAP - Folder Tablist ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal++;
                		p.performCommand("ap folder Tablist " + numberfinal);
                    } else if (e.getRawSlot() == 45) {
                    	String invnamenum = inv.replace("§cAP - Folder Tablist ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal--;
                		p.performCommand("ap folder Tablist " + numberfinal);
                    } else {
                    	p.performCommand("ap edit file T-" + name);
                    }
                } else if (inv.contains("§cAP - Folder CF-Utility")) {
                	if (e.getRawSlot() == 47) {
                    	String invnamenum = inv.replace("§cAP - Folder CF-Utility ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal++;
                		p.performCommand("ap folder CF-Utility " + numberfinal);
                    } else if (e.getRawSlot() == 45) {
                    	String invnamenum = inv.replace("§cAP - Folder CF-Utility ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal--;
                		p.performCommand("ap folder CF-Utility " + numberfinal);
                    } else {
                    	p.performCommand("ap edit file CFU-" + name);
                    }
                } else if (inv.contains("§cAP - Folder CustomJoinItem")) {
                	if (e.getRawSlot() == 47) {
                    	String invnamenum = inv.replace("§cAP - Folder CustomJoinItem ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal++;
                		p.performCommand("ap folder CustomJoinItem " + numberfinal);
                    } else if (e.getRawSlot() == 45) {
                    	String invnamenum = inv.replace("§cAP - Folder CustomJoinItem ", "");
                    	
                    	Integer numberfinal = Integer.valueOf(invnamenum);
                		numberfinal--;
                		p.performCommand("ap folder CustomJoinItem " + numberfinal);
                    } else {
                    	p.performCommand("ap edit file CJI-" + name);
                    }
                } else {
                    p.performCommand("ap edit file " + name);
                }
                
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cUtility")) {
            	p.performCommand("ap folder CF-Utility");
            } else if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial() &&
		            e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"))) {
            	if (inv.equals("§cAP - Folder CF-Utility")) {
            		p.performCommand("ap folder Cosmetics-Fun");
            	} else {
            		p.performCommand("ap edithawnmainmenu");
            	}
            }

            e.setCancelled(true);
        } else if (inv.contains("§cAP - Folder Scoreboard")) {
            if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"))) {
                    p.performCommand("ap");
                }
            }
            
            if (e.getRawSlot() == 47) {
            	String invnamenum = inv.replace("§cAP - Folder Scoreboard ", "");
            	
            	Integer numberfinal = Integer.valueOf(invnamenum);
        		numberfinal++;
        		p.performCommand("ap folder Scoreboard " + numberfinal);
            } else if (e.getRawSlot() == 45) {
            	String invnamenum = inv.replace("§cAP - Folder Scoreboard ", "");
            	
            	Integer numberfinal = Integer.valueOf(invnamenum);
        		numberfinal--;
        		p.performCommand("ap folder Scoreboard " + numberfinal);
            }

            e.setCancelled(true);
        } else if (inv.startsWith("§cAP") && !inv.contains("AP - World")) {
        	
        	String cfinuse = Main.getInstance().getConfigManager().configfileinuse.get(p);

        	File f = new File(Main.getInstance().getDataFolder(), cfinuse);
        	
        	if (!f.exists()) {
        		return;
        	} 
        	
        	YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            
            if (e.getCurrentItem().getType() != XMaterial.BARRIER.parseMaterial()) {
            	if (Bukkit.getVersion().contains("1.16") || Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
                    if (e.getCurrentItem().getType() == XMaterial.GREEN_WOOL.parseMaterial()) {
                    	greenwoolaction(e, inv, p, cfinuse, cfg, f);
                    } else if (e.getCurrentItem().getType() == XMaterial.RED_WOOL.parseMaterial()) {
	                    redwoolaction(e, inv, p, cfinuse, cfg, f);
                    } else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
	                    paperaction(e, inv, p, cfinuse);
                    }
            	} else {
            		if (e.getCurrentItem().getDurability() == 13) {
			            greenwoolaction(e, inv, p, cfinuse, cfg, f);
            		} else if (e.getCurrentItem().getDurability() == 14) {
						redwoolaction(e, inv, p, cfinuse, cfg, f);
            		} else if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
						paperaction(e, inv, p, cfinuse);
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
                } else if (inv.contains("M-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder Messages");
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
                }  else if (inv.contains("CJI-")) {
                	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"))) {
                		p.performCommand("ap folder CustomJoinItem");
                	}
                }
            }

            e.setCancelled(true);
        }
    }

    private void greenwoolaction(InventoryClickEvent e, String inv, Player p, String cfinuse, YamlConfiguration cfg, File f) {
	    String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
	    nameitem = nameitem.replaceAll("§b", "");
	    if (inv.contains("CustomCommand")) {
		    if (e.getCurrentItem().getItemMeta().getDisplayName().contains("commands-general.enable")) {
			    cfg.set(nameitem, false);
		    } else {
			    cfg.set("commands." + nameitem + ".enable", false);
		    }
	    } else {
		    cfg.set(nameitem, false);
	    }

	    try {
		    cfg.save(f);
	    } catch (IOException e1) {
		    e1.printStackTrace();
	    }

	    String invnamenum = inv.replace("§cAP - File " + Main.getInstance().getConfigManager().configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
	    p.performCommand("ap edit file " + cfinuse + " " + invnamenum);

	    if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
		    for (Player all: Bukkit.getServer().getOnlinePlayers()) {
			    if (all.hasPermission("hawn.urgent.spy.adminpanel")) {
				    for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
					    ConfigEventUtils.ExecuteEvent(all, msg.replaceAll("%player%", p.getName())
							    .replaceAll("%arg1%", nameitem)
							    .replaceAll("%arg2%", cfinuse), "", "", false);
				    }
			    }
		    }

		    for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
			    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName())
					    .replaceAll("%arg1%", nameitem)
					    .replaceAll("%arg2%", cfinuse), "", "STRICTSCONSOLE", true);
		    }
	    } else if (AdminPanelCommandConfig.getConfig().getBoolean("General-Options.Warn-when-people-make-change")) {
		    for (Player all: Bukkit.getServer().getOnlinePlayers()) {
			    if (all.hasPermission("hawn.spy.adminpanel")) {
				    for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
					    ConfigEventUtils.ExecuteEvent(all, msg.replaceAll("%player%", p.getName())
							    .replaceAll("%arg1%", nameitem)
							    .replaceAll("%arg2%", cfinuse), "", "", false);
				    }
			    }
		    }

		    for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
			    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName())
					    .replaceAll("%arg1%", nameitem)
					    .replaceAll("%arg2%", cfinuse), "", "STRICTSCONSOLE", true);
		    }
	    }
    }

	private void redwoolaction(InventoryClickEvent e, String inv, Player p, String cfinuse, YamlConfiguration cfg, File f) {
		String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
		nameitem = nameitem.replaceAll("§b", "");
		if (inv.contains("CustomCommand")) {
			if (e.getCurrentItem().getItemMeta().getDisplayName().contains("commands-general.enable")) {
				cfg.set(nameitem, true);
			} else {
				cfg.set("commands." + nameitem + ".enable", true);
			}
		} else {
			cfg.set(nameitem, true);
		}
		try {
			cfg.save(f);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String invnamenum = inv.replace("§cAP - File " + Main.getInstance().getConfigManager().configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
		p.performCommand("ap edit file " + cfinuse + " " + invnamenum);

		if (ServerListConfig.getConfig().getBoolean("Urgent-mode.Enable")) {
			for (Player all: Bukkit.getServer().getOnlinePlayers()) {
				if (all.hasPermission("hawn.urgent.spy.adminpanel")) {
					for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
						ConfigEventUtils.ExecuteEvent(all, msg.replaceAll("%player%", p.getName())
								.replaceAll("%arg1%", nameitem)
								.replaceAll("%arg2%", cfinuse), "", "", false);
					}
				}
			}

			for (String msg: ConfigMAdmin.getConfig().getStringList("Urgent-mode.Hawn-Watch-Panel-Admin")) {
				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName())
						.replaceAll("%arg1%", nameitem)
						.replaceAll("%arg2%", cfinuse), "", "STRICTSCONSOLE", true);
			}
		} else if (AdminPanelCommandConfig.getConfig().getBoolean("General-Options.Warn-when-people-make-change")) {
			for (Player all: Bukkit.getServer().getOnlinePlayers()) {
				if (all.hasPermission("hawn.spy.adminpanel")) {
					for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
						ConfigEventUtils.ExecuteEvent(all, msg.replaceAll("%player%", p.getName())
								.replaceAll("%arg1%", nameitem)
								.replaceAll("%arg2%", cfinuse), "", "", false);
					}
				}
			}

			for (String msg: AdminPanelConfig.getConfig().getStringList("Warning.Hawn-Watch-Panel-Admin")) {
				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName())
						.replaceAll("%arg1%", nameitem)
						.replaceAll("%arg2%", cfinuse), "", "STRICTSCONSOLE", true);
			}
		}
	}

	private void paperaction(InventoryClickEvent e, String inv, Player p, String cfinuse) {
		String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
		String invnamenum = inv.replace("§cAP - File " + Main.getInstance().getConfigManager().configfilereverse.get(Main.getInstance().getDataFolder() + "/" + cfinuse), "");
		if (nameitem.contains(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"))) {
			int numberfinal = Integer.parseInt(invnamenum);
			numberfinal++;
			p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
		} else if (nameitem.contains(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"))) {
			int numberfinal = Integer.parseInt(invnamenum);
			numberfinal--;
			p.performCommand("ap edit file " + cfinuse + " " + numberfinal);
		}
	}

}