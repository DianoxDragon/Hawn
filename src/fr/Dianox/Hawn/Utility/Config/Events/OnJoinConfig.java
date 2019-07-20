package fr.Dianox.Hawn.Utility.Config.Events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class OnJoinConfig {
    
    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;
    
    public OnJoinConfig() {}
    
    public static void loadConfig(Plugin plugin) {
        pl = plugin;
        
        file = new File(pl.getDataFolder(), "Events/OnJoin.yml");
        Config = YamlConfiguration.loadConfiguration(file);
        
        if (!pl.getDataFolder().exists()) {
            pl.getDataFolder().mkdir();
        }
        
        create();
    }
    
    public static File getFile() {
        return file;
    }

    public static YamlConfiguration getConfig() {
        return Config;
    }

    public static void reloadConfig() {
        loadConfig(pl);
    }

    public static void saveConfigFile() {
        try {
            Config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void create() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {}

            Config.set("Event.OnJoin.Tp-To-Spawn", true);
            
            Config.set("Event.OnJoin.Sounds.Enable", true);
            Config.set("Event.OnJoin.Sounds.Sound", "BLOCK_NOTE_HARP");
            Config.set("Event.OnJoin.Sounds.Volume", 1);
            Config.set("Event.OnJoin.Sounds.Pitch", 1);
            Config.set("Event.OnJoin.Sounds.World.All_World", false);
            Config.set("Event.OnJoin.Sounds.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Event.OnJoin.CustomSpawn.Enable", false);
            Config.set("Event.OnJoin.CustomSpawn.Spawn", "CHANGE ME");
            
            Config.set("Event.OnJoin.Custom-Group-Join.VIP.Enable", false);
            Config.set("Event.OnJoin.Custom-Group-Join.VIP.Spawn", "CHANGE ME");
            
            Config.set("Spawn.DefaultSpawn", "CHANGE ME");
            Config.set("Spawn.FirstJoin-Spawn.Enable", false);
            Config.set("Spawn.FirstJoin-Spawn.Spawn", "CHANGE ME");
            
            Config.set("Inventory.Force-Selected-Slot.Enable", true);
            Config.set("Inventory.Force-Selected-Slot.Slot", 1);
            Config.set("Inventory.Force-Selected-Slot.World.All_World", false);
            Config.set("Inventory.Force-Selected-Slot.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("Inventory.Clear.Enable", true);
            Config.set("Inventory.Clear.Bypass", true);
            Config.set("Inventory.Clear.Options.Armor", true);
            Config.set("Inventory.Clear.Options.Inventory", true);
            Config.set("Inventory.Clear.World.All_World", false);
            Config.set("Inventory.Clear.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Chat.Clear.Enable", true);
            Config.set("Chat.Clear.Bypass", true);
            Config.set("Chat.Clear.Lines-To-Clear", 150);
            Config.set("Chat.Clear.World.All_World", false);
            Config.set("Chat.Clear.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Change-Gamemode.Enable", true);
            Config.set("Change-Gamemode.Value", 2);
            Config.set("Change-Gamemode.Bypass-With-Permission", true);
            Config.set("Change-Gamemode.World.All_World", false);
            Config.set("Change-Gamemode.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("Restore.Food.Enable", true);
            Config.set("Restore.Food.Value", 20);
            Config.set("Restore.Food.Bypass-With-Permission", false);
            Config.set("Restore.Food.World.All_World", false);
            Config.set("Restore.Food.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Restore.Health.Enable", true);
            Config.set("Restore.Health.Value", Double.valueOf(20.0));
            Config.set("Restore.Health.Bypass-With-Permission", false);
            Config.set("Restore.Health.World.All_World", false);
            Config.set("Restore.Health.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("XP.Enable", true);
            Config.set("XP.Options.Exp.Enable", true);
            Config.set("XP.Options.Exp.Value", Double.valueOf(0.3));
            Config.set("XP.Options.Exp.World.All_World", false);
            Config.set("XP.Options.Exp.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("XP.Options.Level.Enable", true);
            Config.set("XP.Options.Level.Value", 10);
            Config.set("XP.Options.Level.World.All_World", false);
            Config.set("XP.Options.Level.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Fly.Enable", true);
            Config.set("Fly.World.All_World", false);
            Config.set("Fly.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Title.Enable", true);
            
            Config.set("Title.First-Join.Enable", true);
            Config.set("Title.First-Join.Title.Enable", true);
            Config.set("Title.First-Join.Title.FadeIn", 20);
            Config.set("Title.First-Join.Title.Stay", 150);
            Config.set("Title.First-Join.Title.FadeOut", 20);
            Config.set("Title.First-Join.Title.Message", "&6Welcome %player%");
            
            Config.set("Title.First-Join.SubTitle.Enable", true);
            Config.set("Title.First-Join.SubTitle.FadeIn", 20);
            Config.set("Title.First-Join.SubTitle.Stay", 150);
            Config.set("Title.First-Join.SubTitle.FadeOut", 20);
            Config.set("Title.First-Join.SubTitle.Message", "&eThanks to choose &6Hawn");
            
            Config.set("Title.First-Join.World.All_World", false);
            Config.set("Title.First-Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));

            Config.set("Title.Join.Enable", true);
            Config.set("Title.Join.Title.Enable", true);
            Config.set("Title.Join.Title.FadeIn", 20);
            Config.set("Title.Join.Title.Stay", 150);
            Config.set("Title.Join.Title.FadeOut", 20);
            Config.set("Title.Join.Title.Message", "&6Welcome %player%");
            
            Config.set("Title.Join.SubTitle.Enable", true);
            Config.set("Title.Join.SubTitle.FadeIn", 20);
            Config.set("Title.Join.SubTitle.Stay", 150);
            Config.set("Title.Join.SubTitle.FadeOut", 20);
            Config.set("Title.Join.SubTitle.Message", "&eThanks to choose &6Hawn");
            
            Config.set("Title.Join.World.All_World", false);
            Config.set("Title.Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Action-Bar.Enable", true);
            
            Config.set("Action-Bar.First-Join.Enable", true);
            Config.set("Action-Bar.First-Join.Message", "&6Welcome %player%"); 
            Config.set("Action-Bar.First-Join.Time-Stay", 150); 
            
            Config.set("Action-Bar.First-Join.World.All_World", false);
            Config.set("Action-Bar.First-Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));

            Config.set("Action-Bar.Join.Enable", true);
            Config.set("Action-Bar.Join.Message", "&6Welcome %player%"); 
            Config.set("Action-Bar.Join.Time-Stay", 150); 
            
            Config.set("Action-Bar.Join.World.All_World", false);
            Config.set("Action-Bar.Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Speed.Enable", true);
            Config.set("Speed.Value", 2);
            Config.set("Speed.Option.Priority-For-Player-Option", true);
            Config.set("Speed.World.All_World", false);
            Config.set("Speed.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Potion
            Config.set("Potion-Effect.BLINDNESS.Enable", true);
            Config.set("Potion-Effect.BLINDNESS.Use_Permission", false);
            Config.set("Potion-Effect.BLINDNESS.Duration-Second", 2);
            Config.set("Potion-Effect.BLINDNESS.Amplifier", 2);
            Config.set("Potion-Effect.BLINDNESS.World.All_World", false);
            Config.set("Potion-Effect.BLINDNESS.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Potion-Effect.JUMP.Enable", true);
            Config.set("Potion-Effect.JUMP.Use_Permission", false);
            Config.set("Potion-Effect.JUMP.Duration-Second", 2);
            Config.set("Potion-Effect.JUMP.Amplifier", 2);
            Config.set("Potion-Effect.JUMP.World.All_World", false);
            Config.set("Potion-Effect.JUMP.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            saveConfigFile();

        }
    }

}
