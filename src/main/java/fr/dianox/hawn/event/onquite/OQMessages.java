package fr.dianox.hawn.event.onquite;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.world.OnQuitPW;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;

public class OQMessages {
	
	/*
	 * The main methods
	 */
	
	// The method used for all classic messages that is in the category of on quit message
	public static void OnMessage(Player p, PlayerQuitEvent e) {
		/*
		 * Just disable the message feature
		 */
		if (!ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Enable")) {
			return;
		}
		
		// Disable all messages
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Just-Simply-Disable-All-Quit-Messages")) {
			e.setQuitMessage("");
			return;
		}
		
		// To disable default messages
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Disable-Default-Message")) {
			e.setQuitMessage("");
		}
		
		/*
		 * Here the main code
		 */
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-World.Options.Enable")) {
			sendPerWorldMessage(p, e);
		} else if (!ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.World.All_World")) {
			if (OnQuitPW.getQM().contains(p.getWorld().getName())) {
				if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
					sendPerGroupMessage(p, e);
				} else {
					sendMainMessage(p, e);
				}
			}
		} else {
			if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
				sendPerGroupMessage(p, e);
			} else {
				sendMainMessage(p, e);
			}
		}
	}
	
	private static void sendPerWorldMessage(Player p, PlayerQuitEvent e) {
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-World.Options.Disable-Any-Other-Messages-On-Quit")) {
			e.setQuitMessage("");
		} else {
			if (!ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.World.All_World")) {
				if (OnQuitPW.getQM().contains(p.getWorld().getName())) {
					if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
						sendPerGroupMessage(p, e);
					} else {
						sendMainMessage(p, e);
					}
				}
			} else {
				if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Enable")) {
					sendPerGroupMessage(p, e);
				} else {
					sendMainMessage(p, e);
				}
			}
		}
		
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Silent-Staff-Quit") && p.hasPermission("hawn.event.silentquit")) {
			e.setQuitMessage("");
		} else {

			for (String string : ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-World.Worlds").getKeys(false)) {
				if (p.getLocation().getWorld().getName().equalsIgnoreCase(string)) {
					// 65: the lines of this world, sent once, only to the players of the world with Only-Broadcast-Messages-In-The-World
					fr.dianox.hawn.modules.onjoin.OJMessages.sendWorldLines(p, "General.On-Quit.Quit-Message.Per-World.Worlds." + string,
							ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World"),
							ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console"), "OQMessages");
					break;
				}
			}
			
		}
	}
	
	private static void sendMainMessage(Player p, PlayerQuitEvent e) {
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Silent-Staff-Quit") && p.hasPermission("hawn.event.silentquit")) {
			e.setQuitMessage("");
		} else {
			if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
				for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg.replace("%player%", p.getName()).replace("%player_displayname%", p.getDisplayName()), "", "OQMessagesSTRICTSCONSOLE", true);
					ConfigEventUtils.ExecuteEventAllPlayers(msg.replace("%player%", p.getName()).replace("%player_displayname%", p.getDisplayName()), "", "OQMessages", p, true);
				}
			} else {
				for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
					ConfigEventUtils.ExecuteEventAllPlayers(msg.replace("%player%", p.getName()).replace("%player_displayname%", p.getDisplayName()), "", "OQMessages", p, true);
				}
			}
		}
	}
	
	private static void sendPerGroupMessage(Player p, PlayerQuitEvent e) {
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-Group.Options.Disable-Any-Messages-On-Quit")) {
			e.setQuitMessage("");
		} else {
			sendMainMessage(p, e);
		}
		
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Silent-Staff-Quit") && p.hasPermission("hawn.event.silentquit")) {
			e.setQuitMessage("");
		} else {

			for (String string : ConfigMGeneral.getConfig().getConfigurationSection("General.On-Quit.Quit-Message.Per-Group.Groups").getKeys(false)) {
				if (p.hasPermission("hawn.on-quit.custommessage." + string)) {

					if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
						for (String msg : ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups." + string)) {
							ConfigEventUtils.ExecuteEvent(p, msg.replace("%player%", p.getName()).replace("%player_displayname%", p.getDisplayName()), "", "OQMessagesSTRICTSCONSOLE", true);
							ConfigEventUtils.ExecuteEventAllPlayers(msg.replace("%player%", p.getName()).replace("%player_displayname%", p.getDisplayName()), "", "OQMessages", p, true);
						}
					} else {
						for (String msg : ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups." + string)) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg.replace("%player%", p.getName()).replace("%player_displayname%", p.getDisplayName()), "", "OQMessages", p, true);
						}
					}

					break;
				}
			}
		}
	}

}