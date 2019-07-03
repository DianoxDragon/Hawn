package fr.Dianox.Hawn.Event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.CustomCommandConfig;
import fr.Dianox.Hawn.Utility.Config.ScoreboardMainConfig;
import fr.Dianox.Hawn.Utility.Config.ServerListConfig;
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
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cEvents")) {
                    p.performCommand("ap edit folder Events");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cMessages")) {
                    p.performCommand("ap edit folder Messages");
                } else if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cScoreboard")) {
                    p.sendMessage("§cEdit theses files manually");
                    e.setCancelled(true);
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
            // General
        } else if (inv.equals("§cAP - File G-AutoBroadcast") || inv.equals("§cAP - File G-CustomCommand") ||
            inv.equals("§cAP - File G-general") || inv.equals("§cAP - File G-Scoreboard-General") ||
            inv.equals("§cAP - File G-ServerList") || inv.equals("§cAP - File G-spawn") ||
            inv.equals("§cAP - File G-warplist") || inv.equals("§cAP - File G-between-servers")) {

            String invname = inv.replaceAll("§cAP - File G-", "");
            String prefixcommand = "G-";

             if (invname.contentEquals("CustomCommand")) {
            	 if (Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
	                if (e.getCurrentItem().getType() == XMaterial.GREEN_WOOL.parseMaterial()) {
	                    String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
	                    nameitem = nameitem.replaceAll("§b", "");
	                    if (nameitem.contentEquals("commands-general.enable")) {
	                    	onchangeboolean(invname, prefixcommand, e, p);
	                    } else {
	                    	interactclass(invname, "commands."+nameitem+".enable", prefixcommand, false);
	                    }
	                    p.performCommand("ap edit file G-" + invname);
	                } else if (e.getCurrentItem().getType() == XMaterial.RED_WOOL.parseMaterial()) {
	                    String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
	                    nameitem = nameitem.replaceAll("§b", "");
	                    if (nameitem.contentEquals("commands-general.enable")) {
	                    	onchangeboolean(invname, prefixcommand, e, p);
	                    } else {
	                        interactclass(invname, "commands."+nameitem+".enable", prefixcommand, true);
	                    }
	                    p.performCommand("ap edit file G-" + invname);
	                }
            	 } else {
            		 if (e.getCurrentItem().getDurability() == 13) {
 	                    String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
 	                    nameitem = nameitem.replaceAll("§b", "");
 	                    if (nameitem.contentEquals("commands-general.enable")) {
 	                    	onchangeboolean(invname, prefixcommand, e, p);
 	                    } else {
 	                    	interactclass(invname, "commands."+nameitem+".enable", prefixcommand, false);
 	                    }
 	                    p.performCommand("ap edit file G-" + invname);
 	                } else if (e.getCurrentItem().getDurability() == 14) {
 	                    String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
 	                    nameitem = nameitem.replaceAll("§b", "");
 	                    if (nameitem.contentEquals("commands-general.enable")) {
 	                    	onchangeboolean(invname, prefixcommand, e, p);
 	                    } else {
 	                        interactclass(invname, "commands."+nameitem+".enable", prefixcommand, true);
 	                    }
 	                    p.performCommand("ap edit file G-" + invname);
 	                }
            	 }
            } else {
            	onchangeboolean(invname, prefixcommand, e, p);
            }

            if (e.getCurrentItem().getType() == XMaterial.BARRIER.parseMaterial()) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the menu")) {
                    p.performCommand("ap");
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
                } else if (inv.equals("§cAP - Folder Tablist")) {
                    p.performCommand("ap edit file T-" + name);
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

            // //////////////////////////////
            // 
            // FILES FOR COMMANDS
            // 
            // //////////////////////////////
        } else if (inv.equals("§cAP - File C-Broadcast") || inv.equals("§cAP - File C-ClearChat") ||
            inv.equals("§cAP - File C-ClearInv") || inv.equals("§cAP - File C-DelayChat") ||
            inv.equals("§cAP - File C-Emoji") || inv.equals("§cAP - File C-Fly") ||
            inv.equals("§cAP - File C-Heal") || inv.equals("§cAP - File C-Help") ||
            inv.equals("§cAP - File C-MuteChat") || inv.equals("§cAP - File C-Ping") ||
            inv.equals("§cAP - File C-Spawn") ||
            inv.equals("§cAP - File C-TitleAnnouncer") || inv.equals("§cAP - File C-Vanish") ||
            inv.equals("§cAP - File C-Warp-SetWarp") || inv.equals("§cAP - File C-Weather-Time") ||
            inv.equals("§cAP - File C-Scoreboard") || inv.equals("§cAP - File C-Gamemode")) {

            String invname = inv.replaceAll("§cAP - File C-", "");
            String prefixcommand = "C-";
            
            if (e.getCurrentItem().getType() != XMaterial.BARRIER.parseMaterial()) {
            	onchangeboolean(invname, prefixcommand, e, p);
            } else {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
                    p.performCommand("ap edit folder commands");
                }
            }

            e.setCancelled(true);
            // //////////////////////////////
            // 
            // FILES FOR Cosmetics-Fun
            // 
            // //////////////////////////////
        } else if (inv.equals("§cAP - File CF-DoubleJump") || inv.equals("§cAP - File CF-JumpPads") ||
            inv.equals("§cAP - File CF-OnJoin")) {

            String invname = inv.replaceAll("§cAP - File CF-", "");
            String prefixcommand = "CF-";
            
            if (e.getCurrentItem().getType() != XMaterial.BARRIER.parseMaterial()) {
            	onchangeboolean(invname, prefixcommand, e, p);
            } else {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
                    p.performCommand("ap edit folder Cosmetics-Fun");
                }
            }

            e.setCancelled(true);
            // //////////////////////////////
            // 
            // FILES FOR Events
            // 
            // //////////////////////////////
        } else if (inv.equals("§cAP - File E-Chat") ||
                inv.equals("§cAP - File E-JoinQuitCommand") ||
                        inv.equals("§cAP - File E-OnCommands") ||
                        inv.contains("§cAP - File E-OnJoin") ||
                        inv.equals("§cAP - File E-OtherFeatures") ||
                        inv.equals("§cAP - File E-PlayerEvents") ||
                        inv.equals("§cAP - File E-ProtectionPlayer") ||
                        inv.equals("§cAP - File E-ProtectionWorld") ||
                        inv.equals("§cAP - File E-VoidTP") ||
                        inv.equals("§cAP - File E-WorldEvent") ||
                        inv.equals("§cAP - File E-PlayerWorldChange")) {

            String invname = inv.replaceAll("§cAP - File E-", "");
            String prefixcommand = "E-";
            
            if (e.getCurrentItem().getType() != XMaterial.BARRIER.parseMaterial()) {
            	if (invname.contentEquals("OnJoin") || invname.contentEquals("OnJoin2")) {
            		
            		onchangeboolean(invname, prefixcommand, e, p);
            		
                    if (e.getCurrentItem().getType() == XMaterial.PAPER.parseMaterial()) {
                        String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                        if (nameitem.contains("NEXT")) {
                            p.performCommand("ap edit file E-OnJoin2");
                        } else if (nameitem.contains("PREVIOUS")) {
                            p.performCommand("ap edit file E-OnJoin");
                        }
                    }
                } else {
                	onchangeboolean(invname, prefixcommand, e, p);
                }
                
            	e.setCancelled(true);
            } else {
            	if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
            		p.performCommand("ap edit folder Events");
            	}
            }
            // //////////////////////////////
            // 
            // FILES FOR Messages
            // 
            // //////////////////////////////
        } else if (inv.equals("§cAP - File M-AdministrationAndMore") ||
                    inv.equals("§cAP - File M-Commands") ||
                    inv.equals("§cAP - File M-Events") ||
                    inv.equals("§cAP - File M-General") ||
                    inv.equals("§cAP - File M-Protection")) {

            String invname = inv.replaceAll("§cAP - File M-", "");
            String prefixcommand = "M-";
            
            if (e.getCurrentItem().getType() != XMaterial.BARRIER.parseMaterial()) {
            	onchangeboolean(invname, prefixcommand, e, p);
            } else {
                if (e.getCurrentItem().getItemMeta().getDisplayName().contentEquals("§cBack to the folder menu")) {
                    p.performCommand("ap edit folder Messages");
                }
            }

            e.setCancelled(true);
        }
    }
    
    @SuppressWarnings("deprecation")
	public void onchangeboolean(String invname, String prefixcommand, InventoryClickEvent e, Player p) {
    	if (Bukkit.getVersion().contains("1.15") || Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13")) {
            if (e.getCurrentItem().getType() == XMaterial.GREEN_WOOL.parseMaterial()) {
                String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                nameitem = nameitem.replaceAll("§b", "");
                interactclass(invname, nameitem, prefixcommand, false);
                p.performCommand("ap edit file " + prefixcommand + invname);
            } else if (e.getCurrentItem().getType() == XMaterial.RED_WOOL.parseMaterial()) {
                String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                nameitem = nameitem.replaceAll("§b", "");
                interactclass(invname, nameitem, prefixcommand, true);
                p.performCommand("ap edit file " + prefixcommand + invname);
            }
    	} else {
    		if (e.getCurrentItem().getDurability() == 13) {
                String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                nameitem = nameitem.replaceAll("§b", "");
                interactclass(invname, nameitem, prefixcommand, false);
                p.performCommand("ap edit file " + prefixcommand + invname);
            } else if (e.getCurrentItem().getDurability() == 14) {
                String nameitem = e.getCurrentItem().getItemMeta().getDisplayName();
                nameitem = nameitem.replaceAll("§b", "");
                interactclass(invname, nameitem, prefixcommand, true);
                p.performCommand("ap edit file " + prefixcommand + invname);
            }
    	}
    }
    
    public void interactclass(String inv, String nameitem, String prefixcommand, Boolean trueorfalse) {
        
        String invname = prefixcommand + inv;

        // General
        if (invname.contains("G-AutoBroadcast")) {
            AutoBroadcastConfig.getConfig().set(nameitem, trueorfalse);
            AutoBroadcastConfig.saveConfigFile();
            AutoBroadcastConfig.reloadConfig();
        } else if (invname.contains("G-CustomCommand")) {
            CustomCommandConfig.getConfig().set(nameitem, trueorfalse);
            CustomCommandConfig.saveConfigFile();
            CustomCommandConfig.reloadConfig();
        } else if (invname.contains("G-general")) {
            ConfigGeneral.getConfig().set(nameitem, trueorfalse);
            ConfigGeneral.saveConfigFile();
            ConfigGeneral.reloadConfig();
        } else if (invname.contains("G-Scoreboard-General")) {
            ScoreboardMainConfig.getConfig().set(nameitem, trueorfalse);
            ScoreboardMainConfig.saveConfigFile();
            ScoreboardMainConfig.reloadConfig();
        } else if (invname.contains("G-ServerList")) {
            ServerListConfig.getConfig().set(nameitem, trueorfalse);
            ServerListConfig.saveConfigFile();
            ServerListConfig.reloadConfig();
        // //////////////////////////////
        // 
        // FILES FOR COMMANDS
        // 
        // //////////////////////////////
        } else if (invname.contains("C-Broadcast")) {
            BroadCastCommandConfig.getConfig().set(nameitem, trueorfalse);
            BroadCastCommandConfig.saveConfigFile();
            BroadCastCommandConfig.reloadConfig();
        } else if (invname.contains("C-ClearChat")) {
            ClearChatCommandConfig.getConfig().set(nameitem, trueorfalse);
            ClearChatCommandConfig.saveConfigFile();
            ClearChatCommandConfig.reloadConfig();
        } else if (invname.contains("C-ClearInv")) {
            ClearInvCommandConfig.getConfig().set(nameitem, trueorfalse);
            ClearInvCommandConfig.saveConfigFile();
            ClearInvCommandConfig.reloadConfig();
        } else if (invname.contains("C-DelayChat")) {
            DelayChatCommandConfig.getConfig().set(nameitem, trueorfalse);
            DelayChatCommandConfig.saveConfigFile();
            DelayChatCommandConfig.reloadConfig();
        } else if (invname.contains("C-Emoji")) {
            EmojiCommandConfig.getConfig().set(nameitem, trueorfalse);
            EmojiCommandConfig.saveConfigFile();
            EmojiCommandConfig.reloadConfig();
        } else if (invname.contains("C-Fly")) {
            FlyCommandConfig.getConfig().set(nameitem, trueorfalse);
            FlyCommandConfig.saveConfigFile();
            FlyCommandConfig.reloadConfig();
        } else if (invname.contains("C-Heal")) {
            HealCommandConfig.getConfig().set(nameitem, trueorfalse);
            HealCommandConfig.saveConfigFile();
            HealCommandConfig.reloadConfig();
        } else if (invname.contains("C-Help")) {
            HelpCommandConfig.getConfig().set(nameitem, trueorfalse);
            HelpCommandConfig.saveConfigFile();
            HelpCommandConfig.reloadConfig();
        } else if (invname.contains("C-MuteChat")) {
            MuteChatCommandConfig.getConfig().set(nameitem, trueorfalse);
            MuteChatCommandConfig.saveConfigFile();
            MuteChatCommandConfig.reloadConfig();
        } else if (invname.contains("C-Ping")) {
            PingCommandConfig.getConfig().set(nameitem, trueorfalse);
            PingCommandConfig.saveConfigFile();
            PingCommandConfig.reloadConfig();
        } else if (invname.contains("C-Spawn")) {
            SpawnCommandConfig.getConfig().set(nameitem, trueorfalse);
            SpawnCommandConfig.saveConfigFile();
            SpawnCommandConfig.reloadConfig();
        } else if (invname.contains("C-TitleAnnouncer")) {
            TitleAnnouncerConfig.getConfig().set(nameitem, trueorfalse);
            TitleAnnouncerConfig.saveConfigFile();
            TitleAnnouncerConfig.reloadConfig();
        } else if (invname.contains("C-Vanish")) {
            VanishCommandConfig.getConfig().set(nameitem, trueorfalse);
            VanishCommandConfig.saveConfigFile();
            VanishCommandConfig.reloadConfig();
        } else if (invname.contains("C-Warp-SetWarp")) {
            WarpSetWarpCommandConfig.getConfig().set(nameitem, trueorfalse);
            WarpSetWarpCommandConfig.saveConfigFile();
            WarpSetWarpCommandConfig.reloadConfig();
        } else if (invname.contains("C-Weather-Time")) {
            WeatherTimeCommandConfig.getConfig().set(nameitem, trueorfalse);
            WeatherTimeCommandConfig.saveConfigFile();
            WeatherTimeCommandConfig.reloadConfig();
        } else if (invname.contains("C-Scoreboard")) {
        	ScoreboardCommandConfig.getConfig().set(nameitem, trueorfalse);
        	ScoreboardCommandConfig.saveConfigFile();
        	ScoreboardCommandConfig.reloadConfig();
        } else if (invname.contains("C-Gamemode")) {
        	GamemodeCommandConfig.getConfig().set(nameitem, trueorfalse);
        	GamemodeCommandConfig.saveConfigFile();
        	GamemodeCommandConfig.reloadConfig();
        // //////////////////////////////
        // 
        // FILES FOR Cosmetics-Fun
        // 
        // //////////////////////////////
        } else if (invname.contains("CF-DoubleJump")) {
            ConfigFDoubleJump.getConfig().set(nameitem, trueorfalse);
            ConfigFDoubleJump.saveConfigFile();
            ConfigFDoubleJump.reloadConfig();
        } else if (invname.contains("CF-JumpPads")) {
            ConfigGLP.getConfig().set(nameitem, trueorfalse);
            ConfigGLP.saveConfigFile();
            ConfigGLP.reloadConfig();
        } else if (invname.contains("CF-OnJoin")) {
            ConfigGCos.getConfig().set(nameitem, trueorfalse);
            ConfigGCos.saveConfigFile();
            ConfigGCos.reloadConfig();
        // //////////////////////////////
        // 
        // FILES FOR Events
        // 
        // //////////////////////////////
        } else if (invname.contains("E-Chat")) {
            OnChatConfig.getConfig().set(nameitem, trueorfalse);
            OnChatConfig.saveConfigFile();
            OnChatConfig.reloadConfig();
        } else if (invname.contains("E-JoinQuitCommand")) {
            ConfigGJoinQuitCommand.getConfig().set(nameitem, trueorfalse);
            ConfigGJoinQuitCommand.saveConfigFile();
            ConfigGJoinQuitCommand.reloadConfig();
        } else if (invname.contains("E-OnCommands")) {
            CommandEventConfig.getConfig().set(nameitem, trueorfalse);
            CommandEventConfig.saveConfigFile();
            CommandEventConfig.reloadConfig();
        } else if (invname.contains("E-OnJoin") || invname.contains("E-OnJoin2")) {
            OnJoinConfig.getConfig().set(nameitem, trueorfalse);
            OnJoinConfig.saveConfigFile();
            OnJoinConfig.reloadConfig();
        } else if (invname.contains("E-OtherFeatures")) {
            OtherFeaturesConfig.getConfig().set(nameitem, trueorfalse);
            OtherFeaturesConfig.saveConfigFile();
            OtherFeaturesConfig.reloadConfig();
        } else if (invname.contains("E-PlayerEvents")) {
            PlayerEventsConfig.getConfig().set(nameitem, trueorfalse);
            PlayerEventsConfig.saveConfigFile();
            PlayerEventsConfig.reloadConfig();
        } else if (invname.contains("E-ProtectionPlayer")) {
            ProtectionPlayerConfig.getConfig().set(nameitem, trueorfalse);
            ProtectionPlayerConfig.saveConfigFile();
            ProtectionPlayerConfig.reloadConfig();
        } else if (invname.contains("E-ProtectionWorld")) {
            ConfigGProtection.getConfig().set(nameitem, trueorfalse);
            ConfigGProtection.saveConfigFile();
            ConfigGProtection.reloadConfig();
        } else if (invname.contains("E-VoidTP")) {
            VoidTPConfig.getConfig().set(nameitem, trueorfalse);
            VoidTPConfig.saveConfigFile();
            VoidTPConfig.reloadConfig();
        } else if (invname.contains("E-WorldEvent")) {
            WorldEventConfig.getConfig().set(nameitem, trueorfalse);
            WorldEventConfig.saveConfigFile();
            WorldEventConfig.reloadConfig();
        } else if (invname.contains("E-PlayerWorldChange")) {
            PlayerWorldChangeConfigE.getConfig().set(nameitem, trueorfalse);
            PlayerWorldChangeConfigE.saveConfigFile();
            PlayerWorldChangeConfigE.reloadConfig();
        // //////////////////////////////
        // 
        // FILES FOR Messages
        // 
        // //////////////////////////////
        } else if (invname.contains("M-AdministrationAndMore")) {
            ConfigMOStuff.getConfig().set(nameitem, trueorfalse);
            ConfigMOStuff.saveConfigFile();
            ConfigMOStuff.reloadConfig();
        } else if (invname.contains("M-Commands")) {
            ConfigMCommands.getConfig().set(nameitem, trueorfalse);
            ConfigMCommands.saveConfigFile();
            ConfigMCommands.reloadConfig();
        } else if (invname.contains("M-Events")) {
            ConfigMEvents.getConfig().set(nameitem, trueorfalse);
            ConfigMEvents.saveConfigFile();
            ConfigMEvents.reloadConfig();
        } else if (invname.contains("M-General")) {
            ConfigMGeneral.getConfig().set(nameitem, trueorfalse);
            ConfigMGeneral.saveConfigFile();
            ConfigMGeneral.reloadConfig();
        } else if (invname.contains("M-Protection")) {
            ConfigMProtection.getConfig().set(nameitem, trueorfalse);
            ConfigMProtection.saveConfigFile();
            ConfigMProtection.reloadConfig();
        }
    }

}