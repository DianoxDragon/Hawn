package fr.dianox.hawn.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.KickAllCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import fr.dianox.hawn.utility.config.messages.ConfigMAdmin;

public class KickAllCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.kickall";

    public KickAllCommand(String name) {
        super(name);
        this.description = "Kick all players of the lobby";
        this.usageMessage = "/kickall";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {

        	if (args.length == 0) {
                for (Player all: Bukkit.getServer().getOnlinePlayers()) {
             	   if (!all.hasPermission("hawn.command.bypass.kickall")) {
             		   all.kickPlayer("Kicked");
             	   }
                }
                
                for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Kickall")) {
             	   MessageUtils.ConsoleMessages(msg);
                }
             } else {
             	sender.sendMessage("§c/kickall");
             }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!KickAllCommandConfig.getConfig().getBoolean("KickAll.Enable")) {
            if (KickAllCommandConfig.getConfig().getBoolean("KickAll.Disable-Message")) {
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
           for (Player all: Bukkit.getServer().getOnlinePlayers()) {
        	   if (all.getName().equals(p.getName())) {
        		  continue;
        	   }
        	   
        	   if (!all.hasPermission("hawn.command.bypass.kickall")) {
        		   all.kickPlayer("Kicked");
        	   }
           }
           
           for (String msg: ConfigMAdmin.getConfig().getStringList("Command.Kickall")) {
        	   ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
           }
        } else {
        	p.sendMessage("§c/kickall");
        }

        return true;
    }

}