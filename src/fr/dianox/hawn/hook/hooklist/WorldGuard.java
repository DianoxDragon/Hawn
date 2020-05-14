package fr.dianox.hawn.hook.hooklist;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Method;

public class WorldGuard {

	private WorldGuardPlugin worldGuard;
	public Boolean worldGuard_recent_version = false;

	public WorldGuard(Main plugin) {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Keep-The-Option")
				&& ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
				setWorldGuard((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard"));
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard detected");
				try {
					Class<?> worldGuardClass = Class.forName("com.sk89q.worldguard.WorldGuard");
					Method getInstanceMethod = worldGuardClass.getMethod("getInstance");
					getInstanceMethod.invoke(null);
					Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard 7+ detected");
					worldGuard_recent_version = true;
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard <7 detected");
					worldGuard_recent_version = false;
				}
			} else {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.WorldGuard.Enable", true);
				ConfigGeneral.saveConfigFile();
				setWorldGuard((WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard"));
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard detected");
				try {
					Class<?> worldGuardClass = Class.forName("com.sk89q.worldguard.WorldGuard");
					Method getInstanceMethod = worldGuardClass.getMethod("getInstance");
					getInstanceMethod.invoke(null);
					Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard 7+ detected");
					worldGuard_recent_version = true;
				} catch (Exception e) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard <7 detected");
					worldGuard_recent_version = false;
				}
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.WorldGuard.Enable", false);
				ConfigGeneral.saveConfigFile();
			}
		}
	}

	public void setWorldGuard(WorldGuardPlugin worldGuard) {
		this.worldGuard = worldGuard;
	}

	public WorldGuardPlugin getWorldGuard() {
		return worldGuard;
	}

	public Boolean getWorldGuard_recent_version() {
		return worldGuard_recent_version;
	}

}
