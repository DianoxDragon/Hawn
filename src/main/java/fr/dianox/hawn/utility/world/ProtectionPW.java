package fr.dianox.hawn.utility.world;

import fr.dianox.hawn.utility.config.events.ConfigGProtection;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class ProtectionPW {

    public static List < String > worlds_c_place = new ArrayList < String > ();
    public static List < String > worlds_c_break = new ArrayList < String > ();
    public static List < String > worlds_HagingBreakByEntity = new ArrayList < String > ();
    public static List < String > worlds_PlayerInteractEntity_ItemFrame = new ArrayList < String > ();
    public static List < String > worlds_PlayerInteract_Items_Blocks = new ArrayList < String > ();
    public static List < String > worlds_buckets = new ArrayList < String > ();
    
    public static void setWworldBukets() {
        if (ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.Enable") && !ConfigGProtection.getConfig().getBoolean("Protection.Anti-Bucket-Use.World.All_World")) {
            for (String world: ConfigGProtection.getConfig().getStringList("Protection.Anti-Bucket-Use.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.Anti-Bucket-Use.World: " + world);
                } else {
                	worlds_buckets.add(world);
                }
            }
        }
    }
    
    public static void setWGetWorldProtectionPlace() {
        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.Enable") && !ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Place.World.All_World")) {
            for (String world: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Place.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.Construct.Anti-Place.World: " + world);
                } else {
                    worlds_c_place.add(world);
                }
            }
        }
    }

    public static void setWGetWorldProtectionBreak() {
        if (ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.Enable") && !ConfigGProtection.getConfig().getBoolean("Protection.Construct.Anti-Break.World.All_World")) {
            for (String world: ConfigGProtection.getConfig().getStringList("Protection.Construct.Anti-Break.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.Construct.Anti-Break.World: " + world);
                } else {
                    worlds_c_break.add(world);
                }
            }
        }
    }

    public static void setWGetWorldProtectionHagingBreakByEntity() {
        if (ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.Enable") && !ConfigGProtection.getConfig().getBoolean("Protection.HagingBreakByEntity.World.All_World")) {
            for (String world: ConfigGProtection.getConfig().getStringList("Protection.HagingBreakByEntity.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.HagingBreakByEntity.World: " + world);
                } else {
                    worlds_HagingBreakByEntity.add(world);
                }
            }
        }
    }

    public static void setWGetWorldProtectionPlayerInteractEntityItemFrame() {
        if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.Enable") && !ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteractEntity-ItemFrame.World.All_World")) {
            for (String world: ConfigGProtection.getConfig().getStringList("Protection.PlayerInteractEntity-ItemFrame.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.PlayerInteractEntity-ItemFrame.World: " + world);
                } else {
                    worlds_PlayerInteractEntity_ItemFrame.add(world);
                }
            }
        }
    }
    
    public static void setWGetWorldProtectionPlayerInteractItemsBlocks() {
        if (ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.Enable") && !ConfigGProtection.getConfig().getBoolean("Protection.PlayerInteract-Items-Blocks.World.All_World")) {
            for (String world: ConfigGProtection.getConfig().getStringList("Protection.PlayerInteract-Items-Blocks.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/ProtectionWorld.yml, Protection.PlayerInteract-Items-Blocks.World: " + world);
                } else {
                	worlds_PlayerInteract_Items_Blocks.add(world);
                }
            }
        }
    }

    public static List < String > getWPIIB() {
        return worlds_PlayerInteract_Items_Blocks;
    }
    
    public static List < String > getWHBBE() {
        return worlds_HagingBreakByEntity;
    }

    public static List < String > getWPCB() {
        return worlds_c_break;
    }

    public static List < String > getWPCP() {
        return worlds_c_place;
    }

    public static List < String > getWPIEIF() {
        return worlds_PlayerInteractEntity_ItemFrame;
    }
    
    public static List < String > getWB() {
        return worlds_buckets;
    }

}
