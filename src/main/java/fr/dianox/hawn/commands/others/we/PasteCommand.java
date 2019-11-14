package fr.dianox.hawn.commands.others.we;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.PasteCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMOStuff;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class PasteCommand extends BukkitCommand {

    public PasteCommand(String name) {
        super(name);
        this.description = "paste";
        this.usageMessage = "/p";
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

        if (!PasteCommandConfig.getConfig().getBoolean("P.Enable")) {
            if (PasteCommandConfig.getConfig().getBoolean("P.Disable-Message")) {
                if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                    }
                }
            }

            return true;
        }

        // The command
        p.performCommand(PasteCommandConfig.getConfig().getString("P.Command"));
        
        return true;
    }

}