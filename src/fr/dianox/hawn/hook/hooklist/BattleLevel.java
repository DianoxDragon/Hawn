package fr.dianox.hawn.hook.hooklist;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BattleLevel {

	public BattleLevel(Main plugin) {
		if (Bukkit.getPluginManager().isPluginEnabled("BattleLevels")) {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Keep-The-Option")
				&& ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"BattleLevels detected");
				Bukkit.getPluginManager().registerEvents(plugin, plugin);
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"BattleLevels detected");
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.BattleLevels.Enable", true);
				ConfigGeneral.saveConfigFile();
				Bukkit.getPluginManager().registerEvents(plugin, plugin);
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.BattleLevels.Enable", false);
				ConfigGeneral.saveConfigFile();
			}
		}
	}
}
