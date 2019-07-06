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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.XMaterial;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGProtection;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMProtection;
import fr.Dianox.Hawn.Utility.World.ProtectionPW;

public class ProtectionsEventWorld implements Listener {
	
	@EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        if (Main.buildbypasscommand.contains(p) ) {
        	return;
        }
        
        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Enable")) {
        	if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.World.All_World")) {
        		if (ProtectionPW.getWPCP().contains(p.getWorld().getName())) {
        			if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
        		        if (!p.hasPermission("hawn.event.construct.bypass.place")) {
        		            e.setCancelled(true);
        		            
        		            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
        		            	for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
        		            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
        		            	}
        		            }
        		        }
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
        		if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
        	        if (!p.hasPermission("hawn.event.construct.bypass.place")) {
        	            e.setCancelled(true);
        	            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Message")) {
        	            	for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Place")) {
        	            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
    		            	}
        	            }
        	        }
        	    } else {
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
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void onPlace2(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Material m = p.getItemInHand().getType();
        
        if (Main.buildbypasscommand.contains(p) ) {
        	return;
        }
        
        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Enable")) {
        	if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.World.All_World")) {
        		if (ProtectionPW.getWPCP().contains(p.getWorld().getName())) {
        			if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
        		        if (!p.hasPermission("hawn.event.construct.bypass.place")) {
        		        	if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        		            	if(m == XMaterial.ARMOR_STAND.parseMaterial()){
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
        		    	if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        		        	if(m == XMaterial.ARMOR_STAND.parseMaterial()){
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
        		if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Bypass")) {
        	        if (!p.hasPermission("hawn.event.construct.bypass.place")) {
        	        	if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	            	if(m == XMaterial.ARMOR_STAND.parseMaterial()){
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
        	    	if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
        	        	if(m == XMaterial.ARMOR_STAND.parseMaterial()){
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

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();

        if (Main.buildbypasscommand.contains(p) ) {
        	return;
        }
        
        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Enable")) {
        	if (!ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.World.All_World")) {
        		if (ProtectionPW.getWPCB().contains(p.getWorld().getName())) {
        			if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
        		        if (!p.hasPermission("hawn.event.construct.bypass.break")) {
        		            e.setCancelled(true);
        		            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
        		            	for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
        		            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
        		            	}
        		            }
        		        }
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
        		if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Bypass")) {
    		        if (!p.hasPermission("hawn.event.construct.bypass.break")) {
    		            e.setCancelled(true);
    		            if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Message")) {
    		            	for (String msg: ConfigMProtection.getConfig().getStringList("Protection.Anti-Break")) {
    		            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
    		            	}
    		            }
    		        }
    		    } else {
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
    
    @SuppressWarnings("unlikely-arg-type")
	@EventHandler
    public void onHanging(HangingBreakByEntityEvent e) {
    	if (Main.buildbypasscommand.contains(e.getEntity()) ) {
        	return;
        }
    	
    	if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Enable")) {
    		if (!ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.World.All_World")) {
    			if (ProtectionPW.getWHBBE().contains(e.getEntity().getWorld().getName())) {
    				if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Bypass")) {
    					if (!e.getEntity().hasPermission("hawn.bypass.HagingBreakByEntity")) {
    						e.setCancelled(true);
    					}
    				} else {
    					e.setCancelled(true);
    				}
    			}
    		} else {
    			if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Bypass")) {
					if (!e.getEntity().hasPermission("hawn.bypass.HagingBreakByEntity")) {
						e.setCancelled(true);
					}
				} else {
					e.setCancelled(true);
				}
    		}
        }
    }
    
    @EventHandler
    public void onplayerinteract(PlayerInteractEntityEvent e) {
    	Player p = e.getPlayer();
    	
    	if (Main.buildbypasscommand.contains(p) ) {
        	return;
        }
    	
    	if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.Enable")) {
    		if (!ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.World.All_World")) {
    			if (ProtectionPW.getWPIEIF().contains(p.getWorld().getName())) {
    				if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.Bypass")) {
    					if (!p.hasPermission("hawn.bypass.PlayerInteractEntity")) {
		    				if ((e.getRightClicked() instanceof ItemFrame)) {
		    			        e.setCancelled(true);
		    				}
    					}
    				} else {
    					if ((e.getRightClicked() instanceof ItemFrame)) {
        			        e.setCancelled(true);
        				}
    				}
    			}
    		} else {
    			if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.Bypass")) {
					if (!p.hasPermission("hawn.bypass.PlayerInteractEntity")) {
	    				if ((e.getRightClicked() instanceof ItemFrame)) {
	    			        e.setCancelled(true);
	    				}
					}
				} else {
					if ((e.getRightClicked() instanceof ItemFrame)) {
    			        e.setCancelled(true);
    				}
				}
    		}
        }
    }
	
}
