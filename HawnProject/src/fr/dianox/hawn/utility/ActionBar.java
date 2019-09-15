package fr.dianox.hawn.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;

public class ActionBar {
	
	public static void sendActionBar(Player player, String message) {
	    if (!player.isOnline()) {
	      return;
	    }

	    
	    ActionBarMessageEvent actionBarMessageEvent = new ActionBarMessageEvent(player, message);
	    Bukkit.getPluginManager().callEvent(actionBarMessageEvent);
	    if (actionBarMessageEvent.isCancelled())
	      return; 
	    try {
	      Object packet;
	      Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + Main.nmsver + ".entity.CraftPlayer");
	      Object craftPlayer = craftPlayerClass.cast(player);
	      
	      Class<?> packetPlayOutChatClass = Class.forName("net.minecraft.server." + Main.nmsver + ".PacketPlayOutChat");
	      Class<?> packetClass = Class.forName("net.minecraft.server." + Main.nmsver + ".Packet");
	      if (Main.useOldMethods) {
	        Class<?> chatSerializerClass = Class.forName("net.minecraft.server." + Main.nmsver + ".ChatSerializer");
	        Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + Main.nmsver + ".IChatBaseComponent");
	        Method m3 = chatSerializerClass.getDeclaredMethod("a", new Class[] { String.class });
	        Object cbc = iChatBaseComponentClass.cast(m3.invoke(chatSerializerClass, new Object[] { "{\"text\": \"" + message + "\"}" }));
	        packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, byte.class }).newInstance(new Object[] { cbc, Byte.valueOf((byte)2) });
	      } else {
	        Class<?> chatComponentTextClass = Class.forName("net.minecraft.server." + Main.nmsver + ".ChatComponentText");
	        Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + Main.nmsver + ".IChatBaseComponent");
	        try {
	          Class<?> chatMessageTypeClass = Class.forName("net.minecraft.server." + Main.nmsver + ".ChatMessageType");
	          Object[] chatMessageTypes = chatMessageTypeClass.getEnumConstants();
	          Object chatMessageType = null;
	          for (Object obj : chatMessageTypes) {
	            if (obj.toString().equals("GAME_INFO")) {
	              chatMessageType = obj;
	            }
	          } 
	          Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
	          packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, chatMessageTypeClass }).newInstance(new Object[] { chatCompontentText, chatMessageType });
	        } catch (ClassNotFoundException cnfe) {
	          Object chatCompontentText = chatComponentTextClass.getConstructor(new Class[] { String.class }).newInstance(new Object[] { message });
	          packet = packetPlayOutChatClass.getConstructor(new Class[] { iChatBaseComponentClass, byte.class }).newInstance(new Object[] { chatCompontentText, Byte.valueOf((byte)2) });
	        } 
	      } 
	      Method craftPlayerHandleMethod = craftPlayerClass.getDeclaredMethod("getHandle", new Class[0]);
	      Object craftPlayerHandle = craftPlayerHandleMethod.invoke(craftPlayer, new Object[0]);
	      Field playerConnectionField = craftPlayerHandle.getClass().getDeclaredField("playerConnection");
	      Object playerConnection = playerConnectionField.get(craftPlayerHandle);
	      Method sendPacketMethod = playerConnection.getClass().getDeclaredMethod("sendPacket", new Class[] { packetClass });
	      sendPacketMethod.invoke(playerConnection, new Object[] { packet });
	    } catch (Exception e) {
	      e.printStackTrace();
	    } 
	  }
	  
	  public static void sendActionBar(final Player player, final String message, int duration) {
	    sendActionBar(player, message);
	    
	    if (duration >= 0)
	    {
	      (new BukkitRunnable()
	        {
	          public void run() {
	            sendActionBar(player, "");
	          }
	        }).runTaskLater(Main.getInstance(), (duration + 1));
	    }

	    
	    while (duration > 40) {
	      duration -= 40;
	      (new BukkitRunnable()
	        {
	          public void run() {
	            sendActionBar(player, message);
	          }
	        }).runTaskLater(Main.getInstance(), duration);
	    } 
	  }

	  
	  public static void sendActionBarToAllPlayers(String message) { sendActionBarToAllPlayers(message, -1); }

	  
	  public static void sendActionBarToAllPlayers(String message, int duration) {
	    for (Player p : Bukkit.getOnlinePlayers())
	      sendActionBar(p, message, duration); 
	  }

}
