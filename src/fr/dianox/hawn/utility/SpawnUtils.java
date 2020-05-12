package fr.dianox.hawn.utility;

import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.config.ConfigSpawn;

public class SpawnUtils {
	
	public static void teleportToSpawn(Player player, String str) {
        try {
            org.bukkit.World w = org.bukkit.Bukkit.getServer().getWorld(ConfigSpawn.getConfig().getString("Coordinated."+str+".World"));
            double x = ConfigSpawn.getConfig().getDouble("Coordinated."+str+".X");
            double y = ConfigSpawn.getConfig().getDouble("Coordinated."+str+".Y");
            double z = ConfigSpawn.getConfig().getDouble("Coordinated."+str+".Z");
            float yaw = ConfigSpawn.getConfig().getInt("Coordinated."+str+".Yaw");
            float pitch = ConfigSpawn.getConfig().getInt("Coordinated."+str+".Pitch");

            player.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));
        } catch (Exception e) {
            org.bukkit.Bukkit.getLogger().warning("Hawn : Spawn "+str+" is not set");
            
            if (player.hasPermission("hawn.admin")) {
            	player.sendMessage("Spawn is not set");
            }
        }
	}
	
	public static void createSpawn(Player p, String spawnName, String WorldName, Double X, Double Y, Double Z, Float Yaw, Float Pitch) {
		ConfigSpawn.getConfig().set("Coordinated."+spawnName+".World", WorldName);
		ConfigSpawn.getConfig().set("Coordinated."+spawnName+".X", X);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Y", Y);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Z", Z);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Yaw", Yaw);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Pitch", Pitch);
        ConfigSpawn.getConfig().set("Coordinated."+spawnName+".Info", String.valueOf("Player "+p.getName()+" created the spawn at: "+OtherUtils.getDate()+", "+OtherUtils.getTime()));
        
        ConfigSpawn.saveConfigFile();
	}

}
