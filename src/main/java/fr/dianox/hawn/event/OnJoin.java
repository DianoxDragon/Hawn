package fr.dianox.hawn.event;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.SQL;
import fr.dianox.hawn.commands.features.warp.WarpCommand;
import fr.dianox.hawn.event.onjoine.OJMessages;
import fr.dianox.hawn.event.onjoine.OjCosmetics;
import fr.dianox.hawn.event.onjoine.OjPlayerOption;
import fr.dianox.hawn.modules.onjoin.cji.CustomJoinItem;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.config.ConfigGeneral;
import fr.dianox.hawn.utility.config.ConfigSpawn;
import fr.dianox.hawn.utility.config.PlayerOptionMainConfig;
import fr.dianox.hawn.utility.config.cosmeticsfun.ConfigGCos;
import fr.dianox.hawn.utility.config.customjoinitem.ConfigCJIGeneral;
import fr.dianox.hawn.utility.config.events.ConfigGJoinQuitCommand;
import fr.dianox.hawn.utility.config.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.messages.ConfigMCommands;
import fr.dianox.hawn.utility.config.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.tasks.SwitchClassicOnFirstJoinBB;
import fr.dianox.hawn.utility.tasks.SwitchClassicOnJoinBB;
import fr.dianox.hawn.utility.world.CommandsPW;
import fr.dianox.hawn.utility.world.OnJoinPW;
import fr.dianox.hawn.utility.world.PlayerEventsPW;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class OnJoin implements Listener {

    public static List < Player > player_list = new ArrayList < Player > ();
    int gm = OnJoinConfig.getConfig().getInt("Change-Gamemode.Value");

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

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
        	
        	Integer number = PlayerOptionSQLClass.getNumberOfConnection(p);
        	
        	number++;
        	
        	PlayerOptionSQLClass.SaveSQLNumberofConnections(p, number);
        	
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

        // Clear chat
        ChatClear(p);

        // Force selected slot
        ForceSelectedSlot(p);

        // JOIN TP SPAWN
        if (p.hasPlayedBefore() && PlayerOptionMainConfig.getConfig().getBoolean("TP.Last-Position-On-Join.Enable") && p.hasPermission("hawn.betweenservers.tplastposition") && (ConfigPlayerGet.getFile(uuid).isSet("player_last_position.World") || SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_last_position"))) {
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
                    if (SQL.exists("player_UUID", "" + p.getUniqueId() + "", "player_last_position")) {
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

        OjCosmetics.MainMethod(p);

        // Reset XP
        ResetXP(p);

        // Restore Health and food
        FoodRestore(p);
        HealthRestore(p);

        // Inventory Clear
        InventoryUtils(p);

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

                                actionbarjoin = PlaceHolders.ReplaceMainplaceholderP(actionbarjoin, p);

                                actionbarjoin = actionbarjoin.replaceAll("&", "§");

                                ActionBar.sendActionBar(p, actionbarjoin, OnJoinConfig.getConfig().getInt("Action-Bar.Join.Time-Stay"));
                            }
                        } else {
                            String actionbarjoin = OnJoinConfig.getConfig().getString("Action-Bar.Join.Message");
                            if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                                actionbarjoin = PlaceholderAPI.setPlaceholders(p, actionbarjoin);
                            }

                            actionbarjoin = PlaceHolders.ReplaceMainplaceholderP(actionbarjoin, p);

                            actionbarjoin = actionbarjoin.replaceAll("&", "§");

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

                            actionbarFirstjoin = PlaceHolders.ReplaceMainplaceholderP(actionbarFirstjoin, p);

                            actionbarFirstjoin = actionbarFirstjoin.replaceAll("&", "§");

                            ActionBar.sendActionBar(p, actionbarFirstjoin, OnJoinConfig.getConfig().getInt("Action-Bar.First-Join.Time-Stay"));
                        }
                    } else {
                        String actionbarFirstjoin = OnJoinConfig.getConfig().getString("Action-Bar.First-Join.Message");
                        if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                            actionbarFirstjoin = PlaceholderAPI.setPlaceholders(p, actionbarFirstjoin);
                        }

                        actionbarFirstjoin = PlaceHolders.ReplaceMainplaceholderP(actionbarFirstjoin, p);

                        actionbarFirstjoin = actionbarFirstjoin.replaceAll("&", "§");

                        ActionBar.sendActionBar(p, actionbarFirstjoin, OnJoinConfig.getConfig().getInt("Action-Bar.First-Join.Time-Stay"));
                    }
                }
            } else {
                if (OnJoinConfig.getConfig().getBoolean("Action-Bar.Join.Enable")) {
                    String actionbarjoin = OnJoinConfig.getConfig().getString("Action-Bar.Join.Message");
                    if (ConfigGeneral.getConfig().getBoolean("Plugin.Use.PlaceholderAPI")) {
                        actionbarjoin = PlaceholderAPI.setPlaceholders(p, actionbarjoin);
                    }

                    actionbarjoin = PlaceHolders.ReplaceMainplaceholderP(actionbarjoin, p);

                    actionbarjoin = actionbarjoin.replaceAll("&", "§");

                    ActionBar.sendActionBar(p, actionbarjoin, OnJoinConfig.getConfig().getInt("Action-Bar.Join.Time-Stay"));
                }
            }
        }

        // Join Command
        if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Enable")) {
            if (p.hasPlayedBefore()) {
                if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.Enable")) {
                    if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.World.All_World")) {
                        if (CommandsPW.getWJoinCommandNoNew().contains(p.getWorld().getName())) {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.Commands")) {
                                CommandsMethod(p, commands);
                            }
                        }
                    } else {
                        for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.Commands")) {
                            CommandsMethod(p, commands);
                        }
                    }
                }
            } else {
                if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.Enable")) {
                    if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.New.World.All_World")) {
                        if (CommandsPW.getWJoinCommandNew().contains(p.getWorld().getName())) {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.Commands")) {
                                CommandsMethod(p, commands);
                            }
                        }
                    } else {
                        for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.New.Commands")) {
                            CommandsMethod(p, commands);
                        }
                    }
                } else {
                    if (ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.Enable")) {
                        if (!ConfigGJoinQuitCommand.getConfig().getBoolean("JoinCommand.Options.No-New.World.All_World")) {
                            if (CommandsPW.getWJoinCommandNoNew().contains(p.getWorld().getName())) {
                                for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.Commands")) {
                                    CommandsMethod(p, commands);
                                }
                            }
                        } else {
                            for (String commands: ConfigGJoinQuitCommand.getConfig().getStringList("JoinCommand.Options.No-New.Commands")) {
                                CommandsMethod(p, commands);
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

        player_list.add(p);
        Main.buildbypasscommand.remove(p);
        
        if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Enable")) {
        	if (!OnJoinConfig.getConfig().getBoolean("Boss-Bar.World.All_World")) {
                if (OnJoinPW.getBB().contains(p.getWorld().getName())) {
                	if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Enable")) {
            			if (p.hasPlayedBefore()) {
            				if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Enable")) {
            					if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Time.Keep-Bar")) {
            						BossBarApi.BBBlock.add(p);
            						
            						createClassicBB(p);
            					} else {
            						createClassicBB(p);
            						BossBarApi.BBBlock.add(p);
            						
            						BukkitTask task = new SwitchClassicOnJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.Join.Time.If-not.Time-Stay"));
            						
            						BossBarApi.ptaskbb.put(p, task.getTaskId());
            					}
            				}
            			} else {
            				if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Time.Keep-Bar")) {
        						BossBarApi.BBBlock.add(p);
        						
        						createClassicBBFJ(p);
        					} else {
        						createClassicBBFJ(p);
        						BossBarApi.BBBlock.add(p);
        						
        						BukkitTask task = new SwitchClassicOnFirstJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.First-Join.Time.If-not.Time-Stay"));
        						
        						BossBarApi.ptaskbb.put(p, task.getTaskId());
        					}
            			}
                	} else {
                		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Enable")) {
        					if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Time.Keep-Bar")) {
        						BossBarApi.BBBlock.add(p);
        						
        						createClassicBB(p);
        					} else {
        						createClassicBB(p);
        						BossBarApi.BBBlock.add(p);
        						
        						BukkitTask task = new SwitchClassicOnJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.Join.Time.If-not.Time-Stay"));
        						
        						BossBarApi.ptaskbb.put(p, task.getTaskId());
        					}
        				}
                	}
                }
        	} else {
        		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Enable")) {
        			if (p.hasPlayedBefore()) {
        				if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Enable")) {
        					if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Time.Keep-Bar")) {
        						BossBarApi.BBBlock.add(p);
        						
        						createClassicBB(p);
        					} else {
        						createClassicBB(p);
        						BossBarApi.BBBlock.add(p);
        						
        						BukkitTask task = new SwitchClassicOnJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.Join.Time.If-not.Time-Stay"));
        						
        						BossBarApi.ptaskbb.put(p, task.getTaskId());
        					}
        				}
        			} else {
        				if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.First-Join.Time.Keep-Bar")) {
    						BossBarApi.BBBlock.add(p);
    						
    						createClassicBBFJ(p);
    					} else {
    						createClassicBBFJ(p);
    						BossBarApi.BBBlock.add(p);
    						
    						BukkitTask task = new SwitchClassicOnFirstJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.First-Join.Time.If-not.Time-Stay"));
    						
    						BossBarApi.ptaskbb.put(p, task.getTaskId());
    					}
        			}
            	} else {
            		if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Enable")) {
    					if (OnJoinConfig.getConfig().getBoolean("Boss-Bar.Join.Time.Keep-Bar")) {
    						BossBarApi.BBBlock.add(p);
    						
    						createClassicBB(p);
    					} else {
    						createClassicBB(p);
    						BossBarApi.BBBlock.add(p);
    						
    						BukkitTask task = new SwitchClassicOnJoinBB(p).runTaskLater(Main.getInstance(), OnJoinConfig.getConfig().getInt("Boss-Bar.Join.Time.If-not.Time-Stay"));
    						
    						BossBarApi.ptaskbb.put(p, task.getTaskId());
    					}
    				}
            	}
        	}
        }
    }
    
    private void createClassicBBFJ(Player p) {
    	if (!OnJoinConfig.getConfig().isSet("Boss-Bar.First-Join.Message")) {
			return;
		}
    	
		Double progress = 1D;
		
		if (OnJoinConfig.getConfig().isSet("Boss-Bar.First-Join.Progress")) {
			progress = OnJoinConfig.getConfig().getDouble("Boss-Bar.First-Join.Progress");
		}
		
    	BossBarApi.createnewbar(p, OnJoinConfig.getConfig().getString("Boss-Bar.First-Join.Color"), 
    			OnJoinConfig.getConfig().getString("Boss-Bar.First-Join.Message"), OnJoinConfig.getConfig().getString("Boss-Bar.First-Join.Style"), progress);
    }
    
    private void createClassicBB(Player p) {
    	if (!OnJoinConfig.getConfig().isSet("Boss-Bar.Join.Message")) {
			return;
		}
    	
		Double progress = 1D;
		
		if (OnJoinConfig.getConfig().isSet("Boss-Bar.Join.Progress")) {
			progress = OnJoinConfig.getConfig().getDouble("Boss-Bar.Join.Progress");
		}
		
    	BossBarApi.createnewbar(p, OnJoinConfig.getConfig().getString("Boss-Bar.Join.Color"), 
    			OnJoinConfig.getConfig().getString("Boss-Bar.Join.Message"), OnJoinConfig.getConfig().getString("Boss-Bar.Join.Style"), progress);
    }

    @SuppressWarnings("deprecation")
    private void CommandsMethod(Player p, String commands) {
        String perm = "";
        String world = "";

        if (commands.startsWith("<world>") && commands.contains("</world>")) {
            world = StringUtils.substringBetween(commands, "<world>", "</world>");
            commands = commands.replace("<world>" + world + "</world> ", "");

            if (!p.getWorld().getName().equalsIgnoreCase(world)) {
                return;
            }
        }

        if (commands.contains("<perm>") && commands.contains("</perm>")) {
            perm = StringUtils.substringBetween(commands, "<perm>", "</perm>");
            commands = commands.replace("<perm>" + perm + "</perm> ", "");

            if (!p.hasPermission(perm)) {
                return;
            }
        }

        if (commands.startsWith("[command-player]: ")) {
            commands = commands.replace("[command-player]: ", "");
            commands = commands.replaceAll("%player%", p.getName());

            p.performCommand(commands);
        } else if (commands.startsWith("[command-console]: ")) {
            commands = commands.replace("[command-console]: ", "");
            commands = commands.replaceAll("%player%", p.getName());

            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), commands);
        } else if (commands.startsWith("[gamemode-survival]")) {
            p.setGameMode(GameMode.SURVIVAL);
        } else if (commands.startsWith("[gamemode-creative]")) {
            p.setGameMode(GameMode.CREATIVE);
        } else if (commands.startsWith("[gamemode-adventure]")) {
            p.setGameMode(GameMode.ADVENTURE);
        } else if (commands.startsWith("[gamemode-spectator]")) {
            p.setGameMode(GameMode.SPECTATOR);
        } else if (commands.startsWith("[ping]")) {
            for (String commands1: ConfigMCommands.getConfig().getStringList("Ping.Self")) {
                MessageUtils.ReplaceCharMessagePlayer(commands1, p);
            }
        } else if (commands.startsWith("[spawn]: ")) {
            commands = commands.replace("[spawn]: ", "");
            SpawnUtils.teleportToSpawn(p, commands);
        } else if (commands.startsWith("[warp]: ")) {
            commands = commands.replace("[warp]: ", "");
            WarpCommand.onTp(p, commands);
        } else if (commands.startsWith("[bungee]: ")) {
            commands = commands.replace("[bungee]: ", "");
            Bungee.changeServer(p, commands);
        } else if (commands.startsWith("[effect[")) {
            commands = commands.replace("[effect[", "");

            String[] parts = commands.split("]]: ");

            p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(parts[1]), 1999999999, Integer.valueOf(parts[0])));
        } else if (commands.startsWith("[effectclear]: ")) {
            commands = commands.replace("[effectclear]: ", "");

            p.removePotionEffect(PotionEffectType.getByName(commands));
        } else if (commands.startsWith("[effectclearall]")) {
            for (PotionEffect effect: p.getActivePotionEffects()) {
                p.removePotionEffect(effect.getType());
            }
        } else if (commands.startsWith("[send-title]: ")) {
            commands = commands.replace("[send-title]: ", "");
            commands = commands.replaceAll("&", "§");

            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (commands.contains("//n")) {
                String[] parts = commands.split("//n");
                title = parts[0];
                subtitle = parts[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                TitleUtils.sendTitle(p, 20, 150, 75, commands);
            } else {
                TitleUtils.sendTitle(p, 20, 150, 75, title);
                TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
            }
        } else if (commands.startsWith("[send-title[")) {
            commands = commands.replace("[send-title[", "");

            String[] parts = commands.split("]]: ");

            Boolean activate = false;

            String title = "";
            String subtitle = "";

            if (commands.contains("//n")) {
                String[] part = parts[1].split("//n");
                title = part[0];
                subtitle = part[1];

                title = title.replaceAll("//n", "");
                subtitle = subtitle.replaceAll("//n", "");

                activate = true;
            }

            if (activate == false) {
                TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, parts[1]);
            } else {
                TitleUtils.sendTitle(p, 20, Integer.valueOf(parts[0]), 75, title);
                TitleUtils.sendSubtitle(p, 20, Integer.valueOf(parts[0]), 75, subtitle);
            }
        } else if (commands.startsWith("[send-actionbar]: ")) {
            commands = commands.replace("[send-actionbar]: ", "");
            commands = commands.replaceAll("&", "§");

            ActionBar.sendActionBar(p, commands);
        } else if (commands.startsWith("[send-actionbar[")) {
            commands = commands.replace("[send-actionbar[", "");

            String[] parts = commands.split("]]: ");

            ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
        } else if (commands.startsWith("[sounds]: ")) {
            commands = commands.replace("[sounds]: ", "");
            p.playSound(p.getLocation(), XSound.matchXSound(commands).parseSound(), 1, 1);
        } else {
            MessageUtils.ReplaceCharMessagePlayer(commands, p);
        }
    }

    private void InventoryUtils(Player p) {

        // Inventory clear
        if (OnJoinConfig.getConfig().getBoolean("Inventory.Clear.Enable")) {
            PlayerInventory inv = p.getInventory();

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

        // Custom Join Item
        CustomJoinItem.onItemGive(p);
    }

    private void HealthRestore(Player p) {
        if (OnJoinConfig.getConfig().getBoolean("Restore.Health.Enable")) {

            if (!OnJoinConfig.getConfig().getBoolean("Restore.Health.World.All_World")) {
                if (!OnJoinPW.getWHealth().contains(p.getWorld().getName())) {
                    return;
                }
            }

            if (OnJoinConfig.getConfig().getBoolean("Restore.Health.Bypass-With-Permission")) {
                if (p.hasPermission("hawn.bypass.healthrestore")) {
                    return;
                }
            }

            String HealthValue = String.valueOf(OnJoinConfig.getConfig().getDouble("Restore.Health.Value"));

            p.setHealth(Double.valueOf(HealthValue));
        }
    }

    private void FoodRestore(Player p) {
        if (OnJoinConfig.getConfig().getBoolean("Restore.Food.Enable")) {

            if (!OnJoinConfig.getConfig().getBoolean("Restore.Food.World.All_World")) {
                if (!OnJoinPW.getWFood().contains(p.getWorld().getName())) {
                    return;
                }
            }

            if (OnJoinConfig.getConfig().getBoolean("Restore.Food.Bypass-With-Permission")) {
                if (p.hasPermission("hawn.bypass.foodrestore")) {
                    return;
                }
            }

            String FoodValue = String.valueOf(OnJoinConfig.getConfig().getInt("Restore.Food.Value"));

            p.setFoodLevel(Integer.valueOf(FoodValue));
        }
    }

    private void ResetXP(Player p) {
        if (OnJoinConfig.getConfig().getBoolean("XP.Enable")) {

            /*
             * EXP
             */
            if (OnJoinConfig.getConfig().getBoolean("XP.Options.Exp.Enable")) {
                if (!OnJoinConfig.getConfig().getBoolean("XP.Options.Exp.World.All_World")) {
                    if (OnJoinPW.getWXPEXP().contains(p.getWorld().getName())) {

                        String XPValueEXP = String.valueOf(OnJoinConfig.getConfig().getDouble("XP.Options.Exp.Value"));

                        p.setExp(Float.valueOf(XPValueEXP));
                    }
                } else {

                    String XPValueEXP = String.valueOf(OnJoinConfig.getConfig().getDouble("XP.Options.Exp.Value"));

                    p.setExp(Float.valueOf(XPValueEXP));
                }
            }

            /*
             * Level
             */
            if (OnJoinConfig.getConfig().getBoolean("XP.Options.Level.Enable")) {
                if (!OnJoinConfig.getConfig().getBoolean("XP.Options.Level.World.All_World")) {
                    if (OnJoinPW.getWXPLVL().contains(p.getWorld().getName())) {

                        String XPValueLvl = String.valueOf(OnJoinConfig.getConfig().getInt("XP.Options.Level.Value"));

                        p.setLevel(Integer.valueOf(XPValueLvl));
                    }
                } else {

                    String XPValueLvl = String.valueOf(OnJoinConfig.getConfig().getInt("XP.Options.Level.Value"));

                    p.setLevel(Integer.valueOf(XPValueLvl));
                }
            }
        }
    }

    private void ForceSelectedSlot(Player p) {
        if (OnJoinConfig.getConfig().getBoolean("Inventory.Force-Selected-Slot.Enable")) {

            if (!OnJoinConfig.getConfig().getBoolean("Inventory.Force-Selected-Slot.World.All_World")) {
                if (!PlayerEventsPW.getWOForceSelectedSlot().contains(p.getWorld().getName())) {
                    return;
                }
            }

            Integer slot = OnJoinConfig.getConfig().getInt("Inventory.Force-Selected-Slot.Slot");

            if (slot < 0 || slot > 8) {
                slot = 0;
                Bukkit.getLogger().log(Level.SEVERE, "§7Hawn §3| §eWARNING §c(Configuration error)§e: §fYou put an invalid slot for the Force-Selected-Slot");
                Bukkit.getLogger().log(Level.SEVERE, "§7Hawn §3| §eWARNING §c(Configuration error)§e: §fThe slot §cmust§f be between §b0 and 8");
                Bukkit.getLogger().log(Level.SEVERE, "§7Hawn §3| §eWARNING §c(Configuration error)§e: §fWe have set the slot to 0 for the moment while waiting for a change");
            }

            PlayerInventory inv = p.getInventory();

            inv.setHeldItemSlot(slot);
        }
    }

    private void ChatClear(Player p) {
        if (OnJoinConfig.getConfig().getBoolean("Chat.Clear.Enable")) {

            if (!ConfigCJIGeneral.getConfig().getBoolean("Chat.Clear.World.All_World")) {
                if (!OnJoinPW.getWClearChat().contains(p.getWorld().getName())) {
                    return;
                }
            }

            if (OnJoinConfig.getConfig().getBoolean("Chat.Clear.Bypass")) {
                if (p.hasPermission("hawn.event.onjoin.bypass.clearchat")) {
                    return;
                }
            }

            int lines = OnJoinConfig.getConfig().getInt("Chat.Clear.Lines-To-Clear");

            for (int i = 0; i < lines; i++) {
                p.sendMessage("");
            }
        }
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

    public void TPSPAWNnormalevent(Player p) {
        if (p.hasPlayedBefore()) {
            if (!OnJoinConfig.getConfig().getBoolean("Event.OnJoin.Tp-To-Spawn")) return;

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