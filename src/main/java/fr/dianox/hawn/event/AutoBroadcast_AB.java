package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ActionBar;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class AutoBroadcast_AB extends BukkitRunnable {
	
	@SuppressWarnings("unused")
	private final JavaPlugin pl;
	  
	public AutoBroadcast_AB(JavaPlugin pl) { this.pl = pl; }

	String msg = "";
	
	public void run() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total_ab+1);
			
			msg = String.valueOf(Main.autobroadcast_ab.get(value));
			
			String msg2 = AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Message");
			
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Use-Permission-To-Get-Messages")) {
						if (p.hasPermission("hawn.get.autobroadcast_ab")) {
							msg2 = PlaceHolders.ReplaceMainplaceholderP(msg2, p);
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
							}
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								msg2 = PlaceHolders.BattleLevelPO(msg2, p);
							}
							
							msg2 = msg2.replaceAll("&", "§");
							
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.World.All_World")) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
									ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
								} else {
									ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
									p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
								}
							} else {
								if (BasicEventsPW.getAutoBroadcast_ab().contains(p.getWorld().getName())) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
										ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
									} else {
										ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
										p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
									}
								}
							}
						}
					} else {
						msg2 = PlaceHolders.ReplaceMainplaceholderP(msg2, p);
						
						if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
							msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
						}
						
						if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
							msg2 = PlaceHolders.BattleLevelPO(msg2, p);
						}
						
						msg2 = msg2.replaceAll("&", "§");
						
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.World.All_World")) {
							
							String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
                        	
                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
                        		continue;
                        	}
							
							if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
								ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
							} else {
								ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
							}
							
							if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
								p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
							}
						} else {
							if (BasicEventsPW.getAutoBroadcast_ab().contains(p.getWorld().getName())) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
									ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
								} else {
									ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
									p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
								}
							}
						}
					}
			}
		} else {
			if (Main.curMsg_ab <= Main.autobroadcast_total_ab) {
				msg = String.valueOf(Main.autobroadcast_ab.get(Main.curMsg_ab));
				
				String msg2 = AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Message");
				
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.Use-Permission-To-Get-Messages")) {
							if (p.hasPermission("hawn.get.autobroadcast_ab")) {
								msg2 = PlaceHolders.ReplaceMainplaceholderP(msg2, p);
								
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
									msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
								}
								
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
									msg2 = PlaceHolders.BattleLevelPO(msg2, p);
								}
								
								msg2 = msg2.replaceAll("&", "§");
								
								if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.World.All_World")) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
										ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
									} else {
										ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
										p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
									}
								} else {
									if (BasicEventsPW.getAutoBroadcast_ab().contains(p.getWorld().getName())) {
										
										String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
			                        	
			                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
			                        		continue;
			                        	}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
											ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
										} else {
											ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
											p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
										}
									}
								}
							}
						} else {
							msg2 = PlaceHolders.ReplaceMainplaceholderP(msg2, p);
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
							}
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								msg2 = PlaceHolders.BattleLevelPO(msg2, p);
							}
							
							msg2 = msg2.replaceAll("&", "§");
							
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.Action-Bar.World.All_World")) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
									ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
								} else {
									ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
									p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
								}
							} else {
								if (BasicEventsPW.getAutoBroadcast_ab().contains(p.getWorld().getName())) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Time-Stay")) {
										ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.messages."+msg+".Time-Stay"));
									} else {
										ActionBar.sendActionBar(p, msg2, AutoBroadcastConfig.getConfig().getInt("Config.Action-Bar.Options-Default.Time-Stay"));
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Action-Bar.messages."+msg+".Sound")) {
										p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Action-Bar.messages."+msg+".Sound")).parseSound(), 1, 1);
									}
								}
							}
						}
					}
				
				Main.curMsg_ab++;
			} else {
				Main.curMsg_ab = 0;
			}
		}
	}

}
