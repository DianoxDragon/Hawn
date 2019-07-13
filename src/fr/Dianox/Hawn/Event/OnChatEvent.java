package fr.Dianox.Hawn.Event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Commands.Features.Chat.DelaychatCommand;
import fr.Dianox.Hawn.Utility.EmojiesUtility;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.DelayChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.MuteChatCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.OnChatConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMEvents;

public class OnChatEvent implements Listener {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	List<String> cooling = new ArrayList();
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		final String name = e.getPlayer().getName();
		Player p = e.getPlayer();
		String original = e.getMessage();
				
		if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Enable")) {
			if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Bypass")) {
				if (!p.hasPermission("hawn.bypass.antiswear")) {
					for (String i : OnChatConfig.getConfig().getStringList("Anti-Swear.List")) {
						if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
							if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Notify-Staff")) {
								for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
									if (p1.hasPermission("hawn.antiswear.benotified")) {
										for (String msg: ConfigMEvents.getConfig().getStringList("Anti-Swear.Notify-Staff")) {
											String message = msg.replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage());
											MessageUtils.ReplaceCharMessagePlayer(message, p1);
										}
									}
									
								}
							}
							
							if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Replace-Message.Enable")) {
								original = original.replaceAll(i, OnChatConfig.getConfig().getString("Anti-Swear.Replace-Message.Message").replace("[", "").replace("]", ""));
							}
						}
					}
				}
			} else {
				for (String i : OnChatConfig.getConfig().getStringList("Anti-Swear.List")) {
					if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
						if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Notify-Staff")) {
							for (Player p1: Bukkit.getServer().getOnlinePlayers()) {
								if (p1.hasPermission("hawn.antiswear.benotified")) {
									for (String msg: ConfigMEvents.getConfig().getStringList("Anti-Swear.Notify-Staff")) {
										String message = msg.replaceAll("%player%", p.getName()).replaceAll("%message%", e.getMessage());
										MessageUtils.ReplaceCharMessagePlayer(message, p1);
									}
								}
								
							}
						}
						
						if (OnChatConfig.getConfig().getBoolean("Anti-Swear.Replace-Message.Enable")) {
							original = original.replaceAll(i, OnChatConfig.getConfig().getString("Anti-Swear.Replace-Message.Message").replace("[", "").replace("]", ""));
						}
					}
				}
			}
		}
		
		if (OnChatConfig.getConfig().getBoolean("Chat-Color-Player.Enable")) {
			if (p.hasPermission("hawn.use.chatcolor.chat")) {
				original = ChatColor.translateAlternateColorCodes('&', original);
			}
		}
		
		if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Enable")) {
			if (p.hasPermission("hawn.use.emoji.chat")) {
				// Smileys
				for (String i : EmojiesUtility.smiley_list) {
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list.Smiley.Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list.Smiley.Use_Permission")) {
							if (p.hasPermission("hawn.useemoji.Smiley")) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.smiley);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.smiley);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.sad_list) {
					String emoji = "Sad";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.sad);
								}
			    			}
			    		} else {
			    			if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
		    					original = original.replaceAll(i, EmojiesUtility.sad);
							}
		    			}
		    		}
				}
				
				// ITEMS
				for (String i : EmojiesUtility.email_list) {
					String emoji = "Email";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.email);
								}
			    			}
			    		} else {
			    			if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
		    					original = original.replaceAll(i, EmojiesUtility.email);
							}
		    			}
		    		}
				}
				
				for (String i : EmojiesUtility.swords_list) {
					String emoji = "Swords";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.swords);
								}
			    			}
			    		} else {
			    			if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
		    					original = original.replaceAll(i, EmojiesUtility.swords);
							}
		    			}
		    		}
				}
				
				for (String i : EmojiesUtility.coffee_list) {
					String emoji = "coffee";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.coffee);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.coffee);
							}
						}
					}
				}
				
				// Symbols
				for (String i : EmojiesUtility.airplane_list) {
					String emoji = "airplane";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.airplane);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.airplane);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.radioactive_list) {
					String emoji = "Radioactive";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.radioactive);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.radioactive);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.king_list) {
					String emoji = "King";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.king);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.king);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.scales_list) {
					String emoji = "Scales";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.scales);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.scales);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.heart_list) {
					String emoji = "Heart";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.heart);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.heart);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.notes_list) {
					String emoji = "Notes";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.notes);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.notes);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.star_list) {
					String emoji = "Star";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.star);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.star);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.peace_list) {
					String emoji = "Peace";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.peace);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.peace);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.chess_list) {
					String emoji = "Chess";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.chess);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.chess);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.copyright_list) {
					String emoji = "Copyright";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.copyright);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.copyright);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.arrow_list) {
					String emoji = "Arrow";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.arrow);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.arrow);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.strong_list) {
					String emoji = "Strong";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.strong);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.strong);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.pushups_list) {
					String emoji = "Pushups";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.pushups);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.pushups);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.anchor_list) {
					String emoji = "Anchor";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.anchor);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.anchor);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.skull_list) {
					String emoji = "Skull";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.skull);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.skull);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.umbrella_list) {
					String emoji = "Umbrella";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.umbrella);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.umbrella);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.diamonds_list) {
					String emoji = "Diamonds";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.diamonds);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.diamonds);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.snowflake_list) {
					String emoji = "Snowflake";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.snowflake);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.snowflake);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.snowman_list) {
					String emoji = "Snowman";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.snowman);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.snowman);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.checkmark_list) {
					String emoji = "Checkmark";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.checkmark);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.checkmark);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.crossmark_list) {
					String emoji = "Crossmark";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.crossmark);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.crossmark);
							}
						}
					}
				}
				
				// Japan
				for (String i : EmojiesUtility.shrug_list) {
					String emoji = "shrug";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.shrug);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.shrug);
							}
						}
					}
				}
				
				for (String i : EmojiesUtility.fliptable_list) {
					String emoji = "fliptable";
					if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Enable")) {
						if (OnChatConfig.getConfig().getBoolean("Chat-Emoji-Player.Emojis-list."+emoji+".Use_Permission")) {
							if (p.hasPermission("hawn.useemoji."+emoji)) {
								if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
									original = original.replaceAll(i, EmojiesUtility.fliptable);
								}
							}
						} else {
							if (e.getMessage().toLowerCase().contains(i.toLowerCase())) {
								original = original.replaceAll(i, EmojiesUtility.fliptable);
							}
						}
					}
				}
			}
		}
		
		e.setMessage(original);
		
		if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Enable")) {
			if (MuteChatCommandConfig.getConfig().getBoolean("MuteChat.Mute.Bypass")) {
				if (!p.hasPermission("hawn.event.chat.bypass.mutechat")) {
					e.setCancelled(true);
					for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Can-t-Speak")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else {
				e.setCancelled(true);
				for (String msg: ConfigMCommands.getConfig().getStringList("MuteChat.Can-t-Speak")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		}
		
		if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Enable")) {
			if (DelayChatCommandConfig.getConfig().getBoolean("DelayChat.Delay.Bypass")) {
				if (!p.hasPermission("hawn.event.chat.bypass.chatdelay")) {
					if (cooling.contains(name)) {
				    	e.setCancelled(true);
				    	for (String msg: ConfigMCommands.getConfig().getStringList("ChatDelay.Delay")) {
				    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
				    	}
					} else {
				        cooling.add(name);
				        
				        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
				          public void run() {
				            OnChatEvent.this.cooling.remove(name);
				          }
				        }, DelaychatCommand.delay * 20);
				      }
				}
			} else {
				if (cooling.contains(name)) {
			    	e.setCancelled(true);
			    	for (String msg: ConfigMCommands.getConfig().getStringList("ChatDelay.Delay")) {
			    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
			    	}
			      } else {
			        cooling.add(name);
			        
			        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
			          public void run() {
			            OnChatEvent.this.cooling.remove(name);
			          }
			        }, DelaychatCommand.delay * 20);
			      }
			}
		}
		
	}

}
