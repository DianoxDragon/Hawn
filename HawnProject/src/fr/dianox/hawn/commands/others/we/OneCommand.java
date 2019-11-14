package fr.dianox.hawn.commands.others.we;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.OneCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;

public class OneCommand extends BukkitCommand {

    public OneCommand(String name) {
        super(name);
        this.description = "Set pos 1";
        this.usageMessage = "/1";
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

        if (!OneCommandConfig.getConfig().getBoolean("1.Enable")) {
            if (OneCommandConfig.getConfig().getBoolean("1.Disable-Message")) {
                if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                    }
                }
            }

            return true;
        }

        // The command
        p.performCommand(OneCommandConfig.getConfig().getString("1.Command"));
        
        return true;
    }

}