package fr.dianox.hawn.utility.config.messages.administration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import fr.dianox.hawn.Main;

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

            Config.set("Command.Hawn-Main-help.1", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/hawn setspawn [name] - Â§eSet the spawn",
                "Â§8>> Â§7/hawn delspawn <name> - Â§eDelete the spawn",
                "Â§8>> Â§7/hawn reload Â§eor Â§7rl - Â§eReload some config files",
                "Â§8>> Â§7/hawn version Â§eor Â§7v  - Â§eSee the version of the plugin",
                "Â§8>> Â§7/hawn tps - Â§eSee the TPS of the server",
                "Â§8>> Â§7/hawn info [all/memory/tps/disk/cpu/server/version] - Â§eSee the infos of the server",
                "Â§8>> Â§7/hawn debug emoji - Â§eDebug config files of emoji's items",
                "Â§8>> Â§7/hawn build - Â§eTo bypass protection temporary",
                "Â§8>> Â§7/hawn hooks - Â§ePour vÃ©rifier les sous-dÃ©pendances du plugin, s'ils sont activÃ©s ou non",
                "Â§8>> Â§7/hawn urgent - Â§eTo use the urgent mode",
                "Â§8>> Â§7/hawn [maintenance/m] - Â§eTo use the maintenance mode"
            }));
            Config.set("Command.Hawn-Main-help.2", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/hawn dornor - Â§eShow all the donors of the plugin",
                "Â§8>> Â§7/hawn about - Â§eJust show some informations",
                "",
                "Â§8>> Â§7/ap Â§eor Â§7pa - Â§eAccess to the admin panel",
                "",
                "Â§8>> Â§7/spawn [SpawnName] - Â§eGo to the spawn",
                "Â§8>> Â§7/spawn tp <player> [SpawnName] - Â§eTp a player to a spawn",
                "Â§8>> Â§7/spawnlist - Â§eSee the list of spawns",
                "",
                "Â§8>> Â§7/warp <WarpName> [player] - Â§eGo to the warp",
                "Â§8>> Â§7/warplist - Â§eSee the list of warps",
                "Â§8>> Â§7/setwarp <warpname> - Â§eCreate a warp"
            }));

            Config.set("Command.Hawn-Main-help.3", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/delwarp <warpname> - Â§eDelete a warp",
                "",
                "Â§8>> Â§7/sun or /clearw - Â§eClear the weather",
                "Â§8>> Â§7/rain - Â§eTo make the world raining",
                "Â§8>> Â§7/thunder - Â§eIf you like a bad weather",
                "Â§8>> Â§7/day - Â§ePut the day",
                "Â§8>> Â§7/night - Â§ePut the night",
                "",
                "Â§8>> Â§7/fly [player] - Â§ePut the flight mode",
                "Â§8>> Â§7/heal [player] - Â§eHeal a player",
                "Â§8>> Â§7/feed [player] - Â§eFeed the player"
            }));

            Config.set("Command.Hawn-Main-help.4", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/clearinv [player] - Â§eClear inventory a player",
                "Â§8>> Â§7/ping [player] - Â§eShow the ping of a player",
                "Â§8>> Â§7/v [player] - Â§eVanish a player",
                "Â§8>> Â§7/gamemode or gm... or gma etc. - Â§eSet to player a gamemode",
                "",
                "Â§8>> Â§7/cc - Â§eShow the help of the clearchat",
                "Â§8>> Â§7/delaychat <number> - Â§ePut a delay on the chat",
                "Â§8>> Â§7/gmute - Â§eMute the chat",
                "",
                "Â§8>> Â§7/broadcast <message>Â§7 - Â§eBroadcast a message",
                "Â§8>> Â§7/btcast Â§eor Â§7/ta <message>Â§7 - Â§eBroadcast a title message"
            }));

            Config.set("Command.Hawn-Main-help.5", java.util.Arrays.asList(new String[] {
                "Â§8>> Â§7/bacast Â§eor Â§7/aba <message>Â§7 - Â§eBroadcast an action bar",
                "",
                "Â§8>> Â§7/help <.../...>Â§7 - Â§eShow the custom help, if enabled",
                "Â§8>> Â§7/gotopÂ§7 - Â§eGo to the highest block of your position",
                "",
                "Â§8>> Â§7/emojiÂ§7 - Â§eSee the gui of emojis",
                "",
                "Â§8>> Â§7/scoreboardÂ§7 - Â§eToggle off or on the scoreboard",
                "Â§8>> Â§7/scoreboard set <scoreboard's file name>Â§7 - Â§eTo change the actual scoreboard",
                "Â§8>> Â§7/scoreboard keepÂ§7 - Â§eKeep the scoreboard between servers",
                "Â§8>> Â§7/scoreboard listÂ§7 - Â§eSee all registered scoreboards"
            }));

            Config.set("Command.Hawn-Main-help.6", java.util.Arrays.asList(new String[] {
                "",
                "Â§8>> Â§7/optionÂ§7 - Â§eFor main player's options"
            }));

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

            saveConfigFile();

        }
    }
}