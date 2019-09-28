package fr.dianox.hawn.event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.BossBarApi;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import me.clip.placeholderapi.PlaceholderAPI;

public class AutoBroadcast_BossBar extends BukkitRunnable {
	
	@SuppressWarnings("unused")
	private final JavaPlugin pl;
	  
	public AutoBroadcast_BossBar(JavaPlugin pl) { this.pl = pl; }

	String msg = "";
	
	public void run() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total_bb+1);
			
			msg = String.valueOf(Main.autobroadcast_bb.get(value));
			
			String msg2 = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Message");
			
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.Use-Permission-To-Get-Messages")) {
						if (p.hasPermission("hawn.get.autobroadcastbb")) {
							msg2 = MessageUtils.ReplaceMainplaceholderP(msg2, p);
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
							}
							
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								msg2 = MessageUtils.BattleLevelPO(msg2, p);
							}
							
							msg2 = msg2.replaceAll("&", "§");
							
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.World.All_World")) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
	                        	if (BossBarApi.BBBlock.contains(p)) {
	                        		continue;
	                        	}
	                        	
	                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
	                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
	                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
								
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
	                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
	                        	}
	                        	
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
	                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
	                        	}
	                        	
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
	                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
	                        	}
	                        	
	                        	BossBarApi.deletebar(p);
	                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
	                        	
							} else {
								if (BasicEventsPW.getAutoBroadcast_bb().contains(p.getWorld().getName())) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
		                        	if (BossBarApi.BBBlock.contains(p)) {
		                        		continue;
		                        	}
		                        	
		                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
		                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
		                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
									
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
		                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
		                        	}
		                        	
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
		                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
		                        	}
		                        	
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
		                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
		                        	}
		                        	
		                        	BossBarApi.deletebar(p);
		                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
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
						
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.World.All_World")) {
							
							String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
                        	
                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
                        		continue;
                        	}
							
                        	if (BossBarApi.BBBlock.contains(p)) {
                        		continue;
                        	}
                        	
                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
							
                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
                        	}
                        	
                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
                        	}
                        	
                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
                        	}
                        	
                        	BossBarApi.deletebar(p);
                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
						} else {
							if (BasicEventsPW.getAutoBroadcast_bb().contains(p.getWorld().getName())) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
	                        	if (BossBarApi.BBBlock.contains(p)) {
	                        		continue;
	                        	}
	                        	
	                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
	                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
	                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
								
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
	                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
	                        	}
	                        	
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
	                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
	                        	}
	                        	
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
	                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
	                        	}
	                        	
	                        	BossBarApi.deletebar(p);
	                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
							}
						}
					}
			}
		} else {
			if (Main.curMsg_bb <= Main.autobroadcast_total_bb) {
				msg = String.valueOf(Main.autobroadcast_bb.get(Main.curMsg_bb));
				
				String msg2 = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Message");
				
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.Use-Permission-To-Get-Messages")) {
							if (p.hasPermission("hawn.get.autobroadcastbb")) {
								msg2 = MessageUtils.ReplaceMainplaceholderP(msg2, p);
								
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
									msg2 = PlaceholderAPI.setPlaceholders(p, msg2);
								}
								
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
									msg2 = MessageUtils.BattleLevelPO(msg2, p);
								}
								
								msg2 = msg2.replaceAll("&", "§");
								
								if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.World.All_World")) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
		                        	if (BossBarApi.BBBlock.contains(p)) {
		                        		continue;
		                        	}
		                        	
		                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
		                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
		                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
									
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
		                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
		                        	}
		                        	
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
		                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
		                        	}
		                        	
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
		                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
		                        	}
		                        	
		                        	BossBarApi.deletebar(p);
		                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
								} else {
									if (BasicEventsPW.getAutoBroadcast_bb().contains(p.getWorld().getName())) {
										
										String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
			                        	
			                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
			                        		continue;
			                        	}
										
			                        	if (BossBarApi.BBBlock.contains(p)) {
			                        		continue;
			                        	}
			                        	
			                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
			                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
			                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
										
			                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
			                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
			                        	}
			                        	
			                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
			                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
			                        	}
			                        	
			                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
			                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
			                        	}
			                        	
			                        	BossBarApi.deletebar(p);
			                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
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
							
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.World.All_World")) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
	                        	if (BossBarApi.BBBlock.contains(p)) {
	                        		continue;
	                        	}
	                        	
	                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
	                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
	                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
								
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
	                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
	                        	}
	                        	
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
	                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
	                        	}
	                        	
	                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
	                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
	                        	}
	                        	
	                        	BossBarApi.deletebar(p);
	                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
							} else {
								if (BasicEventsPW.getAutoBroadcast_bb().contains(p.getWorld().getName())) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
		                        	if (BossBarApi.BBBlock.contains(p)) {
		                        		continue;
		                        	}
		                        	
		                        	String color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Color");
		                        	String style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.Options-Default.Style");
		                        	Double progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.Options-Default.Progress");
									
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Color")) {
		                        		color = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Color");
		                        	}
		                        	
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Style")) {
		                        		style = AutoBroadcastConfig.getConfig().getString("Config.BossBar.messages."+msg+".Style");
		                        	}
		                        	
		                        	if (AutoBroadcastConfig.getConfig().isSet("Config.BossBar.messages."+msg+".Progress")) {
		                        		progress = AutoBroadcastConfig.getConfig().getDouble("Config.BossBar.messages."+msg+".Progress");
		                        	}
		                        	
		                        	BossBarApi.deletebar(p);
		                        	BossBarApi.createnewbar(p, color, msg2, style, progress);
								}
							}
						}
					}
				
				Main.curMsg_bb++;
			} else {
				Main.curMsg_bb = 0;
			}
		}
	}

}
