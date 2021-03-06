package fr.dianox.hawn.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.SpawnUtils;

import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.events.PlayerEventsConfig;
import fr.dianox.hawn.utility.world.PlayerEventsPW;

@SuppressWarnings("deprecation")
public class PlayerEvents implements Listener {
	
	String path_wg = "";
	
	@EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player p = e.getPlayer();

        path_wg = "Items.Drop.";
        
        if (Main.buildbypasscommand.contains(p)) {
        	return;
        }
        
    	if (PlayerEventsConfig.getConfig().getBoolean("Items.Drop.Disable")) {
    		if (!PlayerEventsConfig.getConfig().getBoolean("Items.Drop.World.All_World")) {
    			if (PlayerEventsPW.getWItemDrop().contains(p.getWorld().getName())) {
	    			if (PlayerEventsConfig.getConfig().getBoolean("Items.Drop.Bypass")) {
	                    if (!p.hasPermission("hawn.event.playeritem.bypass.drop")) {
	                    	/*
	                         * WorldGuard
	                         */
	                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
	                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
	                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
	                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
	                                        e.setCancelled(true);

	                                        break;
	                                    }
	                                }
	                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
	                                String check = "";

	                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
	                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
    			if (PlayerEventsConfig.getConfig().getBoolean("Items.Drop.Bypass")) {
                    if (!p.hasPermission("hawn.event.playeritem.bypass.drop")) {
                    	/*
                         * WorldGuard
                         */
                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
                    if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                        if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
    public void onPickUp(PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        
        path_wg = "Items.PickUp.";
        
        if (Main.buildbypasscommand.contains(p)) {
        	return;
        }
        
        if (PlayerEventsConfig.getConfig().getBoolean("Items.PickUp.Disable")) {
    		if (!PlayerEventsConfig.getConfig().getBoolean("Items.PickUp.World.All_World")) {
    			if (PlayerEventsPW.getWItemPickUp().contains(p.getWorld().getName())) {
	    			if (PlayerEventsConfig.getConfig().getBoolean("Items.PickUp.Bypass")) {
	                    if (!p.hasPermission("hawn.event.playeritem.bypass.pickup")) {
	                    	/*
	                         * WorldGuard
	                         */
	                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
	                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
	                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
	                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
	                                        e.setCancelled(true);

	                                        break;
	                                    }
	                                }
	                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
	                                String check = "";

	                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
	                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
    			if (PlayerEventsConfig.getConfig().getBoolean("Items.PickUp.Bypass")) {
                    if (!p.hasPermission("hawn.event.playeritem.bypass.pickup")) {
                    	/*
                         * WorldGuard
                         */
                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
                    if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                        if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getPlayer().getLocation()).contains("id='" + s + "'")) {
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
    public void onMove(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        
        path_wg = "Items.Move.";
        
        if (Main.buildbypasscommand.contains(p)) {
        	return;
        }
        
        if (PlayerEventsConfig.getConfig().getBoolean("Items.Move.Disable")) {
    		if (!PlayerEventsConfig.getConfig().getBoolean("Items.Move.World.All_World")) {
    			if (PlayerEventsPW.getWMoveItem().contains(p.getWorld().getName())) {
	    			if (PlayerEventsConfig.getConfig().getBoolean("Items.Move.Bypass")) {
	                    if (!p.hasPermission("hawn.event.playeritem.bypass.moveitem")) {
	                    	/*
	                         * WorldGuard
	                         */
	                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
	                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
	                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
	                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
	                                        e.setCancelled(true);

	                                        break;
	                                    }
	                                }
	                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
	                                String check = "";

	                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
	                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
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
                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
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
    			if (PlayerEventsConfig.getConfig().getBoolean("Items.Move.Bypass")) {
                    if (!p.hasPermission("hawn.event.playeritem.bypass.moveitem")) {
                    	/*
                         * WorldGuard
                         */
                        if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                            if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
                                        e.setCancelled(true);

                                        break;
                                    }
                                }
                            } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                                String check = "";

                                for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                    if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
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
                    if (PlayerEventsConfig.getConfig().getBoolean(path_wg + "WorldGuard.Enable") && ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
                        if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("WHITELIST")) {
                            for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
                                    e.setCancelled(true);

                                    break;
                                }
                            }
                        } else if (PlayerEventsConfig.getConfig().getString(path_wg + "WorldGuard.Method").equalsIgnoreCase("BLACKLIST")) {
                            String check = "";

                            for (String s: PlayerEventsConfig.getConfig().getStringList(path_wg + "WorldGuard.Regions")) {
                                if (Main.getInstance().getHooksManager().getWg().getRegion(e.getWhoClicked().getLocation()).contains("id='" + s + "'")) {
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
    public void onDamageItem(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        
        if (Main.buildbypasscommand.contains(p)) {
        	return;
        }
        
        if (PlayerEventsConfig.getConfig().getBoolean("Items.Damage-Item.Disable")) {
    		if (!PlayerEventsConfig.getConfig().getBoolean("Items.Damage-Item.World.All_World")) {
    			if (PlayerEventsPW.getWItemDamage().contains(p.getWorld().getName())) {
	    			if (PlayerEventsConfig.getConfig().getBoolean("Items.Damage-Item.Bypass")) {
	                    if (!p.hasPermission("hawn.event.playeritem.bypass.damageitem")) {
	                        e.setCancelled(true);
	                    }
	                } else {
	                    e.setCancelled(true);
	                }
    			}
    		} else {
    			if (PlayerEventsConfig.getConfig().getBoolean("Items.Damage-Item.Bypass")) {
                    if (!p.hasPermission("hawn.event.playeritem.bypass.damageitem")) {
                        e.setCancelled(true);
                    }
                } else {
                    e.setCancelled(true);
                }
    		}
        }
    }
    
    // Death
    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        
        // Disable death message
        if (PlayerEventsConfig.getConfig().getBoolean("Death.Death-Message.Disable")) {
        	if (!PlayerEventsConfig.getConfig().getBoolean("Death.Death-Message.World.All_World")) {
        		if (PlayerEventsPW.getWDM().contains(e.getEntity().getWorld().getName())) {
        			e.setDeathMessage(null);
        		}
        	} else {
        		e.setDeathMessage(null);
        	}
        }
        
        // Player Respawn
        if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Enable")) {
			if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Use_Permission")) {
				if (p.hasPermission("hawn.event.respawn")) {
					if (!PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.World.All_World")) {
						if (PlayerEventsPW.getWEventResapwn().contains(p.getWorld().getName())) {
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
								public void run() {
									p.spigot().respawn();
									if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Teleport-Spawn")) {
										if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Custom-Spawn.Enable")) {
											if (!ConfigSpawn.getConfig().isSet("Coordinated."+PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"))) {
												MessageUtils.MessageNoSpawn(p);
											} else {
												SpawnUtils.teleportToSpawn(p, PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"));
											}
										} else {
											if (!ConfigSpawn.getConfig().isSet("Coordinated."+ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"))) {
												MessageUtils.MessageNoSpawn(p);
											} else {
												SpawnUtils.teleportToSpawn(p, ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"));
											}
										}
									}

								}
							}, PlayerEventsConfig.getConfig().getInt("Death.Respawn.Player.Respawn-After"));
						}
					} else {
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							public void run() {
								p.spigot().respawn();
								if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Teleport-Spawn")) {
									if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Custom-Spawn.Enable")) {
										if (!ConfigSpawn.getConfig().isSet("Coordinated."+PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"))) {
											MessageUtils.MessageNoSpawn(p);
										} else {
											SpawnUtils.teleportToSpawn(p, PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"));
										}
									} else {
										if (!ConfigSpawn.getConfig().isSet("Coordinated."+ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"))) {
											MessageUtils.MessageNoSpawn(p);
										} else {
											SpawnUtils.teleportToSpawn(p, ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"));
										}
									}
								}
							}
						}, PlayerEventsConfig.getConfig().getInt("Death.Respawn.Player.Respawn-After"));
					}
				}
			} else {
				if (!PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.World.All_World")) {
					if (PlayerEventsPW.getWEventResapwn().contains(p.getWorld().getName())) {
						Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							public void run() {
								p.spigot().respawn();
								if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Teleport-Spawn")) {
									if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Custom-Spawn.Enable")) {
										if (!ConfigSpawn.getConfig().isSet("Coordinated."+PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"))) {
											MessageUtils.MessageNoSpawn(p);
										} else {
											SpawnUtils.teleportToSpawn(p, PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"));
										}
									} else {
										if (!ConfigSpawn.getConfig().isSet("Coordinated."+ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"))) {
											MessageUtils.MessageNoSpawn(p);
										} else {
											SpawnUtils.teleportToSpawn(p, ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"));
										}
									}
								}
							}
						}, PlayerEventsConfig.getConfig().getInt("Death.Respawn.Player.Respawn-After"));
					}
				} else {
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						public void run() {
							p.spigot().respawn();
							if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Teleport-Spawn")) {
								if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Custom-Spawn.Enable")) {
									if (!ConfigSpawn.getConfig().isSet("Coordinated."+PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"))) {
										MessageUtils.MessageNoSpawn(p);
									} else {
										SpawnUtils.teleportToSpawn(p, PlayerEventsConfig.getConfig().getString("Death.Respawn.Player.Custom-Spawn.Spawn"));
									}
								} else {
									if (!ConfigSpawn.getConfig().isSet("Coordinated."+ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"))) {
										MessageUtils.MessageNoSpawn(p);
									} else {
										SpawnUtils.teleportToSpawn(p, ConfigSpawn.getConfig().getString("Spawn.DefaultSpawn"));
									}
								}
							}
						}
					}, PlayerEventsConfig.getConfig().getInt("Death.Respawn.Player.Respawn-After"));
				}
			}
        }
    
        if (PlayerEventsConfig.getConfig().getBoolean("Items.Clear-Drops-On-Death.Enable")) {
        	if (!PlayerEventsConfig.getConfig().getBoolean("Items.Clear-Drops-On-Death.World.All_World")) {
        		if (PlayerEventsPW.getWClearOnDropsOnDeath().contains(e.getEntity().getWorld().getName())) {
        			if (PlayerEventsConfig.getConfig().getBoolean("Items.Clear-Drops-On-Death.Bypass")) {
                        if (!p.hasPermission("hawn.event.death.bypass.cleardrop")) {
                            if ((e.getEntity() instanceof Player)) {
                                e.getDrops().clear();
                                forceDelete(e);
                            }
                        }
                    } else {
                        if ((e.getEntity() instanceof Player)) {
                            e.getDrops().clear();
                            forceDelete(e);
                        }
                    }
        		}
        	} else {
        		if (PlayerEventsConfig.getConfig().getBoolean("Server.Items.Clear-Drops-On-Death.Bypass")) {
                    if (!p.hasPermission("hawn.event.death.bypass.cleardrop")) {
                        if ((e.getEntity() instanceof Player)) {
                            e.getDrops().clear();
                            forceDelete(e);
                        }
                    }
                } else {
                    if ((e.getEntity() instanceof Player)) {
                        e.getDrops().clear();
                        forceDelete(e);
                    }
                }
        	}
        }
    
    }
    
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
    	Player p = e.getPlayer();
    	
    	if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.Enable")) {
        	if (!PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.World.All_World")) {
        		if (PlayerEventsPW.getWFRPCJI().contains(p.getWorld().getName())) {
        			CustomJoinItem.onItemGive(p);
        		}
        	} else {
        		CustomJoinItem.onItemGive(p);
        	}
        }
    }
    
    public void forceDelete(EntityDeathEvent e) {
        for (int i = 0; i < e.getDrops().size(); i++) e.getDrops().remove(i);
    }
    
    @EventHandler
    public void EntityMountEvent(org.spigotmc.event.entity.EntityMountEvent e) {
    	    
    	 if(!(e.getEntity() instanceof Player)) {
    		 return;
    	 }
    	
	        Player p = (Player) e.getEntity();
	        
	        if (Main.buildbypasscommand.contains(p)) {
	        	return;
	        }
	        
	    	if (PlayerEventsConfig.getConfig().getBoolean("Block-Mount.Enable")) {
	    		if (!PlayerEventsConfig.getConfig().getBoolean("Block-Mount.World.All_World")) {
	    			if (PlayerEventsPW.getWM().contains(p.getWorld().getName())) {
		    			if (PlayerEventsConfig.getConfig().getBoolean("Block-Mount.Bypass-With-Permission")) {
		                    if (!p.hasPermission("hawn.event.bypass.player.antimount")) {
		                    	e.setCancelled(true);
		                    }
		                } else {
		                	e.setCancelled(true);
		                }
	    			}
	    		} else {
	    			if (PlayerEventsConfig.getConfig().getBoolean("Items.Drop.Bypass")) {
	                    if (!p.hasPermission("hawn.event.bypass.player.antimount")) {
	                    	e.setCancelled(true);
	                    }
	                } else {
	                	e.setCancelled(true);
	                }
	    		}
	        }
    	
    }

}
