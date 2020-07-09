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
		if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Just-Simply-Disable-Quit-Join-Messages")) {
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
					if (p.hasPermission("hawn.on-quit.custom-message-per-world." + string)) {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World")) {
							for (Player all : Bukkit.getServer().getWorld(string).getPlayers()) {
								if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Broadcast-To-Console")) {
									for (String msg : ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-World.Worlds." + string)) {
										ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "General.On-Quit.Quit-Message.Broadcast-To-Console", "OQMessagesSTRICTSCONSOLE", true);
										ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "General.On-Quit.Quit-Message.Broadcast-To-Console", "OQMessages", p, true);
									}
								} else {
									for (String msg : ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-World.Worlds." + string)) {
										ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "General.On-Quit.Quit-Message.Broadcast-To-Console", "OQMessages", p, true);
									}
								}
							}
						}
					} else {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-Quit.Quit-Message.Broadcast-To-Console")) {
							for (String msg : ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-World.Worlds." + string)) {
								ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "General.On-Quit.Quit-Message.Broadcast-To-Console", "OQMessagesSTRICTSCONSOLE", true);
								ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "General.On-Quit.Quit-Message.Broadcast-To-Console", "OQMessages", p, true);
							}
						} else {
							for (String msg : ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-World.Worlds." + string)) {
								ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "General.On-Quit.Quit-Message.Broadcast-To-Console", "OQMessages", p, true);
							}
						}

					}
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
					ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "", "OQMessagesSTRICTSCONSOLE", true);
					ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "", "OQMessages", p, true);
				}
			} else {
				for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Messages")) {
					ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname%", p.getDisplayName()), "", "OQMessages", p, true);
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
							ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname", p.getDisplayName()), "", "OQMessagesSTRICTSCONSOLE", true);
							ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname", p.getDisplayName()), "", "OQMessages", p, true);
						}
					} else {
						for (String msg : ConfigMGeneral.getConfig().getStringList("General.On-Quit.Quit-Message.Per-Group.Groups." + string)) {
							ConfigEventUtils.ExecuteEventAllPlayers(msg.replaceAll("%player%", p.getName()).replaceAll("%player_displayname", p.getDisplayName()), "", "OQMessages", p, true);
						}
					}

					break;
				}
			}
		}
	}

}