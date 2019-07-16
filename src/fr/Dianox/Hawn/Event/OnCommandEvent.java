package fr.Dianox.Hawn.Event;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.Dianox.Hawn.Commands.Features.Warp.WarpCommand;
import fr.Dianox.Hawn.Utility.ActionBar;
import fr.Dianox.Hawn.Utility.Bungee;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.SpawnUtils;
import fr.Dianox.Hawn.Utility.TitleUtils;
import fr.Dianox.Hawn.Utility.XSound;
import fr.Dianox.Hawn.Utility.Config.CustomCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Commands.HelpCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Events.CommandEventConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class OnCommandEvent implements Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBlockCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        if (!HelpCommandConfig.getConfig().getBoolean("DISABLE_THE_COMMAND_COMPLETELY")) {
            if (e.getMessage().startsWith("/help") || e.getMessage().startsWith("/?")) {
                e.setCancelled(true);
                p.performCommand(e.getMessage().replace("/", ""));
            }
        }

        if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Enable")) {
            if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Bypass")) {
                if (!p.hasPermission("hawn.event.bypass.blockcommands")) {
                    for (String i: CommandEventConfig.getConfig().getStringList("Block-Commands.List")) {
                        if (e.getMessage().equalsIgnoreCase(i)) {
                            e.setCancelled(true);
                            if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Message-Enable")) {
                                for (String msg: CommandEventConfig.getConfig().getStringList("Block-Commands.Message")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg, p);
                                }
                            }
                        }
                    }
                }
            } else {
                for (String i: CommandEventConfig.getConfig().getStringList("Block-Commands.List")) {
                    if (e.getMessage().equalsIgnoreCase(i)) {
                        e.setCancelled(true);
                        if (CommandEventConfig.getConfig().getBoolean("Block-Commands.Message-Enable")) {
                            for (String msg: CommandEventConfig.getConfig().getStringList("Block-Commands.Message")) {
                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            }
                        }
                    }
                }
            }
        }

        if (CustomCommandConfig.getConfig().getBoolean("commands-general.enable")) {
            Iterator < ? > iterator = CustomCommandConfig.getConfig().getConfigurationSection("commands").getKeys(false).iterator();

            while (iterator.hasNext()) {
                String string = (String) iterator.next();

                if (e.getMessage().equalsIgnoreCase(CustomCommandConfig.getConfig().getString("commands." + string + ".command"))) {
                    if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".enable")) {

                        if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".permission.enable")) {
                            if (!p.hasPermission(CustomCommandConfig.getConfig().getString("commands." + string + ".permission.message"))) {
                                if (CustomCommandConfig.getConfig().getBoolean("commands." + string + ".no-permission-message-enable")) {
                                    String Permission = String.valueOf(CustomCommandConfig.getConfig().getString("commands." + string + ".permission.message"));
                                    MessageUtils.MessageNoPermission(p, Permission);
                                }
                                
                                e.setCancelled(true);
                                return;
                            }
                        }
                        
                        for (String msg: CustomCommandConfig.getConfig().getStringList("commands." + string + ".message")) {

                            String perm = "";

                            if (msg.startsWith("<perm>") && msg.contains("</perm>")) {
                            	perm = StringUtils.substringBetween(msg, "<perm>", "</perm>");
                            	msg = msg.replace("<perm>"+perm+"</perm> ", "");
                            	
                            	if (!p.hasPermission(perm)) {
                            		continue;
                            	}
                            }

                            if (msg.startsWith("[command-player]: ")) {
                                msg = msg.replace("[command-player]: ", "");
                                msg = msg.replaceAll("%player%", p.getName());

                                p.performCommand(msg);
                            } else if (msg.startsWith("[command-console]: ")) {
                                msg = msg.replace("[command-console]: ", "");
                                msg = msg.replaceAll("%player%", p.getName());

                                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), msg);
                            } else if (msg.startsWith("[gamemode-survival]")) {
                                p.setGameMode(GameMode.SURVIVAL);
                            } else if (msg.startsWith("[gamemode-creative]")) {
                                p.setGameMode(GameMode.CREATIVE);
                            } else if (msg.startsWith("[gamemode-adventure]")) {
                                p.setGameMode(GameMode.ADVENTURE);
                            } else if (msg.startsWith("[gamemode-spectator]")) {
                                p.setGameMode(GameMode.SPECTATOR);
                            } else if (msg.startsWith("[ping]")) {
                                for (String msg1: ConfigMCommands.getConfig().getStringList("Ping.Self")) {
                                    MessageUtils.ReplaceCharMessagePlayer(msg1, p);
                                }
                            } else if (msg.startsWith("[spawn]: ")) {
                                msg = msg.replace("[spawn]: ", "");
                                SpawnUtils.teleportToSpawn(p, msg);
                            } else if (msg.startsWith("[warp]: ")) {
                                msg = msg.replace("[warp]: ", "");
                                WarpCommand.onTp(p, msg);
                            } else if (msg.startsWith("[bungee]: ")) {
                                msg = msg.replace("[bungee]: ", "");
                                Bungee.changeServer(p, msg);
                            } else if (msg.startsWith("[effect[")) {
                                msg = msg.replace("[effect[", "");

                                String[] parts = msg.split("]]: ");

                                p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(parts[1]), 1999999999, Integer.valueOf(parts[0])));
                            } else if (msg.startsWith("[effectclear]: ")) {
                                msg = msg.replace("[effectclear]: ", "");

                                p.removePotionEffect(PotionEffectType.getByName(msg));
                            } else if (msg.startsWith("[effectclearall]")) {
                                for (PotionEffect effect: p.getActivePotionEffects()) {
                                    p.removePotionEffect(effect.getType());
                                }
                            } else if (msg.startsWith("[send-title]: ")) {
                                msg = msg.replace("[send-title]: ", "");
                                msg = msg.replaceAll("&", "§");

                                Boolean activate = false;

                                String title = "";
                                String subtitle = "";

                                if (msg.contains("//n")) {
                                    String[] parts = msg.split("//n");
                                    title = parts[0];
                                    subtitle = parts[1];

                                    title = title.replaceAll("//n", "");
                                    subtitle = subtitle.replaceAll("//n", "");

                                    activate = true;
                                }

                                if (activate == false) {
                                    TitleUtils.sendTitle(p, 20, 150, 75, msg);
                                } else {
                                    TitleUtils.sendTitle(p, 20, 150, 75, title);
                                    TitleUtils.sendSubtitle(p, 20, 150, 75, subtitle);
                                }
                            } else if (msg.startsWith("[send-title[")) {
                                msg = msg.replace("[send-title[", "");

                                String[] parts = msg.split("]]: ");

                                Boolean activate = false;

                                String title = "";
                                String subtitle = "";

                                if (msg.contains("//n")) {
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
                            } else if (msg.startsWith("[send-actionbar]: ")) {
                                msg = msg.replace("[send-actionbar]: ", "");
                                msg = msg.replaceAll("&", "§");

                                ActionBar.sendActionBar(p, msg);
                            } else if (msg.startsWith("[send-actionbar[")) {
                                msg = msg.replace("[send-actionbar[", "");

                                String[] parts = msg.split("]]: ");

                                ActionBar.sendActionBar(p, parts[1], Integer.valueOf(parts[0]));
                            } else if (msg.startsWith("[sounds]: ")) {
                                msg = msg.replace("[sounds]: ", "");
                                p.playSound(p.getLocation(), XSound.matchXSound(msg).parseSound(), 1, 1);
                            } else {
                                MessageUtils.ReplaceCharMessagePlayer(msg, p);
                            }
                        }
                        e.setCancelled(true);
                    }
                }
            }
        }
    }
}
