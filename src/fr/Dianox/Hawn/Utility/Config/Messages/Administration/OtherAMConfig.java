package fr.Dianox.Hawn.Utility.Config.Messages.Administration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.Dianox.Hawn.Main;

public class OtherAMConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public OtherAMConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/Administration/Others.yml");
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
                        
            /* --------------- *
			 * RELOAD COMMANDS *
			 * --------------- */
            Config.set("Command.Reload", java.util.Arrays.asList(new String[] {
            		"&aReloaded configuration"
            		}));
            
            Config.set("Command.Build-Bypass.On", java.util.Arrays.asList(new String[] {
            		"&bYou can now bypass all the build restriction"
            		}));
            Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList(new String[] {
            		"&cYou can no longer bypass all the build restriction"
            		}));
            
            Config.set("Command.Hawn-Main-help.1", java.util.Arrays.asList(new String[] {"§8>> §7/hawn setspawn [name] - §eSet the spawn", 
            "§8>> §7/hawn reload §eor §7rl - §eReload some config files",
            "§8>> §7/hawn version §eor §7v  - §eSee the version of the plugin",
            "§8>> §7/hawn tps - §eSee the TPS of the server",
            "§8>> §7/hawn info [all/memory/tps/disk/cpu/server/version] - §eSee the infos of the server",
            "§8>> §7/hawn debug emoji - §eDebug config files of emoji's items",
            "§8>> §7/hawn build - §eTo bypass protection temporary",
            "",
            "§8>> §7/ap §eor §7pa - §eAccess to the admin panel"}));

        Config.set("Command.Hawn-Main-help.2", java.util.Arrays.asList(new String[] {"", 
            "§8>> §7/spawn [SpawnName] - §eGo to the spawn",
            "§8>> §7/spawn tp <player> [SpawnName] - §eTp a player to a spawn",
            "",
            "§8>> §7/sun or /clearw - §eClear the weather",
            "§8>> §7/rain - §eTo make the world raining",
            "§8>> §7/thunder - §eIf you like a bad weather",
            "§8>> §7/day - §ePut the day",
            "§8>> §7/night - §ePut the night",
            ""}));

            Config.set("Command.Hawn-Main-help.3", java.util.Arrays.asList(new String[] {"§8>> §7/fly [player] - §eSet the fly mode", 
            "§8>> §7/heal [player] - §eHeal a player",
            "§8>> §7/clearinv [player] - §eClear inventory a player",
            "§8>> §7/ping [player] - §eShow the ping of a player",
            "§8>> §7/v [player] - §eVanish a player",
            "§8>> §7/gamemode or gm... or gma etc. - §eSet to player a gamemode",
            "",
            "§8>> §7/cc - §eShow the help of the clearchat",
            "§8>> §7/delaychat <number> - §ePut a delay on the chat"}));

            Config.set("Command.Hawn-Main-help.4", java.util.Arrays.asList(new String[] {"§8>> §7/gmute - §eMute the chat", 
            "",
            "§8>> §7/broadcast <message>§7 - §eBroadcast a message",
            "§8>> §7/btcast §eor §7/ta <message>§7 - §eBroadcast a title message",
            "",
            "§8>> §7/help <.../...>§7 - §eShow the custom help, if enabled",
            "",
            "§8>> §7/emoji§7 - §eSee the gui of emojis",
            "",
            "§8>> §7/scoreboard§7 - §eToggle off or on the scoreboard"}));

            Config.set("Command.Hawn-Main-help.5", java.util.Arrays.asList(new String[] {"§8>> §7/scoreboard set <scoreboard's file name>§7 - §eTo change the actual scoreboard", 
            "§8>> §7/scoreboard keep§7 - §eKeep the scoreboard between servers",
            "",
            "§8>> §7/option§7 - §eFor main player's options"}));
            
            /*
             * Vanish
             */
            Config.set("Vanish.Vanish-On", java.util.Arrays.asList(new String[] {
                    "&7[ %player% is now vanished ]"
            		}));
            Config.set("Vanish.Vanish-Off", java.util.Arrays.asList(new String[] {
                    "&7[ %player% is now no longer vanished ]"
            		}));
            Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList(new String[] {
                    "&7[ %target% is now vanished by %player% ]"
            		}));
            Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList(new String[] {
                    "&7[ %target% is now no longer vanished by %player% ]"
            		}));
            
            Config.set("Maintenance.On", java.util.Arrays.asList(new String[] {
                    "%prefix% &7You &aenabled&7 the maintenance"
            		}));
            Config.set("Maintenance.Off", java.util.Arrays.asList(new String[] {
                    "%prefix% &7You &cdisabled&7 the maintenance"
            		}));
            Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(new String[] {
                    " &4* &cThe maintenance is &eon&4 *"
            		}));
            Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(new String[] {
            		" &4* &cThe maintenance is &eoff&4 *"
            		}));
            
            saveConfigFile();

        }
    }
}