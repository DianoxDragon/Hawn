package fr.dianox.hawn.utility.config.configs;

import fr.dianox.hawn.utility.config.ConfigDefaults;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;

public class CustomCommandConfig {
	
	private static Plugin pl;
	private static File file;
	private static YamlConfiguration Config;
	
	public CustomCommandConfig() {}
	
	public static void loadConfig(Plugin plugin) {
		pl = plugin;
		
		file = new File(pl.getDataFolder(), "CustomCommand.yml");
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
        YamlConfiguration loaded = Config;
        Config = new YamlConfiguration();

        Config.set("commands-general.enable", true);
        Config.set("commands.rules.enable", true);
        Config.set("commands.rules.command", "/rules");
        Config.set("commands.rules.permission.enable", true);
        Config.set("commands.rules.permission.message", "hawn.command.rules");
        Config.set("commands.rules.no-permission-message-enable", true);
        Config.set("commands.rules.Cooldown.enable", true);
        Config.set("commands.rules.Cooldown.Ticks", 100);
        Config.set("commands.rules.Cooldown.messages", Collections.singletonList("%prefix% &7Please wait before using the command"));
        Config.set("commands.rules.message", java.util.Arrays.asList("<--center--> °·..·°¯°·._.·  &e&lRULES&r ·._.·°¯°·..·°",
		            "",
		            " &8→ &7Be nice and respectful",
		            " &8→ &7No cheating, no advertising",
		            " &8→ &7Listen to the staff",
		            "",
		            "<perm>server.vip</perm> <--center--> &6&nVIP",
		            "<perm>server.vip</perm>  &8→ &7Thank you for your support! You can use &e/fly &7in the lobby",
		            "<perm>server.vip</perm> ",
		            "<--center--> ·._.·°¯°·..·°¯°·._.·",
		            "<world>world</world> [send-actionbar[60]]: &7Questions? Ask the staff",
		            "[send-title[50]]: &e&lRULES //n &7Thank you for reading them",
		            "[sounds]: ENTITY_EXPERIENCE_ORB_PICKUP"));

        Config.set("commands.youtube.enable", true);
        Config.set("commands.youtube.command", "/youtube");
        Config.set("commands.youtube.permission.enable", true);
        Config.set("commands.youtube.permission.message", "command.youtube");
        Config.set("commands.youtube.no-permission-message-enable", true);
        Config.set("commands.youtube.message", java.util.Arrays.asList("<--center--> °·..·°¯°·._.·  &4&lYOUTUBE&r ·._.·°¯°·..·°",
		            "",
		            "&cWe have a Youtube channel that you can consult at any time %player%",
		            "json:[\"\",{\"text\":\"YouTube:\",\"bold\":true,\"color\":\"red\"},{\"text\":\" \"},{\"text\":\"https://youtube.com/\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://youtube.com/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Open the website\"}}]",
		            ""));

        Config.set("commands.discord.enable", true);
        Config.set("commands.discord.command", "/discord");
        Config.set("commands.discord.permission.enable", true);
        Config.set("commands.discord.permission.message", "command.discord");
        Config.set("commands.discord.no-permission-message-enable", true);
        Config.set("commands.discord.message", java.util.Arrays.asList("<--center--> °·..·°¯°·._.·  &5&lDISCORD&r ·._.·°¯°·..·°",
		            "",
		            "&dWe have a Discord server that you can consult at any time %player%",
		            "json:[\"\",{\"text\":\"Discord:\",\"bold\":true,\"color\":\"light_purple\"},{\"text\":\" \"},{\"text\":\"discord.gg/yourserver\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://discord.gg/yourserver\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Open the website\"}}]",
		            ""));

        Config.set("commands.twitter.enable", true);
        Config.set("commands.twitter.command", "/twitter");
        Config.set("commands.twitter.permission.enable", true);
        Config.set("commands.twitter.permission.message", "command.twitter");
        Config.set("commands.twitter.no-permission-message-enable", true);
        Config.set("commands.twitter.message", java.util.Arrays.asList("<--center--> °·..·°¯°·._.·  &3&lTWITTER&r ·._.·°¯°·..·°",
		            "",
		            "&bWe have a Twitter page that you can consult at any time %player%",
		            "json:[\"\",{\"text\":\"Twitter:\",\"bold\":true,\"color\":\"aqua\"},{\"text\":\" \"},{\"text\":\"https://twitter.com/\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://twitter.com/\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Open the website\"}}]",
		            ""));

        Config.set("commands.complexcommand.enable", true);
        Config.set("commands.complexcommand.command", "/complexc");
        Config.set("commands.complexcommand.permission.enable", false);
        Config.set("commands.complexcommand.permission.message", "command.complexc");
        Config.set("commands.complexcommand.no-permission-message-enable", true);
        Config.set("commands.complexcommand.message", Collections.singletonList("&7A command can have arguments too: type &e/complexc page 1"));

	        Config.set("commands.complexcommand2.enable", true);
	        Config.set("commands.complexcommand2.command", "/complexc page 1");
	        Config.set("commands.complexcommand2.permission.enable", false);
	        Config.set("commands.complexcommand2.permission.message", "command.complexc");
	        Config.set("commands.complexcommand2.no-permission-message-enable", true);
	        Config.set("commands.complexcommand2.message", Collections.singletonList("&7This is the page &e1 &7of &e/complexc"));

        Config = ConfigDefaults.apply(file, loaded, Config, "commands");
    }

}