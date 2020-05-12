package fr.dianox.hawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.PlayerVisibility;
import fr.dianox.hawn.utility.config.commands.CheckAccountCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMAdmin;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class CheckAccountCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.checkaccount";

    public CheckAccountCommand(String name) {
        super(name);
        this.description = "Get some informations about the player";
        this.usageMessage = "/checka <player>";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {

        	 if (args.length == 0) {
                 if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
     				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
     					MessageUtils.ConsoleMessages(msg);
     				}
     			}
             } else if (args.length == 1) {
             	Player target = Bukkit.getServer().getPlayer(args[0]);

         		if (target == null) {
         			if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
        	            for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
        	            	MessageUtils.ConsoleMessages(msg);
        	            }
        	        }
         			return true;
         		}
         		
         		String ip = target.getAddress().getHostString();
         		
         		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.IP")) {
         			MessageUtils.ConsoleMessages(msg
         					.replaceAll("%target%", target.getName())
         					.replaceAll("%getplayerip%", ip));
         		}
             } else {
             	sender.sendMessage("§c/checka <player>");
             }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!CheckAccountCommandConfig.getConfig().getBoolean("CheckAccount.Enable")) {
            if (CheckAccountCommandConfig.getConfig().getBoolean("CheckAccount.Disable-Message")) {
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

        // The command

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
    		
    		String ip = target.getAddress().getHostString();
    		String joindate = ConfigPlayerGet.getFile(target.getUniqueId().toString()).getString("player_info.join_date");
    		String firstjoin = ConfigPlayerGet.getFile(target.getUniqueId().toString()).getString("player_info.first_join");
    		
    		String pv = "";
    		String sp = "";
    		String fly = "";
    		String dj = "";
    		String ksb = "";
    		String ab = "";
    		String v = "";
    		String jb = "";
    		String ps_number = PlayerOptionSQLClass.GetSQLPOSpeed(p, "VALUE");
    		String ksbname = PlayerOptionSQLClass.getYmlaMysqlsb(p, "scoreboard");
    		
    		String gm_number = String.valueOf(PlayerOptionSQLClass.GetSQLPOGamemode(p));
    		
    		if (PlayerVisibility.PVPlayer.contains(p)) {
    			pv = "§a§l✔";
    		} else {
    			pv = "§c§l✗";
    		}
    		
    		String value0 = PlayerOptionSQLClass.GetSQLPOJumpBoost(p);
    		if (value0.equalsIgnoreCase("TRUE")) {
    			jb = "§a§l✔";
    		} else {
    			jb = "§c§l✗";
    		}
    		
    		String value = PlayerOptionSQLClass.GetSQLPOSpeed(p, "ACTIVATE");
    		if (value.equalsIgnoreCase("TRUE")) {
    			sp = "§a§l✔";
    		} else {
    			sp = "§c§l✗";
    		}
    		
    		if (p.getAllowFlight() && (FlyCommand.player_list_flyc.contains(p) || ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_option_fly.Activate"))) {
    			fly = "§a§l✔";
    		} else {
    			fly = "§c§l✗";
    		}
    		
    		String value2 = PlayerOptionSQLClass.GetSQLPODoubleJump(p);
    		if (value2.equalsIgnoreCase("TRUE")) {
    			dj = "§a§l✔";
    		} else {
    			dj = "§c§l✗";
    		}
    		
    		String value3 = PlayerOptionSQLClass.getYmlaMysqlsb(p, "keepsb");
    		if (value3.equalsIgnoreCase("TRUE")) {
    			ksb = "§a§l✔";
    		} else {
    			ksb = "§c§l✗";
    		}
    		
    		String value4 = PlayerOptionSQLClass.GetSQLPOautobc(p);
    		if (value4.equalsIgnoreCase("TRUE")) {
    			ab = "§a§l✔";
    		} else {
    			ab = "§c§l✗";
    		}
    		
    		String value5 = PlayerOptionSQLClass.GetSQLPOVanish(p);
    		if (value5.equalsIgnoreCase("TRUE")) {
    			v = "§a§l✔";
    		} else {
    			v = "§c§l✗";
    		}
    		
    		for (String msg: ConfigMAdmin.getConfig().getStringList("Command.CheckAccount")) {
    			ConfigEventUtils.ExecuteEvent(p, msg
    					.replaceAll("%target%", target.getName())
    					.replaceAll("%player_ip%", ip)
    					.replaceAll("%hawn_player_join_date%", joindate)
    					.replaceAll("%hawn_player_first_join_date%", firstjoin)
    					.replaceAll("%pv_point%", pv)
    					.replaceAll("%ps_point%", sp)
    					.replaceAll("%ps_number%", ps_number)
    					.replaceAll("%pof_point%", fly)
    					.replaceAll("%dj_point%", dj)
    					.replaceAll("%ksb_point%", ksb)
    					.replaceAll("%scorename%", ksbname)
    					.replaceAll("%ab_point%", ab)
    					.replaceAll("%v_point%", v)
    					.replaceAll("%gm_number%", gm_number)
    					.replaceAll("%jb_point%", jb)
    					, "", "", false);
    		}
        } else {
        	p.sendMessage("§c/checka <player>");
        }

        return true;
    }

}