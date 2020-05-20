package fr.dianox.hawn.command.commands.specials.worldedit;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.OneCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

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
        	if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ConsoleMessages(msg);
				}
			}
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!OneCommandConfig.getConfig().getBoolean("1.Enable")) {
            if (OneCommandConfig.getConfig().getBoolean("1.Disable-Message")) {
                if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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