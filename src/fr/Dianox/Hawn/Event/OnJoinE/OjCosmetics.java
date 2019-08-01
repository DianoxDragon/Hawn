package fr.Dianox.Hawn.Event.OnJoinE;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.OtherUtils;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.World.CosmeticsPW;

public class OjCosmetics {
	
	public static void MainMethod(Player p) {
		/*
		 * Fireworks
		 */
		if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Enable")) {
            if (!ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.World.All_World")) {
                if (CosmeticsPW.getWFirework().contains(p.getWorld().getName())) {
                    if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Bypass")) {
                        if (!p.hasPermission("hawn.event.onjoin.bypass.firework")) {
                        	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                        		if (!p.hasPlayedBefore()) {
                        			Fireworkmethod(p);
                        		}
                        	} else {
                        		Fireworkmethod(p);
                        	}
                        }
                    } else {
                    	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                    		if (!p.hasPlayedBefore()) {
                    			Fireworkmethod(p);
                    		}
                    	} else {
                    		Fireworkmethod(p);
                    	}
                    }
                }
            } else {
                if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Bypass")) {
                    if (!p.hasPermission("hawn.event.onjoin.bypass.firework")) {
                    	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                    		if (!p.hasPlayedBefore()) {
                    			Fireworkmethod(p);
                    		}
                    	} else {
                    		Fireworkmethod(p);
                    	}
                    }
                } else {
                	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                		if (!p.hasPlayedBefore()) {
                			Fireworkmethod(p);
                		}
                	} else {
                		Fireworkmethod(p);
                	}
                }
            }
        }
		
		/*
		 * Lightning strike
		 */
		if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Enable")) {
            if (!ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.World.All_World")) {
                if (CosmeticsPW.getWLightningStrike().contains(p.getWorld().getName())) {
                	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Bypass")) {
                		if (!p.hasPermission("hawn.event.onjoin.bypass.lightningstrike")) {
                			if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Options.First-Join-Only")) {
                    			if (!p.hasPlayedBefore()) {
                    				World w = p.getWorld();
                                	
            	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
            	                		w.strikeLightningEffect(p.getLocation());
            	                	}
                    			}
                    		} else {
                    			World w = p.getWorld();
                            	
        	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
        	                		w.strikeLightningEffect(p.getLocation());
        	                	}
                    		}
                		}
                	} else {
                		if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Options.First-Join-Only")) {
                			if (!p.hasPlayedBefore()) {
                				World w = p.getWorld();
                            	
        	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
        	                		w.strikeLightningEffect(p.getLocation());
        	                	}
                			}
                		} else {
                			World w = p.getWorld();
                        	
    	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
    	                		w.strikeLightningEffect(p.getLocation());
    	                	}
                		}
                	}
                }
            } else {
            	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Bypass")) {
            		if (!p.hasPermission("hawn.event.onjoin.bypass.lightningstrike")) {
            			if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Options.First-Join-Only")) {
                			if (!p.hasPlayedBefore()) {
                				World w = p.getWorld();
                            	
        	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
        	                		w.strikeLightningEffect(p.getLocation());
        	                	}
                			}
                		} else {
                			World w = p.getWorld();
                        	
    	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
    	                		w.strikeLightningEffect(p.getLocation());
    	                	}
                		}
            		}
            	} else {
            		if (ConfigGCos.getConfig().getBoolean("Cosmetics.Lightning-Strike.Options.First-Join-Only")) {
            			if (!p.hasPlayedBefore()) {
            				World w = p.getWorld();
                        	
    	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
    	                		w.strikeLightningEffect(p.getLocation());
    	                	}
            			}
            		} else {
            			World w = p.getWorld();
                    	
	                	for (int i = 0; i <= ConfigGCos.getConfig().getInt("Cosmetics.Lightning-Strike.Options.Number-Of-Strikes"); i++) {
	                		w.strikeLightningEffect(p.getLocation());
	                	}
            		}
            	}
            }
		}
	}
	
	public static void Fireworkmethod(Player p) {
        for (int i = 1; i < ConfigGCos.getConfig().getInt("Cosmetics.Firework.Options.Amount"); i++) {
            ArrayList < Color > colors = new ArrayList < Color > ();
            ArrayList < Color > fade = new ArrayList < Color > ();
            List < String > lore = ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Colors");
            List < String > lore2 = ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Fade");
            for (String l1: lore) {
                colors.add(OtherUtils.getColor(l1));
            }
            for (String l2: lore2) {
                fade.add(OtherUtils.getColor(l2));
            }
            final Firework f = p.getWorld().spawn(p.getLocation().add(0.5D, ConfigGCos.getConfig().getInt("Cosmetics.Firework.Options.Height"), 0.5D), Firework.class);

            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder().flicker(ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Flicker"))
                .trail(ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Trail"))
                .with(FireworkEffect.Type.valueOf(ConfigGCos.getConfig().getString("Cosmetics.Firework.Options.Type"))).withColor(colors).withFade(fade)
                .build());

            if (!ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Instant-explode")) {
                fm.setPower(ConfigGCos.getConfig().getInt("Cosmetics.Firework.Options.Power"));
            }
            f.setFireworkMeta(fm);
            if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Instant-explode")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    public void run() {
                        f.detonate();
                    }
                }, 5L);
            }
        }
    }

}
