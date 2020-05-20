package fr.dianox.hawn.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.dianox.hawn.utility.config.configs.events.OnChatConfig;

public class EmojiesUtility {
	
	// Smileys
	public static String smiley = "☺";
	public static List<String> smiley_list = new ArrayList<String>();
	public static String sad = "☹";
	public static List<String> sad_list = new ArrayList<String>();
	
	// Items
	public static String swords = "⚔";
	public static List<String> swords_list = new ArrayList<String>();
	public static String coffee = "☕";
	public static List<String> coffee_list = new ArrayList<String>();
	public static String umbrella = "☂";
	public static List<String> umbrella_list = new ArrayList<String>();
	public static String anchor = "⚓";
	public static List<String> anchor_list = new ArrayList<String>();
	public static String email = "✉";
	public static List<String> email_list = new ArrayList<String>();
	
	// Symbols
	public static String scales = "⚖";
	public static List<String> scales_list = new ArrayList<String>();
	public static String radioactive = "☢";
	public static List<String> radioactive_list = new ArrayList<String>();
	public static String king = "♕";
	public static List<String> king_list = new ArrayList<String>();
	public static String heart = "❤";
	public static List<String> heart_list = new ArrayList<String>();
	public static String notes = "♪";
	public static List<String> notes_list = new ArrayList<String>();
	public static String star = "✰";
	public static List<String> star_list = new ArrayList<String>();
	public static String peace = "☮";
	public static List<String> peace_list = new ArrayList<String>();
	public static String chess = "♖";
	public static List<String> chess_list = new ArrayList<String>();
	public static String copyright = "©";
	public static List<String> copyright_list = new ArrayList<String>();
	public static String skull = "☠";
	public static List<String> skull_list = new ArrayList<String>();
	public static String diamonds = "♦";
	public static List<String> diamonds_list = new ArrayList<String>();
	public static String snowflake = "❄";
	public static List<String> snowflake_list = new ArrayList<String>();
	public static String snowman = "☃";
	public static List<String> snowman_list = new ArrayList<String>();
	public static String checkmark = "✔";
	public static List<String> checkmark_list = new ArrayList<String>();
	public static String crossmark = "✖";
	public static List<String> crossmark_list = new ArrayList<String>();
	public static String flag = "🏳";
	public static List<String> flag_list = new ArrayList<String>();
	public static String curlyloop = "➰";
	public static List<String> curlyloop_list = new ArrayList<String>();
	public static String bed = "🛏";
	public static List<String> bed_list = new ArrayList<String>();
	public static String pill = "💊";
	public static List<String> pill_list = new ArrayList<String>();
	public static String wrench = "🔧";
	public static List<String> wrench_list = new ArrayList<String>();
	public static String gear = "⚙";
	public static List<String> gear_list = new ArrayList<String>();
	public static String hammer = "🔨";
	public static List<String> hammer_list = new ArrayList<String>();
	public static String pick = "⛏";
	public static List<String> pick_list = new ArrayList<String>();
	
	// Travel
	public static String airplane = "✈";
	public static List<String> airplane_list = new ArrayList<String>();
	
	// japan
	public static String arrow = "Σ>―(´･ω･`)→";
	public static List<String> arrow_list = new ArrayList<String>();
	public static String strong = "(9｀･ω･)9";
	public static List<String> strong_list = new ArrayList<String>();
	public static String pushups = "┌(◣﹏◢)┐";
	public static List<String> pushups_list = new ArrayList<String>();
	public static String fliptable = "(╯°□°）╯︵ ┻━┻";
	public static List<String> fliptable_list = new ArrayList<String>();
	public static String shrug = "¯\\_(ツ)_/¯";
	public static List<String> shrug_list = new ArrayList<String>();
		
	public static void setaliaseslist() {
		
		pick_list.clear();
		hammer_list.clear();
		gear_list.clear();
		wrench_list.clear();
		pill_list.clear();
		bed_list.clear();
		curlyloop_list.clear();
		flag_list.clear();
		email_list.clear();
		sad_list.clear();
		radioactive_list.clear();
		king_list.clear();
		scales_list.clear();
		smiley_list.clear();
		swords_list.clear();
		heart_list.clear();
		notes_list.clear();
		star_list.clear();
		peace_list.clear();
		chess_list.clear();
		copyright_list.clear();
		anchor_list.clear();
		skull_list.clear();
		umbrella_list.clear();
		diamonds_list.clear();
		arrow_list.clear();
		strong_list.clear();
		pushups_list.clear();
		fliptable_list.clear();
		shrug_list.clear();
		crossmark_list.clear();
		checkmark_list.clear();
		snowman_list.clear();
		snowflake_list.clear();
		coffee_list.clear();
		airplane_list.clear();
		
		Iterator < ? > iterator = OnChatConfig.getConfig().getConfigurationSection("Chat-Emoji-Player.Emojis-list").getKeys(false).iterator();
		
		while (iterator.hasNext()) {
            String string = (String) iterator.next();

            if (!string.equalsIgnoreCase("Option")) {
            	
            	if (string.equalsIgnoreCase("email")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				email_list.add(aliases);
            			}
            		}
            		email_list.add(":email:");
            	} else if (string.equalsIgnoreCase("pick")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				pick_list.add(aliases);
            			}
            		}
            		pick_list.add(":pick:");
            	} else if (string.equalsIgnoreCase("hammer")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				hammer_list.add(aliases);
            			}
            		}
            		hammer_list.add(":hammer:");
            	} else if (string.equalsIgnoreCase("gear")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				gear_list.add(aliases);
            			}
            		}
            		gear_list.add(":gear:");
            	} else if (string.equalsIgnoreCase("wrench")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				wrench_list.add(aliases);
            			}
            		}
            		wrench_list.add(":wrench:");
            	} else if (string.equalsIgnoreCase("pill")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				pill_list.add(aliases);
            			}
            		}
            		pill_list.add(":pill:");
            	} else if (string.equalsIgnoreCase("bed")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				bed_list.add(aliases);
            			}
            		}
            		bed_list.add(":bed:");
            	} else if (string.equalsIgnoreCase("curlyloop")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				curlyloop_list.add(aliases);
            			}
            		}
            		curlyloop_list.add(":curlyloop:");
            	} else if (string.equalsIgnoreCase("flag")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				flag_list.add(aliases);
            			}
            		}
            		flag_list.add(":flag:");
            	} else if (string.equalsIgnoreCase("sad")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				sad_list.add(aliases);
            			}
            		}
            		sad_list.add(":sad:");
            	} else if (string.equalsIgnoreCase("radioactive")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				radioactive_list.add(aliases);
            			}
            		}
            		radioactive_list.add(":radioactive:");
            	} else if (string.equalsIgnoreCase("scales")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				scales_list.add(aliases);
            			}
            		}
            		scales_list.add(":scales:");
            	} else if (string.equalsIgnoreCase("king")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				king_list.add(aliases);
            			}
            		}
            		king_list.add(":king:");
            	} else if (string.equalsIgnoreCase("airplane")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				airplane_list.add(aliases);
            			}
            		}
            		airplane_list.add(":airplane:");
            	} else if (string.equalsIgnoreCase("coffee")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				coffee_list.add(aliases);
            			}
            		}
            		coffee_list.add(":coffee:");
            	} else if (string.equalsIgnoreCase("smiley")) {
            		if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            				smiley_list.add(aliases);
            			}
            		}
            		smiley_list.add(":smiley:");
            	} else if (string.equalsIgnoreCase("swords")) {
            	if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            		for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
            			swords_list.add(aliases);
            		}
            	}
            	swords_list.add(":swords:");
			} else if (string.equalsIgnoreCase("heart")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							heart_list.add(aliases);
						}
					}
					heart_list.add(":heart:");
				} else if (string.equalsIgnoreCase("notes")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							notes_list.add(aliases);
						}
					}
					notes_list.add(":notes:");
				} else if (string.equalsIgnoreCase("star")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							star_list.add(aliases);
						}
					}
					star_list.add(":star:");
				} else if (string.equalsIgnoreCase("peace")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							peace_list.add(aliases);
						}
					}
					peace_list.add(":peace:");
				} else if (string.equalsIgnoreCase("chess")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							chess_list.add(aliases);
						}
					}
					chess_list.add(":chess:");
				} else if (string.equalsIgnoreCase("copyright")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							copyright_list.add(aliases);
						}
					}
					copyright_list.add(":copyright:");
				} else if (string.equalsIgnoreCase("anchor")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							anchor_list.add(aliases);
						}
					}
					anchor_list.add(":anchor:");
				} else if (string.equalsIgnoreCase("skull")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							skull_list.add(aliases);
						}
					}
					skull_list.add(":skull:");
				} else if (string.equalsIgnoreCase("umbrella")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							umbrella_list.add(aliases);
						}
					}
					umbrella_list.add(":umbrella:");
				} else if (string.equalsIgnoreCase("diamonds")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							diamonds_list.add(aliases);
						}
					}
					diamonds_list.add(":diamonds:");
				} else if (string.equalsIgnoreCase("snowflake")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							snowflake_list.add(aliases);
						}
					}
					snowflake_list.add(":snowflake:");
				} else if (string.equalsIgnoreCase("snowman")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							snowman_list.add(aliases);
						}
					}
					snowman_list.add(":snowman:");
				} else if (string.equalsIgnoreCase("checkmark")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							checkmark_list.add(aliases);
						}
					}
					checkmark_list.add(":checkmark:");
				} else if (string.equalsIgnoreCase("crossmark")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							crossmark_list.add(aliases);
						}
					}
					crossmark_list.add(":crossmark:");
				} else if (string.equalsIgnoreCase("arrow")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							arrow_list.add(aliases);
						}
					}
					arrow_list.add(":arrow:");
				} else if (string.equalsIgnoreCase("strong")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							strong_list.add(aliases);
						}
					}
					strong_list.add(":strong:");
				} else if (string.equalsIgnoreCase("pushups")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							pushups_list.add(aliases);
						}
					}
					pushups_list.add(":pushups:");
				} else if (string.equalsIgnoreCase("fliptable")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							fliptable_list.add(aliases);
						}
					}
					fliptable_list.add(":fliptable:");
				} else if (string.equalsIgnoreCase("shrug")) {
					if (OnChatConfig.getConfig().isSet("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
						for (String aliases: OnChatConfig.getConfig().getStringList("Chat-Emoji-Player.Emojis-list." + string + ".Aliases")) {
							shrug_list.add(aliases);
						}
					}
					shrug_list.add(":shrug:");
				}
            }
		}
	}
}