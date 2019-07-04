package fr.Dianox.Hawn.Utility.Config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CommandAliasesConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public CommandAliasesConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "command-aliases.yml");
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
            
            Config.set("Broadcast.Enable", Boolean.valueOf(true));
            Config.set("Broadcast.Cannot-Be-changed.Main-Command-Is", String.valueOf("broadcast"));
            Config.set("Broadcast.Aliases", java.util.Arrays.asList(new String[] {"bc"}));
            
            Config.set("ClearChat.Enable", Boolean.valueOf(false));
            Config.set("ClearChat.Cannot-Be-changed.Main-Command-Is", String.valueOf("cc"));
            Config.set("ClearChat.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("ClearInv.Enable", Boolean.valueOf(true));
            Config.set("ClearInv.Cannot-Be-changed.Main-Command-Is", String.valueOf("clearinventory"));
            Config.set("ClearInv.Aliases", java.util.Arrays.asList(new String[] {"clearinv"}));
            
            Config.set("Day.Enable", Boolean.valueOf(false));
            Config.set("Day.Cannot-Be-changed.Main-Command-Is", String.valueOf("day"));
            Config.set("Day.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("DelayChat.Enable", Boolean.valueOf(true));
            Config.set("DelayChat.Cannot-Be-changed.Main-Command-Is", String.valueOf("delaychat"));
            Config.set("DelayChat.Aliases", java.util.Arrays.asList(new String[] {"dchat"}));
            
            Config.set("Emojis.Enable", Boolean.valueOf(false));
            Config.set("Emojis.Cannot-Be-changed.Main-Command-Is", String.valueOf("emoji"));
            Config.set("Emojis.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Fly.Enable", Boolean.valueOf(false));
            Config.set("Fly.Cannot-Be-changed.Main-Command-Is", String.valueOf("fly"));
            Config.set("Fly.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gamemode-Classic.Enable", Boolean.valueOf(true));
            Config.set("Gamemode-Classic.Cannot-Be-changed.Main-Command-Is", String.valueOf("gamemode"));
            Config.set("Gamemode-Classic.Aliases", java.util.Arrays.asList(new String[] {"gm"}));
            
            Config.set("Gms.Enable", Boolean.valueOf(false));
            Config.set("Gms.Cannot-Be-changed.Main-Command-Is", String.valueOf("gms"));
            Config.set("Gms.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gmc.Enable", Boolean.valueOf(false));
            Config.set("Gmc.Cannot-Be-changed.Main-Command-Is", String.valueOf("gmc"));
            Config.set("Gmc.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gma.Enable", Boolean.valueOf(false));
            Config.set("Gma.Cannot-Be-changed.Main-Command-Is", String.valueOf("gma"));
            Config.set("Gma.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gmsp.Enable", Boolean.valueOf(false));
            Config.set("Gmsp.Cannot-Be-changed.Main-Command-Is", String.valueOf("gmsp"));
            Config.set("Gmsp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Heal.Enable", Boolean.valueOf(false));
            Config.set("Heal.Cannot-Be-changed.Main-Command-Is", String.valueOf("heal"));
            Config.set("Heal.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Help.Enable", Boolean.valueOf(true));
            Config.set("Help.Cannot-Be-changed.Main-Command-Is", String.valueOf("help"));
            Config.set("Help.Aliases", java.util.Arrays.asList(new String[] {"?"}));
            
            Config.set("MuteChat.Enable", Boolean.valueOf(true));
            Config.set("MuteChat.Cannot-Be-changed.Main-Command-Is", String.valueOf("globalmute"));
            Config.set("MuteChat.Aliases", java.util.Arrays.asList(new String[] {"gmute"}));
            
            Config.set("Night.Enable", Boolean.valueOf(false));
            Config.set("Night.Cannot-Be-changed.Main-Command-Is", String.valueOf("night"));
            Config.set("Night.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Ping.Enable", Boolean.valueOf(false));
            Config.set("Ping.Cannot-Be-changed.Main-Command-Is", String.valueOf("ping"));
            Config.set("Ping.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Player-Option.Enable", Boolean.valueOf(false));
            Config.set("Player-Option.Cannot-Be-changed.Main-Command-Is", String.valueOf("option"));
            Config.set("Player-Option.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Rain.Enable", Boolean.valueOf(false));
            Config.set("Rain.Cannot-Be-changed.Main-Command-Is", String.valueOf("rain"));
            Config.set("Rain.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Sun.Enable", Boolean.valueOf(true));
            Config.set("Sun.Cannot-Be-changed.Main-Command-Is", String.valueOf("sun"));
            Config.set("Sun.Aliases", java.util.Arrays.asList(new String[] {"clearw"}));
            
            Config.set("Scoreboard.Enable", Boolean.valueOf(false));
            Config.set("Scoreboard.Cannot-Be-changed.Main-Command-Is", String.valueOf("scoreboard"));
            Config.set("Scoreboard.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Spawn.Enable", Boolean.valueOf(true));
            Config.set("Spawn.Cannot-Be-changed.Main-Command-Is", String.valueOf("spawn"));
            Config.set("Spawn.Aliases", java.util.Arrays.asList(new String[] {"hub", "lobby"}));
            
            Config.set("Thunder.Enable", Boolean.valueOf(false));
            Config.set("Thunder.Cannot-Be-changed.Main-Command-Is", String.valueOf("thunder"));
            Config.set("Thunder.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("TitleAnnouncer.Enable", Boolean.valueOf(true));
            Config.set("TitleAnnouncer.Cannot-Be-changed.Main-Command-Is", String.valueOf("titleannouncer"));
            Config.set("TitleAnnouncer.Aliases", java.util.Arrays.asList(new String[] {"ta", "titlea", "btcast"}));
            
            Config.set("Vanish.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Cannot-Be-changed.Main-Command-Is", String.valueOf("vanish"));
            Config.set("Vanish.Aliases", java.util.Arrays.asList(new String[] {"v"}));
            
            Config.set("Warp.Del-Warp.Enable", Boolean.valueOf(false));
            Config.set("Warp.Del-Warp.Warp.Cannot-Be-changed.Main-Command-Is", String.valueOf("delwarp"));
            Config.set("Warp.Del-Warp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Warp.Set-Warp.Enable", Boolean.valueOf(false));
            Config.set("Warp.Set-Warp.Warp.Cannot-Be-changed.Main-Command-Is", String.valueOf("setwarp"));
            Config.set("Warp.Set-Warp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Warp.Warp.Enable", Boolean.valueOf(false));
            Config.set("Warp.Warp.Warp.Cannot-Be-changed.Main-Command-Is", String.valueOf("warp"));
            Config.set("Warp.Warp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Warp.Warp-list.Enable", Boolean.valueOf(false));
            Config.set("Warp.Warp-list.Warp.Cannot-Be-changed.Main-Command-Is", String.valueOf("warplist"));
            Config.set("Warp.Warp-list.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Vanish.Enable", Boolean.valueOf(true));
            Config.set("Vanish.Cannot-Be-changed.Main-Command-Is", String.valueOf("vanish"));
            Config.set("Vanish.Aliases", java.util.Arrays.asList(new String[] {"v"}));
            
            saveConfigFile();

        }
    }

}
