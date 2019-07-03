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

            Config.set("Event.OnJoin.Tp-To-Spawn", Boolean.valueOf(true));
            
            Config.set("Event.OnJoin.Sounds.Enable", Boolean.valueOf(true));
            Config.set("Event.OnJoin.Sounds.Sound", String.valueOf("BLOCK_NOTE_HARP"));
            Config.set("Event.OnJoin.Sounds.Volume", Integer.valueOf(1));
            Config.set("Event.OnJoin.Sounds.Pitch", Integer.valueOf(1));
            Config.set("Event.OnJoin.Sounds.World.All_World", Boolean.valueOf(false));
            Config.set("Event.OnJoin.Sounds.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Event.OnJoin.CustomSpawn.Enable", Boolean.valueOf(false));
            Config.set("Event.OnJoin.CustomSpawn.Spawn", String.valueOf("CHANGE ME"));
            
            Config.set("Event.OnJoin.Custom-Group-Join.VIP.Enable", Boolean.valueOf(false));
            Config.set("Event.OnJoin.Custom-Group-Join.VIP.Spawn", String.valueOf("CHANGE ME"));
            
            Config.set("Spawn.DefaultSpawn", String.valueOf("CHANGE ME"));
            Config.set("Spawn.FirstJoin-Spawn.Enable", Boolean.valueOf(false));
            Config.set("Spawn.FirstJoin-Spawn.Spawn", String.valueOf("CHANGE ME"));
            
            Config.set("Inventory.Force-Selected-Slot.Enable", Boolean.valueOf(true));
            Config.set("Inventory.Force-Selected-Slot.Slot", Integer.valueOf(1));
            Config.set("Inventory.Force-Selected-Slot.World.All_World", Boolean.valueOf(false));
            Config.set("Inventory.Force-Selected-Slot.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("Inventory.Clear.Enable", Boolean.valueOf(true));
            Config.set("Inventory.Clear.Bypass", Boolean.valueOf(true));
            Config.set("Inventory.Clear.Options.Armor", Boolean.valueOf(true));
            Config.set("Inventory.Clear.Options.Inventory", Boolean.valueOf(true));
            Config.set("Inventory.Clear.World.All_World", Boolean.valueOf(false));
            Config.set("Inventory.Clear.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Chat.Clear.Enable", Boolean.valueOf(true));
            Config.set("Chat.Clear.Bypass", Boolean.valueOf(true));
            Config.set("Chat.Clear.Lines-To-Clear", Integer.valueOf(150));
            Config.set("Chat.Clear.World.All_World", Boolean.valueOf(false));
            Config.set("Chat.Clear.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Change-Gamemode.Enable", Boolean.valueOf(true));
            Config.set("Change-Gamemode.Value", Integer.valueOf(2));
            Config.set("Change-Gamemode.Bypass-With-Permission", Boolean.valueOf(true));
            Config.set("Change-Gamemode.World.All_World", Boolean.valueOf(false));
            Config.set("Change-Gamemode.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            Config.set("Restore.Food.Enable", Boolean.valueOf(true));
            Config.set("Restore.Food.Value", Integer.valueOf(20));
            Config.set("Restore.Food.Bypass-With-Permission", Boolean.valueOf(false));
            Config.set("Restore.Food.World.All_World", Boolean.valueOf(false));
            Config.set("Restore.Food.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Restore.Health.Enable", Boolean.valueOf(true));
            Config.set("Restore.Health.Value", Double.valueOf(20.0));
            Config.set("Restore.Health.Bypass-With-Permission", Boolean.valueOf(false));
            Config.set("Restore.Health.World.All_World", Boolean.valueOf(false));
            Config.set("Restore.Health.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("XP.Enable", Boolean.valueOf(true));
            Config.set("XP.Options.Exp.Enable", Boolean.valueOf(true));
            Config.set("XP.Options.Exp.Value", Double.valueOf(0.3));
            Config.set("XP.Options.Exp.World.All_World", Boolean.valueOf(false));
            Config.set("XP.Options.Exp.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("XP.Options.Level.Enable", Boolean.valueOf(true));
            Config.set("XP.Options.Level.Value", Integer.valueOf(10));
            Config.set("XP.Options.Level.World.All_World", Boolean.valueOf(false));
            Config.set("XP.Options.Level.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Fly.Enable", Boolean.valueOf(true));
            Config.set("Fly.World.All_World", Boolean.valueOf(false));
            Config.set("Fly.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Title.Enable", Boolean.valueOf(true));
            
            Config.set("Title.First-Join.Enable", Boolean.valueOf(true));
            Config.set("Title.First-Join.Title.Enable", Boolean.valueOf(true));
            Config.set("Title.First-Join.Title.FadeIn", Integer.valueOf(20));
            Config.set("Title.First-Join.Title.Stay", Integer.valueOf(150));
            Config.set("Title.First-Join.Title.FadeOut", Integer.valueOf(20));
            Config.set("Title.First-Join.Title.Message", "&6Welcome %player%");
            
            Config.set("Title.First-Join.SubTitle.Enable", Boolean.valueOf(true));
            Config.set("Title.First-Join.SubTitle.FadeIn", Integer.valueOf(20));
            Config.set("Title.First-Join.SubTitle.Stay", Integer.valueOf(150));
            Config.set("Title.First-Join.SubTitle.FadeOut", Integer.valueOf(20));
            Config.set("Title.First-Join.SubTitle.Message", "&eThanks to choose &6Hawn");
            
            Config.set("Title.First-Join.World.All_World", Boolean.valueOf(false));
            Config.set("Title.First-Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));

            Config.set("Title.Join.Enable", Boolean.valueOf(true));
            Config.set("Title.Join.Title.Enable", Boolean.valueOf(true));
            Config.set("Title.Join.Title.FadeIn", Integer.valueOf(20));
            Config.set("Title.Join.Title.Stay", Integer.valueOf(150));
            Config.set("Title.Join.Title.FadeOut", Integer.valueOf(20));
            Config.set("Title.Join.Title.Message", "&6Welcome %player%");
            
            Config.set("Title.Join.SubTitle.Enable", Boolean.valueOf(true));
            Config.set("Title.Join.SubTitle.FadeIn", Integer.valueOf(20));
            Config.set("Title.Join.SubTitle.Stay", Integer.valueOf(150));
            Config.set("Title.Join.SubTitle.FadeOut", Integer.valueOf(20));
            Config.set("Title.Join.SubTitle.Message", "&eThanks to choose &6Hawn");
            
            Config.set("Title.Join.World.All_World", Boolean.valueOf(false));
            Config.set("Title.Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Action-Bar.Enable", Boolean.valueOf(true));
            
            Config.set("Action-Bar.First-Join.Enable", Boolean.valueOf(true));
            Config.set("Action-Bar.First-Join.Message", "&6Welcome %player%"); 
            
            Config.set("Action-Bar.First-Join.World.All_World", Boolean.valueOf(false));
            Config.set("Action-Bar.First-Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));

            Config.set("Action-Bar.Join.Enable", Boolean.valueOf(true));
            Config.set("Action-Bar.Join.Message", "&6Welcome %player%"); 
            
            Config.set("Action-Bar.Join.World.All_World", Boolean.valueOf(false));
            Config.set("Action-Bar.Join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
                }));
            
            Config.set("Speed.Enable", Boolean.valueOf(true));
            Config.set("Speed.Value", Integer.valueOf(2));
            Config.set("Speed.Option.Priority-For-Player-Option", Boolean.valueOf(true));
            Config.set("Speed.World.All_World", Boolean.valueOf(false));
            Config.set("Speed.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            // Potion
            Config.set("Potion-Effect.BLINDNESS.Enable", Boolean.valueOf(true));
            Config.set("Potion-Effect.BLINDNESS.Use_Permission", Boolean.valueOf(false));
            Config.set("Potion-Effect.BLINDNESS.Duration-Second", Integer.valueOf(2));
            Config.set("Potion-Effect.BLINDNESS.Amplifier", Integer.valueOf(2));
            Config.set("Potion-Effect.BLINDNESS.World.All_World", Boolean.valueOf(false));
            Config.set("Potion-Effect.BLINDNESS.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            Config.set("Potion-Effect.JUMP.Enable", Boolean.valueOf(true));
            Config.set("Potion-Effect.JUMP.Use_Permission", Boolean.valueOf(false));
            Config.set("Potion-Effect.JUMP.Duration-Second", Integer.valueOf(2));
            Config.set("Potion-Effect.JUMP.Amplifier", Integer.valueOf(2));
            Config.set("Potion-Effect.JUMP.World.All_World", Boolean.valueOf(false));
            Config.set("Potion-Effect.JUMP.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"
            }));
            
            saveConfigFile();

        }
    }

}