package fr.dianox.hawn.commands.specials.worldedit;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.PasteCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

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
        	if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ConsoleMessages(msg);
				}
			}
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!PasteCommandConfig.getConfig().getBoolean("P.Enable")) {
            if (PasteCommandConfig.getConfig().getBoolean("P.Disable-Message")) {
                if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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