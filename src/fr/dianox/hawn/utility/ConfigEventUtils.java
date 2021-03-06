package fr.dianox.hawn.utility;

import fr.dianox.hawn.Main;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import fr.dianox.hawn.command.commands.WarpCommand;
import fr.dianox.hawn.event.OnCommandEvent;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;


public class ConfigEventUtils {
	
	public static void ExecuteEvent(Player p, String event, String Informatif, String AdditionalMessageError, Boolean Console) {
		String perm;
        String world;
        
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
	        Main.getInstance().getBungApi().ConnectOthers(event, p);
        } else if (event.startsWith("[effect[")) {
            event = event.replace("[effect[", "");

            String[] parts = event.split("]]: ");

            p.addPotionEffect(new PotionEffect(XPotion.getMat(parts[1], AdditionalMessageError), 1999999999, Integer.parseInt(parts[0])));
        } else if (event.startsWith("[effectclear]: ")) {
            event = event.replace("[effectclear]: ", "");
            
            p.removePotionEffect(XPotion.getMat(event, AdditionalMessageError));
        } else if (event.startsWith("[effectclearall]")) {
            for (PotionEffect effect: p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
        } else if (event.startsWith("[send-title]: ")) {
            event = event.replace("[send-title]: ", "");
            event = MessageUtils.colourTheStuff(event);

            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }
            
            boolean activate = false;

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

	        if (activate) {
		        Titles.sendTitle(p, 20, 150, 75, title, subtitle);
	        } else {
		        Titles.sendTitle(p, 20, 150, 75, event, " ");
	        }
        } else if (event.startsWith("[send-title[")) {
            event = event.replace("[send-title[", "");
            event = MessageUtils.colourTheStuff(event);
            
            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }
            
            String[] parts = event.split("]]: ");

            boolean activate = false;

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

	        if (activate) {
		        Titles.sendTitle(p, 20, Integer.parseInt(parts[0]), 75, title, subtitle);
	        } else {
		        Titles.sendTitle(p, 20, Integer.parseInt(parts[0]), 75, parts[1], " ");
	        }
        } else if (event.startsWith("[send-actionbar]: ")) {
            event = event.replace("[send-actionbar]: ", "");
            event = MessageUtils.colourTheStuff(event);
            
            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }

            ActionBar.sendActionBar(p, event);
        } else if (event.startsWith("[send-actionbar[")) {
            event = event.replace("[send-actionbar[", "");
            event = MessageUtils.colourTheStuff(event);
            
            event = PlaceHolders.ReplaceMainplaceholderP(event, p);
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
            	event = PlaceholderAPI.setPlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
            	event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(p, event);
            }
            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
            	event = PlaceHolders.BattleLevelPO(event, p);
            }
            
            String[] parts = event.split("]]: ");

            ActionBar.sendActionBar(Main.getInstance(), p, parts[1], Integer.parseInt(parts[0]));
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
        		if (Informatif.equals("AUTOCENTER")) {
		            event = "<--center--> " + event;
		        }

        		MessageUtils.ClassicMessages(event, p);
        	}
        }
	}

	public static void ExecuteEventTargetPlaceHolder(Player p, Player target, String event, String Informatif, String AdditionalMessageError, Boolean Console) {
		String perm;
		String world;

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
			Main.getInstance().getBungApi().ConnectOthers(event, p);
		} else if (event.startsWith("[effect[")) {
			event = event.replace("[effect[", "");

			String[] parts = event.split("]]: ");

			p.addPotionEffect(new PotionEffect(XPotion.getMat(parts[1], AdditionalMessageError), 1999999999, Integer.parseInt(parts[0])));
		} else if (event.startsWith("[effectclear]: ")) {
			event = event.replace("[effectclear]: ", "");

			p.removePotionEffect(XPotion.getMat(event, AdditionalMessageError));
		} else if (event.startsWith("[effectclearall]")) {
			for (PotionEffect effect: p.getActivePotionEffects()) {
				p.removePotionEffect(effect.getType());
			}
		} else if (event.startsWith("[send-title]: ")) {
			event = event.replace("[send-title]: ", "");
			event = MessageUtils.colourTheStuff(event);

			event = PlaceHolders.ReplaceMainplaceholderP(event, target);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				event = PlaceholderAPI.setPlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
				event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				event = PlaceHolders.BattleLevelPO(event, target);
			}

			boolean activate = false;

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

			if (activate) {
				Titles.sendTitle(p, 20, 150, 75, title, subtitle);
			} else {
				Titles.sendTitle(p, 20, 150, 75, event, " ");
			}
		} else if (event.startsWith("[send-title[")) {
			event = event.replace("[send-title[", "");
			event = MessageUtils.colourTheStuff(event);

			event = PlaceHolders.ReplaceMainplaceholderP(event, target);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				event = PlaceholderAPI.setPlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
				event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				event = PlaceHolders.BattleLevelPO(event, target);
			}

			String[] parts = event.split("]]: ");

			boolean activate = false;

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

			if (activate) {
				Titles.sendTitle(p, 20, Integer.parseInt(parts[0]), 75, title, subtitle);
			} else {
				Titles.sendTitle(p, 20, Integer.parseInt(parts[0]), 75, parts[1], " ");
			}
		} else if (event.startsWith("[send-actionbar]: ")) {
			event = event.replace("[send-actionbar]: ", "");
			event = MessageUtils.colourTheStuff(event);

			event = PlaceHolders.ReplaceMainplaceholderP(event, target);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				event = PlaceholderAPI.setPlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
				event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				event = PlaceHolders.BattleLevelPO(event, target);
			}

			ActionBar.sendActionBar(p, event);
		} else if (event.startsWith("[send-actionbar[")) {
			event = event.replace("[send-actionbar[", "");
			event = MessageUtils.colourTheStuff(event);

			event = PlaceHolders.ReplaceMainplaceholderP(event, target);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				event = PlaceholderAPI.setPlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
				event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				event = PlaceHolders.BattleLevelPO(event, target);
			}

			String[] parts = event.split("]]: ");

			ActionBar.sendActionBar(Main.getInstance(), p, parts[1], Integer.parseInt(parts[0]));
		} else if (event.startsWith("[sounds]: ")) {
			event = event.replace("[sounds]: ", "");
			p.playSound(p.getLocation(), XSound.getSound(event, AdditionalMessageError), 1, 1);
		} else {
			event = PlaceHolders.ReplaceMainplaceholderP(event, target);
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
				event = PlaceholderAPI.setPlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.MVdWPlaceholderAPI.Enable")) {
				event = be.maximvdw.placeholderapi.PlaceholderAPI.replacePlaceholders(target, event);
			}
			if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.BattleLevels.Enable")) {
				event = PlaceHolders.BattleLevelPO(event, target);
			}

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

	public static void ExecuteEventAllPlayers(String event, String Informatif, String AdditionalMessageError, Player target, Boolean targetbool) {
		if (targetbool) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				ExecuteEventTargetPlaceHolder(p, target, event, Informatif, AdditionalMessageError, false);
			}
		} else {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				ExecuteEvent(p, event, Informatif, AdditionalMessageError, false);
			}
		}
	}

	public static void ExecuteEventAllPlayersConsole(String event, String Informatif, String AdditionalMessageError) {
		for (Player p : Bukkit.getServer().getOnlinePlayers()) {
			ExecuteEvent(p, event, Informatif, AdditionalMessageError, false);
		}
	}
	
}
