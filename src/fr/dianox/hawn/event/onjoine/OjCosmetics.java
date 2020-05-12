package fr.dianox.hawn.event.onjoine;

import org.bukkit.World;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.OtherUtils;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.world.CosmeticsPW;

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
                        			for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
                        				if (s.startsWith("[FWLU]: ")) {
                        					
                        					s = s.replace("[FWLU]: ", "");
                        					
                        					OtherUtils.Fireworkmethod(p, s);
                        				}
                        			}
                        		}
                        	} else {
                        		for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
                    				if (s.startsWith("[FWLU]: ")) {
                    					
                    					s = s.replace("[FWLU]: ", "");
                    					
                    					OtherUtils.Fireworkmethod(p, s);
                    				}
                    			}
                        	}
                        }
                    } else {
                    	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                    		if (!p.hasPlayedBefore()) {
                    			for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
                    				if (s.startsWith("[FWLU]: ")) {
                    					
                    					s = s.replace("[FWLU]: ", "");
                    					
                    					OtherUtils.Fireworkmethod(p, s);
                    				}
                    			}
                    		}
                    	} else {
                    		for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
                				if (s.startsWith("[FWLU]: ")) {
                					
                					s = s.replace("[FWLU]: ", "");
                					
                					OtherUtils.Fireworkmethod(p, s);
                				}
                			}
                    	}
                    }
                }
            } else {
                if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Bypass")) {
                    if (!p.hasPermission("hawn.event.onjoin.bypass.firework")) {
                    	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                    		if (!p.hasPlayedBefore()) {
                    			for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
                    				if (s.startsWith("[FWLU]: ")) {
                    					
                    					s = s.replace("[FWLU]: ", "");
                    					
                    					OtherUtils.Fireworkmethod(p, s);
                    				}
                    			}
                    		}
                    	} else {
                    		for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
                				if (s.startsWith("[FWLU]: ")) {
                					
                					s = s.replace("[FWLU]: ", "");
                					
                					OtherUtils.Fireworkmethod(p, s);
                				}
                			}
                    	}
                    }
                } else {
                	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                		if (!p.hasPlayedBefore()) {
                			for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
                				if (s.startsWith("[FWLU]: ")) {
                					
                					s = s.replace("[FWLU]: ", "");
                					
                					OtherUtils.Fireworkmethod(p, s);
                				}
                			}
                		}
                	} else {
                		for (String s: ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Firework-List")) {
            				if (s.startsWith("[FWLU]: ")) {
            					
            					s = s.replace("[FWLU]: ", "");
            					
            					OtherUtils.Fireworkmethod(p, s);
            				}
            			}
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

}