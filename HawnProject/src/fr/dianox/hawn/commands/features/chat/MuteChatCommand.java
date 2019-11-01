package fr.dianox.hawn.commands.features.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.tasks.TaskShutdownServer;

public class MuteChatCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.mutechat";

	public static Boolean taskrunning = false;
	public static Integer tasknumber = 0;
	
	public MuteChatCommand(String name) {
		super(name);
		this.description = "Mute the chat";
        this.usageMessage = "/gmute [number]";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if (!(sender instanceof Player)) {
			if (args.length == 0) {
				if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.Off")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player%", "console")));
						MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%player%", "console"));
					}
					MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
					
					if (taskrunning) {
						Bukkit.getScheduler().cancelTask(tasknumber);
						taskrunning = false;
					}
					
				} else {
					for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.On")) {
						Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player%", "console")));
						MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%player%", "console"));
					}
					MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
				}
			} else {
				for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.On-Time")) {
					Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg.replaceAll("%player%", "console").replaceAll("%minutes%", args[0])));
					MessageUtils.ReplaceCharBroadcastNoPlayer(msg.replaceAll("%player%", "console").replaceAll("%minutes%", args[0]));
				}
				
				MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
				
				Integer time = (Integer.valueOf(args[0]) * 60) * 20;
				
				BukkitTask task = new TaskShutdownServer().runTaskLater(Main.getInstance(), time);
				
				taskrunning = true;
				tasknumber = task.getTaskId();
			}
		    return true;
		    
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Enable")) {
			if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Disable-Message")) {
				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
				for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.Off")) {
					MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
				}
				MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", false);
				
				if (taskrunning) {
					Bukkit.getScheduler().cancelTask(tasknumber);
					taskrunning = false;
				}
				
			} else {
				for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.On")) {
					MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
				}
				MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
			}
		} else {
			for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Admin.On-Time")) {
				MessageUtils.ReplaceCharBroadcastGeneral(msg.replaceAll("%minutes%", args[0]), p);
			}
			
			MuteChatCommandConfig.getConfig().set("MuteChat.Mute.Enable", true);
			
			Integer time = (Integer.valueOf(args[0]) * 60) * 20;
			
			BukkitTask task = new TaskShutdownServer().runTaskLater(Main.getInstance(), time);
			
			taskrunning = true;
			tasknumber = task.getTaskId();
		}
		
		
		return true;
	}

}
