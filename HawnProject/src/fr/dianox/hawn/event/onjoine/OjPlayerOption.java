package fr.dianox.hawn.event.onjoine;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.commands.features.FlyCommand;
import fr.dianox.hawn.commands.features.VanishCommand;
import fr.dianox.hawn.commands.features.VanishTaskAB;
import fr.dianox.hawn.modules.onjoin.cji.SpecialItemPlayerVisibility;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.PlayerVisibility;
import fr.dianox.hawn.utility.config.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.commands.OptionPlayerConfigCommand;
import fr.dianox.hawn.utility.config.commands.VanishCommandConfig;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMPlayerOption;
import fr.dianox.hawn.utility.config.messages.administration.OtherAMConfig;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import fr.dianox.hawn.utility.world.OnJoinPW;
import fr.dianox.hawn.utility.world.PlayerEventsPW;

public class OjPlayerOption {
	
	public static void PO(Player p) {
		if (PlayerOptionMainConfig.getConfig().getBoolean("General.Enable")) {
			if (!OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.World.All_World")) {
				if (PlayerEventsPW.getWJoinPlayerOption().contains(p.getWorld().getName())) {
					Speed(p);
					onGamemodePO(p);
					FlyDoubleJump(p);
					vanish(p);
					SpecialItemPlayerVisibility.PlayerGivePlayerVisibilityItemOnJoin(p);
				} else {
					int speedvaluepo = OnJoinConfig.getConfig().getInt("Speed.Value");
					
					if (OnJoinConfig.getConfig().getBoolean("Speed.Enable")) {
			            if (!OnJoinConfig.getConfig().getBoolean("Speed.World.All_World")) {
			            	if (OnJoinPW.getSOJ().contains(p.getWorld().getName())) {
			            		if (speedvaluepo == 1) {
			                        p.setWalkSpeed(0.1F);
			                    } else if (speedvaluepo == 2) {
			                        p.setWalkSpeed(0.2F);
			                    } else if (speedvaluepo == 3) {
			                        p.setWalkSpeed(0.3F);
			                    } else if (speedvaluepo == 4) {
			                        p.setWalkSpeed(0.4F);
			                    } else if (speedvaluepo == 5) {
			                        p.setWalkSpeed(0.5F);
			                    } else if (speedvaluepo == 6) {
			                        p.setWalkSpeed(0.6F);
			                    } else if (speedvaluepo == 7) {
			                        p.setWalkSpeed(0.7F);
			                    } else if (speedvaluepo == 8) {
			                        p.setWalkSpeed(0.8F);
			                    } else if (speedvaluepo == 9) {
			                        p.setWalkSpeed(0.9F);
			                    } else if (speedvaluepo == 10) {
			                        p.setWalkSpeed(1.0F);
			                    } else {
			                        p.setWalkSpeed(0.2F);
			                    }
			            	}
			            } else {
			            	if (speedvaluepo == 1) {
		                        p.setWalkSpeed(0.1F);
		                    } else if (speedvaluepo == 2) {
		                        p.setWalkSpeed(0.2F);
		                    } else if (speedvaluepo == 3) {
		                        p.setWalkSpeed(0.3F);
		                    } else if (speedvaluepo == 4) {
		                        p.setWalkSpeed(0.4F);
		                    } else if (speedvaluepo == 5) {
		                        p.setWalkSpeed(0.5F);
		                    } else if (speedvaluepo == 6) {
		                        p.setWalkSpeed(0.6F);
		                    } else if (speedvaluepo == 7) {
		                        p.setWalkSpeed(0.7F);
		                    } else if (speedvaluepo == 8) {
		                        p.setWalkSpeed(0.8F);
		                    } else if (speedvaluepo == 9) {
		                        p.setWalkSpeed(0.9F);
		                    } else if (speedvaluepo == 10) {
		                        p.setWalkSpeed(1.0F);
		                    } else {
		                        p.setWalkSpeed(0.2F);
		                    }
			            }
			            
			            classicGamemode(p);
			            
			            FlyDoubleJumpnull(p);
			            
			            vanishnull(p);
					}
				}
			} else {
				Speed(p);
				onGamemodePO(p);
				FlyDoubleJump(p);
				vanish(p);
				SpecialItemPlayerVisibility.PlayerGivePlayerVisibilityItemOnJoin(p);
			}
		} else {
			int speedvaluepo = OnJoinConfig.getConfig().getInt("Speed.Value");
			
			SpecialItemPlayerVisibility.PlayerGivePlayerVisibilityItemOnJoin(p);
			
			if (OnJoinConfig.getConfig().getBoolean("Speed.Enable")) {
	            if (!OnJoinConfig.getConfig().getBoolean("Speed.World.All_World")) {
	            	if (OnJoinPW.getSOJ().contains(p.getWorld().getName())) {
	            		if (speedvaluepo == 1) {
	                        p.setWalkSpeed(0.1F);
	                    } else if (speedvaluepo == 2) {
	                        p.setWalkSpeed(0.2F);
	                    } else if (speedvaluepo == 3) {
	                        p.setWalkSpeed(0.3F);
	                    } else if (speedvaluepo == 4) {
	                        p.setWalkSpeed(0.4F);
	                    } else if (speedvaluepo == 5) {
	                        p.setWalkSpeed(0.5F);
	                    } else if (speedvaluepo == 6) {
	                        p.setWalkSpeed(0.6F);
	                    } else if (speedvaluepo == 7) {
	                        p.setWalkSpeed(0.7F);
	                    } else if (speedvaluepo == 8) {
	                        p.setWalkSpeed(0.8F);
	                    } else if (speedvaluepo == 9) {
	                        p.setWalkSpeed(0.9F);
	                    } else if (speedvaluepo == 10) {
	                        p.setWalkSpeed(1.0F);
	                    } else {
	                        p.setWalkSpeed(0.2F);
	                    }
	            	}
	            } else {
	            	if (speedvaluepo == 1) {
                        p.setWalkSpeed(0.1F);
                    } else if (speedvaluepo == 2) {
                        p.setWalkSpeed(0.2F);
                    } else if (speedvaluepo == 3) {
                        p.setWalkSpeed(0.3F);
                    } else if (speedvaluepo == 4) {
                        p.setWalkSpeed(0.4F);
                    } else if (speedvaluepo == 5) {
                        p.setWalkSpeed(0.5F);
                    } else if (speedvaluepo == 6) {
                        p.setWalkSpeed(0.6F);
                    } else if (speedvaluepo == 7) {
                        p.setWalkSpeed(0.7F);
                    } else if (speedvaluepo == 8) {
                        p.setWalkSpeed(0.8F);
                    } else if (speedvaluepo == 9) {
                        p.setWalkSpeed(0.9F);
                    } else if (speedvaluepo == 10) {
                        p.setWalkSpeed(1.0F);
                    } else {
                        p.setWalkSpeed(0.2F);
                    }
	            }
	            
	            classicGamemode(p);
	            
	            FlyDoubleJumpnull(p);
	            
	            vanishnull(p);
			}
		}
	}
	
	public static void onJumpBoost(Player p, Integer duration) {
		if (OptionPlayerConfigCommand.getConfig().getBoolean("Chat.Clear.Enable")) {
			if (!OptionPlayerConfigCommand.getConfig().getBoolean("Chat.Clear.World.All_World")) {
				if (PlayerEventsPW.getWJoinPlayerOption().contains(p.getWorld().getName())) {
					if (PlayerOptionSQLClass.GetSQLPOJumpBoost(p).equalsIgnoreCase("TRUE")) {
			            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
							public void run() {
								p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1999999999, OptionPlayerConfigCommand.getConfig().getInt("PlayerOption.Option.Jumpboost.Value")));
							}
						}, duration);
			        }
				}
			} else {
				if (PlayerOptionSQLClass.GetSQLPOJumpBoost(p).equalsIgnoreCase("TRUE")) {
		            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						public void run() {
							p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1999999999, OptionPlayerConfigCommand.getConfig().getInt("PlayerOption.Option.Jumpboost.Value")));
						}
					}, duration);
		        }
			}
		}
	}
	
	private static void Speed(Player p) {
    	int speedvaluepo = OnJoinConfig.getConfig().getInt("Speed.Value");
    	String uuid = p.getUniqueId().toString();
    	
        if (OnJoinConfig.getConfig().getBoolean("Speed.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Speed.World.All_World")) {
                if (OnJoinPW.getSOJ().contains(p.getWorld().getName())) {
                	if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
                		if (PlayerOptionMainConfig.getConfig().getBoolean("Keep.Speed-OnJoin.Enable") && OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
                			if (p.hasPermission("hawn.onjoin.playeroption.speed")) {
                				speedvaluepo = Integer.valueOf(PlayerOptionSQLClass.GetSQLPOSpeed(p, "VALUE"));
                			} else {
                				PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvaluepo);
                			}
                		} else if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {                			
                			if (!ConfigPlayerGet.getFile(uuid).isSet("player_speed.value")) {
                				ConfigPlayerGet.writeBoolean(uuid, "player_speed.Activate", false);
                				ConfigPlayerGet.writeInt(uuid, "player_speed.value", OnJoinConfig.getConfig().getInt("Speed.Value"));
                	        }
                			
                			if (p.hasPermission("hawn.onjoin.playeroption.speed")) {
                				speedvaluepo = ConfigPlayerGet.getFile(uuid).getInt("player_speed.value");
                			} else {
                				PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvaluepo);
                			}
                		}
                		
                		 if (speedvaluepo == 1) {
                             p.setWalkSpeed(0.1F);
                         } else if (speedvaluepo == 2) {
                             p.setWalkSpeed(0.2F);
                         } else if (speedvaluepo == 3) {
                             p.setWalkSpeed(0.3F);
                         } else if (speedvaluepo == 4) {
                             p.setWalkSpeed(0.4F);
                         } else if (speedvaluepo == 5) {
                             p.setWalkSpeed(0.5F);
                         } else if (speedvaluepo == 6) {
                             p.setWalkSpeed(0.6F);
                         } else if (speedvaluepo == 7) {
                             p.setWalkSpeed(0.7F);
                         } else if (speedvaluepo == 8) {
                             p.setWalkSpeed(0.8F);
                         } else if (speedvaluepo == 9) {
                             p.setWalkSpeed(0.9F);
                         } else if (speedvaluepo == 10) {
                             p.setWalkSpeed(1.0F);
                         } else {
                             p.setWalkSpeed(0.2F);
                         }
                	} else {
                		PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvaluepo);
                		
                		if (speedvaluepo == 1) {
                            p.setWalkSpeed(0.1F);
                        } else if (speedvaluepo == 2) {
                            p.setWalkSpeed(0.2F);
                        } else if (speedvaluepo == 3) {
                            p.setWalkSpeed(0.3F);
                        } else if (speedvaluepo == 4) {
                            p.setWalkSpeed(0.4F);
                        } else if (speedvaluepo == 5) {
                            p.setWalkSpeed(0.5F);
                        } else if (speedvaluepo == 6) {
                            p.setWalkSpeed(0.6F);
                        } else if (speedvaluepo == 7) {
                            p.setWalkSpeed(0.7F);
                        } else if (speedvaluepo == 8) {
                            p.setWalkSpeed(0.8F);
                        } else if (speedvaluepo == 9) {
                            p.setWalkSpeed(0.9F);
                        } else if (speedvaluepo == 10) {
                            p.setWalkSpeed(1.0F);
                        } else {
                            p.setWalkSpeed(0.2F);
                        }
                	}
                }
            } else {     
            	if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
            		if (PlayerOptionMainConfig.getConfig().getBoolean("Keep.Speed-OnJoin.Enable") && OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
            			if (p.hasPermission("hawn.onjoin.playeroption.speed")) {
            				speedvaluepo = Integer.valueOf(PlayerOptionSQLClass.GetSQLPOSpeed(p, "VALUE"));
            			} else {
            				PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvaluepo);
            			}
            		} else if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {            			
            			if (!ConfigPlayerGet.getFile(uuid).isSet("player_speed.value")) {
            				ConfigPlayerGet.writeBoolean(uuid, "player_speed.Activate", false);
            				ConfigPlayerGet.writeInt(uuid, "player_speed.value", OnJoinConfig.getConfig().getInt("Speed.Value"));
            	        }
            			
            			if (p.hasPermission("hawn.onjoin.playeroption.speed")) {
            				speedvaluepo = ConfigPlayerGet.getFile(uuid).getInt("player_speed.value");
            			} else {
            				PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvaluepo);
            			}
            		}
            		
            		 if (speedvaluepo == 1) {
                         p.setWalkSpeed(0.1F);
                     } else if (speedvaluepo == 2) {
                         p.setWalkSpeed(0.2F);
                     } else if (speedvaluepo == 3) {
                         p.setWalkSpeed(0.3F);
                     } else if (speedvaluepo == 4) {
                         p.setWalkSpeed(0.4F);
                     } else if (speedvaluepo == 5) {
                         p.setWalkSpeed(0.5F);
                     } else if (speedvaluepo == 6) {
                         p.setWalkSpeed(0.6F);
                     } else if (speedvaluepo == 7) {
                         p.setWalkSpeed(0.7F);
                     } else if (speedvaluepo == 8) {
                         p.setWalkSpeed(0.8F);
                     } else if (speedvaluepo == 9) {
                         p.setWalkSpeed(0.9F);
                     } else if (speedvaluepo == 10) {
                         p.setWalkSpeed(1.0F);
                     } else {
                         p.setWalkSpeed(0.2F);
                     }
            	} else {
            		PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvaluepo);
            		
            		if (speedvaluepo == 1) {
                        p.setWalkSpeed(0.1F);
                    } else if (speedvaluepo == 2) {
                        p.setWalkSpeed(0.2F);
                    } else if (speedvaluepo == 3) {
                        p.setWalkSpeed(0.3F);
                    } else if (speedvaluepo == 4) {
                        p.setWalkSpeed(0.4F);
                    } else if (speedvaluepo == 5) {
                        p.setWalkSpeed(0.5F);
                    } else if (speedvaluepo == 6) {
                        p.setWalkSpeed(0.6F);
                    } else if (speedvaluepo == 7) {
                        p.setWalkSpeed(0.7F);
                    } else if (speedvaluepo == 8) {
                        p.setWalkSpeed(0.8F);
                    } else if (speedvaluepo == 9) {
                        p.setWalkSpeed(0.9F);
                    } else if (speedvaluepo == 10) {
                        p.setWalkSpeed(1.0F);
                    } else {
                        p.setWalkSpeed(0.2F);
                    }
            	}
            }
        }
		
	}
	
	public static void classicGamemode(Player p) {
		int gm = OnJoinConfig.getConfig().getInt("Change-Gamemode.Value");
    	
    	if (OnJoinConfig.getConfig().getBoolean("Change-Gamemode.Enable")) {
    		if (OnJoinConfig.getConfig().getBoolean("Change-Gamemode.Bypass-With-Permission")) {
    			if (!p.hasPermission("hawn.bypass.gamemodeonjoin")) {
    				if (!OnJoinConfig.getConfig().getBoolean("Change-Gamemode.World.All_World")) {
    					if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
    						return;
    					}
    				}
    			} else {
    				return;
    			}
    		} else {
    			if (!OnJoinConfig.getConfig().getBoolean("Change-Gamemode.World.All_World")) {
					if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
						return;
					}
				}
    		}
    	} else {
    		return;
    	}
    	
    	if (gm == 0) {
            p.setGameMode(GameMode.SURVIVAL);
        } else if (gm == 1) {
            p.setGameMode(GameMode.CREATIVE);
        } else if (gm == 2) {
            p.setGameMode(GameMode.ADVENTURE);
        } else if (gm == 3) {
            p.setGameMode(GameMode.SPECTATOR);
        }
	}
	
    public static void onGamemodePO(Player p) {
    	int gm = OnJoinConfig.getConfig().getInt("Change-Gamemode.Value");
    	
    	if (OnJoinConfig.getConfig().getBoolean("Change-Gamemode.Enable")) {
    		if (OnJoinConfig.getConfig().getBoolean("Change-Gamemode.Bypass-With-Permission")) {
    			if (!p.hasPermission("hawn.bypass.gamemodeonjoin")) {
    				if (!OnJoinConfig.getConfig().getBoolean("Change-Gamemode.World.All_World")) {
    					if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
    						return;
    					}
    				}
    			} else {
    				return;
    			}
    		} else {
    			if (!OnJoinConfig.getConfig().getBoolean("Change-Gamemode.World.All_World")) {
					if (BasicEventsPW.getGM().contains(p.getWorld().getName())) {
						return;
					}
				}
    		}
    	} else {
    		return;
    	}
    	
        if (PlayerOptionMainConfig.getConfig().getBoolean("Keep.Gamemode-On-Join.Enable") && p.hasPlayedBefore()) {
            if (p.hasPermission("Hawn.onjoin.keepgamemode")) {

            	int gmstock = 0;
            	gmstock = Integer.valueOf(PlayerOptionSQLClass.GetSQLPOGamemode(p));
                	
            	if (gmstock == 0) {
            		p.setGameMode(GameMode.SURVIVAL);
            	} else if (gmstock == 1) {
            		p.setGameMode(GameMode.CREATIVE);
            	} else if (gmstock == 2) {
            		p.setGameMode(GameMode.ADVENTURE);
            	} else if (gmstock == 3) {
            		p.setGameMode(GameMode.SPECTATOR);
            	}
            } else {
                if (gm == 0) {
                    p.setGameMode(GameMode.SURVIVAL);
                } else if (gm == 1) {
                    p.setGameMode(GameMode.CREATIVE);
                } else if (gm == 2) {
                    p.setGameMode(GameMode.ADVENTURE);
                } else if (gm == 3) {
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }
        } else {
        	if (gm == 0) {
                p.setGameMode(GameMode.SURVIVAL);
            } else if (gm == 1) {
                p.setGameMode(GameMode.CREATIVE);
            } else if (gm == 2) {
                p.setGameMode(GameMode.ADVENTURE);
            } else if (gm == 3) {
                p.setGameMode(GameMode.SPECTATOR);
            }
        }
    }

    @SuppressWarnings("deprecation")
	private static void vanishnull(Player p) {
    	if (!VanishCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
	    	if (VanishCommand.player_list_vanish.contains(p)) {
	            VanishCommand.player_list_vanish.remove(p);
	        }
	
	        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
	       	 if (VanishCommand.player_list_vanish.contains(all)) {	
	            	if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
						p.hidePlayer(Main.getInstance(), all);
					} else {
						p.hidePlayer(all);
					}
	            	
	            	for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
						if (p1.hasPermission("hawn.staff.seevanished")) {
							for (String s: OtherAMConfig.getConfig().getStringList("Vanish.Vanish-On")) {
								MessageUtils.ReplaceCharMessagePlayer(s.replaceAll("%player%", p.getName()), p1);
							}
						}
					}
	            	
	            	if (Main.TaskVanishAB.containsKey(p)) {
						Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
						Main.TaskVanishAB.remove(p);
					}
					
	            	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						public void run() {
							if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar-If-Vanished")) {
								if (p.hasPermission("hawn.command.vanish.actionbar")) {
									BukkitTask task = null;
									if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar.Message-blinking")) {
										task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 100);
									} else {
										task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 20);
									}
									Main.TaskVanishAB.put(p, task.getTaskId());
								}
							}
						}
					}, 20*5);
	            }
	        }
    	}

       for (Player all: Bukkit.getServer().getOnlinePlayers()) {
       	if (PlayerVisibility.PVPlayer.contains(all)) {
       		if (!all.getName().equalsIgnoreCase(p.getName())) {
           		if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
           			all.hidePlayer(Main.getInstance(), p);
       			} else {
       				all.hidePlayer(p);
       			}
           		
           		for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
					if (p1.hasPermission("hawn.staff.seevanished")) {
						for (String s: OtherAMConfig.getConfig().getStringList("Vanish.Vanish-On")) {
							MessageUtils.ReplaceCharMessagePlayer(s.replaceAll("%player%", p.getName()), p1);
						}
					}
				}
       		}
           }
       }
    }
    
    @SuppressWarnings("deprecation")
	private static void vanish(Player p) {
    	if (!VanishCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
            if (PlayerOptionMainConfig.getConfig().getBoolean("Keep.Vanish-On-Join.Enable")) {
            	if (p.hasPermission("hawn.betweenservers.keepvanish")) {
    	        		if (PlayerOptionSQLClass.GetSQLPOVanish(p).equalsIgnoreCase("TRUE")) {
    	        			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
    							if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
    								all.hidePlayer(Main.getInstance(), p);
    							} else {
    								all.hidePlayer(p);
    							}
    						}
    	        			
    	        			for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
								if (p1.hasPermission("hawn.staff.seevanished")) {
									for (String s: OtherAMConfig.getConfig().getStringList("Vanish.Vanish-On")) {
										MessageUtils.ReplaceCharMessagePlayer(s.replaceAll("%player%", p.getName()), p1);
									}
								}
							}
    	        			
    	    				VanishCommand.player_list_vanish.add(p);
    						
    	    				if (Main.TaskVanishAB.containsKey(p)) {
    							Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
    							Main.TaskVanishAB.remove(p);
    						}
    						
    	    				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
    							public void run() {
    								if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar-If-Vanished")) {
    									if (p.hasPermission("hawn.command.vanish.actionbar")) {
    										BukkitTask task = null;
    										if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar.Message-blinking")) {
    											task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 100);
    										} else {
    											task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 20);
    										}
    										Main.TaskVanishAB.put(p, task.getTaskId());
    									}
    								}
    							}
    						}, 20*5);
    						
    	    				
    						if (ConfigMCommands.getConfig().getBoolean("Vanish.Self.Enable")) {
    							for (String msg: ConfigMCommands.getConfig().getStringList("Vanish.Self.Messages")) {
    			            		MessageUtils.ReplaceCharMessagePlayer(msg, p);
    			            	}
    						}
    	    			} else {
    	    				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
    							if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
    								all.showPlayer(Main.getInstance(), p);
    							} else {
    								all.showPlayer(p);
    							}
    						}
    	    				VanishCommand.player_list_vanish.remove(p);
    	    				
    	    				if (Main.TaskVanishAB.containsKey(p)) {
    							Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
    							Main.TaskVanishAB.remove(p);
    						}
    	    			}
            	}
            	
            	for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                    if (VanishCommand.player_list_vanish.contains(all)) {
                    	if (VanishCommand.player_list_vanish.contains(all)) {	
                         	if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
         						p.showPlayer(Main.getInstance(), all);
         					} else {
         						p.showPlayer(all);
         					}
                         	
                         	if (Main.TaskVanishAB.containsKey(p)) {
    							Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
    							Main.TaskVanishAB.remove(p);
    						}
                         }
                    }
                }
            } else {
            	 if (VanishCommand.player_list_vanish.contains(p)) {
                     VanishCommand.player_list_vanish.remove(p);
                 }

                 for (Player all: Bukkit.getServer().getOnlinePlayers()) {
                	 if (VanishCommand.player_list_vanish.contains(all)) {	
                     	if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
     						p.hidePlayer(Main.getInstance(), all);
     					} else {
     						p.hidePlayer(all);
     					}
                     	
                     	for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
							if (p1.hasPermission("hawn.staff.seevanished")) {
								for (String s: OtherAMConfig.getConfig().getStringList("Vanish.Vanish-On")) {
									MessageUtils.ReplaceCharMessagePlayer(s.replaceAll("%player%", p.getName()), p1);
								}
							}
						}
                     	
                     	if (Main.TaskVanishAB.containsKey(p)) {
    						Bukkit.getScheduler().cancelTask(Main.TaskVanishAB.get(p));
    						Main.TaskVanishAB.remove(p);
    					}
    					
                     	Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
    						public void run() {
    							if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar-If-Vanished")) {
    								if (p.hasPermission("hawn.command.vanish.actionbar")) {
    									BukkitTask task = null;
    									if (VanishCommandConfig.getConfig().getBoolean("Vanish.Action-Bar.Message-blinking")) {
    										task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 100);
    									} else {
    										task = new VanishTaskAB(p).runTaskTimer(Main.getInstance(), 20, 20);
    									}
    									Main.TaskVanishAB.put(p, task.getTaskId());
    								}
    							}
    						}
    					}, 20*5);
                     }
                 }

            }

    	        for (Player all: Bukkit.getServer().getOnlinePlayers()) {
    	        	if (PlayerVisibility.PVPlayer.contains(all)) {
    	        		if (!all.getName().equalsIgnoreCase(p.getName())) {
    	            		if (Bukkit.getVersion().contains("1.14") || Bukkit.getVersion().contains("1.13") || Bukkit.getVersion().contains("1.15")) {
    	            			all.hidePlayer(Main.getInstance(), p);
    	        			} else {
    	        				all.hidePlayer(p);
    	        			}
    	            		
    	            		for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
								if (p1.hasPermission("hawn.staff.seevanished")) {
									for (String s: OtherAMConfig.getConfig().getStringList("Vanish.Vanish-On")) {
										MessageUtils.ReplaceCharMessagePlayer(s.replaceAll("%player%", p.getName()), p1);
									}
								}
							}
    	        		}
    	            }
    	        }
            }
    }
    
    private static void FlyDoubleJumpnull(Player p) {
    	FlyCommand.player_list_flyc.remove(p);
        PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");

        if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
            if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
                if (PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
                    if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
                        if (p.hasPermission("hawn.fun.doublejump.double")) {
                            p.setAllowFlight(true);
                            PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                        } else {
                        	PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                        }
                    } else {
                        p.setAllowFlight(true);
                        PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                    }
                }
            } else {
                if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
                    if (p.hasPermission("hawn.fun.doublejump.double")) {
                        p.setAllowFlight(true);
                        PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                    } else {
                    	PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    }
                } else {
                    p.setAllowFlight(true);
                    PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                }
            }
        }

        // Fly on join
        if (OnJoinConfig.getConfig().getBoolean("Fly.Enable")) {
            if (p.hasPermission("hawn.onjoin.fly")) {
                if (!OnJoinConfig.getConfig().getBoolean("Fly.World.All_World")) {
                    if (OnJoinPW.getWflyoj().contains(p.getWorld().getName())) {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                        PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    }
                } else {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                    PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                }
            }
        }
    }
    
    private static void FlyDoubleJump(Player p) {
    	
    	if (PlayerOptionMainConfig.getConfig().getBoolean("Keep.DoubleJump-Fly-OnJoin.Enable")) {
    		if (PlayerOptionSQLClass.GetSQLPOFly(p).equalsIgnoreCase("TRUE")) {
    			p.setAllowFlight(true);
				p.setFlying(true);
				FlyCommand.player_list_flyc.add(p);
				PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
				if (ConfigMCommands.getConfig().getBoolean("Fly.Enable.Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Enable.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
    		} else if (PlayerOptionSQLClass.GetSQLPODoubleJump(p).equalsIgnoreCase("TRUE")) {
    			
    			if (!p.hasPermission("hawn.fun.doublejump.double")) {
    				return;
    			}
    			
    			if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
    				return;
    			}
    			
    			if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
					if (PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
						p.setAllowFlight(true);
						PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
						
						if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.DoubleJump.Enable.Enable")) {
							for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.DoubleJump.Enable.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					} else {
						if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.DoubleJump-Not-Good-World.Enable")) {
							for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.DoubleJump-Not-Good-World.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
						return;
					}
				} else {
					p.setAllowFlight(true);
					PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
					
					if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.DoubleJump.Enable.Enable")) {
						for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.DoubleJump.Enable.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
				}
    		} else {
    			PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
    			PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
    			FlyCommand.player_list_flyc.remove(p);
    			p.setAllowFlight(false);
				p.setFlying(false);
    		}
    	} else {
    		FlyCommand.player_list_flyc.remove(p);
            PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");

            if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
                if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
                    if (PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
                        if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
                            if (p.hasPermission("hawn.fun.doublejump.double")) {
                                p.setAllowFlight(true);
                                PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                            } else {
                            	PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                            }
                        } else {
                            p.setAllowFlight(true);
                            PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                        }
                    }
                } else {
                    if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
                        if (p.hasPermission("hawn.fun.doublejump.double")) {
                            p.setAllowFlight(true);
                            PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                        } else {
                        	PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                        }
                    } else {
                        p.setAllowFlight(true);
                        PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                    }
                }
            }

            // Fly on join
            if (OnJoinConfig.getConfig().getBoolean("Fly.Enable")) {
                if (p.hasPermission("hawn.onjoin.fly")) {
                    if (!OnJoinConfig.getConfig().getBoolean("Fly.World.All_World")) {
                        if (OnJoinPW.getWflyoj().contains(p.getWorld().getName())) {
                            p.setAllowFlight(true);
                            p.setFlying(true);
                            PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                            PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                        }
                    } else {
                        p.setAllowFlight(true);
                        p.setFlying(true);
                        PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                        PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    }
                }
            }
    	}
	}
    
}
