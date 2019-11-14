package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;

public class OBCClass {
	
	public static Class<?> getOBCClass(String name) {
	    String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	    try {
	      return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
	    } catch (ClassNotFoundException e) {
	      e.printStackTrace();
	    }
	    return null;
	}

}
