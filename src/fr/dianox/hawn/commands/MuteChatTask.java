package fr.dianox.hawn.commands;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;


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
		
		for (String msg: ConfigMMsg.getConfig().getStringList("MuteChat.Admin.Off")) {
			MessageUtils.ConsoleMessages(msg);
			ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "");
		}
		
		MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
		
		MuteChatCommand.taskrunning = false;
	}

}