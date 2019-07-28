package fr.Dianox.Hawn.Event;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import fr.Dianox.Hawn.Commands.Features.FlyCommand;
import fr.Dianox.Hawn.Event.CustomJoinItem.SpecialCJIPlayerVisibility;
import fr.Dianox.Hawn.Utility.PlayerOptionSQLClass;
import fr.Dianox.Hawn.Utility.PlayerVisibility;
import fr.Dianox.Hawn.Utility.Config.Events.PlayerWorldChangeConfigE;
import fr.Dianox.Hawn.Utility.World.ChangeWorldPW;

public class PlayerChangeWorld implements Listener {

    @EventHandler
    public void onKeepFly(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.Enable")) {
            if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.World.All_World")) {
                if (ChangeWorldPW.getWPO().contains(p.getWorld().getName()) && PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-When-Enter-Or-Leave-A-World.False-Is-Leave")) {
                	if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.Keep-Options")) {
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.GameMode.Enable")) {
                    		if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 0) {
                                p.setGameMode(GameMode.SURVIVAL);
                            } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 1) {
                                p.setGameMode(GameMode.CREATIVE);
                            } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 2) {
                                p.setGameMode(GameMode.ADVENTURE);
                            } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 3) {
                                p.setGameMode(GameMode.SPECTATOR);
                            }
                    	}
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.Fly")) {
                    		p.setAllowFlight(true);
                            p.setFlying(true);
                            FlyCommand.player_list_flyc.add(p);
                            
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                            	PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                    		}
                    	} else {
                    		if (FlyCommand.player_list_flyc.contains(p)) {
                    			FlyCommand.player_list_flyc.remove(p);
                    		}
                    		
                    		p.setAllowFlight(false);
                            p.setFlying(false);
                            
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                            	PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
                    		}
                    	}
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.DoubleJump")) {
                    		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.Fly")) {
                    			if (FlyCommand.player_list_flyc.contains(p)) {
                                    FlyCommand.player_list_flyc.remove(p);
                                }
                    			
                    			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                                	PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
                        		}
                    		}
                    		
                    		p.setAllowFlight(true);
                			p.setFlying(false);
                			
                			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                    		}
                    	} else {
                    		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    		}
                    	}
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility")) {
                    		PlayerVisibility.hidePlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToON(p);
							SpecialCJIPlayerVisibility.messageitemPVON(p);
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
                    	} else {
                    		PlayerVisibility.showPlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToOFF(p);
							SpecialCJIPlayerVisibility.messageitemPVOFF(p);
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
                    	}
                    }
                } else if (!ChangeWorldPW.getWPO().contains(p.getWorld().getName()) && !PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-When-Enter-Or-Leave-A-World.False-Is-Leave")) {
                	if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.Keep-Options")) {
                		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.GameMode.Enable")) {
                    		if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 0) {
                                p.setGameMode(GameMode.SURVIVAL);
                            } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 1) {
                                p.setGameMode(GameMode.CREATIVE);
                            } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 2) {
                                p.setGameMode(GameMode.ADVENTURE);
                            } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 3) {
                                p.setGameMode(GameMode.SPECTATOR);
                            }
                    	}
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.Fly")) {
                    		p.setAllowFlight(true);
                            p.setFlying(true);
                            FlyCommand.player_list_flyc.add(p);
                            
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                            	PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                    		}
                    	} else {
                    		if (FlyCommand.player_list_flyc.contains(p)) {
                    			FlyCommand.player_list_flyc.remove(p);
                    		}
                    		
                    		p.setAllowFlight(false);
                            p.setFlying(false);
                            
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                            	PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
                    		}
                    	}
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.DoubleJump")) {
                    		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.Fly")) {
                    			if (FlyCommand.player_list_flyc.contains(p)) {
                                    FlyCommand.player_list_flyc.remove(p);
                                }
                    			
                    			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                                	PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
                        		}
                    		}
                    		
                    		p.setAllowFlight(true);
                			p.setFlying(false);
                			
                			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                    		}
                    	} else {
                    		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    		}
                    	}
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility")) {
                    		PlayerVisibility.hidePlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToON(p);
							SpecialCJIPlayerVisibility.messageitemPVON(p);
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
                    	} else {
                    		PlayerVisibility.showPlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToOFF(p);
							SpecialCJIPlayerVisibility.messageitemPVOFF(p);
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
                    	}
                    }
                }
            } else {
                if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.Keep-Options")) {
                	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.GameMode.Enable")) {
                		if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 0) {
                            p.setGameMode(GameMode.SURVIVAL);
                        } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 1) {
                            p.setGameMode(GameMode.CREATIVE);
                        } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 2) {
                            p.setGameMode(GameMode.ADVENTURE);
                        } else if (PlayerWorldChangeConfigE.getConfig().getInt("Player-Options.If-Not-Keeping.Options-Default.GameMode.Value") == 3) {
                            p.setGameMode(GameMode.SPECTATOR);
                        }
                	}
                	
                	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.Fly")) {
                		p.setAllowFlight(true);
                        p.setFlying(true);
                        FlyCommand.player_list_flyc.add(p);
                        
                        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                        	PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                		}
                	} else {
                		if (FlyCommand.player_list_flyc.contains(p)) {
                			FlyCommand.player_list_flyc.remove(p);
                		}
                		
                		p.setAllowFlight(false);
                        p.setFlying(false);
                        
                        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                        	PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
                		}
                	}
                	
                	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.DoubleJump")) {
                		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.Fly")) {
                			if (FlyCommand.player_list_flyc.contains(p)) {
                                FlyCommand.player_list_flyc.remove(p);
                            }
                			
                			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                            	PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
                    		}
                		}
                		
                		p.setAllowFlight(true);
            			p.setFlying(false);
            			
            			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
            				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                		}
                	} else {
                		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
            				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                		}
                	}
                	
                	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility")) {
                		PlayerVisibility.hidePlayer(p);
						SpecialCJIPlayerVisibility.swithPVItemsOnJoinToON(p);
						SpecialCJIPlayerVisibility.messageitemPVON(p);
						PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
                	} else {
                		PlayerVisibility.showPlayer(p);
						SpecialCJIPlayerVisibility.swithPVItemsOnJoinToOFF(p);
						SpecialCJIPlayerVisibility.messageitemPVOFF(p);
						PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
                	}
                }
            }
        }

        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.Enable")) {
            if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.Player-Options-primary")) {
                if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.World.All_World")) {
                    if (ChangeWorldPW.getWFlyKeepOnChangeWorld().contains(p.getWorld().getName())) {
                        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.SetFlyOnChangeWorld")) {
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Cancel-Event-If-Player-Is-In.Gamemode-Creative-Spectator")) {
                                if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
                                    return;
                                }
                            }

                            p.setAllowFlight(true);
                            p.setFlying(true);
                            FlyCommand.player_list_flyc.add(p);
                        }
                    } else {
                        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.DisableFlyIfAWorldIsNotListed")) {
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Cancel-Event-If-Player-Is-In.Gamemode-Creative-Spectator")) {
                                if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
                                    return;
                                }
                            }

                            if (FlyCommand.player_list_flyc.contains(p)) {
                                FlyCommand.player_list_flyc.remove(p);
                            }
                            
                            p.setAllowFlight(false);
                            p.setFlying(false);

                        }
                    }
                }
            }
        }



        // Gamemode
        if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.Enable")) {
            if (!PlayerWorldChangeConfigE.getConfig().getBoolean("GM.Player-Options-primary")) {
                if (!PlayerWorldChangeConfigE.getConfig().getBoolean("GM.World.All_World")) {
                    if (ChangeWorldPW.getWGamemodeOnChangeWorld().contains(p.getWorld().getName())) {
                        if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.Enable")) {
                            if (p.getGameMode() == GameMode.SURVIVAL) {
                                if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Survival")) {
                                    if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                        p.setGameMode(GameMode.SURVIVAL);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                        p.setGameMode(GameMode.CREATIVE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                        p.setGameMode(GameMode.ADVENTURE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                        p.setGameMode(GameMode.SPECTATOR);
                                    }
                                }
                            } else if (p.getGameMode() == GameMode.CREATIVE) {
                                if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Creative")) {
                                    if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                        p.setGameMode(GameMode.SURVIVAL);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                        p.setGameMode(GameMode.CREATIVE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                        p.setGameMode(GameMode.ADVENTURE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                        p.setGameMode(GameMode.SPECTATOR);
                                    }
                                }
                            } else if (p.getGameMode() == GameMode.ADVENTURE) {
                                if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Adventure")) {
                                    if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                        p.setGameMode(GameMode.SURVIVAL);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                        p.setGameMode(GameMode.CREATIVE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                        p.setGameMode(GameMode.ADVENTURE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                        p.setGameMode(GameMode.SPECTATOR);
                                    }
                                }
                            } else if (p.getGameMode() == GameMode.SPECTATOR) {
                                if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Spectator")) {
                                    if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                        p.setGameMode(GameMode.SURVIVAL);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                        p.setGameMode(GameMode.CREATIVE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                        p.setGameMode(GameMode.ADVENTURE);
                                    } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                        p.setGameMode(GameMode.SPECTATOR);
                                    }
                                }
                            }
                        }
                    } else {
                        if (p.getGameMode() == GameMode.SURVIVAL) {
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Survival")) {
                                if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                    p.setGameMode(GameMode.SURVIVAL);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                    p.setGameMode(GameMode.CREATIVE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                    p.setGameMode(GameMode.ADVENTURE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                    p.setGameMode(GameMode.SPECTATOR);
                                }
                            }
                        } else if (p.getGameMode() == GameMode.CREATIVE) {
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Creative")) {
                                if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                    p.setGameMode(GameMode.SURVIVAL);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                    p.setGameMode(GameMode.CREATIVE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                    p.setGameMode(GameMode.ADVENTURE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                    p.setGameMode(GameMode.SPECTATOR);
                                }
                            }
                        } else if (p.getGameMode() == GameMode.ADVENTURE) {
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Adventure")) {
                                if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                    p.setGameMode(GameMode.SURVIVAL);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                    p.setGameMode(GameMode.CREATIVE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                    p.setGameMode(GameMode.ADVENTURE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                    p.setGameMode(GameMode.SPECTATOR);
                                }
                            }
                        } else if (p.getGameMode() == GameMode.SPECTATOR) {
                            if (PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have.Spectator")) {
                                if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 0) {
                                    p.setGameMode(GameMode.SURVIVAL);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 1) {
                                    p.setGameMode(GameMode.CREATIVE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 2) {
                                    p.setGameMode(GameMode.ADVENTURE);
                                } else if (PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode") == 3) {
                                    p.setGameMode(GameMode.SPECTATOR);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
