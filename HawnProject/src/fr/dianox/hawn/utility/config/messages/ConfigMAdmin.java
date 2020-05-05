package fr.dianox.hawn.utility.config.messages;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.dianox.hawn.Main;

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
            } catch (IOException e) {}
                        
            /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
            Config.set("Error.Console.Not-A-Player", java.util.Arrays.asList(new String[] {
            		"&cYou are not a player"
            		}));
            
            Config.set("Error.Command.Hawn", java.util.Arrays.asList(new String[] {
            		"&cError, Try to do /hawn"
            		}));
            
            Config.set("Error.Command.Delspawn", java.util.Arrays.asList(new String[] {
            		"&c/hawn delspawn <spawn>"
            		}));
            
            Config.set("Error.Command.Name-already-exist", java.util.Arrays.asList(new String[] {
            		"&cThe name already exist"
            		}));
            
            Config.set("Error.Argument-Missing", java.util.Arrays.asList(new String[] {
            		"&cI'm sorry, but there must be one or two arguments missing."
            		}));
            Config.set("Error.No-Spawn", java.util.Arrays.asList(new String[] {"&cSpawn doesn't exist"}));
            
            //
            
            /* ----------------- *
			 * COMMANDS COMMANDS *
			 * ----------------- */
            Config.set("Command.Server-Info.General", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
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
            		"&a&lFree disk&7&l/&c&lTotal disk&7&l usable&r&8:&r %freespace%&8MB&7/&7%totalspaceusable%&8MB",
            		"&8Bar-[%bardisk%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.Memory", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (Memory)",
            		"",
            		"  &8→ &6&lMemory",
            		"&7&lMemory max &o(java)&r&8:&r %maxmemory%&8MB",
            		"&a&lFree&7&l/&c&lTotal&r&8:&r %freememory%&8MB&7/&7%totalmemory%&8MB",
            		"&8Bar-[%barmemory%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.CPU", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (CPU)",
            		"",
            		"  &8→ &6&lCPU (Processor)",
            		"&7&lAverage load&8:&r %averagecpuload%&8%",
            		"&7&lActual load&8:&r %cpuload%&8%",
            		"&8Bar-[%barcpu%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
            		}));
            
            Config.set("Command.Server-Info.Disk", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lServer information (Disk)",
            		"",
            		"  &8→ &6&lDisk space",
            		"&7&lTotal disk&r&8:&r %totalspace%&8MB",
            		"&a&lFree disk&7&l/&c&lTotal disk&7&l usable&r&8:&r %freespace%&8MB&7/&7%totalspaceusable%&8MB",
            		"&8Bar-[%bardisk%&8]",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
    				}));
            
            Config.set("Command.Server-Info.Server", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bHawn&3] &8//&7&m---------------&r&8\\\\",
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
            		"&8\\\\&7&m---------------&r&8// &3[&bHawn&3] &8\\\\&7&m---------------&r&8//"
    				}));
            
            Config.set("Command.Server-Info.Tps", java.util.Arrays.asList(new String[] {
            		"  &8→ &6&lTps&8: &r%tps%"
    				}));
            
            Config.set("Command.Version", java.util.Arrays.asList(new String[] {
            		"  &8→ &6&lHawn version (created by Dianox)&8: &r%gethawnversion%"
    				}));
            
            Config.set("TPS.Check.15", java.util.Arrays.asList(new String[] {"&cYour TPS is under 15, done something to improve the stability of your Lobby"}));
            Config.set("TPS.Check.5", java.util.Arrays.asList(new String[] {"&cYour TPS is under 5, your server may shut down, done /stop to avoid any problems.", "&cCRITICAL SERVER CRITICAL STATE ATTENTION"}));
            
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

            Config.set("Command.Hawn-Main-help.1", java.util.Arrays.asList(new String[] {
                "§8>> §7/hawn setspawn [name] - §eSet the spawn",
                "§8>> §7/hawn delspawn <name> - §eDelete the spawn",
                "§8>> §7/hawn reload §eor §7rl - §eReload some config files",
                "§8>> §7/hawn version §eor §7v  - §eSee the version of the plugin",
                "§8>> §7/hawn tps - §eSee the TPS of the server",
                "§8>> §7/hawn info [all/memory/tps/disk/cpu/server/version] - §eSee the infos of the server",
                "§8>> §7/hawn debug emoji - §eDebug config files of emoji's items",
                "§8>> §7/hawn build - §eTo bypass protection temporary",
                "§8>> §7/hawn hooks - §ePour vÃ©rifier les sous-dÃ©pendances du plugin, s'ils sont activÃ©s ou non",
                "§8>> §7/hawn urgent - §eTo use the urgent mode",
                "§8>> §7/hawn [maintenance/m] - §eTo use the maintenance mode"
            }));
            Config.set("Command.Hawn-Main-help.2", java.util.Arrays.asList(new String[] {
                "§8>> §7/hawn dornor - §eShow all the donors of the plugin",
                "§8>> §7/hawn about - §eJust show some informations",
                "",
                "§8>> §7/ap §eor §7pa - §eAccess to the admin panel",
                "",
                "§8>> §7/spawn [SpawnName] - §eGo to the spawn",
                "§8>> §7/spawn tp <player> [SpawnName] - §eTp a player to a spawn",
                "§8>> §7/spawnlist - §eSee the list of spawns",
                "",
                "§8>> §7/warp <WarpName> [player] - §eGo to the warp",
                "§8>> §7/warplist - §eSee the list of warps",
                "§8>> §7/setwarp <warpname> - §eCreate a warp"
            }));

            Config.set("Command.Hawn-Main-help.3", java.util.Arrays.asList(new String[] {
                "§8>> §7/delwarp <warpname> - §eDelete a warp",
                "",
                "§8>> §7/sun or /clearw - §eClear the weather",
                "§8>> §7/rain - §eTo make the world raining",
                "§8>> §7/thunder - §eIf you like a bad weather",
                "§8>> §7/day - §ePut the day",
                "§8>> §7/night - §ePut the night",
                "",
                "§8>> §7/fly [player] - §ePut the flight mode",
                "§8>> §7/heal [player] - §eHeal a player",
                "§8>> §7/feed [player] - §eFeed the player"
            }));

            Config.set("Command.Hawn-Main-help.4", java.util.Arrays.asList(new String[] {
                "§8>> §7/clearinv [player] - §eClear inventory a player",
                "§8>> §7/ping [player] - §eShow the ping of a player",
                "§8>> §7/v [player] - §eVanish a player",
                "§8>> §7/gamemode or gm... or gma etc. - §eSet to player a gamemode",
                "",
                "§8>> §7/cc - §eShow the help of the clearchat",
                "§8>> §7/delaychat <number> - §ePut a delay on the chat",
                "§8>> §7/gmute - §eMute the chat",
                "",
                "§8>> §7/broadcast <message>§7 - §eBroadcast a message",
                "§8>> §7/btcast §eor §7/ta <message>§7 - §eBroadcast a title message"
            }));

            Config.set("Command.Hawn-Main-help.5", java.util.Arrays.asList(new String[] {
                "§8>> §7/bacast §eor §7/aba <message>§7 - §eBroadcast an action bar",
                "",
                "§8>> §7/help <.../...>§7 - §eShow the custom help, if enabled",
                "§8>> §7/gotop§7 - §eGo to the highest block of your position",
                "",
                "§8>> §7/emoji§7 - §eSee the gui of emojis",
                "",
                "§8>> §7/scoreboard§7 - §eToggle off or on the scoreboard",
                "§8>> §7/scoreboard set <scoreboard's file name>§7 - §eTo change the actual scoreboard",
                "§8>> §7/scoreboard keep§7 - §eKeep the scoreboard between servers",
                "§8>> §7/scoreboard list§7 - §eSee all registered scoreboards"
            }));

            Config.set("Command.Hawn-Main-help.6", java.util.Arrays.asList(new String[] {
                "",
                "§8>> §7/option§7 - §eFor main player's options"
            }));

            /*
             * Vanish
             */
            Config.set("Vanish.Vanish-On.Enable", true);
            Config.set("Vanish.Vanish-On.Messages", java.util.Arrays.asList(new String[] {
                "&7[ %player% is now vanished ]"
            }));
            
            Config.set("Vanish.Vanish-Off.Enable", true);
            Config.set("Vanish.Vanish-Off.Messages", java.util.Arrays.asList(new String[] {
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

            Config.set("Urgent-mode.On", java.util.Arrays.asList(new String[] {
                "%prefix% &7You &aenabled&7 the urgent mode"
            }));
            Config.set("Urgent-mode.Off", java.util.Arrays.asList(new String[] {
                "%prefix% &7You &cdisabled&7 the urgent mode"
            }));
            Config.set("Urgent-mode.Broadcast.On", java.util.Arrays.asList(new String[] {
                " &4* &cThe urgent mode is &eon&4 *"
            }));
            Config.set("Urgent-mode.Broadcast.Off", java.util.Arrays.asList(new String[] {
                " &4* &cThe urgent mode is &eoff&4 *"
            }));
            Config.set("Urgent-mode.Zip", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &c&cA backup of Hawn has been made"
            }));
            Config.set("Urgent-mode.Error-Disable", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cError, you need to be on the console to disable the urgent mode"
            }));
            Config.set("Urgent-mode.Error-cant-use-the-command", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &c&cSorry but you can't use the command"
            }));
            Config.set("Urgent-mode.Hawn-Watch-Panel-Admin", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cA modification has been detected by %player% on the admin panel",
                "%arg1% in the file %arg2%"
            }));
            Config.set("Urgent-mode.Disabled-Plugin-function", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &cAll plugins have been disabled"
            }));
            Config.set("Urgent-mode.Back-To-Normal-For-All-Plugins", java.util.Arrays.asList(new String[] {
                "&8[&eHawn-Urgent&8] &7All plugins have been &aenabled",
                "&ePlease, a restart is needed to avoid any problems"
            }));

            Config.set("Command-Blocker.Notify-Staff", java.util.Arrays.asList(new String[] {
                "%prefix% &e%player% tried to do this command: &b%arg1%"
            }));
            
            Config.set("Command.SlotView.On", java.util.Arrays.asList(new String[] {
                    "%prefix% &7You can now &asee&7 every slot of a gui or an inventory when you click on it"
                }));
            Config.set("Command.SlotView.Off", java.util.Arrays.asList(new String[] {
                    "%prefix% &7The SlotView is &cdisabled"
                }));
            
            Config.set("Command.IP", java.util.Arrays.asList(new String[] {
                    "%prefix% &7The player's ip is: &e%getplayerip%"
                }));
            
            Config.set("Command.Kickall", java.util.Arrays.asList(new String[] {
                    "%prefix% &7All player has been kick"
                }));
            
            Config.set("Command.ClearGroundItems", java.util.Arrays.asList(new String[] {
                    "%prefix% &7All items have been cleared"
                }));
            
            Config.set("Command.ClearMobs", java.util.Arrays.asList(new String[] {
                    "%prefix% &7All mobs have been cleared"
                }));

            Config.set("Command.CheckAccount", java.util.Arrays.asList(new String[] {
            		"",
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
                    ""
                }));
            
            // List
            
            Config.set("Command.List.Part-One", java.util.Arrays.asList(new String[] {
            		"§8//§7§m---------------§r§8\\\\ §3[§bList§3] §8//§7§m---------------§r§8\\\\",
            		" ",
            		"  &8→ &6&lPage %number%",
            		" "
                }));
            
            Config.set("Command.List.Part-Two", java.util.Arrays.asList(new String[] {
            		"§8\\\\§7§m---------------§r§8// §3[§bList§3] §8\\\\§7§m---------------§r§8//"
                }));
            
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
            
            Config.set("Command.No-Clip.Enable", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The No clip is &aenabled"
                }));
            
            Config.set("Command.No-Clip.Disable", java.util.Arrays.asList(new String[] {
            		"%prefix% &7The No clip is &cdisabled"
                }));
            
            Config.set("Command.NightVision", java.util.Arrays.asList(new String[] {
            		"%prefix% &7You can see in the night"
                }));
            
            /* -------------- *
			 * SPAWN COMMANDS *
			 * -------------- */
            Config.set("Command.Spawn.Spawn-Set.Default", java.util.Arrays.asList(new String[] {
            		"&cYou have not put a name for this spawn, an automatic name has been chosen",
            		"§eSpawn set on behalf of %spawnName%"
            		}));
            
            Config.set("Command.Spawn.Spawn-Set.Other", java.util.Arrays.asList(new String[] {
            		"§eSpawn set on behalf of %spawnName%"
            		}));
            
            Config.set("Command.Del.Spawn-Delete", java.util.Arrays.asList(new String[] {"&bThe spawn &e%spawn%&b has been deleted"}));
            
            saveConfigFile();

        }
    }
}