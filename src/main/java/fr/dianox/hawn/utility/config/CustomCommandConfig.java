package fr.dianox.hawn.utility.config;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

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
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {}
            
            Config.set("commands-general.enable", true);
            Config.set("commands.rules.enable", true);
            Config.set("commands.rules.command", "/rules");
            Config.set("commands.rules.permission.enable", true);
            Config.set("commands.rules.permission.message", "hawn.command.rules");
            Config.set("commands.rules.no-permission-message-enable", true);
            Config.set("commands.rules.Cooldown.enable", true);
            Config.set("commands.rules.Cooldown.Ticks", 100);
            Config.set("commands.rules.Cooldown.messages", java.util.Arrays.asList(new String[] {
            		"%prefix% &7Please wait before using the command"
                }));
            Config.set("commands.rules.message", java.util.Arrays.asList(new String[] {
            		"<--center--> &4&m>-------------<",
            	    "",
            	    "<--center--> &c&nPlayers",
            	    "",
            	    " &8→ &ePlease be nice",
            	    " &8→ &eDon't cheat and more",
            	    "",
            	    "<perm>hawn.whatyouwant.more</perm> <--center--> &c&nVIP",
            	    "<perm>hawn.whatyouwant.more</perm> ",
            	    "<perm>hawn.whatyouwant.more</perm> &bIf you can see the VIP section, It's because you have the permission for it!",
            	    "<perm>hawn.whatyouwant.more</perm> &bCheck the config file for custom commands to see how it's work!",
            	    "<perm>hawn.whatyouwant.more</perm> ",
            	    "<--center--> &4&m>-------------<",
            	    "<perm>hawn.whatyouwant.more</perm> [send-actionbar[50]]: Actually You can see that if you have ther permission",
            	    "[send-title[50]]: But that's good //n &6to have a real custom commands",
            	    "[sounds]: BLOCK_ANVIL_LAND",
            	    "&eJust check spigot page for more events"
                }));
            
            Config.set("commands.youtube.enable", true);
            Config.set("commands.youtube.command", "/youtube");
            Config.set("commands.youtube.permission.enable", true);
            Config.set("commands.youtube.permission.message", "hawn.command.youtube");
            Config.set("commands.youtube.no-permission-message-enable", true);
            Config.set("commands.youtube.message", java.util.Arrays.asList(new String[] {
            		"<--center--> &4&m>-------------<",
            	    "",
            	    "&cPlease check our youtube channel",
            	    "",
            	    "<--center--> &4&m>-------------<",
                }));
            
            Config.set("commands.discord.enable", true);
            Config.set("commands.discord.command", "/discord");
            Config.set("commands.discord.permission.enable", true);
            Config.set("commands.discord.permission.message", "hawn.command.discord");
            Config.set("commands.discord.no-permission-message-enable", true);
            Config.set("commands.discord.message", java.util.Arrays.asList(new String[] {
            		"<--center--> &5&m>-------------<",
            	    "",
            	    "&dPlease check our discord server",
            	    "",
            	    "<--center--> &5&m>-------------<",
                }));
            
            Config.set("commands.twitter.enable", true);
            Config.set("commands.twitter.command", "/twitter");
            Config.set("commands.twitter.permission.enable", true);
            Config.set("commands.twitter.permission.message", "hawn.command.twitter");
            Config.set("commands.twitter.no-permission-message-enable", true);
            Config.set("commands.twitter.message", java.util.Arrays.asList(new String[] {
            		"<--center--> &3&m>-------------<",
            	    "",
            	    "&bPlease check our twitter channel",
            	    "",
            	    "<--center--> &3&m>-------------<",
                }));
            
            
            Config.set("commands.shop.enable", true);
            Config.set("commands.shop.command", "/shop");
            Config.set("commands.shop.permission.enable", true);
            Config.set("commands.shop.permission.message", "hawn.command.shop");
            Config.set("commands.shop.no-permission-message-enable", true);
            Config.set("commands.shop.message", java.util.Arrays.asList(new String[] {
            		"<--center--> &6&m>-------------<",
            	    "",
            	    "&ePlease check our shop website",
            	    "",
            	    "<--center--> &6&m>-------------<",
                }));
            
            Config.set("commands.shopp2.enable", true);
            Config.set("commands.shopp2.command", "/shop page 2");
            Config.set("commands.shopp2.permission.enable", true);
            Config.set("commands.shopp2.permission.message", "hawn.command.shop");
            Config.set("commands.shopp2.no-permission-message-enable", true);
            Config.set("commands.shopp2.message", java.util.Arrays.asList(new String[] {
            		"<--center--> &6&m>-------------<",
            	    "",
            	    "&ePlease check our shop website",
            	    "multiple command for a command work like that",
            	    "",
            	    "<--center--> &6&m>-------------<",
                }));
            
            saveConfigFile();

        }
    }

}