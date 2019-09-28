package fr.dianox.hawn.utility.world;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.dianox.hawn.utility.config.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiLobbyBow;

public class CjiPW {

	public static List < String > worlds_po_lobbybow_item = new ArrayList < String > ();
    public static List < String > worlds_po_playervisibility_item = new ArrayList < String > ();
    public static List < String > worlds_general = new ArrayList < String > ();
    
    public static void setpoLobbyBow() {
        if (SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.Enable") && !SpecialCjiLobbyBow.getConfig().getBoolean("LobbyBow.World.All_World")) {
            for (String world: SpecialCjiLobbyBow.getConfig().getStringList("LobbyBow.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Special-LobbyBow.yml, LobbyBow.World: " + world);
                } else {
                	worlds_po_lobbybow_item.add(world);
                }
            }
        }
    }
    
    public static void setItemPlayerVisibility() {
        if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Enable") && !SpecialCjiHidePlayers.getConfig().getBoolean("PV.World.All_World")) {
            for (String world: SpecialCjiHidePlayers.getConfig().getStringList("PV.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Special-HidePlayers.yml, PV.World: " + world);
                } else {
                    worlds_po_playervisibility_item.add(world);
                }
            }
        }
    }
    
    public static void setItemPlayerGeneral() {
        if (ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.Enable") && !ConfigCJIGeneral.getConfig().getBoolean("Custom-Join-Item.General-Option.World.All_World")) {
            for (String world: ConfigCJIGeneral.getConfig().getStringList("Custom-Join-Item.General-Option.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in General.yml, Custom-Join-Item.General-Option.World: " + world);
                } else {
                	worlds_general.add(world);
                }
            }
        }
    }
    
    public static List < String > getLobbyBow() {
        return worlds_po_lobbybow_item;
    }
    
    public static List < String > getWItemPVOnJoin() {
        return worlds_po_playervisibility_item;
    }
    
    public static List < String > getWItemPG() {
        return worlds_general;
    }

}