package fr.dianox.hawn.modules.worldsystem;

import java.io.File;
import java.io.FilenameFilter;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.commands.AdminPanelCommandConfig;
import fr.dianox.hawn.utility.config.messages.WorldManagerPanelConfig;

public class GuiSystem implements Listener {
	
	public static List<File> fileList = new ArrayList<>();
	private static List<Player> plistincreation = new ArrayList<>();
	private static HashMap<Player, String> worlddeletion = new HashMap<Player, String>();
	
	/*
	 * Interact with the manager
	 */
	@EventHandler
    public void onMainWorldManager(InventoryClickEvent e) {
		
		/*
		 * TODO:
		 * Add messages config
		 * Add more permissions... and act with it
		 */
		
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
		
		String titlegui = "§cWorld Manager";
		
		/*
		 * Manage the gui
		 */
		if (inv.contains(titlegui + " - Main")) {
			/*
			 * First page
			 */
			ItemStack item = e.getCurrentItem();
						
			if (e.isLeftClick()) {
				if (item.getType() == XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()) {
					if (!p.hasPermission("hawn.command.world.import") && !p.hasPermission("hawn.command.world.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.command.world.import");
						
						return;
					}
					
					String worldname = item.getItemMeta().getDisplayName();
					worldname = worldname.replace("§c§l", "");
					
					if (worldname.contains(" ") || worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
						for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
	                        ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
	                    }
						return;
					}
					
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.Importing-A-World")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Import.Importing-A-World", "GuiSystem", false);
                    }
					
					p.sendMessage(worldname);
					
					p.closeInventory();
					
					Bukkit.getServer().createWorld((new WorldCreator(worldname)));
					
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.World-Loaded")) {
                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Import.Importing-A-World", "GuiSystem", false);
                    }
				} else if (item.getType() == XMaterial.OAK_SAPLING.parseMaterial() || item.getType() == XMaterial.NETHERRACK.parseMaterial()
						|| item.getType() == XMaterial.END_STONE.parseMaterial()) {
					if (!p.hasPermission("hawn.command.world.tp") && !p.hasPermission("hawn.command.world.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.command.world.tp");
						
						return;
					} else {
						
						String worldname = item.getItemMeta().getDisplayName();
						worldname = worldname.replace("§a§l", "");
						
						if (worldname.contains(" ") || worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
							for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
		                        ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
		                    }
							return;
						}
		                
						if (!p.getWorld().getName().equals(worldname)) {
		                	World world = Bukkit.getWorld(worldname);
		                    Location location = world.getSpawnLocation();
		                    p.teleport(location);
		                    for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Success")) {
		                        ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Tp.Success", "GuiSystem", false);
		                    }
						} else {
							for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Error-Tp")) {
		                        ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Tp.Error-Tp", "GuiSystem", false);
		                    }
						}
					}
				} else if (item.getType() == XMaterial.STONE_HOE.parseMaterial()) {
					if (!p.hasPermission("hawn.command.world.create") && !p.hasPermission("hawn.command.world.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.command.world.create");
						
						return;
					}
					
					plistincreation.add(p);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Choose-A-Name")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.Choose-A-Name", "GuiSystem", false);
                    }
					p.closeInventory();
				} else if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
					p.performCommand("paneladmin");
				} else if (item.getType() == XMaterial.ARROW.parseMaterial()) {
					if (e.getRawSlot() == 45) {
						Exceptionnalsecondpage(p);
					} else {
						FirstPage(p);
					}
				}
			} else if (e.isRightClick() && e.isShiftClick()) {
				if (!p.hasPermission("hawn.command.world.delete") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.delete");
					
					return;
				}
				
				String worldname = item.getItemMeta().getDisplayName();
				worldname = worldname.replace("§a§l", "");
				worldname = worldname.replace("§c§l", "");
				
				if (worlddeletion.containsKey(p)) {
					worlddeletion.remove(p);
				}
				
				worlddeletion.put(p, worldname);
								
				SUREPAGEDELETE(p);
			} else if (e.isRightClick()) {
				if (!p.hasPermission("hawn.command.world.modifymain") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifymain");
					
					return;
				}
				
				String worldname = item.getItemMeta().getDisplayName();
				worldname = worldname.replace("§a§l", "");
				worldname = worldname.replace("§c§l", "");
				
				ModifyWorldMainPage(p, worldname);
			}
			
			e.setCancelled(true);
		} else if (inv.equals(titlegui + " - Create world")) {
						
			/*
			 * Creation page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				FirstPage(p);
			} else if (e.getRawSlot() == 12) {
				List<String> lore = new ArrayList<>();
				if (item.getType() == XMaterial.OAK_SAPLING.parseMaterial()) {
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replaceAll("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.NETHERRACK.parseMaterial()));
				} else if (item.getType() == XMaterial.NETHERRACK.parseMaterial()) {
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replaceAll("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.END_STONE.parseMaterial()));
				} else if (item.getType() == XMaterial.END_STONE.parseMaterial()) {
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replaceAll("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_SAPLING.parseMaterial()));
				}
				
				p.updateInventory();
			} else if (e.getRawSlot() == 14) {
				List<String> lore = new ArrayList<>();
				if (item.getType() == XMaterial.GRASS.parseMaterial()) {
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Flat").replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.STONE_PRESSURE_PLATE.parseMaterial()));
				} else if (item.getType() == XMaterial.STONE_PRESSURE_PLATE.parseMaterial()) {
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Amplified").replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.TALL_GRASS.parseMaterial()));
				} else if (item.getType() == XMaterial.TALL_GRASS.parseMaterial()) {
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Large-Biomes").replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_LEAVES.parseMaterial()));
				} else if (item.getType() == XMaterial.OAK_LEAVES.parseMaterial()) {
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Normal").replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.GRASS.parseMaterial()));
				}
				
				p.updateInventory();
			} else if (item.getType() == XMaterial.OAK_SIGN.parseMaterial()) {
				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.Creating-The-World", "GuiSystem", false);
                }
				
				Environment env = Environment.NORMAL;
				WorldType wtype = WorldType.NORMAL;
				
				if (p.getOpenInventory().getItem(12).getType() == XMaterial.NETHERRACK.parseMaterial()) {
					env = Environment.NETHER;
				} else if (p.getOpenInventory().getItem(12).getType() == XMaterial.END_STONE.parseMaterial()) {
					env = Environment.THE_END;
				}
				
				if (p.getOpenInventory().getItem(14).getType() == XMaterial.STONE_PRESSURE_PLATE.parseMaterial()) {
					wtype = WorldType.FLAT;
				} else if (p.getOpenInventory().getItem(14).getType() == XMaterial.TALL_GRASS.parseMaterial()) {
					wtype = WorldType.AMPLIFIED;
				} else if (p.getOpenInventory().getItem(14).getType() == XMaterial.OAK_LEAVES.parseMaterial()) {
					wtype = WorldType.LARGE_BIOMES;
				} 
				
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				if (worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
                    }
					return;
				}
				
				p.closeInventory();
				
				ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", true);
        		ConfigWorldGeneral.saveConfigFile();
				
				Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(env).type(wtype));
				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Create.World-Created", "GuiSystem", false);
                }
			}
			
			e.setCancelled(true);
		} else if (inv.equals(titlegui + " - Delete - SURE ?!")) {
			/*
			 * Delete page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()) {
				FirstPage(p);
				
				if (worlddeletion.containsKey(p)) {
					worlddeletion.remove(p);
				}
			} else if (item.getType() == XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()) {
				if (worlddeletion.containsKey(p)) {
					String worldname = worlddeletion.get(p);
					
					if (Bukkit.getWorld(worldname) != null) {
						File folder = new File(Bukkit.getServer().getWorld(worldname).getWorldFolder().getPath());
		    			World world = Bukkit.getServer().getWorld(worldname);
		    			if (!world.getPlayers().isEmpty()) {
		    				List<Player> list = world.getPlayers();
		    				for (int i = 0; i < list.size(); i++) {
		    					Player plist = (Player)list.get(i);
		    					List<World> tpList = Bukkit.getServer().getWorlds();
		    					World spawn = (World)tpList.get(0);
		    					plist.teleport(spawn.getSpawnLocation());
		    				}
		    			}
		    			
		    			Bukkit.getServer().unloadWorld(worldname, true);
		    			deleteDirectory(folder);
		    			
		    			ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", null);
		    			ConfigWorldGeneral.getConfig().set("World-List." + worldname, null);
		        		ConfigWorldGeneral.saveConfigFile();
		    			
		        		for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.World-Deleted")) {
		                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Delete.World-Deleted", "GuiSystem", false);
		                }
		    			
		    			FirstPage(p);
					} else {
						String pathname = new File(".").getAbsolutePath();
						File directory = new File(pathname + "\\" + worldname + "\\");
		    			deleteDirectory(directory);
		    			
		    			for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.World-Deleted")) {
		                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Delete.World-Deleted", "GuiSystem", false);
		                }
		    			
		    			ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", null);
		    			ConfigWorldGeneral.getConfig().set("World-List." + worldname, null);
		        		ConfigWorldGeneral.saveConfigFile();
		    			
		    			FirstPage(p);
					}
				} else {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.Error.Mystery")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Delete.Error.Mystery", "GuiSystem", false);
	                }
				}
			}
			
			e.setCancelled(true);
		} else if (inv.contains(titlegui + " - Change World")) {
			
			/*
			 * Change main page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				FirstPage(p);
			} else if (item.getType() == XMaterial.CLOCK.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifytime") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifytime");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldTime(p, worldname);
			} else if (item.getType() == XMaterial.WHITE_WOOL.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifyweather") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifyweather");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldWeather(p, worldname);
			} else if (item.getType() == XMaterial.BLUE_WOOL.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifyweather") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifyweather");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldWeather(p, worldname);
			} else if (item.getType() == XMaterial.YELLOW_WOOL.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifyweather") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifyweather");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldWeather(p, worldname);
			} else if (item.getType() == XMaterial.DIAMOND_SWORD.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifydifficulty") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifydifficulty");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldDifficulty(p, worldname);
			}
			
			e.setCancelled(true);
		} else if (inv.contains(titlegui + " - Ch. World - Time")) {
			
			/*
			 * Change time main page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldMainPage(p, worldname);
			} else if (item.getType() == XMaterial.ENDER_PEARL.parseMaterial()) {
				if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 12) {
					
					p.getOpenInventory().setItem(11, createGuiItemWL("§70AM", XMaterial.ENDER_PEARL.parseMaterial()));
				
					p.getOpenInventory().setItem(13, createGuiItemWL("§712PM", XMaterial.ENDER_PEARL.parseMaterial()));
				
					p.getOpenInventory().setItem(15, createGuiItemWL("§76PM", XMaterial.ENDER_PEARL.parseMaterial()));
				
				} else {
					
					p.getOpenInventory().setItem(11, createGuiItemWL("§700:00", XMaterial.ENDER_PEARL.parseMaterial()));
					
					p.getOpenInventory().setItem(13, createGuiItemWL("§712:00", XMaterial.ENDER_PEARL.parseMaterial()));
					
					p.getOpenInventory().setItem(15, createGuiItemWL("§718:00", XMaterial.ENDER_PEARL.parseMaterial()));
					
				}
				
				p.updateInventory();
				
				if (e.getRawSlot() == 11) {
					if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 12) {
						p.getOpenInventory().setItem(11, createGuiItemWL("§70AM", XMaterial.ENDER_EYE.parseMaterial()));
					} else {
						p.getOpenInventory().setItem(11, createGuiItemWL("§700:00", XMaterial.ENDER_EYE.parseMaterial()));
					}
				} else if (e.getRawSlot() == 13) {
					if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 12) {
						p.getOpenInventory().setItem(13, createGuiItemWL("§712PM", XMaterial.ENDER_EYE.parseMaterial()));
					} else {
						p.getOpenInventory().setItem(13, createGuiItemWL("§712:00", XMaterial.ENDER_EYE.parseMaterial()));
					}
				} else if (e.getRawSlot() == 15) {
					if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 12) {
						p.getOpenInventory().setItem(15, createGuiItemWL("§76PM", XMaterial.ENDER_EYE.parseMaterial()));
					} else {
						p.getOpenInventory().setItem(15, createGuiItemWL("§718:00", XMaterial.ENDER_EYE.parseMaterial()));
					}
				}
				
				p.updateInventory();
			} else if (item.getType() == XMaterial.OAK_SIGN.parseMaterial()) {
				
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				if (p.getOpenInventory().getItem(11).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setTime(16000L);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Time-Changed")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Time-Changed", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(13).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setTime(6000L);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Time-Changed")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Time-Changed", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(15).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setTime(11000L);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Time-Changed")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Time-Changed", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else {
					ModifyWorldMainPage(p, worldname);
				}
			}
			
			e.setCancelled(true);
		} else if (inv.contains(titlegui + " - Ch. World - Weather")) {
			
			/*
			 * Change weather main page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldMainPage(p, worldname);
			} else if (item.getType() == XMaterial.ENDER_PEARL.parseMaterial()) {
				p.getOpenInventory().setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.getOpenInventory().setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
					
				p.getOpenInventory().setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.updateInventory();
				
				if (e.getRawSlot() == 11) {
					p.getOpenInventory().setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replaceAll("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 13) {
					p.getOpenInventory().setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replaceAll("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 15) {
					p.getOpenInventory().setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replaceAll("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				}
				
				p.updateInventory();
			} else if (item.getType() == XMaterial.OAK_SIGN.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				if (p.getOpenInventory().getItem(11).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setStorm(false);
					Bukkit.getWorld(worldname).setThundering(false);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Weather.Sun")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Weather.Sun", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(13).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setStorm(true);
					Bukkit.getWorld(worldname).setThundering(false);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Weather.Rain")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Weather.Rain", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(15).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setStorm(true);
					Bukkit.getWorld(worldname).setThundering(true);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Weather.Storm")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Weather.Storm", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else {
					ModifyWorldMainPage(p, worldname);
				}
			}
			
			e.setCancelled(true);
		} else if (inv.contains(titlegui + " - Ch. World - Dif.")) {
			
			/*
			 * Change Difficulty main page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				ModifyWorldMainPage(p, worldname);
			} else if (item.getType() == XMaterial.ENDER_PEARL.parseMaterial()) {
				p.getOpenInventory().setItem(10, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.getOpenInventory().setItem(12, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
					
				p.getOpenInventory().setItem(14, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.getOpenInventory().setItem(16, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.updateInventory();
				
				if (e.getRawSlot() == 10) {
					p.getOpenInventory().setItem(10, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 12) {
					p.getOpenInventory().setItem(12, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 14) {
					p.getOpenInventory().setItem(14, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 16) {
					p.getOpenInventory().setItem(16, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				}
				
				p.updateInventory();
			} else if (item.getType() == XMaterial.OAK_SIGN.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");
				
				if (p.getOpenInventory().getItem(10).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.PEACEFUL);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Peaceful")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Difficulty.Peaceful", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(12).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.EASY);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Easy")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Difficulty.Easy", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(14).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.NORMAL);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Normal")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Difficulty.Normal", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(16).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.HARD);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Hard")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Modify-World.Difficulty.Hard", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else {
					ModifyWorldMainPage(p, worldname);
				}
			}
			
			e.setCancelled(true);
		}
	}
	
	/*
	 * -------------------------------
	 *  DEDICATED TO CREATE THE PAGES
	 * -------------------------------
	 */
	public static void FirstPage(Player p) {
		
		/*
		 * TODO:
		 * Change lore with permission
		 * Add messages config
		 * Add more permissions... and act with it
		 */
		
		/*
		 * Basics
		 */		
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Main");
		
		String pathname = new File(".").getAbsolutePath();
		File directory = new File(pathname);
		getFileList(directory);
		
		// Decimals to calculate folder size
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("0.##");
		format.setDecimalFormatSymbols(symbols);
		
		Integer numberslot = 0;
		Integer checkmax = 0;
		Boolean higher = false;
		
		/*
		 * The gui overall
		 */
		for (File directorfile : fileList) {
			
			if (checkmax > 35) {
				higher = true;
				continue;
			}
			
			if (checkIfIsWorld(directorfile)) {
				// Get folder name and size
				String worldname = directorfile.getName();
				
				float size = (float) 0123456789.0;
				float check = (float) 0123456789.0;
				
				try {
					size = FileUtils.sizeOfDirectory(new File(directory + "\\" + worldname + "\\"));
					size =  Float.parseFloat(format.format(size/1024/1024));
				} catch (NoClassDefFoundError e) {}
				
				if (Bukkit.getWorld(worldname) != null) {
					
					List<String> lore = new ArrayList<>();
					
					lore.add(" ");
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-One").replaceAll("&", "§"));
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Two").replaceAll("&", "§"));
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Three").replaceAll("&", "§"));
					lore.add(" ");
					lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Players") + " §e" + Bukkit.getWorld(worldname).getPlayers().size());
					if (size != check) {
						lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Size") + " §e" + size + "MB");
					}
					
					if (Bukkit.getWorld(worldname).getEnvironment() == Environment.NORMAL) {
						
						if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.EASY) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.HARD) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.NORMAL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.PEACEFUL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"));
						} 
						
						lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replaceAll("&", "§"));
						
						inv.setItem(numberslot, createGuiItem("§a§l" + worldname, (ArrayList<String>) lore, XMaterial.OAK_SAPLING.parseMaterial()));
					} else if (Bukkit.getWorld(worldname).getEnvironment() == Environment.NETHER) {
						
						if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.EASY) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.HARD) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.NORMAL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.PEACEFUL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"));
						} 
						
						lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replaceAll("&", "§"));
						
						inv.setItem(numberslot, createGuiItem("§a§l" + worldname, (ArrayList<String>) lore, XMaterial.NETHERRACK.parseMaterial()));
					} else if (Bukkit.getWorld(worldname).getEnvironment() == Environment.THE_END) {
						
						if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.EASY) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.HARD) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.NORMAL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.PEACEFUL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"));
						} 
						
						lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " " + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replaceAll("&", "§"));
						
						inv.setItem(numberslot, createGuiItem("§a§l" + worldname, (ArrayList<String>) lore, XMaterial.END_STONE.parseMaterial()));
					}
					
					numberslot++;
				} else {
					
					List<String> lore = new ArrayList<>();
										
					lore.add(" ");
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-One-Second").replaceAll("&", "§"));
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Three").replaceAll("&", "§"));
					lore.add(" ");
					lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Size") + " §e" + size + "MB");
					lore.add(" ");
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Four").replaceAll("&", "§"));
					
					inv.setItem(numberslot, createGuiItem("§c§l" + worldname, (ArrayList<String>) lore, XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
					numberslot++;
				}
				
				checkmax++;
			}
		}
		
		fileList.clear();
		
        inv.setItem(36, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(37, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(38, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(39, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(40, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(41, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(42, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(43, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(44, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial())); 
        
		if (higher) {
			inv.setItem(45, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Page.Next").replaceAll("&", "§"), XMaterial.ARROW.parseMaterial()));
		} else {
			inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}
        
        inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        inv.setItem(47, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Create-a-new-world").replaceAll("&", "§"), XMaterial.STONE_HOE.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        List<String> whitelistuse = AdminPanelCommandConfig.getConfig().getStringList("General-Options.List-Of-People-Can-Use-The-Panel");
		
        if (p.hasPermission("hawn.adminpanel") && whitelistuse.contains(p.getName())) {
        	inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.PanelAdmin").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        } else {
        	inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        }
        
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	public static void Exceptionnalsecondpage(Player p) {
		
		/*
		 * TODO:
		 * Change lore with permission
		 * Add messages config
		 * Add more permissions... and act with it
		 */
		
		/*
		 * Basics
		 */		
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Main 2");
		
		String pathname = new File(".").getAbsolutePath();
		File directory = new File(pathname);
		getFileList(directory);
		
		// Decimals to calculate folder size
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("0.##");
		format.setDecimalFormatSymbols(symbols);
		
		Integer numberslot = 0;
		Integer checkmax = 0;
		
		/*
		 * The gui overall
		 */
		for (File directorfile : fileList) {
			if (checkIfIsWorld(directorfile)) {
	
				if (checkmax < 35) {
					checkmax++;
					continue;
				}
				
				// Get folder name and size
				String worldname = directorfile.getName();
				
				float size = FileUtils.sizeOfDirectory(new File(directory + "\\" + worldname + "\\"));
				size =  Float.parseFloat(format.format(size/1024/1024));
				
				if (Bukkit.getWorld(worldname) != null) {
					
					List<String> lore = new ArrayList<>();
					
					lore.add(" ");
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-One").replaceAll("&", "§"));
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Two").replaceAll("&", "§"));
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Three").replaceAll("&", "§"));
					lore.add(" ");
					lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Players") + " §e" + Bukkit.getWorld(worldname).getPlayers().size());
					lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Size") + " §e" + size + "MB");
					
					if (Bukkit.getWorld(worldname).getEnvironment() == Environment.NORMAL) {
						
						if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.EASY) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.HARD) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.NORMAL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.PEACEFUL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"));
						} 
						
						lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replaceAll("&", "§"));
						
						inv.setItem(numberslot, createGuiItem("§a§l" + worldname, (ArrayList<String>) lore, XMaterial.OAK_SAPLING.parseMaterial()));
					} else if (Bukkit.getWorld(worldname).getEnvironment() == Environment.NETHER) {
						
						if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.EASY) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.HARD) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.NORMAL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.PEACEFUL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"));
						} 
						
						lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replaceAll("&", "§"));
						
						inv.setItem(numberslot, createGuiItem("§a§l" + worldname, (ArrayList<String>) lore, XMaterial.NETHERRACK.parseMaterial()));
					} else if (Bukkit.getWorld(worldname).getEnvironment() == Environment.THE_END) {
						
						if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.EASY) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.HARD) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.NORMAL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"));
						} else if (Bukkit.getWorld(worldname).getDifficulty() == Difficulty.PEACEFUL) {
							lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"));
						} 
						
						lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " " + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replaceAll("&", "§"));
						
						inv.setItem(numberslot, createGuiItem("§a§l" + worldname, (ArrayList<String>) lore, XMaterial.END_STONE.parseMaterial()));
					}
					
					numberslot++;
				} else {
					
					List<String> lore = new ArrayList<>();
										
					lore.add(" ");
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-One-Second").replaceAll("&", "§"));
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Three").replaceAll("&", "§"));
					lore.add(" ");
					lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Size") + " §e" + size + "MB");
					lore.add(" ");
					lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Four").replaceAll("&", "§"));
					
					inv.setItem(numberslot, createGuiItem("§c§l" + worldname, (ArrayList<String>) lore, XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
					numberslot++;
				}
			}
		}
		
		fileList.clear();
		
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

        inv.setItem(46, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Page.Back").replaceAll("&", "§"), XMaterial.ARROW.parseMaterial()));
                
        inv.setItem(47, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Create-a-new-world").replaceAll("&", "§"), XMaterial.STONE_HOE.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        List<String> whitelistuse = AdminPanelCommandConfig.getConfig().getStringList("General-Options.List-Of-People-Can-Use-The-Panel");
		
        if (p.hasPermission("hawn.adminpanel") && whitelistuse.contains(p.getName())) {
        	inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.PanelAdmin").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        } else {
        	inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        }
        
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>> Create worlds
	// >> get name
	@EventHandler
	private static void getname(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		
		if (plistincreation.contains(p)) {
			String name = e.getMessage();
			
			plistincreation.remove(p);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				
				@Override
				public void run() {
					PageCreateWorld(p, name);
				}
					
			}, 1);
		}
	}
	
	@EventHandler
	private static void getnameecheckone(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		
		if (plistincreation.contains(p)) {
			plistincreation.remove(p);
		}
		
		if (worlddeletion.containsKey(p)) {
			worlddeletion.remove(p);
		}
	}
	
	@EventHandler
	private static void getnameechecktwo(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if (plistincreation.contains(p)) {
			plistincreation.remove(p);
		}
		
		if (worlddeletion.containsKey(p)) {
			worlddeletion.remove(p);
		}
	}
	
	@EventHandler
	private static void invclose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		
		String inv = p.getOpenInventory().getTitle();
		String titlegui = "§cWorld Manager";
		
		if (inv.equals(titlegui + " - Delete - SURE ?!")) {
			if (worlddeletion.containsKey(p)) {
				worlddeletion.remove(p);
			}
		}
	}
	
	@EventHandler
	private static void getnameecheckthree(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (plistincreation.contains(p)) {
				plistincreation.remove(p);
				
				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Cancelled")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.World-Cancelled", "GuiSystem", false);
                }
				
				e.setCancelled(true);
			}
		}
	}
	
	// >> Method gui
	public static void PageCreateWorld(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Create world");
		
		List<String> lore = new ArrayList<>();
		
		lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replaceAll("&", "§"));
		
		inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.OAK_SAPLING.parseMaterial()));
		
		lore.clear();
		
		lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Normal").replaceAll("&", "§"));
		
		inv.setItem(14, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.GRASS.parseMaterial()));
		
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
        
        lore.clear();
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
		
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Main-Menu").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>> Delete worlds
	public static void SUREPAGEDELETE(Player p) {
		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Delete - SURE ?!");
		
		inv.setItem(0, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(1, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(2, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(3, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(4, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(5, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(6, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(7, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(8, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(9, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(10, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(12, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(14, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(16, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(17, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(18, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(19, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(20, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(21, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(22, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(23, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(24, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(25, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(26, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(27, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(28, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(29, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(30, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(31, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(32, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(33, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(34, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(35, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(36, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(37, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(38, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(39, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(40, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(41, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(42, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(43, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(44, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(45, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(46, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(47, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(48, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(49, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replaceAll("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(50, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(52, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(53, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replaceAll("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
		
		p.sendMessage(worlddeletion.get(p));
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>> Modify settings of a world
	// >> Method gui
	public static void ModifyWorldMainPage(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Change World");
		
		List<String> lore = new ArrayList<>();
		
		if (Bukkit.getWorld(name).hasStorm() && Bukkit.getWorld(name).isThundering()) {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Actual-Weather").replaceAll("&", "§") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replaceAll("&", "§"));
			inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Weather").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.YELLOW_WOOL.parseMaterial()));
		} else if (Bukkit.getWorld(name).hasStorm()) {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Actual-Weather").replaceAll("&", "§") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replaceAll("&", "§"));
			inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Weather").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.BLUE_WOOL.parseMaterial()));
		} else {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Actual-Weather").replaceAll("&", "§") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replaceAll("&", "§"));
			inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Weather").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.WHITE_WOOL.parseMaterial()));
		}
		
		lore.clear();
		
		inv.setItem(14, createGuiItem("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Time").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.CLOCK.parseMaterial()));
		
		if (Bukkit.getWorld(name).getDifficulty() == Difficulty.EASY) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"));
		} else if (Bukkit.getWorld(name).getDifficulty() == Difficulty.HARD) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"));
		} else if (Bukkit.getWorld(name).getDifficulty() == Difficulty.NORMAL) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"));
		} else if (Bukkit.getWorld(name).getDifficulty() == Difficulty.PEACEFUL) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"));
		} 
		
		inv.setItem(22, createGuiItem("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Two").replaceAll("&", "§"),  (ArrayList<String>) lore, XMaterial.DIAMOND_SWORD.parseMaterial()));
		
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
        
        lore.clear();
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e" + name);
                
        inv.setItem(51, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Main-Menu").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	public static void ModifyWorldTime(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Ch. World - Time");
		
		List<String> lore = new ArrayList<>();
		
		if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 12) {
		
			inv.setItem(11, createGuiItem("§70AM",  (ArrayList<String>) lore, XMaterial.ENDER_PEARL.parseMaterial()));
		
			inv.setItem(13, createGuiItem("§712PM",  (ArrayList<String>) lore, XMaterial.ENDER_PEARL.parseMaterial()));
		
			inv.setItem(15, createGuiItem("§76PM",  (ArrayList<String>) lore, XMaterial.ENDER_PEARL.parseMaterial()));
		
		} else {
			
			inv.setItem(11, createGuiItem("§700:00",  (ArrayList<String>) lore, XMaterial.ENDER_PEARL.parseMaterial()));
			
			inv.setItem(13, createGuiItem("§712:00",  (ArrayList<String>) lore, XMaterial.ENDER_PEARL.parseMaterial()));
			
			inv.setItem(15, createGuiItem("§718:00",  (ArrayList<String>) lore, XMaterial.ENDER_PEARL.parseMaterial()));
			
		}
		
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
        
        lore.clear();
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Menu").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	public static void ModifyWorldWeather(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Ch. World - Weather");
					
		inv.setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
			
		inv.setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
			
		inv.setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
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
        
        List<String> lore = new ArrayList<>();
        
        lore.clear();
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Menu").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	public static void ModifyWorldDifficulty(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Ch. World - Dif.");

		inv.setItem(10, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));

		inv.setItem(12, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
		inv.setItem(14, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
		inv.setItem(16, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
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
        
        List<String> lore = new ArrayList<>();
        
        lore.clear();
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Menu").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	/*
	 * UTILS
	 */
	// Check if a folder is a world
	public static boolean checkIfIsWorld(File worldFolder) {
		if (worldFolder.isDirectory()) {
			File[] files = worldFolder.listFiles(new FilenameFilter() {
				public boolean accept(File file, String name) {
					return name.toLowerCase().endsWith(".dat");
				}
			});
			if (files != null && files.length > 0) return true;
		}
		return false;
	}
	
	// Get folder/files list
	public static void getFileList(File directory) {
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                	if (file.getAbsolutePath().contains("\\.\\cache\\")) continue;
                	if (file.getAbsolutePath().contains("\\.\\dumps\\")) continue;
                	
                	fileList.add(file);
                } else {
                    getFileList(file);
                }
            }
        }
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
	
	private static boolean deleteDirectory(File path) {
    	if (path.exists()) {
    		File[] files = path.listFiles();
    		for (int i = 0; i < files.length; i++) {
    			if (files[i].isDirectory()) {
    				deleteDirectory(files[i]);
    			} else {
    				files[i].delete();
    			} 
    		} 
    	} 
    	return path.delete();
    }
}
