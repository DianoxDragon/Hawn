package fr.dianox.hawn.event;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.CustomCommandConfig;
import fr.dianox.hawn.utility.config.commands.HelpCommandConfig;
import fr.dianox.hawn.utility.config.events.CommandEventConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMAdmin;

public class OnCommandEvent implements Listener {

	public static List<String> cooldowncommands = new ArrayList<String>();
	
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        
        if (!HelpCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
            if (e.getMessage().startsWith("/help") || e.getMessage().startsWith("/?")) {
                e.setCancelled(true);
                p.performCommand(e.getMessage().replace("/", ""));
            }
        }

        if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Enable")) {
            if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Bypass")) {
                if (!p.hasPermission("hawn.event.bypass.blockcommands")) {
                    for (String i: CommandEventConfig.getConfig().getStringList("Block-Commands.List")) {
                        if (e.getMessage().equalsIgnoreCase(i)) {
                            e.setCancelled(true);
                            
                            if (Main.Spigot_Version >= 113) {
                            	if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Options.Face-Guardian-1-13-1-14")) {
                            		p.spawnParticle(Particle.MOB_APPEARANCE, p.getLocation(), 1);
                            		p.playSound(p.getLocation(), XSound.ENTITY_ELDER_GUARDIAN_CURSE.parseSound(), 1, 1);
                            	}
                            }
                            
                            if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Options.Notify-Staff")) {
                            	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                            		if (all.hasPermission("hawn.notify.staff.commandblocker")) {
                            			for (String str: ConfigMAdmin.getConfig().getStringList("Command-Blocker.Notify-Staff")) {
                            				  ConfigEventUtils.ExecuteEvent(p, str.replaceAll("%player%", p.getName()).replaceAll("%arg1%", e.getMessage()), "", "", false);
                            			}
                            		}
                            	}
                            }
                            
                            if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Message-Enable")) {
                                for (String msg: CommandEventConfig.getConfig().getStringList("Block-Commands.Message")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
                        }
                    }
                }
            } else {
                for (String i: CommandEventConfig.getConfig().getStringList("Block-Commands.List")) {
                    if (e.getMessage().equalsIgnoreCase(i)) {
                        e.setCancelled(true);
                        
                        if (Main.Spigot_Version >= 113) {
                        	if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Options.Face-Guardian-1-13-1-14")) {
                        		p.spawnParticle(Particle.MOB_APPEARANCE, p.getLocation(), 1);
                        		p.playSound(p.getLocation(), XSound.ENTITY_ELDER_GUARDIAN_CURSE.parseSound(), 1, 1);
                        	}
                        }
                        
                        if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Options.Notify-Staff")) {
                        	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                        		if (all.hasPermission("hawn.notify.staff.commandblocker")) {
                        			for (String str: ConfigMAdmin.getConfig().getStringList("Command-Blocker.Notify-Staff")) {
                        				ConfigEventUtils.ExecuteEvent(p, str.replaceAll("%player%", p.getName()).replaceAll("%arg1%", e.getMessage()), "", "", false);
                        			}
                        		}
                        	}
                        }
                        
                        if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Message-Enable")) {
                            for (String msg: CommandEventConfig.getConfig().getStringList("Block-Commands.Message")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
                    }
                }
            }
        }

        if (CustomCommandConfig.getConfig().getBoolean("commands-general.enable")) {
            Iterator < ? > iterator = CustomCommandConfig.getConfig().getConfigurationSection("commands").getKeys(false).iterator();

            while (iterator.hasNext()) {
                String string = (String) iterator.next();

                if (e.getMessage().equalsIgnoreCase(CustomCommandConfig.getConfig().getString("commands." + string + ".command"))) {
                	
                	// Check if the command is enabled
        			if (!CustomCommandConfig.getConfig().getBoolean("commands." + string + ".enable")) {
        				e.setCancelled(true);
        				return;
        			}
        			
        			// Check if the permission is enabled and the player have the permission
        			if (CustomCommandConfig.getConfig().isSet("commands." + string + ".permission.enable")) {
        	            if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".permission.enable")) {
        	                if (!p.hasPermission(CustomCommandConfig.getConfig().getString("commands." + string + ".permission.message"))) {
        	                    if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".no-permission-message-enable")) {
        	                        String Permission = CustomCommandConfig.getConfig().getString("commands." + string + ".permission.message");
        	                        MessageUtils.MessageNoPermission(p, Permission);
        	                    }
        	                    e.setCancelled(true);
        	                    return;
        	                }
        	            }
                	}
        			
        			// Check cooldown
        			if (CustomCommandConfig.getConfig().isSet("commands." + string + ".Cooldown.enable")) {
                    	if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".Cooldown.enable")) {
                    		if (OnCommandEvent.cooldowncommands.contains(p.getName() + string)) {
                    			
                    			for (String str: CustomCommandConfig.getConfig().getStringList("commands." + string + ".Cooldown.messages")) {
                    				ConfigEventUtils.ExecuteEvent(p, str, "", "", false);
                    			}
                    			e.setCancelled(true);
                    			return;
                    		} else {
                    			OnCommandEvent.cooldowncommands.add(p.getName() + string);
                    			
                    			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

        							@Override
        							public void run() {
        								OnCommandEvent.cooldowncommands.remove(p.getName() + string);
        							}

        						}, CustomCommandConfig.getConfig().getInt("commands." + string + ".Cooldown.Ticks"));
                    		}
                    	}
                    }
        			
                	for (String msg: CustomCommandConfig.getConfig().getStringList("commands." + string + ".message")) {
                		ConfigEventUtils.ExecuteEvent(p, msg, "CustomCommand", string, false);
                	}
                	
                	e.setCancelled(true);
                }
            }
        }
    }
    
    public static void executeCustomCommand(String string, Player p) {
    	// Check if the command is enabled
		if (!CustomCommandConfig.getConfig().getBoolean("commands." + string + ".enable")) {
			return;
		}
		
		// Check if the permission is enabled and the player have the permission
		if (CustomCommandConfig.getConfig().isSet("commands." + string + ".permission.enable")) {
            if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".permission.enable")) {
                if (!p.hasPermission(CustomCommandConfig.getConfig().getString("commands." + string + ".permission.message"))) {
                    if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".no-permission-message-enable")) {
                        String Permission = CustomCommandConfig.getConfig().getString("commands." + string + ".permission.message");
                        MessageUtils.MessageNoPermission(p, Permission);
                    }

                    return;
                }
            }
    	}
		
		// Check cooldown
		if (CustomCommandConfig.getConfig().isSet("commands." + string + ".Cooldown.enable")) {
        	if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".Cooldown.enable")) {
        		if (OnCommandEvent.cooldowncommands.contains(p.getName() + string)) {
        			
        			for (String str: CustomCommandConfig.getConfig().getStringList("commands." + string + ".Cooldown.messages")) {
        				ConfigEventUtils.ExecuteEvent(p, str, "", "", false);
        			}
        			
        			return;
        		} else {
        			OnCommandEvent.cooldowncommands.add(p.getName() + string);
        			
        			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

						@Override
						public void run() {
							OnCommandEvent.cooldowncommands.remove(p.getName() + string);
						}

					}, CustomCommandConfig.getConfig().getInt("commands." + string + ".Cooldown.Ticks"));
        		}
        	}
        }
		
    	for (String msg: CustomCommandConfig.getConfig().getStringList("commands." + string + ".message")) {
    		ConfigEventUtils.ExecuteEvent(p, msg, "CustomCommand", string, false);
    	}
    }
}