package fr.dianox.hawn.commands.others;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.KickAllCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;

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

        	if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				 for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					 MessageUtils.ReplaceMessageForConsole(msg);
				 }
			 }

            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!KickAllCommandConfig.getConfig().getBoolean("KickAll.Enable")) {
            if (KickAllCommandConfig.getConfig().getBoolean("KickAll.Disable-Message")) {
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

        // The command

        if (args.length == 0) {
           for (Player all: Bukkit.getServer().getOnlinePlayers()) {
        	   if (all.getName().equals(p.getName())) {
        		  continue;
        	   }
        	   
        	   if (!p.hasPermission("hawn.command.bypass.kickall")) {
        		   all.kickPlayer("Kicked");
        	   }
           }
           
           for (String msg: OtherAMConfig.getConfig().getStringList("Command.Kickall")) {
        	   MessageUtils.ReplaceCharMessagePlayer(msg, p);
           }
        } else {
        	p.sendMessage("§c/kickall");
        }

        return true;
    }

}