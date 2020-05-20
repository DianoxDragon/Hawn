package fr.dianox.hawn.utility.config.configs;

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
            
            Config.set("ActionBarAnnouncer.Enable", true);
            Config.set("ActionBarAnnouncer.Cannot-Be-changed.Main-Command-Is", "actionbarannouncer");
            Config.set("ActionBarAnnouncer.Aliases", java.util.Arrays.asList(new String[] {"bacast", "aba"}));
            
            Config.set("Broadcast.Enable", true);
            Config.set("Broadcast.Cannot-Be-changed.Main-Command-Is", "broadcast");
            Config.set("Broadcast.Aliases", java.util.Arrays.asList(new String[] {"bc"}));
            
            Config.set("Burn.Enable", false);
            Config.set("Burn.Cannot-Be-changed.Main-Command-Is", "burn");
            Config.set("Burn.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("CheckAccount.Enable", true);
            Config.set("CheckAccount.Cannot-Be-changed.Main-Command-Is", "checkaccount");
            Config.set("CheckAccount.Aliases", java.util.Arrays.asList(new String[] {"checka"}));
            
            Config.set("ClearChat.Enable", false);
            Config.set("ClearChat.Cannot-Be-changed.Main-Command-Is", "cc");
            Config.set("ClearChat.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("ClearGroundItems.Enable", true);
            Config.set("ClearGroundItems.Cannot-Be-changed.Main-Command-Is", "cleargrounditems");
            Config.set("ClearGroundItems.Aliases", java.util.Arrays.asList(new String[] {"cleargi"}));
            
            Config.set("ClearInv.Enable", true);
            Config.set("ClearInv.Cannot-Be-changed.Main-Command-Is", "clearinventory");
            Config.set("ClearInv.Aliases", java.util.Arrays.asList(new String[] {"clearinv"}));
            
            Config.set("ClearMobs.Enable", true);
            Config.set("ClearMobs.Cannot-Be-changed.Main-Command-Is", "clearmobs");
            Config.set("ClearMobs.Aliases", java.util.Arrays.asList(new String[] {"clearm"}));
            
            Config.set("Day.Enable", false);
            Config.set("Day.Cannot-Be-changed.Main-Command-Is", "day");
            Config.set("Day.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("DelayChat.Enable", true);
            Config.set("DelayChat.Cannot-Be-changed.Main-Command-Is", "delaychat");
            Config.set("DelayChat.Aliases", java.util.Arrays.asList(new String[] {"dchat"}));
            
            Config.set("DelSpawn.Enable", true);
            Config.set("DelSpawn.Cannot-Be-changed.Main-Command-Is", "delspawn");
            Config.set("DelSpawn.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Emojis.Enable", false);
            Config.set("Emojis.Cannot-Be-changed.Main-Command-Is", "emoji");
            Config.set("Emojis.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("EnderChest.Enable", true);
            Config.set("EnderChest.Cannot-Be-changed.Main-Command-Is", "enderchest");
            Config.set("EnderChest.Aliases", java.util.Arrays.asList(new String[] {"ec"}));
            
            Config.set("Exp.Enable", false);
            Config.set("Exp.Cannot-Be-changed.Main-Command-Is", "exp");
            Config.set("Exp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Feed.Enable", false);
            Config.set("Feed.Cannot-Be-changed.Main-Command-Is", "feed");
            Config.set("Feed.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Fly.Enable", false);
            Config.set("Fly.Cannot-Be-changed.Main-Command-Is", "fly");
            Config.set("Fly.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("FlySpeed.Enable", true);
            Config.set("FlySpeed.Cannot-Be-changed.Main-Command-Is", "flyspeed");
            Config.set("FlySpeed.Aliases", java.util.Arrays.asList(new String[] {"fs"}));
            
            Config.set("Gamemode-Classic.Enable", true);
            Config.set("Gamemode-Classic.Cannot-Be-changed.Main-Command-Is", "gamemode");
            Config.set("Gamemode-Classic.Aliases", java.util.Arrays.asList(new String[] {"gm"}));
            
            Config.set("GetPos.Enable", false);
            Config.set("GetPos.Cannot-Be-changed.Main-Command-Is", "getpos");
            Config.set("GetPos.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gms.Enable", false);
            Config.set("Gms.Cannot-Be-changed.Main-Command-Is", "gms");
            Config.set("Gms.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gmc.Enable", false);
            Config.set("Gmc.Cannot-Be-changed.Main-Command-Is", "gmc");
            Config.set("Gmc.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gma.Enable", false);
            Config.set("Gma.Cannot-Be-changed.Main-Command-Is", "gma");
            Config.set("Gma.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gmsp.Enable", false);
            Config.set("Gmsp.Cannot-Be-changed.Main-Command-Is", "gmsp");
            Config.set("Gmsp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Gotop.Enable", false);
            Config.set("Gotop.Cannot-Be-changed.Main-Command-Is", "gotop");
            Config.set("Gotop.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Hat.Enable", false);
            Config.set("Hat.Cannot-Be-changed.Main-Command-Is", "hat");
            Config.set("Hat.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Heal.Enable", false);
            Config.set("Heal.Cannot-Be-changed.Main-Command-Is", "heal");
            Config.set("Heal.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Help.Enable", true);
            Config.set("Help.Cannot-Be-changed.Main-Command-Is", "help");
            Config.set("Help.Aliases", java.util.Arrays.asList(new String[] {"?"}));
            
            Config.set("InvSee.Enable", false);
            Config.set("InvSee.Cannot-Be-changed.Main-Command-Is", "invsee");
            Config.set("InvSee.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Ip.Enable", false);
            Config.set("Ip.Cannot-Be-changed.Main-Command-Is", "ip");
            Config.set("Ip.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("KickAll.Enable", false);
            Config.set("KickAll.Cannot-Be-changed.Main-Command-Is", "kickall");
            Config.set("KickAll.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("List.Enable", false);
            Config.set("List.Cannot-Be-changed.Main-Command-Is", "list");
            Config.set("List.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("MuteChat.Enable", true);
            Config.set("MuteChat.Cannot-Be-changed.Main-Command-Is", "globalmute");
            Config.set("MuteChat.Aliases", java.util.Arrays.asList(new String[] {"gmute"}));
            
            Config.set("Night.Enable", false);
            Config.set("Night.Cannot-Be-changed.Main-Command-Is", "night");
            Config.set("Night.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Ping.Enable", false);
            Config.set("Ping.Cannot-Be-changed.Main-Command-Is", "ping");
            Config.set("Ping.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Player-Option.Enable", false);
            Config.set("Player-Option.Cannot-Be-changed.Main-Command-Is", "option");
            Config.set("Player-Option.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Rain.Enable", false);
            Config.set("Rain.Cannot-Be-changed.Main-Command-Is", "rain");
            Config.set("Rain.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Repair.Enable", true);
            Config.set("Repair.Cannot-Be-changed.Main-Command-Is", "repair");
            Config.set("Repair.Aliases", java.util.Arrays.asList(new String[] {"fix"}));
            
            Config.set("Scoreboard.Enable", false);
            Config.set("Scoreboard.Cannot-Be-changed.Main-Command-Is", "scoreboard");
            Config.set("Scoreboard.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("SetSpawn.Enable", true);
            Config.set("SetSpawn.Cannot-Be-changed.Main-Command-Is", "setspawn");
            Config.set("SetSpawn.Aliases", java.util.Arrays.asList(new String[] {"setlobby", "sethub"}));
            
            Config.set("Speed.Enable", false);
            Config.set("Speed.Cannot-Be-changed.Main-Command-Is", "speed");
            Config.set("Speed.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Skull.Enable", false);
            Config.set("Skull.Cannot-Be-changed.Main-Command-Is", "skull");
            Config.set("Skull.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Spawn.Enable", true);
            Config.set("Spawn.Cannot-Be-changed.Main-Command-Is", "spawn");
            Config.set("Spawn.Aliases", java.util.Arrays.asList(new String[] {"hub", "lobby"}));
            
            Config.set("SpawnList.Enable", false);
            Config.set("SpawnList.Cannot-Be-changed.Main-Command-Is", "spawnlist");
            Config.set("SpawnList.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Suicide.Enable", false);
            Config.set("Suicide.Cannot-Be-changed.Main-Command-Is", "suicide");
            Config.set("Suicide.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Sun.Enable", true);
            Config.set("Sun.Cannot-Be-changed.Main-Command-Is", "sun");
            Config.set("Sun.Aliases", java.util.Arrays.asList(new String[] {"clearw"}));
            
            Config.set("Thunder.Enable", false);
            Config.set("Thunder.Cannot-Be-changed.Main-Command-Is", "thunder");
            Config.set("Thunder.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("TitleAnnouncer.Enable", true);
            Config.set("TitleAnnouncer.Cannot-Be-changed.Main-Command-Is", "titleannouncer");
            Config.set("TitleAnnouncer.Aliases", java.util.Arrays.asList(new String[] {"ta", "titlea", "btcast"}));
            
            Config.set("Vanish.Enable", true);
            Config.set("Vanish.Cannot-Be-changed.Main-Command-Is", "vanish");
            Config.set("Vanish.Aliases", java.util.Arrays.asList(new String[] {"v"}));
            
            Config.set("Warning.Enable", true);
            Config.set("Warning.Cannot-Be-changed.Main-Command-Is", "warning");
            Config.set("Warning.Aliases", java.util.Arrays.asList(new String[] {"warn"}));
            
            Config.set("Warp.Del-Warp.Enable", false);
            Config.set("Warp.Del-Warp.Warp.Cannot-Be-changed.Main-Command-Is", "delwarp");
            Config.set("Warp.Del-Warp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Warp.Edit-Warp.Enable", false);
            Config.set("Warp.Edit-Warp.Warp.Cannot-Be-changed.Main-Command-Is", "editwarp");
            Config.set("Warp.Edit-Warp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Warp.Set-Warp.Enable", false);
            Config.set("Warp.Set-Warp.Warp.Cannot-Be-changed.Main-Command-Is", "setwarp");
            Config.set("Warp.Set-Warp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Warp.Warp.Enable", false);
            Config.set("Warp.Warp.Warp.Cannot-Be-changed.Main-Command-Is", "warp");
            Config.set("Warp.Warp.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Warp.Warp-list.Enable", false);
            Config.set("Warp.Warp-list.Warp.Cannot-Be-changed.Main-Command-Is", "warplist");
            Config.set("Warp.Warp-list.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("WorkBench.Enable", false);
            Config.set("WorkBench.Cannot-Be-changed.Main-Command-Is", "workbench");
            Config.set("WorkBench.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("WorldEdit-Aliases.1.Enable", false);
            Config.set("WorldEdit-Aliases.1.Cannot-Be-changed.Main-Command-Is", "1");
            Config.set("WorldEdit-Aliases.1.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("WorldEdit-Aliases.2.Enable", false);
            Config.set("WorldEdit-Aliases.2.Cannot-Be-changed.Main-Command-Is", "2");
            Config.set("WorldEdit-Aliases.2.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("WorldEdit-Aliases.C.Enable", false);
            Config.set("WorldEdit-Aliases.C.Cannot-Be-changed.Main-Command-Is", "c");
            Config.set("WorldEdit-Aliases.C.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("WorldEdit-Aliases.P.Enable", false);
            Config.set("WorldEdit-Aliases.P.Cannot-Be-changed.Main-Command-Is", "p");
            Config.set("WorldEdit-Aliases.P.Aliases", java.util.Arrays.asList(new String[] {}));
            
            Config.set("Vanish.Enable", true);
            Config.set("Vanish.Cannot-Be-changed.Main-Command-Is", "vanish");
            Config.set("Vanish.Aliases", java.util.Arrays.asList(new String[] {"v"}));
            
            saveConfigFile();

        }
    }

}