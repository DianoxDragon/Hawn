package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.ClearGroundItemsCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class ClearGroundItemsCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.cleargrounditems";

	public ClearGroundItemsCommand(String name) {
		 super(name);
		 this.description = "Clear all items on the ground";
		 this.usageMessage = "/cleargrounditems";
	 }

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}

	 @Override
	 public boolean execute(CommandSender sender, String label, String[] args) {
		 
		 // >>> Executed by the console
		 if(!(sender instanceof Player)) {
			 
			 Integer items = 0;
			 
			 for (World w : Bukkit.getWorlds()) {
				 List<Entity> entList = w.getEntities();
				 for (Entity current : entList) {
					 if (current.getType() == EntityType.DROPPED_ITEM) {
						 
						 current.remove();
						 items++;
					 } 
				 } 
			 }
			 
			 for (String msg: ConfigMAdmin.getConfig().getStringList("Command.ClearGroundItems")) {
		 			MessageUtils.ConsoleMessages(msg);
			 }
			 
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!ClearGroundItemsCommandConfig.getConfig().getBoolean("ClearGroundItems.Enable")) {
			 if (ClearGroundItemsCommandConfig.getConfig().getBoolean("ClearGroundItems.Disable-Message")) {
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
		 
		 Integer items = 0;
		 
		 // >>> Commande
		 for (World w : Bukkit.getWorlds()) {
			 List<Entity> entList = w.getEntities();
			 for (Entity current : entList) {
				 if (current.getType() == EntityType.DROPPED_ITEM) {
					 
					 current.remove();
					 items++;
				 }
			 }
		 }
		 
		 for (String msg: ConfigMAdmin.getConfig().getStringList("Command.ClearGroundItems")) {
 			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
		 }
		 
		 return true;
	 }

}