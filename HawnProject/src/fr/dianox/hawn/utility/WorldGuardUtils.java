package fr.Dianox.Hawn.Utility;

import org.bukkit.Location;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;

import fr.dianox.hawn.Main;

public class WorldGuardUtils {
	
	public static String getRegion(Location loc) {
		
		if (Main.getInstance().worldGuard_recent_version) {
			RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
			RegionQuery query = container.createQuery();
			ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
			
			return set.getRegions().toString();
		} else {
			com.sk89q.worldguard.bukkit.RegionContainer container = com.sk89q.worldguard.bukkit.WorldGuardPlugin.inst().getRegionContainer();
			com.sk89q.worldguard.bukkit.RegionQuery query = container.createQuery();
			ApplicableRegionSet set = query.getApplicableRegions(loc);
			
			return set.getRegions().toString();
		}
	}
}