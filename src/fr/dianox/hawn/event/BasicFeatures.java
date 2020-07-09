package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.onjoin.OnJoin;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.events.ConfigGProtection;
import fr.dianox.hawn.utility.config.configs.events.PlayerEventsConfig;
import fr.dianox.hawn.utility.config.configs.events.ProtectionPlayerConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import fr.dianox.hawn.utility.world.ProtectionPW;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class BasicFeatures implements Listener {

	String path_wg = "";
	
    // Can't change Game mode
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();

        if (OnJoin.player_list.contains(p)) {
            if (PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.Enable")) {
                if (PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.Bypass-With-Permission")) {
                    if (!p.hasPermission("hawn.bypass.keepgamemode")) {
                        if (PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.World.All_World")) {
                            e.setCancelled(true);
                        } else {
                            if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
                                e.setCancelled(true);
                            }
                        }
                    }
                } else {
                    if (PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.World.All_World")) {
                        e.setCancelled(true);
                    } else {
                        if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    // Keep food and health
    @EventHandler
    public void onChangeFood(FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();

        if (PlayerEventsConfig.getConfig().getBoolean("Keep.Food.Enable")) {
            if (PlayerEventsConfig.getConfig().getBoolean("Keep.Food.World.All_World")) {
                if (PlayerEventsConfig.getConfig().getBoolean("Keep.Food.Bypass-With-Permission")) {
                    if (! p.hasPermission("hawn.bypass.foodkeep")) {
                        if (e.getEntityType() == EntityType.PLAYER) {
                            e.setCancelled(true);
                        }
                    }
                } else {
                    if (e.getEntityType() == EntityType.PLAYER) {
                        e.setCancelled(true);
                    }
                }
            } else {
                if (BasicEventsPW.getWkFood().contains(p.getWorld().getName())) {
                    if (PlayerEventsConfig.getConfig().getBoolean("Keep.Food.Bypass-With-Permission")) {
                        if (! p.hasPermission("hawn.bypass.foodkeep")) {
                            if (e.getEntityType() == EntityType.PLAYER) {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        if (e.getEntityType() == EntityType.PLAYER) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void EntityDamageEvent(EntityDamageEvent e) {
        path_wg = "Anti-Damage.";

        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Enable")) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.World.All_World")) {
                if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Bypass-With-Permission")) {
                    if (e.getEntity() instanceof Player && ! e.getEntity().hasPermission("hawn.bypass.antidamage")) {
                        /*
                         * WorldGuard
                         */
                        if (ProtectionPlayerConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                            if (e.getEntity() instanceof Player) {
                                                Damage(e);
                                            }
                                        } else {
                                            if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                            e.setCancelled(true);
                                        }

                                        break;
                                    }
                                }
                            } else if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                        if (e.getEntity() instanceof Player) {
                                            Damage(e);
                                        }
                                    } else {
                                        if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                        e.setCancelled(true);
                                    }
                                }
                            }
                        } else {
                            /* The event */

                            if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                if (e.getEntity() instanceof Player) {
                                    Damage(e);
                                }
                            } else {
                                if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                e.setCancelled(true);
                            }
                        }
                    }
                } else {
                    /*
                     * WorldGuard
                     */
                    if (ProtectionPlayerConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                        if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                    if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                        if (e.getEntity() instanceof Player) {
                                            Damage(e);
                                        }
                                    } else {
                                        if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                        e.setCancelled(true);
                                    }

                                    break;
                                }
                            }
                        } else if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                                if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                    if (e.getEntity() instanceof Player) {
                                        Damage(e);
                                    }
                                } else {
                                    if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                    e.setCancelled(true);
                                }
                            }
                        }
                    } else {
                        /* The event */

                        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                            if (e.getEntity() instanceof Player) {
                                Damage(e);
                            }
                        } else {
                            if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                            e.setCancelled(true);
                        }
                    }
                }
            } else {
                if (BasicEventsPW.getWkHealth().contains(e.getEntity().getLocation().getWorld().getName())) {
                    if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Bypass-With-Permission")) {
                        if (e.getEntity() instanceof Player && ! e.getEntity().hasPermission("hawn.bypass.antidamage")) {
                            /*
                             * WorldGuard
                             */
                            if (ProtectionPlayerConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                                if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                    for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                            if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                                if (e.getEntity() instanceof Player) {
                                                    Damage(e);
                                                }
                                            } else {
                                                if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                                e.setCancelled(true);
                                            }

                                            break;
                                        }
                                    }
                                } else if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                    String check = "";

                                    for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                        if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                            check = "true";
                                        }
                                    }

                                    if (check.contains("true")) {
                                        return;
                                    } else {
                                        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                            if (e.getEntity() instanceof Player) {
                                                Damage(e);
                                            }
                                        } else {
                                            if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                            e.setCancelled(true);
                                        }
                                    }
                                }
                            } else {
                                /* The event */

                                if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                    if (e.getEntity() instanceof Player) {
                                        Damage(e);
                                    }
                                } else {
                                    if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                    e.setCancelled(true);
                                }
                            }
                        }
                    } else {
                        /*
                         * WorldGuard
                         */
                        if (ProtectionPlayerConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                            if (e.getEntity() instanceof Player) {
                                                Damage(e);
                                            }
                                        } else {
                                            if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                            e.setCancelled(true);
                                        }

                                        break;
                                    }
                                }
                            } else if (ProtectionPlayerConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s : ProtectionPlayerConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                        check = "true";
                                    }
                                }

                                if (check.contains("true")) {
                                    return;
                                } else {
                                    if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                        if (e.getEntity() instanceof Player) {
                                            Damage(e);
                                        }
                                    } else {
                                        if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                        e.setCancelled(true);
                                    }
                                }
                            }
                        } else {
                            /* The event */

                            if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                if (e.getEntity() instanceof Player) {
                                    Damage(e);
                                }
                            } else {
                                if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

    public void Damage(EntityDamageEvent e) {
        if (Main.getInstance().getEventManager().getDamageEventList().contains(e.getCause())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof ArmorStand) {
            String player = String.valueOf(e.getDamager());

            if (player.contains("CraftPlayer")) {
                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Enable")) {
                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.World.All_World")) {
                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
                            if (! e.getDamager().hasPermission("hawn.event.construct.bypass.break")) {
                                e.setCancelled(true);
                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                    for (String msg : ConfigMMsg.getConfig().getStringList("Protection.Anti-Break")) {
                                        ConfigEventUtils.ExecuteEvent((Player) e.getDamager(), msg, "", "", false);
                                    }
                                }
                            }
                        } else {
                            e.setCancelled(true);
                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                for (String msg : ConfigMMsg.getConfig().getStringList("Protection.Anti-Break")) {
                                    ConfigEventUtils.ExecuteEvent((Player) e.getDamager(), msg, "", "", false);
                                }
                            }
                        }
                    } else {
                        if (ProtectionPW.getWPCB().contains(e.getDamager().getWorld().getName())) {
                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
                                if (! e.getDamager().hasPermission("hawn.event.construct.bypass.break")) {
                                    e.setCancelled(true);
                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                        for (String msg : ConfigMMsg.getConfig().getStringList("Protection.Anti-Break")) {
                                            ConfigEventUtils.ExecuteEvent((Player) e.getDamager(), msg, "", "", false);
                                        }
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                    for (String msg : ConfigMMsg.getConfig().getStringList("Protection.Anti-Break")) {
                                        ConfigEventUtils.ExecuteEvent((Player) e.getDamager(), msg, "", "", false);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Enable")) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.Entity.EntityDamageByEntity")) {
                    if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.World.All_World")) {
                        if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                        e.setCancelled(true);
                        if (e.getEntity() instanceof ItemFrame) {
                            e.setCancelled(true);
                        }
                        if (e.getEntity() instanceof ArmorStand) {
                            e.setCancelled(false);
                        }
                    } else {
                        if (BasicEventsPW.getWkHealth().contains(e.getEntity().getLocation().getWorld().getName())) {
                            if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                            e.setCancelled(true);
                            if (e.getEntity() instanceof ItemFrame) {
                                e.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }

}