package fr.Dianox.Hawn.Event;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.meta.FireworkMeta;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.OtherUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.XSound;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGProtection;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerEventsConfig;
import fr.Dianox.Hawn.Utility.Config.Events.ProtectionPlayerConfig;
import fr.Dianox.Hawn.Utility.Config.Events.VoidTPConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMEvents;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMProtection;
import fr.Dianox.Hawn.Utility.World.BasicEventsPW;
import fr.Dianox.Hawn.Utility.World.ProtectionPW;

public class BasicFeatures implements Listener {

	public static List<String> world_voidtp = new ArrayList<String>();
	
    // Can't change Game mode
    @EventHandler
    public void onGamemodeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();

        if (OnJoin.player_list.contains(p)) {
            if (PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.Enable")) {
                if (PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.Bypass-With-Permission")) {
                    if (!p.hasPermission("hawn.bypass.keepgamemode")) {
                        if (!PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.World.All_World")) {
                            if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
                                e.setCancelled(true);
                            }
                        } else {
                            e.setCancelled(true);
                        }
                    }
                } else {
                    if (!PlayerEventsConfig.getConfig().getBoolean("Keep-Gamemode.World.All_World")) {
                        if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
                            e.setCancelled(true);
                        }
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    // VoidTP
    @EventHandler
    public void onVoidTP(PlayerMoveEvent e) {
        if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Enable")) {
            return;
        }
        
        Player p = e.getPlayer();

        if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Bypass-With-Permission")) {
            if (p.hasPermission("hawn.bypass.voidtp")) {
                return;
            }
        }

        if (!VoidTPConfig.getConfig().getBoolean("VoidTP.World.All_World")) {
            if (!BasicEventsPW.getVTP().contains(p.getWorld().getName())) {
                return;
            }
        }
        
        Boolean multiworld = false;
        
        if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.Enable")) {
        	if (world_voidtp.contains(p.getWorld().getName()) && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + p.getWorld().getName() + ".Enable")) {
        		multiworld = true;
        	}
        }

        Location loc = p.getLocation();
        String spawn = "";
        String w = p.getWorld().getName();
        Integer getYConfig = 0;
        
        if (multiworld) {
        	if (VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".TP-y")) {
        		if (VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Enable") && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Enable")) {
                    if (VoidTPConfig.getConfig().getString("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
                        String Lineerror = "VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn";
                        String Fileerror = "Events/VoidTP.yml";
                        if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
                            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
                                MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
                            }
                        }

                        return;
                    } else {
                        if (!ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn"))) {
                            MessageUtils.MessageNoSpawn(p);
                            return;
                        } else {
                            spawn = VoidTPConfig.getConfig().getString("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn");
                        }
                    }
                } else {
                	if (VoidTPConfig.getConfig().getBoolean("VoidTP.Custom-Spawn.Enable")) {
                        if (VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
                            String Lineerror = "VoidTP.Custom-Spawn.Spawn";
                            String Fileerror = "Events/VoidTP.yml";
                            if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
                                for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
                                }
                            }

                            return;
                        } else {
                            if (!ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn"))) {
                                MessageUtils.MessageNoSpawn(p);
                                return;
                            } else {
                                spawn = VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn");
                            }
                        }
                    } else {
                        if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                            String Lineerror = "Spawn.DefaultSpawn";
                            String Fileerror = "Events/OnJoin.yml";
                            if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
                                for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
                                }
                            }

                            return;
                        } else {
                            if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                MessageUtils.MessageNoSpawn(p);
                                return;
                            } else {
                                spawn = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
                            }
                        }
                    }
                }
        		
        		getYConfig = Integer.valueOf(VoidTPConfig.getConfig().getInt("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".TP-y"));
        	} else {
        		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Custom-Spawn.Enable")) {
                    if (VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
                        String Lineerror = "VoidTP.Custom-Spawn.Spawn";
                        String Fileerror = "Events/VoidTP.yml";
                        if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
                            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
                                MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
                            }
                        }

                        return;
                    } else {
                        if (!ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn"))) {
                            MessageUtils.MessageNoSpawn(p);
                            return;
                        } else {
                            spawn = VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn");
                        }
                    }
                } else {
                    if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                        String Lineerror = "Spawn.DefaultSpawn";
                        String Fileerror = "Events/OnJoin.yml";
                        if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
                            for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
                                MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
                            }
                        }

                        return;
                    } else {
                        if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                            MessageUtils.MessageNoSpawn(p);
                            return;
                        } else {
                            spawn = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
                        }
                    }
                }
        		
        		getYConfig = Integer.valueOf(VoidTPConfig.getConfig().getInt("VoidTP.Options.TP-y"));
        	}
        } else {
        	if (VoidTPConfig.getConfig().getBoolean("VoidTP.Custom-Spawn.Enable")) {
                if (VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
                    String Lineerror = "VoidTP.Custom-Spawn.Spawn";
                    String Fileerror = "Events/VoidTP.yml";
                    if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
                        for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
                            MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
                        }
                    }

                    return;
                } else {
                    if (!ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn"))) {
                        MessageUtils.MessageNoSpawn(p);
                        return;
                    } else {
                        spawn = VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn");
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                    String Lineerror = "Spawn.DefaultSpawn";
                    String Fileerror = "Events/OnJoin.yml";
                    if (ConfigMOStuff.getConfig().getBoolean("Error.Change-Me.Enable")) {
                        for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Change-Me.Messages")) {
                            MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", Lineerror).replaceAll("%arg2%", Fileerror), p);
                        }
                    }

                    return;
                } else {
                    if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                        MessageUtils.MessageNoSpawn(p);
                        return;
                    } else {
                        spawn = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
                    }
                }
            }
        	
        	getYConfig = Integer.valueOf(VoidTPConfig.getConfig().getInt("VoidTP.Options.TP-y"));
        }

        if (loc.getY() <= getYConfig) {

            if (!p.hasPermission("hawn.command.spawn." + spawn)) {
                String Permission = "hawn.command.spawn." + spawn;
                MessageUtils.MessageNoPermission(p, Permission);
                return;
            }

            if (multiworld) {
            	if (VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".VoidTP")) {
	            	if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".VoidTP")) {
	            		SpawnUtils.teleportToSpawn(p, spawn);
	            	}
            	} else {
            		SpawnUtils.teleportToSpawn(p, spawn);
            	}
            } else {
            	SpawnUtils.teleportToSpawn(p, spawn);
            }
            
            if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Message.Custom")) {
                if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Message.Disable")) {
                    for (String msg: ConfigMEvents.getConfig().getStringList("Teleport.VoidTP")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                    }
                }
            } else {
                if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Message.Disable")) {
                    for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                    }
                }
            }

            if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Sounds.Enable")) {
                String sound = VoidTPConfig.getConfig().getString("VoidTP.Options.Sounds.Sound");
                int volume = VoidTPConfig.getConfig().getInt("VoidTP.Options.Sounds.Volume");
                int pitch = VoidTPConfig.getConfig().getInt("VoidTP.Options.Sounds.Pitch");
                p.playSound(p.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
            }
            
            if (multiworld) {
            	if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Enable") && VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Enable")) {
            		for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Commands")) {
            			String perm = "";

                        if (s.startsWith("<perm>") && s.contains("</perm>")) {
                        	perm = StringUtils.substringBetween(s, "<perm>", "</perm>");
                        	s = s.replace("<perm>"+perm+"</perm> ", "");
                        	
                        	if (!p.hasPermission(perm)) {
                        		continue;
                        	}
                        }

                        if (s.startsWith("[command-player]: ")) {
                            s = s.replace("[command-player]: ", "");
                            s = s.replaceAll("%player%", p.getName());

                            p.performCommand(s);
                        } else if (s.startsWith("[customcommand-player]: ")) {
                        	s = s.replace("[customcommand-player]: ", "");
                            s = s.replaceAll("%player%", p.getName());
                            
                            OnCommandEvent.executecustomcommand(s, p);
                        } else if (s.startsWith("[command-console]: ")) {
                            s = s.replace("[command-console]: ", "");
                            s = s.replaceAll("%player%", p.getName());

                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
                        } else {
                        	MessageUtils.ReplaceCharMessagePlayer(s, p);
                        }
            		}
            		
            		if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Override-Default-Commands")) {
	            		for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.Execute-Commands.Commands")) {
	            			String perm = "";
	
	                        if (s.startsWith("<perm>") && s.contains("</perm>")) {
	                        	perm = StringUtils.substringBetween(s, "<perm>", "</perm>");
	                        	s = s.replace("<perm>"+perm+"</perm> ", "");
	                        	
	                        	if (!p.hasPermission(perm)) {
	                        		continue;
	                        	}
	                        }
	
	                        if (s.startsWith("[command-player]: ")) {
	                            s = s.replace("[command-player]: ", "");
	                            s = s.replaceAll("%player%", p.getName());
	
	                            p.performCommand(s);
	                        } else if (s.startsWith("[customcommand-player]: ")) {
	                        	s = s.replace("[customcommand-player]: ", "");
	                            s = s.replaceAll("%player%", p.getName());
	                            
	                            OnCommandEvent.executecustomcommand(s, p);
	                        } else if (s.startsWith("[command-console]: ")) {
	                            s = s.replace("[command-console]: ", "");
	                            s = s.replaceAll("%player%", p.getName());
	
	                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
	                        } else {
	                        	MessageUtils.ReplaceCharMessagePlayer(s, p);
	                        }
	            		}
            		}
            	} else {
            		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Execute-Commands.Enable")) {
                		for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.Execute-Commands.Commands")) {
                			String perm = "";

                            if (s.startsWith("<perm>") && s.contains("</perm>")) {
                            	perm = StringUtils.substringBetween(s, "<perm>", "</perm>");
                            	s = s.replace("<perm>"+perm+"</perm> ", "");
                            	
                            	if (!p.hasPermission(perm)) {
                            		continue;
                            	}
                            }

                            if (s.startsWith("[command-player]: ")) {
                                s = s.replace("[command-player]: ", "");
                                s = s.replaceAll("%player%", p.getName());

                                p.performCommand(s);
                            } else if (s.startsWith("[customcommand-player]: ")) {
                            	s = s.replace("[customcommand-player]: ", "");
                                s = s.replaceAll("%player%", p.getName());
                                
                                OnCommandEvent.executecustomcommand(s, p);
                            } else if (s.startsWith("[command-console]: ")) {
                                s = s.replace("[command-console]: ", "");
                                s = s.replaceAll("%player%", p.getName());

                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
                            } else {
                            	MessageUtils.ReplaceCharMessagePlayer(s, p);
                            }
                		}
                	}
            	}
            } else {
            	if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Execute-Commands.Enable")) {
            		for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.Execute-Commands.Commands")) {
            			String perm = "";

                        if (s.startsWith("<perm>") && s.contains("</perm>")) {
                        	perm = StringUtils.substringBetween(s, "<perm>", "</perm>");
                        	s = s.replace("<perm>"+perm+"</perm> ", "");
                        	
                        	if (!p.hasPermission(perm)) {
                        		continue;
                        	}
                        }

                        if (s.startsWith("[command-player]: ")) {
                            s = s.replace("[command-player]: ", "");
                            s = s.replaceAll("%player%", p.getName());

                            p.performCommand(s);
                        } else if (s.startsWith("[customcommand-player]: ")) {
                        	s = s.replace("[customcommand-player]: ", "");
                            s = s.replaceAll("%player%", p.getName());
                            
                            OnCommandEvent.executecustomcommand(s, p);
                        } else if (s.startsWith("[command-console]: ")) {
                            s = s.replace("[command-console]: ", "");
                            s = s.replaceAll("%player%", p.getName());

                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
                        } else {
                        	MessageUtils.ReplaceCharMessagePlayer(s, p);
                        }
            		}
            	}
            }
            
            if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Fireworks.Enable")) {
    	        for (int i = 1; i < VoidTPConfig.getConfig().getInt("VoidTP.Options.Fireworks.Amount"); i++) {
    	            ArrayList < Color > colors = new ArrayList < Color > ();
    	            ArrayList < Color > fade = new ArrayList < Color > ();
    	            List < String > lore = VoidTPConfig.getConfig().getStringList("VoidTP.Options.Fireworks.Colors");
    	            List < String > lore2 = VoidTPConfig.getConfig().getStringList("VoidTP.Options.Fireworks.Fade");
    	            for (String l1: lore) {
    	                colors.add(OtherUtils.getColor(l1));
    	            }
    	            for (String l2: lore2) {
    	                fade.add(OtherUtils.getColor(l2));
    	            }
    	            final Firework f = p.getWorld().spawn(p.getLocation().add(0.5D, VoidTPConfig.getConfig().getInt("VoidTP.Options.Fireworks.Height"), 0.5D), Firework.class);
    	
    	            FireworkMeta fm = f.getFireworkMeta();
    	            fm.addEffect(FireworkEffect.builder().flicker(VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Fireworks.Flicker"))
    	                .trail(VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Fireworks.Trail"))
    	                .with(FireworkEffect.Type.valueOf(VoidTPConfig.getConfig().getString("VoidTP.Options.Fireworks.Type"))).withColor(colors).withFade(fade)
    	                .build());
    	
    	            if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Fireworks.Instant-explode")) {
    	                fm.setPower(VoidTPConfig.getConfig().getInt("VoidTP.Options.Fireworks.Power"));
    	            }
    	            f.setFireworkMeta(fm);
    	            if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Fireworks.Instant-explode")) {
    	                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
    	                    public void run() {
    	                        f.detonate();
    	                    }
    	                }, 5L);
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
            if (!PlayerEventsConfig.getConfig().getBoolean("Keep.Food.World.All_World")) {
                if (BasicEventsPW.getWkFood().contains(p.getWorld().getName())) {
                    if (PlayerEventsConfig.getConfig().getBoolean("Keep.Food.Bypass-With-Permission")) {
                        if (!p.hasPermission("hawn.bypass.foodkeep")) {
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
            } else {
                if (PlayerEventsConfig.getConfig().getBoolean("Keep.Food.Bypass-With-Permission")) {
                    if (!p.hasPermission("hawn.bypass.foodkeep")) {
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


    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Enable")) {
            if (!ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.World.All_World")) {
                if (BasicEventsPW.getWkHealth().contains(e.getEntity().getLocation().getWorld().getName())) {
                    if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Bypass-With-Permission")) {
                        if (e.getEntity() instanceof Player && !e.getEntity().hasPermission("hawn.bypass.antidamage")) {
                            if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                                if (e.getEntity() instanceof Player) {
                                    Damage(e);
                                }
                            } else {
                                if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                                e.setCancelled(true);
                            }
                        }
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
                if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Bypass-With-Permission")) {
                    if (e.getEntity() instanceof Player && !e.getEntity().hasPermission("hawn.bypass.antidamage")) {
                        if (ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.Custom.Enable")) {
                            if (e.getEntity() instanceof Player) {
                                Damage(e);
                            }
                        } else {
                            if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                            e.setCancelled(true);
                        }
                    }
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
        }
    }

    public void Damage(Event e) {
        if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.BLOCK_EXPLOSION")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.CONTACT) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.CONTACT")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.CRAMMING")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.CUSTOM) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.CUSTOM")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.DROWNING")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.DRAGON_BREATH) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.DRAGON_BREATH")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.DRYOUT) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.DRYOUT")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.ENTITY_ATTACK")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.ENTITY_EXPLOSION")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.ENTITY_SWEEP_ATTACK")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.FALL")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.FALLING_BLOCK")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.FIRE) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.FIRE")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.FIRE_TICK")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.FLY_INTO_WALL")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.HOT_FLOOR")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.LAVA) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.LAVA")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.LIGHTNING")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.MAGIC")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.MELTING) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.MELTING")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.POISON) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.POISON")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.PROJECTILE")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.STARVATION")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.SUFFOCATION")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.SUICIDE")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.THORNS) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.THORNS")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.VOID) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.VOID")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else if (((EntityDamageEvent) e).getCause() == EntityDamageEvent.DamageCause.WITHER) {
            if (ProtectionPlayerConfig.getConfig().getBoolean("AntiDamage-Custom.Entity.Options.WITHER")) {
                ((EntityDamageEvent) e).setCancelled(true);
            }
        } else {
            ((EntityDamageEvent) e).setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof ArmorStand) {
            String player = String.valueOf(e.getDamager());

            if (player.contains("CraftPlayer")) {
                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Enable")) {
                    if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.World.All_World")) {
                        if (ProtectionPW.getWPCB().contains(e.getDamager().getWorld().getName())) {
                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
                                if (!e.getDamager().hasPermission("hawn.event.construct.bypass.break")) {
                                    e.setCancelled(true);
                                    if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                        for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, (Player) e.getDamager());
                                        }
                                    }
                                }
                            } else {
                                e.setCancelled(true);
                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                        MessageUtils.ReplaceCharMessagePlayer(msg, (Player) e.getDamager());
                                    }
                                }
                            }
                        }
                    } else {
                        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
                            if (!e.getDamager().hasPermission("hawn.event.construct.bypass.break")) {
                                e.setCancelled(true);
                                if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                    for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                        MessageUtils.ReplaceCharMessagePlayer(msg, (Player) e.getDamager());
                                    }
                                }
                            }
                        } else {
                            e.setCancelled(true);
                            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
                                for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg, (Player) e.getDamager());
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
                    if (!ProtectionPlayerConfig.getConfig().getBoolean("Anti-Damage.World.All_World")) {
                        if (BasicEventsPW.getWkHealth().contains(e.getEntity().getLocation().getWorld().getName())) {
                            if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                            e.setCancelled(true);
                            if (e.getEntity() instanceof ItemFrame) {
                                e.setCancelled(true);
                            }
                        }
                    } else {
                        if (e.getEntityType() == EntityType.ARMOR_STAND) return;
                        e.setCancelled(true);
                        if (e.getEntity() instanceof ItemFrame) {
                            e.setCancelled(true);
                        }
                        if (e.getEntity() instanceof ArmorStand) {
                            e.setCancelled(false);
                        }
                    }
                }
            }
        }
    }

}
