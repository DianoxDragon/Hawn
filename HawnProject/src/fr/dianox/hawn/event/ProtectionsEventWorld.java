package fr.Dianox.Hawn.Event;

import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.Utility.MessageUtils;
import fr.dianox.hawn.Utility.WorldGuardUtils;
import fr.dianox.hawn.Utility.XMaterial;
import fr.dianox.hawn.Utility.Config.ConfigGeneral;
import fr.dianox.hawn.Utility.Config.Events.ConfigGProtection;
import fr.dianox.hawn.Utility.Config.Messages.ConfigMProtection;
import fr.dianox.hawn.Utility.World.ProtectionPW;

public class ProtectionsEventWorld implements Listener {

    String path_wg = "";

    @EventHandler
    public void PlayerBucketFillEvent(PlayerBucketFillEvent e) {
    	path_wg = "Protection.Anti-Bucket-Use.";
        
        Player p = e.getPlayer();
        
		if (Main.buildbypasscommand.contains(p)) {
            return;
        }
		
        if (ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.Enable")) {
            if (!ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.World.All_World")) {
                if (ProtectionPW.getWB().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.Bypass")) {
                        if (!p.hasPermission("hawn.bypass.protection.buckets")) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);
                                    }
                                }
                            } else {
                                /* The event */
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            /* The event */
                            e.setCancelled(true);
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.Bypass")) {
                    if (!p.hasPermission("hawn.bypass.protection.buckets")) {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            /* The event */
                            e.setCancelled(true);
                        }
                    }
                } else {
                    /*
                     * WorldGuard
                     */
                    if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        /* The event */
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void PlayerBucketEmptyEvent(PlayerBucketEmptyEvent e) {
    	path_wg = "Protection.Anti-Bucket-Use.";
        
        Player p = e.getPlayer();
        
		if (Main.buildbypasscommand.contains(p)) {
            return;
        }
		
        if (ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.Enable")) {
            if (!ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.World.All_World")) {
                if (ProtectionPW.getWB().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.Bypass")) {
                        if (!p.hasPermission("hawn.bypass.protection.buckets")) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);
                                    }
                                }
                            } else {
                                /* The event */
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            /* The event */
                            e.setCancelled(true);
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.Bypass")) {
                    if (!p.hasPermission("hawn.bypass.protection.buckets")) {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            /* The event */
                            e.setCancelled(true);
                        }
                    }
                } else {
                    /*
                     * WorldGuard
                     */
                    if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        /* The event */
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
  
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        path_wg = "Protection.Construct.Anti-Place.";

        if (Main.buildbypasscommand.contains(p)) {
            return;
        }

        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Enable")) {
        	
        	if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Block-Exception.Enable")) {
        		
        		for (Material m : Main.block_exception_place) {
        			if (e.getBlockPlaced().getType() == m) {
        				return;
        			}
        		}
        	}
        	
            if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.World.All_World")) {
                if (ProtectionPW.getWPCP().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
                        if (!p.hasPermission("hawn.event.construct.bypass.place")) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }
                                    }
                                }
                            } else {
                                /* The event */

                                e.setCancelled(true);

                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                    }
                                }
                            }
                        }
                    } else {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }
                                }
                            }
                        } else {
                            /* The event */

                            e.setCancelled(true);

                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                }
                            }
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
                    if (!p.hasPermission("hawn.event.construct.bypass.place")) {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }
                                }
                            }
                        } else {
                            /* The event */

                            e.setCancelled(true);

                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                }
                            }
                        }
                    }
                } else {
                    /*
                     * WorldGuard
                     */
                    if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }

                                    break;
                                }
                            }
                        } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlockPlaced().getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                                e.setCancelled(true);

                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                    }
                                }
                            }
                        }
                    } else {
                        /* The event */

                        e.setCancelled(true);

                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlace2(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material m = p.getItemInHand().getType();

        path_wg = "Protection.Construct.Anti-Place.";

        if (Main.buildbypasscommand.contains(p)) {
            return;
        }

        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Enable")) {
            if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.World.All_World")) {
                if (ProtectionPW.getWPCP().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
                        if (!p.hasPermission("hawn.event.construct.bypass.place")) {
                            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                if (m == XMaterial.ARMOR_STAND.parseMaterial()) {
                                    /*
                                     * WorldGuard
                                     */
                                    if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                        if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                                if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                                    e.setCancelled(true);

                                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                        }
                                                    }

                                                    break;
                                                }
                                            }
                                        } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                            String check = "";

                                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                                if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                                    check = "true";
                                                }
                                            }

                                            if (check.contains("true")) {
                                                return;
                                            } else {
                                                e.setCancelled(true);

                                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        /* The event */

                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (m == XMaterial.ARMOR_STAND.parseMaterial()) {
                                /*
                                 * WorldGuard
                                 */
                                if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                    if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                        for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                            if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                                e.setCancelled(true);

                                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                    }
                                                }

                                                break;
                                            }
                                        }
                                    } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                        String check = "";

                                        for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                            if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                                check = "true";
                                            }
                                        }

                                        if (check.contains("true")) {
                                            return;
                                        } else {
                                            e.setCancelled(true);

                                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    /* The event */

                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
                    if (!p.hasPermission("hawn.event.construct.bypass.place")) {
                        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if (m == XMaterial.ARMOR_STAND.parseMaterial()) {
                                /*
                                 * WorldGuard
                                 */
                                if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                    if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                        for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                            if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                                e.setCancelled(true);

                                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                    }
                                                }

                                                break;
                                            }
                                        }
                                    } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                        String check = "";

                                        for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                            if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                                check = "true";
                                            }
                                        }

                                        if (check.contains("true")) {
                                            return;
                                        } else {
                                            e.setCancelled(true);

                                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    /* The event */

                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        if (m == XMaterial.ARMOR_STAND.parseMaterial()) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }
                                    }
                                }
                            } else {
                                /* The event */

                                e.setCancelled(true);

                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        path_wg = "Protection.Construct.Anti-Break.";

        if (Main.buildbypasscommand.contains(p)) {
            return;
        }

        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Enable")) {
        	
        	if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Block-Exception.Enable")) {
        		
        		for (Material m2 : Main.block_exception_break) {
        			if (e.getBlock().getType() == m2) {
        				return;
        			}
        		}
        	}
        	
            if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.World.All_World")) {
                if (ProtectionPW.getWPCB().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
                        if (!p.hasPermission("hawn.event.construct.bypass.break")) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }
                                    }
                                }
                            } else {
                                /* The event */

                                e.setCancelled(true);

                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                    }
                                }
                            }
                        }
                    } else {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }
                                }
                            }
                        } else {
                            /* The event */

                            e.setCancelled(true);

                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                }
                            }
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
                    if (!p.hasPermission("hawn.event.construct.bypass.break")) {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }
                                }
                            }
                        } else {
                            /* The event */

                            e.setCancelled(true);

                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                }
                            }
                        }
                    }
                } else {
                    /*
                     * WorldGuard
                     */
                    if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }

                                    break;
                                }
                            }
                        } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                                e.setCancelled(true);

                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                    }
                                }
                            }
                        }
                    } else {
                        /* The event */

                        e.setCancelled(true);

                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                            for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onHanging(HangingBreakByEntityEvent e) {
    	    	
        path_wg = "Protection.HagingBreakByEntity.";
                
        Player p = (Player) e.getRemover();
        
		if (Main.buildbypasscommand.contains(p)) {
            return;
        }
		
        if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Enable")) {
            if (!ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.World.All_World")) {
                if (ProtectionPW.getWHBBE().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Bypass")) {
                        if (!p.hasPermission("hawn.bypass.HagingBreakByEntity")) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);
                                    }
                                }
                            } else {
                                /* The event */
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            /* The event */
                            e.setCancelled(true);
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Bypass")) {
                    if (!p.hasPermission("hawn.bypass.HagingBreakByEntity")) {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            /* The event */
                            e.setCancelled(true);
                        }
                    }
                } else {
                    /*
                     * WorldGuard
                     */
                    if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(p.getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        /* The event */
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onplayerinteract(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();

        path_wg = "Protection.PlayerInteractEntity-ItemFrame.";

        if (Main.buildbypasscommand.contains(p)) {
            return;
        }

        if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.Enable")) {
            if (!ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.World.All_World")) {
                if (ProtectionPW.getWPIEIF().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.Bypass")) {
                        if (!p.hasPermission("hawn.bypass.PlayerInteractEntity")) {
                            if ((e.getRightClicked() instanceof ItemFrame)) {
                                /*
                                 * WorldGuard
                                 */
                                if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                    if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                        for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                            if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                                e.setCancelled(true);

                                                break;
                                            }
                                        }
                                    } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                        String check = "";

                                        for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                            if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                                check = "true";
                                            }
                                        }

                                        if (check.contains("true")) {
                                            return;
                                        } else {
                                            e.setCancelled(true);
                                        }
                                    }
                                } else {
                                    /* The event */
                                    e.setCancelled(true);
                                }
                            }
                        }
                    } else {
                        if ((e.getRightClicked() instanceof ItemFrame)) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);
                                    }
                                }
                            } else {
                                /* The event */
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.Bypass")) {
                    if (!p.hasPermission("hawn.bypass.PlayerInteractEntity")) {
                        if ((e.getRightClicked() instanceof ItemFrame)) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                            e.setCancelled(true);

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        e.setCancelled(true);
                                    }
                                }
                            } else {
                                /* The event */
                                e.setCancelled(true);
                            }
                        }
                    }
                } else {
                    if ((e.getRightClicked() instanceof ItemFrame)) {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getRightClicked().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    e.setCancelled(true);
                                }
                            }
                        } else {
                            /* The event */
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
    
    @EventHandler
    public void onplayerinteractblocksitems(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        path_wg = "Protection.PlayerInteract-Items-Blocks.";

        if (Main.buildbypasscommand.contains(p)) {
            return;
        }

        if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Enable")) {
            if (!ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.World.All_World")) {
                if (ProtectionPW.getWPIIB().contains(p.getWorld().getName())) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Bypass")) {
                        if (!p.hasPermission("hawn.event.construct.bypass.protectionitemblocks")) {
                            /*
                             * WorldGuard
                             */
                            if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                                if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                        	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                        		for (Material m : Main.interactables) {
                                        			if (e.getClickedBlock().getType() == m) {
                                        				e.setCancelled(true);
                                        			}
                                        		}
                                        	}

                                            break;
                                        }
                                    }
                                } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                    		for (Material m : Main.interactables) {
                                    			if (e.getClickedBlock().getType() == m) {
                                    				e.setCancelled(true);
                                    			}
                                    		}
                                    	}
                                    }
                                }
                            } else {
                                /* The event */

                            	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            		for (Material m : Main.interactables) {
                            			if (e.getClickedBlock().getType() == m) {
                            				e.setCancelled(true);
                            			}
                            		}
                            	}
                            }
                        }
                    } else {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                    		for (Material m : Main.interactables) {
                                    			if (e.getClickedBlock().getType() == m) {
                                    				e.setCancelled(true);
                                    			}
                                    		}
                                    	}

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                		for (Material m : Main.interactables) {
                                			if (e.getClickedBlock().getType() == m) {
                                				e.setCancelled(true);
                                			}
                                		}
                                	}
                                }
                            }
                        } else {
                            /* The event */

                        	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        		for (Material m : Main.interactables) {
                        			if (e.getClickedBlock().getType() == m) {
                        				e.setCancelled(true);
                        			}
                        		}
                        	}
                        }
                    }
                }
            } else {
                if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Bypass")) {
                    if (!p.hasPermission("hawn.event.construct.bypass.protectionitemblocks")) {
                        /*
                         * WorldGuard
                         */
                        if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                            if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                    		for (Material m : Main.interactables) {
                                    			if (e.getClickedBlock().getType() == m) {
                                    				e.setCancelled(true);
                                    			}
                                    		}
                                    	}

                                        break;
                                    }
                                }
                            } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                		for (Material m : Main.interactables) {
                                			if (e.getClickedBlock().getType() == m) {
                                				e.setCancelled(true);
                                			}
                                		}
                                	}
                                }
                            }
                        } else {
                            /* The event */

                        	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        		for (Material m : Main.interactables) {
                        			if (e.getClickedBlock().getType() == m) {
                        				e.setCancelled(true);
                        			}
                        		}
                        	}
                        }
                    }
                } else {
                    /*
                     * WorldGuard
                     */
                    if (ConfigGProtection.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                                		for (Material m : Main.interactables) {
                                			if (e.getClickedBlock().getType() == m) {
                                				e.setCancelled(true);
                                			}
                                		}
                                	}

                                    break;
                                }
                            }
                        } else if (ConfigGProtection.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: ConfigGProtection.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getClickedBlock().getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                            	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            		for (Material m : Main.interactables) {
                            			if (e.getClickedBlock().getType() == m) {
                            				e.setCancelled(true);
                            			}
                            		}
                            	}
                            }
                        }
                    } else {
                        /* The event */

                    	if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    		for (Material m : Main.interactables) {
                    			if (e.getClickedBlock().getType() == m) {
                    				e.setCancelled(true);
                    			}
                    		}
                    	}
                    }
                }
            }
        }
    }
    
}