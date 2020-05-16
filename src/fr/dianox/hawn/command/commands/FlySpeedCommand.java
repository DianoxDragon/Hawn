package fr.dianox.hawn.command.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.commands.FlySpeedCommandConfig;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class FlySpeedCommand extends BukkitCommand {
	
	private String GeneralPermission = "hawn.command.optionplayer.flyspeed";

	public FlySpeedCommand(String name) {
		 super(name);
		 this.description = "Change or enable/disable flyspeed";
		 this.usageMessage = "/flyspeed [number]";
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

		 if (!FlySpeedCommandConfig.getConfig().getBoolean("FlySpeed.Enable")) {
			 if (FlySpeedCommandConfig.getConfig().getBoolean("FlySpeed.Disable-Message")) {
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
				if (!ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_fly_speed.Activate")) {
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
					
					ConfigPlayerGet.writeBoolean(p.getUniqueId().toString(), "player_fly_speed.Activate", true);
					Float FloatSpeed = (float) Integer.valueOf(args[0]) / 10;
					
					if (Integer.valueOf(args[0]) < 0 || Integer.valueOf(args[0]) > 10) {
						p.sendMessage("§c0-10");
						return false;
					}
					
					p.setFlySpeed(FloatSpeed);
					PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "TRUE", Integer.valueOf(args[0]));
					
					if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Set.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Set.Messages")) {
							ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", String.valueOf(args[0])), "", "", false);
						}
					}
				} catch (NumberFormatException e) {
					p.sendMessage("§c/flyspeed [number]");
				}
			} else if (args.length == 0) {
				String value = PlayerOptionSQLClass.GetSQLPOFlySpeed(p, "ACTIVATE");
				int speedvalue = Integer.valueOf(PlayerOptionSQLClass.GetSQLPOFlySpeed(p, "VALUE"));
				
				if (value.equalsIgnoreCase("TRUE")) {
					PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "FALSE", speedvalue);
                 p.setFlySpeed(0.1F);
                 if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Disable.Enable")) {
                     for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Disable.Messages")) {
                         ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                     }
                 }
				} else {
					if (OnJoinConfig.getConfig().getBoolean("FlySpeed.Option.Priority-For-Player-Option") && p.hasPermission("hawn.command.optionplayer.flyspeed.priorityoptionplayer")) {
						PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "TRUE", speedvalue);

						if (speedvalue < 0 || speedvalue > 10) {
							p.setFlySpeed(0.1F);
						} else {
							Float FlySpeed = (float) speedvalue / 10;
							p.setFlySpeed(FlySpeed);
						}

						if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Enable.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Enable.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
							}
						}
                 } else {
                     speedvalue = OnJoinConfig.getConfig().getInt("FlySpeed.Value");

                     PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "TRUE", speedvalue);

                     if (speedvalue < 0 || speedvalue > 10) {
                         p.setFlySpeed(0.1F);
                     } else {
                         Float FlySpeed = (float) speedvalue / 10;
                         p.setFlySpeed(FlySpeed);
                     }

                     if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Enable.Enable")) {
                         for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Enable.Messages")) {
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