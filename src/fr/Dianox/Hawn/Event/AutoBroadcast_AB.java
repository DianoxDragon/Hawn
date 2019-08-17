package fr.Dianox.Hawn.Event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.ActionBar;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.PlayerOptionSQLClass;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.World.BasicEventsPW;
import me.clip.placeholderapi.PlaceholderAPI;

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
							msg2 = MessageUtils.ReplaceMainplaceholderP(msg2, p);
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
							}
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								msg2 = MessageUtils.BattleLevelPO(msg2, p);
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
								}
							}
						}
					} else {
						msg2 = MessageUtils.ReplaceMainplaceholderP(msg2, p);
						
						if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
							msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
						}
						
						if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
							msg2 = MessageUtils.BattleLevelPO(msg2, p);
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
								msg2 = MessageUtils.ReplaceMainplaceholderP(msg2, p);
								
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
									msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
								}
								
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
									msg2 = MessageUtils.BattleLevelPO(msg2, p);
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
									}
								}
							}
						} else {
							msg2 = MessageUtils.ReplaceMainplaceholderP(msg2, p);
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
							}
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								msg2 = MessageUtils.BattleLevelPO(msg2, p);
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
