package fr.dianox.hawn.commands.others;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.EnderChestCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

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
			 if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				 for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					 MessageUtils.ReplaceMessageForConsole(msg);
				 }
			 }
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!EnderChestCommandConfig.getConfig().getBoolean("EnderChest.Enable")) {
			 if (EnderChestCommandConfig.getConfig().getBoolean("EnderChest.Disable-Message")) {
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
		 
		 // >>> Commande
		 if (args.length == 0) {

			 p.closeInventory();
			 p.openInventory(p.getEnderChest());
	            
			 if (ConfigMCommands.getConfig().getBoolean("EnderChest.Self.Enable")) {
				 for (String msg: ConfigMCommands.getConfig().getStringList("EnderChest.Self.Messages")) {
					 MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
				 
	             if (ConfigMCommands.getConfig().getBoolean("EnderChest.Other-Sender.Enable")) {
	            	 for (String msg: ConfigMCommands.getConfig().getStringList("EnderChest.Other-Sender.Messages")) {
	            		 MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
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