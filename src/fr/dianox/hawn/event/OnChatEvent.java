package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.command.commands.DelaychatCommand;
import fr.dianox.hawn.modules.chat.emojis.ChatEmojisLoad;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.config.configs.ConfigGeneral;
import fr.dianox.hawn.utility.config.configs.commands.DelayChatCommandConfig;
import fr.dianox.hawn.utility.config.configs.commands.MuteChatCommandConfig;
import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("deprecation")
public class OnChatEvent implements Listener {

    List <String> cooling = new ArrayList<>();

    @EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent e) {
        if (e.getLastToken().startsWith("@")) {
            ArrayList <String> a = new ArrayList <> ();
            for (Player players: Bukkit.getOnlinePlayers()) {
                a.add("@" + players.getName());
            }
            e.getTabCompletions().clear();
            e.getTabCompletions().addAll(a);
        }
    }

    @SuppressWarnings("rawtypes")
	@EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        final String name = e.getPlayer().getName();
        Player p = e.getPlayer();
        String original = e.getMessage();

        if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
            if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Bypass")) {
                if (!p.hasPermission("hawn.event.chat.bypass.mutechat")) {
                    e.setCancelled(true);
                    for (String msg: ConfigMMsg.getConfig().getStringList("MuteChat.Can-t-Speak")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
                }
            } else {
                e.setCancelled(true);
                for (String msg: ConfigMMsg.getConfig().getStringList("MuteChat.Can-t-Speak")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                }
            }
        }

        if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Enable")) {
            if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Bypass")) {
                if (!p.hasPermission("hawn.event.chat.bypass.chatdelay")) {
                    if (cooling.contains(name)) {
                        e.setCancelled(true);
                        for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Delay")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    } else {
                        cooling.add(name);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> OnChatEvent.this.cooling.remove(name), DelaychatCommand.delay * 20);
                    }
                }
            } else {
                if (cooling.contains(name)) {
                    e.setCancelled(true);
                    for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Delay")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
                } else {
                    cooling.add(name);

                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> OnChatEvent.this.cooling.remove(name), DelaychatCommand.delay * 20);
                }
            }
        }

        if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Enable")) {
            if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Bypass")) {
                if (!p.hasPermission("hawn.bypass.antiswear")) {
	                original = AntiSwear(p, original, e);
                }
            } else {
	            original = AntiSwear(p, original, e);
            }
        }

        if (OnChatConfig.getConfig().getBoolean("Chat-Color-Player.Enable")) {
        	 if (OnChatConfig.getConfig().getBoolean("Chat-Color-Player.Per-Color-Permission")) {
        		 if (original.contains("&c") && p.hasPermission("hawn.use.chatcolor.chat.code.c") ) {
        		 	 original = original.replaceAll("&c", "§c");
        		 }
              	
              	if (original.contains("&e") && p.hasPermission("hawn.use.chatcolor.chat.code.e")) {
              		original = original.replaceAll("&e", "§e");
              	}
              	
              	if (original.contains("&a") && p.hasPermission("hawn.use.chatcolor.chat.code.a")) {
              		original = original.replaceAll("&a", "§a");
              	}
              	
              	if (original.contains("&b") && p.hasPermission("hawn.use.chatcolor.chat.code.b")) {
              		original = original.replaceAll("&b", "§b");
              	}
              	
              	if (original.contains("&3") && p.hasPermission("hawn.use.chatcolor.chat.code.3")) {
              		original = original.replaceAll("&3", "§3");
              	}
              	
              	if (original.contains("&d") && p.hasPermission("hawn.use.chatcolor.chat.code.d")) {
              		original = original.replaceAll("&d", "§d");
              	}
              	
              	if (original.contains("&f") && p.hasPermission("hawn.use.chatcolor.chat.code.f")) {
              		original = original.replaceAll("&f", "§f");
              	}
              	
              	if (original.contains("&7") && p.hasPermission("hawn.use.chatcolor.chat.code.7")) {
              		original = original.replaceAll("&7", "§7");
              	}
              	
              	if (original.contains("&4") && p.hasPermission("hawn.use.chatcolor.chat.code.4")) {
              		original = original.replaceAll("&4", "§4");
             	}
             	
             	if (original.contains("&6") && p.hasPermission("hawn.use.chatcolor.chat.code.6")) {
             		original = original.replaceAll("&6", "§6");
             	}
             	
             	if (original.contains("&2") && p.hasPermission("hawn.use.chatcolor.chat.code.2")) {
             		original = original.replaceAll("&2", "§2");
             	}
             	
             	if (original.contains("&1") && p.hasPermission("hawn.use.chatcolor.chat.code.1")) {
             		original = original.replaceAll("&1", "§1");
             	}
             	
             	if (original.contains("&9") && p.hasPermission("hawn.use.chatcolor.chat.code.9")) {
             		original = original.replaceAll("&9", "§9");
             	}
             	
             	if (original.contains("&5") && p.hasPermission("hawn.use.chatcolor.chat.code.5")) {
             		original = original.replaceAll("&5", "§5");
             	}
             	
             	if (original.contains("&8") && p.hasPermission("hawn.use.chatcolor.chat.code.8")) {
             		original = original.replaceAll("&8", "§8");
             	}
             	
             	if (original.contains("&0") && p.hasPermission("hawn.use.chatcolor.chat.code.0")) {
             		original = original.replaceAll("&0", "§0");
             	}
             	
             	if (original.contains("&l") && p.hasPermission("hawn.use.chatcolor.chat.code.l")) {
             		original = original.replaceAll("&l", "§l");
             	}
             	
             	if (original.contains("&m") && p.hasPermission("hawn.use.chatcolor.chat.code.m")) {
             		original = original.replaceAll("&m", "§m");
             	}
             	
             	if (original.contains("&n") && p.hasPermission("hawn.use.chatcolor.chat.code.n")) {
             		original = original.replaceAll("&n", "§n");
             	}
             	
             	if (original.contains("&o") && p.hasPermission("hawn.use.chatcolor.chat.code.o")) {
             		original = original.replaceAll("&o", "§o");
             	}
             	
             	if (original.contains("&r") && p.hasPermission("hawn.use.chatcolor.chat.code.r")) {
             		original = original.replaceAll("&r", "§r");
             	}
             	
             	if (original.contains("&k") && p.hasPermission("hawn.use.chatcolor.chat.code.k")) {
             		original = original.replaceAll("&k", "§k");
             	}
        	 } else {
        		 if (p.hasPermission("hawn.use.chatcolor.chat.basic.light")) {
                 	if (original.contains("&c")) {
                 		original = original.replaceAll("&c", "§c");
                 	}
                 	
                 	if (original.contains("&e")) {
                 		original = original.replaceAll("&e", "§e");
                 	}
                 	
                 	if (original.contains("&a")) {
                 		original = original.replaceAll("&a", "§a");
                 	}
                 	
                 	if (original.contains("&b")) {
                 		original = original.replaceAll("&b", "§b");
                 	}
                 	
                 	if (original.contains("&3")) {
                 		original = original.replaceAll("&3", "§3");
                 	}
                 	
                 	if (original.contains("&d")) {
                 		original = original.replaceAll("&d", "§d");
                 	}
                 	
                 	if (original.contains("&f")) {
                 		original = original.replaceAll("&f", "§f");
                 	}
                 	
                 	if (original.contains("&7")) {
                 		original = original.replaceAll("&7", "§7");
                 	}
                 }
        		 
        		 if (p.hasPermission("hawn.use.chatcolor.chat.basic.dark")) {
                 	if (original.contains("&4")) {
                 		original = original.replaceAll("&4", "§4");
                 	}
                 	
                 	if (original.contains("&6")) {
                 		original = original.replaceAll("&6", "§6");
                 	}
                 	
                 	if (original.contains("&2")) {
                 		original = original.replaceAll("&2", "§2");
                 	}
                 	
                 	if (original.contains("&1")) {
                 		original = original.replaceAll("&1", "§1");
                 	}
                 	
                 	if (original.contains("&9")) {
                 		original = original.replaceAll("&9", "§9");
                 	}
                 	
                 	if (original.contains("&5")) {
                 		original = original.replaceAll("&5", "§5");
                 	}
                 	
                 	if (original.contains("&8")) {
                 		original = original.replaceAll("&8", "§8");
                 	}
                 	
                 	if (original.contains("&0")) {
                 		original = original.replaceAll("&0", "§0");
                 	}
                 }
        		 
        		 if (p.hasPermission("hawn.use.chatcolor.chat.special.format")) {
                 	if (original.contains("&l")) {
                 		original = original.replaceAll("&l", "§l");
                 	}
                 	
                 	if (original.contains("&m")) {
                 		original = original.replaceAll("&m", "§m");
                 	}
                 	
                 	if (original.contains("&n")) {
                 		original = original.replaceAll("&n", "§n");
                 	}
                 	
                 	if (original.contains("&o")) {
                 		original = original.replaceAll("&o", "§o");
                 	}
                 	
                 	if (original.contains("&r")) {
                 		original = original.replaceAll("&r", "§r");
                 	}
                 }
                 
                 if (p.hasPermission("hawn.use.chatcolor.chat.special.magic") && original.contains("&k")) {
                 	original = original.replaceAll("&k", "§k");
                 }
        	 }
        }

        if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Enable") && p.hasPermission("hawn.chat.emoji")) {
	        for (Map.Entry<String, String> stringStringEntry : ChatEmojisLoad.emojislist.entrySet()) {
		        String check = String.valueOf(((Map.Entry) stringStringEntry).getKey());
		        String value = String.valueOf(((Map.Entry) stringStringEntry).getValue());

		        if (ChatEmojisLoad.emojislistperm.containsKey(check)) {
			        if (! p.hasPermission(ChatEmojisLoad.emojislistperm.get(check))) {
				        continue;
			        }
		        }

		        if (e.getMessage().toLowerCase().contains(check.toLowerCase())) {
			        original = original.replaceAll(check, value);
		        }
	        }
		}

        if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Enable")) {
            boolean disable = false;

            if (p.hasPermission("hawn.chat.can.mention") && original.contains("@")) {
            	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
            		if (original.contains("@" + all.getName())) {
            			if (original.contains("@" + p.getName()) && !OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Self-Mention.Enable")) {
            				p.sendMessage(String.format(e.getFormat(), p.getDisplayName(), original));
            				continue;
            			}

            			Mentionned(all, p);
            			if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Chat-Highlight.Enable")) {
            				String msgadd;
            				String highlights = OnChatConfig.getConfig().getString("Chat-Mention.Mentionned.Chat-Highlight.Highlighting");
            				highlights = MessageUtils.colourTheStuff(highlights);
            				msgadd = original.replaceAll("@" + all.getName(), highlights + "@" + all.getName() + "§r");
            				all.sendMessage(String.format(e.getFormat(), p.getDisplayName(), msgadd));
            			}
            		} else {
            			if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Chat-Highlight.Enable")) {
            				all.sendMessage(String.format(e.getFormat(), p.getDisplayName(), original));
            			}
            		}
            	}

            	if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Chat-Highlight.Enable")) {
            		Bukkit.getLogger().log(Level.INFO, String.format(e.getFormat(), p.getDisplayName(), e.getMessage()));
            		disable = true;
            	}
            }

            if (disable) {
                e.setCancelled(true);
            }
        }

        e.setMessage(original);
    }

    private String AntiSwear(Player p, String original, AsyncPlayerChatEvent e) {
	    for (String i: OnChatConfig.getConfig().getStringList("Anti-Swear.List")) {
		    if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
			    if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Notify-Staff")) {
				    for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
					    if (p1.hasPermission("hawn.antiswear.benotified")) {
						    for (String msg: ConfigMMsg.getConfig().getStringList("Anti-Swear.Notify-Staff")) {
							    String message = msg.replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage());
							    ConfigEventUtils.ExecuteEvent(p1, message, "", "", false);
						    }
					    }

				    }
			    }

			    if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Replace-Message.Enable")) {
				    original = original.replaceAll(i, OnChatConfig.getConfig().getString("Anti-Swear.Replace-Message.Message").replace("[", "").replace("]", ""));
			    }
		    }
	    }

	    return original;
    }

    private void Mentionned(Player p, Player sender) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            // send Message
            if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Send-Message.Enable")) {
                for (String msg: OnChatConfig.getConfig().getStringList("Chat-Mention.Mentionned.Send-Message.Messages")) {
                    ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%sender%", sender.getName()).replaceAll("%player%", p.getName()), "", "", false);
                }
            }

            // send Action bar
            if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Send-ActionBar.Enable")) {
                String actionbar = OnChatConfig.getConfig().getString("Chat-Mention.Mentionned.Send-ActionBar.Options.Message");
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                    actionbar = PlaceholderAPI.setPlaceholders(p, actionbar);
                }

                actionbar = MessageUtils.colourTheStuff(actionbar);
                actionbar = actionbar.replaceAll("%sender%", sender.getName()).replaceAll("%player%", p.getName());
                actionbar = PlaceHolders.ReplaceMainplaceholderP(actionbar, p);
                ActionBar.sendActionBar(Main.getInstance(), p, actionbar, OnChatConfig.getConfig().getInt("Chat-Mention.Mentionned.Send-ActionBar.Options.Time-Stay"));
            }

            // send title bar
            if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Send-Title.Enable")) {
                String title;
                String subtitle;

                if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Send-Title.Options.Enable")) {
                    title = OnChatConfig.getConfig().getString("Chat-Mention.Mentionned.Send-Title.Options.Title");

                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                        title = PlaceholderAPI.setPlaceholders(p, title);
                    }

                    title = MessageUtils.colourTheStuff(title);
                    title = title.replaceAll("%sender%", sender.getName()).replaceAll("%player%", p.getName());
                    title = PlaceHolders.ReplaceMainplaceholderP(title, p);

                    subtitle = OnChatConfig.getConfig().getString("Chat-Mention.Mentionned.Send-Title.Options.SubTitle");

                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                        subtitle = PlaceholderAPI.setPlaceholders(p, subtitle);
                    }

                    subtitle = MessageUtils.colourTheStuff(subtitle);
                    subtitle = subtitle.replaceAll("%sender%", sender.getName()).replaceAll("%player%", p.getName());
                    subtitle = PlaceHolders.ReplaceMainplaceholderP(subtitle, p);

                    Titles.sendTitle(p, OnChatConfig.getConfig().getInt("Chat-Mention.Mentionned.Send-Title.Options.FadeIn"),
		                    OnChatConfig.getConfig().getInt("Chat-Mention.Mentionned.Send-Title.Options.Stay"),
		                    OnChatConfig.getConfig().getInt("Chat-Mention.Mentionned.Send-Title.Options.FadeOut"), title, subtitle);
                }
            }

            // sound mention
            if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Sound.Enable")) {
                String sound = OnChatConfig.getConfig().getString("Chat-Mention.Mentionned.Sound.Sound");
                int volume = OnChatConfig.getConfig().getInt("Chat-Mention.Mentionned.Sound.Volume");
                int pitch = OnChatConfig.getConfig().getInt("Chat-Mention.Mentionned.Sounds.Pitch");
                p.playSound(p.getLocation(), XSound.getSound(sound, "Chat-Mention.Mentionned.Sound.Sound"), volume, pitch);
            }
        }, 10);
    }

}