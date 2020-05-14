package fr.dianox.hawn.hook.hooklist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PlaceHolderAPI {

	public PlaceHolderAPI(Main plugin) {
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Keep-The-Option")
				&& ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"PlaceHolderAPI detected");
				Bukkit.getPluginManager().registerEvents(plugin, plugin);
			} else {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.PlaceholderAPI.Enable", true);
				ConfigGeneral.saveConfigFile();
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"PlaceHolderAPI detected");
				Bukkit.getPluginManager().registerEvents(plugin, plugin);
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.PlaceholderAPI.Enable", false);
				ConfigGeneral.saveConfigFile();
			}
		}
	}

}
