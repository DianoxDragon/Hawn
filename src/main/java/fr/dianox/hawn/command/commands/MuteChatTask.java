package fr.dianox.hawn.command.commands;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;


public class MuteChatTask extends BukkitRunnable {
	
	private Player p;
	
	public MuteChatTask(Player p) {
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
			ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", p, true);
		}
		
		MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
		
		MuteChatCommand.taskrunning = false;
	}

}