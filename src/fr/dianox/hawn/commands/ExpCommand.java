package fr.dianox.hawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.config.commands.ExpCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class ExpCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.exp";

    public ExpCommand(String name) {
        super(name);
        this.description = "Manage the total of experience point of a player";
        this.usageMessage = "/exp <playerName> <add/set/take/clear> <amount>";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        // >>> Executed by the console
        if (!(sender instanceof Player)) {
        	if (args.length == 0) {
	        	if (ConfigMMsg.getConfig().getBoolean("Error.Not-A-Player.Enable")) {
	        		for (String msg: ConfigMMsg.getConfig().getStringList("Error.Not-A-Player.Messages")) {
	        			MessageUtils.ConsoleMessages(msg);
	        		}
	        	}
        	} else if (args.length >= 1) {
            	if (args.length == 1) {
            		sender.sendMessage("/exp <playerName> <add/set/take/clear> <amount>");
            	} else if (args.length > 1) {
            		if (args[1].equals("clear")) {
            			Player target = Bukkit.getServer().getPlayer(args[0]);

                		if (target == null) {
                			 if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
        						 for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
        							 MessageUtils.ConsoleMessages(msg);
        						 }
        					 }
                			return true;
                		}

                	    target.setExp(0);
                	    target.setLevel(0);
                	    
                	    if (ConfigMMsg.getConfig().getBoolean("Exp.Clear.Sender.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Clear.Sender.Messages")) {
                            	MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()));
                            }
                        }
                	    
                        if (ConfigMMsg.getConfig().getBoolean("Exp.Clear.Target.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Clear.Target.Messages")) {
                            	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console"), "", "", false);
                            }
                        }
            		} else if (args[1].equals("take") || args[1].equals("remove")) {
            			Player target = Bukkit.getServer().getPlayer(args[0]);

                		if (target == null) {
                			 if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
        						 for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
        							 MessageUtils.ConsoleMessages(msg);
        						 }
        					 }
                			return true;
                		}
                		
                		int value = Integer.valueOf(args[2]);
                		int totalexp = target.getTotalExperience();
                		int newxp = totalexp - value;
                		            		
                		if (value > totalexp) {
                			if (ConfigMMsg.getConfig().getBoolean("Exp.Take.Sender-Error.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Take.Sender-Error.Messages")) {
                                	MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()).replaceAll("%target_exp_points%", String.valueOf(totalexp)));
                                }
                            }
                		} else if (value >= 0) {
                			float exp = calculateExp(newxp);
                            int level = calculateLevel(newxp);
                            
                            int old_level = target.getLevel();
                            if (level == old_level) {
                            	target.setTotalExperience(newxp);
                            } else {
                            	target.setLevel(level);
                            	if (level > old_level) {
                            		target.setTotalExperience(newxp);
                            	}
                            }
                            
                            target.setExp(exp);
                            
                            if (ConfigMMsg.getConfig().getBoolean("Exp.Take.Sender.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Take.Sender.Messages")) {
                                	MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()).replaceAll("%number_exp%", String.valueOf(value)));
                                }
                            }
                    	    
                            if (ConfigMMsg.getConfig().getBoolean("Exp.Take.Target.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Take.Target.Messages")) {
                                	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console").replaceAll("%number_exp%", String.valueOf(value)), "", "", false);
                                }
                            }
                            
                            sender.sendMessage("§cHawn - You might need to do this command a second time to work.. It's a bug I know");
                		}
            		} else if (args[1].equals("add") || args[1].equals("give")) {
            			Player target = Bukkit.getServer().getPlayer(args[0]);

                		if (target == null) {
                			if (ConfigMMsg.getConfig().getBoolean("Error.No-Players.Enable")) {
                				for (String msg: ConfigMMsg.getConfig().getStringList("Error.No-Players.Messages")) {
                					MessageUtils.ConsoleMessages(msg);
                				}
                			}
                			return true;
                		}

                		int totalexp = target.getTotalExperience();
                        int newxp = totalexp + Integer.valueOf(args[2]);
                        
                        float exp = calculateExp(newxp);
                        int level = calculateLevel(newxp);
                        
                        int old_level = target.getLevel();
                        if (level == old_level) {
                        	target.setTotalExperience(newxp);
                        } else {
                        	target.setLevel(level);
                        	if (level > old_level) {
                        		target.setTotalExperience(newxp);
                        	}
                        }
                        
                        target.setExp(exp);
                        
                        if (ConfigMMsg.getConfig().getBoolean("Exp.Add.Sender.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Add.Sender.Messages")) {
                            	MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()).replaceAll("%number_exp%", args[2]));
                            }
                        }
                	    
                        if (ConfigMMsg.getConfig().getBoolean("Exp.Add.Target.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Add.Target.Messages")) {
                            	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console").replaceAll("%number_exp%", args[2]), "", "", false);
                            }
                        }
            		} else if (args[1].equals("set")) {
            			Player target = Bukkit.getServer().getPlayer(args[0]);

                		if (target == null) {
                			MessageUtils.PlayerDoesntExist(target);
                			return true;
                		}
                		
                		int newxp = Integer.valueOf(args[2]);
                		
                		float exp = calculateExp(newxp);
                		int level = calculateLevel(newxp);
                         
                		int old_level = target.getLevel();
                		if (level == old_level) {
                			target.setTotalExperience(newxp);
                		} else {
                			target.setLevel(level);
                			if (level > old_level) {
                				target.setTotalExperience(newxp);
                			}
                		}
                		
                		target.setExp(exp);
                		
                		if (ConfigMMsg.getConfig().getBoolean("Exp.Set.Sender.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Set.Sender.Messages")) {
                            	MessageUtils.ConsoleMessages(msg.replaceAll("%target%", target.getName()).replaceAll("%number_exp%", args[2]));
                            }
                        }
                	    
                        if (ConfigMMsg.getConfig().getBoolean("Exp.Set.Target.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Set.Target.Messages")) {
                            	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", "console").replaceAll("%number_exp%", args[2]), "", "", false);
                            }
                        }
            		} else {
            			sender.sendMessage("/exp <playerName> <add/set/take/clear> <amount>");
            		}
            	}
            } else {
            	sender.sendMessage("/exp <playerName> <add/set/take/clear> <amount>");
            }
            return true;
        }

        // >>> Executed by the player
        Player p = (Player) sender;

        if (!ExpCommandConfig.getConfig().getBoolean("Exp.Enable")) {
            if (ExpCommandConfig.getConfig().getBoolean("Exp.Disable-Message")) {
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
            if (ConfigMMsg.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMMsg.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
				}
			}
        } else if (args.length >= 1) {
        	if (args.length == 1) {
        		p.sendMessage("/exp <playerName> <add/set/take/clear> <amount>");
        	} else if (args.length > 1) {
        		if (args[1].equals("clear")) {
        			Player target = Bukkit.getServer().getPlayer(args[0]);

            		if (target == null) {
            			MessageUtils.PlayerDoesntExist(p);
            			return true;
            		}

            	    target.setExp(0);
            	    target.setLevel(0);
            	    
            	    if (ConfigMMsg.getConfig().getBoolean("Exp.Clear.Sender.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Clear.Sender.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
                        }
                    }
            	    
                    if (ConfigMMsg.getConfig().getBoolean("Exp.Clear.Target.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Clear.Target.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
                        }
                    }
        		} else if (args[1].equals("take") || args[1].equals("remove")) {
        			Player target = Bukkit.getServer().getPlayer(args[0]);

            		if (target == null) {
            			MessageUtils.PlayerDoesntExist(p);
            			return true;
            		}
            		
            		int value = Integer.valueOf(args[2]);
            		int totalexp = target.getTotalExperience();
            		int newxp = totalexp - value;
            		            		
            		if (value > totalexp) {
            			if (ConfigMMsg.getConfig().getBoolean("Exp.Take.Sender-Error.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Take.Sender-Error.Messages")) {
                            	ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()).replaceAll("%target_exp_points%", String.valueOf(totalexp)), "", "", false);
                            }
                        }
            		} else if (value >= 0) {
            			float exp = calculateExp(newxp);
                        int level = calculateLevel(newxp);
                        
                        int old_level = target.getLevel();
                        if (level == old_level) {
                        	target.setTotalExperience(newxp);
                        } else {
                        	target.setLevel(level);
                        	if (level > old_level) {
                        		target.setTotalExperience(newxp);
                        	}
                        }
                        
                        target.setExp(exp);
                        
                        if (ConfigMMsg.getConfig().getBoolean("Exp.Take.Sender.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Take.Sender.Messages")) {
                            	ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()).replaceAll("%number_exp%", String.valueOf(value)), "", "", false);
                            }
                        }
                	    
                        if (ConfigMMsg.getConfig().getBoolean("Exp.Take.Target.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Take.Target.Messages")) {
                            	ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%player%", p.getName()).replaceAll("%number_exp%", String.valueOf(value)), "", "", false);
                            }
                        }
                        
                        p.sendMessage("§cHawn - You might need to do this command a second time to work.. It's a bug I know");
            		}
        		} else if (args[1].equals("add") || args[1].equals("give")) {
        			Player target = Bukkit.getServer().getPlayer(args[0]);

            		if (target == null) {
            			MessageUtils.PlayerDoesntExist(p);
            			return true;
            		}

            		int totalexp = target.getTotalExperience();
                    int newxp = totalexp + Integer.valueOf(args[2]);
                    
                    float exp = calculateExp(newxp);
                    int level = calculateLevel(newxp);
                    
                    int old_level = target.getLevel();
                    if (level == old_level) {
                    	target.setTotalExperience(newxp);
                    } else {
                    	target.setLevel(level);
                    	if (level > old_level) {
                    		target.setTotalExperience(newxp);
                    	}
                    }
                    
                    target.setExp(exp);
                    
                    if (ConfigMMsg.getConfig().getBoolean("Exp.Add.Sender.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Add.Sender.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()).replaceAll("%number_exp%", args[2]), "", "", false);
                        }
                    }
            	    
                    if (ConfigMMsg.getConfig().getBoolean("Exp.Add.Target.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Add.Target.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()).replaceAll("%number_exp%", args[2]), "", "", false);
                        }
                    }
        		} else if (args[1].equals("set")) {
        			Player target = Bukkit.getServer().getPlayer(args[0]);

            		if (target == null) {
            			MessageUtils.PlayerDoesntExist(p);
            			return true;
            		}
            		
            		int newxp = Integer.valueOf(args[2]);
            		
            		float exp = calculateExp(newxp);
            		int level = calculateLevel(newxp);
                     
            		int old_level = target.getLevel();
            		if (level == old_level) {
            			target.setTotalExperience(newxp);
            		} else {
            			target.setLevel(level);
            			if (level > old_level) {
            				target.setTotalExperience(newxp);
            			}
            		}
            		
            		target.setExp(exp);
            		
            		if (ConfigMMsg.getConfig().getBoolean("Exp.Set.Sender.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Set.Sender.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()).replaceAll("%number_exp%", args[2]), "", "", false);
                        }
                    }
            	    
                    if (ConfigMMsg.getConfig().getBoolean("Exp.Set.Target.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Exp.Set.Target.Messages")) {
                        	ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()).replaceAll("%number_exp%", args[2]), "", "", false);
                        }
                    }
        		} else {
        			p.sendMessage("/exp <playerName> <add/set/take/clear> <amount>");
        		}
        	}
        } else {
        	p.sendMessage("/exp <playerName> <add/set/take/clear> <amount>");
        }

        return true;
    }
    
    private static int calculateLevel(int total) {
        int remain = total;
        int level = 0;
        while (level < 16) {
          int i = 2 * level + 7;
          if (remain - i < 0) {
            return level;
          }
          remain -= i;
          level++;
        } 
        
        while (level < 31) {
          int i = 5 * level - 38;
          if (remain - i < 0) {
            return level;
          }
          remain -= i;
          level++;
        } 
        
        while (remain > 0) {
          int i = 9 * level - 158;
          if (remain - i < 0) {
            return level;
          }
          remain -= i;
          level++;
        } 
        
        return level;
      }
    
    private static float calculateExp(int total) {
        int remain = total;
        int level = 0;
        while (level < 16) {
          int i = 2 * level + 7;
          if (remain - i < 0) {
            return (float) remain / i;
          }
          remain -= i;
          level++;
        } 
        
        while (level < 31) {
          int i = 5 * level - 38;
          if (remain - i < 0) {
            return (float) remain / i;
          }
          remain -= i;
          level++;
        } 
        
        while (remain > 0) {
          int i = 9 * level - 158;
          if (remain - i < 0) {
            return (float) remain / i;
          }
          remain -= i;
          level++;
        } 

        return 0.0F;
      }

}