package fr.Dianox.Hawn.Utility.World;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;

public class OnJoinPW {

    public static List < String > motd_world = new ArrayList < String > ();
    public static List < String > jm_world = new ArrayList < String > ();
    public static List < String > worlds_Food = new ArrayList < String > ();
    public static List < String > worlds_Health = new ArrayList < String > ();
    public static List < String > worlds_inventory = new ArrayList < String > ();
    public static List < String > worlds_clear_chat = new ArrayList < String > ();
    public static List < String > worlds_XP_Exp = new ArrayList < String > ();
    public static List < String > worlds_XP_Lvl = new ArrayList < String > ();
    public static List < String > worlds_fly = new ArrayList < String > ();
    public static List < String > world_pe_blindness = new ArrayList < String > ();
    public static List < String > world_pe_jump = new ArrayList < String > ();
    public static List < String > world_speed_on_join = new ArrayList < String > ();
    public static List < String > worlds_first_join_ab = new ArrayList < String > ();
    public static List < String > worlds_join_ab = new ArrayList < String > ();
    public static List < String > worlds_first_join_title = new ArrayList < String > ();
    public static List < String > worlds_join_title = new ArrayList < String > ();
    public static List < String > worlds_sounds_join = new ArrayList < String > ();

    // MOTD
    public static void setWGetWorldforMOTD() {
        if (ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.Enable") && !ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.World.All_World")) {
            for (String world: ConfigMGeneral.getConfig().getStringList("Spawn.On-join.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Messages/General.yml, Spawn.On-join.World: " + world);
                } else {
                    motd_world.add(world);
                }
            }
        }
    }

    public static List < String > getWD() {
        return motd_world;
    }

    // Join message
    public static void setGetWorldforJoinMessage() {
        if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Enable") && !ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.World.All_World")) {
            for (String world: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Messages/General.yml, General.On-join.Join-Message: " + world);
                } else {
                    jm_world.add(world);
                }
            }
        }
    }

    public static List < String > getJM() {
        return jm_world;
    }

    // Food
    public static void setWGetWorldFood() {
        if (OnJoinConfig.getConfig().getBoolean("Restore.Food.Enable") && !OnJoinConfig.getConfig().getBoolean("Restore.Food.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Restore.Food.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Restore.Food.World: " + world);
                } else {
                    worlds_Food.add(world);
                }
            }
        }
    }

    // Santé
    public static void setWGetWorldHealth() {
        if (OnJoinConfig.getConfig().getBoolean("Restore.Health.Enable") && !OnJoinConfig.getConfig().getBoolean("Restore.Health.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Restore.Health.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Restore.Health.World: " + world);
                } else {
                    worlds_Health.add(world);
                }
            }
        }
    }

    public static List < String > getWFood() {
        return worlds_Food;
    }

    public static List < String > getWHealth() {
        return worlds_Health;
    }

    // Clear inventory
    public static void setWGetWorldInventory() {
        if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Enable") && !OnJoinConfig.getConfig().getBoolean("Inventory.Clear.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Inventory.Clear.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Inventory.Clear.World: " + world);
                } else {
                    worlds_inventory.add(world);
                }
            }
        }
    }

    public static List < String > getWInventory() {
        return worlds_inventory;
    }

    // Clearchat
    public static void setWGetWorldClearChat() {
        if (OnJoinConfig.getConfig().getBoolean("Chat.Clear.Enable") && !OnJoinConfig.getConfig().getBoolean("Chat.Clear.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Chat.Clear.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Chat.Clear.World: " + world);
                } else {
                    worlds_clear_chat.add(world);
                }
            }
        }
    }

    public static List < String > getWClearChat() {
        return worlds_clear_chat;
    }

    // Experience
    public static void setWGetWorldResetExperience() {
        if (OnJoinConfig.getConfig().getBoolean("XP.Options.Exp.Enable") && !OnJoinConfig.getConfig().getBoolean("XP.Options.Exp.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("XP.Options.Exp.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, XP.Options.Exp.World: " + world);
                } else {
                    worlds_XP_Exp.add(world);
                }
            }
        }
    }

    public static void setWGetWorldResetLevel() {
        if (OnJoinConfig.getConfig().getBoolean("XP.Options.Level.Enable") && !OnJoinConfig.getConfig().getBoolean("XP.Options.Level.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("XP.Options.Level.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, XP.Options.Level.World: " + world);
                } else {
                    worlds_XP_Lvl.add(world);
                }
            }
        }
    }

    public static List < String > getWXPEXP() {
        return worlds_XP_Exp;
    }

    public static List < String > getWXPLVL() {
        return worlds_XP_Lvl;
    }

    // Sounds
    public static void setWGetWorldSoundJoin() {
        if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Sounds.Enable") && !OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Sounds.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Event.OnJoin.Sounds.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Event.OnJoin.Sounds.World: " + world);
                } else {
                    worlds_sounds_join.add(world);
                }
            }
        }
    }

    public static List < String > getWSoundsJoin() {
        return worlds_sounds_join;
    }

    // Fly
    public static void setWGetWorldflyoj() {
        if (OnJoinConfig.getConfig().getBoolean("Fly.Enable") && !OnJoinConfig.getConfig().getBoolean("Fly.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Fly.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Fly.World: " + world);
                } else {
                    worlds_sounds_join.add(world);
                }
            }
        }
    }

    public static List < String > getWflyoj() {
        return worlds_sounds_join;
    }

    // Title on join
    public static void setWGetWorldFirstJoinTitle() {
        if (OnJoinConfig.getConfig().getBoolean("Title.First-Join.Enable") && !OnJoinConfig.getConfig().getBoolean("Title.First-Join.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Title.First-Join.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Title.First-Join.World: " + world);
                } else {
                    worlds_first_join_title.add(world);
                }
            }
        }
    }

    public static void setWGetWorldJoinTitle() {
        if (OnJoinConfig.getConfig().getBoolean("Title.Join.Enable") && !OnJoinConfig.getConfig().getBoolean("Title.Join.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Title.Join.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Title.Join.World: " + world);
                } else {
                    worlds_join_title.add(world);
                }
            }
        }
    }

    public static List < String > getWFirstJoinTitle() {
        return worlds_first_join_title;
    }

    public static List < String > getWJoinTitle() {
        return worlds_join_title;
    }

    // AB on join
    public static void setWGetWorldFirstJoinab() {
        if (OnJoinConfig.getConfig().getBoolean("Action-Bar.First-Join.Enable") && !OnJoinConfig.getConfig().getBoolean("Action-Bar.First-Join.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Action-Bar.First-Join.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Action-Bar.First-Join.World: " + world);
                } else {
                    worlds_first_join_ab.add(world);
                }
            }
        }
    }

    public static void setWGetWorldJoinab() {
        if (OnJoinConfig.getConfig().getBoolean("Action-Bar.Join.Enable") && !OnJoinConfig.getConfig().getBoolean("Action-Bar.Join.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Action-Bar.Join.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Action-Bar.Join.World: " + world);
                } else {
                    worlds_join_ab.add(world);
                }
            }
        }
    }

    public static List < String > getWFirstJoinab() {
        return worlds_first_join_ab;
    }

    public static List < String > getWJoinab() {
        return worlds_join_ab;
    }

    // Others
    public static void setWSOJ() {
        if (OnJoinConfig.getConfig().getBoolean("Speed.Enable") && !OnJoinConfig.getConfig().getBoolean("Speed.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Speed.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Speed.World: " + world);
                } else {
                    world_speed_on_join.add(world);
                }
            }
        }
    }

    public static List < String > getSOJ() {
        return world_speed_on_join;
    }

    // Potion effect
    public static void setWGetWorldblindess() {
        if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.JUMP.Enable") && !OnJoinConfig.getConfig().getBoolean("Potion-Effect.JUMP.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Potion-Effect.JUMP.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Potion-Effect.JUMP.World: " + world);
                } else {
                    world_pe_blindness.add(world);
                }
            }
        }
    }

    public static void setWGetWorldjump() {
        if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.BLINDNESS.Enable") && !OnJoinConfig.getConfig().getBoolean("Potion-Effect.BLINDNESS.World.All_World")) {
            for (String world: OnJoinConfig.getConfig().getStringList("Potion-Effect.BLINDNESS.World.Worlds")) {
                if (Bukkit.getWorld(world) == null) {
                    System.out.println("| Invalid world in Events/OnJoin.yml, Potion-Effect.BLINDNESS.World: " + world);
                } else {
                    world_pe_jump.add(world);
                }
            }
        }
    }

    public static List < String > getWblindess() {
        return world_pe_blindness;
    }

    public static List < String > getWjump() {
        return world_pe_jump;
    }

}
