package fr.dianox.hawn.event;

import com.cryptomorin.xseries.particles.XParticle;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import com.cryptomorin.xseries.XSound;
import fr.dianox.hawn.utility.config.configs.CustomCommandConfig;
import fr.dianox.hawn.utility.config.configs.commands.HelpCommandConfig;
import fr.dianox.hawn.utility.config.configs.events.CommandEventConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMAdmin;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class OnCommandEvent implements Listener {

	public static List<String> cooldowncommands = new ArrayList<String>();
	
    // A command already cancelled (urgent mode, another plugin) is not intercepted: /help would run it again
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        
        if (isBlocked(p, e.getMessage())) {
            e.setCancelled(true);

            if (Main.getInstance().getVersionUtils().getSpigot_Version() >= 113) {
                if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Options.Face-Guardian-1-13-1-14")) {
                    p.spawnParticle(XParticle.ELDER_GUARDIAN.get(), p.getLocation(), 1);
                    p.playSound(p.getLocation(), XSound.ENTITY_ELDER_GUARDIAN_CURSE.parseSound(), 1, 1);
                }
            }

            if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Options.Notify-Staff")) {
                for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                    if (all.hasPermission("hawn.notify.staff.commandblocker")) {
                        for (String str: ConfigMAdmin.getConfig().getStringList("Command-Blocker.Notify-Staff")) {
                            ConfigEventUtils.ExecuteEvent(all, str.replace("%player%", p.getName()).replace("%arg1%", ConfigEventUtils.noAction(e.getMessage())), "", "", false);
                        }
                    }
                }
            }

            if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Message-Enable")) {
                for (String msg: CommandEventConfig.getConfig().getStringList("Block-Commands.Message")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                }
            }

            return;
        }

        if (!HelpCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
            // Only /help and /?, not /helpop or /helpme, and the / of the arguments are kept
            String label = e.getMessage().split(" ", 2)[0].toLowerCase(Locale.ROOT);

            if (label.equals("/help") || label.equals("/?")) {
                e.setCancelled(true);
                p.performCommand(e.getMessage().substring(1));
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

    // The blocked commands are also hidden from the tab completion
    @EventHandler
    public void onCommandSend(PlayerCommandSendEvent e) {
        if (!blockerAppliesTo(e.getPlayer())) {
            return;
        }

        e.getCommands().removeIf(label -> matchesBlockList("/" + label));
    }

    private static boolean blockerAppliesTo(Player p) {
        if (!CommandEventConfig.getConfig().getBoolean("Block-Commands.Enable")) {
            return false;
        }

        return !CommandEventConfig.getConfig().getBoolean("Block-Commands.Bypass") || !p.hasPermission("hawn.event.bypass.blockcommands");
    }

    private static boolean isBlocked(Player p, String message) {
        return blockerAppliesTo(p) && matchesBlockList(message);
    }

    /**
     * Compares the command typed with each entry of Block-Commands.List, without case, extra spaces nor arguments:
     * "/pl", "/PL x" and "/pl " match "/pl". "/bukkit:pl" matches too: the "plugin:" prefix of the command is removed.
     * An entry with several words ("/gamemode creative") blocks the commands that start with these words.
     */
    private static boolean matchesBlockList(String message) {
        String typed = normalize(message);
        if (typed.isEmpty()) {
            return false;
        }

        String withoutPrefix = typed;
        int space = typed.indexOf(' ');
        String label = space == -1 ? typed : typed.substring(0, space);
        int colon = label.indexOf(':');
        if (colon != -1) {
            withoutPrefix = "/" + typed.substring(colon + 1);
        }

        for (String entry : CommandEventConfig.getConfig().getStringList("Block-Commands.List")) {
            String blocked = normalize(entry);
            if (blocked.isEmpty()) {
                continue;
            }

            if (startsWithCommand(typed, blocked) || startsWithCommand(withoutPrefix, blocked)) {
                return true;
            }
        }

        return false;
    }

    private static boolean startsWithCommand(String typed, String blocked) {
        return typed.equals(blocked) || typed.startsWith(blocked + " ");
    }

    private static String normalize(String command) {
        String result = command.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
        if (!result.isEmpty() && !result.startsWith("/")) {
            result = "/" + result;
        }
        return result;
    }
}
