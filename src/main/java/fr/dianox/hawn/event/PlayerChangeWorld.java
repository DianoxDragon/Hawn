package fr.dianox.hawn.event;

import com.cryptomorin.xseries.XPotion;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.dianox.hawn.command.commands.FlyCommand;
import fr.dianox.hawn.modules.onjoin.cji.SpecialItemPlayerVisibility;
import fr.dianox.hawn.utility.BossBarApi;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.PlayerVisibility;
import fr.dianox.hawn.utility.config.configs.AutoBroadcastConfig;
import fr.dianox.hawn.utility.config.configs.commands.OptionPlayerConfigCommand;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.events.PlayerWorldChangeConfigE;
import fr.dianox.hawn.utility.world.ChangeWorldPW;
import fr.dianox.hawn.utility.world.OnJoinPW;

public class PlayerChangeWorld implements Listener {

	@EventHandler
    public void onBossBarChangeWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		
		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Enable")) {
        	if (!OnJoinConfig.getConfig().getBoolean("Boss-Bar.World.All_World")) {
                if (OnJoinPW.getBB().contains(p.getWorld().getName())) {
                	
                	if (!OnJoinConfig.getConfig().getBoolean("Boss-Bar.World.Keep-BossBar-For-Theses-Worlds")) {
            			return;
            		}
                	
                	if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Time.Keep-Bar")) {
                		
                		BossBarApi.BBBlock.add(p);
                		
                		if (!OnJoinConfig.getConfig().isSet("Boss-Bar.Join.Message")) {
                			return;
                		}
                    	
                		double progress = 1D;
                		
                		if (OnJoinConfig.getConfig().isSet("Boss-Bar.Join.Progress")) {
                			progress = OnJoinConfig.getConfig().getDouble("Boss-Bar.Join.Progress");
                		}
                		
                    	BossBarApi.createnewbar(p, OnJoinConfig.getConfig().getString("Boss-Bar.Join.Color"), 
                    			OnJoinConfig.getConfig().getString("Boss-Bar.Join.Message"), OnJoinConfig.getConfig().getString("Boss-Bar.Join.Style"), (float) progress);
                	}
                } else {
                	if (BossBarApi.BBBlock.contains(p)) {
                		BossBarApi.BBBlock.remove(p);
                		BossBarApi.deletebar(p);
                	} else if (!AutoBroadcastConfig.getConfig().getBoolean("Config.BossBar.Enable")) {
                		BossBarApi.deletebar(p);
                	}
                }
        	}
		} else {
			if (BossBarApi.BBBlock.contains(p)) {
        		BossBarApi.BBBlock.remove(p);
        		BossBarApi.deletebar(p);
        	} else {
        		BossBarApi.deletebar(p);
        	}
		}
	}
	
	@EventHandler
    public void onCommandexecutor(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		
		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Execute-Command.Enable")) {
            if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Execute-Command.World.All_World")) {
            	if (ChangeWorldPW.getCommands().contains(p.getWorld().getName())) {
            		String worldname = p.getWorld().getName();
            		            		
            		if (PlayerWorldChangeConfigE.getConfig().isSet("Execute-Command.Options.When-Enter-in-The-World."+worldname+".Enable")) {
            			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Execute-Command.Options.When-Enter-in-The-World."+worldname+".Enable")) {
            				for (String s: PlayerWorldChangeConfigE.getConfig().getStringList("Execute-Command.Options.When-Enter-in-The-World."+worldname+".Command-List")) {
            					ConfigEventUtils.ExecuteEvent(p, s, "none", "PlayerChangedWorldEvent", false);
                    		}
            			}
            		}
            	}
            } else {
            	String worldname = p.getWorld().getName();
        		
        		if (PlayerWorldChangeConfigE.getConfig().isSet("Execute-Command.Options.When-Enter-in-The-World."+worldname+".Enable")) {
        			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Execute-Command.Options.When-Enter-in-The-World."+worldname+".Enable")) {
        				for (String s: PlayerWorldChangeConfigE.getConfig().getStringList("Execute-Command.Options.When-Enter-in-The-World."+worldname+".Command-List")) {
        					ConfigEventUtils.ExecuteEvent(p, s, "none", "PlayerChangedWorldEvent", false);
                		}
        			}
        		}
            }
		}
	}
	
    /**
     * The join items of the new world (Custom-Join-Item.General-Option.Change-World, since 1.5).
     */
    @EventHandler
    public void onJoinItems(PlayerChangedWorldEvent e) {
        fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem.onWorldChange(e.getPlayer());
    }

    @EventHandler
    public void onCW(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();

        gamemode(p);

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
                    		
                    		if (FunFeatures.canDoubleJumpHere(p)) {
                    			FunFeatures.allowDoubleJumpFlight(p);
                    		}
                			p.setFlying(false);
                			
                			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                    		}
                    	} else {
                    		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    		}
                    	}
                    	
                    	// true: nobody is shown, false: everyone
                    	SpecialItemPlayerVisibility.set(p, PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility")
                    	        ? PlayerVisibility.Mode.NONE : PlayerVisibility.Mode.ALL, SpecialItemPlayerVisibility.Source.WORLD);
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.JumpBoost")) {
                    		PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "TRUE");
    						p.addPotionEffect(new PotionEffect(XPotion.JUMP_BOOST.get(), 1999999999, OptionPlayerConfigCommand.getConfig().getInt("PlayerOption.Option.Jumpboost.Value")));
                    	} else {
                    		PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "FALSE");
    						p.removePotionEffect(XPotion.JUMP_BOOST.get());
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
                    		
                    		if (FunFeatures.canDoubleJumpHere(p)) {
                    			FunFeatures.allowDoubleJumpFlight(p);
                    		}
                			p.setFlying(false);
                			
                			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                    		}
                    	} else {
                    		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
                				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    		}
                    	}
                    	
                    	// true: nobody is shown, false: everyone
                    	SpecialItemPlayerVisibility.set(p, PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility")
                    	        ? PlayerVisibility.Mode.NONE : PlayerVisibility.Mode.ALL, SpecialItemPlayerVisibility.Source.WORLD);
                    	
                    	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.JumpBoost")) {
                    		PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "TRUE");
    						p.addPotionEffect(new PotionEffect(XPotion.JUMP_BOOST.get(), 1999999999, OptionPlayerConfigCommand.getConfig().getInt("PlayerOption.Option.Jumpboost.Value")));
                    	} else {
                    		PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "FALSE");
    						p.removePotionEffect(XPotion.JUMP_BOOST.get());
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
                		
                		if (FunFeatures.canDoubleJumpHere(p)) {
                			FunFeatures.allowDoubleJumpFlight(p);
                		}
            			p.setFlying(false);
            			
            			if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
            				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
                		}
                	} else {
                		if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Reset-settings-on-world-change")) {
            				PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                		}
                	}
                	
                	// true: nobody is shown, false: everyone
                	SpecialItemPlayerVisibility.set(p, PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.PlayerVisibility")
                	        ? PlayerVisibility.Mode.NONE : PlayerVisibility.Mode.ALL, SpecialItemPlayerVisibility.Source.WORLD);
                	
                	if (PlayerWorldChangeConfigE.getConfig().getBoolean("Player-Options.If-Not-Keeping.Options-Default.JumpBoost")) {
                		PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "TRUE");
						p.addPotionEffect(new PotionEffect(XPotion.JUMP_BOOST.get(), 1999999999, OptionPlayerConfigCommand.getConfig().getInt("PlayerOption.Option.Jumpboost.Value")));
                	} else {
                		PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "FALSE");
						p.removePotionEffect(XPotion.JUMP_BOOST.get());
                	}
                }
            }
        }

        fly(p);
    }

    private static final GameMode[] MODES = {GameMode.SURVIVAL, GameMode.CREATIVE, GameMode.ADVENTURE, GameMode.SPECTATOR};

    private static boolean listed(String option, java.util.List<String> worlds, Player p) {
        return PlayerWorldChangeConfigE.getConfig().getBoolean(option + ".World.All_World") || worlds.contains(p.getWorld().getName());
    }

    /**
     * GM: the gamemode in the listed worlds (or in all of them with All_World). The other worlds are not touched: before 1.4.9
     * they were the ones changed, and All_World did nothing. First, because a new gamemode removes the fly.
     */
    private static void gamemode(Player p) {
        if (!PlayerWorldChangeConfigE.getConfig().getBoolean("GM.Enable") || !PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.Enable")) {
            return;
        }

        if (!listed("GM", ChangeWorldPW.getWGamemodeOnChangeWorld(), p)) {
            return;
        }

        String now = p.getGameMode().name().charAt(0) + p.getGameMode().name().substring(1).toLowerCase(java.util.Locale.ROOT);
        if (!PlayerWorldChangeConfigE.getConfig().getBoolean("GM.CustomMode.If-player-have." + now)) {
            return;
        }

        int value = PlayerWorldChangeConfigE.getConfig().getInt("GM.CustomMode.GameMode");
        if (value >= 0 && value < MODES.length) {
            p.setGameMode(MODES[value]);
        }
    }

    /**
     * Fly: given in the listed worlds (or in all of them with All_World, which did nothing before 1.4.9), removed in the others.
     */
    private static void fly(Player p) {
        if (!PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.Enable")) {
            return;
        }

        if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Cancel-Event-If-Player-Is-In.Gamemode-Creative-Spectator")
                && (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR)) {
            return;
        }

        if (listed("Fly", ChangeWorldPW.getWFlyKeepOnChangeWorld(), p)) {
            if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.SetFlyOnChangeWorld")) {
                p.setAllowFlight(true);
                p.setFlying(true);
                if (!FlyCommand.player_list_flyc.contains(p)) FlyCommand.player_list_flyc.add(p);
            }
        } else if (PlayerWorldChangeConfigE.getConfig().getBoolean("Fly.Enable.DisableFlyIfAWorldIsNotListed")) {
            FlyCommand.player_list_flyc.remove(p);
            p.setAllowFlight(false);
            p.setFlying(false);
        }
    }

}