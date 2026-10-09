package fr.dianox.hawn.modules.VoidTP;

import fr.dianox.hawn.utility.XParse;

import fr.dianox.hawn.Main;
import fr.dianox.hawn.utility.*;
import fr.dianox.hawn.utility.SpawnGroups;
import fr.dianox.hawn.utility.config.configs.ConfigSpawn;
import fr.dianox.hawn.utility.config.configs.events.OnJoinConfig;
import fr.dianox.hawn.utility.config.configs.events.VoidTPConfig;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMGeneral;
import fr.dianox.hawn.utility.config.configs.messages.ConfigMMsg;
import fr.dianox.hawn.utility.world.BasicEventsPW;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidTPEvent implements Listener {

	// A player under the limit moves many times per second: an error is shown once every 6 seconds
	private static void warnOnce(Player p, Runnable message) {
		if (Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
			return;
		}

		message.run();
		Main.getInstance().getVoidTPManager().getAntispam().add(p);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
	}

	// VoidTP
	@EventHandler
	public void onVoidTP(PlayerMoveEvent e) {
		if (! VoidTPConfig.getConfig().getBoolean("VoidTP.Enable")) {
			return;
		}

		Player p = e.getPlayer();

		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Bypass-With-Permission")) {
			if (p.hasPermission("hawn.bypass.voidtp")) {
				return;
			}
		}

		if (!VoidTPConfig.getConfig().getBoolean("VoidTP.World.All_World")) {
			if (! BasicEventsPW.getVTP().contains(p.getWorld().getName())) {
				return;
			}
		}

		boolean multiworld = false;

		if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.Enable")) {
			if (Main.getInstance().getVoidTPManager().getWorld_voidtp().contains(p.getWorld().getName()) && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + p.getWorld().getName() + ".Enable")) {
				multiworld = true;
			}
		}

		// 21: the height first, nothing to check (and no error to show) above the limit
		String perWorld = "VoidTP.Options.VoidTP-Per-World.World-List." + p.getWorld().getName();
		int limit = multiworld && VoidTPConfig.getConfig().isSet(perWorld + ".TP-y")
				? VoidTPConfig.getConfig().getInt(perWorld + ".TP-y")
				: VoidTPConfig.getConfig().getInt("VoidTP.Options.TP-y");

		if (p.getLocation().getY() > limit) {
			return;
		}

		// 22: "VoidTP: false" for this world, no teleport and no message, sound or command either
		if (multiworld && VoidTPConfig.getConfig().isSet(perWorld + ".VoidTP") && !VoidTPConfig.getConfig().getBoolean(perWorld + ".VoidTP")) {
			return;
		}

		Location loc = p.getLocation();
		String spawn;
		// The default spawn: replaced by the spawn of the group (Spawn.Spawn-Group) only when the player really falls
		boolean defaultSpawn = false;
		String w = p.getWorld().getName();
		int getYConfig;

		if (multiworld) {
			if (VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".TP-y")) {
				if (VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Enable") && VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Enable")) {
					if (VoidTPConfig.getConfig().getString("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
						String Lineerror = "VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn";
						String Fileerror = "Events/VoidTP.yml";
						if (!Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
							if (ConfigMMsg.getConfig().getBoolean("Error.Change-Me.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("Error.Change-Me.Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", Lineerror).replace("%arg2%", Fileerror), "", "", false);
								}

								Main.getInstance().getVoidTPManager().getAntispam().add(p);

								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
							}
						}

						return;
					} else {
						if (ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn"))) {
							spawn = VoidTPConfig.getConfig().getString("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Custom-Spawn.Spawn");
						} else {
							warnOnce(p, () -> MessageUtils.MessageNoSpawn(p));
							return;
						}
					}
				} else {
					if (VoidTPConfig.getConfig().getBoolean("VoidTP.Custom-Spawn.Enable")) {
						if (VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
							String Lineerror = "VoidTP.Custom-Spawn.Spawn";
							String Fileerror = "Events/VoidTP.yml";
							if (!Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
								if (ConfigMMsg.getConfig().getBoolean("Error.Change-Me.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Error.Change-Me.Messages")) {
										ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", Lineerror).replace("%arg2%", Fileerror), "", "", false);
									}

									Main.getInstance().getVoidTPManager().getAntispam().add(p);

									Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
								}
							}

							return;
						} else {
							if (ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn"))) {
								spawn = VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn");
							} else {
								warnOnce(p, () -> MessageUtils.MessageNoSpawn(p));
								return;
							}
						}
					} else {
						if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
							String Lineerror = "Spawn.DefaultSpawn";
							String Fileerror = "Events/OnJoin.yml";
							if (!Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
								if (ConfigMMsg.getConfig().getBoolean("Error.Change-Me.Enable")) {
									for (String msg: ConfigMMsg.getConfig().getStringList("Error.Change-Me.Messages")) {
										ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", Lineerror).replace("%arg2%", Fileerror), "", "", false);
									}

									Main.getInstance().getVoidTPManager().getAntispam().add(p);

									Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
								}
							}

							return;
						} else {
							if (ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
								spawn = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
								defaultSpawn = true;
							} else {
								warnOnce(p, () -> MessageUtils.MessageNoSpawn(p));
								return;
							}
						}
					}
				}

				getYConfig = VoidTPConfig.getConfig().getInt("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".TP-y");
			} else {
				if (VoidTPConfig.getConfig().getBoolean("VoidTP.Custom-Spawn.Enable")) {
					if (VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
						String Lineerror = "VoidTP.Custom-Spawn.Spawn";
						String Fileerror = "Events/VoidTP.yml";
						if (!Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
							if (ConfigMMsg.getConfig().getBoolean("Error.Change-Me.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("Error.Change-Me.Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", Lineerror).replace("%arg2%", Fileerror), "", "", false);
								}

								Main.getInstance().getVoidTPManager().getAntispam().add(p);

								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
							}
						}

						return;
					} else {
						if (ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn"))) {
							spawn = VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn");
						} else {
							warnOnce(p, () -> MessageUtils.MessageNoSpawn(p));
							return;
						}
					}
				} else {
					if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
						String Lineerror = "Spawn.DefaultSpawn";
						String Fileerror = "Events/OnJoin.yml";
						if (!Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
							if (ConfigMMsg.getConfig().getBoolean("Error.Change-Me.Enable")) {
								for (String msg: ConfigMMsg.getConfig().getStringList("Error.Change-Me.Messages")) {
									ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", Lineerror).replace("%arg2%", Fileerror), "", "", false);
								}

								Main.getInstance().getVoidTPManager().getAntispam().add(p);

								Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
							}
						}

						return;
					} else {
						if (ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
							spawn = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
							defaultSpawn = true;
						} else {
							warnOnce(p, () -> MessageUtils.MessageNoSpawn(p));
							return;
						}
					}
				}

				getYConfig = VoidTPConfig.getConfig().getInt("VoidTP.Options.TP-y");
			}
		} else {
			if (VoidTPConfig.getConfig().getBoolean("VoidTP.Custom-Spawn.Enable")) {
				if (VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn").contentEquals("CHANGE ME")) {
					String Lineerror = "VoidTP.Custom-Spawn.Spawn";
					String Fileerror = "Events/VoidTP.yml";
					if (!Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
						if (ConfigMMsg.getConfig().getBoolean("Error.Change-Me.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Error.Change-Me.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", Lineerror).replace("%arg2%", Fileerror), "", "", false);
							}

							Main.getInstance().getVoidTPManager().getAntispam().add(p);

							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
						}
					}

					return;
				} else {
					if (ConfigSpawn.getConfig().isSet("Coordinated." + VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn"))) {
						spawn = VoidTPConfig.getConfig().getString("VoidTP.Custom-Spawn.Spawn");
					} else {
						warnOnce(p, () -> MessageUtils.MessageNoSpawn(p));
						return;
					}
				}
			} else {
				if (OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn").contentEquals("CHANGE ME")) {
					String Lineerror = "Spawn.DefaultSpawn";
					String Fileerror = "Events/OnJoin.yml";
					if (!Main.getInstance().getVoidTPManager().getAntispam().contains(p)) {
						if (ConfigMMsg.getConfig().getBoolean("Error.Change-Me.Enable")) {
							for (String msg: ConfigMMsg.getConfig().getStringList("Error.Change-Me.Messages")) {
								ConfigEventUtils.ExecuteEvent(p, msg.replace("%arg1%", Lineerror).replace("%arg2%", Fileerror), "", "", false);
							}

							Main.getInstance().getVoidTPManager().getAntispam().add(p);

							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> Main.getInstance().getVoidTPManager().getAntispam().remove(p), 120);
						}
					}

					return;
				} else {
					if (ConfigSpawn.getConfig().isSet("Coordinated." + OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn"))) {
						spawn = OnJoinConfig.getConfig().getString("Spawn.DefaultSpawn");
						defaultSpawn = true;
					} else {
						warnOnce(p, () -> MessageUtils.MessageNoSpawn(p));
						return;
					}
				}
			}

			getYConfig = VoidTPConfig.getConfig().getInt("VoidTP.Options.TP-y");
		}

		if (loc.getY() <= getYConfig) {

			if (defaultSpawn) {
				spawn = SpawnGroups.defaultSpawn(p);
			}

			// VoidTP.Options.Spawn-Permission: like the spawn on join, the permission of the spawn can be asked or not
			if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Spawn-Permission.Enable") && !p.hasPermission("hawn.command.spawn." + spawn)) {
				if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Spawn-Permission.No-Permission-Message")) {
					String Permission = "hawn.command.spawn." + spawn;
					warnOnce(p, () -> MessageUtils.MessageNoPermission(p, Permission));
				}
				return;
			}

			if (multiworld) {
				if (VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".VoidTP")) {
					if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".VoidTP")) {
						SpawnUtils.teleportToSpawn(p, spawn);
					}
				} else {
					SpawnUtils.teleportToSpawn(p, spawn);
				}
			} else {
				SpawnUtils.teleportToSpawn(p, spawn);
			}

			if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Message.Custom")) {
				if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Message.Disable")) {
					for (String msg: ConfigMMsg.getConfig().getStringList("Teleport.VoidTP")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
			} else {
				if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Message.Disable")) {
					for (String msg: ConfigMGeneral.getConfig().getStringList("Spawn.Teleport.Messages")) {
						ConfigEventUtils.ExecuteEvent(p, msg, "", "", false);
					}
				}
			}

			if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Sounds.Enable")) {
				String sound = VoidTPConfig.getConfig().getString("VoidTP.Options.Sounds.Sound");
				float volume = (float) VoidTPConfig.getConfig().getDouble("VoidTP.Options.Sounds.Volume");
				float pitch = (float) VoidTPConfig.getConfig().getDouble("VoidTP.Options.Sounds.Pitch");
				p.playSound(p.getLocation(), XParse.sound(sound, "VoidTP.Options.Sounds.Sound"), volume, pitch);
			}

			if (multiworld) {
				if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Enable") && VoidTPConfig.getConfig().isSet("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Enable")) {
					for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Commands")) {
						ConfigEventUtils.ExecuteEvent(p, s, "none", "voidtp", false);
					}

					if (!VoidTPConfig.getConfig().getBoolean("VoidTP.Options.VoidTP-Per-World.World-List." + w + ".Execute-Commands.Override-Default-Commands")) {
						for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.Execute-Commands.Commands")) {
							ConfigEventUtils.ExecuteEvent(p, s, "none", "voidtp", false);
						}
					}
				} else {
					if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Execute-Commands.Enable")) {
						for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.Execute-Commands.Commands")) {
							ConfigEventUtils.ExecuteEvent(p, s, "none", "voidtp", false);
						}
					}
				}
			} else {
				if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Execute-Commands.Enable")) {
					for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.Execute-Commands.Commands")) {
						ConfigEventUtils.ExecuteEvent(p, s, "none", "voidtp", false);
					}
				}
			}

			if (VoidTPConfig.getConfig().getBoolean("VoidTP.Options.Fireworks.Enable")) {
				for (String s: VoidTPConfig.getConfig().getStringList("VoidTP.Options.Fireworks.Firework-List")) {
					if (s.startsWith("[FWLU]: ")) {

						s = s.replace("[FWLU]: ", "");

						OtherUtils.Fireworkmethod(p, s);
					}
				}
			}
		}

	}

}
