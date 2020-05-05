package fr.dianox.hawn.utility;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fr.dianox.hawn.commands.WarpCommand;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;


public class ConfigEventUtils {
	
	public static void ExecuteEvent(Player p, String event, String Informatif, String AdditionalMessageError, Boolean Console) {
		String perm = "";
        String world = "";
        
        if (event.startsWith("<world>") && event.contains("</world>")) {
        	world = StringUtils.substringBetween(event, "<world>", "</world>");
        	event = event.replace("<world>" + world + "</world> ", "");

            if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                return;
            }
        }
        
        if (event.contains("<perm>") && event.contains("</perm>")) {
            perm = StringUtils.substringBetween(event, "<perm>", "</perm>");
            event = event.replace("<perm>" + perm + "</perm> ", "");

            if (!p.hasPermission(perm)) {
            	return;
            }
        }
        
        if (event.startsWith("[command-player]: ")) {
            event = event.replace("[command-player]: ", "");
            event = event.replaceAll("%player%", p.getName());

            p.performCommand(event);
        } else if (event.startsWith("[FWLU]: ")) {
        	if (event.startsWith("[FWLU]: ")) {
				
        		event = event.replace("[FWLU]: ", "");
				
				OtherUtils.Fireworkmethod(p, event);
			}
        } else if (event.startsWith("[command-console]: ")) {
            event = event.replace("[command-console]: ", "");
            event = event.replaceAll("%player%", p.getName());

            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), event);
        } else if (event.startsWith("[gamemode-survival]")) {
            p.setGameMode(GameMode.SURVIVAL);
        } else if (event.startsWith("[gamemode-creative]")) {
            p.setGameMode(GameMode.CREATIVE);
        } else if (event.startsWith("[gamemode-adventure]")) {
            p.setGameMode(GameMode.ADVENTURE);
        } else if (event.startsWith("[gamemode-spectator]")) {
            p.setGameMode(GameMode.SPECTATOR);
        } else if (event.startsWith("[customcommand-player]: ")) {
        	event = event.replace("[customcommand-player]: ", "");
        	event = event.replaceAll("%player%", p.getName());
            
        	OnCommandEvent.executeCustomCommand(event, p);
        } else if (event.startsWith("[ping]")) {
            for (String event1: ConfigMMsg.getConfig().getStringList("Ping.Self")) {
                MessageUtils.ClassicMessages(event1, p);
            }
        } else if (event.startsWith("[spawn]: ")) {
            event = event.replace("[spawn]: ", "");
            SpawnUtils.teleportToSpawn(p, event);
        } else if (event.startsWith("[warp]: ")) {
            event = event.replace("[warp]: ", "");
            WarpCommand.onTp(p, event);
        } else if (event.startsWith("[bungee]: ")) {
            event = event.replace("[bungee]: ", "");
            Bungee.changeServer(p, event);
        } else if (event.startsWith("[effect[")) {
            event = event.replace("[effect[", "");

            String[] parts = event.split("]]: ");

            p.addPotionEffect(new PotionEffect(XPotion.getMat(parts[1], AdditionalMessageError), 1999999999, Integer.valueOf(parts[0])));
        } else if (event.startsWith("[effectclear]: ")) {
            event = event.replace("[effectclear]: ", "");
            
            p.removePotionEffect(XPotion.getMat(event, AdditionalMessageError));
        } else if (event.startsWith("[effectclearall]")) {
            for (PotionEffect effect: p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
        } else if (event.startsWith("[send-title]: ")) {
            event = event.replace("[send-title]: ", "");
            event = event.replaceAll("&", "§");

            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }
            
            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (event.contains("//n")) {
                String[] parts = event.split("//n");
                title = parts[0];
                subtitle = parts[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
            	Titles.sendTitle(p, 20, 150, 75, event, " ");
            } else {
                Titles.sendTitle(p, 20, 150, 75, title, subtitle);
            }
        } else if (event.startsWith("[send-title[")) {
            event = event.replace("[send-title[", "");
            event = event.replaceAll("&", "§");
            
            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }
            
            String[] parts = event.split("]]: ");

            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (event.contains("//n")) {
                String[] part = parts[1].split("//n");
                title = part[0];
                subtitle = part[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
            	Titles.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1], " ");
            } else {
            	Titles.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title, subtitle);
            }
        } else if (event.startsWith("[send-actionbar]: ")) {
            event = event.replace("[send-actionbar]: ", "");
            event = event.replaceAll("&", "§");
            
            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }

            ActionBar.sendActionBar(p, event);
        } else if (event.startsWith("[send-actionbar[")) {
            event = event.replace("[send-actionbar[", "");
            event = event.replaceAll("&", "§");
            
            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }
            
            String[] parts = event.split("]]: ");

            ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
        } else if (event.startsWith("[sounds]: ")) {
            event = event.replace("[sounds]: ", "");
            p.playSound(p.getLocation(), XSound.getSound(event, AdditionalMessageError), 1, 1);
        } else {
        	if (Console) {
        		if (AdditionalMessageError.contains("STRICTSCONSOLE")) {
        			MessageUtils.ConsoleMessages(event);
        		} else {
        			MessageUtils.ClassicMessagesConsoleSupport(event, p);
        		}
        	} else {
        		MessageUtils.ClassicMessages(event, p);
        	}
        }
	}

	public static void ExecuteEventAllPlayers(String event, String Informatif, String AdditionalMessageError) {
		for (Player p: Bukkit.getServer().getOnlinePlayers()) {
			ExecuteEvent(p, event, Informatif, AdditionalMessageError, false);
		}
	}
	
}
