package fr.dianox.hawn;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.config.ServerListConfig;

public class PluginChannelListener implements PluginMessageListener {

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player p, byte[] data) {
    	if ((channel.equals("WDL|INIT") && !p.hasPermission("hawn.antiwdl.bypass")) || ((channel.equals("wdl:init") && !p.hasPermission("hawn.antiwdl.bypass")))) {
			String message = ServerListConfig.getConfig().getString("Anti-WDL.Kick-Message");
			message = message.replaceAll("&", "§");
			message = PlaceHolders.ReplaceMainplaceholderP(message, p);
			
			p.kickPlayer(message);
		}
    }

}