package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.Tasks;
import fr.dianox.hawn.command.commands.tab.Tab;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import fr.dianox.hawn.utility.Task;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.MuteChatCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

import java.util.List;

public class MuteChatCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.mutechat";

	public static Boolean taskrunning = false;
	public static Integer tasknumber = 0;
	
	public MuteChatCommand(String name) {
		super(name);
		Tab.hideWithoutPermission(this, GeneralPermission);
		this.description = "Mute the chat";
        this.usageMessage = "/gmute [number]";
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return Tab.none();
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
					for (String msg : ConfigMMsg.getConfig().getStringList("MuteChat.Admin.Off")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", "console")));
						MessageUtils.ConsoleMessages(msg.replace("%player%", "console"));
					}
					// Each player in their language (since 1.6)
					ConfigEventUtils.broadcast(to -> ConfigMMsg.getConfig(to).getStringList("MuteChat.Admin.Off"), msg -> msg.replace("%player%", "console"), null, "", "");
					MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
					
					if (taskrunning) {
						Tasks.cancel(tasknumber);
						taskrunning = false;
					}
					
				} else {
					for (String msg : ConfigMMsg.getConfig().getStringList("MuteChat.Admin.On")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", "console")));
						MessageUtils.ConsoleMessages(msg.replace("%player%", "console"));
					}
					// Each player in their language (since 1.6)
					ConfigEventUtils.broadcast(to -> ConfigMMsg.getConfig(to).getStringList("MuteChat.Admin.On"), msg -> msg.replace("%player%", "console"), null, "", "");
					MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
				}
			} else {
				int minutes = minutes(args[0]);
				if (minutes <= 0) {
					if (ConfigMMsg.getConfig().getBoolean("Error.Use-Number.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("Error.Use-Number.Messages")) {
							MessageUtils.ConsoleMessages(msg);
						}
					}
					return true;
				}

				for (String msg: ConfigMMsg.getConfig().getStringList("MuteChat.Admin.On-Time")) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replace("%player%", "console").replace("%minutes%", args[0])));
					MessageUtils.ConsoleMessages(msg.replace("%player%", "console").replace("%minutes%", args[0]));
				}
				// Each player in their language (since 1.6)
				String minutesText = ConfigEventUtils.noAction(args[0]);
				ConfigEventUtils.broadcast(to -> ConfigMMsg.getConfig(to).getStringList("MuteChat.Admin.On-Time"),
						msg -> msg.replace("%player%", "console").replace("%minutes%", minutesText), null, "", "");

				MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);

				unmuteIn(minutes, null);
			}
		    return true;
		    
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Enable")) {
			if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Disable-Message")) {
				if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMMsg.getConfig(p).getStringList("Error.Command-Disable.Messages")) {
                		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                	}
    			}
			}
			
			return true;
		}
		
		if (!p.hasPermission(GeneralPermission)) {
			MessageUtils.MessageNoPermission(p, GeneralPermission);
			return true;
		}
			
		// >> The command
		if (args.length == 0) {
			if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
				for (String msg : ConfigMMsg.getConfig().getStringList("MuteChat.Admin.Off")) {
					MessageUtils.ConsoleMessages(msg);
				}
				// Each player in their language (since 1.6)
				ConfigEventUtils.broadcast(to -> ConfigMMsg.getConfig(to).getStringList("MuteChat.Admin.Off"), msg -> msg, p, "", "");
				MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
				
				if (taskrunning) {
					Tasks.cancel(tasknumber);
					taskrunning = false;
				}
				
			} else {
				for (String msg : ConfigMMsg.getConfig().getStringList("MuteChat.Admin.On")) {
					MessageUtils.ConsoleMessages(msg);
				}
				// Each player in their language (since 1.6)
				ConfigEventUtils.broadcast(to -> ConfigMMsg.getConfig(to).getStringList("MuteChat.Admin.On"), msg -> msg, p, "", "");
				MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
			}
		} else {
			int minutes = minutes(args[0]);
			if (minutes <= 0) {
				MessageUtils.UseNumber(p);
				return true;
			}

			for (String msg : ConfigMMsg.getConfig().getStringList("MuteChat.Admin.On-Time")) {
				MessageUtils.ConsoleMessages(msg.replace("%minutes%", args[0]));
			}
			// Each player in their language (since 1.6)
			ConfigEventUtils.broadcast(to -> ConfigMMsg.getConfig(to).getStringList("MuteChat.Admin.On-Time"), msg -> msg.replace("%minutes%", ConfigEventUtils.noAction(args[0])), p, "", "");

			MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);

			unmuteIn(minutes, p);
		}


		return true;
	}

	// The number of minutes typed, -1 when it is not a number
	private static int minutes(String text) {
		try {
			return Integer.parseInt(text.trim());
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	// The chat is opened again after these minutes (it shut the server down before 1.4.1)
	private static void unmuteIn(int minutes, Player by) {
		if (taskrunning) {
			Tasks.cancel(tasknumber);
		}

		Task task = Tasks.later(new MuteChatTask(by), minutes * 60L * 20L);

		taskrunning = true;
		tasknumber = task.getTaskId();
	}

}
