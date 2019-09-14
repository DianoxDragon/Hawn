package fr.Dianox.Hawn.Commands.Features.GameMode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Main;
import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.GamemodeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
import fr.Dianox.Hawn.Utility.Config.Messages.Administration.OtherAMConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMCommands;

public class ClassicGMCommand extends BukkitCommand {
	
	String GeneralPermission = "hawn.command.gamemode.self";
	String GeneralOtherPermission = "hawn.command.gamemode.other";
	
	String Command_section = "Gamemode.";
	
	String msg_self_survival = "Gamemode.Self.Survival.";
	String msg_self_creative = "Gamemode.Self.Creative.";
	String msg_self_adventure = "Gamemode.Self.Adventure.";
	String msg_self_spectator = "Gamemode.Self.Spectator.";
	
	String msg_other_survival = "Gamemode.Other.Survival.";
	String msg_other_creative = "Gamemode.Other.Creative.";
	String msg_other_adventure = "Gamemode.Other.Adventure.";
	String msg_other_spectator = "Gamemode.Other.Spectator.";
	
	String msg_other_survival_sender = "Gamemode.Other-Sender.Survival.";
	String msg_other_creative_sender = "Gamemode.Other-Sender.Creative.";
	String msg_other_adventure_sender = "Gamemode.Other-Sender.Adventure.";
	String msg_other_spectator_sender = "Gamemode.Other-Sender.Spectator.";
	
	public ClassicGMCommand(String name) {
		super(name);
		this.description = "Easily change the gamemode";
        this.usageMessage = "/gamemode or /gm";
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		
		// >>> Executed by the console
		if(!(sender instanceof Player)) {
			if (args.length == 2) {
				Player target = Bukkit.getServer().getPlayer(args[1]);

				if (target == null) {
					if (ConfigMOStuff.getConfig().getBoolean("Error.No-Players.Enable")) {
						for (String msg: ConfigMOStuff.getConfig().getStringList("Error.No-Players.Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg);
						}
					}
					return true;
				}
				
				if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
					
					if (target.getGameMode() == GameMode.SURVIVAL) {
						if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
								MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
							}
						}
						return true;
					}
					
					if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
						if (target.hasPermission("hawn.command.gamemode.buildmode")) {
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-0")) {
								if (!Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.add(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							} else {
								if (Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.remove(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							}
						}
					}
					
					target.setGameMode(GameMode.SURVIVAL);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_survival+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_survival_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", target.getName()).replaceAll("%target%", target.getName()));
						}
					}
					
				} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("cretatif") || args[0].equalsIgnoreCase("1")) {
					
					if (target.getGameMode() == GameMode.CREATIVE) {
						if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
								MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
							}
						}
						return true;
					}
					
					if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
						if (target.hasPermission("hawn.command.gamemode.buildmode")) {
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-1")) {
								if (!Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.add(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							} else {
								if (Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.remove(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							}
						}
					}
					
					target.setGameMode(GameMode.CREATIVE);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_creative+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_creative_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", target.getName()).replaceAll("%target%", target.getName()));
						}
					}
				} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("aventure") || args[0].equalsIgnoreCase("2")) {
					
					if (target.getGameMode() == GameMode.ADVENTURE) {
						if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
								MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
							}
						}
						return true;
					}
					
					if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
						if (target.hasPermission("hawn.command.gamemode.buildmode")) {
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-2")) {
								if (!Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.add(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							} else {
								if (Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.remove(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							}
						}
					}
					
					target.setGameMode(GameMode.ADVENTURE);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", target.getName()).replaceAll("%target%", target.getName()));
						}
					}
				} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spectateur") || args[0].equalsIgnoreCase("3")) {
					
					if (target.getGameMode() == GameMode.SPECTATOR) {
						if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
							for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
								MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
							}
						}
						return true;
					}
					
					if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
						if (target.hasPermission("hawn.command.gamemode.buildmode")) {
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-3")) {
								if (!Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.add(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							} else {
								if (Main.buildbypasscommand.contains(target)) {
									Main.buildbypasscommand.remove(target);
									
									for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, target);
									}
								}
							}
						}
					}
					
					target.setGameMode(GameMode.SPECTATOR);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_spectator+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_spectator+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_spectator_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_spectator_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%player%", target.getName()).replaceAll("%target%", target.getName()));
						}
					}
				}
				
			} else {
				sender.sendMessage("§c/gamemode or /gm <player>");
			}
			return true;
		}
		
		// >>> Executed by the player
		Player p = (Player) sender;
		
		if (!GamemodeCommandConfig.getConfig().getBoolean(Command_section+"Enable")) {
			if (GamemodeCommandConfig.getConfig().getBoolean(Command_section+"Disable-Message")) {
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
			// If no argument has been put in the command
			if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Quick-Mode-Change.Enable")) {
				if (p.hasPermission("hawn.command.gamemode.quickgm")) {
					Integer mode1 = GamemodeCommandConfig.getConfig().getInt("Gamemode.Options.Quick-Mode-Change.Mode1");
					Integer mode2 = GamemodeCommandConfig.getConfig().getInt("Gamemode.Options.Quick-Mode-Change.Mode2");
					Integer modepriority = GamemodeCommandConfig.getConfig().getInt("Gamemode.Options.Quick-Mode-Change.Default-Mode");
					
					Integer finalmode = 0;
					
					if (p.getGameMode() == GameMode.SURVIVAL) {
						finalmode = 0;
					} else if (p.getGameMode() == GameMode.CREATIVE) {
						finalmode = 1;
					} else if (p.getGameMode() == GameMode.ADVENTURE) {
						finalmode = 2;
					} else if (p.getGameMode() == GameMode.SPECTATOR) {
						finalmode = 3;
					}
					
					if (finalmode == mode1) {
						if (mode2 == 0) {
							if (p.getGameMode() == GameMode.SURVIVAL) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-0")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.SURVIVAL);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_survival+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_survival+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (mode2 == 1) {
							if (p.getGameMode() == GameMode.CREATIVE) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-1")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.CREATIVE);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_creative+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_creative+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (mode2 == 2) {
							if (p.getGameMode() == GameMode.ADVENTURE) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-2")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.ADVENTURE);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_adventure+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_adventure+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (mode2 == 3) {
							if (p.getGameMode() == GameMode.SPECTATOR) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-3")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.SPECTATOR);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_spectator+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_spectator+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					} else if (finalmode == mode2) {
						if (mode1 == 0) {
							if (p.getGameMode() == GameMode.SURVIVAL) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-0")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.SURVIVAL);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_survival+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_survival+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (mode1 == 1) {
							if (p.getGameMode() == GameMode.CREATIVE) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-1")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.CREATIVE);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_creative+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_creative+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (mode1 == 2) {
							if (p.getGameMode() == GameMode.ADVENTURE) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-2")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.ADVENTURE);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_adventure+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_adventure+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (mode1 == 3) {
							if (p.getGameMode() == GameMode.SPECTATOR) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-3")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.SPECTATOR);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_spectator+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_spectator+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					} else {
						if (modepriority == 0) {
							if (p.getGameMode() == GameMode.SURVIVAL) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-0")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.SURVIVAL);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_survival+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_survival+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (modepriority == 1) {
							if (p.getGameMode() == GameMode.CREATIVE) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-1")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.CREATIVE);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_creative+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_creative+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (modepriority == 2) {
							if (p.getGameMode() == GameMode.ADVENTURE) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-2")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.ADVENTURE);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_adventure+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_adventure+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else if (modepriority == 3) {
							if (p.getGameMode() == GameMode.SPECTATOR) {
								if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
									for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
										MessageUtils.ReplaceCharMessagePlayer(msg, p);
									}
								}
								return true;
							}
							
							if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
								if (p.hasPermission("hawn.command.gamemode.buildmode")) {
									if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-3")) {
										if (!Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.add(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									} else {
										if (Main.buildbypasscommand.contains(p)) {
											Main.buildbypasscommand.remove(p);
											
											for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
												MessageUtils.ReplaceCharMessagePlayer(msg, p);
											}
										}
									}
								}
							}
							
							p.setGameMode(GameMode.SPECTATOR);
							if (ConfigMCommands.getConfig().getBoolean(msg_self_spectator+"Enable")) {
								for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_spectator+"Messages")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					}
				} else {
					MessageUtils.MessageNoPermission(p, "hawn.command.gamemode.quickgm");
					return true;
				}
			} else if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
				for (String msg: ConfigMOStuff.getConfig().getStringList("Error.Argument-Missing.Messages")) {
					MessageUtils.ReplaceCharMessagePlayer(msg, p);
				}
			}
		} else if (args.length == 1) {
			// Self gamemode
			if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
				
				if (p.getGameMode() == GameMode.SURVIVAL) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
					if (p.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-0")) {
							if (!Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.add(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.remove(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					}
				}
				
				p.setGameMode(GameMode.SURVIVAL);
				if (ConfigMCommands.getConfig().getBoolean(msg_self_survival+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_survival+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("cretatif") || args[0].equalsIgnoreCase("1")) {
				if (p.getGameMode() == GameMode.CREATIVE) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
					if (p.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-1")) {
							if (!Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.add(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.remove(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					}
				}
				
				p.setGameMode(GameMode.CREATIVE);
				if (ConfigMCommands.getConfig().getBoolean(msg_self_creative+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_creative+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("aventure") || args[0].equalsIgnoreCase("2")) {
				if (p.getGameMode() == GameMode.ADVENTURE) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
					if (p.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-2")) {
							if (!Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.add(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.remove(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					}
				}
				
				p.setGameMode(GameMode.ADVENTURE);
				if (ConfigMCommands.getConfig().getBoolean(msg_self_adventure+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_adventure+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spectateur") || args[0].equalsIgnoreCase("3")) {
				if (p.getGameMode() == GameMode.SPECTATOR) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg, p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable")) {
					if (p.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-3")) {
							if (!Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.add(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(p)) {
								Main.buildbypasscommand.remove(p);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, p);
								}
							}
						}
					}
				}
				
				p.setGameMode(GameMode.SPECTATOR);
				if (ConfigMCommands.getConfig().getBoolean(msg_self_spectator+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_self_spectator+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg, p);
					}
				}
			}
		} else if (args.length == 2) {
			// Gamemode for others players
			if (!p.hasPermission(GeneralOtherPermission)) {
				MessageUtils.MessageNoPermission(p, GeneralPermission);
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[1]);

			if (target == null) {
				MessageUtils.PlayerDoesntExist(p);
				return true;
			}
			
			if (args[0].equalsIgnoreCase("survival") || args[0].equalsIgnoreCase("0")) {
				
				if (target.getGameMode() == GameMode.SURVIVAL) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
					if (target.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-0")) {
							if (!Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.add(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.remove(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						}
					}
				}
				
				target.setGameMode(GameMode.SURVIVAL);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_survival+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), target);
					}
				}
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_survival_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival_sender+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
					}
				}
				
			} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("cretatif") || args[0].equalsIgnoreCase("1")) {
				
				if (target.getGameMode() == GameMode.CREATIVE) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
					if (target.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-1")) {
							if (!Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.add(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.remove(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						}
					}
				}
				
				target.setGameMode(GameMode.CREATIVE);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_creative+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), target);
					}
				}
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_creative_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative_sender+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
					}
				}
			} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("aventure") || args[0].equalsIgnoreCase("2")) {
				
				if (target.getGameMode() == GameMode.ADVENTURE) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
					if (target.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-2")) {
							if (!Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.add(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.remove(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						}
					}
				}
				
				target.setGameMode(GameMode.ADVENTURE);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), target);
					}
				}
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure_sender+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
					}
				}
			} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spectateur") || args[0].equalsIgnoreCase("3")) {
				
				if (target.getGameMode() == GameMode.SPECTATOR) {
					if (ConfigMCommands.getConfig().getBoolean("Gamemode.Error.Alread-In-The-Good-GM-Others.Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList("Gamemode.Error.Alread-In-The-Good-GM-Others.Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%target%", target.getName()), p);
						}
					}
					return true;
				}
				
				if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Enable") && GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.Change-For-Others-Too")) {
					if (target.hasPermission("hawn.command.gamemode.buildmode")) {
						if (GamemodeCommandConfig.getConfig().getBoolean("Gamemode.Options.Hawn-Build-Mode.When-Enter-Into.Gamemode-3")) {
							if (!Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.add(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.On")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						} else {
							if (Main.buildbypasscommand.contains(target)) {
								Main.buildbypasscommand.remove(target);
								
								for (String msg: OtherAMConfig.getConfig().getStringList("Command.Build-Bypass.Off")) {
									MessageUtils.ReplaceCharMessagePlayer(msg, target);
								}
							}
						}
					}
				}
				
				target.setGameMode(GameMode.SPECTATOR);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_spectator+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_spectator+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), target);
					}
				}
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_spectator_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_spectator_sender+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
					}
				}
			}
			
		} else {
			p.sendMessage("§c/gamemode or /gm");
		}
		
		return false;
	}
	
}