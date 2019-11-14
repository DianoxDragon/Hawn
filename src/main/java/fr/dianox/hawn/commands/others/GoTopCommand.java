package fr.dianox.hawn.commands.others;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.GoTopCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class GoTopCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.gotop";

    public GoTopCommand(String name) {
        super(name);
        this.description = "Gotop a player";
        this.usageMessage = "/gotop <player>";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
    	if(!(sender instanceof Player)) {
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
			return true;
		}

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!GoTopCommandConfig.getConfig().getBoolean("Gotop.Enable")) {
            if (GoTopCommandConfig.getConfig().getBoolean("Gotop.Disable-Message")) {
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
            
        	Location l = p.getLocation();
        	
        	Block b = p.getWorld().getHighestBlockAt(l.getBlockX(), l.getBlockZ());
        	
        	Location l2 = new Location(b.getLocation().getWorld(), b.getLocation().getBlockX(), b.getLocation().getBlockY() + 1, b.getLocation().getBlockZ());
        	p.teleport(l2);
        	
            if (ConfigMCommands.getConfig().getBoolean("Gotop.Enable")) {
                for (String msg: ConfigMCommands.getConfig().getStringList("Gotop.Messages")) {
                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                }
            }
        } else {
            if (p.hasPermission("hawn.command.gotop.other")) {
                if (GoTopCommandConfig.getConfig().getBoolean("Gotop.Others.Enable")) {
                    p.sendMessage("§c/gotop or §c/gotop [player]");
                } else {
                    p.sendMessage("§c/gotop");
                }
            } else {
                p.sendMessage("§c/gotop");
            }
        }

        return true;
    }

}