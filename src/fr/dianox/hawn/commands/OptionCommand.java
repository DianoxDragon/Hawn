package fr.dianox.hawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.modules.onjoin.cji.SpecialItemPlayerVisibility;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.PlayerVisibility;
import fr.dianox.hawn.utility.config.commands.OptionPlayerConfigCommand;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.customjoinitem.SpecialCjiHidePlayers;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.PlayerEventsPW;

public class OptionCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.optionplayer.main";

    public OptionCommand(String name) {
        super(name);
        this.description = "Access to options";
        this.usageMessage = "/option";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
            if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
                for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
                    MessageUtils.ConsoleMessages(msg);
                }
            }
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.Enable")) {
            if (OptionPlayerConfigCommand.getConfig().getBoolean("PlayerOption.Disable-Message")) {
                if (ConfigMMsg.getConfig().getBoolean("Error.Command-Disable.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Error.Command-Disable.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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
                if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Not-Enable-In-A-World.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Not-Enable-In-A-World.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
                }
                return true;
            }
        }

        // The command
        if (args.length == 0) {
            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Help.Enable")) {
                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Help.Messages")) {
                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                }
            }
        } else {
            if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
                if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Help.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Help.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
                }
            } else if (args[0].equalsIgnoreCase("doublejump") || args[0].equalsIgnoreCase("dj")) {
                if (!p.hasPermission("hawn.command.optionplayer.doublejump") && !p.hasPermission("hawn.fun.doublejump.double")) {
                    MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.doublejump & hawn.fun.doublejump.double");

                    return true;
                }

                if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
                    if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
                        if (!PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
                            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.DoubleJump-Not-Good-World.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.DoubleJump-Not-Good-World.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
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

                        if (ConfigMMsg.getConfig().getBoolean("Fly.Disable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Disable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }

                        p.setAllowFlight(true);
                        PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");

                        if (ConfigMMsg.getConfig().getBoolean("PlayerOption.DoubleJump.Enable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.DoubleJump.Enable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
                    } else {
                        if (PlayerOptionSQLClass.GetSQLPODoubleJump(p).equalsIgnoreCase("TRUE")) {
                            PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                            p.setAllowFlight(false);
                            p.setFlying(false);

                            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.DoubleJump.Disable.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.DoubleJump.Disable.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
                        } else {
                            p.setAllowFlight(true);
                            PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "TRUE");

                            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.DoubleJump.Enable.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.DoubleJump.Enable.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
                        }
                    }
                } else {
                    if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.DoubleJump-Disabled.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.DoubleJump-Disabled.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
                }

            } else if (args[0].equalsIgnoreCase("fly")) {
                if (!p.hasPermission("hawn.command.optionplayer.fly")) {
                    MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.fly");

                    return true;
                }
                if (p.getAllowFlight() && (FlyCommand.player_list_flyc.contains(p) || ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_option_fly.Activate"))) {
                    p.setAllowFlight(false);
                    p.setFlying(false);
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

                    if (ConfigMMsg.getConfig().getBoolean("Fly.Disable.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Disable.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
                } else {
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    FlyCommand.player_list_flyc.add(p);
                    PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
                    PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
                    if (ConfigMMsg.getConfig().getBoolean("Fly.Enable.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Enable.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("jumpboost")) {
                if (!p.hasPermission("hawn.command.optionplayer.jumpboost")) {
                    MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.jumpboost");

                    return true;
                }

                String value = PlayerOptionSQLClass.GetSQLPOJumpBoost(p);

                if (value.equalsIgnoreCase("TRUE")) {
                    PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "FALSE");
                    p.removePotionEffect(PotionEffectType.JUMP);

                    if (ConfigMMsg.getConfig().getBoolean("PlayerOption.JumpBoost.Disable.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.JumpBoost.Disable.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
                } else {
                    PlayerOptionSQLClass.SaveSQLPOJumpBoost(p, "TRUE");
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1999999999, OptionPlayerConfigCommand.getConfig().getInt("PlayerOption.Option.Jumpboost.Value")));

                    if (ConfigMMsg.getConfig().getBoolean("PlayerOption.JumpBoost.Enable.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.JumpBoost.Enable.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("autobc")) {
                if (!p.hasPermission("hawn.command.optionplayer.autobc")) {
                    MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.autobc");

                    return true;
                }


                String value = PlayerOptionSQLClass.GetSQLPOautobc(p);

                if (value.equalsIgnoreCase("TRUE")) {
                    PlayerOptionSQLClass.SaveSQLPOautobc(p, "FALSE");

                    if (ConfigMMsg.getConfig().getBoolean("PlayerOption.AutoBroadcast.Disable.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.AutoBroadcast.Disable.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
                } else {
                    PlayerOptionSQLClass.SaveSQLPOautobc(p, "TRUE");

                    if (ConfigMMsg.getConfig().getBoolean("PlayerOption.AutoBroadcast.Enable.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.AutoBroadcast.Enable.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("flyspeed") || args[0].equalsIgnoreCase("fs")) {
				if (!p.hasPermission("hawn.command.optionplayer.flyspeed")) {
					MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.flyspeed");
					
					return true;
				}

				if (args.length == 2) {
					if (!ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_fly_speed.Activate")) {
                        if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Option-Disabled.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Option-Disabled.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }

                        return true;
                    }
					
					try {
						@SuppressWarnings("unused")
						int i = Integer.parseInt(args[1]);
						
						ConfigPlayerGet.writeBoolean(p.getUniqueId().toString(), "player_fly_speed.Activate", true);
						Float FloatSpeed = (float) Integer.valueOf(args[1]) / 10;
						
						if (Integer.valueOf(args[1]) < 0 || Integer.valueOf(args[1]) > 10) {
							p.sendMessage("§c0-10");
							return false;
						}
						
						p.setFlySpeed(FloatSpeed);
						PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "TRUE", Integer.valueOf(args[1]));
						
						if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Set.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Set.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", String.valueOf(args[1])), "", "", false);
							}
						}
					} catch (NumberFormatException e) {
						p.sendMessage("§c/option fs <number>");
					}
				} else {
					String value = PlayerOptionSQLClass.GetSQLPOFlySpeed(p, "ACTIVATE");
					int speedvalue = Integer.valueOf(PlayerOptionSQLClass.GetSQLPOFlySpeed(p, "VALUE"));
					
					if (value.equalsIgnoreCase("TRUE")) {
						PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "FALSE", speedvalue);
                        p.setFlySpeed(0.1F);
                        if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Disable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Disable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
					} else {
						if (OnJoinConfig.getConfig().getBoolean("FlySpeed.Option.Priority-For-Player-Option") && p.hasPermission("hawn.command.optionplayer.flyspeed.priorityoptionplayer")) {
							PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "TRUE", speedvalue);

							if (speedvalue < 0 || speedvalue > 10) {
								p.setFlySpeed(0.1F);
							} else {
								Float FlySpeed = (float) speedvalue / 10;
								p.setFlySpeed(FlySpeed);
							}

							if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Enable.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Enable.Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
								}
							}
                        } else {
                            speedvalue = OnJoinConfig.getConfig().getInt("FlySpeed.Value");

                            PlayerOptionSQLClass.SaveSQLPOFlySpeed(p, "TRUE", speedvalue);

                            if (speedvalue < 0 || speedvalue > 10) {
                                p.setFlySpeed(0.1F);
                            } else {
                                Float FlySpeed = (float) speedvalue / 10;
                                p.setFlySpeed(FlySpeed);
                            }

                            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.FlySpeed.Enable.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.FlySpeed.Enable.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
                        }
					}
				}
            } else if (args[0].equalsIgnoreCase("speed")) {
                if (!p.hasPermission("hawn.command.optionplayer.speed")) {
                    MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.speed");

                    return true;
                }

                if (args.length == 2) {
                    if (!ConfigPlayerGet.getFile(p.getUniqueId().toString()).getBoolean("player_speed.Activate")) {
                        if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Option-Disabled.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Option-Disabled.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }

                        return true;
                    }

                    try {
                        @SuppressWarnings("unused")
                        int i = Integer.parseInt(args[1]);

                        ConfigPlayerGet.writeBoolean(p.getUniqueId().toString(), "player_speed.Activate", true);
                        Float WalkSpeed = (float) Integer.valueOf(args[1]) / 10;

                        if (Integer.valueOf(args[1]) < 0 || Integer.valueOf(args[1]) > 10) {
                            p.sendMessage("§c0-10");
                            return false;
                        }

                        p.setWalkSpeed(WalkSpeed);
                        PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", Integer.valueOf(args[1]));

                        if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Set.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Set.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%arg1%", args[1]), "", "", false);
                            }
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
                        if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Disable.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Disable.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
                    } else {
                        if (OnJoinConfig.getConfig().getBoolean("Speed.Option.Priority-For-Player-Option") && p.hasPermission("hawn.command.optionplayer.speed.priorityoptionplayer")) {
                        	PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", speedvalue);
                        	
                        	if (speedvalue < 0 || speedvalue > 10) {
                        		p.setWalkSpeed(0.2F);
                        	} else {
                        		Float WalkSpeed = (float) speedvalue / 10;
                        		p.setWalkSpeed(WalkSpeed);
                        	}

                        	if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Enable.Enable")) {
                        		for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Enable.Messages")) {
                        			ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        		}
                        	}
                        } else {
                            speedvalue = OnJoinConfig.getConfig().getInt("Speed.Value");

                            PlayerOptionSQLClass.SaveSQLPOSpeed(p, "TRUE", speedvalue);

                            if (speedvalue < 0 || speedvalue > 10) {
                                p.setWalkSpeed(0.2F);
                            } else {
                                Float WalkSpeed = (float) speedvalue / 10;
                                p.setWalkSpeed(WalkSpeed);
                            }

                            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Speed.Enable.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Speed.Enable.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
                        }
                    }
                }
            } else if (args[0].equalsIgnoreCase("pv")) {
                if (!p.hasPermission("hawn.command.optionplayer.pv")) {
                    MessageUtils.MessageNoPermission(p, "hawn.command.optionplayer.pv");

                    return true;
                }

                if (PlayerVisibility.PVPlayer.contains(p)) {
                    if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
                        if (PlayerVisibility.Cooling().contains(p)) {
                            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
                        } else {
                            PlayerVisibility.Cooling().add(p);
                            PlayerVisibility.showPlayer(p);
                            SpecialItemPlayerVisibility.swithPVItemsOnJoinToOFF(p);

                            SpecialItemPlayerVisibility.messageitemPVOFF(p);

                            PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

                                @Override
                                public void run() {
                                    PlayerVisibility.Cooling().remove(p);
                                }

                            }, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay") * 20);
                            Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
                        }
                    } else {
                        PlayerVisibility.showPlayer(p);
                        SpecialItemPlayerVisibility.swithPVItemsOnJoinToOFF(p);
                        SpecialItemPlayerVisibility.messageitemPVOFF(p);
                        PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "FALSE");
                    }
                } else {
                    if (SpecialCjiHidePlayers.getConfig().getBoolean("PV.Option.Item-Delay.Enable")) {
                        if (PlayerVisibility.Cooling().contains(p)) {
                            if (ConfigMMsg.getConfig().getBoolean("PlayerOption.Error.Player-Visibility.Time.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("PlayerOption.Error.Player-Visibility.Time.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
                        } else {
                            PlayerVisibility.Cooling().add(p);
                            PlayerVisibility.hidePlayer(p);
                            SpecialItemPlayerVisibility.swithPVItemsOnJoinToON(p);

                            SpecialItemPlayerVisibility.messageitemPVON(p);

                            PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");

                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

                                @Override
                                public void run() {
                                    PlayerVisibility.Cooling().remove(p);
                                }

                            }, SpecialCjiHidePlayers.getConfig().getInt("PV.Option.Item-Delay.Delay") * 20);
                            Main.hiderCooldowns.put(p, Long.valueOf(System.currentTimeMillis()));
                        }
                    } else {
                        PlayerVisibility.hidePlayer(p);
                        SpecialItemPlayerVisibility.swithPVItemsOnJoinToON(p);
                        SpecialItemPlayerVisibility.messageitemPVON(p);
                        PlayerOptionSQLClass.onMysqlYamlCJIChange(p, "TRUE");
                    }
                }
            }
        }

        return false;
    }
}