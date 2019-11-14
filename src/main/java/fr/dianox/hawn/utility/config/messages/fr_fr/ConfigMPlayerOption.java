package fr.dianox.hawn.utility.config.messages.fr_fr;

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

        file = new File(pl.getDataFolder(), "Messages/fr_FR/Classic/PlayerOption.yml");
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
            		"     &l>> &e&o&lAide sur l'option des joueurs",
            		"",
            		" &8>> &7/option fly - &eDéfinir le mode de vol",
            		" &8>> &7/option doublejump - &eActiver ou désactiver le double saut",
            		" &8>> &7/option speed - &eActiver ou désactiver la modification de vitesse",
            		" &8>> &7/option jumpboost - &eActiver ou désactiver le jumpboost",
            		" &8>> &7/option autobc - &eActiver ou désactiver la visibilité de l'autobroadcast",
            		" &8>> &7/option pv - &eActiver ou désactiver la visibilité des joueurs",
            		"",
            		"&8\\\\&7&m---------------&r&8// &3[&bPlayerOption&3] &8\\\\&7&m---------------&r&8//"}));
            
            Config.set("PlayerOption.DoubleJump.Enable.Enable", true);
            Config.set("PlayerOption.DoubleJump.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre double saut a été &aactivé"}));
            Config.set("PlayerOption.DoubleJump.Disable.Enable", true);
            Config.set("PlayerOption.DoubleJump.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre double saut a été &cdésactivé"}));
            
            Config.set("PlayerOption.Speed.Enable.Enable", true);
            Config.set("PlayerOption.Speed.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre modification de vitesse a été &aactivé"}));
            Config.set("PlayerOption.Speed.Disable.Enable", true);
            Config.set("PlayerOption.Speed.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre modification de vitesse a été &cdésactivé"}));
            Config.set("PlayerOption.Speed.Set.Enable", true);
            Config.set("PlayerOption.Speed.Set.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre vitesse a été réglée sur &e%arg1%"}));
            
            Config.set("PlayerOption.JumpBoost.Enable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre jumpboost a été &aactivé"}));
            Config.set("PlayerOption.JumpBoost.Disable.Enable", true);
            Config.set("PlayerOption.JumpBoost.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Votre jumpboost a été &cdésactivé"}));
            
            Config.set("PlayerOption.AutoBroadcast.Enable.Enable", true);
            Config.set("PlayerOption.AutoBroadcast.Enable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La visibilité de l'autobroadcast a été &aactivé"}));
            Config.set("PlayerOption.AutoBroadcast.Disable.Enable", true);
            Config.set("PlayerOption.AutoBroadcast.Disable.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7La visibilité de l'autobroadcast a été &cdésactivé"}));
            
            Config.set("PlayerOption.PlayerVisibility.ON.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.ON.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Pouf ! Les joueurs sont partis"}));
            Config.set("PlayerOption.PlayerVisibility.OFF.Enable", true);
            Config.set("PlayerOption.PlayerVisibility.OFF.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7Les joueurs sont maintenant visibles"}));

            Config.set("PlayerOption.Error.DoubleJump-Disabled.Enable", true);
            Config.set("PlayerOption.Error.DoubleJump-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe double saut est désactivé dans &6Cosmetics-Fun/DoubleJump.yml"}));
            Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Enable", true);
            Config.set("PlayerOption.Error.DoubleJump-Not-Good-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLe double saut n'est pas permis dans ce monde"}));
            
            Config.set("PlayerOption.Error.Option-Disabled.Enable", true);
            Config.set("PlayerOption.Error.Option-Disabled.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cVous avez désactivé cette option, donc, vous ne pouvez pas gérer toutes les options"}));
            //Config.set("PlayerOption.Error.Option-Already-Set.Enable", Boolean.valueOf(true));
            //Config.set("PlayerOption.Error.Option-Already-Set.Messages", java.util.Arrays.asList(new String[] {"&cCette option est déjà définie ou est la même"}));
            Config.set("PlayerOption.Error.Player-Visibility.Time.Enable", true);
            Config.set("PlayerOption.Error.Player-Visibility.Time.Messages", java.util.Arrays.asList(new String[] {"%prefix% &7S'il vous plaît , veuillez patienter &4&l%timedelaypvcji% &c&lseconde(s)&7!"}));
           
            Config.set("PlayerOption.Error.Not-Enable-In-A-World.Enable", true);
            Config.set("PlayerOption.Error.Not-Enable-In-A-World.Messages", java.util.Arrays.asList(new String[] {"%prefix% &cLes options des joueurs ne sont pas activées dans ce monde"}));
            /*Config.set("PlayerOption.Error.Player-Visibility.Time-Command", java.util.Arrays.asList(new String[] {"&c&lPlease wait &4%timedelaypvcommands% &c&lseconds!"}));*/            
            saveConfigFile();

        }
    }

}