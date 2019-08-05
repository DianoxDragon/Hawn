package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.Commands.OptionPlayerConfigCommand;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerEventsConfig;

public class PlayerEventsPW {
	
	public static List<String> worlds_playeroption_join = new ArrayList<String>();
	
	public static void setPlayerOptionJoin() {
		if (OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.Enable")) {
	        if (!OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.World.All_World")) {
	            for (String world : OptionPlayerConfigCommand.getConfig().getStringList("PlayerOption.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in PlayerOption.yml, PlayerOption.World: "+world);
	            	} else {
	            		worlds_playeroption_join.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWJoinPlayerOption() {
		return worlds_playeroption_join;
	}
	
	public static List<String> worlds_item_drop = new ArrayList<String>();
	public static List<String> worlds_item_pickup = new ArrayList<String>();
	public static List<String> worlds_item_move = new ArrayList<String>();
	public static List<String> worlds_item_damageitem = new ArrayList<String>();
	
	public static void setWGetWorldItemDrop() {
		if (PlayerEventsConfig.getConfig().getBoolean("Items.Drop.Disable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Items.Drop.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Items.Drop.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Items.Drop.World: "+world);
	            	} else {
	            		worlds_item_drop.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldItemPickUP() {
		if (PlayerEventsConfig.getConfig().getBoolean("Items.PickUp.Disable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Items.PickUp.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Items.PickUp.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Items.PickUp.World: "+world);
	            	} else {
	            		worlds_item_pickup.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldMoveItem() {
		if (PlayerEventsConfig.getConfig().getBoolean("Items.Move.Disable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Items.Move.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Items.Move.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Items.Move.World: "+world);
	            	} else {
	            		worlds_item_move.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWGetWorldItemDamage() {
		if (PlayerEventsConfig.getConfig().getBoolean("Items.Damage-Item.Disable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Items.Damage-Item.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Items.Damage-Item.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Items.Damage-Item.World: "+world);
	            	} else {
	            		worlds_item_damageitem.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWItemDrop() {
		return worlds_item_drop;
	}
	
	public static List<String> getWItemPickUp() {
		return worlds_item_pickup;
	}
	
	public static List<String> getWMoveItem() {
		return worlds_item_move;
	}

	public static List<String> getWItemDamage() {
		return worlds_item_damageitem;
	}
	
	// Death
	public static List<String> worlds_death_message = new ArrayList<String>();
	public static List<String> worlds_respawn = new ArrayList<String>();
	
	public static void setWGetWorldServerDisableDeathMessage() {
		if (PlayerEventsConfig.getConfig().getBoolean("Death.Death-Message.Disable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Death.Death-Message.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Death.Death-Message.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Death.Death-Message.World: "+world);
	            	} else {
	            		worlds_death_message.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static void setWRespawnEvent() {
		if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Enable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Death.Respawn.Player.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Death.Respawn.Player.World: "+world);
	            	} else {
	            		worlds_respawn.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWDM() {
		return worlds_death_message;
	}
	
	public static List<String> getWEventResapwn() {
		return worlds_respawn;
	}
	
	// On Join inventory
	public static List<String> worlds_force_selected_slot = new ArrayList<String>();
	
	public static void setWGetWorldForceSelectedJoin() {
		if (OnJoinConfig.getConfig().getBoolean("Inventory.Force-Selected-Slot.Enable")) {
	        if (!OnJoinConfig.getConfig().getBoolean("Inventory.Force-Selected-Slot.World.All_World")) {
	            for (String world : OnJoinConfig.getConfig().getStringList("Inventory.Force-Selected-Slot.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/OnJoin.yml, Inventory.Force-Selected-Slot.World: "+world);
	            	} else {
	            		worlds_force_selected_slot.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWOForceSelectedSlot() {
		return worlds_force_selected_slot;
	}
	
	// Others
	public static List<String> worlds_item_clearondrop = new ArrayList<String>();
	
	public static void setWGetWorldClearDropOnDeath() {
		if (PlayerEventsConfig.getConfig().getBoolean("Items.Clear-Drops-On-Death.Enable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Items.Clear-Drops-On-Death.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Items.Clear-Drops-On-Death.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Items.Clear-Drops-On-Death.World: "+world);
	            	} else {
	            		worlds_item_clearondrop.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWClearOnDropsOnDeath() {
		return worlds_item_clearondrop;
	}
	
	public static List<String> worlds_fun_doublejump = new ArrayList<String>();
	
	public static void setWGetFunDoubleJump() {
		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Enable")) {
	        if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
	            for (String world : ConfigFDoubleJump.getConfig().getStringList("DoubleJump.Double.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Cosmetics-Fun/DoubleJump.yml, DoubleJump.Double.World: "+world);
	            	} else {
	            		worlds_fun_doublejump.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWFDoubleJump() {
		return worlds_fun_doublejump;
	}
	
	/*
	 * Player respawn join items
	 */
	public static List<String> worlds_respawncji = new ArrayList<String>();
	
	public static void setWGetWorldRCJI() {
		if (PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.Enable")) {
	        if (!PlayerEventsConfig.getConfig().getBoolean("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.World.All_World")) {
	            for (String world : PlayerEventsConfig.getConfig().getStringList("Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.World.Worlds")) {
	            	if (Bukkit.getWorld(world) == null) {
	            		System.out.println("| Invalid world in Events/PlayerEvents.yml, Death.Respawn.Player.Regive-Hawn-Custom-Join-Items.World: "+world);
	            	} else {
	            		worlds_respawncji.add(world);
	            	}
	            }
	        }
        }
	}
	
	public static List<String> getWFRPCJI() {
		return worlds_respawncji;
	}
}
