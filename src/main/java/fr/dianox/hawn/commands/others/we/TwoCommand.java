package fr.dianox.hawn.commands.others.we;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.TwoCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class TwoCommand extends BukkitCommand {

    public TwoCommand(String name) {
        super(name);
        this.description = "Set pos 2";
        this.usageMessage = "/2";
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

        if (!TwoCommandConfig.getConfig().getBoolean("2.Enable")) {
            if (TwoCommandConfig.getConfig().getBoolean("2.Disable-Message")) {
                if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                    }
                }
            }

            return true;
        }

        // The command
        p.performCommand(TwoCommandConfig.getConfig().getString("2.Command"));
        
        return true;
    }

}