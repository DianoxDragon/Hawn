package fr.Dianox.Hawn.Utility.Config.Messages;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigMPlayerOption {

    private static Plugin pl;
    private static File file;
    private static YamlConfiguration Config;

    public ConfigMPlayerOption() {}

    public static void loadConfig(Plugin plugin) {
        pl = plugin;

        file = new File(pl.getDataFolder(), "Messages/Classic/PlayerOption.yml");
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
            		//" &8>> &7/option fly - &eSet the fly",
            		//" &8>> &7/option doublejump - &eEnable or disable the doublejump",
            		" &8>> &7/option speed - &eEnable or disable the speed",
            		" &8>> &7/option jumpboost - &eEnable or disable the jumpboost",
            		" &8>> &7/option pv - &eEnable or disable player visibility",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));
            
            //Config.set("PlayerOption.DoubleJump.Enable", java.util.Arrays.asList(new String[] {"&aYour doublejump has been activated"}));
            //Config.set("PlayerOption.DoubleJump.Disable", java.util.Arrays.asList(new String[] {"&cYour doublejump has been disabled"}));
            
            Config.set("PlayerOption.Speed.Enable.Enable", true);
            Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"&aYour speed has been activated"}));
            Config.set("PlayerOption.Speed.Disable.Enable", true);
            Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"&cYour speed has been disabled"}));
            Config.set("PlayerOption.Speed.Set.Enable", true);
            Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"&bSpeed has been set to %arg1%"}));
            
            Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"&aYour jumpboost has been activated"}));
            Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"&cYour jumpboost has been disabled"}));
            
            Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"&aOh boy, the players are gone ?"}));
            Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"&ePlayers are visible again"}));
            /*Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", Boolean.valueOf(true));
            Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"&c&lPlease wait &4%timedelaypvcji% &c&lseconds!"}));
            Config.set("PlayerOption.Error.DoubleJump-Fly", java.util.Arrays.asList(new String[] {"&c&lYou&7 have the fly and doublejump activated, please deactivate one or the other."}));
            Config.set("PlayerOption.Error.DoubleJump", java.util.Arrays.asList(new String[] {"&c&lYou&7 cannot activate your doublejump because the fly is active!"}));
            Config.set("PlayerOption.Error.Fly", java.util.Arrays.asList(new String[] {"&c&lYou&7 cannot activate your fly because the doublejump is active!"}));*/
            
            Config.set("PlayerOption.Error.Option-Disabled.Enable", true);
            Config.set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"&cYou disabled this option, so, you can't manage all the option"}));
            //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
            //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cThis option is already set or is the same"}));
            Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
            Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"&c&lPlease wait &4%timedelaypvcji% &c&lseconds!"}));
            /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lPlease wait &4%timedelaypvcommands% &c&lseconds!"}));
            Config.set("PlayerOption.Config_version_NEVER_TOUCH_HERE", Integer.valueOf(1));*/
            
            saveConfigFile();

        }
    }

}
