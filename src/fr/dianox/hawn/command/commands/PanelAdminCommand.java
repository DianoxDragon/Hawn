package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.commands.AdminPanelCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.AdminPanelConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PanelAdminCommand extends BukkitCommand {

    public PanelAdminCommand(String name) {
        /*
         * Main class to register the essential information of the command
         */
        super(name);
        this.description = "Manage the server for admins.";
        this.usageMessage = "/<command>";
    }

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
                for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
                    MessageUtils.ConsoleMessages(msg);
                }
            }
            return true;
        }

        Player p = (Player) sender;
        int numberitemstoeleimate = 0;
        List<String> lore = new ArrayList<String>();
        
        if (!p.hasPermission("hawn.adminpanel")) {
            MessageUtils.MessageNoPermission(p, "hawn.adminpanel");
            return true;
        }

        List<String> whitelistuse = AdminPanelCommandConfig.getConfig().getStringList("General-Options.List-Of-People-Can-Use-The-Panel");
		
		if (!whitelistuse.contains(p.getName())) {
			
			for (String msg: AdminPanelConfig.getConfig().getStringList("Error.Not-listed")) {
				ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			}
			
			return true;
		}
        
        if (args.length == 0) {
            Inventory inv = Bukkit.createInventory(null, 54, "§cAP");
            
            inv.setItem(0, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(1, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(2, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(3, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(4, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(5, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(6, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(7, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(8, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(9, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            
            inv.setItem(10, createGuiItemWL(AdminPanelConfig.getConfig().getString("Special.Item.Hawn-Main-Menu-Configuration.Name").replaceAll("&", "§"), XMaterial.CHEST.parseMaterial()));
            
            inv.setItem(11, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            
            inv.setItem(12, createGuiItemWL(AdminPanelConfig.getConfig().getString("Special.Item.Shutdown.Name").replaceAll("&", "§"), XMaterial.REDSTONE.parseMaterial()));
            inv.setItem(13, createGuiItemWL(AdminPanelConfig.getConfig().getString("Special.Item.Reload.Name").replaceAll("&", "§"), XMaterial.NETHER_STAR.parseMaterial()));
            inv.setItem(14, createGuiItemWL(AdminPanelConfig.getConfig().getString("Special.Item.Save-Players.Name").replaceAll("&", "§"), XMaterial.PLAYER_HEAD.parseMaterial()));

            inv.setItem(15, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            
            inv.setItem(16, createGuiItemWL(AdminPanelConfig.getConfig().getString("Special.Item.Reload-Hawn.Name").replaceAll("&", "§"), XMaterial.EMERALD.parseMaterial()));
            
            inv.setItem(17, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(18, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(19, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(20, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(21, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(22, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(23, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(24, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(25, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(26, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(27, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(28, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            
            inv.setItem(29, createGuiItemWL("§bPlayer list", XMaterial.ZOMBIE_HEAD.parseMaterial()));
            
            inv.setItem(30, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(31, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(32, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            
            inv.setItem(33, createGuiItemWL("§aWorld System", XMaterial.GRASS_BLOCK.parseMaterial()));
            
            inv.setItem(34, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(35, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
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
            inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            inv.setItem(53, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
            
            p.openInventory(inv);
        } else if (args.length == 1) {
        	if (args[0].equalsIgnoreCase("edithawnmainmenu")) {
        		Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Hawn edit menu");

                // Folders
                lore.clear();
            	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.Folder.Lore")) {
            		msg = MessageUtils.colourTheStuff(msg);
                    lore.add(msg);
                }
            	
                inv.setItem(0, createGuiItem("§cCommands", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));
                inv.setItem(1, createGuiItem("§cCosmetics-Fun", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));
                inv.setItem(2, createGuiItem("§cCustomJoinItem", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));
                inv.setItem(3, createGuiItem("§cEvents", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));
                inv.setItem(4, createGuiItem("§cMessages", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));
                inv.setItem(5, createGuiItem("§cScoreboard", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));
                inv.setItem(6, createGuiItem("§cStockageInfo", new ArrayList < String > (Arrays.asList(" ", "§cAvailable soon")), XMaterial.CHEST.parseMaterial()));
                inv.setItem(7, createGuiItem("§cTablist", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));

                int number_place = 8;
                File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath());
                File[] listOfFiles = folder.listFiles();

                lore.clear();
            	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
            		msg = MessageUtils.colourTheStuff(msg);
                    lore.add(msg);
                }
                
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        String filename = listOfFiles[i].getName().replace(".yml", "");
                        inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                        number_place++;
                    }
                }
                
                lore.clear();
            	for (String msg: AdminPanelConfig.getConfig().getStringList("Special.Item.Notice.Lore")) {
            		msg = MessageUtils.colourTheStuff(msg);
                    lore.add(msg);
                }
            	
            	inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Special.Item.Notice.Name").replaceAll("&", "§"), (ArrayList<String>) lore, Material.MAP));
            	
            	inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                
                inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
            	
            	p.openInventory(inv);
        	}
        } else if (args.length >= 2) {
            if (args[0].equalsIgnoreCase("edit")) {
                if (args[1].equalsIgnoreCase("file")) {
                    String filename = "";

                    if (Main.getInstance().getConfigManager().configfile.containsKey(args[2])) {
                        filename = Main.getInstance().getConfigManager().configfile.get(args[2]);
                    } else {
                        filename = args[2];
                    }

                    Main.getInstance().getConfigManager().configfileinuse.put(p, filename);

                    File f = new File(Main.getInstance().getDataFolder(), filename);

                    if (!f.exists()) {
                        return true;
                    }

                    YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);

                    Iterator < ? > iterator = null;

                    String invname = Main.getInstance().getConfigManager().configfilereverse.get(Main.getInstance().getDataFolder() + "/" + filename);
                    try {
                    	invname = invname.replaceAll(Main.getInstance().getDataFolder() + "/", "");
                    } catch (Exception e) {
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Error.Edit-Files")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    	
                    	return true;
                    }
                    Integer pagenumber = 1;
                    Boolean cannextpage = false;
                    
                    if (invname.contains("Commands/")) {
                        invname = invname.replace("Commands/", "C-");
                    } else if (invname.contains("Cosmetics-Fun/")) {
                        invname = invname.replace("Cosmetics-Fun/", "CF-");
                    } else if (invname.contains("Events/")) {
                        invname = invname.replace("Events/", "E-");
                    } else if (invname.contains("Tablist/")) {
                        invname = invname.replace("Tablist/", "T-");
                    } else if (invname.contains("Cosmetics-Fun/Utility/")) {
                        invname = invname.replace("Cosmetics-Fun/Utility/", "CFU-");
                    }

                    if (args.length != 4) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[3]);
                    }

                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - File " + invname + pagenumber);
                    Integer number_place = 0;

                    Integer maximumitem = 44;
                    Integer numberitems = 0;
                    Integer loopaeleminer = 0;

                    if (invname.contains("CustomCommand") && pagenumber == 1) {
                        if (cfg.getBoolean("commands-general.enable")) {   	
                        	lore.clear();
                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Boolean.True")) {
                        		msg = MessageUtils.colourTheStuff(msg);
                                lore.add(msg);
                            }
                        	
                            inv.setItem(0, createGuiItemColor("§bcommands-general.enable", (ArrayList<String>) lore, "GREEN_WOOL"));
                        } else {
                        	lore.clear();
                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Boolean.False")) {
                        		msg = MessageUtils.colourTheStuff(msg);
                                lore.add(msg);
                            }
                        	
                            inv.setItem(0, createGuiItemColor("§bcommands-general.enable", (ArrayList<String>) lore, "RED_WOOL"));
                        }

                        numberitems = 9;
                        number_place = 9;
                    }

                    if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45;
                        
                        loopaeleminer = 45 * (pagenumber - 1);

                        if (invname.contains("CustomCommand")) {
                            maximumitem = 36 * pagenumber;
                            loopaeleminer = 36 * (pagenumber - 1);
                        }
                    }

                    if (invname.contains("CustomCommand")) {
                        iterator = cfg.getConfigurationSection("commands").getKeys(false).iterator();
                    } else if (invname.contains("warplist") || invname.contains("spawn")) {
                        if (!cfg.isSet("Coordinated")) {
                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Error.Edit-Empty")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                            return true;
                        }

                        iterator = cfg.getConfigurationSection("Coordinated").getKeys(false).iterator();
                    } else {
                        iterator = cfg.getKeys(true).iterator();
                    }


                    while (iterator.hasNext()) {
                        String string = (String) iterator.next();

                        if (pagenumber == 1) {
                            if (numberitems <= maximumitem) {
                                if (invname.contains("CustomCommand")) {
                                    String getperm = cfg.getString("commands." + string + ".permission.message");
                                    String getpermyesorno = String.valueOf(cfg.getBoolean("commands." + string + ".permission.enable"));
                                    String nopermmsg = String.valueOf(cfg.getBoolean("commands." + string + ".no-permission-message-enable"));
                                    String command = cfg.getString("commands." + string + ".command");

                                    if (cfg.getBoolean("commands." + string + ".enable")) {
                                    	lore.clear();
                                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.CustomCommand.True")) {
                                    		msg = MessageUtils.colourTheStuff(msg);
                                    		msg = msg.replaceAll("%ap_getperm%", getperm);
                                    		msg = msg.replaceAll("%ap_getpermyesorno%", getpermyesorno);
                                    		msg = msg.replaceAll("%ap_nopermmsg%", nopermmsg);
                                    		msg = msg.replaceAll("%ap_command%", command);
                                            lore.add(msg);
                                        }
                                    	
                                        inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "GREEN_WOOL"));
                                    } else {
                                    	lore.clear();
                                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.CustomCommand.False")) {
                                    		msg = MessageUtils.colourTheStuff(msg);
                                    		msg = msg.replaceAll("%ap_getperm%", getperm);
                                    		msg = msg.replaceAll("%ap_getpermyesorno%", getpermyesorno);
                                    		msg = msg.replaceAll("%ap_nopermmsg%", nopermmsg);
                                    		msg = msg.replaceAll("%ap_command%", command);
                                            lore.add(msg);
                                        }
                                    	
                                        inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "RED_WOOL"));
                                    }

                                    number_place++;
                                    numberitems++;
                                } else if (invname.contains("G-warplist")) {
                                    String getworld = cfg.getString("Coordinated." + string + ".World");
                                    String x = String.valueOf(cfg.getDouble("Coordinated." + string + ".X"));
                                    String y = String.valueOf(cfg.getDouble("Coordinated." + string + ".Y"));
                                    String z = String.valueOf(cfg.getDouble("Coordinated." + string + ".Z"));
                                    String yaw = String.valueOf(cfg.getInt("Coordinated." + string + ".Yaw"));
                                    String pitch = String.valueOf(cfg.getInt("Coordinated." + string + ".Pitch"));

                                    lore.clear();
                                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.WarpList.Lore")) {
                                		msg = MessageUtils.colourTheStuff(msg);
                                		msg = msg.replaceAll("%ap_x%", x);
                                		msg = msg.replaceAll("%ap_y%", y);
                                		msg = msg.replaceAll("%ap_z%", z);
                                		msg = msg.replaceAll("%ap_getworld%", getworld);
                                		msg = msg.replaceAll("%ap_yaw%", yaw);
                                		msg = msg.replaceAll("%ap_pitch%", pitch);
                                        lore.add(msg);
                                    }
                                    
                                    inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "ORANGE_WOOL"));

                                    number_place++;
                                } else if (invname.contains("G-spawn")) {
                                    String getworld = cfg.getString("Coordinated." + string + ".World");
                                    String x = String.valueOf(cfg.getDouble("Coordinated." + string + ".X"));
                                    String y = String.valueOf(cfg.getDouble("Coordinated." + string + ".Y"));
                                    String z = String.valueOf(cfg.getDouble("Coordinated." + string + ".Z"));
                                    String yaw = String.valueOf(cfg.getInt("Coordinated." + string + ".Yaw"));
                                    String pitch = String.valueOf(cfg.getInt("Coordinated." + string + ".Pitch"));
                                    
                                    lore.clear();
                                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.SpawnList.Lore")) {
                                		msg = MessageUtils.colourTheStuff(msg);
                                		msg = msg.replaceAll("%ap_x%", x);
                                		msg = msg.replaceAll("%ap_y%", y);
                                		msg = msg.replaceAll("%ap_z%", z);
                                		msg = msg.replaceAll("%ap_getworld%", getworld);
                                		msg = msg.replaceAll("%ap_yaw%", yaw);
                                		msg = msg.replaceAll("%ap_pitch%", pitch);
                                        lore.add(msg);
                                    }
                                    
                                    inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "ORANGE_WOOL"));

                                    number_place++;
                                } else {
                                    if (cfg.isBoolean(string)) {
                                        if (cfg.getBoolean(string)) {
                                        	lore.clear();
                                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Boolean.True")) {
                                        		msg = MessageUtils.colourTheStuff(msg);
                                                lore.add(msg);
                                            }

                                        	inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "GREEN_WOOL"));
                                            number_place++;
                                            numberitems++;
                                        } else {
                                        	lore.clear();
                                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Boolean.False")) {
                                        		msg = MessageUtils.colourTheStuff(msg);
                                                lore.add(msg);
                                            }

                                        	inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "RED_WOOL"));
                                            number_place++;
                                            numberitems++;
                                        }
                                    }
                                }
                            } else if (numberitems >= maximumitem) {
                                cannextpage = true;
                            }
                            
                            inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                        } else {
                            if (numberitemstoeleimate < loopaeleminer) {
                                if (cfg.isBoolean(string)) {
                                    numberitemstoeleimate++;
                                }
                            } else {
                                if (numberitems <= maximumitem) {
                                    if (invname.contains("CustomCommand")) {
                                        String getperm = cfg.getString("commands." + string + ".permission.message");
                                        String getpermyesorno = String.valueOf(cfg.getBoolean("commands." + string + ".permission.enable"));
                                        String nopermmsg = String.valueOf(cfg.getBoolean("commands." + string + ".no-permission-message-enable"));
                                        String command = cfg.getString("commands." + string + ".command");

                                        if (cfg.getBoolean("commands." + string + ".enable")) {
                                        	lore.clear();
                                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.CustomCommand.True")) {
                                        		msg = MessageUtils.colourTheStuff(msg);
                                        		msg = msg.replaceAll("%ap_getperm%", getperm);
                                        		msg = msg.replaceAll("%ap_getpermyesorno%", getpermyesorno);
                                        		msg = msg.replaceAll("%ap_nopermmsg%", nopermmsg);
                                        		msg = msg.replaceAll("%ap_command%", command);
                                                lore.add(msg);
                                            }
                                        	
                                            inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "GREEN_WOOL"));
                                        } else {
                                        	lore.clear();
                                        	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.CustomCommand.False")) {
                                        		msg = MessageUtils.colourTheStuff(msg);
                                        		msg = msg.replaceAll("%ap_getperm%", getperm);
                                        		msg = msg.replaceAll("%ap_getpermyesorno%", getpermyesorno);
                                        		msg = msg.replaceAll("%ap_nopermmsg%", nopermmsg);
                                        		msg = msg.replaceAll("%ap_command%", command);
                                                lore.add(msg);
                                            }
                                        	
                                            inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "RED_WOOL"));
                                        }

                                        number_place++;
                                        numberitems++;
                                    } else if (invname.contains("G-warplist")) {
                                        String getworld = cfg.getString("Coordinated." + string + ".World");
                                        String x = String.valueOf(cfg.getDouble("Coordinated." + string + ".X"));
                                        String y = String.valueOf(cfg.getDouble("Coordinated." + string + ".Y"));
                                        String z = String.valueOf(cfg.getDouble("Coordinated." + string + ".Z"));
                                        String yaw = String.valueOf(cfg.getInt("Coordinated." + string + ".Yaw"));
                                        String pitch = String.valueOf(cfg.getInt("Coordinated." + string + ".Pitch"));

                                        lore.clear();
                                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.WarpList.Lore")) {
                                    		msg = MessageUtils.colourTheStuff(msg);
                                    		msg = msg.replaceAll("%ap_x%", x);
                                    		msg = msg.replaceAll("%ap_y%", y);
                                    		msg = msg.replaceAll("%ap_z%", z);
                                    		msg = msg.replaceAll("%ap_getworld%", getworld);
                                    		msg = msg.replaceAll("%ap_yaw%", yaw);
                                    		msg = msg.replaceAll("%ap_pitch%", pitch);
                                            lore.add(msg);
                                        }
                                        
                                        inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "ORANGE_WOOL"));

                                        number_place++;
                                    } else if (invname.contains("G-spawn")) {
                                        String getworld = cfg.getString("Coordinated." + string + ".World");
                                        String x = String.valueOf(cfg.getDouble("Coordinated." + string + ".X"));
                                        String y = String.valueOf(cfg.getDouble("Coordinated." + string + ".Y"));
                                        String z = String.valueOf(cfg.getDouble("Coordinated." + string + ".Z"));
                                        String yaw = String.valueOf(cfg.getInt("Coordinated." + string + ".Yaw"));
                                        String pitch = String.valueOf(cfg.getInt("Coordinated." + string + ".Pitch"));

                                        lore.clear();
                                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Special.SpawnList.Lore")) {
                                    		msg = MessageUtils.colourTheStuff(msg);
                                    		msg = msg.replaceAll("%ap_x%", x);
                                    		msg = msg.replaceAll("%ap_y%", y);
                                    		msg = msg.replaceAll("%ap_z%", z);
                                    		msg = msg.replaceAll("%ap_getworld%", getworld);
                                    		msg = msg.replaceAll("%ap_yaw%", yaw);
                                    		msg = msg.replaceAll("%ap_pitch%", pitch);
                                            lore.add(msg);
                                        }
                                        
                                        inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "ORANGE_WOOL"));

                                        number_place++;
                                    } else {
                                        if (cfg.isBoolean(string)) {
                                            if (cfg.getBoolean(string)) {
                                            	lore.clear();
                                            	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Boolean.True")) {
                                            		msg = MessageUtils.colourTheStuff(msg);
                                                    lore.add(msg);
                                                }

                                            	inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "GREEN_WOOL"));
                                                number_place++;
                                                numberitems++;
                                            } else {
                                            	lore.clear();
                                            	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Boolean.False")) {
                                            		msg = MessageUtils.colourTheStuff(msg);
                                                    lore.add(msg);
                                                }

                                            	inv.setItem(number_place, createGuiItemColor("§b" + string, (ArrayList<String>) lore, "RED_WOOL"));
                                                number_place++;
                                                numberitems++;
                                            }
                                        }
                                    }
                                } else if (numberitems >= maximumitem) {
                                    cannextpage = true;
                                }

                                lore.clear();
                            	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
                            		msg = MessageUtils.colourTheStuff(msg);
                                    lore.add(msg);
                                }
                                
                                inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                            }
                        }
                    }

                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    
                	inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));             
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));

                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Folder-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
                    
                    p.openInventory(inv);
                }
            } else if (args[0].equalsIgnoreCase("folder")) {
                if (args[1].equalsIgnoreCase("Commands")) {
                	
                	Integer pagenumber = 1;
                	Boolean cannextpage = false;
                	Integer number_place = 0;
                	Integer maximumitem = 44;
                	Integer numberitems = 0;
                	Integer loopaeleminer = 0;
                	
                	if (args.length != 3) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[2]);
                    }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder commands " + pagenumber);
                    
                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Commands/");
                    File[] listOfFiles = folder.listFiles();

                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                    
                	Boolean stop = false;
                	
                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			
	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
		                    			
		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }
	                    			
	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}
	                    			
	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));

                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));

                    p.openInventory(inv);
                } else if (args[1].equalsIgnoreCase("Cosmetics-Fun")) {
                	
                	Integer pagenumber = 1;
                	Boolean cannextpage = false;
                	Integer number_place = 0;
                	Integer maximumitem = 44;
                	Integer numberitems = 0;
                	Integer loopaeleminer = 0;
                	
                	if (args.length != 3) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[2]);
                    }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Cosmetics-Fun " + pagenumber);

                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Cosmetics-Fun/");
                    File[] listOfFiles = folder.listFiles();

                    // Folders
                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.Folder.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	inv.setItem(0, createGuiItem("§cUtility", (ArrayList<String>) lore, XMaterial.CHEST.parseMaterial()));
                	
                    // Files
                	lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                	
                	Boolean stop = false;
                    
                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			
	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
		                    			
		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }
	                    			
	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}
	                    			
	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }

                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));

                    p.openInventory(inv);
                } else if (args[1].equalsIgnoreCase("CF-Utility")) {
                	
                	Integer pagenumber = 1;
                	Boolean cannextpage = false;
                	Integer number_place = 0;
                	Integer maximumitem = 44;
                	Integer numberitems = 0;
                	Integer loopaeleminer = 0;
                	
                	if (args.length != 3) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[2]);
                    }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder CF-Utility " + pagenumber);

                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Cosmetics-Fun/Utility/");
                    File[] listOfFiles = folder.listFiles();

                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                    
                	Boolean stop = false;
                	
                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			
	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
		                    			
		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }
	                    			
	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}
	                    			
	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }

                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
                    
                    p.openInventory(inv);
                    p.updateInventory();
                } else if (args[1].equalsIgnoreCase("CustomJoinItem")) {
                	
                	Integer pagenumber = 1;
                	Boolean cannextpage = false;
                	Integer number_place = 0;
                	Integer maximumitem = 44;
                	Integer numberitems = 0;
                	Integer loopaeleminer = 0;
                	
                	if (args.length != 3) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[2]);
                    }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder CustomJoinItem " + pagenumber);

                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/CustomJoinItem/");
                    File[] listOfFiles = folder.listFiles();

                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                    
                	Boolean stop = false;
                	
                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			
	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
		                    			
		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }
	                    			
	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}
	                    			
	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }

                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
                    
                    p.openInventory(inv);
                    p.updateInventory();
                } else if (args[1].equalsIgnoreCase("Events")) {
                	
                	Integer pagenumber = 1;
                	Boolean cannextpage = false;
                	Integer number_place = 0;
                	Integer maximumitem = 44;
                	Integer numberitems = 0;
                	Integer loopaeleminer = 0;
                	
                	if (args.length != 3) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[2]);
                    }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Events " + pagenumber);

                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Events/");
                    File[] listOfFiles = folder.listFiles();

                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                    
                	Boolean stop = false;
                	
                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			
	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
		                    			
		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }
	                    			
	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}
	                    			
	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }

                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));

                    p.openInventory(inv);
                    p.updateInventory();
                } else if (args[1].equalsIgnoreCase("Messages")) {
                    Integer pagenumber = 1;
                	Boolean cannextpage = false;
                	Integer number_place = 0;
                	Integer maximumitem = 44;
                	Integer numberitems = 0;
                	Integer loopaeleminer = 0;
                	
                	if (args.length != 3) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[2]);
                    }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Messages " + pagenumber);

                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Messages/" + Main.LanguageType + "/");
                    File[] listOfFiles = folder.listFiles();

                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                	
                	Boolean stop = false;
                    
                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			
	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
		                    			
		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }
	                    			
	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}
	                    			
	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }

                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));
                    
                    p.openInventory(inv);
                    p.updateInventory();
                } else if (args[1].equalsIgnoreCase("Scoreboard")) {
                	
                	Integer pagenumber = 1;
                	Boolean cannextpage = false;
                	Integer number_place = 0;
                	Integer maximumitem = 44;
                	Integer numberitems = 0;
                	Integer loopaeleminer = 0;
                	
                	if (args.length != 3) {
                        pagenumber = 1;
                    } else {
                        pagenumber = Integer.parseInt(args[2]);
                    }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Scoreboard " + pagenumber);

                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Scoreboard/");
                    File[] listOfFiles = folder.listFiles();

                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                	
                	Boolean stop = false;
                    
                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			
	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
		                    			
		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }
	                    			
	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}
	                    			
	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }

                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));

                    p.openInventory(inv);
                } else if (args[1].equalsIgnoreCase("Tablist")) {
                	
                	int pagenumber = 1;
                	boolean cannextpage = false;
                	int number_place = 0;
                	int maximumitem = 44;
                	int numberitems = 0;
                	int loopaeleminer = 0;

	                if (args.length == 3) {
		                pagenumber = Integer.parseInt(args[2]);
	                } else {
		                pagenumber = 1;
	                }
                	
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Tablist " + pagenumber);

                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Tablist/");
                    File[] listOfFiles = folder.listFiles();

                    lore.clear();
                	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Items.Lore")) {
                		msg = MessageUtils.colourTheStuff(msg);
                        lore.add(msg);
                    }
                	
                	if (pagenumber != 1) {
                        numberitems = 0;

                        maximumitem = 45 * pagenumber;
                        loopaeleminer = 45 * (pagenumber - 1);
                    }
                    
                	boolean stop = false;

                    for (int i = 0; i < listOfFiles.length; i++) {
                    	if (listOfFiles[number_place].isFile()) {
	                    	if (pagenumber == 1) {
	                    		if (numberitems <= maximumitem) {
	                    			String filename = listOfFiles[i].getName().replace(".yml", "");
	                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));

	                    			number_place++;
	                    			numberitems++;
	                    		} else if (numberitems >= maximumitem) {
	                        		cannextpage = true;
	                        	}
	                    	} else {
	                    		if (numberitemstoeleimate < loopaeleminer) {
	                    			numberitemstoeleimate++;
	                    		} else {
	                    			if (numberitems <= maximumitem) {
	                    				String filename = listOfFiles[i].getName().replace(".yml", "");
		                    			inv.setItem(number_place, createGuiItem("§b" + filename, (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));

		                    			number_place++;
		                    			numberitems++;
	                    			} else if (numberitems >= maximumitem) {
	                                    cannextpage = true;
	                                }

	                    			lore.clear();
	                    			for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Previous.Lore")) {
	                    				msg = MessageUtils.colourTheStuff(msg);
	                    				lore.add(msg);
	                    			}

	                    			inv.setItem(45, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Previous.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
	                    			stop = true;
	                    		}
	                    	}
                    	}
                    }
                    
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    if (cannextpage) {
                    	lore.clear();
                    	for (String msg: AdminPanelConfig.getConfig().getStringList("Edit.File.Next.Lore")) {
                    		msg = MessageUtils.colourTheStuff(msg);
                            lore.add(msg);
                        }
                    	
                        inv.setItem(47, createGuiItem(AdminPanelConfig.getConfig().getString("Edit.File.Next.Name").replaceAll("&", "§"), (ArrayList<String>) lore, XMaterial.PAPER.parseMaterial()));
                    } else {
                    	inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }

                    if (!stop) {
                    	inv.setItem(45, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    }
                    inv.setItem(46, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(47, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(48, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(49, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(50, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(51, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(52, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(53, createGuiItemWL(AdminPanelConfig.getConfig().getString("Edit.File.Back-Menu.Name").replaceAll("&", "§"), XMaterial.BARRIER.parseMaterial()));

                    p.openInventory(inv);
                }
            }
        }

        return false;
    }

    public ItemStack createGuiItem(String name, ArrayList < String > desc, Material mat) {
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        iMeta.setLore(desc);
        i.setItemMeta(iMeta);
        return i;
    }

    @SuppressWarnings("deprecation")
    public ItemStack createGuiItemColor(String name, ArrayList < String > desc, String mat) {

        mat = mat.toUpperCase();
        ItemStack i;
        
        if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 113) {
            i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1);
            ItemMeta iMeta = i.getItemMeta();
            iMeta.setDisplayName(name);
            iMeta.setLore(desc);
            i.setItemMeta(iMeta);
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

            } else {
                i = new ItemStack(XMaterial.getMat(mat, "Error from the panel admin"), 1);
                ItemMeta iMeta = i.getItemMeta();
                iMeta.setDisplayName(name);
                iMeta.setLore(desc);
                i.setItemMeta(iMeta);
            }
        }
	    return i;
    }

    public ItemStack createGuiItemWL(String name, Material mat) {
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        i.setItemMeta(iMeta);
        return i;
    }

}