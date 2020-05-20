package fr.dianox.hawn.modules.tablist.tab;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.NMSClass;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.tab.TablistConfig;
import me.clip.placeholderapi.PlaceholderAPI;

public class MainTablist extends BukkitRunnable {

	String hea;
	String foo;
	
	Class<?> ChatComponentText;
	Constructor<?> newPacketPlayOutPlayerListHeaderFooter;
	Class<?> PacketPlayOutPlayerListHeaderFooter;
	
	public MainTablist(String hea, String foo, Class<?> PacketPlayOutPlayerListHeaderFooter, Class<?> ChatComponentText, Constructor<?> newPacketPlayOutPlayerListHeaderFooter) {
		this.hea = hea;
		this.foo = foo;
		this.ChatComponentText = ChatComponentText;
		this.newPacketPlayOutPlayerListHeaderFooter = newPacketPlayOutPlayerListHeaderFooter;
		this.PacketPlayOutPlayerListHeaderFooter = PacketPlayOutPlayerListHeaderFooter;
	}
	
	@Override
	public void run() {
		if (TablistConfig.getConfig().getBoolean("Tablist.header.enabled")) {
			
			hea = "";
			String anim = "";
			
	    	for (String s: TablistConfig.getConfig().getStringList("Tablist.header.message")) {				    		
	    		if (s.contains("{anim_")) {
	    			anim = StringUtils.substringBetween(s, "{anim_", "}");
	    			if (TablistConfig.getConfig().isSet("Animations." + anim + ".text")) {
	    				try {
	    					s = s.replace("{anim_" + anim + "}", TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(Main.getInstance().getTabManager().animationtab.get(anim)));
	    				} catch (Exception ignored) {}
	    			}
	    		}
	    		
	    		s = s.replaceAll("&", "§");
	    		hea = hea + "\n" + s;
	    	}
	    	
	    	hea = hea.substring(1, hea.length());
	    }

	    if (TablistConfig.getConfig().getBoolean("Tablist.footer.enabled")) {
	    	
	    	foo = "";
	    	String anim = "";
	    	
	    	for (String s: TablistConfig.getConfig().getStringList("Tablist.footer.message")) {				    		
	    		if (s.contains("{anim_")) {
	    			anim = StringUtils.substringBetween(s, "{anim_", "}");
	    			if (TablistConfig.getConfig().isSet("Animations." + anim + ".text")) {
	    				try {
	    				s = s.replace("{anim_" + anim + "}", TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(Main.getInstance().getTabManager().animationtab.get(anim)));
	    				} catch (Exception ignored) {}
	    			}
	    		}
	    		
	    		s = s.replaceAll("&", "§");
	    		foo = foo + "\n" + s;
	    	}
	    	
	    	foo = foo.substring(1, foo.length());
	    }
	    
		try {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {							
				String hea2 = "";
				String foo2 = "";
				Object packet = null;
				
				hea2 = PlaceHolders.ReplaceMainplaceholderP(hea, p);
				foo2 = PlaceHolders.ReplaceMainplaceholderP(foo, p);
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
					foo2 = PlaceholderAPI.setPlaceholders(p, foo2);
					hea2 = PlaceholderAPI.setPlaceholders(p, hea2);
				}
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
					foo2 = PlaceHolders.BattleLevelPO(foo2, p);
					hea2 = PlaceHolders.BattleLevelPO(hea2, p);
				}
				
				Constructor<?> constructor = ChatComponentText.getConstructors()[0];
				Object header = constructor.newInstance(hea2);
				Object footer = constructor.newInstance(foo2);
				
				try {
					Field a = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("a");
					a.setAccessible(true);
					Field b = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("b");
					b.setAccessible(true);

					packet = newPacketPlayOutPlayerListHeaderFooter.newInstance();

					a.set(packet, header);
					b.set(packet, footer);
				} catch (Exception e) {
					Field a = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("header");
					a.setAccessible(true);
					Field b = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("footer");
					b.setAccessible(true);

					try {
						packet = newPacketPlayOutPlayerListHeaderFooter.newInstance();
					} catch (InstantiationException | InvocationTargetException e1) {
						e1.printStackTrace();
					}

					a.set(packet, header);
					b.set(packet, footer);
				}

				try {
					Object handle = p.getClass().getMethod("getHandle", new Class[0]).invoke(p);
					Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
					playerConnection.getClass().getMethod("sendPacket", new Class[] { NMSClass.getNMSClass("Packet") }).invoke(playerConnection, packet);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException |
				IllegalArgumentException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
