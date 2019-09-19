package fr.dianox.hawn.commands.others;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.InvSeeCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class InvSeeCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.invsee";

	public InvSeeCommand(String name) {
		 super(name);
		 this.description = "See player's inventory";
		 this.usageMessage = "/invsee [player]";
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

		 if (!InvSeeCommandConfig.getConfig().getBoolean("InvSee.Enable")) {
			 if (InvSeeCommandConfig.getConfig().getBoolean("InvSee.Disable-Message")) {
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

			 if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				 for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					 MessageUtils.ReplaceCharMessagePlayer(msg, p);
				 }
			 }

		 } else if (args.length == 1) {
			 Player target = Bukkit.getServer().getPlayer(args[0]);
				 
			 if (target == null) {
				 MessageUtils.PlayerDoesntExist(p);
				 return true;
			 }

			 p.closeInventory();
			 p.openInventory(target.getInventory());
				 
			 if (ConfigMCommands.getConfig().getBoolean("InvSee.Other-Sender.Enable")) {
				 for (String msg: ConfigMCommands.getConfig().getStringList("InvSee.Other-Sender.Messages")) {
					 MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
				 }
			 }
		 } else {
			 p.sendMessage("§c/invsee <player>");
		 }
		 
		 return true;
	 }

}