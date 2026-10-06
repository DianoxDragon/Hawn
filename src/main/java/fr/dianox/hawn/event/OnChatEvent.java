package fr.dianox.hawn.event;

import fr.dianox.hawn.utility.XParse;

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
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public class OnChatEvent implements Listener {

    List <String> cooling = new ArrayList<>();

    @SuppressWarnings("rawtypes")
	// HIGH and ignoreCancelled: the mutes of the other plugins (LiteBans...) are already applied,
	// otherwise the mentions below would send the message of a muted player
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent e) {
        final String name = e.getPlayer().getName();
        Player p = e.getPlayer();
        String original = e.getMessage();

        if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
            if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Bypass")) {
                if (!p.hasPermission("hawn.event.chat.bypass.mutechat")) {
                    e.setCancelled(true);
                    for (String msg: ConfigMMsg.getConfig().getStringList("MuteChat.Can-t-Speak")) {
                        runSync(() -> ConfigEventUtils.ExecuteEvent(p, msg, "", "", false));
                    }
                    return;
                }
            } else {
                e.setCancelled(true);
                for (String msg: ConfigMMsg.getConfig().getStringList("MuteChat.Can-t-Speak")) {
                    runSync(() -> ConfigEventUtils.ExecuteEvent(p, msg, "", "", false));
                }
                return;
            }
        }

        if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Enable")) {
            if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Bypass")) {
                if (!p.hasPermission("hawn.event.chat.bypass.chatdelay")) {
                    if (cooling.contains(name)) {
                        e.setCancelled(true);
                        for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Delay")) {
                            runSync(() -> ConfigEventUtils.ExecuteEvent(p, msg, "", "", false));
                        }
                        return;
                    } else {
                        cooling.add(name);

                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> OnChatEvent.this.cooling.remove(name), DelaychatCommand.delay * 20);
                    }
                }
            } else {
                if (cooling.contains(name)) {
                    e.setCancelled(true);
                    for (String msg: ConfigMMsg.getConfig().getStringList("ChatDelay.Delay")) {
                        runSync(() -> ConfigEventUtils.ExecuteEvent(p, msg, "", "", false));
                    }
                    return;
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
        		 	 original = original.replace("&c", "§c");
        		 }
              	
              	if (original.contains("&e") && p.hasPermission("hawn.use.chatcolor.chat.code.e")) {
              		original = original.replace("&e", "§e");
              	}
              	
              	if (original.contains("&a") && p.hasPermission("hawn.use.chatcolor.chat.code.a")) {
              		original = original.replace("&a", "§a");
              	}
              	
              	if (original.contains("&b") && p.hasPermission("hawn.use.chatcolor.chat.code.b")) {
              		original = original.replace("&b", "§b");
              	}
              	
              	if (original.contains("&3") && p.hasPermission("hawn.use.chatcolor.chat.code.3")) {
              		original = original.replace("&3", "§3");
              	}
              	
              	if (original.contains("&d") && p.hasPermission("hawn.use.chatcolor.chat.code.d")) {
              		original = original.replace("&d", "§d");
              	}
              	
              	if (original.contains("&f") && p.hasPermission("hawn.use.chatcolor.chat.code.f")) {
              		original = original.replace("&f", "§f");
              	}
              	
              	if (original.contains("&7") && p.hasPermission("hawn.use.chatcolor.chat.code.7")) {
              		original = original.replace("&7", "§7");
              	}
              	
              	if (original.contains("&4") && p.hasPermission("hawn.use.chatcolor.chat.code.4")) {
              		original = original.replace("&4", "§4");
             	}
             	
             	if (original.contains("&6") && p.hasPermission("hawn.use.chatcolor.chat.code.6")) {
             		original = original.replace("&6", "§6");
             	}
             	
             	if (original.contains("&2") && p.hasPermission("hawn.use.chatcolor.chat.code.2")) {
             		original = original.replace("&2", "§2");
             	}
             	
             	if (original.contains("&1") && p.hasPermission("hawn.use.chatcolor.chat.code.1")) {
             		original = original.replace("&1", "§1");
             	}
             	
             	if (original.contains("&9") && p.hasPermission("hawn.use.chatcolor.chat.code.9")) {
             		original = original.replace("&9", "§9");
             	}
             	
             	if (original.contains("&5") && p.hasPermission("hawn.use.chatcolor.chat.code.5")) {
             		original = original.replace("&5", "§5");
             	}
             	
             	if (original.contains("&8") && p.hasPermission("hawn.use.chatcolor.chat.code.8")) {
             		original = original.replace("&8", "§8");
             	}
             	
             	if (original.contains("&0") && p.hasPermission("hawn.use.chatcolor.chat.code.0")) {
             		original = original.replace("&0", "§0");
             	}
             	
             	if (original.contains("&l") && p.hasPermission("hawn.use.chatcolor.chat.code.l")) {
             		original = original.replace("&l", "§l");
             	}
             	
             	if (original.contains("&m") && p.hasPermission("hawn.use.chatcolor.chat.code.m")) {
             		original = original.replace("&m", "§m");
             	}
             	
             	if (original.contains("&n") && p.hasPermission("hawn.use.chatcolor.chat.code.n")) {
             		original = original.replace("&n", "§n");
             	}
             	
             	if (original.contains("&o") && p.hasPermission("hawn.use.chatcolor.chat.code.o")) {
             		original = original.replace("&o", "§o");
             	}
             	
             	if (original.contains("&r") && p.hasPermission("hawn.use.chatcolor.chat.code.r")) {
             		original = original.replace("&r", "§r");
             	}
             	
             	if (original.contains("&k") && p.hasPermission("hawn.use.chatcolor.chat.code.k")) {
             		original = original.replace("&k", "§k");
             	}
        	 } else {
        		 if (p.hasPermission("hawn.use.chatcolor.chat.basic.light")) {
                 	if (original.contains("&c")) {
                 		original = original.replace("&c", "§c");
                 	}
                 	
                 	if (original.contains("&e")) {
                 		original = original.replace("&e", "§e");
                 	}
                 	
                 	if (original.contains("&a")) {
                 		original = original.replace("&a", "§a");
                 	}
                 	
                 	if (original.contains("&b")) {
                 		original = original.replace("&b", "§b");
                 	}
                 	
                 	if (original.contains("&3")) {
                 		original = original.replace("&3", "§3");
                 	}
                 	
                 	if (original.contains("&d")) {
                 		original = original.replace("&d", "§d");
                 	}
                 	
                 	if (original.contains("&f")) {
                 		original = original.replace("&f", "§f");
                 	}
                 	
                 	if (original.contains("&7")) {
                 		original = original.replace("&7", "§7");
                 	}
                 }
        		 
        		 if (p.hasPermission("hawn.use.chatcolor.chat.basic.dark")) {
                 	if (original.contains("&4")) {
                 		original = original.replace("&4", "§4");
                 	}
                 	
                 	if (original.contains("&6")) {
                 		original = original.replace("&6", "§6");
                 	}
                 	
                 	if (original.contains("&2")) {
                 		original = original.replace("&2", "§2");
                 	}
                 	
                 	if (original.contains("&1")) {
                 		original = original.replace("&1", "§1");
                 	}
                 	
                 	if (original.contains("&9")) {
                 		original = original.replace("&9", "§9");
                 	}
                 	
                 	if (original.contains("&5")) {
                 		original = original.replace("&5", "§5");
                 	}
                 	
                 	if (original.contains("&8")) {
                 		original = original.replace("&8", "§8");
                 	}
                 	
                 	if (original.contains("&0")) {
                 		original = original.replace("&0", "§0");
                 	}
                 }
        		 
        		 if (p.hasPermission("hawn.use.chatcolor.chat.special.format")) {
                 	if (original.contains("&l")) {
                 		original = original.replace("&l", "§l");
                 	}
                 	
                 	if (original.contains("&m")) {
                 		original = original.replace("&m", "§m");
                 	}
                 	
                 	if (original.contains("&n")) {
                 		original = original.replace("&n", "§n");
                 	}
                 	
                 	if (original.contains("&o")) {
                 		original = original.replace("&o", "§o");
                 	}
                 	
                 	if (original.contains("&r")) {
                 		original = original.replace("&r", "§r");
                 	}
                 }
                 
                 if (p.hasPermission("hawn.use.chatcolor.chat.special.magic") && original.contains("&k")) {
                 	original = original.replace("&k", "§k");
                 }
        	 }
        }

        // Hex colours: &#RRGGBB or #<RRGGBB>
        if (OnChatConfig.getConfig().getBoolean("Chat-Color-Player.Enable") && p.hasPermission("hawn.use.chatcolor.chat.hex")) {
            original = MessageUtils.colourHex(original);
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
			        original = original.replaceAll("(?i)" + Pattern.quote(check), Matcher.quoteReplacement(value));
		        }
	        }
		}

        if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Enable")) {
            boolean disable = false;

            if (p.hasPermission("hawn.chat.can.mention") && original.contains("@")) {
            	for (Player all: new ArrayList<>(e.getRecipients())) {
            		if (original.contains("@" + all.getName())) {
            			if (original.contains("@" + p.getName()) && !OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Self-Mention.Enable")) {
            				p.sendMessage(String.format(e.getFormat(), p.getDisplayName(), original));
            				continue;
            			}

            			runSync(() -> Mentionned(all, p));
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
							    String message = msg.replace("%player%", p.getName()).replace("%message%", ConfigEventUtils.noAction(e.getMessage()));
							    runSync(() -> ConfigEventUtils.ExecuteEvent(p1, message, "", "", false));
						    }
					    }

				    }
			    }

			    if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Replace-Message.Enable")) {
				    original = original.replaceAll("(?i)" + Pattern.quote(i), Matcher.quoteReplacement(OnChatConfig.getConfig().getString("Anti-Swear.Replace-Message.Message").replace("[", "").replace("]", "")));
			    }
		    }
	    }

	    return original;
    }

    // The chat runs outside the main thread: the actions (commands, sounds, titles...) go back to it
    private static void runSync(Runnable task) {
        if (Bukkit.isPrimaryThread()) {
            task.run();
        } else {
            Bukkit.getScheduler().runTask(Main.getInstance(), task);
        }
    }

    private void Mentionned(Player p, Player sender) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
            // send Message
            if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Send-Message.Enable")) {
                for (String msg: OnChatConfig.getConfig().getStringList("Chat-Mention.Mentionned.Send-Message.Messages")) {
                    ConfigEventUtils.ExecuteEvent(p, msg.replace("%sender%", sender.getName()).replace("%player%", p.getName()), "", "", false);
                }
            }

            // send Action bar
            if (OnChatConfig.getConfig().getBoolean("Chat-Mention.Mentionned.Send-ActionBar.Enable")) {
                String actionbar = OnChatConfig.getConfig().getString("Chat-Mention.Mentionned.Send-ActionBar.Options.Message");
                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                    actionbar = PlaceholderAPI.setPlaceholders(p, actionbar);
                }

                actionbar = MessageUtils.colourTheStuff(actionbar);
                actionbar = actionbar.replace("%sender%", sender.getName()).replace("%player%", p.getName());
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
                    title = title.replace("%sender%", sender.getName()).replace("%player%", p.getName());
                    title = PlaceHolders.ReplaceMainplaceholderP(title, p);

                    subtitle = OnChatConfig.getConfig().getString("Chat-Mention.Mentionned.Send-Title.Options.SubTitle");

                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.Hook.PlaceholderAPI.Enable")) {
                        subtitle = PlaceholderAPI.setPlaceholders(p, subtitle);
                    }

                    subtitle = MessageUtils.colourTheStuff(subtitle);
                    subtitle = subtitle.replace("%sender%", sender.getName()).replace("%player%", p.getName());
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
                int pitch = OnChatConfig.getConfig().getInt("Chat-Mention.Mentionned.Sound.Pitch");
                p.playSound(p.getLocation(), XParse.sound(sound, "Chat-Mention.Mentionned.Sound.Sound"), volume, pitch);
            }
        }, 10);
    }

}