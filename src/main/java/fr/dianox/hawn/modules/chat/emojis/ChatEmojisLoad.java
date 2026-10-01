package fr.dianox.hawn.modules.chat.emojis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fr.dianox.hawn.utility.config.configs.cosmeticsfun.EmojisListCUtility;

public class ChatEmojisLoad {
	
	public static Map<String, String> emojislist = new HashMap<String, String>();
	public static Map<String, String> emojislistperm = new HashMap<String, String>();
	public static Map<String, String> emojislistname = new HashMap<String, String>();
	
	public static void onLoad() {
		
		emojislist.clear();
		emojislistperm.clear();
		
		Iterator < ? > iterator = EmojisListCUtility.getConfig().getConfigurationSection("Emojis-list").getKeys(false).iterator();
		
		while (iterator.hasNext()) {
            String string = (String) iterator.next();
            
            if (EmojisListCUtility.getConfig().getBoolean("Emojis-list." + string + ".Enable")) {
	            for (String replacewith: EmojisListCUtility.getConfig().getStringList("Emojis-list." + string + ".Emoji.Replace_With")) {
	            	emojislist.put(replacewith, EmojisListCUtility.getConfig().getString("Emojis-list." + string + ".Emoji.Shape"));
	            	
	            	if (EmojisListCUtility.getConfig().getBoolean("Emojis-list." + string + ".Use_Permission")) {
	            		emojislistperm.put(replacewith, "hawn.emoji." + string);
	                }
	            	
	            	emojislistname.put(replacewith, string);
	            }
            }
		};
	}

}
