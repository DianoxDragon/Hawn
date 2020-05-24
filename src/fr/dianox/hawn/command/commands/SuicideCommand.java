package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.SuicideCommandConfig;

import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

public class SuicideCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.suicide";

	public SuicideCommand(String name) {
		 super(name);
		 this.description = "Kill yourself";
		 this.usageMessage = "/suicide <player>";
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

		 if (!SuicideCommandConfig.getConfig().getBoolean("Suicide.Enable")) {
			 if (SuicideCommandConfig.getConfig().getBoolean("Suicide.Disable-Message")) {
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
		 EntityDamageEvent ede = new EntityDamageEvent(p, EntityDamageEvent.DamageCause.SUICIDE, 32767.0D);
		 Bukkit.getPluginManager().callEvent(ede);
		 p.damage(32767.0D);
			 
		 if (p.getHealth() > 0.0D) {
			 p.setHealth(0.0D);
		 }
		 
		 if (ConfigMMsg.getConfig().getBoolean("Suicide.Self.Enable")) {
			 for (String msg: ConfigMMsg.getConfig().getStringList("Suicide.Self.Messages")) {
				 ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
			 }
		 }
			 
		 if (ConfigMMsg.getConfig().getBoolean("Suicide.Broadcast.Enable")) {
			 for (String msg: ConfigMMsg.getConfig().getStringList("Suicide.Broadcast.Messages")) {
				 MessageUtils.ConsoleMessages(msg.replaceAll("%player%", p.getName()));
				 ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()), "", "", p, true);
			 }
		 }
			 
		 
		 
		 return true;
	 }

}