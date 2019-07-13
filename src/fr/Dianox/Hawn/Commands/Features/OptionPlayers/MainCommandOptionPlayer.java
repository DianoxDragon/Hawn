package fr.Dianox.Hawn.Commands.Features.OptionPlayers;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.SQL;
import fr.Dianox.Hawn.Event.CustomJoinItem.SpecialCJIPlayerVisibility;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.PlayerOptionSQLClass;
import fr.Dianox.Hawn.Utility.PlayerVisibility;
import fr.Dianox.Hawn.Utility.Config.PlayerConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.OptionPlayerConfigCommand;
import fr.Dianox.Hawn.Utility.Config.CustomJoinItem.SpecialCjiHidePlayers;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMPlayerOption;

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

						if (!PlayerConfig.getConfig().getBoolean("player_speed."+p.getUniqueId()+".Activate")) {
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

							PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(true));
							PlayerConfig.saveConfigFile();

							if (args[1].equalsIgnoreCase("1")) {
								p.setWalkSpeed(0.1F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 1);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "1", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '1', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "1", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '1', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(1)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("2")) {
								p.setWalkSpeed(0.2F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 2);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "2", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '2', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "2", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '2', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(2)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("3")) {
								p.setWalkSpeed(0.3F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 3);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "3", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '3', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "3", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '3', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(3)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("4")) {
								p.setWalkSpeed(0.4F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 4);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "4", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '4', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "4", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                        SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '4', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(4)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("5")) {
								p.setWalkSpeed(0.5F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 5);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "5", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '5', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "5", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                        SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '5', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(5)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("6")) {
								p.setWalkSpeed(0.6F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 6);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "6", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '6', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "6", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                        SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '6', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(6)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("7")) {
								p.setWalkSpeed(0.7F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 7);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "7", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '7', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "7", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                        SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '7', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(7)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("8")) {
								p.setWalkSpeed(0.8F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 8);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "8", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '8', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "8", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                        SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '8', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(8)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("9")) {
								p.setWalkSpeed(0.9F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 9);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "9", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '9', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "9", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                        SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '9', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

								if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
									for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%arg1%", String.valueOf(9)), p);
									}
								}
							} else if (args[1].equalsIgnoreCase("10")) {
								p.setWalkSpeed(1.0F);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", 10);
								PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));

								PlayerConfig.saveConfigFile();

								if (!Main.useyamllistplayer) {
									if (SQL.tableExists("player_speed")) {
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "10", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                    	SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '10', 'TRUE' ", "player_speed");
	                                    }
	                                } else {
	                                	SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
	                                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
	                                        SQL.set("player_speed", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
	                                        SQL.set("player_speed", "value", "10", "player_UUID", "" + p.getUniqueId() + "");
	                                    } else {
	                                        SQL.insertData("player, player_UUID, value, Activate",
	                                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '10', 'TRUE' ", "player_speed");
	                                    }
	                                }
								}

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

						if (!PlayerConfig.getConfig().isSet("player_speed."+p.getUniqueId()+".value")) {
							PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".value", OnJoinConfig.getConfig().getInt("Speed.Value"));
                            PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".player_name", String.valueOf(p.getName()));
                            PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(true));

                            PlayerConfig.saveConfigFile();
						}

						if (Main.useyamllistplayer) {

							if (PlayerConfig.getConfig().getBoolean("player_speed."+p.getUniqueId()+".Activate")) {
								 PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
								 p.setWalkSpeed(0.2F);

								 if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Disable.Enable")) {
									 for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Disable.Messages")) {
										 MessageUtils.ReplaceCharMessagePlayer(msg, p);
									 }
								 }

								 PlayerConfig.saveConfigFile();
							} else {
								 PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(true));

								 if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
									 if (p.hasPermission("hawn.command.optionplayer.speed.priorityoptionplayer")) {
										 int speedvalue = PlayerConfig.getConfig().getInt("player_speed."+p.getUniqueId()+".value");

										 PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(true));

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

										 PlayerConfig.saveConfigFile();
									 } else {
										 int speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

										 PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(true));

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

										 PlayerConfig.saveConfigFile();
									 }
								 } else {
									 int speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

									 PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(true));

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

									 PlayerConfig.saveConfigFile();
								 }
							}
						} else {
							if (!SQL.tableExists("player_speed")) {
								SQL.createTable("player_speed", "player TEXT, player_UUID TEXT, value INT, Activate TEXT");
							}

							if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_speed")) {
								String value = SQL.getInfoString("player_speed", "Activate", "" + p.getUniqueId() + "");
								 PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(false));
								 PlayerConfig.saveConfigFile();

								if (value.equalsIgnoreCase("true")) {
									SQL.set("player_speed", "Activate", "FALSE", "player_UUID", "" + p.getUniqueId() + "");
									 p.setWalkSpeed(0.2F);

									 if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Disable.Enable")) {
										 for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Disable.Messages")) {
											 MessageUtils.ReplaceCharMessagePlayer(msg, p);
										 }
									 }
								} else {

									SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

									PlayerConfig.getConfig().set("player_speed."+p.getUniqueId()+".Activate", Boolean.valueOf(true));
									 PlayerConfig.saveConfigFile();

									if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
										 if (p.hasPermission("hawn.command.optionplayer.speed.priorityoptionplayer")) {
											 int speedvalue  = Integer.valueOf(SQL.getInfoInt("player_gamemode", "gamemode_state", "" + p.getUniqueId() + ""));

											 SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

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
											 int speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

											 SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

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
										int speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

										 SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

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
							} else {

								SQL.insertData("player, player_UUID, gamemode_state",
	                                    " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + PlayerConfig.getConfig().getInt("player_gamemode." + p.getUniqueId() + ".player_gamemode") + "' ", "player_gamemode");

								String value = SQL.getInfoString("player_speed", "Activate", "" + p.getUniqueId() + "");

								if (value.equalsIgnoreCase("true")) {
									SQL.set("player_speed", "Activate", "FALSE", "player_UUID", "" + p.getUniqueId() + "");
									 p.setWalkSpeed(0.2F);

									 if (ConfigMPlayerOption.getConfig().getBoolean("PlayerOption.Speed.Disable.Enable")) {
										 for (String msg: ConfigMPlayerOption.getConfig().getStringList("PlayerOption.Speed.Disable.Messages")) {
											 MessageUtils.ReplaceCharMessagePlayer(msg, p);
										 }
									 }
								} else {

									SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

									if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option")) {
										 if (p.hasPermission("hawn.command.optionplayer.speed.priorityoptionplayer")) {
											 int speedvalue  = Integer.valueOf(SQL.getInfoInt("player_gamemode", "gamemode_state", "" + p.getUniqueId() + ""));

											 SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

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
											 int speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

											 SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

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
										int speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

										 SQL.set("player_speed", "Activate", "TRUE", "player_UUID", "" + p.getUniqueId() + "");

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

								SpecialCJIPlayerVisibility.onMysqlYamlCJIChange(p, "FALSE");

								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

									@Override
									public void run() {
										PlayerVisibility.Cooling().remove(p.getName());
									}

								}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
							}
						} else {
							PlayerVisibility.showPlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToOFF(p);
							SpecialCJIPlayerVisibility.messageitemPVOFF(p);
							SpecialCJIPlayerVisibility.onMysqlYamlCJIChange(p, "FALSE");
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

								SpecialCJIPlayerVisibility.onMysqlYamlCJIChange(p, "TRUE");

								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

									@Override
									public void run() {
										PlayerVisibility.Cooling().remove(p.getName());
									}

								}, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay")*20);
							}
						} else {
							PlayerVisibility.hidePlayer(p);
							SpecialCJIPlayerVisibility.swithPVItemsOnJoinToON(p);
							SpecialCJIPlayerVisibility.messageitemPVON(p);
							SpecialCJIPlayerVisibility.onMysqlYamlCJIChange(p, "TRUE");
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
