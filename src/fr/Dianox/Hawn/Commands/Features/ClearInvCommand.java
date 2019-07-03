package fr.Dianox.Hawn.Commands.Features;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.ClearInvCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class ClearInvCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.clearinv";

	public ClearInvCommand(String name) {
		super(name);
		this.description = "Clear the inventory of a player";
        this.usageMessage = "/clearinv [player]";
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg, sender);
				}
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!ClearInvCommandConfig.getConfig().getBoolean("ClearInv.Enable")) {
			if (ClearInvCommandConfig.getConfig().getBoolean("ClearInv.Disable-Message")) {
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
						
						p.getInventory().clear();
						p.getInventory().setArmorContents(new ItemStack[4]);
						
						if (ConfigMCommands.getConfig().getBoolean("ClearInv.Self.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("ClearInv.Self.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					} else if (args.length == 1) {
						if (p.hasPermission("Hawn.command.clearinv.others")) {
							Player target = Bukkit.getServer().getPlayer(args[0]);
							
							if (target == null) {
								MessageUtils.PlayerDoesntExist(p);
			            		return true;
							}
								
							target.getInventory().clear();
							target.getInventory().setArmorContents(new ItemStack[4]);
							
							if (ConfigMCommands.getConfig().getBoolean("ClearInv.Other-Sender.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("ClearInv.Other-Sender.Messages")) {
									msg = msg.replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName());
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
								
							if (ConfigMCommands.getConfig().getBoolean("ClearInv.Other-Target.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("ClearInv.Other-Target.Messages")) {
									msg = msg.replaceAll("%player%", p.getName());
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
							
						} else {
							String Permission = "hawn.command.clearinv.others";
							MessageUtils.MessageNoPermission(p, Permission);
						}
					}

		
		return false;
	}
	
}
