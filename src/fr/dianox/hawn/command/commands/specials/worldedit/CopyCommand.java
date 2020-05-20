package fr.dianox.hawn.command.commands.specials.worldedit;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.configs.commands.CopyCommandConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;

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
        	if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ConsoleMessages(msg);
				}
			}
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!CopyCommandConfig.getConfig().getBoolean("C.Enable")) {
            if (CopyCommandConfig.getConfig().getBoolean("C.Disable-Message")) {
                if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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