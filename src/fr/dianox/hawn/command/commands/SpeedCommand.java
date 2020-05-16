package fr.dianox.hawn.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.commands.SpeedCommandConfig;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class SpeedCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.optionplayer.speed";

	public SpeedCommand(String name) {
		 super(name);
		 this.description = "Change or enable/disable speed";
		 this.usageMessage = "/speed [number]";
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

		 if (!SpeedCommandConfig.getConfig().getBoolean("Speed.Enable")) {
			 if (SpeedCommandConfig.getConfig().getBoolean("Speed.Disable-Message")) {
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
		 if (args.length == 1) {
             if (!ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_speed.Activate")) {
                 if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Option-Disabled.Enable")) {
                     for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Option-Disabled.Messages")) {
                         ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                     }
                 }

                 return true;
             }

             try {
                 @SuppressWarnings("unused")
                 int i = Integer.parseInt(args[0]);

                 ConfigPlayerGet.writeBoolean(p.getUniqueId().toString(), "player_speed.Activate", true);
                 Float WalkSpeed = (float) Integer.valueOf(args[0]) / 10;

                 if (Integer.valueOf(args[0]) < 0 || Integer.valueOf(args[0]) > 10) {
                     p.sendMessage("§c0-10");
                     return false;
                 }

                 p.setWalkSpeed(WalkSpeed);
                 PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", Integer.valueOf(args[0]));

                 if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
                     for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
                         ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", args[0]), "", "", false);
                     }
                 }
             } catch (NumberFormatException e) {
                 p.sendMessage("§c/option speed <number>");
             }
         } else if (args.length == 0) {

             String value = PlayerOptionSQLClass.GetSQLPOSpeed(p, "ACTIVATE");
             int speedvalue = Integer.valueOf(PlayerOptionSQLClass.GetSQLPOSpeed(p, "VALUE"));

             if (value.equalsIgnoreCase("TRUE")) {
                 PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvalue);
                 p.setWalkSpeed(0.2F);
                 if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Disable.Enable")) {
                     for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Disable.Messages")) {
                         ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                     }
                 }
             } else {
                 if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option") && p.hasPermission("hawn.command.optionplayer.speed.priorityoptionplayer")) {
                 	PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", speedvalue);
                 	
                 	if (speedvalue < 0 || speedvalue > 10) {
                 		p.setWalkSpeed(0.2F);
                 	} else {
                 		Float WalkSpeed = (float) speedvalue / 10;
                 		p.setWalkSpeed(WalkSpeed);
                 	}

                 	if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Enable.Enable")) {
                 		for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Enable.Messages")) {
                 			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                 		}
                 	}
                 } else {
                     speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

                     PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", speedvalue);

                     if (speedvalue < 0 || speedvalue > 10) {
                         p.setWalkSpeed(0.2F);
                     } else {
                         Float WalkSpeed = (float) speedvalue / 10;
                         p.setWalkSpeed(WalkSpeed);
                     }

                     if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Enable.Enable")) {
                         for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Enable.Messages")) {
                             ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                         }
                     }
                 }
             }
		 } else {
			 p.sendMessage("§c/flyspeed [number]");
		 }
		 
		 return true;
	 }

}