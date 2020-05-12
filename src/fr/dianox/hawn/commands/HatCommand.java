package fr.dianox.hawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.ConfigEventUtils;
import fr.dianox.hawn.utility.MessageUtils;
import fr.dianox.hawn.utility.XMaterial;
import fr.dianox.hawn.utility.config.commands.HatCommandConfig;

import fr.dianox.hawn.utility.config.messages.ConfigMMsg;

public class HatCommand extends BukkitCommand {

    String GeneralPermission = "hawn.command.hat";
    
    public HatCommand(String name) {
        super(name);
        this.description = "Put a hat to a player";
        this.usageMessage = "/hat [player]";
    }

    @SuppressWarnings("deprecation")
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

        if (!HatCommandConfig.getConfig().getBoolean("Hat.Enable")) {
            if (HatCommandConfig.getConfig().getBoolean("Hat.Disable-Message")) {
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
        	PlayerInventory inv = p.getInventory();
        	ItemStack head = inv.getHelmet();
        	
        	if (head == null || head.getType() == XMaterial.AIR.parseMaterial()) {
        		ItemStack hand;
        		
        		if (Main.Spigot_Version > 18) {
        			hand = inv.getItemInMainHand();
        		} else {
        			hand = p.getItemInHand();
        		}
        		
        		if (hand != null && hand.getType() != XMaterial.AIR.parseMaterial()) {
        			if (hand.getType().getMaxDurability() == 0) {
        				inv.setHelmet(hand);
        				
        				if (Main.Spigot_Version > 18) {
        					inv.setItemInMainHand(head);
                		} else {
                			inv.setItemInHand(head);
                		}
        				
        				if (ConfigMMsg.getConfig().getBoolean("Hat.Self.Set.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Self.Set.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
        			} else {
        				if (ConfigMMsg.getConfig().getBoolean("Hat.Error.No-Hat-Can-Be-Set.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.No-Hat-Can-Be-Set.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
        			}
        		} else {
        			if (ConfigMMsg.getConfig().getBoolean("Hat.Error.No-Hat-Can-Be-Set.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.No-Hat-Can-Be-Set.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
        		}
        	} else {
        		ItemStack air = new ItemStack(XMaterial.AIR.parseMaterial());
        		
        		if (Main.Spigot_Version > 18) {
					inv.setItemInMainHand(head);
        		} else {
        			inv.setItemInHand(head);
        		}
        		
    	        inv.setHelmet(air);
    	        
    	        if (ConfigMMsg.getConfig().getBoolean("Hat.Self.Removed.Enable")) {
                    for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Self.Removed.Messages")) {
                        ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                    }
                }
        	}
    	} else if (args.length == 1) {
        	PlayerInventory inv = p.getInventory();
        	ItemStack head = inv.getHelmet();
        	
        	if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("rem") || args[0].equalsIgnoreCase("0")) {
        		if (head == null || head.getType() == XMaterial.AIR.parseMaterial()) {
        			if (ConfigMMsg.getConfig().getBoolean("Hat.Error.Need-Have-NoEmpty-Helmet.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.Need-Have-NoEmpty-Helmet.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
        		} else {
        			ItemStack air = new ItemStack(XMaterial.AIR.parseMaterial());
        			
        			if (Main.Spigot_Version > 18) {
    					inv.setItemInMainHand(head);
            		} else {
            			inv.setItemInHand(head);
            		}
        			
        	        inv.setHelmet(air);
        	        
        	        if (ConfigMMsg.getConfig().getBoolean("Hat.Self.Removed.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Self.Removed.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
        		}
        	} else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("put")) {
        		
        		ItemStack hand;
        		
        		if (Main.Spigot_Version > 18) {
        			hand = inv.getItemInMainHand();
        		} else {
        			hand = p.getItemInHand();
        		}
        		
        		if (hand != null && hand.getType() != XMaterial.AIR.parseMaterial()) {
        			if (hand.getType().getMaxDurability() == 0) {
        				inv.setHelmet(hand);
        				
        				if (Main.Spigot_Version > 18) {
        					inv.setItemInMainHand(head);
                		} else {
                			inv.setItemInHand(head);
                		}
        				
        				if (ConfigMMsg.getConfig().getBoolean("Hat.Self.Set.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Self.Set.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
        			} else {
        				if (ConfigMMsg.getConfig().getBoolean("Hat.Error.No-Hat-Can-Be-Set.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.No-Hat-Can-Be-Set.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
        			}
        		} else {
        			if (ConfigMMsg.getConfig().getBoolean("Hat.Error.No-Hat-Can-Be-Set.Enable")) {
                        for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.No-Hat-Can-Be-Set.Messages")) {
                            ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                        }
                    }
        		}
        	}
        } else if (args.length == 2) {
        	if (p.hasPermission("hawn.command.hat.other")) {
        		Player target = Bukkit.getServer().getPlayer(args[1]);

        		if (target == null) {
        			MessageUtils.PlayerDoesntExist(p);
        			return true;
        		}
        		
        		PlayerInventory inv = target.getInventory();
            	ItemStack head = inv.getHelmet();
            	
            	if (args[0].equalsIgnoreCase("off") || args[0].equalsIgnoreCase("rem") || args[0].equalsIgnoreCase("0")) {
            		if (head == null || head.getType() == XMaterial.AIR.parseMaterial()) {
            			if (ConfigMMsg.getConfig().getBoolean("Hat.Error.Need-Have-NoEmpty-Helmet.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.Need-Have-NoEmpty-Helmet.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
            		} else {
            			ItemStack air = new ItemStack(XMaterial.AIR.parseMaterial());
            			
            			if (Main.Spigot_Version > 18) {
        					p.getInventory().setItemInMainHand(head);
                		} else {
                			p.getInventory().setItemInHand(head);
                		}
            			
            	        inv.setHelmet(air);
            	        
            	        if (ConfigMMsg.getConfig().getBoolean("Hat.Other-Sender.Removed.Enable")) {
                			for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Other-Sender.Removed.Messages")) {
                				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
                			}
                		}
        				
                		if (ConfigMMsg.getConfig().getBoolean("Hat.Other-Target.Removed.Enable")) {
                			for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Other-Target.Removed.Messages")) {
                				ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
                			}
                		}
            		}
            	} else if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("put")) {
            		
            		ItemStack hand;
            		
            		if (Main.Spigot_Version > 18) {
            			hand = p.getInventory().getItemInMainHand();
            		} else {
            			hand = p.getItemInHand();
            		}
            		
            		if (hand != null && hand.getType() != XMaterial.AIR.parseMaterial()) {
            			if (hand.getType().getMaxDurability() == 0) {
            				inv.setHelmet(hand);
            				
            				if (Main.Spigot_Version > 18) {
            					p.getInventory().setItemInMainHand(head);
                    		} else {
                    			p.getInventory().setItemInHand(head);
                    		}
            				
            				if (ConfigMMsg.getConfig().getBoolean("Hat.Other-Sender.Set.Enable")) {
                    			for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Other-Sender.Set.Messages")) {
                    				ConfigEventUtils.ExecuteEvent(p, msg.replaceAll("%target%", target.getName()), "", "", false);
                    			}
                    		}
            				
                    		if (ConfigMMsg.getConfig().getBoolean("Hat.Other-Target.Set.Enable")) {
                    			for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Other-Target.Set.Messages")) {
                    				ConfigEventUtils.ExecuteEvent(target, msg.replaceAll("%player%", p.getName()), "", "", false);
                    			}
                    		}
            			} else {
            				if (ConfigMMsg.getConfig().getBoolean("Hat.Error.No-Hat-Can-Be-Set.Enable")) {
                                for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.No-Hat-Can-Be-Set.Messages")) {
                                    ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                                }
                            }
            			}
            		} else {
            			if (ConfigMMsg.getConfig().getBoolean("Hat.Error.No-Hat-Can-Be-Set.Enable")) {
                            for (String msg: ConfigMMsg.getConfig().getStringList("Hat.Error.No-Hat-Can-Be-Set.Messages")) {
                                ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
                            }
                        }
            		}
            	}
        	} else {
        		String Permission = "hawn.command.hat.other";
        		MessageUtils.MessageNoPermission(p, Permission);
        	}
        } else {
            if (p.hasPermission("hawn.command.hat.other")) {
            	p.sendMessage("§c/hat or §c/hat [player]");
            } else {
                p.sendMessage("§c/hat");
            }
        }

        return true;
    }

}