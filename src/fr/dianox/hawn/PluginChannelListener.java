package fr.dianox.hawn;

import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.configs.ServerListConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginChannelListener implements PluginMessageListener {

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player p, byte[] data) {
    	if (channel.equals("WDL|INIT") || channel.equals("wdl:init")) {
    		if (!p.hasPermission("hawn.antiwdl.bypass")) {
			    StringBuilder message = new StringBuilder();
			    boolean bool = false;
			    for (String str : ServerListConfig.getConfig().getStringList("Anti-WDL.Kick-Message")) {
				    if (bool) {
					    message.append("\n").append(str);
				    } else {
					    message = new StringBuilder(str);
					    bool = true;
				    }
			    }
			    message = new StringBuilder(message.toString().replaceAll("&", "§"));
			    message = new StringBuilder(PlaceHolders.ReplaceMainplaceholderP(message.toString(), p));

			    p.kickPlayer(message.toString());
		    }
		}
    }

}