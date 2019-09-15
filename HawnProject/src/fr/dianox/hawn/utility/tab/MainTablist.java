package fr.Dianox.Hawn.Utility.Tab;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.Utility.MessageUtils;
import fr.dianox.hawn.Utility.TitleUtils;
import fr.dianox.hawn.Utility.Config.ConfigGeneral;
import fr.dianox.hawn.Utility.Config.Tab.TablistConfig;
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
	    					s = s.replace("{anim_" + anim + "}", TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(Main.animationtab.get(anim)));
	    				} catch (Exception e) {}
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
	    				s = s.replace("{anim_" + anim + "}", TablistConfig.getConfig().getStringList("Animations." + anim + ".text").get(Main.animationtab.get(anim)));
	    				} catch (Exception e) {}
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
				
				hea2 = MessageUtils.ReplaceMainplaceholderP(hea, p);
				foo2 = MessageUtils.ReplaceMainplaceholderP(foo, p);
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
					foo2 = PlaceholderAPI.setPlaceholders(p, foo2);
					hea2 = PlaceholderAPI.setPlaceholders(p, hea2);
				}
				
				if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
					foo2 = MessageUtils.BattleLevelPO(foo2, p);
					hea2 = MessageUtils.BattleLevelPO(hea2, p);
				}
				
				Constructor<?> constructor = ChatComponentText.getConstructors()[0];
				Object header = constructor.newInstance(hea2);
				Object footer = constructor.newInstance(foo2);
				
				try {
					Field a = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("a");
					a.setAccessible(true);
					Field b = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("b");
					b.setAccessible(true);

					packet = newPacketPlayOutPlayerListHeaderFooter.newInstance(new Object[0]);

					a.set(packet, header);
					b.set(packet, footer);
				} catch (Exception e) {
					Field a = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("header");
					a.setAccessible(true);
					Field b = PacketPlayOutPlayerListHeaderFooter.getDeclaredField("footer");
					b.setAccessible(true);

					try {
						packet = newPacketPlayOutPlayerListHeaderFooter.newInstance(new Object[0]);
					} catch (InstantiationException | InvocationTargetException e1) {
						e1.printStackTrace();
					}

					a.set(packet, header);
					b.set(packet, footer);
				}

				TitleUtils.sendPacket(p, packet);
			}
		} catch (IllegalAccessException | NoSuchFieldException | SecurityException |
				IllegalArgumentException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
