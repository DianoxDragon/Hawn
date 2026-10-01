package fr.dianox.hawn.utility.world;

import fr.dianox.hawn.utility.config.configs.events.WorldEventConfig;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class WorldPW {

    public static List < String > worlds_weather = new ArrayList<>();
    public static List < String > worlds_ThunderChange = new ArrayList<>();
    public static List < String > worlds_LightningStrike = new ArrayList<>();
    public static List < String > worlds_burn_block = new ArrayList<>();
    public static List < String > worlds_explosions = new ArrayList<>();
    public static List < String > worlds_LeaveDecay = new ArrayList<>();
    public static List < String > worlds_firespread = new ArrayList<>();
    public static List < String > worlds_BlockFade = new ArrayList<>();
    public static List < String > worlds_spawning_mob_animals = new ArrayList<>();
    public static List < String > worlds_shears = new ArrayList<>();
    public static List < String > worlds_edtp = new ArrayList<>();

    // WEATHER
    public static void setWGetWorldServerDisableWeather() {
        if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.Weather.Enable") && !WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.Weather.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Weather.Disable.Weather.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Weather.Disable.Weather.World: " + world);
                } else {
                    worlds_weather.add(world);
                }
            }
        }
    }

    public static void setWGetWorldServerDisableThunderChange() {
        if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.ThunderChange.Enable") && !WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.ThunderChange.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Weather.Disable.ThunderChange.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Weather.Disable.ThunderChange.World: " + world);
                } else {
                    worlds_ThunderChange.add(world);
                }
            }
        }
    }

    public static void setWGetWorldServerDisableLightningStrike() {
        if (WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.Disable") && !WorldEventConfig.getConfig().getBoolean("World.Weather.Disable.LightningStrike.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Weather.Disable.LightningStrike.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Weather.Disable.LightningStrike.World: " + world);
                } else {
                    worlds_LightningStrike.add(world);
                }
            }
        }
    }

    // Blocks
    public static void setWGetWorldServerDisableBurnBlock() {
        if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.Disable") && !WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.Burn-Block.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Burn.Disable.Burn-Block.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Burn.Disable.Burn-Block.World: " + world);
                } else {
                    worlds_burn_block.add(world);
                }
            }
        }
    }

    public static void setWGetWorldServerDisableExplosion() {
        if (WorldEventConfig.getConfig().getBoolean("World.Explosion.Disable.Explosion.Disable") && !WorldEventConfig.getConfig().getBoolean("World.Explosion.Disable.Explosion.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Explosion.Disable.Explosion.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Explosion.Disable.Explosion.World: " + world);
                } else {
                    worlds_explosions.add(world);
                }
            }
        }
    }

    public static void setWGetWorldServerDisableLeaveDecay() {
        if (WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Leave-Decay.Disable") && !WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Leave-Decay.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Blocks.Disable.Leave-Decay.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Blocks.Disable.Leave-Decay.World: " + world);
                } else {
                    worlds_LeaveDecay.add(world);
                }
            }
        }
    }

    public static void setWGetWorldServerDisableFireSpread() {
        if (WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.Disable") && !WorldEventConfig.getConfig().getBoolean("World.Burn.Disable.BlockIgnite-FireSpread.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Burn.Disable.BlockIgnite-FireSpread.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Burn.Disable.BlockIgnite-FireSpread.World: " + world);
                } else {
                    worlds_firespread.add(world);
                }
            }
        }
    }

    public static void setWGetWorldServerDisableblockFade() {
        if (WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Block-Fade.Disable") && !WorldEventConfig.getConfig().getBoolean("World.Blocks.Disable.Block-Fade.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Blocks.Disable.Block-Fade.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Blocks.Disable.Block-Fade.World: " + world);
                } else {
                    worlds_BlockFade.add(world);
                }
            }
        }
    }

    // Entity
    public static void setWGetWorldServerDisableSpawningMobAnimals() {
        if (WorldEventConfig.getConfig().getBoolean("World.Disable.Spawning-Monster-Animals.Disable") && !WorldEventConfig.getConfig().getBoolean("World.Disable.Spawning-Monster-Animals.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("World.Disable.Spawning-Monster-Animals.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, World.Disable.Spawning-Monster-Animals.World: " + world);
                } else {
                    worlds_spawning_mob_animals.add(world);
                }
            }
        }
    }
    
    public static void setWShears() {
        if (WorldEventConfig.getConfig().getBoolean("No-Shears.Enable") && !WorldEventConfig.getConfig().getBoolean("No-Shears.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("No-Shears.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, No-Shears.World: " + world);
                } else {
                    worlds_shears.add(world);
                }
            }
        }
    }

    public static void setWEDTP() {
        if (WorldEventConfig.getConfig().getBoolean("DenyEntityTravelPortal.Enable") && !WorldEventConfig.getConfig().getBoolean("DenyEntityTravelPortal.World.All_World")) {
            for (String world: WorldEventConfig.getConfig().getStringList("DenyEntityTravelPortal.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/WorldEvent.yml, DenyEntityTravelPortal.World: " + world);
                } else {
                	worlds_edtp.add(world);
                }
            }
        }
    }
    
    public static List < String > getShears() {
        return worlds_shears;
    }
    
    public static List < String > getPreventionEtityPortal() {
        return worlds_edtp;
    }
    
    public static List < String > getWSMA() {
        return worlds_spawning_mob_animals;
    }

    public static List < String > getWBF() {
        return worlds_BlockFade;
    }

    public static List < String > getWFS() {
        return worlds_firespread;
    }

    public static List < String > getWLD() {
        return worlds_LeaveDecay;
    }

    public static List < String > getWBB() {
        return worlds_burn_block;
    }

    public static List < String > getWE() {
        return worlds_explosions;
    }

    public static List < String > getWW() {
        return worlds_weather;
    }

    public static List < String > getWTC() {
        return worlds_ThunderChange;
    }

    public static List < String > getWLS() {
        return worlds_LightningStrike;
    }

}
