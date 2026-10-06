package fr.dianox.hawn.hook.hooklist;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

public class WorldGuard {

	private boolean available = false;

	public WorldGuard() {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Keep-The-Option")
				|| !ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Enable")) {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.WorldGuard.Enable", true);
				ConfigGeneral.saveConfigFile();
			}

			try {
				Class.forName("com.sk89q.worldguard.WorldGuard");
				available = true;
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.YELLOW+"WorldGuard detected");
			} catch (ClassNotFoundException e) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.BLUE+"| "+ChatColor.RED+"WorldGuard 7+ is required, the region features are disabled");
			}
		} else {
			if (!ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.WorldGuard.Keep-The-Option")) {
				ConfigGeneral.getConfig().set("Plugin.Use.Hook.WorldGuard.Enable", false);
				ConfigGeneral.saveConfigFile();
			}
		}
	}

	public boolean isAvailable() {
		return available;
	}

	/**
	 * @return the regions at this location, formatted as "id='region1', id='region2'"
	 */
	public String getRegion(Location loc) {
		if (!available) {
			return "";
		}

		RegionQuery query = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
		ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));

		StringBuilder sb = new StringBuilder();
		for (ProtectedRegion region : set.getRegions()) {
			if (sb.length() > 0) sb.append(", ");
			sb.append("id='").append(region.getId()).append("'");
		}

		return sb.toString();
	}

	/**
	 * WorldGuard saves the IDs in lower case: "Spawn" in a config is the region "spawn"
	 */
	public boolean isInRegion(Location loc, String name) {
		if (!available || name == null) {
			return false;
		}

		RegionQuery query = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

		for (ProtectedRegion region : query.getApplicableRegions(BukkitAdapter.adapt(loc)).getRegions()) {
			if (region.getId().equalsIgnoreCase(name)) {
				return true;
			}
		}

		return false;
	}

}
