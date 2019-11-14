package fr.dianox.hawn.commands.others.we;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.CopyCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class CopyCommand extends BukkitCommand {

    public CopyCommand(String name) {
        super(name);
        this.description = "copy";
        this.usageMessage = "/c";
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

        if (!CopyCommandConfig.getConfig().getBoolean("C.Enable")) {
            if (CopyCommandConfig.getConfig().getBoolean("C.Disable-Message")) {
                if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                    }
                }
            }

            return true;
        }

        // The command
        p.performCommand(CopyCommandConfig.getConfig().getString("C.Command"));
        
        return true;
    }

}