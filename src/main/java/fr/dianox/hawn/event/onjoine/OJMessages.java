package fr.dianox.hawn.event.onjoine;

import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.world.OnJoinPW;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Iterator;

public class OJMessages {

	/*
	 * The main methods
	 */
	
	// The method used for all classic messages that is in the category of on join message
	public static void OnMessage(Player p, PlayerJoinEvent e) {
		/*
		 * Just disable the message feature
		 */
		if (!ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Enable")) {
			return;
		}
		
		// Disable all messages
		if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Just-Simply-Disable-All-Join-Messages")) {
			e.setJoinMessage("");
			return;
		}
		
		// To disable default messages
		if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Disable-Default-Message")) {
			e.setJoinMessage("");
		}
		
		/*
		 * Here the main code
		 */
		if (p.hasPlayedBefore()) {			
			if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-World.Options.Enable")) {
				sendPerWorldMessage(p, e);
			} else if (!ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.World.All_World")) {
				if (OnJoinPW.getJM().contains(p.getWorld().getName())) {
					if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-Group.Options.Enable")) {
						sendPerGroupMessage(p, e);
					} else {
						sendMainMessage(p, e);
					}
				}
			} else {
				if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-Group.Options.Enable")) {
					sendPerGroupMessage(p, e);
				} else {
					sendMainMessage(p, e);
				}
			}
			
		// If the player didn't played before
		} else {
			// If messages are not disabled for new players, execute the code below
			if (!ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Disable-For-New-Players")) {
				
				if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-World.Options.Enable")) {
					sendPerWorldMessage(p, e);
				} else if (!ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.World.All_World")) {
					if (OnJoinPW.getJM().contains(p.getWorld().getName())) {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-Group.Options.Enable")) {
							sendPerGroupMessage(p, e);
						} else {
							sendMainMessage(p, e);
						}
					}
				} else {
					if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-Group.Options.Enable")) {
						sendPerGroupMessage(p, e);
					} else {
						sendMainMessage(p, e);
					}
				}
				
			}
		}
		
	}
	
	private static void sendPerWorldMessage(Player p, PlayerJoinEvent e) {
		if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-World.Options.Disable-Any-Other-Messages-On-Join")) {
			e.setJoinMessage("");
		} else {
			if (!ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.World.All_World")) {
				if (OnJoinPW.getJM().contains(p.getWorld().getName())) {
					if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-Group.Options.Enable")) {
						sendPerGroupMessage(p, e);
					} else {
						sendMainMessage(p, e);
					}
				}
			} else {
				if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-Group.Options.Enable")) {
					sendPerGroupMessage(p, e);
				} else {
					sendMainMessage(p, e);
				}
			}
		}
		
		if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Silent-Staff-Join") && p.hasPermission("hawn.event.silentjoin")) {
			e.setJoinMessage("");
		} else {
			Iterator < ? > iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-join.Join-Message.Per-World.Worlds").getKeys(false).iterator();
			
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				
				if (p.getLocation().getWorld().getName().equalsIgnoreCase(string)) {
					if (p.hasPermission("hawn.on-join.custom-message-per-world." + string)) {
						if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-World.Options.Only-Broadcast-Messages-In-The-World")) {							
							for (Player all : Bukkit.getServer().getWorld(string).getPlayers()) {
				                    for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Per-World.Worlds." + string)) {
				                        MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), all);
				                    }
				                }
							}
							
							if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Broadcast-To-Console")) {
			                    for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Per-World.Worlds." + string)) {
			                    	MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", p.getName()));
			                    }
			                } else {
			                    for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Per-World.Worlds." + string)) {
			                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
			                    }
			                }
							
							break;
						} else {
							if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Broadcast-To-Console")) {
			                    for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Per-World.Worlds." + string)) {
			                        MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
			                    }
			                } else {
			                    for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Per-World.Worlds." + string)) {
			                        MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
			                    }
			                }
			
			                break;
						}
		            }
				}
			
		}
	}
	
	private static void sendMainMessage(Player p, PlayerJoinEvent e) {
		if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Silent-Staff-Join") && p.hasPermission("hawn.event.silentjoin")) {
			e.setJoinMessage("");
		} else {
			if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Broadcast-To-Console")) {
	            for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Messages")) {
	                MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
	            }
	        } else {
	            for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Messages")) {
	                MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
	            }
	        }
		}
	}
	
	private static void sendPerGroupMessage(Player p, PlayerJoinEvent e) {
		if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Per-Group.Options.Disable-Any-Messages-On-Join")) {
			e.setJoinMessage("");
		} else {
			sendMainMessage(p, e);
		}

		if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Silent-Staff-Join") && p.hasPermission("hawn.event.silentjoin")) {
			e.setJoinMessage("");
		} else {
			Iterator < ? > iterator = ConfigMGeneral.getConfig().getConfigurationSection("General.On-join.Join-Message.Per-Group.Groups").getKeys(false).iterator();
			
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				
				if (p.hasPermission("hawn.on-join.custommessage." + string)) {
	                if (ConfigMGeneral.getConfig().getBoolean("General.On-join.Join-Message.Broadcast-To-Console")) {
	                    for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Per-Group.Groups." + string)) {
	                        MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
	                    }
	                } else {
	                    for (String msg: ConfigMGeneral.getConfig().getStringList("General.On-join.Join-Message.Per-Group.Groups." + string)) {
	                        MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
	                    }
	                }
	
	                break;
	            }
			}
		}
	}
	
	/*
	 * Here all the rest of piece of code for some messages on join
	 */
	
	public static void onMOTD(Player p) {
		if (p.hasPlayedBefore()) {
			if (ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.Per-World.Options.Enable")) {
				sendPerWorldMessageMotd(p);
			} else {
				onMethodMOTDMainMessages(p);
			}
		} else {
			onMethodMOTDFJ(p);
		}
    }
	
	public static void onMethodMOTDMainMessages(Player p) {
		if (ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.Enable")) {
            if (!ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.World.All_World")) {
                if (OnJoinPW.getWD().contains(p.getWorld().getName())) {
                	for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.On-join.Messages")) {
                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                    }
                }
            } else {
                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.On-join.Messages")) {
                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                }
            }
        }
	}
	
	public static void onMethodMOTDFJ(Player p) {
		if (ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.First-Join.Motd.Enable")) {
            for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.On-join.First-Join.Motd.Messages")) {
                MessageUtils.ReplaceCharMessagePlayer(msg, p);
            }
            if (ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.First-Join.Motd.Both-Motd")) {
            	onMethodMOTDMainMessages(p);
            }
        } else {
        	onMethodMOTDMainMessages(p);
        }
	}
	
	private static void sendPerWorldMessageMotd(Player p) {
		if (!ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.Per-World.Options.Disable-All-The-Others-Motd")) {
			if (p.hasPlayedBefore()) {
				onMethodMOTDMainMessages(p);
			} else {
				onMethodMOTDFJ(p);
			}
		}
		
		Iterator < ? > iterator = ConfigMGeneral.getConfig().getConfigurationSection("Spawn.On-join.Per-World.Worlds").getKeys(false).iterator();
		
		while (iterator.hasNext()) {
			String string = (String) iterator.next();
				
			if (p.getLocation().getWorld().getName().equalsIgnoreCase(string)) {
				if (p.hasPermission("hawn.on-join.custom-motd-per-world." + string)) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.On-join.Per-World.Worlds." + string)) {
	                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
	                }
					
					break;
				}				
			}
		}
	}
	
	/*
	 * 
	 * Broadcast on first join
	 * 
	 */
	
	public static void onBroadcastFJ(Player p) {
		if (!p.hasPlayedBefore()) {
			if (ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.First-Join.Broadcast.Enable")) {
	            if (ConfigMGeneral.getConfig().getBoolean("Spawn.On-join.First-Join.Broadcast.Broadcast-To-The-Console")) {
	                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.On-join.First-Join.Broadcast.Messages")) {
	                    MessageUtils.ReplaceCharBroadcastGeneral(msg, p);
	                }
	            } else {
	                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.On-join.First-Join.Broadcast.Messages")) {
	                    MessageUtils.ReplaceCharBroadcastPlayer(msg, p);
	                }
	            }
	        }
		}
	}
	
}
