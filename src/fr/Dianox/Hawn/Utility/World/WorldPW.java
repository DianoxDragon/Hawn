package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.Events.WorldEventConfig;

public class WorldPW {
	
	// WEATHER
	
	public static List<String> worlds_weather = new ArrayList<String>();
	public static List<String> worlds_ThunderChange = new ArrayList<String>();
	public static List<String> worlds_LightningStrike = new ArrayList<String>();
	
	public static void setWGetWorldServerDisableWeather() {
		if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.Weather.Enable")) {
	        if (!WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.Weather.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Weather.Disable.Weather.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Weather.Disable.Weather.World: "+world);
	            	} else {
	            		worlds_weather.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldServerDisableThunderChange() {
		if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.ThunderChange.Enable")) {
	        if (!WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.ThunderChange.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Weather.Disable.ThunderChange.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Weather.Disable.ThunderChange.World: "+world);
	            	} else {
	            		worlds_ThunderChange.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldServerDisableLightningStrike() {
		if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.Disable")) {
	        if (!WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Weather.Disable.LightningStrike.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Weather.Disable.LightningStrike.World: "+world);
	            	} else {
	            		worlds_LightningStrike.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWW() {
		return worlds_weather;
	}
	
	public static List<String> getWTC() {
		return worlds_ThunderChange;
	}
	
	public static List<String> getWLS() {
		return worlds_LightningStrike;
	}
	
	// Blocks
	
	public static List<String> worlds_burn_block = new ArrayList<String>();
	public static List<String> worlds_explosions = new ArrayList<String>();
	public static List<String> worlds_LeaveDecay = new ArrayList<String>();
	public static List<String> worlds_firespread = new ArrayList<String>();
	public static List<String> worlds_BlockFade = new ArrayList<String>();
	
	public static void setWGetWorldServerDisableBurnBlock() {
		if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.Disable")) {
			if (!WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Burn.Disable.Burn-Block.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Burn.Disable.Burn-Block.World: "+world);
	            	} else {
	            		worlds_burn_block.add(world);
	            	}
	            }
	        }
		}
	}
	
	public static void setWGetWorldServerDisableExplosion() {
		if (!WorldEventConfig.getConfig().getBoolean("World.Explosion.Disable.Explosion.World.All_World")) {
            for (String world : WorldEventConfig.getConfig().getStringList("World.Explosion.Disable.Explosion.World.Worlds")) {
            	if (Bukkit.getWorld(world) == null) {
            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Explosion.Disable.Explosion.World: "+world);
            	} else {
            		worlds_explosions.add(world);
            	}
            }
        }
	}
	
	public static void setWGetWorldServerDisableLeaveDecay() {
		if (WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Leave-Decay.Disable")) {
	        if (!WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Leave-Decay.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Blocks.Disable.Leave-Decay.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Blocks.Disable.Leave-Decay.World: "+world);
	            	} else {
	            		worlds_LeaveDecay.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldServerDisableFireSpread() {
		if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.Disable")) {
	        if (!WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Burn.Disable.BlockIgnite-FireSpread.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Burn.Disable.BlockIgnite-FireSpread.World: "+world);
	            	} else {
	            		worlds_firespread.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldServerDisableblockFade() {
		if (WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Block-Fade.Disable")) {
	        if (!WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Block-Fade.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Blocks.Disable.Block-Fade.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Blocks.Disable.Block-Fade.World: "+world);
	            	} else {
	            		worlds_BlockFade.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWBF() {
		return worlds_BlockFade;
	}
	
	public static List<String> getWFS() {
		return worlds_firespread;
	}
	
	public static List<String> getWLD() {
		return worlds_LeaveDecay;
	}
	
	public static List<String> getWBB() {
		return worlds_burn_block;
	}
	
	public static List<String> getWE() {
		return worlds_explosions;
	}
	
	// Entity
	public static List<String> worlds_spawning_mob_animals = new ArrayList<String>();
	
	public static void setWGetWorldServerDisableSpawningMobAnimals() {
		if (WorldEventConfig.getConfig().getBoolean("World.Disable.Spawning-Monster-Animals.Disable")) {
	        if (!WorldEventConfig.getConfig().getBoolean("World.Disable.Spawning-Monster-Animals.World.All_World")) {
	            for (String world : WorldEventConfig.getConfig().getStringList("World.Disable.Spawning-Monster-Animals.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/WorldEvent.yml, World.Disable.Spawning-Monster-Animals.World: "+world);
	            	} else {
	            		worlds_spawning_mob_animals.add(world);
	            	}
	            }
	        }
        }
	}

	public static List<String> getWSMA() {
		return worlds_spawning_mob_animals;
	}
	
}
