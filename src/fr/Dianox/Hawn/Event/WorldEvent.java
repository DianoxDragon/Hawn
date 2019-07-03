package fr.Dianox.Hawn.Event;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import fr.Dianox.Hawn.Utility.Config.Events.WorldEventConfig;
import fr.Dianox.Hawn.Utility.World.WorldPW;

public class WorldEvent implements Listener {
	
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
    	if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.Disable")) {
    		if (!WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.World.All_World")) {
    			if (WorldPW.getWBB().contains(e.getBlock().getWorld().getName())) {
    				e.setCancelled(true);
    			}
    		} else {
    			e.setCancelled(true);
    		}
        }
    }
    
    @EventHandler
    public void FireSpread(BlockIgniteEvent e) {
    	if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.Disable")) {
    		if (!WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.World.All_World")) {
    			if (WorldPW.getWFS().contains(e.getBlock().getWorld().getName())) {
    				if (e.getCause() == IgniteCause.SPREAD) {
    					e.setCancelled(true);
    				}
    			}
    		} else {
    			if (e.getCause() == IgniteCause.SPREAD) {
					e.setCancelled(true);
				}
    		}
        }
    }
    
    // Explosion
    @EventHandler
    public void onExplode(ExplosionPrimeEvent e) {
        if (WorldEventConfig.getConfig().getBoolean("World.Explosion.Disable.Explosion.Disable")) {
        	if (!WorldEventConfig.getConfig().getBoolean("World.Explosion.Disable.Explosion.World.All_World")) {
        		if (WorldPW.getWE().contains(e.getEntity().getWorld().getName())) {
        			e.setCancelled(true);
        		}
        	} else {
        		e.setCancelled(true);
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
        			if(e.getEntityType() == EntityType.ARMOR_STAND) return;
        			e.setCancelled(true);
        		}
        	} else {
        		if(e.getEntityType() == EntityType.ARMOR_STAND) return;
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
    
}
