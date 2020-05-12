package fr.dianox.hawn.event;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.dianox.hawn.utility.WorldGuardUtils;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.events.WorldEventConfig;
import fr.dianox.hawn.utility.world.WorldPW;

public class WorldEvent implements Listener {

    String path_wg = "";

    //Disable Thunder
    @EventHandler
    public void EntityPortal(EntityPortalEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("DenyEntityTravelPortal.Enable")) {
            if (!WorldEventConfig.getConfig().getBoolean("DenyEntityTravelPortal.World.All_World")) {
                if (WorldPW.getPreventionEtityPortal().contains(e.getEntity().getWorld().getName())) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }
    
    // Disable Weather
    @EventHandler
    public void WeatherDisable(WeatherChangeEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.Weather.Enable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.Weather.World.All_World")) {
                if (WorldPW.getWW().contains(e.getWorld().getName())) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    //Disable Thunder
    @EventHandler
    public void Thunder(ThunderChangeEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.ThunderChange.Enable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.ThunderChange.World.All_World")) {
                if (WorldPW.getWTC().contains(e.getWorld().getName())) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    // Anti lightning
    @EventHandler
    public void onLightNing(LightningStrikeEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.Disable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.World.All_World")) {
                if (WorldPW.getWLS().contains(e.getLightning().getWorld().getName())) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    // Block Burn
    @EventHandler
    public void BlockBurn(BlockBurnEvent e) {
    	path_wg = "World.Burn.Disable.Burn-Block.";
    	
        if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.Disable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.World.All_World")) {
                if (WorldPW.getWBB().contains(e.getBlock().getWorld().getName())) {
                	/*
                     * WorldGuard
                     */
                    if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
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
                if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                    if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                e.setCancelled(true);

                                break;
                            }
                        }
                    } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                        String check = "";

                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
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

    @EventHandler
    public void FireSpread(BlockIgniteEvent e) {
    	path_wg = "World.Burn.Disable.BlockIgnite-FireSpread.";
    	
        if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.Disable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.World.All_World")) {
                if (WorldPW.getWFS().contains(e.getBlock().getWorld().getName())) {
                	/*
                     * WorldGuard
                     */
                    if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                	if (e.getCause() == IgniteCause.SPREAD) {
                                        e.setCancelled(true);
                                    }

                                    break;
                                }
                            }
                        } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                    check = "true";
                                }
                            }

                            if (check.contains("true")) {
                                return;
                            } else {
                            	if (e.getCause() == IgniteCause.SPREAD) {
                                    e.setCancelled(true);
                                }
                            }
                        }
                    } else {
                        /* The event */

                    	if (e.getCause() == IgniteCause.SPREAD) {
                            e.setCancelled(true);
                        }
                    }
                }
            } else {
            	/*
                 * WorldGuard
                 */
                if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                    if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                            	if (e.getCause() == IgniteCause.SPREAD) {
                                    e.setCancelled(true);
                                }

                                break;
                            }
                        }
                    } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                        String check = "";

                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getBlock().getLocation()).contains("id='" + s + "'")) {
                                check = "true";
                            }
                        }

                        if (check.contains("true")) {
                            return;
                        } else {
                        	if (e.getCause() == IgniteCause.SPREAD) {
                                e.setCancelled(true);
                            }
                        }
                    }
                } else {
                    /* The event */

                	if (e.getCause() == IgniteCause.SPREAD) {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    // Explosion
    @EventHandler
    public void onExplosion(ExplosionPrimeEvent e) {
        path_wg = "World.Explosion.Disable.Explosion.";

        if (WorldEventConfig.getConfig().getBoolean("World.Explosion.Disable.Explosion.Disable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Explosion.Disable.Explosion.World.All_World")) {
                if (WorldPW.getWE().contains(e.getEntity().getWorld().getName())) {
                    /*
                     * WorldGuard
                     */
                    if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
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
                if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                    if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                e.setCancelled(true);

                                break;
                            }
                        }
                    } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                        String check = "";

                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
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

    // Anti leave decay
    @EventHandler
    public void onLeaveDecay(LeavesDecayEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Leave-Decay.Disable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Leave-Decay.World.All_World")) {
                if (WorldPW.getWLD().contains(e.getBlock().getWorld().getName())) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }

    // Disable Spawning
    @EventHandler
    public void onSpawning(CreatureSpawnEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("World.Disable.Spawning-Monster-Animals.Disable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Disable.Spawning-Monster-Animals.World.All_World")) {
                if (WorldPW.getWSMA().contains(e.getEntity().getWorld().getName())) {
                    if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                    e.setCancelled(true);
                }
            } else {
                if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                e.setCancelled(true);
            }
        }
    }

    // BLock Fade and Farm
    @EventHandler
    public void onBlockFade(BlockFadeEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Block-Fade.Disable")) {
            if (!WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Block-Fade.World.All_World")) {
                if (WorldPW.getWBF().contains(e.getBlock().getWorld().getName())) {
                    e.setCancelled(true);
                }
            } else {
                e.setCancelled(true);
            }
        }
    }
    
    // No sheep shears
    @EventHandler
    public void NoShears(PlayerShearEntityEvent e) {
    	path_wg = "No-Shears.";
    	
        if (WorldEventConfig.getConfig().getBoolean("No-Shears.Enable")) {
        	
        	if (WorldEventConfig.getConfig().getBoolean("No-Shears.Bypass")) {
	        	if (e.getPlayer().hasPermission("hawn.bypass.world.event.shears")) {
	        		return;
	        	}
        	}
        	
            if (!WorldEventConfig.getConfig().getBoolean("No-Shears.World.All_World")) {
                if (WorldPW.getShears().contains(e.getPlayer().getWorld().getName())) {
                	/*
                     * WorldGuard
                     */
                    if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                        if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
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
                if (WorldEventConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.WorldGuard.Enable")) {
                    if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
                                e.setCancelled(true);

                                break;
                            }
                        }
                    } else if (WorldEventConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                        String check = "";

                        for (String s: WorldEventConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                            if (WorldGuardUtils.getRegion(e.getEntity().getLocation()).contains("id='" + s + "'")) {
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