package fr.dianox.hawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.ClearInvCommandConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

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
			if (args.length == 1) {
					Player target = Bukkit.getServer().getPlayer(args[0]);
					
					if (target == null) {
						if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
								MessageUtils.ConsoleMessages(msg);
							}
						}
	            		return true;
					}
						
					target.getInventory().clear();
					target.getInventory().setArmorContents(new ItemStack[4]);
					
					if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Sender.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("ClearInv.Other-Sender.Messages")) {
							msg = msg.replaceAll("%player%", "console").replaceAll("%target%", target.getName());
		            		MessageUtils.ConsoleMessages(msg);
						}
					}
						
					if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Target.Enable")) {
						for (String msg: ConfigMMsg.getConfig().getStringList("ClearInv.Other-Target.Messages")) {
							ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
						}
					}
					
			} else {
				Bukkit.getConsoleSender().sendMessage("§c/clearinv <player>");
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!ClearInvCommandConfig.getConfig().getBoolean("ClearInv.Enable")) {
			if (ClearInvCommandConfig.getConfig().getBoolean("ClearInv.Disable-Message")) {
				if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
						
						if (ConfigMMsg.getConfig().getBoolean("ClearInv.Self.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("ClearInv.Self.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
							
							if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Sender.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("ClearInv.Other-Sender.Messages")) {
									msg = msg.replaceAll("%player%", p.getName()).replaceAll("%target%", target.getName());
									ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
								}
							}
								
							if (ConfigMMsg.getConfig().getBoolean("ClearInv.Other-Target.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("ClearInv.Other-Target.Messages")) {
									msg = msg.replaceAll("%player%", p.getName());
									ConfigEventUtils.ExecuteEvent(target, msg, "", "", false);
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
