package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.command.commands.tab.Tab;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.BurnCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

import java.util.ArrayList;
import java.util.List;

public class BurnCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.burn";

	public BurnCommand(String name) {
		 super(name);
		 Tab.hideWithoutPermission(this, GeneralPermission);
		 this.description = "Burn a player";
		 this.usageMessage = "/burn <player> <duration>";
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
			 if (args.length == 2) {
				 Player target = Bukkit.getServer().getPlayer(args[0]);
					 
				 if (target == null) {
					 if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
						 for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
							 MessageUtils.ConsoleMessages(msg);
						 }
					 }
					 return true;
				 }

				 target.setFireTicks(Integer.parseInt(args[1]) * 20);
					 
				 if (ConfigMMsg.getConfig().getBoolean("Burn.Sender.Enable")) {
					 for (String msg: ConfigMMsg.getConfig().getStringList("Burn.Sender.Messages")) {
						 MessageUtils.ConsoleMessages(msg.replace("%target%", target.getName()).replace("%duration%", args[1]));
					 }
				 }
				 
				 if (ConfigMMsg.getConfig().getBoolean("Burn.Target.Enable")) {
					 for (String msg: ConfigMMsg.getConfig().getStringList("Burn.Target.Messages")) {
						 ConfigEventUtils.ExecuteEvent(target, msg.replace("%player%", "console").replace("%duration%", ConfigEventUtils.noAction(args[1])), "", "", false);
					 }
				 }
			 } else {
				 sender.sendMessage("§c/burn <player> <duration>");
			 }
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!BurnCommandConfig.getConfig().getBoolean("Burn.Enable")) {
			 if (BurnCommandConfig.getConfig().getBoolean("Burn.Disable-Message")) {
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
		 if (args.length == 2) {
			 Player target = Bukkit.getServer().getPlayer(args[0]);
				 
			 if (target == null) {
				 MessageUtils.PlayerDoesntExist(p);
				 return true;
			 }

			 target.setFireTicks(Integer.parseInt(args[1]) * 20);
				 
			 if (ConfigMMsg.getConfig().getBoolean("Burn.Sender.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Burn.Sender.Messages")) {
					 ConfigEventUtils.ExecuteEvent(p, msg.replace("%target%", target.getName()).replace("%duration%", ConfigEventUtils.noAction(args[1])), "", "", false);
				 }
			 }
			 
			 if (ConfigMMsg.getConfig().getBoolean("Burn.Target.Enable")) {
				 for (String msg: ConfigMMsg.getConfig().getStringList("Burn.Target.Messages")) {
					 ConfigEventUtils.ExecuteEvent(target, msg.replace("%player%", p.getName()).replace("%duration%", ConfigEventUtils.noAction(args[1])), "", "", false);
				 }
			 }
		 } else {
			 p.sendMessage("§c/burn <player> <duration>");
		 }
		 
		 return true;
	 }

}