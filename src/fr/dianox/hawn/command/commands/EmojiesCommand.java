package fr.dianox.hawn.command.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.commands.EmojiCommandConfig;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.EmojisListCUtility;
import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;

@SuppressWarnings("deprecation")
public class EmojiesCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.emoji";
	
	public EmojiesCommand(String name) {
		super(name);
		this.description = "Open the emoji gui";
        this.usageMessage = "/emoji";
	}
	
	@SuppressWarnings("rawtypes")
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

        @SuppressWarnings("unused")
        int menu = 0;
        @SuppressWarnings("unused")
		int number = 0;
        int number_check_place = 0;

        @SuppressWarnings("unused")
        int boucle_loop = 0;

        @SuppressWarnings("unused")
        Material material_item;
        String Displayname = "";
        ItemStack ref1 = null;
        ItemMeta metaref1 = null;
        SkullMeta meta = null;

        Player p = (Player) sender;

        
		// >>> Executed by the player
		if (!EmojiCommandConfig.getConfig().getBoolean("Emoji.Enable")) {
			if (EmojiCommandConfig.getConfig().getBoolean("Emoji.Disable-Message")) {
				if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                	}
    			}
			}
			
			return true;
		}
		
		if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list.Option.Gui.Use_Permission")) {
			if (!p.hasPermission(GeneralPermission)) {
				MessageUtils.MessageNoPermission(p, GeneralPermission);
				return true;
			}
		}
		
		// The command	
                String titlegui = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Title");
                titlegui = titlegui.replaceAll("&", "§");

                Inventory inv = Bukkit.createInventory(null, 54, titlegui);

                // List
                    Iterator it = ChatEmojisLoad.emojislist.entrySet().iterator();
   				 
                    List<String> stringcheck = new ArrayList<String>();
                    
                    while (it.hasNext()) {
                    	Map.Entry pair = (Map.Entry)it.next();
	   					 
                    	String check = String.valueOf(pair.getKey());
	   					String string = String.valueOf(ChatEmojisLoad.emojislistname.get(check));
                    	
                    	if (ChatEmojisLoad.emojislistperm.containsKey(check)) {
                    		if (!p.hasPermission(ChatEmojisLoad.emojislistperm.get(check))) {
                    			continue;
                    		}
                    	}
                    	
                    	if (stringcheck.contains(string)) {
                    		continue;
                    	} else {
                    		stringcheck.add(string);
                    	}
                    	
                    	Displayname = EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Title");
                    	Displayname = Displayname.replaceAll("&", "§");
                    	if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                            Displayname = PlaceholderAPI.setPlaceholders(p, Displayname);
                        }
                    	
                    	if (EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL") ||
                                EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                                EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                                EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                                if (EmojisListCUtility.getConfig().isSet("Emojis-list." + string + ".Gui.Skull-Name")) {
                                    String skullname = EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Skull-Name");
                                    skullname = skullname.replaceAll("%player%", p.getName());
                                    ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
                                    metaref1 = ref1.getItemMeta();
                                    meta = (SkullMeta) ref1.getItemMeta();
                                    meta.setOwner(skullname);
                                } else {
                                    ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial());
                                    metaref1 = ref1.getItemMeta();
                                }
                            } else {

                            	if (EmojisListCUtility.getConfig().isSet("Emojis-list." + string + ".Gui.Data-value")) {
                        			ref1 = new ItemStack(XMaterial.getMat(EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material"), "Emojis-list." + string + ".Gui.Material"), 1, (short) OnChatConfig.getConfig().getInt("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Data-value"));
                        		} else {
                        			ref1 = new ItemStack(XMaterial.getMat(EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material"), "Emojis-list." + string + ".Gui.Material"));
                        		}
                                metaref1 = ref1.getItemMeta();
                            }

                            if (EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL") ||
                                EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                                EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                                EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                                if (EmojisListCUtility.getConfig().isSet("Emojis-list." + string + ".Gui.Skull-Name")) {
                                    if (EmojisListCUtility.getConfig().isSet("Emojis-list." + string + ".Gui.Lore")) {

                                        ArrayList < String > lore = new ArrayList < String > ();

                                        for (String loremsg: EmojisListCUtility.getConfig().getStringList("Emojis-list." + string + ".Gui.Lore")) {
                                            loremsg = loremsg.replaceAll("&", "§");
                                            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                                                loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                                            }

                                            lore.add(loremsg);
                                        }

                                        meta.setLore(lore);
                                    }

                                    meta.setDisplayName(Displayname);


                                    ref1.setItemMeta(meta);
                                    inv.setItem(number_check_place, ref1);

                                    number_check_place++;
                                } else {
                                    if (EmojisListCUtility.getConfig().isSet("Emojis-list." + string + ".Gui.Lore")) {

                                        ArrayList < String > lore = new ArrayList < String > ();

                                        for (String loremsg: EmojisListCUtility.getConfig().getStringList("Emojis-list." + string + ".Gui.Lore")) {
                                            loremsg = loremsg.replaceAll("&", "§");
                                            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                                                loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                                            }

                                            lore.add(loremsg);
                                        }

                                        metaref1.setLore(lore);
                                    }

                                    metaref1.setDisplayName(Displayname);


                                    ref1.setItemMeta(metaref1);
                                    inv.setItem(number_check_place, ref1);

                                    number_check_place++;
                                }

                            } else {
                                if (EmojisListCUtility.getConfig().isSet("Emojis-list." + string + ".Gui.Lore")) {

                                    ArrayList < String > lore = new ArrayList < String > ();

                                    for (String loremsg: EmojisListCUtility.getConfig().getStringList("Emojis-list." + string + ".Gui.Lore")) {
                                        loremsg = loremsg.replaceAll("&", "§");
                                        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                                            loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                                        }

                                        lore.add(loremsg);
                                    }

                                    metaref1.setLore(lore);
                                }

                                metaref1.setDisplayName(Displayname);


                                ref1.setItemMeta(metaref1);
                                inv.setItem(number_check_place, ref1);

                                number_check_place++;
                            }
                    	
                    	boucle_loop++;
                    }     
                

                // Close Gui

                if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Enable")) {

                    Displayname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title");
                    Displayname = Displayname.replaceAll("&", "§");
                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                        Displayname = PlaceholderAPI.setPlaceholders(p, Displayname);
                    }

                    if (OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("SKULL") ||
                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Skull-Name")) {
                            String skullname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Skull-Name");
                            skullname = skullname.replaceAll("%player%", p.getName());
                            ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial(), 1, (short) SkullType.PLAYER.ordinal());
                            metaref1 = ref1.getItemMeta();
                            meta = (SkullMeta) ref1.getItemMeta();
                            meta.setOwner(skullname);
                        } else {
                            ref1 = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial());
                            metaref1 = ref1.getItemMeta();
                        }
                    } else {
                    	if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Data-value")) {
                			ref1 = new ItemStack(XMaterial.getMat(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material"), "Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material"), 1, (short) OnChatConfig.getConfig().getInt("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Data-value"));
                		}else {
                			ref1 = new ItemStack(XMaterial.getMat(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material"), "Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material"));
                		}
                        metaref1 = ref1.getItemMeta();
                    }

                    if (OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("SKULL") ||
                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Skull-Name")) {
                            if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore")) {

                                ArrayList < String > lore = new ArrayList < String > ();

                                for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore")) {
                                    loremsg = loremsg.replaceAll("&", "§");
                                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                                    }

                                    lore.add(loremsg);
                                }

                                meta.setLore(lore);
                            }

                            meta.setDisplayName(Displayname);


                            ref1.setItemMeta(meta);
                        } else {
                            if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore")) {

                                ArrayList < String > lore = new ArrayList < String > ();

                                for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore")) {
                                    loremsg = loremsg.replaceAll("&", "§");
                                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                                        loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                                    }

                                    lore.add(loremsg);
                                }

                                metaref1.setLore(lore);
                            }

                            metaref1.setDisplayName(Displayname);


                            ref1.setItemMeta(metaref1);
                        }

                    } else {
                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore")) {

                            ArrayList < String > lore = new ArrayList < String > ();

                            for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Lore")) {
                                loremsg = loremsg.replaceAll("&", "§");
                                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                                    loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                                }

                                lore.add(loremsg);
                            }

                            metaref1.setLore(lore);
                        }

                        metaref1.setDisplayName(Displayname);


                        ref1.setItemMeta(metaref1);
                    }

                    inv.setItem(54 - 9, ref1);
                }

                p.openInventory(inv);
                p.updateInventory();

        return false;
    }
}
