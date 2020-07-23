package fr.dianox.hawn.command.commands;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.ConfigPlayerGet;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.PlayerOptionSQLClass;
import fr.dianox.hawn.utility.config.configs.commands.FlyCommandConfig;
import fr.dianox.hawn.utility.config.configs.cosmeticsfun.ConfigFDoubleJump;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.PlayerEventsPW;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand extends BukkitCommand {
	
	public static List<Player> player_list_flyc = new ArrayList<Player>();
	
	public static String GeneralPermission = "hawn.command.fly";
	public static String perm_other_fly = "hawn.command.fly.others";
	
	public FlyCommand(String name) {
		super(name);
		this.description = "Flying to other skies!";
        this.usageMessage = "/fly [player]";
	}

	@Override
	public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {

		if (args.length == 1) {
			List<String> tab = new ArrayList<>();
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				tab.add(p.getName());
			}

			java.util.Collections.sort(tab);

			return tab;
		}

		return null;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			
			if (args.length == 0) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					MessageUtils.ConsoleMessages(msg);
            	}
                return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			if (target == null) {
				if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
						MessageUtils.ConsoleMessages(msg);
					}
				}
        		return true;
			}
			
			if (target.getAllowFlight() && (player_list_flyc.contains(target) || ConfigPlayerGet.getFile(target.getUniqueId().toString()).getBoolean("player_option_fly.Activate"))) {
				target.setAllowFlight(false);
				target.setFlying(false);
				player_list_flyc.remove(target);
				PlayerOptionSQLClass.SaveSQLPOFly(target, "FALSE");
				if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
		        	if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
		        		if (PlayerEventsPW.getWFDoubleJump().contains(target.getWorld().getName())) {
		        			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
		        				if (target.hasPermission("hawn.fun.doublejump.double")) {
		        					target.setAllowFlight(true);
		        					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
		        				} else {
		        					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "FALSE");
		        				}
		        			} else {
		        				target.setAllowFlight(true);
		        				PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
		        			}
		        		}
		        	} else {
		        		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
		    				if (target.hasPermission("hawn.fun.doublejump.double")) {
		    					target.setAllowFlight(true);
		    					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
		    				} else {
		    					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "FALSE");
		    				}
		    			} else {
		    				target.setAllowFlight(true);
		    				PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
		    			}
		        	} 	
		        }
				
				if (ConfigMMsg.getConfig().getBoolean("Fly.Disable-Other.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Disable-Other.Messages")) {
						ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
					}
				}
				if (ConfigMMsg.getConfig().getBoolean("Fly.Disable-Other-Executor.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Disable-Other-Executor.Messages")) {
						MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()));
					}
				}
			} else {
				target.setAllowFlight(true);
				target.setFlying(true);
				player_list_flyc.add(target);
				PlayerOptionSQLClass.SaveSQLPOFly(target, "TRUE");
				PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "FALSE");
				if (ConfigMMsg.getConfig().getBoolean("Fly.Enable-Other.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Enable-Other.Messages")) {
						ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
					}
				}
				if (ConfigMMsg.getConfig().getBoolean("Fly.Enable-Other-Executor.Enable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Enable-Other-Executor.Messages")) {
						MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()));
					}
				}
			}
			
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		String uuid = p.getUniqueId().toString();
		
		if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable")) {
			if (FlyCommandConfig.getConfig().getBoolean("Fly.Disable-Message")) {
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
		
		// The command
					if (args.length == 0) {
						if (p.getAllowFlight() && (player_list_flyc.contains(p) || ConfigPlayerGet.getFile(uuid).getBoolean("player_option_fly.Activate"))) {
							p.setAllowFlight(false);
							p.setFlying(false);
							player_list_flyc.remove(p);
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
							player_list_flyc.add(p);
							PlayerOptionSQLClass.SaveSQLPOFly(p, "TRUE");
							PlayerOptionSQLClass.SaveSQLPODoubleJump(p, "FALSE");
							if (ConfigMMsg.getConfig().getBoolean("Fly.Enable.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Enable.Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
								}
							}
						}
					} else if (args.length == 1) {
						if (p.hasPermission(perm_other_fly)) {
							Player target = Bukkit.getServer().getPlayer(args[0]);
							
							if (target == null) {
								MessageUtils.PlayerDoesntExist(p);
			            		return true;
							}
							
							if (target.getAllowFlight() && (player_list_flyc.contains(target) || ConfigPlayerGet.getFile(target.getUniqueId().toString()).getBoolean("player_option_fly.Activate"))) {
								target.setAllowFlight(false);
								target.setFlying(false);
								player_list_flyc.remove(target);
								PlayerOptionSQLClass.SaveSQLPOFly(target, "FALSE");
								if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
						        	if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
						        		if (PlayerEventsPW.getWFDoubleJump().contains(target.getWorld().getName())) {
						        			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
						        				if (target.hasPermission("hawn.fun.doublejump.double")) {
						        					target.setAllowFlight(true);
						        					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
						        				} else {
						        					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "FALSE");
						        				}
						        			} else {
						        				target.setAllowFlight(true);
						        				PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
						        			}
						        		}
						        	} else {
						        		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
						    				if (target.hasPermission("hawn.fun.doublejump.double")) {
						    					target.setAllowFlight(true);
						    					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
						    				} else {
						    					PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "FALSE");
						    				}
						    			} else {
						    				target.setAllowFlight(true);
						    				PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "TRUE");
						    			}
						        	} 	
						        }
								
								if (ConfigMMsg.getConfig().getBoolean("Fly.Disable-Other.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Disable-Other.Messages")) {
										ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
									}
								}
								if (ConfigMMsg.getConfig().getBoolean("Fly.Disable-Other-Executor.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Disable-Other-Executor.Messages")) {
										ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
									}
								}
							} else {
								target.setAllowFlight(true);
								target.setFlying(true);
								player_list_flyc.add(target);
								PlayerOptionSQLClass.SaveSQLPOFly(target, "TRUE");
								PlayerOptionSQLClass.SaveSQLPODoubleJump(target, "FALSE");
								if (ConfigMMsg.getConfig().getBoolean("Fly.Enable-Other.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Enable-Other.Messages")) {
										ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
									}
								}
								if (ConfigMMsg.getConfig().getBoolean("Fly.Enable-Other-Executor.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Fly.Enable-Other-Executor.Messages")) {
										ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
									}
								}
							}
						} else {
							MessageUtils.MessageNoPermission(p, perm_other_fly);
						}
					} else {
						if (p.hasPermission(perm_other_fly)) {
							p.sendMessage("§c/fly [players]");
						} else {
							p.sendMessage("§c/fly");
						}
					}
		
		return true;
	}

}