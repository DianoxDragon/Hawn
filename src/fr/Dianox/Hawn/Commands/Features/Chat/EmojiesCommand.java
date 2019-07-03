package fr.Dianox.Hawn.Commands.Features.Chat;

import java.util.ArrayList;
import java.util.Iterator;

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

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.Commands.EmojiCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import me.clip.placeholderapi.PlaceholderAPI;

@SuppressWarnings("deprecation")
public class EmojiesCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.use.gui.emojilist";
	public static Integer emojis_total_enabled = 0;
	
	public EmojiesCommand(String name) {
		super(name);
		this.description = "Open the emoji gui";
        this.usageMessage = "/emoji";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
        	if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg, sender);
				}
			}
            return true;

        }

        @SuppressWarnings("unused")
        int menu = 0;
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
				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
        

                // Count
                if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Enable")) {
                    Iterator < ? > iterator3 = OnChatConfig.getConfig().getConfigurationSection("Chat-Emoji-Player.Emojis-list").getKeys(false).iterator();

                    emojis_total_enabled = 0;

                    while (iterator3.hasNext()) {
                        String string = (String) iterator3.next();
                        if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list." + string + ".Enable")) {
                            if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list." + string + ".Use_Permission")) {
                                if (p.hasPermission("hawn.useemoji." + string)) {
                                    emojis_total_enabled++;
                                }
                            } else {
                                emojis_total_enabled++;
                            }
                        }
                    }

                }

                // Menu type
                if (emojis_total_enabled <= 9) {
                    menu = 1;
                    number = 18;
                } else if (emojis_total_enabled <= 18) {
                    menu = 2;
                    number = 27;
                } else if (emojis_total_enabled <= 27) {
                    menu = 3;
                    number = 36;
                } else if (emojis_total_enabled <= 36) {
                    menu = 4;
                    number = 47;
                } else if (emojis_total_enabled <= 47) {
                    menu = 5;
                    number = 54;
                }

                String titlegui = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Title");
                titlegui = titlegui.replaceAll("&", "§");

                Inventory inv = Bukkit.createInventory(null, number, titlegui);

                // List
                Iterator < ? > iterator = OnChatConfig.getConfig().getConfigurationSection("Chat-Emoji-Player.Emojis-list").getKeys(false).iterator();

                while (iterator.hasNext()) {
                    String string = (String) iterator.next();

                    if (!string.equalsIgnoreCase("Option")) {
                        if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list." + string + ".Enable")) {
                            if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list." + string + ".Use_Permission")) {
                                if (p.hasPermission("hawn.useemoji." + string)) {
                                    Displayname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Title");
                                    Displayname = Displayname.replaceAll("&", "§");
                                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                                        Displayname = PlaceholderAPI.setPlaceholders(p, Displayname);
                                    }

                                    if (OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL") ||
                                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Skull-Name")) {
                                            String skullname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Skull-Name");
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

                                    	if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Data-value")) {
                                			ref1 = new ItemStack(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material")).parseMaterial(), 1, (short) OnChatConfig.getConfig().getInt("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Data-value"));
                                		} else {
                                			ref1 = new ItemStack(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material")).parseMaterial());
                                		}
                                        metaref1 = ref1.getItemMeta();
                                    }

                                    if (OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL") ||
                                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                                        OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Skull-Name")) {
                                            if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {

                                                ArrayList < String > lore = new ArrayList < String > ();

                                                for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {
                                                    loremsg = loremsg.replaceAll("&", "§");
                                                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                                            if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {

                                                ArrayList < String > lore = new ArrayList < String > ();

                                                for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {
                                                    loremsg = loremsg.replaceAll("&", "§");
                                                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {

                                            ArrayList < String > lore = new ArrayList < String > ();

                                            for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {
                                                loremsg = loremsg.replaceAll("&", "§");
                                                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                                }
                            } else {
                                Displayname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Title");
                                Displayname = Displayname.replaceAll("&", "§");
                                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                                    Displayname = PlaceholderAPI.setPlaceholders(p, Displayname);
                                }


                                if (OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL") ||
                                    OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                                    OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                                    OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                                    if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Skull-Name")) {
                                        String skullname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Skull-Name");
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
                                	
                                	if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Data-value")) {
                            			ref1 = new ItemStack(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material")).parseMaterial(), 1, (short) OnChatConfig.getConfig().getInt("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Data-value"));
                            		}else {
                            			ref1 = new ItemStack(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material")).parseMaterial());
                            		}
                                	
                                    ref1 = new ItemStack(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material")).parseMaterial());
                                    metaref1 = ref1.getItemMeta();
                                }

                                if (OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL") ||
                                    OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL") ||
                                    OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("SKULL_ITEM") ||
                                    OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Material").equalsIgnoreCase("LEGACY_SKULL_ITEM")) {

                                    if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Skull-Name")) {
                                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {

                                            ArrayList < String > lore = new ArrayList < String > ();

                                            for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {
                                                loremsg = loremsg.replaceAll("&", "§");
                                                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                                        if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {

                                            ArrayList < String > lore = new ArrayList < String > ();

                                            for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {
                                                loremsg = loremsg.replaceAll("&", "§");
                                                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                                    if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {

                                        ArrayList < String > lore = new ArrayList < String > ();

                                        for (String loremsg: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Gui.Lore")) {
                                            loremsg = loremsg.replaceAll("&", "§");
                                            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                            }
                        }

                        boucle_loop++;
                    }
                }

                // Close Gui

                if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Enable")) {

                    Displayname = OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Title");
                    Displayname = Displayname.replaceAll("&", "§");
                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                			ref1 = new ItemStack(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material")).parseMaterial(), 1, (short) OnChatConfig.getConfig().getInt("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Data-value"));
                		}else {
                			ref1 = new ItemStack(XMaterial.matchXMaterial(OnChatConfig.getConfig().getString("Chat-Emoji-Player.Emojis-list.Option.Gui.Close-Gui.Material")).parseMaterial());
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
                                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
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
                                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                                    loremsg = PlaceholderAPI.setPlaceholders(p, loremsg);
                                }

                                lore.add(loremsg);
                            }

                            metaref1.setLore(lore);
                        }

                        metaref1.setDisplayName(Displayname);


                        ref1.setItemMeta(metaref1);
                    }

                    inv.setItem(number - 9, ref1);
                }

                p.openInventory(inv);
                p.updateInventory();

        return false;
    }
}
