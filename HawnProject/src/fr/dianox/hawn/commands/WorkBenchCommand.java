package fr.dianox.hawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.WorkBenchCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class WorkBenchCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.workbench";

	public WorkBenchCommand(String name) {
		 super(name);
		 this.description = "Open player's workbench";
		 this.usageMessage = "/workbench <player>";
	 }

	 @Override
	 public boolean execute(CommandSender sender, String label, String[] args) {
		 
		 // >>> Executed by the console
		 if(!(sender instanceof Player)) {
			 if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					 MessageUtils.ConsoleMessages(msg);
				 }
			 }
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!WorkBenchCommandConfig.getConfig().getBoolean("WorkBench.Enable")) {
			 if (WorkBenchCommandConfig.getConfig().getBoolean("WorkBench.Disable-Message")) {
				 if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
					 for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
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
		 
		 // >>> Commande
		 if (args.length == 0) {
			 p.closeInventory();
			 p.openWorkbench(null, true);
				 
			 if (ConfigMMsg.getConfig().getBoolean("WorkBench.Self.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("WorkBench.Self.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				 }
			 }

		 } else if (args.length == 1) {
			 Player target = Bukkit.getServer().getPlayer(args[0]);
				 
			 if (target == null) {
				 MessageUtils.PlayerDoesntExist(p);
				 return true;
			 }

			 target.closeInventory();
			 target.openWorkbench(null, true);
				 
			 if (ConfigMMsg.getConfig().getBoolean("WorkBench.Sender.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("WorkBench.Sender.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
				 }
			 }
			 
			 if (ConfigMMsg.getConfig().getBoolean("WorkBench.Target.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("WorkBench.Target.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName()), "", "", false);
				 }
			 }
		 } else {
			 p.sendMessage("§c/workbench <player>");
		 }
		 
		 return true;
	 }

}