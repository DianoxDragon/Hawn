package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup2World;
import fr.dianox.hawn.modules.world.generator.VoidGenerator;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.configs.commands.AdminPanelCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.SetupLangFile;
import fr.dianox.hawn.utility.config.configs.messages.WorldManagerPanelConfig;
import org.bukkit.*;
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

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GuiSystem implements Listener {
	
	public static List<File> fileList = new ArrayList<>();
	private static final List<Player> plistincreation = new ArrayList<>();
	private static final HashMap<Player, String> pchoosegenerator = new HashMap<>();
	private static final HashMap<Player, String> worlddeletion = new HashMap<>();
	
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
						return;
					}

					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Import.Importing-With-A-Command")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Import.Importing-With-A-Command", "GuiSystem", false);
                    }
				} else if (item.getType() == XMaterial.OAK_SAPLING.parseMaterial() || item.getType() == XMaterial.NETHERRACK.parseMaterial()
						|| item.getType() == XMaterial.END_STONE.parseMaterial()) {
					if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
						String worldname = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
						worldname = worldname.replace("§a§l", "");

						if (worldname.contains(" ") || worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
							for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
							}
							return;
						}

						for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.World-Changed")) {
							ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg 1%", worldname), "SetupWorld.World-Changed", "GuiSystem", false);
						}
						Setup2World.chooseworld(worldname);
						e.setCancelled(true);
						return;
					}

					if (!p.hasPermission("hawn.command.world.tp") && !p.hasPermission("hawn.command.world.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.command.world.tp");
						
						return;
					} else {
						
						String worldname = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
						worldname = worldname.replace("§a§l", "");
						
						if (worldname.contains(" ") || worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
							for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
		                        ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
		                    }
							return;
						}

						if (p.getWorld().getName().equals(worldname)) {
							for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Error-Tp")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Tp.Error-Tp", "GuiSystem", false);
							}
						} else {
							World world = Bukkit.getWorld(worldname);
							assert world != null;
							Location location = world.getSpawnLocation();
							p.teleport(location);
							for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Success")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Tp.Success", "GuiSystem", false);
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

					if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
						for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.WARNING")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "SetupWorld.WARNING", "GuiSystem", false);
						}
					}

					p.closeInventory();
				} else if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
					if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
						File file = new File(Main.getInstance().getDataFolder(), "StockageInfo/Setup.lock");
						if (!file.exists()) {
							try {
								file.createNewFile();
							} catch (Exception IOException) {}
						}
						e.setCancelled(true);
						p.closeInventory();
						Setup.needsetup = false;
						return;
					}
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
				
				String worldname = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
				worldname = worldname.replace("§a§l", "");
				worldname = worldname.replace("§c§l", "");

				worlddeletion.remove(p);
				
				worlddeletion.put(p, worldname);
								
				SUREPAGEDELETE(p);
			} else if (e.isRightClick()) {
				if (!p.hasPermission("hawn.command.world.modifymain") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifymain");
					
					return;
				}
				
				String worldname = Objects.requireNonNull(item.getItemMeta()).getDisplayName();
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
				ArrayList<String> lore = new ArrayList<>();
				if (item.getType() == XMaterial.OAK_SAPLING.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether")).replaceAll("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type")).replaceAll("&", "§"), lore, XMaterial.NETHERRACK.parseMaterial()));
				} else if (item.getType() == XMaterial.NETHERRACK.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End")).replaceAll("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type")).replaceAll("&", "§"), lore, XMaterial.END_STONE.parseMaterial()));
				} else if (item.getType() == XMaterial.END_STONE.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal")).replaceAll("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type")).replaceAll("&", "§"), lore, XMaterial.OAK_SAPLING.parseMaterial()));
				}

				p.updateInventory();
			} else if (e.getRawSlot() == 14) {
				ArrayList<String> lore = new ArrayList<>();
				if (item.getType() == XMaterial.GRASS.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Flat")).replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replaceAll("&", "§"), lore, XMaterial.STONE_PRESSURE_PLATE.parseMaterial()));
				} else if (item.getType() == XMaterial.STONE_PRESSURE_PLATE.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Amplified")).replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replaceAll("&", "§"), lore, XMaterial.TALL_GRASS.parseMaterial()));
				} else if (item.getType() == XMaterial.TALL_GRASS.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Large-Biomes")).replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replaceAll("&", "§"), lore, XMaterial.OAK_LEAVES.parseMaterial()));
				} else if (item.getType() == XMaterial.OAK_LEAVES.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Normal")).replaceAll("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replaceAll("&", "§"), lore, XMaterial.GRASS.parseMaterial()));
				}

				p.updateInventory();
			} else if (e.getRawSlot() == 22) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name")).replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");

				String generator;

				try {
					generator = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(1);
					generator = generator.replace("§c", "");
				} catch (Exception e1) {
					generator = "NOGENERATOR";
				}

				PageSelectGenerator(p, worldname, generator);
			} else if (item.getType() == XMaterial.OAK_SIGN.parseMaterial()) {
				for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Creating-The-World")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.Creating-The-World", "GuiSystem", false);
				}

				Environment env = Environment.NORMAL;
				WorldType wtype = WorldType.NORMAL;

				if (Objects.requireNonNull(p.getOpenInventory().getItem(12)).getType() == XMaterial.NETHERRACK.parseMaterial()) {
					env = Environment.NETHER;
				} else if (Objects.requireNonNull(p.getOpenInventory().getItem(12)).getType() == XMaterial.END_STONE.parseMaterial()) {
					env = Environment.THE_END;
				}

				if (Objects.requireNonNull(p.getOpenInventory().getItem(14)).getType() == XMaterial.STONE_PRESSURE_PLATE.parseMaterial()) {
					wtype = WorldType.FLAT;
				} else if (Objects.requireNonNull(p.getOpenInventory().getItem(14)).getType() == XMaterial.TALL_GRASS.parseMaterial()) {
					wtype = WorldType.AMPLIFIED;
				} else if (Objects.requireNonNull(p.getOpenInventory().getItem(14)).getType() == XMaterial.OAK_LEAVES.parseMaterial()) {
					wtype = WorldType.LARGE_BIOMES;
				}

				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name")).replaceAll("&", "§") + " §e", "");
				worldname = worldname.replaceAll(" ", "_");

				if (worldname.contains("\\(") || worldname.contains("\\)") || worldname.contains("§")) {
					for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
					}
					return;
				}


				ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", true);
				ConfigWorldGeneral.saveConfigFile();

				String generator;

				try {
					generator = p.getOpenInventory().getItem(22).getItemMeta().getLore().get(0);
					generator = generator.replace("§c", "");

					if (env == Environment.NORMAL) {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "normal");
						ConfigWorldGeneral.saveConfigFile();
					} else if (env == Environment.NETHER) {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "nether");
						ConfigWorldGeneral.saveConfigFile();
					} else {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "the_end");
						ConfigWorldGeneral.saveConfigFile();
					}

					if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
						for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.WARNING")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "SetupWorld.WARNING", "GuiSystem", false);
						}
					}

					p.closeInventory();


					if (generator.equals("hvg")) {
						Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(env).generator(new VoidGenerator()));
					} else {
						Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(env).generator(generator));
					}

					ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Generator", generator);
					ConfigWorldGeneral.saveConfigFile();
				} catch (Exception e1) {

					if (env == Environment.NORMAL) {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "normal");
						ConfigWorldGeneral.saveConfigFile();
					} else if (env == Environment.NETHER) {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "nether");
						ConfigWorldGeneral.saveConfigFile();
					} else {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Environment", "the_end");
						ConfigWorldGeneral.saveConfigFile();
					}

					if (wtype == WorldType.FLAT) {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "flat");
						ConfigWorldGeneral.saveConfigFile();
					} else if (wtype == WorldType.AMPLIFIED) {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "amplified");
						ConfigWorldGeneral.saveConfigFile();
					} else if (wtype == WorldType.LARGE_BIOMES) {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "large_biomes");
						ConfigWorldGeneral.saveConfigFile();
					} else {
						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Type", "normal");
						ConfigWorldGeneral.saveConfigFile();
					}

					if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
						for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.WARNING")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "SetupWorld.WARNING", "GuiSystem", false);
						}
					}

					p.closeInventory();

					Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(env).type(wtype));
				}
				for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", worldname), "Gui.Create.World-Created", "GuiSystem", false);
				}
			}

			e.setCancelled(true);
		} else if (inv.equals(titlegui +" - Generator")) {

			ItemStack item = e.getCurrentItem();

			String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
			worldname = worldname.replace("§7" + Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name")).replaceAll("&", "§") + " §e", "");
			worldname = worldname.replaceAll(" ", "_");

			String generator;

			try {
				generator = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(1);
				generator = generator.replace("§c", "");
			} catch (Exception e1) {
				generator = "NOGENERATOR";
			}

			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				PageCreateWorld(p, worldname, generator);
			} else if (e.getRawSlot() == 12) {
				p.getOpenInventory().setItem(12,
						createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Generator-Page.Void-Generator").replaceAll("&", "§"),
								XMaterial.ENDER_EYE.parseMaterial()));

				p.getOpenInventory().setItem(14,
						createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Generator-Page.Custom-Generator").replaceAll("&", "§"),
								XMaterial.ENDER_PEARL.parseMaterial()));

				ArrayList<String> lore = new ArrayList<>();

				lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e" + worldname);
				lore.add("§chvg");

				p.getOpenInventory().setItem(51, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Page.Back").replaceAll("&", "§"), lore, XMaterial.BARRIER.parseMaterial()));
			} else if (e.getRawSlot() == 14) {
				pchoosegenerator.put(p, worldname);
				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Choose-A-Generator")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.Choose-A-Name", "GuiSystem", false);
				}

				if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
					for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.WARNING")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "SetupWorld.WARNING", "GuiSystem", false);
					}
				}

				p.closeInventory();
			}

			p.updateInventory();

			e.setCancelled(true);
		} else if (inv.equals(titlegui + " - Delete - SURE ?!")) {
			/*
			 * Delete page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()) {
				FirstPage(p);

				worlddeletion.remove(p);
			} else if (item.getType() == XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()) {
				if (worlddeletion.containsKey(p)) {
					String worldname = worlddeletion.get(p);
					
					if (Bukkit.getWorld(worldname) != null) {
						File folder = new File(Objects.requireNonNull(Bukkit.getServer().getWorld(worldname)).getWorldFolder().getPath());
		    			World world = Bukkit.getServer().getWorld(worldname);
						assert world != null;
						if (!world.getPlayers().isEmpty()) {
		    				List<Player> list = world.getPlayers();
							for (Player player : list) {
								List<World> tpList = Bukkit.getServer().getWorlds();
								World spawn = tpList.get(0);
								player.teleport(spawn.getSpawnLocation());
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

					}
					FirstPage(p);
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
		fileList.clear();
		getFileList(directory);
		
		// Decimals to calculate folder size
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("0.##");
		format.setDecimalFormatSymbols(symbols);
		
		int numberslot = 0;
		int checkmax = 0;
		boolean higher = false;
		
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
				} catch (NoClassDefFoundError ignored) {}
				
				if (Bukkit.getWorld(worldname) != null) {
					
					List<String> lore = new ArrayList<>();
					
					lore.add(" ");
					if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
						lore.add(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Line-1")));
					} else {
						lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-One").replaceAll("&", "§"));
						lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Two").replaceAll("&", "§"));
					}
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

		if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
			inv.setItem(48, createGuiItemWL(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Done")), XMaterial.EMERALD_BLOCK.parseMaterial()));
		} else {
			inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        List<String> whitelistuse = AdminPanelCommandConfig.getConfig().getStringList("General-Options.List-Of-People-Can-Use-The-Panel");

        if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
	        inv.setItem(51, createGuiItemWL(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Close-Inventory")), XMaterial.BARRIER.parseMaterial()));
        } else if (p.hasPermission("hawn.adminpanel") && whitelistuse.contains(p.getName())) {
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
		fileList.clear();
		getFileList(directory);
		
		// Decimals to calculate folder size
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		DecimalFormat format = new DecimalFormat("0.##");
		format.setDecimalFormatSymbols(symbols);
		
		int numberslot = 0;
		int checkmax = 0;
		
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
					if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
						lore.add(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Line-1")));
					} else {
						lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-One").replaceAll("&", "§"));
						lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Two").replaceAll("&", "§"));
					}
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

		if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
			inv.setItem(48, createGuiItemWL(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Done")), XMaterial.EMERALD_BLOCK.parseMaterial()));
		} else {
			inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        List<String> whitelistuse = AdminPanelCommandConfig.getConfig().getStringList("General-Options.List-Of-People-Can-Use-The-Panel");

		if (Setup.needsetup && Setup.setupplace == 21 && p.hasPermission("hawn.setup")) {
			inv.setItem(51, createGuiItemWL(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Close-Inventory")), XMaterial.BARRIER.parseMaterial()));
		} else if (p.hasPermission("hawn.adminpanel") && whitelistuse.contains(p.getName())) {
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
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> PageCreateWorld(p, name, "NOGENERATOR"), 1);

			e.setCancelled(true);
		} else if (pchoosegenerator.containsKey(p)) {
			String name = e.getMessage();

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				@Override
				public void run() {
					PageCreateWorld(p, pchoosegenerator.get(p), name);
					pchoosegenerator.remove(p);
				}
			}, 1);

			e.setCancelled(true);
		}
	}
	
	@EventHandler
	private static void getnameecheckone(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		plistincreation.remove(p);

		worlddeletion.remove(p);
	}
	
	@EventHandler
	private static void getnameechecktwo(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		plistincreation.remove(p);

		worlddeletion.remove(p);
	}
	
	@EventHandler
	private static void invclose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		
		String inv = p.getOpenInventory().getTitle();
		String titlegui = "§cWorld Manager";
		
		if (inv.equals(titlegui + " - Delete - SURE ?!")) {
			worlddeletion.remove(p);
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
	public static void PageCreateWorld(Player p, String name, String Generator) {
		
		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Create world");
		
		ArrayList<String> lore = new ArrayList<>();
		
		lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replaceAll("&", "§"));
		
		inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type").replaceAll("&", "§"), lore, XMaterial.OAK_SAPLING.parseMaterial()));
		
		lore.clear();
		
		lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Normal").replaceAll("&", "§"));
		
		inv.setItem(14, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face").replaceAll("&", "§"), lore, XMaterial.GRASS.parseMaterial()));

		lore.clear();

		if (!Generator.equals("NOGENERATOR")) {
			lore.add("§c" + Generator);
		}

		inv.setItem(22, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Generator").replaceAll("&", "§"), lore, XMaterial.CRAFTING_TABLE.parseMaterial()));

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
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replaceAll("&", "§"), lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
		
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Main-Menu").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}

	public static void PageSelectGenerator(Player p, String name, String Generator) {

		/*
		 * Basics
		 */
		Inventory inv = Bukkit.createInventory(null, 54, "§cWorld Manager - Generator");

		inv.setItem(12, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Generator-Page.Void-Generator").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));

		inv.setItem(14, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Generator-Page.Custom-Generator").replaceAll("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));

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
		inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));

		ArrayList<String> lore = new ArrayList<>();

		lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replaceAll("&", "§") + " §e" + name);

		if (!Generator.equals("NOGENERATOR")) {
			lore.add("§c" + Generator);
		}

		inv.setItem(51, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Page.Back").replaceAll("&", "§"), lore, XMaterial.BARRIER.parseMaterial()));

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
		
		ArrayList<String> lore = new ArrayList<>();
		
		if (ConfigGeneral.getConfig().getInt("Plugin.12-Hours-Or-24-Hours-Format") == 12) {
		
			inv.setItem(11, createGuiItem("§70AM", lore, XMaterial.ENDER_PEARL.parseMaterial()));
		
			inv.setItem(13, createGuiItem("§712PM", lore, XMaterial.ENDER_PEARL.parseMaterial()));
		
			inv.setItem(15, createGuiItem("§76PM", lore, XMaterial.ENDER_PEARL.parseMaterial()));
		
		} else {
			
			inv.setItem(11, createGuiItem("§700:00", lore, XMaterial.ENDER_PEARL.parseMaterial()));
			
			inv.setItem(13, createGuiItem("§712:00", lore, XMaterial.ENDER_PEARL.parseMaterial()));
			
			inv.setItem(15, createGuiItem("§718:00", lore, XMaterial.ENDER_PEARL.parseMaterial()));
			
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
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replaceAll("&", "§"), lore, XMaterial.OAK_SIGN.parseMaterial()));
        
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
			File[] files = worldFolder.listFiles((file, name) -> name.toLowerCase().endsWith(".dat"));
			return files != null && files.length > 0;
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
		assert iMeta != null;
		iMeta.setDisplayName(name);
		i.setItemMeta(iMeta);
		return i;
	}
	
	public static ItemStack createGuiItem(String name, ArrayList < String > desc, Material mat) {
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta iMeta = i.getItemMeta();
		assert iMeta != null;
		iMeta.setDisplayName(name);
        iMeta.setLore(desc);
        i.setItemMeta(iMeta);
        return i;
    }
	
	private static void deleteDirectory(File path) {
    	if (path.exists()) {
    		File[] files = path.listFiles();
    		for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
    			if (files[i].isDirectory()) {
    				deleteDirectory(files[i]);
    			} else {
    				files[i].delete();
    			} 
    		} 
    	}
		path.delete();
	}
}
