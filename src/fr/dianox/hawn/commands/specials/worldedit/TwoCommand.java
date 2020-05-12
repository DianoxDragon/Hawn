package fr.dianox.hawn.commands.specials.worldedit;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.TwoCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

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
        	if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ConsoleMessages(msg);
				}
			}
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!TwoCommandConfig.getConfig().getBoolean("2.Enable")) {
            if (TwoCommandConfig.getConfig().getBoolean("2.Disable-Message")) {
                if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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