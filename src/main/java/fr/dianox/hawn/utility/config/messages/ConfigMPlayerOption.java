package fr.dianox.hawn.utility.config.messages;

import fr.dianox.hawn.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigMPlayerOption {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public ConfigMPlayerOption() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Messages/" + Main.LanguageType + "/Classic/PlayerOption.yml");
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
            } catch (IOException e) {
                e.printStackTrace();
            }

            Config.set("PlayerOption.Help.Enable", true);
            Config.set("PlayerOption.Help.Messages", java.util.Arrays.asList(new String[] {
            		"&8//&7&m---------------&r&8\\\\ &3[&bPlayerOption&3] &8//&7&m---------------&r&8\\\\",
            		"",
            		"     &l>> &e&o&lPlayer option Help",
            		"",
            		" &8>> &7/option fly - &eSet the fly",
            		" &8>> &7/option doublejump - &eEnable or disable the doublejump",
            		" &8>> &7/option speed - &eEnable or disable the speed",
            		" &8>> &7/option jumpboost - &eEnable or disable the jumpboost",
            		" &8>> &7/option autobc - &eEnable or disable the autobroadcast visibility",
            		" &8>> &7/option pv - &eEnable or disable player visibility",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));
            
            Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
            Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your double jump has been &aactivated"}));
            Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
            Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your double jump has been &cdisabled"}));
            
            Config.set("PlayerOption.Speed.Enable.Enable", true);
            Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your speed has been &aactivated"}));
            Config.set("PlayerOption.Speed.Disable.Enable", true);
            Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your speed has been &cdisabled"}));
            Config.set("PlayerOption.Speed.Set.Enable", true);
            Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your speed has been set to &e%arg1%"}));
            
            Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your jumpboost has been &aactivated"}));
            Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Your jumpboost has been &cdisabled"}));
            
            Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
            Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The autobroadcast visibility has been &aactivated"}));
            Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
            Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The autobroadcast visibility has been &cdisabled"}));
            
            Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Poof! The players are gone"}));
            Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7The players are visible now"}));

            Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
            Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe double jump is disabled in &6Cosmetics-Fun/DoubleJump.yml"}));
            Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
            Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cThe double jump is not enabled in this world"}));
            
            Config.set("PlayerOption.Error.Option-Disabled.Enable", true);
            Config.set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cYou disabled this option, so, you can't manage all the option"}));
            //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
            //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cThis option is already set or is the same"}));
            Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
            Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Please wait &4&l%timedelaypvcji% &c&lsecond(s)&7!"}));
           
            Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
            Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cPlayer options are not enabled in this world"}));
            /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lPlease wait &4%timedelaypvcommands% &c&lseconds!"}));*/            
            saveConfigFile();

        }
    }

}