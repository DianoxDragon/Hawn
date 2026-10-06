package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.command.commands.tab.Tab;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.InvSeeCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

import java.util.ArrayList;
import java.util.List;

public class InvSeeCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.invsee";

	public InvSeeCommand(String name) {
		 super(name);
		 Tab.hideWithoutPermission(this, GeneralPermission);
		 this.description = "See player's inventory";
		 this.usageMessage = "/invsee [player]";
	 }

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		if (args.length == 1) {
			return Tab.players(sender, args);
		}

		return Tab.none();
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

		 if (!InvSeeCommandConfig.getConfig().getBoolean("InvSee.Enable")) {
			 if (InvSeeCommandConfig.getConfig().getBoolean("InvSee.Disable-Message")) {
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

			 if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
				 
			 if (ConfigMMsg.getConfig().getBoolean("InvSee.Other-Sender.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("InvSee.Other-Sender.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg.replace("%target%", target.getName()), "", "", false);
				 }
			 }
		 } else {
			 p.sendMessage("§c/invsee <player>");
		 }
		 
		 return true;
	 }

}