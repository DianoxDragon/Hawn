package fr.Dianox.Hawn.Commands.Features.GameMode;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import fr.Dianox.Hawn.Utility.MessageUtils;
import fr.Dianox.Hawn.Utility.Config.Commands.GamemodeCommandConfig;
import fr.Dianox.Hawn.Utility.Config.Messages.ConfigMOStuff;
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
					target.setGameMode(GameMode.SURVIVAL);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_survival+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_survival_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
						}
					}
					
				} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("cretatif") || args[0].equalsIgnoreCase("1")) {
					target.setGameMode(GameMode.CREATIVE);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_creative+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_creative_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
						}
					}
				} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("aventure") || args[0].equalsIgnoreCase("2")) {
					target.setGameMode(GameMode.ADVENTURE);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
						}
					}
				} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spectateur") || args[0].equalsIgnoreCase("3")) {
					target.setGameMode(GameMode.SPECTATOR);
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_spectator+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_spectator+"Messages")) {
							MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", "console"), target);
						}
					}
					
					if (ConfigMCommands.getConfig().getBoolean(msg_other_spectator_sender+"Enable")) {
						for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_spectator_sender+"Messages")) {
							MessageUtils.ReplaceMessageForConsole(msg.replaceAll("%target%", target.getName()));
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
			if (ConfigMOStuff.getConfig().getBoolean("Error.Argument-Missing.Enable")) {
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
				target.setGameMode(GameMode.SURVIVAL);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_survival+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), p);
					}
				}
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_survival_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_survival_sender+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
					}
				}
				
			} else if (args[0].equalsIgnoreCase("creative") || args[0].equalsIgnoreCase("cretatif") || args[0].equalsIgnoreCase("1")) {
				target.setGameMode(GameMode.CREATIVE);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_creative+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), p);
					}
				}
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_creative_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_creative_sender+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
					}
				}
			} else if (args[0].equalsIgnoreCase("adventure") || args[0].equalsIgnoreCase("aventure") || args[0].equalsIgnoreCase("2")) {
				target.setGameMode(GameMode.ADVENTURE);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), p);
					}
				}
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_adventure_sender+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_adventure_sender+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", target.getName()), p);
					}
				}
			} else if (args[0].equalsIgnoreCase("spectator") || args[0].equalsIgnoreCase("spectateur") || args[0].equalsIgnoreCase("3")) {
				target.setGameMode(GameMode.SPECTATOR);
				
				if (ConfigMCommands.getConfig().getBoolean(msg_other_spectator+"Enable")) {
					for (String msg: ConfigMCommands.getConfig().getStringList(msg_other_spectator+"Messages")) {
						MessageUtils.ReplaceCharMessagePlayer(msg.replaceAll("%player%", p.getName()), p);
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
