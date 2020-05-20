package fr.dianox.hawn.hook.hooklist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MVdWPlaceholderAPI {

	public MVdWPlaceholderAPI(Main plugin) {
		if (Bukkit.getPluginManager().isPluginEnabled("MVdWPlaceholderAPI")) {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Keep-The-Option")
				&& ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"MVdWPlaceholderAPI detected");
				Bukkit.getConsoleSender().sendMessage("| "+ChatColor.YELLOW+"MAKE SURE you have at LEAST one of the Maximvdw's up-to-date and purchased premium placeholder plugins in the server such as FeatherBoard, AnimatedNames..");
				Bukkit.getConsoleSender().sendMessage("| "+ChatColor.YELLOW+"Otherwise, you will get a good spam in the console");
				Bukkit.getPluginManager().registerEvents(plugin, plugin);
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"MVdWPlaceholderAPI detected");
				Bukkit.getConsoleSender().sendMessage("| "+ChatColor.YELLOW+"MAKE SURE you have at LEAST one of the Maximvdw's up-to-date and purchased premium placeholder plugins in the server such as FeatherBoard, AnimatedNames..");
				Bukkit.getConsoleSender().sendMessage("| "+ChatColor.YELLOW+"Otherwise, you will get a good spam in the console");
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable", true);
				ConfigGeneral.saveConfigFile();
				Bukkit.getPluginManager().registerEvents(plugin, plugin);
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable", false);
				ConfigGeneral.saveConfigFile();
			}
		}
	}

}
