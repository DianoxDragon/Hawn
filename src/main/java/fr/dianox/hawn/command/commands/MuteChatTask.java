package fr.dianox.hawn.command.commands;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

/**
 * The end of /gmute <minutes>: the chat is opened again, even if the one who muted it has left.
 */
public class MuteChatTask extends BukkitRunnable {

	// Who muted the chat, for the placeholders of the messages: null for the console
	private final Player by;
	private final String name;

	public MuteChatTask(Player by) {
        this.by = by;
        this.name = by == null ? "console" : by.getName();
	}

	@Override
	public void run() {
		MuteChatCommand.taskrunning = false;

		// Already opened with /gmute
		if (!MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
			return;
		}

		MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);

		for (String msg: ConfigMMsg.getConfig().getStringList("MuteChat.Admin.Off")) {
			if (by != null && by.isOnline()) {
				MessageUtils.ConsoleMessages(msg);
				ConfigEventUtils.ExecuteEventAllPlayers(msg, "", "", by, true);
			} else {
				msg = msg.replace("%player%", name);
				MessageUtils.ConsoleMessages(msg);
				ConfigEventUtils.ExecuteEventAllPlayersConsole(msg, "", "");
			}
		}
	}

}
