package fr.dianox.hawn.utility;

import java.lang.reflect.Constructor;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import fr.dianox.hawn.commands.PingCommand;
import fr.dianox.hawn.commands.features.chat.DelaychatCommand;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import me.clip.placeholderapi.PlaceholderAPI;

public class TitleUtils extends Event implements Listener {
	
	  private static final HandlerList handlers = new HandlerList();
	  private final Player player;
	  private String title;
	  private String subtitle;
	  private boolean cancelled = false;
	
	  public TitleUtils(Player player, String title, String subtitle) {
	    this.player = player;
	    this.title = title;
	    this.subtitle = subtitle;
	  }
	
	  @SuppressWarnings("rawtypes")
	public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
	    TitleUtils TitleUtils = new TitleUtils(player, title, subtitle);
	    Bukkit.getPluginManager().callEvent(TitleUtils);
	    if (TitleUtils.isCancelled()) {
	      return;
	    } try {
	      if (title != null) {
	        title = ChatColor.translateAlternateColorCodes('&', title);
	        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	        	title = PlaceholderAPI.setPlaceholders(player, title).replaceAll("%player%", player.getDisplayName()).replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay)).replaceAll("%ping%", String.valueOf(PingCommand.getPing(player)));
	        } else {
	        	title = title.replaceAll("%player%", player.getDisplayName()).replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay)).replaceAll("%ping%", String.valueOf(PingCommand.getPing(player)));
	        }
	        	        
	        Object e = NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	        Object chatTitle = NMSClass.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor subtitleConstructor = NMSClass.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSClass.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        Object titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle, fadeIn, stay, fadeOut });
	        sendPacket(player, titlePacket);
	        	        
	        e = NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TITLE").get(null);
	        chatTitle = NMSClass.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        subtitleConstructor = NMSClass.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSClass.getNMSClass("IChatBaseComponent") });
	        titlePacket = subtitleConstructor.newInstance(new Object[] { e, chatTitle });
	        sendPacket(player, titlePacket);
	      } if (subtitle != null) {
	        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
	        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
	        	subtitle = PlaceholderAPI.setPlaceholders(player, subtitle).replaceAll("%player%", player.getDisplayName()).replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay)).replaceAll("%ping%", String.valueOf(PingCommand.getPing(player)));
	        } else {
	        	subtitle = subtitle.replaceAll("%player%", player.getDisplayName()).replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay)).replaceAll("%ping%", String.valueOf(PingCommand.getPing(player)));
	        }
	        	
	        Object e = NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("TIMES").get(null);
	        Object chatSubtitle = NMSClass.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + title + "\"}" });
	        Constructor subtitleConstructor = NMSClass.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSClass.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        Object subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);
	        
	        e = NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0].getField("SUBTITLE").get(null);
	        chatSubtitle = NMSClass.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0].getMethod("a", new Class[] { String.class }).invoke(null, new Object[] { "{\"text\":\"" + subtitle + "\"}" });
	        subtitleConstructor = NMSClass.getNMSClass("PacketPlayOutTitle").getConstructor(new Class[] { NMSClass.getNMSClass("PacketPlayOutTitle").getDeclaredClasses()[0], NMSClass.getNMSClass("IChatBaseComponent"), Integer.TYPE, Integer.TYPE, Integer.TYPE });
	        subtitlePacket = subtitleConstructor.newInstance(new Object[] { e, chatSubtitle, fadeIn, stay, fadeOut });
	        sendPacket(player, subtitlePacket);
	      }
	    } catch (Exception var11) {
	      var11.printStackTrace();
	    }
	  }

	  public HandlerList getHandlers() {
	    return handlers;
	  }
	  
	  public static HandlerList getHandlerList() {
	    return handlers;
	  }
	  
	  public Player getPlayer() {
	    return this.player;
	  }
	  
	  public String getTitle() {
	    return this.title;
	  }
	  
	  public void setTitle(String title) {
	    this.title = title;
	  }
	  
	  public String getSubtitle() {
	    return this.subtitle;
	  }
	  
	  public void setSubtitle(String subtitle) {
	    this.subtitle = subtitle;
	  }
	  
	  public boolean isCancelled() {
	    return this.cancelled;
	  }
	  
	  public void setCancelled(boolean cancelled) {
	    this.cancelled = cancelled;
	  }
	  
	  @Deprecated
	  public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
	    sendTitle(player, fadeIn, stay, fadeOut, message, null);
	  }
	  
	  @Deprecated
	  public static void sendSubtitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
	    sendTitle(player, fadeIn, stay, fadeOut, null, message);
	  }
	  
	  @Deprecated
	  public static void sendFullTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
	    sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
	  }
	  
	  public static void sendPacket(Player player, Object packet){
	    try {
	      Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player, new Object[0]);
	      Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
	      playerConnection.getClass().getMethod("sendPacket", new Class[] { NMSClass.getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
	  
	  public static void clearTitle(Player player) {
	    sendTitle(player, Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), "", "");
	  }
	
}