package fr.dianox.hawn.commands.features.chat;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class MuteChatTask extends BukkitRunnable {
	
	private Player p;
	
	public MuteChatTask(Player p, Integer time) {
        this.p = p;
	}

	@Override
	public void run() {
		if (p == null || !p.isOnline()) {
            p = null;
            cancel();
            return;
        }
		
		for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.Off")) {
			MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
		}
		MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
		
		MuteChatCommand.taskrunning = false;
	}

}