package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.ClearMobsCommandConfig;
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

public class ClearMobsCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.clearmobs";

	public ClearMobsCommand(String name) {
		 super(name);
		 this.description = "Clear all mobs";
		 this.usageMessage = "/clearmobs";
	 }

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
		return null;
	}

	 @Override
	 public boolean execute(CommandSender sender, String label, String[] args) {
		 
		 // >>> Executed by the console
		 if(!(sender instanceof Player)) {
			 
			 Integer mobs = 0;
			 
			 for (World w : Bukkit.getWorlds()) {
				 List<Entity> entList = w.getEntities();
				 for (Entity current : entList) {
					 if (!current.getType().equals(EntityType.PLAYER)) {
						 current.remove();
						 mobs++;
					 }
				 }
			 }
			 
			 for (String msg: ConfigMAdmin.getConfig().getStringList("Command.ClearMobs")) {
		 			MessageUtils.ConsoleMessages(msg);
			 }
			 
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!ClearMobsCommandConfig.getConfig().getBoolean("ClearMobs.Enable")) {
			 if (ClearMobsCommandConfig.getConfig().getBoolean("ClearMobs.Disable-Message")) {
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
		 
		 Integer mobs = 0;
		 
		 // >>> Commande
		 for (World w : Bukkit.getWorlds()) {
			 List<Entity> entList = w.getEntities();
			 for (Entity current : entList) {
				 if (!current.getType().equals(EntityType.PLAYER)) {
					 current.remove();
					 mobs++;
				 }
			 }
		 }
		 
		 for (String msg: ConfigMAdmin.getConfig().getStringList("Command.ClearMobs")) {
 			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
		 }
		 
		 return true;
	 }

}