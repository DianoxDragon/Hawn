package fr.dianox.hawn.utility.config.configs.messages;

import fr.dianox.hawn.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigMAdmin {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public ConfigMAdmin() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/Admin.yml");
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
                        
            /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
            Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList("&cYou are not a player"));
            Config.set("Error.Command.Hawn", java.util.Arrays.asList("&cError, Try to do /hawn"));
            Config.set("Error.Command.Delspawn", java.util.Arrays.asList("&c/hawn delspawn <spawn>"));
            Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList("&cThe name already exist"));
            Config.set("Error.Argument-Missing", java.util.Arrays.asList("&cI'm sorry, but there must be one or two arguments missing."));
            Config.set("Error.No-Spawn", java.util.Arrays.asList("&cSpawn doesn't exist"));

	        /* ------------ *
	         * HELP COMMAND *
	         * ------------ */

	        Config.set("Command.Help.Hawn-SpawnManager", "Manage the spawn");
	        Config.set("Command.Help.Hawn-Reload", "Reload some config files");
	        Config.set("Command.Help.Hawn-Version", "See the version of the plugin");
	        Config.set("Command.Help.Hawn-Tps", "See the TPS of the server");
	        Config.set("Command.Help.Hawn-Info", "See the infos of the server");
	        Config.set("Command.Help.Hawn-Build", "To bypass protection temporary");
	        Config.set("Command.Help.Hawn-Hooks", "To check the plugin's sub-dependencies, if they are enabled");
	        Config.set("Command.Help.Hawn-Urgent", "To use the urgent mode");
	        Config.set("Command.Help.Hawn-Maintenance", "To use the maintenance mode");
	        Config.set("Command.Help.Hawn-Donors", "See those who donated for the plugin");
	        Config.set("Command.Help.Hawn-About", "Just show some informations");
	        Config.set("Command.Help.Hawn-Parse", "See what a placeholder return");
	        Config.set("Command.Help.Hawn-NightVision", "See in the dark");
	        Config.set("Command.Help.Hawn-NoClip", "Go through the blocks while staying creative");
	        Config.set("Command.Help.Hawn-SlotView", "See the slot number by clicking on it");
	        Config.set("Command.Help.Hawn-EditPlayer", "Edit a player");
	        Config.set("Command.Help.Hawn-Setup", "Setup your configuration of Hawn for the first time");
	        Config.set("Command.Help.Broadcast", "Broadcast a message");
	        Config.set("Command.Help.Warning", "Broadcast a warning");
	        Config.set("Command.Help.Broadcast-Title", "Broadcast a title message");
	        Config.set("Command.Help.Broadcast-ActionBar", "Broadcast an actionbar message");
	        Config.set("Command.Help.AdminPanel", "Access to the admin panel");
	        Config.set("Command.Help.CheckAccount", "Check a player");
	        Config.set("Command.Help.ClearInv", "Reset player inv");
	        Config.set("Command.Help.InvSee", "See the inventory of a player");
	        Config.set("Command.Help.Ip", "See the ip of a player");
	        Config.set("Command.Help.List", "Get the list of players on the lobby");
	        Config.set("Command.Help.Spawn", "Go to the spawn");
	        Config.set("Command.Help.Spawn-Tp", "Tp a player to a spawn");
	        Config.set("Command.Help.DelSpawn", "Remove a spawn");
	        Config.set("Command.Help.SpawnList", "See the list of spawns");
	        Config.set("Command.Help.SetSpawn", "Set a spawn");
	        Config.set("Command.Help.SetWarp", "Set a warp");
	        Config.set("Command.Help.Warp", "Go to the warp");
	        Config.set("Command.Help.WarpList", "See the list of warps");
	        Config.set("Command.Help.DelWarp", "Remove the warp");
	        Config.set("Command.Help.EditWarp", "Edit warp location");
	        Config.set("Command.Help.Sun", "Clear the weather");
	        Config.set("Command.Help.Rain", "To make the world raining");
	        Config.set("Command.Help.Thunder", "If you like a bad weather");
	        Config.set("Command.Help.Day", "Put the day");
	        Config.set("Command.Help.Night", "Put the night");
	        Config.set("Command.Help.Fly", "Put the flight mode");
	        Config.set("Command.Help.FlySpeed", "Change the fly speed of a player");
	        Config.set("Command.Help.Speed", "Change or enable/disable speed");
	        Config.set("Command.Help.Heal", "Heal a player");
	        Config.set("Command.Help.Feed", "Feed a player");
	        Config.set("Command.Help.Ping", "Show the ping of a player");
	        Config.set("Command.Help.Vanish", "Vanish a player");
	        Config.set("Command.Help.Gamemode", "Set to player a gamemode");
	        Config.set("Command.Help.Burn", "Burn a player");
	        Config.set("Command.Help.Cleargrounditems", "Clear items on the ground");
	        Config.set("Command.Help.Clearmobs", "Clear mobs");
	        Config.set("Command.Help.EnderChest", "Open EnderChest");
	        Config.set("Command.Help.Exp", "Edit player exp");
	        Config.set("Command.Help.Getpos", "Get the location of a player");
	        Config.set("Command.Help.Hat", "Get a hat");
	        Config.set("Command.Help.Kickall", "Kick everyone");
	        Config.set("Command.Help.Repair", "Repair an item");
	        Config.set("Command.Help.Skull", "Get the head of a player");
	        Config.set("Command.Help.Suicide", "Kill yourself");
	        Config.set("Command.Help.Workbench", "Open player's workbench");
	        Config.set("Command.Help.ClearChat", "Show the help of the clearchat");
	        Config.set("Command.Help.DelayChat", "Put a delay on the chat");
	        Config.set("Command.Help.Gmute", "Mute the chat");
	        Config.set("Command.Help.Help", "Show the custom help, if enabled");
	        Config.set("Command.Help.Gotop", "Go to the highest block of your position");
	        Config.set("Command.Help.Emoji", "See the gui of emojis");
	        Config.set("Command.Help.Scoreboard", "Toggle off or on the scoreboard");
	        Config.set("Command.Help.Scoreboard-Set", "To change the actual scoreboard");
	        Config.set("Command.Help.Scoreboard-Keep", "Keep the scoreboard between servers");
	        Config.set("Command.Help.Scoreboard-List", "See all registered scoreboards");
	        Config.set("Command.Help.Option", "For main player's options");
	        Config.set("Command.Help.Hworld", "Manage world system");

            /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
            Config.set("Command.Server-Info.General", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer information (All)",
		            "        &3created by Dianox",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lJava version&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lHawn check update&r&8:&r %checkupdatehawn%",
		            "&7&lServer version&r&8:&r %serverversion%",
		            "",
		            "  &8→ &6&lMemory",
		            "&7&lMemory max &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lFree&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "  &8→ &6&lCPU (Processor)",
		            "&7&lAverage load&8:&r %averagecpuload%&8%",
		            "&7&lActual load&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "  &8→ &6&lDisk space",
		            "&7&lTotal disk&r&8:&r %totalspace%&8MB",
		            "&a&lFree disk&7&l/&c&lTotal disk&7&l usable&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Memory", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer information (Memory)",
		            "",
		            "  &8→ &6&lMemory",
		            "&7&lMemory max &o(java)&r&8:&r %maxmemory%&8MB",
		            "&a&lFree&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
		            "&8Bar-[%barmemory%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.CPU", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer information (CPU)",
		            "",
		            "  &8→ &6&lCPU (Processor)",
		            "&7&lAverage load&8:&r %averagecpuload%&8%",
		            "&7&lActual load&8:&r %cpuload%&8%",
		            "&8Bar-[%barcpu%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Disk", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer information (Disk)",
		            "",
		            "  &8→ &6&lDisk space",
		            "&7&lTotal disk&r&8:&r %totalspace%&8MB",
		            "&a&lFree disk&7&l/&c&lTotal disk&7&l usable&r&8:&r %freespace%&8MB&7/&7%totalspace%&8MB",
		            "&8Bar-[%bardisk%&8]",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Server", java.util.Arrays.asList("&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
		            "",
		            "     &l>> &e&o&lServer information (Server)",
		            "",
		            "  &8→ &6&lServer",
		            "&7&lTps&r&8:&r %tps%",
		            "&7&lJava version&r&8:&r %javaversion%",
		            "&7&lOS&r&8:&r %osversion%",
		            "&7&lHawn check update&r&8:&r %checkupdatehawn%",
		            "&7&lServer version&r&8:&r %serverversion%",
		            "",
		            "&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"));
            
            Config.set("Command.Server-Info.Tps", java.util.Arrays.asList("  &8→ &6&lTps&8: &r%tps%"));
            
            Config.set("Command.Version", java.util.Arrays.asList("  &8→ &6&lHawn version (created by Dianox)&8: &r%gethawnversion%"));
            
            Config.set("TPS.Check.15", java.util.Arrays.asList("&cYour TPS is under 15, done something to improve the stability of your Lobby"));
            Config.set("TPS.Check.5", java.util.Arrays.asList("&cYour TPS is under 5, your server may shut down, done /stop to avoid any problems.", "&cCRITICAL SERVER CRITICAL STATE ATTENTION"));
            
            /* --------------- *
             * RELOAD COMMANDS *
             * --------------- */
            Config.set("Command.Reload", java.util.Arrays.asList("&aReloaded configuration"));

            Config.set("Command.Build-Bypass.On", java.util.Arrays.asList("&bYou can now bypass all the build restriction"));
            Config.set("Command.Build-Bypass.Off", java.util.Arrays.asList("&cYou can no longer bypass all the build restriction"));


            /*
             * Vanish
             */
            Config.set("Vanish.Vanish-On.Enable", true);
            Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList("&7[ %player% is now vanished ]"));
            
            Config.set("Vanish.Vanish-Off.Enable", true);
            Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList("&7[ %player% is now no longer vanished ]"));
            Config.set("Vanish.Vanish-On-Others", java.util.Arrays.asList("&7[ %target% is now vanished by %player% ]"));
            Config.set("Vanish.Vanish-Off-Others", java.util.Arrays.asList("&7[ %target% is now no longer vanished by %player% ]"));

            Config.set("Maintenance.On", java.util.Arrays.asList("%prefix% &7You &aenabled&7 the maintenance"));
            Config.set("Maintenance.Off", java.util.Arrays.asList("%prefix% &7You &cdisabled&7 the maintenance"));
            Config.set("Maintenance.Broadcast.On", java.util.Arrays.asList(" &4* &cThe maintenance is &eon&4 *"));
            Config.set("Maintenance.Broadcast.Off", java.util.Arrays.asList(" &4* &cThe maintenance is &eoff&4 *"));

            Config.set("Urgent-mode.On", java.util.Arrays.asList("%prefix% &7You &aenabled&7 the urgent mode"));
            Config.set("Urgent-mode.Off", java.util.Arrays.asList("%prefix% &7You &cdisabled&7 the urgent mode"));
            Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(" &4* &cThe urgent mode is &eon&4 *"));
            Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(" &4* &cThe urgent mode is &eoff&4 *"));
            Config.set("Urgent-mode.Zip", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cA backup of Hawn has been made"));
            Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cError, you need to be on the console to disable the urgent mode"));
            Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &c&cSorry but you can't use the command"));
            Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cA modification has been detected by %player% on the admin panel",
		            "%arg1% in the file %arg2%"));
            Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &cAll plugins have been disabled"));
            Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList("&8[&eHawn-Urgent&8] &7All plugins have been &aenabled",
		            "&ePlease, a restart is needed to avoid any problems"));

            Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList("%prefix% &e%player% tried to do this command: &b%arg1%"));
            
            Config.set("Command.SlotView.On", java.util.Arrays.asList("%prefix% &7You can now &asee&7 every slot of a gui or an inventory when you click on it"));
            Config.set("Command.SlotView.Off", java.util.Arrays.asList("%prefix% &7The SlotView is &cdisabled"));
            
            Config.set("Command.IP", java.util.Arrays.asList("%prefix% &7The player's ip is: &e%getplayerip%"));
            
            Config.set("Command.Kickall", java.util.Arrays.asList("%prefix% &7All player has been kick"));
            
            Config.set("Command.ClearGroundItems", java.util.Arrays.asList("%prefix% &7All items have been cleared"));
            
            Config.set("Command.ClearMobs", java.util.Arrays.asList("%prefix% &7All mobs have been cleared"));

            Config.set("Command.CheckAccount", java.util.Arrays.asList("",
		            "  &8→ &r&lPlayer info for&8:&r &b%target%",
		            "&7&lJoin date&r&8:&e %hawn_player_join_date%",
		            "&7&lFirst join date&r&8:&e %hawn_player_first_join_date%",
		            "&7&lIp&r&8:&e %player_ip%",
		            "",
		            "  &8→ &r&lOptions&8:&r &8(&aGreen &8=&7 true / &cRed&8 = &7false&8)",
		            "&7&lPlayer visibility: %pv_point%",
		            "&7&lSpeed: %ps_point% &7(&e%ps_number%&7)",
		            "&7&lFly: %pof_point%",
		            "&7&lDouble jump: %dj_point%",
		            "&7&lAuto broadcast: %ab_point%",
		            "&7&lVanished: %v_point%",
		            "&7&lKeep scoreboard: %ksb_point% &8(&e%scorename%&8)",
		            "&7&lJump Boost: %jb_point%",
		            "",
		            "&7&lGamemode: &e%gm_number%",
		            ""));
            
            // List
            
            Config.set("Command.List.Part-One", java.util.Arrays.asList("§8//§7§m---------------§r§8\\\\ §3[§bList§3] §8//§7§m---------------§r§8\\\\",
		            " ",
		            "  &8→ &6&lPage %number%",
		            " "));
            
            Config.set("Command.List.Part-Two", java.util.Arrays.asList("§8\\\\§7§m---------------§r§8// §3[§bList§3] §8\\\\§7§m---------------§r§8//"));
            
            Config.set("Command.List.Gui.Other.Page.Next", "&bNext");
            Config.set("Command.List.Gui.Other.Page.Back", "&bPrevious");
            Config.set("Command.List.Gui.Other.Back.PanelAdmin", "&c&lBack to the panel admin");
            Config.set("Command.List.Gui.Player.Survival", "&cSURVIVAL");
            Config.set("Command.List.Gui.Player.Spectator", "&eSPECTATOR");
            Config.set("Command.List.Gui.Player.Creative", "&6CREATIVE");
            Config.set("Command.List.Gui.Player.Adventure", "&aADVENTURE");
            Config.set("Command.List.Gui.Player.Gamemode", "&7Gamemode: ");
            Config.set("Command.List.Gui.Player.World", "&7World: &e");
            Config.set("Command.List.Gui.Player.LeftClick", "&eLeft-Click&7 to edit the player");
            
            // EDIT PLAYER
            
            Config.set("Command.EditPlayer.Gui.Gamemode.LeftClick", "&eLeft-Click&7 to edit the player's gamemode");
            Config.set("Command.EditPlayer.Gui.Gamemode.Survival", "&cSURVIVAL");
            Config.set("Command.EditPlayer.Gui.Gamemode.Spectator", "&eSPECTATOR");
            Config.set("Command.EditPlayer.Gui.Gamemode.Creative", "&6CREATIVE");
            Config.set("Command.EditPlayer.Gui.Gamemode.Adventure", "&aADVENTURE");
            
            Config.set("Command.EditPlayer.Gui.ClearInv.LeftClick", "&eLeft-Click&7 to clear the player's inventory/armor");
            Config.set("Command.EditPlayer.Gui.ClearInv.Item-Name", "&6Clear inventory");
            
            Config.set("Command.EditPlayer.Gui.Teleport.LeftClick", "&eLeft-Click&7 to be teleported to be the player");
            Config.set("Command.EditPlayer.Gui.Teleport.Item-Name", "&6Teleport to this player");
            
            Config.set("Command.EditPlayer.Gui.MoreSoon.Item-Name", "More soon");
            
            Config.set("Command.EditPlayer.Gui.BackToPlayerList.Item-Name", "&cBack to player list");
            
            //
            
            Config.set("Command.No-Clip.Enable", java.util.Arrays.asList("%prefix% &7The No clip is &aenabled"));
            
            Config.set("Command.No-Clip.Disable", java.util.Arrays.asList("%prefix% &7The No clip is &cdisabled"));
            
            Config.set("Command.NightVision", java.util.Arrays.asList("%prefix% &7You can see in the night"));
            
            /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
            Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList("&cYou have not put a name for this spawn, an automatic name has been chosen",
		            "§eSpawn set on behalf of %spawnName%"));
            
            Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList("§eSpawn set on behalf of %spawnName%"));
            
            Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList("&bThe spawn &e%spawn%&b has been deleted"));
            
            saveConfigFile();

        }
    }
}