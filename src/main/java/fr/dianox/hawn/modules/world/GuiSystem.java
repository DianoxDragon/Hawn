package fr.dianox.hawn.modules.world;

import fr.dianox.hawn.utility.gui.HawnMenu;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.admin.Setup;
import fr.dianox.hawn.modules.admin.SetupUtils.Setup2World;
import fr.dianox.hawn.modules.world.generator.Generators;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import com.cryptomorin.xseries.XMaterial;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral;
import fr.dianox.hawn.utility.config.configs.commands.AdminPanelCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.SetupLangFile;
import fr.dianox.hawn.utility.config.configs.messages.WorldManagerPanelConfig;
import org.bukkit.*;
import org.bukkit.World.Environment;
import fr.dianox.hawn.utility.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
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

	// Loaded worlds without a folder of their own in the world container (recent Paper: world/dimensions/...)
	private static final java.util.Set<File> loadedOnly = new java.util.HashSet<>();

	// The world folders of the world container, then the loaded worlds that are not one of them
	private static void listWorlds(File directory) {
		fileList.clear();
		loadedOnly.clear();
		getFileList(directory);
		fileList.removeIf(WorldDeletion::isDeleting);

		java.util.Set<String> names = new java.util.HashSet<>();
		for (File f : fileList) {
			if (checkIfIsWorld(f)) {
				names.add(f.getName());
			}
		}

		for (World w : Bukkit.getWorlds()) {
			if (!names.contains(w.getName())) {
				File entry = new File(directory, w.getName());
				fileList.add(entry);
				loadedOnly.add(entry);
				names.add(w.getName());
			}
		}

		// Paper 26.x: a world of Worlds.yml kept in the folder of the main world, not loaded (unloaded from the menu or /hw unload)
		org.bukkit.configuration.ConfigurationSection list = fr.dianox.hawn.utility.config.configs.ConfigWorldGeneral.getConfig().getConfigurationSection("World-List");
		if (list != null) {
			for (String name : list.getKeys(false)) {
				if (!names.contains(name) && WorldFolders.dimension(name) != null && !WorldDeletion.isDeleting(WorldFolders.dimension(name))) {
					File entry = new File(directory, name);
					fileList.add(entry);
					loadedOnly.add(entry);
					names.add(name);
				}
			}
		}
	}

	// The folder used by a world: its own folder when it is loaded (it may be elsewhere), else the folder of that name
	private static File folderOf(File directory, String worldname) {
		World w = Bukkit.getWorld(worldname);
		if (w != null) return w.getWorldFolder();
		File own = new File(directory, worldname);
		File dimension = own.isDirectory() ? null : WorldFolders.dimension(worldname);
		return dimension != null ? dimension : own;
	}
	// The players typing the name of a world, or its generator (the world name), in the chat: read by the chat thread too
	private static final java.util.Set<java.util.UUID> plistincreation = java.util.concurrent.ConcurrentHashMap.newKeySet();
	private static final java.util.Map<java.util.UUID, String> pchoosegenerator = new java.util.concurrent.ConcurrentHashMap<>();
	private static final HashMap<java.util.UUID, String> worlddeletion = new HashMap<>();
	
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

		/*
		 * Implement first variables
		 */
		Player p = (Player) e.getWhoClicked();

		// 88: the menus of the World Manager by their type, never by the title (a chest named like them does nothing)
		HawnMenu.Type type = HawnMenu.typeOf(e.getView());
		if (type == null || !type.isWorldManager()) return;

		// A menu: nothing can be taken or put, even when the click does nothing
		e.setCancelled(true);

		// An empty slot, or the inventory of the player
		if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR
				|| e.getClickedInventory() != e.getView().getTopInventory()) return;
		
		/*
		 * Manage the gui
		 */
		if (type == HawnMenu.Type.WORLD_LIST) {
			/*
			 * First page
			 */
			ItemStack item = e.getCurrentItem();
						
			if (e.isLeftClick()) {
				if (item.getType() == XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()) {
					if (!p.hasPermission("hawn.command.world.import") && !p.hasPermission("hawn.command.world.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.command.world.import");
						deny(p);
						return;
					}

					// A world folder not loaded: loaded like /hw import (and at each start)
					String worldname = worldOf(e, true);
					if (worldname == null) return;
					WorldLoading.loadFromMenu(p, worldname);
				} else if (worldOf(e, false) != null) {
					String worldname = worldOf(e, false);

					if (Setup.inWorldStep(p)) {
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
						if (p.getWorld().getName().equals(worldname)) {
							for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Error-Tp")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Tp.Error-Tp", "GuiSystem", false);
							}
						} else {
							WorldFolders.fixSpawn(Bukkit.getWorld(worldname));
							Location location = Bukkit.getWorld(worldname).getSpawnLocation();
							p.teleport(location);
							for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Tp.Success")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Tp.Success", "GuiSystem", false);
							}
						}
					}
				} else if (item.getType() == XMaterial.STONE_HOE.parseMaterial()) {
					if (!p.hasPermission("hawn.command.world.create") && !p.hasPermission("hawn.command.world.*")) {
						MessageUtils.MessageNoPermission(p, "hawn.command.world.create");
						
						return;
					}
					
					plistincreation.add(p.getUniqueId());
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Choose-A-Name")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.Choose-A-Name", "GuiSystem", false);
                    }

					if (Setup.inWorldStep(p)) {
						for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.WARNING")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "SetupWorld.WARNING", "GuiSystem", false);
						}
					}

					p.closeInventory();
				} else if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
					if (Setup.inWorldStep(p)) {
						e.setCancelled(true);
						Setup.finish(p);
						return;
					}
					p.performCommand("paneladmin");
				} else if (item.getType() == XMaterial.ARROW.parseMaterial()) {
					int page = HawnMenu.pageOf(e.getView().getTopInventory());
					if (e.getRawSlot() == 45) {
						WorldsPage(p, page + 1);
					} else if (e.getRawSlot() == 46 && page > 0) {
						WorldsPage(p, page - 1);
					}
				}
			} else if (e.isRightClick() && e.isShiftClick()) {
				// A world, loaded or not: the panes, the arrows... do nothing
				String worldname = worldOf(e, true);
				if (worldname == null) return;

				if (!p.hasPermission("hawn.command.world.delete") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.delete");

					return;
				}

				worlddeletion.put(p.getUniqueId(), worldname);

				SUREPAGEDELETE(p);
			} else if (e.isRightClick()) {
				// Only a loaded world has settings
				String worldname = worldOf(e, false);
				if (worldname == null) return;

				if (!p.hasPermission("hawn.command.world.modifymain") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifymain");

					return;
				}

				ModifyWorldMainPage(p, worldname);
			}
			
			e.setCancelled(true);
		} else if (type == HawnMenu.Type.WORLD_CREATE) {

			/*
			 * Creation page
			 */
			ItemStack item = e.getCurrentItem();

			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				FirstPage(p);
			} else if (e.getRawSlot() == 12) {
				click(p);
				ArrayList<String> lore = new ArrayList<>();
				if (item.getType() == XMaterial.OAK_SAPLING.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether")).replace("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type")).replace("&", "§"), lore, XMaterial.NETHERRACK.parseMaterial()));
				} else if (item.getType() == XMaterial.NETHERRACK.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End")).replace("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type")).replace("&", "§"), lore, XMaterial.END_STONE.parseMaterial()));
				} else if (item.getType() == XMaterial.END_STONE.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal")).replace("&", "§"));
					p.getOpenInventory().setItem(12, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type")).replace("&", "§"), lore, XMaterial.OAK_SAPLING.parseMaterial()));
				}

				p.updateInventory();
			} else if (e.getRawSlot() == 14) {
				click(p);
				ArrayList<String> lore = new ArrayList<>();
				if (item.getType() == XMaterial.GRASS_BLOCK.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Flat")).replace("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replace("&", "§"), lore, XMaterial.STONE_PRESSURE_PLATE.parseMaterial()));
				} else if (item.getType() == XMaterial.STONE_PRESSURE_PLATE.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Amplified")).replace("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replace("&", "§"), lore, XMaterial.TALL_GRASS.parseMaterial()));
				} else if (item.getType() == XMaterial.TALL_GRASS.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Large-Biomes")).replace("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replace("&", "§"), lore, XMaterial.OAK_LEAVES.parseMaterial()));
				} else if (item.getType() == XMaterial.OAK_LEAVES.parseMaterial()) {
					lore.add(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Normal")).replace("&", "§"));
					p.getOpenInventory().setItem(14, createGuiItem(Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face")).replace("&", "§"), lore, XMaterial.GRASS_BLOCK.parseMaterial()));
				}

				p.updateInventory();
			} else if (e.getRawSlot() == 22) {
				// The generator, on this page: left click default or void, right click a generator typed in the chat
				if (e.isRightClick()) {
					String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
					worldname = worldname.replace("§7" + Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name")).replace("&", "§") + " §e", "");
					worldname = worldname.replace(" ", "_");

					pchoosegenerator.put(p.getUniqueId(), worldname);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Choose-A-Generator")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.Choose-A-Generator", "GuiSystem", false);
					}
					p.closeInventory();
				} else {
					String generator = generatorOf(item);
					p.getOpenInventory().setItem(22, generatorItem("hvg".equals(generator) ? "NOGENERATOR" : "hvg"));
					p.updateInventory();
					click(p);
				}
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
				worldname = worldname.replace("§7" + Objects.requireNonNull(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name")).replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");

				if (!WorldFolders.validName(worldname)) {
					for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
					}
					return;
				}

				if (WorldFolders.exists(worldname)) {
					for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Error.World-Already-Exist")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Error.World-Already-Exist", "GuiSystem", false);
					}
					deny(p);
					return;
				}

				ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", true);
				ConfigWorldGeneral.saveConfigFile();

				String generator;
				World created;

				try {
					generator = generatorOf(p.getOpenInventory().getItem(22));
					if (generator.equals("NOGENERATOR")) {
						throw new IllegalStateException("no generator"); // the world type, below
					}

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

					if (Setup.inWorldStep(p)) {
						for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.WARNING")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "SetupWorld.WARNING", "GuiSystem", false);
						}
					}

					p.closeInventory();


					if (generator.equals("hvg")) {
						created = Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(env).generator(Generators.voidGenerator()));
					} else {
						created = Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(env).generator(generator));
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

					if (Setup.inWorldStep(p)) {
						for (String msg: SetupLangFile.getConfig().getStringList("SetupWorld.WARNING")) {
							ConfigEventUtils.ExecuteEvent(p, msg, "SetupWorld.WARNING", "GuiSystem", false);
						}
					}

					p.closeInventory();

					created = Bukkit.getServer().createWorld((new WorldCreator(worldname)).environment(env).type(wtype));
				}

				// The title "World in creation" goes away (a message without a title would leave it)
				p.resetTitle();
				if (created == null) {
					ConfigWorldGeneral.getConfig().set("World-List." + worldname, null);
					ConfigWorldGeneral.saveConfigFile();
					for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.Failed")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Create.Failed", "GuiSystem", false);
					}
					deny(p);
					return;
				}

				done(p);
				for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Created")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Create.World-Created", "GuiSystem", false);
				}
			}

			e.setCancelled(true);
		} else if (type == HawnMenu.Type.WORLD_DELETE) {
			/*
			 * Delete page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()) {
				FirstPage(p);

				worlddeletion.remove(p.getUniqueId());
			} else if (item.getType() == XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()) {
				if (worlddeletion.containsKey(p.getUniqueId())) {
					String worldname = worlddeletion.remove(p.getUniqueId());
					
					if (WorldDeletion.isProtected(worldname)) {
						for (String msg: WorldDeletion.message("Error.Protected-World", "%prefix% &cThe world &e%arg1% &ccan't be deleted: the server needs it")) {
							ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Error.Protected-World", "GuiSystem", false);
						}
					} else if (DefaultWorld.is(worldname)) {
						for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Error.Default-World")) {
							ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Error.Default-World", "GuiSystem", false);
						}
					} else if (Bukkit.getWorld(worldname) != null) {
						if (WorldDeletion.delete(Bukkit.getWorld(worldname))) {
							ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", null);
							ConfigWorldGeneral.getConfig().set("World-List." + worldname, null);
							ConfigWorldGeneral.saveConfigFile();

							for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.World-Deleted")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Delete.World-Deleted", "GuiSystem", false);
							}
						} else {
							for (String msg: WorldDeletion.message("Error.Unload-Failed", "%prefix% &cThe world &e%arg1% &ccould not be unloaded: nothing was deleted")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Error.Unload-Failed", "GuiSystem", false);
							}
						}
					} else {
						// Not loaded: only its folder (Paper 26.x: maybe in the folder of the main world)
						File own = new File(Bukkit.getWorldContainer(), worldname);
						File dimension = own.isDirectory() ? null : WorldFolders.dimension(worldname);
						WorldDeletion.deleteFolder(dimension != null ? dimension : own);

						for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.World-Deleted")) {
							ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Delete.World-Deleted", "GuiSystem", false);
						}

						ConfigWorldGeneral.getConfig().set("World-List." + worldname + ".Load", null);
						ConfigWorldGeneral.getConfig().set("World-List." + worldname, null);
						ConfigWorldGeneral.saveConfigFile();
					}
					FirstPage(p);
				} else {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Delete.Error-Mystery")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Delete.Error.Mystery", "GuiSystem", false);
	                }
				}
			}
			
			e.setCancelled(true);
		} else if (type == HawnMenu.Type.WORLD_SETTINGS) {

			/*
			 * Change main page
			 */
			ItemStack item = e.getCurrentItem();

			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				FirstPage(p);
			} else if (e.getRawSlot() == 40 && item.getType() == XMaterial.HOPPER.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");

				if (!p.hasPermission("hawn.command.world.unload") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.unload");
					deny(p);
				} else if (WorldLoading.unload(p, worldname)) {
					done(p);
					FirstPage(p);
				} else {
					deny(p);
				}
			} else if (e.getRawSlot() == 31) {
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");

				if (!p.hasPermission("hawn.command.world.setdefault") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.setdefault");
					deny(p);
				} else if (DefaultWorld.is(worldname)) {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Default-World.Already")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Default-World.Already", "GuiSystem", false);
					}
					deny(p);
				} else if (!e.isShiftClick()) {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Default-World.Shift-Click")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Default-World.Shift-Click", "GuiSystem", false);
					}
					click(p);
				} else {
					DefaultWorld.apply(p, worldname);
					done(p);
					ModifyWorldMainPage(p, worldname);
				}
			} else if (item.getType() == XMaterial.CLOCK.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifytime") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifytime");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				ModifyWorldTime(p, worldname);
			} else if (item.getType() == XMaterial.WHITE_WOOL.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifyweather") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifyweather");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				ModifyWorldWeather(p, worldname);
			} else if (item.getType() == XMaterial.BLUE_WOOL.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifyweather") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifyweather");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				ModifyWorldWeather(p, worldname);
			} else if (item.getType() == XMaterial.YELLOW_WOOL.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifyweather") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifyweather");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				ModifyWorldWeather(p, worldname);
			} else if (item.getType() == XMaterial.DIAMOND_SWORD.parseMaterial()) {
				if (!p.hasPermission("hawn.command.world.modifydifficulty") && !p.hasPermission("hawn.command.world.*")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.world.modifydifficulty");
					
					return;
				}
				
				String worldname = p.getOpenInventory().getItem(51).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				ModifyWorldDifficulty(p, worldname);
			}
			
			e.setCancelled(true);
		} else if (type == HawnMenu.Type.WORLD_TIME) {
			
			/*
			 * Change time main page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
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
				click(p);
				
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
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				if (p.getOpenInventory().getItem(11).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setTime(18000L); // 00:00
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Time-Changed")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Time-Changed", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(13).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setTime(6000L); // 12:00
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Time-Changed")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Time-Changed", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(15).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setTime(12000L); // 18:00
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Time-Changed")) {
						ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Time-Changed", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Nothing-Selected")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Modify-World.Nothing-Selected", "GuiSystem", false);
					}
					deny(p);
					return;
				}
				done(p);
			}

			e.setCancelled(true);
		} else if (type == HawnMenu.Type.WORLD_WEATHER) {
			
			/*
			 * Change weather main page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				ModifyWorldMainPage(p, worldname);
			} else if (item.getType() == XMaterial.ENDER_PEARL.parseMaterial()) {
				p.getOpenInventory().setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.getOpenInventory().setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
					
				p.getOpenInventory().setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.updateInventory();
				click(p);
				
				if (e.getRawSlot() == 11) {
					p.getOpenInventory().setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replace("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 13) {
					p.getOpenInventory().setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replace("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 15) {
					p.getOpenInventory().setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replace("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				}
				
				p.updateInventory();
			} else if (item.getType() == XMaterial.OAK_SIGN.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				if (p.getOpenInventory().getItem(11).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setStorm(false);
					Bukkit.getWorld(worldname).setThundering(false);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Weather.Sun")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Weather.Sun", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(13).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setStorm(true);
					Bukkit.getWorld(worldname).setThundering(false);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Weather.Rain")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Weather.Rain", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(15).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setStorm(true);
					Bukkit.getWorld(worldname).setThundering(true);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Weather.Storm")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Weather.Storm", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Nothing-Selected")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Modify-World.Nothing-Selected", "GuiSystem", false);
					}
					deny(p);
					return;
				}
				done(p);
			}

			e.setCancelled(true);
		} else if (type == HawnMenu.Type.WORLD_DIFFICULTY) {
			
			/*
			 * Change Difficulty main page
			 */
			ItemStack item = e.getCurrentItem();
			
			if (item.getType() == XMaterial.BARRIER.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				ModifyWorldMainPage(p, worldname);
			} else if (item.getType() == XMaterial.ENDER_PEARL.parseMaterial()) {
				p.getOpenInventory().setItem(10, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.getOpenInventory().setItem(12, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
					
				p.getOpenInventory().setItem(14, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.getOpenInventory().setItem(16, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
				
				p.updateInventory();
				click(p);
				
				if (e.getRawSlot() == 10) {
					p.getOpenInventory().setItem(10, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replace("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 12) {
					p.getOpenInventory().setItem(12, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replace("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 14) {
					p.getOpenInventory().setItem(14, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replace("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				} else if (e.getRawSlot() == 16) {
					p.getOpenInventory().setItem(16, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replace("&", "§"), XMaterial.ENDER_EYE.parseMaterial()));
				}
				
				p.updateInventory();
			} else if (item.getType() == XMaterial.OAK_SIGN.parseMaterial()) {
				String worldname = p.getOpenInventory().getItem(47).getItemMeta().getLore().get(0);
				worldname = worldname.replace("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e", "");
				worldname = worldname.replace(" ", "_");
				
				if (p.getOpenInventory().getItem(10).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.PEACEFUL);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Peaceful")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Difficulty.Peaceful", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(12).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.EASY);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Easy")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Difficulty.Easy", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(14).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.NORMAL);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Normal")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Difficulty.Normal", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else if (p.getOpenInventory().getItem(16).getType() == XMaterial.ENDER_EYE.parseMaterial()) {
					Bukkit.getWorld(worldname).setDifficulty(Difficulty.HARD);
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Difficulty.Hard")) {
	                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", worldname), "Gui.Modify-World.Difficulty.Hard", "GuiSystem", false);
	                }
					ModifyWorldMainPage(p, worldname);
				} else {
					for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Modify-World.Nothing-Selected")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Modify-World.Nothing-Selected", "GuiSystem", false);
					}
					deny(p);
					return;
				}
				done(p);
			}

			e.setCancelled(true);
		}
	}
	
	/*
	 * -------------------------------
	 *  DEDICATED TO CREATE THE PAGES
	 * -------------------------------
	 */
	// The worlds of a page of the list, the bar below
	private static final int PER_PAGE = 36;

	public static void FirstPage(Player p) {
		WorldsPage(p, 0);
	}

	/**
	 * A page of the world list (0 = the first one), 36 worlds per page.
	 */
	public static void WorldsPage(Player p, int page) {
		Inventory inv = HawnMenu.create(54, pageTitle(page), HawnMenu.Type.WORLD_LIST, page);
		fillWorldsPage(p, inv, page);
		p.openInventory(inv);
	}

	private static String pageTitle(int page) {
		return page <= 0 ? "§cWorld Manager - Main" : "§cWorld Manager - Main " + (page + 1);
	}

	/**
	 * The players who look at the world list see it again, without reopening it (the sizes computed, a world deleted).
	 */
	public static void refreshWorldPages() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			Inventory top = player.getOpenInventory().getTopInventory();
			if (HawnMenu.typeOf(top) == HawnMenu.Type.WORLD_LIST) {
				fillWorldsPage(player, top, HawnMenu.pageOf(top));
			}
		}
	}

	private static void fillWorldsPage(Player p, Inventory inv, int page) {

		// The folder of the worlds (--world-container), not always the folder of the server
		File directory = Bukkit.getWorldContainer();
		listWorlds(directory);

		List<String> worlds = new ArrayList<>();
		for (File directorfile : fileList) {
			if (checkIfIsWorld(directorfile) || loadedOnly.contains(directorfile)) {
				worlds.add(directorfile.getName());
			}
		}
		fileList.clear();

		// The sizes not known yet are computed outside the main thread, then the page is shown again
		List<File> outdated = new ArrayList<>();

		for (int slot = 0; slot < PER_PAGE; slot++) {
			int index = page * PER_PAGE + slot;

			if (index >= worlds.size()) {
				inv.setItem(slot, null);
				continue;
			}

			String worldname = worlds.get(index);
			File folder = folderOf(directory, worldname);
			if (WorldSizes.outdated(folder)) {
				outdated.add(folder);
			}

			inv.setItem(slot, worldItem(p, worldname, WorldSizes.get(folder)));
		}

		for (int slot = 36; slot <= 44; slot++) {
			inv.setItem(slot, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		if ((page + 1) * PER_PAGE < worlds.size()) {
			inv.setItem(45, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Page.Next").replace("&", "§"), XMaterial.ARROW.parseMaterial()));
		} else {
			inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		if (page > 0) {
			inv.setItem(46, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Page.Back").replace("&", "§"), XMaterial.ARROW.parseMaterial()));
		} else {
			inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		inv.setItem(47, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Create-a-new-world").replace("&", "§"), XMaterial.STONE_HOE.parseMaterial()));

		if (Setup.inWorldStep(p)) {
			inv.setItem(48, createGuiItemWL(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Done")), XMaterial.EMERALD_BLOCK.parseMaterial()));
		} else {
			inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		// The setup: Previous step (a door: the arrows of 45 and 46 are the pages)
		if (Setup.inWorldStep(p)) {
			inv.setItem(49, createGuiItemWL(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("Setup.Previous")), Setup.NAVIGATION));
		} else {
			inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}
		inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));

		List<String> whitelistuse = AdminPanelCommandConfig.getConfig().getStringList("General-Options.List-Of-People-Can-Use-The-Panel");

		if (Setup.inWorldStep(p)) {
			inv.setItem(51, createGuiItemWL(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Close-Inventory")), XMaterial.BARRIER.parseMaterial()));
		} else if (p.hasPermission("hawn.adminpanel") && whitelistuse.contains(p.getName())) {
			inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.PanelAdmin").replace("&", "§"), XMaterial.BARRIER.parseMaterial()));
		} else {
			inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		}

		inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));

		if (!outdated.isEmpty()) {
			WorldSizes.compute(outdated, GuiSystem::refreshWorldPages);
		}
	}

	// The item of a world in the list: a loaded world (its environment), or a red pane for a world folder not loaded
	private static ItemStack worldItem(Player p, String worldname, long bytes) {
		String size;
		if (bytes < 0) {
			size = text("Gui.Other.Size-Pending", "&7...");
		} else {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setDecimalSeparator('.');
			DecimalFormat format = new DecimalFormat("0.##");
			format.setDecimalFormatSymbols(symbols);
			size = "§e" + format.format(bytes / 1024.0 / 1024.0) + "MB";
		}

		World world = Bukkit.getWorld(worldname);
		ArrayList<String> lore = new ArrayList<>();

		if (world == null) {
			lore.add(" ");
			lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Three").replace("&", "§"));
			lore.add(" ");
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Size") + " " + size);
			lore.add(" ");
			lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Four").replace("&", "§"));
			lore.add(text("Gui.Other.Main.Line-One-Second", "&eLeft-Click&7 to load the world"));

			return createGuiItem("§c§l" + worldname, lore, XMaterial.RED_STAINED_GLASS_PANE.parseMaterial());
		}

		if (DefaultWorld.is(worldname)) {
			lore.add(text("Gui.Other.Main.Default-World", "&6Default world"));
		}
		lore.add(" ");
		if (Setup.inWorldStep(p)) {
			lore.add(MessageUtils.colourTheStuff(SetupLangFile.getConfig().getString("SetupWorld.Line-1")));
		} else {
			lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-One").replace("&", "§"));
			lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Two").replace("&", "§"));
		}
		lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Main.Line-Three").replace("&", "§"));
		lore.add(" ");
		lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Players") + " §e" + world.getPlayers().size());
		lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Size") + " " + size);

		String difficulty;
		switch (world.getDifficulty()) {
			case PEACEFUL: difficulty = "Gui.Other.Difficulty.Peaceful"; break;
			case EASY: difficulty = "Gui.Other.Difficulty.Easy"; break;
			case HARD: difficulty = "Gui.Other.Difficulty.Hard"; break;
			default: difficulty = "Gui.Other.Difficulty.Normal";
		}
		lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Main") + " §e" + WorldManagerPanelConfig.getConfig().getString(difficulty).replace("&", "§"));

		if (world.getEnvironment() == Environment.NETHER) {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Nether").replace("&", "§"));
			return createGuiItem("§a§l" + worldname, lore, XMaterial.NETHERRACK.parseMaterial());
		} else if (world.getEnvironment() == Environment.THE_END) {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " " + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.The_End").replace("&", "§"));
			return createGuiItem("§a§l" + worldname, lore, XMaterial.END_STONE.parseMaterial());
		}

		lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Environment") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replace("&", "§"));
		return createGuiItem("§a§l" + worldname, lore, XMaterial.OAK_SAPLING.parseMaterial());
	}

	/**
	 * The world of an item of the world list: a loaded world, or with unloaded a world folder that is not loaded.
	 * Null for the other items (panes, arrows...) and for a world gone since the menu was opened.
	 */
	private static String worldOf(InventoryClickEvent e, boolean unloaded) {
		ItemStack item = e.getCurrentItem();
		if (e.getRawSlot() >= PER_PAGE || item == null || !item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return null;

		String name = item.getItemMeta().getDisplayName();
		Material type = item.getType();

		if (name.startsWith("§a§l") && (type == XMaterial.OAK_SAPLING.parseMaterial() || type == XMaterial.NETHERRACK.parseMaterial()
				|| type == XMaterial.END_STONE.parseMaterial())) {
			String world = name.substring(4);
			return Bukkit.getWorld(world) != null ? world : null;
		}

		if (unloaded && name.startsWith("§c§l") && type == XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()) {
			String world = name.substring(4);
			return WorldFolders.exists(world) ? world : null;
		}

		return null;
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>> Create worlds
	// >> get name
	/**
	 * Called by the chat listener (Spigot or Paper): the name of a world typed in the chat.
	 * @return true when the message was the name (it is not sent to the chat)
	 */
	public static boolean captureChat(Player p, String name) {
		// The chat thread: remove() tells at once whether the player was typing a name, even if the main thread changes the lists
		if (plistincreation.remove(p.getUniqueId())) {
			String worldname = name.trim().replace(" ", "_");

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
				if (!p.isOnline()) return;

				// Letters, numbers, _ and -: the player types another name
				if (!WorldFolders.validName(worldname)) {
					for (String msg : WorldManagerPanelConfig.getConfig().getStringList("Error.NotGoodName")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "Error.NotGoodName", "GuiSystem", false);
					}
					deny(p);
					plistincreation.add(p.getUniqueId());
					return;
				}

				PageCreateWorld(p, worldname, "NOGENERATOR");
			}, 1);

			return true;
		}

		String worldname = pchoosegenerator.remove(p.getUniqueId());
		if (worldname != null) {
			String generator = name.trim();

			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
				if (p.isOnline()) {
					PageCreateWorld(p, worldname, generator.isEmpty() || generator.contains(" ") ? "NOGENERATOR" : generator);
				}
			}, 1);

			return true;
		}

		return false;
	}
	
	@EventHandler
	private static void getnameecheckone(PlayerQuitEvent e) {
		Player p = e.getPlayer();

		plistincreation.remove(p.getUniqueId());
		pchoosegenerator.remove(p.getUniqueId());

		worlddeletion.remove(p.getUniqueId());
	}

	@EventHandler
	private static void getnameechecktwo(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		plistincreation.remove(p.getUniqueId());
		pchoosegenerator.remove(p.getUniqueId());

		worlddeletion.remove(p.getUniqueId());
	}
	
	@EventHandler
	private static void invclose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		
		if (HawnMenu.typeOf(e.getView()) == HawnMenu.Type.WORLD_DELETE) {
			worlddeletion.remove(p.getUniqueId());
		}
	}
	
	@EventHandler
	private static void getnameecheckthree(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (plistincreation.remove(p.getUniqueId())) {
				for (String msg: WorldManagerPanelConfig.getConfig().getStringList("Gui.Create.World-Cancelled")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.World-Cancelled", "GuiSystem", false);
                }
				
				e.setCancelled(true);
				return;
			}

			// No generator typed: back to the creation page, with the default generator
			String worldname = pchoosegenerator.remove(p.getUniqueId());
			if (worldname != null) {
				for (String msg: WorldDeletion.message("Gui.Create.Generator-Cancelled", "%prefix% &7No generator: the default one is kept")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "Gui.Create.Generator-Cancelled", "GuiSystem", false);
				}

				e.setCancelled(true);
				PageCreateWorld(p, worldname, "NOGENERATOR");
			}
		}
	}
	
	// >> Method gui
	public static void PageCreateWorld(Player p, String name, String Generator) {
		
		/*
		 * Basics
		 */
		Inventory inv = HawnMenu.create(54, "§cWorld Manager - Create world", HawnMenu.Type.WORLD_CREATE);
		
		ArrayList<String> lore = new ArrayList<>();
		
		lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.Normal").replace("&", "§"));
		
		inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldType.World-Type").replace("&", "§"), lore, XMaterial.OAK_SAPLING.parseMaterial()));
		
		lore.clear();
		
		lore.add(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.Normal").replace("&", "§"));
		
		inv.setItem(14, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.WorldFace.World-Face").replace("&", "§"), lore, XMaterial.GRASS_BLOCK.parseMaterial()));

		lore.clear();

		inv.setItem(22, generatorItem(Generator));

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
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replace("&", "§"), lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
		
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Main-Menu").replace("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}

	// >>>>>>>>>>>>>>>>>>>>>>> Delete worlds
	public static void SUREPAGEDELETE(Player p) {
		/*
		 * Basics
		 */
		Inventory inv = HawnMenu.create(54, "§cWorld Manager - Delete - SURE ?!", HawnMenu.Type.WORLD_DELETE);
		
		inv.setItem(0, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(1, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(2, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(3, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(4, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(5, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(6, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(7, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(8, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(9, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(10, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(12, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(14, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(16, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(17, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(18, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(19, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(20, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(21, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(22, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(23, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(24, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(25, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(26, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(27, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(28, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(29, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(30, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(31, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(32, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(33, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(34, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(35, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(36, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(37, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(38, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(39, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(40, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(41, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(42, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(43, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(44, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(45, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(46, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(47, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(48, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(49, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.Confirm").replace("&", "§"), XMaterial.GREEN_STAINED_GLASS_PANE.parseMaterial()));
		
		inv.setItem(50, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(52, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		inv.setItem(53, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Delete.No").replace("&", "§"), XMaterial.RED_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	// >>>>>>>>>>>>>>>>>>>>>>> Modify settings of a world
	// >> Method gui
	public static void ModifyWorldMainPage(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = HawnMenu.create(54, "§cWorld Manager - Change World", HawnMenu.Type.WORLD_SETTINGS);
		
		List<String> lore = new ArrayList<>();
		
		if (Bukkit.getWorld(name).hasStorm() && Bukkit.getWorld(name).isThundering()) {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Actual-Weather").replace("&", "§") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replace("&", "§"));
			inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Weather").replace("&", "§"),  (ArrayList<String>) lore, XMaterial.YELLOW_WOOL.parseMaterial()));
		} else if (Bukkit.getWorld(name).hasStorm()) {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Actual-Weather").replace("&", "§") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replace("&", "§"));
			inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Weather").replace("&", "§"),  (ArrayList<String>) lore, XMaterial.BLUE_WOOL.parseMaterial()));
		} else {
			lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Actual-Weather").replace("&", "§") + " §e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replace("&", "§"));
			inv.setItem(12, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Weather").replace("&", "§"),  (ArrayList<String>) lore, XMaterial.WHITE_WOOL.parseMaterial()));
		}
		
		lore.clear();
		
		inv.setItem(14, createGuiItem("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.World-Time").replace("&", "§"),  (ArrayList<String>) lore, XMaterial.CLOCK.parseMaterial()));
		
		if (Bukkit.getWorld(name).getDifficulty() == Difficulty.EASY) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replace("&", "§"));
		} else if (Bukkit.getWorld(name).getDifficulty() == Difficulty.HARD) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replace("&", "§"));
		} else if (Bukkit.getWorld(name).getDifficulty() == Difficulty.NORMAL) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replace("&", "§"));
		} else if (Bukkit.getWorld(name).getDifficulty() == Difficulty.PEACEFUL) {
			lore.add("§e" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replace("&", "§"));
		} 
		
		inv.setItem(22, createGuiItem("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty-Two").replace("&", "§"),  (ArrayList<String>) lore, XMaterial.DIAMOND_SWORD.parseMaterial()));

		inv.setItem(4, infoItem(Bukkit.getWorld(name)));
		inv.setItem(31, defaultWorldItem(name));
		
		inv.setItem(36, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(37, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(38, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(39, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        // Not the main world, its nether, its end nor the default world: the server or Hawn needs them
        if (!WorldDeletion.isProtected(name) && !DefaultWorld.is(name)) {
        	ArrayList<String> unloadLore = new ArrayList<>();
        	unloadLore.add(text("Gui.Other.Unload.Lore", "&7Until the next restart: the players in it go to the default world"));
        	inv.setItem(40, createGuiItem(text("Gui.Other.Unload.Name", "&cUnload the world"), unloadLore, XMaterial.HOPPER.parseMaterial()));
        } else {
        	inv.setItem(40, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        }
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
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e" + name);
                
        inv.setItem(51, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Main-Menu").replace("&", "§"), (ArrayList<String>) lore, XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	public static void ModifyWorldTime(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = HawnMenu.create(54, "§cWorld Manager - Ch. World - Time", HawnMenu.Type.WORLD_TIME);
		
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
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replace("&", "§"), lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Menu").replace("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	public static void ModifyWorldWeather(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = HawnMenu.create(54, "§cWorld Manager - Ch. World - Weather", HawnMenu.Type.WORLD_WEATHER);
					
		inv.setItem(11, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Sun").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
			
		inv.setItem(13, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Rain").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
			
		inv.setItem(15, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.ChangeWorld.Weather.Storm").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
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
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replace("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Menu").replace("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
        inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
		
		p.openInventory(inv);
	}
	
	public static void ModifyWorldDifficulty(Player p, String name) {
		
		/*
		 * Basics
		 */
		Inventory inv = HawnMenu.create(54, "§cWorld Manager - Ch. World - Dif.", HawnMenu.Type.WORLD_DIFFICULTY);

		inv.setItem(10, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Peaceful").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));

		inv.setItem(12, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Easy").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
		inv.setItem(14, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Normal").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
		inv.setItem(16, createGuiItemWL("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Difficulty.Hard").replace("&", "§"), XMaterial.ENDER_PEARL.parseMaterial()));
		
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
        
        lore.add("§7" + WorldManagerPanelConfig.getConfig().getString("Gui.Other.Name").replace("&", "§") + " §e" + name);
        
        inv.setItem(47, createGuiItem(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Done").replace("&", "§"), (ArrayList<String>) lore, XMaterial.OAK_SIGN.parseMaterial()));
        
        inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
        
        inv.setItem(51, createGuiItemWL(WorldManagerPanelConfig.getConfig().getString("Gui.Other.Back.Menu").replace("&", "§"), XMaterial.BARRIER.parseMaterial()));
        	
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
	
	// The generator item of the creation page: "§c<generator>" then the help, or "default" then the help
	public static ItemStack generatorItem(String generator) {
		ArrayList<String> lore = new ArrayList<>();
		if (generator.equals("NOGENERATOR")) {
			lore.add(text("Gui.Other.Generator-Default", "&7Default"));
		} else if (generator.equals("hvg")) {
			lore.add("§chvg §8(" + text("Gui.Other.Generator-Page.Void-Generator", "&7Void-Generator") + "§8)");
		} else {
			lore.add("§c" + generator);
		}
		for (String help : WorldManagerPanelConfig.getConfig().getStringList("Gui.Other.Generator-Help")) {
			lore.add(MessageUtils.colourTheStuff(help));
		}
		return createGuiItem(text("Gui.Other.Generator", "&7Generator"), lore, XMaterial.CRAFTING_TABLE.parseMaterial());
	}

	public static String generatorOf(ItemStack item) {
		try {
			String first = item.getItemMeta().getLore().get(0);
			return first.startsWith("§c") ? first.substring(2).split(" ")[0] : "NOGENERATOR";
		} catch (Exception e) {
			return "NOGENERATOR";
		}
	}

	// What is useful to know about a world, in its menu
	private static ItemStack infoItem(World w) {
		ArrayList<String> lore = new ArrayList<>();
		if (w != null) {
			String type = ConfigWorldGeneral.getConfig().getString("World-List." + w.getName() + ".Type", "");
			String generator = ConfigWorldGeneral.getConfig().getString("World-List." + w.getName() + ".Generator",
					w.getGenerator() == null ? "" : w.getGenerator().getClass().getSimpleName());
			Location spawn = w.getSpawnLocation();
			long time = w.getTime();

			lore.add(text("Gui.Other.Info.Players", "&7Players:") + " §e" + w.getPlayers().size());
			lore.add(text("Gui.Other.Info.Environment", "&7Environment:") + " §e" + w.getEnvironment().name().toLowerCase() + (type.isEmpty() ? "" : " §7(" + type + ")"));
			lore.add(text("Gui.Other.Info.Generator", "&7Generator:") + " §e" + (generator.isEmpty() ? "-" : generator));
			lore.add(text("Gui.Other.Info.Chunks", "&7Loaded chunks:") + " §e" + w.getLoadedChunks().length);
			lore.add(text("Gui.Other.Info.Entities", "&7Entities:") + " §e" + w.getEntities().size());
			lore.add(text("Gui.Other.Info.Spawn", "&7Spawn:") + " §e" + spawn.getBlockX() + " " + spawn.getBlockY() + " " + spawn.getBlockZ());
			lore.add(text("Gui.Other.Info.Time", "&7Time:") + " §e" + String.format("%02d:%02d", (time / 1000 + 6) % 24, time % 1000 * 60 / 1000) + " §7(" + time + ")");
			lore.add(text("Gui.Other.Info.PvP", "&7PvP:") + " §e" + w.getPVP());
			lore.add(text("Gui.Other.Info.Seed", "&7Seed:") + " §e" + w.getSeed());
		}
		return createGuiItem(text("Gui.Other.Info.Title", "&eInformation"), lore, XMaterial.BOOK.parseMaterial());
	}

	private static String text(String path, String def) {
		return MessageUtils.colourTheStuff(WorldManagerPanelConfig.getConfig().getString(path, def));
	}

	// The default world item of the page of a world: what it is, or how to make this world the default one
	private static ItemStack defaultWorldItem(String world) {
		ArrayList<String> lore = new ArrayList<>();
		String current = DefaultWorld.get();
		List<String> lines = WorldManagerPanelConfig.getConfig().getStringList(DefaultWorld.is(world) ? "Gui.Other.Default-World.Current" : "Gui.Other.Default-World.Change");
		for (String line : lines) {
			lore.add(MessageUtils.colourTheStuff(line.replace("%arg1%", current)));
		}
		return createGuiItem(text("Gui.Other.Default-World.Title", "&6Default world"), lore, DefaultWorld.is(world) ? XMaterial.BEACON.parseMaterial() : XMaterial.NETHER_STAR.parseMaterial());
	}

	// A sound for each click: a choice, a change made, a refusal
	private static void click(Player p) {
		sound(p, "UI_BUTTON_CLICK");
	}

	private static void done(Player p) {
		sound(p, "ENTITY_EXPERIENCE_ORB_PICKUP");
	}

	private static void deny(Player p) {
		sound(p, "ENTITY_VILLAGER_NO");
	}

	private static void sound(Player p, String name) {
		try {
			p.playSound(p.getLocation(), fr.dianox.hawn.utility.XParse.sound(name, "GuiSystem"), 0.6f, 1f);
		} catch (Exception ignored) {}
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
	
}
