package fr.Dianox.Hawn.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;

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
    public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
        	if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
            return true;
        }

        Player p = (Player) sender;
        int numberitemstoeleimate = 0;
        
        if (!p.hasPermission("hawn.adminpanel")) {
			MessageUtils.MessageNoPermission(p, "hawn.adminpanel");
			return true;
		}
        
                if (args.length == 0) {
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP");
                    
                    // Folders
                    inv.setItem(0, createGuiItem("§cCommands", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(1, createGuiItem("§cCosmetics-Fun", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(2, createGuiItem("§cCustomJoinItem", new ArrayList < String > (Arrays.asList(" ", "§cAvailable soon")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(3, createGuiItem("§cEvents", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(4, createGuiItem("§cMessages", new ArrayList < String > (Arrays.asList(" ", "§cAvailable soon")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(5, createGuiItem("§cScoreboard", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(6, createGuiItem("§cStockageInfo", new ArrayList < String > (Arrays.asList(" ", "§cAvailable soon")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(7, createGuiItem("§cTablist", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));

                    int number_place = 8;
                    File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath());
                    File[] listOfFiles = folder.listFiles();

                    for (int i = 0; i < listOfFiles.length; i++) {
                        if (listOfFiles[i].isFile()) {
                            String filename = listOfFiles[i].getName().replace(".yml", "");
                            inv.setItem(number_place, createGuiItem("§b" + filename, new ArrayList < String > (Arrays.asList(" ", "§eClick to edit this file")), XMaterial.PAPER.parseMaterial()));
                            number_place++;
                        }
                    }

                    inv.setItem(18, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(19, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(20, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(21, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(22, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(23, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(24, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(25, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(26, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    
                    inv.setItem(27, createGuiItemWL("§cShutdown the server", XMaterial.REDSTONE_BLOCK.parseMaterial()));
                    inv.setItem(28, createGuiItemWL("§eReload The server", XMaterial.NETHER_STAR.parseMaterial()));
                    inv.setItem(29, createGuiItemWL("§aSave players", XMaterial.PLAYER_HEAD.parseMaterial()));
                    
                    inv.setItem(36, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(37, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(38, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(39, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(40, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(41, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(42, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(43, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(44, createGuiItemWL(" ", XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial()));
                    inv.setItem(45, createGuiItem("§aNotice", new ArrayList < String > (Arrays.asList(" ", "§eActually, I can't put all the config file here.", "§eIf you want to edit everything that is missing", "§eplease do it manually")), Material.MAP));
                    
                    p.openInventory(inv);
                } else if (args.length >= 3) {
                    if (args[0].equalsIgnoreCase("edit")) {
                        if (args[1].equalsIgnoreCase("file")) {      	
                            	String filename = "";
                            	
                            	if (Main.configfile.containsKey(args[2])) {
                            		filename = Main.configfile.get(args[2]);
                            	} else {
                            		filename = args[2];
                            	}
                            	
                            	Main.configfileinuse.put(p, filename);

                            	File f = new File(Main.getInstance().getDataFolder(), filename);
                            	
                            	if (!f.exists()) {
                            		return true;
                            	} 
                            	
                            	YamlConfiguration cfg = YamlConfiguration.loadConfiguration(f);
                            	
                            	Iterator < ? > iterator = null;
                            	
                            	String invname = Main.configfilereverse.get(Main.getInstance().getDataFolder() + "/" + filename);
                            	invname = invname.replaceAll(Main.getInstance().getDataFolder() + "/", "");
                            	Integer pagenumber = 1;
                            	Boolean cannextpage = false;
                            	
                            	if (invname.contains("Commands/")) {
                            		invname = invname.replace("Commands/", "C-");
                            	} else if (invname.contains("Cosmetics-Fun/")) {
                            		invname = invname.replace("Cosmetics-Fun/", "CF-");
                            	} else if (invname.contains("Events/")) {
                            		invname = invname.replace("Events/", "E-");
                            	}
                            	
                            	if (args.length != 4) {
                            		pagenumber = 1;
                            	} else {
                            		pagenumber = Integer.valueOf(args[3]);
                            	}
                            	                            	
                            	Inventory inv = Bukkit.createInventory(null, 54, "§cAP - File " + invname + pagenumber);
                            	Integer number_place = 0;
                            	                           
                                Integer maximumitem = 44;
                                Integer numberitems = 0;
                                Integer loopaeleminer = 0;
                                
                                if (invname.contains("CustomCommand") && pagenumber == 1) {
                                	if (cfg.getBoolean("commands-general.enable")) {
                                        inv.setItem(0, createGuiItemColor("§bcommands-general.enable", new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                    } else {
                                        inv.setItem(0, createGuiItemColor("§bcommands-general.enable", new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                    }
                                	
                                	numberitems = 9;
                                	number_place = 9;
                                }
                                                                
                                if (pagenumber != 1) {
	                                numberitems = 0;
	                   			 
	                                maximumitem = 45 * pagenumber;
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
                                        p.sendMessage("§cThe file is empty");
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
                                                     inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                         " ",
                                                         "§b► Command :§e " + command,
                                                         " ",
                                                         "§n§bPermission :",
                                                         "§b► §e" + getperm,
                                                         "§b► Enable ? §e" + getpermyesorno,
                                                         "§b► No permission message ? §e" + nopermmsg,
                                                         " ",
                                                         "§eClick to §cdisable§e the command"
                                                     )), "GREEN_WOOL"));
                                                 } else {
                                                     inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                         " ",
                                                         "§b► Command :§e " + command,
                                                         " ",
                                                         "§n§bPermission :",
                                                         "§b► §e" + getperm,
                                                         "§b► Enable ? §e" + getpermyesorno,
                                                         "§b► No permission message ? §e" + nopermmsg,
                                                         " ",
                                                         "§eClick to §aenable§e the command")), "RED_WOOL"));
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

                                                 inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                     " ",
                                                     "§bWorld : §e" + getworld,
                                                     "§bX : §e" + x + "§b, Y : §e" + y + "§b, Z : §e" + z,
                                                     "§bYaw : §e" + yaw,
                                                     "§bPitch : §e" + pitch,
                                                     " ",
                                                     "§6You can't edit warp list here"
                                                 )), "ORANGE_WOOL"));

                                                 number_place++;
                            				 } else if (invname.contains("G-spawn")) {
                            					 String getworld = cfg.getString("Coordinated." + string + ".World");
                                                 String x = String.valueOf(cfg.getDouble("Coordinated." + string + ".X"));
                                                 String y = String.valueOf(cfg.getDouble("Coordinated." + string + ".Y"));
                                                 String z = String.valueOf(cfg.getDouble("Coordinated." + string + ".Z"));
                                                 String yaw = String.valueOf(cfg.getInt("Coordinated." + string + ".Yaw"));
                                                 String pitch = String.valueOf(cfg.getInt("Coordinated." + string + ".Pitch"));

                                                 inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                     " ",
                                                     "§bWorld : §e" + getworld,
                                                     "§bX : §e" + x + "§b, Y : §e" + y + "§b, Z : §e" + z,
                                                     "§bYaw : §e" + yaw,
                                                     "§bPitch : §e" + pitch,
                                                     " ",
                                                     "§6You can't edit spawn list here"
                                                 )), "ORANGE_WOOL"));

                                                 number_place++;
                            				 } else {
                            					 if (cfg.isBoolean(string)) {
			                            			 if (cfg.getBoolean(string)) {
			                            				 inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
			                            				 number_place++;
			                            				 numberitems++;
			                            			 } else {
			                            				 inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
			                                             number_place++;
			                                             numberitems++;
			                            			 }
			                            		 }
                            				 }
                            			 } else if (numberitems >= maximumitem) {
	                            			 cannextpage = true;
	                            		 }
                            		 } else {
                                         if (numberitemstoeleimate < loopaeleminer) {
                                        	 if (OnJoinConfig.getConfig().isBoolean(string)) {
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
                                                         inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                             " ",
                                                             "§b► Command :§e " + command,
                                                             " ",
                                                             "§n§bPermission :",
                                                             "§b► §e" + getperm,
                                                             "§b► Enable ? §e" + getpermyesorno,
                                                             "§b► No permission message ? §e" + nopermmsg,
                                                             " ",
                                                             "§eClick to §cdisable§e the command"
                                                         )), "GREEN_WOOL"));
                                                     } else {
                                                         inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                             " ",
                                                             "§b► Command :§e " + command,
                                                             " ",
                                                             "§n§bPermission :",
                                                             "§b► §e" + getperm,
                                                             "§b► Enable ? §e" + getpermyesorno,
                                                             "§b► No permission message ? §e" + nopermmsg,
                                                             " ",
                                                             "§eClick to §aenable§e the command")), "RED_WOOL"));
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

                                                     inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                         " ",
                                                         "§bWorld : §e" + getworld,
                                                         "§bX : §e" + x + "§b, Y : §e" + y + "§b, Z : §e" + z,
                                                         "§bYaw : §e" + yaw,
                                                         "§bPitch : §e" + pitch,
                                                         " ",
                                                         "§6You can't edit warp list here"
                                                     )), "ORANGE_WOOL"));

                                                     number_place++;
                                        		 } else if (invname.contains("G-spawn")) {
                                					 String getworld = cfg.getString("Coordinated." + string + ".World");
                                                     String x = String.valueOf(cfg.getDouble("Coordinated." + string + ".X"));
                                                     String y = String.valueOf(cfg.getDouble("Coordinated." + string + ".Y"));
                                                     String z = String.valueOf(cfg.getDouble("Coordinated." + string + ".Z"));
                                                     String yaw = String.valueOf(cfg.getInt("Coordinated." + string + ".Yaw"));
                                                     String pitch = String.valueOf(cfg.getInt("Coordinated." + string + ".Pitch"));

                                                     inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(
                                                         " ",
                                                         "§bWorld : §e" + getworld,
                                                         "§bX : §e" + x + "§b, Y : §e" + y + "§b, Z : §e" + z,
                                                         "§bYaw : §e" + yaw,
                                                         "§bPitch : §e" + pitch,
                                                         " ",
                                                         "§6You can't edit spawn list here"
                                                     )), "ORANGE_WOOL"));

                                                     number_place++;
                                				 } else {
                                					 if (cfg.isBoolean(string)) {
    			                            			 if (cfg.getBoolean(string)) {
    			                            				 inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
    			                            				 number_place++;
    			                            				 numberitems++;
    			                            			 } else {
    			                            				 inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
    			                                             number_place++;
    			                                             numberitems++;
    			                            			 }
    			                            		 }
                                				 }
                                        	 } else if (numberitems >= maximumitem) {
		                            			 cannextpage = true;
		                            		 }
                                        	 
                                        	 inv.setItem(45, createGuiItem("§bPREVIOUS", new ArrayList < String > (Arrays.asList(" ", "§ePrevious")), XMaterial.PAPER.parseMaterial()));
                                         }
                            		 }
                            	 }
                            	 
                            	 if (cannextpage) {
                            		 inv.setItem(47, createGuiItem("§bNEXT", new ArrayList < String > (Arrays.asList(" ", "§eNext")), XMaterial.PAPER.parseMaterial()));
                            	 }
                            	 
                            	 inv.setItem(53, createGuiItemWL("§cBack to the folder menu", XMaterial.BARRIER.parseMaterial()));
                            	 
                            	 p.openInventory(inv);
                            }
                        } else if (args[1].equalsIgnoreCase("folder")) {
                            if (args[2].equalsIgnoreCase("Commands")) {
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder commands");

                                File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Commands/");
                                File[] listOfFiles = folder.listFiles();

                                for (int i = 0; i < listOfFiles.length; i++) {
                                    if (listOfFiles[i].isFile()) {
                                        String filename = listOfFiles[i].getName().replace(".yml", "");
                                        inv.setItem(i, createGuiItem("§b" + filename, new ArrayList < String > (Arrays.asList(" ", "§eClick to edit this file")), XMaterial.PAPER.parseMaterial()));
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);
                            } else if (args[2].equalsIgnoreCase("Cosmetics-Fun")) {
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Cosmetics-Fun");

                                File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Cosmetics-Fun/");
                                File[] listOfFiles = folder.listFiles();

                                for (int i = 0; i < listOfFiles.length; i++) {
                                    if (listOfFiles[i].isFile()) {
                                        String filename = listOfFiles[i].getName().replace(".yml", "");
                                        inv.setItem(i, createGuiItem("§b" + filename, new ArrayList < String > (Arrays.asList(" ", "§eClick to edit this file")), XMaterial.PAPER.parseMaterial()));
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);
                            } else if (args[2].equalsIgnoreCase("Events")) {
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Events");

                                File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Events/");
                                File[] listOfFiles = folder.listFiles();

                                for (int i = 0; i < listOfFiles.length; i++) {
                                    if (listOfFiles[i].isFile()) {
                                        String filename = listOfFiles[i].getName().replace(".yml", "");
                                        inv.setItem(i, createGuiItem("§b" + filename, new ArrayList < String > (Arrays.asList(" ", "§eClick to edit this file")), XMaterial.PAPER.parseMaterial()));
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);
                                p.updateInventory();
                            } else if (args[2].equalsIgnoreCase("Messages")) {
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Messages");

                                File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Messages/");
                                File[] listOfFiles = folder.listFiles();

                                for (int i = 0; i < listOfFiles.length; i++) {
                                    if (listOfFiles[i].isFile()) {
                                        String filename = listOfFiles[i].getName().replace(".yml", "");
                                        inv.setItem(i, createGuiItem("§b" + filename, new ArrayList < String > (Arrays.asList(" ", "§eClick to edit this file")), XMaterial.PAPER.parseMaterial()));
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);
                            } else if (args[2].equalsIgnoreCase("Scoreboard")) {
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Scoreboard");

                                File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Scoreboard/");
                                File[] listOfFiles = folder.listFiles();

                                for (int i = 0; i < listOfFiles.length; i++) {
                                    if (listOfFiles[i].isFile()) {
                                        String filename = listOfFiles[i].getName().replace(".yml", "");
                                        inv.setItem(i, createGuiItem("§b" + filename, new ArrayList < String > (Arrays.asList(" ", "§6You can't edit the file here")), XMaterial.PAPER.parseMaterial()));
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);
                            } else if (args[2].equalsIgnoreCase("Tablist")) {
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - Folder Tablist");

                                File folder = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/Tablist/");
                                File[] listOfFiles = folder.listFiles();

                                for (int i = 0; i < listOfFiles.length; i++) {
                                    if (listOfFiles[i].isFile()) {
                                        String filename = listOfFiles[i].getName().replace(".yml", "");
                                        inv.setItem(i, createGuiItem("§b" + filename, new ArrayList < String > (Arrays.asList(" ", "§eClick to edit this file")), XMaterial.PAPER.parseMaterial()));
                                    }
                                }

                                inv.setItem(54, createGuiItemWL("§cBack to the menu", XMaterial.BARRIER.parseMaterial()));

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
        
        if (Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
            i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1);
            ItemMeta iMeta = i.getItemMeta();
            iMeta.setDisplayName(name);
            iMeta.setLore(desc);
            i.setItemMeta(iMeta);
            return i;
        } else {
                if (mat.startsWith("ORANGE") || mat.startsWith("MAGENTA") || mat.startsWith("LIGHT_BLUE") || mat.startsWith("YELLOW")
                         || mat.startsWith("LIME") || mat.startsWith("PINK") || mat.startsWith("GRAY") || mat.startsWith("LIGHT_GRAY")
                         || mat.startsWith("CYAN") || mat.startsWith("PURPLE") || mat.startsWith("BLUE") || mat.startsWith("BROWN")
                         || mat.startsWith("GREEN") || mat.startsWith("RED") || mat.startsWith("BLACK")) {
                    
                    if (mat.startsWith("ORANGE")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 1);
                    } else if (mat.startsWith("MAGENTA")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 2);
                    } else if (mat.startsWith("LIGHT_BLUE")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 3);
                    } else if (mat.startsWith("YELLOW")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 4);
                    } else if (mat.startsWith("LIME")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 5);
                    } else if (mat.startsWith("PINK")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 6);
                    } else if (mat.startsWith("GRAY")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 7);
                    } else if (mat.startsWith("LIGHT_GRAY")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 8);
                    } else if (mat.startsWith("CYAN")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 9);
                    } else if (mat.startsWith("PURPLE")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 10);
                    } else if (mat.startsWith("BLUE")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 11);
                    } else if (mat.startsWith("BROWN")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 12);
                    } else if (mat.startsWith("GREEN")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 13);
                    } else if (mat.startsWith("RED")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 14);
                    } else if (mat.startsWith("BLACK")) {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1, (short) 15);
                    } else {
                        i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1);
                    }
                    
                    ItemMeta iMeta = i.getItemMeta();
                    iMeta.setDisplayName(name);
                    iMeta.setLore(desc);
                    i.setItemMeta(iMeta);
                    return i;

                } else {
                    i = new ItemStack(XMaterial.matchXMaterial(mat).parseMaterial(), 1);
                    ItemMeta iMeta = i.getItemMeta();
                    iMeta.setDisplayName(name);
                    iMeta.setLore(desc);
                    i.setItemMeta(iMeta);
                    return i;
                }
            }
    }
    
    public ItemStack createGuiItemWL(String name, Material mat) {
        ItemStack i = new ItemStack(mat, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        i.setItemMeta(iMeta);
        return i;
    }

}
