package fr.dianox.hawn.commands.others;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.ClearMobsCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;

public class ClearMobsCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.clearmobs";

	public ClearMobsCommand(String name) {
		 super(name);
		 this.description = "Clear all mobs";
		 this.usageMessage = "/clearmobs";
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
			 
			 for (String msg: OtherAMConfig.getConfig().getStringList("Command.ClearMobs")) {
		 			MessageUtils.ReplaceMessageForConsole(msg);
			 }
			 
			 return true;
		 }
	    	
		 // >>> Executed by the player
		 Player p = (Player) sender;

		 if (!ClearMobsCommandConfig.getConfig().getBoolean("ClearMobs.Enable")) {
			 if (ClearMobsCommandConfig.getConfig().getBoolean("ClearMobs.Disable-Message")) {
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
		 
		 for (String msg: OtherAMConfig.getConfig().getStringList("Command.ClearMobs")) {
 			MessageUtils.ReplaceCharMessagePlayer(msg, p);
		 }
		 
		 return true;
	 }

}