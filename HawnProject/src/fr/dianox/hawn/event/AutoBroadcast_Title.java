package fr.dianox.hawn.event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.PlaceHolders;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.TitleUtils;
import fr.dianox.hawn.utility.XSound;
import fr.dianox.hawn.utility.config.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import me.clip.placeholderapi.PlaceholderAPI;

public class AutoBroadcast_Title extends BukkitRunnable  {
	
	@SuppressWarnings("unused")
	private final JavaPlugin pl;
	  
	public AutoBroadcast_Title(JavaPlugin pl) { this.pl = pl; }

	String msg = "";

	@SuppressWarnings("deprecation")
	public void run() {
		if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Random")) {
			Random rand = new Random();
			
			int value = rand.nextInt(Main.autobroadcast_total_titles+1);
			
			msg = String.valueOf(Main.autobroadcast_titles.get(value));
			
			String title = "";
			String subtitle = "";
			
			if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
				title = AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Title.Message");
			}
			
			if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
				subtitle = AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".SubTitle.Message");
			}
			
				for (Player p : Bukkit.getServer().getOnlinePlayers()) {
					if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Use-Permission-To-Get-Messages")) {
						if (p.hasPermission("hawn.get.autobroadcast_titles")) {
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
								title = PlaceHolders.ReplaceMainplaceholderP(title, p);
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
									title = PlaceholderAPI.setPlaceholders(p, title);
								}
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
									title = PlaceHolders.BattleLevelPO(title, p);
								}
								title = title.replaceAll("&", "§");
							}
							
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
								subtitle = PlaceHolders.ReplaceMainplaceholderP(subtitle, p);
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
									subtitle = PlaceholderAPI.setPlaceholders(p, subtitle);
								}
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
									subtitle = PlaceHolders.BattleLevelPO(subtitle, p);
								}
								subtitle = subtitle.replaceAll("&", "§");
							}
														
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.World.All_World")) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
								Integer FadeIn = 0;
								Integer Stay = 0;
								Integer FadeOut = 0;
								
								Integer FadeIn_s = 0;
								Integer Stay_s = 0;
								Integer FadeOut_s = 0;
								
								// Titles
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
										FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
									} else {
										FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
										Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
									} else {
										Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
										FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
									} else {
										FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
									}
								}
								
								// Subtitles
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
										FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
									} else {
										FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
										Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
									} else {
										Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
										FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
									} else {
										FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
									}
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
									
									TitleUtils.sendTitle(p, 
											FadeIn,
											Stay,
											FadeOut, 
											title);
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										TitleUtils.sendSubtitle(p, 
												FadeIn_s,
												Stay_s,
												FadeOut_s, 
												subtitle);
									}
									
								} else {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										TitleUtils.sendTitle(p, 50, 50, 50, " ");
										TitleUtils.sendSubtitle(p, 
												FadeIn_s,
												Stay_s,
												FadeOut_s, 
												subtitle);
									}
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
									p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
								}
								
							} else {
								if (BasicEventsPW.getAutoBroadcast_title().contains(p.getWorld().getName())) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
									Integer FadeIn = 0;
									Integer Stay = 0;
									Integer FadeOut = 0;
									
									Integer FadeIn_s = 0;
									Integer Stay_s = 0;
									Integer FadeOut_s = 0;
									
									// Titles
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
											FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
										} else {
											FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
											Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
										} else {
											Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
											FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
										} else {
											FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
										}
									}
									
									// Subtitles
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
											FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
										} else {
											FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
											Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
										} else {
											Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
											FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
										} else {
											FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
										}
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
										
										TitleUtils.sendTitle(p, 
												FadeIn,
												Stay,
												FadeOut, 
												title);
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
											TitleUtils.sendSubtitle(p, 
													FadeIn_s,
													Stay_s,
													FadeOut_s, 
													subtitle);
										}
										
									} else {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
											TitleUtils.sendTitle(p, 50, 50, 50, " ");
											TitleUtils.sendSubtitle(p, 
													FadeIn_s,
													Stay_s,
													FadeOut_s, 
													subtitle);
										}
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
										p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
									}
								}
							}
						}
					} else {
						if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
							title = PlaceHolders.ReplaceMainplaceholderP(title, p);
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								title = PlaceholderAPI.setPlaceholders(p, title);
							}
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								title = PlaceHolders.BattleLevelPO(title, p);
							}
							title = title.replaceAll("&", "§");
						}
						
						if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
							subtitle = PlaceHolders.ReplaceMainplaceholderP(subtitle, p);
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
								subtitle = PlaceholderAPI.setPlaceholders(p, subtitle);
							}
							if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
								subtitle = PlaceHolders.BattleLevelPO(subtitle, p);
							}
							subtitle = subtitle.replaceAll("&", "§");
						}
						
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.World.All_World")) {
							
							String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
                        	
                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
                        		continue;
                        	}
							
							Integer FadeIn = 0;
							Integer Stay = 0;
							Integer FadeOut = 0;
							
							Integer FadeIn_s = 0;
							Integer Stay_s = 0;
							Integer FadeOut_s = 0;
							
							// Titles
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
									FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
								} else {
									FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
									Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
								} else {
									Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
									FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
								} else {
									FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
								}
							}
							
							// Subtitles
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
									FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
								} else {
									FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
									Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
								} else {
									Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
									FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
								} else {
									FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
								}
							}
							
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
								
								TitleUtils.sendTitle(p, 
										FadeIn,
										Stay,
										FadeOut, 
										title);
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
									TitleUtils.sendSubtitle(p, 
											FadeIn_s,
											Stay_s,
											FadeOut_s, 
											subtitle);
								}
								
							} else {
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
									TitleUtils.sendTitle(p, 50, 50, 50, " ");
									TitleUtils.sendSubtitle(p, 
											FadeIn_s,
											Stay_s,
											FadeOut_s, 
											subtitle);
								}
							}
							
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
								p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
							}
						} else {
							if (BasicEventsPW.getAutoBroadcast_title().contains(p.getWorld().getName())) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
								Integer FadeIn = 0;
								Integer Stay = 0;
								Integer FadeOut = 0;
								
								Integer FadeIn_s = 0;
								Integer Stay_s = 0;
								Integer FadeOut_s = 0;
								
								// Titles
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
										FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
									} else {
										FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
										Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
									} else {
										Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
										FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
									} else {
										FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
									}
								}
								
								// Subtitles
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
										FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
									} else {
										FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
										Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
									} else {
										Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
										FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
									} else {
										FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
									}
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
									
									TitleUtils.sendTitle(p, 
											FadeIn,
											Stay,
											FadeOut, 
											title);
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										TitleUtils.sendSubtitle(p, 
												FadeIn_s,
												Stay_s,
												FadeOut_s, 
												subtitle);
									}
									
								} else {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										TitleUtils.sendTitle(p, 50, 50, 50, " ");
										TitleUtils.sendSubtitle(p, 
												FadeIn_s,
												Stay_s,
												FadeOut_s, 
												subtitle);
									}
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
									p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
								}
							}
						}
					}
			}
		} else {
			if (Main.curMsg_titles <= Main.autobroadcast_total_titles) {
				msg = String.valueOf(Main.autobroadcast_titles.get(Main.curMsg_titles));
				
				String title = "";
				String subtitle = "";
				
				if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
					title = AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Title.Message");
				}
				
				if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
					subtitle = AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".SubTitle.Message");
				}
				
					for (Player p : Bukkit.getServer().getOnlinePlayers()) {
						if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.Use-Permission-To-Get-Messages")) {
							if (p.hasPermission("hawn.get.autobroadcast_titles")) {
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
									title = PlaceHolders.ReplaceMainplaceholderP(title, p);
									if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
										title = PlaceholderAPI.setPlaceholders(p, title);
									}
									if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
										title = PlaceHolders.BattleLevelPO(title, p);
									}
									title = title.replaceAll("&", "§");
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
									subtitle = PlaceHolders.ReplaceMainplaceholderP(subtitle, p);
									if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
										subtitle = PlaceholderAPI.setPlaceholders(p, subtitle);
									}
									if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
										subtitle = PlaceHolders.BattleLevelPO(subtitle, p);
									}
									subtitle = subtitle.replaceAll("&", "§");
								}
								
								if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.World.All_World")) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
									Integer FadeIn = 0;
									Integer Stay = 0;
									Integer FadeOut = 0;
									
									Integer FadeIn_s = 0;
									Integer Stay_s = 0;
									Integer FadeOut_s = 0;
									
									// Titles
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
											FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
										} else {
											FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
											Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
										} else {
											Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
											FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
										} else {
											FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
										}
									}
									
									// Subtitles
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
											FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
										} else {
											FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
											Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
										} else {
											Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
											FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
										} else {
											FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
										}
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
										
										TitleUtils.sendTitle(p, 
												FadeIn,
												Stay,
												FadeOut, 
												title);
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
											TitleUtils.sendSubtitle(p, 
													FadeIn_s,
													Stay_s,
													FadeOut_s, 
													subtitle);
										}
										
									} else {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
											TitleUtils.sendTitle(p, 50, 50, 50, " ");
											TitleUtils.sendSubtitle(p, 
													FadeIn_s,
													Stay_s,
													FadeOut_s, 
													subtitle);
										}
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
										p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
									}
								} else {
									if (BasicEventsPW.getAutoBroadcast_title().contains(p.getWorld().getName())) {
										
										String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
			                        	
			                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
			                        		continue;
			                        	}
										
										Integer FadeIn = 0;
										Integer Stay = 0;
										Integer FadeOut = 0;
										
										Integer FadeIn_s = 0;
										Integer Stay_s = 0;
										Integer FadeOut_s = 0;
										
										// Titles
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
												FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
											} else {
												FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
											}
											
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
												Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
											} else {
												Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
											}
											
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
												FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
											} else {
												FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
											}
										}
										
										// Subtitles
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
												FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
											} else {
												FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
											}
											
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
												Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
											} else {
												Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
											}
											
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
												FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
											} else {
												FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
											}
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
											
											TitleUtils.sendTitle(p, 
													FadeIn,
													Stay,
													FadeOut, 
													title);
											
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
												TitleUtils.sendSubtitle(p, 
														FadeIn_s,
														Stay_s,
														FadeOut_s, 
														subtitle);
											}
											
										} else {
											if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
												TitleUtils.sendTitle(p, 50, 50, 50, " ");
												TitleUtils.sendSubtitle(p, 
														FadeIn_s,
														Stay_s,
														FadeOut_s, 
														subtitle);
											}
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
											p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
										}
									}
								}
							}
						} else {
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
								title = PlaceHolders.ReplaceMainplaceholderP(title, p);
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
									title = PlaceholderAPI.setPlaceholders(p, title);
								}
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
									title = PlaceHolders.BattleLevelPO(title, p);
								}
								title = title.replaceAll("&", "§");
							}
							
							if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
								subtitle = PlaceHolders.ReplaceMainplaceholderP(subtitle, p);
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
									subtitle = PlaceholderAPI.setPlaceholders(p, subtitle);
								}
								if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.BattleLevels.Enable")) {
									subtitle = PlaceHolders.BattleLevelPO(subtitle, p);
								}
								subtitle = subtitle.replaceAll("&", "§");
							}
							
							if (AutoBroadcastConfig.getConfig().getBoolean("Config.Titles.World.All_World")) {
								
								String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
	                        	
	                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
	                        		continue;
	                        	}
								
								Integer FadeIn = 0;
								Integer Stay = 0;
								Integer FadeOut = 0;
								
								Integer FadeIn_s = 0;
								Integer Stay_s = 0;
								Integer FadeOut_s = 0;
								
								// Titles
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
										FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
									} else {
										FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
										Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
									} else {
										Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
										FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
									} else {
										FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
									}
								}
								
								// Subtitles
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
										FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
									} else {
										FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
										Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
									} else {
										Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
										FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
									} else {
										FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
									}
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
									
									TitleUtils.sendTitle(p, 
											FadeIn,
											Stay,
											FadeOut, 
											title);
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										TitleUtils.sendSubtitle(p, 
												FadeIn_s,
												Stay_s,
												FadeOut_s, 
												subtitle);
									}
									
								} else {
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										TitleUtils.sendTitle(p, 50, 50, 50, " ");
										TitleUtils.sendSubtitle(p, 
												FadeIn_s,
												Stay_s,
												FadeOut_s, 
												subtitle);
									}
								}
								
								if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
									p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
								}
							} else {
								if (BasicEventsPW.getAutoBroadcast_title().contains(p.getWorld().getName())) {
									
									String valuepo = PlayerOptionSQLClass.GetSQLPOautobc(p);
		                        	
		                        	if (!valuepo.equalsIgnoreCase("TRUE")) {
		                        		continue;
		                        	}
									
									Integer FadeIn = 0;
									Integer Stay = 0;
									Integer FadeOut = 0;
									
									Integer FadeIn_s = 0;
									Integer Stay_s = 0;
									Integer FadeOut_s = 0;
									
									// Titles
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeIn")) {
											FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeIn");
										} else {
											FadeIn = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeIn");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Stay")) {
											Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.Stay");
										} else {
											Stay = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.Stay");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.FadeOut")) {
											FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".Title.FadeOut");
										} else {
											FadeOut = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.Title.FadeOut");
										}
									}
									
									// Subtitles
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeIn")) {
											FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeIn");
										} else {
											FadeIn_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeIn");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Stay")) {
											Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.Stay");
										} else {
											Stay_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.Stay");
										}
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.FadeOut")) {
											FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.messages."+msg+".SubTitle.FadeOut");
										} else {
											FadeOut_s = AutoBroadcastConfig.getConfig().getInt("Config.Titles.Options-Default.SubTitle.FadeOut");
										}
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Title.Message")) {
										
										TitleUtils.sendTitle(p, 
												FadeIn,
												Stay,
												FadeOut, 
												title);
										
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
											TitleUtils.sendSubtitle(p, 
													FadeIn_s,
													Stay_s,
													FadeOut_s, 
													subtitle);
										}
										
									} else {
										if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".SubTitle.Message")) {
											TitleUtils.sendTitle(p, 50, 50, 50, " ");
											TitleUtils.sendSubtitle(p, 
													FadeIn_s,
													Stay_s,
													FadeOut_s, 
													subtitle);
										}
									}
									
									if (AutoBroadcastConfig.getConfig().isSet("Config.Titles.messages."+msg+".Sound")) {
										p.playSound(p.getLocation(), XSound.matchXSound(AutoBroadcastConfig.getConfig().getString("Config.Titles.messages."+msg+".Sound")).parseSound(), 1, 1);
									}
								}
							}
						}
					}
				
				Main.curMsg_titles++;
			} else {
				Main.curMsg_titles = 0;
			}
		}
	}
	
}
