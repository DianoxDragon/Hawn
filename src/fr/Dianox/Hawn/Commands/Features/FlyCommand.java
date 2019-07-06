package fr.Dianox.Hawn.Commands.Features;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.FlyCommandConfig;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigFDoubleJump;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;
import fr.Dianox.Hawn.Utility.World.PlayerEventsPW;

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
		
		if (!FlyCommandConfig.getConfig().getBoolean("Fly.Enable")) {
			if (FlyCommandConfig.getConfig().getBoolean("Fly.Disable-Message")) {
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
						if (p.getAllowFlight() && player_list_flyc.contains(p)) {
							p.setAllowFlight(false);
							p.setFlying(false);
							player_list_flyc.remove(p);
							
							if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
					        	if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
					        		if (PlayerEventsPW.getWFDoubleJump().contains(p.getWorld().getName())) {
					        			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
					        				if (p.hasPermission("hawn.fun.doublejump.double")) {
					        					p.setAllowFlight(true);
					        				}
					        			} else {
					        				p.setAllowFlight(true);
					        			}
					        		}
					        	} else {
					        		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
					    				if (p.hasPermission("hawn.fun.doublejump.double")) {
					    					p.setAllowFlight(true);
					    				}
					    			} else {
					    				p.setAllowFlight(true);
					    			}
					        	} 	
					        }
							
							if (ConfigMCommands.getConfig().getBoolean("Fly.Disable.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Disable.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else {
							p.setAllowFlight(true);
							p.setFlying(true);
							player_list_flyc.add(p);
							if (ConfigMCommands.getConfig().getBoolean("Fly.Enable.Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Enable.Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
							
							if (target.getAllowFlight() && player_list_flyc.contains(p)) {
								target.setAllowFlight(false);
								target.setFlying(false);
								player_list_flyc.remove(target);
								
								if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Enable")) {
						        	if (!ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.World.All_World")) {
						        		if (PlayerEventsPW.getWFDoubleJump().contains(target.getWorld().getName())) {
						        			if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
						        				if (target.hasPermission("hawn.fun.doublejump.double")) {
						        					target.setAllowFlight(true);
						        				}
						        			} else {
						        				target.setAllowFlight(true);
						        			}
						        		}
						        	} else {
						        		if (ConfigFDoubleJump.getConfig().getBoolean("DoubleJump.Double.Use_Permission")) {
						    				if (target.hasPermission("hawn.fun.doublejump.double")) {
						    					target.setAllowFlight(true);
						    				}
						    			} else {
						    				target.setAllowFlight(true);
						    			}
						        	} 	
						        }
								
								if (ConfigMCommands.getConfig().getBoolean("Fly.Disable-Other.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Disable-Other.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
								if (ConfigMCommands.getConfig().getBoolean("Fly.Disable-Other-Executor.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Disable-Other-Executor.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
							} else {
								target.setAllowFlight(true);
								target.setFlying(true);
								player_list_flyc.add(target);
								if (ConfigMCommands.getConfig().getBoolean("Fly.Enable-Other.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Enable-Other.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
								if (ConfigMCommands.getConfig().getBoolean("Fly.Enable-Other-Executor.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Fly.Enable-Other-Executor.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
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
