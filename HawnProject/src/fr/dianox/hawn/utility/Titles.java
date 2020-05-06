/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Crypto Morin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.dianox.hawn.utility;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import me.clip.placeholderapi.PlaceholderAPI;

import java.lang.reflect.Constructor;

/**
 * A reflection API for titles in Minecraft.
 * Fully optimized - Supports 1.8.8+ and above.
 * Requires ReflectionUtils.
 * Messages are not colorized by default.
 * <p>
 * Titles are text messages that appear in the
 * middle of the players screen: https://minecraft.gamepedia.com/Commands/title
 * PacketPlayOutTitle: https://wiki.vg/Protocol#Title
 *
 * @author Crypto Morin
 * @version 1.0.1
 * @see ReflectionUtils
 */
public class Titles {
	
	public static void clearTitle(Player player) {
		try {
			sendTitle(player, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), "", "");
		} catch (Exception e) {}
	}
	
	public static void sendPacket(Player player, Object packet) {
	    try {
	    	Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
	    	Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
	    	playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } 
	}
	  
	public static Class<?> getNMSClass(String name) {
		  String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
		  try {
			  return Class.forName("net.minecraft.server." + version + "." + name);
		  } catch (ClassNotFoundException e) {
			  e.printStackTrace();
			  return null;
		  } 
	}
	
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
	    TitleSendEvent titleSendEvent = new TitleSendEvent(player, title, subtitle);
	    Bukkit.getPluginManager().callEvent(titleSendEvent);
	    if (titleSendEvent.isCancelled())
	      return; 
	    
	    if (Main.avoidtitles.contains(player)) {
	    	return;
	    }
	    
	    try {
	      if (title != null) {
	    	  title = PlaceHolders.ReplaceMainplaceholderP(title, player);
				
	    	  if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	    		  title = PlaceholderAPI.setPlaceholders(player, title);
	    	  }
				
	    	  if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
	    		  title = PlaceHolders.BattleLevelPO(title, player);
	    	  }
	        title = ChatColor.translateAlternateColorCodes('&', title);
	        title = title.replaceAll("%player%", player.getDisplayName());
	        Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	        Object chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
	        Object titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
	        sendPacket(player, titlePacket);
	        e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
	        chatTitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent") });
	        titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
	        sendPacket(player, titlePacket);
	      } 
	      if (subtitle != null) {
	    	  subtitle = PlaceHolders.ReplaceMainplaceholderP(subtitle, player);
				
	    	  if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	    		  subtitle = PlaceholderAPI.setPlaceholders(player, subtitle);
	    	  }
				
	    	  if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
	    		  subtitle = PlaceHolders.BattleLevelPO(subtitle, player);
	    	  }
	        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
	        subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
	        Object e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	        Object chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor<?> subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
	        Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);
	        e = getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
	        chatSubtitle = getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
	        subtitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], getNMSClass("IChatBaseComponent"), int.class, int.class, int.class });
	        subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);
	      } 
	    } catch (Exception var11) {
	    	var11.printStackTrace();
	    } 
	}
	
	public static void time (Player p, Integer ticks) {
		Main.avoidtitles.add(p);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

			@Override
			public void run() {
				Main.avoidtitles.remove(p);
			}

		}, ticks);
	}
}