package fr.Dianox.Hawn.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.XSound;
import fr.Dianox.Hawn.Utility.Config.AutoBroadcastConfig;
import fr.Dianox.Hawn.Utility.World.BasicEventsPW;

public class AutoBroadcast extends BukkitRunnable {

    @SuppressWarnings("unused")
    private final JavaPlugin pl;

    public AutoBroadcast(JavaPlugin pl) {
        this.pl = pl;
    }

    String msg = "";
    public static List < String > newmessage = new ArrayList < String > ();

    public void run() {
    	newmessage.clear();
        if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Random")) {
            Random rand = new Random();

            int value = rand.nextInt(Main.autobroadcast_total + 1);

            msg = String.valueOf(Main.autobroadcast.get(value));

            if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Header.Enable")) {
                for (String s: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Header.messages")) {
                    newmessage.add(s);
                }
            }

            for (String msg: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".message")) {
                newmessage.add(msg);
            }

            if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Footer.Enable")) {
                for (String s: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Footer.messages")) {
                    newmessage.add(s);
                }
            }

            for (Player p: Bukkit.getServer().getOnlinePlayers()) {
            	Boolean check = false;
            	
                if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Use-Permission-To-Get-Messages")) {
                    if (p.hasPermission("hawn.get.autobroadcast")) {
                        if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
                            for (String s: newmessage) {
                            	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                                	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                                		if (p.getWorld().getName().contains(str)) {
                                			check = true;
                                		}
                                	}
                            	} else {
                            		check = true;
                            	}
                            	
                            	if (!check) {
                            		continue;
                            	}
                            	
                            	if (s.startsWith("[sounds]: ")) {
                                    s = s.replace("[sounds]: ", "");
                                    p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                                } else {
                                	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                                		s = s + "<--center-->";
                                	}

                                	MessageUtils.ReplaceCharMessagePlayer(s, p);
                                }
                            }
                        } else {
                            if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
                                for (String s: newmessage) {
                                	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                                    	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                                    		if (p.getWorld().getName().contains(str)) {
                                    			check = true;
                                    		}
                                    	}
                                	} else {
                                		check = true;
                                	}
                                	
                                	if (!check) {
                                		continue;
                                	}
                                	
                                	if (s.startsWith("[sounds]: ")) {
                                        s = s.replace("[sounds]: ", "");
                                        p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                                    } else {
                                    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                                    		s = s + "<--center-->";
                                    	}

                                    	MessageUtils.ReplaceCharMessagePlayer(s, p);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
                        for (String s: newmessage) {
                        	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                            	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                            		if (p.getWorld().getName().contains(str)) {
                            			check = true;
                            		}
                            	}
                        	} else {
                        		check = true;
                        	}
                        	
                        	if (!check) {
                        		continue;
                        	}
                        	
                        	if (s.startsWith("[sounds]: ")) {
                                s = s.replace("[sounds]: ", "");
                                p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                            } else {
                            	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                            		s = s + "<--center-->";
                            	}

                            	MessageUtils.ReplaceCharMessagePlayer(s, p);
                            }
                        }
                    } else {
                        if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
                            for (String s: newmessage) {
                            	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                                	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                                		if (p.getWorld().getName().contains(str)) {
                                			check = true;
                                		}
                                	}
                            	} else {
                            		check = true;
                            	}
                            	
                            	if (!check) {
                            		continue;
                            	}
                            	
                            	if (s.startsWith("[sounds]: ")) {
                                    s = s.replace("[sounds]: ", "");
                                    p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                                } else {
                                	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                                		s = s + "<--center-->";
                                	}

                                	MessageUtils.ReplaceCharMessagePlayer(s, p);
                                }
                            }
                        }
                    }
                }
            }

            if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Broadcast-To-Console")) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg
                    .replaceAll("%player%", "Player name")
                    .replaceAll("%target%", "Player name")
                ));
            }
        } else {
            if (Main.curMsg <= Main.autobroadcast_total) {
                msg = String.valueOf(Main.autobroadcast.get(Main.curMsg));

                if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Header.Enable")) {
                    for (String s: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Header.messages")) {
                        newmessage.add(s);
                    }
                }

                for (String msg: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".message")) {
                    newmessage.add(msg);
                }

                if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Custom-Header-Footer.Footer.Enable")) {
                    for (String s: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.Custom-Header-Footer.Footer.messages")) {
                        newmessage.add(s);
                    }
                }

                for (Player p: Bukkit.getServer().getOnlinePlayers()) {
                	Boolean check = false;
                	
                    if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Use-Permission-To-Get-Messages")) {
                        if (p.hasPermission("hawn.get.autobroadcast")) {
                            if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
                                for (String s: newmessage) {
                                	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                                    	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                                    		if (p.getWorld().getName().contains(str)) {
                                    			check = true;
                                    		}
                                    	}
                                	} else {
                                		check = true;
                                	}
                                	
                                	if (!check) {
                                		continue;
                                	}
                                	
                                	if (s.startsWith("[sounds]: ")) {
                                        s = s.replace("[sounds]: ", "");
                                        p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                                    } else {
                                    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                                    		s = s + "<--center-->";
                                    	}

                                    	MessageUtils.ReplaceCharMessagePlayer(s, p);
                                    }
                                }
                            } else {
                                if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
                                    for (String s: newmessage) {
                                    	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                                        	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                                        		if (p.getWorld().getName().contains(str)) {
                                        			check = true;
                                        		}
                                        	}
                                    	} else {
                                    		check = true;
                                    	}
                                    	
                                    	if (!check) {
                                    		continue;
                                    	}
                                    	
                                    	if (s.startsWith("[sounds]: ")) {
                                            s = s.replace("[sounds]: ", "");
                                            p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                                        } else {
                                        	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                                        		s = s + "<--center-->";
                                        	}

                                        	MessageUtils.ReplaceCharMessagePlayer(s, p);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.World.All_World")) {
                            for (String s: newmessage) {
                            	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                                	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                                		if (p.getWorld().getName().contains(str)) {
                                			check = true;
                                		}
                                	}
                            	} else {
                            		check = true;
                            	}
                            	
                            	if (!check) {
                            		continue;
                            	}
                            	
                            	if (s.startsWith("[sounds]: ")) {
                                    s = s.replace("[sounds]: ", "");
                                    p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                                } else {
                                	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                                		s = s + "<--center-->";
                                	}

                                	MessageUtils.ReplaceCharMessagePlayer(s, p);
                                }
                            }
                        } else {
                            if (BasicEventsPW.getAutoBroadcast().contains(p.getWorld().getName())) {
                                for (String s: newmessage) {
                                	if (AutoBroadcastConfig.getConfig().isSet("Config.Messages.messages." + msg + ".world_list")) {
                                    	for (String str: AutoBroadcastConfig.getConfig().getStringList("Config.Messages.messages." + msg + ".world_list")) {
                                    		if (p.getWorld().getName().contains(str)) {
                                    			check = true;
                                    		}
                                    	}
                                	} else {
                                		check = true;
                                	}
                                	
                                	if (!check) {
                                		continue;
                                	}
                                	
                                	if (s.startsWith("[sounds]: ")) {
                                        s = s.replace("[sounds]: ", "");
                                        p.playSound(p.getLocation(), XSound.matchXSound(s).parseSound(), 1, 1);
                                    } else {
                                    	if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Options.Auto-Center")) {
                                    		s = s + "<--center-->";
                                    	}

                                    	MessageUtils.ReplaceCharMessagePlayer(s, p);
                                    }
                                }
                            }
                        }
                    }
                }

                if (AutoBroadcastConfig.getConfig().getBoolean("Config.Messages.Broadcast-To-Console")) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', msg
                        .replaceAll("%player%", "Player name")
                        .replaceAll("%target%", "Player name")
                    ));
                }

                Main.curMsg++;
            } else {
                Main.curMsg = 0;
            }
        }
    }

}
