package fr.dianox.hawn.utility.config.configs;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

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
            } catch (IOException ignored) {}
            
            Config.set("ActionBarAnnouncer.Enable", true);
            Config.set("ActionBarAnnouncer.Cannot-Be-changed.Main-Command-Is", "actionbarannouncer");
            Config.set("ActionBarAnnouncer.Aliases", java.util.Arrays.asList("bacast", "aba"));
            
            Config.set("Broadcast.Enable", true);
            Config.set("Broadcast.Cannot-Be-changed.Main-Command-Is", "broadcast");
            Config.set("Broadcast.Aliases", Collections.singletonList("bc"));
            
            Config.set("Burn.Enable", false);
            Config.set("Burn.Cannot-Be-changed.Main-Command-Is", "burn");
            Config.set("Burn.Aliases", Collections.emptyList());
            
            Config.set("CheckAccount.Enable", true);
            Config.set("CheckAccount.Cannot-Be-changed.Main-Command-Is", "checkaccount");
            Config.set("CheckAccount.Aliases", Collections.singletonList("checka"));
            
            Config.set("ClearChat.Enable", false);
            Config.set("ClearChat.Cannot-Be-changed.Main-Command-Is", "cc");
            Config.set("ClearChat.Aliases", Collections.emptyList());
            
            Config.set("ClearGroundItems.Enable", true);
            Config.set("ClearGroundItems.Cannot-Be-changed.Main-Command-Is", "cleargrounditems");
            Config.set("ClearGroundItems.Aliases", Collections.singletonList("cleargi"));
            
            Config.set("ClearInv.Enable", true);
            Config.set("ClearInv.Cannot-Be-changed.Main-Command-Is", "clearinventory");
            Config.set("ClearInv.Aliases", Collections.singletonList("clearinv"));
            
            Config.set("ClearMobs.Enable", true);
            Config.set("ClearMobs.Cannot-Be-changed.Main-Command-Is", "clearmobs");
            Config.set("ClearMobs.Aliases", Collections.singletonList("clearm"));
            
            Config.set("Day.Enable", false);
            Config.set("Day.Cannot-Be-changed.Main-Command-Is", "day");
            Config.set("Day.Aliases", Collections.emptyList());
            
            Config.set("DelayChat.Enable", true);
            Config.set("DelayChat.Cannot-Be-changed.Main-Command-Is", "delaychat");
            Config.set("DelayChat.Aliases", Collections.singletonList("dchat"));
            
            Config.set("DelSpawn.Enable", true);
            Config.set("DelSpawn.Cannot-Be-changed.Main-Command-Is", "delspawn");
            Config.set("DelSpawn.Aliases", Collections.emptyList());
            
            Config.set("Emojis.Enable", false);
            Config.set("Emojis.Cannot-Be-changed.Main-Command-Is", "emoji");
            Config.set("Emojis.Aliases", Collections.emptyList());
            
            Config.set("EnderChest.Enable", true);
            Config.set("EnderChest.Cannot-Be-changed.Main-Command-Is", "enderchest");
            Config.set("EnderChest.Aliases", Collections.singletonList("ec"));
            
            Config.set("Exp.Enable", false);
            Config.set("Exp.Cannot-Be-changed.Main-Command-Is", "exp");
            Config.set("Exp.Aliases", Collections.emptyList());
            
            Config.set("Feed.Enable", false);
            Config.set("Feed.Cannot-Be-changed.Main-Command-Is", "feed");
            Config.set("Feed.Aliases", Collections.emptyList());
            
            Config.set("Fly.Enable", false);
            Config.set("Fly.Cannot-Be-changed.Main-Command-Is", "fly");
            Config.set("Fly.Aliases", Collections.emptyList());
            
            Config.set("FlySpeed.Enable", true);
            Config.set("FlySpeed.Cannot-Be-changed.Main-Command-Is", "flyspeed");
            Config.set("FlySpeed.Aliases", Collections.singletonList("fs"));
            
            Config.set("Gamemode-Classic.Enable", true);
            Config.set("Gamemode-Classic.Cannot-Be-changed.Main-Command-Is", "gamemode");
            Config.set("Gamemode-Classic.Aliases", Collections.singletonList("gm"));
            
            Config.set("GetPos.Enable", false);
            Config.set("GetPos.Cannot-Be-changed.Main-Command-Is", "getpos");
            Config.set("GetPos.Aliases", Collections.emptyList());
            
            Config.set("Gms.Enable", false);
            Config.set("Gms.Cannot-Be-changed.Main-Command-Is", "gms");
            Config.set("Gms.Aliases", Collections.emptyList());
            
            Config.set("Gmc.Enable", false);
            Config.set("Gmc.Cannot-Be-changed.Main-Command-Is", "gmc");
            Config.set("Gmc.Aliases", Collections.emptyList());
            
            Config.set("Gma.Enable", false);
            Config.set("Gma.Cannot-Be-changed.Main-Command-Is", "gma");
            Config.set("Gma.Aliases", Collections.emptyList());
            
            Config.set("Gmsp.Enable", false);
            Config.set("Gmsp.Cannot-Be-changed.Main-Command-Is", "gmsp");
            Config.set("Gmsp.Aliases", Collections.emptyList());
            
            Config.set("Gotop.Enable", false);
            Config.set("Gotop.Cannot-Be-changed.Main-Command-Is", "gotop");
            Config.set("Gotop.Aliases", Collections.emptyList());
            
            Config.set("Hat.Enable", false);
            Config.set("Hat.Cannot-Be-changed.Main-Command-Is", "hat");
            Config.set("Hat.Aliases", Collections.emptyList());
            
            Config.set("Heal.Enable", false);
            Config.set("Heal.Cannot-Be-changed.Main-Command-Is", "heal");
            Config.set("Heal.Aliases", Collections.emptyList());
            
            Config.set("Help.Enable", true);
            Config.set("Help.Cannot-Be-changed.Main-Command-Is", "help");
            Config.set("Help.Aliases", Collections.singletonList("?"));
            
            Config.set("InvSee.Enable", false);
            Config.set("InvSee.Cannot-Be-changed.Main-Command-Is", "invsee");
            Config.set("InvSee.Aliases", Collections.emptyList());
            
            Config.set("Ip.Enable", false);
            Config.set("Ip.Cannot-Be-changed.Main-Command-Is", "ip");
            Config.set("Ip.Aliases", Collections.emptyList());
            
            Config.set("KickAll.Enable", false);
            Config.set("KickAll.Cannot-Be-changed.Main-Command-Is", "kickall");
            Config.set("KickAll.Aliases", Collections.emptyList());
            
            Config.set("List.Enable", false);
            Config.set("List.Cannot-Be-changed.Main-Command-Is", "list");
            Config.set("List.Aliases", Collections.emptyList());
            
            Config.set("MuteChat.Enable", true);
            Config.set("MuteChat.Cannot-Be-changed.Main-Command-Is", "globalmute");
            Config.set("MuteChat.Aliases", Collections.singletonList("gmute"));
            
            Config.set("Night.Enable", false);
            Config.set("Night.Cannot-Be-changed.Main-Command-Is", "night");
            Config.set("Night.Aliases", Collections.emptyList());
            
            Config.set("Ping.Enable", false);
            Config.set("Ping.Cannot-Be-changed.Main-Command-Is", "ping");
            Config.set("Ping.Aliases", Collections.emptyList());
            
            Config.set("Player-Option.Enable", false);
            Config.set("Player-Option.Cannot-Be-changed.Main-Command-Is", "option");
            Config.set("Player-Option.Aliases", Collections.emptyList());
            
            Config.set("Rain.Enable", false);
            Config.set("Rain.Cannot-Be-changed.Main-Command-Is", "rain");
            Config.set("Rain.Aliases", Collections.emptyList());
            
            Config.set("Repair.Enable", true);
            Config.set("Repair.Cannot-Be-changed.Main-Command-Is", "repair");
            Config.set("Repair.Aliases", Collections.singletonList("fix"));
            
            Config.set("Scoreboard.Enable", false);
            Config.set("Scoreboard.Cannot-Be-changed.Main-Command-Is", "scoreboard");
            Config.set("Scoreboard.Aliases", Collections.emptyList());
            
            Config.set("SetSpawn.Enable", true);
            Config.set("SetSpawn.Cannot-Be-changed.Main-Command-Is", "setspawn");
            Config.set("SetSpawn.Aliases", java.util.Arrays.asList("setlobby", "sethub"));
            
            Config.set("Speed.Enable", false);
            Config.set("Speed.Cannot-Be-changed.Main-Command-Is", "speed");
            Config.set("Speed.Aliases", Collections.emptyList());
            
            Config.set("Skull.Enable", false);
            Config.set("Skull.Cannot-Be-changed.Main-Command-Is", "skull");
            Config.set("Skull.Aliases", Collections.emptyList());
            
            Config.set("Spawn.Enable", true);
            Config.set("Spawn.Cannot-Be-changed.Main-Command-Is", "spawn");
            Config.set("Spawn.Aliases", java.util.Arrays.asList("hub", "lobby"));
            
            Config.set("SpawnList.Enable", false);
            Config.set("SpawnList.Cannot-Be-changed.Main-Command-Is", "spawnlist");
            Config.set("SpawnList.Aliases", Collections.emptyList());
            
            Config.set("Suicide.Enable", false);
            Config.set("Suicide.Cannot-Be-changed.Main-Command-Is", "suicide");
            Config.set("Suicide.Aliases", Collections.emptyList());
            
            Config.set("Sun.Enable", true);
            Config.set("Sun.Cannot-Be-changed.Main-Command-Is", "sun");
            Config.set("Sun.Aliases", Collections.singletonList("clearw"));
            
            Config.set("Thunder.Enable", false);
            Config.set("Thunder.Cannot-Be-changed.Main-Command-Is", "thunder");
            Config.set("Thunder.Aliases", Collections.emptyList());
            
            Config.set("TitleAnnouncer.Enable", true);
            Config.set("TitleAnnouncer.Cannot-Be-changed.Main-Command-Is", "titleannouncer");
            Config.set("TitleAnnouncer.Aliases", java.util.Arrays.asList("ta", "titlea", "btcast"));
            
            Config.set("Vanish.Enable", true);
            Config.set("Vanish.Cannot-Be-changed.Main-Command-Is", "vanish");
            Config.set("Vanish.Aliases", Collections.singletonList("v"));
            
            Config.set("Warning.Enable", true);
            Config.set("Warning.Cannot-Be-changed.Main-Command-Is", "warning");
            Config.set("Warning.Aliases", Collections.singletonList("warn"));
            
            Config.set("Warp.Del-Warp.Enable", false);
            Config.set("Warp.Del-Warp.Warp.Cannot-Be-changed.Main-Command-Is", "delwarp");
            Config.set("Warp.Del-Warp.Aliases", Collections.emptyList());
            
            Config.set("Warp.Edit-Warp.Enable", false);
            Config.set("Warp.Edit-Warp.Warp.Cannot-Be-changed.Main-Command-Is", "editwarp");
            Config.set("Warp.Edit-Warp.Aliases", Collections.emptyList());
            
            Config.set("Warp.Set-Warp.Enable", false);
            Config.set("Warp.Set-Warp.Warp.Cannot-Be-changed.Main-Command-Is", "setwarp");
            Config.set("Warp.Set-Warp.Aliases", Collections.emptyList());
            
            Config.set("Warp.Warp.Enable", false);
            Config.set("Warp.Warp.Warp.Cannot-Be-changed.Main-Command-Is", "warp");
            Config.set("Warp.Warp.Aliases", Collections.emptyList());
            
            Config.set("Warp.Warp-list.Enable", false);
            Config.set("Warp.Warp-list.Warp.Cannot-Be-changed.Main-Command-Is", "warplist");
            Config.set("Warp.Warp-list.Aliases", Collections.emptyList());
            
            Config.set("WorkBench.Enable", false);
            Config.set("WorkBench.Cannot-Be-changed.Main-Command-Is", "workbench");
            Config.set("WorkBench.Aliases", Collections.emptyList());
            
            Config.set("WorldEdit-Aliases.1.Enable", false);
            Config.set("WorldEdit-Aliases.1.Cannot-Be-changed.Main-Command-Is", "1");
            Config.set("WorldEdit-Aliases.1.Aliases", Collections.emptyList());
            
            Config.set("WorldEdit-Aliases.2.Enable", false);
            Config.set("WorldEdit-Aliases.2.Cannot-Be-changed.Main-Command-Is", "2");
            Config.set("WorldEdit-Aliases.2.Aliases", Collections.emptyList());
            
            Config.set("WorldEdit-Aliases.C.Enable", false);
            Config.set("WorldEdit-Aliases.C.Cannot-Be-changed.Main-Command-Is", "c");
            Config.set("WorldEdit-Aliases.C.Aliases", Collections.emptyList());
            
            Config.set("WorldEdit-Aliases.P.Enable", false);
            Config.set("WorldEdit-Aliases.P.Cannot-Be-changed.Main-Command-Is", "p");
            Config.set("WorldEdit-Aliases.P.Aliases", Collections.emptyList());
            
            Config.set("Vanish.Enable", true);
            Config.set("Vanish.Cannot-Be-changed.Main-Command-Is", "vanish");
            Config.set("Vanish.Aliases", Collections.singletonList("v"));
            
            saveConfigFile();

        }
    }

}