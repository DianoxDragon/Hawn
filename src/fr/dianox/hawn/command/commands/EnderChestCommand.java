package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.EnderChestCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

public class EnderChestCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.enderchest";

	public EnderChestCommand(String name) {
		 super(name);
		 this.description = "See player's enderchest";
		 this.usageMessage = "/ec [player]";
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

		 if (!EnderChestCommandConfig.getConfig().getBoolean("EnderChest.Enable")) {
			 if (EnderChestCommandConfig.getConfig().getBoolean("EnderChest.Disable-Message")) {
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
			 p.openInventory(p.getEnderChest());
	            
			 if (ConfigMMsg.getConfig().getBoolean("EnderChest.Self.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("EnderChest.Self.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				 }
			 }

		 } else if (args.length == 1) {
			 if (p.hasPermission("hawn.command.enderchest.other")) {
				 Player target = Bukkit.getServer().getPlayer(args[0]);
				 
				 if (target == null) {
					 MessageUtils.PlayerDoesntExist(p);
					 return true;
				 }

	             p.closeInventory();
	             p.openInventory(target.getEnderChest());
				 
	             if (ConfigMMsg.getConfig().getBoolean("EnderChest.Other-Sender.Enable")) {
	            	 for (String msg: ConfigMMsg.getConfig().getStringList("EnderChest.Other-Sender.Messages")) {
	            		 ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
	            	 }
	             }

			 } else {
				 String Permission = "hawn.command.enderchest.other";
				 MessageUtils.MessageNoPermission(p, Permission);
			 }
		 } else {
			 p.sendMessage("§c/enderchest [player]");
		 }
		 
		 return true;
	 }

}