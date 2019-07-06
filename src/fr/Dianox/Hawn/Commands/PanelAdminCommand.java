package fr.Dianox.Hawn.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.CustomCommandConfig;
import fr.Dianox.Hawn.Utility.Config.ScoreboardMainConfig;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
import fr.Dianox.Hawn.Utility.Config.WarpListConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.BroadCastCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ClearChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ClearInvCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.DelayChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.EmojiCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.FlyCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.GamemodeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.HealCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.HelpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.MuteChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.PingCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.ScoreboardCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.SpawnCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.TitleAnnouncerConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.VanishCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WarpSetWarpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.WeatherTimeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGLP;
import fr.Dianox.Hawn.Utility.Config.Events.CommandEventConfig;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGJoinQuitCommand;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGProtection;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OtherFeaturesConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerEventsConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerWorldChangeConfigE;
import fr.Dianox.Hawn.Utility.Config.Events.ProtectionPlayerConfig;
import fr.Dianox.Hawn.Utility.Config.Events.VoidTPConfig;
import fr.Dianox.Hawn.Utility.Config.Events.WorldEventConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMEvents;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMProtection;

public class PanelAdminCommand implements CommandExecutor {
        
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
        	if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
            return true;
        }

        Player p = (Player) sender;
        int numberitems = 0;
        int maximumitem = 44;
        String newnumber = "";
        int numberitemstoeleimate = 0;
        
        if (label.equalsIgnoreCase("adminpanel") || label.equalsIgnoreCase("paneladmin") || label.equalsIgnoreCase("pa") || label.equalsIgnoreCase("ap")) {
            if (p.hasPermission("hawn.adminpanel")) {
                if (args.length == 0) {
                    Inventory inv = Bukkit.createInventory(null, 54, "§cAP");
                    
                    // Folders
                    inv.setItem(0, createGuiItem("§cCommands", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(1, createGuiItem("§cCosmetics-Fun", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(2, createGuiItem("§cEvents", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(3, createGuiItem("§cMessages", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(4, createGuiItem("§cScoreboard", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    inv.setItem(5, createGuiItem("§cTablist", new ArrayList < String > (Arrays.asList(" ", "§6Click to edit this folder")), XMaterial.CHEST.parseMaterial()));
                    
                    int number_place = 6;
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
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("edit")) {
                        if (args[1].equalsIgnoreCase("file")) {
                            // //////////////////////////////
                            // 
                            // FILES FOR GENERAL FILES
                            // 
                            // //////////////////////////////
                            if (args[2].contains("G-AutoBroadcast") || args[2].contains("G-CustomCommand") ||
                                args[2].contains("G-general") || args[2].contains("G-Scoreboard-General") ||
                                args[2].contains("G-ServerList") || args[2].contains("G-spawn") ||
                                args[2].contains("G-warplist") || args[2].contains("G-between-servers")) {

                                String value = String.valueOf(args[2].replaceAll("G-", ""));

                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - File " + args[2]);
                                int number_place = 0;

                                if (value.contentEquals("between-servers")) {
                                    Iterator < ? > iterator = BetweenServersConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (BetweenServersConfig.getConfig().isBoolean(string)) {
                                            if (BetweenServersConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("AutoBroadcast")) {
                                    Iterator < ? > iterator = AutoBroadcastConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (AutoBroadcastConfig.getConfig().isBoolean(string)) {
                                            if (AutoBroadcastConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("CustomCommand")) {
                                    number_place = 9;
                                    Iterator < ? > iterator = CustomCommandConfig.getConfig().getConfigurationSection("commands").getKeys(false).iterator();

                                    if (CustomCommandConfig.getConfig().getBoolean("commands-general.enable")) {
                                        inv.setItem(0, createGuiItemColor("§bcommands-general.enable", new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                    } else {
                                        inv.setItem(0, createGuiItemColor("§bcommands-general.enable", new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                    }

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        String getperm = CustomCommandConfig.getConfig().getString("commands." + string + ".permission.message");
                                        String getpermyesorno = String.valueOf(CustomCommandConfig.getConfig().getBoolean("commands." + string + ".permission.enable"));
                                        String nopermmsg = String.valueOf(CustomCommandConfig.getConfig().getBoolean("commands." + string + ".no-permission-message-enable"));
                                        String command = CustomCommandConfig.getConfig().getString("commands." + string + ".command");

                                        if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".enable")) {
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
                                    }
                                } else if (value.contentEquals("general")) {
                                    Iterator < ? > iterator = ConfigGeneral.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigGeneral.getConfig().isBoolean(string)) {
                                            if (ConfigGeneral.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Scoreboard-General")) {
                                    Iterator < ? > iterator = ScoreboardMainConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ScoreboardMainConfig.getConfig().isBoolean(string)) {
                                            if (ScoreboardMainConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("ServerList")) {
                                    Iterator < ? > iterator = ServerListConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ServerListConfig.getConfig().isBoolean(string)) {
                                            if (ServerListConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("spawn")) {
                                    if (!ConfigSpawn.getConfig().isSet("Coordinated")) {
                                        p.sendMessage("§cThe file is empty");
                                        return true;
                                    }

                                    Iterator < ? > iterator = ConfigSpawn.getConfig().getConfigurationSection("Coordinated").getKeys(false).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        String getworld = ConfigSpawn.getConfig().getString("Coordinated." + string + ".World");
                                        String x = String.valueOf(ConfigSpawn.getConfig().getDouble("Coordinated." + string + ".X"));
                                        String y = String.valueOf(ConfigSpawn.getConfig().getDouble("Coordinated." + string + ".Y"));
                                        String z = String.valueOf(ConfigSpawn.getConfig().getDouble("Coordinated." + string + ".Z"));
                                        String yaw = String.valueOf(ConfigSpawn.getConfig().getInt("Coordinated." + string + ".Yaw"));
                                        String pitch = String.valueOf(ConfigSpawn.getConfig().getInt("Coordinated." + string + ".Pitch"));

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
                                    }
                                } else if (value.contentEquals("warplist")) {
                                    if (!WarpListConfig.getConfig().isSet("Coordinated")) {
                                        p.sendMessage("§cThe file is empty");
                                        return true;
                                    }

                                    Iterator < ? > iterator = WarpListConfig.getConfig().getConfigurationSection("Coordinated").getKeys(false).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        String getworld = WarpListConfig.getConfig().getString("Coordinated." + string + ".World");
                                        String x = String.valueOf(WarpListConfig.getConfig().getDouble("Coordinated." + string + ".X"));
                                        String y = String.valueOf(WarpListConfig.getConfig().getDouble("Coordinated." + string + ".Y"));
                                        String z = String.valueOf(WarpListConfig.getConfig().getDouble("Coordinated." + string + ".Z"));
                                        String yaw = String.valueOf(WarpListConfig.getConfig().getInt("Coordinated." + string + ".Yaw"));
                                        String pitch = String.valueOf(WarpListConfig.getConfig().getInt("Coordinated." + string + ".Pitch"));

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
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);

                                // //////////////////////////////
                                // 
                                // FILES FOR COMMANDS
                                // 
                                // //////////////////////////////
                            } else if (args[2].equalsIgnoreCase("C-Broadcast") || args[2].equalsIgnoreCase("C-ClearChat") ||
                                args[2].equalsIgnoreCase("C-ClearInv") || args[2].equalsIgnoreCase("C-DelayChat") ||
                                args[2].equalsIgnoreCase("C-Emoji") || args[2].equalsIgnoreCase("C-Fly") ||
                                args[2].equalsIgnoreCase("C-Heal") || args[2].equalsIgnoreCase("C-Help") ||
                                args[2].equalsIgnoreCase("C-MuteChat") || args[2].equalsIgnoreCase("C-Ping") ||
                                args[2].equalsIgnoreCase("C-Spawn") ||
                                args[2].equalsIgnoreCase("C-TitleAnnouncer") || args[2].equalsIgnoreCase("C-Vanish") ||
                                args[2].equalsIgnoreCase("C-Warp-SetWarp") || args[2].equalsIgnoreCase("C-Weather-Time") ||
                                args[2].equalsIgnoreCase("C-Scoreboard") || args[2].equalsIgnoreCase("C-Gamemode")) {

                                String value = String.valueOf(args[2].replaceAll("C-", ""));

                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - File " + args[2]);
                                int number_place = 0;

                                
                                if (value.contentEquals("Gamemode")) {
                                    Iterator < ? > iterator = GamemodeCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (GamemodeCommandConfig.getConfig().isBoolean(string)) {
                                            if (GamemodeCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Scoreboard")) {
                                    Iterator < ? > iterator = ScoreboardCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ScoreboardCommandConfig.getConfig().isBoolean(string)) {
                                            if (ScoreboardCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Broadcast")) {
                                    Iterator < ? > iterator = BroadCastCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (BroadCastCommandConfig.getConfig().isBoolean(string)) {
                                            if (BroadCastCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }

                                } else if (value.contentEquals("ClearChat")) {
                                    Iterator < ? > iterator = ClearChatCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ClearChatCommandConfig.getConfig().isBoolean(string)) {
                                            if (ClearChatCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("ClearInv")) {
                                    Iterator < ? > iterator = ClearInvCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ClearInvCommandConfig.getConfig().isBoolean(string)) {
                                            if (ClearInvCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("DelayChat")) {
                                    Iterator < ? > iterator = DelayChatCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (DelayChatCommandConfig.getConfig().isBoolean(string)) {
                                            if (DelayChatCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Emoji")) {
                                    Iterator < ? > iterator = EmojiCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (EmojiCommandConfig.getConfig().isBoolean(string)) {
                                            if (EmojiCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Fly")) {
                                    Iterator < ? > iterator = FlyCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (FlyCommandConfig.getConfig().isBoolean(string)) {
                                            if (FlyCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Heal")) {
                                    Iterator < ? > iterator = HealCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (HealCommandConfig.getConfig().isBoolean(string)) {
                                            if (HealCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Help")) {
                                    Iterator < ? > iterator = HelpCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (HelpCommandConfig.getConfig().isBoolean(string)) {
                                            if (HelpCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("MuteChat")) {
                                    Iterator < ? > iterator = MuteChatCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (MuteChatCommandConfig.getConfig().isBoolean(string)) {
                                            if (MuteChatCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Ping")) {
                                    Iterator < ? > iterator = PingCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (PingCommandConfig.getConfig().isBoolean(string)) {
                                            if (PingCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Spawn")) {
                                    Iterator < ? > iterator = SpawnCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (SpawnCommandConfig.getConfig().isBoolean(string)) {
                                            if (SpawnCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("TitleAnnouncer")) {
                                    Iterator < ? > iterator = TitleAnnouncerConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (TitleAnnouncerConfig.getConfig().isBoolean(string)) {
                                            if (TitleAnnouncerConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Vanish")) {
                                    Iterator < ? > iterator = VanishCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (VanishCommandConfig.getConfig().isBoolean(string)) {
                                            if (VanishCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Warp-SetWarp")) {
                                    Iterator < ? > iterator = WarpSetWarpCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (WarpSetWarpCommandConfig.getConfig().isBoolean(string)) {
                                            if (WarpSetWarpCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Weather-Time")) {
                                    Iterator < ? > iterator = WeatherTimeCommandConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (WeatherTimeCommandConfig.getConfig().isBoolean(string)) {
                                            if (WeatherTimeCommandConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the folder menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);

                                // //////////////////////////////
                                // 
                                // FILES FOR Cosmetics-Fun
                                // 
                                // //////////////////////////////
                            } else if (args[2].equalsIgnoreCase("CF-DoubleJump") || args[2].equalsIgnoreCase("CF-JumpPads") ||
                                args[2].equalsIgnoreCase("CF-OnJoin")) {

                                String value = String.valueOf(args[2].replaceAll("CF-", ""));

                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - File " + args[2]);
                                int number_place = 0;

                                if (value.contentEquals("DoubleJump")) {
                                    Iterator < ? > iterator = ConfigFDoubleJump.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigFDoubleJump.getConfig().isBoolean(string)) {
                                            if (ConfigFDoubleJump.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("JumpPads")) {
                                    Iterator < ? > iterator = ConfigGLP.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigGLP.getConfig().isBoolean(string)) {
                                            if (ConfigGLP.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("OnJoin")) {
                                    Iterator < ? > iterator = ConfigGCos.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigGCos.getConfig().isBoolean(string)) {
                                            if (ConfigGCos.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                }

                                inv.setItem(45, createGuiItemWL("§cBack to the folder menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);
                                // //////////////////////////////
                                // 
                                // FILES FOR Events
                                // 
                                // //////////////////////////////
                            } else if (args[2].contains("E-Chat") ||
                                    args[2].contains("E-JoinQuitCommand") ||
                                    args[2].contains("E-OnCommands") ||
                                    args[2].contains("E-OnJoin") ||
                                    args[2].contains("E-OtherFeatures") ||
                                    args[2].contains("E-PlayerEvents") ||
                                    args[2].contains("E-ProtectionPlayer") ||
                                    args[2].contains("E-ProtectionWorld") ||
                                    args[2].contains("E-VoidTP") ||
                                    args[2].contains("E-WorldEvent") ||
                                    args[2].contains("E-PlayerWorldChange")) {

                                String value = String.valueOf(args[2].replaceAll("E-", ""));
                                
                                
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - File " + args[2]);
                                int number_place = 0;

                                if (value.contentEquals("PlayerWorldChange")) {
                                    Iterator < ? > iterator = PlayerWorldChangeConfigE.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (PlayerWorldChangeConfigE.getConfig().isBoolean(string)) {
                                            if (PlayerWorldChangeConfigE.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Chat")) {
                                    Iterator < ? > iterator = OnChatConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (OnChatConfig.getConfig().isBoolean(string)) {
                                            if (OnChatConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("JoinQuitCommand")) {
                                    Iterator < ? > iterator = ConfigGJoinQuitCommand.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigGJoinQuitCommand.getConfig().isBoolean(string)) {
                                            if (ConfigGJoinQuitCommand.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("OnCommands")) {
                                    Iterator < ? > iterator = CommandEventConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (CommandEventConfig.getConfig().isBoolean(string)) {
                                            if (CommandEventConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contains("OnJoin")) {
                                    String numberpagetoisolate = String.valueOf(value.replaceAll("OnJoin", ""));
                                    
                                    if (numberpagetoisolate.isEmpty()) {
                                        numberpagetoisolate = String.valueOf(1);
                                    }
                                    
                                    Iterator < ? > iterator = OnJoinConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (numberpagetoisolate.equalsIgnoreCase("1") || numberpagetoisolate == null) {
                                            if (numberitems <= maximumitem) {
                                                if (OnJoinConfig.getConfig().isBoolean(string)) {
                                                    if (OnJoinConfig.getConfig().getBoolean(string)) {
                                                        inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                        number_place++;
                                                        numberitems++;
                                                    } else {
                                                        inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                        number_place++;
                                                        numberitems++;
                                                    }
                                                }
                                            } else {
                                                newnumber = newnumber.replaceAll("1", "");
                                                p.performCommand("ap edit file "+newnumber+"2");
                                            }
                                        } else {
                                            int numberpagetotal = Integer.valueOf(numberpagetoisolate);
                                            numberitems = 0;
                                            
                                            // page 2
                                            maximumitem = 45 * numberpagetotal;
                                            int loopaeleminer = 45 * (numberpagetotal - 1);
                                            
                                            if (numberitemstoeleimate < loopaeleminer) {
                                                if (OnJoinConfig.getConfig().isBoolean(string)) {
                                                    numberitemstoeleimate++;
                                                }
                                            } else {
                                                if (numberitems <= maximumitem) {
                                                    if (OnJoinConfig.getConfig().isBoolean(string)) {
                                                        if (OnJoinConfig.getConfig().getBoolean(string)) {
                                                            inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                            number_place++;
                                                            numberitems++;
                                                        } else {
                                                            inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                            number_place++;
                                                            numberitems++;
                                                        }
                                                    }
                                                } else {
                                                    newnumber = newnumber.replaceAll(numberpagetoisolate, "");
                                                    numberpagetotal = numberpagetotal + 1;
                                                    newnumber = String.valueOf(numberpagetotal);
                                                    p.performCommand("ap edit file args[2]"+newnumber);
                                                }
                                            }
                                        }
                                        
                                    }
                                    if (numberpagetoisolate.equalsIgnoreCase("1") || numberpagetoisolate == null) {
                                        inv.setItem(47, createGuiItem("§bNEXT", new ArrayList < String > (Arrays.asList(" ", "§eNext")), XMaterial.PAPER.parseMaterial()));
                                    } else {
                                        inv.setItem(45, createGuiItem("§bPREVIOUS", new ArrayList < String > (Arrays.asList(" ", "§ePrevious")), XMaterial.PAPER.parseMaterial()));
                                    }
                                } else if (value.contentEquals("OtherFeatures")) {
                                    Iterator < ? > iterator = OtherFeaturesConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (OtherFeaturesConfig.getConfig().isBoolean(string)) {
                                            if (OtherFeaturesConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("PlayerEvents")) {
                                    Iterator < ? > iterator = PlayerEventsConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (PlayerEventsConfig.getConfig().isBoolean(string)) {
                                            if (PlayerEventsConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("ProtectionPlayer")) {
                                    Iterator < ? > iterator = ProtectionPlayerConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ProtectionPlayerConfig.getConfig().isBoolean(string)) {
                                            if (ProtectionPlayerConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("ProtectionWorld")) {
                                    Iterator < ? > iterator = ConfigGProtection.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigGProtection.getConfig().isBoolean(string)) {
                                            if (ConfigGProtection.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("VoidTP")) {
                                    Iterator < ? > iterator = VoidTPConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (VoidTPConfig.getConfig().isBoolean(string)) {
                                            if (VoidTPConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("WorldEvent")) {
                                    Iterator < ? > iterator = WorldEventConfig.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (WorldEventConfig.getConfig().isBoolean(string)) {
                                            if (WorldEventConfig.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                }

                                inv.setItem(53, createGuiItemWL("§cBack to the folder menu", XMaterial.BARRIER.parseMaterial()));

                                p.openInventory(inv);
                                // //////////////////////////////
                                // 
                                // FILES FOR Messages
                                // 
                                // //////////////////////////////
                            } else if (args[2].equalsIgnoreCase("M-AdministrationAndMore") ||
                                    args[2].equalsIgnoreCase("M-Commands") ||
                                    args[2].equalsIgnoreCase("M-Events") ||
                                    args[2].equalsIgnoreCase("M-General") ||
                                    args[2].equalsIgnoreCase("M-Protection")) {

                                String value = String.valueOf(args[2].replaceAll("M-", ""));
                                
                                Inventory inv = Bukkit.createInventory(null, 54, "§cAP - File " + args[2]);
                                int number_place = 0;

                                if (value.contentEquals("AdministrationAndMore")) {
                                    Iterator < ? > iterator = ConfigMOStuff.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigMOStuff.getConfig().isBoolean(string)) {
                                            if (ConfigMOStuff.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Commands")) {
                                    Iterator < ? > iterator = ConfigMCommands.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigMCommands.getConfig().isBoolean(string)) {
                                            if (ConfigMCommands.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Events")) {
                                    Iterator < ? > iterator = ConfigMEvents.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigMEvents.getConfig().isBoolean(string)) {
                                            if (ConfigMEvents.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("General")) {
                                    Iterator < ? > iterator = ConfigMGeneral.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigMGeneral.getConfig().isBoolean(string)) {
                                            if (ConfigMGeneral.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
                                } else if (value.contentEquals("Protection")) {
                                    Iterator < ? > iterator = ConfigMProtection.getConfig().getKeys(true).iterator();

                                    while (iterator.hasNext()) {
                                        String string = (String) iterator.next();

                                        if (ConfigMProtection.getConfig().isBoolean(string)) {
                                            if (ConfigMProtection.getConfig().getBoolean(string)) {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §cfalse")), "GREEN_WOOL"));
                                                number_place++;
                                            } else {
                                                inv.setItem(number_place, createGuiItemColor("§b" + string, new ArrayList < String > (Arrays.asList(" ", "§eClick to put this line to §atrue")), "RED_WOOL"));
                                                number_place++;
                                            }
                                        }
                                    }
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
