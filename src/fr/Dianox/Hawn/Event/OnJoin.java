package fr.Dianox.Hawn.Event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.SQL;
import fr.Dianox.Hawn.Commands.PingCommand;
import fr.Dianox.Hawn.Commands.Features.Chat.DelaychatCommand;
import fr.Dianox.Hawn.Event.OnJoinE.OJMessages;
import fr.Dianox.Hawn.Event.OnJoinE.OjPlayerOption;
import fr.Dianox.Hawn.Utility.ActionBar;
import fr.Dianox.Hawn.Utility.ConfigPlayerGet;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.OtherUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.TitleUtils;
import fr.Dianox.Hawn.Utility.XSound;
import fr.Dianox.Hawn.Utility.Config.BetweenServersConfig;
import fr.Dianox.Hawn.Utility.Config.ConfigGeneral;
import fr.Dianox.Hawn.Utility.Config.ConfigSpawn;
import fr.Dianox.Hawn.Utility.Config.CosmeticsFun.ConfigGCos;
import fr.Dianox.Hawn.Utility.Config.Events.ConfigGJoinQuitCommand;
import fr.Dianox.Hawn.Utility.Config.Events.OnJoinConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMGeneral;
import fr.Dianox.Hawn.Utility.Server.Tps;
import fr.Dianox.Hawn.Utility.World.CommandsPW;
import fr.Dianox.Hawn.Utility.World.CosmeticsPW;
import fr.Dianox.Hawn.Utility.World.OnJoinPW;
import fr.Dianox.Hawn.Utility.World.PlayerEventsPW;
import me.clip.placeholderapi.PlaceholderAPI;

public class OnJoin implements Listener {

    public static List < Player > player_list = new ArrayList < Player > ();
    int gm = OnJoinConfig.getConfig().getInt("Change-Gamemode.Value");

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PlayerInventory inv = p.getInventory();
        int lines = OnJoinConfig.getConfig().getInt("Chat.Clear.Lines-To-Clear");
        String uuid = p.getUniqueId().toString();
        
        String date = String.valueOf(OtherUtils.getDate() + ", " + OtherUtils.getTime());

        // Yaml
        // Player info
        if (!ConfigPlayerGet.getFile(uuid).isSet("player_info.player_name")) {
        	ConfigPlayerGet.writeString(uuid, "player_info.player_name", p.getName());
        	ConfigPlayerGet.writeString(uuid, "player_info.join_date", String.valueOf(date));
        	ConfigPlayerGet.writeString(uuid, "player_info.first_join", String.valueOf(date));
        	ConfigPlayerGet.writeString(uuid, "player_info.player_ip", String.valueOf(p.getAddress().getHostString()));
        } else {
        	ConfigPlayerGet.writeString(uuid, "player_info.player_name", p.getName());
            ConfigPlayerGet.writeString(uuid, "player_info.join_date", String.valueOf(date));
            ConfigPlayerGet.writeString(uuid, "player_info.player_ip", String.valueOf(p.getAddress().getHostString()));
        }
        
        // MYSQL
        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.MYSQL.Enable")) {
            // Player info
            if (!Main.useyamllistplayer) {
                if (!ConfigPlayerGet.getFile(uuid).isSet("player_info.player_name")) {
                    if (SQL.tableExists("player_info")) {
                        if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_info")) {
                            SQL.set("player_info", "join_date", "" + date + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player_ip", "" + p.getAddress().getHostString() + "", "player_UUID", "" + p.getUniqueId() + "");
                        } else {
                            SQL.insertData("player, player_UUID, join_date, first_join, player_ip",
                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + date + "', '" + date + "', '" + p.getAddress().getHostString() + "' ", "player_info");
                        }
                    } else {
                        SQL.createTable("player_info", "player TEXT, player_UUID TEXT, join_date TEXT, first_join TEXT, player_ip TEXT");
                        if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_info")) {
                            SQL.set("player_info", "join_date", "" + date + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player_ip", "" + p.getAddress().getHostString() + "", "player_UUID", "" + p.getUniqueId() + "");
                        } else {
                            SQL.insertData("player, player_UUID, join_date, first_join, player_ip",
                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + date + "', '" + date + "', '" + p.getAddress().getHostString() + "' ", "player_info");
                        }
                    }
                } else {
                    if (SQL.tableExists("player_info")) {
                        if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_info")) {
                            SQL.set("player_info", "join_date", "" + date + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player_ip", "" + p.getAddress().getHostString() + "", "player_UUID", "" + p.getUniqueId() + "");
                        } else {
                            SQL.insertData("player, player_UUID, join_date, first_join, player_ip",
                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + date + "', '" + ConfigPlayerGet.getFile(uuid).getString("player_info.first_join") + "', '" + p.getAddress().getHostString() + "' ", "player_info");
                        }
                    } else {
                        SQL.createTable("player_info", "player TEXT, player_UUID TEXT, join_date TEXT, first_join TEXT, player_ip TEXT");
                        if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_info")) {
                            SQL.set("player_info", "join_date", "" + date + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player", "" + p.getName() + "", "player_UUID", "" + p.getUniqueId() + "");
                            SQL.set("player_info", "player_ip", "" + p.getAddress().getHostString() + "", "player_UUID", "" + p.getUniqueId() + "");
                        } else {
                            SQL.insertData("player, player_UUID, join_date, first_join, player_ip",
                                " '" + p.getName() + "', '" + p.getUniqueId() + "', '" + date + "', '" + ConfigPlayerGet.getFile(uuid).getString("player_info.first_join") + "', '" + p.getAddress().getHostString() + "', '" + p.getAddress().getHostString() + "' ", "player_info");
                        }
                    }
                }
            } else {
                Bukkit.getConsoleSender().sendMessage("§cRemember, the database MYSQL is not working, or the connection is not possible at the moment. Save on MYSQL is not possible");
            }
        }

        // ON  JOIN VRAI
        if (OnJoinConfig.getConfig().getBoolean("Chat.Clear.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Chat.Clear.World.All_World")) {
                if (OnJoinPW.getWClearChat().contains(p.getWorld().getName())) {
                    if (OnJoinConfig.getConfig().getBoolean("Chat.Clear.Bypass")) {
                        if (!p.hasPermission("hawn.event.onjoin.bypass.clearchat")) {
                            for (int i = 0; i < lines; i++) {
                                p.sendMessage("");
                            }
                        }
                    } else {
                        for (int i = 0; i < lines; i++) {
                            p.sendMessage("");
                        }
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Chat.Clear.Bypass")) {
                    if (!p.hasPermission("hawn.event.onjoin.bypass.clearchat")) {
                        for (int i = 0; i < lines; i++) {
                            p.sendMessage("");
                        }
                    }
                } else {
                    for (int i = 0; i < lines; i++) {
                        p.sendMessage("");
                    }
                }
            }
        }

        // Force selected slot
        if (OnJoinConfig.getConfig().getBoolean("Inventory.Force-Selected-Slot.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Inventory.Force-Selected-Slot.World.All_World")) {
                if (PlayerEventsPW.getWOForceSelectedSlot().contains(p.getWorld().getName())) {
                    inv.setHeldItemSlot(OnJoinConfig.getConfig().getInt("Inventory.Force-Selected-Slot.Slot") - 1);
                }
            } else {
                inv.setHeldItemSlot(OnJoinConfig.getConfig().getInt("Inventory.Force-Selected-Slot.Slot") - 1);
            }
        }


        // JOIN TP SPAWN
        if (p.hasPlayedBefore() && BetweenServersConfig.getConfig().getBoolean("TP.Last-Position-On-Join.Enable") && p.hasPermission("hawn.betweenservers.tplastposition") && (ConfigPlayerGet.getFile(uuid).isSet("player_last_position.World") || SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_last_position"))) {
        	if (Main.useyamllistplayer) {
        		if (ConfigPlayerGet.getFile(uuid).isSet("player_last_position.World")) {
        			
        			World w = Bukkit.getServer().getWorld(ConfigPlayerGet.getFile(uuid).getString("player_last_position.World"));
                    double x = ConfigPlayerGet.getFile(uuid).getDouble("player_last_position.X");
                    double y = ConfigPlayerGet.getFile(uuid).getDouble("player_last_position.Y");
                    double z = ConfigPlayerGet.getFile(uuid).getDouble("player_last_position.Z");
                    float yaw = ConfigPlayerGet.getFile(uuid).getInt("player_last_position.YAW");
                    float pitch = ConfigPlayerGet.getFile(uuid).getInt("player_last_position.PITCH");
                    
                    p.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));
                } else {
                	TPSPAWNnormalevent(p);
                }
        	} else {
        		if (SQL.tableExists("player_last_position")) {
	        		if (SQL.exists("player_UUID", ""+p.getUniqueId()+"", "player_last_position")) {
	        			World w = Bukkit.getServer().getWorld(SQL.getInfoString("player_last_position", "World", "" + p.getUniqueId() + ""));
	        			double x = SQL.getInfoDouble("player_last_position", "X", "" + p.getUniqueId() + "");
	        			double y = SQL.getInfoDouble("player_last_position", "Y", "" + p.getUniqueId() + "");
	        			double z = SQL.getInfoDouble("player_last_position", "Z", "" + p.getUniqueId() + "");
	        			float yaw = SQL.getInfoFloat("player_last_position", "YAW", "" + p.getUniqueId() + "");
	        			float pitch = SQL.getInfoFloat("player_last_position", "PITCH", "" + p.getUniqueId() + "");
	        			
	        			p.teleport(new org.bukkit.Location(w, x, y, z, yaw, pitch));	        			
	        		} else {
	        			TPSPAWNnormalevent(p);
	        		}
        		} else {
        			TPSPAWNnormalevent(p);
        		}
        	}
        } else {
        	TPSPAWNnormalevent(p);
        }

        // Firework

        if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Enable")) {
            if (!ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.World.All_World")) {
                if (CosmeticsPW.getWFirework().contains(p.getWorld().getName())) {
                    if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Bypass")) {
                        if (!p.hasPermission("hawn.event.onjoin.bypass.firework")) {
                        	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                        		if (!p.hasPlayedBefore()) {
                        			Fireworkmethod(p);
                        		}
                        	} else {
                        		Fireworkmethod(p);
                        	}
                        }
                    } else {
                    	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                    		if (!p.hasPlayedBefore()) {
                    			Fireworkmethod(p);
                    		}
                    	} else {
                    		Fireworkmethod(p);
                    	}
                    }
                }
            } else {
                if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Bypass")) {
                    if (!p.hasPermission("hawn.event.onjoin.bypass.firework")) {
                    	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                    		if (!p.hasPlayedBefore()) {
                    			Fireworkmethod(p);
                    		}
                    	} else {
                    		Fireworkmethod(p);
                    	}
                    }
                } else {
                	if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.First-Join-Only")) {
                		if (!p.hasPlayedBefore()) {
                			Fireworkmethod(p);
                		}
                	} else {
                		Fireworkmethod(p);
                	}
                }
            }
        }

        // Reset XP
        String XPValueEXP = String.valueOf(OnJoinConfig.getConfig().getDouble("XP.Options.Exp.Value"));
        String XPValueLvl = String.valueOf(OnJoinConfig.getConfig().getInt("XP.Options.Level.Value"));

        if (OnJoinConfig.getConfig().getBoolean("XP.Enable")) {
            if (OnJoinConfig.getConfig().getBoolean("XP.Options.Exp.Enable")) {
                if (!OnJoinConfig.getConfig().getBoolean("XP.Options.Exp.World.All_World")) {
                    if (OnJoinPW.getWXPEXP().contains(p.getWorld().getName())) {
                        p.setExp(Float.valueOf(XPValueEXP));
                    }
                } else {
                    p.setExp(Float.valueOf(XPValueEXP));
                }
            }
            if (OnJoinConfig.getConfig().getBoolean("XP.Options.Level.Enable")) {
                if (!OnJoinConfig.getConfig().getBoolean("XP.Options.Level.World.All_World")) {
                    if (OnJoinPW.getWXPLVL().contains(p.getWorld().getName())) {
                        p.setLevel(Integer.valueOf(XPValueLvl));
                    }
                } else {
                    p.setLevel(Integer.valueOf(XPValueLvl));
                }
            }
        }

        // NEW JOIN BROADCAST


        // Restore Health and food
        String FoodValue = String.valueOf(OnJoinConfig.getConfig().getInt("Restore.Food.Value"));
        String HealthValue = String.valueOf(OnJoinConfig.getConfig().getDouble("Restore.Health.Value"));

        if (OnJoinConfig.getConfig().getBoolean("Restore.Food.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Restore.Food.World.All_World")) {
                if (OnJoinPW.getWFood().contains(p.getWorld().getName())) {
                    if (OnJoinConfig.getConfig().getBoolean("Restore.Food.Bypass-With-Permission")) {
                        if (!p.hasPermission("hawn.bypass.foodrestore")) {
                            p.setFoodLevel(Integer.valueOf(FoodValue));
                        }
                    } else {
                        p.setFoodLevel(Integer.valueOf(FoodValue));
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Restore.Food.Bypass-With-Permission")) {
                    if (!p.hasPermission("hawn.bypass.foodrestore")) {
                        p.setFoodLevel(Integer.valueOf(FoodValue));
                    }
                } else {
                    p.setFoodLevel(Integer.valueOf(FoodValue));
                }
            }
        }

        if (OnJoinConfig.getConfig().getBoolean("Restore.Health.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Restore.Health.World.All_World")) {
                if (OnJoinPW.getWHealth().contains(p.getWorld().getName())) {
                    if (OnJoinConfig.getConfig().getBoolean("Restore.Health.Bypass-With-Permission")) {
                        if (!p.hasPermission("hawn.bypass.healthrestore")) {
                            p.setHealth(Double.valueOf(HealthValue));
                        }
                    } else {
                        p.setHealth(Double.valueOf(HealthValue));
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Restore.Health.Bypass-With-Permission")) {
                    if (!p.hasPermission("hawn.bypass.healthrestore")) {
                        p.setHealth(Double.valueOf(HealthValue));
                    }
                } else {
                    p.setHealth(Double.valueOf(HealthValue));
                }
            }
        }

        if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Inventory.Clear.World.All_World")) {
                if (OnJoinPW.getWInventory().contains(p.getWorld().getName())) {
                    if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Bypass")) {
                        if (!p.hasPermission("hawn.event.onjoin.bypass.clearinv")) {
                            if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Inventory")) {
                                inv.clear();
                            }
                            if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Armor")) {
                                inv.setArmorContents(new ItemStack[4]);
                            }
                        }
                    } else {
                        if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Inventory")) {
                            inv.clear();
                        }
                        if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Armor")) {
                            inv.setArmorContents(new ItemStack[4]);
                        }
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Bypass")) {
                    if (!p.hasPermission("hawn.event.onjoin.bypass.clearinv")) {
                        if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Inventory")) {
                            inv.clear();
                        }
                        if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Armor")) {
                            inv.setArmorContents(new ItemStack[4]);
                        }
                    }
                } else {
                    if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Inventory")) {
                        inv.clear();
                    }
                    if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Options.Armor")) {
                        inv.setArmorContents(new ItemStack[4]);
                    }
                }
            }
        }

        // Blindness
        if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.BLINDNESS.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Potion-Effect.BLINDNESS.World.All_World")) {
                if (OnJoinPW.getWblindess().contains(p.getWorld().getName())) {
                	if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.BLINDNESS.Use_Permission")) {
                		if (p.hasPermission("hawn.onjoin.potion.blindness")) {
                			int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Duration-Second") * 20;
    	                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Amplifier");
    	                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier));
                		}
                	} else {
                		int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Duration-Second") * 20;
	                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Amplifier");
	                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier));
                	}
                }
            } else {
            	if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.BLINDNESS.Use_Permission")) {
            		if (p.hasPermission("hawn.onjoin.potion.blindness")) {
            			int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Duration-Second") * 20;
	                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Amplifier");
	                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier));
            		}
            	} else {
            		int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Duration-Second") * 20;
                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.BLINDNESS.Amplifier");
                    p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, amplifier));
            	}
            }
        }

        // Jump
        if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.JUMP.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Potion-Effect.JUMP.World.All_World")) {
                if (OnJoinPW.getWjump().contains(p.getWorld().getName())) {
                	if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.JUMP.Use_Permission")) {
                		if (p.hasPermission("hawn.onjoin.potion.jump")) {
		                    int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Duration-Second") * 20;
		                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Amplifier");
		                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, amplifier));
		                    OjPlayerOption.onJumpBoost(p, duration);
                		}
                	} else {
                		int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Duration-Second") * 20;
	                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Amplifier");
	                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, amplifier));
	                    OjPlayerOption.onJumpBoost(p, duration);
                	}
                }
            } else {
            	if (OnJoinConfig.getConfig().getBoolean("Potion-Effect.JUMP.Use_Permission")) {
            		if (p.hasPermission("hawn.onjoin.potion.jump")) {
	                    int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Duration-Second") * 20;
	                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Amplifier");
	                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, amplifier));
	                    OjPlayerOption.onJumpBoost(p, duration);
            		}
            	} else {
            		int duration = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Duration-Second") * 20;
                    int amplifier = OnJoinConfig.getConfig().getInt("Potion-Effect.JUMP.Amplifier");
                    p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, amplifier));
                    OjPlayerOption.onJumpBoost(p, duration);
            	}
            }
        }
        
        OjPlayerOption.PO(p);

        /*
         * 
         * This line of code is the category : On-Join.Join-Message 
         * Of the file Hawn/Messages/Classic/General.yml (messages)
         * 
         */
        OJMessages.OnMessage(p, e);
        
        /*
         * The motd on join
         */
        OJMessages.onMOTD(p);
        
        /*
         * Broadcast on first join
         */
        OJMessages.onBroadcastFJ(p);
        
        
        
        if (OnJoinConfig.getConfig().getBoolean("Action-Bar.Enable")) {
            if (OnJoinConfig.getConfig().getBoolean("Action-Bar.First-Join.Enable")) {
                if (p.hasPlayedBefore()) {
                    if (OnJoinConfig.getConfig().getBoolean("Action-Bar.Join.Enable")) {
                        if (!OnJoinConfig.getConfig().getBoolean("Action-Bar.Join.World.All_World")) {
                            if (OnJoinPW.getWJoinab().contains(p.getWorld().getName())) {
                                String actionbarjoin = OnJoinConfig.getConfig().getString("Action-Bar.Join.Message");
                                if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                                    actionbarjoin = PlaceholderAPI.setPlaceholders(p, actionbarjoin);
                                }
                                actionbarjoin = actionbarjoin.replaceAll("&", "§")
                                    .replaceAll("%player%", p.getName())
                                    .replaceAll("%ping%", String.valueOf(PingCommand.getPing(p)))
                                    .replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay))
                                    .replaceAll("%tps%", String.valueOf(Tps.getTPS()));

                                ActionBar.sendActionBar(p, actionbarjoin, OnJoinConfig.getConfig().getInt("Action-Bar.Join.Time-Stay"));
                            }
                        } else {
                            String actionbarjoin = OnJoinConfig.getConfig().getString("Action-Bar.Join.Message");
                            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                                actionbarjoin = PlaceholderAPI.setPlaceholders(p, actionbarjoin);
                            }
                            actionbarjoin = actionbarjoin.replaceAll("&", "§")
                                .replaceAll("%player%", p.getName())
                                .replaceAll("%ping%", String.valueOf(PingCommand.getPing(p)))
                                .replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay))
                                .replaceAll("%tps%", String.valueOf(Tps.getTPS()));

                            ActionBar.sendActionBar(p, actionbarjoin, OnJoinConfig.getConfig().getInt("Action-Bar.Join.Time-Stay"));
                        }
                    }
                } else {
                    if (!OnJoinConfig.getConfig().getBoolean("Action-Bar.First-Join.World.All_World")) {
                        if (OnJoinPW.getWFirstJoinab().contains(p.getWorld().getName())) {
                            String actionbarFirstjoin = OnJoinConfig.getConfig().getString("Action-Bar.First-Join.Message");
                            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                                actionbarFirstjoin = PlaceholderAPI.setPlaceholders(p, actionbarFirstjoin);
                            }
                            actionbarFirstjoin = actionbarFirstjoin.replaceAll("&", "§")
                                .replaceAll("%player%", p.getName())
                                .replaceAll("%ping%", String.valueOf(PingCommand.getPing(p)))
                                .replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay))
                                .replaceAll("%tps%", String.valueOf(Tps.getTPS()));

                            ActionBar.sendActionBar(p, actionbarFirstjoin, OnJoinConfig.getConfig().getInt("Action-Bar.First-Join.Time-Stay"));
                        }
                    } else {
                        String actionbarFirstjoin = OnJoinConfig.getConfig().getString("Action-Bar.First-Join.Message");
                        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                            actionbarFirstjoin = PlaceholderAPI.setPlaceholders(p, actionbarFirstjoin);
                        }
                        actionbarFirstjoin = actionbarFirstjoin.replaceAll("&", "§")
                            .replaceAll("%player%", p.getName())
                            .replaceAll("%ping%", String.valueOf(PingCommand.getPing(p)))
                            .replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay))
                            .replaceAll("%tps%", String.valueOf(Tps.getTPS()));

                        ActionBar.sendActionBar(p, actionbarFirstjoin, OnJoinConfig.getConfig().getInt("Action-Bar.First-Join.Time-Stay"));
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Action-Bar.Join.Enable")) {
                    String actionbarjoin = OnJoinConfig.getConfig().getString("Action-Bar.Join.Message");
                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                        actionbarjoin = PlaceholderAPI.setPlaceholders(p, actionbarjoin);
                    }
                    actionbarjoin = actionbarjoin.replaceAll("&", "§")
                        .replaceAll("%player%", p.getName())
                        .replaceAll("%ping%", String.valueOf(PingCommand.getPing(p)))
                        .replaceAll("%DELAY%", String.valueOf(DelaychatCommand.delay))
                        .replaceAll("%tps%", String.valueOf(Tps.getTPS()));

                    ActionBar.sendActionBar(p, actionbarjoin);
                }
            }
        }

        // Join Command
        if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Enable")) {
            if (p.hasPlayedBefore()) {
                if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Player.Enable")) {
                    if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Player.World.All_World")) {
                        if (CommandsPW.getWPlayerJoinCommandNoNew().contains(p.getWorld().getName())) {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Player.Commands")) {
                                p.performCommand(commands);
                            }
                        }
                    } else {
                        for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Player.Commands")) {
                            p.performCommand(commands);
                        }
                    }
                }
                if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Console.Enable")) {
                    if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Console.World.All_World")) {
                        if (CommandsPW.getWConsoleJoinCommandNoNew().contains(p.getWorld().getName())) {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Console.Commands")) {
                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
                            }
                        }
                    } else {
                        for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Console.Commands")) {
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
                        }
                    }
                }
            } else {
                if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Player.Enable")) {
                    if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Player.World.All_World")) {
                        if (CommandsPW.getWPlayerJoinCommandNew().contains(p.getWorld().getName())) {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.JoinCommand-Player.Commands")) {
                                p.performCommand(commands);
                            }
                        }
                    } else {
                        for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.JoinCommand-Player.Commands")) {
                            p.performCommand(commands);
                        }
                    }
                } else {
                    if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Player.Enable")) {
                        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Player.World.All_World")) {
                            if (CommandsPW.getWPlayerJoinCommandNoNew().contains(p.getWorld().getName())) {
                                for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Player.Commands")) {
                                    p.performCommand(commands);
                                }
                            }
                        } else {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Player.Commands")) {
                                p.performCommand(commands);
                            }
                        }
                    }
                }
                if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Console.Enable")) {
                    if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.JoinCommand-Console.World.All_World")) {
                        if (CommandsPW.getWConsoleJoinCommandNew().contains(p.getWorld().getName())) {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.JoinCommand-Console.Commands")) {
                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
                            }
                        }
                    } else {
                        for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.JoinCommand-Console.Commands")) {
                            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
                        }
                    }
                } else {
                    if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Console.Enable")) {
                        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.JoinCommand-Console.World.All_World")) {
                            if (CommandsPW.getWConsoleJoinCommandNoNew().contains(p.getWorld().getName())) {
                                for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Console.Commands")) {
                                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
                                }
                            }
                        } else {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.JoinCommand-Console.Commands")) {
                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands.replaceAll("%player%", p.getName()));
                            }
                        }
                    }
                }
            }
        }

        // TitleJoin
        if (OnJoinConfig.getConfig().getBoolean("Title.Enable")) {
            if (OnJoinConfig.getConfig().getBoolean("Title.First-Join.Enable")) {
                if (!OnJoinConfig.getConfig().getBoolean("Title.First-Join.World.All_World")) {
                    if (OnJoinPW.getWFirstJoinTitle().contains(p.getWorld().getName())) {
                        if (p.hasPlayedBefore()) {
                            Titlemethod(p);
                        } else {
                            if (OnJoinConfig.getConfig().getBoolean("Title.First-Join.Title.Enable")) {
                                TitleUtils.sendTitle(p, OnJoinConfig.getConfig().getInt("Title.First-Join.Title.FadeIn"), OnJoinConfig.getConfig().getInt("Title.First-Join.Title.Stay"), OnJoinConfig.getConfig().getInt("Title.First-Join.Title.FadeOut"), OnJoinConfig.getConfig().getString("Title.First-Join.Title.Message"));
                            }
                            if (OnJoinConfig.getConfig().getBoolean("Title.First-Join.SubTitle.Enable")) {
                                TitleUtils.sendSubtitle(p, OnJoinConfig.getConfig().getInt("Title.First-Join.SubTitle.FadeIn"), OnJoinConfig.getConfig().getInt("Title.First-Join.SubTitle.Stay"), OnJoinConfig.getConfig().getInt("Title.First-Join.SubTitle.FadeOut"), OnJoinConfig.getConfig().getString("Title.First-Join.SubTitle.Message"));
                            }
                        }
                    }
                } else {
                    if (p.hasPlayedBefore()) {
                        Titlemethod(p);
                    } else {
                        if (OnJoinConfig.getConfig().getBoolean("Title.First-Join.Title.Enable")) {
                            TitleUtils.sendTitle(p, OnJoinConfig.getConfig().getInt("Title.First-Join.Title.FadeIn"), OnJoinConfig.getConfig().getInt("Title.First-Join.Title.Stay"), OnJoinConfig.getConfig().getInt("Title.First-Join.Title.FadeOut"), OnJoinConfig.getConfig().getString("Title.First-Join.Title.Message"));
                        }
                        if (OnJoinConfig.getConfig().getBoolean("Title.First-Join.SubTitle.Enable")) {
                            TitleUtils.sendSubtitle(p, OnJoinConfig.getConfig().getInt("Title.First-Join.SubTitle.FadeIn"), OnJoinConfig.getConfig().getInt("Title.First-Join.SubTitle.Stay"), OnJoinConfig.getConfig().getInt("Title.First-Join.SubTitle.FadeOut"), OnJoinConfig.getConfig().getString("Title.First-Join.SubTitle.Message"));
                        }
                    }
                }
            } else {
                Titlemethod(p);
            }
        }
        
        /*if (NameTagConfig.getConfig().getBoolean("nametag-general.enable")) {			
			for(Player p1 : Bukkit.getServer().getOnlinePlayers()) {
				Iterator<?> iterator = NameTagConfig.getConfig().getConfigurationSection("nametag").getKeys(false).iterator();
				
				while (iterator.hasNext()) {
					String string = (String)iterator.next();
					
					if (p1.hasPermission("hawn.nametag." + string)) {
						
						Tablist.setTeam(p1, string, NameTagConfig.getConfig().getInt("nametag." + string + ".priority") + string);
						
						break;
					}
				}
			}
		}*/
        
        player_list.add(p);
        Main.buildbypasscommand.remove(p);
    }
    
    @SuppressWarnings("deprecation")
    public void Titlemethod(Player p) {
        if (OnJoinConfig.getConfig().getBoolean("Title.Join.Enable")) {
            if (!OnJoinConfig.getConfig().getBoolean("Title.Join.World.All_World")) {
                if (OnJoinPW.getWJoinTitle().contains(p.getWorld().getName())) {
                    if (OnJoinConfig.getConfig().getBoolean("Title.Join.Title.Enable")) {
                        TitleUtils.sendTitle(p, OnJoinConfig.getConfig().getInt("Title.Join.Title.FadeIn"), OnJoinConfig.getConfig().getInt("Title.Join.Title.Stay"), OnJoinConfig.getConfig().getInt("Title.Join.Title.FadeOut"), OnJoinConfig.getConfig().getString("Title.Join.Title.Message"));
                    }
                    if (OnJoinConfig.getConfig().getBoolean("Title.Join.SubTitle.Enable")) {
                        TitleUtils.sendSubtitle(p, OnJoinConfig.getConfig().getInt("Title.Join.SubTitle.FadeIn"), OnJoinConfig.getConfig().getInt("Title.Join.SubTitle.Stay"), OnJoinConfig.getConfig().getInt("Title.Join.SubTitle.FadeOut"), OnJoinConfig.getConfig().getString("Title.Join.SubTitle.Message"));
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Title.Join.Title.Enable")) {
                    TitleUtils.sendTitle(p, OnJoinConfig.getConfig().getInt("Title.Join.Title.FadeIn"), OnJoinConfig.getConfig().getInt("Title.Join.Title.Stay"), OnJoinConfig.getConfig().getInt("Title.Join.Title.FadeOut"), OnJoinConfig.getConfig().getString("Title.Join.Title.Message"));
                }
                if (OnJoinConfig.getConfig().getBoolean("Title.Join.SubTitle.Enable")) {
                    TitleUtils.sendSubtitle(p, OnJoinConfig.getConfig().getInt("Title.Join.SubTitle.FadeIn"), OnJoinConfig.getConfig().getInt("Title.Join.SubTitle.Stay"), OnJoinConfig.getConfig().getInt("Title.Join.SubTitle.FadeOut"), OnJoinConfig.getConfig().getString("Title.Join.SubTitle.Message"));
                }
            }
        }
    }

    public void Fireworkmethod(Player p) {
        for (int i = 1; i < ConfigGCos.getConfig().getInt("Cosmetics.Firework.Options.Amount"); i++) {
            ArrayList < Color > colors = new ArrayList < Color > ();
            ArrayList < Color > fade = new ArrayList < Color > ();
            List < String > lore = ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Colors");
            List < String > lore2 = ConfigGCos.getConfig().getStringList("Cosmetics.Firework.Options.Fade");
            for (String l1: lore) {
                colors.add(OtherUtils.getColor(l1));
            }
            for (String l2: lore2) {
                fade.add(OtherUtils.getColor(l2));
            }
            final Firework f = p.getWorld().spawn(p.getLocation().add(0.5D, ConfigGCos.getConfig().getInt("Cosmetics.Firework.Options.Height"), 0.5D), Firework.class);

            FireworkMeta fm = f.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder().flicker(ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Flicker"))
                .trail(ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Trail"))
                .with(FireworkEffect.Type.valueOf(ConfigGCos.getConfig().getString("Cosmetics.Firework.Options.Type"))).withColor(colors).withFade(fade)
                .build());

            if (!ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Instant-explode")) {
                fm.setPower(ConfigGCos.getConfig().getInt("Cosmetics.Firework.Options.Power"));
            }
            f.setFireworkMeta(fm);
            if (ConfigGCos.getConfig().getBoolean("Cosmetics.Firework.Options.Instant-explode")) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
                    public void run() {
                        f.detonate();
                    }
                }, 5L);
            }
        }
    }

    public void SoundMethodOnJoin(Player p) {
        if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Sounds.Enable")) {
            if (!ConfigGCos.getConfig().getBoolean("Event.OnJoin.Sounds.World.All_World")) {
                if (OnJoinPW.getWSoundsJoin().contains(p.getWorld().getName())) {
                    String sound = OnJoinConfig.getConfig().getString("Event.OnJoin.Sounds.Sound");
                    int volume = OnJoinConfig.getConfig().getInt("Event.OnJoin.Sounds.Volume");
                    int pitch = OnJoinConfig.getConfig().getInt("Event.OnJoin.Sounds.Pitch");
                    p.playSound(p.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
                }
            } else {
                String sound = OnJoinConfig.getConfig().getString("Event.OnJoin.Sounds.Sound");
                int volume = OnJoinConfig.getConfig().getInt("Event.OnJoin.Sounds.Volume");
                int pitch = OnJoinConfig.getConfig().getInt("Event.OnJoin.Sounds.Pitch");
                p.playSound(p.getLocation(), XSound.matchXSound(sound).parseSound(), volume, pitch);
            }

        }
    }
    
    public void TPSPAWNnormalevent (Player p) {
    	if (p.hasPlayedBefore()) {
            if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Tp-To-Spawn")) {
                if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Custom-Group-Join.VIP.Enable")) {
                    if (p.hasPermission("hawn.event.spawn.join.vip")) {
                        if (OnJoinConfig.getConfig().getString("Event.OnJoin.Custom-Group-Join.VIP.Spawn").contentEquals("CHANGE ME")) {
                            p.sendMessage("You have to change the spawn on Event.OnJoin.Custom-Group-Join.VIP.Spawn on Events/OnJoin.yml");
                        } else {
                            if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Event.OnJoin.Custom-Group-Join.VIP.Spawn"))) {
                                MessageUtils.MessageNoSpawn(p);
                            } else {
                                SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Event.OnJoin.Custom-Group-Join.VIP.Spawn"));
                                if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                    if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                        for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                            MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                        }
                                    }
                                }

                                SoundMethodOnJoin(p);
                            }
                        }
                    } else {
                        if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.CustomSpawn.Enable")) {
                            if (OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
                                p.sendMessage("You have to change the spawn on Event.OnJoin.CustomSpawn.Spawn on Events/OnJoin.yml");
                            } else {
                                if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                    MessageUtils.MessageNoSpawn(p);
                                } else {
                                    if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                        String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn");
                                        MessageUtils.MessageNoPermission(p, Permission);
                                    } else {
                                        SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"));
                                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                            if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }
                                        }

                                        SoundMethodOnJoin(p);
                                    }
                                }
                            }
                        } else {
                            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                                p.sendMessage("You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml");
                            } else {
                                if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                    MessageUtils.MessageNoSpawn(p);
                                } else {
                                    if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                        String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
                                        MessageUtils.MessageNoPermission(p, Permission);
                                    } else {
                                        SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
                                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                            if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }
                                        }

                                        SoundMethodOnJoin(p);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.CustomSpawn.Enable")) {
                        if (OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
                            p.sendMessage("You have to change the spawn on Event.OnJoin.CustomSpawn.Spawn on Events/OnJoin.yml");
                        } else {
                            if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                MessageUtils.MessageNoSpawn(p);
                            } else {
                                if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                    String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn");
                                    MessageUtils.MessageNoPermission(p, Permission);
                                } else {
                                    SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"));
                                    if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                            for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }
                                    }

                                    SoundMethodOnJoin(p);
                                }
                            }
                        }
                    } else {
                        if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                            p.sendMessage("You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml");
                        } else {
                            if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                MessageUtils.MessageNoSpawn(p);
                            } else {
                                if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                    String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
                                    MessageUtils.MessageNoPermission(p, Permission);
                                } else {
                                    SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
                                    if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                            for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }
                                    }

                                    SoundMethodOnJoin(p);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (OnJoinConfig.getConfig().getBoolean("Spawn.FirstJoin-Spawn.Enable")) {
                if (OnJoinConfig.getConfig().getString("Spawn.FirstJoin-Spawn.Spawn").contentEquals("CHANGE ME")) {
                    p.sendMessage("You have to change the spawn on Spawn.FirstJoin-Spawn.Spawn on Events/OnJoin.yml");
                } else {
                    if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.FirstJoin-Spawn.Spawn"))) {
                        MessageUtils.MessageNoSpawn(p);
                    } else {
                        SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.FirstJoin-Spawn.Spawn"));
                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                            if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                }
                            }
                        }

                        SoundMethodOnJoin(p);
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Tp-To-Spawn")) {
                    if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Custom-Group-Join.VIP.Enable")) {
                        if (p.hasPermission("hawn.event.spawn.join.vip")) {
                            if (OnJoinConfig.getConfig().getString("Event.OnJoin.Custom-Group-Join.VIP.Spawn").contentEquals("CHANGE ME")) {
                                p.sendMessage("You have to change the spawn on Event.OnJoin.Custom-Group-Join.VIP.Spawn on Events/OnJoin.yml");
                            } else {
                                if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Event.OnJoin.Custom-Group-Join.VIP.Spawn"))) {
                                    MessageUtils.MessageNoSpawn(p);
                                } else {
                                    SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Event.OnJoin.Custom-Group-Join.VIP.Spawn"));
                                    if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                            for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                            }
                                        }
                                    }

                                    SoundMethodOnJoin(p);
                                }
                            }
                        } else {
                            if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.CustomSpawn.Enable")) {
                                if (OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
                                    p.sendMessage("You have to change the spawn on Event.OnJoin.CustomSpawn.Spawn on Events/OnJoin.yml");
                                } else {
                                    if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                        MessageUtils.MessageNoSpawn(p);
                                    } else {
                                        if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                            String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn");
                                            MessageUtils.MessageNoPermission(p, Permission);
                                        } else {
                                            SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"));
                                            if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                                if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                                    for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                    }
                                                }
                                            }

                                            SoundMethodOnJoin(p);
                                        }
                                    }
                                }
                            } else {
                                if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                                    p.sendMessage("You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml");
                                } else {
                                    if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                        MessageUtils.MessageNoSpawn(p);
                                    } else {
                                        if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                            String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
                                            MessageUtils.MessageNoPermission(p, Permission);
                                        } else {
                                            SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
                                            if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                                if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                                    for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                        MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                    }
                                                }
                                            }

                                            SoundMethodOnJoin(p);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (OnJoinConfig.getConfig().getBoolean("Event.OnJoin.CustomSpawn.Enable")) {
                            if (OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn").contentEquals("CHANGE ME")) {
                                p.sendMessage("You have to change the spawn on Event.OnJoin.CustomSpawn.Spawn on Events/OnJoin.yml");
                            } else {
                                if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                    MessageUtils.MessageNoSpawn(p);
                                } else {
                                    if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"))) {
                                        String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn");
                                        MessageUtils.MessageNoPermission(p, Permission);
                                    } else {
                                        SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Event.OnJoin.CustomSpawn.Spawn"));
                                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                            if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }
                                        }

                                        SoundMethodOnJoin(p);
                                    }
                                }
                            }
                        } else {
                            if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
                                p.sendMessage("You have to change the spawn on Spawn.DefaultSpawn on Events/OnJoin.yml");
                            } else {
                                if (!ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                    MessageUtils.MessageNoSpawn(p);
                                } else {
                                    if (!p.hasPermission("hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
                                        String Permission = "hawn.command.spawn." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
                                        MessageUtils.MessageNoPermission(p, Permission);
                                    } else {
                                        SpawnUtils.teleportToSpawn(p, OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"));
                                        if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable-For-On-Join")) {
                                            if (ConfigMGeneral.getConfig().getBoolean("Spawn.Teleport.Enable")) {
                                                for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
                                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                                }
                                            }
                                        }

                                        SoundMethodOnJoin(p);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
