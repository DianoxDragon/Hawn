package fr.dianox.hawn.utility.config.messages;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.dianox.hawn.Main;

public class ConfigMGeneral {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMGeneral() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/"+ Main.LanguageType +"/General.yml");
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

            Config.set("General.Prefix", " &3Hawn &7|");
            Config.set("General.On-join.Join-Message.Enable", true);
            Config.set("General.On-join.Join-Message.Just-Simply-Disable-All-Join-Messages", false);
            Config.set("General.On-join.Join-Message.Disable-Default-Message", true);
            Config.set("General.On-join.Join-Message.Silent-Staff-Join", true);
            Config.set("General.On-join.Join-Message.Disable-For-New-Players", false);
            Config.set("General.On-join.Join-Message.Broadcast-To-Console", true);
            
            Config.set("General.On-join.Join-Message.Per-Group.Options.Enable", true);
            Config.set("General.On-join.Join-Message.Per-Group.Options.Disable-Any-Messages-On-Join", false);
            Config.set("General.On-join.Join-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
                    "&cPLEASE WELCOME a owner"}));
            Config.set("General.On-join.Join-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
                    "unlimited groups of course"}));
            
            Config.set("General.On-join.Join-Message.Per-World.Options.Enable", false);
            Config.set("General.On-join.Join-Message.Per-World.Options.Disable-Any-Other-Messages-On-Join", false);
            Config.set("General.On-join.Join-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World", false);
            Config.set("General.On-join.Join-Message.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
                    "&ctest1"}));
            Config.set("General.On-join.Join-Message.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
                    "test2"}));
            
            Config.set("General.On-join.Join-Message.Messages", java.util.Arrays.asList(new String[] {"&7[&a+&7] %player%"}));
            Config.set("General.On-join.Join-Message.World.All_World", true);
            Config.set("General.On-join.Join-Message.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"}));
            
            Config.set("General.On-Quit.Quit-Message.Enable", true);
            Config.set("General.On-Quit.Quit-Message.Just-Simply-Disable-All-Quit-Messages", false);
            Config.set("General.On-Quit.Quit-Message.Disable-Default-Message", true);
            Config.set("General.On-Quit.Quit-Message.Silent-Staff-Quit", true);
            Config.set("General.On-Quit.Quit-Message.Broadcast-To-Console", true);
            
            Config.set("General.On-Quit.Quit-Message.Per-Group.Options.Enable", true);
            Config.set("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit", true);
            Config.set("General.On-Quit.Quit-Message.Per-Group.Groups.Owner", java.util.Arrays.asList(new String[] {
            		"&cPLEASE SAY GOODBYE a owner"}));
            Config.set("General.On-Quit.Quit-Message.Per-Group.Groups.Admin", java.util.Arrays.asList(new String[] {
            		"unlimited groups of course"}));
            
            Config.set("General.On-Quit.Quit-Message.Per-World.Options.Enable", false);
            Config.set("General.On-Quit.Quit-Message.Per-World.Options.Disable-Any-Other-Messages-On-Quit", false);
            Config.set("General.On-Quit.Quit-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World", false);
            Config.set("General.On-Quit.Quit-Message.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
                    "&ctest1"}));
            Config.set("General.On-Quit.Quit-Message.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
                    "test2"}));
            
            Config.set("General.On-Quit.Quit-Message.Messages", java.util.Arrays.asList(new String[] {"&7[&c-&7] %player%"}));
            Config.set("General.On-Quit.Quit-Message.World.All_World", true);
            Config.set("General.On-Quit.Quit-Message.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"}));
            
            Config.set("Spawn.On-join.Enable", true);
            Config.set("Spawn.On-join.Messages", java.util.Arrays.asList(new String[] {
            		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>",
                    "&cHello %player%",
                    "&cDon't forget to see our rules",
            		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>"}));
            Config.set("Spawn.On-join.World.All_World", true);
            Config.set("Spawn.On-join.World.Worlds", java.util.Arrays.asList(new String[] {
                    "world",
                    "world_nether"}));
            
            Config.set("Spawn.On-join.Per-World.Options.Enable", false);
            Config.set("Spawn.On-join.Per-World.Options.Disable-All-The-Others-Motd", false);
            Config.set("Spawn.On-join.Per-World.Worlds.world", java.util.Arrays.asList(new String[] {
				"&ctest1 - motd"}));
            Config.set("Spawn.On-join.Per-World.Worlds.world_the_end", java.util.Arrays.asList(new String[] {
				"test2 - motd"}));
            
            Config.set("Spawn.On-join.First-Join.Broadcast.Enable", true);
            Config.set("Spawn.On-join.First-Join.Broadcast.Broadcast-To-The-Console", true);
            Config.set("Spawn.On-join.First-Join.Broadcast.Messages", java.util.Arrays.asList(new String[] {"&eWelcome %player% to the server"}));
            Config.set("Spawn.On-join.First-Join.Motd.Enable", true);
            Config.set("Spawn.On-join.First-Join.Motd.Both-Motd", false);
            Config.set("Spawn.On-join.First-Join.Motd.Messages", java.util.Arrays.asList(new String[] {
            		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>",
                    "&cWelcome %player%",
                    "&cDon't forget to see our rules",
            		"&8&m<=====-------<&r &6Hawn &8&m>-------=====>"}));
            Config.set("Spawn.Teleport.Enable", true);
            Config.set("Spawn.Teleport.Enable-For-On-Join", false);
            Config.set("Spawn.Teleport.Messages", java.util.Arrays.asList(new String[] {"&7Teleport..."}));
            Config.set("Spawn.Teleport-By-Player.Messages", java.util.Arrays.asList(new String[] {"&7Teleported by someone..."}));
            Config.set("Spawn.Teleport-By-Player.Sender", java.util.Arrays.asList(new String[] {"&7Teleported %target%..."}));

            saveConfigFile();

        }
    }

}