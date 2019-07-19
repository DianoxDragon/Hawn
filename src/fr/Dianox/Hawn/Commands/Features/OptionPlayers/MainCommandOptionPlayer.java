package fr.Dianox.Hawn.Commands.Features.OptionPlayers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Commands.Features.FlyCommand;
import fr.Dianox.Hawn.Event.CustomJoinItem.SpecialCJIPlayerVisibility;
import fr.Dianox.Hawn.Utility.ConfigPlayerGet;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.PlayerOptionSQLClass;
import fr.Dianox.Hawn.Utility.PlayerVisibility;
import fr.Dianox.Hawn.Utility.Config.Commands.OptionPlayerConfigCommand;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMPlayerOption;
import fr.Dianox.Hawn.Utility.World.PlayerEventsPW;

public class MainCommandOptionPlayer extends BukkitCommand {

	String GeneralPermission = "hawn.command.optionplayer.main";

	public MainCommandOptionPlayer(String name) {
		super(name);
		this.description = "Access to options";
        this.usageMessage = "/option";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {

		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (ConfigMOStuff.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Not-A-Player.Messages")) {
					MessageUtils.ReplaceMessageForConsole(msg);
				}
			}
			return true;
		}

		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.Enable")) {
			if (OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.Disable-Message")) {
				if (ConfigMOStuff.getConfig().getBoolean("Error.Command-Disable.Enable")) {
        			for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Command-Disable.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                	}
    			}
			}

			return true;
		}

		if (!p.hasPermission(GeneralPermission)) {
			MessageUtils.MessageNoPermission(p, GeneralPermission);
			return true;
		}

		// Worlds
		
		if (!OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.World.All_World")) {
			if (!PlayerEventsPW.getWJoinPlayerOption().contains(p.getWorld().getName())) {
				if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Not-Enable-In-A-World.Enable")) {
        			for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Not-Enable-In-A-World.Messages")) {
                		MessageUtils.ReplaceCharMessagePlayer(msg, p);
                	}
    			}
				return true;
			}
		}
		
		// The command
		if (args.length == 0) {
			if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Help.Enable")) {
				for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Help.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		} else {
			if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Help.Enable")) {
					for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Help.Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else if (args[0].equalsIgnoreCase("doublejump") || args[0].equalsIgnoreCase("dj")) {
				if (p.hasPermission("hawn.command.optionplayer.doublejump") && p.hasPermission("hawn.fun.doublejump.double")) {
					if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
						if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
							if (!PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.DoubleJump-Not-Good-World.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.DoubleJump-Not-Good-World.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
						}
						
						if (PlayerOptionSQLClass.GetSQLPOFly(p).equalsIgnoreCase("TRUE") || FlyCommand.player_list_flyc.contains(p)) {
							PlayerOptionSQLClass.SaveSQLPOFly(p, "FALSE");
							PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
							p.setAllowFlight(false);
							p.setFlying(false);
							FlyCommand.player_list_flyc.remove(p);
							
							if (ConfigMCommands.getConfig().getBoolean("Fly.Disable.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Disable.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
							
							p.setAllowFlight(true);
	    					PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");
	    					
	    					if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.DoubleJump.Enable.Enable")) {
								for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.DoubleJump.Enable.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else {
							if (PlayerOptionSQLClass.GetSQLPODoubleJump(p).equalsIgnoreCase("TRUE")) {
								PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
								p.setAllowFlight(false);
								p.setFlying(false);
								
								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.DoubleJump.Disable.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.DoubleJump.Disable.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
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
						}
					} else {
						if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.DoubleJump-Disabled.Enable")) {
							for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.DoubleJump-Disabled.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					}
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.doublejump or hawn.fun.doublejump.double");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("fly")) {
				p.performCommand("fly");
			} else if (args[0].equalsIgnoreCase("jumpboost")) {
				if (p.hasPermission("hawn.command.optionplayer.jumpboost")) {
					
					String value = PlayerOptionSQLClass.GetSQLPOJumpBoost(p);
					
					if (value.equalsIgnoreCase("TRUE")) {
						PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "FALSE");
						p.removePotionEffect(PotionEffectType.JUMP);
						
						if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.JumpBoost.Disable.Enable")) {
							for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.JumpBoost.Disable.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					} else {
						PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "TRUE");
						p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1999999999, OptionPlayerConfigCommand.getConfig().getInt("PlayerOption.Option.Jumpboost.Value")));
						
						if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.JumpBoost.Enable.Enable")) {
							for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.JumpBoost.Enable.Messages")) {
								MessageUtils.ReplaceCharMessagePlayer(msg, p);
							}
						}
					}
					
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.jumpboost");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("speed")) {
				if (p.hasPermission("hawn.command.optionplayer.speed")) {
					if (args.length == 2) {
						
						if (!ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_speed.Activate")) {
							if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Option-Disabled.Enable")) {
								for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Option-Disabled.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}

			        		return true;
			        	}

						try {
							@SuppressWarnings("unused")
							int i = Integer.parseInt(args[1]);

							ConfigPlayerGet.writeBoolean(p.getUniqueId().toString(), "player_speed.Activate", true);

							if (args[1].equalsIgnoreCase("1")) {
								p.setWalkSpeed(0.1F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 1);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(1)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("2")) {
								p.setWalkSpeed(0.2F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 2);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(2)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("3")) {
								p.setWalkSpeed(0.3F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 3);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(3)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("4")) {
								p.setWalkSpeed(0.4F);

								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 4);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(4)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("5")) {
								p.setWalkSpeed(0.5F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 5);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(5)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("6")) {
								p.setWalkSpeed(0.6F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 6);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(6)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("7")) {
								p.setWalkSpeed(0.7F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 7);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(7)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("8")) {
								p.setWalkSpeed(0.8F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 8);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(8)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("9")) {
								p.setWalkSpeed(0.9F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 9);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(9)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("10")) {
								p.setWalkSpeed(1.0F);
								
								PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", 10);

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(10)), p);
									}
								}
							} else {
								p.sendMessage("§c1-10");
							}

						} catch (NumberFormatException e) {

							p.sendMessage("§c/option speed <number>");
						}
					} else {

						String value = PlayerOptionSQLClass.GetSQLPOSpeed(p, "ACTIVATE");
						int speedvalue = Integer.valueOf(PlayerOptionSQLClass.GetSQLPOSpeed(p, "VALUE"));
						
						if (value.equalsIgnoreCase("TRUE")) {
							PlayerOptionSQLClass.SaveSQLPOSpeed(p, "FALSE", speedvalue);
							p.setWalkSpeed(0.2F);
							if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Disable.Enable")) {
								for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Disable.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else {
							if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
								 if (p.hasPermission("hawn.command.optionplayer.speed.priorityoptionplayer")) {
									PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", speedvalue);
									
									 if (speedvalue == 1) {
			                                p.setWalkSpeed(0.1F);
			                            } else if (speedvalue == 2) {
			                                p.setWalkSpeed(0.2F);
			                            } else if (speedvalue == 3) {
			                                p.setWalkSpeed(0.3F);
			                            } else if (speedvalue == 4) {
			                                p.setWalkSpeed(0.4F);
			                            } else if (speedvalue == 5) {
			                                p.setWalkSpeed(0.5F);
			                            } else if (speedvalue == 6) {
			                                p.setWalkSpeed(0.6F);
			                            } else if (speedvalue == 7) {
			                                p.setWalkSpeed(0.7F);
			                            } else if (speedvalue == 8) {
			                                p.setWalkSpeed(0.8F);
			                            } else if (speedvalue == 9) {
			                                p.setWalkSpeed(0.9F);
			                            } else if (speedvalue == 10) {
			                                p.setWalkSpeed(1.0F);
			                            } else {
			                                p.setWalkSpeed(0.2F);
			                            }
									 
									 if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Enable.Enable")) {
										 for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Enable.Messages")) {
											 MessageUtils.ReplaceCharMessagePlayer(msg, p);
										 }
									 }
								 } else {
									speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");
									
									PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", speedvalue);
									
									 if (speedvalue == 1) {
			                                p.setWalkSpeed(0.1F);
			                            } else if (speedvalue == 2) {
			                                p.setWalkSpeed(0.2F);
			                            } else if (speedvalue == 3) {
			                                p.setWalkSpeed(0.3F);
			                            } else if (speedvalue == 4) {
			                                p.setWalkSpeed(0.4F);
			                            } else if (speedvalue == 5) {
			                                p.setWalkSpeed(0.5F);
			                            } else if (speedvalue == 6) {
			                                p.setWalkSpeed(0.6F);
			                            } else if (speedvalue == 7) {
			                                p.setWalkSpeed(0.7F);
			                            } else if (speedvalue == 8) {
			                                p.setWalkSpeed(0.8F);
			                            } else if (speedvalue == 9) {
			                                p.setWalkSpeed(0.9F);
			                            } else if (speedvalue == 10) {
			                                p.setWalkSpeed(1.0F);
			                            } else {
			                                p.setWalkSpeed(0.2F);
			                            }
									 
									 if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Enable.Enable")) {
										 for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Enable.Messages")) {
											 MessageUtils.ReplaceCharMessagePlayer(msg, p);
										 }
									 }
								 }
							} else {
								speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

								 PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", speedvalue);

								 if (speedvalue == 1) {
		                                p.setWalkSpeed(0.1F);
		                            } else if (speedvalue == 2) {
		                                p.setWalkSpeed(0.2F);
		                            } else if (speedvalue == 3) {
		                                p.setWalkSpeed(0.3F);
		                            } else if (speedvalue == 4) {
		                                p.setWalkSpeed(0.4F);
		                            } else if (speedvalue == 5) {
		                                p.setWalkSpeed(0.5F);
		                            } else if (speedvalue == 6) {
		                                p.setWalkSpeed(0.6F);
		                            } else if (speedvalue == 7) {
		                                p.setWalkSpeed(0.7F);
		                            } else if (speedvalue == 8) {
		                                p.setWalkSpeed(0.8F);
		                            } else if (speedvalue == 9) {
		                                p.setWalkSpeed(0.9F);
		                            } else if (speedvalue == 10) {
		                                p.setWalkSpeed(1.0F);
		                            } else {
		                                p.setWalkSpeed(0.2F);
		                            }

								 if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Enable.Enable")) {
									 for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Enable.Messages")) {
										 MessageUtils.ReplaceCharMessagePlayer(msg, p);
									 }
								 }
							}
						}
					}
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.speed");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("pv")) {
				if (p.hasPermission("hawn.command.optionplayer.pv")) {
					if (PlayerVisibility.PVPlayer.contains(p)) {
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
							if (PlayerVisibility.Cooling().contains(p.getName())) {
								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
							    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
							    	}
								}
							} else {
								PlayerVisibility.Cooling().add(p.getName());
								PlayerVisibility.showPlayer(p);
								SpecialCJIPlayerVisibility.swithPVItemsOnJoinToOFF(p);

								SpecialCJIPlayerVisibility.messageitemPVOFF(p);

								PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");

								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

									@Override
									public void run() {
										PlayerVisibility.Cooling().remove(p.getName());
									}

								}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
								Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
							}
						} else {
							PlayerVisibility.showPlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToOFF(p);
							SpecialCJIPlayerVisibility.messageitemPVOFF(p);
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
						}
					} else {
						if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
							if (PlayerVisibility.Cooling().contains(p.getName())) {
								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
							    		MessageUtils.ReplaceCharMessagePlayer(msg, p);
							    	}
								}
							} else {
								PlayerVisibility.Cooling().add(p.getName());
								PlayerVisibility.hidePlayer(p);
								SpecialCJIPlayerVisibility.swithPVItemsOnJoinToON(p);

								SpecialCJIPlayerVisibility.messageitemPVON(p);

								PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");

								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

									@Override
									public void run() {
										PlayerVisibility.Cooling().remove(p.getName());
									}

								}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
								Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
							}
						} else {
							PlayerVisibility.hidePlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToON(p);
							SpecialCJIPlayerVisibility.messageitemPVON(p);
							PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
						}
					}
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.pv");
					return true;
				}
			}
		}

		return false;
	}
}
